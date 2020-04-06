package com.gmartdev.komsi.g_mart.Class;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNumberPhone = (TextInputLayout) findViewById(R.id.NoTelepon);
        etAddress = (TextInputLayout) findViewById(R.id.Alamat);
        etName = (TextInputLayout) findViewById(R.id.Nama);
        etEmail = (TextInputLayout) findViewById(R.id.Email);
        etPassword = (TextInputLayout) findViewById(R.id.Password);

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

                String NumberPhone = etNumberPhone.getEditText().getText().toString();
                String Address = etAddress.getEditText().getText().toString();
                String Name = etName.getEditText().getText().toString();
                String Email = etEmail.getEditText().getText().toString();
                String Password = etPassword.getEditText().getText().toString();

                if(!validateNumberPhone() | !validatePassword() | !validateName() | !validateAddress() | !validateEmail()){
                    return;
                } else{
                    Intent intent = new Intent(Register.this,MainActivity.class);
                    startActivity(intent);
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

    private boolean validatePassword(){
        String Password = etPassword.getEditText().getText().toString().trim();

        if(Password.isEmpty()){
            etPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(Password).matches()){
            etPassword.setError("at least 1 digit, upper and lower case, no whitespace and minimum 4 characters");
            return false;
        }else {
            etPassword.setError(null);
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

}
