package d2.hu.offsiteinvcount.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import d2.hu.offsiteinvcount.R;
import d2.hu.offsiteinvcount.app.singleton.SettingsSingleton;
import d2.hu.offsiteinvcount.ui.view.component.OnBackPressedDialog;
import d2.hu.offsiteinvcount.ui.view.inventoryCount.InventoryCountListActivity;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.package_inventoryCount)
    CardView inventoryCountCard;


    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.i("------------------>","START MainActivity ");

    }


    public void inventoryCount(View view) {
        Intent intent = new Intent(this, InventoryCountListActivity.class);
        startActivity(intent);
    }



    @Override
    public void onBackPressed() {
        android.app.FragmentManager fm = getFragmentManager();
        OnBackPressedDialog alertDialogFragment = new OnBackPressedDialog();
        alertDialogFragment.show(fm,"AlertDialogFragment");

    }

    public String getLoggedInUser() {
        return SettingsSingleton.getInstance().getUserName();
    }



    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
    }






}
