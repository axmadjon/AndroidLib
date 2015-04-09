package uz.support.v14.letter.error;

public class LetterException extends RuntimeException {

    public LetterException() {
    }

    public LetterException(String detailMessage) {
        super(detailMessage);
    }

    public LetterException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public LetterException(Throwable throwable) {
        super(throwable);
    }
}
