package com.example.zverys.to_do;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kerut on 19/03/2018.
 */

public class User {
    private String usernameLogin, usernameRegister, password, email;
    private static final String PREFERENCES_FILE_NAME = "com.example.zverys.to_do";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String REMEMBER_ME_KEY = "rememberMe";
    private SharedPreferences sharedPreferences;

    //Naudojama registruojant naują vartotoją
    public User(String usernameRegister, String password, String email) {
        this.usernameRegister = usernameRegister;
        this.password = password;
        this.email = email;
    }

    //Naudojamas prisijungimo lange
    public User(Context context) {
        this.sharedPreferences = context.getSharedPreferences(User.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    public String getUsernameForLogin() {
        return this.sharedPreferences.getString(USERNAME_KEY, "");
    }

    public void setUsernameLogin(String usernameLogin) {
        this.sharedPreferences.edit().putString(USERNAME_KEY, usernameLogin).commit();
    }

    public String getUsernameForRegistration() {
        return this.usernameRegister;
    }

    public void setUsernameForRegistration(String usernameRegister) {
        this.usernameRegister = usernameRegister;
    }

    public String getPasswordForRegistration() {
        return this.password;
    }

    public String getPasswordForLogin() {
        return this.sharedPreferences.getString(PASSWORD_KEY, "");
    }

    public void setPasswordForLogin(String password) {
        this.sharedPreferences.edit().putString(PASSWORD_KEY, password).commit();
    }

    public String getEmailForRegistration() {
        return this.email;
    }

    public boolean isRemembered() {
        return this.sharedPreferences.getBoolean(REMEMBER_ME_KEY, false);
    }

    public void setRemembered(boolean remembered) {
        this.sharedPreferences.edit().putBoolean(REMEMBER_ME_KEY, remembered).commit();
    }
}
