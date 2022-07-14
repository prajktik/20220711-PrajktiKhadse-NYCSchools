package com.example.nycschools.network;

import com.android.volley.VolleyError;

import org.json.JSONArray;
/**
 * Callback interface to process response from network request.
 *
 * */
public interface ApiResultCallback {

    void onResult(JSONArray jsonArray);
    void onError(VolleyError error);
}
