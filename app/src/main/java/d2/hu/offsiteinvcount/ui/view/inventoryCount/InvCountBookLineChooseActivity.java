package d2.hu.offsiteinvcount.ui.view.inventoryCount;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2.hu.offsiteinvcount.R;
import d2.hu.offsiteinvcount.ui.model.InventoryCount;
import d2.hu.offsiteinvcount.ui.view.base.RemoteCallBack;
import d2.hu.offsiteinvcount.ui.view.component.InvCountBookLineDetailsDialog;

public class InvCountBookLineChooseActivity extends AppCompatActivity {

    private Map<Integer,InventoryCount.CountBookLine> lineMap = new HashMap<>();

    private Map<Integer,InventoryCount.CountBookLine> tempMap = new HashMap<>();

    private InvCountBookChooseLineAdapter adapter;
    private InventoryCountPresenter presenter;
   private InventoryCountBookLinesActivity activity;

    @BindView(R.id.act_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.actTitle_dialog)
    TextView title;

    private InvCountBookLineDetailsDialog detailsDialog;
    private InventoryCount invCountBook ;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choose_countbookline);
        ButterKnife.bind(this);
        Log.i("------------------>", "Start Activity - InvCountBookLineChooseActivity");

        presenter = new InventoryCountPresenter();
        detailsDialog = new InvCountBookLineDetailsDialog();

        lineMap = (HashMap<Integer,InventoryCount.CountBookLine>)getIntent().getExtras().getSerializable(InventoryCount.CountBookLine.SERIALIZABLE_NAME);
        invCountBook = (InventoryCount)getIntent().getExtras().getSerializable(InventoryCount.SERIALIZABLE_NAME);


        this.setupRecyclerView();

        lineMap.forEach((Integer key, InventoryCount.CountBookLine value) ->{
           // System.out.println(" Key = "+key+"; value = "+value.getPartnumber()+" & "+value.getBatch()+" & "+value.getSerialnumber());
            if (value.isRotable()){
               // System.out.println(" ---> SHOW SERIAL NUMBER : key="+key+"; value="+value.getSerialnumber());
                title.setText(getResources().getString(R.string.title_chooseserialnum));

            }else{
                //System.out.println(" ---> SHOW BATCH : key="+key+"; value="+value.getBatch());
                title.setText(getResources().getString(R.string.title_choosebatch));

            }
        });

    }


    private void setupRecyclerView() {
        Context context = getApplicationContext();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        this.adapter = new InvCountBookChooseLineAdapter(lineMap,this);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(this.adapter);

    }



    public void reloadCountBook(String partnr, InventoryCount.CountBookLine line){
        //1
        //System.out.println("---> reload Count Book : partnr="+partnr+"; line = ["+line.getBatch()+"; "+line.getSerialnumber()+"]");
        tempMap.clear();

        presenter.getInvCountBookLinesList_2(invCountBook.getCountbook(),new RemoteCallBack<InventoryCount>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSucces(InventoryCount inventory_count_book) {
               for (int i=0;i<inventory_count_book.getCountBookLineList().size();i++){
                    if (inventory_count_book.getCountBookLineList().get(i).getPartnumber().equals(partnr)){
                        //2
                        //System.out.println(" PARTNR ==> "+partnr+"; pos="+i);
                      //  if (line.isRotable()){
                            //if (inventory_count_book.getCountBookLineList().get(i).getSerialnumber().equals(line.getSerialnumber())){
                               // System.out.println(" SN = "+line.getSerialnumber());

//                                tempMap.put(i,line);
                           // }
                       // }else{
                         //   if (inventory_count_book.getCountBookLineList().get(i).getBatch().equals(line.getBatch())){
                                //System.out.println(" BATCH = "+line.getBatch());

//                                tempMap.put(i,line);
                          //  }
                       // }
                        tempMap.put(i,inventory_count_book.getCountBookLineList().get(i));
                        //lineMap.put(i,inventory_count_book.getCountBookLineList().get(i));
                    }
                }
               // System.out.println(" TEMP MAP size = "+tempMap.size());
                adapter.setCountBookLines_values(tempMap);

            }
        });
    }

    public void loadLineDetailsDialog(InventoryCount.CountBookLine countBookLine,int position){
        System.out.println(" -------> load Count Book line DETAILS : from CHOOSE activity");

        FragmentManager fm = this.getFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putSerializable(InvCountBookLineDetailsDialog.SERIALIZABLE_NAME,countBookLine);
        bundle.putSerializable(InvCountBookLineDetailsDialog.COUNT_BOOK,invCountBook);
        bundle.putSerializable(InvCountBookLineDetailsDialog.ITEM_POSITION,position);
        detailsDialog.setArguments(bundle);
        detailsDialog.show(fm,"ShowHistoryDetails");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //System.out.println("ChooseActivity onDestroy");

    }



       // @Override
    //public void onBackPressed() {
    //    System.out.println(" --> 1 onBackPress - App context="+ OffsiteInvCountApp.getmContext());
        //--> 1 onBackPress : d2.hu.offsiteinvcount.ui.view.inventoryCount.InvCountBookLineChooseActivity@faf97cf; ---> android.app.ContextImpl@71ee55c
        //               getIntent = Intent { flg=0x10000000 cmp=d2.hu.offsiteinvcount/.ui.view.inventoryCount.InvCountBookLineChooseActivity (has extras) }

        //super.onBackPressed();



//        Intent intent = new Intent(this,InventoryCountBookLinesActivity.class);
//        intent.putExtra(InventoryCount.SERIALIZABLE_NAME,invCountBook);
//        startActivity(intent);


//        this.finish();
//        finish();
//        startActivity(getIntent());
      //  ((InventoryCountBookLinesActivity)getBaseContext()).reloadLines(invCountBook.getCountbook());
      //  System.out.println(" --> 2 onBackPress : "+this+"; "+getBaseContext());

    //}


}
