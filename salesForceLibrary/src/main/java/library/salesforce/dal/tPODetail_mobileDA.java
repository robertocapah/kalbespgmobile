package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.tPODetail_mobileData;

public class tPODetail_mobileDA {
	private static final String TABLE_CONTACTS = new clsHardCode().txtTable_tPODetail_Mobile;

	// Contacts Table Columns names

	public tPODetail_mobileDA(SQLiteDatabase db) {
		tPODetail_mobileData dt=new tPODetail_mobileData();
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
				+ dt.Property_txtDataId + " TEXT PRIMARY KEY,"
				+ dt.Property_txtNoPO + " TEXT NULL,"
				+ dt.Property_txtNoDoc + " TEXT NULL,"
				+ dt.Property_intProductCode + " TEXT NULL,"
				+ dt.Property_txtProductName + " TEXT NULL,"
				+ dt.Property_intQty + " TEXT NULL,"
				+ dt.Property_intQtyGRN + " TEXT NULL,"
				+ dt.Property_intQtySisa + " TEXT NULL,"
				+ dt.Property_intStockAwal + " TEXT NULL,"
				+ dt.Property_bitActive + " TEXT NULL,"
				+ dt.Property_intSubmit + " TEXT NULL,"
				+ dt.Property_intSync + " TEXT NULL"
				+ ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}
	public tPODetail_mobileData getDataBYHeaderAndProduct2(SQLiteDatabase db,String idPO,String idProduct) {
		tPODetail_mobileData dt=new tPODetail_mobileData();
		String selectQuery = "SELECT "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtNoPO+"='"+idPO+"' AND "+dt.Property_intProductCode+"='"+idProduct+"' AND " + dt.Property_bitActive + "='1'";
		Cursor cursor = db.rawQuery(selectQuery, null);
		tPODetail_mobileData contact = null;
		if(cursor.getCount()>0){
			contact = new tPODetail_mobileData();
			if (cursor != null)
				cursor.moveToFirst();
			contact.set_intProductCode(cursor.getString(0));
			contact.set_intQty(cursor.getString(1));
			contact.set_intQtyGRN(cursor.getString(2));
			contact.set_intQtySisa(cursor.getString(3));
			contact.set_intStockAwal(cursor.getString(4));
			contact.set_intSubmit(cursor.getString(5));
			contact.set_intSync(cursor.getString(6));
			contact.set_txtDataId(cursor.getString(7));
			contact.set_txtNoDoc(cursor.getString(8));
			contact.set_txtNoPO(cursor.getString(9));
			contact.set_txtProductName(cursor.getString(10));
			contact.set_bitActive(cursor.getString(11));
		}
		// return contact
		cursor.close();
		return contact;
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
	public void SaveDatatPODetail_mobileData(SQLiteDatabase db,tPODetail_mobileData data) {
		tPODetail_mobileData dt=new tPODetail_mobileData();
		db.execSQL("INSERT OR REPLACE into "+TABLE_CONTACTS+" ("+dt.Property_All+") "+
			"values('"	+String.valueOf(data.get_intProductCode())+"','"
				+String.valueOf(data.get_intQty())+"','"
				+String.valueOf(data.get_intQtyGRN())+"','"
				+String.valueOf(data.get_intQtySisa())+"','"
				+String.valueOf(data.get_intStockAwal())+"','"
				+String.valueOf(data.get_intSubmit())+"','"
				+String.valueOf(data.get_intSync())+"','"
				+String.valueOf(data.get_txtDataId())+"','"
				+String.valueOf(data.get_txtNoDoc())+"','"
				+String.valueOf(data.get_txtNoPO())+"','"
				+String.valueOf(data.get_txtProductName())+"','"
				+String.valueOf(data.get_bitActive())+"'"
				+")");
	}
	public List<tPODetail_mobileData> getAllDataByHeaderId2(SQLiteDatabase db,String Id) {
		List<tPODetail_mobileData> contactList = new ArrayList<tPODetail_mobileData>();
		// Select All Query
		tPODetail_mobileData dt=new tPODetail_mobileData();
		String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtNoPO +"='"+Id+"' AND " + dt.Property_bitActive + "='1'";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPODetail_mobileData>();
			do {
				tPODetail_mobileData contact = new tPODetail_mobileData();
				contact.set_intProductCode(cursor.getString(0));
				contact.set_intQty(cursor.getString(1));
				contact.set_intQtyGRN(cursor.getString(2));
				contact.set_intQtySisa(cursor.getString(3));
				contact.set_intStockAwal(cursor.getString(4));
				contact.set_intSubmit(cursor.getString(5));
				contact.set_intSync(cursor.getString(6));
				contact.set_txtDataId(cursor.getString(7));
				contact.set_txtNoDoc(cursor.getString(8));
				contact.set_txtNoPO(cursor.getString(9));
				contact.set_txtProductName(cursor.getString(10));
				contact.set_bitActive(cursor.getString(11));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public void UpdateDataItem(SQLiteDatabase db,tPODetail_mobileData data) {
		tPODetail_mobileData dt=new tPODetail_mobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intProductCode+"="+
				"'"+String.valueOf(data.get_intProductCode())+"'"
				+","+dt.Property_txtProductName+"="+
				"'"+String.valueOf(data.get_txtProductName())+"'"
				+","+dt.Property_intQty+"="+
				"'"+String.valueOf(data.get_intQty())+"'"
				+","+dt.Property_intQtyGRN+"="+
				"'"+String.valueOf(data.get_intQtyGRN())+"'"
				+","+dt.Property_intQtySisa+"="+
				"'"+String.valueOf(data.get_intQtySisa())+"'"
				+" Where " + dt.Property_txtDataId +"='"+ data.get_txtDataId()+"'");
	}
	
	public void UpdateDataItemForSubmit(SQLiteDatabase db,String dataid) {
		tPODetail_mobileData dt=new tPODetail_mobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intSubmit+"=1"
				+" Where " + dt.Property_txtDataId +"='"+ dataid+"'");
	}
	public void UpdateDataItemForSync(SQLiteDatabase db,String dataid) {
		tPODetail_mobileData dt=new tPODetail_mobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intSync+"=1"
				+" Where " + dt.Property_txtDataId +"='"+ dataid+"'");
	}
	public void UpdateDataItemForSubmitByHeaderId(SQLiteDatabase db,String dataid) {
		tPODetail_mobileData dt=new tPODetail_mobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intSubmit+"=1"
				+" Where " + dt.Property_txtNoPO +"='"+ dataid+"'");
	}
	
	public tPODetail_mobileData getDataBYHeaderAndProduct(SQLiteDatabase db,String idPO,String idProduct) {
		tPODetail_mobileData dt=new tPODetail_mobileData();
		String selectQuery = "SELECT "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtNoPO+"='"+idPO+"' AND "+dt.Property_intProductCode+"='"+idProduct+"'";
		Cursor cursor = db.rawQuery(selectQuery, null);
		tPODetail_mobileData contact = null;
		if(cursor.getCount()>0){
			contact = new tPODetail_mobileData();
			if (cursor != null)
				cursor.moveToFirst();
			contact.set_intProductCode(cursor.getString(0));
			contact.set_intQty(cursor.getString(1));
			contact.set_intQtyGRN(cursor.getString(2));
			contact.set_intQtySisa(cursor.getString(3));
			contact.set_intStockAwal(cursor.getString(4));
			contact.set_intSubmit(cursor.getString(5));
			contact.set_intSync(cursor.getString(6));
			contact.set_txtDataId(cursor.getString(7));
			contact.set_txtNoDoc(cursor.getString(8));
			contact.set_txtNoPO(cursor.getString(9));
			contact.set_txtProductName(cursor.getString(10));
			contact.set_bitActive(cursor.getString(11));
		}
		// return contact
		cursor.close();
		return contact;
	}
	// Getting single contact
	public tPODetail_mobileData getData(SQLiteDatabase db,String id) {
		tPODetail_mobileData dt=new tPODetail_mobileData();
		String selectQuery = "SELECT "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1 AND "+dt.Property_intSync+"=0";
		Cursor cursor = db.rawQuery(selectQuery, null);
		tPODetail_mobileData contact = null;
		if(cursor.getCount()>0){
			contact = new tPODetail_mobileData();
			if (cursor != null)
				cursor.moveToFirst();
			contact.set_intProductCode(cursor.getString(0));
			contact.set_intQty(cursor.getString(1));
			contact.set_intQtyGRN(cursor.getString(2));
			contact.set_intQtySisa(cursor.getString(3));
			contact.set_intStockAwal(cursor.getString(4));
			contact.set_intSubmit(cursor.getString(5));
			contact.set_intSync(cursor.getString(6));
			contact.set_txtDataId(cursor.getString(7));
			contact.set_txtNoDoc(cursor.getString(8));
			contact.set_txtNoPO(cursor.getString(9));
			contact.set_txtProductName(cursor.getString(10));
			contact.set_bitActive(cursor.getString(11));
		}
		// return contact
		cursor.close();
		return contact;
	}
	
	public boolean CheckDataPushData(SQLiteDatabase db) {		
		tPODetail_mobileData dt=new tPODetail_mobileData();
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
	public List<tPODetail_mobileData> getAllDataToPushData(SQLiteDatabase db) {
		List<tPODetail_mobileData> contactList = null;
		// Select All Query
		tPODetail_mobileData dt=new tPODetail_mobileData();
		String selectQuery = "SELECT "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1 AND "+dt.Property_intSync+"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPODetail_mobileData>();
			do {
				tPODetail_mobileData contact = new tPODetail_mobileData();
				contact.set_intProductCode(cursor.getString(0));
				contact.set_intQty(cursor.getString(1));
				contact.set_intQtyGRN(cursor.getString(2));
				contact.set_intQtySisa(cursor.getString(3));
				contact.set_intStockAwal(cursor.getString(4));
				contact.set_intSubmit(cursor.getString(5));
				contact.set_intSync(cursor.getString(6));
				contact.set_txtDataId(cursor.getString(7));
				contact.set_txtNoDoc(cursor.getString(8));
				contact.set_txtNoPO(cursor.getString(9));
				contact.set_txtProductName(cursor.getString(10));
				contact.set_bitActive(cursor.getString(11));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tPODetail_mobileData> getAllData(SQLiteDatabase db) {
		List<tPODetail_mobileData> contactList = null;
		// Select All Query
		tPODetail_mobileData dt=new tPODetail_mobileData();
		String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS;

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPODetail_mobileData>();
			do {
				tPODetail_mobileData contact = new tPODetail_mobileData();
				contact.set_intProductCode(cursor.getString(0));
				contact.set_intQty(cursor.getString(1));
				contact.set_intQtyGRN(cursor.getString(2));
				contact.set_intQtySisa(cursor.getString(3));
				contact.set_intStockAwal(cursor.getString(4));
				contact.set_intSubmit(cursor.getString(5));
				contact.set_intSync(cursor.getString(6));
				contact.set_txtDataId(cursor.getString(7));
				contact.set_txtNoDoc(cursor.getString(8));
				contact.set_txtNoPO(cursor.getString(9));
				contact.set_txtProductName(cursor.getString(10));
				contact.set_bitActive(cursor.getString(11));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tPODetail_mobileData> getAllDataSubmit(SQLiteDatabase db) {
		List<tPODetail_mobileData> contactList = null;
		// Select All Query
		tPODetail_mobileData dt=new tPODetail_mobileData();
		String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPODetail_mobileData>();
			do {
				tPODetail_mobileData contact = new tPODetail_mobileData();
				contact.set_intProductCode(cursor.getString(0));
				contact.set_intQty(cursor.getString(1));
				contact.set_intQtyGRN(cursor.getString(2));
				contact.set_intQtySisa(cursor.getString(3));
				contact.set_intStockAwal(cursor.getString(4));
				contact.set_intSubmit(cursor.getString(5));
				contact.set_intSync(cursor.getString(6));
				contact.set_txtDataId(cursor.getString(7));
				contact.set_txtNoDoc(cursor.getString(8));
				contact.set_txtNoPO(cursor.getString(9));
				contact.set_txtProductName(cursor.getString(10));
				contact.set_bitActive(cursor.getString(11));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tPODetail_mobileData> getAllDataNotSync(SQLiteDatabase db) {
		List<tPODetail_mobileData> contactList = null;
		// Select All Query
		tPODetail_mobileData dt=new tPODetail_mobileData();
		String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSync +"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPODetail_mobileData>();
			do {
				tPODetail_mobileData contact = new tPODetail_mobileData();
				contact.set_intProductCode(cursor.getString(0));
				contact.set_intQty(cursor.getString(1));
				contact.set_intQtyGRN(cursor.getString(2));
				contact.set_intQtySisa(cursor.getString(3));
				contact.set_intStockAwal(cursor.getString(4));
				contact.set_intSubmit(cursor.getString(5));
				contact.set_intSync(cursor.getString(6));
				contact.set_txtDataId(cursor.getString(7));
				contact.set_txtNoDoc(cursor.getString(8));
				contact.set_txtNoPO(cursor.getString(9));
				contact.set_txtProductName(cursor.getString(10));
				contact.set_bitActive(cursor.getString(11));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tPODetail_mobileData> getAllDataByHeaderId(SQLiteDatabase db,String Id) {
		List<tPODetail_mobileData> contactList = new ArrayList<tPODetail_mobileData>();
		// Select All Query
		tPODetail_mobileData dt=new tPODetail_mobileData();
		String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtNoPO +"='"+Id+"' AND "+dt.Property_bitActive+"=1";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPODetail_mobileData>();
			do {
				tPODetail_mobileData contact = new tPODetail_mobileData();
				contact.set_intProductCode(cursor.getString(0));
				contact.set_intQty(cursor.getString(1));
				contact.set_intQtyGRN(cursor.getString(2));
				contact.set_intQtySisa(cursor.getString(3));
				contact.set_intStockAwal(cursor.getString(4));
				contact.set_intSubmit(cursor.getString(5));
				contact.set_intSync(cursor.getString(6));
				contact.set_txtDataId(cursor.getString(7));
				contact.set_txtNoDoc(cursor.getString(8));
				contact.set_txtNoPO(cursor.getString(9));
				contact.set_txtProductName(cursor.getString(10));
				contact.set_bitActive(cursor.getString(11));
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
		tPODetail_mobileData dt = new tPODetail_mobileData();
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
