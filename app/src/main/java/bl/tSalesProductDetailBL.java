package bl;

import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import library.salesforce.common.APIData;
import library.salesforce.common.clsHelper;
import library.salesforce.common.linkAPI;
import library.salesforce.common.mconfigData;
import library.salesforce.common.tSalesProductDetailData;
import library.salesforce.common.tSalesProductHeaderData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.enumConfigData;
import library.salesforce.dal.mconfigDA;
import library.salesforce.dal.tSalesProductDetailDA;
import library.salesforce.dal.tSalesProductHeaderDA;
import library.salesforce.dal.tUserLoginDA;

public class tSalesProductDetailBL extends clsMainBL {
	public List<tSalesProductDetailData> GetDataByNoSO(String Noso){
		SQLiteDatabase db=getDb();
		//mEmployeeSalesProductDA _mEmployeeSalesProductDA= new mEmployeeSalesProductDA(db);
		tSalesProductDetailDA _tSalesProductDetailDA= new tSalesProductDetailDA(db);
		//List<mEmployeeSalesProductData>ListData=_mEmployeeSalesProductDA.SearchData(db, "", Noso);
		List<tSalesProductDetailData>ListData=_tSalesProductDetailDA.getDataByNoSO(db,Noso);
		db.close();
		return ListData;
	}
	public void DownloadEmployeeTransaction(String versionName) throws Exception{
		//ambil linkapi Database sqllite
		SQLiteDatabase _db=getDb();
		tSalesProductDetailDA _tSalesProductDetailDA=new tSalesProductDetailDA(_db);
		tSalesProductHeaderDA _tSalesProductHeaderDA=new tSalesProductHeaderDA(_db);
		tUserLoginDA _tUserLoginDA=new tUserLoginDA(_db);
		mconfigDA _mconfigDA =new mconfigDA(_db);
		tUserLoginData _dataUserLogin = _tUserLoginDA.getData(_db, 1);
		String strVal2="";
		mconfigData dataAPI = _mconfigDA.getData(db,enumConfigData.ApiKalbe.getidConfigData());
		strVal2 = dataAPI.get_txtValue();
		if (dataAPI.get_txtValue() == "") {
			strVal2 = dataAPI.get_txtDefaultValue();
		}
		//ambil version dari webservices
		
		clsHelper _help =new clsHelper();
		linkAPI dtlinkAPI=new linkAPI();
		String txtMethod="GetDataTransactionReso";
		dtlinkAPI.set_txtMethod(txtMethod);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		dtlinkAPI.set_txtParam("|"+_dataUserLogin.get_TxtEmpId()+"|"+dateFormat.format(cal.getTime()));
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
		Long intData= _tSalesProductDetailDA.getContactsCount(db);
		while (i.hasNext()) {
			org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
			int boolValid= Integer.valueOf(String.valueOf( innerObj.get(dtAPIDATA.boolValid)));
			if(boolValid == Integer.valueOf(new clsHardCode().intSuccess)){
				org.json.simple.JSONArray JsonArrayDetail= _help.ResultJsonArray(String.valueOf(innerObj.get("ListDatatSalesProductDetail_mobile")));
				org.json.simple.JSONArray JsonArrayHeader= _help.ResultJsonArray(String.valueOf(innerObj.get("ListDatatSalesProductHeader_mobile")));
				Iterator iDetail = JsonArrayDetail.iterator();
				Iterator iHeader = JsonArrayHeader.iterator();
				while (iHeader.hasNext()) {
					org.json.simple.JSONObject innerDetailObj = (org.json.simple.JSONObject) iHeader.next();
					_tSalesProductHeaderDA.deleteContact(db, String.valueOf(innerDetailObj.get("TxtNoSO")));
					_tSalesProductDetailDA.deleteByNOSO(db, String.valueOf(innerDetailObj.get("TxtNoSO")));
					tSalesProductHeaderData _data =new tSalesProductHeaderData();
					_data.set_dtDate(dateFormat.format(cal.getTime()));
					_data.set_intId(String.valueOf(innerDetailObj.get("TxtNoSO")));
					_data.set_intIdAbsenUser(String.valueOf(_dataUserLogin.get_intId()));
					_data.set_intSubmit("1");
					_data.set_intSumAmount(String.valueOf(innerDetailObj.get("IntSumAmount")));
					_data.set_intSumItem(String.valueOf(innerDetailObj.get("IntSumItem")));
					_data.set_intSync("1");
					_data.set_OutletCode(String.valueOf(innerDetailObj.get("TxtOutletCode")));
					_data.set_OutletName(String.valueOf(innerDetailObj.get("TxtOutletName")));
					_data.set_txtBranchCode(String.valueOf(innerDetailObj.get("TxtBranchCode")));
					_data.set_txtBranchName(String.valueOf(innerDetailObj.get("TxtBranchName")));
					_data.set_txtKeterangan(String.valueOf(innerDetailObj.get("TxtKeterangan")));
					_data.set_txtNIK((String) _dataUserLogin.get_TxtEmpId());
					_data.set_UserId((String) _dataUserLogin.get_txtUserId());
					_tSalesProductHeaderDA.SaveDatatSalesProductHeaderData(db, _data);;
				}
				while (iDetail.hasNext()) {
					intData+=1;
					org.json.simple.JSONObject innerDetailObj = (org.json.simple.JSONObject) iDetail.next();
					tSalesProductDetailData _data =new tSalesProductDetailData();
					String intIdDetail =String.valueOf(intData);
					_data.set_intId(String.valueOf(intIdDetail));
					_data.set_dtDate(dateFormat.format(cal.getTime()));
					_data.set_intPrice((String) innerDetailObj.get("IntPrice"));
					_data.set_intQty((String) innerDetailObj.get("IntQty"));
					_data.set_txtCodeProduct((String) innerDetailObj.get("TxtCodeProduct"));
					_data.set_txtKeterangan("");
					_data.set_txtNameProduct((String) innerDetailObj.get("TxtNameProduct"));
					_data.set_txtNoSo((String) innerDetailObj.get("TxtNoSO"));
					_data.set_intActive("1");
					_data.set_txtNIK(_dataUserLogin.get_TxtEmpId());
					_data.set_intTotal((String) innerDetailObj.get("IntTotal"));
					_tSalesProductDetailDA.SaveDatatSalesProductDetailData(db, _data);
				}
				
			}else{
				flag=false;
				ErrorMess=(String) innerObj.get(dtAPIDATA.strMessage);
				break;
			}
		}
	}
}
