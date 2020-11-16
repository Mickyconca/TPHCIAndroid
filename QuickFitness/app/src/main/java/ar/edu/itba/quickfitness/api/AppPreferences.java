package ar.edu.itba.quickfitness.api;

import android.content.Context;
import android.content.SharedPreferences;

import ar.edu.itba.quickfitness.R;

public class AppPreferences {

    private SharedPreferences preferences;
    public final String AUTH_TOKEN = "auth_token";

    public AppPreferences(Context context) {
        preferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public void setAuthToken(String token){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AUTH_TOKEN,token);
        editor.apply();
    }

    public String getToken(){
        return preferences.getString(AUTH_TOKEN, null);
    }
}
