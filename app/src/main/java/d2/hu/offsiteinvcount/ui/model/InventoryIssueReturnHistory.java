package d2.hu.offsiteinvcount.ui.model;

import java.io.Serializable;

public class InventoryIssueReturnHistory implements Serializable {


    //MATUSETRANS
    public static String SERIALIZABLE_NAME = "InventoryIssueReturnHistory_Serializable";

    private String asset_num; // . ASSETNUM (melyik assethez tartozik)
    private String binnum;
    private String transDate;
    private String transType;
    private String quantity;
    private String workorder;

    private String issueTo;
    private String storeroom;
    private String batch;
    private String enteredBy;
    private String asset_serialnum;

    // private String aircraft_registration;//ASSET.PLUSAREG

    private MatusetransAsset matusetransAsset;


    public String getAsset_num() {
        return asset_num;
    }

    public void setAsset_num(String asset_num) {
        this.asset_num = asset_num;
    }

    public String getBinnum() {
        return binnum;
    }

    public void setBinnum(String binnum) {
        this.binnum = binnum;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getWorkorder() {
        return workorder;
    }

    public void setWorkorder(String workorder) {
        this.workorder = workorder;
    }

//    public String getAircraft_registration() {
//        return aircraft_registration;
//    }
//
//    public void setAircraft_registration(String aircraft_registration) {
//        this.aircraft_registration = aircraft_registration;
//    }

    public String getIssueTo() {
        return issueTo;
    }

    public void setIssueTo(String issueTo) {
        this.issueTo = issueTo;
    }

    public String getStoreroom() {
        return storeroom;
    }

    public void setStoreroom(String storeroom) {
        this.storeroom = storeroom;
    }

    public String getAsset_serialnum() {
        return asset_serialnum;
    }

    public void setAsset_serialnum(String asset_serialnum) {
        this.asset_serialnum = asset_serialnum;
    }

    public String getBatch() {
        return batch;
    }

//    public String getSerialnum() {
//        return serialnum;
//    }
//
//    public void setSerialnum(String serialnum) {
//        this.serialnum = serialnum;
//    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public MatusetransAsset getMatusetransAsset() {
        return matusetransAsset;
    }

    public void setMatusetransAsset(MatusetransAsset matusetransAsset) {
        this.matusetransAsset = matusetransAsset;
    }

    public static class MatusetransAsset implements Serializable{

        public static String SERIALIZABLE_NAME="MatusetransAsset_serializable";

        private String assetnum;
        private String description;
        private String registration;
        private String serialnum;

        public String getAssetnum() {
            return assetnum;
        }

        public void setAssetnum(String assetnum) {
            this.assetnum = assetnum;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getRegistration() {
            return registration;
        }

        public void setRegistration(String registration) {
            this.registration = registration;
        }

        public String getSerialnum() {
            return serialnum;
        }

        public void setSerialnum(String serialnum) {
            this.serialnum = serialnum;
        }
    }
}
