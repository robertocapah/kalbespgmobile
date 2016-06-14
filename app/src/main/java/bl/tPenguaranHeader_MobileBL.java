package bl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.APIData;
import library.salesforce.common.clsHelper;
import library.salesforce.common.linkAPI;
import library.salesforce.common.mCounterNumberData;
import library.salesforce.common.mconfigData;
import library.salesforce.common.tPenguaranHeader_MobileData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.enumConfigData;
import library.salesforce.dal.enumCounterData;
import library.salesforce.dal.mCounterNumberDA;
import library.salesforce.dal.mconfigDA;
import library.salesforce.dal.tPenguaranHeader_MobileDA;
import library.salesforce.dal.tUserLoginDA;

public class tPenguaranHeader_MobileBL extends clsMainBL {
	public void GetDatamTypePenguaranMobile(String versionName) throws Exception{
		
		SQLiteDatabase _db=getDb();
		Boolean flag=true;
		String ErrorMess="";
		String txtMethod="GetDataNotPenguaranHeader_Mobile";
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
				_data.set_intId(enumCounterData.NoPenguaran.getidCounterData());
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
	
	public ArrayList<tPenguaranHeader_MobileData> getData(String id){
		SQLiteDatabase db=getDb();
		List<tPenguaranHeader_MobileData> listData=new ArrayList<tPenguaranHeader_MobileData>();
		tPenguaranHeader_MobileDA _tPenguaranHeader_MobileDA=new tPenguaranHeader_MobileDA(db);
		if(id.equals("")){
			listData=_tPenguaranHeader_MobileDA.getAllData(db);
		}else{
			tPenguaranHeader_MobileData data=new tPenguaranHeader_MobileData();
			data=_tPenguaranHeader_MobileDA.getData(db, id);
			listData.add(data);
		}
		return (ArrayList<tPenguaranHeader_MobileData>) listData;
	}
	
	public void saveData(tPenguaranHeader_MobileData dt){
		SQLiteDatabase _db=getDb();
		tPenguaranHeader_MobileDA _tPenguaranHeader_MobileDA=new tPenguaranHeader_MobileDA(_db);
		_tPenguaranHeader_MobileDA.SaveDatatSalesProductHeaderData(_db, dt);	
	}
	
	
	public List<tPenguaranHeader_MobileData> getDataSubmit(String txtOutletCode){
		SQLiteDatabase db=getDb();
		tPenguaranHeader_MobileDA _tPenguaranHeader_MobileDA=new tPenguaranHeader_MobileDA(db);
		List<tPenguaranHeader_MobileData> listData=new ArrayList<tPenguaranHeader_MobileData>();
		listData=_tPenguaranHeader_MobileDA.getAllDataSubmit(db,txtOutletCode);
		return listData;
	}
	
}
