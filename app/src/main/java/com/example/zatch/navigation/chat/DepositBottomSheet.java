package com.example.zatch.navigation.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.zatch.R;
import com.example.zatch.databinding.BottomSheetDepositBinding;
import com.example.zatch.navigation.chat.data.GatchDepositData;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class DepositBottomSheet extends BottomSheetDialogFragment{

    private BottomSheetDepositBinding binding;
    private BottomSheetDepositListener listener;

    interface BottomSheetDepositListener{
        void finishBottomSheet(GatchDepositData data);
    }

    public DepositBottomSheet(BottomSheetDepositListener listener) {
        this.listener = listener;
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
    }

    public void finish(){
        DepositInputInfoFragment fragment = (DepositInputInfoFragment) getChildFragmentManager().findFragmentById(R.id.depositFragment)
                .getChildFragmentManager().findFragmentById(R.id.depositFragment);
        listener.finishBottomSheet(fragment.registerDepositInfo());
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
