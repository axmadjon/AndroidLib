package uz.support.v14.letter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import uz.support.v14.letter.error.LetterException;
import uz.support.v14.letter.service.FetchService;

public class LetterManager {

    private static LetterManager instance;

    public static LetterManager init(Context context) {
        if (context == null) {
            throw new LetterException("Context is null");
        }
        if (instance == null) {
            instance = new LetterManager(context);
        }
        return instance;
    }

    public static LetterManager getInstance() {
        if (instance == null) {
            throw new LetterException("LetterManager is not initial");
        }
        return instance;
    }

    public static Context getContext() {
        return instance.ctx;
    }

    private final List<LetterContainer<?, ?>> letters;
    private final Context ctx;
    private final Handler mainHandler;

    private LetterManager(Context context) {
        this.letters = new ArrayList<>();
        this.ctx = context;
        this.mainHandler = new Handler(context.getMainLooper());
    }

    public void push(LetterContainer<?, ?> letter) {
        synchronized (this) {
            letters.add(letter);
        }
        mainHandler.post(POST_LETTER);
    }

    public List<LetterContainer<?, ?>> pollLetters() {
        synchronized (this) {
            List<LetterContainer<?, ?>> result = letters;
            letters.clear();
            return result;
        }
    }

    private final Runnable POST_LETTER = new Runnable() {

        @Override
        public void run() {
            if (letters != null) {
                ctx.startService(new Intent(ctx, FetchService.class));
            }
        }
    };
}
