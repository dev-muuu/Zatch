package com.example.zatch.navigation.my_zatch;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.zatch.R;

import java.util.ArrayList;


public class mz_adapter extends RecyclerView.Adapter<mz_adapter.ItemViewHolder>{

    private ArrayList<Data> listData = new ArrayList<>();

    //커스텀 리스너 인터페이스 정의
    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private mg_adapter.OnItemClickListener mListener = null;

    public void setOnItemClickListener(mg_adapter.OnItemClickListener listener){
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_zatch_list,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(Data data){
        listData.add(data);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView tv1;
        private TextView tv2;
        private ImageView img;

        ItemViewHolder(View itemView){
            super(itemView);

            tv1 = itemView.findViewById(R.id.myItem);
            tv2 = itemView.findViewById(R.id.yourItem);
            img = itemView.findViewById(R.id.img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItemClick(view,pos);
                        }
                    }
                }
            });
        }

        void onBind(Data data){
            tv1.setText(data.getMyitem());
            tv2.setText(data.getYours());
            //img.setImageResource(data.getResId());

        }
    }
}