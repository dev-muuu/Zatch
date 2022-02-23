package com.example.zatch.navigation.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.R;
import com.example.zatch.bottomsheet.GatchDetailBottomSheet;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class GatchScrollAdapter extends RecyclerView.Adapter<GatchScrollAdapter.ViewHolder> {

    private String[] localDataSet;
    private Fragment fragment;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            imageView = view.findViewById(R.id.gatchImage);
            textView = view.findViewById(R.id.aroundGatchNowNum);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public GatchScrollAdapter(String[] dataSet, Fragment fragment) {
        this.localDataSet = dataSet;
        this.fragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GatchScrollAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_around_gatch, viewGroup, false);

        GatchScrollAdapter.ViewHolder viewHolder = new GatchScrollAdapter.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveDetailDialog(localDataSet[viewHolder.getAdapterPosition()]);
            }
        });

        return viewHolder;
    }

    void moveDetailDialog(String data){
        GatchDetailBottomSheet bottomSheet = new GatchDetailBottomSheet(data);
        BottomSheetDialogFragment dialog = bottomSheet;
        dialog.show(fragment.getParentFragmentManager(),null);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(GatchScrollAdapter.ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(localDataSet[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}