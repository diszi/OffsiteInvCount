package d2.hu.offsiteinvcount.ui.view.inventoryCount;

import java.util.List;

import d2.hu.offsiteinvcount.ui.model.InventoryCount;
import d2.hu.offsiteinvcount.ui.view.base.BaseViewPresenter;
import d2.hu.offsiteinvcount.ui.view.base.RemoteCallBack;


public interface InventoryCounting {
    interface View{

        void showLoading();

        void hideLoading();

        void loadCountBookLineList(InventoryCount invCount);
    }

    interface Presenter extends BaseViewPresenter {

        void getInventoryCountList(RemoteCallBack<List<InventoryCount>> remoteCallBack);

        void getInvCountBookLinesList(String countBook, InventoryCountBookLinesActivity activity, RemoteCallBack<InventoryCount> remoteCallBack);

        void updateInvCountBookLine(String phyCount, String countBookID, int line_number, InventoryCount.CountBookLine countBookItem, RemoteCallBack<Boolean> remoteCallBack);
    }

}
