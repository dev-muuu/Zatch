package com.example.zatch.navigation.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.R;
import com.example.zatch.bottomsheet.GatchDetailBottomSheet;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MoreGatchAdapter extends RecyclerView.Adapter<MoreGatchAdapter.GatchDetailViewHolder> {

    private String[] localDataSet;
    private Fragment fragment;

    public static class GatchDetailViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public GatchDetailViewHolder(@NonNull View view) {
            super(view);
//            this.textView = view.findViewById(R.id.textView62);
        }

        public TextView getTextView() {
            return textView;
        }

    }

    public MoreGatchAdapter(String[] dataSet, Fragment fragment) {
        this.localDataSet = dataSet;
        this.fragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GatchDetailViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_more_gatch, viewGroup, false);

        final GatchDetailViewHolder viewHolder = new GatchDetailViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveDetailDialog(localDataSet[viewHolder.getAdapterPosition()]);
            }
        });

        return viewHolder;
    }

    void moveDetailDialog(String data){
        BottomSheetDialogFragment dialog;
        GatchDetailBottomSheet bottomSheet = new GatchDetailBottomSheet(data);
//        bottomSheet.setDialogListener(this);
        dialog = bottomSheet;
        dialog.show(fragment.getParentFragmentManager(),null);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(GatchDetailViewHolder viewHolder, final int position) {

        String data = localDataSet[position];

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
//        viewHolder.getTextView().setText(data);

    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

}
