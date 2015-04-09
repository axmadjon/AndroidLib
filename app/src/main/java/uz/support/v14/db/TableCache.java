package uz.support.v14.db;

import android.database.sqlite.SQLiteDatabase;


public class TableCache {

    private SQLiteDatabase db;

    public TableCache(SQLiteDatabase db) {
        this.db = db;
    }

    public String loadOne(String code) {
        String query = "SELECT val FROM oc_cache WHERE code = ?";
        return CursorUtil.loadOne(db, CursorUtil.ONLY_STRING, query, code);
    }

    public void replace(String code, String val) {
        String query = "INSERT OR REPLACE INTO oc_cache(code, val) VALUES (?,?)";
        db.execSQL(query, new Object[]{code, val});
    }

    public static String genDDL() {
        return new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS oc_cache(")
                .append("code TEXT NOT NULL,")
                .append("val TEXT NOT NULL,")
                .append("PRIMARY KEY(code))")
                .toString();
    }

    public static String getDropDDL() {
        return "DROP TABLE IF EXISTS oc_cache";
    }
}
