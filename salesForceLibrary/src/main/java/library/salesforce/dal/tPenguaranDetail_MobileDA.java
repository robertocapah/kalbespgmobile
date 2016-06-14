package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.tPenguaranDetail_MobileData;

public class tPenguaranDetail_MobileDA {
	// All Static variables
	// Contacts table name
	private static final String TABLE_CONTACTS = new clsHardCode().txtTable_tPenguaranDetail_Mobile;
  
	// Contacts Table Columns names
	public tPenguaranDetail_MobileDA(SQLiteDatabase db) {
		tPenguaranDetail_MobileData dt =new tPenguaranDetail_MobileData();
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
				+ dt.Property_txtDataId + " TEXT PRIMARY KEY," 
				+ dt.Property_txtNoPenguaran + " TEXT NULL,"
				+ dt.Property_intProductCode + " TEXT NULL,"
				+ dt.Property_txtProductName + " TEXT NULL,"
				+ dt.Property_intQty + " TEXT NULL,"
				+ dt.Property_txtBatchNo + " TEXT NULL,"
				+ dt.Property_dtED + " TEXT NULL,"
				+ dt.Property_txtNoPO + " TEXT NULL,"
				+ dt.Property_intReason + " TEXT NULL,"
				+ dt.Property_txtDesc + " TEXT NULL,"
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
	public void SaveDatatPenguaranDetail_MobileData(SQLiteDatabase db,tPenguaranDetail_MobileData data) {
		tPenguaranDetail_MobileData dt=new tPenguaranDetail_MobileData();
		// Inserting Row
		db.execSQL("INSERT OR REPLACE into "+TABLE_CONTACTS+" ("+dt.Property_txtDataId+","
				+dt.Property_txtNoPenguaran+","
				+dt.Property_intProductCode+","
				+dt.Property_txtProductName+","
				+dt.Property_intQty+","
				+dt.Property_txtBatchNo+","
				+dt.Property_dtED+","
				+dt.Property_txtNoPO +","
				+dt.Property_intReason+","
				+dt.Property_txtDesc+","
				+dt.Property_bitActive+","
				+dt.Property_intSubmit+","
				+dt.Property_intSync+") "+
			"values('"	+String.valueOf(data.get_txtDataId())+"','"
				+String.valueOf(data.get_txtNoPenguaran())+"','"
				+String.valueOf(data.get_intProductCode())+"','"
				+String.valueOf(data.get_txtProductName())+"','"
				+String.valueOf(data.get_intQty())+"','"
				+String.valueOf(data.get_txtBatchNo())+"','"
				+String.valueOf(data.get_dtED())+"','"
				+String.valueOf(data.get_txtNoPO())+"','"
				+String.valueOf(data.get_intReason())+"','"
				+String.valueOf(data.get_txtDesc())+"','"
				+String.valueOf(data.get_bitActive())+"','"
				+String.valueOf(data.get_intSubmit())+"','"
				+String.valueOf(data.get_intSync())+"')");
	}
	
	// Adding new contact
	public void UpdateInactiveData(SQLiteDatabase db,String id) {
		tPenguaranDetail_MobileData dt=new tPenguaranDetail_MobileData();
		db.execSQL("UPDATE "+TABLE_CONTACTS+" SET "+dt.Property_bitActive+" = 0 " +
				   "WHERE "+ dt.Property_txtDataId +" = '" +id+"'");
	}
	
	public void UpdateDataItemForSubmit(SQLiteDatabase db,String dataid) {
		tPenguaranDetail_MobileData dt=new tPenguaranDetail_MobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intSubmit+"=1"
				+" Where " + dt.Property_txtDataId +"='"+ dataid+"'");
	}
	
	public void UpdateDataDetailByIntProductCode(SQLiteDatabase db,String txtNoPenguaran,String intProductCode){
		tPenguaranDetail_MobileData dt=new tPenguaranDetail_MobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intQty+"=" + dt.Property_intQty + " + 1"
				+" WHERE " + dt.Property_txtNoPenguaran +"='"+ txtNoPenguaran+"'"
				+" AND " + dt.Property_intProductCode +"='"+ intProductCode+"'");
	}
	
	public void UpdateDataDetail(SQLiteDatabase db,String txtNoPenguaran,String intProductCode,String IntQty,String txtBatchNo,String dtEd){
		tPenguaranDetail_MobileData dt=new tPenguaranDetail_MobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intQty+"=" + IntQty
				+" ," + dt.Property_txtBatchNo+"='" + txtBatchNo+"' "
				+" ," + dt.Property_dtED+"='" + dtEd+"' "
				+" WHERE " + dt.Property_txtNoPenguaran +"='"+ txtNoPenguaran+"'"
				+" AND " + dt.Property_intProductCode +"='"+ intProductCode+"'");
	}
	
	public void UpdateDataDetailByTxtDataID(SQLiteDatabase db,String txtDataID,String intProductCode,String IntQty,String txtBatchNo,String dtEd){
		tPenguaranDetail_MobileData dt=new tPenguaranDetail_MobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intQty+"=" + IntQty
				+" ," + dt.Property_txtBatchNo+"='" + txtBatchNo+"' "
				+" ," + dt.Property_dtED+"='" + dtEd+"' "
				+" WHERE " + dt.Property_txtDataId +"='"+ txtDataID+"'");
	}
	
	// Getting single contact
	public tPenguaranDetail_MobileData getData(SQLiteDatabase db,String id) {
		tPenguaranDetail_MobileData dt=new tPenguaranDetail_MobileData();
		tPenguaranDetail_MobileData contact = null;
		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { 
									 dt.Property_txtDataId
									,dt.Property_txtNoPenguaran
									,dt.Property_intProductCode
									,dt.Property_txtProductName
									,dt.Property_intQty
									,dt.Property_txtBatchNo 
									,dt.Property_dtED
									,dt.Property_txtNoPO
									,dt.Property_intReason
									,dt.Property_txtDesc
									,dt.Property_bitActive
									,dt.Property_intSubmit
									,dt.Property_intSync}, dt.Property_txtDataId + "=?",
								 new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
			contact=new tPenguaranDetail_MobileData();
			contact.set_txtDataId(cursor.getString(0));
			contact.set_txtNoPenguaran(cursor.getString(1));
			contact.set_intProductCode(cursor.getString(2));
			contact.set_txtProductName(cursor.getString(3));
			contact.set_intQty(cursor.getString(4));
			contact.set_txtBatchNo (cursor.getString(5));
			contact.set_dtED(cursor.getString(6));
			contact.set_txtNoPO(cursor.getString(7));
			contact.set_intReason(cursor.getString(8));
			contact.set_txtDesc(cursor.getString(9));
			contact.set_bitActive(cursor.getString(10));
			contact.set_intSubmit(cursor.getString(11));
			contact.set_intSync(cursor.getString(12));
			// return contact
			cursor.close();
			return contact;
	}
	public void UpdateDataDetailByTxtDataID(SQLiteDatabase db,String txtDataID,String intProductCode,String IntQty,String txtBatchNo,String dtEd,String intReason){
		tPenguaranDetail_MobileData dt=new tPenguaranDetail_MobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intQty+"=" + IntQty
				+" ," + dt.Property_txtBatchNo+"='" + txtBatchNo+"' "
				+" ," + dt.Property_dtED+"='" + dtEd+"' "
				+" ," + dt.Property_intReason+"='" + intReason+"' "
				+" WHERE " + dt.Property_txtDataId +"='"+ txtDataID+"'");
	}
	public List<tPenguaranDetail_MobileData> fillData(Cursor cursor){
		List<tPenguaranDetail_MobileData> contactList=new ArrayList<tPenguaranDetail_MobileData>();
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPenguaranDetail_MobileData>();
			do {
				tPenguaranDetail_MobileData contact = new tPenguaranDetail_MobileData();
				contact.set_txtDataId(cursor.getString(0));
				contact.set_txtNoPenguaran(cursor.getString(1));
				contact.set_intProductCode(cursor.getString(2));
				contact.set_txtProductName(cursor.getString(3));
				contact.set_intQty(cursor.getString(4));
				contact.set_txtBatchNo (cursor.getString(5));
				contact.set_dtED(cursor.getString(6));
				contact.set_txtNoPO(cursor.getString(7));
				contact.set_intReason(cursor.getString(8));
				contact.set_txtDesc(cursor.getString(9));
				contact.set_bitActive(cursor.getString(10));
				contact.set_intSubmit(cursor.getString(11));
				contact.set_intSync(cursor.getString(12));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		return contactList;
	}
	
	// Getting single contact
	public List<tPenguaranDetail_MobileData> getDataByNoBatch(SQLiteDatabase db,String NoBatch) {
		List<tPenguaranDetail_MobileData> contactList = null;
		tPenguaranDetail_MobileData dt=new tPenguaranDetail_MobileData();
		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { 
				dt.Property_txtDataId
				,dt.Property_txtNoPenguaran
				,dt.Property_intProductCode
				,dt.Property_txtProductName
				,dt.Property_intQty
				,dt.Property_txtBatchNo 
				,dt.Property_dtED
				,dt.Property_txtNoPO
				,dt.Property_txtDesc
				,dt.Property_bitActive
				,dt.Property_intSubmit
				,dt.Property_intSync}, dt.Property_txtDataId + "=?",
		new String[] { String.valueOf(NoBatch) }, null, null, null, null);
		if (cursor != null){
			if (cursor.moveToFirst()) {
				contactList=new ArrayList<tPenguaranDetail_MobileData>();
				do {
					tPenguaranDetail_MobileData contact = new tPenguaranDetail_MobileData();
					contact.set_txtDataId(cursor.getString(0));
					contact.set_txtNoPenguaran(cursor.getString(1));
					contact.set_intProductCode(cursor.getString(2));
					contact.set_txtProductName(cursor.getString(3));
					contact.set_intQty(cursor.getString(4));
					contact.set_txtBatchNo (cursor.getString(5));
					contact.set_dtED(cursor.getString(6));
					contact.set_txtNoPO(cursor.getString(7));
					contact.set_intReason(cursor.getString(8));
					contact.set_txtDesc(cursor.getString(9));
					contact.set_bitActive(cursor.getString(10));
					contact.set_intSubmit(cursor.getString(11));
					contact.set_intSync(cursor.getString(12));
					// Adding contact to list
					contactList.add(contact);
				} while (cursor.moveToNext());
			}
		}
		// return contact
		cursor.close();
		return contactList;
	}
	
	public List<tPenguaranDetail_MobileData> getPenguaranDetailByTxtDataId(SQLiteDatabase db,String txtDataId) {
		List<tPenguaranDetail_MobileData> contactList = new ArrayList<tPenguaranDetail_MobileData>();
		
		tPenguaranDetail_MobileData dt=new tPenguaranDetail_MobileData();
		String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +" Where "+dt.Property_txtDataId+"='"+txtDataId+"'";

		Cursor cursor = db.rawQuery(selectQuery, null);
		
		// looping through all rows and adding to list
		contactList = fillData(cursor);
		cursor.close();
		// return contact list
		return contactList;
	}
	
	// Getting All Contacts
	public List<tPenguaranDetail_MobileData> getDataByTxtNoByIntProductByTxtBatchNoByDtED(SQLiteDatabase db,String txtNo,String intProductID,String txtBatchNo,String dtED,String txtDataId) {
		List<tPenguaranDetail_MobileData> contactList = new ArrayList<tPenguaranDetail_MobileData>();
		// Select All Query
		tPenguaranDetail_MobileData dt=new tPenguaranDetail_MobileData();
		String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +
							 " WHERE "+dt.Property_txtNoPenguaran +"='"+txtNo+"'" +
							 " AND "+dt.Property_intProductCode+"='"+intProductID+"'" +
							 " AND "+dt.Property_txtBatchNo+"='"+txtBatchNo+"'" +
							 " AND "+dt.Property_dtED+"='"+dtED+"'" +
							 " AND "+dt.Property_txtDataId+"<>'"+txtDataId+"'";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		contactList = fillData(cursor);
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tPenguaranDetail_MobileData> getPenguaranDetailByHeaderId(SQLiteDatabase db,String txtNoPenguaran) {
		List<tPenguaranDetail_MobileData> contactList = new ArrayList<tPenguaranDetail_MobileData>();
		// Select All Query
		tPenguaranDetail_MobileData dt=new tPenguaranDetail_MobileData();
		String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +" Where "+dt.Property_txtNoPenguaran+"='"+txtNoPenguaran+"' AND "+dt.Property_bitActive+"=1";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		contactList = fillData(cursor);
		cursor.close();
		// return contact list
		return contactList;
	}
	
	// Getting All Contacts
	public List<tPenguaranDetail_MobileData> getAllData(SQLiteDatabase db) {
		List<tPenguaranDetail_MobileData> contactList = new ArrayList<tPenguaranDetail_MobileData>();
		// Select All Query
		tPenguaranDetail_MobileData dt=new tPenguaranDetail_MobileData();
		String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_bitActive+"=1";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		contactList=fillData(cursor);
		cursor.close();
		// return contact list
		return contactList;
	}
	
	public boolean CheckDataPushData(SQLiteDatabase db) {		
		tPenguaranDetail_MobileData dt=new tPenguaranDetail_MobileData();
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
	
	public List<tPenguaranDetail_MobileData> getAllDataToPushData(SQLiteDatabase db) {
		List<tPenguaranDetail_MobileData> contactList = new ArrayList<tPenguaranDetail_MobileData>();;
		// Select All Query
		tPenguaranDetail_MobileData dt=new tPenguaranDetail_MobileData();
		String selectQuery = "SELECT "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1 AND "+dt.Property_intSync+"=0";

		Cursor cursor = db.rawQuery(selectQuery, null);
		contactList = fillData(cursor);
		cursor.close();
		// return contact list
		return contactList;
	}
	
	public List<tPenguaranDetail_MobileData> getCalculateProductGroupByintProductCodetxtBatchNodtED(SQLiteDatabase db,String txtNoPenguaran) {
		List<tPenguaranDetail_MobileData> contactList = null;
		// Select All Query
		tPenguaranDetail_MobileData dt=new tPenguaranDetail_MobileData();
		String selectQuery = "select b.[intProductCode],b.[txtBatchNo],b.[dtED],sum(b.[intQty]) intQty from [tPenguaranHeader_Mobile] a "+
		"inner join [tPenguaranDetail_Mobile] b on a.[txtNoPenguaran]=b.[txtNoPenguaran] "+
		"where a.[txtNoPenguaran]='"+txtNoPenguaran+"' "+
		"group by b.[intProductCode],b.[txtBatchNo],b.[dtED] ";

		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPenguaranDetail_MobileData>();
			do {
				tPenguaranDetail_MobileData contact = new tPenguaranDetail_MobileData();
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
	public List<tPenguaranDetail_MobileData> getCalculateProductGroupBytxtNoByIntProductBytxtBatchNoByDtED(SQLiteDatabase db,String txtNoPenguaran, String intProductCode, String txtBatchNo, String dtED) {
		List<tPenguaranDetail_MobileData> contactList = null;
		// Select All Query
		tPenguaranDetail_MobileData dt=new tPenguaranDetail_MobileData();
		String selectQuery = "select b.[intProductCode],b.[txtBatchNo],b.[dtED],sum(b.[intQty]) intQty from [tPenguaranHeader_Mobile] a "+
		"inner join [tPenguaranDetail_Mobile] b on a.[txtNoPenguaran]=b.[txtNoPenguaran] "+
		"where b.[txtNoPenguaran]='"+txtNoPenguaran+"' "+
		"and b.[intProductCode]='"+intProductCode+"' "+
		"and b.[txtBatchNo]='"+txtBatchNo+"' "+
		"and b.[dtED]='"+dtED+"' "+
		"group by b.[intProductCode],b.[txtBatchNo],b.[dtED] ";

		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPenguaranDetail_MobileData>();
			do {
				tPenguaranDetail_MobileData contact = new tPenguaranDetail_MobileData();
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
	public List<tPenguaranDetail_MobileData> getCalculateProduct(SQLiteDatabase db,String txtNoPenguaran) {
		List<tPenguaranDetail_MobileData> contactList = null;
		// Select All Query
		tPenguaranDetail_MobileData dt=new tPenguaranDetail_MobileData();
		String selectQuery = "select b.[intProductCode],sum(b.[intQty]) intQty from [tPenguaranHeader_Mobile] a "+
		"inner join [tPenguaranDetail_Mobile] b on a.[txtNoPenguaran]=b.[txtNoPenguaran] "+
		"where a.[txtNoPenguaran]='"+txtNoPenguaran+"' "+
		"group by b.[intProductCode] ";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tPenguaranDetail_MobileData>();
			do {
				tPenguaranDetail_MobileData contact = new tPenguaranDetail_MobileData();
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
	
	public void DeleteAllDAta(SQLiteDatabase db) {
		// Drop older table if existed
		db.execSQL("DELETE FROM " + TABLE_CONTACTS);
	}
	// Deleting single contact
	public void deleteContact(SQLiteDatabase db,String id) {		
		tPenguaranDetail_MobileData dt = new tPenguaranDetail_MobileData();
		db.delete(TABLE_CONTACTS, dt.Property_txtDataId + " = ?",
				new String[] { String.valueOf(id) });
	}
	public void deleteByNOSO(SQLiteDatabase db,String NoBatch) {		
		tPenguaranDetail_MobileData dt = new tPenguaranDetail_MobileData();
		db.delete(TABLE_CONTACTS, dt.Property_txtBatchNo + " = ?",
				new String[] { NoBatch });
	}

	// Getting contacts Count
	public Long getContactsCount(SQLiteDatabase db) {
		String countQuery = "SELECT 1 FROM " + TABLE_CONTACTS;
		Cursor cursor = db.rawQuery(countQuery, null);
		long intres=(long) cursor.getCount();
		cursor.close();

		// return count
		return intres;
	}
	
}
