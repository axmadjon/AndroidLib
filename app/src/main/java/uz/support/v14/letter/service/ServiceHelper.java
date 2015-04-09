package uz.support.v14.letter.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;

import uz.support.v14.letter.Letter;
import uz.support.v14.letter.LetterManager;
import uz.support.v14.letter.LetterReceiver;
import uz.support.v14.letter.LetterResponse;
import uz.support.v14.letter.group.LetterGroup;

public class ServiceHelper {

    private final List<LetterResponse<?, ?>> responses;
    private boolean started;

    public ServiceHelper() {
        this.responses = new ArrayList<>();
        this.started = false;
    }

    public <R, E> void send(Letter<R, E> letter, LetterReceiver<R, E> receiver) {
        LetterResponse<R, E> r = LetterResponse.newInstance(letter, receiver);
        responses.add(r);
        if (started) {
            LetterManager.getInstance().push(r.letter);
        }
    }

    public void onStart() {
        IntentFilter filter = new IntentFilter(FetchService.BROADCAST);
        LocalBroadcastManager.getInstance(LetterManager.getContext()).registerReceiver(ON_RESULT, filter);
        this.started = true;
    }

    public void onStop() {
        LocalBroadcastManager.getInstance(LetterManager.getContext()).unregisterReceiver(ON_RESULT);
        this.started = false;
    }

    private void onResult() {
        if (responses == null || !started) {
            return;
        }
        for (final LetterResponse<?, ?> r : responses) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    r.callReceiver();
                    responses.remove(r);
                }
            });
        }
    }

    private final BroadcastReceiver ON_RESULT = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            onResult();
        }

    };
}
