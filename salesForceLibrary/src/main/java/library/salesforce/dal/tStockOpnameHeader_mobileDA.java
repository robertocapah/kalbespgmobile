package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.tStockOpnameHeader_mobileData;

public class tStockOpnameHeader_mobileDA {
	private static final String TABLE_CONTACTS = new clsHardCode().txtTable_tStockOpnameHeader_mobile;

	// Contacts Table Columns names

	public tStockOpnameHeader_mobileDA(SQLiteDatabase db) {
		tStockOpnameHeader_mobileData dt=new tStockOpnameHeader_mobileData();
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
				+ dt.Property_txtNoAdj + " TEXT PRIMARY KEY,"
				+ dt.Property_dtDate + " TEXT NULL,"
				+ dt.Property_intPush + " TEXT NULL,"
				+ dt.Property_intSubmit + " TEXT NULL,"
				+ dt.Property_txtBranchCode + " TEXT NULL,"
				+ dt.Property_txtDeviceId + " TEXT NULL,"
				+ dt.Property_txtOutletCode + " TEXT NULL,"
				+ dt.Property_txtOutletName + " TEXT NULL,"
				+ dt.Property_txtUserId + " TEXT NULL" + ")";
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
	public void SaveDatatStockOpnameHeader_mobileData(SQLiteDatabase db,tStockOpnameHeader_mobileData data) {
		tStockOpnameHeader_mobileData dt=new tStockOpnameHeader_mobileData();
		//ContentValues values = new ContentValues();
		//values.put(dt.Property_intIdMenu, data.get_intIdMenu()); 
		//values.put(dt.Property_txtRoleId, data.get_txtRoleId()); 
		// Inserting Row
		//db.insert(TABLE_CONTACTS, null, values);
		db.execSQL("INSERT OR REPLACE into "+TABLE_CONTACTS+" ("+dt.PropertyAll+") "+
			"values('"	+String.valueOf(data.get_dtDate())+"','"
				+String.valueOf(data.get_intPush())+"','"
				+String.valueOf(data.get_intSubmit())+"','"
				+String.valueOf(data.get_txtBranchCode())+"','"
				+String.valueOf(data.get_txtDeviceId())+"','"
				+String.valueOf(data.get_txtNoAdj())+"','"
				+String.valueOf(data.get_txtOutletCode())+"','"
				+String.valueOf(data.get_txtOutletName())+"','"
				+String.valueOf(data.get_txtUserId())+"')");
	}
	
	public void UpdateDataItemForSubmit(SQLiteDatabase db,String dataid) {
		tStockOpnameHeader_mobileData dt=new tStockOpnameHeader_mobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intSubmit+"=1"
				+" Where " + dt.Property_txtNoAdj +"='"+ dataid+"'");
	}
	
	// Getting single contact
	public tStockOpnameHeader_mobileData getData(SQLiteDatabase db,String id) {
		tStockOpnameHeader_mobileData dt=new tStockOpnameHeader_mobileData();
		String selectQuery = "SELECT "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtNoAdj+"='"+id+"'";
		Cursor cursor = db.rawQuery(selectQuery, null);
		tStockOpnameHeader_mobileData contact = null;
		if(cursor.getCount()>0){
			contact = new tStockOpnameHeader_mobileData();
			if (cursor != null)
				cursor.moveToFirst();
			contact.set_dtDate(cursor.getString(0));
			contact.set_intPush(cursor.getString(1));
			contact.set_intSubmit(cursor.getString(2));
			contact.set_txtBranchCode(cursor.getString(3));
			contact.set_txtDeviceId(cursor.getString(4));
			contact.set_txtNoAdj(cursor.getString(5));
			contact.set_txtOutletCode(cursor.getString(6));
			contact.set_txtOutletName(cursor.getString(7));
			contact.set_txtUserId(cursor.getString(8));
		}
		// return contact
		cursor.close();
		return contact;
	}
	
	public boolean CheckDataPushData(SQLiteDatabase db) {		
		tStockOpnameHeader_mobileData dt=new tStockOpnameHeader_mobileData();
		String selectQuery = "SELECT 1 FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1 AND "+dt.Property_intPush+"=0";
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
	public List<tStockOpnameHeader_mobileData> getAllDataToPushData(SQLiteDatabase db) {
		List<tStockOpnameHeader_mobileData> contactList = null;
		// Select All Query
		tStockOpnameHeader_mobileData dt=new tStockOpnameHeader_mobileData();
		String selectQuery = "SELECT "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1 AND "+dt.Property_intPush+"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tStockOpnameHeader_mobileData>();
			do {
				tStockOpnameHeader_mobileData contact = new tStockOpnameHeader_mobileData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_intPush(cursor.getString(1));
				contact.set_intSubmit(cursor.getString(2));
				contact.set_txtBranchCode(cursor.getString(3));
				contact.set_txtDeviceId(cursor.getString(4));
				contact.set_txtNoAdj(cursor.getString(5));
				contact.set_txtOutletCode(cursor.getString(6));
				contact.set_txtOutletName(cursor.getString(7));
				contact.set_txtUserId(cursor.getString(8));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tStockOpnameHeader_mobileData> getAllData(SQLiteDatabase db) {
		List<tStockOpnameHeader_mobileData> contactList = null;
		// Select All Query
		tStockOpnameHeader_mobileData dt=new tStockOpnameHeader_mobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS;

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tStockOpnameHeader_mobileData>();
			do {
				tStockOpnameHeader_mobileData contact = new tStockOpnameHeader_mobileData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_intPush(cursor.getString(1));
				contact.set_intSubmit(cursor.getString(2));
				contact.set_txtBranchCode(cursor.getString(3));
				contact.set_txtDeviceId(cursor.getString(4));
				contact.set_txtNoAdj(cursor.getString(5));
				contact.set_txtOutletCode(cursor.getString(6));
				contact.set_txtOutletName(cursor.getString(7));
				contact.set_txtUserId(cursor.getString(8));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}public List<tStockOpnameHeader_mobileData> getAllDataByOulet(SQLiteDatabase db,String txtOutletCode) {
		List<tStockOpnameHeader_mobileData> contactList = null;
		// Select All Query
		tStockOpnameHeader_mobileData dt=new tStockOpnameHeader_mobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtOutletCode+"='"+txtOutletCode+"' AND "+dt.Property_intSubmit+"=1";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tStockOpnameHeader_mobileData>();
			do {
				tStockOpnameHeader_mobileData contact = new tStockOpnameHeader_mobileData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_intPush(cursor.getString(1));
				contact.set_intSubmit(cursor.getString(2));
				contact.set_txtBranchCode(cursor.getString(3));
				contact.set_txtDeviceId(cursor.getString(4));
				contact.set_txtNoAdj(cursor.getString(5));
				contact.set_txtOutletCode(cursor.getString(6));
				contact.set_txtOutletName(cursor.getString(7));
				contact.set_txtUserId(cursor.getString(8));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tStockOpnameHeader_mobileData> getAllDataSubmit(SQLiteDatabase db,String txtOutletCode) {
		List<tStockOpnameHeader_mobileData> contactList = null;
		// Select All Query
		tStockOpnameHeader_mobileData dt=new tStockOpnameHeader_mobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +
							 " WHERE "+dt.Property_txtOutletCode+"='" + txtOutletCode + "'" +
							 " AND "+dt.Property_intSubmit+"=1";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tStockOpnameHeader_mobileData>();
			do {
				tStockOpnameHeader_mobileData contact = new tStockOpnameHeader_mobileData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_intPush(cursor.getString(1));
				contact.set_intSubmit(cursor.getString(2));
				contact.set_txtBranchCode(cursor.getString(3));
				contact.set_txtDeviceId(cursor.getString(4));
				contact.set_txtNoAdj(cursor.getString(5));
				contact.set_txtOutletCode(cursor.getString(6));
				contact.set_txtOutletName(cursor.getString(7));
				contact.set_txtUserId(cursor.getString(8));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tStockOpnameHeader_mobileData> getAllDataNotSync(SQLiteDatabase db) {
		List<tStockOpnameHeader_mobileData> contactList = null;
		// Select All Query
		tStockOpnameHeader_mobileData dt=new tStockOpnameHeader_mobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intPush +"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tStockOpnameHeader_mobileData>();
			do {
				tStockOpnameHeader_mobileData contact = new tStockOpnameHeader_mobileData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_intPush(cursor.getString(1));
				contact.set_intSubmit(cursor.getString(2));
				contact.set_txtBranchCode(cursor.getString(3));
				contact.set_txtDeviceId(cursor.getString(4));
				contact.set_txtNoAdj(cursor.getString(5));
				contact.set_txtOutletCode(cursor.getString(6));
				contact.set_txtOutletName(cursor.getString(7));
				contact.set_txtUserId(cursor.getString(8));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tStockOpnameHeader_mobileData> getAllDataByIdAbsen(SQLiteDatabase db,String Id) {
		List<tStockOpnameHeader_mobileData> contactList = null;
		// Select All Query
		tStockOpnameHeader_mobileData dt=new tStockOpnameHeader_mobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtUserId +"='"+Id+"'";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tStockOpnameHeader_mobileData>();
			do {
				tStockOpnameHeader_mobileData contact = new tStockOpnameHeader_mobileData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_intPush(cursor.getString(1));
				contact.set_intSubmit(cursor.getString(2));
				contact.set_txtBranchCode(cursor.getString(3));
				contact.set_txtDeviceId(cursor.getString(4));
				contact.set_txtNoAdj(cursor.getString(5));
				contact.set_txtOutletCode(cursor.getString(6));
				contact.set_txtOutletName(cursor.getString(7));
				contact.set_txtUserId(cursor.getString(8));
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
		tStockOpnameHeader_mobileData dt = new tStockOpnameHeader_mobileData();
		db.delete(TABLE_CONTACTS, dt.Property_txtNoAdj + " = ?",
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
