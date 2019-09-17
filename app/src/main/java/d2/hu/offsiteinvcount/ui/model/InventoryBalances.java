package d2.hu.offsiteinvcount.ui.model;

import java.io.Serializable;

public class InventoryBalances implements Serializable {

    public static String SERIALIZABLE_NAME = "InventoryBalances_Serializable";

    private String invBalancesID;
    private String binnum; //BINNUM
    private String batch; //LOTNUM
    private String currBalance; //CURBAL
    private String physicalCount; //PHYSCNT
    private String physicalCountDate; //PHYSCNTDATE
    private String shelflife; //SHELFLIFE
    private String expirationDate; //USEBY
    private boolean reconciled; //RECONCILED

    private String issueUnit;


    //private InvBalanceAsset asset;


    public String getInvBalancesID() {
        return invBalancesID;
    }

    public void setInvBalancesID(String invBalancesID) {
        this.invBalancesID = invBalancesID;
    }

    public String getBinnum() {
        return binnum;
    }

    public void setBinnum(String binnum) {
        this.binnum = binnum;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getCurrBalance() {
        return currBalance;
    }

    public void setCurrBalance(String currBalance) {
        this.currBalance = currBalance;
    }

    public String getPhysicalCount() {
        return physicalCount;
    }

    public void setPhysicalCount(String physicalCount) {
        this.physicalCount = physicalCount;
    }

    public String getPhysicalCountDate() {
        return physicalCountDate;
    }

    public void setPhysicalCountDate(String physicalCountDate) {
        this.physicalCountDate = physicalCountDate;
    }

    public String getShelflife() {
        return shelflife;
    }

    public void setShelflife(String shelflife) {
        this.shelflife = shelflife;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isReconciled() {
        return reconciled;
    }

    public void setReconciled(boolean reconciled) {
        this.reconciled = reconciled;
    }

    public String getIssueUnit() {
        return issueUnit;
    }

    public void setIssueUnit(String issueUnit) {
        this.issueUnit = issueUnit;
    }

    //    public InvBalanceAsset getAsset() {
//        return asset;
//    }
//
//    public void setAsset(InvBalanceAsset asset) {
//        this.asset = asset;
//    }
//
//    public static class InvBalanceAsset implements Serializable{
//
//        public static String SERIALIZABLE_NAME = "InvBalanceAsset_Serializable";
//
//
//        private String assetnum;
//        private String description;
//        private String registration;
//        private String serialnum;
//
//        public String getAssetnum() {
//            return assetnum;
//        }
//
//        public void setAssetnum(String assetnum) {
//            this.assetnum = assetnum;
//        }
//
//        public String getDescription() {
//            return description;
//        }
//
//        public void setDescription(String description) {
//            this.description = description;
//        }
//
//        public String getRegistration() {
//            return registration;
//        }
//
//        public void setRegistration(String registration) {
//            this.registration = registration;
//        }
//
//        public String getSerialnum() {
//            return serialnum;
//        }
//
//        public void setSerialnum(String serialnum) {
//            this.serialnum = serialnum;
//        }
//    }
}
