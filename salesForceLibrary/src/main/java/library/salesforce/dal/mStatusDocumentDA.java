package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.mStatusDocumentData;

public class mStatusDocumentDA {
	public mStatusDocumentDA(SQLiteDatabase db) {
		mStatusDocumentData dt = new mStatusDocumentData();
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS "
				+ TABLE_CONTACTS + "(" + dt.Property_intStatus
				+ " TEXT PRIMARY KEY," + dt.Property_txtStatus
				+ " TEXT NULL," + dt.Property_bitActive + " TEXT  NULL)";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// All Static variables

	// Contacts table name
	private static final String TABLE_CONTACTS = new clsHardCode().txtTable_mStatusDocument;

	// Upgrading database
	public void DropTable(SQLiteDatabase db) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	public void SaveDataMConfig(SQLiteDatabase db, mStatusDocumentData data) {
		mStatusDocumentData dt = new mStatusDocumentData();
		db.execSQL("INSERT OR REPLACE into " + TABLE_CONTACTS + " ("
				+ dt.Property_intStatus
				+ "," + dt.Property_txtStatus
				+ ","+ dt.Property_bitActive
				+ ") " + "values('"
				+ String.valueOf(data.get_intStatus()) + "','"
				+ String.valueOf(data.get_txtStatus()) + "','"
				+ String.valueOf(data.get_bitActive()) + "'"
				+ ")");
		// db.insert(TABLE_CONTACTS, null, values);
		// db.close(); // Closing database connection
	}
	public void DeleteAllDataMConfig(SQLiteDatabase db) {
		db.execSQL("DELETE FROM " + TABLE_CONTACTS );
		// db.insert(TABLE_CONTACTS, null, values);
		// db.close(); // Closing database connection
	}
	// Getting single contact
	public mStatusDocumentData getData(SQLiteDatabase db, String id) {
		mStatusDocumentData dt = new mStatusDocumentData();
		Cursor cursor = db.query(TABLE_CONTACTS, new String[] {
				dt.Property_bitActive , dt.Property_intStatus, dt.Property_txtStatus
				},
				dt.Property_intStatus + "=?", new String[] { String.valueOf(id) },
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		mStatusDocumentData contact = new mStatusDocumentData();
		if (cursor.getCount() > 0) {
			contact.set_bitActive(cursor.getString(0));
			contact.set_intStatus(cursor.getString(1));
			contact.set_txtStatus(cursor.getString(2));
			// return contact
		} else {
			contact = null;
		}
		cursor.close();
		return contact;
	}
	
	// Getting All Contacts
	public List<mStatusDocumentData> getAllData(SQLiteDatabase db) {
		List<mStatusDocumentData> contactList = new ArrayList<mStatusDocumentData>();
		// Select All Query
		mStatusDocumentData dt = new mStatusDocumentData();
		String selectQuery = "SELECT  " + dt.PropertyALL + " FROM "
				+ TABLE_CONTACTS +" ORDER BY "+dt.Property_txtStatus+" ";
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		
		if (cursor.moveToFirst()) {
			do {
				mStatusDocumentData contact = new mStatusDocumentData();
				contact.set_bitActive(cursor.getString(0));
				contact.set_intStatus(cursor.getString(1));
				contact.set_txtStatus(cursor.getString(2));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}

	// Deleting single contact
	public void deleteContact(SQLiteDatabase db, String id) {
		mStatusDocumentData dt = new mStatusDocumentData();
		db.delete(TABLE_CONTACTS, dt.Property_intStatus + " = ?",
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
