package com.example.zatch.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.zatch.R;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CategoryBottomSheet extends BottomSheetDialogFragment {

    View view;
    String whichCategory;
    String[] categoryList;

    CategoryBottomSheet.CategoryBottomSheetListener dialogListener;

    public interface CategoryBottomSheetListener {
        void CategoryBottomFinish(String title, String category);
    }

    public void setDialogListener(CategoryBottomSheetListener dialogListener){
        this.dialogListener = dialogListener;
    }

    public CategoryBottomSheet(String[] categoryList, String which) {
        this.categoryList = categoryList;
        this.whichCategory = which;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.bottom_sheet_category_chosse,container);

        FlexboxLayout flexboxLayout = view.findViewById(R.id.categoryFlex);
        for (String eachCheckBox : categoryList) {
            View each = inflater.inflate(R.layout.item_search_category_select, container, false);
            TextView category = each.findViewById(R.id.categoryItemText);
            each.setClickable(true);
            category.setText(eachCheckBox);
            each.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogListener.CategoryBottomFinish(category.getText().toString(), whichCategory);
                    dismiss();
                }
            });
            flexboxLayout.addView(each);
        }

        return view;
    }

}