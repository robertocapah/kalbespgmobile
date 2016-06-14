package bl;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;

import library.salesforce.common.tGRNDetail_mobileData;
import library.salesforce.dal.tGRNDetail_mobileDA;

public class tGRNDetail_mobileBL extends clsMainBL{
	public ArrayList<tGRNDetail_mobileData> getData(String id){
		SQLiteDatabase db=getDb();
		List<tGRNDetail_mobileData> listData=new ArrayList<tGRNDetail_mobileData>();
		tGRNDetail_mobileDA _tGRNDetail_mobileDA=new tGRNDetail_mobileDA(db);
		if(id.equals("")){
			listData=_tGRNDetail_mobileDA.getAllData(db);
		}else{
			tGRNDetail_mobileData data=new tGRNDetail_mobileData();
			data=_tGRNDetail_mobileDA.getData(db, id);
			listData.add(data);
		}
		return (ArrayList<tGRNDetail_mobileData>) listData;
	}
	public tGRNDetail_mobileData getDataBYHeaderAndProduct(String id,String idProduct){
		SQLiteDatabase db=getDb();
		tGRNDetail_mobileData dt=new tGRNDetail_mobileData();
		tGRNDetail_mobileDA _tGRNDetail_mobileDA=new tGRNDetail_mobileDA(db);
		dt=_tGRNDetail_mobileDA.getDataBYHeaderAndProduct(db, id,idProduct);
		return dt;
	}
	public ArrayList<tGRNDetail_mobileData> getDataByHeaderId(String id){
		SQLiteDatabase db=getDb();
		List<tGRNDetail_mobileData> listData=new ArrayList<tGRNDetail_mobileData>();
		tGRNDetail_mobileDA _tGRNDetail_mobileDA=new tGRNDetail_mobileDA(db);
		listData=_tGRNDetail_mobileDA.getAllDataByHeaderId(db, id);
		return (ArrayList<tGRNDetail_mobileData>) listData;
	}
	public void  SaveData(tGRNDetail_mobileData dt){
		SQLiteDatabase db=getDb();
		tGRNDetail_mobileDA _tGRNDetail_mobileDA=new tGRNDetail_mobileDA(db);
		_tGRNDetail_mobileDA.SaveDatatGRNDetail_mobileData(db, dt);
	}
	public void  DeleteData(tGRNDetail_mobileData dt){
		SQLiteDatabase db=getDb();
		tGRNDetail_mobileDA _tGRNDetail_mobileDA=new tGRNDetail_mobileDA(db);
		_tGRNDetail_mobileDA.deleteContact(db, dt.get_txtDataId());
	}
	public void SaveDataSubmit(List<tGRNDetail_mobileData> listData){
		SQLiteDatabase db=getDb();
		tGRNDetail_mobileDA _tGRNDetail_mobileDA=new tGRNDetail_mobileDA(db);
		for(tGRNDetail_mobileData dt : listData){
			_tGRNDetail_mobileDA.UpdateDataItemForSubmit(db, dt.get_txtDataId());	
		}
		
	}
}
