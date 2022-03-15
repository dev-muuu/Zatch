package com.example.zatch.navigation.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.example.zatch.R;
import com.example.zatch.databinding.BottomSheetDepositBinding;
import com.example.zatch.navigation.chat.data.GatchDepositData;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class DepositBottomSheet extends BottomSheetDialogFragment{

    private BottomSheetDepositBinding binding;
    private BottomSheetDepositListener listener;
    private boolean isDepositInfoRegister;

    interface BottomSheetDepositListener{
        void uploadTutorial(GatchDepositData data);
    }

    public DepositBottomSheet(BottomSheetDepositListener listener, boolean register) {
        this.listener = listener;
        //if true, binding 다른걸로 되도록 수정
        this.isDepositInfoRegister = register;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetDepositBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if(!isDepositInfoRegister)
            NavHostFragment.findNavController(getChildFragmentManager().findFragmentById(R.id.depositFragment)).setGraph(R.navigation.navigation_deposit_bottom_sheet);
        else
            NavHostFragment.findNavController(getChildFragmentManager().findFragmentById(R.id.depositFragment)).setGraph(R.navigation.navigation_deposit_check);
    }

    public void registerFinish(){
        DepositInputInfoFragment fragment = (DepositInputInfoFragment) getChildFragmentManager().findFragmentById(R.id.depositFragment)
                .getChildFragmentManager().findFragmentById(R.id.depositFragment);
        listener.uploadTutorial(fragment.registerDepositInfo());
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
