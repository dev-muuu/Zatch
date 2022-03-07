package com.example.zatch.navigation.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.zatch.R;
import com.example.zatch.ServiceType;

public class ChatFragment extends Fragment{

    private View view,textLine;
    private CheckBox zatchTab, gatchTab;
    private ConstraintLayout.LayoutParams params;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_chat, container, false);

        zatchTab = view.findViewById(R.id.zatchChatTab);
        gatchTab = view.findViewById(R.id.gatchChatTab);
        textLine = view.findViewById(R.id.textBottomLine);
        params = (ConstraintLayout.LayoutParams) textLine.getLayoutParams();

        zatchTab.setOnClickListener(onClickListener);
        gatchTab.setOnClickListener(onClickListener);

        //초기화
        zatchTab.setChecked(true);
        gatchTab.setChecked(false);

        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.zatchChatTab:
                    navigateChatList(ServiceType.Zatch);
                    break;
                case R.id.gatchChatTab:
                    navigateChatList(ServiceType.Gatch);
                    break;
            }
        }
    };

    Fragment getChildFragment(){
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.chatListViewPagerFragment);
        fragment = fragment.getChildFragmentManager().findFragmentById(R.id.chatListViewPagerFragment);
        return fragment;
    }

    void navigateChatList(ServiceType wantList){

        if(wantList.equals(ServiceType.Zatch)){
            if(gatchTab.isChecked()) {
                params.endToEnd = zatchTab.getId();
                params.startToStart = zatchTab.getId();
                textLine.setBackgroundResource(R.color.zatch_purple);
                gatchTab.toggle();
                textLine.setLayoutParams(params);
                ((GatchChatListFragment) getChildFragment()).navigateZatchChat();
            }else
                zatchTab.toggle();
        }else if(wantList.equals(ServiceType.Gatch)){
            if(zatchTab.isChecked()) {
                params.endToEnd = gatchTab.getId();
                params.startToStart = gatchTab.getId();
                textLine.setBackgroundResource(R.color.zatch_yellow);
                zatchTab.toggle();
                textLine.setLayoutParams(params);
                ((ZatchChatListFragment) getChildFragment()).navigateGatchChat();
            }else
                gatchTab.toggle();
        }
    }


}