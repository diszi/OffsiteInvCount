package d2.hu.offsiteinvcount.ui.view.inventoryCount;

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

public class InventoryCountBookLinesAdapter extends RecyclerView.Adapter<InventoryCountBookLinesAdapter.InventoryCountBookViewHolder>   {


    private InventoryCountBookLinesActivity view;
    private InventoryCount countBook;
    private List<InventoryCount.CountBookLine> countBook_lines = new ArrayList<>();


    public InventoryCountBookLinesAdapter(InventoryCountBookLinesActivity view){
        this.view = view;
    }

    public void setCountBookLines(InventoryCount countBook_item){
       // System.out.println(" ---> InventoryCountBookLinesAdapter.setCountBookLines("+countBook_item.getCountbook()+"); list size = "+countBook_item.getCountBookLineList().size());
//        this.countBook = countBook_item;
        this.countBook = countBook_item;

        this.countBook_lines.clear();
        this.countBook_lines.addAll(countBook_item.getCountBookLineList());

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
        holder.bind(countBookLine_items);
        //System.out.println(" ---------------> line ["+position+"] = "+countBookLine_items.getPartnumber()+" - "+countBookLine_items.getEquipment()+" - date="+countBookLine_items.getCountedDate()+" - by="+countBookLine_items.getCountedBy());

        System.out.println(" --> CountBook line item = "+countBookLine_items.getPartnumber()+"; "+countBookLine_items.getEquipment());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*System.out.println(" ---> onClick on CountBook line item : pos="+position+"; details=["+countBookLine_items.getPartnumber()+"; "+countBookLine_items.getId()+
                "; countBookID = "+countBook.getCountBookID()+"; status = "+countBook.getStatus());*/
                //System.out.println(" >>> SHOW INV COUNT BOOK ITEM DETAILS IN DIALOG");
//                view.loadCountBookLineDetailsDialog(countBookLine_items,countBook.getCountBookID(),position,countBook.getStatus());
                view.loadCountBookLineDetailsDialog(countBookLine_items,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        //System.out.println(" getItemCount() = "+countBook_lines.size());
        if (countBook_lines != null && countBook_lines.size() > 0) {
            return countBook_lines.size();
        } else {
            return 0;
        }

    }



    static class InventoryCountBookViewHolder extends RecyclerView.ViewHolder {


//        @BindView(R.id.countBook_item_reconcile)
//        TextView reconclie;
        @BindView(R.id.countBook_item_partnr)
        TextView partnumber;
        @BindView(R.id.countBook_item_assetnr)
        TextView equipment;
//        @BindView(R.id.reconcile_checkbox)
//        CheckBox reconcile_checkbox;
        @BindView(R.id.itemslayout)
        LinearLayout layout;
        @BindView(R.id.countBook_item_reconcile)
        ImageButton isReconcileButton;

        public InventoryCountBookViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(InventoryCount.CountBookLine countBookLine_item){
//            System.out.println(" Count book lines item ["+countBookLine_item.isReconcile()+"; "
//                    +countBookLine_item.getPartnumber()+"; "+countBookLine_item.getEquipment()+"; "+countBookLine_item.getPhysicalCount()+"]");
            partnumber.setText(countBookLine_item.getPartnumber());
            equipment.setText(countBookLine_item.getEquipment());
            if (countBookLine_item.isReconcile()){
                isReconcileButton.setVisibility(View.VISIBLE);
            }else{
                isReconcileButton.setVisibility(View.INVISIBLE);
            }

//            if (!countBookLine_item.isReconcile()){
//                layout.setBackgroundResource(R.color.colorLightGray);
//            }else{
//                layout.setBackgroundResource(R.color.colorDarkGray);
//            }



        }


    }

}
