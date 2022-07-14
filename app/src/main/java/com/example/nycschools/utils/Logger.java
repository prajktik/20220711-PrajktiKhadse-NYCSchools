package com.example.nycschools.utils;

import android.util.Log;
/**
 * Logger utility
 *
 * */
public class Logger {

    private static boolean debug = false;
    private static final String DEBUG_TAG = "DEBUG_NYCSCHOOLS";

    public static void setDebugMode(boolean isEnabled) {
        debug = isEnabled;
    }

    public static void i(String TAG, String message) {
        if (debug) {
            Log.i(DEBUG_TAG, TAG + ": " + message);
        }
    }

    public static void d(String TAG, String message) {

        if (debug) {
            Log.d(DEBUG_TAG, TAG + ": " + message);
        }
    }

    public static void e(String TAG, String message) {

        Log.e(DEBUG_TAG, TAG + ": " + message);

    }

    public static void e(String TAG, String message, Exception ex) {

        Log.e(DEBUG_TAG, TAG + ": " + message, ex);
    }

    public static void w(String TAG, String message) {

        Log.w(DEBUG_TAG, TAG + ": " + message);
    }

    public static void v(String TAG, String message) {

        if (debug) {
            Log.v(DEBUG_TAG, TAG + ": " + message);
        }
    }

    public static void logMethodName(String message) {

        if (debug) {
            StackTraceElement callersStackTraceElement = Thread.currentThread().getStackTrace()[3];
            String methodName = callersStackTraceElement.getMethodName() + "()";

            Log.d(DEBUG_TAG, message + ":: "+methodName);
        }
    }


}
