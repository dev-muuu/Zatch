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
import com.example.zatch.databinding.FragmentFindPlaceSearchBinding;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

public class AddressSearchFragment extends Fragment {

    private FragmentFindPlaceSearchBinding binding;
    private InputMethodManager inputMethodManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFindPlaceSearchBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        binding.inputPlaceText.setOnEditorActionListener((v, actionId, event) -> {
            switch (actionId){
                case IME_ACTION_DONE:
                    binding.inputPlaceText.setCursorVisible(false);
                    isSearchFieldContainContent();
                    return true;
            }
            return false;
        });

        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    void isSearchFieldContainContent(){
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(),0);
        String searchPlace = binding.inputPlaceText.getText().toString();
        if(!searchPlace.equals("")) {
            Bundle bundle = new Bundle();
            bundle.putString("searchPlace",searchPlace);
            Navigation.findNavController(getView()).navigate(R.id.action_addressSearchFragment_to_addressResultFragment,bundle);
        }
    }
}
