package it.spasia.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import it.spasia.database.Schema;
import it.spasia.model.Card;
import it.spasia.model.Group;

public class DAOGroup extends AbstractDAO<Group> implements DAO<Group> {

    public DAOGroup(SQLiteDatabase db) {
        super(db);
    }

    public long insert(long id_card, String name, String window) {
        ContentValues insertValues = createContentValues(id_card, name, window);
        return db.insert(Schema.TABLENAME_GROUP, null, insertValues);
    }

    public boolean update(long id, long id_card, String name, String window) {
        return db.update(Schema.TABLENAME_GROUP, createContentValues(id_card, name, window),
                Schema.COLUMN_ID + "=" + id, null) > 0;
    }


    public List<Group> findAll() {
        Cursor c = db.query(Schema.TABLENAME_GROUP, new String[]{
                        Schema.COLUMN_ID,
                        Schema.COLUMN_CARD_GROUP,
                        Schema.COLUMN_NAME,
                        Schema.COLUMN_WINDOW}, null, null, null, null,
                null);
        return cursorToServers(c);
    }

    public Group findById(long id) {
        Cursor c = db.query(Schema.TABLENAME_GROUP, null,
                Schema.COLUMN_ID + " like " + id, null, null,
                null, null);

        if (c != null) {
            c.moveToFirst();
        }
        return cursorToServer(c);
    }

    private ContentValues createContentValues(Long id_card, String name, String window) {
        ContentValues updateValues = new ContentValues();
        updateValues.put(Schema.COLUMN_CARD_GROUP, id_card);
        updateValues.put(Schema.COLUMN_NAME_GROUP, name);
        updateValues.put(Schema.COLUMN_WINDOW, window);
        return updateValues;
    }

    public boolean update(Group group) {
        return update(group.getId(), group.getId_card(), group.getName(), group.getWindow());
    }

    public long insert(Group group) {
        return insert(group.getId_card(), group.getName(), group.getWindow());
    }

    protected Group createServerFromCursor(Cursor c) {
        return new Group(
                c.getInt(Schema.COLUMN_ID_ID),
                c.getInt(Schema.COLUMN_CARD_GROUP_ID),
                c.getString(Schema.COLUMN_NAME_GROUP_ID),
                c.getString(Schema.COLUMN_WINDOW_ID));
    }

}
