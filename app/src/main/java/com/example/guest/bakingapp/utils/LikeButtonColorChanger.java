package com.example.guest.bakingapp.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;

import com.example.guest.bakingapp.R;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

/**
 * Created by l1maginaire on 5/2/18.
 */

public class LikeButtonColorChanger {
    public static void change(FloatingActionButton fab, Context context, boolean isFavorite) {
        int color = (isFavorite) ? R.color.colorAccent : R.color.lightLight;
        if (Build.VERSION.SDK_INT >= LOLLIPOP) {
            fab.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(color)));
        } else {
            fab.getBackground().setColorFilter(context.getResources().getColor(color), PorterDuff.Mode.MULTIPLY);
        }
    }
}
