package paulrachwalski.uiuc.datacollectionframework.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import paulrachwalski.uiuc.datacollectionframework.Managers.ProjectManager;
import paulrachwalski.uiuc.datacollectionframework.R;

public class SummaryActivity extends Activity {

    private ActionBar actionBar;
    private TextView projectNameText;
    private TextView userIdText;
    private TextView dateText;
    private TextView startTimeText;
    private TextView endTimeText;
    private TextView numberOfLabelsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        long startTime = getIntent().getLongExtra("startTime", 0);
        long endTime = getIntent().getLongExtra("endTime", 0);

        projectNameText = (TextView) findViewById(R.id.project_name);
        userIdText = (TextView) findViewById(R.id.user_id);
        dateText = (TextView) findViewById(R.id.date);
        startTimeText = (TextView) findViewById(R.id.start_time);
        endTimeText = (TextView) findViewById(R.id.end_time);
        numberOfLabelsText = (TextView) findViewById(R.id.number_labels);

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

        Date startTimeDate = new Date(startTime);
        Date endTimeDate = new Date(endTime);
        SimpleDateFormat startTimeFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        SimpleDateFormat endTimeFormat = new SimpleDateFormat("HH:mm:ss.SSS");

        ProjectManager pm = ProjectManager.getInstance();
        projectNameText.setText(pm.getName());
        userIdText.setText(pm.getUserId());
        dateText.setText(dateFormat.format(date));
        startTimeText.setText(startTimeFormat.format(startTimeDate));
        endTimeText.setText(endTimeFormat.format(endTimeDate));
        numberOfLabelsText.setText("" + pm.getNumberOfLabels());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.summary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
