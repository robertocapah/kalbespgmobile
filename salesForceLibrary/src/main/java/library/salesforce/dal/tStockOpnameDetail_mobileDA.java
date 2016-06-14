package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.tStockOpnameDetail_mobileData;

public class tStockOpnameDetail_mobileDA {
	private static final String TABLE_CONTACTS = new clsHardCode().txtTable_tStockOpnameDetail_mobile;

	// Contacts Table Columns names

	public tStockOpnameDetail_mobileDA(SQLiteDatabase db) {
		tStockOpnameDetail_mobileData dt=new tStockOpnameDetail_mobileData();
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
				+ dt.Property_txtDataId + " TEXT  PRIMARY KEY," 
				+ dt.Property_txtNoAdj + " TEXT NULL,"
				+ dt.Property_intProductCode + " TEXT NULL,"
				+ dt.Property_txtProductName + " TEXT NULL,"
				+ dt.Property_intQtyStock + " TEXT NULL,"
				+ dt.Property_intQtyAdj + " TEXT NULL,"
				+ dt.Property_intQty + " TEXT NULL,"
				+ dt.Property_txtBatchNo + " TEXT NULL,"
				+ dt.Property_dtED + " TEXT NULL,"
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
	public void SaveDatatStockOpnameDetail_mobileData(SQLiteDatabase db,tStockOpnameDetail_mobileData data) {
		tStockOpnameDetail_mobileData dt=new tStockOpnameDetail_mobileData();
		db.execSQL("INSERT OR REPLACE into "+TABLE_CONTACTS+" ("+dt.PropertyAll+") "+
			"values('"	+String.valueOf(data.get_dtED())+"','"
				+String.valueOf(data.get_intProductCode())+"','"
				+String.valueOf(data.get_intQty())+"','"
				+String.valueOf(data.get_intQtyAdj())+"','"
				+String.valueOf(data.get_intQtyStock())+"','"
				+String.valueOf(data.get_intSubmit())+"','"
				+String.valueOf(data.get_intSync())+"','"
				+String.valueOf(data.get_txtBatchNo())+"','"
				+String.valueOf(data.get_txtDataId())+"','"
				+String.valueOf(data.get_txtNoAdj())+"','"
				+String.valueOf(data.get_txtProductName())+"'"
				+")");
	}
	public void UpdateDataQuantity(SQLiteDatabase db,tStockOpnameDetail_mobileData data) {
		tStockOpnameDetail_mobileData dt=new tStockOpnameDetail_mobileData();
		db.execSQL("Update "+TABLE_CONTACTS+
				   " set  " +dt.Property_intQtyStock+"='"+String.valueOf(data.get_intQtyStock()) + "'"
				   +","+dt.Property_intQtyAdj+"='"+String.valueOf(data.get_intQtyAdj()) + "'"
				   +","+dt.Property_intQty+"='"+String.valueOf(data.get_intQty()) + "'"
				   +","+dt.Property_txtBatchNo+"='"+String.valueOf(data.get_txtBatchNo()) + "'"
				   +","+dt.Property_dtED+"='"+String.valueOf(data.get_dtED()) + "'"
				+" Where " + dt.Property_txtDataId +"='"+ data.get_txtDataId()+"'");
	}
	public void UpdateDataItem(SQLiteDatabase db,tStockOpnameDetail_mobileData data) {
		tStockOpnameDetail_mobileData dt=new tStockOpnameDetail_mobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intQty+"="
				+String.valueOf(data.get_intQty())
				+","+dt.Property_intQtyAdj+"="
				+String.valueOf(data.get_intQtyStock())
				+" Where " + dt.Property_txtDataId +"='"+ data.get_txtDataId()+"'");
	}
	
	public void UpdateDataItemForSubmit(SQLiteDatabase db,String dataid) {
		tStockOpnameDetail_mobileData dt=new tStockOpnameDetail_mobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intSubmit+"=1"
				+" Where " + dt.Property_txtDataId +"='"+ dataid+"'");
	}
	
	// Getting single contact
	public tStockOpnameDetail_mobileData getDataByTxtDataID(SQLiteDatabase db,String id) {
		tStockOpnameDetail_mobileData dt=new tStockOpnameDetail_mobileData();
		String selectQuery = "SELECT "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtDataId+"='" + id + "' ";
		Cursor cursor = db.rawQuery(selectQuery, null);
		tStockOpnameDetail_mobileData contact = null;
		if(cursor.getCount()>0){
			contact = new tStockOpnameDetail_mobileData();
			if (cursor != null)
				cursor.moveToFirst();
			contact.set_dtED(cursor.getString(0));
			contact.set_intProductCode(cursor.getString(1));
			contact.set_intQty(cursor.getString(2));
			contact.set_intQtyAdj(cursor.getString(3));
			contact.set_intQtyStock(cursor.getString(4));
			contact.set_intSubmit(cursor.getString(5));
			contact.set_intSync(cursor.getString(6));
			contact.set_txtBatchNo(cursor.getString(7));
			contact.set_txtDataId(cursor.getString(8));
			contact.set_txtNoAdj(cursor.getString(9));
			contact.set_txtProductName(cursor.getString(10));
		}
		// return contact
		cursor.close();
		return contact;
	}
	
	public tStockOpnameDetail_mobileData getData(SQLiteDatabase db,String id) {
		tStockOpnameDetail_mobileData dt=new tStockOpnameDetail_mobileData();
		String selectQuery = "SELECT 1 FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1 AND "+dt.Property_intSync+"=0";
		Cursor cursor = db.rawQuery(selectQuery, null);
		tStockOpnameDetail_mobileData contact = null;
		if(cursor.getCount()>0){
			contact = new tStockOpnameDetail_mobileData();
			if (cursor != null)
				cursor.moveToFirst();
			contact.set_dtED(cursor.getString(0));
			contact.set_intProductCode(cursor.getString(1));
			contact.set_intQty(cursor.getString(2));
			contact.set_intQtyAdj(cursor.getString(3));
			contact.set_intQtyStock(cursor.getString(4));
			contact.set_intSubmit(cursor.getString(5));
			contact.set_intSync(cursor.getString(6));
			contact.set_txtBatchNo(cursor.getString(7));
			contact.set_txtDataId(cursor.getString(8));
			contact.set_txtNoAdj(cursor.getString(9));
			contact.set_txtProductName(cursor.getString(10));
		}
		// return contact
		cursor.close();
		return contact;
	}
	
	public boolean CheckDataPushData(SQLiteDatabase db) {		
		tStockOpnameDetail_mobileData dt=new tStockOpnameDetail_mobileData();
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
	public List<tStockOpnameDetail_mobileData> getAllDataToPushData(SQLiteDatabase db) {
		List<tStockOpnameDetail_mobileData> contactList = null;
		// Select All Query
		tStockOpnameDetail_mobileData dt=new tStockOpnameDetail_mobileData();
		String selectQuery = "SELECT "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1 AND "+dt.Property_intSync+"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tStockOpnameDetail_mobileData>();
			do {
				tStockOpnameDetail_mobileData contact = new tStockOpnameDetail_mobileData();
				contact.set_dtED(cursor.getString(0));
				contact.set_intProductCode(cursor.getString(1));
				contact.set_intQty(cursor.getString(2));
				contact.set_intQtyAdj(cursor.getString(3));
				contact.set_intQtyStock(cursor.getString(4));
				contact.set_intSubmit(cursor.getString(5));
				contact.set_intSync(cursor.getString(6));
				contact.set_txtBatchNo(cursor.getString(7));
				contact.set_txtDataId(cursor.getString(8));
				contact.set_txtNoAdj(cursor.getString(9));
				contact.set_txtProductName(cursor.getString(10));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tStockOpnameDetail_mobileData> getCalculateProduct(SQLiteDatabase db,String NoOP) {
		List<tStockOpnameDetail_mobileData> contactList = null;
		// Select All Query
		tStockOpnameDetail_mobileData dt=new tStockOpnameDetail_mobileData();
		String selectQuery = "select b.[intProductCode],sum(b.[intQtyAdj]) intQty from [tStockOpnameHeader_mobile] a "+
				"inner join [tStockOpnameDetail_mobile] b on a.[txtNoAdj]=b.[txtNoAdj] "+
				"where a.[txtNoAdj]='"+NoOP+"' "+
				"group by b.[intProductCode] ";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tStockOpnameDetail_mobileData>();
			do {
				tStockOpnameDetail_mobileData contact = new tStockOpnameDetail_mobileData();
				contact.set_intProductCode(cursor.getString(0));
				contact.set_intQtyAdj(cursor.getString(1));
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tStockOpnameDetail_mobileData> getAllData(SQLiteDatabase db) {
		List<tStockOpnameDetail_mobileData> contactList = new ArrayList<tStockOpnameDetail_mobileData>();
		// Select All Query
		tStockOpnameDetail_mobileData dt=new tStockOpnameDetail_mobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS;

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tStockOpnameDetail_mobileData>();
			do {
				tStockOpnameDetail_mobileData contact = new tStockOpnameDetail_mobileData();
				contact.set_dtED(cursor.getString(0));
				contact.set_intProductCode(cursor.getString(1));
				contact.set_intQty(cursor.getString(2));
				contact.set_intQtyAdj(cursor.getString(3));
				contact.set_intQtyStock(cursor.getString(4));
				contact.set_intSubmit(cursor.getString(5));
				contact.set_intSync(cursor.getString(6));
				contact.set_txtBatchNo(cursor.getString(7));
				contact.set_txtDataId(cursor.getString(8));
				contact.set_txtNoAdj(cursor.getString(9));
				contact.set_txtProductName(cursor.getString(10));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tStockOpnameDetail_mobileData> getAllDataSubmit(SQLiteDatabase db) {
		List<tStockOpnameDetail_mobileData> contactList = null;
		// Select All Query
		tStockOpnameDetail_mobileData dt=new tStockOpnameDetail_mobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tStockOpnameDetail_mobileData>();
			do {
				tStockOpnameDetail_mobileData contact = new tStockOpnameDetail_mobileData();
				contact.set_dtED(cursor.getString(0));
				contact.set_intProductCode(cursor.getString(1));
				contact.set_intQty(cursor.getString(2));
				contact.set_intQtyAdj(cursor.getString(3));
				contact.set_intQtyStock(cursor.getString(4));
				contact.set_intSubmit(cursor.getString(5));
				contact.set_intSync(cursor.getString(6));
				contact.set_txtBatchNo(cursor.getString(7));
				contact.set_txtDataId(cursor.getString(8));
				contact.set_txtNoAdj(cursor.getString(9));
				contact.set_txtProductName(cursor.getString(10));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tStockOpnameDetail_mobileData> getAllDataNotSync(SQLiteDatabase db) {
		List<tStockOpnameDetail_mobileData> contactList = null;
		// Select All Query
		tStockOpnameDetail_mobileData dt=new tStockOpnameDetail_mobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSync +"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tStockOpnameDetail_mobileData>();
			do {
				tStockOpnameDetail_mobileData contact = new tStockOpnameDetail_mobileData();
				contact.set_dtED(cursor.getString(0));
				contact.set_intProductCode(cursor.getString(1));
				contact.set_intQty(cursor.getString(2));
				contact.set_intQtyAdj(cursor.getString(3));
				contact.set_intQtyStock(cursor.getString(4));
				contact.set_intSubmit(cursor.getString(5));
				contact.set_intSync(cursor.getString(6));
				contact.set_txtBatchNo(cursor.getString(7));
				contact.set_txtDataId(cursor.getString(8));
				contact.set_txtNoAdj(cursor.getString(9));
				contact.set_txtProductName(cursor.getString(10));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tStockOpnameDetail_mobileData> getDataByTxtNoByIntProductByTxtBatchNoByDtED(SQLiteDatabase db,String txtNo,String intProductID,String txtBatchNo,String dtED,String txtDataId) {
		List<tStockOpnameDetail_mobileData> contactList = new ArrayList<tStockOpnameDetail_mobileData>();
		// Select All Query
		tStockOpnameDetail_mobileData dt=new tStockOpnameDetail_mobileData();
		String selectQuery = " SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +
							 " WHERE "+dt.Property_txtNoAdj +"='"+txtNo+"'" +
							 " AND "+dt.Property_intProductCode+"='"+intProductID+"'" +
							 " AND "+dt.Property_txtBatchNo+"='"+txtBatchNo+"'" +
							 " AND "+dt.Property_dtED+"='"+dtED+"'" +
							 " AND "+dt.Property_txtDataId+"<>'"+txtDataId+"'";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tStockOpnameDetail_mobileData>();
			do {
				tStockOpnameDetail_mobileData contact = new tStockOpnameDetail_mobileData();
				contact.set_dtED(cursor.getString(0));
				contact.set_intProductCode(cursor.getString(1));
				contact.set_intQty(cursor.getString(2));
				contact.set_intQtyAdj(cursor.getString(3));
				contact.set_intQtyStock(cursor.getString(4));
				contact.set_intSubmit(cursor.getString(5));
				contact.set_intSync(cursor.getString(6));
				contact.set_txtBatchNo(cursor.getString(7));
				contact.set_txtDataId(cursor.getString(8));
				contact.set_txtNoAdj(cursor.getString(9));
				contact.set_txtProductName(cursor.getString(10));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tStockOpnameDetail_mobileData> getAllDataByHeaderId(SQLiteDatabase db,String Id) {
		List<tStockOpnameDetail_mobileData> contactList = new ArrayList<tStockOpnameDetail_mobileData>();
		// Select All Query
		tStockOpnameDetail_mobileData dt=new tStockOpnameDetail_mobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtNoAdj +"='"+Id+"'";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tStockOpnameDetail_mobileData>();
			do {
				tStockOpnameDetail_mobileData contact = new tStockOpnameDetail_mobileData();
				contact.set_dtED(cursor.getString(0));
				contact.set_intProductCode(cursor.getString(1));
				contact.set_intQty(cursor.getString(2));
				contact.set_intQtyAdj(cursor.getString(3));
				contact.set_intQtyStock(cursor.getString(4));
				contact.set_intSubmit(cursor.getString(5));
				contact.set_intSync(cursor.getString(6));
				contact.set_txtBatchNo(cursor.getString(7));
				contact.set_txtDataId(cursor.getString(8));
				contact.set_txtNoAdj(cursor.getString(9));
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
		tStockOpnameDetail_mobileData dt = new tStockOpnameDetail_mobileData();
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
