package com.example.nycschools;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nycschools.models.NycSchool;
import com.example.nycschools.network.SchoolApiService.SchoolApiStatus;
import com.example.nycschools.overview.SchoolListAdapter;
import com.example.nycschools.utils.Logger;

import java.util.ArrayList;

public class BindingAdapters {

    @BindingAdapter("listData")
    public static void bindRecyclerView(RecyclerView recyclerView, ArrayList<NycSchool> data) {

        SchoolListAdapter adapter = (SchoolListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.submitList(data);
        }
    }

    /**
     * This binding adapter displays the SchoolApiStatus of the network request in an image view.
     * When the request is loading, it displays a loading_animation.
     * If the request has an error, it displays a broken image to reflect the connection error.
     * When the request is finished, it hides the image view.
     */
    @BindingAdapter("schoolApiStatus")
    public static void bindStatus(ImageView statusImageView, SchoolApiStatus apiStatus) {
        if (apiStatus == null) {
            Logger.d("bindStatus", " status is null");

            return;
        }
        switch (apiStatus) {
            case LOADING:
            case DONE: {
                statusImageView.setVisibility(View.GONE);
            }
            break;
            case ERROR: {
                statusImageView.setVisibility(View.VISIBLE);
                statusImageView.setImageResource(R.drawable.ic_connection_error);
            }
            break;
        }
    }

    @BindingAdapter("loadingStatus")
    public static void bindLoadingStatus(ProgressBar pbLoadingIndicator, SchoolApiStatus apiStatus) {
        if (apiStatus == SchoolApiStatus.LOADING) {
            pbLoadingIndicator.setVisibility(View.VISIBLE);
        } else {
            pbLoadingIndicator.setVisibility(View.GONE);
        }

    }


}
