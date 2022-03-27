package com.example.zatch.navigation.my_zatch;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zatch.R;
import com.example.zatch.databinding.ItemGatchRegisterImageWithStarBinding;

public class GatchImageAdapter extends RecyclerView.Adapter<GatchImageAdapter.ViewHolder> {

    private String[] localDataSet;
    public Activity activity;


    public GatchImageAdapter(String[] dataSet, Activity activity) {
        localDataSet = dataSet;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemGatchRegisterImageWithStarBinding binding;

        public ViewHolder(View view) {
            super(view);

            binding = ItemGatchRegisterImageWithStarBinding.bind(view);
        }

        public void setData(){
//            Glide.with(activity)
//                    .load(data.getUserImage())
//                    .into(this.binding.gatchImage);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_gatch_register_image_with_star, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}