package com.gmartdev.komsi.g_mart.Class;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.gmartdev.komsi.g_mart.API.API;
import com.gmartdev.komsi.g_mart.Model.ConsumerModel;
import com.gmartdev.komsi.g_mart.Model.GetConsumerModel;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfile";

    private String nama, birth, email, alamat, id_konsumen, token_konsumen, nohp;
    TextInputLayout etBirth, etAddress, etName, etEmail;
    Button buttonUpdate;

    DatePickerDialog.OnDateSetListener dateSetListener;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://gmart.vokasidev.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API api = retrofit.create(API.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        TextView profileName = (TextView) findViewById(R.id.profile_name);
        etBirth = (TextInputLayout) findViewById(R.id.TanggalLahir);
        etAddress = (TextInputLayout) findViewById(R.id.Alamat);
        etName = (TextInputLayout) findViewById(R.id.Nama);
        etEmail = (TextInputLayout) findViewById(R.id.Email);
        buttonUpdate = (Button) findViewById(R.id.btnSaveEditProfile);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        profileName.setText(sharedPreferences.getString("nama", null));
        etName.getEditText().setText(sharedPreferences.getString("nama", null));
        etEmail.getEditText().setText(sharedPreferences.getString("email", null));
        etAddress.getEditText().setText(sharedPreferences.getString("alamat", null));
        etBirth.getEditText().setText(sharedPreferences.getString("date", null));
        nohp = sharedPreferences.getString("no_hp", null);
//        getProfile();

//        dateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                i1 = month+1;
//            }
//        };

        etBirth.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + "DatePickerDialog");
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = year+"-"+month+"-"+day;
                        birth = date;
                        etBirth.getEditText().setText(birth);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                birth = etBirth.getEditText().getText().toString();
                alamat = etAddress.getEditText().getText().toString();
                nama = etName.getEditText().getText().toString();
                email = etEmail.getEditText().getText().toString();

                if(!validateName() | !validateAddress() | !validateEmail() | !validateBirth()){
                    return;
                } else{
                    updateProfile();
                }
            }
        });
    }

    private boolean validateBirth(){
        String Birth = etBirth.getEditText().getText().toString().trim();
        if(Birth.isEmpty()){
            etBirth.setError("Field can't be empty");
            return false;
        }else {
            etBirth.setError(null);
            return true;
        }
    }

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

    private void updateProfile(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        id_konsumen = sharedPreferences.getString("id_konsumen", null);
        token_konsumen = sharedPreferences.getString("token", null);
        Call<GetConsumerModel> call = api.updateConsumer(id_konsumen, nama, email, alamat, token_konsumen, birth);
        call.enqueue(new Callback<GetConsumerModel>() {
            @Override
            public void onResponse(Call<GetConsumerModel> call, Response<GetConsumerModel> response) {
                if (response.isSuccessful()){
                    sharedPreferences.edit()
                            .commit();
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString("no_hp", nohp);
                    editor.putString("nama",nama);
                    editor.putString("email",email);
                    editor.putString("alamat",alamat);
                    editor.putString("date", birth);
                    editor.commit();

                    Toast.makeText(EditProfileActivity.this, "Profil berhasil di update", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(EditProfileActivity.this, "Profil gagal di update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetConsumerModel> call, Throwable t) {

            }
        });
    }

    private void getProfile(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        id_konsumen = sharedPreferences.getString("id_konsumen", null);
        token_konsumen = sharedPreferences.getString("token", null);
        Log.d(TAG, "getProfile: " + "kenapa");
        Log.d(TAG, "getProfile: " + id_konsumen);
        Log.d(TAG, "getProfile: " + token_konsumen);
        Call<GetConsumerModel> callProfile = api.getProfileConsumer(id_konsumen, token_konsumen);
        callProfile.enqueue(new Callback<GetConsumerModel>() {
            @Override
            public void onResponse(Call<GetConsumerModel> call, Response<GetConsumerModel> response) {
                //List<ConsumerModel> listDataKonsumen = response.body().getListDataKonsumen();
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: getConsumer " + response.body().getMessage());
//                    etBirth.getEditText().setText(listDataKonsumen.get(0).getTanggal_lahir());
//                    etName.getEditText().setText(listDataKonsumen.get(0).getNama());
//                    etAddress.getEditText().setText(listDataKonsumen.get(0).getAlamat());
//                    etEmail.getEditText().setText(listDataKonsumen.get(0).getEmail());
                }else {
                    Log.d(TAG, "onResponse: getConsumer " + response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetConsumerModel> call, Throwable t) {

            }
        });
    }
}
