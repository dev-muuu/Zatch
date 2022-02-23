package com.example.zatch.navigation.my_zatch;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.zatch.R;

public class gatch_upload2 extends Activity {
    Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gatch_upload2);

        Intent intent= getIntent();
        String name = intent.getStringExtra("name");
        String category = intent.getStringExtra("category");
        int[] infoArr = intent.getIntArrayExtra("info");
        boolean purchase_state[] = intent.getBooleanArrayExtra("purchase_state");
        boolean pstate = purchase_state[0];
        TextView state_tag = (TextView) findViewById(R.id.purchase_state_tag);
        if (!pstate) {
            state_tag.setText("구매 전");
            state_tag.setTextColor(getResources().getColor(R.color.black_45));
            state_tag.setBackgroundResource(R.drawable.tag_gray);
        } else {
            state_tag.setText("구매 후");
            state_tag.setTextColor(getResources().getColor(R.color.zatch_purple));
            state_tag.setBackgroundResource(R.drawable.tag_purple);
        }

        TextView tv_name, tv_category, tv_people, tv_oprice, tv_nprice;
        tv_name = (TextView)findViewById(R.id.gatch_item);
        tv_category = (TextView) findViewById(R.id.item_category);
        tv_people = (TextView) findViewById(R.id.item_person);
        tv_oprice = (TextView) findViewById(R.id.item_price1);
        tv_nprice = (TextView) findViewById(R.id.item_price2);

        tv_name.setText(name);
        tv_category.setText(category);
        tv_people.setText(infoArr[1]+"명");
        tv_oprice.setText(String.valueOf(infoArr[0]));
        tv_nprice.setText(String.valueOf(infoArr[2]));

        EditText et_add;
        et_add = (EditText) findViewById(R.id.add_et);

        dialog = new Dialog(gatch_upload2.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog2);



        Button upload = (Button) findViewById(R.id.upload_gatch);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

    }

    public void showDialog() {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView noBtn = dialog.findViewById(R.id.dialog_cancel_btn);
        TextView okBtn = dialog.findViewById(R.id.dialog_ok_btn);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //등록 완료
            }
        });
    }
}
