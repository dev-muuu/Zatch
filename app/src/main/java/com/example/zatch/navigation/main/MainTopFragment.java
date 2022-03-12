package com.example.zatch.navigation.main;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.zatch.R;
import com.example.zatch.ReturnPx;
import com.example.zatch.bottomsheet.MyTownBottomSheet;
import com.example.zatch.databinding.FragmentMainTopBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

public class MainTopFragment extends Fragment implements MyTownBottomSheet.MyTownBottomSheetListener {

    private String[] dataset2;
    private ConstraintLayout.LayoutParams params;
    private boolean isParamOriginal = true;
    private InputMethodManager inputMethodManager;
    private int margin_8, margin_16;
    private FragmentMainTopBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainTopBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                forceFinishSearch();
                return true;
            }
        });

        dataset2 = new String[]{"상현동","성복동","인계동"};
        binding.myTownSelectText.setText(dataset2[0]);

        binding.myTownSelectText.setOnClickListener(v->{
            showTownBottomSheet();
        });

        binding.moveMainFragButton.setOnClickListener(v->{
            forceFinishSearch();
            visibleChange();
        });

        binding.searchButton.setOnClickListener(v->{
            isGatchFragmentCalled();
        });

        binding.searchGatchFieldText.setOnEditorActionListener((v, actionId, event) -> {
            switch (actionId){
                case IME_ACTION_DONE:
                    binding.searchGatchFieldText.setCursorVisible(false);
                    isSearchFieldContainContent();
                    return true;
            }
            return false;
        });

        params = (ConstraintLayout.LayoutParams) binding.searchGatchFieldText.getLayoutParams();

        //keyboard control
        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        //dp to px setting
        margin_8 = (int) new ReturnPx(8,getActivity()).returnPx();
        margin_16 = (int) new ReturnPx(16,getActivity()).returnPx();

    }

    void newParamSetting(){
        if(isParamOriginal) {
            isParamOriginal = false;
            params.width = 0;
            params.rightToLeft = binding.bellButton.getId();
            params.leftToRight = binding.moveMainFragButton.getId();
            params.leftMargin = margin_8;
            params.rightMargin = margin_8;
        }else{
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.leftMargin = margin_16;
            params.rightMargin = margin_16;
        }
        binding.searchGatchFieldText.setLayoutParams(params);
    }

    void isGatchFragmentCalled(){
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.mainContentFragment);
        fragment = fragment.getChildFragmentManager().findFragmentById(R.id.mainContentFragment);
        if(fragment instanceof MoreGatchFragment)
            searchFieldOpen();
        else
            return;
    }

    void searchFieldOpen(){
        binding.searchGatchFieldText.setText("");
        binding.searchGatchFieldText.setCursorVisible(true);
        binding.searchGatchFieldText.setVisibility(View.VISIBLE);
        if(!isParamOriginal)
            newParamSetting();
        binding.bellButton.setVisibility(View.GONE);
        binding.searchButton.setVisibility(View.INVISIBLE);
        inputMethodManager.showSoftInput( binding.searchGatchFieldText,InputMethodManager.SHOW_FORCED);
    }

    void isSearchFieldContainContent(){
        if(binding.searchGatchFieldText.getText().toString().equals(""))
            forceFinishSearch();
        else
            searchInputFinish();

    }

    //search field 강제 종료
    void forceFinishSearch(){
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(),0);
        binding.searchGatchFieldText.setVisibility(View.GONE);
        binding.bellButton.setVisibility(View.VISIBLE);
        binding.searchButton.setVisibility(View.VISIBLE);
        binding.textView43.setTextColor(getResources().getColor(R.color.black_85));
    }

    //search field 입력 end
    void searchInputFinish(){
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(),0);
        if(!isParamOriginal)
            isParamOriginal = true;
        binding.bellButton.setVisibility(View.VISIBLE);
        binding.textView43.setTextColor(getResources().getColor(R.color.white));
        newParamSetting();
    }

    @Override
    public void MyTownBottomFinish(String townName) {
        binding.myTownSelectText.setText(townName);
    }

    void showTownBottomSheet(){
        MyTownBottomSheet bottomSheet = new MyTownBottomSheet(dataset2,binding.myTownSelectText.getText().toString());
        bottomSheet.setDialogListener(this);
        BottomSheetDialogFragment dialogFragment = bottomSheet;
        dialogFragment.show(getParentFragmentManager(),null);
    }

    public void visibleChange(){
        if(binding.backButtonGroup.getVisibility() == View.GONE){
            binding.backButtonGroup.setVisibility(View.VISIBLE);
            binding.myTownSelectText.setVisibility(View.GONE);
        }else{
            Navigation.findNavController(getView().findViewById(R.id.mainContentFragment)).popBackStack();
            binding.backButtonGroup.setVisibility(View.GONE);
            binding.myTownSelectText.setVisibility(View.VISIBLE);
        }
    }
}