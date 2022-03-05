package com.example.zatch.navigation.chat;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.zatch.R;
import com.example.zatch.navigation.chat.data.ChatItemData;
import com.example.zatch.navigation.chat.data.ChatType;

import java.util.ArrayList;

public class ChattingMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ChatItemData> chatItem;
    private Context context;
    private Chat type;

    public ChattingMessageAdapter(Chat type, ArrayList<ChatItemData> dataSet, Context context) {
        this.type = type;
        this.chatItem = dataSet;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view;

        if(viewType == ChatType.LEFT_MESSAGE){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_left, viewGroup, false);
            return new LeftMessageViewHolder(view);
        }else if(viewType == ChatType.RIGHT_MESSAGE){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_right, viewGroup, false);
            return new RightMessageViewHolder(view);
        }else if(viewType == ChatType.LEFT_IMAGE){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chate_image_left, viewGroup, false);
            return new LeftImageViewHolder(view);
        }else{
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_image_right, viewGroup, false);
            return new RightImageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        if(viewHolder instanceof LeftMessageViewHolder)
            ((LeftMessageViewHolder)viewHolder).setItem(chatItem.get(position));
        else if(viewHolder instanceof RightMessageViewHolder)
            ((RightMessageViewHolder)viewHolder).setItem(chatItem.get(position));
        else if(viewHolder instanceof RightMessageViewHolder)
            ((LeftImageViewHolder)viewHolder).setItem(chatItem.get(position));
        else
            ((RightImageViewHolder)viewHolder).setItem(chatItem.get(position));
    }

    public class LeftMessageViewHolder extends RecyclerView.ViewHolder {

        private final TextView message, time;
        private final ImageView profile;

        public LeftMessageViewHolder(View view) {
            super(view);
            this.message = (TextView) view.findViewById(R.id.otherChat);
            this.time = (TextView) view.findViewById(R.id.otherChatTime);
            this.profile = (ImageView) view.findViewById(R.id.otherProfileImage);
        }

        public void setItem(ChatItemData data) {
            this.message.setText(data.getContent());
            this.time.setText(data.getTimeText());
            if(data.getUserImage() != null)
                this.profile.setImageURI(data.getUserImage());
        }
    }

    public class RightMessageViewHolder extends RecyclerView.ViewHolder {

        private final TextView message, time;

        public RightMessageViewHolder(View view) {
            super(view);
            this.message = (TextView) view.findViewById(R.id.myChat);
            this.time = (TextView) view.findViewById(R.id.myChatTime);
        }

        public void setItem(ChatItemData data) {
            if(type.equals(Chat.Gatch))
                message.setBackground(context.getDrawable(R.drawable.drawable_chat_gatch_right));
            this.message.setText(data.getContent());
            this.time.setText(data.getTimeText());
        }
    }

    public class LeftImageViewHolder extends RecyclerView.ViewHolder {

        private final TextView time;
        private final ImageView image, profile;

        public LeftImageViewHolder(View view) {
            super(view);
            this.time = (TextView) view.findViewById(R.id.imageOtherChatTime);
            this.image = (ImageView) view.findViewById(R.id.imageOtherImage);
            this.profile = (ImageView) view.findViewById(R.id.imageOtherProfileImage);
        }

        public void setItem(ChatItemData data) {
            this.time.setText(data.getTimeText());
            MultiTransformation option = new MultiTransformation(new CenterCrop(), new RoundedCorners(8));
            Glide.with(context)
                    .load(data.getContent())
                    .apply(RequestOptions.bitmapTransform(option))
                    .into(image);

            if(data.getUserImage() != null) {
                Glide.with(context)
                        .load(data.getUserImage())
                        .circleCrop()
                        .into(profile);
            }
        }
    }

    public class RightImageViewHolder extends RecyclerView.ViewHolder {

        private final TextView time;
        private final ImageView image;

        public RightImageViewHolder(View view) {
            super(view);
            this.time = (TextView) view.findViewById(R.id.imageMyChatTime);
            this.image = (ImageView) view.findViewById(R.id.imageMyChatImage);
        }

        public void setItem(ChatItemData data) {
            this.time.setText(data.getTimeText());
            MultiTransformation option = new MultiTransformation(new CenterCrop(), new RoundedCorners(8));
            Glide.with(context)
                    .load(data.getContent())
                    .apply(RequestOptions.bitmapTransform(option))
                    .into(image);

        }
    }

    void addItem(ChatItemData newChat){
        chatItem.add(newChat);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return chatItem.size();
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        return chatItem.get(position).getViewType();
    }
}