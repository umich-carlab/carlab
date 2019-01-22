package edu.umich.carlab.io;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import edu.umich.carlab.DataMarshal;
import edu.umich.carlab.clog.CLog;
import edu.umich.carlab.trips.TripRecord;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Class which accepts data and writes to a file
 */

public class DataDumpWriter {
    private final String TAG = "DumpWriter";
    private String filename;
    private File saveFile;
    private SharedPreferences prefs;

    public DataDumpWriter(Context context) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.US);
        filename = dateFormatter.format(Calendar.getInstance().getTime()) + ".obj";
        saveFile = new File(DataDumpWriter.GetDumpsDir(context), filename);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static File GetDumpsDir(Context context) {
        File tracesDir = context.getExternalFilesDir("dumps");
        tracesDir.mkdirs();
        return tracesDir;
    }

    public void dumpData(List<DataMarshal.DataObject> dataObjects) {
        try {
            FileOutputStream fos = new FileOutputStream(saveFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(dataObjects);
            oos.close();
            fos.close();
            Log.v(TAG, "Saved data dump");
        } catch (Exception e) {
            Log.e(TAG, "Failed to write file");
        }
    }
}
