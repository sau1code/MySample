package com.sample.otptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private Button bt_send, bt_verify;
    private EditText edt_otp;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_send = findViewById(R.id.bt_send);
        bt_verify = findViewById(R.id.bt_verify);
        edt_otp = findViewById(R.id.edt_otp);

        // 發送OTP碼 鍵
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 處裡驗證過程中的不同事件
                callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    // 少數設備不須驗證，會直接運行此方法，使用者甚麼都不需要做
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Log.d("main", "onVerificationCompleted()");
                    }

                    // 手機號碼格式錯誤，或應用簡訊超出配額
                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Log.d("main", "onVerificationFailed(): " + e.getMessage());
                    }

                    // OTP碼已寄出至手機
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verificationId = s;
                        Toast.makeText(MainActivity.this, "驗證碼 已寄出至手機", Toast.LENGTH_SHORT).show();
                    }
                };

                // 設定用戶手機、請求時間、回調類別
                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setActivity(MainActivity.this)
                        .setPhoneNumber("+886910419179")
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setCallbacks(callbacks)
                        .build();

                // 發送驗證碼給手機
                PhoneAuthProvider.verifyPhoneNumber(options);
            }
        });

        // 驗證 鍵
        bt_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 寫 if 避免空值錯誤
                if (verificationId != null && !edt_otp.getText().toString().isEmpty()) {

                    // 比對用戶輸入的驗證碼
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(
                            verificationId,
                            edt_otp.getText().toString()
                    );
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(MainActivity.this, "驗證成功", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, "驗證失敗", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }
}
