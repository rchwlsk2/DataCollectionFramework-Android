package paulrachwalski.uiuc.datacollectionframework.Data;

import paulrachwalski.uiuc.datacollectionframework.R;

/**
 * Created by paulrachwalski on 9/4/14.
 */
public enum DataType {

    ACCELEROMETER("Accelerometer", R.drawable.accelerometer, true),
    BAROMETER("Barometer", R.drawable.barometer, true),
    CELLTOWER("Cell Tower", R.drawable.celltower, true),
    COMPASS("Compass", R.drawable.compass, true),
    GPS("GPS", R.drawable.gps, true),
    GYROSCOPE("Gyroscope", R.drawable.gyroscope, true),
    MICROPHONE("Microphone", R.drawable.microphone, true),
    WIFI("WiFi", R.drawable.wifi, true);

    private String name;
    private int drawable;
    private boolean using;

    private DataType(String s, int i, boolean b) {
        name = s;
        drawable = i;
        using = b;
    }

    public String getName() {
        return name;
    }

    public int getDrawable() {
        return drawable;
    }

    public boolean isUsing() {
        return using;
    }

    public void setUsing(Boolean b) {
        using = b;
    }

}
