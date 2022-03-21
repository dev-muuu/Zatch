package com.example.zatch.navigation.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

import com.example.zatch.R;
import com.example.zatch.ServiceType;
import com.example.zatch.databinding.BottomSheetMakeMeetingBinding;
import com.example.zatch.navigation.chat.data.MeetingData;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MakeMeetingBottomSheet extends BottomSheetDialogFragment{

    private MakeMeetingBottomSheetListener listener;
    private BottomSheetMakeMeetingBinding binding;
    private boolean isMeetingMade;
    public View view;
    private ServiceType type;

    interface MakeMeetingBottomSheetListener {
        void finishBottomSheet(MeetingData meetingData);
    }

    public MakeMeetingBottomSheet(MakeMeetingBottomSheetListener listener, boolean isMeetingMade, ServiceType type) {
        this.listener = listener;
        this.isMeetingMade = isMeetingMade;
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = BottomSheetMakeMeetingBinding.inflate(inflater,container,false);
        view = binding.getRoot();
        return view;
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

    public View sendBehaviourView(){
        return (View)this.view.getParent();
    }

    public ServiceType getType(){
        return this.type;
    }

    public boolean getIsMeetingMade(){
        return this.isMeetingMade;
    }

    public MakeMeetingBottomSheetListener getBottomSheetListener(){
        return this.listener;
    }
}

