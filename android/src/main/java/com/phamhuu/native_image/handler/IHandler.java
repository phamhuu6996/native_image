package com.phamhuu.native_image.handler;

import android.graphics.Bitmap;

import com.phamhuu.native_image.option.Option;
import com.phamhuu.native_image.option.Text;

import java.util.List;

public abstract class IHandler {

    public abstract Bitmap runByte(Bitmap bitmap);

    public abstract String runFile();

    public Bitmap handlerBitMap(List<Option> options, Bitmap bitmap) throws Throwable{
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i) instanceof Text) {
                Text text = (Text) options.get(i);
                IHandler addTextHandler = new AddTextHandler(text);
                bitmap = addTextHandler.runByte(bitmap);
            }
        }
        return  bitmap;
    }

}
