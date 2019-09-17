package d2.hu.offsiteinvcount.ui.model;

import java.io.Serializable;

public class InventoryReceiptHistory  implements Serializable {


    public static String SERIALIZABLE_NAME = "InventoryHistory_Serializable";



    //history
    private String receipt_insp_num; //PLUSMRECEIPTINSPLINE.RECEIPTINSPNUM
    private String receipt_date; // .STATUSDATE VAGY ACTUALDATE
    private String inspection_status; // .STATUS
    private String serial_num; // .SERIALNUM
    private String asset_num; // . ASSETNUM (melyik assethez tartozik)
    private String quantity_serviceable; // .ACCEPTEDQTY
    private String quantity_quarantined; // .QUARANTINEQTY
    private String quantity_returned;// .RETURNQTY
    private String quantity_scrapped; // .SCRAPQTY
    private String inspectedBy; // .INSPECTEDBY
    private String line;
    private String description; //DESCRIPTION
    private String enterby; //ENTERBY
    private String inspection_date; //INSPECTIONDATE
    private String ponum; //PONUM
    private String status_changedby; //STATUSCHANGEBY
    private String tolocation; //TOLOCATION
    private String toStoreloc; //TOSTORELOC


    public String getReceipt_insp_num() {
        return receipt_insp_num;
    }

    public void setReceipt_insp_num(String receipt_insp_num) {
        this.receipt_insp_num = receipt_insp_num;
    }

    public String getReceipt_date() {
        return receipt_date;
    }

    public void setReceipt_date(String receipt_date) {
        this.receipt_date = receipt_date;
    }

    public String getInspection_status() {
        return inspection_status;
    }

    public void setInspection_status(String inspection_status) {
        this.inspection_status = inspection_status;
    }

    public String getSerial_num() {
        return serial_num;
    }

    public void setSerial_num(String serial_num) {
        this.serial_num = serial_num;
    }

    public String getAsset_num() {
        return asset_num;
    }

    public void setAsset_num(String asset_num) {
        this.asset_num = asset_num;
    }

    public String getQuantity_serviceable() {
        return quantity_serviceable;
    }

    public void setQuantity_serviceable(String quantity_serviceable) {
        this.quantity_serviceable = quantity_serviceable;
    }

    public String getQuantity_quarantined() {
        return quantity_quarantined;
    }

    public void setQuantity_quarantined(String quantity_quarantined) {
        this.quantity_quarantined = quantity_quarantined;
    }

    public String getQuantity_returned() {
        return quantity_returned;
    }

    public void setQuantity_returned(String quantity_returned) {
        this.quantity_returned = quantity_returned;
    }

    public String getQuantity_scrapped() {
        return quantity_scrapped;
    }

    public void setQuantity_scrapped(String quantity_scrapped) {
        this.quantity_scrapped = quantity_scrapped;
    }

    public String getInspectedBy() {
        return inspectedBy;
    }

    public void setInspectedBy(String inspectedBy) {
        this.inspectedBy = inspectedBy;
    }


    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnterby() {
        return enterby;
    }

    public void setEnterby(String enterby) {
        this.enterby = enterby;
    }

    public String getInspection_date() {
        return inspection_date;
    }

    public void setInspection_date(String inspection_date) {
        this.inspection_date = inspection_date;
    }

    public String getPonum() {
        return ponum;
    }

    public void setPonum(String ponum) {
        this.ponum = ponum;
    }

    public String getStatus_changedby() {
        return status_changedby;
    }

    public void setStatus_changedby(String status_changedby) {
        this.status_changedby = status_changedby;
    }

    public String getTolocation() {
        return tolocation;
    }

    public void setTolocation(String tolocation) {
        this.tolocation = tolocation;
    }

    public String getToStoreloc() {
        return toStoreloc;
    }

    public void setToStoreloc(String toStoreloc) {
        this.toStoreloc = toStoreloc;
    }
}
