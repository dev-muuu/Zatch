package com.example.zatch.search;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.zatch.R;
import com.google.android.flexbox.FlexboxLayout;

public class SearchFirstFragment extends Fragment {

    View view;
    EditText zatchNameField;
    CompoundButton nowSelect;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search_first, container, false);

        view.findViewById(R.id.moveSecondSearchButton).setOnClickListener(onClickListener);
        view.findViewById(R.id.skipFirstStepButton).setOnClickListener(onClickListener);
        zatchNameField = view.findViewById(R.id.exchangeZatchNameField);


        String[] myZatchList = {"몰랑이 피규어", "매일우유 250ml","콜드브루 60ml","예시가 있다면","이렇게 들어값니다","짧아야 "};

        FlexboxLayout flexboxLayout = view.findViewById(R.id.myZatchFlexContainer);
        ContextThemeWrapper wrapper = new ContextThemeWrapper(getContext(), R.style.CustomCheckBoxZatchSearch);
        for (String eachCheckBox : myZatchList) {
            CheckBox each = new CheckBox(wrapper,null,0);
            each.setClickable(true);
            each.setText(eachCheckBox);
            each.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        if(nowSelect != null)
                            nowSelect.toggle();
                        zatchNameField.setText(eachCheckBox);
                        nowSelect = buttonView;
                    }
                    else {
                        zatchNameField.setText("");
                        nowSelect = null;
                    }
                }
            });
            flexboxLayout.addView(each);
        }
        
        return view;

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.moveSecondSearchButton:
                    moveNextFragment(zatchNameField.getText().toString());
                    break;
                case R.id.skipFirstStepButton:
                    moveNextFragment("");
                    break;

            }
        }
    };

    private void moveNextFragment(String name){
        Bundle bundle = new Bundle();
        bundle.putString("myZatch", name);
        Navigation.findNavController(view).navigate(R.id.action_searchFirstFragment_to_searchSecondFragment, bundle);
    }
}
