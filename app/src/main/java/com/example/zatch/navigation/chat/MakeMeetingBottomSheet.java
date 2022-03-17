package com.example.zatch.navigation.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

import com.example.zatch.R;
import com.example.zatch.databinding.BottomSheetMakeMeetingBinding;
import com.example.zatch.navigation.chat.data.MeetingData;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MakeMeetingBottomSheet extends BottomSheetDialogFragment{

    private MakeMeetingBottomSheetListener listener;
    private BottomSheetMakeMeetingBinding binding;
    private boolean isMeetingMade;

    interface MakeMeetingBottomSheetListener {
        void finishBottomSheet(MeetingData meetingData);
    }

    public MakeMeetingBottomSheet(MakeMeetingBottomSheetListener listener, boolean isMeetingMade) {
        this.listener = listener;
        this.isMeetingMade = isMeetingMade;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = BottomSheetMakeMeetingBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(isMeetingMade) {
            NavGraph navGraph = NavHostFragment.findNavController(getChildFragmentManager().findFragmentById(R.id.makeMeetingContainer)).getGraph();
            navGraph.setStartDestination(R.id.makeMeetingEditFragment);
            NavHostFragment.findNavController(getChildFragmentManager().findFragmentById(R.id.makeMeetingContainer)).setGraph(navGraph);
        }else{

        }
    }

    public boolean getIsMeetingMade(){
        return this.isMeetingMade;
    }

    public MakeMeetingBottomSheetListener getBottomSheetListener(){
        return this.listener;
    }
}

