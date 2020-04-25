package com.gmartdev.komsi.g_mart.Class;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gmartdev.komsi.g_mart.R;

public class LoginActivity extends AppCompatActivity {

    EditText etNumberPhone;

    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etNumberPhone = (EditText) findViewById(R.id.numberPhone);
    }

    private boolean validateNumberPhone(){
        String NumberPhone = etNumberPhone.getText().toString().trim();

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

        String fullNumberPhone = "+62" + NumberPhone;

        if(!validateNumberPhone()){
            return;
        }else {

            Intent intent = new Intent(getApplicationContext(), VerificationLoginActivity.class);
            intent.putExtra("fullNumberPhone",fullNumberPhone);
            startActivity(intent);

        }
    }

}
