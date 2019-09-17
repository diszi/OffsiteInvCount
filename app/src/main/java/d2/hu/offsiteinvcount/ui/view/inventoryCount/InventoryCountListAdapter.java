package d2.hu.offsiteinvcount.ui.view.inventoryCount;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2.hu.offsiteinvcount.R;
import d2.hu.offsiteinvcount.ui.model.InventoryCount;

public class InventoryCountListAdapter extends RecyclerView.Adapter<InventoryCountListAdapter.InventoryCountViewHolder> {

    private InventoryCounting.View view;
    private List<InventoryCount> inventoryCountList = new ArrayList<>();


    InventoryCountListAdapter(InventoryCounting.View view){
        this.view = view;
    }


    public void setInventoryCountList(List<InventoryCount> inventoryCountList){
        Log.d("----------->","Adapter - Inventory Count list");
        this.inventoryCountList.clear();
        this.inventoryCountList.addAll(inventoryCountList);
        this.notifyDataSetChanged();
//        view.compEmpty.setVisibility(activeInboundList.isEmpty()?View.VISIBLE:View.GONE);
    }

    public void setInventoryCountListRefresh(List<InventoryCount> invCountList){
        Log.d("----------->","Adapter - Inventory Count list REFRESH");
        this.inventoryCountList = invCountList;
        this.notifyDataSetChanged();
    }


    @Override
    public InventoryCountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_inventory_count_list_row,parent,false);
        return new InventoryCountListAdapter.InventoryCountViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InventoryCountViewHolder holder, int position) {
        InventoryCount countBook= inventoryCountList.get(position);
        holder.bind(countBook);

        holder.itemView.setOnClickListener((v) ->{
          //  Log.i("------------->","onClick on CountBook Item - pos:"+position);
//            view.loadCountBookLineList(new InventoryHolder(countBook,position));
            view.loadCountBookLineList(countBook);

        });

    }

    @Override
    public int getItemCount() {
        if (inventoryCountList != null && inventoryCountList.size() > 0) {
            return inventoryCountList.size();
        } else {
            return 0;
        }
    }

    static class InventoryCountViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.countbook_number)
        TextView invCount_countBook;
        @BindView(R.id.textView5)
        TextView invCount_status;
        @BindView(R.id.textView4)
        TextView invCount_storeroom;
//        @BindView(R.id.actInventoryCount_modifyBtn)
//        ImageButton modifyBtn;


        public InventoryCountViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


        public void bind(InventoryCount inventoryCount){
            invCount_countBook.setText(inventoryCount.getCountbook());
            invCount_status.setText(inventoryCount.getStatus());
            invCount_storeroom.setText(inventoryCount.getStoreroom());


        }


    }

}
