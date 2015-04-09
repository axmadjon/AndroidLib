package uz.support.v14.letter.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import uz.support.v14.BuildConfig;
import uz.support.v14.letter.Letter;
import uz.support.v14.letter.LetterContainer;
import uz.support.v14.letter.LetterManager;
import uz.support.v14.letter.request.HttpRequest;

public class FetchService extends IntentService {

    public static final String BROADCAST = BuildConfig.APPLICATION_ID + ".FetchService.BROADCAST";
    private static Intent broadcast = new Intent(BROADCAST);

    public FetchService() {
        super("Support_Fetch_Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            LetterManager manager = LetterManager.getInstance();
            for (final LetterContainer<?, ?> lc : manager.pollLetters()) {
                run(lc);
            }
        } finally {
            //System.gc();
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcast);
        }
    }

    private void run(final LetterContainer<?, ?> lc) {
        try {
            HttpRequest.send(lc.getUrl(),
                    lc, lc.letter.method == Letter.POST, lc.letter.contentType);
        } catch (Throwable thr) {
            lc.error(thr);
        }
    }
}
