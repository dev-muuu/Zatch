package com.example.zatch.navigation.my_zatch;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.zatch.R;
import com.example.zatch.ReturnPx;
import com.example.zatch.navigation.my_zatch.gatchDataItem;

import java.io.File;
import java.util.ArrayList;

public class MultiImageAdapter extends RecyclerView.Adapter<MultiImageAdapter.ViewHolder>{
    private ArrayList<gatchDataItem> mData;
    private Activity activity;

    public void clearAdapter(){
        mData.clear();
        notifyDataSetChanged();
    }

    // 생성자에서 데이터 리스트 객체, Context를 전달받음.
    MultiImageAdapter(ArrayList<gatchDataItem> imageData, Activity activity) {
        this.mData = imageData;
        this.activity = activity;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        CheckBox star;
        TextView text;
        ViewHolder(View itemView) {
            super(itemView) ;
            // 뷰 객체에 대한 참조.
            image = itemView.findViewById(R.id.uploaded_img);
            star = itemView.findViewById(R.id.icon_star);
            text = itemView.findViewById(R.id.img_item_text);

            if (star.isChecked()){
                star.setVisibility(View.VISIBLE);
                text.setVisibility(View.VISIBLE);
            } else {
                text.setVisibility(View.INVISIBLE);
            }

        }
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    // LayoutInflater - XML에 정의된 Resource(자원) 들을 View의 형태로 반환.
    @Override
    public MultiImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;    // context에서 LayoutInflater 객체를 얻는다.
        View view = inflater.inflate(R.layout.gatch_upload_img_item, parent, false) ;	// 리사이클러뷰에 들어갈 아이템뷰의 레이아웃을 inflate.
        MultiImageAdapter.ViewHolder vh = new MultiImageAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.star.setChecked(mData.get(position).isSelected());
        holder.star.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                final gatchDataItem item = mData.get(position);
                item.setCheckbox(b);
            }
        });

        MultiTransformation option = new MultiTransformation(new CenterCrop(), new RoundedCorners((int)new ReturnPx(4, activity).returnPx()));
        Glide.with(activity)
                .load(mData.get(position).getImage_uri())
                .apply(RequestOptions.bitmapTransform(option))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mData.size() ;
    }

}

