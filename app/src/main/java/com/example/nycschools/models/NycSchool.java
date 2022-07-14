package com.example.nycschools.models;

import com.example.nycschools.utils.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Objects;

/**
 * Data class that stores and parses school information from JSON response
 **/
public class NycSchool implements Serializable {

    private static final String TAG = NycSchool.class.getSimpleName();

    private String dbn;
    private String schoolName;
    private String overviewParagraph;
    private String phoneNumber;
    private String emailAddress;
    private String website;
    private String address;
    //Scores are not available for some schools.
    private String scoreReading = "Not available";
    private String scoreWriting = "Not available";
    private String scoreMath = "Not available";

    /**
     * Create new NycSchool object with school data
     */
    public static NycSchool fromJson(JSONObject jsonObject) {

        NycSchool school = new NycSchool();

        try {
            if (jsonObject.has("dbn")) {
                school.dbn = jsonObject.getString("dbn");
            }

            if (jsonObject.has("school_name")) {
                school.schoolName = jsonObject.getString("school_name");
            }

            if (jsonObject.has("overview_paragraph")) {
                school.overviewParagraph = jsonObject.getString("overview_paragraph");
            }
            if (jsonObject.has("phone_number")) {
                school.phoneNumber = jsonObject.getString("phone_number");
            }
            if (jsonObject.has("school_email")) {
                school.emailAddress = jsonObject.getString("school_email");
            }
            if (jsonObject.has("website")) {
                school.website = jsonObject.getString("website");
            }
            StringBuilder builder = new StringBuilder();
            if (jsonObject.has("primary_address_line_1")) {
                String addressLine1 = jsonObject.getString("primary_address_line_1");
                builder.append(addressLine1);
                builder.append(", ");
            }
            if (jsonObject.has("city")) {
                String city = jsonObject.getString("city");
                builder.append(city);
                builder.append(", ");
            }
            if (jsonObject.has("state_code")) {
                String stateCode = jsonObject.getString("state_code");
                builder.append(stateCode);
                builder.append(" ");
            }
            if (jsonObject.has("zip")) {
                String zip = jsonObject.getString("zip");
                builder.append(zip);
            }

            school.address = builder.toString();

        } catch (JSONException exception) {
            Logger.e(TAG, "fromJson error parsing school data: " + exception.getStackTrace());
        }

        return school;
    }

    /**
     * Update existing school object with SAT scores
     */
    public void updateSatData(String dbn, JSONObject jsonObject) {
        if (!this.dbn.equals(dbn)) {
            return;
        }
        try {
            if (jsonObject.has("sat_critical_reading_avg_score")) {
                scoreReading = jsonObject.getString("sat_critical_reading_avg_score");
            }
            if (jsonObject.has("sat_writing_avg_score")) {
                scoreWriting = jsonObject.getString("sat_writing_avg_score");
            }
            if (jsonObject.has("sat_math_avg_score")) {
                scoreMath = jsonObject.getString("sat_math_avg_score");
            }
        } catch (JSONException e) {
            Logger.e(TAG, "updateSatData error parsing school data: " + e.getStackTrace());
        }
    }

    public String getDbn() {
        return dbn;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getOverviewParagraph() {
        return overviewParagraph;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getWebsite() {
        return website;
    }

    public String getAddress() {
        return address;
    }

    public String getScoreReading() {
        return scoreReading;
    }

    public String getScoreWriting() {
        return scoreWriting;
    }

    public String getScoreMath() {
        return scoreMath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NycSchool nycSchool = (NycSchool) o;
        if (nycSchool.dbn == null) {
            return false;
        }
        return Objects.equals(dbn, nycSchool.dbn) && Objects.equals(schoolName, nycSchool.schoolName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dbn, schoolName);
    }

}
