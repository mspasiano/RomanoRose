package it.spasia.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import it.spasia.database.Schema;
import it.spasia.model.Card;

public class DAOCard extends AbstractDAO<Card> implements DAO<Card> {

    public DAOCard(SQLiteDatabase db) {
        super(db);
    }

    public long insert(String name, String uid, String password, Integer timeout, String note,
                       String rele_1, String rele_2, String rele_3, String rele_4,
                       String rele_5, String rele_6, String rele_7, String rele_8,
                       String rele_9, String rele_10, String rele_11, String rele_12,
                       String rele_13, String rele_14, String rele_15, String rele_16) {
        ContentValues insertValues = createContentValues(name, uid, password, timeout, note,
                rele_1, rele_2, rele_3, rele_4,
                rele_5, rele_6, rele_7, rele_8,
                rele_9, rele_10, rele_11, rele_12,
                rele_13, rele_14, rele_15, rele_16);
        return db.insert(Schema.TABLENAME_CARD, null, insertValues);
    }

    public boolean update(long id, String name, String uid, String password, Integer timeout, String note,
                          String rele_1, String rele_2, String rele_3, String rele_4,
                          String rele_5, String rele_6, String rele_7, String rele_8,
                          String rele_9, String rele_10, String rele_11, String rele_12,
                          String rele_13, String rele_14, String rele_15, String rele_16) {
        return db.update(Schema.TABLENAME_CARD, createContentValues(name, uid, password, timeout, note,
                rele_1, rele_2, rele_3, rele_4,
                rele_5, rele_6, rele_7, rele_8,
                rele_9, rele_10, rele_11, rele_12,
                rele_13, rele_14, rele_15, rele_16),
                Schema.COLUMN_ID + "=" + id, null) > 0;
    }


    public List<Card> findAll() {
        Cursor c = db.query(Schema.TABLENAME_CARD, new String[]{
                        Schema.COLUMN_ID,
                        Schema.COLUMN_NAME,
                        Schema.COLUMN_UID,
                        Schema.COLUMN_PASSWORD,
                        Schema.COLUMN_TIMEOUT,
                        Schema.COLUMN_RELE_1,
                        Schema.COLUMN_RELE_2,
                        Schema.COLUMN_RELE_3,
                        Schema.COLUMN_RELE_4,
                        Schema.COLUMN_RELE_5,
                        Schema.COLUMN_RELE_6,
                        Schema.COLUMN_RELE_7,
                        Schema.COLUMN_RELE_8,
                        Schema.COLUMN_RELE_9,
                        Schema.COLUMN_RELE_10,
                        Schema.COLUMN_RELE_11,
                        Schema.COLUMN_RELE_12,
                        Schema.COLUMN_RELE_13,
                        Schema.COLUMN_RELE_14,
                        Schema.COLUMN_RELE_15,
                        Schema.COLUMN_RELE_16,
                        Schema.COLUMN_NOTE
                }, null, null, null, null,
                null);
        return cursorToServers(c);
    }

    public Card findById(long id) {
        Cursor c = db.query(Schema.TABLENAME_CARD, null,
                Schema.COLUMN_ID + " like " + id, null, null,
                null, null);

        if (c != null) {
            c.moveToFirst();
        }
        return cursorToServer(c);
    }

    private ContentValues createContentValues(String name, String uid, String password, Integer timeout, String note,
                                              String rele_1, String rele_2, String rele_3, String rele_4,
                                              String rele_5, String rele_6, String rele_7, String rele_8,
                                              String rele_9, String rele_10, String rele_11, String rele_12,
                                              String rele_13, String rele_14, String rele_15, String rele_16) {
        ContentValues updateValues = new ContentValues();

        updateValues.put(Schema.COLUMN_NAME, name);
        updateValues.put(Schema.COLUMN_UID, uid);
        updateValues.put(Schema.COLUMN_PASSWORD, password);
        updateValues.put(Schema.COLUMN_TIMEOUT, timeout);
        updateValues.put(Schema.COLUMN_NOTE, note);

        updateValues.put(Schema.COLUMN_RELE_1, rele_1);
        updateValues.put(Schema.COLUMN_RELE_2, rele_2);
        updateValues.put(Schema.COLUMN_RELE_3, rele_3);
        updateValues.put(Schema.COLUMN_RELE_4, rele_4);
        updateValues.put(Schema.COLUMN_RELE_5, rele_5);
        updateValues.put(Schema.COLUMN_RELE_6, rele_6);
        updateValues.put(Schema.COLUMN_RELE_7, rele_7);
        updateValues.put(Schema.COLUMN_RELE_8, rele_8);
        updateValues.put(Schema.COLUMN_RELE_9, rele_9);
        updateValues.put(Schema.COLUMN_RELE_10, rele_10);
        updateValues.put(Schema.COLUMN_RELE_11, rele_11);
        updateValues.put(Schema.COLUMN_RELE_12, rele_12);
        updateValues.put(Schema.COLUMN_RELE_13, rele_13);
        updateValues.put(Schema.COLUMN_RELE_14, rele_14);
        updateValues.put(Schema.COLUMN_RELE_15, rele_15);
        updateValues.put(Schema.COLUMN_RELE_16, rele_16);

        return updateValues;
    }

    public boolean update(Card card) {
        return update(card.getId(), card.getName(), card.getUid(), card.getPassword(), card.getTimeout(), card.getNote(),
                card.getRele_1(), card.getRele_2(), card.getRele_3(), card.getRele_4(),
                card.getRele_5(), card.getRele_6(), card.getRele_7(), card.getRele_8(),
                card.getRele_9(), card.getRele_10(), card.getRele_11(), card.getRele_12(),
                card.getRele_13(), card.getRele_14(), card.getRele_15(), card.getRele_16());
    }

    public long insert(Card card) {
        return insert(card.getName(), card.getUid(), card.getPassword(), card.getTimeout(), card.getNote(),
                card.getRele_1(), card.getRele_2(), card.getRele_3(), card.getRele_4(),
                card.getRele_5(), card.getRele_6(), card.getRele_7(), card.getRele_8(),
                card.getRele_9(), card.getRele_10(), card.getRele_11(), card.getRele_12(),
                card.getRele_13(), card.getRele_14(), card.getRele_15(), card.getRele_16());
    }

    public boolean delete(Card card) {
        return delete(card.getId(), Schema.TABLENAME_CARD);
    }

    protected Card createServerFromCursor(Cursor c) {
        return new Card(
                c.getInt(Schema.COLUMN_ID_ID),
                c.getString(Schema.COLUMN_NAME_ID),
                c.getString(Schema.COLUMN_UID_ID),
                c.getString(Schema.COLUMN_PASSWORD_ID),
                c.getInt(Schema.COLUMN_TIMEOUT_ID),
                c.getString(Schema.COLUMN_NOTE_ID),
                c.getString(Schema.COLUMN_RELE_1_ID),
                c.getString(Schema.COLUMN_RELE_2_ID),
                c.getString(Schema.COLUMN_RELE_3_ID),
                c.getString(Schema.COLUMN_RELE_4_ID),
                c.getString(Schema.COLUMN_RELE_5_ID),
                c.getString(Schema.COLUMN_RELE_6_ID),
                c.getString(Schema.COLUMN_RELE_7_ID),
                c.getString(Schema.COLUMN_RELE_8_ID),
                c.getString(Schema.COLUMN_RELE_9_ID),
                c.getString(Schema.COLUMN_RELE_10_ID),
                c.getString(Schema.COLUMN_RELE_11_ID),
                c.getString(Schema.COLUMN_RELE_12_ID),
                c.getString(Schema.COLUMN_RELE_13_ID),
                c.getString(Schema.COLUMN_RELE_14_ID),
                c.getString(Schema.COLUMN_RELE_15_ID),
                c.getString(Schema.COLUMN_RELE_16_ID)
        );
    }

}
