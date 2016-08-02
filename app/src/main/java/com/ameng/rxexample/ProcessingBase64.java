package com.ameng.rxexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by ameng on 8/1/16.
 */
public class ProcessingBase64 {
    private static ByteArrayOutputStream stream = null;
    private Context context;

    public static ByteArrayOutputStream getStream() {
        if (stream == null) {
            synchronized (ProcessingBase64.class) {
                if (stream == null) {
                    stream = new ByteArrayOutputStream();
                }
            }
        }
        return stream;
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        getStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    public String getStringImage(Bitmap bitmap) {
        getStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageBytes = stream.toByteArray();
        String input = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return input;
    }

    public void decodeImage(ImageView imageView, String input) {
        byte[] decodedByte = Base64.decode(input, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        imageView.setImageBitmap(bitmap);
    }

    public Bitmap decodeImage(byte[] bytes) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        if (bitmap==null){
            throw new RuntimeException("bitmap==null!");
        }
        return bitmap;
    }

    public Bitmap getGrayScale(Bitmap bitmap){
        Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return mutableBitmap;
    }
}
