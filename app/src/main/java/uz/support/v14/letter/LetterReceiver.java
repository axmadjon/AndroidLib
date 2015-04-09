package uz.support.v14.letter;

public interface LetterReceiver<R, E> {

    void onSuccess(R result);

    void onError(E error);

}
