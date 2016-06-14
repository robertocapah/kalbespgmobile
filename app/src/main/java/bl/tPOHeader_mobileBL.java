package bl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import android.database.sqlite.SQLiteDatabase;

import library.salesforce.common.APIData;
import library.salesforce.common.clsHelper;
import library.salesforce.common.linkAPI;
import library.salesforce.common.mCounterNumberData;
import library.salesforce.common.mconfigData;
import library.salesforce.common.tPOHeader_mobileData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.enumConfigData;
import library.salesforce.dal.enumCounterData;
import library.salesforce.dal.mCounterNumberDA;
import library.salesforce.dal.mconfigDA;
import library.salesforce.dal.tPOHeader_mobileDA;
import library.salesforce.dal.tUserLoginDA;
public class tPOHeader_mobileBL extends clsMainBL {
	public List<tPOHeader_mobileData> getData(String id){
		SQLiteDatabase db=getDb();
		tPOHeader_mobileDA _tPOHeader_mobileDA=new tPOHeader_mobileDA(db);
		List<tPOHeader_mobileData> listData=new ArrayList<tPOHeader_mobileData>();
		if(id.equals("")){
			listData=_tPOHeader_mobileDA.getAllData(db);
		}else{
			tPOHeader_mobileData dt=new tPOHeader_mobileData();
			dt=_tPOHeader_mobileDA.getData(db, id);
			if(dt!=null){
				listData.add(dt);
			}
		}
		return listData;
	}
	public List<tPOHeader_mobileData> getDataNonOutstanding(String outletCode){
		SQLiteDatabase db=getDb();
		tPOHeader_mobileDA _tPOHeader_mobileDA=new tPOHeader_mobileDA(db);
		List<tPOHeader_mobileData> listData=new ArrayList<tPOHeader_mobileData>();
		listData=_tPOHeader_mobileDA.GetPONonOutStanding(db, outletCode);
		return listData;
	}
	public List<tPOHeader_mobileData> getDataByOutletCode(String OutletCOde){
		SQLiteDatabase db=getDb();
		tPOHeader_mobileDA _tPOHeader_mobileDA=new tPOHeader_mobileDA(db);
		List<tPOHeader_mobileData> listData=new ArrayList<tPOHeader_mobileData>();
		listData=_tPOHeader_mobileDA.getAllDataByOutletCode(db,OutletCOde);
		return listData;
	}
	public List<tPOHeader_mobileData> getDataSubmit(){
		SQLiteDatabase db=getDb();
		tPOHeader_mobileDA _tPOHeader_mobileDA=new tPOHeader_mobileDA(db);
		List<tPOHeader_mobileData> listData=new ArrayList<tPOHeader_mobileData>();
		listData=_tPOHeader_mobileDA.getAllDataSubmit(db);
		return listData;
	}
	public void SaveDataSync(String dataid){
		SQLiteDatabase db=getDb();
		tPOHeader_mobileDA _tPOHeader_mobileDA=new tPOHeader_mobileDA(db);
		_tPOHeader_mobileDA.UpdateDataItemForSyncByHeaderId(db, dataid);
	}
	public void SaveDataSubmit(String dataid){
		SQLiteDatabase db=getDb();
		tPOHeader_mobileDA _tPOHeader_mobileDA=new tPOHeader_mobileDA(db);
		_tPOHeader_mobileDA.UpdateDataItemForSubmitByHeaderId(db, dataid);
	}
	public void saveDataAll(List<tPOHeader_mobileData> listdt){
		SQLiteDatabase db=getDb();
		tPOHeader_mobileDA _tPOHeader_mobileDA=new tPOHeader_mobileDA(db);
		for (tPOHeader_mobileData tPOHeader_mobileData : listdt) {
			_tPOHeader_mobileDA.SaveDatatPOHeader_mobileData(db, tPOHeader_mobileData);	
		}
	}
	public void saveData(tPOHeader_mobileData dt){
		SQLiteDatabase db=getDb();
		tPOHeader_mobileDA _tPOHeader_mobileDA=new tPOHeader_mobileDA(db);
		_tPOHeader_mobileDA.SaveDatatPOHeader_mobileData(db, dt);
	}
	public void GetDataNoPurchaseOrder(String versionName) throws Exception{
		SQLiteDatabase _db=getDb();
		Boolean flag=true;
		String ErrorMess="";
		String txtMethod="GetDataNoPurchaseOrder";
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
				_data.set_intId(enumCounterData.NoPurchaseOrder.getidCounterData());
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
	public List<tPOHeader_mobileData> getListPO(String branchCode,String outletCode, String versionApp) throws ParseException{
		SQLiteDatabase _db=getDb();
		tUserLoginDA _tUserLoginDA=new tUserLoginDA(_db);
		List<tPOHeader_mobileData> Listdata=new ArrayList<tPOHeader_mobileData>();
		linkAPI dtlinkAPI=new linkAPI();
		String txtMethod="GetDataPOHeaderByCabang";
		JSONObject resJson = new JSONObject();
		tUserLoginData _dataUserLogin = _tUserLoginDA.getData(_db, 1);
		resJson.put("txtBranchCode", branchCode);
		resJson.put("txtOutletCode", outletCode);
		resJson.put("txtNOPO", "");
		resJson.put("User", _dataUserLogin.get_txtUserId());
		dtlinkAPI.set_txtMethod(txtMethod);
		dtlinkAPI.set_txtParam(null);
		dtlinkAPI.set_txtToken(new clsHardCode().txtTokenAPI);
		dtlinkAPI.set_txtVesion(versionApp);
		String strLinkAPI= dtlinkAPI.QueryString(getLinkAPI());
		APIData dtAPIDATA=new APIData();
		clsHelper _clsHelper=new clsHelper();
		String JsonData= _clsHelper.pushtData(strLinkAPI,String.valueOf(resJson), Integer.valueOf(getBackGroundServiceOnline()));
		org.json.simple.JSONArray JsonArray= _clsHelper.ResultJsonArray(JsonData);
		Iterator i = JsonArray.iterator();
		
		while (i.hasNext()) {
			org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
			int boolValid= Integer.valueOf(String.valueOf( innerObj.get(dtAPIDATA.boolValid)));
			if(boolValid == Integer.valueOf(new clsHardCode().intSuccess)){
				org.json.simple.JSONArray JsonArray_detail= _clsHelper.ResultJsonArray(String.valueOf(innerObj.get("ListOfclsTPOHeader_mobile")));
				Iterator j = JsonArray_detail.iterator();
				while(j.hasNext()){
					tPOHeader_mobileData _data =new tPOHeader_mobileData();
					org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) j.next();
					_data.set_txtNoPO(String.valueOf(innerObj_detail.get("_txtNoPO")));
					_data.set_intStockAwal(String.valueOf(innerObj_detail.get("_intStockAwal")));
					_data.set_txtNOMO(String.valueOf(innerObj_detail.get("_txtNOMO")));
					Listdata.add(_data);
				}
				
			}
		}
		
		return Listdata;
	}
	
	public List<tPOHeader_mobileData> changeUserPO(String noPO, String noGRN, String dtDate, String userId, String deviceId, String versionApp) throws ParseException{
		List<tPOHeader_mobileData> Listdata=new ArrayList<tPOHeader_mobileData>();
		linkAPI dtlinkAPI=new linkAPI();
		String txtMethod="ChangeUserIdtpoheader_mobile";
		JSONObject resJson = new JSONObject();
		resJson.put("txtNoPO", noPO);
		resJson.put("txtNoGRN", noGRN);
		resJson.put("dtDate", dtDate);
		resJson.put("txtUserId", userId);
		resJson.put("txtDeviceId", deviceId);
		dtlinkAPI.set_txtMethod(txtMethod);
		dtlinkAPI.set_txtParam(null);
		dtlinkAPI.set_txtToken(new clsHardCode().txtTokenAPI);
		dtlinkAPI.set_txtVesion(versionApp);
		String strLinkAPI= dtlinkAPI.QueryString(getLinkAPI());
		APIData dtAPIDATA=new APIData();
		clsHelper _clsHelper=new clsHelper();
		String JsonData= _clsHelper.pushtData(strLinkAPI,String.valueOf(resJson), Integer.valueOf(getBackGroundServiceOnline()));
		org.json.simple.JSONArray JsonArray= _clsHelper.ResultJsonArray(JsonData);
		Iterator i = JsonArray.iterator();
		
		while (i.hasNext()) {
			org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
			int boolValid= Integer.valueOf(String.valueOf( innerObj.get(dtAPIDATA.boolValid)));
			if(boolValid == Integer.valueOf(new clsHardCode().intSuccess)){
				
				tPOHeader_mobileData _data =new tPOHeader_mobileData();
				_data.set_intSubmit(String.valueOf( innerObj.get("_pboolValid")));
				_data.set_txtUserId(String.valueOf( innerObj.get("_pstrMessage")));
				Listdata.add(_data);
			}
		}
		return Listdata;
	}
}
