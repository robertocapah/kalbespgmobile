package bl;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import library.salesforce.common.clsHelper;
import library.salesforce.common.linkAPI;
import library.salesforce.common.mTypeLeaveMobileData;
import library.salesforce.common.mconfigData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.enumConfigData;
import library.salesforce.dal.mTypeLeaveMobileDA;
import library.salesforce.dal.mconfigDA;
import library.salesforce.dal.tUserLoginDA;

public class mTypeLeaveBL extends clsMainBL{

	public void saveData(List<mTypeLeaveMobileData> Listdata){
		SQLiteDatabase db=getDb();
		mTypeLeaveMobileDA _mTypeLeaveMobileDA=new mTypeLeaveMobileDA(db);
		_mTypeLeaveMobileDA.DeleteAllData(db);
		for(mTypeLeaveMobileData data:Listdata){
			_mTypeLeaveMobileDA.SaveData(db, data);	
		}
	}
	
	public List<mTypeLeaveMobileData> GetAllData(){
		SQLiteDatabase db=getDb();
		mTypeLeaveMobileDA _TypeLeaveMobileDA = new mTypeLeaveMobileDA(db);
		List<mTypeLeaveMobileData> listData = _TypeLeaveMobileDA.getAllData(db);
		return listData;
	}

	public mTypeLeaveMobileData GetDataByintTypeLeave(String id){
		SQLiteDatabase db=getDb();
		mTypeLeaveMobileDA _TypeLeaveMobileDA = new mTypeLeaveMobileDA(db);
		mTypeLeaveMobileData listData = _TypeLeaveMobileDA.getData(db, id);
		return listData;
	}

	public int  getContactsCount(){
		SQLiteDatabase db=getDb();
		mTypeLeaveMobileDA _mTypeLeaveMobileDA=new mTypeLeaveMobileDA(db);
		return _mTypeLeaveMobileDA.getContactsCount(db);
	}
	
	public org.json.simple.JSONArray DownloadTypeLeave2(String versionName) throws Exception{
		//ambil linkapi Database sqllite
		SQLiteDatabase _db=getDb();
		tUserLoginDA _tUserLoginDA=new tUserLoginDA(_db);
		mconfigDA _mconfigDA =new mconfigDA(_db);
		
		String strVal2="";
		mconfigData dataAPI = _mconfigDA.getData(_db,enumConfigData.ApiKalbe.getidConfigData());
		strVal2 = dataAPI.get_txtValue();
		if (dataAPI.get_txtValue() == "") {
			strVal2 = dataAPI.get_txtDefaultValue();
		}
		//ambil version dari webservices
		tUserLoginData _dataUserLogin = _tUserLoginDA.getData(_db, 1);
		clsHelper _help =new clsHelper();
		linkAPI dtlinkAPI=new linkAPI();
		String txtMethod="GetDatamTypeLeaveMobile";
		dtlinkAPI.set_txtMethod(txtMethod);
		dtlinkAPI.set_txtParam(_dataUserLogin.get_TxtEmpId()+"|||");
		dtlinkAPI.set_txtToken(new clsHardCode().txtTokenAPI);
		dtlinkAPI.set_txtVesion(versionName);
		String strLinkAPI= dtlinkAPI.QueryString(strVal2);
		String JsonData= _help.ResultJsonData(_help.getHTML(strLinkAPI));
		org.json.simple.JSONArray JsonArray= _help.ResultJsonArray(JsonData);
		return JsonArray;
	}

}
