package xyz.fadhilprawira.jmp_surveihakpilihkpu.utils;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import xyz.fadhilprawira.jmp_surveihakpilihkpu.BuildConfig;

public class ImageUtils {

    // Constants
    private static final String FILENAME_FORMAT = "yyyyMMdd_HHmmss";

    // Generate a timestamp for file naming
    private static final String timeStamp = new SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(new Date());
    public static Uri getImageUri(Context context) {
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timeStamp + ".jpg");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/MyCamera/");

            uri = context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
            );
            Log.d("Image URI", "getImageUri (Q+): " + uri);
        }

        return uri != null ? uri : getImageUriForPreQ(context);
    }


    private static Uri getImageUriForPreQ(Context context) {
        // Get external files directory for pictures
        File filesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Create the image file in "MyCamera" folder with a timestamp in its name
        File imageFile = new File(filesDir, "/MyCamera/" + timeStamp + ".jpg");

        // Create the parent directory if it doesn't exist
        if (imageFile.getParentFile() != null && !imageFile.getParentFile().exists()) {
            imageFile.getParentFile().mkdir();
        }

        // Get the URI using FileProvider
        return FileProvider.getUriForFile(
                context,
                BuildConfig.APPLICATION_ID + ".fileprovider",
                imageFile
        );
    }
}
