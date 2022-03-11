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
import com.example.zatch.databinding.FragmentGatchChatListBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class GatchChatListFragment extends Fragment {

    private FragmentGatchChatListBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentGatchChatListBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ArrayList<GatchJoinState> data = new ArrayList<>(Arrays.asList(GatchJoinState.Admin,GatchJoinState.member,GatchJoinState.AdminAccept));

//        RecyclerView recyclerView = binding.gatchChatListRecycler;
        GatchChatListAdapter adapter = new GatchChatListAdapter(getActivity(),data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.gatchChatListRecycler.setLayoutManager(layoutManager);
        binding.gatchChatListRecycler.setAdapter(adapter);

//        new ItemTouchHelper(new SwipeHelperCallback(getActivity())).attachToRecyclerView(recyclerView);
    }

    void navigateZatchChat(){
        Navigation.findNavController(getView()).navigate(R.id.action_gatchChatListFragment_to_zatchChatListFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
