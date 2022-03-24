package com.example.zatch.navigation.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.R;
import com.example.zatch.data.NotificationData;
import com.example.zatch.databinding.ItemNotificationBinding;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationData> dataList;
    public Context context;

    public NotificationAdapter(List<NotificationData> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemNotificationBinding binding;

        public ViewHolder(@NonNull View view) {
            super(view);
            binding = ItemNotificationBinding.bind(view);
        }

        public void setData(NotificationData data){
            binding.notificationTitle.setText(data.getTypeTitle());
            binding.notificationMessage.setText(data.getTypeMessage());
//            binding.notificationDate.setText(data.getDate());

            int color = context.getColor(R.color.black_85);
            switch (data.getTypeTitle()){
                case "재치":
                    color = context.getColor(R.color.zatch_purple);
                    break;
                case "가치":
                    color = context.getColor(R.color.zatch_deepyellow);
                    break;
            }
            binding.notificationTitle.setTextColor(color);
        }
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_notification, viewGroup, false);

        final NotificationAdapter.ViewHolder viewHolder = new NotificationAdapter.ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NotificationAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.setData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}