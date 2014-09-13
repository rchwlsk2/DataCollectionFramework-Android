package paulrachwalski.uiuc.datacollectionframework.Listeners;

import android.content.Context;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import paulrachwalski.uiuc.datacollectionframework.Managers.DataCollectionManager;

/**
 * Created by paulrachwalski on 8/31/14.
 */
public class CellTowerListener  {

    private Context context;
    private TelephonyManager telephonyManager;

    private GsmCellLocation gsmCellLocation;

    public CellTowerListener(Context context, final DataCollectionManager dataCollectionManager) {
        this.context = context;
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final Handler handler = new Handler();
        Thread cellTowerListener = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                gsmCellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
                                dataCollectionManager.setCellTowerId(gsmCellLocation.getCid());
                            }
                        });
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Log.e("Could not get cell tower id", e.toString());
                        e.printStackTrace();
                    }
                }
            }
        });

        cellTowerListener.start();
    }
}
