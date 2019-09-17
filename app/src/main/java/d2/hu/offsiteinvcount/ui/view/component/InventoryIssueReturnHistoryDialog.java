package d2.hu.offsiteinvcount.ui.view.component;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2.hu.offsiteinvcount.R;
import d2.hu.offsiteinvcount.ui.model.InventoryIssueReturnHistory;
import d2.hu.offsiteinvcount.util.EnvironmentTool;

public class InventoryIssueReturnHistoryDialog extends DialogFragment {

    public static String SERIALIZABLE_NAME = "InventoryHistoryIssueReturn_serializable";
    private InventoryIssueReturnHistory historyItem = null;


    @BindView(R.id.dialogIssueReturn_title)
    TextView title;
    @BindView(R.id.diagHistory_cancelButton)
    Button cancelButton;

    @BindView(R.id.invHistory_trans_type)
    TextView transaction_type;
    @BindView(R.id.invHistory_trans_date)
    TextView transaction_date;
    @BindView(R.id.invHistory_quantity)
    TextView quantity;
    @BindView(R.id.invHistory_workorder)
    TextView workorder;
    @BindView(R.id.invHistory_serialnum)
    TextView serialnum;
    @BindView(R.id.invHistory_registration)
    TextView registration;
    @BindView(R.id.invHistory_issueTo)
    TextView issueto;
    @BindView(R.id.invHistory_batch)
    TextView batch;
    @BindView(R.id.invHistory_storeroom)
    TextView inventory_location;
    @BindView(R.id.invHistory_enteredby)
    TextView enteredby;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("----------------->","InventoryIssueReturnHistoryDialog");
        if (getArguments() != null) {
            historyItem = (InventoryIssueReturnHistory) getArguments().getSerializable(InventoryIssueReturnHistoryDialog.SERIALIZABLE_NAME);
        }


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View contentView = inflater.inflate(R.layout.dialog_inv_issuereturn_history, container, false);

        this.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        ButterKnife.bind(this, contentView);

        cancelButton.setOnClickListener((view -> {
            dismiss();
        }));

        title.setText("Issue Return");


        if (historyItem.getMatusetransAsset()!=null){
            registration.setText(historyItem.getMatusetransAsset().getRegistration());
        }

        inventory_location.setText(historyItem.getStoreroom());
        enteredby.setText(historyItem.getEnteredBy());
        batch.setText(historyItem.getBatch());
        issueto.setText(historyItem.getIssueTo());
        workorder.setText(historyItem.getWorkorder());
        quantity.setText(historyItem.getQuantity());
        transaction_date.setText(EnvironmentTool.convertDateTimeString(historyItem.getTransDate()));
        transaction_type.setText(historyItem.getTransType());

        return contentView;
    }
}
