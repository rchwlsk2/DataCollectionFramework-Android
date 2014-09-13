package paulrachwalski.uiuc.datacollectionframework.Listeners;

import android.app.Service;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import paulrachwalski.uiuc.datacollectionframework.Managers.DataCollectionManager;

/**
 * Created by paulrachwalski on 8/31/14.
 */
public class GPSListener implements LocationListener {

    private Context context;
    private DataCollectionManager dataCollectionManager;

    public GPSListener(Context context, DataCollectionManager dataCollectionManager) {
        this.context = context;
        this.dataCollectionManager = dataCollectionManager;

        LocationManager locationManager = (LocationManager) context.getSystemService(Service.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        /*if(isGPSEnabled){

            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(location != null)
            {
                dataCollectionManager.setGpsLongitude((float) location.getLongitude());
                dataCollectionManager.setGpsLatitude((float) location.getLatitude());

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }else
            {

                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if(location != null)
                {
                    dataCollectionManager.setGpsLongitude((float) location.getLongitude());
                    dataCollectionManager.setGpsLatitude((float) location.getLatitude());

                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                }else
                {
                    dataCollectionManager.setGpsLongitude(0.0f);
                    dataCollectionManager.setGpsLatitude(0.0f);
                }
            }
        }*/
    }

    @Override
    public void onLocationChanged(Location location)
    {
        dataCollectionManager.setGpsLatitude((float) location.getLatitude());
        dataCollectionManager.setGpsLongitude((float)location.getLongitude());
        dataCollectionManager.setGpsSpeed(location.getSpeed());
        Log.i("Got location data", "" + location.getLatitude() + ", " + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle b)
    {
    }
    @Override
    public void onProviderDisabled(String s)
    {
    }
    @Override
    public void onProviderEnabled(String s)
    {
    }
}
