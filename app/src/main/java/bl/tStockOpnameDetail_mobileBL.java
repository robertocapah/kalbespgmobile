package bl;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;

import library.salesforce.common.tStockOpnameDetail_mobileData;
import library.salesforce.dal.tStockOpnameDetail_mobileDA;

public class tStockOpnameDetail_mobileBL extends clsMainBL{
	public ArrayList<tStockOpnameDetail_mobileData> getDataByTxtDataID(String id){
		SQLiteDatabase db=getDb();
		List<tStockOpnameDetail_mobileData> listData=new ArrayList<tStockOpnameDetail_mobileData>();
		tStockOpnameDetail_mobileDA _tStockOpnameDetail_mobileDA=new tStockOpnameDetail_mobileDA(db);
		
		tStockOpnameDetail_mobileData data=new tStockOpnameDetail_mobileData();
		data=_tStockOpnameDetail_mobileDA.getDataByTxtDataID(db, id);
		listData.add(data);
		
		return (ArrayList<tStockOpnameDetail_mobileData>) listData;
	}
	
	public ArrayList<tStockOpnameDetail_mobileData> getData(String id){
		SQLiteDatabase db=getDb();
		List<tStockOpnameDetail_mobileData> listData=new ArrayList<tStockOpnameDetail_mobileData>();
		tStockOpnameDetail_mobileDA _tStockOpnameDetail_mobileDA=new tStockOpnameDetail_mobileDA(db);
		if(id.equals("")){
			listData=_tStockOpnameDetail_mobileDA.getAllData(db);
			if(listData!=null){
				listData=new ArrayList<tStockOpnameDetail_mobileData>();
			}
		}else{
			tStockOpnameDetail_mobileData data=new tStockOpnameDetail_mobileData();
			data=_tStockOpnameDetail_mobileDA.getData(db, id);
			listData.add(data);
		}
		return (ArrayList<tStockOpnameDetail_mobileData>) listData;
	}
	public ArrayList<tStockOpnameDetail_mobileData> getDataByHeaderId(String id){
		SQLiteDatabase db=getDb();
		List<tStockOpnameDetail_mobileData> listData=new ArrayList<tStockOpnameDetail_mobileData>();
		tStockOpnameDetail_mobileDA _tStockOpnameDetail_mobileDA=new tStockOpnameDetail_mobileDA(db);
		listData=_tStockOpnameDetail_mobileDA.getAllDataByHeaderId(db, id);
		return (ArrayList<tStockOpnameDetail_mobileData>) listData;
	}
	public ArrayList<tStockOpnameDetail_mobileData> getDataByTxtNoByIntProductByTxtBatchNoByDtED(String txtNo,String intProductID,String txtBatchNo,String dtED,String txtDataId){
		SQLiteDatabase db=getDb();
		List<tStockOpnameDetail_mobileData> listData=new ArrayList<tStockOpnameDetail_mobileData>();
		tStockOpnameDetail_mobileDA _tStockOpnameDetail_mobileDA=new tStockOpnameDetail_mobileDA(db);
		listData=_tStockOpnameDetail_mobileDA.getDataByTxtNoByIntProductByTxtBatchNoByDtED(db, txtNo,intProductID,txtBatchNo,dtED,txtDataId);
		return (ArrayList<tStockOpnameDetail_mobileData>) listData;
	}
	public void  SaveData(tStockOpnameDetail_mobileData dt){
		SQLiteDatabase db=getDb();
		tStockOpnameDetail_mobileDA _tStockOpnameDetail_mobileDA=new tStockOpnameDetail_mobileDA(db);
		_tStockOpnameDetail_mobileDA.SaveDatatStockOpnameDetail_mobileData(db, dt);
	}
	public void UpdateDataQuantity(tStockOpnameDetail_mobileData dt){
		SQLiteDatabase db=getDb();
		tStockOpnameDetail_mobileDA _tStockOpnameDetail_mobileDA=new tStockOpnameDetail_mobileDA(db);
		_tStockOpnameDetail_mobileDA.UpdateDataQuantity(db, dt);
	}
	public void  DeleteData(tStockOpnameDetail_mobileData dt){
		SQLiteDatabase db=getDb();
		tStockOpnameDetail_mobileDA _tStockOpnameDetail_mobileDA=new tStockOpnameDetail_mobileDA(db);
		_tStockOpnameDetail_mobileDA.deleteContact(db, dt.get_txtDataId());
	}
	public void SaveDataSubmit(List<tStockOpnameDetail_mobileData> listData){
		SQLiteDatabase db=getDb();
		tStockOpnameDetail_mobileDA _tStockOpnameDetail_mobileDA=new tStockOpnameDetail_mobileDA(db);
		for(tStockOpnameDetail_mobileData dt : listData){
			_tStockOpnameDetail_mobileDA.UpdateDataItemForSubmit(db, dt.get_txtDataId());	
		}
		
	}
}
