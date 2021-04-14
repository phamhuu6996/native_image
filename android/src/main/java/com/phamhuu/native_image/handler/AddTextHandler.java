package com.phamhuu.native_image.handler;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Size;

import com.phamhuu.native_image.option.Text;

public class AddTextHandler extends HandlerImp {
    final Text text;

    public AddTextHandler(Text text) {
        this.text = text;
    }

    private Paint getTextPaint(Text text) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextSize(text.size);
        paint.setShadowLayer(2f, 2f, 2f, Color.BLACK);
        paint.setTextAlign(text.textAlign);
        return paint;
    }

    @SuppressLint("NewApi")
    public Size getLocationText(Canvas canvas, Text text) {
        if (text.textAlign == Paint.Align.CENTER) {
            return new Size(canvas.getWidth() / 2, text.size + text.verPadding);
        } else if (text.textAlign == Paint.Align.RIGHT) {
            return new Size(canvas.getWidth() - text.horPadding, text.size + text.verPadding);
        } else if (text.textAlign == Paint.Align.LEFT) {
            return new Size(text.horPadding, text.size + text.verPadding);
        }
        return new Size(0, 0);
    }

    @SuppressLint("NewApi")
    private Bitmap drawTextImage(Bitmap bitmap, Text text) {
        Canvas canvas = new Canvas(bitmap);
        Size locationText = getLocationText(canvas, text);
        Paint paint = getTextPaint(text);
        String[] texts = text.label.split("\n");
        for (String s : texts) {
            canvas.drawText(s, locationText.getWidth(), locationText.getHeight(), paint);
            locationText = new Size(locationText.getWidth(), (int) (locationText.getHeight() + paint.descent() - paint.ascent()));
        }
        return bitmap;
    }

    @Override
    public Bitmap runByte(Bitmap bitmap) {
        return drawTextImage(bitmap, text);
    }

    @Override
    public String runFile() {
        return null;
    }
}
