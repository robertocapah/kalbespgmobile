package bl;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.tSalesOrderDetail_MobileData;
import library.salesforce.dal.tSalesOrderDetail_MobileDA;

public class tSalesOrderDetail_MobileBL extends clsMainBL{
	public ArrayList<tSalesOrderDetail_MobileData> getData(String id){
		SQLiteDatabase db=getDb();
		List<tSalesOrderDetail_MobileData> listData=new ArrayList<tSalesOrderDetail_MobileData>();
		tSalesOrderDetail_MobileDA _tSalesOrderDetail_MobileDA=new tSalesOrderDetail_MobileDA(db);
		if(id.equals("")){
			listData=_tSalesOrderDetail_MobileDA.getAllData(db);
			if(listData!=null){
				listData=new ArrayList<tSalesOrderDetail_MobileData>();
			}
		}else{
			tSalesOrderDetail_MobileData data=new tSalesOrderDetail_MobileData();
			data=_tSalesOrderDetail_MobileDA.getData(db, id);
			listData.add(data);
		}
		return (ArrayList<tSalesOrderDetail_MobileData>) listData;
	}
	public ArrayList<tSalesOrderDetail_MobileData> getDataByHeaderId(String id){
		SQLiteDatabase db=getDb();
		List<tSalesOrderDetail_MobileData> listData=new ArrayList<tSalesOrderDetail_MobileData>();
		tSalesOrderDetail_MobileDA _tSalesOrderDetail_MobileDA=new tSalesOrderDetail_MobileDA(db);
		listData=_tSalesOrderDetail_MobileDA.getAllDataByHeaderId(db, id);
		return (ArrayList<tSalesOrderDetail_MobileData>) listData;
	}
	public ArrayList<tSalesOrderDetail_MobileData> getDataByTxtNoByIntProductByTxtBatchNoByDtED(String txtNo,String intProductID,String txtBatchNo,String dtED,String txtDataId){
		SQLiteDatabase db=getDb();
		List<tSalesOrderDetail_MobileData> listData=new ArrayList<tSalesOrderDetail_MobileData>();
		tSalesOrderDetail_MobileDA _tSalesOrderDetail_MobileDA=new tSalesOrderDetail_MobileDA(db);
		listData=_tSalesOrderDetail_MobileDA.getDataByTxtNoByIntProductByTxtBatchNoByDtED(db, txtNo,intProductID,txtBatchNo,dtED,txtDataId);
		return (ArrayList<tSalesOrderDetail_MobileData>) listData;
	}
	public ArrayList<tSalesOrderDetail_MobileData> getDataBytxtDataID(String txtDataId){
		SQLiteDatabase db=getDb();
		List<tSalesOrderDetail_MobileData> listData=new ArrayList<tSalesOrderDetail_MobileData>();
		tSalesOrderDetail_MobileDA _tSalesOrderDetail_MobileDA=new tSalesOrderDetail_MobileDA(db);
		listData=_tSalesOrderDetail_MobileDA.getAllDataByTxtDataID(db, txtDataId);
		return (ArrayList<tSalesOrderDetail_MobileData>) listData;
	}
	public void  SaveData(tSalesOrderDetail_MobileData dt){
		SQLiteDatabase db=getDb();
		tSalesOrderDetail_MobileDA _tSalesOrderDetail_MobileDA=new tSalesOrderDetail_MobileDA(db);
		_tSalesOrderDetail_MobileDA.SaveDatatSalesOrderDetail_MobileData(db, dt);
	}
	public void  DeleteData(tSalesOrderDetail_MobileData dt){
		SQLiteDatabase db=getDb();
		tSalesOrderDetail_MobileDA _tSalesOrderDetail_MobileDA=new tSalesOrderDetail_MobileDA(db);
		_tSalesOrderDetail_MobileDA.deleteContact(db, dt.get_txtDataId());
	}
	public void SaveDataSubmit(ArrayList<tSalesOrderDetail_MobileData> listOftSalesOrderDetail_MobileData){
		SQLiteDatabase db=getDb();
		tSalesOrderDetail_MobileDA _tSalesOrderDetail_MobileDA=new tSalesOrderDetail_MobileDA(db);
		for(tSalesOrderDetail_MobileData dt : listOftSalesOrderDetail_MobileData){
			_tSalesOrderDetail_MobileDA.UpdateDataItemForSubmit(db, dt.get_txtDataId());	
		}
	}
	
	
	public void UpdateDataItem(tSalesOrderDetail_MobileData _data){
		SQLiteDatabase db=getDb();
		tSalesOrderDetail_MobileDA _tSalesOrderDetail_MobileDA=new tSalesOrderDetail_MobileDA(db);
		_tSalesOrderDetail_MobileDA.UpdateDataItem(db, _data);
	}
	
	public void UpdateDataItemSO(tSalesOrderDetail_MobileData _data){
		SQLiteDatabase db=getDb();
		tSalesOrderDetail_MobileDA _tSalesOrderDetail_MobileDA=new tSalesOrderDetail_MobileDA(db);
		_tSalesOrderDetail_MobileDA.UpdateDataItemSO(db, _data);
	}
	
	public void UpdateDataByIntProductCode(String txtNoInventoryOut,String intProductCode){
		SQLiteDatabase db=getDb();
		tSalesOrderDetail_MobileDA _tSalesOrderDetail_MobileDA=new tSalesOrderDetail_MobileDA(db);
		_tSalesOrderDetail_MobileDA.UpdateDataDetailByIntProductCode(db,txtNoInventoryOut, intProductCode);
	}
	
	public void UpdateData(String txtNoInventoryOut,String intProductCode,String intQty){
		SQLiteDatabase db=getDb();
		tSalesOrderDetail_MobileDA _tSalesOrderDetail_MobileDA=new tSalesOrderDetail_MobileDA(db);
		_tSalesOrderDetail_MobileDA.UpdateDataDetail(db, txtNoInventoryOut, intProductCode, intQty);
	}
	
	public List<tSalesOrderDetail_MobileData> getQuantity(String txtNo, String intProductCode, String txtBatchNo,String dtED){
		SQLiteDatabase db=getDb();
		tSalesOrderDetail_MobileDA _tSalesOrderDetail_MobileDA=new tSalesOrderDetail_MobileDA(db);
		List<tSalesOrderDetail_MobileData> listData=_tSalesOrderDetail_MobileDA.getCalculateProductGroupBytxtNoByIntProductBytxtBatchNoByDtED(db, txtNo, intProductCode, txtBatchNo, dtED);
		return (List<tSalesOrderDetail_MobileData>) listData;
	}
	
	
}
