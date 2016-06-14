package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.mReasonData;

public class mReasonDA {
	// Contacts table name
	private static final String TABLE_CONTACTS = new clsHardCode().txtTable_mReason;
	
	public mReasonDA(SQLiteDatabase db) {
		mReasonData dt=new mReasonData();
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
				+ dt.Property_intdata + " TEXT PRIMARY KEY," 
				+ dt.Property_txtType + " TEXT NULL,"
				+ dt.Property_txtReason + " TEXT NULL,"
				+ dt.Property_bitActive + " TEXT NULL" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}
	public List<mReasonData> getAllDataByTxtReason(SQLiteDatabase db,String txtType) {
		List<mReasonData> contactList = null;
		// Select All Query
		mReasonData dt=new mReasonData();
		String selectQuery = "SELECT  "+dt.Property_ALL+" FROM " + TABLE_CONTACTS +
							 " WHERE "+dt.Property_txtType + "='"+ txtType + "' ";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
	    contactList=fillData(cursor);
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<mReasonData> getAllDataByTxtReason2(SQLiteDatabase db,String txtReason) {
		List<mReasonData> contactList = null;
		// Select All Query
		mReasonData dt=new mReasonData();
		String selectQuery = "SELECT  "+dt.Property_ALL+" FROM " + TABLE_CONTACTS +
							 " WHERE "+dt.Property_txtType + "='"+ txtReason + "' ";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
	    contactList=fillData(cursor);
		cursor.close();
		// return contact list
		return contactList;
	}
	
	public List<mReasonData> getIdByTxtReason(SQLiteDatabase db,String txtReason, String txtType) {
		List<mReasonData> contactList = null;
		// Select All Query
		mReasonData dt=new mReasonData();
		String selectQuery = "SELECT  "+dt.Property_ALL+" FROM " + TABLE_CONTACTS +
							 " WHERE "+dt.Property_txtReason + "='"+ txtReason + "' AND " + dt.Property_txtType + "='" + txtType + "' ";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
	    contactList=fillData(cursor);
		cursor.close();
		// return contact list
		return contactList;
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
	public void SaveDataMReasonData(SQLiteDatabase db,mReasonData data){
		mReasonData dt=new mReasonData();
		// Inserting Row
		db.execSQL("INSERT OR REPLACE into "+TABLE_CONTACTS+" ("+dt.Property_intdata+","
				   +dt.Property_txtType+","
				   +dt.Property_txtReason+","
				   +dt.Property_bitActive+") "+
					"values('"	+String.valueOf(data.get_intData())+"','"
				   +String.valueOf(data.get_txtType())+"','"
				   +String.valueOf(data.get_txtReason())+"','"
				   +String.valueOf(data.get_bitActive())+"')");
	}
		
	// Getting single contact
	public mReasonData getData(SQLiteDatabase db,String id) {
		mReasonData dt=new mReasonData();
		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { 
				 dt.Property_intdata
				,dt.Property_txtType
				,dt.Property_txtReason 
				,dt.Property_bitActive}, dt.Property_intdata + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		mReasonData contact = null;
		if(cursor.getCount()>0){
			contact = new mReasonData();
			if (cursor != null)
				cursor.moveToFirst();
				contact.set_intData(cursor.getString(0));
				contact.set_txtType(cursor.getString(1));
				contact.set_txtReason(cursor.getString(2));
				contact.set_bitActive(cursor.getString(3));
		}
		// return contact
		cursor.close();
		return contact;
	}
		
	public List<mReasonData> fillData(Cursor cursor){
		List<mReasonData> contactList=null;
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<mReasonData>();
			do {
				mReasonData contact = new mReasonData();
				contact.set_intData(cursor.getString(0));
				contact.set_txtType(cursor.getString(1));
				contact.set_txtReason(cursor.getString(2));
				contact.set_bitActive(cursor.getString(3));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		return contactList;
	}
	
	public List<mReasonData> getAllData(SQLiteDatabase db) {
		List<mReasonData> contactList = null;
		// Select All Query
		mReasonData dt=new mReasonData();
		String selectQuery = "SELECT  "+dt.Property_ALL+" FROM " + TABLE_CONTACTS;

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
	    contactList=fillData(cursor);
		cursor.close();
		// return contact list
		return contactList;
	}
	
	// Deleting single contact
	public void deleteContact(SQLiteDatabase db,String id) {		
		mReasonData dt = new mReasonData();
		db.delete(TABLE_CONTACTS, dt.Property_intdata + " = ?",
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
