package bl;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.clsHelper;
import library.salesforce.common.linkAPI;
import library.salesforce.common.mTypePenguaranMobileData;
import library.salesforce.common.mconfigData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.enumConfigData;
import library.salesforce.dal.mTypePenguaranMobileDA;
import library.salesforce.dal.mconfigDA;
import library.salesforce.dal.tUserLoginDA;

public class mTypePenguaranMobileBL extends clsMainBL {
	public List<mTypePenguaranMobileData> getData(String id){
		SQLiteDatabase _db=getDb();
		mTypePenguaranMobileDA _mTypePenguaranMobileDA=new mTypePenguaranMobileDA(_db);
		List<mTypePenguaranMobileData> listData=new ArrayList<mTypePenguaranMobileData>();
		if(id.equals("")){
			listData=_mTypePenguaranMobileDA.getAllData(_db);
		}else{
			mTypePenguaranMobileData dt=_mTypePenguaranMobileDA.getData(_db, id);
			listData.add(dt);
		}
		return listData;
	}
	public void saveData(List<mTypePenguaranMobileData> listData){
		SQLiteDatabase _db=getDb();
		mTypePenguaranMobileDA _mTypePenguaranMobileDA=new mTypePenguaranMobileDA(_db);
		for(mTypePenguaranMobileData dt : listData){
			_mTypePenguaranMobileDA.SaveDataMConfig(_db, dt);
		}
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
		String txtMethod="GetDatamTypePenguaranMobile";
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
