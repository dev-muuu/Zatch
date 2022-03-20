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
import com.example.zatch.databinding.BottomSheetMakeMeetingBinding;
import com.example.zatch.navigation.chat.data.MeetingData;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MakeMeetingBottomSheet extends BottomSheetDialogFragment{

    private MakeMeetingBottomSheetListener listener;
    private BottomSheetMakeMeetingBinding binding;
    private boolean isMeetingMade;
    private BottomSheetBehavior behavior;
    public View view;

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

//        behavior = BottomSheetBehavior.from((View)view.getParent());
//        System.out.println(behaviourView.getY());
//
//        behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
////                System.out.println(bottomSheet.getHeight());
//                if(newState == BottomSheetBehavior.STATE_DRAGGING){
//                    Log.e("drag","drag");
////                    System.out.println(behaviourView.getY());
////                    System.out.println(binding.placeCheckFinishButton.getY());
//                }else if(newState == BottomSheetBehavior.STATE_EXPANDED){
//                    Log.e("expand","expand");
////                    System.out.println(bottomHeight);
////                    System.out.println(behaviourView.getY());
////                    System.out.println(binding.placeCheckFinishButton.getY());
//                }else if(newState == BottomSheetBehavior.STATE_HALF_EXPANDED){
//                    Log.e("half","half");
////                    System.out.println(bottomHeight);
////                    System.out.println(behaviourView.getY());
////                    System.out.println(binding.placeCheckFinishButton.getY());
//                }

    }

    public View sendBehaviourView(){
        return (View)this.view.getParent();
    }



    public boolean getIsMeetingMade(){
        return this.isMeetingMade;
    }

    public MakeMeetingBottomSheetListener getBottomSheetListener(){
        return this.listener;
    }
}

