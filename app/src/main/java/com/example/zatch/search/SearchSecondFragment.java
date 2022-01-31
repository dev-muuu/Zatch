package com.example.zatch.search;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.zatch.R;
import com.google.android.flexbox.FlexboxLayout;

public class SearchSecondFragment extends Fragment {

    View view;
    EditText wantZatchField;
    CompoundButton nowSelect;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search_second, container, false);

        //나의 재치 이전 fragment에서 받아오기 & text에 넣기
        TextView myZatch = view.findViewById(R.id.myZatchTitleField);
        String zatchTitle = getArguments().getString("myZatch");

        if(zatchTitle.equals("")) {
            myZatch.setText("???");
            myZatch.setTextColor(getResources().getColor(R.color.zatch_yellow));
        }else
            myZatch.setText(zatchTitle);

        view.findViewById(R.id.moveSearchListUpButton).setOnClickListener(onClickListener);

        wantZatchField = view.findViewById(R.id.wantZatchField);

        String[] popularList = {"밀키트", "일회용 수저","물","예시"};

        FlexboxLayout popularFlex = view.findViewById(R.id.nowPopularFlexLayout);
        ContextThemeWrapper wrapper = new ContextThemeWrapper(getContext(), R.style.CustomCheckBoxZatchSearch);
        for (String eachCheckBox : popularList) {
            CheckBox each = new CheckBox(wrapper,null,0);
            each.setClickable(true);
            each.setText(eachCheckBox);
            each.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        if (nowSelect != null)
                            nowSelect.toggle();
                        wantZatchField.setText(eachCheckBox);
                        nowSelect = buttonView;
                    } else{
                        wantZatchField.setText("");
                        nowSelect = null;
                    }
                }
            });
            popularFlex.addView(each);
        }

        String[] wantList = {"알코올알러지", "바밤바 막걸리","호랑이 몰랑이","예시"};
        FlexboxLayout wantFlex = view.findViewById(R.id.wantFindZatchFlexLayout);
        for (String eachCheckBox : wantList) {
            CheckBox each = new CheckBox(wrapper,null,0);
            each.setClickable(true);
            each.setText(eachCheckBox);
            each.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        if (nowSelect != null)
                            nowSelect.toggle();
                        wantZatchField.setText(eachCheckBox);
                        nowSelect = buttonView;
                    }
                    else {
                        wantZatchField.setText("");
                        nowSelect = null;
                    }
                }
            });
            wantFlex.addView(each);
        }

        return view;

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.moveSearchListUpButton:
                    moveNextFragment();
            }
        }
    };

    private void moveNextFragment(){
        Navigation.findNavController(view).navigate(R.id.action_searchSecondFragment_to_searchListUpFragment);
    }

}
