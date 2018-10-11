package com.hafidzqode.myalarmmanager_dicodingp7;

import android.content.Context;
import android.content.SharedPreferences;

//todo 2
//Setelah itu kita akan membuat sebuah mekanisme penyimpanan sederhana dengan menggunakan SharedPreference.
//buat sebuah kelas java baru dengan nama AlarmPreference.
//klik kanan pada package project yang ada saat ini → new → Java Class.
public class AlarmPreference {
    private final String PREF_NAME = "AlarmPreference";
    private final String KEY_ONE_TIME_DATE = "oneTimeDate";
    private final String KEY_ONE_TIME_TIME = "oneTimeTime";
    private final String KEY_ONE_TIME_MESSAGE = "oneTimeMessage";
    private final String KEY_REPEATING_TIME = "repeatingTime";
    private final String KEY_REPEATING_MESSAGE = "repeatingMessage";
    private SharedPreferences mSharedPreference;
    private SharedPreferences.Editor editor;
    public AlarmPreference(Context context){
        mSharedPreference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreference.edit();
    }
    public void setOneTimeDate(String date){
        editor.putString(KEY_ONE_TIME_DATE, date);
        editor.commit();
    }
    public String getOneTimeDate(){
        return mSharedPreference.getString(KEY_ONE_TIME_DATE, null);
    }
    public void setOneTimeTime(String time){
        editor.putString(KEY_ONE_TIME_TIME, time);
        editor.commit();
    }
    public String getOneTimeTime(){
        return mSharedPreference.getString(KEY_ONE_TIME_TIME, null);
    }
    public void setOneTimeMessage(String message){
        editor.putString(KEY_ONE_TIME_MESSAGE, message);
        editor.commit();
    }
    public String getOneTimeMessage(){
        return mSharedPreference.getString(KEY_ONE_TIME_MESSAGE, null);
    }
    public void setRepeatingTime(String time){
        editor.putString(KEY_REPEATING_TIME, time);
        editor.commit();
    }
    public String getRepeatingTime(){
        return mSharedPreference.getString(KEY_REPEATING_TIME, null);
    }
    public void setRepeatingMessage(String message){
        editor.putString(KEY_REPEATING_MESSAGE, message);
        editor.commit();
    }
    public String getRepeatingMessage(){
        return mSharedPreference.getString(KEY_REPEATING_MESSAGE, null);
    }
    public void clear(){
        editor.clear();
        editor.commit();
    }
}
