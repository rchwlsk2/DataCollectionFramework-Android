package paulrachwalski.uiuc.datacollectionframework.Application;

import android.app.Application;
import android.os.Bundle;

import paulrachwalski.uiuc.datacollectionframework.Managers.ProjectManager;

/**
 * Created by paulrachwalski on 9/2/14.
 */
public class SensorApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ProjectManager.newInstance();
    }
}
