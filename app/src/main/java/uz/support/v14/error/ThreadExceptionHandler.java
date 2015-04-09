/*
 * Copyright (c) 2014. Green White Solutions LLC. Tashkent.
 */

package uz.support.v14.error;

import java.lang.Thread.UncaughtExceptionHandler;

public class ThreadExceptionHandler implements UncaughtExceptionHandler {

    private final UncaughtExceptionHandler defaultExceptionHandler;
    private final OnError onError;

    public ThreadExceptionHandler(UncaughtExceptionHandler defaultExceptionHandler, OnError onError) {
        this.defaultExceptionHandler = defaultExceptionHandler;
        this.onError = onError;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        onError.onError(ex);

        if (defaultExceptionHandler != null) {
            defaultExceptionHandler.uncaughtException(thread, ex);
        }
    }

    public static void register(OnError onError) {
        UncaughtExceptionHandler currentHandler = Thread.getDefaultUncaughtExceptionHandler();

        if (!(currentHandler instanceof ThreadExceptionHandler)) {

            Thread.setDefaultUncaughtExceptionHandler(new ThreadExceptionHandler(currentHandler, onError));
        }
    }

    public interface OnError{

        void onError(Throwable ex);

    }
}
