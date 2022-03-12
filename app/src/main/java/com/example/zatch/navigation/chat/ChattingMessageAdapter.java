package com.example.zatch.navigation.chat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.zatch.R;
import com.example.zatch.ReturnPx;
import com.example.zatch.ServiceType;
import com.example.zatch.databinding.ItemChatImageLeftBinding;
import com.example.zatch.databinding.ItemChatImageRightBinding;
import com.example.zatch.databinding.ItemChatLeftBinding;
import com.example.zatch.databinding.ItemChatRightBinding;
import com.example.zatch.navigation.chat.data.ChatItemData;
import com.example.zatch.navigation.chat.data.ChatType;

import java.util.ArrayList;

public class ChattingMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ChatItemData> chatItem;
    private Activity activity;
    private ServiceType type;

    public ChattingMessageAdapter(ServiceType type, ArrayList<ChatItemData> dataSet, Activity activity) {
        this.type = type;
        this.chatItem = dataSet;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view;

        if(viewType == ChatType.LEFT_MESSAGE){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_left, viewGroup, false);
            return new LeftMessageViewHolder(ItemChatLeftBinding.bind(view));
        }else if(viewType == ChatType.RIGHT_MESSAGE){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_right, viewGroup, false);
            return new RightMessageViewHolder(ItemChatRightBinding.bind(view));
        }else if(viewType == ChatType.LEFT_IMAGE){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_image_left, viewGroup, false);
            return new LeftImageViewHolder(ItemChatImageLeftBinding.bind(view));
        }else{
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_image_right, viewGroup, false);
            return new RightImageViewHolder(ItemChatImageRightBinding.bind(view));
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

        private ItemChatLeftBinding binding;

        public LeftMessageViewHolder(ItemChatLeftBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setItem(ChatItemData data) {
            this.binding.otherChat.setText(data.getContent());
            this.binding.otherChatTime.setText(data.getTimeText());
            if(data.getUserImage() != null)
                this.binding.otherProfileImage.setImageURI(data.getUserImage());
        }
    }

    public class RightMessageViewHolder extends RecyclerView.ViewHolder {

        private ItemChatRightBinding binding;

        public RightMessageViewHolder(ItemChatRightBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setItem(ChatItemData data) {
            if(type.equals(ServiceType.Gatch))
                binding.myChat.setBackground(activity.getDrawable(R.drawable.drawable_chat_gatch_right));
            this.binding.myChat.setText(data.getContent());
            this.binding.myChatTime.setText(data.getTimeText());
        }
    }

    public class LeftImageViewHolder extends RecyclerView.ViewHolder {

        private ItemChatImageLeftBinding binding;

        public LeftImageViewHolder(ItemChatImageLeftBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setItem(ChatItemData data) {
            this.binding.imageOtherChatTime.setText(data.getTimeText());
            MultiTransformation option = new MultiTransformation(new CenterCrop(), new RoundedCorners((int)new ReturnPx(8,activity).returnPx()));
            Glide.with(activity)
                    .load(data.getContent())
                    .apply(RequestOptions.bitmapTransform(option))
                    .into(this.binding.imageOtherImage);

            if(data.getUserImage() != null) {
                Glide.with(activity)
                        .load(data.getUserImage())
                        .circleCrop()
                        .into(this.binding.imageOtherProfileImage);
            }
        }
    }

    public class RightImageViewHolder extends RecyclerView.ViewHolder {

        private ItemChatImageRightBinding binding;

        public RightImageViewHolder(ItemChatImageRightBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setItem(ChatItemData data) {
            this.binding.imageMyChatTime.setText(data.getTimeText());
            MultiTransformation option = new MultiTransformation(new CenterCrop(), new RoundedCorners((int)new ReturnPx(8, activity).returnPx()));
            Glide.with(activity)
                    .load(data.getContent())
                    .apply(RequestOptions.bitmapTransform(option))
                    .into(this.binding.imageMyChatImage);

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