package d2.hu.offsiteinvcount.ui.view.inventoryCount;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.List;

import d2.hu.offsiteinvcount.app.CustomerProperties;
import d2.hu.offsiteinvcount.app.singleton.SettingsSingleton;
import d2.hu.offsiteinvcount.ui.model.InventoryCount;
import d2.hu.offsiteinvcount.ui.view.base.BasePresenter;
import d2.hu.offsiteinvcount.ui.view.base.RemoteCallBack;
import d2.hu.offsiteinvcount.util.EntityMapper;
import d2.hu.offsiteinvcount.util.EnvironmentTool;
import d2.hu.offsiteinvcount.util.HttpCall;
import d2.hu.offsiteinvcount.util.HttpRequestAsyncTask;


public class InventoryCountPresenter extends BasePresenter implements InventoryCounting.Presenter {


    private InventoryCounting.View view;
    private List<InventoryCount> inventoryCountList= null;
    private InventoryCount inventoryCountBook_item = null;
    private boolean update_success=false;
    private StringBuffer tempURL = new StringBuffer();

    InventoryCountPresenter(InventoryCounting.View view){
        this.view = view;
    }

    public InventoryCountPresenter(){}




    @SuppressLint("StaticFieldLeak")
    @Override
    public void getInventoryCountList(RemoteCallBack<List<InventoryCount>> remoteCallBack) {
        Log.d("-----------------> ","REST GET : Inventory count list");
        view.showLoading();

        HttpCall httpCall = new HttpCall();
        httpCall.setMethod(HttpCall.RequestMethod.GET);
        httpCall.setUrl(CustomerProperties.GET_INVENTORY_COUNT);
        new HttpRequestAsyncTask(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (response != null){
                    inventoryCountList = EntityMapper.transformInventoryCountList(response);
                    remoteCallBack.onSucces(inventoryCountList);
                }else{
                    Log.e("------------------>" ,"load Inventory Count List - Error");
                }
            }
        }.execute(httpCall);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void getInvCountBookLinesList(String countBookNumber, InventoryCountBookLinesActivity activity, RemoteCallBack<InventoryCount> remoteCallBack){
        Log.d("-----------------> ","REST GET : Inventory count book line list");
        activity.showLoading();

        HttpCall httpCall = new HttpCall();
        httpCall.setMethod(HttpCall.RequestMethod.GET);
        httpCall.setUrl(CustomerProperties.GET_INVENTORY_COUNT+"&COUNTBOOKNUM="+countBookNumber);
        new HttpRequestAsyncTask(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (response != null){
                    inventoryCountBook_item = EntityMapper.transformInventoryCountBookItem(response);
                    if (inventoryCountBook_item!=null){
                        remoteCallBack.onSucces(inventoryCountBook_item);
                    }else {
                        System.out.println(" >>> NINCS LINE a count-book-hoz");
                    }
                }else{
                    Log.e("------------------>" ,"load History - Error");
                }
            }
        }.execute(httpCall);
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void getInvCountBookLinesList_2(String countBookNumber, RemoteCallBack<InventoryCount> remoteCallBack){
        Log.d("-----------------> ","REST GET : Inventory count book line list 2");


        HttpCall httpCall = new HttpCall();
        httpCall.setMethod(HttpCall.RequestMethod.GET);
        httpCall.setUrl(CustomerProperties.GET_INVENTORY_COUNT+"&COUNTBOOKNUM="+countBookNumber);
        new HttpRequestAsyncTask(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (response != null){
                    inventoryCountBook_item = EntityMapper.transformInventoryCountBookItem(response);
                    if (inventoryCountBook_item!=null){
                        remoteCallBack.onSucces(inventoryCountBook_item);
                    }else {
                        System.out.println(" >>> NINCS LINE a count-book-hoz");
                    }
                }else{
                    Log.e("------------------>" ,"load History - Error");
                }
            }
        }.execute(httpCall);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void updateInvCountBookLine(String phyCount, String countBookID, int line_number, InventoryCount.CountBookLine countBookItem, RemoteCallBack<Boolean> remoteCallBack) {
        Log.d("------------------>" ,"REST PUT : Inventory count book line UPDATE ==> LOTNUM="+countBookItem.getBatch());

        tempURL.delete(0,tempURL.length());

       // System.out.println(" ---> rotable = "+countBookItem.isRotable()+"; equipment="+countBookItem.getEquipment()+"; ID="+countBookItem.getId());

        //if (!countBookItem.getEquipment().equals("")){
        if (countBookItem.isRotable()){
            //rotable
            tempURL.append(countBookID+"?PLUSTCBLINES."+line_number+".ITEMNUM="+countBookItem.getPartnumber()+
                    "&PLUSTCBLINES."+line_number+".TOOL=0&PLUSTCBLINES."+line_number+".ASSETNUM="+countBookItem.getEquipment()+
                    "&PLUSTCBLINES."+line_number+".PHYSCNT="+phyCount+
                    "&PLUSTCBLINES."+line_number+".PHYSCNTBY="+ SettingsSingleton.getInstance().getUserName());
        }else{
            //non rotable
            tempURL.append(countBookID+"?PLUSTCBLINES."+line_number+".ITEMNUM="+countBookItem.getPartnumber()+
                    "&PLUSTCBLINES."+line_number+".TOOL=0"+
                    "&PLUSTCBLINES."+line_number+".PHYSCNT="+phyCount+
                    "&PLUSTCBLINES."+line_number+".PHYSCNTBY="+ SettingsSingleton.getInstance().getUserName()+
                    "&PLUSTCBLINES."+line_number+".BINNUM="+countBookItem.getBin()+
                    "&PLUSTCBLINES."+line_number+".LOTNUM="+countBookItem.getBatch());
        }


        if (Double.parseDouble(phyCount) == Double.parseDouble(countBookItem.getCurrentBalance())){
            tempURL.append("&PLUSTCBLINES."+line_number+".MATCH=1");
        }else{
            tempURL.append("&PLUSTCBLINES."+line_number+".MATCH=0");
        }

        String encodedURL = EnvironmentTool.encodeStringtoUTF8(tempURL.toString());
        HttpCall httpCall = new HttpCall();
        httpCall.setMethod(HttpCall.RequestMethod.PUT);
        httpCall.setUrl(CustomerProperties.UPDATE_INVENTORY_COUNT+"/"+encodedURL);
        new HttpRequestAsyncTask(){
            @Override
            public void onResponse(String response) {
                super.onResponse(response);

                if (response != null){
                    update_success = true;
                }else{
                    update_success = false;
                    Log.e("------------------>" ,"update Inventory Count Book line - Error");
                }
                remoteCallBack.onSucces(update_success);
            }
        }.execute(httpCall);

    }


    @Override
    public void onDestroy() {
        super.disposeAll();
    }




}
