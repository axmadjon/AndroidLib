package uz.support.v14.demo.request;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import uz.support.v14.SupportApplication;
import uz.support.v14.letter.request.HttpRequest;
import uz.support.v14.letter.Letter;

public abstract class DemoLetter<R, E> extends Letter<R, E> {

    public DemoLetter() {
        super("http://192.168.1.25:8080/aylana/control/do/env/stream/req");
    }

    @Override
    protected R parseNetworkResponse(InputStream is) throws IOException {
        System.out.println(HttpRequest.makeString(is));
        return null;
    }

    @Override
    protected E parseNetworkError(Throwable throwable) {
        Log.d(SupportApplication.TAG, throwable.getLocalizedMessage());
        return null;
    }

    @Override
    protected void parseNetworkRequest(OutputStream os) throws IOException {
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(os, "UTF8"), true);
        writer.print("[{\"user_id\":\"22\"},{\"n\":\"user_gcm_register\",\"d\":{\"register_code\":\"APA91bF2ZEMHN4Tj5kqk-ZkBoPauDwvTPCgab1jmfx7NNYBSBOavrB3Yyun5AaoRHIWJPYvUnMeosflpqghtTJL6DC08SeTIHqMB5bpSDbMR6jmMhgwqINJEibdEoR8rXP8weHxugP52A_4Oj9n5XEGoyKssSLzbNw\"},\"i\":\"4\"}]");
        writer.flush();
    }
}
