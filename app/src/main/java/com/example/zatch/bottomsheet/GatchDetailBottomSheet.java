package com.example.zatch.bottomsheet;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.zatch.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.Nullable;

import java.sql.SQLOutput;


public class GatchDetailBottomSheet extends BottomSheetDialogFragment{

    View view;
    float density;
    //임시 data
    String data;
    TextView detailExplain;

    //oncreatedialog oncreateview 차이..?

    public GatchDetailBottomSheet(String data){
        this.data = data;
    }

    // oncreatedialog 이후 oncreateview 호출
    //        behavior.setPeekHeight((int) (546 * density + 0.5f));

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(),R.layout.bottom_sheet_gatch_detail,null);
        dialog.setContentView(view);
        BottomSheetBehavior behavior = BottomSheetBehavior.from((View)view.getParent());

        detailExplain = view.findViewById(R.id.gatchDetailMultilineText);

        //density for dp
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        density = displayMetrics.density;
//        (int) (84 * density + 0.5f)
        behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == behavior.STATE_EXPANDED)
                    detailExplain.setVisibility(View.VISIBLE);
                else
                    detailExplain.setVisibility(View.GONE);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        return dialog;
    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//
////        Log.e("1","1");
////        view = inflater.inflate(R.layout.bottom_sheet_gatch_detail,container);
//////        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View)view.getParent());
//////        bottomSheetBehavior.setPeekHeight(view.getHeight());
//////        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//////            @Override
//////            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//////
//////            }
//////
//////            @Override
//////            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//////
//////            }
//////        });
////
////        return view;
////    }
//    }

}
