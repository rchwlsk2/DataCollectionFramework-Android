package paulrachwalski.uiuc.datacollectionframework.Listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import paulrachwalski.uiuc.datacollectionframework.Managers.DataCollectionManager;

/**
 * Created by paulrachwalski on 8/31/14.
 */
public class WifiSSIDListener {

    private Context context;
    private DataCollectionManager dataCollectionManager;
    private WifiManager wifiManager;
    private BroadcastReceiver broadcastReceiver;

    private List<ScanResult> scanResults;
    private ArrayList<String> ssids;

    public WifiSSIDListener(Context context, final DataCollectionManager dataCollectionManager) {
        this.context = context;
        this.dataCollectionManager = dataCollectionManager;
        ssids = new ArrayList<String>();

        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                scanResults = wifiManager.getScanResults();
                ssids.clear();
                for (int i = 0; i < scanResults.size(); i ++) {
                    ssids.add(scanResults.get(i).SSID);
                }
                dataCollectionManager.setWifiSSIDs(ssids);
            }
        };

        registerReciever();

        final Handler handler = new Handler();
        Thread scanThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                wifiManager.startScan();
                            }
                        });
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.e("Wifi scan interrupted", e.toString());
                        e.printStackTrace();
                    }
                }
            }
        });
        scanThread.start();
    }

    public void registerReciever() {
        context.registerReceiver(broadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

    }

    public void unregisterReciever() {
        context.unregisterReceiver(broadcastReceiver);
    }
}
