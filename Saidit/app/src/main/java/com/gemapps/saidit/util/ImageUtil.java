/*
 *    Copyright 2017 Edu Graciano
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gemapps.saidit.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.IntDef;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by edu on 3/23/17.
 */

public class ImageUtil {
    private static final String TAG = "ImageUtil";
    private static final String PERMISSION_DENIED = "Permission denied";
    @IntDef(flag=true, value={SAVED, UNKNOWN_FAIL, PERMISSION_FAIL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ImageSaveState{}
    public static final int SAVED = 0;
    public static final int UNKNOWN_FAIL = 1;
    public static final int PERMISSION_FAIL = 1<<1;

    public static @ImageSaveState int saveImage(Context context, Bitmap bitmap) {

        try {
            File file = createImageFile(context);
            return saveImageIntoFile(bitmap, file) ? SAVED : UNKNOWN_FAIL;
        } catch (IOException e) {
            Log.d(TAG, "saveImage: "+e.getLocalizedMessage());
            e.printStackTrace();
            return e.getLocalizedMessage().equals(PERMISSION_DENIED) ? PERMISSION_FAIL : UNKNOWN_FAIL;
        }
    }

    private static File createImageFile(Context context) throws IOException {

        String imageFileName = createFileName();
//        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "SaidIt");

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("SaidIt", "failed to create directory");
                return null;
            }
        }

        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                mediaStorageDir      /* directory */
        );
    }

    private static boolean saveImageIntoFile(Bitmap bitmap, File file) throws IOException {
        if(file == null) return false;

        FileOutputStream out = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
        out.flush();
        out.close();
        return true;
    }

    private static String createFileName(){
        return DateUtil.getToday() + "_";
    }

    private static Intent galleryAddPic(String filePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(filePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        return mediaScanIntent;
    }

}
