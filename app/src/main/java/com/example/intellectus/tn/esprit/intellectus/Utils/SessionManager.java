package com.example.intellectus.tn.esprit.intellectus.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.intellectus.LoginActivity;
import com.example.intellectus.tn.esprit.intellectus.Entity.User;

public class SessionManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Pref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";


    public static final String KEY_ID = "name";
    public static final String KEY_FNAME = "name";
    public static final String KEY_LNAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ROLE = "email";



    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(User user){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID, user.getId()+"");
        editor.putString(KEY_FNAME, user.getName());
        editor.putString(KEY_LNAME, user.getLast_name());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_ROLE, user.getRole());
        // commit changes
        editor.commit();
    }


    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    // Get Email State
    public String getEmail(){
        return pref.getString(KEY_EMAIL, "NULL");
    }
    // Get Email State
    public String getFName(){
        return pref.getString(KEY_FNAME, "NULL");
    }
    // Get Email State
    public String getLName(){
        return pref.getString(KEY_LNAME, "NULL");
    }
    // Get Email State
    public String getID(){
        return pref.getString(KEY_ID, "NULL");
    }

    public void cleanEditor(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }
}