package uz.support.v14.letter.request;

import java.io.InputStream;
import java.io.OutputStream;

public interface Request {

    public void send(OutputStream os) throws Exception;

    public void receive(InputStream is) throws Exception;

}
