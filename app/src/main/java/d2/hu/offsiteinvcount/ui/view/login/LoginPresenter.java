package d2.hu.offsiteinvcount.ui.view.login;

import android.util.Base64;
import android.util.Log;

import java.net.HttpURLConnection;

import d2.hu.offsiteinvcount.R;
import d2.hu.offsiteinvcount.app.CustomerProperties;
import d2.hu.offsiteinvcount.ui.view.base.BasePresenter;
import d2.hu.offsiteinvcount.util.NetworkTool;
import d2.hu.offsiteinvcount.util.UIThrowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter implements Login.Presenter{

    private Login.View loginView;
    private Disposable disposable;


    public LoginPresenter(Login.View view){
        this.loginView = view;
    }

    @Override
    public void onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void login(String userName, String password, String ipAddress) {
        Log.d("------------------>","Login presenter");
        loginView.showLoading();

        disposable = createObservable(userName, password,ipAddress).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnNext((param) -> {
                }).subscribe((object) -> { // onNext Consumer

                }, (throwable) -> { // onError Consumer
                    UIThrowable uiThrowable = (UIThrowable) throwable;
                    loginView.showErrorMessage(uiThrowable.getMessage());
                    loginView.hideLoading();
                }, () -> { // onComplate Action
                    loginView.setUserToContext(userName);
                    loginView.launchMainView();
                });
    }


    public Observable createObservable(String userName, String password, String ipaddress) {
        Observable result = Observable.create(emitter -> {
            try {


                HttpURLConnection connection = null;
                try {
                    CustomerProperties.setURLs(ipaddress);
                    connection = NetworkTool.createConnection(CustomerProperties.LOGIN_URL);

                    String credentials = userName + ":" + password;
                    String base64Credentials = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
                    connection.setRequestProperty("MAXAUTH", base64Credentials); //bWF4YWRtaW46SGUxMW9vNzAu
                    connection.connect();

                    int responseCode = connection.getResponseCode();
                    Log.i("------------------>","Connection response="+responseCode);

                    if (responseCode == 200) {
                        emitter.onComplete();
                    } else if (responseCode == 400) {
                        emitter.onError(new UIThrowable(R.string.error_wrongUser));
                        Log.e("------------------>","Wrong User");
                    } else {
                        emitter.onError(new UIThrowable(R.string.error_authFailed));
                        Log.e("------------------>","Authentication Failed");
                    }

                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            } catch (Exception ex) {
                Log.e("", "---------->", ex);
                if (ex.getMessage().equals("Network is unreachable")){
                    emitter.onError(new UIThrowable(R.string.error_network));
                    Log.e("------------------>","Network Erro");
                }else{
                    emitter.onError(new UIThrowable(R.string.error_unknown));
                    Log.e("------------------>","Unknown Error");
                }


            }

        });

        return result;
    }







}
