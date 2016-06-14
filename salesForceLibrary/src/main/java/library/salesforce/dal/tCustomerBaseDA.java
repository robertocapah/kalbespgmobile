package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.tCustomerBaseData;

public class tCustomerBaseDA {
	// All Static variables

		// Contacts table name
		private static final String TABLE_CONTACTS = new clsHardCode().txtTable_tCustomerBase;

		// Contacts Table Columns names

		public tCustomerBaseDA(SQLiteDatabase db) {
			tCustomerBaseData dt=new tCustomerBaseData();
			String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
					+ dt.Property_intCustomerId + " INTEGER PRIMARY KEY," 
					+ dt.Property_intCustomerIdSync + " TEXT NULL,"
					+ dt.Property_bitActive + " TEXT NULL,"
					+ dt.Property_dtDate + " TEXT NULL,"
					+ dt.Property_intSubmit + " TEXT NULL,"
					+ dt.Property_txtAlamat + " TEXT NULL,"
					+ dt.Property_txtBranchId + " TEXT NULL,"
					+ dt.Property_txtNama + " TEXT NULL,"
					+ dt.Property_txtOutletId + " TEXT NULL,"
					+ dt.Property_txtDeviceId + " TEXT NULL,"
					+ dt.Property_txtUserId + " TEXT NULL,"
					+ dt.Property_txtSex + " TEXT NULL,"
					+ dt.Property_txtProductBrandCode + " TEXT NULL,"
					+ dt.Property_txtTelp + " TEXT NULL" + ")";
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
		public void SaveDatatCustomerBaseData(SQLiteDatabase db,tCustomerBaseData data) {
			tCustomerBaseData dt=new tCustomerBaseData();
			//ContentValues values = new ContentValues();
			//values.put(dt.Property_intIdMenu, data.get_intIdMenu()); 
			//values.put(dt.Property_txtRoleId, data.get_txtRoleId()); 
			// Inserting Row
			//db.insert(TABLE_CONTACTS, null, values);
			db.execSQL("INSERT OR REPLACE into "+TABLE_CONTACTS+" ("+dt.Property_ALL+") "+
				"values('"	+String.valueOf(data.get_bitActive())+"','"
					+String.valueOf(data.get_dtDate())+"','"
					+String.valueOf(data.get_intCustomerId())+"','"
					+String.valueOf(data.get_intCustomerIdSync())+"','"
					+String.valueOf(data.get_intSubmit())+"','"
					+String.valueOf(data.get_txtAlamat().replace("'", "`"))+"','"
					+String.valueOf(data.get_txtBranchId())+"','"
					+String.valueOf(data.get_txtNama().replace("'", "`"))+"','"
					+String.valueOf(data.get_txtOutletId())+"','"
					+String.valueOf(data.get_txtTelp())+"','"
					+String.valueOf(data.get_txtUserId())+"','"
					+String.valueOf(data.get_txtDeviceId())+"','"
					+String.valueOf(data.get_txtSex())+"','"
					+String.valueOf(data.get_txtProductBrandCode())+"')");
			
			
		}

		// Getting single contact
		public tCustomerBaseData getData(SQLiteDatabase db,String id) {
			tCustomerBaseData dt=new tCustomerBaseData();
			Cursor cursor = db.query(TABLE_CONTACTS, new String[] { dt.Property_bitActive,
					dt.Property_dtDate,dt.Property_intCustomerId, dt.Property_intCustomerIdSync, dt.Property_intSubmit,dt.Property_txtAlamat,
					dt.Property_txtBranchId,dt.Property_txtNama,dt.Property_txtOutletId,dt.Property_txtTelp,dt.Property_txtUserId,dt.Property_txtDeviceId,
					dt.Property_txtSex,dt.Property_txtProductBrandCode}, dt.Property_intCustomerId + "=?",
					new String[] { String.valueOf(id) }, null, null, null, null);
			if (cursor != null)
				cursor.moveToFirst();
			tCustomerBaseData contact = new tCustomerBaseData();
			contact.set_bitActive(cursor.getString(0));
			contact.set_dtDate(cursor.getString(1));
			contact.set_intCustomerId(cursor.getString(2));
			contact.set_intCustomerIdSync(cursor.getString(3));
			contact.set_intSubmit(cursor.getString(4));
			contact.set_txtAlamat(cursor.getString(5));
			contact.set_txtBranchId(cursor.getString(6));
			contact.set_txtNama(cursor.getString(7));
			contact.set_txtOutletId(cursor.getString(8));
			contact.set_txtTelp(cursor.getString(9));
			contact.set_txtUserId(cursor.getString(10));
			contact.set_txtDeviceId(cursor.getString(11));
			contact.set_txtSex(cursor.getString(12));
			contact.set_txtProductBrandCode(cursor.getString(13));
			// return contact
			cursor.close();
			return contact;
		}
		
		// Getting All Contacts
		public List<tCustomerBaseData> getAllData(SQLiteDatabase db) {
			List<tCustomerBaseData> contactList = new ArrayList<tCustomerBaseData>();
			// Select All Query
			tCustomerBaseData dt=new tCustomerBaseData();
			String selectQuery = "SELECT  "+dt.Property_ALL+" FROM " + TABLE_CONTACTS;

			Cursor cursor = db.rawQuery(selectQuery, null);

			if (cursor.moveToFirst()) {
				do {
					tCustomerBaseData contact = new tCustomerBaseData();
					contact.set_bitActive(cursor.getString(0));
					contact.set_dtDate(cursor.getString(1));
					contact.set_intCustomerId(cursor.getString(2));
					contact.set_intCustomerIdSync(cursor.getString(3));
					contact.set_intSubmit(cursor.getString(4));
					contact.set_txtAlamat(cursor.getString(5));
					contact.set_txtBranchId(cursor.getString(6));
					contact.set_txtNama(cursor.getString(7));
					contact.set_txtOutletId(cursor.getString(8));
					contact.set_txtTelp(cursor.getString(9));
					contact.set_txtUserId(cursor.getString(10));
					contact.set_txtDeviceId(cursor.getString(11));
					contact.set_txtSex(cursor.getString(12));
					contact.set_txtProductBrandCode(cursor.getString(13));
					contactList.add(contact);
				} while (cursor.moveToNext());
			}
			cursor.close();
			// return contact list
			return contactList;
		}
		public List<tCustomerBaseData> getPushData(SQLiteDatabase db) {
			List<tCustomerBaseData> contactList = null;
			// Select All Query
			tCustomerBaseData dt=new tCustomerBaseData();
			String selectQuery = "SELECT  "+dt.Property_ALL+" FROM " + TABLE_CONTACTS+" WHERE "+dt.Property_intSubmit+"='0' AND "+dt.Property_intCustomerIdSync+"=''";

			Cursor cursor = db.rawQuery(selectQuery, null);

			if (cursor.moveToFirst()) {
				contactList=new ArrayList<tCustomerBaseData>();
				do {
					tCustomerBaseData contact = new tCustomerBaseData();
					contact.set_bitActive(cursor.getString(0));
					contact.set_dtDate(cursor.getString(1));
					contact.set_intCustomerId(cursor.getString(2));
					contact.set_intCustomerIdSync(cursor.getString(3));
					contact.set_intSubmit(cursor.getString(4));
					contact.set_txtAlamat(cursor.getString(5));
					contact.set_txtBranchId(cursor.getString(6));
					contact.set_txtNama(cursor.getString(7));
					contact.set_txtOutletId(cursor.getString(8));
					contact.set_txtTelp(cursor.getString(9));
					contact.set_txtUserId(cursor.getString(10));
					contact.set_txtDeviceId(cursor.getString(11));
					contact.set_txtSex(cursor.getString(12));
					contact.set_txtProductBrandCode(cursor.getString(13));
					contactList.add(contact);
				} while (cursor.moveToNext());
			}
			cursor.close();
			// return contact list
			return contactList;
		}
		public List<tCustomerBaseData> getNewData(SQLiteDatabase db) {
			List<tCustomerBaseData> contactList = new ArrayList<tCustomerBaseData>();
			// Select All Query
			tCustomerBaseData dt=new tCustomerBaseData();
			String selectQuery = "SELECT  "+dt.Property_ALL+" FROM " + TABLE_CONTACTS+" WHERE "+dt.Property_intSubmit+"='0'";

			Cursor cursor = db.rawQuery(selectQuery, null);

			if (cursor.moveToFirst()) {
				do {
					tCustomerBaseData contact = new tCustomerBaseData();
					contact.set_bitActive(cursor.getString(0));
					contact.set_dtDate(cursor.getString(1));
					contact.set_intCustomerId(cursor.getString(2));
					contact.set_intCustomerIdSync(cursor.getString(3));
					contact.set_intSubmit(cursor.getString(4));
					contact.set_txtAlamat(cursor.getString(5));
					contact.set_txtBranchId(cursor.getString(6));
					contact.set_txtNama(cursor.getString(7));
					contact.set_txtOutletId(cursor.getString(8));
					contact.set_txtTelp(cursor.getString(9));
					contact.set_txtUserId(cursor.getString(10));
					contact.set_txtDeviceId(cursor.getString(11));
					contact.set_txtSex(cursor.getString(12));
					contact.set_txtProductBrandCode(cursor.getString(13));
					contactList.add(contact);
				} while (cursor.moveToNext());
			}
			cursor.close();
			// return contact list
			return contactList;
		}
		public List<tCustomerBaseData> getAllDataByOutlet(SQLiteDatabase db,String txtOutlet) {
			List<tCustomerBaseData> contactList = new ArrayList<tCustomerBaseData>();
			// Select All Query
			tCustomerBaseData dt=new tCustomerBaseData();
			String selectQuery = "SELECT  "+dt.Property_ALL+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intSubmit+"=1 AND "+dt.Property_txtOutletId+"='"+txtOutlet+"'";
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor.moveToFirst()) {
				do {
					tCustomerBaseData contact = new tCustomerBaseData();
					contact.set_bitActive(cursor.getString(0));
					contact.set_dtDate(cursor.getString(1));
					contact.set_intCustomerId(cursor.getString(2));
					contact.set_intCustomerIdSync(cursor.getString(3));
					contact.set_intSubmit(cursor.getString(4));
					contact.set_txtAlamat(cursor.getString(5));
					contact.set_txtBranchId(cursor.getString(6));
					contact.set_txtNama(cursor.getString(7));
					contact.set_txtOutletId(cursor.getString(8));
					contact.set_txtTelp(cursor.getString(9));
					contact.set_txtUserId(cursor.getString(10));
					contact.set_txtDeviceId(cursor.getString(11));
					contact.set_txtSex(cursor.getString(12));
					contact.set_txtProductBrandCode(cursor.getString(13));
					contactList.add(contact);
				} while (cursor.moveToNext());
			}
			cursor.close();
			// return contact list
			return contactList;
		}
		// Deleting single contact
		public void deleteContact(SQLiteDatabase db,String id) {		
			tCustomerBaseData dt = new tCustomerBaseData();
			db.delete(TABLE_CONTACTS, dt.Property_intCustomerId + " = ?",
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
