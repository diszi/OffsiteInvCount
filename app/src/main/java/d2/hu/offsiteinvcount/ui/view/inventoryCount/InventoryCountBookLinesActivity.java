package d2.hu.offsiteinvcount.ui.view.inventoryCount;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2.hu.offsiteinvcount.R;
import d2.hu.offsiteinvcount.ui.model.InventoryCount;
import d2.hu.offsiteinvcount.ui.scan.BaseScannerActivity;
import d2.hu.offsiteinvcount.ui.view.base.BaseViewPresenter;
import d2.hu.offsiteinvcount.ui.view.base.RemoteCallBack;
import d2.hu.offsiteinvcount.ui.view.component.InvCountBookLineChooseDialog;
import d2.hu.offsiteinvcount.ui.view.component.InvCountBookLineDialog;
import d2.hu.offsiteinvcount.util.EnvironmentTool;


public class InventoryCountBookLinesActivity extends BaseScannerActivity {



    @BindView(R.id.actInvCountBookLines_recyclerView)
    RecyclerView compRecyclerView;
    @BindView(R.id.actInvCountBookLines_swipeRefreshLayout)
    SwipeRefreshLayout compSwipeRefreshLayout;
    @BindView(R.id.invCount_countbook_nr)
    TextView countbook_nr;
    @BindView(R.id.invCount_status)
    TextView status;
    @BindView(R.id.invCount_storeroom)
    TextView storeroom;
    @BindView(R.id.loading_progessBar)
    ProgressBar loading_progressBar;


    private InventoryCount invCountBook ;

    private InventoryCountBookLinesAdapter adapter;
    private InventoryCountPresenter presenter;
    private InvCountBookLineDialog detailsDialog;
    private InvCountBookLineChooseDialog chooseDialog;


    private List<InventoryCount.CountBookLine> lineList = new ArrayList<>();
    private Map<Integer,InventoryCount.CountBookLine> lineMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        Log.i("------------------>", "Start Activity - InventoryCountBookLinesActivity");



        setContentView(R.layout.activity_inv_count_book_list);
        ButterKnife.bind(this);


//        InventoryHolder holder =  (InventoryHolder)getIntent().getExtras().getSerializable(InventoryHolder.SERIALIZABLE_NAME);
//        InventoryCount countBook = holder.getEntity_countBook();
        InventoryCount countBook = (InventoryCount)getIntent().getExtras().getSerializable(InventoryCount.SERIALIZABLE_NAME);

        String countBook_number = countBook.getCountbook();


        Log.d("------------------>","Selected item : [nr="+countBook_number+"; ID="+countBook.getCountBookID()+"; status="+countBook.getStatus()+
                "; storeroom="+countBook.getStoreroom()+"]");
        //System.out.println("\t[countBooknr="+countBook.getCountbook()+"; line size = "+countBook.getCountBookLineList().size()+"]");


        this.setupRecyclerView();

        storeroom.setText(countBook.getStoreroom());
        status.setText(countBook.getStatus());
        countbook_nr.setText(countBook.getCountbook());


        presenter = new InventoryCountPresenter();

        detailsDialog = new InvCountBookLineDialog();
        chooseDialog = new InvCountBookLineChooseDialog();


        getCountBookLinesList(countBook_number);


    }

    private void setupRecyclerView() {
        Context context = getApplicationContext();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);

        this.adapter = new InventoryCountBookLinesAdapter(this);
        this.compRecyclerView.setLayoutManager(layoutManager);
        this.compRecyclerView.setAdapter(this.adapter);
    }


    private void getCountBookLinesList(String countbook_number){
        presenter.getInvCountBookLinesList(countbook_number,this, new RemoteCallBack<InventoryCount>() {
                    @Override
                    public void onSucces(InventoryCount object) {
                        invCountBook = object;
                        System.out.println("COUNTBOOK lines size = "+invCountBook.getCountBookLineList().size());

                        adapter.setCountBookLines(invCountBook);
                        hideLoading();
                    }
                });
    }


    public void reloadLines(String counbook_number)
    {
        getCountBookLinesList(counbook_number);
    }

    public void loadCountBookLineDetailsDialog(InventoryCount.CountBookLine countBookLine,int position){
        Log.d("------------------>","loadCountBookLineDetailsDialog : [line item = <"+countBookLine.getPartnumber()+", "+countBookLine.getEquipment()+"> ; countbookNR="+
        invCountBook.getCountbook()+"; position="+position+"]");
//        System.out.println(" ---> loadCountBookLineDetailsDialog = lineITEM ["+countBookLine.getPartnumber()+" - "+countBookLine.getEquipment()+"]; position = "
//                +position+"; countBook number = "+invCountBook.getCountbook()+"; countDate = "+countBookLine.getCountedDate()+"; countby="+countBookLine.getCountedBy()+"]");

        FragmentManager fm = this.getFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putSerializable(InvCountBookLineDialog.SERIALIZABLE_NAME,countBookLine);
        bundle.putSerializable(InvCountBookLineDialog.COUNT_BOOK,invCountBook);
        bundle.putSerializable(InvCountBookLineDialog.ITEM_POSITION,position);
        detailsDialog.setArguments(bundle);
        detailsDialog.show(fm,"ShowHistoryDetails");
    }

    @Override
    protected BaseViewPresenter getBasePresenter() {
        return presenter;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, InventoryCountListActivity.class);
        startActivity(intent);
    }



    @Override
    public void onDataScanned(String scanData) {
        Log.d("------------->","onDataScanned() : "+scanData.toString());

        String valid_partnum_format=null;
        valid_partnum_format = EnvironmentTool.scannedDataVerification_partnumber(scanData.toString());
        if (valid_partnum_format!=null){
//            partnumField.setText(valid_partnum_format);
//            loadPickingItemDetails(valid_partnum_format);

            Map<Integer,InventoryCount.CountBookLine> countLineMap = getLineMap(valid_partnum_format);

            System.out.println(" MAP SIZE = "+countLineMap.size());
            if (countLineMap.size() == 0){
                //  onError();
                //System.out.println(" ---> This part number not exist in Count Book");
                Toast.makeText(this,"Part number "+valid_partnum_format+" not exist in Count Book", Toast.LENGTH_SHORT).show();
            }
            if (countLineMap.size() == 1 ){
                //System.out.println(" == countLineMap.get(0) = "+countLineMap.get(0));
                Map.Entry<Integer,InventoryCount.CountBookLine> entry = countLineMap.entrySet().iterator().next();
                Integer key= entry.getKey();
                InventoryCount.CountBookLine value=entry.getValue();
                System.out.println(" ----> key="+key+"; value="+value);

                loadCountBookLineDetailsDialog(value,key);
            }

            if (countLineMap.size() > 1){
                loadCountBookLineChooseDialog(countLineMap);
            }

        }else{
            Toast.makeText(this,"Invalid barcode/QRcode format", Toast.LENGTH_SHORT).show();
        }

        System.out.println(" ------> valid part num format = "+valid_partnum_format);




    }

//    private List<InventoryCount.CountBookLine> getLineList(String partnumber){
    private Map<Integer,InventoryCount.CountBookLine> getLineMap(String partnumber){

        //clearLineList();
        clearLineMap();

        for(int i=0;i<invCountBook.getCountBookLineList().size();i++){
          //  System.out.println(" ---> line items : "+invCountBook.getCountBookLineList().get(i).getPartnumber());
            System.out.println(" getLineMap ["+invCountBook.getCountBookLineList().get(i).getPartnumber() +" == ? "+partnumber);

            if (invCountBook.getCountBookLineList().get(i).getPartnumber().equals(partnumber)){
                //lineList.add(invCountBook.getCountBookLineList().get(i));

                lineMap.put(i,invCountBook.getCountBookLineList().get(i));

            }
        }

        return lineMap;

    }




//   private void loadCountBookLineChooseDialog(List<InventoryCount.CountBookLine> list){
    public void loadCountBookLineChooseDialog(Map<Integer,InventoryCount.CountBookLine> lineMap){
        System.out.println(" ---> loadCountBookLineChooseDialog = lineMap size ="+lineMap.size());
       FragmentManager fm = this.getFragmentManager();
       Bundle bundle = new Bundle();
       bundle.putSerializable(InventoryCount.CountBookLine.SERIALIZABLE_NAME,(HashMap<Integer,InventoryCount.CountBookLine>)lineMap);
       chooseDialog.setArguments(bundle);
       chooseDialog.show(fm,"ShowHistoryDetails");
   }

//    private void clearLineList(){
//        lineList.clear();
//    }

    private void clearLineMap(){
        lineMap.clear();
    }



    @Override
    public void onStatusUpdate(String scanStatus) {

    }

    @Override
    public void onError() {
       // System.out.println("ERROR in scanning...");
        Toast.makeText(this,"Error in scanning...", Toast.LENGTH_SHORT).show();
    }

    public void showLoading() {
        loading_progressBar.setVisibility(View.VISIBLE);
    }


    public void hideLoading() {
        loading_progressBar.setVisibility(View.GONE);
    }

}
