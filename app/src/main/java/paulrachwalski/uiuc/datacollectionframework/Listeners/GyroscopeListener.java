package paulrachwalski.uiuc.datacollectionframework.Listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import paulrachwalski.uiuc.datacollectionframework.Managers.DataCollectionManager;

/**
 * Created by paulrachwalski on 8/31/14.
 */
public class GyroscopeListener implements SensorEventListener {

    private DataCollectionManager dataCollectionManager;

    public GyroscopeListener(DataCollectionManager dataCollectionManager) {
        this.dataCollectionManager = dataCollectionManager;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            dataCollectionManager.setGyroscopeX(x);
            dataCollectionManager.setGyroscopeY(y);
            dataCollectionManager.setGyroscopeZ(z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
