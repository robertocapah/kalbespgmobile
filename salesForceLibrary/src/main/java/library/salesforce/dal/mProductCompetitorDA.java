package library.salesforce.dal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import library.salesforce.common.mProductCompetitorData;

/**
 * Created by Rian Andrivani on 3/8/2017.
 */

public class mProductCompetitorDA {
    public mProductCompetitorDA(SQLiteDatabase db) {
        mProductCompetitorData dt = new mProductCompetitorData();
        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_CONTACTS + "(" + dt.Property_intmProductUmbrandId
                + " TEXT PRIMARY KEY," + dt.Property_txtAliasName
                + " TEXT NULL," + dt.Property_txtProductCompetitorCode + " TEXT NULL,"
                + dt.Property_txtProductCompetitorName + " TEXT NULL)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // All Static variables

    // Contacts table name
    private static final String TABLE_CONTACTS = new clsHardCode().txtTable_mProductCompetitorData;

    // Upgrading database
    public void DropTable(SQLiteDatabase db) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void SaveDataConfig(SQLiteDatabase db, mProductCompetitorData data) {
        mProductCompetitorData dt = new mProductCompetitorData();
        db.execSQL("INSERT OR REPLACE into " + TABLE_CONTACTS + " ("
            + dt.Property_intmProductUmbrandId
            + "," + dt.Property_txtAliasName
            + "," + dt.Property_txtProductCompetitorCode
            + "," + dt.Property_txtProductCompetitorName
            + ") " + "values('"
            + String.valueOf(data.get_intmProductUmbrandId()) + "','"
            + String.valueOf(data.get_txtAliasName()) + "','"
            + String.valueOf(data.get_txtProductCompetitorCode()) + "','"
            + String.valueOf(data.get_txtProductCompetitorName()) + "'"+ ")");
        // db.insert(TABLE_CONTACTS, null, values);
        // db.close(); // Closing database connection
    }
    public void DeleteAllDataMConfig(SQLiteDatabase db) {
        db.execSQL("DELETE FROM " + TABLE_CONTACTS );
        // db.insert(TABLE_CONTACTS, null, values);
        // db.close(); // Closing database connection
    }
    // Getting single contact
    public mProductCompetitorData getData(SQLiteDatabase db, String id) {
        mProductCompetitorData dt = new mProductCompetitorData();
        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{
                        dt.Property_intmProductUmbrandId, dt.Property_txtAliasName
                        , dt.Property_txtProductCompetitorCode
                        , dt.Property_txtProductCompetitorName
                },
                dt.Property_intmProductUmbrandId + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        mProductCompetitorData contact = new mProductCompetitorData();
        if (cursor.getCount() > 0) {
            contact.set_intmProductUmbrandId(cursor.getString(0));
            contact.set_txtAliasName(cursor.getString(1));
            contact.set_txtProductCompetitorCode(cursor.getString(2));
            contact.set_txtProductCompetitorName(cursor.getString(3));
            // return contact
        } else {
            contact = null;
        }
        cursor.close();
        return contact;
    }

    // Getting All Contacts
    public List<mProductCompetitorData> getAllData(SQLiteDatabase db) {
        List<mProductCompetitorData> contactList = new ArrayList<mProductCompetitorData>();
        // Select All Query
        mProductCompetitorData dt = new mProductCompetitorData();
        String selectQuery = "SELECT " + dt.Property_All + " FROM "
                + TABLE_CONTACTS + " ORDER BY " + dt.Property_txtProductCompetitorName+" ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                mProductCompetitorData contact = new mProductCompetitorData();
                contact.set_intmProductUmbrandId(cursor.getString(0));
                contact.set_txtAliasName(cursor.getString(1));
                contact.set_txtProductCompetitorCode(cursor.getString(2));
                contact.set_txtProductCompetitorName(cursor.getString(3));
                // adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return contact list
        return contactList;
    }

    // Deleting single contact
    public void deleteContact(SQLiteDatabase db, String id) {
        mProductCompetitorData dt = new mProductCompetitorData();
        db.delete(TABLE_CONTACTS, dt.Property_intmProductUmbrandId + " = ?",
                new String[] { String.valueOf(id) });
        // db.close();
    }

    // Getting contacts Count
    public int getContactsCount(SQLiteDatabase db) {
        String countQuery = "SELECT * FROM " + TABLE_CONTACTS;
        Cursor cursor = db.rawQuery(countQuery, null);
        int countData = cursor.getCount();
        cursor.close();
        // return count
        return countData;
    }
}
