package com.example.zatch.search;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

public class SearchListUpFragment extends Fragment
        implements MyZatchBottomSheet.MyZatchBottomSheetListener, WantZatchBottomSheet.WantZatchBottomSheetListener
        , CategoryBottomSheet.CategoryBottomSheetListener {

    View view;
    String[] dataset3;
    TextView my, want;
    EditText myChange, wantChange;
    ImageButton category1,category2;
    InputMethodManager inputMethodManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search_result, container, false);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //search change cancel
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
                //view change
                textViewVisibilityChange();

                return false;
            }
        });

        String[] dataset1 = new String[]{"1","2","3","4","5","6","7"};
        String[] dataset2 = new String[]{};
        dataset3 = new String[]{"타이머","안경닦이","2022 달력","호랑이 인형","마우스 패드"};
        if(dataset1.length == 0){
            view.findViewById(R.id.noSearchResultLayout).setVisibility(View.VISIBLE);
        } else{
            RecyclerView searchRecyclerview = view.findViewById(R.id.searchZatchRecyclerview);
            SearchListAdapter adapter = new SearchListAdapter(dataset1);
            searchRecyclerview.setAdapter(adapter);
            searchRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        view.findViewById(R.id.categoryCheckButton1).setOnClickListener(onClickListener);
        view.findViewById(R.id.categoryCheckButton2).setOnClickListener(onClickListener);
        view.findViewById(R.id.myRegisterField).setOnClickListener(onClickListener);
        view.findViewById(R.id.wantField).setOnClickListener(onClickListener);
        view.findViewById(R.id.myRegisterField).setOnLongClickListener(onLongClickListener);
        view.findViewById(R.id.wantField).setOnLongClickListener(onLongClickListener);

        my = view.findViewById(R.id.myRegisterField);
        want = view.findViewById(R.id.wantField);
        myChange = view.findViewById(R.id.myZatchChangeSearch);
        myChange.setOnEditorActionListener(onEditorAction);
        wantChange = view.findViewById(R.id.wantZatchChangeSearch);
        wantChange.setOnEditorActionListener(onEditorAction);
        category1 = view.findViewById(R.id.categoryCheckButton1);
        category2 = view.findViewById(R.id.categoryCheckButton2);

        //keyboard show_hide
        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        return view;

    }

    void textViewVisibilityChange(){
        myChange.setVisibility(View.GONE);
        wantChange.setVisibility(View.GONE);
        my.setVisibility(View.VISIBLE);
        want.setVisibility(View.VISIBLE);
    }

    void searchChangeText(){
        if(!myChange.getText().toString().equals(""))
            if(!myChange.getText().toString().equals(myChange.getHint().toString())){
                my.setText(myChange.getText().toString());
            }
        if(!wantChange.getText().toString().equals(""))
            if(!wantChange.getText().toString().equals(wantChange.getHint().toString())){
                want.setText(wantChange.getText().toString());
            }
    }

    TextView.OnEditorActionListener onEditorAction = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (actionId){
                case IME_ACTION_DONE:
                    inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    searchChangeText();
                    textViewVisibilityChange();
                    return true;
            }
            return false;
        }

    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.myRegisterField:
                    bringBottomSheet("my");
                    break;
                case R.id.wantField:
                    bringBottomSheet("want");
                    break;
                case R.id.categoryCheckButton1:
                    bringBottomSheet("category1");
                    break;
                case R.id.categoryCheckButton2:
                    bringBottomSheet("category2");
                    break;
            }
        }
    };

    View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            switch (v.getId()){
                case R.id.myRegisterField:
                    changeSearch("my");
                    return true;
                case R.id.wantField:
                    changeSearch("want");
                    return true;
            }
            return false;
        }
    };

    void changeSearch(String which){

        //init
        myChange.setHint("");
        wantChange.setHint("");

        myChange.setVisibility(View.VISIBLE);
        wantChange.setVisibility(View.VISIBLE);
        my.setVisibility(View.INVISIBLE);
        want.setVisibility(View.INVISIBLE);

        if(which.equals("my")) {
            wantChange.setHint(want.getText());
            myChange.requestFocus();
            inputMethodManager.showSoftInput(myChange,InputMethodManager.SHOW_FORCED);
        }else if(which.equals("want")){
            myChange.setHint(my.getText());
            wantChange.requestFocus();
            inputMethodManager.showSoftInput(wantChange,InputMethodManager.SHOW_FORCED);
        }

    }

    void bringBottomSheet(String which){

        BottomSheetDialogFragment bottomSheet;

        if(which.equals("my")) {
            MyZatchBottomSheet myZatch = new MyZatchBottomSheet(dataset3, my.getText().toString());
            myZatch.setDialogListener(this);
            bottomSheet = myZatch;
            bottomSheet.show(getParentFragmentManager(), null);
        }else if(which.equals("want")){
            WantZatchBottomSheet wantZatch = new WantZatchBottomSheet(dataset3, want.getText().toString());
            wantZatch.setDialogListener(this);
            bottomSheet = wantZatch;
            bottomSheet.show(getParentFragmentManager(), null);
        }else{
            CategoryBottomSheet category = new CategoryBottomSheet(which);
            category.setDialogListener(this);
            bottomSheet = category;
            bottomSheet.show(getParentFragmentManager(), null);
        }
    }

    //myzatch bottom sheet call back method
    @Override
    public void MyZatchBottomFinish(String title) {
        my.setText(title);
    }

    @Override
    public void WantZatchBottomFinish(String title) {
        want.setText(title);
    }

    @Override
    public void CategoryBottomFinish(String title, String category) {
        if (category.equals("category1"))
            category1.setSelected(true);
        else
            category2.setSelected(true);

    }
}