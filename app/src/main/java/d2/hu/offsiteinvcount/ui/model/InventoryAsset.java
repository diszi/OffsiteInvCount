package d2.hu.offsiteinvcount.ui.model;

import java.io.Serializable;

public class InventoryAsset implements Serializable {

    public static String SERIALIZABLE_NAME = "InventoryAsset_Serializable";

    private String assetnum;
    private String serialnum;
    private String description;
    private String registration;
    private String status;
    private String location; //ASSET.LOCATION
    private String binnum;




    public String getAssetnum() {
        return assetnum;
    }

    public void setAssetnum(String assetnum) {
        this.assetnum = assetnum;
    }

    public String getSerialnum() {
        return serialnum;
    }

    public void setSerialnum(String serialnum) {
        this.serialnum = serialnum;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBinnum() {
        return binnum;
    }

    public void setBinnum(String binnum) {
        this.binnum = binnum;
    }
}
