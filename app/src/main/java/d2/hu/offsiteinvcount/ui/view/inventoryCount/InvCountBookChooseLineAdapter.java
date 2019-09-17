package d2.hu.offsiteinvcount.ui.view.inventoryCount;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2.hu.offsiteinvcount.R;
import d2.hu.offsiteinvcount.ui.model.InventoryCount;
import d2.hu.offsiteinvcount.ui.view.component.InvCountBookLineChooseDialog;

public class InvCountBookChooseLineAdapter extends RecyclerView.Adapter<InvCountBookChooseLineAdapter.CountBookLineViewHolder>{

    private ArrayList<InventoryCount.CountBookLine> countBookLineList = new ArrayList<>();
    private Map<Integer,InventoryCount.CountBookLine > countBookLineMap = new HashMap<>();
    private int countBookLine_number=0;
    private InventoryCountBookLinesActivity activity;
    private InvCountBookLineChooseDialog chooseDialog;

//    public InvCountBookChooseLineAdapter(ArrayList<InventoryCount.CountBookLine> lines){
//        this.countBookLineList.clear();
//        this.countBookLineList.addAll(lines);
//        this.notifyDataSetChanged();
//        Log.d(" ----------> ","Start ADAPTER : list size = "+this.countBookLineList.size());
//    }



    public InvCountBookChooseLineAdapter(Map<Integer,InventoryCount.CountBookLine> lines, InventoryCountBookLinesActivity linesActivity, InvCountBookLineChooseDialog dialog){
        this.countBookLineList.clear();
        this.activity = linesActivity;
        this.chooseDialog = dialog;

        lines.forEach((Integer key, InventoryCount.CountBookLine value) -> {
            this.countBookLineList.add(value);
        });

        this.countBookLineMap.clear();
        this.countBookLineMap.putAll(lines);
        this.notifyDataSetChanged();

    }

    @Override
    public CountBookLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_choose_countbookline_row,parent,false);

        return new InvCountBookChooseLineAdapter.CountBookLineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CountBookLineViewHolder holder, int position) {

        InventoryCount.CountBookLine line = countBookLineList.get(position);
        holder.bind(line);
      //  System.out.println(" ---> line = "+line.getEquipment()+" --> position = "+position+"; mapsize = "+countBookLineMap.size());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

              //  System.out.println(" ---> ONCLICK on equipment -> line = "+line.getEquipment());


                countBookLineMap.forEach((Integer key, InventoryCount.CountBookLine value) -> {
                    if (value.getEquipment().equals(line.getEquipment())){
                           countBookLine_number = key;
                    }
                    System.out.println(" ---> key="+key+"; value="+value.getEquipment());
                });
                System.out.println(" ---> LINE = "+line.getPartnumber()+" - "+line.getEquipment());

               activity.loadCountBookLineDetailsDialog(line,countBookLine_number);
                chooseDialog.dismiss();
//                System.out.println(" ---> onclick on EQUIPMENT : "+line.getEquipment()+" ---> partnumber = "+line.getPartnumber()+" - line nr = "+countBookLine_number);

            }
        });



    }

    @Override
    public int getItemCount() {
        if (countBookLineList != null && countBookLineList.size() > 0) {
            return countBookLineList.size();
        } else {

            return 0;
        }
    }


    static class CountBookLineViewHolder extends RecyclerView.ViewHolder {


//        @BindView(R.id.countBook_equipmentNumber)
//        Button equipment_number;


        @BindView(R.id.countbookLineCardViewText)
        TextView cardText;


        public CountBookLineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(InventoryCount.CountBookLine lineItem) {
           // cardText.setText(lineItem.getEquipment());
            cardText.setText(lineItem.getSerialnumber());
        }
    }
}
