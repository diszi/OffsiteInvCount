package d2.hu.offsiteinvcount.ui.model;

import java.io.Serializable;

public class Inventory implements Serializable {

    public static String SERIALIZABLE_NAME = "Inventory_serializable";

    private String issueunit;
    private String defaultbin;
    private String itemnum;

    public String getIssueunit() {
        return issueunit;
    }

    public void setIssueunit(String issueunit) {
        this.issueunit = issueunit;
    }

    public String getDefaultbin() {
        return defaultbin;
    }

    public void setDefaultbin(String defaultbin) {
        this.defaultbin = defaultbin;
    }

    public String getItemnum() {
        return itemnum;
    }

    public void setItemnum(String itemnum) {
        this.itemnum = itemnum;
    }
}
