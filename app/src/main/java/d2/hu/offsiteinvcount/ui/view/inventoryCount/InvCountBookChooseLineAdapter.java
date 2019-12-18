package d2.hu.offsiteinvcount.ui.view.inventoryCount;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.CardView;
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

public class InvCountBookChooseLineAdapter extends RecyclerView.Adapter<InvCountBookChooseLineAdapter.CountBookLineViewHolder>{

    private ArrayList<InventoryCount.CountBookLine> countBookLineList = new ArrayList<>();
    private Map<Integer,InventoryCount.CountBookLine> countBookLineMap = new HashMap<>();
    private int countBookLine_number=0;
    private InventoryCountBookLinesActivity activity;
    //private InvCountBookLineChooseDialog chooseDialog;

    private InvCountBookLineChooseActivity chooseDialog;

    /*@TargetApi(Build.VERSION_CODES.N)
    public InvCountBookChooseLineAdapter(Map<Integer,InventoryCount.CountBookLine> lines, InventoryCountBookLinesActivity linesActivity, InvCountBookLineChooseDialog dialog){
        this.countBookLineList.clear();
        this.activity = linesActivity;
        this.chooseDialog = dialog;

        lines.forEach((Integer key, InventoryCount.CountBookLine value) -> this.countBookLineList.add(value));

        this.countBookLineMap.clear();
        this.countBookLineMap.putAll(lines);
        this.notifyDataSetChanged();

    }*/


    @TargetApi(Build.VERSION_CODES.N)
    public InvCountBookChooseLineAdapter(Map<Integer,InventoryCount.CountBookLine> lines,InvCountBookLineChooseActivity dialog){
        this.countBookLineList.clear();
        this.chooseDialog = dialog;
//        this.countBook_lines.addAll(EnvironmentTool.sortCountBookList(countBook_item.getCountBookLineList()));

        lines.forEach((Integer key, InventoryCount.CountBookLine value) ->{
            //System.out.println(" add item to LIST --> key = "+key+"; value="+value.getPartnumber()+" & "+value.getBatch()+" & "+value.getSerialnumber());
            if (!value.isReconcile()){
                this.countBookLineList.add(value);
            }
        });

       // System.out.println(" LIST sIZE = "+this.countBookLineList.size());
//        lines.forEach((Integer key, InventoryCount.CountBookLine value) ->
//                this.countBookLineList.add(value));

        this.countBookLineMap.clear();
        this.countBookLineMap.putAll(lines);
        this.notifyDataSetChanged();

    }

    public void setCountBookLines_values(Map<Integer,InventoryCount.CountBookLine> lines){
        this.countBookLineList.clear();
       // this.chooseDialog = dialog;
        lines.forEach((Integer key, InventoryCount.CountBookLine value) ->{
            //System.out.println(" add item to LIST --> key = "+key+"; value="+value.getPartnumber()+" & "+value.getBatch()+" & "+value.getSerialnumber());
            if (!value.isReconcile()){
                this.countBookLineList.add(value);
            }
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

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(CountBookLineViewHolder holder, int position) {

        InventoryCount.CountBookLine line = countBookLineList.get(position);
       // System.out.println(" Line selected = "+line.getPartnumber()+" & "+line.getSerialnumber()+" & "+line.getBatch());
        holder.bind(line);
        holder.itemView.setOnClickListener(v -> {
            countBookLineMap.forEach((Integer key, InventoryCount.CountBookLine value) -> {
                //System.out.println(" onCLICK on item : MAP ["+key+"; "+value.getSerialnumber()+" - "+value.getBatch()+"]");
                if (value.isRotable()){
                    if (value.getEquipment().equals(line.getEquipment())){
                        countBookLine_number = key;
                    }
                }else{
                    if (value.getBatch().equals(line.getBatch())){
                        countBookLine_number = key;
                    }
                }

            });
            //System.out.println(" ---> line getbatch = "+line.getBatch()+"; line SN = "+line.getSerialnumber()+"; number = "+countBookLine_number);
            chooseDialog.loadLineDetailsDialog(line,countBookLine_number);

           //activity.loadCountBookLineDetailsDialog(line,countBookLine_number);
           //chooseDialog.dismiss();
            //chooseDialog.finish();

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

        @BindView(R.id.countBookCardView)
        CardView cardView;
        @BindView(R.id.countbookLineCardViewText)
        TextView cardText;


        CountBookLineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(InventoryCount.CountBookLine lineItem) {
            if (lineItem.isRotable()){
                cardText.setText(lineItem.getSerialnumber());

            }else {
                cardText.setText(lineItem.getBatch());
            }

            if (lineItem.getPhysicalCount().isEmpty()){
                //cardView.setBackgroundResource(R.color.colorLightGray);
//                cardText.setTextColor(Color.parseColor("#9c9faa"));
                cardView.setCardBackgroundColor(Color.parseColor("#e0dede"));
            }else{
                if (lineItem.getPhysicalCount().equals(lineItem.getCurrentBalance())){
                    //cardView.setBackgroundResource(R.color.colorMatch);
//                    cardText.setTextColor(Color.parseColor("#bce6bc"));
                    cardView.setCardBackgroundColor(Color.parseColor("#bce6bc"));
                }else{
                   // cardView.setBackgroundResource(R.color.colorWait);

//                    cardText.setTextColor(Color.parseColor("#faa0a0"));
                    cardView.setCardBackgroundColor(Color.parseColor("#faa0a0"));

                }

            }


        }
    }
}
