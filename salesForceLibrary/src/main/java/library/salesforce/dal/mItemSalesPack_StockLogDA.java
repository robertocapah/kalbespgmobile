package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.mItemSalesPack_StockLogData;

public class mItemSalesPack_StockLogDA {
	private static final String TABLE_CONTACTS = new clsHardCode().txtTable_mItemSalesPack_StockLog;

	// Contacts Table Columns names

	public mItemSalesPack_StockLogDA(SQLiteDatabase db) {
		mItemSalesPack_StockLogData dt=new mItemSalesPack_StockLogData();
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
				+ dt.Property_txtDataId + " TEXT PRIMARY KEY," 
				+ dt.Property_txtPeriode + " TEXT NULL,"
				+ dt.Property_intWeek + " TEXT NULL,"
				+ dt.Property_intProductCode + " TEXT NULL,"
				+ dt.Property_intSaldoAwal + " TEXT NULL,"
				+ dt.Property_intQtyIn + " TEXT NULL,"
				+ dt.Property_intQtyOut + " TEXT NULL,"
				+ dt.Property_intQtyAdj + " TEXT NULL,"
				+ dt.Property_intQtyReserved + " TEXT NULL,"
				+ dt.Property_intQtyAvailable + " TEXT NULL,"
				+ dt.Property_txtOutletCode + " TEXT NULL,"
				+ dt.Property_txtOutletName + " TEXT NULL,"
				+ dt.Property_txtBranchCode + " TEXT NULL,"
				+ dt.Property_intSubmit + " TEXT NULL,"
				+ dt.Property_intSync + " TEXT NULL,"
				+ dt.Property_txtNoTransaction + ")";
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
	public void SaveDatamItemSalesPack_StockLogData(SQLiteDatabase db,mItemSalesPack_StockLogData data) {
		mItemSalesPack_StockLogData dt=new mItemSalesPack_StockLogData();
		//ContentValues values = new ContentValues();
		//values.put(dt.Property_intIdMenu, data.get_intIdMenu()); 
		//values.put(dt.Property_txtRoleId, data.get_txtRoleId()); 
		// Inserting Row
		//db.insert(TABLE_CONTACTS, null, values);
		db.execSQL("INSERT OR REPLACE into "+TABLE_CONTACTS+" ("+dt.PropertyAll+") "+
			"values('"	+String.valueOf(data.get_intProductCode())+"','"
				+String.valueOf(data.get_intQtyAdj())+"','"
				+String.valueOf(data.get_intQtyAvailable())+"','"
				+String.valueOf(data.get_intQtyIn())+"','"
				+String.valueOf(data.get_intQtyOut())+"','"
				+String.valueOf(data.get_intQtyReserved())+"','"
				+String.valueOf(data.get_intSaldoAwal())+"','"
				+String.valueOf(data.get_intSubmit())+"','"
				+String.valueOf(data.get_intSync())+"','"
				+String.valueOf(data.get_intWeek())+"','"
				+String.valueOf(data.get_txtBranchCode())+"','"
				+String.valueOf(data.get_txtDataId())+"','"
				+String.valueOf(data.get_txtNoTransaction())+"','"
				+String.valueOf(data.get_txtOutletCode())+"','"
				+String.valueOf(data.get_txtOutletName())+"','"
				+String.valueOf(data.get_txtPeriode())+"')");
	}
	public void UpdateDataItem(SQLiteDatabase db,mItemSalesPack_StockLogData data) {
		mItemSalesPack_StockLogData dt=new mItemSalesPack_StockLogData();
		//ContentValues values = new ContentValues();
		//values.put(dt.Property_intIdMenu, data.get_intIdMenu()); 
		//values.put(dt.Property_txtRoleId, data.get_txtRoleId()); 
		// Inserting Row
		//db.insert(TABLE_CONTACTS, null, values);
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intQtyAdj+"="
				+String.valueOf(data.get_intQtyAdj())
				+","+dt.Property_intQtyAvailable+"="+
				String.valueOf(data.get_intQtyAvailable())
				+","+dt.Property_intQtyIn+"="+
				String.valueOf(data.get_intQtyIn())
				+","+dt.Property_intQtyOut+"="+
				String.valueOf(data.get_intQtyOut())
				+" Where " + dt.Property_txtDataId +"='"+ data.get_txtDataId()+"'");
	}
	
	public void UpdateDataItemForSubmit(SQLiteDatabase db,String dataid) {
		mItemSalesPack_StockLogData dt=new mItemSalesPack_StockLogData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intSubmit+"=1"
				+" Where " + dt.Property_txtDataId +"='"+ dataid+"'");
	}
	
	// Getting single contact
	public mItemSalesPack_StockLogData getData(SQLiteDatabase db,String id) {
		mItemSalesPack_StockLogData dt=new mItemSalesPack_StockLogData();
		String selectQuery = "SELECT 1 FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtDataId+"='"+id+"'";
		Cursor cursor = db.rawQuery(selectQuery, null);
		mItemSalesPack_StockLogData contact = null;
		if(cursor.getCount()>0){
			contact = new mItemSalesPack_StockLogData();
			if (cursor != null)
				cursor.moveToFirst();
			contact.set_intProductCode(cursor.getString(0));
			contact.set_intQtyAdj(cursor.getString(1));
			contact.set_intQtyIn(cursor.getString(3));
			contact.set_intQtyOut(cursor.getString(4));
			contact.set_intQtyReserved(cursor.getString(5));
			contact.set_intSaldoAwal(cursor.getString(6));
			contact.set_intSubmit(cursor.getString(7));
			contact.set_intSync(cursor.getString(8));
			contact.set_intWeek(cursor.getString(9));
			contact.set_txtBranchCode(cursor.getString(10));
			contact.set_txtDataId(cursor.getString(11));
			contact.set_txtNoTransaction(cursor.getString(12));
			contact.set_txtOutletCode(cursor.getString(13));
			contact.set_txtOutletName(cursor.getString(14));
			contact.set_txtPeriode(cursor.getString(15));
		}
		// return contact
		cursor.close();
		return contact;
	}
	
	public boolean CheckDataPushData(SQLiteDatabase db) {		
		mItemSalesPack_StockLogData dt=new mItemSalesPack_StockLogData();
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
	public List<mItemSalesPack_StockLogData> getAllDataToPushData(SQLiteDatabase db) {
		List<mItemSalesPack_StockLogData> contactList = null;
		// Select All Query
		mItemSalesPack_StockLogData dt=new mItemSalesPack_StockLogData();
		String selectQuery = "SELECT "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1 AND "+dt.Property_intSync+"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<mItemSalesPack_StockLogData>();
			do {
				mItemSalesPack_StockLogData contact = new mItemSalesPack_StockLogData();
				contact.set_intProductCode(cursor.getString(0));
				contact.set_intQtyAdj(cursor.getString(1));
				contact.set_intQtyIn(cursor.getString(3));
				contact.set_intQtyOut(cursor.getString(4));
				contact.set_intQtyReserved(cursor.getString(5));
				contact.set_intSaldoAwal(cursor.getString(6));
				contact.set_intSubmit(cursor.getString(7));
				contact.set_intSync(cursor.getString(8));
				contact.set_intWeek(cursor.getString(9));
				contact.set_txtBranchCode(cursor.getString(10));
				contact.set_txtDataId(cursor.getString(11));
				contact.set_txtNoTransaction(cursor.getString(12));
				contact.set_txtOutletCode(cursor.getString(13));
				contact.set_txtOutletName(cursor.getString(14));
				contact.set_txtPeriode(cursor.getString(15));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<mItemSalesPack_StockLogData> getAllData(SQLiteDatabase db) {
		List<mItemSalesPack_StockLogData> contactList = new ArrayList<mItemSalesPack_StockLogData>();
		// Select All Query
		mItemSalesPack_StockLogData dt=new mItemSalesPack_StockLogData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS;

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<mItemSalesPack_StockLogData>();
			do {
				mItemSalesPack_StockLogData contact = new mItemSalesPack_StockLogData();
				contact.set_intProductCode(cursor.getString(0));
				contact.set_intQtyAdj(cursor.getString(1));
				contact.set_intQtyIn(cursor.getString(3));
				contact.set_intQtyOut(cursor.getString(4));
				contact.set_intQtyReserved(cursor.getString(5));
				contact.set_intSaldoAwal(cursor.getString(6));
				contact.set_intSubmit(cursor.getString(7));
				contact.set_intSync(cursor.getString(8));
				contact.set_intWeek(cursor.getString(9));
				contact.set_txtBranchCode(cursor.getString(10));
				contact.set_txtDataId(cursor.getString(11));
				contact.set_txtNoTransaction(cursor.getString(12));
				contact.set_txtOutletCode(cursor.getString(13));
				contact.set_txtOutletName(cursor.getString(14));
				contact.set_txtPeriode(cursor.getString(15));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<mItemSalesPack_StockLogData> getAllDataByOuletAndIdProduct(SQLiteDatabase db,String txtOutletCode,String idProduct,String intWeek) {
		List<mItemSalesPack_StockLogData> contactList = null;
		// Select All Query
		mItemSalesPack_StockLogData dt=new mItemSalesPack_StockLogData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtOutletCode+"='"+txtOutletCode+"' AND "+dt.Property_intProductCode+"='"+idProduct+"' AND "+dt.Property_intWeek+"='"+intWeek+"'";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<mItemSalesPack_StockLogData>();
			do {
				mItemSalesPack_StockLogData contact = new mItemSalesPack_StockLogData();
				contact.set_intProductCode(cursor.getString(0));
				contact.set_intQtyAdj(cursor.getString(1));
				contact.set_intQtyIn(cursor.getString(3));
				contact.set_intQtyOut(cursor.getString(4));
				contact.set_intQtyReserved(cursor.getString(5));
				contact.set_intSaldoAwal(cursor.getString(6));
				contact.set_intSubmit(cursor.getString(7));
				contact.set_intSync(cursor.getString(8));
				contact.set_intWeek(cursor.getString(9));
				contact.set_txtBranchCode(cursor.getString(10));
				contact.set_txtDataId(cursor.getString(11));
				contact.set_txtNoTransaction(cursor.getString(12));
				contact.set_txtOutletCode(cursor.getString(13));
				contact.set_txtOutletName(cursor.getString(14));
				contact.set_txtPeriode(cursor.getString(15));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<mItemSalesPack_StockLogData> getAllDataByOulet(SQLiteDatabase db,String txtOutletCode) {
		List<mItemSalesPack_StockLogData> contactList = null;
		// Select All Query
		mItemSalesPack_StockLogData dt=new mItemSalesPack_StockLogData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtOutletCode+"='"+txtOutletCode+"' AND "+dt.Property_intSubmit+"=1";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<mItemSalesPack_StockLogData>();
			do {
				mItemSalesPack_StockLogData contact = new mItemSalesPack_StockLogData();
				contact.set_intProductCode(cursor.getString(0));
				contact.set_intQtyAdj(cursor.getString(1));
				contact.set_intQtyIn(cursor.getString(3));
				contact.set_intQtyOut(cursor.getString(4));
				contact.set_intQtyReserved(cursor.getString(5));
				contact.set_intSaldoAwal(cursor.getString(6));
				contact.set_intSubmit(cursor.getString(7));
				contact.set_intSync(cursor.getString(8));
				contact.set_intWeek(cursor.getString(9));
				contact.set_txtBranchCode(cursor.getString(10));
				contact.set_txtDataId(cursor.getString(11));
				contact.set_txtNoTransaction(cursor.getString(12));
				contact.set_txtOutletCode(cursor.getString(13));
				contact.set_txtOutletName(cursor.getString(14));
				contact.set_txtPeriode(cursor.getString(15));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<mItemSalesPack_StockLogData> getAllDataSubmit(SQLiteDatabase db) {
		List<mItemSalesPack_StockLogData> contactList = null;
		// Select All Query
		mItemSalesPack_StockLogData dt=new mItemSalesPack_StockLogData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<mItemSalesPack_StockLogData>();
			do {
				mItemSalesPack_StockLogData contact = new mItemSalesPack_StockLogData();
				contact.set_intProductCode(cursor.getString(0));
				contact.set_intQtyAdj(cursor.getString(1));
				contact.set_intQtyIn(cursor.getString(3));
				contact.set_intQtyOut(cursor.getString(4));
				contact.set_intQtyReserved(cursor.getString(5));
				contact.set_intSaldoAwal(cursor.getString(6));
				contact.set_intSubmit(cursor.getString(7));
				contact.set_intSync(cursor.getString(8));
				contact.set_intWeek(cursor.getString(9));
				contact.set_txtBranchCode(cursor.getString(10));
				contact.set_txtDataId(cursor.getString(11));
				contact.set_txtNoTransaction(cursor.getString(12));
				contact.set_txtOutletCode(cursor.getString(13));
				contact.set_txtOutletName(cursor.getString(14));
				contact.set_txtPeriode(cursor.getString(15));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<mItemSalesPack_StockLogData> getAllDataNotSync(SQLiteDatabase db) {
		List<mItemSalesPack_StockLogData> contactList = null;
		// Select All Query
		mItemSalesPack_StockLogData dt=new mItemSalesPack_StockLogData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSync +"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<mItemSalesPack_StockLogData>();
			do {
				mItemSalesPack_StockLogData contact = new mItemSalesPack_StockLogData();
				contact.set_intProductCode(cursor.getString(0));
				contact.set_intQtyAdj(cursor.getString(1));
				contact.set_intQtyIn(cursor.getString(3));
				contact.set_intQtyOut(cursor.getString(4));
				contact.set_intQtyReserved(cursor.getString(5));
				contact.set_intSaldoAwal(cursor.getString(6));
				contact.set_intSubmit(cursor.getString(7));
				contact.set_intSync(cursor.getString(8));
				contact.set_intWeek(cursor.getString(9));
				contact.set_txtBranchCode(cursor.getString(10));
				contact.set_txtDataId(cursor.getString(11));
				contact.set_txtNoTransaction(cursor.getString(12));
				contact.set_txtOutletCode(cursor.getString(13));
				contact.set_txtOutletName(cursor.getString(14));
				contact.set_txtPeriode(cursor.getString(15));
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
		mItemSalesPack_StockLogData dt = new mItemSalesPack_StockLogData();
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
