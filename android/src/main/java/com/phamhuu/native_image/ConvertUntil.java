package com.phamhuu.native_image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;

import com.phamhuu.native_image.option.Option;
import com.phamhuu.native_image.option.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;

public class ConvertUntil {

    static ConvertUntil instance = new ConvertUntil();
    static final String ADD_TEXT = "add_text";
    static final String ROTATE = "rotate";

    public String getPath(MethodCall call) throws Throwable {
        return call.argument("path");
    }

    public byte[] getMemory(MethodCall call) throws Throwable {
        return null;
    }

    public List<Option> getOptions(MethodCall call) throws Throwable {
        List<Map> mapList = call.argument("result");
        List<Option> optionList = new ArrayList<>();
        for (int i = 0; i < mapList.size(); i++) {
            String key = mapList.get(i).get("key").toString();
            Map<String, Object> map = (Map<String, Object>) mapList.get(i).get("value");
            switch (key) {
                case ADD_TEXT:
                    optionList.add(convertToText(map));
                    break;
                case ROTATE:
                    break;
            }
        }
        return optionList;
    }

    private Text convertToText(Map<String, Object> map) throws Throwable {
        for (int i = 0; i < map.size(); i++) {
            String label = map.containsKey("label") ? map.get("label").toString() : "";
            int size = map.containsKey("size") ? (int) map.get("size") : Text.SIZE;
            Paint.Align textAlign = Text.setAlignText(map.containsKey("text_align") ? (int) map.get("text_align") : Text.RIGHT);
            int gravity = map.containsKey("gravity") ? (int) map.get("gravity") : Text.LEFT;
            int color = map.containsKey("color") ? (int) map.get("color"): Text.COLOR;
            int horPadding = map.containsKey("x") ? (int) map.get("x") : Text.PADDING;
            int verPadding = map.containsKey("y") ? (int) map.get("y") : Text.PADDING;
            return new Text(label, size, textAlign, gravity, color, horPadding, verPadding);
        }
        throw new NoSuchFieldException();
    }

    public Bitmap rotateExif(Bitmap bitmap, ExifInterface exifInterface) throws Throwable {
        float degree = 0;

        switch (exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1)) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                degree = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                degree = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                degree = 270;
                break;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public Bitmap getBitMapPath(String path) throws Throwable {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inMutable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, option);
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (exifInterface != null)
            bitmap = rotateExif(bitmap, exifInterface);
        return bitmap;
    }
}

