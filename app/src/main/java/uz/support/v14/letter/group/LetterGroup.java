package uz.support.v14.letter.group;

import java.util.ArrayList;

import uz.support.v14.letter.Letter;
import uz.support.v14.letter.LetterReceiver;
import uz.support.v14.letter.LetterResponse;

public abstract class LetterGroup {

    final ArrayList<LetterResponse<?, ?>> requests = new ArrayList<>();

    public <S, R> void add(final Letter<S, R> letter, final LetterReceiver<S, R> receiver) {
        LetterReceiver<S, R> rec = new LetterReceiver<S, R>() {
            @Override
            public void onSuccess(S result) {
                receiver.onSuccess(result);
                onResult(letter);
            }

            @Override
            public void onError(R error) {
                receiver.onError(error);
                onResult(letter);
            }
        };
        requests.add(LetterResponse.newInstance(letter, rec));
    }

    private <S, R> void onResult(final Letter<S, R> letter) {
        requests.remove(letter);
        if (requests.size() == 0) {
            stop();
        }
    }

    protected void start() {
        onStart();
    }

    protected void stop() {
        onStop();
    }

    public abstract void onStart();

    public abstract void onStop();
}
