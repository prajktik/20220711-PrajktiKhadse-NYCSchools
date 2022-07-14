package com.example.nycschools.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nycschools.MainActivity;
import com.example.nycschools.databinding.FragmentDetailsBinding;
import com.example.nycschools.models.NycSchool;

/**
 * This fragment displays all the SAT scores and some additional
 * information about the selected school.
 */
public class DetailsFragment extends Fragment {

    private DetailsViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentDetailsBinding binding = FragmentDetailsBinding.inflate(inflater);

        //Get selected NycSchool object passed from previous fragment
        NycSchool schoolData = DetailsFragmentArgs.fromBundle(getArguments()).getSelectedSchool();

        DetailsViewModelFactory viewModelFactory = new DetailsViewModelFactory(schoolData);
        mViewModel = new ViewModelProvider(this, viewModelFactory).get(DetailsViewModel.class);

        //Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.setLifecycleOwner(this);

        // Giving the binding access to the DetailsViewModel
        binding.setViewModel(mViewModel);

        //Set title for this fragment to School name
        setTitle(schoolData.getSchoolName());

        return binding.getRoot();
    }

    private void setTitle(String schoolName) {
        MainActivity mainActivity = (MainActivity) getActivity();
        ActionBar actionBar = mainActivity.getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(schoolName);
        }
    }

}
