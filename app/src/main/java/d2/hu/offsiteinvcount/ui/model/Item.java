package d2.hu.offsiteinvcount.ui.model;

import java.io.Serializable;

public class Item implements Serializable {

    public static String SERIALIZABLE_NAME = "Item_Serializable";

    private String item_description;
    private boolean item_rotating;


    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public boolean isItem_rotating() {
        return item_rotating;
    }

    public void setItem_rotating(boolean item_rotating) {
        this.item_rotating = item_rotating;
    }
}
