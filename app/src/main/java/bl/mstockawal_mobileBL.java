package bl;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.clsHelper;
import library.salesforce.common.linkAPI;
import library.salesforce.common.mStockAwalData;
import library.salesforce.common.mconfigData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.enumConfigData;
import library.salesforce.dal.mStockAwalDA;
import library.salesforce.dal.mconfigDA;
import library.salesforce.dal.tUserLoginDA;

public class mstockawal_mobileBL extends clsMainBL {
	public void saveData(List<mStockAwalData> Listdata){
		SQLiteDatabase db=getDb();
		mStockAwalDA _mStockAwalDA=new mStockAwalDA(db);
		for(mStockAwalData data:Listdata){
			_mStockAwalDA.SaveDataMStockAwalData(db, data);	
		}
	}
	public List<mStockAwalData> getData(String idData){
		SQLiteDatabase db=getDb();
		mStockAwalDA _mStockAwalDA=new mStockAwalDA(db);
		List<mStockAwalData> listData=new ArrayList<mStockAwalData>();
		if(idData.equals("")){
			listData=_mStockAwalDA.getAllData(db);
		}else{
			mStockAwalData dtmStockAwalData=_mStockAwalDA.getData(db, idData);
			listData.add(dtmStockAwalData);
		}
		return listData;
	}
	public org.json.simple.JSONArray DownloadData(String versionName,String txtNIK) throws Exception{
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
		JSONObject resJson = new JSONObject();
		resJson.put("txtNIK", txtNIK);
		tUserLoginData _dataUserLogin = _tUserLoginDA.getData(db, 1);
		clsHelper _help =new clsHelper();
		linkAPI dtlinkAPI=new linkAPI();
		String txtMethod="GetDatamstockawal_mobileByNIK";
		dtlinkAPI.set_txtMethod(txtMethod);
		dtlinkAPI.set_txtParam(_dataUserLogin.get_TxtEmpId()+"|||");
		dtlinkAPI.set_txtToken(new clsHardCode().txtTokenAPI);
		dtlinkAPI.set_txtVesion(versionName);
		String strLinkAPI= dtlinkAPI.QueryString(strVal2);
		//String JsonData= _help.ResultJsonData(_help.getHTML(strLinkAPI));
		String JsonData= _help.pushtData(strLinkAPI,String.valueOf(resJson), Integer.valueOf(getBackGroundServiceOnline()));
		org.json.simple.JSONArray JsonArray= _help.ResultJsonArray(JsonData);
		return JsonArray;
	}
}
