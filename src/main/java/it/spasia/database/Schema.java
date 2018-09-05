package it.spasia.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import it.spasia.dao.DAOCard;
import it.spasia.model.Card;

public class Schema {

    public static final String TABLENAME_CARD = "card";
    public static final String TABLENAME_GROUP = "gruppo";

    public static final String COLUMN_ID = "id";
    public static final int COLUMN_ID_ID = 0;

    /**
     * Card column
     */
    public static final String COLUMN_NAME = "name";
    public static final int COLUMN_NAME_ID = 1;

    public static final String COLUMN_UID = "uid";
    public static final int COLUMN_UID_ID = 2;

    public static final String COLUMN_PASSWORD = "password";
    public static final int COLUMN_PASSWORD_ID = 3;

    public static final String COLUMN_TIMEOUT = "timeout";
    public static final int COLUMN_TIMEOUT_ID = 4;

    public static final String COLUMN_RELE_1 = "rele_1";
    public static final int COLUMN_RELE_1_ID = 5;

    public static final String COLUMN_RELE_2 = "rele_2";
    public static final int COLUMN_RELE_2_ID = 6;

    public static final String COLUMN_RELE_3 = "rele_3";
    public static final int COLUMN_RELE_3_ID = 7;

    public static final String COLUMN_RELE_4 = "rele_4";
    public static final int COLUMN_RELE_4_ID = 8;

    public static final String COLUMN_RELE_5 = "rele_5";
    public static final int COLUMN_RELE_5_ID = 9;

    public static final String COLUMN_RELE_6 = "rele_6";
    public static final int COLUMN_RELE_6_ID = 10;

    public static final String COLUMN_RELE_7 = "rele_7";
    public static final int COLUMN_RELE_7_ID = 11;

    public static final String COLUMN_RELE_8 = "rele_8";
    public static final int COLUMN_RELE_8_ID = 12;

    public static final String COLUMN_RELE_9 = "rele_9";
    public static final int COLUMN_RELE_9_ID = 13;

    public static final String COLUMN_RELE_10 = "rele_10";
    public static final int COLUMN_RELE_10_ID = 14;

    public static final String COLUMN_RELE_11 = "rele_11";
    public static final int COLUMN_RELE_11_ID = 15;

    public static final String COLUMN_RELE_12 = "rele_12";
    public static final int COLUMN_RELE_12_ID = 16;

    public static final String COLUMN_RELE_13 = "rele_13";
    public static final int COLUMN_RELE_13_ID = 17;

    public static final String COLUMN_RELE_14 = "rele_14";
    public static final int COLUMN_RELE_14_ID = 18;

    public static final String COLUMN_RELE_15 = "rele_15";
    public static final int COLUMN_RELE_15_ID = 19;

    public static final String COLUMN_RELE_16 = "rele_16";
    public static final int COLUMN_RELE_16_ID = 20;

    public static final String COLUMN_NOTE = "note";
    public static final int COLUMN_NOTE_ID = 21;
    /**
     * Group column
     */
    public static final String COLUMN_CARD_GROUP = "id_card";
    public static final int COLUMN_CARD_GROUP_ID = 1;

    public static final String COLUMN_NAME_GROUP = "name";
    public static final int COLUMN_NAME_GROUP_ID = 2;

    public static final String COLUMN_WINDOW = "window";
    public static final int COLUMN_WINDOW_ID = 3;


    private static final String QUERY_TABLE_CARD_CREATE =
            "create table " + TABLENAME_CARD + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT NOT NULL,"
                    + COLUMN_UID + " TEXT NOT NULL,"
                    + COLUMN_PASSWORD + " TEXT NOT NULL,"
                    + COLUMN_TIMEOUT + " INTEGER NOT NULL,"
                    + COLUMN_RELE_1 + " TEXT,"
                    + COLUMN_RELE_2 + " TEXT,"
                    + COLUMN_RELE_3 + " TEXT,"
                    + COLUMN_RELE_4 + " TEXT,"
                    + COLUMN_RELE_5 + " TEXT,"
                    + COLUMN_RELE_6 + " TEXT,"
                    + COLUMN_RELE_7 + " TEXT,"
                    + COLUMN_RELE_8 + " TEXT,"
                    + COLUMN_RELE_9 + " TEXT,"
                    + COLUMN_RELE_10 + " TEXT,"
                    + COLUMN_RELE_11 + " TEXT,"
                    + COLUMN_RELE_12 + " TEXT,"
                    + COLUMN_RELE_13 + " TEXT,"
                    + COLUMN_RELE_14 + " TEXT,"
                    + COLUMN_RELE_15 + " TEXT,"
                    + COLUMN_RELE_16 + " TEXT,"
                    + COLUMN_NOTE + " TEXT"
                    + ");";

    private static final String QUERY_TABLE_GROUP_CREATE =
            "create table " + TABLENAME_GROUP + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CARD_GROUP + " INTEGER NOT NULL,"
                    + COLUMN_NAME_GROUP + " TEXT NOT NULL,"
                    + COLUMN_WINDOW + " TEXT NOT NULL,"
                    + "FOREIGN KEY(" + COLUMN_CARD_GROUP + ") REFERENCES " + TABLENAME_CARD + "(" + COLUMN_ID + ")"
                    + ");";

    private static final String QUERY_TABLE_CARD_DROP = "DROP TABLE IF EXISTS " + TABLENAME_CARD;
    private static final String QUERY_TABLE_GROUP_DROP = "DROP TABLE IF EXISTS " + TABLENAME_GROUP;

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_TABLE_CARD_CREATE);
        db.execSQL(QUERY_TABLE_GROUP_CREATE);
        //TEST EXAMPLE INSERT
        DAOCard daoCard = new DAOCard(db);
        daoCard.insert("SERRA 1", "******", "*******", 30, "",
                null,null,null,null,
                null,null,null,null,
                null,null,null,null,
                null,null,null,null);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("ServersSchema: ", "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL(QUERY_TABLE_CARD_DROP);
        db.execSQL(QUERY_TABLE_GROUP_DROP);
        onCreate(db);
    }

}
