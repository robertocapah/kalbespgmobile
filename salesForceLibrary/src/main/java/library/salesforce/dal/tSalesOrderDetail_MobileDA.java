package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.tSalesOrderDetail_MobileData;

public class tSalesOrderDetail_MobileDA {
	private static final String TABLE_CONTACTS = new clsHardCode().txtTable_tSalesOrderDetail_mobile;

	// Contacts Table Columns names

	public tSalesOrderDetail_MobileDA(SQLiteDatabase db) {
		tSalesOrderDetail_MobileData dt=new tSalesOrderDetail_MobileData();
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
				+ dt.Property_txtDataId + " TEXT PRIMARY KEY,"
				+ dt.Property_txtNoSalesOrder + " TEXT NULL,"
				+ dt.Property_intProductCode + " TEXT NULL,"
				+ dt.Property_txtProductName + " TEXT NULL,"
				+ dt.Property_intQty + " TEXT NULL,"
				+ dt.Property_txtBatchNo + " TEXT NULL,"
				+ dt.Property_dtED + " TEXT NULL,"
				+ dt.Property_txtNoPO + " TEXT NULL,"
				+ dt.Property_intItemPriceID + " TEXT NULL,"
				+ dt.Property_decPrice + " TEXT NULL,"
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
	public void SaveDatatSalesOrderDetail_MobileData(SQLiteDatabase db,tSalesOrderDetail_MobileData data) {
		tSalesOrderDetail_MobileData dt=new tSalesOrderDetail_MobileData();
		db.execSQL("INSERT OR REPLACE into "+TABLE_CONTACTS+" ("+dt.PropertyAll+") "+
			"values('"	+String.valueOf(data.get_dtED())+"','"
				+String.valueOf(data.get_intProductCode())+"','"
				+String.valueOf(data.get_intQty())+"','"
				+String.valueOf(data.get_intSubmit())+"','"
				+String.valueOf(data.get_intSync())+"','"
				+String.valueOf(data.get_txtBatchNo())+"','"
				+String.valueOf(data.get_txtDataId())+"','"
				+String.valueOf(data.get_txtNoPO())+"','"
				+String.valueOf(data.get_txtNoSalesOrder())+"','"
				+String.valueOf(data.get_txtProductName())+"','"
				+String.valueOf(data.get_intItemPriceID())+"','"
				+String.valueOf(data.get_decPrice())+"'"
				+")");
	}
	public void UpdateDataItem(SQLiteDatabase db,tSalesOrderDetail_MobileData data) {
		tSalesOrderDetail_MobileData dt=new tSalesOrderDetail_MobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intQty+"="
				+String.valueOf(data.get_intQty())
				+","+dt.Property_intQty+"="
				+String.valueOf(data.get_intQty())
				+" Where " + dt.Property_txtDataId +"='"+ data.get_txtDataId()+"'");
	}
	
	public void UpdateDataItemForSubmit(SQLiteDatabase db,String dataid) {
		tSalesOrderDetail_MobileData dt=new tSalesOrderDetail_MobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intSubmit+"=1"
				+" Where " + dt.Property_txtDataId +"='"+ dataid+"'");
	}
	
	public void UpdateDataDetailByIntProductCode(SQLiteDatabase db,String txtNoSalesOrder,String intProductCode){
		tSalesOrderDetail_MobileData dt=new tSalesOrderDetail_MobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intQty+"=" + dt.Property_intQty + " + 1"
				+" WHERE " + dt.Property_txtNoSalesOrder +"='"+ txtNoSalesOrder+"'"
				+" AND " + dt.Property_intProductCode +"='"+ intProductCode+"'");
	}
	
	public void UpdateDataDetail(SQLiteDatabase db,String txtNoSalesOrder,String intProductCode,String IntQty){
		tSalesOrderDetail_MobileData dt=new tSalesOrderDetail_MobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intQty+"=" + IntQty
				+" WHERE " + dt.Property_txtNoSalesOrder +"='"+ txtNoSalesOrder+"'"
				+" AND " + dt.Property_intProductCode +"='"+ intProductCode+"'");
	}
	
	public void UpdateDataItemSO(SQLiteDatabase db,tSalesOrderDetail_MobileData data) {
		tSalesOrderDetail_MobileData dt=new tSalesOrderDetail_MobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intQty+"='"+String.valueOf(data.get_intQty())+"'"
				+","+dt.Property_txtBatchNo+"='"+String.valueOf(data.get_txtBatchNo())+"'"
				+","+dt.Property_dtED+"='"+String.valueOf(data.get_dtED())+"'"
				+","+dt.Property_intItemPriceID+"='"+String.valueOf(data.get_intItemPriceID())+"'"
				+","+dt.Property_decPrice+"='"+String.valueOf(data.get_decPrice())+"'"
				+" Where " + dt.Property_txtDataId +"='"+ data.get_txtDataId()+"'");
	}
	
	// Getting single contact
	public tSalesOrderDetail_MobileData getData(SQLiteDatabase db,String id) {
		tSalesOrderDetail_MobileData dt=new tSalesOrderDetail_MobileData();
		String selectQuery = "SELECT "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1 AND "+dt.Property_intSync+"=0";
		Cursor cursor = db.rawQuery(selectQuery, null);
		tSalesOrderDetail_MobileData contact = null;
		if(cursor.getCount()>0){
			contact = new tSalesOrderDetail_MobileData();
			if (cursor != null)
				cursor.moveToFirst();
			contact.set_dtED(cursor.getString(0));
			contact.set_intProductCode(cursor.getString(1));
			contact.set_intQty(cursor.getString(2));
			contact.set_intSubmit(cursor.getString(3));
			contact.set_intSync(cursor.getString(4));
			contact.set_txtBatchNo(cursor.getString(5));
			contact.set_txtDataId(cursor.getString(6));
			contact.set_txtNoPO(cursor.getString(7));
			contact.set_txtNoSalesOrder(cursor.getString(8));
			contact.set_txtProductName(cursor.getString(9));
			contact.set_intItemPriceID(cursor.getString(10));
			contact.set_decPrice(cursor.getString(11));
		}
		// return contact
		cursor.close();
		return contact;
	}
	
	public boolean CheckDataPushData(SQLiteDatabase db) {		
		tSalesOrderDetail_MobileData dt=new tSalesOrderDetail_MobileData();
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
	public List<tSalesOrderDetail_MobileData> getAllDataToPushData(SQLiteDatabase db) {
		List<tSalesOrderDetail_MobileData> contactList = null;
		// Select All Query
		tSalesOrderDetail_MobileData dt=new tSalesOrderDetail_MobileData();
		String selectQuery = "SELECT "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1 AND "+dt.Property_intSync+"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tSalesOrderDetail_MobileData>();
			do {
				tSalesOrderDetail_MobileData contact = new tSalesOrderDetail_MobileData();
				contact.set_dtED(cursor.getString(0));
				contact.set_intProductCode(cursor.getString(1));
				contact.set_intQty(cursor.getString(2));
				contact.set_intSubmit(cursor.getString(3));
				contact.set_intSync(cursor.getString(4));
				contact.set_txtBatchNo(cursor.getString(5));
				contact.set_txtDataId(cursor.getString(6));
				contact.set_txtNoPO(cursor.getString(7));
				contact.set_txtNoSalesOrder(cursor.getString(8));
				contact.set_txtProductName(cursor.getString(9));
				contact.set_intItemPriceID(cursor.getString(10));
				contact.set_decPrice(cursor.getString(11));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tSalesOrderDetail_MobileData> getCalculateProductGroupBytxtNoByIntProductBytxtBatchNoByDtED(SQLiteDatabase db,String txtNoSalesOrder, String intProductCode, String txtBatchNo, String dtED) {
		List<tSalesOrderDetail_MobileData> contactList = null;
		// Select All Query
		tSalesOrderDetail_MobileData dt=new tSalesOrderDetail_MobileData();
		String selectQuery = "select b.[intProductCode],b.[txtBatchNo],b.[dtED],sum(b.[intQty]) intQty from [tSalesOrderHeader_Mobile] a "+
		"inner join [tSalesOrderDetail_Mobile] b on a.[txtNoSalesOrder]=b.[txtNoSalesOrder] "+
		"where b.[txtNoSalesOrder]='"+txtNoSalesOrder+"' "+
		"and b.[intProductCode]='"+intProductCode+"' "+
		"and b.[txtBatchNo]='"+txtBatchNo+"' "+ 
		"and b.[dtED]='"+dtED+"' "+
		"group by b.[intProductCode],b.[txtBatchNo],b.[dtED] ";

		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tSalesOrderDetail_MobileData>();
			do {
				tSalesOrderDetail_MobileData contact = new tSalesOrderDetail_MobileData();
				contact.set_intProductCode(cursor.getString(0));
				contact.set_txtBatchNo(cursor.getString(1));
				contact.set_dtED(cursor.getString(2));
				contact.set_intQty(cursor.getString(3));
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tSalesOrderDetail_MobileData> getCalculateProductGroupByintProductCodetxtBatchNodtED(SQLiteDatabase db,String txtNoSO) {
		List<tSalesOrderDetail_MobileData> contactList = null;
		// Select All Query
		tSalesOrderDetail_MobileData dt=new tSalesOrderDetail_MobileData();
		String selectQuery = "select b.[intProductCode],b.[txtBatchNo],b.[dtED],sum(b.[intQty]) intQty from [tSalesOrderHeader_mobile] a "+
		"inner join [tSalesOrderDetail_mobile] b on a.[txtNoSalesOrder]=b.[txtNoSalesOrder] "+
		"where a.[txtNoSalesOrder]='"+txtNoSO+"' "+
		"group by b.[intProductCode],b.[txtBatchNo],b.[dtED] ";

		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tSalesOrderDetail_MobileData>();
			do {
				tSalesOrderDetail_MobileData contact = new tSalesOrderDetail_MobileData();
				contact.set_intProductCode(cursor.getString(0));
				contact.set_txtBatchNo(cursor.getString(1));
				contact.set_dtED(cursor.getString(2));
				contact.set_intQty(cursor.getString(3));
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tSalesOrderDetail_MobileData> getCalculateProduct(SQLiteDatabase db,String txtNoSO) {
		List<tSalesOrderDetail_MobileData> contactList = null;
		// Select All Query
		tSalesOrderDetail_MobileData dt=new tSalesOrderDetail_MobileData();
		String selectQuery = "select b.[intProductCode],sum(b.[intQty]) intQty from [tSalesOrderHeader_mobile] a "+
		"inner join [tSalesOrderDetail_mobile] b on a.[txtNoSalesOrder]=b.[txtNoSalesOrder] "+
		"where a.[txtNoSalesOrder]='"+txtNoSO+"' "+
		"group by b.[intProductCode] ";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tSalesOrderDetail_MobileData>();
			do {
				tSalesOrderDetail_MobileData contact = new tSalesOrderDetail_MobileData();
				contact.set_intProductCode(cursor.getString(0));
				contact.set_intQty(cursor.getString(1));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tSalesOrderDetail_MobileData> getAllData(SQLiteDatabase db) {
		List<tSalesOrderDetail_MobileData> contactList = null;
		// Select All Query
		tSalesOrderDetail_MobileData dt=new tSalesOrderDetail_MobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS;

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tSalesOrderDetail_MobileData>();
			do {
				tSalesOrderDetail_MobileData contact = new tSalesOrderDetail_MobileData();
				contact.set_dtED(cursor.getString(0));
				contact.set_intProductCode(cursor.getString(1));
				contact.set_intQty(cursor.getString(2));
				contact.set_intSubmit(cursor.getString(3));
				contact.set_intSync(cursor.getString(4));
				contact.set_txtBatchNo(cursor.getString(5));
				contact.set_txtDataId(cursor.getString(6));
				contact.set_txtNoPO(cursor.getString(7));
				contact.set_txtNoSalesOrder(cursor.getString(8));
				contact.set_txtProductName(cursor.getString(9));
				contact.set_intItemPriceID(cursor.getString(10));
				contact.set_decPrice(cursor.getString(11));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tSalesOrderDetail_MobileData> getAllDataSubmit(SQLiteDatabase db) {
		List<tSalesOrderDetail_MobileData> contactList = null;
		// Select All Query
		tSalesOrderDetail_MobileData dt=new tSalesOrderDetail_MobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tSalesOrderDetail_MobileData>();
			do {
				tSalesOrderDetail_MobileData contact = new tSalesOrderDetail_MobileData();
				contact.set_dtED(cursor.getString(0));
				contact.set_intProductCode(cursor.getString(1));
				contact.set_intQty(cursor.getString(2));
				contact.set_intSubmit(cursor.getString(3));
				contact.set_intSync(cursor.getString(4));
				contact.set_txtBatchNo(cursor.getString(5));
				contact.set_txtDataId(cursor.getString(6));
				contact.set_txtNoPO(cursor.getString(7));
				contact.set_txtNoSalesOrder(cursor.getString(8));
				contact.set_txtProductName(cursor.getString(9));
				contact.set_intItemPriceID(cursor.getString(10));
				contact.set_decPrice(cursor.getString(11));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tSalesOrderDetail_MobileData> getAllDataNotSync(SQLiteDatabase db) {
		List<tSalesOrderDetail_MobileData> contactList = null;
		// Select All Query
		tSalesOrderDetail_MobileData dt=new tSalesOrderDetail_MobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSync +"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tSalesOrderDetail_MobileData>();
			do {
				tSalesOrderDetail_MobileData contact = new tSalesOrderDetail_MobileData();
				contact.set_dtED(cursor.getString(0));
				contact.set_intProductCode(cursor.getString(1));
				contact.set_intQty(cursor.getString(2));
				contact.set_intSubmit(cursor.getString(3));
				contact.set_intSync(cursor.getString(4));
				contact.set_txtBatchNo(cursor.getString(5));
				contact.set_txtDataId(cursor.getString(6));
				contact.set_txtNoPO(cursor.getString(7));
				contact.set_txtNoSalesOrder(cursor.getString(8));
				contact.set_txtProductName(cursor.getString(9));
				contact.set_intItemPriceID(cursor.getString(10));
				contact.set_decPrice(cursor.getString(11));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	
	public List<tSalesOrderDetail_MobileData> getAllDataByTxtDataID(SQLiteDatabase db,String txtDataId) {
		List<tSalesOrderDetail_MobileData> contactList = new ArrayList<tSalesOrderDetail_MobileData>();
		// Select All Query
		tSalesOrderDetail_MobileData dt=new tSalesOrderDetail_MobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtDataId +"='"+txtDataId+"'";

		Cursor cursor = db.rawQuery(selectQuery, null);
		
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tSalesOrderDetail_MobileData>();
			do {
				tSalesOrderDetail_MobileData contact = new tSalesOrderDetail_MobileData();
				contact.set_dtED(cursor.getString(0));
				contact.set_intProductCode(cursor.getString(1));
				contact.set_intQty(cursor.getString(2));
				contact.set_intSubmit(cursor.getString(3));
				contact.set_intSync(cursor.getString(4));
				contact.set_txtBatchNo(cursor.getString(5));
				contact.set_txtDataId(cursor.getString(6));
				contact.set_txtNoPO(cursor.getString(7));
				contact.set_txtNoSalesOrder(cursor.getString(8));
				contact.set_txtProductName(cursor.getString(9));
				contact.set_intItemPriceID(cursor.getString(10));
				contact.set_decPrice(cursor.getString(11));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tSalesOrderDetail_MobileData> getDataByTxtNoByIntProductByTxtBatchNoByDtED(SQLiteDatabase db,String txtNo,String intProductID,String txtBatchNo,String dtED,String txtDataId) {
		List<tSalesOrderDetail_MobileData> contactList = new ArrayList<tSalesOrderDetail_MobileData>();
		// Select All Query
		tSalesOrderDetail_MobileData dt=new tSalesOrderDetail_MobileData();
		String selectQuery = " SELECT "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +
							 " WHERE "+dt.Property_txtNoSalesOrder +"='"+txtNo+"'" +
							 " AND "+dt.Property_intProductCode+"='"+intProductID+"'" +
							 " AND "+dt.Property_txtBatchNo+"='"+txtBatchNo+"'" +
							 " AND "+dt.Property_dtED+"='"+dtED+"'" +
							 " AND "+dt.Property_txtDataId+"<>'"+txtDataId+"'";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tSalesOrderDetail_MobileData>();
			do {
				tSalesOrderDetail_MobileData contact = new tSalesOrderDetail_MobileData();
				contact.set_dtED(cursor.getString(0));
				contact.set_intProductCode(cursor.getString(1));
				contact.set_intQty(cursor.getString(2));
				contact.set_intSubmit(cursor.getString(3));
				contact.set_intSync(cursor.getString(4));
				contact.set_txtBatchNo(cursor.getString(5));
				contact.set_txtDataId(cursor.getString(6));
				contact.set_txtNoPO(cursor.getString(7));
				contact.set_txtNoSalesOrder(cursor.getString(8));
				contact.set_txtProductName(cursor.getString(9));
				contact.set_intItemPriceID(cursor.getString(10));
				contact.set_decPrice(cursor.getString(11));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tSalesOrderDetail_MobileData> getAllDataByHeaderId(SQLiteDatabase db,String Id) {
		List<tSalesOrderDetail_MobileData> contactList = new ArrayList<tSalesOrderDetail_MobileData>();
		// Select All Query
		tSalesOrderDetail_MobileData dt=new tSalesOrderDetail_MobileData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtNoSalesOrder +"='"+Id+"'";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tSalesOrderDetail_MobileData>();
			do {
				tSalesOrderDetail_MobileData contact = new tSalesOrderDetail_MobileData();
				contact.set_dtED(cursor.getString(0));
				contact.set_intProductCode(cursor.getString(1));
				contact.set_intQty(cursor.getString(2));
				contact.set_intSubmit(cursor.getString(3));
				contact.set_intSync(cursor.getString(4));
				contact.set_txtBatchNo(cursor.getString(5));
				contact.set_txtDataId(cursor.getString(6));
				contact.set_txtNoPO(cursor.getString(7));
				contact.set_txtNoSalesOrder(cursor.getString(8));
				contact.set_txtProductName(cursor.getString(9));
				contact.set_intItemPriceID(cursor.getString(10));
				contact.set_decPrice(cursor.getString(11));
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
		tSalesOrderDetail_MobileData dt = new tSalesOrderDetail_MobileData();
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
