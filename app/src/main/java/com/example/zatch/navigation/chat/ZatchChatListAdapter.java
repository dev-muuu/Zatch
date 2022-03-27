package com.example.zatch.navigation.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.R;
import com.example.zatch.ServiceType;

public class ZatchChatListAdapter extends RecyclerView.Adapter<ZatchChatListAdapter.ViewHolder>{

    private String[] localDataSet;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ConstraintLayout swipeView, infoView;
        private final TextView exitButton;

        public ViewHolder(View view) {
            super(view);

            swipeView = (ConstraintLayout) view.findViewById(R.id.zatchChattingSwipeFrame);
            infoView = (ConstraintLayout) view.findViewById(R.id.chatItemMoreInfoLayout);
            exitButton = (TextView) view.findViewById(R.id.chattingExitButton);
        }

        public ConstraintLayout getSwipeView(){
            return this.swipeView;
        }
        public ConstraintLayout getInfoView(){
            return this.infoView;
        }
        public TextView getExitButton(){
            return this.exitButton;
        }
        public boolean getInfoViewVisibility(){
            return this.infoView.getVisibility() == View.VISIBLE ? true : false;
        }
    }

    public ZatchChatListAdapter(String[] dataSet, Context context) {
        this.localDataSet = dataSet;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_zatch_chat, viewGroup, false);

        ViewHolder holder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChattingRoomActivity.class);
                intent.putExtra("service", ServiceType.Zatch);
                context.startActivity(intent);
            }
        });


        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

}