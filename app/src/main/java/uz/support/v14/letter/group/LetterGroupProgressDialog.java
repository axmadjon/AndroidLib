package uz.support.v14.letter.group;

import android.app.Activity;
import android.app.ProgressDialog;

import uz.support.v14.R;

public class LetterGroupProgressDialog extends LetterGroup {

    final ProgressDialog pd;

    public LetterGroupProgressDialog(Activity activity) {
        pd = new ProgressDialog(activity);
        pd.setMessage(activity.getString(R.string.please_wait));
        pd.setCancelable(false);
    }

    @Override
    public final void start() {
        super.start();
        pd.show();
    }

    @Override
    public final void stop() {
        super.stop();
        pd.dismiss();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
