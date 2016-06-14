package library.salesforce.dal;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import library.salesforce.common.mconfigData;
import library.salesforce.common.tErrorLogData;
import library.salesforce.common.tReportSalesSOData;
import library.salesforce.common.tSalesProductDetailData;
import library.salesforce.common.tSalesProductHeaderData;

public class tReportSalesSODA {
	

	public tReportSalesSODA(SQLiteDatabase db) {
	}
	// Getting All Contacts
	public List<tReportSalesSOData> getReportDataByOutletCode(SQLiteDatabase db) {
		List<tReportSalesSOData> contactList = null;
		// Select All Query
		tErrorLogData dt = new tErrorLogData();
		String selectQuery = "SELECT a.intId,a.dtDate,d.txtOutletCode,d.txtOutletName,a.txtKeterangan,a.intSumItem, "+
			"a.intSumAmount,a.intSync,a.txtBranchCode,a.txtBranchName,a.txtNIK,b.intPrice, "+
			"b.intQty,c.txtBrandDetailGramCode,c.txtProductBrandDetailGramName, "+
			"b.intTotal,b.intActive "+
			"FROM tSalesProductHeader a "+
			"LEFT JOIN tSalesProductDetail b on a.intId=b.txtNoSO and b.intActive='1' "+
			"LEFT JOIN mEmployeeSalesProduct c on b.txtCodeProduct=c.txtBrandDetailGramCode "+
			"LEFT JOIN mEmployeeArea d on a.OutletCode=d.txtOutletCode WHERE a.intSubmit=1";
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			contactList = new ArrayList<tReportSalesSOData>();
			do {
				tReportSalesSOData contact = new tReportSalesSOData();
				tSalesProductHeaderData _datatSalesProductHeaderData=new tSalesProductHeaderData();
				tSalesProductDetailData _datatSalesProductDetailData=new tSalesProductDetailData();
				_datatSalesProductHeaderData.set_intId(cursor.getString(0));
				_datatSalesProductHeaderData.set_dtDate(cursor.getString(1));
				_datatSalesProductHeaderData.set_OutletCode(cursor.getString(2));
				_datatSalesProductHeaderData.set_OutletName(cursor.getString(3));
				_datatSalesProductHeaderData.set_txtKeterangan(cursor.getString(4));
				_datatSalesProductHeaderData.set_intSumItem(cursor.getString(5));
				_datatSalesProductHeaderData.set_intSumAmount(cursor.getString(6));
				_datatSalesProductHeaderData.set_intSync(cursor.getString(7));
				_datatSalesProductHeaderData.set_txtBranchCode(cursor.getString(8));
				_datatSalesProductHeaderData.set_txtBranchName(cursor.getString(9));
				_datatSalesProductHeaderData.set_txtNIK(cursor.getString(10));
				_datatSalesProductDetailData.set_intPrice(cursor.getString(11));
				_datatSalesProductDetailData.set_intQty(cursor.getString(12));
				_datatSalesProductDetailData.set_txtCodeProduct(cursor.getString(13));
				_datatSalesProductDetailData.set_txtNameProduct(cursor.getString(14));
				_datatSalesProductDetailData.set_intTotal(cursor.getString(15));
				_datatSalesProductDetailData.set_intActive(cursor.getString(16));
				contact.set_tSalesProductDetailData(_datatSalesProductDetailData);
				contact.set_tSalesProductHeaderData(_datatSalesProductHeaderData);
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tReportSalesSOData> getReportSummaryDataByOutletCode(SQLiteDatabase db) {
		List<tReportSalesSOData> contactList = null;
		// Select All Query
		tErrorLogData dt = new tErrorLogData();
		String selectQuery = "SELECT d.txtOutletCode,d.txtOutletName,CAST(SUM(b.intQty) as text) as intSumItem, "+
			"CAST(SUM(b.intTotal) as text) as intSumAmount,a.txtBranchCode,a.txtBranchName,a.txtNIK "+
			"FROM tSalesProductHeader a "+
			"LEFT JOIN tSalesProductDetail b on a.intId=b.txtNoSO and b.intActive='1' "+
			"LEFT JOIN mEmployeeArea d on a.OutletCode=d.txtOutletCode WHERE a.intSubmit=1 "+
			"GROUP BY d.txtOutletCode,d.txtOutletName,a.txtBranchCode,a.txtBranchName,a.txtNIK ";
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			contactList = new ArrayList<tReportSalesSOData>();
			do {
				tReportSalesSOData contact = new tReportSalesSOData();
				tSalesProductDetailData _datatSalesProductDetailData=new tSalesProductDetailData();
				tSalesProductHeaderData _datatSalesProductHeaderData=new tSalesProductHeaderData();
				_datatSalesProductHeaderData.set_OutletCode(cursor.getString(0));
				_datatSalesProductHeaderData.set_OutletName(cursor.getString(1));
				_datatSalesProductHeaderData.set_intSumItem(cursor.getString(2));
				_datatSalesProductHeaderData.set_intSumAmount(cursor.getString(3));
				_datatSalesProductHeaderData.set_txtBranchCode(cursor.getString(4));
				_datatSalesProductHeaderData.set_txtBranchName(cursor.getString(5));
				_datatSalesProductHeaderData.set_txtNIK(cursor.getString(6));
				contact.set_tSalesProductHeaderData(_datatSalesProductHeaderData);
				contact.set_tSalesProductDetailData(_datatSalesProductDetailData);
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
	public List<tReportSalesSOData> getReportDataByProduk(SQLiteDatabase db) {
		List<tReportSalesSOData> contactList = null;
		// Select All Query
		tErrorLogData dt = new tErrorLogData();
		String selectQuery = "SELECT c.txtBrandDetailGramCode,c.txtProductBrandDetailGramName,b.intPrice, "+
		"SUM(CAST(b.intQty as Integer)) TotalQTY, "+
		"SUM(CAST(b.intTotal as Integer)) TotalAmount "+
		"FROM tSalesProductHeader a "+
		"LEFT JOIN tSalesProductDetail b on a.intId=b.txtNoSO and b.intActive='1' "+
		"LEFT JOIN mEmployeeSalesProduct c on b.txtCodeProduct=c.txtBrandDetailGramCode "+
		"LEFT JOIN mEmployeeArea d on a.OutletCode=d.txtOutletCode "+
		"GROUP BY c.txtBrandDetailGramCode,c.txtProductBrandDetailGramName,b.intPrice ";
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			contactList = new ArrayList<tReportSalesSOData>();
			do {
				tReportSalesSOData contact = new tReportSalesSOData();
				tSalesProductDetailData _datatSalesProductDetailData=new tSalesProductDetailData();
				_datatSalesProductDetailData.set_intPrice(cursor.getString(2));
				_datatSalesProductDetailData.set_intQty(cursor.getString(3));
				_datatSalesProductDetailData.set_txtCodeProduct(cursor.getString(0));
				_datatSalesProductDetailData.set_txtNameProduct(cursor.getString(1));
				_datatSalesProductDetailData.set_intTotal(cursor.getString(4));
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		return contactList;
	}
}
