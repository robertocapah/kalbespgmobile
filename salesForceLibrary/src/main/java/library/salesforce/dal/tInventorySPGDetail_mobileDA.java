package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import library.salesforce.common.tInventorySPGDetail_mobileData;

public class tInventorySPGDetail_mobileDA {
	// All Static variables
			// Contacts table name
			private static final String TABLE_CONTACTS = new clsHardCode().txtTable_tInventorySPGDetail_mobile;

			// Contacts Table Columns names

			public tInventorySPGDetail_mobileDA(SQLiteDatabase db) {
				tInventorySPGDetail_mobileData dt=new tInventorySPGDetail_mobileData();
				String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
						+ dt.Property_intId + " TEXT PRIMARY KEY," 
						+ dt.Property_dtDate + " TEXT NULL,"
						+ dt.Property_intQtyDisplay + " TEXT NULL,"
						+ dt.Property_intQtyGudang + " TEXT NULL,"
						+ dt.Property_txtCodeProduct + " TEXT NULL,"
						+ dt.Property_txtKeterangan + " TEXT NULL,"
						+ dt.Property_txtNameProduct + " TEXT NULL,"
						+ dt.Property_intTotal + " TEXT NULL,"
						+ dt.Property_txtNoInv + " TEXT NULL,"
						+ dt.Property_intActive + " TEXT NULL,"
						+ dt.Property_txtNIK + " TEXT NULL" + ")";
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
			public void SaveDatatInventorySPGDetail_mobileData(SQLiteDatabase db,tInventorySPGDetail_mobileData data) {
				tInventorySPGDetail_mobileData dt=new tInventorySPGDetail_mobileData();
				//ContentValues values = new ContentValues();
				//values.put(dt.Property_intIdMenu, data.get_intIdMenu()); 
				//values.put(dt.Property_txtRoleId, data.get_txtRoleId()); 
				// Inserting Row
				//db.insert(TABLE_CONTACTS, null, values);
				db.execSQL("INSERT OR REPLACE into "+TABLE_CONTACTS+" ("+dt.Property_intId+","
						+dt.Property_dtDate+","
						+dt.Property_intQtyDisplay+","
						+dt.Property_intQtyGudang+","
						+dt.Property_txtCodeProduct+","
						+dt.Property_txtKeterangan+","
						+dt.Property_txtNameProduct+","
						+dt.Property_intTotal+","
						+dt.Property_txtNoInv+","
						+dt.Property_intActive+","
						+dt.Property_txtNIK+") "+
					"values('"	+String.valueOf(data.get_intId())+"','"
						+String.valueOf(data.get_dtDate())+"','"
						+String.valueOf(data.get_intQtyDisplay())+"','"
						+String.valueOf(data.get_intQtyGudang())+"','"
						+String.valueOf(data.get_txtCodeProduct())+"','"
						+String.valueOf(data.get_txtKeterangan())+"','"
						+String.valueOf(data.get_txtNameProduct())+"','"
						+String.valueOf(data.get_intTotal())+"','"
						+String.valueOf(data.get_txtNoInv())+"','"
						+String.valueOf(data.get_intActive())+"','"
						+String.valueOf(data.get_txtNIK())+"')");
				
			}

			// Adding new contact
			public void UpdateInactiveData(SQLiteDatabase db,String id) {
				tInventorySPGDetail_mobileData dt=new tInventorySPGDetail_mobileData();
				db.execSQL("Update "+TABLE_CONTACTS+" SET "+dt.Property_intActive+" = 0 " +
					" WHERE "+ dt.Property_intId +" = '" +id+"'");
				
			}
						
			// Getting single contact
			public tInventorySPGDetail_mobileData getData(SQLiteDatabase db,int id) {
				tInventorySPGDetail_mobileData dt=new tInventorySPGDetail_mobileData();
				tInventorySPGDetail_mobileData contact = null;
				Cursor cursor = db.query(TABLE_CONTACTS, new String[] { dt.Property_intId,
						dt.Property_dtDate, dt.Property_intQtyDisplay, dt.Property_intQtyGudang,
						dt.Property_txtCodeProduct,dt.Property_txtKeterangan ,dt.Property_txtNameProduct,dt.Property_txtNIK,
						dt.Property_intTotal,dt.Property_txtNoInv,dt.Property_intActive}, dt.Property_intId + "=?",
						new String[] { String.valueOf(id) }, null, null, null, null);
				if (cursor != null)
					cursor.moveToFirst();
					contact=new tInventorySPGDetail_mobileData();
					contact.set_intId(Integer.valueOf(cursor.getString(0)));
					contact.set_dtDate(cursor.getString(1));
					contact.set_intQtyDisplay(cursor.getString(2));
					contact.set_intQtyGudang(cursor.getString(3));
					contact.set_txtCodeProduct(cursor.getString(4));
					contact.set_txtKeterangan(cursor.getString(5));
					contact.set_txtNameProduct(cursor.getString(6));
					contact.set_txtNIK(cursor.getString(7));
					contact.set_intTotal(cursor.getString(8));
					contact.set_txtNoInv(cursor.getString(9));
					contact.set_intActive(cursor.getString(10));
				// return contact
				cursor.close();
				return contact;
			}
			
			// Getting single contact
			public List<tInventorySPGDetail_mobileData> getDataByNoSO(SQLiteDatabase db,String NoSo) {
				List<tInventorySPGDetail_mobileData> contactList = null;
				tInventorySPGDetail_mobileData dt=new tInventorySPGDetail_mobileData();
				Cursor cursor = db.query(TABLE_CONTACTS, new String[] { dt.Property_intId,
						dt.Property_dtDate, dt.Property_intQtyDisplay, dt.Property_intQtyGudang,
						dt.Property_txtCodeProduct,dt.Property_txtKeterangan ,dt.Property_txtNameProduct,dt.Property_txtNIK,dt.Property_intTotal,dt.Property_txtNoInv,
						dt.Property_intActive}, dt.Property_txtNoInv + "=?",
						new String[] { String.valueOf(NoSo) }, null, null, null, null);
				if (cursor != null){
					if (cursor.moveToFirst()) {
						contactList=new ArrayList<tInventorySPGDetail_mobileData>();
						do {
							tInventorySPGDetail_mobileData contact = new tInventorySPGDetail_mobileData();
							contact.set_intId(Integer.valueOf(cursor.getString(0)));
							contact.set_dtDate(cursor.getString(1));
							contact.set_intQtyDisplay(cursor.getString(2));
							contact.set_intQtyGudang(cursor.getString(3));
							contact.set_txtCodeProduct(cursor.getString(4));
							contact.set_txtKeterangan(cursor.getString(5));
							contact.set_txtNameProduct(cursor.getString(6));
							contact.set_txtNIK(cursor.getString(7));
							contact.set_intTotal(cursor.getString(8));
							contact.set_txtNoInv(cursor.getString(9));
							contact.set_intActive(cursor.getString(10));
							// Adding contact to list
							contactList.add(contact);
						} while (cursor.moveToNext());
					}
				}
				// return contact
				cursor.close();
				return contactList;
			}
			// Getting All Contacts
			public List<tInventorySPGDetail_mobileData> getSalesProductDetailByHeaderId(SQLiteDatabase db,String txtNoInv) {
				List<tInventorySPGDetail_mobileData> contactList = new ArrayList<tInventorySPGDetail_mobileData>();
				// Select All Query
				tInventorySPGDetail_mobileData dt=new tInventorySPGDetail_mobileData();
				String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +" Where "+dt.Property_txtNoInv+"='"+txtNoInv+"' AND "+dt.Property_intActive+"=1";

				Cursor cursor = db.rawQuery(selectQuery, null);

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						tInventorySPGDetail_mobileData contact = new tInventorySPGDetail_mobileData();
						contact.set_intId(Integer.valueOf(cursor.getString(1)));
						contact.set_dtDate(cursor.getString(0));
						contact.set_intQtyDisplay(cursor.getString(2));
						contact.set_intQtyGudang(cursor.getString(3));
						contact.set_txtCodeProduct(cursor.getString(4));
						contact.set_txtKeterangan(cursor.getString(5));
						contact.set_txtNameProduct(cursor.getString(6));
						contact.set_intTotal(cursor.getString(7));
						contact.set_txtNIK(cursor.getString(8));
						contact.set_txtNoInv(cursor.getString(9));
						contact.set_intActive(cursor.getString(10));
						// Adding contact to list
						contactList.add(contact);
					} while (cursor.moveToNext());
				}
				cursor.close();
				// return contact list
				return contactList;
			}
			// Getting All Contacts
			public List<tInventorySPGDetail_mobileData> getAllData(SQLiteDatabase db) {
				List<tInventorySPGDetail_mobileData> contactList = new ArrayList<tInventorySPGDetail_mobileData>();
				// Select All Query
				tInventorySPGDetail_mobileData dt=new tInventorySPGDetail_mobileData();
				String selectQuery = "SELECT  "+dt.Property_All+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intActive+"=1";

				Cursor cursor = db.rawQuery(selectQuery, null);

				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						tInventorySPGDetail_mobileData contact = new tInventorySPGDetail_mobileData();
						contact.set_intId(Integer.valueOf(cursor.getString(1)));
						contact.set_dtDate(cursor.getString(0));
						contact.set_intQtyDisplay(cursor.getString(2));
						contact.set_intQtyGudang(cursor.getString(3));
						contact.set_txtCodeProduct(cursor.getString(4));
						contact.set_txtKeterangan(cursor.getString(5));
						contact.set_txtNameProduct(cursor.getString(6));
						contact.set_intTotal(cursor.getString(7));
						contact.set_txtNIK(cursor.getString(8));
						contact.set_txtNoInv(cursor.getString(9));
						contact.set_intActive(cursor.getString(10));
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
				tInventorySPGDetail_mobileData dt = new tInventorySPGDetail_mobileData();
				db.delete(TABLE_CONTACTS, dt.Property_intId + " = ?",
						new String[] { String.valueOf(id) });
			}
			public void deleteByNOSO(SQLiteDatabase db,String NOInv) {		
				tInventorySPGDetail_mobileData dt = new tInventorySPGDetail_mobileData();
				db.delete(TABLE_CONTACTS, dt.Property_txtNoInv + " = ?",
						new String[] { NOInv });
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
