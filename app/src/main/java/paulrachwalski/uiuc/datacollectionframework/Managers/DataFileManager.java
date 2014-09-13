package paulrachwalski.uiuc.datacollectionframework.Managers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by paulrachwalski on 8/31/14.
 */
public class DataFileManager {

    private Context context;
    private FileWriter fileWriter;
    private String filename;
    private String filePath;

    public DataFileManager(Context context, String filename) {
        this.context = context;
        this.filename = filename;
        openFile();
    }

    public void openFile() {
        try {
            File dataFile = new File(context.getExternalFilesDir(null), filename);

            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }

            this.filePath = dataFile.getAbsolutePath();
            fileWriter = new FileWriter(filePath, true);

        } catch (IOException e) {
            fileWriter = null;
            Log.e("Could not open file for writing", e.toString());
            e.printStackTrace();
        }
    }

    public void addEntry(String entry) {
        if (fileWriter != null) {
           try {
               fileWriter.append(entry);
           } catch (IOException e) {
               Log.e("Could not write to sensor log", e.toString());
               e.printStackTrace();
           }
        } else {
            Log.i("Cannot write to file", "FileWriter not intitialized");
        }
    }

    public void closeFile() {
        if (fileWriter != null) {
            try {
                fileWriter.close();
            } catch (IOException e) {
                Log.e("Could not close file", e.toString());
                e.printStackTrace();
            }
        } else {
            Log.i("Cannot close file", "FileWriter not intitialized");
        }
    }

    public void sendFile() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setType("application/text");
        i.putExtra(Intent.EXTRA_STREAM, Uri.parse(filePath));
        context.startActivity(i);
    }
}
