package d2.hu.offsiteinvcount.ui.view.component;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2.hu.offsiteinvcount.R;
import d2.hu.offsiteinvcount.ui.model.InventoryReceiptHistory;
import d2.hu.offsiteinvcount.util.EnvironmentTool;

public class InventoryHistoryDialog extends DialogFragment {

    public static String SERIALIZABLE_NAME = "InventoryHistory_serializable";
    private InventoryReceiptHistory historyItem = null;



    @BindView(R.id.dialogHistory_title)
    TextView title;
    @BindView(R.id.diagHistory_cancelButton)
    Button cancelButton;

    @BindView(R.id.invHistory_receipt_insp_number)
    TextView receipt_insp_number;
    @BindView(R.id.invHistory_receiptDate)
    TextView receiptDate;
    @BindView(R.id.invHistory_serialNR)
    TextView serialnumber;
    @BindView(R.id.invHistory_inspStatus)
    TextView status;
    @BindView(R.id.invHistory_qtyQuarantined)
    TextView quantity_quarantined;
    @BindView(R.id.invHistory_qtyReturned)
    TextView quantity_returned;
    @BindView(R.id.invHistory_qtyScrapped)
    TextView quantity_scrapped;
    @BindView(R.id.invHistory_qtyServiceable)
    TextView quantity_serviceable;
    @BindView(R.id.invHistory_line)
    TextView line;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            historyItem = (InventoryReceiptHistory) getArguments().getSerializable(InventoryHistoryDialog.SERIALIZABLE_NAME);
        }


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View contentView = inflater.inflate(R.layout.dialog_inventory_history, container, false);


        this.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ButterKnife.bind(this, contentView);

        cancelButton.setOnClickListener((view -> {
            dismiss();
        }));

        title.setText("HISTORY");

        receipt_insp_number.setText(historyItem.getReceipt_insp_num());
        line.setText(historyItem.getLine());
        receiptDate.setText(EnvironmentTool.convertDateTimeString(historyItem.getReceipt_date()));
        serialnumber.setText(historyItem.getSerial_num());
        status.setText(historyItem.getInspection_status());
        quantity_quarantined.setText(historyItem.getQuantity_quarantined());
        quantity_returned.setText(historyItem.getQuantity_returned());
        quantity_scrapped.setText(historyItem.getQuantity_scrapped());
        quantity_serviceable.setText(historyItem.getQuantity_serviceable());

        return contentView;
    }
}
