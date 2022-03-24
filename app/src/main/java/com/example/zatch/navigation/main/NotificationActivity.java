package com.example.zatch.navigation.main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.zatch.data.NotificationData;
import com.example.zatch.data.NotificationEnum;
import com.example.zatch.databinding.ActivityNotificationBinding;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private ActivityNotificationBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<NotificationData> dataList = new ArrayList<>();
        dataList.add(new NotificationData(NotificationEnum.ZatchMatch,0));
        dataList.add(new NotificationData(NotificationEnum.GatchAllDeposit,0));
        dataList.add(new NotificationData(NotificationEnum.GatchMemberChange,0));
        dataList.add(new NotificationData(NotificationEnum.MeetingAlarm,0));
        dataList.add(new NotificationData(NotificationEnum.FinishExchange,0));
        dataList.add(new NotificationData(NotificationEnum.RequestCertification,0));
        dataList.add(new NotificationData(NotificationEnum.FinishCertification,0));

        if(dataList.size() == 0)
            binding.emptyText.setVisibility(View.VISIBLE);
        else {
            binding.notificationRecycer.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
            binding.notificationRecycer.setAdapter(new NotificationAdapter(dataList, NotificationActivity.this));
        }
    }
}
