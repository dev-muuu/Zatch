package com.example.zatch.bottomsheet;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import com.example.zatch.R;
import com.example.zatch.ReturnPx;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.sql.SQLOutput;


public class GatchDetailBottomSheet extends BottomSheetDialogFragment{

    View view;
    float density;
    //임시 data
    String data;
    TextView detailExplain;
    BottomSheetDialog dialog;
    BottomSheetBehavior behavior;

    //oncreatedialog oncreateview 차이..?

    public GatchDetailBottomSheet(String data){
        this.data = data;
    }

    // oncreatedialog 이후 oncreateview 호출
    //        behavior.setPeekHeight((int) (546 * density + 0.5f));

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        view = View.inflate(getContext(),R.layout.bottom_sheet_gatch_detail,null);
        dialog.setContentView(view);
        behavior = BottomSheetBehavior.from((View)view.getParent());

        detailExplain = view.findViewById(R.id.gatchDetailMultilineText);

        ConstraintLayout entire = view.findViewById(R.id.detailEntire);
        ConstraintLayout.LayoutParams entireParam = (ConstraintLayout.LayoutParams) entire.getLayoutParams();

        int etcHeight = (int) new ReturnPx(88,getActivity()).returnPx();

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size); // or getSize(size)
        int dispalyHeight = size.y;


        NestedScrollView scrollView = view.findViewById(R.id.detailNestedScrollView);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) scrollView.getLayoutParams();

        behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        int finalDispalyHeight = dispalyHeight - etcHeight;

        Button exitButton = view.findViewById(R.id.gatchDetailExitButton);
        exitButton.setOnClickListener(v->{
            dialog.dismiss();
        });

        behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                params.height = finalDispalyHeight - entire.getHeight();
                scrollView.setLayoutParams(params);
                if(newState == BottomSheetBehavior.STATE_EXPANDED) {
                    scrollView.setVisibility(View.VISIBLE);
                    exitButton.setVisibility(View.VISIBLE);
                }else
                    exitButton.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if(slideOffset > 0)
                    scrollView.setVisibility(View.VISIBLE);
                else
                    scrollView.setVisibility(View.GONE);
            }
        });
        return dialog;
    }
}
