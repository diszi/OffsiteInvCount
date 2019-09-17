package d2.hu.offsiteinvcount.ui.view.login;

import d2.hu.offsiteinvcount.ui.view.base.BaseViewPresenter;

public interface Login {

    interface View{

        void showLoading();

        void hideLoading();

        void showErrorMessage(int messageID);

        void launchMainView();

        void setUserToContext(String username);

    }

    interface Presenter extends BaseViewPresenter {

        void login(String userName, String password,String ipAddress);
    }
}
