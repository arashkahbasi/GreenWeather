package com.arash.home.greenweather.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class WeatherTextView extends AppCompatTextView {
    public WeatherTextView(Context context) {
        super(context);
    }

    public WeatherTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        String fontName = "vazir.ttf";
        Typeface vazir = Typeface.createFromAsset(context.getAssets(), fontName);
        this.setTypeface(vazir);
    }
}
