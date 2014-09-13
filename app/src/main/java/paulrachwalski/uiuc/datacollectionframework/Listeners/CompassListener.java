package paulrachwalski.uiuc.datacollectionframework.Listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import paulrachwalski.uiuc.datacollectionframework.Managers.DataCollectionManager;

/**
 * Created by paulrachwalski on 8/31/14.
 */
public class CompassListener implements SensorEventListener {

    private DataCollectionManager dataCollectionManager;

    private float[] gravityMatrix;
    private float[] geomagneticMatrix;
    private float[] rotationMatrix;

    public CompassListener(DataCollectionManager dataCollectionManager) {
        this.dataCollectionManager = dataCollectionManager;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            gravityMatrix = event.values;

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            geomagneticMatrix = event.values;

        if (gravityMatrix != null && geomagneticMatrix != null) {
            float R[] = new float[9];
            float I[] = new float[9];

            boolean success = SensorManager.getRotationMatrix(R, I, gravityMatrix, geomagneticMatrix);

            if (success) {
                rotationMatrix = new float[3];
                SensorManager.getOrientation(R, rotationMatrix);
                dataCollectionManager.setAzimuth(rotationMatrix[0]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
