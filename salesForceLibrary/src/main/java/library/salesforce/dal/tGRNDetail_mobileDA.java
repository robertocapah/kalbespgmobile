package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.tGRNDetail_mobileData;

public class tGRNDetail_mobileDA {
	private static final String TABLE_CONTACTS = new clsHardCode().txtTable_tGRNDetail_mobile;

	// Contacts Table Columns names

	public tGRNDetail_mobileDA(SQLiteDatabase db) {
		tGRNDetail_mobileData dt=new tGRNDetail_mobileData();
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
				+ dt.Property_txtDataId + " TEXT PRIMARY KEY,"
				+ dt.Property_txtNoGRN + " TEXT NULL,"
				+ dt.Property_intProductCode + " TEXT NULL,"
				+ dt.Property_txtProductName + " TEXT NULL,"
				+ dt.Property_intQty + " TEXT NULL,"
				+ dt.Property_intReason + " TEXT NULL,"
				+ dt.Property_txtBatchNo + " TEXT NULL,"
				+ dt.Property_dtED + " TEXT NULL,"
				+ dt.Property_intSubmit + " TEXT NULL,"
				+ dt.Property_intStockAwal + " TEXT NULL,"
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
	public void SaveDatatGRNDetail_mobileData(SQLiteDatabase db,tGRNDetail_mobileData data) {
		tGRNDetail_mobileData dt=new tGRNDetail_mobileData();
		db.execSQL("INSERT OR REPLACE into "+TABLE_CONTACTS+" ("+dt.PropertyAll+") "+
			"values('"	+String.valueOf(data.get_dtED())+"','"
				+String.valueOf(data.get_intProductCode())+"','"
				+String.valueOf(data.get_intQty())+"','"
				+String.valueOf(data.get_intReason())+"','"
				+String.valueOf(data.get_intSubmit())+"','"
				+String.valueOf(data.get_intStockAwal())+"','"
				+String.valueOf(data.get_intSync())+"','"
				+String.valueOf(data.get_txtBatchNo())+"','"
				+String.valueOf(data.get_txtDataId())+"','"
				+String.valueOf(data.get_txtNoGRN())+"','"
				+String.valueOf(data.get_txtProductName())+"'"
				+")");
	}
	public void UpdateDataItem(SQLiteDatabase db,tGRNDetail_mobileData data) {
		tGRNDetail_mobileData dt=new tGRNDetail_mobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intQty+"="
				+String.valueOf(data.get_intQty())
				+","+dt.Property_intQty+"="
				+String.valueOf(data.get_intQty())
				+" Where " + dt.Property_txtDataId +"='"+ data.get_txtDataId()+"'");
	}
	
	public void UpdateDataItemForSubmit(SQLiteDatabase db,String dataid) {
		tGRNDetail_mobileData dt=new tGRNDetail_mobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intSubmit+"=1"
				+" Where " + dt.Property_txtDataId +"='"+ dataid+"'");
	}
	public tGRNDetail_mobileData getDataBYHeaderAndProduct(SQLiteDatabase db,String id,String idProduct) {
		tGRNDetail_mobileData dt=new tGRNDetail_mobileData();
		String selectQuery = "SELECT "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtNoGRN+"='"+id+"' AND "+dt.Property_intProductCode+"='"+idProduct+"'";
		Cursor cursor = db.rawQuery(selectQuery, null);
		tGRNDetail_mobileData contact = null;
		if(cursor.getCount()>0){
			contact = new tGRNDetail_mobileData();
			if (cursor != null)
				cursor.moveToFirst();
			contact.set_dtED(cursor.getString(0));
			contact.set_intProductCode(cursor.getString(1));
			contact.set_intQty(cursor.getString(2));
			contact.set_intReason(cursor.getString(3));
			contact.set_intSubmit(cursor.getString(4));
			contact.set_intStockAwal(cursor.getString(5));
			contact.set_intSync(cursor.getString(6));
			contact.set_txtBatchNo(cursor.getString(7));
			contact.set_txtDataId(cursor.getString(8));
			contact.set_txtNoGRN(cursor.getString(9));
			contact.set_txtProductName(cursor.getString(10));
		}
		// return contact
		cursor.close();
		return contact;
	}
	// Getting single contact
	public tGRNDetail_mobileData getData(SQLiteDatabase db,String id) {
		tGRNDetail_mobileData dt=new tGRNDetail_mobileData();
		String selectQuery = "SELECT "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1 AND "+dt.Property_intSync+"=0";
		Cursor cursor = db.rawQuery(selectQuery, null);
		tGRNDetail_mobileData contact = null;
		if(cursor.getCount()>0){
			contact = new tGRNDetail_mobileData();
			if (cursor != null)
				cursor.moveToFirst();
			contact.set_dtED(cursor.getString(0));
			contact.set_intProductCode(cursor.getString(1));
			contact.set_intQty(cursor.getString(2));
			contact.set_intReason(cursor.getString(3));
			contact.set_intSubmit(cursor.getString(4));
			contact.set_intStockAwal(cursor.getString(5));
			contact.set_intSync(cursor.getString(6));
			contact.set_txtBatchNo(cursor.getString(7));
			contact.set_txtDataId(cursor.getString(8));
			contact.set_txtNoGRN(cursor.getString(9));
			contact.set_txtProductName(cursor.getString(10));
		}
		// return contact
		cursor.close();
		return contact;
	}
	public List<tGRNDetail_mobileData> getCalculateProduct(SQLiteDatabase db,String id) {
		List<tGRNDetail_mobileData> contactList=new ArrayList<tGRNDetail_mobileData>();
		String selectQuery = "select b.[intProductCode],sum(b.[intQty]) intQty from [tGRNHeader_mobile] a "+ 
				"inner join [tGRNDetail_mobile] b on a.[txtNoGRN]=b.[txtNoGRN] "+
				"where a.[txtNoGRN]='"+id+"' "+
				"group by b.[intProductCode]";
		Cursor cursor = db.rawQuery(selectQuery, null);
		tGRNDetail_mobileData contact = null;
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tGRNDetail_mobileData>();
			do {
				contact = new tGRNDetail_mobileData();
				contact.set_intProductCode(cursor.getString(0));
				contact.set_intQty(cursor.getString(1));
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		// return contact
		cursor.close();
		return contactList;
	}
	public List<tGRNDetail_mobileData> getCalculateProductGroupByintProductCodetxtBatchNodtED(SQLiteDatabase db,String id) {
		List<tGRNDetail_mobileData> contactList=new ArrayList<tGRNDetail_mobileData>();
		String selectQuery = "select b.[intProductCode],b.[txtBatchNo],b.[dtED],sum(b.[intQty]) intQty from [tGRNHeader_mobile] a "+ 
				"inner join [tGRNDetail_mobile] b on a.[txtNoGRN]=b.[txtNoGRN] "+
				"where a.[txtNoGRN]='"+id+"' "+
				"group by b.[intProductCode],b.[txtBatchNo],b.[dtED]";
		Cursor cursor = db.rawQuery(selectQuery, null);
		tGRNDetail_mobileData contact = null;
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tGRNDetail_mobileData>();
			do {
				contact = new tGRNDetail_mobileData();
				contact.set_intProductCode(cursor.getString(0));
				contact.set_txtBatchNo(cursor.getString(1));
				contact.set_dtED(cursor.getString(2));
				contact.set_intQty(cursor.getString(3));
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		// return contact
		cursor.close();
		return contactList;
	}
	public boolean CheckDataPushData(SQLiteDatabase db) {		
		tGRNDetail_mobileData dt=new tGRNDetail_mobileData();
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
	public List<tGRNDetail_mobileData> getAllDataToPushData(SQLiteDatabase db) {
		List<tGRNDetail_mobileData> contactList = null;
		// Select All Query
		tGRNDetail_mobileData dt=new tGRNDetail_mobileData();
		String selectQuery = "SELECT "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1 AND "+dt.Property_intSync+"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tGRNDetail_mobileData>();
			do {
				tGRNDetail_mobileData contact = new tGRNDetail_mobileData();
				contact.set_dtED(cursor.getString(0));
				contact.set_intProductCode(cursor.getString(1));
				contact.set_intQty(cursor.getString(2));
				contact.set_intReason(cursor.getString(3));
				contact.set_intSubmit(cursor.getString(4));
				contact.set_intStockAwal(cursor.getString(5));
				contact.set_intSync(cursor.getString(6));
				contact.set_txtBatchNo(cursor.getString(7));
				contact.set_txtDataId(cursor.getString(8));
				contact.set_txtNoGRN(cursor.getString(9));
				contact.set_txtProductName(cursor.getString(10));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tGRNDetail_mobileData> getAllData(SQLiteDatabase db) {
		List<tGRNDetail_mobileData> contactList = null;
		// Select All Query
		tGRNDetail_mobileData dt=new tGRNDetail_mobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS;

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tGRNDetail_mobileData>();
			do {
				tGRNDetail_mobileData contact = new tGRNDetail_mobileData();
				contact.set_dtED(cursor.getString(0));
				contact.set_intProductCode(cursor.getString(1));
				contact.set_intQty(cursor.getString(2));
				contact.set_intReason(cursor.getString(3));
				contact.set_intSubmit(cursor.getString(4));
				contact.set_intStockAwal(cursor.getString(5));
				contact.set_intSync(cursor.getString(6));
				contact.set_txtBatchNo(cursor.getString(7));
				contact.set_txtDataId(cursor.getString(8));
				contact.set_txtNoGRN(cursor.getString(9));
				contact.set_txtProductName(cursor.getString(10));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tGRNDetail_mobileData> getAllDataSubmit(SQLiteDatabase db) {
		List<tGRNDetail_mobileData> contactList = null;
		// Select All Query
		tGRNDetail_mobileData dt=new tGRNDetail_mobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tGRNDetail_mobileData>();
			do {
				tGRNDetail_mobileData contact = new tGRNDetail_mobileData();
				contact.set_dtED(cursor.getString(0));
				contact.set_intProductCode(cursor.getString(1));
				contact.set_intQty(cursor.getString(2));
				contact.set_intReason(cursor.getString(3));
				contact.set_intSubmit(cursor.getString(4));
				contact.set_intStockAwal(cursor.getString(5));
				contact.set_intSync(cursor.getString(6));
				contact.set_txtBatchNo(cursor.getString(7));
				contact.set_txtDataId(cursor.getString(8));
				contact.set_txtNoGRN(cursor.getString(9));
				contact.set_txtProductName(cursor.getString(10));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tGRNDetail_mobileData> getAllDataNotSync(SQLiteDatabase db) {
		List<tGRNDetail_mobileData> contactList = null;
		// Select All Query
		tGRNDetail_mobileData dt=new tGRNDetail_mobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSync +"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tGRNDetail_mobileData>();
			do {
				tGRNDetail_mobileData contact = new tGRNDetail_mobileData();
				contact.set_dtED(cursor.getString(0));
				contact.set_intProductCode(cursor.getString(1));
				contact.set_intQty(cursor.getString(2));
				contact.set_intReason(cursor.getString(3));
				contact.set_intSubmit(cursor.getString(4));
				contact.set_intStockAwal(cursor.getString(5));
				contact.set_intSync(cursor.getString(6));
				contact.set_txtBatchNo(cursor.getString(7));
				contact.set_txtDataId(cursor.getString(8));
				contact.set_txtNoGRN(cursor.getString(9));
				contact.set_txtProductName(cursor.getString(10));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tGRNDetail_mobileData> getAllDataByHeaderId(SQLiteDatabase db,String Id) {
		List<tGRNDetail_mobileData> contactList = new ArrayList<tGRNDetail_mobileData>();
		// Select All Query
		tGRNDetail_mobileData dt=new tGRNDetail_mobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtNoGRN +"='"+Id+"'";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tGRNDetail_mobileData>();
			do {
				tGRNDetail_mobileData contact = new tGRNDetail_mobileData();
				contact.set_dtED(cursor.getString(0));
				contact.set_intProductCode(cursor.getString(1));
				contact.set_intQty(cursor.getString(2));
				contact.set_intReason(cursor.getString(3));
				contact.set_intSubmit(cursor.getString(4));
				contact.set_intStockAwal(cursor.getString(5));
				contact.set_intSync(cursor.getString(6));
				contact.set_txtBatchNo(cursor.getString(7));
				contact.set_txtDataId(cursor.getString(8));
				contact.set_txtNoGRN(cursor.getString(9));
				contact.set_txtProductName(cursor.getString(10));
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
		tGRNDetail_mobileData dt = new tGRNDetail_mobileData();
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
