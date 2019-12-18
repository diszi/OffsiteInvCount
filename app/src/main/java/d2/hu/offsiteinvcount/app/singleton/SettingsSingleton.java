package d2.hu.offsiteinvcount.app.singleton;

import android.content.Context;
import android.content.SharedPreferences;

import d2.hu.offsiteinvcount.util.UIConstans;

public class SettingsSingleton {


    private static final SettingsSingleton ourInstance = new SettingsSingleton();

    private Context context;
    private String fileConstant;
    private SharedPreferences sharedPreferences;

    private SettingsSingleton() {}

    public static SettingsSingleton getInstance() {
        return ourInstance;
    }

    public String getFileConstant() {
        return fileConstant;
    }

    public SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }


    private void setSharedPreferences(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    public String getUserName(){
        return sharedPreferences.getString(UIConstans.LOGGED_IN_USER,null);
    }


    public void init(Context context, String userName) {

        this.context = context;
        this.fileConstant = UIConstans.APP_NAME + userName;
        sharedPreferences = context.getSharedPreferences(fileConstant, Context.MODE_PRIVATE);
        setSharedPreferences(sharedPreferences);
        sharedPreferences.edit().putString(UIConstans.LOGGED_IN_USER, userName).commit();

    }
}
