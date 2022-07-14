package com.example.nycschools.overview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nycschools.models.NycSchool;
import com.example.nycschools.databinding.*;

public class SchoolListAdapter extends ListAdapter<NycSchool, SchoolListAdapter.SchoolViewHolder> {

    private final OnItemClickListener mListener;
    /**
     * Custom listener that handles clicks on RecyclerView items.  Passes the NycSchool
     * associated with the current item to the onClick function.
     **/
    interface OnItemClickListener{
        void onClick(NycSchool school);
    }

    protected SchoolListAdapter(OnItemClickListener l) {
        super(diffCallback);
        mListener = l;
    }

    /**
     * Create new RecyclerView item views (invoked by the layout manager)
     */
    @NonNull
    @Override
    public SchoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

         SchoolItemBinding binding = SchoolItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new SchoolViewHolder(binding);

    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    @Override
    public void onBindViewHolder(@NonNull SchoolViewHolder holder, int position) {

        NycSchool school = getItem(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(school);
            }
        });
        holder.bind(school);
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the List of NycSchools
     * has been updated.
     */
    private static final DiffUtil.ItemCallback<NycSchool> diffCallback = new DiffUtil.ItemCallback<NycSchool>() {
        @Override
        public boolean areItemsTheSame(@NonNull NycSchool oldItem, @NonNull NycSchool newItem) {
            return (oldItem == newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull NycSchool oldItem, @NonNull NycSchool newItem) {

            return (oldItem.equals(newItem));

        }
    };

    /**
     * The SchoolViewHolder constructor takes the binding variable from the associated
     * SchoolItem, which nicely gives it access to the full NycSchool information.
     */
    public class SchoolViewHolder extends RecyclerView.ViewHolder{

        private  SchoolItemBinding mBinding;

        public SchoolViewHolder(SchoolItemBinding b) {
            super(b.getRoot());
            mBinding = b;
        }

        public void bind(NycSchool school) {
            mBinding.setSchool(school);
            mBinding.tvSchoolName.setText(school.getSchoolName());
            mBinding.tvAddress.setText(school.getAddress());
            mBinding.tvPhone.setText(school.getPhoneNumber());
            mBinding.tvWebsite.setText(school.getWebsite());

            //force the data binding to execute immediately,allowing
            //the RecyclerView to make the correct view size measurements
            mBinding.executePendingBindings();
        }
    }
}
