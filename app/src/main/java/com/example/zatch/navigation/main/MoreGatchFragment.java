package com.example.zatch.navigation.main;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.R;
import com.example.zatch.bottomsheet.MyTownBottomSheet;
import com.example.zatch.bottomsheet.CategoryBottomSheet;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MoreGatchFragment extends Fragment implements MyTownBottomSheet.MyTownBottomSheetListener, CategoryBottomSheet.CategoryBottomSheetListener {

    View view;
    CheckBox nowFilter;
    TextView gatchTownArea, gatchSeatchCategory;
    float density;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_more_gatch,container,false);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                NavHostFragment fr = (NavHostFragment) MoreGatchFragment.this.getParentFragment();
                MainTopFragment top = (MainTopFragment) fr.getParentFragment();
                top.isSearchFieldContainContent();
                return true;
            }
        });

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event

                NavHostFragment fragment = (NavHostFragment) MoreGatchFragment.this.getParentFragment();
                MainTopFragment topFragment = (MainTopFragment) fragment.getParentFragment();
                topFragment.isSearchFieldContainContent();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        //recycler view setting
        String[] data = new String[]{"1","2","3","4"};
        RecyclerView recyclerView = view.findViewById(R.id.moreGatchRecyclerView);
        recyclerView.setAdapter(new MoreGatchAdapter(data,this));
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        //density for dp
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        density = displayMetrics.density;

        LinearLayout searchCategory = view.findViewById(R.id.gatchSearchCategoryLayout);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) searchCategory.getLayoutParams();
        //density 수정 필
        params.leftMargin = (int)(8 * density + 0.5f);

        ContextThemeWrapper wrapper = new ContextThemeWrapper(getContext(), R.style.SearchCategoryCheckBox);
        for(String category: getResources().getStringArray(R.array.gatch_search_category)){
            CheckBox each = new CheckBox(wrapper,null,0);
            //array 첫번째 데이터로 select되도록 초기설정
            if(category.equals(getResources().getStringArray(R.array.gatch_search_category)[0])) {
                nowFilter = each;
                each.setChecked(true);
            }

            each.setText(category);
            each.setClickable(true);
            each.setLayoutParams(params);
            searchCategory.addView(each,searchCategory.indexOfChild(view.findViewById(R.id.gatchSearchCategory)));
            each.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nowFilter.toggle();
                    nowFilter = (CheckBox) v;
                }
            });
        }

        //가치 동네 설정
        gatchTownArea = view.findViewById(R.id.gatchTownAreaText);
        gatchTownArea.setOnClickListener(onClickListener);

        //가치 카테고리 설정
        gatchSeatchCategory = view.findViewById(R.id.gatchSearchCategory);
        gatchSeatchCategory.setOnClickListener(onClickListener);

        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.gatchTownAreaText:
                    townBottomSheet();
                    break;
                case R.id.gatchSearchCategory:
                    categoryBottomSheet();
                    break;
            }
        }
    };

    void categoryBottomSheet(){
        //임시 data
        String data = "음식";
        CategoryBottomSheet bottomSheet = new CategoryBottomSheet(getResources().getStringArray(R.array.gatch_category),data);
        bottomSheet.setDialogListener(this);
        BottomSheetDialogFragment dialog = bottomSheet;
        dialog.show(getParentFragmentManager(),null);
    }

    void townBottomSheet(){
        String[] data = new String[]{"상현동","성복동","풍덕천동"};
        BottomSheetDialogFragment dialog;
        MyTownBottomSheet bottomSheet = new MyTownBottomSheet(data,data[0]);
        bottomSheet.setDialogListener(this);
        dialog = bottomSheet;
        dialog.show(getParentFragmentManager(),null);

    }

    @Override
    public void CategoryBottomFinish(String title, String category) {

    }

    @Override
    public void MyTownBottomFinish(String townName) {
        gatchTownArea.setText(townName);
    }
}
