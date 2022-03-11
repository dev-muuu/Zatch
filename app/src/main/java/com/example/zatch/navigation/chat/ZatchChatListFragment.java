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
import com.example.zatch.ReturnPx;
import com.example.zatch.databinding.FragmentZatchChatListBinding;

public class ZatchChatListFragment extends Fragment {

    private FragmentZatchChatListBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentZatchChatListBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //임시 data
        String[] data = new String[10];

        RecyclerView recyclerView = binding.zatchChatListRecycler;
        ZatchChatListAdapter adapter = new ZatchChatListAdapter(data,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(new SwipeHelperCallback(getActivity(),recyclerView));
        helper.attachToRecyclerView(recyclerView);
    }

    void navigateGatchChat(){
        Navigation.findNavController(getView()).navigate(R.id.action_zatchChatListFragment_to_gatchChatListFragment);
    }
}
