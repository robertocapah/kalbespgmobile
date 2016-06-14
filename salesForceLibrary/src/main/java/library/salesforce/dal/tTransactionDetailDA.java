package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import library.salesforce.common.tTransactionDetailData;

public class tTransactionDetailDA {
	private static final String TABLE_CONTACTS = new clsHardCode().txtTable_tTransactionDetail;

	// Contacts Table Columns names

	public tTransactionDetailDA(SQLiteDatabase db) {
		tTransactionDetailData dt=new tTransactionDetailData();
		String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
				+ dt.Property_txtTransId + " TEXT PRIMARY KEY," 
				+ dt.Property_dtDate + " TEXT NULL,"
				+ dt.Property_intWeek + " TEXT NULL,"
				+ dt.Property_txtType + " TEXT NULL,"
				+ dt.Property_intProductCode + " TEXT NULL,"
				+ dt.Property_txtBatchNo + " TEXT NULL,"
				+ dt.Property_dtED + " TEXT NULL,"
				+ dt.Property_txtNoDoc + " TEXT NULL,"
				+ dt.Property_txtNoMO + " TEXT NULL,"
				+ dt.Property_txtOutletCode + " TEXT NULL,"
				+ dt.Property_txtOutletName + " TEXT NULL,"
				+ dt.Property_txtBranchCode + " TEXT NULL,"
				+ dt.Property_intQty + " TEXT NULL,"
				+ dt.Property_intSubmit + " TEXT NULL,"
				+ dt.Property_intPush + " TEXT NULL" + ")";
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
	public org.json.simple.JSONArray getCalculateQtyForReportExpiredDate(SQLiteDatabase db, String txtOutletCode,String txtProductName) throws JSONException {
		tTransactionDetailData dt=new tTransactionDetailData();
		String selectQuery = " SELECT a.txtOutletCode " +
							 "		,b.txtOutletName " +
							 "	    ,a.intProductCode " +
							 "		,c.txtProductCode " +
							 "		,c.txtProductName " +
							 "	    ,a.txtBatchNo " +
							 "	    ,a.dtED " +
							 "		,SUM(CASE WHEN a.txtType='"+new clsHardCode().txtStatus_TypeDataGRN+"' THEN a.intQty else 0 END) " +
							 "		+SUM(CASE WHEN a.txtType='"+new clsHardCode().txtStatus_TypeDataBB+"' THEN a.intQty else 0 END) " +
							 "		-SUM(CASE WHEN a.txtType='"+new clsHardCode().txtStatus_TypeDataRESO+"' THEN a.intQty else 0 END) " +
							 "		+SUM(CASE WHEN a.txtType='"+new clsHardCode().txtStatus_TypeDataStockOpname+"' THEN a.intQty else 0 END) " +
							 "		-SUM(CASE WHEN a.txtType='"+new clsHardCode().txtStatus_TypeDataPengeluaran+"' THEN a.intQty else 0 END) " +
							 "		-SUM(CASE WHEN a.txtType='"+new clsHardCode().txtStatus_TypeDataReserved+"' THEN a.intQty else 0 END) intQty " +
							 " FROM tTransactionDetail a " +
							 " INNER JOIN mEmployeeArea b ON a.txtOutletCode=b.txtOutletCode " +
							 " INNER JOIN mProductBarcode c ON a.intProductCode=c.intProductCode " +
							 " WHERE a.txtOutletCode='" + txtOutletCode + "' " +
							 " AND c.txtProductName LIKE '%" + txtProductName + "%' " +
							 " GROUP BY a.txtOutletCode " +
							 "		   ,a.intProductCode " +
							 "		   ,a.txtBatchNo " +
							 "		   ,a.dtED " +
							 "ORDER BY a.dtED";
		
		Cursor cursor = db.rawQuery(selectQuery, null);
		org.json.simple.JSONObject result = new org.json.simple.JSONObject();
		org.json.simple.JSONArray JsonArray=new org.json.simple.JSONArray();
		
		if(cursor.getCount()>0){
			if (cursor != null)
				cursor.moveToFirst();
			do {
				result = new org.json.simple.JSONObject();
				
				result.put("txtOutletName", cursor.getString(1));
				result.put("txtProductCode", cursor.getString(3));
				result.put("txtProductName", cursor.getString(4));
				result.put("txtBatchNo", cursor.getString(5));
				result.put("dtED", cursor.getString(6));
				result.put("intQty", cursor.getString(7));
				
				JsonArray.add(result);
			} while (cursor.moveToNext());
		}
		// return contact
		cursor.close();
		
		return JsonArray;
	}
	
	public org.json.simple.JSONArray getCalculateQtyForReportPenjualanSO(SQLiteDatabase db, String txtOutletCode,String txtProductName) throws JSONException {
		tTransactionDetailData dt=new tTransactionDetailData();
		String selectQuery = "SELECT  a.txtOutletCode " +
							 "		 ,b.txtOutletName " +
							 "		 ,a.intProductCode " +
							 "		 ,c.txtProductCode " +
							 "		 ,c.txtProductName " +
							 "		 ,a.txtType " +
							 "		 ,a.txtBatchNo " +
							 "		 ,a.dtED " +
							 "		 ,SUM(a.intQty) as intQty " +
							 "		 ,d.decPriceHJD as txtHargaSatuan " +
							 "		 ,intQty * d.decPriceHJD as txtTotal " +
							 "FROM tTransactionDetail a " +
							 "INNER JOIN mEmployeeArea b ON b.txtOutletCode=a.txtOutletCode " +
							 "INNER JOIN mProductBarcode c ON c.intProductCode=a.intProductCode " +
							 "INNER JOIN mPriceInOutlet d ON a.intProductCode=d.intProductCode AND a.txtOutletCode=d.txtOutletCode " +
							 "WHERE a.txtType='"+new clsHardCode().txtStatus_TypeDataRESO+"' " +
							 "AND a.txtOutletCode='" + txtOutletCode + "' " +
							 "AND c.txtProductName LIKE '%" + txtProductName + "%' " +
							 "GROUP BY a.txtOutletCode,a.intProductCode,a.txtBatchNo,a.dtED " +
							 "ORDER BY c.txtProductName ";
		
		Cursor cursor = db.rawQuery(selectQuery, null);
		org.json.simple.JSONObject result = new org.json.simple.JSONObject();
		org.json.simple.JSONArray JsonArray=new org.json.simple.JSONArray();
		
		if(cursor.getCount()>0){
			if (cursor != null)
				cursor.moveToFirst();
			do {
				result = new org.json.simple.JSONObject();
				
				result.put("txtOutletName", cursor.getString(1));
				result.put("txtProductCode", cursor.getString(3));
				result.put("txtProductName", cursor.getString(4));
				result.put("txtType", cursor.getString(5));
				result.put("txtBatchNo", cursor.getString(6));
				result.put("dtED", cursor.getString(7));
				result.put("intQty", cursor.getString(8));
				result.put("txtHargaSatuan", cursor.getString(9));
				result.put("txtTotal", cursor.getString(10));
				
				JsonArray.add(result);
			} while (cursor.moveToNext());
		}
		// return contact
		cursor.close();
		
		return JsonArray;
	}
	
	public org.json.simple.JSONArray getCalculateQtyForReportAvailableVsStock(SQLiteDatabase db, String txtOutletCode,String txtProductName) throws JSONException {
		tTransactionDetailData dt=new tTransactionDetailData();
		String selectQuery = "SELECT  a.txtOutletCode " +
							 "	     ,c.txtOutletName " +
							 "		 ,a.txtProductCode AS intProductCode " +
							 "		 ,d.txtProductCode " +
							 "		 ,d.txtProductName " +
							 "		 ,a.intQty AS intQty_Stock " +
							 "		 ,SUM(CASE WHEN b.txtType='"+new clsHardCode().txtStatus_TypeDataGRN+"' THEN coalesce(b.intQty,0) else 0 END) " +
							 "		 +SUM(CASE WHEN b.txtType='"+new clsHardCode().txtStatus_TypeDataBB+"' THEN coalesce(b.intQty,0) else 0 END) " +
							 "		 -SUM(CASE WHEN b.txtType='"+new clsHardCode().txtStatus_TypeDataRESO+"' THEN coalesce(b.intQty,0) else 0 END) " +
							 "		 +SUM(CASE WHEN b.txtType='"+new clsHardCode().txtStatus_TypeDataStockOpname+"' THEN coalesce(b.intQty,0) else 0 END) " +
							 "		 -SUM(CASE WHEN b.txtType='"+new clsHardCode().txtStatus_TypeDataPengeluaran+"' THEN coalesce(b.intQty,0) else 0 END) " +
							 "		 -SUM(CASE WHEN b.txtType='"+new clsHardCode().txtStatus_TypeDataReserved+"' THEN coalesce(b.intQty,0) else 0 END) intQty_Available " +
							 " FROM mStockAwal_Mobile a " +
							 " LEFT JOIN tTransactionDetail b ON a.txtProductCode=b.intProductCode AND b.txtoutletcode=a.txtoutletcode " +
							 " INNER JOIN mEmployeeArea c ON c.txtOutletCode=a.txtOutletCode " +
							 " INNER JOIN mProductBarcode d ON d.intProductCode=a.txtProductCode " +
							 " WHERE a.txtOutletCode LIKE '%"+txtOutletCode+"%' " +
							 " AND d.txtProductName LIKE '%"+txtProductName+"%' " +
							 " GROUP BY a.txtProductCode " + 
							 " 		   ,a.txtOutletCode ";
		
		Cursor cursor = db.rawQuery(selectQuery, null);
		org.json.simple.JSONObject result = new org.json.simple.JSONObject();
		org.json.simple.JSONArray JsonArray=new org.json.simple.JSONArray();
		
		if(cursor.getCount()>0){
			if (cursor != null)
				cursor.moveToFirst();
			do {
				result = new org.json.simple.JSONObject();
				
				result.put("txtOutletName", cursor.getString(1));
				result.put("txtProductCode", cursor.getString(3));
				result.put("txtProductName", cursor.getString(4));
				result.put("intQty_Stock", cursor.getString(5));
				result.put("intQty_Available", cursor.getString(6));
				
				JsonArray.add(result);
			} while (cursor.moveToNext());
		}
		// return contact
		cursor.close();
		
		return JsonArray;
	}
	public org.json.simple.JSONArray getCalculateQtyForReportMutasi(SQLiteDatabase db, String txtOutletCode,String txtProductName) throws JSONException {
		tTransactionDetailData dt=new tTransactionDetailData();
		String selectQuery = "DROP TABLE IF EXISTS tempMaster; ";
		String selectQuery2 = "DROP TABLE IF EXISTS tempData; ";
		String selectQuery3 = "CREATE TEMP TABLE tempMaster AS " +
							 "SELECT  dtDate " +
							 "       ,intProductCode " +
							 "		 ,txtProductCode " +
							 "		 ,txtProductName " +
							 "		 ,txtNoDoc " +
							 "		 ,txtBatchNo " +
							 "		 ,dtED " +
							 "		 ,txtType " +
							 "	     ,CASE WHEN txtStatus='D' THEN CASE WHEN intQty >= 0 THEN intQty ELSE intQty*-1 END ELSE 0 END as 'IN' " +
							 "	     ,CASE WHEN txtStatus='K' THEN CASE WHEN intQty >= 0 THEN intQty ELSE intQty*-1 END ELSE 0 END as 'OUT' " +
							 "	     ,txtOutletCode " +
							 "		 ,txtOutletName " +
							 "FROM ( " +
							 "		 SELECT  a.dtDate " +
							 "				,a.intProductCode " +
							 "				,c.txtProductCode " +
							 "			    ,c.txtProductName " +
							 "			    ,a.txtNoDoc " +
							 "				,a.txtBatchNo " +
							 "			    ,a.dtED " +
							 "				,a.txtType " +
							 "			    ,CASE WHEN a.txtType='"+new clsHardCode().txtStatus_TypeDataStockOpname+"' THEN " +
							 "				      CASE WHEN a.intQty<0 THEN 'K' ELSE 'D' END " +
							 "				 WHEN a.txtType='"+new clsHardCode().txtStatus_TypeDataGRN+"' THEN 'D' " +
							 "				 WHEN a.txtType='"+new clsHardCode().txtStatus_TypeDataBB+"' THEN 'D' " +
							 "			     ELSE 'K' END AS txtStatus " +
							 "			    ,a.intQty " +
							 "				,a.txtOutletCode " +
							 "				,b.txtOutletName " +
							 "		 FROM tTransactionDetail a " +
							 "		 INNER JOIN mEmployeeArea b ON a.txtOutletCode=b.txtOutletCode " +
							 "		 INNER JOIN mProductBarcode c ON a.intProductCode=c.intProductCode " +
							 "		 WHERE a.txtOutletCode='"+txtOutletCode+"' " +
							 "		 AND c.txtProductName like '%"+txtProductName+"%' " +
							 "		 ORDER BY a.intProductCode,a.txtBatchNo,a.dtED,a.dtDate " +
							 "		); ";
		String selectQuery4 = "CREATE TEMP TABLE tempData AS " +
							 "SELECT a.dtDate " +
							 "	 	,b.txtProductName " +
							 "      ,a.txtBatchNo " +
							 "      ,a.dtED " +
							 "		,CASE WHEN a.intQty >= 0 THEN a.intQty ELSE a.intQty*-1 END intQty " +
							 "		,CASE WHEN a.txtType='"+new clsHardCode().txtStatus_TypeDataStockOpname+"' THEN " +
							 "		 	  CASE WHEN a.intQty<0 THEN 'K' ELSE 'D' END " +
							 "		 WHEN a.txtType='"+new clsHardCode().txtStatus_TypeDataGRN+"' THEN 'D' " +
							 "		 WHEN a.txtType='"+new clsHardCode().txtStatus_TypeDataBB+"' THEN 'D' " +
							 "		 ELSE 'K'END AS txtStatus " +
							 "FROM tTransactionDetail a " +
							 "INNER JOIN mProductBarcode b ON a.intProductCode=b.intProductCode " +
							 "WHERE a.txtOutletCode='"+txtOutletCode+"' " +
							 "AND b.txtProductName like '%"+txtProductName+"%' " +
							 "ORDER BY a.intProductCode,a.txtBatchNo,a.dtED,a.dtDate; ";
		String selectQuery5 = "SELECT  dtDate " +
							 "		 ,txtProductCode " +
							 "		 ,txtProductName " +
							 "		 ,txtNoDoc " +
							 "		 ,txtBatchNo " +
							 "		 ,dtED " +
							 "		 ,txtType " +
							 "		 ,[IN] " +
							 "		 ,OUT " +
							 "		 ,BALANCE " +
							 "		 ,txtOutletCode " +
							 "		 ,txtOutletName " +
							 "FROM ( " +
							 "		 SELECT  * " +
							 "				,(SELECT(CASE WHEN (SELECT SUM(intQty) FROM tempData " +
							 "									WHERE txtStatus = 'D' " +
							 "									AND dtDate <= a.dtDate AND txtProductName=a.txtProductName AND txtBatchNo=a.txtBatchNo AND dtED=a.dtED) is null THEN 0 " +
							 "				 ELSE (SELECT SUM(intQty) FROM tempData " +
							 "					   WHERE txtStatus = 'D' " +
							 "					   AND dtDate <= a.dtDate  AND txtProductName=a.txtProductName AND txtBatchNo=a.txtBatchNo AND dtED=a.dtED) END ) - " +
							 "	     	     (CASE WHEN (SELECT SUM(intQty) FROM tempData " +
							 "							 WHERE txtStatus = 'K' " +
							 "							 AND dtDate <= a.dtDate  AND txtProductName=a.txtProductName AND txtBatchNo=a.txtBatchNo AND dtED=a.dtED) is null THEN 0 " +
							 "				 ELSE (SELECT SUM(intQty) FROM tempData " +
							 "					   WHERE txtStatus = 'K' " +
							 "					   AND dtDate <= a.dtDate  AND txtProductName=a.txtProductName AND txtBatchNo=a.txtBatchNo AND dtED=a.dtED) END)) AS Balance " +
							 "		 FROM tempMaster a " +
							 "	    ) " +
							 "ORDER BY txtProductName; ";
		
		db.execSQL(selectQuery3);
		db.execSQL(selectQuery4);
		Cursor cursor5 = db.rawQuery(selectQuery5, null);
		
		org.json.simple.JSONObject result = new org.json.simple.JSONObject();
		org.json.simple.JSONArray JsonArray=new org.json.simple.JSONArray();
		
		if(cursor5.getCount()>0){
			if (cursor5 != null)
				cursor5.moveToFirst();
			do {
				result = new org.json.simple.JSONObject();
				
				result.put("dtDate", cursor5.getString(0));
				result.put("txtProductCode", cursor5.getString(1));
				result.put("txtProductName", cursor5.getString(2));
				result.put("txtNoDoc", cursor5.getString(3));
				result.put("txtBatchNo", cursor5.getString(4));
				result.put("dtED", cursor5.getString(5));
				result.put("txtType", cursor5.getString(6));
				result.put("IN", cursor5.getString(7));
				result.put("OUT", cursor5.getString(8));
				result.put("BALANCE", cursor5.getString(9));
				result.put("txtOutletName", cursor5.getString(11));
				
				JsonArray.add(result);
			} while (cursor5.moveToNext());
		}
		// return contact
		cursor5.close();
		
		db.execSQL(selectQuery);
		db.execSQL(selectQuery2);
		
		return JsonArray;
	}
	public org.json.simple.JSONArray getCalculateQtyForReport(SQLiteDatabase db, String txtOutletCode,String txtProductName) throws JSONException {
		tTransactionDetailData dt=new tTransactionDetailData();
		String selectQuery = "SELECT z.txtOutletCode " +
							 " 		,b.txtOutletName " +
							 "      ,z.intProductCode " +
							 " 		,c.txtProductCode " +
							 " 		,c.txtProductName " +
							 " 		,z.txtBatchNo " +
							 " 		,z.dtED " +
							 " 		,z.intQty " +
							 " FROM ( " +
							 " 			SELECT txtOutletCode " +
							 " 				  ,intProductCode " +
							 " 			      ,txtBatchNo " +
							 " 				  ,dtED " +
							 " 				  ,SUM(CASE WHEN txtType='"+new clsHardCode().txtStatus_TypeDataGRN+"' THEN intQty else 0 END) " +
							 " 				  +SUM(CASE WHEN txtType='"+new clsHardCode().txtStatus_TypeDataBB+"' THEN intQty else 0 END) " +
							 " 				  -SUM(CASE WHEN txtType='"+new clsHardCode().txtStatus_TypeDataRESO+"' THEN intQty else 0 END) " +
							 " 				  +SUM(CASE WHEN txtType='"+new clsHardCode().txtStatus_TypeDataStockOpname+"' THEN intQty else 0 END) " +
							 " 				  -SUM(CASE WHEN txtType='"+new clsHardCode().txtStatus_TypeDataPengeluaran+"' THEN intQty else 0 END) " +
							 " 				  -SUM(CASE WHEN txtType='"+new clsHardCode().txtStatus_TypeDataReserved+"' THEN intQty else 0 END) intQty " +
							 " 			FROM [tTransactionDetail] " +
							 " 			WHERE txtOutletCode LIKE '%" + txtOutletCode + "%' " +
							 " 			GROUP BY txtOutletCode " +
							 " 					,intProductCode " +
							 " 					,txtBatchNo " +
							 " 					,dtED " +
							 " 			UNION " +
							 " 			SELECT  txtOutletCode " +
							 " 				   ,txtProductCode as intProductCode " +
							 " 				   ,'-' as txtBatchNo" +
							 " 				   ,'-' as dtED " +
							 " 				   ,'0' as intQty " +
							 " 			FROM mStockAwal_Mobile " +
							 " 		    WHERE txtProductCode NOT IN (SELECT DISTINCT intProductCode " +
							 " 										 FROM tTransactionDetail " +
							 " 										WHERE txtOutletCode LIKE '%"+ txtOutletCode +"%') " +
							 "		    AND txtOutletCode LIKE '%" + txtOutletCode + "%' " +
							 "      ) z " +
							 " INNER JOIN mEmployeeArea b ON z.txtOutletCode=b.txtOutletCode " +
							 " INNER JOIN mProductBarcode c ON z.intProductCode=c.intProductCode " +
							 " WHERE c.txtProductName LIKE '%"+ txtProductName +"%' " +
							 " ORDER BY c.txtProductName ";
		Cursor cursor = db.rawQuery(selectQuery, null);
		org.json.simple.JSONObject result = new org.json.simple.JSONObject();
		org.json.simple.JSONArray JsonArray=new org.json.simple.JSONArray();
	    
		if(cursor.getCount()>0){
			if (cursor != null)
				cursor.moveToFirst();
			do {
				result = new org.json.simple.JSONObject();
				
				result.put("txtOutletName", cursor.getString(1));
				result.put("txtProductCode", cursor.getString(3));
				result.put("txtProductName", cursor.getString(4));
				result.put("txtBatchNo", cursor.getString(5));
				result.put("dtED", cursor.getString(6));
				result.put("intQty", cursor.getString(7));
				
				JsonArray.add(result);
			} while (cursor.moveToNext());
		}
		// return contact
		cursor.close();
		
		return JsonArray;
	}
	public void SaveDatatTransactionDetailData(SQLiteDatabase db,tTransactionDetailData data) {
		tTransactionDetailData dt=new tTransactionDetailData();
		//ContentValues values = new ContentValues();
		//values.put(dt.Property_intIdMenu, data.get_intIdMenu()); 
		//values.put(dt.Property_txtRoleId, data.get_txtRoleId()); 
		// Inserting Row
		//db.insert(TABLE_CONTACTS, null, values);
		String txtQuery="INSERT OR REPLACE into "+TABLE_CONTACTS+" ("+dt.PropertyAll+") "+
			"values('"	+String.valueOf(data.get_dtDate())+"','"
				+String.valueOf(data.get_dtED())+"','"
				+String.valueOf(data.get_intProductCode())+"','"
				+String.valueOf(data.get_intPush())+"','"
				+String.valueOf(data.get_intQty())+"','"
				+String.valueOf(data.get_intSubmit())+"','"
				+String.valueOf(data.get_intWeek())+"','"
				+String.valueOf(data.get_txtBatchNo())+"','"
				+String.valueOf(data.get_txtBranchCode())+"','"
				+String.valueOf(data.get_txtNoDoc())+"','"
				+String.valueOf(data.get_txtNoMO())+"','"
				+String.valueOf(data.get_txtOutletCode())+"','"
				+String.valueOf(data.get_txtOutletName())+"','"
				+String.valueOf(data.get_txtTransId())+"','"
				+String.valueOf(data.get_txtType())+"')";
		db.execSQL(txtQuery);
	}
	public List<tTransactionDetailData> getAllDataToPushData(SQLiteDatabase db) {
		tTransactionDetailData dt=new tTransactionDetailData();
		String selectQuery = "SELECT "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_intPush+"=0 And "+dt.Property_intSubmit+" =1";
		Cursor cursor = db.rawQuery(selectQuery, null);
		tTransactionDetailData contact = null;
		List<tTransactionDetailData> contactList = null;
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tTransactionDetailData>();
			do {
				contact = new tTransactionDetailData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_dtED(cursor.getString(1));
				contact.set_intProductCode(cursor.getString(2));
				contact.set_intPush(cursor.getString(3));
				contact.set_intQty(cursor.getString(4));
				contact.set_intSubmit(cursor.getString(5));
				contact.set_intWeek(cursor.getString(6));
				contact.set_txtBatchNo(cursor.getString(7));
				contact.set_txtBranchCode(cursor.getString(8));
				contact.set_txtNoDoc(cursor.getString(9));
				contact.set_txtNoMO(cursor.getString(10));
				contact.set_txtOutletCode(cursor.getString(11));
				contact.set_txtOutletName(cursor.getString(12));
				contact.set_txtTransId(cursor.getString(13));
				contact.set_txtType(cursor.getString(14));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		// return contact
		cursor.close();
		return contactList;
	}
	public void UpdateDataItem(SQLiteDatabase db,tTransactionDetailData data) {
		tTransactionDetailData dt=new tTransactionDetailData();
		//ContentValues values = new ContentValues();
		//values.put(dt.Property_intIdMenu, data.get_intIdMenu()); 
		//values.put(dt.Property_txtRoleId, data.get_txtRoleId()); 
		// Inserting Row
		//db.insert(TABLE_CONTACTS, null, values);
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intQty+"="
				+String.valueOf(data.get_intQty())+
				","+dt.Property_txtBatchNo+"="
				+String.valueOf(data.get_txtBatchNo())+
				","+dt.Property_txtNoDoc+"="
				+String.valueOf(data.get_txtNoDoc())+
				","+dt.Property_txtNoMO+"="
				+String.valueOf(data.get_txtNoMO())
				+" Where " + dt.Property_txtTransId +"='"+ data.get_txtTransId()+"'");
	}
	
	public void UpdateDataItemForSubmit(SQLiteDatabase db,String dataid) {
		tTransactionDetailData dt=new tTransactionDetailData();
		db.execSQL("Update "+TABLE_CONTACTS+" set  "
				+dt.Property_intSubmit+"=1"
				+" Where " + dt.Property_txtTransId +"='"+ dataid+"'");
	}
	
	public org.json.simple.JSONArray getCalculateQtyForReport(SQLiteDatabase db) throws JSONException {
		tTransactionDetailData dt=new tTransactionDetailData();
		String selectQuery = "select  b.txtProductCode " +
							 ",b.txtProductName " +
							 ",a.txtBatchNo " +
							 ",a.dtED " +
							 ",sum(case when a.txtType='"+new clsHardCode().txtStatus_TypeDataGRN+"' then a.intQty else 0 end) " +
							 "+sum(case when a.txtType='"+new clsHardCode().txtStatus_TypeDataBB+"' then a.intQty else 0 end) " +
							 "-sum(case when a.txtType='"+new clsHardCode().txtStatus_TypeDataRESO+"' then a.intQty else 0 end) " +
							 "+sum(case when a.txtType='"+new clsHardCode().txtStatus_TypeDataStockOpname+"' then a.intQty else 0 end) " +
							 "-sum(case when a.txtType='"+new clsHardCode().txtStatus_TypeDataPengeluaran+"' then a.intQty else 0 end) " +
							 "-sum(case when a.txtType='"+new clsHardCode().txtStatus_TypeDataReserved+"' then a.intQty else 0 end) intQty" +
				" from [tTransactionDetail] a " +
				" inner join [mProductBarCode] b ON a.intProductCode=b.intProductCode" +
				" group by a.intProductCode " +
				"		 ,b.txtProductName " +
				"		 ,a.txtBatchNo " +
				"		 ,a.dtED ";
		Cursor cursor = db.rawQuery(selectQuery, null);
		org.json.simple.JSONObject result = new org.json.simple.JSONObject();
		org.json.simple.JSONArray JsonArray=new org.json.simple.JSONArray();
	    
		if(cursor.getCount()>0){
			if (cursor != null)
				cursor.moveToFirst();
			do {
				result = new org.json.simple.JSONObject();
				
				result.put("txtProductCode", cursor.getString(0));
				result.put("txtProductName", cursor.getString(1));
				result.put("txtBatchNo", cursor.getString(2));
				result.put("dtED", cursor.getString(3));
				result.put("intQty", cursor.getString(4));
				
				JsonArray.add(result);
			} while (cursor.moveToNext());
		}
		// return contact
		cursor.close();
		
		return JsonArray;
	}
	
	public tTransactionDetailData getCalculateQtyByProductId(SQLiteDatabase db,String txtBatch,String dtED,String id) {
		tTransactionDetailData dt=new tTransactionDetailData();
		String selectQuery = "select intProductCode," +
							 "sum(case when txtType='"+new clsHardCode().txtStatus_TypeDataGRN+"' then intQty else 0 end) " +
							 "+sum(case when txtType='"+new clsHardCode().txtStatus_TypeDataBB+"' then intQty else 0 end) " +
							 "-sum(case when txtType='"+new clsHardCode().txtStatus_TypeDataRESO+"' then intQty else 0 end) " +
							 "+sum(case when txtType='"+new clsHardCode().txtStatus_TypeDataStockOpname+"' then intQty else 0 end) " +
							 "-sum(case when txtType='"+new clsHardCode().txtStatus_TypeDataPengeluaran+"' then intQty else 0 end) " +
							 "-sum(case when txtType='"+new clsHardCode().txtStatus_TypeDataReserved+"' then intQty else 0 end) intQty" +
				" from [tTransactionDetail] where [intProductCode]='"+id+"'" +
			    " and [txtBatchNo]='"+txtBatch+"'" +
			    " and [dtED]='"+dtED+"'" +
				" group by intProductCode";
		Cursor cursor = db.rawQuery(selectQuery, null);
		tTransactionDetailData contact = null;
		if(cursor.getCount()>0){
			contact = new tTransactionDetailData();
			if (cursor != null)
				cursor.moveToFirst();
			contact.set_intProductCode(cursor.getString(0));
			contact.set_intQty(cursor.getString(1));
		}
		// return contact
		cursor.close();
		return contact;
	}
	
	// Getting single contact
	public tTransactionDetailData getData(SQLiteDatabase db,String id) {
		tTransactionDetailData dt=new tTransactionDetailData();
		String selectQuery = "SELECT "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtTransId+"="+id;
		Cursor cursor = db.rawQuery(selectQuery, null);
		tTransactionDetailData contact = null;
		if(cursor.getCount()>0){
			contact = new tTransactionDetailData();
			if (cursor != null)
				cursor.moveToFirst();
			contact.set_dtDate(cursor.getString(0));
			contact.set_dtED(cursor.getString(1));
			contact.set_intProductCode(cursor.getString(2));
			contact.set_intPush(cursor.getString(3));
			contact.set_intQty(cursor.getString(4));
			contact.set_intSubmit(cursor.getString(5));
			contact.set_intWeek(cursor.getString(6));
			contact.set_txtBatchNo(cursor.getString(7));
			contact.set_txtBranchCode(cursor.getString(8));
			contact.set_txtNoDoc(cursor.getString(9));
			contact.set_txtNoMO(cursor.getString(10));
			contact.set_txtOutletCode(cursor.getString(11));
			contact.set_txtOutletName(cursor.getString(12));
			contact.set_txtTransId(cursor.getString(13));
			contact.set_txtType(cursor.getString(14));
		}
		// return contact
		cursor.close();
		return contact;
	}
	
	public List<tTransactionDetailData> getAllDataBatchFromIntProductCodeBatchNo(SQLiteDatabase db,String intProductCode, String txtBatchNo) {
		List<tTransactionDetailData> contactList = null;
		// Select All Query
		tTransactionDetailData dt=new tTransactionDetailData();
		if(intProductCode.equals("")){
			intProductCode="intProductCode";
		}else{
			intProductCode="'"+intProductCode+"'";
		}
		if(txtBatchNo.equals("")){
			txtBatchNo="txtBatchNo";
		}else{
			txtBatchNo="'"+txtBatchNo+"'";
		}
		String selectQuery = "SELECT DISTINCT "+dt.Property_txtBatchNo+ ","
							+dt.Property_txtOutletName+ ","
							+dt.Property_txtOutletCode
							+" FROM " + TABLE_CONTACTS 
							+" WHERE "+dt.Property_intProductCode+"="+intProductCode
							+" AND "+dt.Property_txtBatchNo+"= " +txtBatchNo + " "
							+" AND "+dt.Property_txtBatchNo+ "<> ''";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tTransactionDetailData>();
			do {
				tTransactionDetailData contact = new tTransactionDetailData();
				contact.set_txtBatchNo(cursor.getString(0));
				contact.set_txtOutletName(cursor.getString(1));
				contact.set_txtOutletCode(cursor.getString(2));
				
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	
	public List<tTransactionDetailData> getAllDataFromIntProductCodeBatchNo(SQLiteDatabase db,String intProductCode, String txtBatchNo) {
		List<tTransactionDetailData> contactList = null;
		// Select All Query
		tTransactionDetailData dt=new tTransactionDetailData();
		if(intProductCode.equals("")){
			intProductCode="intProductCode";
		}else{
			intProductCode="'"+intProductCode+"'";
		}
		if(txtBatchNo.equals("")){
			txtBatchNo="txtBatchNo";
		}else{
			txtBatchNo="'"+txtBatchNo+"'";
		}
		String selectQuery = "SELECT DISTINCT "+dt.Property_dtED + "," +
							 dt.Property_txtOutletName+ "," +
							 dt.Property_txtOutletCode+
							 " FROM " + TABLE_CONTACTS +
							 " WHERE "+dt.Property_intProductCode+"="+intProductCode+
							 " AND "+dt.Property_txtBatchNo+"= " +txtBatchNo + " " +
							 "ORDER BY " + dt.Property_dtED;

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tTransactionDetailData>();
			do {
				tTransactionDetailData contact = new tTransactionDetailData();
				contact.set_dtED(cursor.getString(0));
				contact.set_txtOutletName(cursor.getString(1));
				contact.set_txtOutletCode(cursor.getString(2));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	
	public List<tTransactionDetailData> getAllData(SQLiteDatabase db) {
		List<tTransactionDetailData> contactList = null;
		// Select All Query
		tTransactionDetailData dt=new tTransactionDetailData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS;

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tTransactionDetailData>();
			do {
				tTransactionDetailData contact = new tTransactionDetailData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_dtED(cursor.getString(1));
				contact.set_intProductCode(cursor.getString(2));
				contact.set_intPush(cursor.getString(3));
				contact.set_intQty(cursor.getString(4));
				contact.set_intSubmit(cursor.getString(5));
				contact.set_intWeek(cursor.getString(6));
				contact.set_txtBatchNo(cursor.getString(7));
				contact.set_txtBranchCode(cursor.getString(8));
				contact.set_txtNoDoc(cursor.getString(9));
				contact.set_txtNoMO(cursor.getString(10));
				contact.set_txtOutletCode(cursor.getString(11));
				contact.set_txtOutletName(cursor.getString(12));
				contact.set_txtTransId(cursor.getString(13));
				contact.set_txtType(cursor.getString(14));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tTransactionDetailData> getAllDataByOulet(SQLiteDatabase db,String txtOutletCode) {
		List<tTransactionDetailData> contactList = null;
		// Select All Query
		tTransactionDetailData dt=new tTransactionDetailData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtOutletCode+"='"+txtOutletCode+"' AND "+dt.Property_intSubmit+"=1";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tTransactionDetailData>();
			do {
				tTransactionDetailData contact = new tTransactionDetailData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_dtED(cursor.getString(1));
				contact.set_intProductCode(cursor.getString(2));
				contact.set_intPush(cursor.getString(3));
				contact.set_intQty(cursor.getString(4));
				contact.set_intSubmit(cursor.getString(5));
				contact.set_intWeek(cursor.getString(6));
				contact.set_txtBatchNo(cursor.getString(7));
				contact.set_txtBranchCode(cursor.getString(8));
				contact.set_txtNoDoc(cursor.getString(9));
				contact.set_txtNoMO(cursor.getString(10));
				contact.set_txtOutletCode(cursor.getString(11));
				contact.set_txtOutletName(cursor.getString(12));
				contact.set_txtTransId(cursor.getString(13));
				contact.set_txtType(cursor.getString(14));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tTransactionDetailData> getAllDataByOuletintProduct(SQLiteDatabase db,String txtOutletCode,String intProductId,String txtBatchNo,String dtEd) {
		List<tTransactionDetailData> contactList = null;
		// Select All Query
		tTransactionDetailData dt=new tTransactionDetailData();
		String selectQuery = "SELECT  "+dt.PropertyAll+" FROM " + TABLE_CONTACTS +" WHERE "+dt.Property_txtOutletCode+"='"+txtOutletCode+"' AND "+dt.Property_dtED+"='"+dtEd+"' AND "+dt.Property_intProductCode+"='"+intProductId+"' AND "+dt.Property_txtBatchNo+"='"+txtBatchNo+"'";

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			contactList=new ArrayList<tTransactionDetailData>();
			do {
				tTransactionDetailData contact = new tTransactionDetailData();
				contact.set_dtDate(cursor.getString(0));
				contact.set_dtED(cursor.getString(1));
				contact.set_intProductCode(cursor.getString(2));
				contact.set_intPush(cursor.getString(3));
				contact.set_intQty(cursor.getString(4));
				contact.set_intSubmit(cursor.getString(5));
				contact.set_intWeek(cursor.getString(6));
				contact.set_txtBatchNo(cursor.getString(7));
				contact.set_txtBranchCode(cursor.getString(8));
				contact.set_txtNoDoc(cursor.getString(9));
				contact.set_txtNoMO(cursor.getString(10));
				contact.set_txtOutletCode(cursor.getString(11));
				contact.set_txtOutletName(cursor.getString(12));
				contact.set_txtTransId(cursor.getString(13));
				contact.set_txtType(cursor.getString(14));
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
		tTransactionDetailData dt = new tTransactionDetailData();
		db.delete(TABLE_CONTACTS, dt.Property_txtTransId + " = ?",
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
