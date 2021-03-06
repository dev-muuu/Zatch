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
import com.example.zatch.ReturnPx;
import com.example.zatch.ServiceType;
import com.example.zatch.bottomsheet.MyTownBottomSheet;
import com.example.zatch.bottomsheet.CategoryBottomSheet;
import com.example.zatch.databinding.FragmentMoreGatchBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MoreGatchFragment extends Fragment implements MyTownBottomSheet.MyTownBottomSheetListener,
        CategoryBottomSheet.CategoryBottomSheetListener {

    private CheckBox nowFilter;
    private FragmentMoreGatchBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMoreGatchBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

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
        binding.moreGatchRecyclerView.setAdapter(new MoreGatchAdapter(data,this));
        binding.moreGatchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.gatchSearchCategoryLayout.getLayoutParams();

        params.leftMargin = (int) new ReturnPx(8,getActivity()).returnPx();

        ContextThemeWrapper wrapper = new ContextThemeWrapper(getContext(), R.style.SearchCategoryCheckBox);
        for(String category: getResources().getStringArray(R.array.gatch_search_category)){
            CheckBox each = new CheckBox(wrapper,null,0);
            //array ????????? ???????????? select????????? ????????????
            if(category.equals(getResources().getStringArray(R.array.gatch_search_category)[0])) {
                nowFilter = each;
                each.setChecked(true);
            }
            each.setText(category);
            each.setClickable(true);
            each.setLayoutParams(params);
            binding.gatchSearchCategoryLayout.addView(each,binding.gatchSearchCategoryLayout.indexOfChild(binding.gatchSearchCategory));
            each.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nowFilter.toggle();
                    nowFilter = (CheckBox) v;
                }
            });
        }

        //?????? ?????? ??????
        binding.gatchTownAreaText.setOnClickListener(v->{
            townBottomSheet();
        });

        //?????? ???????????? ??????
        binding.gatchSearchCategory.setOnClickListener(v->{
            categoryBottomSheet();
        });
    }

    void categoryBottomSheet(){
        //?????? data
        String data = "??????";
        CategoryBottomSheet bottomSheet = new CategoryBottomSheet(ServiceType.Gatch,data, getContext());
        bottomSheet.setDialogListener(this);
        BottomSheetDialogFragment dialog = bottomSheet;
        dialog.show(getParentFragmentManager(),null);
    }

    void townBottomSheet(){
        String[] data = new String[]{"?????????","?????????","????????????"};
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
        binding.gatchTownAreaText.setText(townName);
    }
}
