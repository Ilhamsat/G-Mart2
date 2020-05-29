package com.gmartdev.komsi.g_mart.Class;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gmartdev.komsi.g_mart.API.API;
import com.gmartdev.komsi.g_mart.API.APIClient;
import com.gmartdev.komsi.g_mart.API.SharedPrefManager;
import com.gmartdev.komsi.g_mart.Model.GetConsumerModel;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {


    private String nama, no_hp, email, alamat, password;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    TextInputLayout etNumberPhone, etAddress, etName, etEmail, etPassword;
    Button buttonDaftar;
    ImageButton butonBack;


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://gmart.vokasidev.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API api = retrofit.create(API.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNumberPhone = (TextInputLayout) findViewById(R.id.NoTelepon);
        etAddress = (TextInputLayout) findViewById(R.id.Alamat);
        etName = (TextInputLayout) findViewById(R.id.Nama);
        etEmail = (TextInputLayout) findViewById(R.id.Email);

        butonBack = (ImageButton) findViewById(R.id.backInRegister);
        butonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonDaftar = (Button) findViewById(R.id.ButtonDaftar);
        buttonDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                no_hp = etNumberPhone.getEditText().getText().toString();
                alamat = etAddress.getEditText().getText().toString();
                nama = etName.getEditText().getText().toString();
                email = etEmail.getEditText().getText().toString();

                if(!validateNumberPhone() | !validateName() | !validateAddress() | !validateEmail()){
                    return;
                } else{
                    register();
                }
            }
        });
    }

    private boolean validateNumberPhone(){
        String NumberPhone = etNumberPhone.getEditText().getText().toString().trim();

        if(NumberPhone.isEmpty()){
            etNumberPhone.setError("Field can't be empty");
            return false;
        } else if(NumberPhone.length()>14){
            etNumberPhone.setError("Number Phone too long");
            return false;
        }else {
            etNumberPhone.setError(null);
            return true;
        }
    }

//    private boolean validatePassword(){
//        String Password = etPassword.getEditText().getText().toString().trim();
//
//        if(Password.isEmpty()){
//            etPassword.setError("Field can't be empty");
//            return false;
//        } else if (!PASSWORD_PATTERN.matcher(Password).matches()){
//            etPassword.setError("at least 1 digit, upper and lower case, no whitespace and minimum 4 characters");
//            return false;
//        }else {
//            etPassword.setError(null);
//            return true;
//        }
//    }

    private boolean validateName(){
        String Name = etName.getEditText().getText().toString().trim();

        if(Name.isEmpty()){
            etName.setError("Field can't be empty");
            return false;
        }else {
            etName.setError(null);
            return true;
        }
    }

    private boolean validateAddress(){
        String Address = etAddress.getEditText().getText().toString().trim();

        if(Address.isEmpty()){
            etAddress.setError("Field can't be empty");
            return false;
        }else {
            etAddress.setError(null);
            return true;
        }
    }

    private boolean validateEmail(){
        String Email = etEmail.getEditText().getText().toString().trim();

        if(Email.isEmpty()){
            etEmail.setError("Field can't be empty");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            etEmail.setError("Please enter a valid email address");
            return false;
        }else {
            etEmail.setError(null);
            return true;
        }
    }


//    public void SetNewComsumer(String id_konsumen, String nama, String no_hp, String email, String alamat, String token){
//        Call<GetConsumerModel> arsipBook = mAPI.setNewConsumer(id_konsumen, nama, no_hp, email, alamat, token);
//        arsipBook.enqueue(new Callback<GetConsumerModel>() {
//            @Override
//            public void onResponse(Call<GetConsumerModel> call, Response<GetConsumerModel> response) {
//                String code = response.body().getCode();
//                Log.d("Retrofit set", code + response.body().getMessage());
//                UpdateToken(sharedPrefManager.getSpId(), sharedPrefManager.getSpPhonenumber(), sharedPrefManager.getSpToken());
//                Log.d("Retrofit Update Token", "onResponse : " + sharedPrefManager.getSpToken());
//            }
//
//            @Override
//            public void onFailure(Call<GetConsumerModel> call, Throwable t) {
//                Log.e("Retrofit Gets", t.toString());
//            }
//        });
//    }
//
//    public void UpdateToken(final String id_konsumen, String no_hp, String token){
//        Call<GetConsumerModel> call = mAPI.updateConsumerToken(id_konsumen, no_hp, token);
//        call.enqueue(new Callback<GetConsumerModel>() {
//            @Override
//            public void onResponse(Call<GetConsumerModel> call, Response<GetConsumerModel> response) {
//                Intent intent = new Intent(Register.this, MainActivity.class);
//                String respond = response.body().getCode();
//                String valid = "200";
//                String invalid = "404";
//
//                if (respond.equals(valid)) {
//                    sharedPrefManager.saveSPString(SharedPrefManager.SP_ID, id_konsumen);
//                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_IS_LOGIN, true);
//                    Log.d("Retrofit", "onResponse UpdateToken: " + sharedPrefManager.getSpToken());
//                    startActivity(intent);
//                    finish();
//                } else if (respond.equals(invalid)) {
//                    startActivity(intent);
//                    finish();
//                }
//                Log.d("Retrofit Get", response.body().getCode());
//            }
//
//            @Override
//            public void onFailure(Call<GetConsumerModel> call, Throwable t) {
//                Log.e("Retrofit Gets", t.toString());
//            }
//        });
//    }


    private void register(){
        Call<GetConsumerModel> call = api.setNewConsumer(nama, no_hp, email, alamat, "");
        call.enqueue(new Callback<GetConsumerModel>() {
            @Override
            public void onResponse(Call<GetConsumerModel> call, Response<GetConsumerModel> response) {
                if (response.isSuccessful()){
                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                    Toast.makeText(Register.this,"Registered Complete",Toast.LENGTH_LONG);
                    if(sharedPreferences.getString("no_hp",null) != null){
                        sharedPreferences.edit()
                                .clear()
                                .commit();
                        SharedPreferences.Editor editor= sharedPreferences.edit();
                        editor.putString("no_hp",no_hp);
                        editor.putString("nama",nama);
                        editor.putString("email",email);
                        editor.putString("alamat",alamat);

                        editor.commit();
                    }else{
                        SharedPreferences.Editor editor= sharedPreferences.edit();
                        editor.putString("no_hp",no_hp);
                        editor.putString("nama",nama);
                        editor.putString("email",email);
                        editor.putString("alamat",alamat);

                        editor.commit();
                    }
                    Intent i = new Intent(Register.this,LoginActivity.class);
                    startActivity(i);

                }else {

                }
            }

            @Override
            public void onFailure(Call<GetConsumerModel> call, Throwable t) {
                Log.d("Register",t.getMessage());
            }
        });
    }

}
