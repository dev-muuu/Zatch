package com.example.zatch.navigation.my_zatch;

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


public class mg_adapter extends Adapter<mg_adapter.ItemViewHolder>{
    private ArrayList<Data> listData = new ArrayList<>();


    //커스텀 리스너 인터페이스 정의
    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_gatch_list,parent,false);
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
        private TextView item_name;
        private TextView current, target;
        private TextView price;

        ItemViewHolder(View itemView){
            super(itemView);

            item_name = itemView.findViewById(R.id.gatch_item);
            current = itemView.findViewById(R.id.gatch_current1);
            target = itemView.findViewById(R.id.gatch_current3);
            price = itemView.findViewById(R.id.price_per_person_2);

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
            item_name.setText(data.getGatch_item());
            current.setText(data.getCurrent());
            target.setText(data.getTarget());
            price.setText(data.getPrice_per_person()+"원");
        }
    }
}