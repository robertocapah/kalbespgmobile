package bl;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.tPOStatus_mobileData;
import library.salesforce.dal.tPOStatus_mobileDA;

public class tPOStatus_mobileBL extends clsMainBL {
	public ArrayList<tPOStatus_mobileData> getData(String id){
		SQLiteDatabase db=getDb();
		List<tPOStatus_mobileData> listData=new ArrayList<tPOStatus_mobileData>();
		tPOStatus_mobileDA _tPOStatus_mobileDA=new tPOStatus_mobileDA(db);
		if(id.equals("")){
			listData=_tPOStatus_mobileDA.getAllData(db);
			if(listData!=null){
				listData=new ArrayList<tPOStatus_mobileData>();
			}
		}else{
			tPOStatus_mobileData data=new tPOStatus_mobileData();
			data=_tPOStatus_mobileDA.getData(db, id);
			listData.add(data);
		}
		return (ArrayList<tPOStatus_mobileData>) listData;
	}
	public ArrayList<tPOStatus_mobileData> getDataByHeaderId(String id){
		SQLiteDatabase db=getDb();
		List<tPOStatus_mobileData> listData=new ArrayList<tPOStatus_mobileData>();
		tPOStatus_mobileDA _tPOStatus_mobileDA=new tPOStatus_mobileDA(db);
		listData=_tPOStatus_mobileDA.getAllDataByHeaderId(db, id);
		return (ArrayList<tPOStatus_mobileData>) listData;
	}
	public ArrayList<tPOStatus_mobileData> getStatusActiveByHeaderId(String id){
		SQLiteDatabase db=getDb();
		List<tPOStatus_mobileData> listData=new ArrayList<tPOStatus_mobileData>();
		tPOStatus_mobileDA _tPOStatus_mobileDA=new tPOStatus_mobileDA(db);
		listData=_tPOStatus_mobileDA.getStatusActiveByHeaderId(db, id);
		return (ArrayList<tPOStatus_mobileData>) listData;
	}
	public void  SaveData(tPOStatus_mobileData dt){
		SQLiteDatabase db=getDb();
		tPOStatus_mobileDA _tPOStatus_mobileDA=new tPOStatus_mobileDA(db);
		_tPOStatus_mobileDA.SaveDatatPOStatus_mobileData(db, dt);
	}
	public void  SaveDataAll(List<tPOStatus_mobileData> dt){
		SQLiteDatabase db=getDb();
		tPOStatus_mobileDA _tPOStatus_mobileDA=new tPOStatus_mobileDA(db);
		for (tPOStatus_mobileData tPOStatus_mobileData : dt) {
			_tPOStatus_mobileDA.SaveDatatPOStatus_mobileData(db, tPOStatus_mobileData);	
		}
		
	}
	public void  DeleteData(tPOStatus_mobileData dt){
		SQLiteDatabase db=getDb();
		tPOStatus_mobileDA _tPOStatus_mobileDA=new tPOStatus_mobileDA(db);
		_tPOStatus_mobileDA.deleteContact(db, dt.get_txtDataId());
	}
	public void SaveDataSubmit(ArrayList<tPOStatus_mobileData> listOftPOStatus_mobileData){
		SQLiteDatabase db=getDb();
		tPOStatus_mobileDA _tPOStatus_mobileDA=new tPOStatus_mobileDA(db);
		for(tPOStatus_mobileData dt : listOftPOStatus_mobileData){
			_tPOStatus_mobileDA.UpdateDataItemForSubmit(db, dt.get_txtDataId());	
		}
		
	}
	public void SaveDataSync(ArrayList<tPOStatus_mobileData> listOftPOStatus_mobileData){
		SQLiteDatabase db=getDb();
		tPOStatus_mobileDA _tPOStatus_mobileDA=new tPOStatus_mobileDA(db);
		for(tPOStatus_mobileData dt : listOftPOStatus_mobileData){
			_tPOStatus_mobileDA.UpdateDataItemForSync(db, dt.get_txtDataId());	
		}
		
	}
}
