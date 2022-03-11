package com.example.zatch.navigation.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.R;
import com.example.zatch.databinding.FragmentMainBinding;
import com.example.zatch.search.SearchActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        String[] dataset1 = new String[]{"1","2","3","4","5","6","7"};

        binding.aroundZatchRecyclerView.setAdapter(new ZatchScrollAdapter(getContext(),dataset1));
        binding.aroundZatchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        binding.popularZatchRecyclerView.setAdapter(new ZatchScrollAdapter(getContext(),dataset1));
        binding.popularZatchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        binding.gatchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        binding.gatchRecyclerView.setAdapter(new GatchScrollAdapter(dataset1, this));

        //button click listener
        binding.goSearchButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            startActivity(intent);
        });

        binding.moreGatchMoveButton.setOnClickListener(v->{
            NavHostFragment fr = (NavHostFragment) MainFragment.this.getParentFragment();
            MainTopFragment top = (MainTopFragment) fr.getParentFragment();
            top.visibleChange();
            Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_moreGatchFragment);
        });
    }

}