package bl;

import android.database.sqlite.SQLiteDatabase;

import com.kalbenutritionals.app.kalbespgmobile.clsMainActivity;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import library.salesforce.common.APIData;
import library.salesforce.common.clsHelper;
import library.salesforce.common.linkAPI;
import library.salesforce.common.mCounterNumberData;
import library.salesforce.common.mItemSalesPack_StockData;
import library.salesforce.common.mconfigData;
import library.salesforce.common.tGRNDetail_mobileData;
import library.salesforce.common.tGRNHeader_mobileData;
import library.salesforce.common.tPenguaranDetail_MobileData;
import library.salesforce.common.tPenguaranHeader_MobileData;
import library.salesforce.common.tSalesOrderDetail_MobileData;
import library.salesforce.common.tSalesOrderHeader_MobileData;
import library.salesforce.common.tStockOpnameDetail_mobileData;
import library.salesforce.common.tStockOpnameHeader_mobileData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.enumConfigData;
import library.salesforce.dal.enumCounterData;
import library.salesforce.dal.mCounterNumberDA;
import library.salesforce.dal.mItemSalesPack_StockDA;
import library.salesforce.dal.mconfigDA;
import library.salesforce.dal.tGRNDetail_mobileDA;
import library.salesforce.dal.tGRNHeader_mobileDA;
import library.salesforce.dal.tPenguaranDetail_MobileDA;
import library.salesforce.dal.tPenguaranHeader_MobileDA;
import library.salesforce.dal.tSalesOrderDetail_MobileDA;
import library.salesforce.dal.tSalesOrderHeader_MobileDA;
import library.salesforce.dal.tStockOpnameDetail_mobileDA;
import library.salesforce.dal.tStockOpnameHeader_mobileDA;
import library.salesforce.dal.tUserLoginDA;

//import com.kalbe.salesforce.clsMainActivity;

public class mItemSalesPack_stockBL extends clsMainBL {
	public void saveData(List<mItemSalesPack_StockData> Listdata){
		SQLiteDatabase db=getDb();
		mItemSalesPack_StockDA _mItemSalesPack_StockDA=new mItemSalesPack_StockDA(db);
		_mItemSalesPack_StockDA.DeleteAllDAta(db);
		for(mItemSalesPack_StockData data:Listdata){
			_mItemSalesPack_StockDA.SaveDatamItemSalesPack_StockData(db, data);	
		}
		db.close();
	}
	public List<mItemSalesPack_StockData> GetAllData(){
		SQLiteDatabase db=getDb();
		mItemSalesPack_StockDA _mItemSalesPack_StockDA=new mItemSalesPack_StockDA(db);
		List<mItemSalesPack_StockData> listData= _mItemSalesPack_StockDA.getAllData(db);
		db.close();
		return listData;
	}
	public void calculatemItemSalesPack_StockDataFromPenguaran(String txtNo) throws ParseException{
		SQLiteDatabase db=getDb();
		tPenguaranDetail_MobileDA _tPenguaranDetail_MobileDA=new tPenguaranDetail_MobileDA(db);
		tPenguaranHeader_MobileDA _tPenguaranHeader_MobileDA=new tPenguaranHeader_MobileDA(db);
		mItemSalesPack_StockDA _mItemSalesPack_StockDA=new mItemSalesPack_StockDA(db);
		tPenguaranHeader_MobileData dtHeader=_tPenguaranHeader_MobileDA.getData(db, txtNo);
		List<tPenguaranDetail_MobileData> dtDetail=_tPenguaranDetail_MobileDA.getCalculateProduct(db, txtNo);
		mCounterNumberDA _mCounterNumberDA=new mCounterNumberDA(db);
		mCounterNumberData dtmCounterNumberDataDatePartNow=_mCounterNumberDA.getData(db, enumCounterData.DatePartNow.getidCounterData());
		mCounterNumberData dtmCounterNumberDataPeriodeNow=_mCounterNumberDA.getData(db, enumCounterData.PeriodeNow.getidCounterData());
		clsMainActivity _clsMainActivity=new clsMainActivity();
		for(tPenguaranDetail_MobileData dt :  dtDetail){
			List<mItemSalesPack_StockData> listDatamItemSalesPack_StockData=_mItemSalesPack_StockDA.getAllDataByOuletAndIdProduct(db,dtHeader.get_txtOutletCode(),dt.get_intProductCode(),dtmCounterNumberDataDatePartNow.get_txtValue());
			if(listDatamItemSalesPack_StockData!=null){
				mItemSalesPack_StockData dtcurr=listDatamItemSalesPack_StockData.get(0);
				int intQtyCurr= Integer.valueOf(dtcurr.get_intQtyOut())+Integer.valueOf(dt.get_intQty());
				dtcurr.set_txtNoTransaction(txtNo);
				dtcurr.set_intQtyOut(String.valueOf(intQtyCurr));
				dtcurr.set_intSubmit("1");
				dtcurr.set_intSync("0");
				_mItemSalesPack_StockDA.SaveDatamItemSalesPack_StockData(db, dtcurr);
			}else{
				mItemSalesPack_StockData dtmItemSalesPack_StockData=new mItemSalesPack_StockData();
				dtmItemSalesPack_StockData.set_intProductCode(dt.get_intProductCode());
				dtmItemSalesPack_StockData.set_intQtyOut(dt.get_intQty());
				dtmItemSalesPack_StockData.set_intQtyAdj("0");
				dtmItemSalesPack_StockData.set_intQtyIn("0");
				dtmItemSalesPack_StockData.set_intSaldoAwal("0");
				dtmItemSalesPack_StockData.set_intSubmit("1");
				dtmItemSalesPack_StockData.set_intSync("0");
				dtmItemSalesPack_StockData.set_intWeek(dtmCounterNumberDataDatePartNow.get_txtValue());
				dtmItemSalesPack_StockData.set_txtBranchCode(dtHeader.get_txtBranchCode());
				dtmItemSalesPack_StockData.set_txtDataId(GenerateGuid());
				dtmItemSalesPack_StockData.set_txtNoTransaction(txtNo);
				dtmItemSalesPack_StockData.set_txtOutletCode(dtHeader.get_txtOutletCode());
				//dtmItemSalesPack_StockData.set_txtPeriode(dtmCounterNumberDataPeriodeNow.get_txtValue());
				dtmItemSalesPack_StockData.set_txtPeriode(dtmCounterNumberDataPeriodeNow.get_txtValue());
				dtmItemSalesPack_StockData.set_txtOutletName(dtHeader.get_txtOutletName());
				_mItemSalesPack_StockDA.SaveDatamItemSalesPack_StockData(db, dtmItemSalesPack_StockData);
			}
		}
		db.close();
	}
	public void calculatemItemSalesPack_StockDataFromSO(String txtNo) throws ParseException{
		SQLiteDatabase db=getDb();
		tSalesOrderDetail_MobileDA _tSalesOrderDetail_MobileDA=new tSalesOrderDetail_MobileDA(db);
		tSalesOrderHeader_MobileDA _tSalesOrderHeader_MobileDA=new tSalesOrderHeader_MobileDA(db);
		mItemSalesPack_StockDA _mItemSalesPack_StockDA=new mItemSalesPack_StockDA(db);
		tSalesOrderHeader_MobileData dtHeader=_tSalesOrderHeader_MobileDA.getData(db, txtNo);
		List<tSalesOrderDetail_MobileData> dtDetail=_tSalesOrderDetail_MobileDA.getCalculateProduct(db, txtNo);
		mCounterNumberDA _mCounterNumberDA=new mCounterNumberDA(db);
		mCounterNumberData dtmCounterNumberDataDatePartNow=_mCounterNumberDA.getData(db, enumCounterData.DatePartNow.getidCounterData());
		mCounterNumberData dtmCounterNumberDataPeriodeNow=_mCounterNumberDA.getData(db, enumCounterData.PeriodeNow.getidCounterData());
		clsMainActivity _clsMainActivity=new clsMainActivity();
		for(tSalesOrderDetail_MobileData dt :  dtDetail){
			List<mItemSalesPack_StockData> listDatamItemSalesPack_StockData=_mItemSalesPack_StockDA.getAllDataByOuletAndIdProduct(db,dtHeader.get_txtOutletCode(),dt.get_intProductCode(),dtmCounterNumberDataDatePartNow.get_txtValue());
			if(listDatamItemSalesPack_StockData!=null){
				mItemSalesPack_StockData dtcurr=listDatamItemSalesPack_StockData.get(0);
				int intQtyCurr= Integer.valueOf(dtcurr.get_intQtyOut())+Integer.valueOf(dt.get_intQty());
				dtcurr.set_txtNoTransaction(txtNo);
				dtcurr.set_intQtyOut(String.valueOf(intQtyCurr));
				dtcurr.set_intSubmit("1");
				dtcurr.set_intSync("0");
				_mItemSalesPack_StockDA.SaveDatamItemSalesPack_StockData(db, dtcurr);
			}else{
				mItemSalesPack_StockData dtmItemSalesPack_StockData=new mItemSalesPack_StockData();
				dtmItemSalesPack_StockData.set_intProductCode(dt.get_intProductCode());
				dtmItemSalesPack_StockData.set_intQtyOut(dt.get_intQty());
				dtmItemSalesPack_StockData.set_intQtyAdj("0");
				dtmItemSalesPack_StockData.set_intQtyIn("0");
				dtmItemSalesPack_StockData.set_intSaldoAwal("0");
				dtmItemSalesPack_StockData.set_intSubmit("1");
				dtmItemSalesPack_StockData.set_intSync("0");
				dtmItemSalesPack_StockData.set_intWeek(dtmCounterNumberDataDatePartNow.get_txtValue());
				dtmItemSalesPack_StockData.set_txtBranchCode(dtHeader.get_txtBranchCode());
				dtmItemSalesPack_StockData.set_txtDataId(GenerateGuid());
				dtmItemSalesPack_StockData.set_txtNoTransaction(txtNo);
				dtmItemSalesPack_StockData.set_txtOutletCode(dtHeader.get_txtOutletCode());
				//dtmItemSalesPack_StockData.set_txtPeriode(dtmCounterNumberDataPeriodeNow.get_txtValue());
				dtmItemSalesPack_StockData.set_txtPeriode(dtmCounterNumberDataPeriodeNow.get_txtValue());
				dtmItemSalesPack_StockData.set_txtOutletName(dtHeader.get_txtOutletName());
				_mItemSalesPack_StockDA.SaveDatamItemSalesPack_StockData(db, dtmItemSalesPack_StockData);
			}
		}
		db.close();
	}
	public void calculatemItemSalesPack_StockDataFromOpname(String txtNo) throws ParseException{
		SQLiteDatabase db=getDb();
		tStockOpnameDetail_mobileDA _tStockOpnameDetail_mobileDA=new tStockOpnameDetail_mobileDA(db);
		tStockOpnameHeader_mobileDA _tStockOpnameHeader_mobileDA=new tStockOpnameHeader_mobileDA(db);
		mItemSalesPack_StockDA _mItemSalesPack_StockDA=new mItemSalesPack_StockDA(db);
		tStockOpnameHeader_mobileData dtHeader=_tStockOpnameHeader_mobileDA.getData(db, txtNo);
		List<tStockOpnameDetail_mobileData> dtDetail=_tStockOpnameDetail_mobileDA.getCalculateProduct(db, txtNo);
		mCounterNumberDA _mCounterNumberDA=new mCounterNumberDA(db);
		mCounterNumberData dtmCounterNumberDataDatePartNow=_mCounterNumberDA.getData(db, enumCounterData.DatePartNow.getidCounterData());
		mCounterNumberData dtmCounterNumberDataPeriodeNow=_mCounterNumberDA.getData(db, enumCounterData.PeriodeNow.getidCounterData());
		clsMainActivity _clsMainActivity=new clsMainActivity();
		for(tStockOpnameDetail_mobileData dt :  dtDetail){
			List<mItemSalesPack_StockData> listDatamItemSalesPack_StockData=_mItemSalesPack_StockDA.getAllDataByOuletAndIdProduct(db,dtHeader.get_txtOutletCode(),dt.get_intProductCode(),dtmCounterNumberDataDatePartNow.get_txtValue());
			if(listDatamItemSalesPack_StockData!=null){
				mItemSalesPack_StockData dtcurr=listDatamItemSalesPack_StockData.get(0);
				int intQtyCurr= Integer.valueOf(dtcurr.get_intQtyAdj())+Integer.valueOf(dt.get_intQtyAdj());
				dtcurr.set_txtNoTransaction(txtNo);
				dtcurr.set_intQtyAdj(String.valueOf(intQtyCurr));
				dtcurr.set_intSubmit("1");
				dtcurr.set_intSync("0");
				_mItemSalesPack_StockDA.SaveDatamItemSalesPack_StockData(db, dtcurr);
			}else{
				mItemSalesPack_StockData dtmItemSalesPack_StockData=new mItemSalesPack_StockData();
				dtmItemSalesPack_StockData.set_intProductCode(dt.get_intProductCode());
				dtmItemSalesPack_StockData.set_intQtyAdj(dt.get_intQtyAdj());
				dtmItemSalesPack_StockData.set_intQtyOut("0");
				dtmItemSalesPack_StockData.set_intQtyIn("0");
				dtmItemSalesPack_StockData.set_intSaldoAwal("0");
				dtmItemSalesPack_StockData.set_intSubmit("1");
				dtmItemSalesPack_StockData.set_intSync("0");
				dtmItemSalesPack_StockData.set_intWeek(dtmCounterNumberDataDatePartNow.get_txtValue());
				dtmItemSalesPack_StockData.set_txtBranchCode(dtHeader.get_txtBranchCode());
				dtmItemSalesPack_StockData.set_txtDataId(GenerateGuid());
				dtmItemSalesPack_StockData.set_txtNoTransaction(txtNo);
				dtmItemSalesPack_StockData.set_txtOutletCode(dtHeader.get_txtOutletCode());
				//dtmItemSalesPack_StockData.set_txtPeriode(dtmCounterNumberDataPeriodeNow.get_txtValue());
				dtmItemSalesPack_StockData.set_txtPeriode(dtmCounterNumberDataPeriodeNow.get_txtValue());
				dtmItemSalesPack_StockData.set_txtOutletName(dtHeader.get_txtOutletName());
				_mItemSalesPack_StockDA.SaveDatamItemSalesPack_StockData(db, dtmItemSalesPack_StockData);
			}
		}	
		db.close();
	}
	public void calculatemItemSalesPack_StockDataFromGRN(String txtNo) throws ParseException{
		SQLiteDatabase db=getDb();
		tGRNHeader_mobileDA _tGRNHeader_mobileDA=new tGRNHeader_mobileDA(db);
		tGRNDetail_mobileDA _tGRNDetail_mobileDA=new tGRNDetail_mobileDA(db);
		mItemSalesPack_StockDA _mItemSalesPack_StockDA=new mItemSalesPack_StockDA(db);
		tGRNHeader_mobileData dtHeader=_tGRNHeader_mobileDA.getData(db, txtNo);
		List<tGRNDetail_mobileData> dtDetail=_tGRNDetail_mobileDA.getCalculateProduct(db, txtNo);
		mCounterNumberDA _mCounterNumberDA=new mCounterNumberDA(db);
		mCounterNumberData dtmCounterNumberDataDatePartNow=_mCounterNumberDA.getData(db, enumCounterData.DatePartNow.getidCounterData());
		mCounterNumberData dtmCounterNumberDataPeriodeNow=_mCounterNumberDA.getData(db, enumCounterData.PeriodeNow.getidCounterData());
		clsMainActivity _clsMainActivity=new clsMainActivity();
		for(tGRNDetail_mobileData dt :  dtDetail){
			List<mItemSalesPack_StockData> listDatamItemSalesPack_StockData=_mItemSalesPack_StockDA.getAllDataByOuletAndIdProduct(db,dtHeader.get_txtOutletCode(),dt.get_intProductCode(),dtmCounterNumberDataDatePartNow.get_txtValue());
			if(listDatamItemSalesPack_StockData!=null){
				mItemSalesPack_StockData dtcurr=listDatamItemSalesPack_StockData.get(0);
				int intQtyCurr= Integer.valueOf(dtcurr.get_intQtyIn())+Integer.valueOf(dt.get_intQty());
				dtcurr.set_txtNoTransaction(txtNo);
				dtcurr.set_intQtyIn(String.valueOf(intQtyCurr));
				dtcurr.set_intSubmit("1");
				dtcurr.set_intSync("0");
				_mItemSalesPack_StockDA.SaveDatamItemSalesPack_StockData(db, dtcurr);
			}else{
				mItemSalesPack_StockData dtmItemSalesPack_StockData=new mItemSalesPack_StockData();
				dtmItemSalesPack_StockData.set_intProductCode(dt.get_intProductCode());
				dtmItemSalesPack_StockData.set_intQtyIn(dt.get_intQty());
				dtmItemSalesPack_StockData.set_intQtyAdj("0");
				dtmItemSalesPack_StockData.set_intQtyOut("0");
				dtmItemSalesPack_StockData.set_intSaldoAwal("0");
				dtmItemSalesPack_StockData.set_intSubmit("1");
				dtmItemSalesPack_StockData.set_intSync("0");
				dtmItemSalesPack_StockData.set_intWeek(dtmCounterNumberDataDatePartNow.get_txtValue());
				dtmItemSalesPack_StockData.set_txtBranchCode(dtHeader.get_txtBranchCode());
				dtmItemSalesPack_StockData.set_txtDataId(GenerateGuid());
				dtmItemSalesPack_StockData.set_txtNoTransaction(txtNo);
				dtmItemSalesPack_StockData.set_txtOutletCode(dtHeader.get_txtOutletCode());
				//dtmItemSalesPack_StockData.set_txtPeriode(dtmCounterNumberDataPeriodeNow.get_txtValue());
				dtmItemSalesPack_StockData.set_txtPeriode(dtmCounterNumberDataPeriodeNow.get_txtValue());
				dtmItemSalesPack_StockData.set_txtOutletName(dtHeader.get_txtOutletName());
				_mItemSalesPack_StockDA.SaveDatamItemSalesPack_StockData(db, dtmItemSalesPack_StockData);
			}
		}
		db.close();
	}
	public void GenerateDatepartNow(String versionName) throws Exception{
		
		SQLiteDatabase _db=getDb();
		Boolean flag=true;
		String ErrorMess="";
		String txtMethod="GenerateDatepartNow";
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
				_data.set_intId(enumCounterData.DatePartNow.getidCounterData());
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
		_db.close();
	}
public void GeneratePeriodeNow(String versionName) throws Exception{
	
	SQLiteDatabase _db=getDb();
	Boolean flag=true;
	String ErrorMess="";
	String txtMethod="GeneratePeriodeNow";
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
			_data.set_intId(enumCounterData.PeriodeNow.getidCounterData());
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
	_db.close();
}
	public org.json.simple.JSONArray DownloadmProductBarcode2(String versionName) throws Exception{
		//ambil linkapi Database sqllite
		SQLiteDatabase _db=getDb();
		tUserLoginDA _tUserLoginDA=new tUserLoginDA(_db);
		mconfigDA _mconfigDA =new mconfigDA(_db);
		
		String strVal2="";
		mconfigData dataAPI = _mconfigDA.getData(_db,enumConfigData.ApiKalbe.getidConfigData());
		strVal2 = dataAPI.get_txtValue();
		if (dataAPI.get_txtValue() == "") {
			strVal2 = dataAPI.get_txtDefaultValue();
		}
		//ambil version dari webservices
		tUserLoginData _dataUserLogin = _tUserLoginDA.getData(_db, 1);
		clsHelper _help =new clsHelper();
		linkAPI dtlinkAPI=new linkAPI();
		String txtMethod="GetDataNowmItemSalesPack_Stock";
		dtlinkAPI.set_txtMethod(txtMethod);
		dtlinkAPI.set_txtParam(_dataUserLogin.get_TxtEmpId()+"|||");
		dtlinkAPI.set_txtToken(new clsHardCode().txtTokenAPI);
		dtlinkAPI.set_txtVesion(versionName);
		String strLinkAPI= dtlinkAPI.QueryString(strVal2);
		String JsonData= _help.ResultJsonData(_help.getHTML(strLinkAPI));
		org.json.simple.JSONArray JsonArray= _help.ResultJsonArray(JsonData);
		_db.close();
		return JsonArray;
	}
	public void DownloadmProductBarcode(String versionName) throws Exception{
		//ambil linkapi Database sqllite
		SQLiteDatabase _db=getDb();
		tUserLoginDA _tUserLoginDA=new tUserLoginDA(_db);
		mconfigDA _mconfigDA =new mconfigDA(_db);
		
		String strVal2="";
		mconfigData dataAPI = _mconfigDA.getData(_db,enumConfigData.ApiKalbe.getidConfigData());
		strVal2 = dataAPI.get_txtValue();
		if (dataAPI.get_txtValue() == "") {
			strVal2 = dataAPI.get_txtDefaultValue();
		}
		//ambil version dari webservices
		tUserLoginData _dataUserLogin = _tUserLoginDA.getData(_db, 1);
		clsHelper _help =new clsHelper();
		linkAPI dtlinkAPI=new linkAPI();
		String txtMethod="GetDataNowmItemSalesPack_Stock";
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
		mItemSalesPack_StockDA _mItemSalesPack_StockDA=new mItemSalesPack_StockDA(_db);
		_mItemSalesPack_StockDA.DeleteAllDAta(_db);
		while (i.hasNext()) {
			org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
			int boolValid= Integer.valueOf(String.valueOf( innerObj.get(dtAPIDATA.boolValid)));
			if(boolValid == Integer.valueOf(new clsHardCode().intSuccess)){				
				mItemSalesPack_StockData _data =new mItemSalesPack_StockData();
				_data.set_intProductCode((String) innerObj.get("_intProductCode"));
				_data.set_txtDataId((String) innerObj.get("_txtDataId"));
				_data.set_txtPeriode((String) innerObj.get("_txtPeriode"));
				_data.set_intSaldoAwal((String) innerObj.get("_intSaldoAwal"));
				_data.set_intWeek((String) innerObj.get("_intWeek"));
				_data.set_intQtyIn((String) innerObj.get("_intQtyIn"));
				_data.set_intQtyOut((String) innerObj.get("_intQtyOut"));
				_data.set_intQtyAdj((String) innerObj.get("_intQtyAdj"));
				_data.set_txtOutletCode((String) innerObj.get("_txtOutletCode"));
				_data.set_txtOutletName((String) innerObj.get("_txtOutletName"));
				_data.set_txtBranchCode((String) innerObj.get("_txtBranchCode"));
				_data.set_txtNoTransaction((String) innerObj.get("_txtNoTransaction"));
				_mItemSalesPack_StockDA.SaveDatamItemSalesPack_StockData(_db, _data);
			}else{
				flag=false;
				ErrorMess=(String) innerObj.get(dtAPIDATA.strMessage);
				break;
			}
		}
		_db.close();
	}
}
