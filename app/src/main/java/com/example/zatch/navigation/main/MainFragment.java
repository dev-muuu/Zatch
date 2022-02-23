package com.example.zatch.navigation.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.R;
import com.example.zatch.search.SearchActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MainFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        String[] dataset1 = new String[]{"1","2","3","4","5","6","7"};

        RecyclerView aroundZatch = view.findViewById(R.id.aroundZatchRecyclerView);
        ZatchScrollAdapter adapter1 = new ZatchScrollAdapter(getContext(),dataset1);
        aroundZatch.setAdapter(adapter1);
        aroundZatch.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        RecyclerView popularZatch = view.findViewById(R.id.popularZatchRecyclerView);
        ZatchScrollAdapter adapter2 = new ZatchScrollAdapter(getContext(),dataset1);
        popularZatch.setAdapter(adapter2);
        popularZatch.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        RecyclerView aroundGatch = view.findViewById(R.id.gatchRecyclerView);
        GatchScrollAdapter adapter3 = new GatchScrollAdapter(dataset1, this);
        aroundGatch.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        aroundGatch.setAdapter(adapter3);

        view.findViewById(R.id.goSearchButton).setOnClickListener(onClickListener);
        view.findViewById(R.id.moreGatchMoveButton).setOnClickListener(onClickListener);

        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.goSearchButton:
                    Intent intent = new Intent(getContext(), SearchActivity.class);
                    startActivity(intent);
                    break;

                case R.id.moreGatchMoveButton:
                    NavHostFragment fr = (NavHostFragment) MainFragment.this.getParentFragment();
                    MainTopFragment top = (MainTopFragment) fr.getParentFragment();
                    top.visibleChange();

                    Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_moreGatchFragment);
                    break;
            }
        }
    };
}