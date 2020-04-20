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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2.hu.offsiteinvcount.R;
import d2.hu.offsiteinvcount.ui.model.InventoryCount;
import d2.hu.offsiteinvcount.ui.view.base.RemoteCallBack;
import d2.hu.offsiteinvcount.ui.view.inventoryCount.InvCountBookLineChooseActivity;
import d2.hu.offsiteinvcount.ui.view.inventoryCount.InventoryCountBookLinesActivity;
import d2.hu.offsiteinvcount.ui.view.inventoryCount.InventoryCountPresenter;
import d2.hu.offsiteinvcount.util.EnvironmentTool;

public class InvCountBookLineDetailsDialog extends DialogFragment {

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
    @BindView(R.id.invCountBook_def_bin)
    TextView countBook_defaultbin;
    @BindView(R.id.invCountBook_batch)
    TextView countBook_batch;
    @BindView(R.id.invCountBook_partnr)
    TextView countBook_partnr;
//    @BindView(R.id.invCountBook_rotable)
//    TextView countBook_rotable;
    @BindView(R.id.invCountBook_serialnr)
    TextView countBook_serialnr;
//    @BindView(R.id.invCountBook_equipment)
//    TextView countBook_equipment;
    @BindView(R.id.invCountBook_assetnum)
    TextView countBook_assetnum;
//    @BindView(R.id.invCountBook_curbal)
//    TextView countBook_currentBalance;
    @BindView(R.id.invCountBook_issueunit)
    TextView countBook_issueunit;
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
    @BindView(R.id.invCountBook_rotableIcon)
    ImageButton rotableImageIcon;
    @BindView(R.id.invCountBook_nonrotableIcon)
    ImageButton nonrotableImageIcon;

    @BindView(R.id.invCountBook_rotablelayout)
    LinearLayout rotable_layout;

    private int item_position;
    private InventoryCount.CountBookLine countBookLineItem;
    private InventoryCount countBookItem;
    private InventoryCountPresenter presenter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("------------------>","START InvCountBookLineDetailsDialog");

        if (getArguments() != null) {
            countBookLineItem = (InventoryCount.CountBookLine) getArguments().getSerializable(InvCountBookLineDetailsDialog.SERIALIZABLE_NAME); //line item
            countBookItem = (InventoryCount)getArguments().getSerializable(InvCountBookLineDetailsDialog.COUNT_BOOK); //main item
            item_position = (int)getArguments().getSerializable(InvCountBookLineDetailsDialog.ITEM_POSITION);
        }
        item_position=item_position+1;


        //System.out.println(" DETAILS dialog :\titem_position="+item_position+"\titem = "+countBookItem.getCountbook()+"\t PN = "+countBookLineItem.getPartnumber());
        presenter = new InventoryCountPresenter();

  }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View contentView = inflater.inflate(R.layout.dialog_invcountbook, container, false);
        ButterKnife.bind(this, contentView);

        String physicalCount = countBookLineItem.getPhysicalCount();
        Log.d("------------------>","Physical Count = "+physicalCount+"; is reconciled? = "+countBookLineItem.isReconcile());

        if (countBookLineItem.isReconcile()) {
            countBook_physicalCount.setVisibility(View.INVISIBLE);
            countBook_physicalCount_view.setVisibility(View.VISIBLE);
            modifyCount_icon.setVisibility(View.INVISIBLE);
            dialog_saveBtn.setVisibility(View.INVISIBLE);

            countBook_physicalCount_view.setText(physicalCount);

            countBook_countedBy.setText(countBookLineItem.getCountedBy());
            countBook_countDate.setText(EnvironmentTool.convertDateTimeString(countBookLineItem.getCountedDate()));
        }else{
            if (!TextUtils.isEmpty(countBookLineItem.getCountedBy())){
                countBook_countedBy.setText(countBookLineItem.getCountedBy());
            }

            if (!TextUtils.isEmpty(countBookLineItem.getCountedDate())){
                countBook_countDate.setText(EnvironmentTool.convertDateTimeString(countBookLineItem.getCountedDate()));
            }


            if (physicalCount.isEmpty()){
                countBook_physicalCount_view.setVisibility(View.INVISIBLE);
                modifyCount_icon.setVisibility(View.INVISIBLE);
                countBook_physicalCount.setVisibility(View.VISIBLE);
//                countBook_physicalCount.requestFocus();
//                   InputMethodManager imm = (InputMethodManager)countBook_physicalCount.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.showSoftInput(countBook_physicalCount,InputMethodManager.SHOW_IMPLICIT);

            }else{
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

        dialog_title.setText(getResources().getString(R.string.title_countbookdetails));

        countBook_bin.setText(countBookLineItem.getBin());
        countBook_defaultbin.setText(countBookLineItem.getInventory().getDefaultbin());
        countBook_batch.setText(countBookLineItem.getBatch());
        countBook_partnr.setText(countBookLineItem.getPartnumber());
        //countBook_rotable.setText(String.valueOf(countBookLineItem.isRotable()));
        countBook_serialnr.setText(countBookLineItem.getSerialnumber());
        countBook_assetnum.setText(countBookLineItem.getEquipment());

        if (countBookLineItem.isRotable()){
            setLayoutHeight(rotable_layout,1);
            setLayoutWidth(rotableImageIcon,30);
            setLayoutWidth(nonrotableImageIcon,0);
        }else{
            setLayoutHeight(rotable_layout,0);
            setLayoutWidth(rotableImageIcon,0);
            setLayoutWidth(nonrotableImageIcon,28);
        }

       // countBook_equipment.setText(countBookLineItem.getEquipment());
       // countBook_currentBalance.setText(countBookLineItem.getCurrentBalance());
        countBook_issueunit.setText(countBookLineItem.getInventory().getIssueunit());


        dialog_saveBtn.setOnClickListener(v -> {

            if (TextUtils.isEmpty(countBook_physicalCount.getText())){
                countBook_physicalCount.setError(getResources().getString(R.string.error_phycountfield));
                countBook_physicalCount.requestFocus();
            }else{
                String count = countBook_physicalCount.getText().toString();
                double count_in_double  = Double.parseDouble(count);

                if (countBookLineItem.getCurrentBalance().isEmpty()){
                    countBook_physicalCount.setError("Current balance field is EMPTY! Please add value to current balance field!");
                    countBook_physicalCount.requestFocus();
                    countBook_physicalCount.setText(null);
                }else{

                countBook_physicalCount.setText(null);

                presenter.updateInvCountBookLine(count,countBookItem.getCountBookID(), item_position, countBookLineItem, new RemoteCallBack<Boolean>() {
                        @Override
                        public void onSucces(Boolean object) {
                            if (object){
                                dismiss();
                                Log.d("------------------>",getResources().getString(R.string.succes_msg));
                                Toast.makeText(getActivity(),getResources().getString(R.string.succes_msg),Toast.LENGTH_SHORT).show();
                                if (getActivity().getClass().equals(InventoryCountBookLinesActivity.class)){
                                    ((InventoryCountBookLinesActivity)getActivity()).reloadLines(countBookItem.getCountbook());
                                }else{
//                                    System.out.println(" GETACTIVITY != ");
                                    ((InvCountBookLineChooseActivity)getActivity()).reloadCountBook(countBookLineItem.getPartnumber(),countBookLineItem);
//                                    Intent intent = new Intent(getActivity(),InventoryCountBookLinesActivity.class);
//                                    intent.putExtra(InventoryCount.SERIALIZABLE_NAME,countBookItem);
//                                    startActivity(intent);

                                }

                            }else{
                                Log.e("------------->",getResources().getString(R.string.error_invcount_failed));
                                Toast.makeText(getActivity(),getResources().getString(R.string.error_invcount_failed),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        dialog_cancelBtn.setOnClickListener(v -> dismiss());
        return contentView;

    }


    private void setLayoutHeight(LinearLayout linearLayout,int parameter){
        ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
        if (parameter==0){
            params.height=0;
            linearLayout.setLayoutParams(params);
        }else{
            params.height= ViewGroup.LayoutParams.WRAP_CONTENT;
            linearLayout.setLayoutParams(params);
        }
    }

    private void setLayoutWidth(ImageButton img,int parameter){
        ViewGroup.LayoutParams params = img.getLayoutParams();
        if (parameter==0){
            params.width=0;
            img.setLayoutParams(params);
        }else{
            params.width=parameter;
            img.setLayoutParams(params);
        }
    }




}
