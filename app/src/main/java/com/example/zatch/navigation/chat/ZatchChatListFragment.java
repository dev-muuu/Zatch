package com.example.zatch.navigation.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.R;

public class ZatchChatListFragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.fragment_zatch_chat_list,container,false);

        //임시 data
        String[] data = new String[10];

        RecyclerView recyclerView = view.findViewById(R.id.zatchChatListRecycler);
        ZatchChatListAdapter adapter = new ZatchChatListAdapter(data,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(new ChatItemDecoration(adapter, getContext()));
        helper.attachToRecyclerView(recyclerView);

        return view;
    }

    void navigateGatchChat(){
        Navigation.findNavController(view).navigate(R.id.action_zatchChatListFragment_to_gatchChatListFragment);
    }
}
