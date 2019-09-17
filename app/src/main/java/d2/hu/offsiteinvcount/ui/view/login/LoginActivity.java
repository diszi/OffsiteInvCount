package d2.hu.offsiteinvcount.ui.view.login;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import d2.hu.offsiteinvcount.R;
import d2.hu.offsiteinvcount.app.singleton.HolderSingleton;
import d2.hu.offsiteinvcount.app.singleton.SettingsSingleton;
import d2.hu.offsiteinvcount.ui.view.MainActivity;
import d2.hu.offsiteinvcount.ui.view.base.BaseActivity;
import d2.hu.offsiteinvcount.ui.view.base.BaseViewPresenter;
import d2.hu.offsiteinvcount.ui.view.component.SelectIPDialog;

public class LoginActivity extends BaseActivity implements Login.View{

    public static Context mContext;
    private LoginPresenter presenter;
    private SelectIPDialog ipDialog;

    @BindView(R.id.actLogin_Name)
    EditText compLoginName;
    //AutoCompleteTextView compLoginName;

    @BindView(R.id.actLogin_Password)
    EditText compLoginPassword;
    @BindView(R.id.actLogin_Button)
    Button compLoginButton;
    @BindView(R.id.actLogin_ProgressBar)
    ProgressBar compProgressBar;
    @BindView(R.id.version)
    TextView compVersion;

    @BindView(R.id.actSelectIP_button)
    Button compSelectIP;



    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Log.d("---------->","Start LoginActivity");

        setUpFocus();
        this.mContext = getApplicationContext();
        ipDialog = new SelectIPDialog();
        presenter = new LoginPresenter(this);

       // compVersion.setText(EnvironmentTool.getVersionApp());
        HolderSingleton.getInstance().setServerIPaddress("172.31.147.51");
    }


    private void setUpFocus(){
        compLoginName.setNextFocusDownId(R.id.actLogin_Password);
    }

    @Override
    public void onBackPressed(){
        System.out.println("Back Pressed");
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();

        finishAndRemoveTask();
    }


    @OnClick(R.id.actLogin_Button)
    public void onClick(){


        // System.out.println(" ---> onClick on LOGIN button");

        compLoginButton.setEnabled(false);
        compLoginName.setError(null);
        compLoginPassword.setError(null);

        String loginName = compLoginName.getText().toString();
        String password = compLoginPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;





        if (TextUtils.isEmpty(password)){
            cancel = true;
            compLoginPassword.setError("Required");
            focusView = compLoginPassword;
        }

        if (TextUtils.isEmpty(loginName)){
            cancel = true;
            compLoginName.setError("Required");
            focusView = compLoginName;
        }



        if (cancel) {
            focusView.requestFocus();
        } else {

            if (HolderSingleton.getInstance().getServerIPaddress()!=null){
                presenter.login(loginName, password,HolderSingleton.getInstance().getServerIPaddress());

            }else{
                Toast.makeText(this,"Please select IP address!",Toast.LENGTH_SHORT).show();
            }
        }


        compLoginButton.setEnabled(true);
    }

    @OnClick(R.id.actSelectIP_button)
    public void onClickSelectIPaddress(){
        FragmentManager fm = this.getFragmentManager();
        ipDialog.show(fm,"selectIPdialog");
    }

    public void setIPAddress(String ip){
        System.out.println(" ----> "+ip);
        //if (ip!=null){
        compSelectIP.setText(ip);
        //}
    }

    @Override
    public void showLoading() {

        //Log.d("----------> ","Login : showLoading");
        compProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {

        // Log.d("----------> ","Login : hideLoading");
        compProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage(int messageID) {

    }

    @Override
    public void launchMainView() {

        Log.d("---------->","Login : launchFunctionView");
        initApplication();


        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }

    @Override
    protected BaseViewPresenter getBasePresenter() {

        return presenter;
    }

    @Override
    public void setUserToContext(String username) {
        SettingsSingleton.getInstance().init(this,username);
    }


    //  App initialization
    private void initApplication(){
        HolderSingleton.getInstance().setContext(this);
    }



}
