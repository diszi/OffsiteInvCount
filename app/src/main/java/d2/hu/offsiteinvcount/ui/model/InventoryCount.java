package d2.hu.offsiteinvcount.ui.model;

import java.io.Serializable;
import java.util.List;

public class InventoryCount  implements Serializable {

    public static String SERIALIZABLE_NAME = "InventoryCount_serializable";

    private  String countbook;//COUNTBOOKNUM
    private String status;//STATUS
    private String storeroom;//STOREROOM
    private String countBookID;

    //if status==INPRG  ==> lehet leltarozni

    //LISTA
    private List<CountBookLine> countBookLineList;


    public InventoryCount(){}

    public String getCountBookID() {
        return countBookID;
    }

    public void setCountBookID(String countBookID) {
        this.countBookID = countBookID;
    }

    public String getCountbook() {
        return countbook;
    }

    public void setCountbook(String countbook) {
        this.countbook = countbook;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStoreroom() {
        return storeroom;
    }

    public void setStoreroom(String storeroom) {
        this.storeroom = storeroom;
    }

    public List<CountBookLine> getCountBookLineList() {
        return countBookLineList;
    }

    public void setCountBookLineList(List<CountBookLine> countBookLineList) {
        this.countBookLineList = countBookLineList;
    }



    public static class CountBookLine implements Serializable {

        public static String SERIALIZABLE_NAME = "CountBookLine_serializable";


        private String bin;//BINNUM == Bin
        private String batch;//LOTNUM == Batch
        private String partnumber;// ITEMNUM == Partnumber
        private boolean rotable;//ROTATING = rotable
        private String serialnumber;//SERIALNUM = serialnr
        private String equipment;//ASSETNUM = equipment
        private String currentBalance;//CURBAL = curr balance ==> decimal
        private String physicalCount;//PHYSCNT = physical count (writeable) ==>decimal
        private boolean reconcile;
        private String id;
        private String countedBy;
        private String countedDate;

        public String getBin() {
            return bin;
        }

        public void setBin(String bin) {
            this.bin = bin;
        }

        public String getBatch() {
            return batch;
        }

        public void setBatch(String batch) {
            this.batch = batch;
        }

        public String getPartnumber() {
            return partnumber;
        }

        public void setPartnumber(String partnumber) {
            this.partnumber = partnumber;
        }

        public boolean isRotable() {
            return rotable;
        }

        public void setRotable(boolean rotable) {
            this.rotable = rotable;
        }

        public String getSerialnumber() {
            return serialnumber;
        }

        public void setSerialnumber(String serialnumber) {
            this.serialnumber = serialnumber;
        }

        public String getEquipment() {
            return equipment;
        }

        public void setEquipment(String equipment) {
            this.equipment = equipment;
        }

        public String getCurrentBalance() {
            return currentBalance;
        }

        public void setCurrentBalance(String currentBalance) {
            this.currentBalance = currentBalance;
        }

        public String getPhysicalCount() {
            return physicalCount;
        }

        public void setPhysicalCount(String physicalCount) {
            this.physicalCount = physicalCount;
        }

        public boolean isReconcile() {
            return reconcile;
        }

        public void setReconcile(boolean reconcile) {
            this.reconcile = reconcile;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCountedBy() {
            return countedBy;
        }

        public void setCountedBy(String countedBy) {
            this.countedBy = countedBy;
        }

        public String getCountedDate() {
            return countedDate;
        }

        public void setCountedDate(String countedDate) {
            this.countedDate = countedDate;
        }
    }

}

