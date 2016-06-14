package bl;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.clsHelper;
import library.salesforce.common.linkAPI;
import library.salesforce.common.mStatusDocumentData;
import library.salesforce.common.mconfigData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.enumConfigData;
import library.salesforce.dal.mStatusDocumentDA;
import library.salesforce.dal.mconfigDA;
import library.salesforce.dal.tUserLoginDA;

public class mStatusDocumentBL extends clsMainBL {
	public void saveData(List<mStatusDocumentData> listData){
		SQLiteDatabase _db=getDb();
		mStatusDocumentDA _mStatusDocumentDA= new mStatusDocumentDA(_db);
		for (mStatusDocumentData dt : listData) {
			_mStatusDocumentDA.SaveDataMConfig(_db, dt);
		} 
	}
	public List<mStatusDocumentData> getData(String id){
		SQLiteDatabase _db=getDb();
		mStatusDocumentDA _mStatusDocumentDA=new mStatusDocumentDA(_db);
		List<mStatusDocumentData> listData=new ArrayList<mStatusDocumentData>();
		if(id.equals("")){
			listData=_mStatusDocumentDA.getAllData(_db);
		}else{
			mStatusDocumentData dt=_mStatusDocumentDA.getData(_db, id);
			listData.add(dt);
		}
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
		String txtMethod="GetDatamStatusDocument";
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
