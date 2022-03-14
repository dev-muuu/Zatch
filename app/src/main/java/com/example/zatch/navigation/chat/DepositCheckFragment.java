package com.example.zatch.navigation.chat;

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
import com.example.zatch.databinding.BottomSheetDepositCheckBinding;
import com.example.zatch.databinding.ItemOwnerDepositCheckBinding;

public class DepositCheckFragment extends Fragment {

    private BottomSheetDepositCheckBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetDepositCheckBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] array = new String[4];
        binding.recyclerview.setAdapter(new DepositCheckAdapter(array));
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public class DepositCheckAdapter extends RecyclerView.Adapter<DepositCheckAdapter.ViewHolder> {

        private String[] memberData;
//        private ItemOwnerDepositCheckBinding binding; //error

        public class ViewHolder extends RecyclerView.ViewHolder {

            private ItemOwnerDepositCheckBinding binding;

            public ViewHolder(View view) {
                super(view);
                binding = ItemOwnerDepositCheckBinding.bind(view);
                binding.checkDepositState.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    String state = isChecked ? "입금 후" : "입금 전";
                    binding.checkDepositState.setText(state);
               });

            }

            public void setData(String data){
            }
        }

        public DepositCheckAdapter(String[] dataSet) {
            memberData = dataSet;
        }

        @Override
        public DepositCheckAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_owner_deposit_check, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DepositCheckAdapter.ViewHolder viewHolder, final int position) {
            viewHolder.setData(memberData[position]);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return memberData.length;
        }
    }
}
