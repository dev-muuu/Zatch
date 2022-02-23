package com.example.zatch.navigation.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.R;

import java.util.zip.Inflater;

interface SwipeStateListener {
    void onItemSwipe(int position);
    void onLeftClick(int position, RecyclerView.ViewHolder viewHolder);
    void onRightClick(int position, RecyclerView.ViewHolder viewHolder);
}

enum ButtonsState{
    GONE,
    LEFT_VISIBLE,
    RIGHT_VISIBLE
}


public class ChatItemDecoration extends ItemTouchHelper.Callback {

    private SwipeStateListener stateListener;
    private Context context;
    private boolean swipeBack = false;
    private ButtonsState buttonsShowedState = ButtonsState.GONE;
    private static final float buttonWidth = 300; //72dp임 수정 필요
    private Canvas buttonInstance = null;
    private RecyclerView.ViewHolder currenrtItemViewHolder = null;

    public ChatItemDecoration(SwipeStateListener stateListener, Context context) {
        this.stateListener = stateListener;
        this.context = context;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int drag_flags = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
        int swipe_flags = ItemTouchHelper.START|ItemTouchHelper.END;
        return makeMovementFlags(drag_flags,swipe_flags);

    }
    
    @Override public boolean isLongPressDragEnabled() {
        return true;
    }


    //item의 position 변경시 사용 -> 사용X
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    //user의해 swipe 됐을때 호출
    //direction 인자로 사용자의 swipe 방향 left/right 구분
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        stateListener.onItemSwipe(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            if(buttonsShowedState != ButtonsState.GONE){
                if(buttonsShowedState == ButtonsState.LEFT_VISIBLE)
                    dX = Math.max(dX, buttonWidth);
                if(buttonsShowedState == ButtonsState.RIGHT_VISIBLE)
                    dX = Math.min(dX, -buttonWidth);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }else{
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
            if(buttonsShowedState == ButtonsState.GONE){
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }
        currenrtItemViewHolder = viewHolder;
        //버튼을 그려주는 함수
        drawButtons(c, currenrtItemViewHolder);

    }

    private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder){
        float buttonWidthWithOutPadding = buttonWidth - 10;
        float corners = 5;
        View itemView = viewHolder.itemView;
        Paint p = new Paint();
        buttonInstance = null; //오른쪽으로 스와이프 했을때 (왼쪽에 버튼이 보여지게 될 경우)
         if(buttonsShowedState == ButtonsState.LEFT_VISIBLE){
//             RectF leftButton = new RectF(itemView.getLeft() + 10, itemView.getTop() + 10,
//                     itemView.getLeft() + buttonWidthWithOutPadding, itemView.getBottom() - 10);
             p.setColor(Color.BLUE);
//             c.drawRoundRect(leftButton, corners, corners, p);
//             buttonInstance = leftButton; //왼쪽으로 스와이프 했을때 (오른쪽에 버튼이 보여지게 될 경우)
//





         }else if(buttonsShowedState == ButtonsState.RIGHT_VISIBLE){
             RectF rightButton = new RectF(itemView.getRight() - buttonWidthWithOutPadding,
                     itemView.getTop() + 10, itemView.getRight() -10, itemView.getBottom() - 10);
             p.setColor(Color.RED);
             c.drawRoundRect(rightButton, corners, corners, p);
//             buttonInstance = rightButton;
         }
    }


    @SuppressLint("ClickableViewAccessibility")
    private void setTouchListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder,
                                  final float dX, final float dY, final int actionState, final boolean isCurrentlyActive){

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;
                if(swipeBack){
                    if(dX < -buttonWidth)
                        buttonsShowedState = ButtonsState.RIGHT_VISIBLE;
                    else if(dX > buttonWidth)
                        buttonsShowedState = ButtonsState.LEFT_VISIBLE;

                    if(buttonsShowedState != ButtonsState.GONE){
                        setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        setItemsClickable(recyclerView, false);
                    }
                }
                return false;
            }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchDownListener(final Canvas c, final RecyclerView recyclerView , final RecyclerView.ViewHolder viewHolder,
                                      final float dX, final float dY , final int actionState, final boolean isCurrentlyActive){
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
                return false;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchUpListener(final Canvas c, final RecyclerView recyclerView , final RecyclerView.ViewHolder viewHolder,
                                    final float dX, final float dY , final int actionState, final boolean isCurrentlyActive){
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ChatItemDecoration.super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive);
                recyclerView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });

//                setItemsClickable(recyclerView, true);
//                swipeBack = false;
//                if(stateListener != null && buttonInstance != null && buttonInstance.contains(event.getX(), event.getY())){
//                    if(buttonsShowedState == ButtonsState.LEFT_VISIBLE){
//                        stateListener.onLeftClick(viewHolder.getAdapterPosition(), viewHolder);
//                    }else if(buttonsShowedState == ButtonsState.RIGHT_VISIBLE){
//                        stateListener.onRightClick(viewHolder.getAdapterPosition(), viewHolder);
//                    }
//                }
//                buttonsShowedState = ButtonsState.GONE;
//                currenrtItemViewHolder = null;
                return false;
            }
        });
    }

    private void setItemsClickable(RecyclerView recyclerView, boolean isClickable){
        for(int i = 0; i < recyclerView.getChildCount(); i++){
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }





}
