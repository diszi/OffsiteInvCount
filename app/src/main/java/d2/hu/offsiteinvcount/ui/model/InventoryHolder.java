package d2.hu.offsiteinvcount.ui.model;

import java.io.Serializable;

public class InventoryHolder implements Serializable {

    public static String SERIALIZABLE_NAME = "InventoryHolder_serializable";

    private Inventory entity;
    private InventoryCount entity_countBook;
    private int position;
    private boolean changed = false;

    public InventoryHolder(Inventory entity,int position){
        this.entity = entity;
        this.position = position;
    }

    public InventoryHolder(InventoryCount entity_countBook,int position){
        this.entity_countBook = entity_countBook;
        this.position = position;
    }

    public InventoryCount getEntity_countBook(){
        return entity_countBook;
    }

    public Inventory getEntity() {
        return entity;
    }

    public int getPosition(){
        return position;
    }

    public boolean isChanged(){
        return changed;

    }

    public void setChanged(boolean changed){
        this.changed = changed;
    }
}
