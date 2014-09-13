package paulrachwalski.uiuc.datacollectionframework.Managers;

import android.content.Context;
import android.provider.ContactsContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by paulrachwalski on 8/31/14.
 */
public class DataCollectionManager {

    private Context context;


    // Should collect data?
    private boolean shouldCollectData;


    // File to add data to
    DataFileManager dataFileManager;


    // The current values of all sensor data
    private float accelerometerX;
    private float accelerometerY;
    private float accelerometerZ;

    private float gyroscopeX;
    private float gyroscopeY;
    private float gyroscopeZ;

    private float azimuth;

    private float gpsLatitude;
    private float gpsLongitude;
    private float gpsSpeed;

    private ArrayList<String> wifiSSIDs;
    private int cellTowerId;


    // Constructors
    public DataCollectionManager(Context context) {
        this.context = context;
        dataFileManager = new DataFileManager(context, "" + ProjectManager.getInstance().getName() + ".txt");
        shouldCollectData = false;

        accelerometerX = 0;
        accelerometerY = 0;
        accelerometerZ = 0;
        gyroscopeX = 0;
        gyroscopeY = 0;
        gyroscopeZ = 0;
        azimuth = 0;
        wifiSSIDs = new ArrayList<String>();
        cellTowerId = 0;
    }


    // Dumps the current data to a line in a file
    public void dumpToFile() {
        if (shouldCollectData) {
            Date now = new Date();
            String timestamp = new SimpleDateFormat("yyyy-MM-dd--HH:mm:ss.SSSZ").format(now);

            String dataLine = "";
            dataLine += " " + timestamp;
            int activeLabel = ProjectManager.getInstance().getActiveLabel();
            dataLine += " " + ( (activeLabel == 0) ? "NULL" : ProjectManager.getInstance().getLabel(activeLabel - 1) );
            dataLine += " " + accelerometerX + " " + accelerometerY + " " + accelerometerZ;
            dataLine += " " + gyroscopeX + " " + gyroscopeY + " " + gyroscopeZ;
            dataLine += " " + azimuth;
            dataLine += " " + gpsLatitude + " " + gpsLongitude + " " + gpsSpeed;
            if (wifiSSIDs.size() > 0) {
                for (int i = 0; i < wifiSSIDs.size(); i++) {
                    dataLine += " \"" + wifiSSIDs.get(i) + "\"";
                }
            } else {
                dataLine += " NULL";
            }
            dataLine += " " + cellTowerId;
            dataLine += "\n";
            dataFileManager.addEntry(dataLine);
        }
    }


    // Collection functions
    public void startCollecting() {
        this.shouldCollectData = true;
        dataFileManager.openFile();
    }

    public void stopCollecting() {
        this.shouldCollectData = false;
        dataFileManager.closeFile();
    }


    // Setters
    public void setAccelerometerX(float x) {
        this.accelerometerX = x;
        dumpToFile();
    }

    public void setAccelerometerY(float y) {
        this.accelerometerY = y;
        dumpToFile();
    }

    public void setAccelerometerZ(float z) {
        this.accelerometerZ = z;
        dumpToFile();
    }

    public void setGyroscopeX(float x) {
        this.gyroscopeX = x;
        dumpToFile();
    }

    public void setGyroscopeY(float y) {
        this.gyroscopeY = y;
        dumpToFile();
    }

    public void setGyroscopeZ(float z) {
        this.gyroscopeZ = z;
        dumpToFile();
    }

    public void setAzimuth(float azimuth) {
        this.azimuth = azimuth;
        dumpToFile();
    }

    public void setGpsLatitude(float latitude) {
        this.gpsLatitude = latitude;
        dumpToFile();
    }

    public void setGpsLongitude(float longitude) {
        this.gpsLongitude = longitude;
        dumpToFile();
    }

    public void setGpsSpeed(float speed) {
        this.gpsSpeed = speed;
        dumpToFile();
    }

    public void setWifiSSIDs(ArrayList<String> ssids) {
        this.wifiSSIDs = ssids;
        dumpToFile();
    }

    public void setCellTowerId(int id) {
        this.cellTowerId = id;
        dumpToFile();
    }


    // Getters
    public boolean isCollectingData() {
        return shouldCollectData;
    }
}
