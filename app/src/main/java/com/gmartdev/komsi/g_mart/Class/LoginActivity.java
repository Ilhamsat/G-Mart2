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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gmartdev.komsi.g_mart.API.API;
import com.gmartdev.komsi.g_mart.Model.ConsumerModel;
import com.gmartdev.komsi.g_mart.Model.GetConsumerModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText etNumberPhone;
    Button buttonLogin;

    String fullNumberPhone;
    String NumberPhone;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://gmart.vokasidev.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API api = retrofit.create(API.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etNumberPhone = (EditText) findViewById(R.id.numberPhone);

        NumberPhone = etNumberPhone.getText().toString().trim();
    }

    private boolean validateNumberPhone(){
        NumberPhone = etNumberPhone.getText().toString().trim();

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

    public void confirmLogin(View view){
        String NumberPhone = etNumberPhone.getText().toString();

        fullNumberPhone = "+62" + NumberPhone;

        if(!validateNumberPhone()){
            return;
        }else {
            checkRegisteredKonsumen(NumberPhone);
//            Intent intent = new Intent(getApplicationContext(), VerificationLoginActivity.class);
//            intent.putExtra("fullNumberPhone",fullNumberPhone);
//            startActivity(intent);

        }
    }


    public void checkRegisteredKonsumen(String phoneNumber){
        Call<GetConsumerModel> call = api.getRegisteredKonsumen(phoneNumber);
        call.enqueue(new Callback<GetConsumerModel>() {
            @Override
            public void onResponse(Call<GetConsumerModel> call, Response<GetConsumerModel> response) {

                Intent intent = null;
                String code = response.body().getCode();
                List<ConsumerModel> listDataKonsumen = response.body().getListDataKonsumen();
                String berhasil = "200";
                String belumkedaftar = "404";

                if (code.equals(belumkedaftar)) {
                    intent = new Intent(getApplicationContext(), Register.class);
                    Log.d("Retrofit Get", "Maaf Anda belum terdaftar");
                    startActivity(intent);
                } else if (code.equals(berhasil)) {
                    Log.d("Retrofit Get", "Anda sudah terdaftar");
                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                    Toast.makeText(LoginActivity.this,"Login Complete",Toast.LENGTH_LONG);

                    sharedPreferences.edit()
                                .clear()
                                .commit();
                        SharedPreferences.Editor editor= sharedPreferences.edit();
                        editor.putString("id_konsumen",listDataKonsumen.get(0).getId_konsumen());
                        editor.putString("no_hp",listDataKonsumen.get(0).getNo_hp());
                        editor.putString("nama",listDataKonsumen.get(0).getNama());
                        editor.putString("email",listDataKonsumen.get(0).getEmail());
                        editor.putString("alamat",listDataKonsumen.get(0).getAlamat());
                        editor.commit();

                    intent = new Intent(getApplicationContext(), VerificationLoginActivity.class);
                    intent.putExtra("fullNumberPhone",fullNumberPhone);
                    intent.putExtra("PhoneNumber", NumberPhone);
                    startActivity(intent);

//                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, listDataKonsumen.get(0).getNama());
//                    sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, listDataKonsumen.get(0).getEmail());
//                    sharedPrefManager.saveSPString(SharedPrefManager.SP_PHONENUMBER, listDataKonsumen.get(0).getNo_hp());
//                    Log.d("Retrofit", "onResponse: " + sharedPrefManager.getSpToken());
//                    UpdateToken(sharedPrefManager.getSpId(), sharedPrefManager.getSpPhonenumber(), sharedPrefManager.getSpToken());

                } else {
                    Log.d("Retrofit Get", "onResponse: gak kebaca");
                }

            }

            @Override
            public void onFailure(Call<GetConsumerModel> call, Throwable t) {

            }
        });

    }

}
