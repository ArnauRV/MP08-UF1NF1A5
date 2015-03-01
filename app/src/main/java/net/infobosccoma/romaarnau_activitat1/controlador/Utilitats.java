package net.infobosccoma.romaarnau_activitat1.controlador;

/**
 * Created by Arnau on 22/02/2015.
 */

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;

public class Utilitats {

    /**
     * Mètode que obté un array de bytes a partir de un bitmap
     * @param bitmap
     * @return es retorna l'array de bytes
     */
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        return stream.toByteArray();
    }

    /**
     * Mètode que converteix bytes a bitmap
     * @param image
     * @return es retorna un bitmap
     */
    public static Bitmap getPhoto(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    /**
     * Mètode que a partir de un path, width i height retorna un bitmap amb una les mides passades
     * @param path
     * @param reqWidth
     * @param reqHeight
     * @return es retorna el bitmap de la imatge
     */
    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * Mètode per obtenir el path absolut de qualsevol imatge
     * @param content
     * @param uri
     * @return un string que conté le path de la imatge
     */
    public static String getAbsolutePath(ContentResolver content, Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};

        Cursor cursor = content.query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }
}
