package com.phamhuu.native_image.handler;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.phamhuu.native_image.option.Rotate;

public class RotateHandler extends HandlerImp{
    public final Rotate rotate;

    public RotateHandler(Rotate rotate) {
        this.rotate = rotate;
    }

    private Bitmap rotateImage(Bitmap bitmap, Rotate rotate) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate.degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    @Override
    public Bitmap runByte(Bitmap bitmap) {
        return rotateImage(bitmap, rotate);
    }

    @Override
    public String runFile() {
        return null;
    }
}
