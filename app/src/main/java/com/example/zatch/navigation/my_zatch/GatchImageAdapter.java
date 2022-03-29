package com.example.zatch.navigation.my_zatch;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.zatch.R;
import com.example.zatch.ReturnPx;
import com.example.zatch.databinding.ItemGatchRegisterImageWithStarBinding;

import java.util.ArrayList;

public class GatchImageAdapter extends RecyclerView.Adapter<GatchImageAdapter.ViewHolder> {

    private ArrayList<gatchDataItem> itemSet;
    public Activity activity;


    public GatchImageAdapter(ArrayList<gatchDataItem> itemSet, Activity activity) {
        this.itemSet = itemSet;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemGatchRegisterImageWithStarBinding binding;

        public ViewHolder(View view) {
            super(view);
            binding = ItemGatchRegisterImageWithStarBinding.bind(view);
        }

        public void setData(Uri image, boolean certified){
            MultiTransformation option = new MultiTransformation(new CenterCrop(), new RoundedCorners((int)new ReturnPx(4, activity).returnPx()));
            Glide.with(activity)
                    .load(image)
                    .apply(RequestOptions.bitmapTransform(option))
                    .into(this.binding.gatchImage);
            binding.imageCertifiedCheck.setChecked(certified);
            binding.imageCertifiedCheck.setClickable(false);
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
        gatchDataItem item = itemSet.get(position);
        viewHolder.setData(item.getImage_uri(),item.isSelected());
    }

    @Override
    public int getItemCount() {
        return itemSet.size();
    }
}