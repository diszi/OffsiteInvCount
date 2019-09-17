package d2.hu.offsiteinvcount.ui.view.base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
    }


    protected void onPause() {
        super.onPause();

    }


    protected void onResume() {
        super.onResume();
    }

    protected abstract BaseViewPresenter getBasePresenter();


    protected void onDestroy() {
        super.onDestroy();
        //System.out.println(" BaseActivity >> basePresenter = "+getBasePresenter());
        getBasePresenter().onDestroy();

    }

    public void showErrorMessage(int messageID) {
        Toast.makeText(this, messageID, Toast.LENGTH_SHORT).show();
    }

    public void showSuccessMessage() {
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }
}
