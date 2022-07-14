package com.example.nycschools.network;

import android.content.Context;
import android.os.Looper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.nycschools.utils.Logger;

import org.json.JSONArray;

/**
 * This class is responsible for handling network requests using android's
 * Volley library
 * TODO : Given more time, I would prefer to use Retrofit library with
 *        Moshi converter that uses less memory and cleaner code.
 *        Instead of using ArrayList to save parsed school data, I would use
 *        Room library to save data in local database, that way user will
 *        be able to access data even when offline.
 *
 **/

public class SchoolApiService {

   public enum SchoolApiStatus {
        LOADING, ERROR, DONE
    }


    private static final String TAG = SchoolApiService.class.getSimpleName();

    private RequestQueue queue;
    private static SchoolApiService sService;

    //make this singleton. Since we do not want
    //multiple instances of the ApiService and ReuqestQueue
    private SchoolApiService(Context context) {

        if (context != null) {
            queue = Volley.newRequestQueue(context);
            queue.start();
        }
    }

    public static void init(Context context) {
        if(sService == null){
            sService = new SchoolApiService(context);
        }
    }

    public static SchoolApiService getInstance(){
        if(sService != null){
            return sService;
        }
        Logger.e(TAG, "getInstance service has not been initialised yet.");
        return null;
    }

    //Clean up service instance on app exit.
    public static void resetService(){
        sService = null;
    }

    public void requestData(String url, ApiResultCallback callback) {

        Logger.d(TAG, "requestData for url : "+url);
        if(url == null || url.isEmpty()) {
            Logger.d(TAG, "requestData invalid url!");
            return;
        }

        if(callback == null) {
            Logger.d(TAG, "requestData callback is null!");
            return;
        }

        JsonArrayRequest jsonRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new JsonArrayListener(callback), new ErrorListener(callback));

        queue.add(jsonRequest);
    }

    /**
     * Implement custom response listeners
     * */
    static class JsonArrayListener implements Response.Listener<JSONArray> {

        ApiResultCallback callback;

        JsonArrayListener(ApiResultCallback callback) {
            super();
            this.callback = callback;
        }

        @Override
        public void onResponse(JSONArray jsonArray) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                // Parser logic should be executed in background thread.
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResult( jsonArray);
                    }
                }, "SchoolApiService");
                thread.start();
            } else {
                callback.onResult(jsonArray);
            }
        }
    }

    static class ErrorListener implements Response.ErrorListener {

        ApiResultCallback callback;

        ErrorListener(ApiResultCallback callback) {
            super();
            this.callback = callback;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            callback.onError( error);
        }

    }

}

