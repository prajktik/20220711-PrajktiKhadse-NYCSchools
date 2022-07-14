package com.example.nycschools.network;

import android.content.Context;

import com.example.nycschools.utils.Logger;

public class FeedOperation implements Runnable {

    private static final String TAG = FeedOperation.class.getSimpleName();
    private final String url;
    private final Thread thread;
    private final ApiResultCallback callback;

    public FeedOperation(String url, ApiResultCallback callback) {
        this.url = url;
        this.callback = callback;
        this.thread = new Thread(this, "FeedOperation");
    }

    public void startOperation() {
        this.thread.start();
    }

    public void run() {
        Logger.d(TAG, "run : "+url);
        SchoolApiService service = SchoolApiService.getInstance();
        if(service != null) {
            service.requestData(url, callback);
        }
    }
}
