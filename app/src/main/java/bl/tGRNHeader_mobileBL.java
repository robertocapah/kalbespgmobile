package bl;

import java.util.ArrayList;
import java.util.Iterator;

import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.APIData;
import library.salesforce.common.clsHelper;
import library.salesforce.common.linkAPI;
import library.salesforce.common.mCounterNumberData;
import library.salesforce.common.mconfigData;
import library.salesforce.common.tGRNHeader_mobileData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.enumConfigData;
import library.salesforce.dal.enumCounterData;
import library.salesforce.dal.mCounterNumberDA;
import library.salesforce.dal.mconfigDA;
import library.salesforce.dal.tGRNHeader_mobileDA;
import library.salesforce.dal.tUserLoginDA;

public class tGRNHeader_mobileBL extends clsMainBL{
	public ArrayList<tGRNHeader_mobileData> getData(String id){
		tGRNHeader_mobileData dt=new tGRNHeader_mobileData();
		SQLiteDatabase _db=getDb();
		ArrayList<tGRNHeader_mobileData> listData=null;
		tGRNHeader_mobileDA _tGRNHeader_mobileDA=new tGRNHeader_mobileDA(_db);
		if(id.equals("")){
			listData=(ArrayList<tGRNHeader_mobileData>) _tGRNHeader_mobileDA.getAllData(_db);
		}else{
			dt=_tGRNHeader_mobileDA.getData(_db, id);
			if(dt!=null){
				listData=new ArrayList<tGRNHeader_mobileData>();
				listData.add(dt);	
			}
		}
		return listData;
	}
	public ArrayList<tGRNHeader_mobileData> getAllDataSubmit(){
		tGRNHeader_mobileData dt=new tGRNHeader_mobileData();
		SQLiteDatabase _db=getDb();
		ArrayList<tGRNHeader_mobileData> listData=null;
		tGRNHeader_mobileDA _tGRNHeader_mobileDA=new tGRNHeader_mobileDA(_db);
		listData=(ArrayList<tGRNHeader_mobileData>) _tGRNHeader_mobileDA.getAllDataSubmit(_db);
		return listData;
	}
	
	
	public ArrayList<tGRNHeader_mobileData> getAllDataByOutletCode(String outletCode){
		tGRNHeader_mobileData dt=new tGRNHeader_mobileData();
		SQLiteDatabase _db=getDb();
		ArrayList<tGRNHeader_mobileData> listData=null;
		tGRNHeader_mobileDA _tGRNHeader_mobileDA=new tGRNHeader_mobileDA(_db);
		listData=(ArrayList<tGRNHeader_mobileData>) _tGRNHeader_mobileDA.getAllDataByOutletCode(_db, outletCode);
		return listData;
	}
	
	
	public void saveData(tGRNHeader_mobileData dt){
		SQLiteDatabase _db=getDb();
		tGRNHeader_mobileDA _tGRNHeader_mobileDA=new tGRNHeader_mobileDA(_db);
		_tGRNHeader_mobileDA.SaveDatatGRNHeader_mobileData(_db, dt);	
	}
	public void SaveDataSubmit(String Dataid){
		SQLiteDatabase _db=getDb();
		tGRNHeader_mobileDA _tGRNHeader_mobileDA=new tGRNHeader_mobileDA(_db);
		_tGRNHeader_mobileDA.UpdateDataItemForSubmit(_db, Dataid);
	}
	public void GetDataNotGRNHeader_mobile(String versionName) throws Exception{
		
		SQLiteDatabase _db=getDb();
		Boolean flag=true;
		String ErrorMess="";
		String txtMethod="GetDataNotGRNHeader_mobile";
		linkAPI dtlinkAPI=new linkAPI();
		clsHelper _help =new clsHelper();
		dtlinkAPI=new linkAPI();
		dtlinkAPI.set_txtMethod(txtMethod);
		tUserLoginDA _tUserLoginDA=new tUserLoginDA(_db);
		tUserLoginData _dataUserLogin = _tUserLoginDA.getData(_db, 1);
		dtlinkAPI.set_txtParam(_dataUserLogin.get_txtUserId()+"|||");
		dtlinkAPI.set_txtToken(new clsHardCode().txtTokenAPI);
		dtlinkAPI.set_txtVesion(versionName);
		String strVal2="";
		mconfigDA _mconfigDA =new mconfigDA(_db);
		mconfigData dataAPI = _mconfigDA.getData(db,enumConfigData.ApiKalbe.getidConfigData());
		strVal2 = dataAPI.get_txtValue();
		if (dataAPI.get_txtValue() == "") {
			strVal2 = dataAPI.get_txtDefaultValue();
		}
		String strLinkAPI= dtlinkAPI.QueryString(strVal2);
		
		String JsonData= _help.ResultJsonData(_help.getHTML(strLinkAPI));
		org.json.simple.JSONArray JsonArray= _help.ResultJsonArray(JsonData);
		APIData dtAPIDATA=new APIData();
		Iterator i = JsonArray.iterator();
		mCounterNumberDA _mCounterNumberDA=new mCounterNumberDA(_db);
		while (i.hasNext()) {
			org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
			int boolValid= Integer.valueOf(String.valueOf( innerObj.get(dtAPIDATA.boolValid)));
			if(boolValid == Integer.valueOf(new clsHardCode().intSuccess)){
				mCounterNumberData _data =new mCounterNumberData();
				_data.set_intId(enumCounterData.NoGRN.getidCounterData());
				_data.set_txtDeskripsi((String) innerObj.get("_pstrMethodRequest"));
				_data.set_txtName((String) innerObj.get("_pstrMethodRequest"));
				_data.set_txtValue((String) innerObj.get("_pstrArgument"));
				_mCounterNumberDA.SaveDataMConfig(_db, _data);
			}else{
				flag=false;
				ErrorMess=(String) innerObj.get(dtAPIDATA.strMessage);
				break;
			}
		}
	}
}
