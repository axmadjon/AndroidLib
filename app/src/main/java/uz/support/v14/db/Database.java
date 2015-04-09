package uz.support.v14.db;

import android.content.Context;

public class Database {

    private static final String CACHE = "cache";

    public static final String TAG = "uz.support.v14.DB";

    public final TableCache cache;

    public Database(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context, "support_v14.db");
        this.cache = new TableCache(helper.getWritableDatabase());
    }
}
