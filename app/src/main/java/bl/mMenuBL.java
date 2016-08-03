package bl;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import library.salesforce.common.mMenuData;
import library.salesforce.common.tLeaveMobileData;
import library.salesforce.dal.mEmployeeAreaDA;
import library.salesforce.dal.mEmployeeBranchDA;
import library.salesforce.dal.mEmployeeSalesProductDA;
import library.salesforce.dal.mMenuDA;
import library.salesforce.dal.mProductBarcodeDA;
import library.salesforce.dal.mTypeLeaveMobileDA;
import library.salesforce.dal.tAbsenUserDA;
import library.salesforce.dal.tLeaveMobileDA;
import library.salesforce.dal.tSalesProductHeaderDA;

public class mMenuBL extends clsMainBL {
	public void SaveData(List<mMenuData> Listdata) {
		SQLiteDatabase db = getDb();
		mMenuDA _mMenuDA = new mMenuDA(db);
		_mMenuDA.DeleteAllDAta(db);
		Long index = Long.valueOf(_mMenuDA.getContactsCount(db) + 1);
		for (mMenuData data : Listdata) {
			data.set_intId(index);
			_mMenuDA.SaveDataMConfig(db, data);
			index += 1;
		}
		db.close();
	}

	public mMenuData getMenuDataByMenuName(String menuName) {
		SQLiteDatabase db = getDb();
		mMenuDA _mMenuDA = new mMenuDA(db);
		mMenuData dt = _mMenuDA.getDataByNamaMenu(db, menuName);
		db.close();
		return dt;
	}

	public List<mMenuData> getDatabyParentId(int id) {
		SQLiteDatabase db = getDb();
		mMenuDA _mMenuDA = new mMenuDA(db);
		tSalesProductHeaderDA _tSalesProductHeaderDA = new tSalesProductHeaderDA(db);
		List<mMenuData> listData = _mMenuDA.getDatabyParentId(db, id);
		List<mMenuData> tmpData = new ArrayList<mMenuData>();
		List<tLeaveMobileData> listDataLeave = new tLeaveMobileBL().getData("");
		for (mMenuData data : listData) {
			if (listDataLeave.size() == 0 && data.get_TxtDescription().contains("mnReporting")) {
				if (_tSalesProductHeaderDA.getContactsCount(db) > 0) {
					tmpData.add(data);
				}
			} else if (data.get_TxtDescription().contains("mnAbsenKBN") || data.get_TxtDescription().contains("mnInventoryKBN") || data.get_TxtDescription().contains("mnInventoryOut") || data.get_TxtDescription().contains("mnStockopname") || data.get_TxtDescription().contains("mnPushDataData") || data.get_TxtDescription().contains("mnReportKBN") || data.get_TxtDescription().contains("mnLogoutKBN")) {
				mEmployeeAreaDA _mEmployeeAreaDA = new mEmployeeAreaDA(db);
				mEmployeeBranchDA _mEmployeeBranchDA = new mEmployeeBranchDA(db);
				mProductBarcodeDA _mProductBarcodeDA = new mProductBarcodeDA(db);
				tLeaveMobileDA _tLeaveMobileDA = new tLeaveMobileDA(db);
				mEmployeeSalesProductDA _mEmployeeSalesProductDA = new mEmployeeSalesProductDA(db);
				if (_mEmployeeAreaDA.getContactsCount(db) > 0 && _mEmployeeBranchDA.getContactsCount(db) > 0 && _mEmployeeSalesProductDA.getContactsCount(db) > 0) {
					if (listDataLeave.size() == 0 && _mEmployeeAreaDA.getContactsCount(db) > 0 && _mEmployeeBranchDA.getContactsCount(db) > 0 && _mProductBarcodeDA.getContactsCount(db) > 0) {
						tmpData.add(data);
					}
				} else if (data.get_TxtDescription().contains("mnLeave")) {
					mTypeLeaveMobileDA _mTypeLeaveMobileDA = new mTypeLeaveMobileDA(db);
					tAbsenUserDA _tAbsenUserDA = new tAbsenUserDA(db);
					if (_tAbsenUserDA.getContactsCountSubmit(db) == 0 && _mTypeLeaveMobileDA.getContactsCount(db) > 0) {
						tmpData.add(data);
					}
				}
			} else if(data.get_TxtDescription().contains("mnReso") || data.get_TxtDescription().contains("mnActivity") || data.get_TxtDescription().contains("mnCustomerBase") || data.get_TxtDescription().contains("mnDownloadData")){
				tmpData.add(data);
			}
		}
		db.close();
		return tmpData;
	}

	public void setMenuManual(){
		SQLiteDatabase db = getDb();
		mMenuDA _mMenuDA = new mMenuDA(db);
		_mMenuDA.DeleteAllDAta(db);
		_mMenuDA.InsertDefaultMconfig(db);
	}
}
