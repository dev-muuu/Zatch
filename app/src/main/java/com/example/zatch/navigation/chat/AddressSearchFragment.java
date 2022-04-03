package com.example.zatch.navigation.chat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.zatch.R;
import com.example.zatch.databinding.BottomSheetFragmentFindPlaceSearchBinding;
import com.example.zatch.location.CallMapViewEnum;
import com.example.zatch.location.MapViewActivity;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH;

public class AddressSearchFragment extends Fragment {

    private BottomSheetFragmentFindPlaceSearchBinding binding;
    private InputMethodManager inputMethodManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetFragmentFindPlaceSearchBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        binding.inputPlaceText.setOnEditorActionListener((v, actionId, event) -> {
            switch (actionId){
                case IME_ACTION_SEARCH:
                    binding.inputPlaceText.setCursorVisible(false);
                    isSearchFieldContainContent();
                    return true;
            }
            return false;
        });

        binding.setByMyPlaceButton.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), MapViewActivity.class);
            intent.putExtra("serviceType",CallMapViewEnum.MakeMeeting);

            mGetContent.launch(intent);
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

    // GetContent creates an ActivityResultLauncher<String> to allow you to pass
    // in the mime type you'd like to allow the user to select
    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // Handle the returned Uri
                    String placeResult = result.getData().getStringExtra("placeResult");
                    Navigation.findNavController(getView())
                            .getBackStackEntry(R.id.makeMeetingFragment)
                            .getSavedStateHandle()
                            .set("result",placeResult);
                    Navigation.findNavController(getView()).popBackStack();
                }
    });
}
