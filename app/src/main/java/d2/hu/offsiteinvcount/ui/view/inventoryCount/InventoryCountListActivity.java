package d2.hu.offsiteinvcount.ui.view.inventoryCount;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2.hu.offsiteinvcount.R;
import d2.hu.offsiteinvcount.app.singleton.SettingsSingleton;
import d2.hu.offsiteinvcount.ui.model.InventoryCount;
import d2.hu.offsiteinvcount.ui.view.MainActivity;
import d2.hu.offsiteinvcount.ui.view.base.BaseActivity;
import d2.hu.offsiteinvcount.ui.view.base.BaseViewPresenter;
import d2.hu.offsiteinvcount.ui.view.base.RemoteCallBack;


public class InventoryCountListActivity extends BaseActivity implements InventoryCounting.View {


    public static int INVENTORY_COUNT_REQUEST_CODE = 0;


    @BindView(R.id.actInventoryCountList_recyclerView)
    RecyclerView compRecyclerView;
    @BindView(R.id.actInventoryCountList_swipeRefreshLayout)
    SwipeRefreshLayout compSwipeRefreshLayout;
    @BindView(R.id.actInventoryCountList_progressBar)
    ProgressBar compProgressBar;
    @BindView(R.id.userName)
    TextView username;
    @BindView(R.id.syncDate)
    TextView actualDate;
    @BindView(R.id.emptyText)
    TextView emptyText;

    @BindView(R.id.actInventoryCountList_toolbar)
    Toolbar compToolbar;

    private InventoryCountListAdapter adapter;
    private InventoryCountPresenter presenter;
    private List<InventoryCount> inventoryCountList;
    private String syncDateString;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_inventory_count_list);
        ButterKnife.bind(this);
        Log.i("------------------>", "Start Activity - InventoryCountListActivity");

        this.setupRecyclerView();

        setSyncDate();
        actualDate.setText(syncDateString);
        username.setText(SettingsSingleton.getInstance().getUserName());

        compToolbar.setTitle("Count Books");
        compToolbar.setTitleTextColor(Color.WHITE);

        presenter = new InventoryCountPresenter(this);

        getInventoryCountList();

        compSwipeRefreshLayout.setOnRefreshListener(() -> {
            compSwipeRefreshLayout.setRefreshing(false);
            getInventoryCountListRefresh();


        });



    }

    private void getInventoryCountList(){
        presenter.getInventoryCountList(new RemoteCallBack<List<InventoryCount>>() {
            @Override
            public void onSucces(List<InventoryCount> object) {
                adapter.setInventoryCountList(object);
                hideLoading();
                emptyText.setVisibility(object.isEmpty()? View.VISIBLE: View.GONE);
            }
        });
    }

    private void getInventoryCountListRefresh(){
        presenter.getInventoryCountList(new RemoteCallBack<List<InventoryCount>>() {
            @Override
            public void onSucces(List<InventoryCount> object) {
                adapter.setInventoryCountListRefresh(object);
                hideLoading();
                emptyText.setVisibility(object.isEmpty()? View.VISIBLE: View.GONE);
            }
        });
    }

    private void setupRecyclerView() {
        Context context = getApplicationContext();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);

        this.adapter = new InventoryCountListAdapter(this);
        this.compRecyclerView.setLayoutManager(layoutManager);
        this.compRecyclerView.setAdapter(this.adapter);
    }

//    @Override
//    public void loadCountBookList(List<InventoryCount> inventoryCountList) {
//        this.inventoryCountList = inventoryCountList;
//        adapter.setInventoryCountList(this.inventoryCountList);
//    }


//    public void loadCountBookLineList(InventoryHolder holder) {
//        Intent intent = new Intent(this,InventoryCountBookLinesActivity.class);
//        intent.putExtra(InventoryHolder.SERIALIZABLE_NAME,holder);
//        startActivity(intent);
//
//    }

    @Override
    public void loadCountBookLineList(InventoryCount invCount) {
        Log.d("---------------------> ","Launch bookline list activity for countBook "+invCount.getCountbook());
        Intent intent = new Intent(this,InventoryCountBookLinesActivity.class);
        intent.putExtra(InventoryCount.SERIALIZABLE_NAME,invCount);
        startActivity(intent);
    }


    @Override
    protected BaseViewPresenter getBasePresenter() {
        return presenter;
    }

    @Override
    public void showLoading() {
        compProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
            compProgressBar.setVisibility(View.GONE);
    }



    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    /**
     * Set actual date - real-time synchronization
     */
    public void setSyncDate(){
        Thread t = new Thread(){
            public void run(){
                try{
                    while(!isInterrupted()){
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
                                syncDateString = sdf.format(date);
                                actualDate.setText(syncDateString);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

    }




}

