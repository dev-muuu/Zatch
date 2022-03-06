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

enum GatchJoinState {
    Admin, //아직 수락/거부 여부 선택 안함
    member,
    AdminAccept //admin이 수락 상태
}

public class GatchChatListFragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.fragment_gatch_chat_list,container,false);
        //임시 data
        GatchJoinState[] data = new GatchJoinState[]{GatchJoinState.Admin,GatchJoinState.member,GatchJoinState.AdminAccept};

        RecyclerView recyclerView = view.findViewById(R.id.gatchChatListRecycler);
        GatchChatListAdapter adapter = new GatchChatListAdapter(getContext(),data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

//        ItemTouchHelper helper = new ItemTouchHelper(new SwipeHelperCallback(getActivity()));
//        helper.attachToRecyclerView(recyclerView);

        return view;
    }

    void navigateZatchChat(){
        Navigation.findNavController(view).navigate(R.id.action_gatchChatListFragment_to_zatchChatListFragment);
    }
}
