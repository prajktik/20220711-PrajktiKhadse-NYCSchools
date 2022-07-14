package com.example.nycschools.overview;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.VolleyError;
import com.example.nycschools._settings.AppSettings;
import com.example.nycschools.models.NycSchool;
import com.example.nycschools.network.ApiResultCallback;
import com.example.nycschools.network.SchoolApiService;
import com.example.nycschools.utils.JsonUtils;
import com.example.nycschools.utils.Logger;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * The ViewModel that is attached to the OverviewFragment.
 */
public class OverviewViewModel extends ViewModel {


    private static final String TAG = OverviewViewModel.class.getSimpleName();

    //LiveData - list of schools
    public MutableLiveData<ArrayList<NycSchool>> schoolList = new MutableLiveData<>();

    //LiveData - NycSchool to handle navigation to selected school
    public MutableLiveData<NycSchool> navigateToSelectedSchool = new MutableLiveData<>();

    // The MutableLiveData that stores the status of the most recent request
    public MutableLiveData<SchoolApiService.SchoolApiStatus> status = new MutableLiveData<>(SchoolApiService.SchoolApiStatus.DONE);

    /**
    * Fetch school data immediately on app launch
    * */
    public OverviewViewModel() {
        getSchoolsData(AppSettings.getSchoolsUrl());
    }

    /**
     * Get NYC High Schools data using Volley networking library,
     * on success response, immediately fetch SAT data
     * Update schoolList and status
     */
    private void getSchoolsData(String url) {

        if (url == null || url.isEmpty()) {
            Logger.e(TAG, "getSchoolsData url can not be empty.");
            return;
        }
        SchoolApiService service = SchoolApiService.getInstance();
        if (service != null) { status.postValue(SchoolApiService.SchoolApiStatus.LOADING);
           service.requestData(url, new ResultCallback(url));
        }
    }

    class ResultCallback implements ApiResultCallback {

        private final String mRequestUrl;

        public ResultCallback(String requestUrl) {
            this.mRequestUrl = requestUrl;
        }

        @Override
        public void onResult(JSONArray jsonArray) {

            Logger.d(TAG, "onResult : Success for "+ mRequestUrl);

            ArrayList<NycSchool> resultList = new ArrayList<>();
            if (jsonArray != null && jsonArray.length() != 0) {
                if (AppSettings.getSchoolsUrl().equals(mRequestUrl)) {
                     //received response for School Data
                    resultList = JsonUtils.parseSchoolData(jsonArray);
                    schoolList.postValue(resultList);

                    //Request SAT data
                    //TODO Using retrofit and room, we can request school and SAT
                    // data back-to-back without having to wait for a response from
                    // former one
                    getSchoolsData(AppSettings.getSatDataUrl());
                } else if (AppSettings.getSatDataUrl().equals(mRequestUrl)) {
                    //received response for SAT Data
                    resultList = JsonUtils.parseSATData(jsonArray, schoolList.getValue());
                    status.postValue(SchoolApiService.SchoolApiStatus.DONE);
                    schoolList.postValue(resultList);
                }
            }

        }

        @Override
        public void onError(VolleyError error) {
            Logger.d(TAG, "onError  :  " + error.networkResponse.statusCode);
            status.postValue(SchoolApiService.SchoolApiStatus.ERROR);
            schoolList.postValue(null);
        }
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedSchool
     * is set to null
     */
    public void displaySchoolDetailsComplete() {
        navigateToSelectedSchool.setValue(null);
    }

    /**
     * When the school is clicked, set the navigateToSelectedSchool liveData to
     * the NycSchool that was clicked on.
     */
    public void displaySchoolDetails(NycSchool school) {
        navigateToSelectedSchool.setValue(school);
    }
}
