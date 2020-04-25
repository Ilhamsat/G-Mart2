package com.gmartdev.komsi.g_mart.Class;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gmartdev.komsi.g_mart.R;

public class PostSplashScreen extends AppCompatActivity {

    Button buttonRegister, buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_splash_screen);

        buttonLogin = (Button) findViewById(R.id.ButtonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostSplashScreen.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        buttonRegister = (Button) findViewById(R.id.ButtonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostSplashScreen.this,Register.class);
                startActivity(intent);
            }
        });
    }
}
