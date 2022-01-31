package com.example.zatch.zatch_chat;

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

import java.util.ArrayList;

public class ChatZatchFragment extends Fragment{

    private RecyclerView chatRecycler;
    private RecyclerView.LayoutManager layoutManager;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_chat_zatch, container, false);

        chatRecycler = view.findViewById(R.id.zatchChatRecycler);

        //chat 임시데이터 set
        ArrayList list = new ArrayList();
        list.add("first");
        list.add("second");
        list.add("third");

        layoutManager = new LinearLayoutManager(getContext());
        ChatRecyclerViewAdapter adapter = new ChatRecyclerViewAdapter(list);
        chatRecycler.setAdapter(adapter);
        chatRecycler.setLayoutManager(layoutManager);

        return view;
    }
}
