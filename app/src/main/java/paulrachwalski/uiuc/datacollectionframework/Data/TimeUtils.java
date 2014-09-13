package paulrachwalski.uiuc.datacollectionframework.Data;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by paulrachwalski on 9/4/14.
 */
public class TimeUtils {

    public static String convertMillisToString(long millis) {
        Date date = new Date(millis);
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS");
        String result = sdf.format(date);

        Log.i("DATE", result);

        return result;
    }
}
