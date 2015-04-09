package uz.support.v14.db;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public class DatabaseFactory {

    private static DatabaseFactory instance;

    public synchronized static DatabaseFactory init(Context ctx) {
        if (instance == null) {
            instance = new DatabaseFactory(ctx);
        }
        return instance;
    }

    public static DatabaseFactory getInstance() {
        if (instance == null) {
            throw new RuntimeException("DatabaseFactory is not init");
        }
        return instance;
    }

    private final Map<String, Database> dbList;
    private final Context ctx;

    private DatabaseFactory(Context ctx) {
        this.dbList = new HashMap<>();
        this.ctx = ctx;
    }

    public Database getDatabase(String dbName) {
        Database db = dbList.get(dbName);
        if (db == null) {
            db = new Database(ctx);
            dbList.put(dbName, db);
        }
        return db;
    }
}
