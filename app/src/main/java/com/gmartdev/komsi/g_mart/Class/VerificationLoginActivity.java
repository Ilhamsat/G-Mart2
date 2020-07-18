package com.gmartdev.komsi.g_mart.Class;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gmartdev.komsi.g_mart.API.API;
import com.gmartdev.komsi.g_mart.Model.ConsumerModel;
import com.gmartdev.komsi.g_mart.Model.GetConsumerModel;
import com.gmartdev.komsi.g_mart.Model.GetLoginModel;
import com.gmartdev.komsi.g_mart.Model.LoginModel;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VerificationLoginActivity extends AppCompatActivity {

    private static final String TAG = "VerifActivity";

    String verificationCodeBySystem, phoneNumber, token, NumberPhone;

    TextView textNumberPhone;
    EditText etVerification;
    Button buttonVerification;
    String tokenFirenbase;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://gmart.vokasidev.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API api = retrofit.create(API.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_login);

        textNumberPhone = (TextView) findViewById(R.id.textNumberPhone);
        etVerification = (EditText) findViewById(R.id.numberVerification);
        buttonVerification = (Button) findViewById(R.id.ButtonVerificationLogin);

        String fullNumberPhone = getIntent().getStringExtra("fullNumberPhone");

        phoneNumber = getIntent().getStringExtra("fullNumberPhone");
        NumberPhone = getIntent().getStringExtra("PhoneNumber");

        textNumberPhone.setText(fullNumberPhone);

        sendVerificationToUser(fullNumberPhone);

        buttonVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = etVerification.getText().toString();
                if (code.isEmpty() | code.length()<6){
                    etVerification.setError("Wrong OTP...");
                    etVerification.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });

    }

    private void sendVerificationToUser(String fullNumberPhone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                fullNumberPhone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem = s;
        }
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                verifyCode(code);
            }
        }
        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerificationLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String codeByUser){

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);
        signInTheUserByCredentials(credential);

    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerificationLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            login();
                        }else {
                            Toast.makeText(VerificationLoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    private void login(){
        tokenFirenbase = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Call<GetLoginModel> call = api.loginKonsumen(NumberPhone,tokenFirenbase);
        call.enqueue(new Callback<GetLoginModel>() {
            @Override
            public void onResponse(Call<GetLoginModel> call, Response<GetLoginModel> response) {

                if(response.isSuccessful()){
                    Toast.makeText(VerificationLoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    if(response.body()!=null){
                        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

                        Log.d("testToken", response.body().getData().getToken());
                        Log.d("testToken", tokenFirenbase);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", response.body().getData().getToken());
                        editor.putString("no_hp",phoneNumber);

                        editor.commit();
//                        Log.d("token :",token);
                        Log.d(TAG, "onStart: Yang login = " + FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                        Log.d("Retrofit set", response.body().getCode() + " "+response.body().getMessage());
                        Intent i = new Intent(VerificationLoginActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }else{
                        Toast.makeText(VerificationLoginActivity.this,"No Data Found",Toast.LENGTH_LONG);
                    }
                }else{
                    switch (response.code()) {
                        case 404:
                            Toast.makeText(VerificationLoginActivity.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(VerificationLoginActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(VerificationLoginActivity.this, "unknown error " + response.code(), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<GetLoginModel> call, Throwable t) {
                String message = t.getMessage();
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(VerificationLoginActivity.this,"Connection time out  " + message,Toast.LENGTH_LONG ).show();
                }else if (t instanceof IOException){
                    Toast.makeText(VerificationLoginActivity.this,"Time Out  " + message,Toast.LENGTH_LONG ).show();
                }else {
                    //Call was cancelled by user
                    if(call.isCanceled()) {
                        System.out.println("Call was cancelled forcefully");
                    }
                    else {
                        //Generic error handling
                        System.out.println("Network Error :: " + t.getLocalizedMessage());
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Log.d(TAG, "onStart: Yang login = " + FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
            login();
//            Intent i = new Intent(VerificationLoginActivity.this, MainActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(i);
        }
    }



}
