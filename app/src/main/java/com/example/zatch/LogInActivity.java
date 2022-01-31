package com.example.zatch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zatch.location.StartLocationActivity;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;

public class LogInActivity extends AppCompatActivity {

    private String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.loginButton).setOnClickListener(onClickListener);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.loginButton:
                    Log.e("LogInActivity","tryLogin");
                    tryLogin();
                    break;
            }
        }
    };

    private void tryLogin(){
//        // 카카오계정으로 로그인
//        UserApiClient.getInstance().loginWithKakaoAccount(LogInActivity.this, (token, error) ->{
//            if (error != null) {
//                Log.e(TAG, "로그인 실패", error);
//            }
//            else if (token != null) {
//                Log.i(TAG, "로그인 성공 ${token.accessToken}");
//                moveActivity();
//            }
//            return null;
//        });

        UserApiClient.getInstance().loginWithKakaoAccount(LogInActivity.this,(oAuthToken, error) ->{
                    if (error != null) {
                        Log.e(TAG, "로그인 실패", error);
                    }
                    else if (oAuthToken != null) {
                        Log.i(TAG, "로그인 성공 ${token.accessToken}");

                        // 사용자 정보 요청
                        UserApiClient.getInstance().me((user, meError) -> {
                            if (meError != null) {
                                Log.e(TAG, "사용자 정보 요청 실패", meError);
                            } else {
                                System.out.println("로그인 됐다");
                                Log.i(TAG, user.toString());

                                Account user1 = user.getKakaoAccount();
                                System.out.println("유저 어카운트"+user1);
                                System.out.println(user.getId());
                                System.out.println(user1.getEmail());
                                System.out.println(user1.getGender());
                                moveActivity();
                            }
                            return null;
                        });


                    }
                    return null;
                }
        );
    }


    private void moveActivity(){
        Intent intent = new Intent(this, StartLocationActivity.class);
        startActivity(intent);
    }
}


