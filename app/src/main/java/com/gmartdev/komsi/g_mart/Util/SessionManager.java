package com.gmartdev.komsi.g_mart.Util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.gmartdev.komsi.g_mart.Class.Nilai;
import com.gmartdev.komsi.g_mart.Class.PostSplashScreen;

import java.util.HashMap;

public class SessionManager {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private static final String KEY_TOKEN = "token";
    private static final String KEY_PHONE_NUMBER = "phoneNumber";
    private static final String is_login = "loginStatus";
    private final String SHARE_NAME = "loginSession";
    private final int MODE_PRIVATE = 0;
    private Context _context;

    public SessionManager (Context context){
        this._context = context;
        sp = _context.getSharedPreferences(SHARE_NAME, MODE_PRIVATE);
        editor = sp.edit();
    }

    public void storeLogin(String phone_number, String token){
        editor.putBoolean(is_login, true);
        editor.putString(KEY_PHONE_NUMBER, phone_number);
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public HashMap getDetailLogin(){
        HashMap<String, String> map = new HashMap<>();
        map.put(KEY_PHONE_NUMBER, sp.getString(KEY_PHONE_NUMBER, null));
        map.put(KEY_TOKEN, sp.getString(KEY_TOKEN, null));
        return map;
    }

    public void checkLogin(){
        if (!this.isLogin()){
            Intent login = new Intent(_context, PostSplashScreen.class);
            login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(login);
        }
    }

    public boolean isLogin(){
        return sp.getBoolean(is_login, false);
    }

}
