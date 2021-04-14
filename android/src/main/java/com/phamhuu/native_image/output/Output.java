package com.phamhuu.native_image.output;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class Output {
    public static final Output instance = new Output();

    public byte[] getOutputByte(Bitmap bitmap) throws Throwable {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] bytes = outputStream.toByteArray();
        outputStream.close();
        return bytes;
    }

    public String getOutputFile(Bitmap bitmap, String path) throws Throwable {
        FileOutputStream outputStream = new FileOutputStream(path);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        outputStream.flush();
        outputStream.close();
        return path;
    }
}
