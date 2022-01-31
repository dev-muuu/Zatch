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

public class MyZatchBottomSheet extends BottomSheetDialogFragment {

    View view;
    String[] list;
    String title;
    MyZatchBottomSheetListener dialogListener;

    public interface MyZatchBottomSheetListener {
        void MyZatchBottomFinish(String title);
    }

    public void setDialogListener(MyZatchBottomSheetListener dialogListener){
        this.dialogListener = dialogListener;
    }

    public MyZatchBottomSheet(String[] list, String title) {
        this.list = list;
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.bottom_sheet_my_register_zatch,container);

        FlexboxLayout flexboxLayout = view.findViewById(R.id.myZatchBottomFlex);
        ContextThemeWrapper wrapper = new ContextThemeWrapper(getContext(), R.style.CustomButtonMyZatchBottomSheet);
        for (String eachCheckBox : list) {
            Button each = new Button(wrapper,null,0);
            each.setClickable(true);
            each.setText(eachCheckBox);
            if(eachCheckBox.equals(title))
                each.setSelected(true);
            each.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogListener.MyZatchBottomFinish(each.getText().toString());
                    dismiss();
                }
            });
            flexboxLayout.addView(each);
        }

        return view;
    }

}