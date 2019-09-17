package d2.hu.offsiteinvcount.ui.view.component;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import d2.hu.offsiteinvcount.R;
import d2.hu.offsiteinvcount.app.singleton.HolderSingleton;
import d2.hu.offsiteinvcount.ui.view.login.LoginActivity;

public class SelectIPDialog extends DialogFragment {



    @BindView(R.id.selectDialog_title)
    TextView title;
    @BindView(R.id.button_eles_sim)
    Button button41SIM;
    @BindView(R.id.button_eles_wifi)
    Button button41WIFI;

    @BindView(R.id.button_teszt_sim)
    Button button51SIM;
    @BindView(R.id.button_teszt_wifi)
    Button button51WIFI;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("------------------>", "Start SelectIPDialog ");

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View contentView = inflater.inflate(R.layout.dialog_select_ipaddress, container, false);

        ButterKnife.bind(this, contentView);



        return contentView;

    }


    @OnClick(R.id.button_teszt_sim)
    public void onClickTestSIM(){
        HolderSingleton.getInstance().setServerIPaddress("172.31.147.51");
      //  System.out.println(" ---> selected IP = "+HolderSingleton.getInstance().getServerIPaddress());

        ((LoginActivity)getActivity()).setIPAddress("TEST - SIM");
        dismiss();
    }


    @OnClick(R.id.button_teszt_wifi)
    public void onClickTestWIFI(){
        HolderSingleton.getInstance().setServerIPaddress("192.168.133.51");
        ((LoginActivity)getActivity()).setIPAddress("TEST - WIFI");
        dismiss();
    }


    @OnClick(R.id.button_eles_sim)
    public void onClickElesSIM(){
        HolderSingleton.getInstance().setServerIPaddress("172.31.147.41:9080");
//        ((LoginActivity)getActivity()).setIPAddress(HolderSingleton.getInstance().getServerIPaddress());
        ((LoginActivity)getActivity()).setIPAddress("PRODUCTION - SIM");
        dismiss();
    }


    @OnClick(R.id.button_eles_wifi)
    public void onClickElesWIFI(){
        HolderSingleton.getInstance().setServerIPaddress("192.168.133.41:9080");
//        ((LoginActivity)getActivity()).setIPAddress(HolderSingleton.getInstance().getServerIPaddress());
        ((LoginActivity)getActivity()).setIPAddress("PRODUCTION - WIFI");

        dismiss();
    }

}
