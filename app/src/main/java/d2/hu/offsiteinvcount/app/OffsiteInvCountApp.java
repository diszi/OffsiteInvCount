package d2.hu.offsiteinvcount.app;

import android.app.Application;
import android.content.Context;

public class OffsiteInvCountApp  extends Application {

    private static Context appcontext;

    public static Context getAppContext(){
        return OffsiteInvCountApp.appcontext;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        OffsiteInvCountApp.appcontext = getApplicationContext();
    }
}
