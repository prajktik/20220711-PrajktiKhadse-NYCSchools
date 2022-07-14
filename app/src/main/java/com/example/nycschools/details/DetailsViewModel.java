package com.example.nycschools.details;

import androidx.lifecycle.ViewModel;

import com.example.nycschools.models.NycSchool;

public class DetailsViewModel extends ViewModel {

    public NycSchool mSchool;
    //TODO With more time, I would use LiveData to update
    // UI in fragment_details.xml

    public DetailsViewModel(NycSchool school) {
        this.mSchool = school;
    }


}
