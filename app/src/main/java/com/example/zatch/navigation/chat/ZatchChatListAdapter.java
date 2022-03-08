package com.example.zatch.navigation.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.R;

public class ZatchChatListAdapter extends RecyclerView.Adapter<ZatchChatListAdapter.ViewHolder>{

    private String[] localDataSet;
    private Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ConstraintLayout swipeView, infoView;
        private final TextView exitButton;

        public ViewHolder(View view) {
            super(view);

            swipeView = (ConstraintLayout) view.findViewById(R.id.zatchChattingSwipeFrame);
            infoView = (ConstraintLayout) view.findViewById(R.id.chatItemMoreInfoLayout);
            exitButton = (TextView) view.findViewById(R.id.chattingExitButton);
        }

        public ConstraintLayout getSwipeView(){
            return this.swipeView;
        }
        public ConstraintLayout getInfoView(){
            return this.infoView;
        }
        public TextView getExitButton(){
            return this.exitButton;
        }
        public boolean getInfoViewVisibility(){
            return this.infoView.getVisibility() == View.VISIBLE ? true : false;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public ZatchChatListAdapter(String[] dataSet, Context context) {
        this.localDataSet = dataSet;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_zatch_chat, viewGroup, false);

        ViewHolder holder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ZatchChattingRoomActivity.class);
                context.startActivity(intent);
            }
        });


        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
//        viewHolder.getTextView().setText(localDataSet[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

}