package com.gmartdev.komsi.g_mart.Class;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.gmartdev.komsi.g_mart.API.API;
import com.gmartdev.komsi.g_mart.Model.ConsumerModel;
import com.gmartdev.komsi.g_mart.Model.GetConsumerModel;
import com.gmartdev.komsi.g_mart.Model.GetLoginModel;
import com.gmartdev.komsi.g_mart.R;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    private static final String TAG = "SplashScreen";

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    String phoneNumberUser, tokenFirebase;
    String phoneNumberUserAPI;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://gmart.vokasidev.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API api = retrofit.create(API.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                dataDummy();
//                if(FirebaseAuth.getInstance().getCurrentUser() != null){
//                    Log.d(TAG, "run: ");
//                    phoneNumberUser = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
//                    tokenFirebase = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                    String[] parts = phoneNumberUser.split("\\+62");
//                    phoneNumberUserAPI = parts[1];
//                    Log.d(TAG, "run: phoneNumberUserAPI " + phoneNumberUserAPI);
//                    checkKonsumen(phoneNumberUserAPI);
//
//                }else {
//                    Intent i = new Intent(SplashScreen.this, PostSplashScreen.class);
//                    startActivity(i);
//                    // close this activity
//                    finish();
//                }

            }
        }, SPLASH_TIME_OUT);
    }


    public void checkKonsumen(String phoneNumber){
        Call<GetConsumerModel> call = api.getRegisteredKonsumen(phoneNumber);
        call.enqueue(new Callback<GetConsumerModel>() {
            @Override
            public void onResponse(Call<GetConsumerModel> call, Response<GetConsumerModel> response) {

                String code = response.body().getCode();
                List<ConsumerModel> listDataKonsumen = response.body().getListDataKonsumen();
                String berhasil = "200";

                if (code.equals(berhasil)) {
                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                    Toast.makeText(SplashScreen.this,"Login Complete",Toast.LENGTH_LONG);

                    sharedPreferences.edit()
                            .clear()
                            .commit();
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString("id_konsumen",listDataKonsumen.get(0).getId_konsumen());
                    editor.putString("no_hp",listDataKonsumen.get(0).getNo_hp());
                    editor.putString("nama",listDataKonsumen.get(0).getNama());
                    editor.putString("email",listDataKonsumen.get(0).getEmail());
                    editor.putString("alamat",listDataKonsumen.get(0).getAlamat());
                    editor.putString("token", tokenFirebase);
                    editor.commit();

                    login();

                } else {
                    Log.d("Retrofit Get", "onResponse: gak kebaca");
                }
            }

            @Override
            public void onFailure(Call<GetConsumerModel> call, Throwable t) {

            }
        });
    }


    private void login(){
        tokenFirebase = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Call<GetLoginModel> call = api.loginKonsumen(phoneNumberUserAPI,tokenFirebase);
        call.enqueue(new Callback<GetLoginModel>() {
            @Override
            public void onResponse(Call<GetLoginModel> call, Response<GetLoginModel> response) {

                if(response.isSuccessful()){
                    Toast.makeText(SplashScreen.this, "Success", Toast.LENGTH_SHORT).show();
                    if(response.body()!=null){
                        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

                        Log.d("testToken", response.body().getData().getToken());
                        Log.d("testToken", tokenFirebase);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", response.body().getData().getToken());
                        editor.putString("no_hp", phoneNumberUser);

                        editor.commit();
//                        Log.d("token :",token);
                        Log.d(TAG, "onStart: Yang login = " + FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                        Log.d("Retrofit set", response.body().getCode() + " "+response.body().getMessage());
                        Intent i = new Intent(SplashScreen.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();

                    }else{
                        Toast.makeText(SplashScreen.this,"No Data Found",Toast.LENGTH_LONG);
                    }
                }else{
                    switch (response.code()) {
                        case 404:
                            Toast.makeText(SplashScreen.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(SplashScreen.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(SplashScreen.this, "unknown error " + response.code(), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<GetLoginModel> call, Throwable t) {
                String message = t.getMessage();
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(SplashScreen.this,"Connection time out  " + message,Toast.LENGTH_LONG ).show();
                }else if (t instanceof IOException){
                    Toast.makeText(SplashScreen.this,"Time Out  " + message,Toast.LENGTH_LONG ).show();
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
    public void dataDummy(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id_konsumen","54337");
        editor.putString("nama","Joe MantapBos");
        editor.putString("email","joetaslim@gmail.com");
        editor.putString("alamat","yogyakarta");
        editor.putString("token", "$2y$10$9df733661f50f5dcf8ae7u6g69HlxOYmqCA1UtOlO6qo/DBiy5j.K");
        editor.putString("no_hp", "+6281347591227");
        editor.commit();

        Intent i = new Intent(SplashScreen.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
