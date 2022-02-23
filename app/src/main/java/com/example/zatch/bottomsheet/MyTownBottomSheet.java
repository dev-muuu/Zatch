package com.example.zatch.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.example.zatch.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MyTownBottomSheet extends BottomSheetDialogFragment {

    View view;
    String[] data;
    String nowTown;
    MyTownBottomSheetListener dialogListener;

    public interface MyTownBottomSheetListener{
        void MyTownBottomFinish(String townName);
    }

    public void setDialogListener(MyTownBottomSheetListener dialogListener){
        this.dialogListener = dialogListener;
    }

    public MyTownBottomSheet(String[] data, String nowTown){
        this.data = data;
        this.nowTown = nowTown;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.bottom_sheet_town_set,container);
        ListView townList = view.findViewById(R.id.townDataList);
        townList.setAdapter(new TownListAdapter(data));
        return view;
    }

    class TownListAdapter extends BaseAdapter {

        String[] townList;

        TownListAdapter(String[] townList){
            this.townList = townList;
        }

        @Override
        public int getCount() {
            return townList.length;
        }

        @Override
        public String getItem(int position) {
            return townList[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            System.out.println(position);
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_bottom_town_set, parent, false);
            }

            TextView townText = convertView.findViewById(R.id.townListText);

            if(getItem(position).equals(nowTown)) {
                townText.setTextColor(getResources().getColor(R.color.zatch_yellow));
                //font: bold로 change
                townText.setTypeface(ResourcesCompat.getFont(getContext(),R.font.pretendard_bold));
            }
            townText.setText(getItem(position));
            townText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogListener.MyTownBottomFinish(townText.getText().toString());
                    dismiss();
                }
            });
            return convertView;
        }

    }


}
