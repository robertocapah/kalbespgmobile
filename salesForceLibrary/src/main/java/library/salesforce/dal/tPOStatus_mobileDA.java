package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.tPOStatus_mobileData;

public class tPOStatus_mobileDA {
	private static final String TABLE_CONTACTS = new clsHardCode().txtTable_tPOStatus_Mobile;

	// Contacts Table Columns names

	public tPOStatus_mobileDA(SQLiteDatabase db) {
		tPOStatus_mobileData dt=new tPOStatus_mobileData();
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
				+ dt.Property_txtDataId + " TEXT PRIMARY KEY,"
				+ dt.Property_txtNoDoc + " TEXT NULL,"
				+ dt.Property_txtNoPO + " TEXT NULL,"
				+ dt.Property_bitActive + " TEXT NULL,"
				+ dt.Property_intStatus + " TEXT NULL,"
				+ dt.Property_txtStatus + " TEXT NULL,"
				+ dt.Property_intSubmit + " TEXT NULL,"
				+ dt.Property_intSync + " TEXT NULL"
				+ ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	public void DropTable(SQLiteDatabase db) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
		// Create tables again
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	public void SaveDatatPOStatus_mobileData(SQLiteDatabase db,tPOStatus_mobileData data) {
		tPOStatus_mobileData dt=new tPOStatus_mobileData();
		db.execSQL("INSERT OR REPLACE into "+TABLE_CONTACTS+" ("+dt.Property_All+") "+
			"values('"	+String.valueOf(data.get_intBitActive())+"','"
				+String.valueOf(data.get_intStatus())+"','"
				+String.valueOf(data.get_intSubmit())+"','"
				+String.valueOf(data.get_intSync())+"','"
				+String.valueOf(data.get_txtDataId())+"','"
				+String.valueOf(data.get_txtNoDoc())+"','"
				+String.valueOf(data.get_txtNoPO())+"','"
				+String.valueOf(data.get_txtStatus())+"'"
				+")");
	}
	public void UpdateDataItem(SQLiteDatabase db,tPOStatus_mobileData data) {
		tPOStatus_mobileData dt=new tPOStatus_mobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intStatus+"="
				+String.valueOf(data.get_intStatus())
				+","+dt.Property_txtStatus+"="
				+String.valueOf(data.get_txtStatus())
				+" Where " + dt.Property_txtDataId +"='"+ data.get_txtDataId()+"'");
	}
	
	public void UpdateDataItemForSubmit(SQLiteDatabase db,String dataid) {
		tPOStatus_mobileData dt=new tPOStatus_mobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intSubmit+"=1"
				+" Where " + dt.Property_txtDataId +"='"+ dataid+"'");
	}
	public void UpdateDataItemForSync(SQLiteDatabase db,String dataid) {
		tPOStatus_mobileData dt=new tPOStatus_mobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intSync+"=1"
				+" Where " + dt.Property_txtDataId +"='"+ dataid+"'");
	}
	public void UpdateDataItemForSubmitByHeaderId(SQLiteDatabase db,String dataid) {
		tPOStatus_mobileData dt=new tPOStatus_mobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intSubmit+"=1"
				+" Where " + dt.Property_txtNoPO +"='"+ dataid+"'");
	}
	public void UpdateDataItemForSyncByHeaderId(SQLiteDatabase db,String dataid) {
		tPOStatus_mobileData dt=new tPOStatus_mobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intSync+"=1"
				+" Where " + dt.Property_txtNoPO +"='"+ dataid+"'");
	}
	// Getting single contact
	public tPOStatus_mobileData getData(SQLiteDatabase db,String id) {
		tPOStatus_mobileData dt=new tPOStatus_mobileData();
		String selectQuery = "SELECT "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1 AND "+dt.Property_intSync+"=0";
		Cursor cursor = db.rawQuery(selectQuery, null);
		tPOStatus_mobileData contact = null;
		if(cursor.getCount()>0){
			contact = new tPOStatus_mobileData();
			if (cursor != null)
				cursor.moveToFirst();
			contact.set_intBitActive(cursor.getString(0));
			contact.set_intStatus(cursor.getString(1));
			contact.set_intSubmit(cursor.getString(2));
			contact.set_intSync(cursor.getString(3));
			contact.set_txtDataId(cursor.getString(4));
			contact.set_txtNoDoc(cursor.getString(5));
			contact.set_txtNoPO(cursor.getString(6));
			contact.set_txtStatus(cursor.getString(7));
		}
		// return contact
		cursor.close();
		return contact;
	}
	
	public boolean CheckDataPushData(SQLiteDatabase db) {		
		tPOStatus_mobileData dt=new tPOStatus_mobileData();
		String selectQuery = "SELECT 1 FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1 AND "+dt.Property_intSync+"=0";
		Cursor cursor = db.rawQuery(selectQuery, null);
		boolean result=false;
		if(cursor.getCount()>0){
			result=true;
		}
		cursor.close();
		// return contact list
		return result;
	}
	
	// Getting All Contacts
	public List<tPOStatus_mobileData> getAllDataToPushData(SQLiteDatabase db) {
		List<tPOStatus_mobileData> contactList = null;
		// Select All Query
		tPOStatus_mobileData dt=new tPOStatus_mobileData();
		String selectQuery = "SELECT "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1 AND "+dt.Property_intSync+"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPOStatus_mobileData>();
			do {
				tPOStatus_mobileData contact = new tPOStatus_mobileData();
				contact.set_intBitActive(cursor.getString(0));
				contact.set_intStatus(cursor.getString(1));
				contact.set_intSubmit(cursor.getString(2));
				contact.set_intSync(cursor.getString(3));
				contact.set_txtDataId(cursor.getString(4));
				contact.set_txtNoDoc(cursor.getString(5));
				contact.set_txtNoPO(cursor.getString(6));
				contact.set_txtStatus(cursor.getString(7));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tPOStatus_mobileData> getAllData(SQLiteDatabase db) {
		List<tPOStatus_mobileData> contactList = null;
		// Select All Query
		tPOStatus_mobileData dt=new tPOStatus_mobileData();
		String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS;

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPOStatus_mobileData>();
			do {
				tPOStatus_mobileData contact = new tPOStatus_mobileData();
				contact.set_intBitActive(cursor.getString(0));
				contact.set_intStatus(cursor.getString(1));
				contact.set_intSubmit(cursor.getString(2));
				contact.set_intSync(cursor.getString(3));
				contact.set_txtDataId(cursor.getString(4));
				contact.set_txtNoDoc(cursor.getString(5));
				contact.set_txtNoPO(cursor.getString(6));
				contact.set_txtStatus(cursor.getString(7));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tPOStatus_mobileData> getAllDataSubmit(SQLiteDatabase db) {
		List<tPOStatus_mobileData> contactList = null;
		// Select All Query
		tPOStatus_mobileData dt=new tPOStatus_mobileData();
		String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPOStatus_mobileData>();
			do {
				tPOStatus_mobileData contact = new tPOStatus_mobileData();
				contact.set_intBitActive(cursor.getString(0));
				contact.set_intStatus(cursor.getString(1));
				contact.set_intSubmit(cursor.getString(2));
				contact.set_intSync(cursor.getString(3));
				contact.set_txtDataId(cursor.getString(4));
				contact.set_txtNoDoc(cursor.getString(5));
				contact.set_txtNoPO(cursor.getString(6));
				contact.set_txtStatus(cursor.getString(7));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tPOStatus_mobileData> getAllDataNotSync(SQLiteDatabase db) {
		List<tPOStatus_mobileData> contactList = null;
		// Select All Query
		tPOStatus_mobileData dt=new tPOStatus_mobileData();
		String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSync +"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPOStatus_mobileData>();
			do {
				tPOStatus_mobileData contact = new tPOStatus_mobileData();
				contact.set_intBitActive(cursor.getString(0));
				contact.set_intStatus(cursor.getString(1));
				contact.set_intSubmit(cursor.getString(2));
				contact.set_intSync(cursor.getString(3));
				contact.set_txtDataId(cursor.getString(4));
				contact.set_txtNoDoc(cursor.getString(5));
				contact.set_txtNoPO(cursor.getString(6));
				contact.set_txtStatus(cursor.getString(7));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tPOStatus_mobileData> getStatusActiveByHeaderId(SQLiteDatabase db,String Id) {
		List<tPOStatus_mobileData> contactList = new ArrayList<tPOStatus_mobileData>();
		// Select All Query
		tPOStatus_mobileData dt=new tPOStatus_mobileData();
		String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtNoPO +"='"+Id+"' AND "+dt.Property_bitActive+"=1";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPOStatus_mobileData>();
			do {
				tPOStatus_mobileData contact = new tPOStatus_mobileData();
				contact.set_intBitActive(cursor.getString(0));
				contact.set_intStatus(cursor.getString(1));
				contact.set_intSubmit(cursor.getString(2));
				contact.set_intSync(cursor.getString(3));
				contact.set_txtDataId(cursor.getString(4));
				contact.set_txtNoDoc(cursor.getString(5));
				contact.set_txtNoPO(cursor.getString(6));
				contact.set_txtStatus(cursor.getString(7));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tPOStatus_mobileData> getAllDataByHeaderId(SQLiteDatabase db,String Id) {
		List<tPOStatus_mobileData> contactList = new ArrayList<tPOStatus_mobileData>();
		// Select All Query
		tPOStatus_mobileData dt=new tPOStatus_mobileData();
		String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtNoPO +"='"+Id+"'";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPOStatus_mobileData>();
			do {
				tPOStatus_mobileData contact = new tPOStatus_mobileData();
				contact.set_intBitActive(cursor.getString(0));
				contact.set_intStatus(cursor.getString(1));
				contact.set_intSubmit(cursor.getString(2));
				contact.set_intSync(cursor.getString(3));
				contact.set_txtDataId(cursor.getString(4));
				contact.set_txtNoDoc(cursor.getString(5));
				contact.set_txtNoPO(cursor.getString(6));
				contact.set_txtStatus(cursor.getString(7));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	// Deleting single contact
	public void deleteContact(SQLiteDatabase db,String id) {		
		tPOStatus_mobileData dt = new tPOStatus_mobileData();
		db.delete(TABLE_CONTACTS, dt.Property_txtDataId + " = ?",
				new String[] { String.valueOf(id) });
	}
	public void DeleteAllDAta(SQLiteDatabase db) {
		// Drop older table if existed
		db.execSQL("DELETE FROM " + TABLE_CONTACTS);
	}
	// Getting contacts Count
	public int getContactsCount(SQLiteDatabase db) {
		String countQuery = "SELECT 1 FROM " + TABLE_CONTACTS;
		Cursor cursor = db.rawQuery(countQuery, null);
		// return count
		int index=cursor.getCount();
		cursor.close();
		return index;
	}
}
