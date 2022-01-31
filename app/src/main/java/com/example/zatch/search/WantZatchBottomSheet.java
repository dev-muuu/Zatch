package com.example.zatch.search;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.zatch.R;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class WantZatchBottomSheet extends BottomSheetDialogFragment {

    View view;
    String[] list;
    String title;
    WantZatchBottomSheet.WantZatchBottomSheetListener dialogListener;

    public interface WantZatchBottomSheetListener {
        void WantZatchBottomFinish(String title);
    }

    public void setDialogListener(WantZatchBottomSheet.WantZatchBottomSheetListener dialogListener){
        this.dialogListener = dialogListener;
    }

    public WantZatchBottomSheet(String[] list, String title) {
        this.list = list;
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.bottom_sheet_want_zatch,container);

        FlexboxLayout flexboxLayout = view.findViewById(R.id.wantZatchBottomFlex);
        ContextThemeWrapper wrapper = new ContextThemeWrapper(getContext(), R.style.CustomButtonWantZatchBottomSheet);
        for (String eachCheckBox : list) {
            Button each = new Button(wrapper,null,0);
            each.setClickable(true);
            each.setText(eachCheckBox);
            if(eachCheckBox.equals(title))
                each.setSelected(true);
            each.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogListener.WantZatchBottomFinish(each.getText().toString());
                    dismiss();
                }
            });
            flexboxLayout.addView(each);
        }

        return view;
    }

}