package com.example.zatch.navigation.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.R;

public class GatchChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private GatchJoinState[] localDataSet;
    private Context context;

    public GatchChatListAdapter(Context context, GatchJoinState[] dataSet) {
        this.localDataSet = dataSet;
        this.context = context;
    }
    //view type통해 admin 등 어떤 item 사용할지 구분 필요
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view;
        RecyclerView.ViewHolder holder;

//        if(joinType.equals(GatchJoinState.Admin)){
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_gatch_chat_admin, viewGroup, false);
            holder = new AdminViewHolder(view);
//        } else{
//            view = LayoutInflater.from(viewGroup.getContext())
//                    .inflate(R.layout.item_gatch_chat, viewGroup, false);
//            holder = new MemberViewHolder(view);
//        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GatchChattingRoomActivity.class);
                context.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

//        viewHolder.getTextView().setText(localDataSet[position]);
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public static class AdminViewHolder extends RecyclerView.ViewHolder {
        private final TextView message;
        private final TextView time;

        public AdminViewHolder(View view) {
            super(view);
            message = view.findViewById(R.id.chatMessageGatchAdmin);
            time = view.findViewById(R.id.chatTimeGatchAdmin);
        }

        public TextView getMessage() {
            return message;
        }

        public TextView getTime() {
            return time;
        }

    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {
        private final TextView message;
        private final TextView time;

        public MemberViewHolder(View view) {
            super(view);
            message = view.findViewById(R.id.chatMessageGatchAdmin);
            time = view.findViewById(R.id.chatTimeGatchAdmin);
        }

        public TextView getMessage() {
            return message;
        }

        public TextView getTime() {
            return time;
        }

    }
}
