package bl;

import android.database.sqlite.SQLiteDatabase;

import org.json.simple.JSONArray;

import java.util.Iterator;
import java.util.List;

import library.salesforce.common.APIData;
import library.salesforce.common.clsHelper;
import library.salesforce.common.linkAPI;
import library.salesforce.common.mCounterNumberData;
import library.salesforce.common.mEmployeeAreaData;
import library.salesforce.common.mEmployeeSalesProductData;
import library.salesforce.common.mconfigData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.enumConfigData;
import library.salesforce.dal.enumCounterData;
import library.salesforce.dal.mCounterNumberDA;
import library.salesforce.dal.mEmployeeAreaDA;
import library.salesforce.dal.mEmployeeSalesProductDA;
import library.salesforce.dal.mconfigDA;
import library.salesforce.dal.tUserLoginDA;

public class mEmployeeAreaBL extends clsMainBL{
	public void saveData(List<mEmployeeAreaData> Listdata){
		SQLiteDatabase db=getDb();
		mEmployeeAreaDA _mEmployeeAreaDA=new mEmployeeAreaDA(db);
		_mEmployeeAreaDA.DeleteAllDataMConfig(db);
		for(mEmployeeAreaData data:Listdata){
			_mEmployeeAreaDA.SaveDataMConfig(db, data);	
		}
		db.close();
	}	
	public mEmployeeAreaData GetAllDataByOutletCode(String idOutlet){
		SQLiteDatabase db=getDb();
		mEmployeeAreaDA _mEmployeeAreaDA=new mEmployeeAreaDA(db);
		mEmployeeAreaData listdata=_mEmployeeAreaDA.getDataByOutlet(db, idOutlet);
		db.close();
		return listdata;
	}
	public List<mEmployeeAreaData> GetAllData(){
		SQLiteDatabase db=getDb();
		mEmployeeAreaDA _mEmployeeAreaDA=new mEmployeeAreaDA(db);
		List<mEmployeeAreaData> lisdata=_mEmployeeAreaDA.getAllData(db);
		db.close();
		return lisdata;
	}
	public void DeleteEmployeeNotInId(String outletCode){
		SQLiteDatabase db=getDb();
		mEmployeeAreaDA _mEmployeeAreaDA=new mEmployeeAreaDA(db);
		List<mEmployeeAreaData> dt = GetAllData();

		for(int i = 0; i < dt.size(); i++){
			if(dt.get(i).get_txtOutletCode().equals(outletCode)){
				continue;
			}
			else{
				new mEmployeeAreaDA(db).deleteContact(db, Integer.parseInt(dt.get(i).get_intID()));
			}
		}
	}
	public JSONArray DownloadEmployeeArea2(String versionName) throws Exception{
		//ambil linkapi Database sqllite
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
		String txtMethod="GetDatavw_SalesInsentive_EmployeeArea";
		dtlinkAPI.set_txtMethod(txtMethod);
		dtlinkAPI.set_txtParam(_dataUserLogin.get_TxtEmpId()+"|||");
		dtlinkAPI.set_txtToken(new clsHardCode().txtTokenAPI);
		dtlinkAPI.set_txtVesion(versionName);
		String strLinkAPI= dtlinkAPI.QueryString(strVal2);
		String JsonData= _help.ResultJsonData(_help.getHTML(strLinkAPI));
		org.json.simple.JSONArray JsonArray= _help.ResultJsonArray(JsonData);
		APIData dtAPIDATA=new APIData();
		//String aa=new clsHelper().linkAPI(db);
//		Iterator i = JsonArray.iterator();
//		Boolean flag=true;
//		String ErrorMess="";
//		mEmployeeSalesProductDA _mEmployeeBranchDA=new mEmployeeSalesProductDA(_db);
//		_mEmployeeBranchDA.DeleteAllDataMConfig(_db);
//		int intsum= _mEmployeeBranchDA.getContactsCount(_db);
//		while (i.hasNext()) {
//			org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
//			int boolValid= Integer.valueOf(String.valueOf( innerObj.get(dtAPIDATA.boolValid)));
//			if(boolValid == Integer.valueOf(new clsHardCode().intSuccess)){
//				intsum+=1;
//				mEmployeeSalesProductData _data =new mEmployeeSalesProductData();
//				//mEmployeeSalesProductData _dataProperty =new mEmployeeSalesProductData();
//				_data.set_intId(String.valueOf(intsum));
//				_data.set_decBobot((String) innerObj.get("DecBobot"));
//				_data.set_decHJD((String) innerObj.get("DecHJD"));
//				_data.set_txtBrandDetailGramCode((String) innerObj.get("TxtBrandDetailGramCode"));
//				_data.set_txtNIK((String) innerObj.get("TxtNIK"));
//				_data.set_txtName((String) innerObj.get("TxtName"));
//				_data.set_txtProductBrandDetailGramName((String) innerObj.get("TxtProductBrandDetailGramName"));
//				_mEmployeeBranchDA.SaveDataMConfig(db, _data);
//			}else{
//				flag=false;
//				ErrorMess=(String) innerObj.get(dtAPIDATA.strMessage);
//				break;
//			}
//		}
//		txtMethod="GetDataNoSalesProduct";
//		dtlinkAPI=new linkAPI();
//		dtlinkAPI.set_txtMethod(txtMethod);
//		dtlinkAPI.set_txtParam(_dataUserLogin.get_txtUserId()+"|||");
//		dtlinkAPI.set_txtToken(new clsHardCode().txtTokenAPI);
//		dtlinkAPI.set_txtVesion(versionName);
//		strLinkAPI= dtlinkAPI.QueryString(strVal2);
//		JsonData= _help.ResultJsonData(_help.getHTML(strLinkAPI));
//		JsonArray= _help.ResultJsonArray(JsonData);
//		dtAPIDATA=new APIData();
//		i = JsonArray.iterator();
//		mCounterNumberDA _mCounterNumberDA=new mCounterNumberDA(_db);
//		while (i.hasNext()) {
//			org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
//			int boolValid= Integer.valueOf(String.valueOf( innerObj.get(dtAPIDATA.boolValid)));
//			if(boolValid == Integer.valueOf(new clsHardCode().intSuccess)){
//				mCounterNumberData _data =new mCounterNumberData();
//				_data.set_intId(enumCounterData.NoDataSO.getidCounterData());
//				_data.set_txtDeskripsi((String) innerObj.get("_pstrMethodRequest"));
//				_data.set_txtName((String) innerObj.get("_pstrMethodRequest"));
//				_data.set_txtValue((String) innerObj.get("_pstrArgument"));
//				_mCounterNumberDA.SaveDataMConfig(db, _data);
//			}else{
//				flag=false;
//				ErrorMess=(String) innerObj.get(dtAPIDATA.strMessage);
//				break;
//			}
//		}
//		_db.close();
		return JsonArray;
	}
	public void DownloadEmployeeArea(String versionName) throws Exception{
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
		String txtMethod="GetDatavw_SalesInsentive_EmployeeArea";
		dtlinkAPI.set_txtMethod(txtMethod);
		dtlinkAPI.set_txtParam(_dataUserLogin.get_TxtEmpId()+"|||");
		dtlinkAPI.set_txtToken(new clsHardCode().txtTokenAPI);
		dtlinkAPI.set_txtVesion(versionName);
		String strLinkAPI= dtlinkAPI.QueryString(strVal2);
		String JsonData= _help.ResultJsonData(_help.getHTML(strLinkAPI));
		org.json.simple.JSONArray JsonArray= _help.ResultJsonArray(JsonData);
		APIData dtAPIDATA=new APIData();
		//String aa=new clsHelper().linkAPI(db);
		Iterator i = JsonArray.iterator();
		Boolean flag=true;
		String ErrorMess="";
		mEmployeeAreaDA _mEmployeeAreaData=new mEmployeeAreaDA(_db);
		_mEmployeeAreaData.DeleteAllDataMConfig(_db);
		while (i.hasNext()) {
			org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
			int boolValid= Integer.valueOf(String.valueOf( innerObj.get(dtAPIDATA.boolValid)));
			if(boolValid == Integer.valueOf(new clsHardCode().intSuccess)){
				mEmployeeAreaData _data =new mEmployeeAreaData();
				_data.set_intBranchId((String) innerObj.get("IntBranchId"));
				_data.set_intChannelId((String) innerObj.get("IntChannelId"));
				_data.set_intEmployeeId((String) innerObj.get("IntEmployeeId"));
				_data.set_intID((String) innerObj.get("IntID"));
				_data.set_intOutletId((String) innerObj.get("IntOutletId"));
				_data.set_intChannelId((String) innerObj.get("IntChannelId"));
				_data.set_intRayonId((String) innerObj.get("IntRayonId"));
				_data.set_intRegionId((String) innerObj.get("IntRegionId"));
				_data.set_txtBranchCode((String) innerObj.get("TxtBranchCode"));
				_data.set_txtBranchName((String) innerObj.get("TxtBranchName"));
				_data.set_txtNIK((String) innerObj.get("TxtNIK"));
				_data.set_txtName((String) innerObj.get("TxtName"));
				_data.set_txtOutletCode((String) innerObj.get("TxtOutletCode"));
				_data.set_txtOutletName((String) innerObj.get("TxtOutletName"));
				_data.set_txtRayonCode((String) innerObj.get("TxtRayonCode"));
				_data.set_txtRayonName((String) innerObj.get("TxtRayonName"));
				_data.set_txtRegionName((String) innerObj.get("TxtRegionName"));
				_mEmployeeAreaData.SaveDataMConfig(db, _data);
				
			}else{
				flag=false;
				ErrorMess=(String) innerObj.get(dtAPIDATA.strMessage);
				break;
			}
		}
		_db.close();
	}
}
