package paulrachwalski.uiuc.datacollectionframework.Managers;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import paulrachwalski.uiuc.datacollectionframework.Data.DataType;

/**
 * Created by paulrachwalski on 9/1/14.
 */
public class ProjectManager {

    // Singleton instance of ProjectManager
    private static ProjectManager instance;


    // Singleton methods
    public static void newInstance() {
        if (instance == null) {
            instance = new ProjectManager();
        }
    }

    public static ProjectManager getInstance() {
        return instance;
    }


    // Project data
    private String name;
    private String id;
    private String userId;

    private List<String> labels;
    private int activeLabel;

    private DataType[] dataTypes;


    // Constructors
    public ProjectManager() {
        initializeDataTypes();
        labels = new ArrayList<String>();

        //setupExampleVals();
    }

    public void initializeDataTypes() {
        DataType[] tempDataTypes = {DataType.ACCELEROMETER, DataType.BAROMETER, DataType.CELLTOWER,
                DataType.COMPASS, DataType.GPS, DataType.GYROSCOPE, DataType.MICROPHONE, DataType.WIFI};

        dataTypes = tempDataTypes;
    }

    private void setupExampleVals() {
        name = "Test Project";
        id = "12345678";
        userId = "Paul";

        labels.add("Walking");
        labels.add("Running");
        labels.add("Elevator");
        labels.add("Escalator");
        labels.add("Biking");
    }


    // Getters
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public int getActiveLabel() {
        return activeLabel;
    }

    public String getLabel(int index) {
        return labels.get(index);
    }

    public List<String> getLabels() {
        return labels;
    }

    public int getNumberOfLabels() {
        return labels.size();
    }

    public DataType[] getDataTypes() {
        return dataTypes;
    }

    public DataType getDataType(int index) {
        if (index >= 0 && index < dataTypes.length) {
            return dataTypes[index];
        }

        return null;
    }

    public boolean isDataTypeEnables(int index) {
        if (index >= 0 && index < dataTypes.length) {
            return dataTypes[index].isUsing();
        }

        return false;
    }


    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setActiveLabel(int activeLabel) {
        this.activeLabel = activeLabel;
    }

    public void addLabel(String label) {
        labels.add(label);
    }

    public void removeLabel(int index) {
        if (labels.size() > index && index >= 0)
            labels.remove(index);
        else
            Log.e("No matching label", "Label at " + index + " does not exist.");
    }
}
