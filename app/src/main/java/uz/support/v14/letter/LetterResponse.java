package uz.support.v14.letter;

public class LetterResponse<R, E> {

    public final LetterContainer<R, E> letter;
    public final LetterReceiver<R, E> receiver;

    public static <R, E> LetterResponse<R, E> newInstance(Letter<R, E> letter, LetterReceiver<R, E> receiver) {
        return new LetterResponse<>(letter, receiver);
    }

    private LetterResponse(Letter<R, E> letter, LetterReceiver<R, E> receiver) {
        this.letter = new LetterContainer<>(letter);
        this.receiver = receiver;
    }

    public boolean hasResult() {
        return letter.hasResult();
    }

    public void callReceiver() {
        if (letter != null && receiver != null) {
            if (hasResult()) {
                if (letter.getResult() != null) {
                    receiver.onSuccess(letter.getResult());
                } else if (letter.getError() != null) {
                    receiver.onError(letter.getError());
                }
            }
        }
    }
}
