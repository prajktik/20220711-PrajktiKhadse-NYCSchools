package com.example.nycschools.utils;

import com.example.nycschools.models.NycSchool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/**
 * Utility class to parse JSON response and  update the existing SchoolList with SAT data
 * */
public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    public static ArrayList<NycSchool> parseSchoolData(JSONArray jsonArray) {

        ArrayList<NycSchool> resultList = new ArrayList<>();
        try {
            JSONObject jsonObject;
            String dbn;
            NycSchool school;
            Logger.i(TAG, "parseSchoolData total no of schools : "+jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                school = NycSchool.fromJson(jsonObject);
                dbn = school.getDbn();
                if(dbn != null){
                    resultList.add(school);
                }
            }
        } catch (JSONException exception){
            Logger.e(TAG, " Error Parsing school json data");
        }
        return resultList;
    }

    public static ArrayList<NycSchool> parseSATData(JSONArray jsonArray, ArrayList<NycSchool> schoolList) {

        try {
            JSONObject jsonObject;
            String dbn;
            NycSchool school;
            Logger.i(TAG, "parseSATData SAT data available for "+jsonArray.length() + " schools");

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.has("dbn")) {
                    dbn = jsonObject.getString("dbn");
                    school = getSchoolByDbn(schoolList, dbn);
                    if(school != null) {
                        school.updateSatData(dbn, jsonObject);
                    }
                }
            }
        } catch (JSONException exception){
            Logger.e(TAG, " Error Parsing school json data");
        }

        return schoolList;
    }

    //Get school info by DBN
    private static NycSchool getSchoolByDbn(ArrayList<NycSchool> schoolList, String dbn) {

        if (dbn != null && schoolList != null && !schoolList.isEmpty()) {
            for (NycSchool school : schoolList) {
                if (dbn.equals(school.getDbn())) {
                    return school;
                }
            }
        }
        return null;
    }

}
