package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.tPOHeader_mobileData;

public class tPOHeader_mobileDA {
	private static final String TABLE_CONTACTS = new clsHardCode().txtTable_tPOHeader_Mobile;

	// Contacts Table Columns names

	public tPOHeader_mobileDA(SQLiteDatabase db) {
		tPOHeader_mobileData dt=new tPOHeader_mobileData();
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
				+ dt.Property_txtNoPO + " TEXT PRIMARY KEY,"
				+ dt.Property_txtNoMO + " TEXT NULL,"
				+ dt.Property_dtDate + " TEXT NULL,"
				+ dt.Property_txtDesc + " TEXT NULL,"
				+ dt.Property_txtOutletCode + " TEXT NULL,"
				+ dt.Property_txtOutletName + " TEXT NULL,"
				+ dt.Property_txtBranchCode + " TEXT NULL,"
				+ dt.Property_txtDeviceId + " TEXT NULL,"
				+ dt.Property_txtUserId + " TEXT NULL,"
				+ dt.Property_intSubmit + " TEXT NULL,"
				+ dt.Property_intStockAwal + " TEXT NULL,"
				+ dt.Property_typePO + " TEXT NULL,"
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
	public List<tPOHeader_mobileData> GetPONonOutStanding(SQLiteDatabase db, String outletCode) {
		List<tPOHeader_mobileData> contactList = null;
		// Select All Query
		tPOHeader_mobileData dt=new tPOHeader_mobileData();
		String selectQuery = "SELECT * FROM tPOHeader_Mobile LEFT JOIN tPOStatus_Mobile ON tPOHeader_Mobile.txtNoPO=tPOStatus_Mobile.txtNoPO where tPOStatus_Mobile.bitActive=1 AND NOT tPOStatus_Mobile.txtStatus='RECEIVED' AND tPOHeader_mobile.txtOutletCode='" + outletCode + "'";
		
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPOHeader_mobileData>();
			do {
				tPOHeader_mobileData contact = new tPOHeader_mobileData();
				contact.set_txtNoPO(cursor.getString(0));
				contact.set_txtNOMO(cursor.getString(1));
				contact.set_dtDate(cursor.getString(2));
				contact.set_txtOutletCode(cursor.getString(3));
				contact.set_txtOutletName(cursor.getString(4));
				contact.set_txtBranchCode(cursor.getString(5));
				contact.set_txtDeviceId(cursor.getString(6));
				//contact.set_txtDesc(cursor.getString(7));
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	public void SaveDatatPOHeader_mobileData(SQLiteDatabase db,tPOHeader_mobileData data) {
		tPOHeader_mobileData dt=new tPOHeader_mobileData();
		db.execSQL("INSERT OR REPLACE into "+TABLE_CONTACTS+" ("+dt.Property_All+") "+
			"values('"	+String.valueOf(data.get_dtDate())+"','"
				+String.valueOf(data.get_intSubmit())+"','"
				+String.valueOf(data.get_intStockAwal())+"','"
				+String.valueOf(data.get_intSync())+"','"
				+String.valueOf(data.get_txtBranchCode())+"','"
				+String.valueOf(data.get_txtDesc())+"','"
				+String.valueOf(data.get_txtDeviceId())+"','"
				+String.valueOf(data.get_txtNoPO())+"','"
				+String.valueOf(data.get_txtNOMO())+"','"
				+String.valueOf(data.get_txtOutletCode())+"','"
				+String.valueOf(data.get_txtOutletName())+"','"
				+String.valueOf(data.get_txtUserId())+"','"
				+String.valueOf(data.get_intTyePO())+"'"
				+")");
	}
	
	
	public void UpdateDataItemForSubmitByHeaderId(SQLiteDatabase db,String dataid) {
		tPOHeader_mobileData dt=new tPOHeader_mobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intSubmit+"=1"
				+" Where " + dt.Property_txtNoPO +"='"+ dataid+"'");
	}
	public void UpdateDataItemForSyncByHeaderId(SQLiteDatabase db,String dataid) {
		tPOHeader_mobileData dt=new tPOHeader_mobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intSync+"=1"
				+" Where " + dt.Property_txtNoPO +"='"+ dataid+"'");
	}
	// Getting single contact
	public tPOHeader_mobileData getData(SQLiteDatabase db,String id) {
		tPOHeader_mobileData dt=new tPOHeader_mobileData();
		String selectQuery = "SELECT "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtNoPO+"='"+id+"';";
		Cursor cursor = db.rawQuery(selectQuery, null);
		tPOHeader_mobileData contact = null;
		if(cursor.getCount()>0){
			contact = new tPOHeader_mobileData();
			if (cursor != null)
				cursor.moveToFirst();
			contact.set_dtDate(cursor.getString(0));
			contact.set_intSubmit(cursor.getString(1));
			contact.set_intStockAwal(cursor.getString(2));
			contact.set_intSync(cursor.getString(3));
			contact.set_txtBranchCode(cursor.getString(4));
			contact.set_txtDesc(cursor.getString(5));
			contact.set_txtDeviceId(cursor.getString(6));
			contact.set_txtNoPO(cursor.getString(7));
			contact.set_txtNOMO(cursor.getString(8));
			contact.set_txtOutletCode(cursor.getString(9));
			contact.set_txtOutletName(cursor.getString(10));
			contact.set_txtUserId(cursor.getString(11));
			contact.set_intTyePO(cursor.getString(12));
		}
		// return contact
		cursor.close();
		return contact;
	}
	
	public boolean CheckDataPushData(SQLiteDatabase db) {		
		tPOHeader_mobileData dt=new tPOHeader_mobileData();
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
	
	public List<tPOHeader_mobileData> GetPOOutStanding(SQLiteDatabase db) {
		List<tPOHeader_mobileData> contactList = null;
		// Select All Query
		tPOHeader_mobileData dt=new tPOHeader_mobileData();
		String selectQuery = "SELECT a.txtNoPO,a.txtNoMO,a.dtDate,a.txtOutletCode,a.txtOutletName,a.txtBranchCode,a.txtDeviceId,a.txtUserId,c.txtStatus from tPOHeader_Mobile a ";
		selectQuery+=" Left Join tPOStatus_Mobile b on a.txtNoPO=b.txtNoPO and b.bitActive=1 and b.intStatus in (4,6)";
		selectQuery+=" Left Join mStatusDocument c on b.intStatus=c.intStatus and c.bitActive=1";
		
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPOHeader_mobileData>();
			do {
				tPOHeader_mobileData contact = new tPOHeader_mobileData();
				contact.set_txtNoPO(cursor.getString(0));
				contact.set_txtNOMO(cursor.getString(1));
				contact.set_dtDate(cursor.getString(2));
				contact.set_txtOutletCode(cursor.getString(3));
				contact.set_txtOutletName(cursor.getString(4));
				contact.set_txtBranchCode(cursor.getString(5));
				contact.set_txtDeviceId(cursor.getString(6));
				contact.set_txtDesc(cursor.getString(7));
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	
	// Getting All Contacts
	public List<tPOHeader_mobileData> getAllDataToPushData(SQLiteDatabase db) {
		List<tPOHeader_mobileData> contactList = null;
		// Select All Query
		tPOHeader_mobileData dt=new tPOHeader_mobileData();
		String selectQuery = "SELECT "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1 AND "+dt.Property_intSync+"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPOHeader_mobileData>();
			do {
				tPOHeader_mobileData contact = new tPOHeader_mobileData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_intSubmit(cursor.getString(1));
				contact.set_intStockAwal(cursor.getString(2));
				contact.set_intSync(cursor.getString(3));
				contact.set_txtBranchCode(cursor.getString(4));
				contact.set_txtDesc(cursor.getString(5));
				contact.set_txtDeviceId(cursor.getString(6));
				contact.set_txtNoPO(cursor.getString(7));
				contact.set_txtNOMO(cursor.getString(8));
				contact.set_txtOutletCode(cursor.getString(9));
				contact.set_txtOutletName(cursor.getString(10));
				contact.set_txtUserId(cursor.getString(11));
				contact.set_intTyePO(cursor.getString(12));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tPOHeader_mobileData> getAllData(SQLiteDatabase db) {
		List<tPOHeader_mobileData> contactList = null;
		// Select All Query
		tPOHeader_mobileData dt=new tPOHeader_mobileData();
		String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS;

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPOHeader_mobileData>();
			do {
				tPOHeader_mobileData contact = new tPOHeader_mobileData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_intSubmit(cursor.getString(1));
				contact.set_intStockAwal(cursor.getString(2));
				contact.set_intSync(cursor.getString(3));
				contact.set_txtBranchCode(cursor.getString(4));
				contact.set_txtDesc(cursor.getString(5));
				contact.set_txtDeviceId(cursor.getString(6));
				contact.set_txtNoPO(cursor.getString(7));
				contact.set_txtNOMO(cursor.getString(8));
				contact.set_txtOutletCode(cursor.getString(9));
				contact.set_txtOutletName(cursor.getString(10));
				contact.set_txtUserId(cursor.getString(11));
				contact.set_intTyePO(cursor.getString(12));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tPOHeader_mobileData> getAllDataSubmit(SQLiteDatabase db) {
		List<tPOHeader_mobileData> contactList = null;
		// Select All Query
		tPOHeader_mobileData dt=new tPOHeader_mobileData();
		String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPOHeader_mobileData>();
			do {
				tPOHeader_mobileData contact = new tPOHeader_mobileData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_intSubmit(cursor.getString(1));
				contact.set_intStockAwal(cursor.getString(2));
				contact.set_intSync(cursor.getString(3));
				contact.set_txtBranchCode(cursor.getString(4));
				contact.set_txtDesc(cursor.getString(5));
				contact.set_txtDeviceId(cursor.getString(6));
				contact.set_txtNoPO(cursor.getString(7));
				contact.set_txtNOMO(cursor.getString(8));
				contact.set_txtOutletCode(cursor.getString(9));
				contact.set_txtOutletName(cursor.getString(10));
				contact.set_txtUserId(cursor.getString(11));
				contact.set_intTyePO(cursor.getString(12));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tPOHeader_mobileData> getAllDataNotSync(SQLiteDatabase db) {
		List<tPOHeader_mobileData> contactList = null;
		// Select All Query
		tPOHeader_mobileData dt=new tPOHeader_mobileData();
		String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSync +"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPOHeader_mobileData>();
			do {
				tPOHeader_mobileData contact = new tPOHeader_mobileData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_intSubmit(cursor.getString(1));
				contact.set_intStockAwal(cursor.getString(2));
				contact.set_intSync(cursor.getString(3));
				contact.set_txtBranchCode(cursor.getString(4));
				contact.set_txtDesc(cursor.getString(5));
				contact.set_txtDeviceId(cursor.getString(6));
				contact.set_txtNoPO(cursor.getString(7));
				contact.set_txtNOMO(cursor.getString(8));
				contact.set_txtOutletCode(cursor.getString(9));
				contact.set_txtOutletName(cursor.getString(10));
				contact.set_txtUserId(cursor.getString(11));
				contact.set_intTyePO(cursor.getString(12));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tPOHeader_mobileData> getAllDataByHeaderId(SQLiteDatabase db,String Id) {
		List<tPOHeader_mobileData> contactList = new ArrayList<tPOHeader_mobileData>();
		// Select All Query
		tPOHeader_mobileData dt=new tPOHeader_mobileData();
		String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtNoPO +"='"+Id+"'";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPOHeader_mobileData>();
			do {
				tPOHeader_mobileData contact = new tPOHeader_mobileData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_intSubmit(cursor.getString(1));
				contact.set_intStockAwal(cursor.getString(2));
				contact.set_intSync(cursor.getString(3));
				contact.set_txtBranchCode(cursor.getString(4));
				contact.set_txtDesc(cursor.getString(5));
				contact.set_txtDeviceId(cursor.getString(6));
				contact.set_txtNoPO(cursor.getString(7));
				contact.set_txtNOMO(cursor.getString(8));
				contact.set_txtOutletCode(cursor.getString(9));
				contact.set_txtOutletName(cursor.getString(10));
				contact.set_txtUserId(cursor.getString(11));
				contact.set_intTyePO(cursor.getString(12));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tPOHeader_mobileData> getAllDataByOutletCode(SQLiteDatabase db,String Id) {
		List<tPOHeader_mobileData> contactList = new ArrayList<tPOHeader_mobileData>();
		// Select All Query
		tPOHeader_mobileData dt=new tPOHeader_mobileData();
		String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtOutletCode +"='"+Id+"' AND "+dt.Property_intSubmit+"=1";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPOHeader_mobileData>();
			do {
				tPOHeader_mobileData contact = new tPOHeader_mobileData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_intSubmit(cursor.getString(1));
				contact.set_intStockAwal(cursor.getString(2));
				contact.set_intSync(cursor.getString(3));
				contact.set_txtBranchCode(cursor.getString(4));
				contact.set_txtDesc(cursor.getString(5));
				contact.set_txtDeviceId(cursor.getString(6));
				contact.set_txtNoPO(cursor.getString(7));
				contact.set_txtNOMO(cursor.getString(8));
				contact.set_txtOutletCode(cursor.getString(9));
				contact.set_txtOutletName(cursor.getString(10));
				contact.set_txtUserId(cursor.getString(11));
				contact.set_intTyePO(cursor.getString(12));
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
		tPOHeader_mobileData dt = new tPOHeader_mobileData();
		db.delete(TABLE_CONTACTS, dt.Property_txtNoPO + " = ?",
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
