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
import com.example.zatch.databinding.BottomSheetFragmentDepositBinding;
import com.example.zatch.navigation.chat.data.GatchDepositData;

public class DepositInputInfoFragment extends Fragment {

    private BottomSheetFragmentDepositBinding binding;
    private String bankData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetFragmentDepositBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        try{
            bankData = getArguments().getString("bankName");
            binding.chooseBankText.setText(bankData);
        }catch (NullPointerException e){
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.chooseBankText.setOnClickListener(v ->
            Navigation.findNavController(getView()).navigate(R.id.action_depositInputInfoBottomSheet_to_depositBankCategoryFragment)
        );

        binding.depositInfoRegisterButton.setOnClickListener(v->{
            if(checkNullDataExist())
                ((DepositBottomSheet) getParentFragment().getParentFragment()).registerFinish();
        });
    }

    boolean checkNullDataExist(){
        TextView[] viewList = new TextView[]{binding.chooseBankText,binding.bankAccountText
                ,binding.bankOwnerText,binding.priceForDeposit};
        for(TextView view: viewList){
            if(view.getText().toString().equals(""))
                return false;
        }
        return true;
    }

    GatchDepositData registerDepositInfo(){
        GatchDepositData data = new GatchDepositData(
                binding.chooseBankText.getText().toString(),
                binding.bankAccountText.getText().toString(),
                binding.bankOwnerText.getText().toString(),
                binding.priceForDeposit.getText().toString(),
                binding.moreInfo.getText().toString());
        return data;
    }


}
