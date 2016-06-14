package bl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.APIData;
import library.salesforce.common.clsHelper;
import library.salesforce.common.dataJson;
import library.salesforce.common.linkAPI;
import library.salesforce.common.tPODetail_mobileData;
import library.salesforce.common.tPOHeader_mobileData;
import library.salesforce.common.tPOStatus_mobileData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.tPODetail_mobileDA;
import library.salesforce.dal.tPOStatus_mobileDA;
import library.salesforce.dal.tUserLoginDA;


public class tPODetail_mobileBL extends clsMainBL{
	public ArrayList<tPODetail_mobileData> getData(String id){
		SQLiteDatabase db=getDb();
		List<tPODetail_mobileData> listData=new ArrayList<tPODetail_mobileData>();
		tPODetail_mobileDA _tPODetail_MobileDA=new tPODetail_mobileDA(db);
		if(id.equals("")){
			listData=_tPODetail_MobileDA.getAllData(db);
			if(listData!=null){
				listData=new ArrayList<tPODetail_mobileData>();
			}
		}else{
			tPODetail_mobileData data=new tPODetail_mobileData();
			data=_tPODetail_MobileDA.getData(db, id);
			listData.add(data);
		}
		return (ArrayList<tPODetail_mobileData>) listData;
	}

	public tPODetail_mobileData getDataBYHeaderAndProduct2(String idPO,String IdProduct){
		SQLiteDatabase db=getDb();
		tPODetail_mobileData data=new tPODetail_mobileData();
		tPODetail_mobileDA _tPODetail_MobileDA=new tPODetail_mobileDA(db);
		data=_tPODetail_MobileDA.getDataBYHeaderAndProduct2(db,idPO,IdProduct);
		
		return data;
	}
	public ArrayList<tPODetail_mobileData> getDataByBitactive(String id){
		SQLiteDatabase db=getDb();
		List<tPODetail_mobileData> listData=new ArrayList<tPODetail_mobileData>();
		tPODetail_mobileDA _tPODetail_MobileDA=new tPODetail_mobileDA(db);
		listData=_tPODetail_MobileDA.getAllDataByHeaderId2(db, id);
		return (ArrayList<tPODetail_mobileData>) listData;
	}
	
	public tPODetail_mobileData getDataBYHeaderAndProduct(String idPO,String IdProduct){
		SQLiteDatabase db=getDb();
		tPODetail_mobileData data=new tPODetail_mobileData();
		tPODetail_mobileDA _tPODetail_MobileDA=new tPODetail_mobileDA(db);
		data=_tPODetail_MobileDA.getDataBYHeaderAndProduct(db,idPO,IdProduct);
		
		return data;
	}
	public void UpdateDataItem(tPODetail_mobileData _data){
		SQLiteDatabase db=getDb();
		tPODetail_mobileDA _tPODetail_MobileDA=new tPODetail_mobileDA(db);
		_tPODetail_MobileDA.UpdateDataItem(db, _data);
	}
	
	public ArrayList<tPODetail_mobileData> getDataByHeaderId(String id){
		SQLiteDatabase db=getDb();
		List<tPODetail_mobileData> listData=new ArrayList<tPODetail_mobileData>();
		tPODetail_mobileDA _tPODetail_MobileDA=new tPODetail_mobileDA(db);
		listData=_tPODetail_MobileDA.getAllDataByHeaderId(db, id);
		return (ArrayList<tPODetail_mobileData>) listData;
	}
	public void  SaveData(tPODetail_mobileData dt){
		SQLiteDatabase db=getDb();
		tPODetail_mobileDA _tPODetail_MobileDA=new tPODetail_mobileDA(db);
		_tPODetail_MobileDA.SaveDatatPODetail_mobileData(db, dt);
	}
	public void  SaveDataAll(List<tPODetail_mobileData> dt){
		SQLiteDatabase db=getDb();
		tPODetail_mobileDA _tPODetail_MobileDA=new tPODetail_mobileDA(db);
		for (tPODetail_mobileData tPODetail_mobileData : dt) {
			_tPODetail_MobileDA.SaveDatatPODetail_mobileData(db, tPODetail_mobileData);	
		}
		
	}
	public void  DeleteData(tPODetail_mobileData dt){
		SQLiteDatabase db=getDb();
		tPODetail_mobileDA _tPODetail_MobileDA=new tPODetail_mobileDA(db);
		_tPODetail_MobileDA.deleteContact(db, dt.get_txtDataId());
	}
	public void SaveDataSubmit(ArrayList<tPODetail_mobileData> listOftPODetail_MobileData){
		SQLiteDatabase db=getDb();
		tPODetail_mobileDA _tPODetail_MobileDA=new tPODetail_mobileDA(db);
		for(tPODetail_mobileData dt : listOftPODetail_MobileData){
			_tPODetail_MobileDA.UpdateDataItemForSubmit(db, dt.get_txtDataId());	
		}
		
	}
	public void SaveDataSync(ArrayList<tPODetail_mobileData> listOftPODetail_MobileData){
		SQLiteDatabase db=getDb();
		tPODetail_mobileDA _tPODetail_MobileDA=new tPODetail_mobileDA(db);
		for(tPODetail_mobileData dt : listOftPODetail_MobileData){
			_tPODetail_MobileDA.UpdateDataItemForSync(db, dt.get_txtDataId());	
		}
		
	}
	public dataJson getPODetail(String branchCode,String outletCode, String noPO, String versionApp) throws ParseException{
		SQLiteDatabase _db=getDb();
		tUserLoginDA _tUserLoginDA=new tUserLoginDA(_db);
		List<tPOHeader_mobileData> ListdataHeader = new ArrayList<tPOHeader_mobileData>();
		List<tPOStatus_mobileData> ListdataStatus = new ArrayList<tPOStatus_mobileData>();
		List<tPODetail_mobileData> Listdata = new ArrayList<tPODetail_mobileData>();
		linkAPI dtlinkAPI=new linkAPI();
		dataJson dtdataJson=new dataJson();
		String txtMethod="GetDataPOByCabang"; 
		JSONObject resJson = new JSONObject();
		tUserLoginData _dataUserLogin = _tUserLoginDA.getData(_db, 1);
		resJson.put("txtBranchCode", branchCode);
		resJson.put("txtOutletCode", outletCode);
		resJson.put("txtNOPO", noPO);
		resJson.put("User", _dataUserLogin.get_txtUserId());
		resJson.put("txtDeviceId", _dataUserLogin.get_txtDeviceId());
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
				org.json.simple.JSONArray JsonArray_header= _clsHelper.ResultJsonArray(String.valueOf(innerObj.get("ListOfclsTPOHeader_mobile")));
				Iterator j = JsonArray_header.iterator();
				int index = 0;
				while(j.hasNext()){
					tPOHeader_mobileData _data =new tPOHeader_mobileData();
					org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) j.next();
					_data.set_dtDate(String.valueOf(innerObj_detail.get("_dtDate")));
					_data.set_intStockAwal(String.valueOf(innerObj_detail.get("_intStockAwal")));
					_data.set_intSubmit("1");
					_data.set_intSync("1");
					_data.set_txtBranchCode(String.valueOf(innerObj_detail.get("_txtBranchName")));
					_data.set_txtOutletCode(String.valueOf(innerObj_detail.get("_txtOutletCode")));
					_data.set_txtOutletName(String.valueOf(innerObj_detail.get("_txtOutletName")));
					_data.set_txtDesc("");
					_data.set_txtDeviceId(String.valueOf(innerObj_detail.get("_txtDeviceId")));
					_data.set_txtNOMO(String.valueOf(innerObj_detail.get("_txtNOMO")));
					_data.set_txtNoPO(String.valueOf(innerObj_detail.get("_txtNoPO")));
					_data.set_txtUserId(String.valueOf(innerObj_detail.get("_txtUserId")));
					ListdataHeader.add(_data);
				}
				
				org.json.simple.JSONArray JsonArray_detail= _clsHelper.ResultJsonArray(String.valueOf(innerObj.get("ListOfclsTPODetail_mobile")));
				j = JsonArray_detail.iterator();
				index = 0;
				while(j.hasNext()){
					tPODetail_mobileData _data =new tPODetail_mobileData();
					org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) j.next();
					_data.set_dtED(String.valueOf(""));
					_data.set_intProductCode(String.valueOf(innerObj_detail.get("_intProductCode")));
					_data.set_intQty(String.valueOf(innerObj_detail.get("_intQty")));
					_data.set_intQtyGRN(String.valueOf(innerObj_detail.get("_intQtyGRN")));
					_data.set_intQtySisa(String.valueOf(innerObj_detail.get("_intQtySisa")));
					_data.set_intStockAwal(String.valueOf(innerObj_detail.get("_intStockAwal")));
					_data.set_intSubmit("1");
					_data.set_intSync("1");
					_data.set_txtBatchNo("");
					_data.set_txtDataId(String.valueOf(innerObj_detail.get("_txtDataId")));
					_data.set_txtNoDoc(String.valueOf(innerObj_detail.get("_txtNoDoc")));
					_data.set_txtNoPO(String.valueOf(innerObj_detail.get("_txtNoPO")));
					_data.set_txtProductName(String.valueOf(innerObj_detail.get("_txtProductName")));
					Listdata.add(_data);
				}
				tPOStatus_mobileDA _tPOStatus_mobileDA=new tPOStatus_mobileDA(_db);
				org.json.simple.JSONArray JsonArray_Status= _clsHelper.ResultJsonArray(String.valueOf(innerObj.get("ListOfclsTPOStatus_mobile")));
				j = JsonArray_Status.iterator();
				index = 0;
				while(j.hasNext()){
					tPOStatus_mobileData _data =new tPOStatus_mobileData();
					org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) j.next();
					_data.set_intBitActive("1");
					_data.set_intStatus(String.valueOf(innerObj_detail.get("_intStatus")));
					_data.set_intSubmit("1");
					_data.set_intSync("1");
					_data.set_txtDataId(_tPOStatus_mobileDA.getContactsCount(_db)+1+"");
					_data.set_txtNoDoc(String.valueOf(innerObj_detail.get("_txtNoDoc")));
					_data.set_txtNoPO(String.valueOf(innerObj_detail.get("_txtNoPO")));
					_data.set_txtStatus(String.valueOf(innerObj_detail.get("_txtStatus")));
					ListdataStatus.add(_data);
				}
			}
		}
		dtdataJson.set_ListOftPODetail_mobileData(Listdata);
		dtdataJson.set_ListOftPOHeader_mobileData(ListdataHeader);
		dtdataJson.set_ListOftPOStatus_mobileData(ListdataStatus);
		return dtdataJson;
	}
}
