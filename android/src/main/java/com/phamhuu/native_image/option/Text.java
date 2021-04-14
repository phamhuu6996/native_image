package com.phamhuu.native_image.option;

import android.graphics.Paint;

public class Text implements Option {
    public final String label;
    public final int size;
    public final Paint.Align textAlign;
    public final int gravity;
    public final String color;
    public final int horPadding;
    public final int verPadding;

    public Text(String label, int size, Paint.Align textAlign, int gravity, String color, int horPadding, int verPadding) {
        this.label = label;
        this.size = size;
        this.textAlign = textAlign;
        this.gravity = gravity;
        this.color = color;
        this.horPadding = horPadding;
        this.verPadding = verPadding;
    }

   static public Paint.Align setAlignText(int caseAlign) {
        switch (caseAlign) {
            case Text.CENTER:
                return  Paint.Align.CENTER;
            case Text.RIGHT:
                return  Paint.Align.RIGHT;
            default: return  Paint.Align.LEFT;
        }
    }
}
