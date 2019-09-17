package d2.hu.offsiteinvcount.ui.view.component;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2.hu.offsiteinvcount.R;
import d2.hu.offsiteinvcount.ui.model.InventoryCount;
import d2.hu.offsiteinvcount.ui.view.base.RemoteCallBack;
import d2.hu.offsiteinvcount.ui.view.inventoryCount.InventoryCountBookLinesActivity;
import d2.hu.offsiteinvcount.ui.view.inventoryCount.InventoryCountPresenter;
import d2.hu.offsiteinvcount.util.EnvironmentTool;

public class InvCountBookLineDialog extends DialogFragment {

    public static String SERIALIZABLE_NAME = "InvCountBookLineDialog_serializable";
    public static String COUNT_BOOK = "InvCountBook_serializable";
    public static String ITEM_POSITION="item_position";

    @BindView(R.id.dialogInvCount_title)
    TextView dialog_title;
    @BindView(R.id.dialogInvCountBook_saveBtn)
    Button dialog_saveBtn;
    @BindView(R.id.dialogInvCountBook_cancelBtn)
    Button dialog_cancelBtn;
    @BindView(R.id.invCountBook_bin)
    TextView countBook_bin;
    @BindView(R.id.invCountBook_batch)
    TextView countBook_batch;
    @BindView(R.id.invCountBook_partnr)
    TextView countBook_partnr;
    @BindView(R.id.invCountBook_rotable)
    TextView countBook_rotable;
    @BindView(R.id.invCountBook_serialnr)
    TextView countBook_serialnr;
    @BindView(R.id.invCountBook_equipment)
    TextView countBook_equipment;
    @BindView(R.id.invCountBook_curbal)
    TextView countBook_currentBalance;
    @BindView(R.id.invCountBook_physicalcount)
    EditText countBook_physicalCount;
    @BindView(R.id.invCountBook_physicalcount_view)
    TextView countBook_physicalCount_view;
    @BindView(R.id.invCountBook_modifyIcon)
    ImageButton modifyCount_icon;
    @BindView(R.id.countedBy_label)
    TextView countedBy_label;
    @BindView(R.id.countDate_label)
    TextView countDate_label;
    @BindView(R.id.invCountBook_countdate)
    TextView countBook_countDate;
    @BindView(R.id.invCountBook_countedBy)
    TextView countBook_countedBy;

    private int item_position;
    private InventoryCount.CountBookLine countBookLineItem;
    private InventoryCount countBookItem;
    private InventoryCountPresenter presenter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("------------------>","START InvCountBookLineDialog");

        if (getArguments() != null) {
            countBookLineItem = (InventoryCount.CountBookLine) getArguments().getSerializable(InvCountBookLineDialog.SERIALIZABLE_NAME); //line item
            countBookItem = (InventoryCount)getArguments().getSerializable(InvCountBookLineDialog.COUNT_BOOK); //main item
            item_position = (int)getArguments().getSerializable(InvCountBookLineDialog.ITEM_POSITION);
        }
        item_position=item_position+1;
        presenter = new InventoryCountPresenter();

//        System.out.println("LINE="+item_position+"; partnr="+countBookLineItem.getPartnumber()+"; equipment="+countBookLineItem.getEquipment()
//        +"; countby="+countBookLineItem.getCountedBy()+"; countdate="+countBookLineItem.getCountedDate()+"; count="+countBookLineItem.getPhysicalCount());
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View contentView = inflater.inflate(R.layout.dialog_invcountbook, container, false);

        ButterKnife.bind(this, contentView);
        String physicalCount = countBookLineItem.getPhysicalCount();
        Log.d("------------------>","Physical Count = "+physicalCount+"; is reconciled? = "+countBookLineItem.isReconcile());

        if (countBookLineItem.isReconcile()) {
//            System.out.println(" ISRECONCILE = TRUE ");

            countBook_physicalCount.setVisibility(View.INVISIBLE);
            countBook_physicalCount_view.setVisibility(View.VISIBLE);
            modifyCount_icon.setVisibility(View.INVISIBLE);
            dialog_saveBtn.setVisibility(View.INVISIBLE);

            countBook_physicalCount_view.setText(physicalCount);

            countBook_countedBy.setText(countBookLineItem.getCountedBy());
            countBook_countDate.setText(EnvironmentTool.convertDateTimeString(countBookLineItem.getCountedDate()));
        }else{
//            System.out.println(" ISRECONCILE = FALSE ");

            if (!TextUtils.isEmpty(countBookLineItem.getCountedBy())){
                System.out.println(" line.getCountby = "+countBookLineItem.getCountedBy()+" is NOT Empty");
                countBook_countedBy.setText(countBookLineItem.getCountedBy());
            }else{
                System.out.println(" line.getCountby = "+countBookLineItem.getCountedBy()+" isEmpty");
            }

            if (!TextUtils.isEmpty(countBookLineItem.getCountedDate())){
                System.out.println(" line.getCountedDate = "+countBookLineItem.getCountedDate()+" is NOT Empty");
                countBook_countDate.setText(EnvironmentTool.convertDateTimeString(countBookLineItem.getCountedDate()));
            }else{
                System.out.println(" line.getCountedDate = "+countBookLineItem.getCountedDate()+" isEmpty");
            }


            if (physicalCount.isEmpty()){
//                System.out.println(" ---> PHYSICAL count is EMPTY >> physicalcount="+physicalCount );
                countBook_physicalCount_view.setVisibility(View.INVISIBLE);
                modifyCount_icon.setVisibility(View.INVISIBLE);
                countBook_physicalCount.setVisibility(View.VISIBLE);
            }else{
//                System.out.println(" ---> PHYSICAL count is NOT EMPTY >> physicalcount="+physicalCount );
                countBook_physicalCount_view.setVisibility(View.VISIBLE);
                countBook_physicalCount_view.setText(physicalCount);
                countBook_physicalCount.setVisibility(View.INVISIBLE);
                modifyCount_icon.setVisibility(View.VISIBLE);
                dialog_saveBtn.setVisibility(View.INVISIBLE);
            }
        }


        modifyCount_icon.setOnClickListener(v -> {
            countBook_physicalCount_view.setVisibility(View.INVISIBLE);
            countBook_physicalCount.setVisibility(View.VISIBLE);
            dialog_saveBtn.setVisibility(View.VISIBLE);
        });

        dialog_title.setText("Count book item details: ");

        countBook_bin.setText(countBookLineItem.getBin());
        countBook_batch.setText(countBookLineItem.getBatch());
        countBook_partnr.setText(countBookLineItem.getPartnumber());
        countBook_rotable.setText(String.valueOf(countBookLineItem.isRotable()));
        countBook_serialnr.setText(countBookLineItem.getSerialnumber());
        countBook_equipment.setText(countBookLineItem.getEquipment());
        countBook_currentBalance.setText(countBookLineItem.getCurrentBalance());


//        String physicalCount = countBookLineItem.getPhysicalCount();
//
//        if (countBookLineItem.isReconcile()) {
//            countBook_physicalCount.setVisibility(View.INVISIBLE);
//            countBook_physicalCount_view.setVisibility(View.VISIBLE);
//            countBook_physicalCount_view.setText(physicalCount);
//
//            System.out.println(" ISRECONCILE = TRUE : line.getphysical count = "+countBookLineItem.getPhysicalCount()+" --> variable = "+physicalCount);
//            dialog_saveBtn.setVisibility(View.INVISIBLE);
//            countBook_countedBy.setText(countBookLineItem.getCountedBy());
//            countBook_countDate.setText(EnvironmentTool.convertDateTimeString(countBookLineItem.getCountedDate()));
//
//        }else{
//
//            countBook_physicalCount.setVisibility(View.VISIBLE);
//            countBook_physicalCount_view.setVisibility(View.INVISIBLE);
//            countBook_physicalCount.setText(physicalCount);
//
//
//            System.out.println(" ISRECONCILE = FALSE >> physicalCount="+physicalCount);
//            if (TextUtils.isEmpty(countBookLineItem.getCountedBy())){
//                System.out.println(" line.getCountby = "+countBookLineItem.getCountedBy()+" isEmpty");
//            }else{
//                System.out.println(" line.getCountby = "+countBookLineItem.getCountedBy()+" is NOT Empty");
//                countBook_countedBy.setText(countBookLineItem.getCountedBy());
//            }
//
//            if (TextUtils.isEmpty(countBookLineItem.getCountedDate())){
//                System.out.println(" line.getCountedDate = "+countBookLineItem.getCountedDate()+" isEmpty");
//            }else{
//                System.out.println(" line.getCountedDate = "+countBookLineItem.getCountedDate()+" is NOT Empty");
//                countBook_countDate.setText(EnvironmentTool.convertDateTimeString(countBookLineItem.getCountedDate()));
//            }
//
//            System.out.println(" line.physical count = "+physicalCount);
//        }
//
//
//        dialog_title.setText("Count book item details: ");
//
//        countBook_bin.setText(countBookLineItem.getBin());
//        countBook_batch.setText(countBookLineItem.getBatch());
//        countBook_partnr.setText(countBookLineItem.getPartnumber());
//        countBook_rotable.setText(String.valueOf(countBookLineItem.isRotable()));
//        countBook_serialnr.setText(countBookLineItem.getSerialnumber());
//        countBook_equipment.setText(countBookLineItem.getEquipment());
//        countBook_currentBalance.setText(countBookLineItem.getCurrentBalance());
//        countBook_physicalCount.setText(physicalCount);


        dialog_saveBtn.setOnClickListener(v -> {

            System.out.println(" ----> SAVE inventory counting --> "+countBook_physicalCount.getText());
            if (TextUtils.isEmpty(countBook_physicalCount.getText())){
                countBook_physicalCount.setError("Physical count field is required!");
                countBook_physicalCount.requestFocus();
            }else{
                String count = countBook_physicalCount.getText().toString();
                double count_in_double  = Double.parseDouble(count);
                if (count_in_double > Double.parseDouble(countBookLineItem.getCurrentBalance())){
                    countBook_physicalCount.setError("Physical count field is must be <= than current balance!");
                    countBook_physicalCount.requestFocus();
                }else{

                    countBook_physicalCount.setText(null);

                    presenter.updateInvCountBookLine(count,countBookItem.getCountBookID(), item_position, countBookLineItem, new RemoteCallBack<Boolean>() {
                        @Override
                        public void onSucces(Boolean object) {

                            System.out.println(" >>>>>> UPDATE line - SUCCESS = "+object+" -- on line "+item_position);
                            if (object){
                                dismiss();
                                Log.d("-------------->","Successful inventory!");
                                Toast.makeText(getActivity(),"Successful inventory",Toast.LENGTH_SHORT).show();
                                ((InventoryCountBookLinesActivity)getActivity()).reloadLines(countBookItem.getCountbook());
                            }else{
                                Log.e("------------->","Inventory Counting failed!");
                                Toast.makeText(getActivity(),"Inventory Counting failed!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        dialog_cancelBtn.setOnClickListener(v -> dismiss());
        return contentView;

    }

}
