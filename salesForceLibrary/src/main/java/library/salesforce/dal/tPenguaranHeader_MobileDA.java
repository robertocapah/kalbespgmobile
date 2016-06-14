package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.tPenguaranHeader_MobileData;

public class tPenguaranHeader_MobileDA {
	// All Static variables

	// Contacts table name
	private static final String TABLE_CONTACTS = new clsHardCode().txtTable_tPenguaranHeader_Mobile;

	// Contacts Table Columns names
	
	public tPenguaranHeader_MobileDA(SQLiteDatabase db) {
		tPenguaranHeader_MobileData dt = new tPenguaranHeader_MobileData();
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
										+ dt.Property_txtNoPenguaran + " TEXT PRIMARY KEY," 
										+ dt.Property_dtDate + " TEXT NULL,"
										+ dt.Property_txtOutletCode + " TEXT NULL,"
										+ dt.Property_txtOutletName + " TEXT NULL,"
										+ dt.Property_txtBranchCode + " TEXT NULL,"
										+ dt.Property_txtOutletCodeDestination + " TEXT NULL,"
										+ dt.Property_txtOutletNameDestination + " TEXT NULL,"
										+ dt.Property_intTypePenguaran + " TEXT NULL,"
										+ dt.Property_txtDesc + " TEXT NULL,"
										+ dt.Property_bitPO + " TEXT NULL,"
										+ dt.Property_intGenerate + " TEXT NULL,"
										+ dt.Property_txtDeviceId + " TEXT NULL,"
										+ dt.Property_txtUserId + " TEXT NULL,"
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
	public void SaveDatatSalesProductHeaderData(SQLiteDatabase db,tPenguaranHeader_MobileData data) {
		tPenguaranHeader_MobileData dt=new tPenguaranHeader_MobileData();
		// Inserting Row
		db.execSQL("INSERT OR REPLACE into "+TABLE_CONTACTS+" ("+dt.Property_txtNoPenguaran+","
				+dt.Property_dtDate+","
				+dt.Property_txtOutletCode+","
				+dt.Property_txtOutletName+","
				+dt.Property_txtBranchCode+","
				+dt.Property_txtOutletCodeDestination+","
				+dt.Property_txtOutletNameDestination+","
				+dt.Property_intTypePenguaran+","
				+dt.Property_txtDesc+","
				+dt.Property_bitPO+","
				+dt.Property_intGenerate+","
				+dt.Property_txtDeviceId+","
				+dt.Property_txtUserId+","
				+dt.Property_txtBranchCode+","
				+dt.Property_bitActive+","
				+dt.Property_intSubmit+","
				+dt.Property_intSync+") "+
			"values('"	+String.valueOf(data.get_txtNoPenguaran())+"','"
				+String.valueOf(data.get_dtDate())+"','"
				+String.valueOf(data.get_txtOutletCode())+"','"
				+String.valueOf(data.get_txtOutletName())+"','"
				+String.valueOf(data.get_txtBranchCode())+"','"
				+String.valueOf(data.get_txtOutletCodeDestination())+"','"
				+String.valueOf(data.get_txtOutletNameDestination())+"','"
				+String.valueOf(data.get_intTypePenguaran())+"','"
				+String.valueOf(data.get_txtDesc())+"','"
				+String.valueOf(data.get_bitPO())+"','"
				+String.valueOf(data.get_intGenerate())+"','"
				+String.valueOf(data.get_txtDeviceId())+"','"
				+String.valueOf(data.get_txtUserId())+"','"
				+String.valueOf(data.get_txtBranchCode())+"','"
				+String.valueOf(data.get_bitActive())+"','"
				+String.valueOf(data.get_intSubmit())+"','"
				+String.valueOf(data.get_intSync())+"')");
	}
	
	public void UpdateDataItemForSubmit(SQLiteDatabase db,String dataid) {
		tPenguaranHeader_MobileData dt=new tPenguaranHeader_MobileData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intSubmit+"=1"
				+" Where " + dt.Property_txtNoPenguaran +"='"+ dataid+"'");
	}
	
	// Getting single contact
		public tPenguaranHeader_MobileData getData(SQLiteDatabase db,String id) {
			tPenguaranHeader_MobileData dt=new tPenguaranHeader_MobileData();
			Cursor cursor = db.query(TABLE_CONTACTS, new String[] { 
					 dt.Property_txtNoPenguaran
					,dt.Property_dtDate
					,dt.Property_txtOutletCode 
					,dt.Property_txtOutletName
					,dt.Property_txtBranchCode 
					,dt.Property_txtOutletCodeDestination
					,dt.Property_txtOutletNameDestination
					,dt.Property_intTypePenguaran
					,dt.Property_txtDesc
					,dt.Property_bitPO
					,dt.Property_intGenerate
					,dt.Property_txtDeviceId
					,dt.Property_txtUserId 
					,dt.Property_txtBranchCode 
					,dt.Property_bitActive
					,dt.Property_intSubmit
					,dt.Property_intSync}, dt.Property_txtNoPenguaran + "=?",
					new String[] { String.valueOf(id) }, null, null, null, null);
			tPenguaranHeader_MobileData contact = null;
			if(cursor.getCount()>0){
				contact = new tPenguaranHeader_MobileData();
				if (cursor != null)
					cursor.moveToFirst();
					contact.set_txtNoPenguaran(cursor.getString(0));
					contact.set_dtDate(cursor.getString(1));
					contact.set_txtOutletCode(cursor.getString(2));
					contact.set_txtOutletName(cursor.getString(3));
					contact.set_txtBranchCode (cursor.getString(4));
					contact.set_txtOutletCodeDestination(cursor.getString(5));
					contact.set_txtOutletNameDestination(cursor.getString(6));
					contact.set_intTypePenguaran(cursor.getString(7));
					contact.set_txtDesc(cursor.getString(8));
					contact.set_bitPO(cursor.getString(9));
					contact.set_intGenerate(cursor.getString(10));
					contact.set_txtDeviceId(cursor.getString(11));
					contact.set_txtUserId (cursor.getString(12));
					contact.set_txtBranchCode(cursor.getString(13));
					contact.set_bitActive(cursor.getString(14));
					contact.set_intSubmit(cursor.getString(15));
					contact.set_intSync(cursor.getString(16));
			}
			// return contact
			cursor.close();
			return contact;
		}
		
		public List<tPenguaranHeader_MobileData> fillData(Cursor cursor){
			List<tPenguaranHeader_MobileData> contactList=new ArrayList<tPenguaranHeader_MobileData>();
			if (cursor.moveToFirst()) {
				contactList=new ArrayList<tPenguaranHeader_MobileData>();
				do {
					tPenguaranHeader_MobileData contact = new tPenguaranHeader_MobileData();
					contact.set_txtNoPenguaran(cursor.getString(0));
					contact.set_dtDate(cursor.getString(1));
					contact.set_txtOutletCode(cursor.getString(2));
					contact.set_txtOutletName(cursor.getString(3));
					contact.set_txtBranchCode (cursor.getString(4));
					contact.set_txtOutletCodeDestination(cursor.getString(5));
					contact.set_txtOutletNameDestination(cursor.getString(6));
					contact.set_intTypePenguaran(cursor.getString(7));
					contact.set_txtDesc(cursor.getString(8));
					contact.set_bitPO(cursor.getString(9));
					contact.set_intGenerate(cursor.getString(10));
					contact.set_txtDeviceId(cursor.getString(11));
					contact.set_txtUserId (cursor.getString(12));
					contact.set_txtBranchCode(cursor.getString(13));
					contact.set_bitActive(cursor.getString(14));
					contact.set_intSubmit(cursor.getString(15));
					contact.set_intSync(cursor.getString(16));
					// Adding contact to list
					contactList.add(contact);
				} while (cursor.moveToNext());
			}
			return contactList;
		}
		
		
		public boolean CheckDataPushData(SQLiteDatabase db) {		
			tPenguaranHeader_MobileData dt=new tPenguaranHeader_MobileData();
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
		public List<tPenguaranHeader_MobileData> getAllDataToPushData(SQLiteDatabase db) {
			List<tPenguaranHeader_MobileData> contactList = null;
			// Select All Query
			tPenguaranHeader_MobileData dt=new tPenguaranHeader_MobileData();
			String selectQuery = "SELECT "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1 AND "+dt.Property_intSync+"=0";

			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			contactList=fillData(cursor);
			cursor.close();
			// return contact list
			return contactList;
		}
		
		public List<tPenguaranHeader_MobileData> getAllData(SQLiteDatabase db) {
			List<tPenguaranHeader_MobileData> contactList = null;
			// Select All Query
			tPenguaranHeader_MobileData dt=new tPenguaranHeader_MobileData();
			String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS;

			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
		    contactList=fillData(cursor);
			cursor.close();
			// return contact list
			return contactList;
		}
		
		public List<tPenguaranHeader_MobileData> getAllDataByOulet(SQLiteDatabase db,String txtOutletCode) {
			List<tPenguaranHeader_MobileData> contactList = null;
			// Select All Query
			tPenguaranHeader_MobileData dt=new tPenguaranHeader_MobileData();
			String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtOutletCode+"='"+txtOutletCode+"' AND "+dt.Property_intSubmit+"=1";

			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			contactList=fillData(cursor);
			cursor.close();
			// return contact list
			return contactList;
		}
		
		public List<tPenguaranHeader_MobileData> getAllDataSubmit(SQLiteDatabase db,String txtOutletCode) {
			List<tPenguaranHeader_MobileData> contactList = null;
			// Select All Query
			tPenguaranHeader_MobileData dt=new tPenguaranHeader_MobileData();
			String selectQuery = " SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +
								 " WHERE "+dt.Property_txtOutletCode+"='" + txtOutletCode + "'" +
								 " AND "+dt.Property_intSubmit+"=1";

			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			contactList=fillData(cursor);
			cursor.close();
			// return contact list
			return contactList;
		}
		
		public List<tPenguaranHeader_MobileData> getAllDataNotSync(SQLiteDatabase db) {
			List<tPenguaranHeader_MobileData> contactList = null;
			// Select All Query
			tPenguaranHeader_MobileData dt=new tPenguaranHeader_MobileData();
			String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSync +"=0";

			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			contactList=fillData(cursor);
			cursor.close();
			// return contact list
			return contactList;
		}
		
		public List<tPenguaranHeader_MobileData> getAllDataByIdAbsen(SQLiteDatabase db,String Id) {
			List<tPenguaranHeader_MobileData> contactList = null;
			// Select All Query
			tPenguaranHeader_MobileData dt=new tPenguaranHeader_MobileData();
			String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtUserId +"='"+Id+"'";

			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			contactList=fillData(cursor);
			cursor.close();
			// return contact list
			return contactList;
		}
		
		// Deleting single contact
		public void deleteContact(SQLiteDatabase db,String id) {		
			tPenguaranHeader_MobileData dt = new tPenguaranHeader_MobileData();
			db.delete(TABLE_CONTACTS, dt.Property_txtNoPenguaran + " = ?",
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
