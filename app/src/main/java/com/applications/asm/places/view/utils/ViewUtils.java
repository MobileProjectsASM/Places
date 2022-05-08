package com.applications.asm.places.view.utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class ViewUtils {

    public static void showSnackBar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    public static void showSnackBar(View view, String message) {
        showSnackBar(view, message, Snackbar.LENGTH_LONG);
    }
}
