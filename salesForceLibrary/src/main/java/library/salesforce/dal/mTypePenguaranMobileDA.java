package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.mTypePenguaranMobileData;

public class mTypePenguaranMobileDA {
	public mTypePenguaranMobileDA(SQLiteDatabase db) {
		mTypePenguaranMobileData dt = new mTypePenguaranMobileData();
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS "
				+ TABLE_CONTACTS + "(" + dt.Property_intTypePenguaranMobile
				+ " TEXT PRIMARY KEY," + dt.Property_txtNamaPenguaran
				+ " TEXT NULL," + dt.Property_bitActive + " TEXT  NULL)";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// All Static variables

	// Contacts table name
	private static final String TABLE_CONTACTS = new clsHardCode().txtTable_mTypePenguaranMobile;

	// Upgrading database
	public void DropTable(SQLiteDatabase db) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	public void SaveDataMConfig(SQLiteDatabase db, mTypePenguaranMobileData data) {
		mTypePenguaranMobileData dt = new mTypePenguaranMobileData();
		db.execSQL("INSERT OR REPLACE into " + TABLE_CONTACTS + " ("
				+ dt.PropertyAll
				+ ") " + "values('"
				+ String.valueOf(data.get_bitActive()) + "','"
				+ String.valueOf(data.get_intTypePenguaranMobile()) + "','"
				+ String.valueOf(data.get_txtNamaPenguaran()) + "'"
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
	public mTypePenguaranMobileData getData(SQLiteDatabase db, String id) {
		mTypePenguaranMobileData dt = new mTypePenguaranMobileData();
		Cursor cursor = db.query(TABLE_CONTACTS, new String[] {
				dt.Property_bitActive , dt.Property_intTypePenguaranMobile, dt.Property_txtNamaPenguaran
				},
				dt.Property_intTypePenguaranMobile + "=?", new String[] { String.valueOf(id) },
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		mTypePenguaranMobileData contact = new mTypePenguaranMobileData();
		if (cursor.getCount() > 0) {
			contact.set_bitActive(cursor.getString(0));
			contact.set_intTypePenguaranMobile(cursor.getString(1));
			contact.set_txtNamaPenguaran(cursor.getString(2));
			// return contact
		} else {
			contact = null;
		}
		cursor.close();
		return contact;
	}
	
	// Getting All Contacts
	public List<mTypePenguaranMobileData> getAllData(SQLiteDatabase db) {
		List<mTypePenguaranMobileData> contactList = new ArrayList<mTypePenguaranMobileData>();
		// Select All Query
		mTypePenguaranMobileData dt = new mTypePenguaranMobileData();
		String selectQuery = "SELECT  " + dt.PropertyAll + " FROM "
				+ TABLE_CONTACTS +" ORDER BY "+dt.Property_txtNamaPenguaran+" ";
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		
		if (cursor.moveToFirst()) {
			do {
				mTypePenguaranMobileData contact = new mTypePenguaranMobileData();
				contact.set_bitActive(cursor.getString(0));
				contact.set_intTypePenguaranMobile(cursor.getString(1));
				contact.set_txtNamaPenguaran(cursor.getString(2));
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
		mTypePenguaranMobileData dt = new mTypePenguaranMobileData();
		db.delete(TABLE_CONTACTS, dt.Property_intTypePenguaranMobile + " = ?",
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
