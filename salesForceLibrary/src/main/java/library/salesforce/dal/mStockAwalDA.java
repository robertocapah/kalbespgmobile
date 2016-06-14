package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.mStockAwalData;

public class mStockAwalDA {
	// Contacts table name
	private static final String TABLE_CONTACTS = new clsHardCode().txtTable_mStockAwal;
		
	public mStockAwalDA(SQLiteDatabase db) {
		mStockAwalData dt=new mStockAwalData();
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
										+ dt.Property_intdata + " TEXT PRIMARY KEY," 
										+ dt.Property_txtProductCode + " TEXT NULL,"
										+ dt.Property_txtProductName + " TEXT NULL,"
										+ dt.Property_txtOutletCode + " TEXT NULL,"
										+ dt.Property_txtOutletName + " TEXT NULL,"
										+ dt.Property_txtBranchCode + " TEXT NULL,"
										+ dt.Property_intQty + " TEXT NULL,"
										+ dt.Property_txtStatus + " TEXT NULL,"
										+ dt.Property_txtNoDoc + " TEXT NULL,"
										+ dt.Property_intSubmit + " TEXT NULL,"
										+ dt.Property_intSync + " TEXT NULL,"
										+ dt.Property_bitActive + " TEXT NULL" + ")";
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
	public void SaveDataMStockAwalData(SQLiteDatabase db,mStockAwalData data){
		mStockAwalData dt=new mStockAwalData();
		// Inserting Row
		db.execSQL("INSERT OR REPLACE into "+TABLE_CONTACTS+" ("+dt.Property_intdata+","
					+dt.Property_txtProductCode+","
					+dt.Property_txtProductName+","
					+dt.Property_txtOutletCode+","
					+dt.Property_txtOutletName+","
					+dt.Property_txtBranchCode+","
					+dt.Property_intQty+","
					+dt.Property_intSubmit+","
					+dt.Property_intSync+","
					+dt.Property_txtStatus+","
					+dt.Property_txtNoDoc+","
					+dt.Property_bitActive+") "+
					"values('"	+String.valueOf(data.get_intdata())+"','"
					+String.valueOf(data.get_txtProductCode())+"','"
					+String.valueOf(data.get_txtProductName())+"','"
					+String.valueOf(data.get_txtOutletCode())+"','"
					+String.valueOf(data.get_txtOutletName())+"','"
					+String.valueOf(data.get_txtBranchCode())+"','"
					+String.valueOf(data.get_intQty())+"','"
					+String.valueOf(data.get_intSubmit())+"','"
					+String.valueOf(data.get_intSync())+"','"
					+String.valueOf(data.get_txtStatus())+"','"
					+String.valueOf(data.get_txtNoDoc())+"','"
					+String.valueOf(data.get_bitActive())+"')");
	}
	
	
	// Getting single contact
	public mStockAwalData getData(SQLiteDatabase db,String id) {
		mStockAwalData dt=new mStockAwalData();
		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { 
				 dt.Property_intdata
				,dt.Property_txtProductCode
				,dt.Property_txtProductName 
				,dt.Property_txtOutletCode
				,dt.Property_txtOutletName 
				,dt.Property_txtBranchCode
				,dt.Property_intQty
				,dt.Property_txtStatus
				,dt.Property_txtNoDoc
				,dt.Property_bitActive}, dt.Property_intdata + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		mStockAwalData contact = null;
		if(cursor.getCount()>0){
			contact = new mStockAwalData();
			if (cursor != null)
				cursor.moveToFirst();
				contact.set_intdata(cursor.getString(0));
				contact.set_txtProductCode(cursor.getString(1));
				contact.set_txtProductName(cursor.getString(2));
				contact.set_txtOutletCode(cursor.getString(3));
				contact.set_txtOutletName  (cursor.getString(4));
				contact.set_txtBranchCode(cursor.getString(5));
				contact.set_intQty(cursor.getString(6));
				contact.set_txtStatus(cursor.getString(7));
				contact.set_txtNoDoc(cursor.getString(8));
				contact.set_bitActive(cursor.getString(9));
		}
		// return contact
		cursor.close();
		return contact;
	}
	
	/*
	public List<mStockAwalData> getAllStockProduct(SQLiteDatabase db) {
		List<mStockAwalData> contactList = null;
		// Select All Query
		mStockAwalData dt=new mStockAwalData();
		String selectQuery = "SELECT "+dt.Property_ALL+
							 " FROM " + TABLE_CONTACTS +
							 " WHERE "+dt.Property_intSubmit+"=1";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		contactList=fillData(cursor);
		cursor.close();
		// return contact list
		return contactList;
	}
	*/
	
	public List<mStockAwalData> fillData(Cursor cursor){
		List<mStockAwalData> contactList=null;
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<mStockAwalData>();
			do {
				mStockAwalData contact = new mStockAwalData();
				contact.set_intdata(cursor.getString(0));
				contact.set_txtProductCode(cursor.getString(1));
				contact.set_txtProductName(cursor.getString(2));
				contact.set_txtOutletCode(cursor.getString(3));
				contact.set_txtOutletName  (cursor.getString(4));
				contact.set_txtBranchCode(cursor.getString(5));
				contact.set_intQty(cursor.getString(6));
				contact.set_txtStatus(cursor.getString(7));
				contact.set_txtNoDoc(cursor.getString(8));
				contact.set_bitActive(cursor.getString(9));
				contact.set_intSubmit(cursor.getString(10));
				contact.set_intSync(cursor.getString(11));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		return contactList;
	}
	public List<mStockAwalData> getAllDataPushData(SQLiteDatabase db) {
		List<mStockAwalData> contactList = null;
		// Select All Query
		mStockAwalData dt=new mStockAwalData();
		String selectQuery = "SELECT  "+dt.Property_ALL+" FROM " + TABLE_CONTACTS +" where "+dt.Property_intSubmit+"=1 And "+dt.Property_intSync+"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
	    contactList=fillData(cursor);
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<mStockAwalData> getAllData(SQLiteDatabase db) {
		List<mStockAwalData> contactList = null;
		// Select All Query
		mStockAwalData dt=new mStockAwalData();
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
		mStockAwalData dt = new mStockAwalData();
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
