package com.example.zatch.navigation.my_zatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.zatch.R;
import com.example.zatch.navigation.main.NotificationActivity;
import com.example.zatch.zatch_register.ZatchRegisterActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MyZatchFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_my_zatch, container, false);
//        view.findViewById(R.id.moveRegisterFloatingButton).setOnClickListener(onClickListener);

        ViewPager vp = (ViewPager)view.findViewById(R.id.viewpager);
        VPAdapter vadapter = new VPAdapter(getParentFragmentManager());
        vp.setAdapter(vadapter);

        TabLayout tab = (TabLayout) view.findViewById(R.id.tab);
        tab.setupWithViewPager(vp);

        FloatingActionButton add_btn = (FloatingActionButton) view.findViewById(R.id.float_add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),gatch_upload.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.alarm).setOnClickListener(v->{
            Intent intent = new Intent(getContext(), NotificationActivity.class);
            startActivity(intent);
        });


        return view;
    }

//    View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()){
//                case R.id.moveRegisterFloatingButton:
//                    moveRegisterActivity();
//                    break;
//            }
//        }
//    };
//
//    void moveRegisterActivity(){
//        Intent intent = new Intent(getContext(), ZatchRegisterActivity.class);
//        startActivity(intent);
//    }
}