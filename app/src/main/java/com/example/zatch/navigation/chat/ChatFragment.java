package com.example.zatch.navigation.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.zatch.R;
import com.example.zatch.ServiceType;
import com.example.zatch.databinding.FragmentChatBinding;
import com.example.zatch.navigation.main.NotificationActivity;

public class ChatFragment extends Fragment{

    private ConstraintLayout.LayoutParams params;
    private FragmentChatBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        params = (ConstraintLayout.LayoutParams) binding.textBottomLine.getLayoutParams();
        binding.zatchChatTab.setChecked(true);
        binding.gatchChatTab.setChecked(false);

        binding.zatchChatTab.setOnClickListener(v->{
            navigateChatList(ServiceType.Zatch);
        });

        binding.gatchChatTab.setOnClickListener(v->{
            navigateChatList(ServiceType.Gatch);
        });

        binding.notificationButton.setOnClickListener(v->{
            Intent intent = new Intent(getContext(),NotificationActivity.class);
            startActivity(intent);
        });
    }

    Fragment getChildFragment(){
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.chatListViewPagerFragment);
        fragment = fragment.getChildFragmentManager().findFragmentById(R.id.chatListViewPagerFragment);
        return fragment;
    }

    void navigateChatList(ServiceType wantList){

        if(wantList.equals(ServiceType.Zatch)){
            if(binding.gatchChatTab.isChecked()) {
                params.endToEnd = binding.zatchChatTab.getId();
                params.startToStart = binding.zatchChatTab.getId();
                binding.textBottomLine.setBackgroundResource(R.color.zatch_purple);
                binding.gatchChatTab.toggle();
                binding.textBottomLine.setLayoutParams(params);
                ((GatchChatListFragment) getChildFragment()).navigateZatchChat();
            }else
                binding.zatchChatTab.toggle();
        }else if(wantList.equals(ServiceType.Gatch)){
            if(binding.zatchChatTab.isChecked()) {
                params.endToEnd = binding.gatchChatTab.getId();
                params.startToStart = binding.gatchChatTab.getId();
                binding.textBottomLine.setBackgroundResource(R.color.zatch_yellow);
                binding.zatchChatTab.toggle();
                binding.textBottomLine.setLayoutParams(params);
                ((ZatchChatListFragment) getChildFragment()).navigateGatchChat();
            }else
                binding.gatchChatTab.toggle();
        }
    }


}