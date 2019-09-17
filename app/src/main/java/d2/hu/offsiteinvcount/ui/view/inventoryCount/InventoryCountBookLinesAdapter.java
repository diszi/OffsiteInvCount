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
    private List<InventoryCount.CountBookLine> countBook_lines = new ArrayList<>();


    InventoryCountBookLinesAdapter(InventoryCountBookLinesActivity view){
        this.view = view;
    }

    public void setCountBookLines(InventoryCount countBook_item){
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
        @BindView(R.id.countBook_item_assetnr)
        TextView equipment;
        @BindView(R.id.itemslayout)
        LinearLayout layout;
        @BindView(R.id.countBook_item_reconcile)
        ImageButton isReconcileButton;

        InventoryCountBookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(InventoryCount.CountBookLine countBookLine_item) {
            partnumber.setText(countBookLine_item.getPartnumber());
           // equipment.setText(countBookLine_item.getEquipment());
            equipment.setText(countBookLine_item.getSerialnumber());
            if (countBookLine_item.isReconcile()) {
                isReconcileButton.setVisibility(View.VISIBLE);
            } else {
                isReconcileButton.setVisibility(View.INVISIBLE);
            }
        }

    }

}
