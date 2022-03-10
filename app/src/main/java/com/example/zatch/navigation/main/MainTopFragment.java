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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

public class MainTopFragment extends Fragment implements MyTownBottomSheet.MyTownBottomSheetListener {

    String[] dataset2;
    TextView myTownText, gatchTextView;
    Group gatchGroup;
    EditText searchField;
    Button alarmButton, searchButton;
    ConstraintLayout.LayoutParams params;
    boolean isParamOriginal = true;
    InputMethodManager inputMethodManager;
    int margin_8, margin_16;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_top, container, false);

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
        myTownText = view.findViewById(R.id.myTownSelectText);
        myTownText.setText(dataset2[0]);
        view.findViewById(R.id.myTownSelectText).setOnClickListener(onClickListener);

        gatchGroup = view.findViewById(R.id.backButtonGroup);
        view.findViewById(R.id.moveMainFragButton).setOnClickListener(onClickListener);
        view.findViewById(R.id.searchButton).setOnClickListener(onClickListener);
        searchButton = view.findViewById(R.id.searchButton);
        searchField = view.findViewById(R.id.searchGatchFieldText);
        searchField.setOnEditorActionListener(onEditorListener);
        alarmButton = view.findViewById(R.id.bellButton);
        gatchTextView = view.findViewById(R.id.textView43);

        params = (ConstraintLayout.LayoutParams) searchField.getLayoutParams();

        //keyboard control
        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        //dp to px setting
        margin_8 = (int) new ReturnPx(8,getActivity()).returnPx();
        margin_16 = (int) new ReturnPx(16,getActivity()).returnPx();

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.myTownSelectText:
                    showTownBottomSheet();
                    break;

                case R.id.moveMainFragButton:
                    forceFinishSearch();
                    visibleChange();
                    break;

                case R.id.searchButton:
                    isGatchFragmentCalled();
                    break;
            }
        }
    };

    TextView.OnEditorActionListener onEditorListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (actionId){
                case IME_ACTION_DONE:
                    searchField.setCursorVisible(false);
                    isSearchFieldContainContent();
                    return true;
            }
            return false;
        }
    };

    void newParamSetting(){
        if(isParamOriginal) {
            isParamOriginal = false;
            params.width = 0;
            params.rightToLeft = R.id.bellButton;
            params.leftToRight = R.id.moveMainFragButton;
            params.leftMargin = margin_8;
            params.rightMargin = margin_8;
        }else{
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.leftMargin = margin_16;
            params.rightMargin = margin_16;
        }
        searchField.setLayoutParams(params);
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
        searchField.setText("");
        searchField.setCursorVisible(true);
        searchField.setVisibility(View.VISIBLE);
        if(!isParamOriginal)
            newParamSetting();
        alarmButton.setVisibility(View.GONE);
        searchButton.setVisibility(View.INVISIBLE);
        inputMethodManager.showSoftInput(searchField,InputMethodManager.SHOW_FORCED);
    }

    void isSearchFieldContainContent(){
//        searchField.setCursorVisible(false);
        if(searchField.getText().toString().equals(""))
            forceFinishSearch();
        else
            searchInputFinish();

    }

    //search field 강제 종료
    void forceFinishSearch(){
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(),0);
        searchField.setVisibility(View.GONE);
        alarmButton.setVisibility(View.VISIBLE);
        searchButton.setVisibility(View.VISIBLE);
        gatchTextView.setTextColor(getResources().getColor(R.color.black_85));
    }

    //search field 입력 end
    void searchInputFinish(){
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(),0);
        if(!isParamOriginal)
            isParamOriginal = true;
        alarmButton.setVisibility(View.VISIBLE);
        gatchTextView.setTextColor(getResources().getColor(R.color.white));
        newParamSetting();
    }

    @Override
    public void MyTownBottomFinish(String townName) {
        myTownText.setText(townName);
    }

    void showTownBottomSheet(){
        MyTownBottomSheet bottomSheet = new MyTownBottomSheet(dataset2,myTownText.getText().toString());
        bottomSheet.setDialogListener(this);
        BottomSheetDialogFragment dialogFragment = bottomSheet;
        dialogFragment.show(getParentFragmentManager(),null);
    }

    public void visibleChange(){
       if(gatchGroup.getVisibility() == View.GONE){
           gatchGroup.setVisibility(View.VISIBLE);
           myTownText.setVisibility(View.GONE);
       }else{
           Navigation.findNavController(getView().findViewById(R.id.mainContentFragment)).popBackStack();
           gatchGroup.setVisibility(View.GONE);
           myTownText.setVisibility(View.VISIBLE);
       }
    }

}