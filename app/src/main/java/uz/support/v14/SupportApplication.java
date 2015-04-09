package uz.support.v14;

import android.app.Application;
import android.content.Context;

import uz.support.v14.error.ThreadError;
import uz.support.v14.error.ThreadExceptionHandler;
import uz.support.v14.letter.LetterManager;

public class SupportApplication extends Application {

    public static final String TAG = "Support";
    protected LetterManager lm;
    protected Context ctx;

    @Override
    public void onCreate() {
        super.onCreate();
        this.ctx = getApplicationContext();
        this.lm = LetterManager.init(ctx);
        ThreadExceptionHandler.register(new ThreadError());
    }
}
