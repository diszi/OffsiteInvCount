package d2.hu.offsiteinvcount.ui.view.component;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2.hu.offsiteinvcount.R;
import d2.hu.offsiteinvcount.ui.model.InventoryCount;
import d2.hu.offsiteinvcount.ui.view.inventoryCount.InvCountBookChooseLineAdapter;
import d2.hu.offsiteinvcount.ui.view.inventoryCount.InventoryCountBookLinesActivity;

public class InvCountBookLineChooseDialog  extends DialogFragment {

    private Map<Integer,InventoryCount.CountBookLine> lineMap = new HashMap<>();

    private InvCountBookChooseLineAdapter adapter;
    private InventoryCountBookLinesActivity activity;

    @BindView(R.id.act_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.actTitle_dialog)
    TextView title;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(" -------------->","Start InvCountBookLineChooseDialog");


        if (getArguments()!=null){
            lineMap = (HashMap<Integer,InventoryCount.CountBookLine>)getArguments().getSerializable(InventoryCount.CountBookLine.SERIALIZABLE_NAME);
        }

        this.activity= (InventoryCountBookLinesActivity) getActivity();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View contentView = inflater.inflate(R.layout.dialog_choose_countbookline, container, false);

        ButterKnife.bind(this, contentView);
        this.setupRecyclerView();

        lineMap.forEach((Integer key, InventoryCount.CountBookLine value) ->{
            if (value.isRotable()){
                //System.out.println(" ---> SHOW SERIAL NUMBER");
                title.setText(getResources().getString(R.string.title_chooseserialnum));

            }else{
               // System.out.println(" ---> SHOW BATCH");
                title.setText(getResources().getString(R.string.title_choosebatch));

            }
        });
        return contentView;
    }

    private void setupRecyclerView() {
        Context context = getActivity().getApplicationContext();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
       // this.adapter = new InvCountBookChooseLineAdapter(lineMap,activity,this);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(this.adapter);

    }

}
