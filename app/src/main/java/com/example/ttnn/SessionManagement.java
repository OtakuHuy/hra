package com.example.ttnn;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_key";
    String SESSION_STRING = "session_string";

    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public  void saveSession(User user){
        int status = user.getStatus();
        editor.putInt(SESSION_KEY, status).commit();
    }

    public int getSession(){
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }

    public void saveString(User user){
        String mabhyt = user.getMabhyt();
        editor.putString("MA", mabhyt).commit();
    }

    public String getString(User user){
        return sharedPreferences.getString("MA", user.getMabhyt());
    }

}

