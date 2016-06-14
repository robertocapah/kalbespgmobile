package bl;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.tPenguaranDetail_MobileData;
import library.salesforce.dal.tPenguaranDetail_MobileDA;

public class tPenguaranDetail_MobileBL extends clsMainBL {
	public ArrayList<tPenguaranDetail_MobileData> getData(String id){
		SQLiteDatabase db=getDb();
		List<tPenguaranDetail_MobileData> listData=new ArrayList<tPenguaranDetail_MobileData>();
		tPenguaranDetail_MobileDA _tPenguaranDetail_MobileDA=new tPenguaranDetail_MobileDA(db);
		if(id.equals("")){
			listData=_tPenguaranDetail_MobileDA.getAllData(db);
		}else{
			tPenguaranDetail_MobileData data=new tPenguaranDetail_MobileData();
			data=_tPenguaranDetail_MobileDA.getData(db, id);
			listData.add(data);
		}
		return (ArrayList<tPenguaranDetail_MobileData>) listData;
	}
	public void UpdateDataByTxtDataID(String txtDataID,String intProductCode,String intQty,String txtBatchNo,String dtED,String intReason){
		SQLiteDatabase db=getDb();
		tPenguaranDetail_MobileDA _tPenguaranDetail_MobileDA=new tPenguaranDetail_MobileDA(db);
		_tPenguaranDetail_MobileDA.UpdateDataDetailByTxtDataID(db, txtDataID, intProductCode, intQty,txtBatchNo,dtED,intReason);
	}
	public List<tPenguaranDetail_MobileData> getDataBytxtDataId(String txtDataId){
		SQLiteDatabase db=getDb();
		List<tPenguaranDetail_MobileData> listData=new ArrayList<tPenguaranDetail_MobileData>();
		tPenguaranDetail_MobileDA _tPenguaranDetail_MobileDA=new tPenguaranDetail_MobileDA(db);
		listData=_tPenguaranDetail_MobileDA.getPenguaranDetailByTxtDataId(db,txtDataId);
		return (ArrayList<tPenguaranDetail_MobileData>) listData;
	}
	public ArrayList<tPenguaranDetail_MobileData> getDataByHeaderId(String id){
		SQLiteDatabase db=getDb();
		List<tPenguaranDetail_MobileData> listData=new ArrayList<tPenguaranDetail_MobileData>();
		tPenguaranDetail_MobileDA _tPenguaranDetail_MobileDA=new tPenguaranDetail_MobileDA(db);
		listData=_tPenguaranDetail_MobileDA.getPenguaranDetailByHeaderId(db, id);
		return (ArrayList<tPenguaranDetail_MobileData>) listData;
	}
	public ArrayList<tPenguaranDetail_MobileData> getDataByTxtNoByIntProductByTxtBatchNoByDtED(String txtNo,String intProductID,String txtBatchNo,String dtED,String txtDataId){
		SQLiteDatabase db=getDb();
		List<tPenguaranDetail_MobileData> listData=new ArrayList<tPenguaranDetail_MobileData>();
		tPenguaranDetail_MobileDA _tPenguaranDetail_MobileDA=new tPenguaranDetail_MobileDA(db);
		listData=_tPenguaranDetail_MobileDA.getDataByTxtNoByIntProductByTxtBatchNoByDtED(db, txtNo,intProductID,txtBatchNo,dtED,txtDataId);
		return (ArrayList<tPenguaranDetail_MobileData>) listData;
	}
	
	public void  SaveData(tPenguaranDetail_MobileData dt){
		SQLiteDatabase db=getDb();
		tPenguaranDetail_MobileDA _tPenguaranDetail_MobileDA=new tPenguaranDetail_MobileDA(db);
		_tPenguaranDetail_MobileDA.SaveDatatPenguaranDetail_MobileData(db, dt);
	}
	
	public void UpdateDataByIntProductCode(String txtNoPenguaran,String intProductCode){
		SQLiteDatabase db=getDb();
		tPenguaranDetail_MobileDA _tPenguaranDetail_MobileDA=new tPenguaranDetail_MobileDA(db);
		_tPenguaranDetail_MobileDA.UpdateDataDetailByIntProductCode(db,txtNoPenguaran, intProductCode);
	}
	
	public void UpdateData(String txtNoPenguaran,String intProductCode,String intQty,String txtBatchNo,String dtED){
		SQLiteDatabase db=getDb();
		tPenguaranDetail_MobileDA _tPenguaranDetail_MobileDA=new tPenguaranDetail_MobileDA(db);
		_tPenguaranDetail_MobileDA.UpdateDataDetail(db, txtNoPenguaran, intProductCode, intQty,txtBatchNo,dtED);
	}
	
	public void UpdateDataByTxtDataID(String txtDataID,String intProductCode,String intQty,String txtBatchNo,String dtED){
		SQLiteDatabase db=getDb();
		tPenguaranDetail_MobileDA _tPenguaranDetail_MobileDA=new tPenguaranDetail_MobileDA(db);
		_tPenguaranDetail_MobileDA.UpdateDataDetailByTxtDataID(db, txtDataID, intProductCode, intQty,txtBatchNo,dtED);
	}
	
	public void  DeleteData(tPenguaranDetail_MobileData dt){
		SQLiteDatabase db=getDb();
		tPenguaranDetail_MobileDA _tPenguaranDetail_MobileDA=new tPenguaranDetail_MobileDA(db);
		_tPenguaranDetail_MobileDA.deleteContact(db, dt.get_txtDataId());
	}
	
	public void SaveDataSubmit(List<tPenguaranDetail_MobileData> listData){
		SQLiteDatabase db=getDb();
		tPenguaranDetail_MobileDA _tPenguaranDetail_MobileDA=new tPenguaranDetail_MobileDA(db);
		for(tPenguaranDetail_MobileData dt : listData){
			_tPenguaranDetail_MobileDA.UpdateDataItemForSubmit(db, dt.get_txtDataId());	
		}
	}
	
	public List<tPenguaranDetail_MobileData> getQuantity(String txtNo, String intProductCode, String txtBatchNo,String dtED){
		SQLiteDatabase db=getDb();
		tPenguaranDetail_MobileDA _tPenguaranDetail_MobileDA=new tPenguaranDetail_MobileDA(db);
		List<tPenguaranDetail_MobileData> listData=_tPenguaranDetail_MobileDA.getCalculateProductGroupBytxtNoByIntProductBytxtBatchNoByDtED(db, txtNo, intProductCode, txtBatchNo, dtED);
		return (List<tPenguaranDetail_MobileData>) listData;
	}
	
}
