package it.spasia.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {

    public static final String DATABASE_NAME = "ROMANOROSE";

    private static final int DATABASE_VERSION = 2;

    private final SQLiteOpenHelper cmisDbHelper;
    private SQLiteDatabase sqliteDb;

    protected Database(Context ctx){
        this.cmisDbHelper = new ROMANOROSEDBAdapterHelper(ctx);
    }

    public static Database create(Context ctx) {
        return new Database(ctx);
    }

    public SQLiteDatabase open() {
        if (sqliteDb == null || !sqliteDb.isOpen()) {
            sqliteDb = cmisDbHelper.getWritableDatabase();
        }
        return sqliteDb;
    }

    public void close() {
        sqliteDb.close();
    }


    private static class ROMANOROSEDBAdapterHelper extends SQLiteOpenHelper {
        ROMANOROSEDBAdapterHelper(Context ctx) {
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Schema.onCreate(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Schema.onUpgrade(db, oldVersion, newVersion);
        }
    }
}
