package d2.hu.offsiteinvcount.ui.view.inventoryCount;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2.hu.offsiteinvcount.R;
import d2.hu.offsiteinvcount.ui.model.InventoryCount;
import d2.hu.offsiteinvcount.util.EnvironmentTool;

public class InventoryCountBookLinesAdapter extends RecyclerView.Adapter<InventoryCountBookLinesAdapter.InventoryCountBookViewHolder>   {


    private InventoryCountBookLinesActivity view;
    private List<InventoryCount.CountBookLine> countBook_lines = new ArrayList<>();
    private List<InventoryCount.CountBookLine> countBook_lines_unsorted=new ArrayList<>();


    InventoryCountBookLinesAdapter(InventoryCountBookLinesActivity view){
        this.view = view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setCountBookLines(InventoryCount countBook_item){
        this.countBook_lines.clear();
        this.countBook_lines_unsorted = countBook_item.getCountBookLineList();

        this.countBook_lines.addAll(EnvironmentTool.sortCountBookList(countBook_item.getCountBookLineList()));
        //this.countBook_lines.addAll(countBook_item.getCountBookLineList());

//        for (int  i=0;i<this.countBook_lines.size();i++){
//            System.out.println(" ITEM["+i+"] sorted = "+this.countBook_lines.get(i).getPartnumber()+" - "+this.countBook_lines.get(i).getBin());
//        }

        this.notifyDataSetChanged();


    }

    @Override
    public InventoryCountBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_inv_count_book_list_row,parent,false);
        return new InventoryCountBookLinesAdapter.InventoryCountBookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InventoryCountBookViewHolder holder, int position) {
        InventoryCount.CountBookLine countBookLine_items= countBook_lines.get(position);
        //System.out.println(" ITEM = "+countBookLine_items.getPartnumber()+"; position = "+position);
        holder.bind(countBookLine_items);
        holder.itemView.setOnClickListener(v -> view.loadCountBookLineDetailsDialog(countBookLine_items,position));
    }

    @Override
    public int getItemCount() {
        if (countBook_lines != null && countBook_lines.size() > 0) {
            return countBook_lines.size();
        } else {
            return 0;
        }

    }



    static class InventoryCountBookViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.countBook_item_partnr)
        TextView partnumber;
        @BindView(R.id.countBook_item_serialnr_batch)
        TextView serialnum_batch;
        @BindView(R.id.countBook_item_bin)
        TextView bin;

        @BindView(R.id.itemslayout)
        LinearLayout layout;
        @BindView(R.id.countBook_item_reconcile)
        ImageButton isReconcileButton;

        InventoryCountBookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(InventoryCount.CountBookLine countBookLine_item) {
            if (countBookLine_item.getPhysicalCount().isEmpty()){
               // System.out.println(" ----> leltárra vár --> szürke" );
                layout.setBackgroundResource(R.color.colorLightGray);
            }else{
                //System.out.println(" ----> volt leltározva");
                if (countBookLine_item.getPhysicalCount().equals(countBookLine_item.getCurrentBalance())){
                   // System.out.println(" physical count == cur balance --> zöld");
                    layout.setBackgroundResource(R.color.colorMatch);
                }else{
                   // System.out.println(" physical count != cur balance --> piros");
                    layout.setBackgroundResource(R.color.colorWait);
                }
                if (countBookLine_item.isReconcile()){
                   // System.out.println(" ---> isRECONCILE ==> szurke");
                    layout.setBackgroundResource(R.color.colorLightGray);
                }
            }

            partnumber.setText(countBookLine_item.getPartnumber());
           // equipment.setText(countBookLine_item.getEquipment());
            if (countBookLine_item.isRotable()){
                serialnum_batch.setText(countBookLine_item.getSerialnumber());
            }else{
                serialnum_batch.setText(countBookLine_item.getBatch());
            }

            bin.setText(countBookLine_item.getBin());

            if (countBookLine_item.isReconcile()) {
                isReconcileButton.setVisibility(View.VISIBLE);
            } else {
                isReconcileButton.setVisibility(View.INVISIBLE);
            }


        }

    }

}
