package com.example.zatch.navigation.my_zatch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.R;

import java.util.Arrays;
import java.util.List;


public class gatchTab extends Fragment {

    private mg_adapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gatch_tab, container, false);

        Context context = view.getContext();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new mg_adapter();


        recyclerView.setAdapter(adapter);
//        init();
        getData();

        adapter.setOnItemClickListener(new mg_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(getContext(),my_gatch_detail.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void getData(){
        List<String> list_gatch = Arrays.asList("넷플릭스","왓챠","라면 1박스","넷플릭스","왓챠","라면 1박스");
        List<Integer> list_price = Arrays.asList(9900,12000,3000,9900,12000,3000);
        List<String> list_current = Arrays.asList("1","2","3","1","2","3");
        List<String> list_target = Arrays.asList("4","4","7","1","2","3");

        for (int i=0; i<list_gatch.size();i++){
            Data data = new Data();
            data.setGatch_item(list_gatch.get(i));
            data.setPrice_per_person(list_price.get(i));
            data.setCurrent(list_current.get(i));
            data.setTarget(list_target.get(i));

            adapter.addItem(data);
        }

        adapter.notifyDataSetChanged();
    }


}