package paulrachwalski.uiuc.datacollectionframework.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import paulrachwalski.uiuc.datacollectionframework.Adapters.BriefLabelArrayAdapter;
import paulrachwalski.uiuc.datacollectionframework.Data.TimeUtils;
import paulrachwalski.uiuc.datacollectionframework.Listeners.AccelerometerListener;
import paulrachwalski.uiuc.datacollectionframework.Listeners.CellTowerListener;
import paulrachwalski.uiuc.datacollectionframework.Listeners.CompassListener;
import paulrachwalski.uiuc.datacollectionframework.Listeners.GPSListener;
import paulrachwalski.uiuc.datacollectionframework.Listeners.GyroscopeListener;
import paulrachwalski.uiuc.datacollectionframework.Listeners.WifiSSIDListener;
import paulrachwalski.uiuc.datacollectionframework.Managers.DataCollectionManager;
import paulrachwalski.uiuc.datacollectionframework.Managers.ProjectManager;
import paulrachwalski.uiuc.datacollectionframework.R;


public class CollectionActivity extends Activity{

    private SensorManager sensorManager;
    private LocationManager locationManager;
    private DataCollectionManager dataCollectionManager;

    private AccelerometerListener accelerometerListener;
    private GyroscopeListener gyroscopeListener;
    private CompassListener compassListener;
    private GPSListener gpsListener;
    private WifiSSIDListener wifiSSIDListener;
    private CellTowerListener cellTowerListener;
    private Thread clockThread;

    private ActionBar actionBar;
    private Button startButton;
    private Button stopButton;
    private Button addLabelButton;
    private TextView clockText;
    private ListView labelsListView;
    private BriefLabelArrayAdapter briefLabelArrayAdapter;
    private List<String> labels;

    private boolean clockRunning;
    private long startMillis;
    private long endMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        dataCollectionManager = new DataCollectionManager(this);
        briefLabelArrayAdapter = new BriefLabelArrayAdapter(this);

        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(ProjectManager.getInstance().getName());

        startButton = (Button) findViewById(R.id.start_button);
        stopButton = (Button) findViewById(R.id.stop_button);
        addLabelButton = (Button) findViewById(R.id.add_label_button);
        labelsListView = (ListView) findViewById(R.id.label_quick_list);
        clockText = (TextView) findViewById(R.id.time_stamp_text);

        labels = ProjectManager.getInstance().getLabels();
        clockRunning = false;

        initializeListeners();
        registerSensorListeners();
        registerButtonListeners();
        setupLabelsList();

        final Handler handler = new Handler();
        clockThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (clockRunning) {
                    try {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                updateClock();
                            }
                        });
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        Log.e("Clock interrupted", e.toString());
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initializeListeners() {
        accelerometerListener = new AccelerometerListener(dataCollectionManager);
        gyroscopeListener = new GyroscopeListener(dataCollectionManager);
        compassListener = new CompassListener(dataCollectionManager);
        gpsListener = new GPSListener(this, dataCollectionManager);
        wifiSSIDListener = new WifiSSIDListener(this, dataCollectionManager);
        cellTowerListener = new CellTowerListener(this, dataCollectionManager);
    }

    private void registerSensorListeners() {
        sensorManager.registerListener(accelerometerListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(gyroscopeListener, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(compassListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(compassListener, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_UI);
    }

    private void unregisterSensorListeners() {
        sensorManager.unregisterListener(accelerometerListener);
        sensorManager.unregisterListener(gyroscopeListener);
        sensorManager.unregisterListener(compassListener);
    }

    private void registerButtonListeners() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dataCollectionManager.isCollectingData()) {
                    dataCollectionManager.startCollecting();
                    CollectionActivity.this.runClock();

                    Log.i("File directory", CollectionActivity.this.getExternalFilesDir(null).toString());

                    v.setBackgroundResource(R.drawable.round_rect_button);
                    stopButton.setBackgroundResource(R.drawable.round_rect_button_red);
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataCollectionManager.isCollectingData()) {
                    dataCollectionManager.stopCollecting();
                    CollectionActivity.this.stopClock();

                    v.setBackgroundResource(R.drawable.round_rect_button);
                    startButton.setBackgroundResource(R.drawable.round_rect_button_green);

                    Intent i = new Intent(CollectionActivity.this, SummaryActivity.class);
                    i.putExtra("startTime", startMillis);
                    i.putExtra("endTime", endMillis);
                    startActivity(i);
                }
            }
        });

        addLabelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLabelDialog();
            }
        });
    }

    private void setupLabelsList() {
        labelsListView.setAdapter(briefLabelArrayAdapter);

        labelsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProjectManager.getInstance().setActiveLabel(position);
                briefLabelArrayAdapter.notifyDataSetChanged();
            }
        });
    }

    private void addLabelDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Enter Label Name");
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String labelName = input.getText().toString();
                ProjectManager.getInstance().addLabel(labelName);
                briefLabelArrayAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    private void runClock() {
        clockRunning = true;
        startMillis = System.currentTimeMillis();
        clockThread.start();
    }

    private void updateClock() {
        long timeDiff = System.currentTimeMillis() - startMillis;
        String totalTime = TimeUtils.convertMillisToString(timeDiff);
        clockText.setText(totalTime);
    }

    private void stopClock() {
        clockRunning = false;
        endMillis = System.currentTimeMillis();

        long timeDiff = endMillis - startMillis;
        String totalTime = TimeUtils.convertMillisToString(timeDiff);
        clockText.setText(totalTime);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerButtonListeners();
        wifiSSIDListener.registerReciever();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterSensorListeners();
        wifiSSIDListener.unregisterReciever();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.collection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
