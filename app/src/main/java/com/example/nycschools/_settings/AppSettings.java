package com.example.nycschools._settings;

import com.example.nycschools.utils.Logger;

//Define app settings here - enable logs, define URLs, and so on

public class AppSettings {

    public enum AppMode {
        PRODUCTION,
        DEV,
        QA
    }

    private static AppMode appMode = AppMode.DEV;

    public static void InitializeAppSettings() {

        AppSettings.setProductionEnv();

        switch (AppSettings.appMode) {
            case PRODUCTION: {
                break;
            }
            case DEV:{
                isDebugEnabled = true;
                break;
            }

        }
        Logger.setDebugMode(isDebugEnabled);
    }

    private static void setProductionEnv() {
        isDebugEnabled = false;
    }

    public static String getSchoolsUrl() {
        return schoolsUrl;
    }

    public static String getSatDataUrl() {
        return satDataUrl;
    }

    private static boolean isDebugEnabled = false;
    private static final String schoolsUrl = "https://data.cityofnewyork.us/resource/s3k6-pzi2.json";
    private static final String satDataUrl = "https://data.cityofnewyork.us/resource/f9bf-2cp4.json";

}
