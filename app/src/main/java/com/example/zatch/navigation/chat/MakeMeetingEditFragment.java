package com.example.zatch.navigation.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.zatch.R;
import com.example.zatch.databinding.FragmentMakeMeetingEditBinding;
import com.example.zatch.navigation.chat.data.MeetingData;

public class MakeMeetingEditFragment extends Fragment {

    private FragmentMakeMeetingEditBinding binding;
    private MeetingData meetingData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMakeMeetingEditBinding.inflate(inflater,container,false);

        //임시 data set
        meetingData = new MeetingData("4","22","13","56","성신여대 입구역",true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.button9.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putSerializable("meetingData",meetingData);
            Navigation.findNavController(getView()).navigate(R.id.action_makeMeetingEditFragment_to_makeMeetingFragment,bundle);
        });

        binding.makeMeetingMonth.setText(meetingData.getMonth());
        binding.makeMeetingDate.setText(meetingData.getDate());
        binding.makeMeetingHourText.setText(meetingData.getHour());
        binding.makeMeetingMinuteText.setText(meetingData.getMinute());
        binding.meetingPlace.setText(meetingData.getPlace());
        binding.alarmSwitch.setChecked(meetingData.isGiveAlarm());
        binding.alarmSwitch.setEnabled(false);
    }
}
