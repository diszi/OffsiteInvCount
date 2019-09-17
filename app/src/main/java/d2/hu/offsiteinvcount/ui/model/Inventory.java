package d2.hu.offsiteinvcount.ui.model;

import java.io.Serializable;
import java.util.List;

public class Inventory  implements Serializable {


    public static String SERIALIZABLE_NAME = "Inventory_Serializable";


    private String part_number; //INVENTORY.ITEMNUM
    private String description; //ITEM.DESCRIPTION
    private String storeroom; //INVENTORY.LOCATION
    private String current_balance; //INVENTORY.CURBALTOTAL
    private boolean rotable; //item.ROTATING
    private String default_bin; //INVENTORY.BINNUM
    private String status; //INVENTORY.STATUS

    private Item item; //ITEM
    private List<InventoryReceiptHistory> historyList; //PLUSMRECEIPTINSPLINE
    private List<InventoryIssueReturnHistory> historyIssueReturnList;//MATUSETRANS
    private List<InventoryAsset> invAssetList; //rotable lista //ASSET
    private List<InventoryBalances> inventoryBalanceList;

    public String getPart_number() {
        return part_number;
    }

    public void setPart_number(String part_number) {
        this.part_number = part_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStoreroom() {
        return storeroom;
    }

    public void setStoreroom(String storeroom) {
        this.storeroom = storeroom;
    }

    public String getCurrent_balance() {
        return current_balance;
    }

    public void setCurrent_balance(String current_balance) {
        this.current_balance = current_balance;
    }

    public boolean isRotable() {
        return rotable;
    }

    public void setRotable(boolean rotable) {
        this.rotable = rotable;
    }

    public String getDefault_bin() {
        return default_bin;
    }

    public void setDefault_bin(String default_bin) {
        this.default_bin = default_bin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<InventoryReceiptHistory> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<InventoryReceiptHistory> historyList) {
        this.historyList = historyList;
    }

    public List<InventoryAsset> getInvAssetList() {
        return invAssetList;
    }

    public void setInvAssetList(List<InventoryAsset> invAssetList) {
        this.invAssetList = invAssetList;
    }

    //    public List<Asset> getAssetList() {
//        return assetList;
//    }
//
//    public void setAssetList(List<Asset> assetList) {
//        this.assetList = assetList;
//    }

    public List<InventoryIssueReturnHistory> getHistoryIssueReturnList() {
        return historyIssueReturnList;
    }

    public void setHistoryIssueReturnList(List<InventoryIssueReturnHistory> historyIssueReturnList) {
        this.historyIssueReturnList = historyIssueReturnList;
    }

    public List<InventoryBalances> getInventoryBalanceList() {
        return inventoryBalanceList;
    }

    public void setInventoryBalanceList(List<InventoryBalances> inventoryBalanceList) {
        this.inventoryBalanceList = inventoryBalanceList;
    }
}
