package com.example.zatch.bottomsheet;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.zatch.ReturnPx;
import com.example.zatch.databinding.BottomSheetGatchDetailBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class GatchDetailBottomSheet extends BottomSheetDialogFragment{

    String data;    //임시 data
    private BottomSheetDialog dialog;
    private BottomSheetBehavior behavior;
    private BottomSheetGatchDetailBinding binding;

    //oncreatedialog oncreateview 차이..?

    public GatchDetailBottomSheet(String data){
        this.data = data;
    }

    // oncreatedialog 이후 oncreateview 호출
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetGatchDetailBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        behavior = BottomSheetBehavior.from((View)view.getParent());

        int etcHeight = (int) new ReturnPx(88,getActivity()).returnPx();
        int dispalyHeight = new ReturnPx(getActivity()).returnDisplayHeight();
        int finalDispalyHeight = dispalyHeight - etcHeight;

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) binding.gatchDetailMultilineText.getLayoutParams();

        binding.gatchDetailExitButton.setOnClickListener(v->{
            dialog.dismiss();
        });

        params.height = finalDispalyHeight - binding.detailEntire.getHeight();
        binding.gatchDetailMultilineText.setLayoutParams(params);

        behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding.gatchDetailMultilineText.setVisibility(View.VISIBLE);
                    binding.gatchDetailExitButton.setVisibility(View.VISIBLE);
                }else
                    binding.gatchDetailExitButton.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if(slideOffset > 0)
                    binding.gatchDetailMultilineText.setVisibility(View.VISIBLE);
                else
                    binding.gatchDetailMultilineText.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
