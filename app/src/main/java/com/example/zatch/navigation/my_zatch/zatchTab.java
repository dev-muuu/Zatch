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


public class zatchTab extends Fragment {

    private mz_adapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zatch_tab, container, false);
        Context context = view.getContext();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new mz_adapter();
        recyclerView.setAdapter(adapter);
        getData();

        adapter.setOnItemClickListener(new mg_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(getContext(),my_zatch_detail.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void getData(){
        List<String> listmyitem = Arrays.asList("라면","떡볶이","생수","휴지","라면","떡볶이","생수","휴지");
        List<String> listyours = Arrays.asList("생수","화장지","만두","포도","생수","화장지","만두","포도");
        List<Integer> listImages = Arrays.asList(R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4,R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4);
        for (int i=0; i<listmyitem.size();i++){
            Data data = new Data();
            data.setMyitem(listmyitem.get(i));
            data.setYours(listyours.get(i));
            //data.setResId(listImages.get(i));

            adapter.addItem(data);
        }

        adapter.notifyDataSetChanged();
    }

}