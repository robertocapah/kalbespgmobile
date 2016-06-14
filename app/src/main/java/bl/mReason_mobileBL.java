package bl;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.clsHelper;
import library.salesforce.common.linkAPI;
import library.salesforce.common.mReasonData;
import library.salesforce.common.mconfigData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.enumConfigData;
import library.salesforce.dal.mReasonDA;
import library.salesforce.dal.mconfigDA;
import library.salesforce.dal.tUserLoginDA;

public class mReason_mobileBL extends clsMainBL {
	public void saveData(List<mReasonData> listData){
		SQLiteDatabase _db=getDb();
		mReasonDA _mReasonDA=new mReasonDA(_db);
		for(mReasonData dt : listData){
			_mReasonDA.SaveDataMReasonData(_db, dt);
		}
	}
	public List<mReasonData> getDataByTxtReason(String txtReason){
		SQLiteDatabase _db=getDb();
		mReasonDA _mReasonDA=new mReasonDA(_db);
		List<mReasonData> listData=new ArrayList<mReasonData>();
		listData=_mReasonDA.getAllDataByTxtReason2(_db,txtReason);
		return listData;
	}
	
	public List<mReasonData> getIdByTxtReason(String txtType, String txtReason){
		SQLiteDatabase _db=getDb();
		mReasonDA _mReasonDA=new mReasonDA(_db);
		List<mReasonData> listData=new ArrayList<mReasonData>();
		listData=_mReasonDA.getIdByTxtReason(_db, txtType, txtReason);
		return listData;
	}
	public List<mReasonData> getData(String idData){
		SQLiteDatabase _db=getDb();
		mReasonDA _mReasonDA=new mReasonDA(_db);
		List<mReasonData> listData=new ArrayList<mReasonData>();
		if(idData.equals("")){
			listData=_mReasonDA.getAllData(_db);
		}else{
			mReasonData dtmReasonData=new mReasonData();
			dtmReasonData=_mReasonDA.getData(_db, idData);
			listData.add(dtmReasonData);
		}
		return listData;
	}
	public List<mReasonData> getDataByTxtType(String txtType){
		SQLiteDatabase _db=getDb();
		mReasonDA _mReasonDA=new mReasonDA(_db);
		List<mReasonData> listData=new ArrayList<mReasonData>();
		listData=_mReasonDA.getAllDataByTxtReason(_db,txtType);
		return listData;
	}
	public org.json.simple.JSONArray DownloadData(String versionName) throws Exception{
		SQLiteDatabase _db=getDb();
		tUserLoginDA _tUserLoginDA=new tUserLoginDA(_db);
		mconfigDA _mconfigDA =new mconfigDA(_db);
		
		String strVal2="";
		mconfigData dataAPI = _mconfigDA.getData(db,enumConfigData.ApiKalbe.getidConfigData());
		strVal2 = dataAPI.get_txtValue();
		if (dataAPI.get_txtValue() == "") {
			strVal2 = dataAPI.get_txtDefaultValue();
		}
		//ambil version dari webservices
		tUserLoginData _dataUserLogin = _tUserLoginDA.getData(db, 1);
		clsHelper _help =new clsHelper();
		linkAPI dtlinkAPI=new linkAPI();
		String txtMethod="GetDatamReason_mobile";
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
