package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.tPenguaranStatus_MobileData;

public class tPenguaranStatus_MobileDA {
	// All Static variables

	// Contacts table name
	private static final String TABLE_CONTACTS = new clsHardCode().txtTable_tPenguaranStatus_Mobile;

	// Contacts Table Columns names
	
	public tPenguaranStatus_MobileDA(SQLiteDatabase db) {
		tPenguaranStatus_MobileData dt = new tPenguaranStatus_MobileData();
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
										+ dt.Property_txtDataId + " TEXT PRIMARY KEY," 
										+ dt.Property_txtNoPenguaran + " TEXT NULL,"
										+ dt.Property_intStatus + " TEXT NULL,"
										+ dt.Property_txtStatus + " TEXT NULL,"
										+ dt.Property_bitActive + " TEXT NULL,"
										+ dt.Property_intSubmit + " TEXT NULL,"
										+ dt.Property_intSync + " TEXT NULL" + ")";
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
	public void SaveDatatSalesProductHeaderData(SQLiteDatabase db,tPenguaranStatus_MobileData data) {
		tPenguaranStatus_MobileData dt=new tPenguaranStatus_MobileData();
		// Inserting Row
		db.execSQL("INSERT OR REPLACE into "+TABLE_CONTACTS+" ("+dt.Property_txtDataId+","
					+dt.Property_txtNoPenguaran+","
					+dt.Property_intStatus+","
					+dt.Property_txtStatus+","	
					+dt.Property_bitActive+","	
					+dt.Property_intSubmit+","	
					+dt.Property_intSync+") "+
				"values('"	+String.valueOf(data.get_txtDataId())+"','"
					+String.valueOf(data.get_txtNoPenguaran())+"','"
					+String.valueOf(data.get_intStatus())+"','"
					+String.valueOf(data.get_txtStatus())+"','"
					+String.valueOf(data.get_bitActive())+"','"
					+String.valueOf(data.get_intSubmit())+"','"
					+String.valueOf(data.get_intSync())+"')");
	}
	
	public boolean CheckDataPushData(SQLiteDatabase db) {		
		tPenguaranStatus_MobileData dt=new tPenguaranStatus_MobileData();
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
	
	public List<tPenguaranStatus_MobileData> getAllDataToPushData(SQLiteDatabase db) {
		List<tPenguaranStatus_MobileData> contactList = null;
		// Select All Query
		tPenguaranStatus_MobileData dt=new tPenguaranStatus_MobileData();
		String selectQuery = "SELECT "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1 AND "+dt.Property_intSync+"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		contactList=fillData(cursor);
		cursor.close();
		// return contact list
		return contactList;
	}
	
	public List<tPenguaranStatus_MobileData> fillData(Cursor cursor){
		List<tPenguaranStatus_MobileData> contactList=null;
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPenguaranStatus_MobileData>();
			do {
				tPenguaranStatus_MobileData contact = new tPenguaranStatus_MobileData();
				contact.set_txtDataId(cursor.getString(0));
				contact.set_txtNoPenguaran(cursor.getString(1));
				contact.set_intStatus(cursor.getString(2));
				contact.set_txtStatus(cursor.getString(3));
				contact.set_bitActive(cursor.getString(4));
				contact.set_intSubmit(cursor.getString(5));
				contact.set_intSync(cursor.getString(6));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		return contactList;
	}
	
}
