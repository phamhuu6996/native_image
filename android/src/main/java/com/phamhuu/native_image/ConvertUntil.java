package com.phamhuu.native_image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.media.ExifInterface;

import com.phamhuu.native_image.option.Option;
import com.phamhuu.native_image.option.Rotate;
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
                    optionList.add(convertToRotate(map));
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
            int color = map.containsKey("color") ? (int) map.get("color") : Text.COLOR;
            int horPadding = map.containsKey("x") ? (int) map.get("x") : Text.PADDING;
            int verPadding = map.containsKey("y") ? (int) map.get("y") : Text.PADDING;
            return new Text(label, size, textAlign, gravity, color, horPadding, verPadding);
        }
        throw new NoSuchFieldException();
    }

    private Rotate convertToRotate(Map<String, Object> map) throws Throwable {
        for (int i = 0; i < map.size(); i++) {
            float degree = map.containsKey("degree") ? ((Double) map.get("degree")).floatValue() : 0f;
            return new Rotate(degree);
        }
        throw new NoSuchFieldException();
    }

    public Bitmap getBitMapPath(String path) throws Throwable {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inMutable = true;
        return BitmapFactory.decodeFile(path, option);
    }

    public float getDegreeExif(String path) throws Throwable {
        float degree = 0;
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        return degree;
    }

    public void replaceRotateWithExif(float degreeExif , List<Option> options){
        boolean isReplace = false;
        for(int i = 0 ; i<options.size(); i++){
            if(options.get(i) instanceof Rotate){
               Rotate rotateReplace =new Rotate((((Rotate) options.get(i)).degree)+ degreeExif);
                options.set(i,rotateReplace);
                isReplace = true;
                break;
            }
        }
        if(!isReplace){
            options.add(0,new Rotate(degreeExif));
        }
    }
}

