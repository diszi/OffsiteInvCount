package d2.hu.offsiteinvcount.ui.scan;

import android.app.Activity;
import android.widget.Toast;

import d2.hu.offsiteinvcount.ui.view.base.BaseViewPresenter;

public abstract class BaseScannerActivity extends Activity implements IOnScannerEvent {

    protected abstract BaseViewPresenter getBasePresenter();

    protected void onDestroy() {
        super.onDestroy();
        System.out.println(" BaseScannerActivity ----------> onDestroy --->  getBasePresenter.onDestory + BarcodeScanner.releaseEmdk");
        getBasePresenter().onDestroy();
        BarcodeScanner.releaseEmdk();

    }

    protected void onResume() {
        super.onResume();
        System.out.println(" BaseScannerActivity ----------> onResume --> ");
        BarcodeScanner.getInstance(this);
        BarcodeScanner.registerUIobject(this);
    }


    public void showErrorMessage(int messageID) {
        Toast.makeText(this, messageID, Toast.LENGTH_SHORT).show();
    }


    protected void onPause() {
        super.onPause();
        System.out.println(" BaseScannerActivity ----------> onPause");
        BarcodeScanner.unregisterUIobject();
    }

    protected void onStop() {
        super.onStop();
        System.out.println(" BaseScannerActivity ----------> onStop");
        BarcodeScanner.deInitScanner();
        BarcodeScanner.releaseEmdk();


    }
}
