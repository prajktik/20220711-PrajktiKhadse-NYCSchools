package com.example.nycschools;

import android.app.Application;

import com.example.nycschools._settings.AppSettings;


public class NycSchoolsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Settings.
        AppSettings.InitializeAppSettings();

    }
}
