package com.example.komak;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private SharedPreferences prefs;

    public Session(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public void setemail(String email){
        prefs.edit().putString("email", email).commit();

    }
    public String getemail(){
        String email = prefs.getString("email", "");
        return email;
    }
    public void DestroySession(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }
}
