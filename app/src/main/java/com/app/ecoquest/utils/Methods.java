package com.app.ecoquest.utils;

import android.app.Activity;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Methods {

    public static void showMessage(Activity activity, String msg) {
        Snackbar snackbar = Snackbar.make(activity, activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    public static String getCurrentTime() {
        return new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());
    }

    public static int getTotalExp(int level) {
        return (int) ((level * 100) * 1.25);
    }

}
