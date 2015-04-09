package uz.support.v14.letter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Letter<R, E> {

    private static final String UTF8 = "utf-8";

    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", UTF8);

    public static final int POST = 0;
    public static final int GET = 1;

    public final String url;
    public final int method;
    public final String contentType;

    protected Letter(String url, int method, String contentType) {
        this.url = url;
        this.method = method;
        this.contentType = contentType;
    }

    protected Letter(String url, int method) {
        this(url, method, PROTOCOL_CONTENT_TYPE);
    }

    protected Letter(String url) {
        this(url, POST);
    }

    protected abstract R parseNetworkResponse(InputStream is) throws IOException;

    protected abstract E parseNetworkError(Throwable throwable);

    protected abstract void parseNetworkRequest(OutputStream os) throws IOException;

    public boolean isCache() {
        return false;
    }
}

