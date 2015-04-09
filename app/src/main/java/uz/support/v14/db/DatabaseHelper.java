package uz.support.v14.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context, String databaseName) {
        super(context, databaseName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(Database.TAG, "DB:Creating databse");
        dropTables(db);
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(Database.TAG, "DB:Upgrading database from version " + oldVersion + " to " + newVersion);
        dropTables(db);
        createTables(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(Database.TAG, "DB:Downgrading database from version " + oldVersion + " to " + newVersion);
        dropTables(db);
        createTables(db);
    }

    private void createTables(SQLiteDatabase db) {
        db.execSQL(TableCache.genDDL());
    }

    private void dropTables(SQLiteDatabase db) {
        db.execSQL(TableCache.getDropDDL());
    }
}
