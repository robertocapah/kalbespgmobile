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
import library.salesforce.common.tSalesOrderHeader_MobileData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.enumConfigData;
import library.salesforce.dal.enumCounterData;
import library.salesforce.dal.mCounterNumberDA;
import library.salesforce.dal.mconfigDA;
import library.salesforce.dal.tSalesOrderHeader_MobileDA;
import library.salesforce.dal.tUserLoginDA;

public class tSalesOrderHeader_MobileBL extends clsMainBL {
	public List<tSalesOrderHeader_MobileData> getData(String id){
		SQLiteDatabase db=getDb();
		tSalesOrderHeader_MobileDA _tSalesOrderHeader_MobileDA=new tSalesOrderHeader_MobileDA(db);
		List<tSalesOrderHeader_MobileData> listData=new ArrayList<tSalesOrderHeader_MobileData>();
		if(id.equals("")){
			listData=_tSalesOrderHeader_MobileDA.getAllData(db);
		}else{
			tSalesOrderHeader_MobileData dt=new tSalesOrderHeader_MobileData();
			dt=_tSalesOrderHeader_MobileDA.getData(db, id);
			if(dt!=null){
				listData.add(dt);
			}
		}
		return listData;
	}
	public List<tSalesOrderHeader_MobileData> getDataSubmit(String txtOutletCode){
		SQLiteDatabase db=getDb();
		tSalesOrderHeader_MobileDA _tSalesOrderHeader_MobileDA=new tSalesOrderHeader_MobileDA(db);
		List<tSalesOrderHeader_MobileData> listData=new ArrayList<tSalesOrderHeader_MobileData>();
		listData=_tSalesOrderHeader_MobileDA.getAllDataSubmit(db,txtOutletCode);
		return listData;
	}
	
	public void SaveDataSubmit(String dt){
		SQLiteDatabase db=getDb();
		tSalesOrderHeader_MobileDA _tSalesOrderHeader_MobileDA=new tSalesOrderHeader_MobileDA(db);
		_tSalesOrderHeader_MobileDA.UpdateDataItemForSubmit(db, dt);
	}
	public void saveData(tSalesOrderHeader_MobileData dt){
		SQLiteDatabase db=getDb();
		tSalesOrderHeader_MobileDA _tSalesOrderHeader_MobileDA=new tSalesOrderHeader_MobileDA(db);
		_tSalesOrderHeader_MobileDA.SaveDatatSalesOrderHeader_MobileData(db, dt);
	}
	public void GetDataNotSalesOrderHeader_Mobile(String versionName) throws Exception{
		SQLiteDatabase _db=getDb();
		Boolean flag=true;
		String ErrorMess="";
		String txtMethod="GetDataNotSalesOrderHeader_Mobile";
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
				_data.set_intId(enumCounterData.NoSalesOrder.getidCounterData());
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
