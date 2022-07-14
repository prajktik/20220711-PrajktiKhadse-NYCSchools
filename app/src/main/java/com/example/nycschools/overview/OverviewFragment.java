package com.example.nycschools.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.nycschools.MainActivity;
import com.example.nycschools.databinding.FragmentOverviewBinding;
import com.example.nycschools.models.NycSchool;

/**
 * This fragment displays list of NYC High Schools.
 */
public class OverviewFragment extends Fragment {

    private  OverviewViewModel mViewModel;

    /** onCreateView
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(OverviewViewModel.class);
        FragmentOverviewBinding binding = FragmentOverviewBinding.inflate(inflater);
        //Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.setLifecycleOwner(this);

        // Giving the binding access to the OverviewViewModel
        binding.setViewModel(mViewModel);

        SchoolListAdapter adapter = new SchoolListAdapter(new SchoolListAdapter.OnItemClickListener() {
            @Override
            public void onClick(NycSchool school) {
                mViewModel.displaySchoolDetails(school);

            }
        });
        binding.rvSchoolList.setAdapter(adapter);
        binding.rvSchoolList.setLayoutManager( new LinearLayoutManager(requireContext()) );

        mViewModel.navigateToSelectedSchool.observe(getViewLifecycleOwner(), new Observer<NycSchool>() {
            @Override
            public void onChanged(NycSchool nycSchool) {

                if (nycSchool != null) {
                    // Must find the NavController from the Fragment
                    NavController navController = NavHostFragment.findNavController(OverviewFragment.this);
                    navController.navigate(OverviewFragmentDirections.actionShowDetail(nycSchool));


                    // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                    mViewModel.displaySchoolDetailsComplete();
                }
            }
        });
        //Set action bar title
        setTitle();
        return binding.getRoot();
    }

    private void setTitle() {
        MainActivity mainActivity = (MainActivity) getActivity();
        ActionBar actionBar = mainActivity.getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("NYC Schools");
        }
    }


}
