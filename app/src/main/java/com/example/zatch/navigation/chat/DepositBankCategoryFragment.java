package com.example.zatch.navigation.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.zatch.R;
import com.example.zatch.databinding.BottomSheetFragmentSelectBankBinding;

public class DepositBankCategoryFragment extends Fragment {

    private BottomSheetFragmentSelectBankBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetFragmentSelectBankBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        String[] bankList = getResources().getStringArray(R.array.bank_list);
        for(String bank: bankList){
            View bankItem = LayoutInflater.from(getContext()).inflate(R.layout.item_bottom_sheet_bank_category,null);
            TextView bankName = bankItem.findViewById(R.id.bankNameText);
            bankName.setText(bank);
            bankItem.setOnClickListener(v->{
                //기타가 아닌 은행 선택하는 경우
                if(!bank.equals("기타")) {
                    Bundle bankData = new Bundle();
                    bankData.putString("bankName", bank);
                    Navigation.findNavController(getView()).navigate(R.id.action_depositBankCategoryFragment_to_depositInputInfoBottomSheet, bankData);
                }else{  //기타 선택한 경우, 은행 입력창 이동
                    Navigation.findNavController(getView()).navigate(R.id.action_depositBankCategoryFragment_to_depositEtcBankFragment);
                }
            });
            binding.bankCategoryFlex.addView(bankItem);
        }
    }
}
