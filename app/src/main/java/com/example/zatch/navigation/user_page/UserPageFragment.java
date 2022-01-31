package com.example.zatch.navigation.user_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.zatch.R;

public class UserPageFragment extends Fragment {

    private UserPageViewModel userPageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userPageViewModel =
                new ViewModelProvider(this).get(UserPageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user_page, container, false);
        final TextView textView = root.findViewById(R.id.text_userpage);
        userPageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
