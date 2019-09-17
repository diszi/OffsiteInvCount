package d2.hu.offsiteinvcount.app;

public class CustomerProperties {

    public static String LOGIN_URL =null;
    public static String GET_INVENTORY_COUNT=null;
    public static String UPDATE_INVENTORY_COUNT =null;


    public static void setURLs(String ipAddress){
        LOGIN_URL ="http://"+ ipAddress+"/maxrest/rest/login";
        GET_INVENTORY_COUNT="http://"+ ipAddress+"/maxrest/rest/os/ACE_INVCOUNT?_dropnulls=0&STATUS=INPRG";
        UPDATE_INVENTORY_COUNT ="http://"+ ipAddress+"/maxrest/rest/os/ACE_INVCOUNT_UPDATE";

    }
}
