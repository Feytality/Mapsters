package delta.soen390.mapsters.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.provider.ContactsContract;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Mathieu on 3/26/2015.
 */
public class FileUtility {

    public static String[] getFileInDirectory(Context c, String DirectoryPath)
    {

        try {
            //get a list of all the files in the given directory
            String[] fileList = c.getAssets().list(DirectoryPath);
            return fileList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return new String[0];
    }


    }
