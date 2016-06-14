package library.salesforce.dal;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import library.salesforce.common.tEmployeeBRWithLOBData;
import library.salesforce.common.tEmployeeBRWithLOBData;

public class tEmployeeBRWithLOBDA {
	
	public tEmployeeBRWithLOBDA(SQLiteDatabase db) {
		tEmployeeBRWithLOBData dt = new tEmployeeBRWithLOBData();
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS "
				+ TABLE_CONTACTS + "(" + dt.Property_IntEmployeeDetailId
				+ " TEXT PRIMARY KEY," + dt.Property_IntEmployeeId
				+ " TEXT NULL," + dt.Property_IntlobId + " TEXT  NULL,"
				+ dt.Property_TxtLobName + " TEXT  NULL)";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// All Static variables

	// Contacts table name
	private static final String TABLE_CONTACTS = new clsHardCode().txtTable_tEmployeeBRWithLOB;

	// Upgrading database
	public void DropTable(SQLiteDatabase db) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	public void SaveDataMConfig(SQLiteDatabase db, tEmployeeBRWithLOBData data) {
		tEmployeeBRWithLOBData dt = new tEmployeeBRWithLOBData();
		db.execSQL("INSERT OR REPLACE into " + TABLE_CONTACTS + " ("
				+ dt.Property_All
				+ ") " + "values('"
				+ String.valueOf(data.get_IntEmployeeDetailId()) + "','"
				+ String.valueOf(data.get_IntEmployeeId()) + "','"
				+ String.valueOf(data.get_IntlobId()) + "','"
				+ String.valueOf(data.get_TxtLobName()) + "'"
				+ ")");
	}
	public void DeleteAllDataMConfig(SQLiteDatabase db) {
		db.execSQL("DELETE FROM " + TABLE_CONTACTS );
	}
	// Getting single contact
	public tEmployeeBRWithLOBData getData(SQLiteDatabase db, String id) {
		tEmployeeBRWithLOBData dt = new tEmployeeBRWithLOBData();
		Cursor cursor = db.query(TABLE_CONTACTS, new String[] {
				dt.Property_IntEmployeeDetailId , dt.Property_IntEmployeeId 
				, dt.Property_IntlobId
				, dt.Property_TxtLobName },
				dt.Property_IntlobId + "=?", new String[] { String.valueOf(id) },
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		tEmployeeBRWithLOBData contact = new tEmployeeBRWithLOBData();
		if (cursor.getCount() > 0) {
			contact.set_IntEmployeeDetailId(cursor.getString(0));
			contact.set_IntEmployeeId(cursor.getString(1));
			contact.set_IntlobId(cursor.getString(2));
			contact.set_TxtLobName(cursor.getString(3));
		} else {
			contact = null;
		}
		cursor.close();
		return contact;
	}
	
	// Getting All Contacts
	public List<tEmployeeBRWithLOBData> getAllData(SQLiteDatabase db) {
		List<tEmployeeBRWithLOBData> contactList = new ArrayList<tEmployeeBRWithLOBData>();
		// Select All Query
		tEmployeeBRWithLOBData dt = new tEmployeeBRWithLOBData();
		String selectQuery = "SELECT  " + dt.Property_All + " FROM "
				+ TABLE_CONTACTS;
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		
		if (cursor.moveToFirst()) {
			do {
				tEmployeeBRWithLOBData contact = new tEmployeeBRWithLOBData();
				contact.set_IntEmployeeDetailId(cursor.getString(0));
				contact.set_IntEmployeeId(cursor.getString(1));
				contact.set_IntlobId(cursor.getString(2));
				contact.set_TxtLobName(cursor.getString(3));
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
		tEmployeeBRWithLOBData dt = new tEmployeeBRWithLOBData();
		db.delete(TABLE_CONTACTS, dt.Property_IntlobId + " = ?",
				new String[] { String.valueOf(id) });
		// db.close();
	}
	// Deleting single contact
		public void deleteContact(SQLiteDatabase db) {
			db.delete(TABLE_CONTACTS, null,null);
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
