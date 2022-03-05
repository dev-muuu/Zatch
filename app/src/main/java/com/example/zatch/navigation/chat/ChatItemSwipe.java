package com.example.zatch.navigation.chat;

import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


enum ButtonsState{
    GONE,
//    LEFT_VISIBLE,
    RIGHT_VISIBLE
}


public class ChatItemSwipe extends ItemTouchHelper.Callback{

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

        int drag_flags = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
        int swipe_flags = ItemTouchHelper.START|ItemTouchHelper.END;
        return  makeMovementFlags(drag_flags,swipe_flags);

    }

    //move(drag) 기능은 사용하지 않을 예정
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder){
        Log.e("clear","clear");
        //view가 원상태로 돌아올 때 호출되는 메서드?
        getDefaultUIUtil().clearView(getView(viewHolder));
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        Log.e("change","change");
        if (viewHolder != null)
            getDefaultUIUtil().onSelected(getView(viewHolder));

    }

    //swipe될 경우, 어떤 content를 보일지 만드 메서드?
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {

        //super 제거시, 뒤의 etcFrame도 제거되는 현상 발생하지 않음 => 따로따로 구분
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            if(dX < 0)
                ((ZatchChatListAdapter.ViewHolder) viewHolder).getExitButton().setVisibility(View.VISIBLE);
            else
                if(viewHolder instanceof ZatchChatListAdapter.ViewHolder)
                    ((ZatchChatListAdapter.ViewHolder) viewHolder).getExitButton().setVisibility(View.INVISIBLE);

            getDefaultUIUtil().onDraw(c, recyclerView, getView(viewHolder),dX,dY,actionState,isCurrentlyActive);
        }

    }

    private View getView(RecyclerView.ViewHolder viewHolder){
        return ((ZatchChatListAdapter.ViewHolder) viewHolder).getSwipeView();
    }






}
