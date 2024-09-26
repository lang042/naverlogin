package com.example.naverlogin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.navercorp.nid.NaverIdLoginSDK;
import com.navercorp.nid.oauth.OAuthLoginCallback;
import com.navercorp.nid.oauth.view.NidOAuthLoginButton;

public class NaverLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naverlogin);

        // 뷰 객체 연결
        TextView textView = findViewById(R.id.textView);
        NidOAuthLoginButton btnLogin = findViewById(R.id.btn_login);

        // 네이버 로그인 SDK 초기화
        NaverIdLoginSDK.INSTANCE.initialize(
                this,
                getString(R.string.naver_client_id),    // 클라이언트 ID
                getString(R.string.naver_client_secret), // 클라이언트 Secret
                getString(R.string.app_name)
        );

        // 네이버 로그인 버튼 클릭 리스너 설정
        btnLogin.setOAuthLogin(new OAuthLoginCallback() {
            @Override
            public void onSuccess() {
                // 로그인 성공 시 액세스 토큰 가져오기
                String accessToken = NaverIdLoginSDK.INSTANCE.getAccessToken();
                textView.setText(accessToken);
                btnLogin.setVisibility(View.GONE); // 로그인 성공 후 버튼 숨기기
            }

            @Override
            public void onFailure(int httpStatus, @NonNull String message) {
                // 통신 오류 처리
                Log.e("네아로", "onFailure: httpStatus - " + httpStatus + " / message - " + message);
            }

            @Override
            public void onError(int errorCode, @NonNull String message) {
                // 로그인 중 오류 처리
                Log.e("네아로", "onError: errorCode - " + errorCode + " / message - " + message);
            }
        });
    }
}
