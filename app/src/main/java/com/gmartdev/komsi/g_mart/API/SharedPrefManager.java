package com.gmartdev.komsi.g_mart.API;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    public static final String SP_USER_APP = "spUserApp";
    public static final String SP_TOKEN = "spToken";
    public static final String SP_ID = "spId";
    public static final String SP_PHONENUMBER = "spPhoneNumber";
    public static final String SP_IS_LOGIN = "spIsLogin";
    public static final String SP_NAME = "spName";
    public static final String SP_ADDRESS = "spAddress";
    public static final String SP_EMAIL = "spEmail";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context) {
        sp = context.getSharedPreferences(SP_USER_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value) {
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void deleteSPString(String keySP) {
        spEditor.remove(keySP).commit();
    }


    public void saveSPInt(String keySP, int value) {
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value) {
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSpToken() {
        return sp.getString(SP_TOKEN, "");
    }

    public String getSpEmail() {
        return sp.getString(SP_EMAIL, "");
    }

    public String getSpName() {
        return sp.getString(SP_NAME, "");
    }

    public String getSpId() {
        return sp.getString(SP_ID, "");
    }

    public String getSpPhonenumber() {
        return sp.getString(SP_PHONENUMBER, ""); }

    public String getSP_Address() {
        return sp.getString(SP_ADDRESS, ""); }


    public Boolean getSPIsLogin() {
        return sp.getBoolean(SP_IS_LOGIN, false);
    }

}
