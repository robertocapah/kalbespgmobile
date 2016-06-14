package library.salesforce.dal;
import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.tUserLOBData;

public class tUserLOBDA {
	
	public tUserLOBDA(SQLiteDatabase db) {
		tUserLOBData dt = new tUserLOBData();
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS "
				+ TABLE_CONTACTS + "(" + dt.Property_intLOB
				+ " TEXT PRIMARY KEY," + dt.Property_txtLOB
				+ " TEXT NULL," + dt.Property_txtNik + " TEXT  NULL)";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// All Static variables

	// Contacts table name
	private static final String TABLE_CONTACTS = new clsHardCode().txtTable_tUserLOB;

	// Upgrading database
	public void DropTable(SQLiteDatabase db) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	public void SaveDataMConfig(SQLiteDatabase db, tUserLOBData data) {
		tUserLOBData dt = new tUserLOBData();
		db.execSQL("INSERT OR REPLACE into " + TABLE_CONTACTS + " ("
				+ dt.Property_All
				+ ") " + "values('"
				+ String.valueOf(data.get_intLOB()) + "','"
				+ String.valueOf(data.get_txtLOB()) + "','"
				+ String.valueOf(data.get_txtNik()) + "'"
				+ ")");
	}
	public void DeleteAllDataMConfig(SQLiteDatabase db) {
		db.execSQL("DELETE FROM " + TABLE_CONTACTS );
	}
	// Getting single contact
	public tUserLOBData getData(SQLiteDatabase db, String id) {
		tUserLOBData dt = new tUserLOBData();
		Cursor cursor = db.query(TABLE_CONTACTS, new String[] {
				dt.Property_intLOB , dt.Property_txtLOB 
				, dt.Property_txtNik},
				dt.Property_intLOB + "=?", new String[] { String.valueOf(id) },
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		tUserLOBData contact = new tUserLOBData();
		if (cursor.getCount() > 0) {
			contact.set_intLOB(cursor.getString(0));
			contact.set_txtLOB(cursor.getString(1));
			contact.set_txtNik(cursor.getString(2));
		} else {
			contact = null;
		}
		cursor.close();
		return contact;
	}
	public tUserLOBData getDataByOutlet(SQLiteDatabase db, String id) {
		tUserLOBData dt = new tUserLOBData();
		Cursor cursor = db.query(TABLE_CONTACTS, new String[] {
				dt.Property_intLOB , dt.Property_txtLOB 
				, dt.Property_txtNik
				},
				dt.Property_intLOB + "=?", new String[] { String.valueOf(id) },
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		tUserLOBData contact = new tUserLOBData();
		if (cursor.getCount() > 0) {
			contact.set_intLOB(cursor.getString(0));
			contact.set_txtLOB(cursor.getString(1));
			contact.set_txtNik(cursor.getString(2));
			// return contact
		} else {
			contact = null;
		}
		cursor.close();
		return contact;
	}
	
	// Getting All Contacts
	public List<tUserLOBData> getAllData(SQLiteDatabase db) {
		List<tUserLOBData> contactList = new ArrayList<tUserLOBData>();
		// Select All Query
		tUserLOBData dt = new tUserLOBData();
		String selectQuery = "SELECT  " + dt.Property_All + " FROM "
				+ TABLE_CONTACTS;
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		
		if (cursor.moveToFirst()) {
			do {
				tUserLOBData contact = new tUserLOBData();
				contact.set_intLOB(cursor.getString(0));
				contact.set_txtLOB(cursor.getString(1));
				contact.set_txtNik(cursor.getString(2));
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
		tUserLOBData dt = new tUserLOBData();
		db.delete(TABLE_CONTACTS, dt.Property_intLOB + " = ?",
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
