package com.example.zatch.navigation.chat;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.drawerlayout.widget.DrawerLayout;
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
    public ServiceType type;
    public Context context;
    private DrawerLayout drawerBinding;

    public ChattingMemberListAdapter(ServiceType type, List<Boolean> dataSet, DrawerLayout drawerBinding, FragmentManager manager, Context context) {
        this.localDataSet = dataSet;
        this.manager = manager;
        this.type = type;
        this.context = context;
        this.drawerBinding = drawerBinding;
    }

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
            //zatch일 경우, 색상 변경
            if(type == ServiceType.Zatch){
                binding.isOwner.setTextColor(context.getResources().getColor(R.color.zatch_purple));
                binding.isOwner.setBackground(context.getResources().getDrawable(R.drawable.text_background_stroke_purple_10));
                binding.imageView19.setImageResource(R.drawable.profile_purple);
            }
            //방장
            if(data) {
                binding.crownImage.setVisibility(View.VISIBLE);
                binding.isOwner.setVisibility(View.VISIBLE);
                binding.memberDeclarationButton.setVisibility(View.GONE);
            }else { //일반 멤버
                binding.crownImage.setVisibility(View.INVISIBLE);
                binding.isOwner.setVisibility(View.GONE);
                binding.memberDeclarationButton.setVisibility(View.VISIBLE);
            }
        }

        private void initItem(){
        }

        private void makeRoomEtcBottomSheet(){
            RoomEtcBottomSheet bottomSheet = new RoomEtcBottomSheet(this, type);
            BottomSheetDialogFragment dialogFragment = bottomSheet;
            dialogFragment.show(manager,null);
            drawerBinding.closeDrawer(Gravity.RIGHT);
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