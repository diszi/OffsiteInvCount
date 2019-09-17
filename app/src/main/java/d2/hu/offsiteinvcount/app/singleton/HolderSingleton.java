package d2.hu.offsiteinvcount.app.singleton;

import android.content.Context;

public class HolderSingleton {

    private static final HolderSingleton ourInstance = new HolderSingleton();
    private Context context;


    private String serverIPaddress=null;


    public void setContext(Context context) {
        this.context = context;
    }

    public static HolderSingleton getInstance() {
        return ourInstance;
    }


    private HolderSingleton() {
    }


    public String getServerIPaddress() {
        return serverIPaddress;
    }

    public void setServerIPaddress(String serverIPaddress) {
        this.serverIPaddress = serverIPaddress;


    }

}
