package uz.support.v14.letter;

import java.io.InputStream;
import java.io.OutputStream;

import uz.support.v14.letter.request.Request;

public class LetterContainer<S, E> implements Request {

    public final Letter<S, E> letter;
    private S result;
    private E error;

    public LetterContainer(Letter<S, E> letter) {
        this.letter = letter;
    }

    public boolean hasResult() {
        return result != null || error != null;
    }

    public String getUrl() {
        return letter.url;
    }

    public S getResult() {
        return result;
    }

    public E getError() {
        return error;
    }

    public void error(Throwable throwable) {
        error = letter.parseNetworkError(throwable);
    }

    @Override
    public void send(OutputStream os) throws Exception {
        letter.parseNetworkRequest(os);
    }

    @Override
    public void receive(InputStream is) throws Exception {
        result = letter.parseNetworkResponse(is);
    }
}
