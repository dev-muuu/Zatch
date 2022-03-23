package com.example.zatch.navigation.chat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.PNDialogMessage;
import com.example.zatch.PositiveNegativeDialog;
import com.example.zatch.R;
import com.example.zatch.ReturnPx;
import com.example.zatch.ServiceType;

import java.util.ArrayList;

//타입 종류 수정하기..........
enum GatchJoinState {
    Admin, //아직 수락/거부 여부 선택 안함
    member,
    AdminAccept, //admin이 수락 상태
}

public class GatchChatListAdapter extends RecyclerView.Adapter<GatchChatListAdapter.ViewHolder>{

    private ArrayList<GatchJoinState> localDataSet;
    public Activity context;

    public GatchChatListAdapter(Activity context, ArrayList<GatchJoinState> dataSet) {
        this.localDataSet = dataSet;
        this.context = context;
    }
    //view type통해 admin 등 어떤 item 사용할지 구분 필요
    @Override
    public GatchChatListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view;
        GatchChatListAdapter.ViewHolder holder;

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gatch_chat, viewGroup, false);
        holder = new GatchChatListAdapter.ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChattingRoomActivity.class);
                intent.putExtra("service",ServiceType.Gatch);
                context.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(GatchChatListAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.setData(localDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name, message, time;
        private GatchJoinState joinState;
        private final RelativeLayout acceptLayout;

        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.chatNameGatch);
            message = view.findViewById(R.id.chatMessageGatch);
            time = view.findViewById(R.id.chatTimeGatch);
            acceptLayout = view.findViewById(R.id.gatchAcceptLayout);

            //refuse
            view.findViewById(R.id.textView80).setOnClickListener(v -> {
                makeDialogMessage(PNDialogMessage.GatchRefuse);
            });
            //accept -> layout변경 필요
            view.findViewById(R.id.textView107).setOnClickListener(v -> {
                makeDialogMessage(PNDialogMessage.GatchAccept);
            });
        }

        private void makeDialogMessage(PNDialogMessage dialogData){

            PositiveNegativeDialog dialogClass = new PositiveNegativeDialog(context, ServiceType.Gatch,dialogData);
            AlertDialog dialog = dialogClass.createDialog();
            dialogClass.getNegative().setOnClickListener(v->{
                dialog.dismiss();
            });
            dialogClass.getPositive().setOnClickListener(v->{
                switch (dialogData){
                    case GatchAccept:
                        moveGatchRoomActivity();
                        break;
                    case GatchRefuse:
                    case Exit:
                        removeChattingRoomItem();
                        break;
                }
                dialog.dismiss();
            });
            dialog.show();

        }

        private void removeChattingRoomItem(){
            localDataSet.remove(getAdapterPosition());
            notifyDataSetChanged();
        }

        //가치 수락시, 채팅방으로 이동
        private void moveGatchRoomActivity(){
            this.joinState = GatchJoinState.AdminAccept;
            localDataSet.set(getAdapterPosition(),GatchJoinState.AdminAccept);
            changeAccessLayout();

            Intent intent = new Intent(context, ChattingRoomActivity.class);
            intent.putExtra("service",ServiceType.Gatch);
            context.startActivity(intent);
        }

        private void changeAdminLayout(){
            ConstraintLayout.LayoutParams timeParams = (ConstraintLayout.LayoutParams) time.getLayoutParams();
            timeParams.baselineToBaseline = message.getId();
            time.setLayoutParams(timeParams);

            ConstraintLayout.LayoutParams messageParams = (ConstraintLayout.LayoutParams) message.getLayoutParams();
            messageParams.endToStart = acceptLayout.getId();
            messageParams.rightMargin = (int) new ReturnPx(15,context).returnPx();
            message.setLayoutParams(messageParams);

        }

        private void changeAccessLayout(){
            
            acceptLayout.setVisibility(View.GONE);

            ConstraintLayout.LayoutParams timeParams = (ConstraintLayout.LayoutParams) time.getLayoutParams();
            timeParams.baselineToBaseline = name.getId();
            time.setLayoutParams(timeParams);

            ConstraintLayout.LayoutParams messageParams = (ConstraintLayout.LayoutParams) message.getLayoutParams();
            messageParams.endToStart = time.getId();
            messageParams.rightMargin = (int) new ReturnPx(18,context).returnPx();
            message.setLayoutParams(messageParams);

        }

        //recyclerview item 초기화 코드 작성 필요
        public void setData(GatchJoinState data){
            this.joinState = data;
            acceptLayout.setVisibility(View.INVISIBLE);
            if(data == GatchJoinState.Admin) {
                acceptLayout.setVisibility(View.VISIBLE);
                changeAdminLayout();
            }
        }


        public RelativeLayout getAcceptLayout(){
            return  this.acceptLayout;
        }

        public TextView getMessage() {
            return message;
        }

        public TextView getTime() {
            return time;
        }

    }

}
