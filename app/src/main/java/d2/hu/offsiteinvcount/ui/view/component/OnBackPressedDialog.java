package d2.hu.offsiteinvcount.ui.view.component;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import d2.hu.offsiteinvcount.R;
import d2.hu.offsiteinvcount.app.singleton.HolderSingleton;
import d2.hu.offsiteinvcount.ui.view.login.LoginActivity;

public class OnBackPressedDialog extends DialogFragment {

    /**
     *  Create custom Dialog object
     * @param savedInstanceState - the last saved instance state of the Fragment, or null if this is a freshly created Fragment
     * @return - a new Dialog instance to be displayed by the Fragment.
     */
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Log out
        return new AlertDialog.Builder(getActivity()).setMessage(R.string.logout_question).setPositiveButton(R.string.text_YES, (dialogInterface, i) -> {

            HolderSingleton.getInstance().setServerIPaddress(null);
            Intent intent = new Intent(getContext(),LoginActivity.class);
            intent.putExtra("backPressed","true");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }).setNegativeButton(R.string.text_NO, (dialogInterface, i) -> dismiss()).create();
    }
}
