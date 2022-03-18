package com.example.zatch.navigation.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.R;
import com.example.zatch.ServiceType;
import com.example.zatch.databinding.ItemChattingRoomMemeberListBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class ChattingMemberListAdapter extends RecyclerView.Adapter<ChattingMemberListAdapter.ViewHolder> {

    private List<Boolean> localDataSet;
    public FragmentManager manager;

    public class ViewHolder extends RecyclerView.ViewHolder implements RoomEtcBottomSheet.RoomEtcBottomSheetListener {

        private ItemChattingRoomMemeberListBinding binding;

        public ViewHolder(View view) {
            super(view);
            binding = ItemChattingRoomMemeberListBinding.bind(view);
            binding.memberDeclarationButton.setOnClickListener(v->{
                makeRoomEtcBottomSheet();
            });
        }

        public void setMemberData(boolean data){
            if(data) {
                binding.crownImage.setVisibility(View.VISIBLE);
                binding.isOwner.setVisibility(View.VISIBLE);
                binding.memberDeclarationButton.setVisibility(View.GONE);
            }else {
                binding.crownImage.setVisibility(View.INVISIBLE);
                binding.isOwner.setVisibility(View.GONE);
                binding.memberDeclarationButton.setVisibility(View.VISIBLE);
            }
        }

        private void initItem(){
        }

        private void makeRoomEtcBottomSheet(){
            RoomEtcBottomSheet bottomSheet = new RoomEtcBottomSheet(this, ServiceType.Gatch);
            BottomSheetDialogFragment dialogFragment = bottomSheet;
            dialogFragment.show(manager,null);
        }

        @Override
        public void finishBottomSheet(EtcFunc etc) {
            switch (etc){
                case Out:
                    localDataSet.remove(getAdapterPosition());
                    notifyDataSetChanged();
                    break;
            }
        }
    }

    public ChattingMemberListAdapter(List<Boolean> dataSet, FragmentManager manager) {
        localDataSet = dataSet;
        this.manager = manager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_chatting_room_memeber_list, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.setMemberData(localDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}