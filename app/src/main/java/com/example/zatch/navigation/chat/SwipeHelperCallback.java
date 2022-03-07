package com.example.zatch.navigation.chat;

import android.app.Activity;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.ReturnPx;

import java.util.Optional;

public class SwipeHelperCallback extends ItemTouchHelper.Callback{

    private final float exitWidth;
    private float currentDx = 0f;

    public SwipeHelperCallback(Activity activity) {
        exitWidth = new ReturnPx(72,activity).returnPx();
    }

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
        currentDx = 0f;
        getDefaultUIUtil().clearView(getView(viewHolder));
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null)
            getDefaultUIUtil().onSelected(getView(viewHolder));
    }

    //swipe 인식 위해 view 이동시켜야 하는 정도 지정
    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        boolean isClamp = getTag(viewHolder);
        setTag(viewHolder, !isClamp && currentDx <= -exitWidth );
        return .3f;
    }

    //일정 속도 미만, action 취소?
    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return defaultValue * 10;
    }


    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            if(dX < 0) {  //오른쪽 swipe
                ((ZatchChatListAdapter.ViewHolder) viewHolder).getExitButton().setVisibility(View.VISIBLE);
                ((ZatchChatListAdapter.ViewHolder) viewHolder).getInfoView().setVisibility(View.INVISIBLE);

                View view = ((ZatchChatListAdapter.ViewHolder) viewHolder).itemView;
                boolean isClamp = getTag(viewHolder);
                float x = calculateDxSize(view,dX,isClamp,isCurrentlyActive);
                currentDx = x;
                getDefaultUIUtil().onDraw(c, recyclerView, getView(viewHolder),x,dY,actionState,isCurrentlyActive);
            }
            else {   //왼쪽 swipe
                if (viewHolder instanceof ZatchChatListAdapter.ViewHolder) {
                    ((ZatchChatListAdapter.ViewHolder) viewHolder).getExitButton().setVisibility(View.INVISIBLE);
                    ((ZatchChatListAdapter.ViewHolder) viewHolder).getInfoView().setVisibility(View.VISIBLE);
                    getDefaultUIUtil().onDraw(c, recyclerView, getView(viewHolder),dX,dY,actionState,isCurrentlyActive);
                }
            }
        }
    }

    private float calculateDxSize(View view, float dX, boolean isClamp, boolean isCurrnetlyActive){

        float min = (float)-view.getWidth()/2;
        float x;
        if(isClamp){
            if(isCurrnetlyActive)
                x = dX - exitWidth;
            else
                x = -exitWidth;
        }else
            x = dX;

        return Math.min(Math.max(min, x), 0f);
    }

    private void setTag(RecyclerView.ViewHolder viewHolder, boolean isClamped){
        viewHolder.itemView.setTag(isClamped);
    }

    private boolean getTag(RecyclerView.ViewHolder viewHolder){
        try {
            boolean isClamp = (boolean)viewHolder.itemView.getTag();
            return isClamp;
        }catch (NullPointerException e){
            return false;
        }
    }

    private View getView(RecyclerView.ViewHolder viewHolder){
        return ((ZatchChatListAdapter.ViewHolder) viewHolder).getSwipeView();
    }

}
