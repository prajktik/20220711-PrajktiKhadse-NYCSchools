package com.example.nycschools.details;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.nycschools.models.NycSchool;

/**
 * Simple ViewModel factory that provides the NycSchool object to ViewModel.
 */
public class DetailsViewModelFactory implements ViewModelProvider.Factory {

    private final NycSchool mSchool;

    public DetailsViewModelFactory(NycSchool school) {
        this.mSchool = school;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(DetailsViewModel.class)) {
            return (T) (new DetailsViewModel(mSchool));
        }
        throw new IllegalArgumentException("Unknown ViewModel class");

    }

}
