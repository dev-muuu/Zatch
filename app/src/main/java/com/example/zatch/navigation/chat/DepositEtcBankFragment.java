package com.example.zatch.navigation.chat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.zatch.R;
import com.example.zatch.databinding.BottomSheetFragmentEtcBankBinding;

import static android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH;

public class DepositEtcBankFragment extends Fragment {

    private BottomSheetFragmentEtcBankBinding binding;
    private InputMethodManager inputManger;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetFragmentEtcBankBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputManger = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        binding.etcBankFiled.setOnEditorActionListener((v, actionId, event) -> {
            switch (actionId) {
                case IME_ACTION_SEARCH:
                    Bundle bundle = new Bundle();
                    bundle.putString("bankName",binding.etcBankFiled.getText().toString());
                    Navigation.findNavController(getView()).navigate(R.id.action_depositEtcBankFragment_to_depositInputInfoBottomSheet,bundle);
                    return true;
            }
            return false;
        });

        binding.backButton.setOnClickListener(v->
                Navigation.findNavController(getView()).navigate(R.id.action_depositEtcBankFragment_to_depositBankCategoryFragment));
        
    }
}
