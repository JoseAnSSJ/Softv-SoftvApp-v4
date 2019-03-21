package com.example.pablo.prueba7.sampledata;

import android.content.SharedPreferences;

public class Util {
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;

    public static String getEncoPreference(SharedPreferences preferences){
        return preferences.getString("enco", "");
    }

    public static String getUsuarioPreference(SharedPreferences preferences){
        return preferences.getString("usuario", "");
    }

    public static String getTokenPreference(SharedPreferences preferences){
        return preferences.getString("token", "");
    }

    public static String getClvTecnicoPreference(SharedPreferences preferences){
        return preferences.getString("clv_Tecnico", "");
    }

    public static String getNombreTecnicoPreference(SharedPreferences preferences){
        return preferences.getString("nombre_Tecnico", "");
    }
}