package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.tSalesOrderHeader_MobileData;

public class tSalesOrderHeader_MobileDA {
	private static final String TABLE_CONTACTS = new clsHardCode().txtTable_tSalesOrderHeader_mobile;

	// Contacts Table Columns names

	public tSalesOrderHeader_MobileDA(SQLiteDatabase db) {
		tSalesOrderHeader_MobileData dt=new tSalesOrderHeader_MobileData();
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
				+ dt.Property_txtNoSalesOrder + " TEXT PRIMARY KEY," 
				+ dt.Property_dtDate + " TEXT NULL,"
				+ dt.Property_txtOutletCode + " TEXT NULL,"
				+ dt.Property_txtOutletName + " TEXT NULL,"
				+ dt.Property_txtBranchCode + " TEXT NULL,"
				+ dt.Property_bitPO + " TEXT NULL,"
				+ dt.Property_intGenerate + " TEXT NULL,"
				+ dt.Property_txtDeviceId + " TEXT NULL,"
				+ dt.Property_txtUserId + " TEXT NULL,"
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
	public void SaveDatatSalesOrderHeader_MobileData(SQLiteDatabase db,tSalesOrderHeader_MobileData data) {
		tSalesOrderHeader_MobileData dt=new tSalesOrderHeader_MobileData();
		//ContentValues values = new ContentValues();
		//values.put(dt.Property_intIdMenu, data.get_intIdMenu()); 
		//values.put(dt.Property_txtRoleId, data.get_txtRoleId()); 
		// Inserting Row
		//db.insert(TABLE_CONTACTS, null, values);
		db.execSQL("INSERT OR REPLACE into "+TABLE_CONTACTS+" ("+dt.PropertyAll+") "+
			"values('"	+String.valueOf(data.get_dtDate())+"','"
				+String.valueOf(data.get_intSubmit())+"','"
				+String.valueOf(data.get_intSync())+"','"
				+String.valueOf(data.get_txtBranchCode())+"','"
				+String.valueOf(data.get_txtDeviceId())+"','"
				+String.valueOf(data.get_bitPO())+"','"
				+String.valueOf(data.get_intGenerate())+"','"
				+String.valueOf(data.get_txtNoSalesOrder())+"','"
				+String.valueOf(data.get_txtOutletCode())+"','"
				+String.valueOf(data.get_txtOutletName())+"','"
				+String.valueOf(data.get_txtUserId())+"')");
	}
	
	public void UpdateDataItemForSubmit(SQLiteDatabase db,String dataid) {
		tSalesOrderHeader_MobileData dt=new tSalesOrderHeader_MobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intSubmit+"=1"
				+" Where " + dt.Property_txtNoSalesOrder +"='"+ dataid+"'");
	}
	
	// Getting single contact
	public tSalesOrderHeader_MobileData getData(SQLiteDatabase db,String id) {
		tSalesOrderHeader_MobileData dt=new tSalesOrderHeader_MobileData();
		String selectQuery = "SELECT "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtNoSalesOrder+"='"+id+"'";
		Cursor cursor = db.rawQuery(selectQuery, null);
		tSalesOrderHeader_MobileData contact = null;
		if(cursor.getCount()>0){
			contact = new tSalesOrderHeader_MobileData();
			if (cursor != null)
				cursor.moveToFirst();
			contact.set_dtDate(cursor.getString(0));
			contact.set_intSubmit(cursor.getString(1));
			contact.set_intSync(cursor.getString(2));
			contact.set_txtBranchCode(cursor.getString(3));
			contact.set_txtDeviceId(cursor.getString(4));
			contact.set_bitPO(cursor.getString(5));
			contact.set_intGenerate(cursor.getString(6));
			contact.set_txtNoSalesOrder(cursor.getString(7));			
			contact.set_txtOutletCode(cursor.getString(8));
			contact.set_txtOutletName(cursor.getString(9));
			contact.set_txtUserId(cursor.getString(10));
		}
		// return contact
		cursor.close();
		return contact;
	}
	
	public boolean CheckDataPushData(SQLiteDatabase db) {		
		tSalesOrderHeader_MobileData dt=new tSalesOrderHeader_MobileData();
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
	public List<tSalesOrderHeader_MobileData> getAllDataToPushData(SQLiteDatabase db) {
		List<tSalesOrderHeader_MobileData> contactList = null;
		// Select All Query
		tSalesOrderHeader_MobileData dt=new tSalesOrderHeader_MobileData();
		String selectQuery = "SELECT "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1 AND "+dt.Property_intSync+"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tSalesOrderHeader_MobileData>();
			do {
				tSalesOrderHeader_MobileData contact = new tSalesOrderHeader_MobileData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_intSubmit(cursor.getString(1));
				contact.set_intSync(cursor.getString(2));
				contact.set_txtBranchCode(cursor.getString(3));
				contact.set_txtDeviceId(cursor.getString(4));
				contact.set_bitPO(cursor.getString(5));
				contact.set_intGenerate(cursor.getString(6));
				contact.set_txtNoSalesOrder(cursor.getString(7));			
				contact.set_txtOutletCode(cursor.getString(8));
				contact.set_txtOutletName(cursor.getString(9));
				contact.set_txtUserId(cursor.getString(10));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tSalesOrderHeader_MobileData> getAllData(SQLiteDatabase db) {
		List<tSalesOrderHeader_MobileData> contactList = null;
		// Select All Query
		tSalesOrderHeader_MobileData dt=new tSalesOrderHeader_MobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS;

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tSalesOrderHeader_MobileData>();
			do {
				tSalesOrderHeader_MobileData contact = new tSalesOrderHeader_MobileData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_intSubmit(cursor.getString(1));
				contact.set_intSync(cursor.getString(2));
				contact.set_txtBranchCode(cursor.getString(3));
				contact.set_txtDeviceId(cursor.getString(4));
				contact.set_bitPO(cursor.getString(5));
				contact.set_intGenerate(cursor.getString(6));
				contact.set_txtNoSalesOrder(cursor.getString(7));			
				contact.set_txtOutletCode(cursor.getString(8));
				contact.set_txtOutletName(cursor.getString(9));
				contact.set_txtUserId(cursor.getString(10));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}public List<tSalesOrderHeader_MobileData> getAllDataByOulet(SQLiteDatabase db,String txtOutletCode) {
		List<tSalesOrderHeader_MobileData> contactList = null;
		// Select All Query
		tSalesOrderHeader_MobileData dt=new tSalesOrderHeader_MobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtOutletCode+"='"+txtOutletCode+"' AND "+dt.Property_intSubmit+"=1";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tSalesOrderHeader_MobileData>();
			do {
				tSalesOrderHeader_MobileData contact = new tSalesOrderHeader_MobileData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_intSubmit(cursor.getString(1));
				contact.set_intSync(cursor.getString(2));
				contact.set_txtBranchCode(cursor.getString(3));
				contact.set_txtDeviceId(cursor.getString(4));
				contact.set_bitPO(cursor.getString(5));
				contact.set_intGenerate(cursor.getString(6));
				contact.set_txtNoSalesOrder(cursor.getString(7));			
				contact.set_txtOutletCode(cursor.getString(8));
				contact.set_txtOutletName(cursor.getString(9));
				contact.set_txtUserId(cursor.getString(10));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tSalesOrderHeader_MobileData> getAllDataSubmit(SQLiteDatabase db,String txtOutletCode) {
		List<tSalesOrderHeader_MobileData> contactList = null;
		// Select All Query
		tSalesOrderHeader_MobileData dt=new tSalesOrderHeader_MobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +
							 " WHERE "+dt.Property_txtOutletCode+"='" + txtOutletCode + "'" +
							 " AND "+dt.Property_intSubmit+"=1";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tSalesOrderHeader_MobileData>();
			do {
				tSalesOrderHeader_MobileData contact = new tSalesOrderHeader_MobileData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_intSubmit(cursor.getString(1));
				contact.set_intSync(cursor.getString(2));
				contact.set_txtBranchCode(cursor.getString(3));
				contact.set_txtDeviceId(cursor.getString(4));
				contact.set_bitPO(cursor.getString(5));
				contact.set_intGenerate(cursor.getString(6));
				contact.set_txtNoSalesOrder(cursor.getString(7));
				contact.set_txtOutletCode(cursor.getString(8));
				contact.set_txtOutletName(cursor.getString(9));
				contact.set_txtUserId(cursor.getString(10));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tSalesOrderHeader_MobileData> getAllDataNotSync(SQLiteDatabase db) {
		List<tSalesOrderHeader_MobileData> contactList = null;
		// Select All Query
		tSalesOrderHeader_MobileData dt=new tSalesOrderHeader_MobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSync +"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tSalesOrderHeader_MobileData>();
			do {
				tSalesOrderHeader_MobileData contact = new tSalesOrderHeader_MobileData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_intSubmit(cursor.getString(1));
				contact.set_intSync(cursor.getString(2));
				contact.set_txtBranchCode(cursor.getString(3));
				contact.set_txtDeviceId(cursor.getString(4));
				contact.set_bitPO(cursor.getString(5));
				contact.set_intGenerate(cursor.getString(6));
				contact.set_txtNoSalesOrder(cursor.getString(7));			
				contact.set_txtOutletCode(cursor.getString(8));
				contact.set_txtOutletName(cursor.getString(9));
				contact.set_txtUserId(cursor.getString(10));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tSalesOrderHeader_MobileData> getAllDataByIdAbsen(SQLiteDatabase db,String Id) {
		List<tSalesOrderHeader_MobileData> contactList = null;
		// Select All Query
		tSalesOrderHeader_MobileData dt=new tSalesOrderHeader_MobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtUserId +"='"+Id+"'";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tSalesOrderHeader_MobileData>();
			do {
				tSalesOrderHeader_MobileData contact = new tSalesOrderHeader_MobileData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_intSubmit(cursor.getString(1));
				contact.set_intSync(cursor.getString(2));
				contact.set_txtBranchCode(cursor.getString(3));
				contact.set_txtDeviceId(cursor.getString(4));
				contact.set_bitPO(cursor.getString(5));
				contact.set_intGenerate(cursor.getString(6));
				contact.set_txtNoSalesOrder(cursor.getString(7));			
				contact.set_txtOutletCode(cursor.getString(8));
				contact.set_txtOutletName(cursor.getString(9));
				contact.set_txtUserId(cursor.getString(10));
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
		tSalesOrderHeader_MobileData dt = new tSalesOrderHeader_MobileData();
		db.delete(TABLE_CONTACTS, dt.Property_txtNoSalesOrder + " = ?",
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
