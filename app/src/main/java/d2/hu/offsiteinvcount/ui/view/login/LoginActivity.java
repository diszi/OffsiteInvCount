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
import d2.hu.offsiteinvcount.util.EnvironmentTool;

public class LoginActivity extends BaseActivity implements Login.View{

    public static Context mContext;
    private LoginPresenter presenter;
    private SelectIPDialog ipDialog;

    @BindView(R.id.actLogin_Name)
    EditText compLoginName;
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
        Log.d("------------------>","Start LoginActivity");

        setUpFocus();
        this.mContext = getApplicationContext();
        ipDialog = new SelectIPDialog();
        presenter = new LoginPresenter(this);
        compVersion.setText(EnvironmentTool.getVersionApp());
    }


    private void setUpFocus(){
        compLoginName.setNextFocusDownId(R.id.actLogin_Password);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        super.onBackPressed();
    }



    @OnClick(R.id.actLogin_Button)
    public void onClick(){

        compLoginButton.setEnabled(false);
        compLoginName.setError(null);
        compLoginPassword.setError(null);

        String loginName = compLoginName.getText().toString();
        String password = compLoginPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(password)){
            cancel = true;
            compLoginPassword.setError(getResources().getString(R.string.error_isRequired));
            focusView = compLoginPassword;
        }

        if (TextUtils.isEmpty(loginName)){
            cancel = true;
            compLoginName.setError(getResources().getString(R.string.error_isRequired));
            focusView = compLoginName;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            if (HolderSingleton.getInstance().getServerIPaddress()!=null){
                presenter.login(loginName, password,HolderSingleton.getInstance().getServerIPaddress());

            }else{
                Toast.makeText(this,getResources().getString(R.string.text_selectIP),Toast.LENGTH_SHORT).show();
                Log.d("------------------>",getResources().getString(R.string.text_selectIP));
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
        compSelectIP.setText(ip);
    }

    @Override
    public void showLoading() {
        compProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        compProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage(String msg) {
        Log.e("------------------>",msg);
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void launchMainView() {
        Log.d("------------------>","Login : launchFunctionView");
        hideLoading();
        initApplication();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



    @Override
    public void setUserToContext(String username) {
        SettingsSingleton.getInstance().init(this,username);
    }


    private void initApplication(){
        HolderSingleton.getInstance().setContext(this);
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

    @Override
    protected BaseViewPresenter getBasePresenter() {
        return presenter;
    }




}
