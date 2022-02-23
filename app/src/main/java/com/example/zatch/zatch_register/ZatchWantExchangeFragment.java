package com.example.zatch.zatch_register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.zatch.R;

public class ZatchWantExchangeFragment extends Fragment {

    View view;
    Spinner firstSpinner, secondSpinner, thirdSpinner;
    TextView firstWant, secondWant, thirdWant;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_zatch_register_want_exchange, container, false);

        firstSpinner = view.findViewById(R.id.firstWantSpinner);
        secondSpinner = view.findViewById(R.id.secondWantSpinner);
        thirdSpinner = view.findViewById(R.id.thirdWantSpinner);

        ArrayAdapter<CharSequence> spinnerAdapter
                = ArrayAdapter.createFromResource(getContext(), R.array.gatch_category, R.layout.item_spinner_category);

        firstSpinner.setAdapter(spinnerAdapter);
        firstSpinner.setOnItemSelectedListener(SpinnerListener);
        secondSpinner.setAdapter(spinnerAdapter);
        secondSpinner.setOnItemSelectedListener(SpinnerListener);
        thirdSpinner.setAdapter(spinnerAdapter);
        thirdSpinner.setOnItemSelectedListener(SpinnerListener);

        firstWant = view.findViewById(R.id.firstWantInput);
        firstWant.setVisibility(View.INVISIBLE);

        secondWant = view.findViewById(R.id.secondWantInput);
        thirdWant = view.findViewById(R.id.thirdWantInput);

        view.findViewById(R.id.moveRegisterStep3Button).setOnClickListener(onClickListener);

        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.moveRegisterStep3Button:
                    moveNextViewPagerFragment();
                    break;
            }
        }
    };

    AdapterView.OnItemSelectedListener SpinnerListener = new AdapterView.OnItemSelectedListener(){

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(parent == firstSpinner){
                if(position == 0)
                    firstWant.setVisibility(View.INVISIBLE);
                else
                    firstWant.setVisibility(View.VISIBLE);

            }

            else if(parent == secondSpinner){
                if(position == 0)
                    secondWant.setVisibility(View.GONE);
                else
                    secondWant.setVisibility(View.VISIBLE);
            }else if(parent == thirdSpinner){
                if(position == 0)
                    thirdWant.setVisibility(View.GONE);
                else
                    thirdWant.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    void moveNextViewPagerFragment(){
        ((ZatchRegisterActivity) getActivity()).moveNextFragment();
    }


}

