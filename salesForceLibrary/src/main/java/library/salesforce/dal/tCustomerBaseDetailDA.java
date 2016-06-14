package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import library.salesforce.common.tCustomerBaseDetailData;

public class tCustomerBaseDetailDA {
	// All Static variables

			// Contacts table name
			private static final String TABLE_CONTACTS = new clsHardCode().txtTable_tCustomerBaseDetail;

			// Contacts Table Columns names

			public tCustomerBaseDetailDA(SQLiteDatabase db) {
				tCustomerBaseDetailData dt=new tCustomerBaseDetailData();
				String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
						+ dt.property_txtDataId + " TEXT PRIMARY KEY," 
						+ dt.Property_intCustomerId + " TEXT  NULL,"
						+ dt.Property_intQty + " TEXT NULL,"
						+ dt.Property_txtProductBrandCode + " TEXT NULL,"
						+ dt.Property_txtProductBrandName + " TEXT NULL" + ")";
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
			public void SaveDatatCustomerBaseDetailData(SQLiteDatabase db,tCustomerBaseDetailData data) {
				tCustomerBaseDetailData dt=new tCustomerBaseDetailData();
				//ContentValues values = new ContentValues();
				//values.put(dt.Property_intIdMenu, data.get_intIdMenu()); 
				//values.put(dt.Property_txtRoleId, data.get_txtRoleId()); 
				// Inserting Row
				//db.insert(TABLE_CONTACTS, null, values);
				db.execSQL("INSERT OR REPLACE into "+TABLE_CONTACTS+" ("+dt.PropertyAll+") "+
					"values('"	+String.valueOf(data.get_txtDataId())+"','"
						+String.valueOf(data.get_intCustomerId())+"','"
						+String.valueOf(data.get_intQty())+"','"
						+String.valueOf(data.get_txtProductBrandCode())+"','"
						+String.valueOf(data.get_txtProductBrandName())+"')");
				
				
			}

			// Getting single contact
			public tCustomerBaseDetailData getData(SQLiteDatabase db,String id) {
				tCustomerBaseDetailData dt=new tCustomerBaseDetailData();
				Cursor cursor = db.query(TABLE_CONTACTS, new String[] { dt.property_txtDataId,
						dt.Property_intCustomerId,dt.Property_intQty, dt.Property_txtProductBrandCode, dt.Property_txtProductBrandName}, dt.property_txtDataId + "=?",
						new String[] { String.valueOf(id) }, null, null, null, null);
				if (cursor != null)
					cursor.moveToFirst();
				tCustomerBaseDetailData contact = new tCustomerBaseDetailData();
				contact.set_txtDataId(cursor.getString(0));
				contact.set_intCustomerId(cursor.getString(1));
				contact.set_intQty(cursor.getString(2));
				contact.set_txtProductBrandCode(cursor.getString(3));
				contact.set_txtProductBrandName(cursor.getString(4));
				// return contact
				cursor.close();
				return contact;
			}
			
			// Getting All Contacts
			public List<tCustomerBaseDetailData> getAllData(SQLiteDatabase db) {
				List<tCustomerBaseDetailData> contactList = new ArrayList<tCustomerBaseDetailData>();
				// Select All Query
				tCustomerBaseDetailData dt=new tCustomerBaseDetailData();
				String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS;

				Cursor cursor = db.rawQuery(selectQuery, null);

				if (cursor.moveToFirst()) {
					do {
						tCustomerBaseDetailData contact = new tCustomerBaseDetailData();
						contact.set_txtDataId(cursor.getString(0));
						contact.set_intCustomerId(cursor.getString(1));
						contact.set_intQty(cursor.getString(2));
						contact.set_txtProductBrandCode(cursor.getString(3));
						contact.set_txtProductBrandName(cursor.getString(4));
						contactList.add(contact);
					} while (cursor.moveToNext());
				}
				cursor.close();
				// return contact list
				return contactList;
			}
			public List<tCustomerBaseDetailData> getDataByCustomerId(SQLiteDatabase db,String Id) {
				List<tCustomerBaseDetailData> contactList = new ArrayList<tCustomerBaseDetailData>();
				// Select All Query
				tCustomerBaseDetailData dt=new tCustomerBaseDetailData();
				String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS+" WHERE "+dt.Property_intCustomerId+"="+Id;

				Cursor cursor = db.rawQuery(selectQuery, null);

				if (cursor.moveToFirst()) {
					do {
						tCustomerBaseDetailData contact = new tCustomerBaseDetailData();
						contact.set_txtDataId(cursor.getString(0));
						contact.set_intCustomerId(cursor.getString(1));
						contact.set_intQty(cursor.getString(2));
						contact.set_txtProductBrandCode(cursor.getString(3));
						contact.set_txtProductBrandName(cursor.getString(4));
						contactList.add(contact);
					} while (cursor.moveToNext());
				}
				cursor.close();
				// return contact list
				return contactList;
			}
			
			
			// Deleting single contact
			public void deleteContactByCustomerId(SQLiteDatabase db,String id) {		
				tCustomerBaseDetailData dt = new tCustomerBaseDetailData();
				db.delete(TABLE_CONTACTS, dt.Property_intCustomerId + " = ?",
						new String[] { String.valueOf(id) });
			}

			public void deleteContact(SQLiteDatabase db,String id) {		
				tCustomerBaseDetailData dt = new tCustomerBaseDetailData();
				db.delete(TABLE_CONTACTS, dt.property_txtDataId + " = ?",
						new String[] { String.valueOf(id) });
			}
			public void DeleteAllDAta(SQLiteDatabase db) {
				// Drop older table if existed
				db.execSQL("DELETE FROM " + TABLE_CONTACTS);
			}
			// Getting contacts Count
			public int getContactsCount(SQLiteDatabase db) {
				String countQuery = "SELECT * FROM " + TABLE_CONTACTS;
				Cursor cursor = db.rawQuery(countQuery, null);
				int num =cursor.getCount();
				cursor.close();

				// return count
				return num;
			}
}
