package com.example.zatch.bottomsheet;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.zatch.R;
import com.example.zatch.ServiceType;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CategoryBottomSheet extends BottomSheetDialogFragment {

    View view;
    String whichCategory;
    String[] categoryList;
    Context mContext;
    ServiceType type;

    CategoryBottomSheet.CategoryBottomSheetListener dialogListener;

    public interface CategoryBottomSheetListener {
        void CategoryBottomFinish(String title, String category);
    }

    public void setDialogListener(CategoryBottomSheetListener dialogListener){
        this.dialogListener = dialogListener;
    }

    public CategoryBottomSheet(ServiceType type, String which, Context mContext) {
        this.type = type;
        categoryList = type.equals(ServiceType.Zatch) ?
                mContext.getResources().getStringArray(R.array.zatch_category) : mContext.getResources().getStringArray(R.array.gatch_category);
        this.whichCategory = which;
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.bottom_sheet_category_chosse,container);

        TypedArray imageArray = getResources().obtainTypedArray(R.array.category_image);

        FlexboxLayout flexboxLayout = view.findViewById(R.id.categoryFlex);
        int index = 0;
        for (String eachCheckBox : categoryList) {
            View each = inflater.inflate(R.layout.item_search_category_select, container, false);
            TextView category = each.findViewById(R.id.categoryItemText);
            ImageView image = each.findViewById(R.id.categoryItemImage);
            each.setClickable(true);
            category.setText(eachCheckBox);
            if(index == 1 && type == ServiceType.Zatch)
                index++;
            image.setImageDrawable(imageArray.getDrawable(index));
            each.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogListener.CategoryBottomFinish(category.getText().toString(), whichCategory);
                    dismiss();
                }
            });
            flexboxLayout.addView(each);
            index++;
        }

        return view;
    }
}