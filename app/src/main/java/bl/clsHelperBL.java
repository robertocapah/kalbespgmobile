package bl;

import android.database.sqlite.SQLiteDatabase;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import library.salesforce.common.APIData;
import library.salesforce.common.clsHelper;
import library.salesforce.common.clsPushData;
import library.salesforce.common.dataJson;
import library.salesforce.common.linkAPI;
import library.salesforce.common.mCounterNumberData;
import library.salesforce.common.mItemSalesPack_StockData;
import library.salesforce.common.mItemSalesPack_StockLogData;
import library.salesforce.common.mProductBarcodeData;
import library.salesforce.common.mStockAwalData;
import library.salesforce.common.mconfigData;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tActivityData;
import library.salesforce.common.tGRNDetail_mobileData;
import library.salesforce.common.tGRNHeader_mobileData;
import library.salesforce.common.tLeaveMobileData;
import library.salesforce.common.tNotificationData;
import library.salesforce.common.tPODetail_mobileData;
import library.salesforce.common.tPOHeader_mobileData;
import library.salesforce.common.tPOStatus_mobileData;
import library.salesforce.common.tPenguaranDetail_MobileData;
import library.salesforce.common.tPenguaranHeader_MobileData;
import library.salesforce.common.tPenguaranStatus_MobileData;
import library.salesforce.common.tSalesOrderDetail_MobileData;
import library.salesforce.common.tSalesOrderHeader_MobileData;
import library.salesforce.common.tStockOpnameDetail_mobileData;
import library.salesforce.common.tStockOpnameHeader_mobileData;
import library.salesforce.common.tTransactionDetailData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.enumConfigData;
import library.salesforce.dal.enumCounterData;
import library.salesforce.dal.mCounterNumberDA;
import library.salesforce.dal.mItemSalesPack_StockDA;
import library.salesforce.dal.mItemSalesPack_StockLogDA;
import library.salesforce.dal.mProductBarcodeDA;
import library.salesforce.dal.mStockAwalDA;
import library.salesforce.dal.mconfigDA;
import library.salesforce.dal.tAbsenUserDA;
import library.salesforce.dal.tActivityDA;
import library.salesforce.dal.tGRNDetail_mobileDA;
import library.salesforce.dal.tGRNHeader_mobileDA;
import library.salesforce.dal.tLeaveMobileDA;
import library.salesforce.dal.tPODetail_mobileDA;
import library.salesforce.dal.tPOHeader_mobileDA;
import library.salesforce.dal.tPOStatus_mobileDA;
import library.salesforce.dal.tPenguaranDetail_MobileDA;
import library.salesforce.dal.tPenguaranHeader_MobileDA;
import library.salesforce.dal.tPenguaranStatus_MobileDA;
import library.salesforce.dal.tSalesOrderDetail_MobileDA;
import library.salesforce.dal.tSalesOrderHeader_MobileDA;
import library.salesforce.dal.tStockOpnameDetail_mobileDA;
import library.salesforce.dal.tStockOpnameHeader_mobileDA;
import library.salesforce.dal.tTransactionDetailDA;
import library.salesforce.dal.tUserLoginDA;

//import org.xml.sax.DTDHandler;
//import com.kalbe.salesforce.TableNotif;
//import com.kalbe.service.MyNotificationService;
//import android.content.Intent;
//import come.example.viewbadger.ShortcutBadger;

public class clsHelperBL extends clsMainBL{
	public void DeleteAllDB(){
		SQLiteDatabase db=getDb();
		new clsHelper().DeleteAllDB(db);
		db.close();
	}
	public void DownloadData(String versionName) throws ParseException{
		SQLiteDatabase _db=getDb();
		tUserLoginDA _tUserLoginDA=new tUserLoginDA(_db);
		tUserLoginData _dataUserLogin = _tUserLoginDA.getData(db, 1);
		JSONObject resJson = new JSONObject();
		resJson.put("User", _dataUserLogin.get_txtUserId());
		resJson.put("txtBranchCode", "");
		resJson.put("txtOutletCode", "");
		resJson.put("txtDeviceId", _dataUserLogin.get_txtDeviceId());
		mconfigDA _mconfigDA =new mconfigDA(_db);
		String strVal2="";
		mconfigData dataAPI = _mconfigDA.getData(db,enumConfigData.ApiKalbe.getidConfigData());
		strVal2 = dataAPI.get_txtValue();
		if (dataAPI.get_txtValue() == "") {
			strVal2 = dataAPI.get_txtDefaultValue();
		}
		clsHelper _help =new clsHelper();
		linkAPI dtlinkAPI=new linkAPI();
		String txtMethod="DownloadAllDataTransaction";
		dtlinkAPI.set_txtMethod(txtMethod);
		dtlinkAPI.set_txtParam(_dataUserLogin.get_TxtEmpId()+"|||");
		dtlinkAPI.set_txtToken(new clsHardCode().txtTokenAPI);
		dtlinkAPI.set_txtVesion(versionName);
		String strLinkAPI= dtlinkAPI.QueryString(strVal2);
		//String JsonData= _help.ResultJsonData(_help.getHTML(strLinkAPI));
		String JsonData= _help.pushtData(strLinkAPI,String.valueOf(resJson), Integer.valueOf(getBackGroundServiceOnline()));
		org.json.simple.JSONArray JsonArray= _help.ResultJsonArray(JsonData);

		APIData dtAPIDATA=new APIData();
		//String aa=new clsHelper().linkAPI(db);
		Iterator i = JsonArray.iterator();
		Boolean flag=true;
		String ErrorMess="";
		tPOHeader_mobileDA _tPOHeader_mobileDA=new tPOHeader_mobileDA(_db);
		tPOStatus_mobileDA _tPOStatus_mobileDA=new tPOStatus_mobileDA(_db);
		tPODetail_mobileDA _tPODetail_mobileDA=new tPODetail_mobileDA(_db);
		tGRNHeader_mobileDA _tGRNHeader_mobileDA= new tGRNHeader_mobileDA(_db);
		tGRNDetail_mobileDA _tGRNDetail_mobileDA =new tGRNDetail_mobileDA(_db);
		tSalesOrderHeader_MobileDA _tSalesOrderHeader_MobileDA= new tSalesOrderHeader_MobileDA(_db);
		tSalesOrderDetail_MobileDA _tSalesOrderDetail_MobileDA =new tSalesOrderDetail_MobileDA(_db);
		tStockOpnameDetail_mobileDA _tStockOpnameDetail_mobileDA= new tStockOpnameDetail_mobileDA(_db);
		tStockOpnameHeader_mobileDA _tStockOpnameHeader_mobileDA =new tStockOpnameHeader_mobileDA(_db);
		clsHelper _clsHelper=new clsHelper();
		while (i.hasNext()) {
			org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
			int boolValid= Integer.valueOf(String.valueOf( innerObj.get(dtAPIDATA.boolValid)));
			if(boolValid == Integer.valueOf(new clsHardCode().intSuccess)){
				Iterator j=null;
				int index = 0;
				if(innerObj.get("ListOfclsTTransactionDetail")!=null){
					List<tTransactionDetailData> Listdata	= new ArrayList<tTransactionDetailData>();
					org.json.simple.JSONArray JsonArray_header= _clsHelper.ResultJsonArray(String.valueOf(innerObj.get("ListOfclsTTransactionDetail")));
					j = JsonArray_header.iterator();
					index = 0;
					while(j.hasNext()){
						tTransactionDetailData _data =new tTransactionDetailData();
						org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) j.next();
						_data.set_dtDate(String.valueOf(innerObj_detail.get("_dtDate")));
						_data.set_dtED(String.valueOf(innerObj_detail.get("_dtED")));
						_data.set_intSubmit("1");
						_data.set_intPush("1");
						_data.set_intQty(String.valueOf(innerObj_detail.get("_intQty")));
						_data.set_txtBranchCode(String.valueOf(innerObj_detail.get("_txtBranchCode")));
						_data.set_txtOutletCode(String.valueOf(innerObj_detail.get("_txtOutletCode")));
						_data.set_txtOutletName(String.valueOf(innerObj_detail.get("_txtOutletName")));
						_data.set_intProductCode(String.valueOf(innerObj_detail.get("_intProductCode")));
						_data.set_intWeek(String.valueOf(innerObj_detail.get("_intWeek")));
						_data.set_txtBatchNo(String.valueOf(innerObj_detail.get("_txtBatchNo")));
						_data.set_txtNoDoc(String.valueOf(innerObj_detail.get("_txtNoDoc")));
						_data.set_txtNoMO(String.valueOf(innerObj_detail.get("_txtNoMO")));
						_data.set_txtTransId(String.valueOf(innerObj_detail.get("_txtTransId")));
						_data.set_txtType(String.valueOf(innerObj_detail.get("_txtType")));
						Listdata.add(_data);
					}
					new tTransactionDetailBL().saveData(Listdata);
				}
				if(innerObj.get("ListOfclsTPOHeader_mobile")!=null){
					List<tPOHeader_mobileData> Listdata	= new ArrayList<tPOHeader_mobileData>();
					org.json.simple.JSONArray JsonArray_header= _clsHelper.ResultJsonArray(String.valueOf(innerObj.get("ListOfclsTPOHeader_mobile")));
					j = JsonArray_header.iterator();
					index = 0;
					while(j.hasNext()){
						tPOHeader_mobileData _data =new tPOHeader_mobileData();
						org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) j.next();
						_data.set_dtDate(String.valueOf(innerObj_detail.get("_dtDate")));
						_data.set_intStockAwal(String.valueOf(innerObj_detail.get("_intStockAwal")));
						_data.set_intSubmit("1");
						_data.set_intSync("1");
						_data.set_txtBranchCode(String.valueOf(innerObj_detail.get("_txtBranchCode")));
						_data.set_txtOutletCode(String.valueOf(innerObj_detail.get("_txtOutletCode")));
						_data.set_txtOutletName(String.valueOf(innerObj_detail.get("_txtOutletName")));
						_data.set_txtDesc("");
						_data.set_txtDeviceId(String.valueOf(innerObj_detail.get("_txtDeviceId")));
						_data.set_txtNOMO(String.valueOf(innerObj_detail.get("_txtNOMO")));
						_data.set_txtNoPO(String.valueOf(innerObj_detail.get("_txtNoPO")));
						_data.set_txtUserId(String.valueOf(innerObj_detail.get("_txtUserId")));
						Listdata.add(_data);
					}
					new tPOHeader_mobileBL().saveDataAll(Listdata);
				}
				if(innerObj.get("ListOfclsMItemSalesPack_Stock")!=null){
					List<mItemSalesPack_StockData> Listdata	= new ArrayList<mItemSalesPack_StockData>();
					org.json.simple.JSONArray JsonArray_detail= _clsHelper.ResultJsonArray(String.valueOf(innerObj.get("ListOfclsMItemSalesPack_Stock")));
					j = JsonArray_detail.iterator();
					index = 0;
					while(j.hasNext()){
						mItemSalesPack_StockData _data =new mItemSalesPack_StockData();
						org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) j.next();
						_data.set_intProductCode(String.valueOf("_intProductCode"));
						_data.set_intQtyAdj(String.valueOf(innerObj_detail.get("_intQtyAdj")));
						_data.set_intQtyIn(String.valueOf(innerObj_detail.get("_intQtyIn")));
						_data.set_intQtyOut(String.valueOf(innerObj_detail.get("_intQtyOut")));
						_data.set_intQtyReserved(String.valueOf(innerObj_detail.get("_intQtyReserved")));
						_data.set_intSaldoAwal(String.valueOf(innerObj_detail.get("_intSaldoAwal")));
						_data.set_intSubmit("1");
						_data.set_intSync("1");
						_data.set_intWeek(String.valueOf(innerObj_detail.get("_intWeek")));
						_data.set_txtBranchCode(String.valueOf(innerObj_detail.get("_txtBranchCode")));
						_data.set_txtNoTransaction(String.valueOf(innerObj_detail.get("_txtNoTransaction")));
						_data.set_txtOutletCode(String.valueOf(innerObj_detail.get("_txtOutletCode")));
						_data.set_txtOutletName(String.valueOf(innerObj_detail.get("_txtOutletName")));
						_data.set_txtPeriode(String.valueOf(innerObj_detail.get("_txtPeriode")));
						Listdata.add(_data);
					}
					new mItemSalesPack_stockBL().saveData(Listdata);
				}
				if(innerObj.get("ListOfclsTPODetail_mobile")!=null){
					List<tPODetail_mobileData> Listdata	= new ArrayList<tPODetail_mobileData>();
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
						_data.set_bitActive(String.valueOf(innerObj_detail.get("_bitActive")));
						_data.set_intStockAwal(String.valueOf(innerObj_detail.get("_intStockAwal")));
						_data.set_intSubmit("1");
						_data.set_intSync("1");
						_data.set_txtBatchNo(String.valueOf(innerObj_detail.get("_txtBatchNo")));
						_data.set_txtDataId(String.valueOf(innerObj_detail.get("_txtDataId")));
						_data.set_txtNoDoc(String.valueOf(innerObj_detail.get("_txtNoDoc")));
						_data.set_txtNoPO(String.valueOf(innerObj_detail.get("_txtNoPO")));
						_data.set_txtProductName(String.valueOf(innerObj_detail.get("_txtProductName")));
						Listdata.add(_data);
					}
					new tPODetail_mobileBL().SaveDataAll(Listdata);
				}
				if(innerObj.get("ListOfclsTPOStatus_mobile")!=null){
					List<tPOStatus_mobileData> Listdata	= new ArrayList<tPOStatus_mobileData>();
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
						_data.set_txtDataId(String.valueOf(innerObj_detail.get("_txtDataId")));
						_data.set_txtNoDoc(String.valueOf(innerObj_detail.get("_txtNoDoc")));
						_data.set_txtNoPO(String.valueOf(innerObj_detail.get("_txtNoPO")));
						_data.set_txtStatus(String.valueOf(innerObj_detail.get("_txtStatus")));
						Listdata.add(_data);
					}
					new tPOStatus_mobileBL().SaveDataAll(Listdata);
				}
				if(innerObj.get("ListOfclsTGRNHeader_mobile")!=null){
					List<tGRNHeader_mobileData> Listdata= new ArrayList<tGRNHeader_mobileData>();
					org.json.simple.JSONArray JsonArray_Status= _clsHelper.ResultJsonArray(String.valueOf(innerObj.get("ListOfclsTGRNHeader_mobile")));
					j = JsonArray_Status.iterator();
					index = 0;
					while(j.hasNext()){
						tGRNHeader_mobileData _data =new tGRNHeader_mobileData();
						org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) j.next();
						_data.set_dtDate(String.valueOf(innerObj_detail.get("_dtDate")));
						_data.set_intStockAwal(String.valueOf(innerObj_detail.get("_intStockAwal")));
						_data.set_intSubmit("1");
						_data.set_intSync("1");
						_data.set_txtBranchCode(String.valueOf(innerObj_detail.get("_txtBranchCode")));
						_data.set_txtDeviceId(String.valueOf(innerObj_detail.get("_txtDeviceId")));						
						_data.set_txtNoGRN(String.valueOf(innerObj_detail.get("_txtNoGRN")));
						_data.set_txtNoMO(String.valueOf(innerObj_detail.get("_txtNoMO")));
						_data.set_txtNoPO(String.valueOf(innerObj_detail.get("_txtNoPO")));
						_data.set_txtOutletCode(String.valueOf(innerObj_detail.get("_txtOutletCode")));
						_data.set_txtOutletName(String.valueOf(innerObj_detail.get("_txtOutletName")));
						_data.set_txtSource(String.valueOf(innerObj_detail.get("_txtSource")));
						_data.set_txtUserId(String.valueOf(innerObj_detail.get("_txtUserId")));
						new tGRNHeader_mobileBL().saveData(_data);
						Listdata.add(_data);
					}
					
				}
				if(innerObj.get("ListOfclsTGRNDetail_mobile")!=null){
					List<tGRNDetail_mobileData> Listdata= new ArrayList<tGRNDetail_mobileData>();
					org.json.simple.JSONArray JsonArray_Status= _clsHelper.ResultJsonArray(String.valueOf(innerObj.get("ListOfclsTGRNDetail_mobile")));
					j = JsonArray_Status.iterator();
					index = 0;
					while(j.hasNext()){
						tGRNDetail_mobileData _data =new tGRNDetail_mobileData();
						org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) j.next();
						_data.set_dtED(String.valueOf(innerObj_detail.get("_dtED")));
						_data.set_intProductCode(String.valueOf(innerObj_detail.get("_intProductCode")));
						_data.set_intQty(String.valueOf(innerObj_detail.get("_intQty")));
						_data.set_intReason(String.valueOf(innerObj_detail.get("_intReason")));
						_data.set_intStockAwal(String.valueOf(innerObj_detail.get("_intStockAwal")));						
						_data.set_intSubmit("1");
						_data.set_intSync("1");
						_data.set_txtBatchNo(String.valueOf(innerObj_detail.get("_txtBatchNo")));
						_data.set_txtDataId(String.valueOf(innerObj_detail.get("_txtDataId")));
						_data.set_txtNoGRN(String.valueOf(innerObj_detail.get("_txtNoGRN")));
						_data.set_txtProductName(String.valueOf(innerObj_detail.get("_txtProductName")));
						new tGRNDetail_mobileBL().SaveData(_data);
						Listdata.add(_data);
					}
				}
				if(innerObj.get("ListOfclsTSalesOrderHeader_Mobile")!=null){
					List<tSalesOrderHeader_MobileData> Listdata= new ArrayList<tSalesOrderHeader_MobileData>();
					org.json.simple.JSONArray JsonArray_Status= _clsHelper.ResultJsonArray(String.valueOf(innerObj.get("ListOfclsTSalesOrderHeader_Mobile")));
					j = JsonArray_Status.iterator();
					index = 0;
					while(j.hasNext()){
						tSalesOrderHeader_MobileData _data =new tSalesOrderHeader_MobileData();
						org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) j.next();
						_data.set_dtDate(String.valueOf(innerObj_detail.get("_dtDate")));
						_data.set_intSubmit("1");
						_data.set_intSync("1");
						_data.set_txtBranchCode(String.valueOf(innerObj_detail.get("_txtBranchCode")));
						_data.set_txtDeviceId(String.valueOf(innerObj_detail.get("_txtDeviceId")));						
						_data.set_txtNoSalesOrder(String.valueOf(innerObj_detail.get("_txtNoSalesOrder")));
						_data.set_txtOutletCode(String.valueOf(innerObj_detail.get("_txtOutletCode")));
						_data.set_txtOutletName(String.valueOf(innerObj_detail.get("_txtOutletName")));
						_data.set_txtUserId(String.valueOf(innerObj_detail.get("_txtUserId")));
						new tSalesOrderHeader_MobileBL().saveData(_data);
						Listdata.add(_data);
					}
				}
				if(innerObj.get("ListOfclsTSalesOrderDetail_Mobile")!=null){
					List<tSalesOrderDetail_MobileData> Listdata= new ArrayList<tSalesOrderDetail_MobileData>();
					org.json.simple.JSONArray JsonArray_Status= _clsHelper.ResultJsonArray(String.valueOf(innerObj.get("ListOfclsTSalesOrderDetail_Mobile")));
					j = JsonArray_Status.iterator();
					index = 0;
					while(j.hasNext()){
						tSalesOrderDetail_MobileData _data =new tSalesOrderDetail_MobileData();
						org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) j.next();
						_data.set_dtED(String.valueOf(innerObj_detail.get("_dtED")));
						_data.set_intProductCode(String.valueOf(innerObj_detail.get("_intProductCode")));
						_data.set_intQty(String.valueOf(innerObj_detail.get("_intQty")));
						_data.set_intSubmit("1");
						_data.set_intSync("1");
						_data.set_txtBatchNo(String.valueOf(innerObj_detail.get("_txtBatchNo")));
						_data.set_txtDataId(String.valueOf(innerObj_detail.get("_txtDataId")));
						_data.set_intItemPriceID(String.valueOf(innerObj_detail.get("_intItemPriceID")));
						_data.set_decPrice(String.valueOf(innerObj_detail.get("_decPrice")));
						_data.set_txtNoSalesOrder(String.valueOf(innerObj_detail.get("_txtNoSalesOrder")));
						_data.set_txtProductName(String.valueOf(innerObj_detail.get("_txtProductName")));
						new tSalesOrderDetail_MobileBL().SaveData(_data);
						Listdata.add(_data);
					}
				}
				if(innerObj.get("ListOfclsTStockOpnameHeader_mobile")!=null){
					List<tStockOpnameHeader_mobileData> Listdata= new ArrayList<tStockOpnameHeader_mobileData>();
					org.json.simple.JSONArray JsonArray_Status= _clsHelper.ResultJsonArray(String.valueOf(innerObj.get("ListOfclsTStockOpnameHeader_mobile")));
					j = JsonArray_Status.iterator();
					index = 0;
					while(j.hasNext()){
						tStockOpnameHeader_mobileData _data =new tStockOpnameHeader_mobileData();
						org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) j.next();
						_data.set_dtDate(String.valueOf(innerObj_detail.get("_dtDate")));
						_data.set_intSubmit("1");
						_data.set_intPush("1");
						_data.set_txtBranchCode(String.valueOf(innerObj_detail.get("_txtBranchCode")));
						_data.set_txtDeviceId(String.valueOf(innerObj_detail.get("_txtDeviceId")));						
						_data.set_txtNoAdj(String.valueOf(innerObj_detail.get("_txtNoAdj")));
						_data.set_txtOutletCode(String.valueOf(innerObj_detail.get("_txtOutletCode")));
						_data.set_txtOutletName(String.valueOf(innerObj_detail.get("_txtOutletName")));
						_data.set_txtUserId(String.valueOf(innerObj_detail.get("_txtUserId")));
						new tStockOpnameHeader_mobileBL().saveData(_data);
						Listdata.add(_data);
					}
				}
				if(innerObj.get("ListOfclsTStockOpnameDetail_mobile")!=null){
					List<tStockOpnameDetail_mobileData> Listdata= new ArrayList<tStockOpnameDetail_mobileData>();
					org.json.simple.JSONArray JsonArray_Status= _clsHelper.ResultJsonArray(String.valueOf(innerObj.get("ListOfclsTStockOpnameDetail_mobile")));
					j = JsonArray_Status.iterator();
					index = 0;
					while(j.hasNext()){
						tStockOpnameDetail_mobileData _data =new tStockOpnameDetail_mobileData();
						org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) j.next();
						_data.set_dtED(String.valueOf(innerObj_detail.get("_dtED")));
						_data.set_intProductCode(String.valueOf(innerObj_detail.get("_intProductCode")));
						_data.set_intQty(String.valueOf(innerObj_detail.get("_intQty")));
						_data.set_intQtyAdj(String.valueOf(innerObj_detail.get("_intQtyAdj")));
						_data.set_intQtyStock(String.valueOf(innerObj_detail.get("_intQtyStock")));
						_data.set_intSubmit("1");
						_data.set_intSync("1");
						_data.set_txtBatchNo(String.valueOf(innerObj_detail.get("_txtBatchNo")));
						_data.set_txtDataId(String.valueOf(innerObj_detail.get("_txtDataId")));
						_data.set_txtNoAdj(String.valueOf(innerObj_detail.get("_txtNoAdj")));
						_data.set_txtProductName(String.valueOf(innerObj_detail.get("_txtProductName")));
						new tStockOpnameDetail_mobileBL().SaveData(_data);
						Listdata.add(_data);
					}
				}
				if(innerObj.get("ListOfclsTPenguaranHeader_Mobile")!=null){
					List<tPenguaranHeader_MobileData> Listdata= new ArrayList<tPenguaranHeader_MobileData>();
					org.json.simple.JSONArray JsonArray_Status= _clsHelper.ResultJsonArray(String.valueOf(innerObj.get("ListOfclsTPenguaranHeader_Mobile")));
					j = JsonArray_Status.iterator();
					index = 0;
					while(j.hasNext()){
						tPenguaranHeader_MobileData _data =new tPenguaranHeader_MobileData();
						org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) j.next();
						_data.set_dtDate(String.valueOf(innerObj_detail.get("_dtDate")));
						_data.set_intSubmit("1");
						_data.set_intSync("1");
						_data.set_txtBranchCode(String.valueOf(innerObj_detail.get("_txtBranchCode")));
						_data.set_txtDeviceId(String.valueOf(innerObj_detail.get("_txtDeviceId")));						
						_data.set_txtNoPenguaran(String.valueOf(innerObj_detail.get("_txtNoPenguaran")));
						_data.set_txtOutletCode(String.valueOf(innerObj_detail.get("_txtOutletCode")));
						_data.set_txtOutletName(String.valueOf(innerObj_detail.get("_txtOutletName")));
						_data.set_txtUserId(String.valueOf(innerObj_detail.get("_txtUserId")));
						new tPenguaranHeader_MobileBL().saveData(_data);
						Listdata.add(_data);
					}
				}
				if(innerObj.get("ListOfclsTPenguaranDetail_mobile")!=null){
					List<tPenguaranDetail_MobileData> Listdata= new ArrayList<tPenguaranDetail_MobileData>();
					org.json.simple.JSONArray JsonArray_Status= _clsHelper.ResultJsonArray(String.valueOf(innerObj.get("ListOfclsTPenguaranDetail_mobile")));
					j = JsonArray_Status.iterator();
					index = 0;
					while(j.hasNext()){
						tPenguaranDetail_MobileData _data =new tPenguaranDetail_MobileData();
						org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) j.next();
						_data.set_dtED(String.valueOf(innerObj_detail.get("_dtED")));
						_data.set_intReason(String.valueOf(innerObj_detail.get("_intReason")));
						_data.set_txtDesc(String.valueOf(innerObj_detail.get("_txtDesc")));
						_data.set_intProductCode(String.valueOf(innerObj_detail.get("_intProductCode")));
						_data.set_intQty(String.valueOf(innerObj_detail.get("_intQty")));
						_data.set_intSubmit("1");
						_data.set_intSync("1");
						_data.set_bitActive("1");
						_data.set_txtBatchNo(String.valueOf(innerObj_detail.get("_txtBatchNo")));
						_data.set_txtDataId(String.valueOf(innerObj_detail.get("_txtDataId")));
						_data.set_txtNoPenguaran(String.valueOf(innerObj_detail.get("_txtNoPenguaran")));
						_data.set_txtProductName(String.valueOf(innerObj_detail.get("_txtProductName")));
						new tPenguaranDetail_MobileBL().SaveData(_data);
						Listdata.add(_data);
					}
				}
				if(innerObj.get("ListOfclsTPenguaranStatus_mobile")!=null){
					List<tPenguaranStatus_MobileData> Listdata= new ArrayList<tPenguaranStatus_MobileData>();
					org.json.simple.JSONArray JsonArray_Status= _clsHelper.ResultJsonArray(String.valueOf(innerObj.get("ListOfclsTPenguaranStatus_mobile")));
					j = JsonArray_Status.iterator();
					index = 0;
					while(j.hasNext()){
						tPenguaranStatus_MobileData _data =new tPenguaranStatus_MobileData();
						org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) j.next();
						_data.set_intStatus(String.valueOf(innerObj_detail.get("_intStatus")));
						_data.set_txtStatus(String.valueOf(innerObj_detail.get("_txtStatus")));
						_data.set_intSubmit("1");
						_data.set_intSync("1");
						_data.set_bitActive("1");
						_data.set_txtDataId(String.valueOf(innerObj_detail.get("_txtDataId")));
						_data.set_txtNoPenguaran(String.valueOf(innerObj_detail.get("_txtNoPenguaran")));
						new tPenguaranStatus_MobileBL().SaveData(_data);
						Listdata.add(_data);
					}
				}
			}
		}
		db.close();
	}
	public void saveDataPush(dataJson dtJson,org.json.simple.JSONArray JsonResult){
		SQLiteDatabase db=getDb();
		if(dtJson.get_ListOfmItemSalesPack_StockData()!=null){
			for (mItemSalesPack_StockData dt : dtJson.get_ListOfmItemSalesPack_StockData()) {
				mItemSalesPack_StockDA _mItemSalesPack_StockDA=new mItemSalesPack_StockDA(db);
				dt.set_intSync("1");
				_mItemSalesPack_StockDA.SaveDatamItemSalesPack_StockData(db, dt);
			}
		}
		if(dtJson.get_ListOfmItemSalesPack_StockLogData()!=null){
			for (mItemSalesPack_StockLogData dt : dtJson.get_ListOfmItemSalesPack_StockLogData()) {
				mItemSalesPack_StockLogDA _mMItemSalesPack_StockDA=new mItemSalesPack_StockLogDA(db);
				dt.set_intSync("1");
				_mMItemSalesPack_StockDA.SaveDatamItemSalesPack_StockLogData(db, dt);
			}
		}
		if(dtJson.get_ListOfmProductBarcodeData()!=null){
			for (mProductBarcodeData dt : dtJson.get_ListOfmProductBarcodeData()) {
				mProductBarcodeDA _mProductBarcodeDA=new mProductBarcodeDA(db);
				dt.set_intSync("1");
				dt.set_intSubmit("1");
				_mProductBarcodeDA.SaveDatamProductBarcodeData(db, dt);
			}
		}
		if(dtJson.get_ListOfmStockAwalData()!=null){
			for (mStockAwalData dt : dtJson.get_ListOfmStockAwalData()) {
				mStockAwalDA _mProductBarcodeDA=new mStockAwalDA(db);
				dt.set_intSync("1");
				dt.set_intSubmit("1");
				_mProductBarcodeDA.SaveDataMStockAwalData(db, dt);
			}
		}
		if(dtJson.get_ListOftGRNDetail_mobileData()!=null){
			for (tGRNDetail_mobileData dt : dtJson.get_ListOftGRNDetail_mobileData()) {
				tGRNDetail_mobileDA _tGRNDetail_mobileDA=new tGRNDetail_mobileDA(db);
				dt.set_intSync("1");
				_tGRNDetail_mobileDA.SaveDatatGRNDetail_mobileData(db, dt);
			}
		}
		if(dtJson.get_ListOftGRNHeader_mobileData()!=null){
			for (tGRNHeader_mobileData dt : dtJson.get_ListOftGRNHeader_mobileData()) {
				tGRNHeader_mobileDA _tGRNHeader_mobileDA=new tGRNHeader_mobileDA(db);
				dt.set_intSync("1");
				_tGRNHeader_mobileDA.SaveDatatGRNHeader_mobileData(db, dt);
			}
		}
		if(dtJson.get_ListOftPenguaranDetail_MobileData()!=null){
			for (tPenguaranDetail_MobileData dt : dtJson.get_ListOftPenguaranDetail_MobileData()) {
				tPenguaranDetail_MobileDA _tPenguaranDetail_MobileDA=new tPenguaranDetail_MobileDA(db);
				dt.set_intSync("1");
				_tPenguaranDetail_MobileDA.SaveDatatPenguaranDetail_MobileData(db, dt);
			}
		}
		if(dtJson.get_ListOftPenguaranHeader_MobileData()!=null){
			for (tPenguaranHeader_MobileData dt : dtJson.get_ListOftPenguaranHeader_MobileData()) {
				tPenguaranHeader_MobileDA _tPenguaranDetail_MobileDA=new tPenguaranHeader_MobileDA(db);
				dt.set_intSync("1");
				_tPenguaranDetail_MobileDA.SaveDatatSalesProductHeaderData(db, dt);
			}
		}
		if(dtJson.get_ListOftPenguaranStatus_MobileData()!=null){
			for (tPenguaranStatus_MobileData dt : dtJson.get_ListOftPenguaranStatus_MobileData()) {
				tPenguaranStatus_MobileDA _tPenguaranStatus_MobileDA=new tPenguaranStatus_MobileDA(db);
				dt.set_intSync("1");
				_tPenguaranStatus_MobileDA.SaveDatatSalesProductHeaderData(db, dt);
			}
		}
		if(dtJson.get_ListOftPODetail_mobileData()!=null){
			for (tPODetail_mobileData dt : dtJson.get_ListOftPODetail_mobileData()) {
				tPODetail_mobileDA _tPODetail_mobileDA=new tPODetail_mobileDA(db);
				dt.set_intSync("1");
				_tPODetail_mobileDA.SaveDatatPODetail_mobileData(db, dt);
			}
		}
		if(dtJson.get_ListOftPOHeader_mobileData()!=null){
			for (tPOHeader_mobileData dt : dtJson.get_ListOftPOHeader_mobileData()) {
				tPOHeader_mobileDA _tPOHeader_mobileDA=new tPOHeader_mobileDA(db);
				dt.set_intSync("1");
				_tPOHeader_mobileDA.SaveDatatPOHeader_mobileData(db, dt);
			}
		}
		if(dtJson.get_ListOftPOStatus_mobileData()!=null){
			for (tPOStatus_mobileData dt : dtJson.get_ListOftPOStatus_mobileData()) {
				tPOStatus_mobileDA _tPOStatus_mobileDA=new tPOStatus_mobileDA(db);
				dt.set_intSync("1");
				_tPOStatus_mobileDA.SaveDatatPOStatus_mobileData(db, dt);
			}
		}
		if(dtJson.get_ListOftSalesOrderDetail_MobileData()!=null){
			for (tSalesOrderDetail_MobileData dt : dtJson.get_ListOftSalesOrderDetail_MobileData()) {
				tSalesOrderDetail_MobileDA _tSalesOrderDetail_MobileDA=new tSalesOrderDetail_MobileDA(db);
				dt.set_intSync("1");
				_tSalesOrderDetail_MobileDA.SaveDatatSalesOrderDetail_MobileData(db, dt);
			}
		}
		if(dtJson.get_ListOftSalesOrderHeader_MobileData()!=null){
			for (tSalesOrderHeader_MobileData dt : dtJson.get_ListOftSalesOrderHeader_MobileData()) {
				tSalesOrderHeader_MobileDA _tSalesOrderDetail_MobileDA=new tSalesOrderHeader_MobileDA(db);
				dt.set_intSync("1");
				_tSalesOrderDetail_MobileDA.SaveDatatSalesOrderHeader_MobileData(db, dt);
			}
		}
		if(dtJson.get_ListOftSalesOrderDetail_MobileData()!=null){
			for (tSalesOrderDetail_MobileData dt : dtJson.get_ListOftSalesOrderDetail_MobileData()) {
				tSalesOrderDetail_MobileDA _tSalesOrderDetail_MobileDA=new tSalesOrderDetail_MobileDA(db);
				dt.set_intSync("1");
				_tSalesOrderDetail_MobileDA.SaveDatatSalesOrderDetail_MobileData(db, dt);
			}
		}
		if(dtJson.get_ListOftStockOpnameDetail_mobileData()!=null){
			for (tStockOpnameDetail_mobileData dt : dtJson.get_ListOftStockOpnameDetail_mobileData()) {
				tStockOpnameDetail_mobileDA _tStockOpnameDetail_mobileDA=new tStockOpnameDetail_mobileDA(db);
				dt.set_intSync("1");
				_tStockOpnameDetail_mobileDA.SaveDatatStockOpnameDetail_mobileData(db, dt);
			}
		}
		if(dtJson.get_ListOftStockOpnameHeader_mobileData()!=null){
			for (tStockOpnameHeader_mobileData dt : dtJson.get_ListOftStockOpnameHeader_mobileData()) {
				tStockOpnameHeader_mobileDA _tStockOpnameHeader_mobileDA=new tStockOpnameHeader_mobileDA(db);
				dt.set_intPush("1");
				_tStockOpnameHeader_mobileDA.SaveDatatStockOpnameHeader_mobileData(db, dt);
			}
		}
		if(dtJson.getListOftLeaveMobileData()!=null){
			for (tLeaveMobileData dt : dtJson.getListOftLeaveMobileData()) {
				tLeaveMobileDA _tLeaveMobileDA=new tLeaveMobileDA(db);
				dt.set_intLeaveIdSync("1");
				_tLeaveMobileDA.SaveDataMConfig(db, dt);
			}
		}
		
		if(dtJson.getListOftAbsenUserData()!=null){
			Iterator j=null;
			j = JsonResult.iterator();
			while(j.hasNext()){
				org.json.simple.JSONObject innerObj_Header = (org.json.simple.JSONObject) j.next();
				org.json.simple.JSONArray JsonArray_Detail=(JSONArray) innerObj_Header.get("ListOftAbsenUser_mobile");
				Iterator jDetail=JsonArray_Detail.iterator();
				while(jDetail.hasNext()){
					org.json.simple.JSONObject innerObj_Detail = (org.json.simple.JSONObject) jDetail.next();
					for (tAbsenUserData dt : dtJson.getListOftAbsenUserData()) {
						tAbsenUserDA _tAbsenUserDA=new tAbsenUserDA(db);
						if(String.valueOf(innerObj_Detail.get("TxtDataIdFromSource")).equals(dt.get_intId())){
							dt.set_intSync("1");
							dt.set_txtAbsen(String.valueOf(innerObj_Detail.get("TxtDataId")));
							_tAbsenUserDA.SaveDatatAbsenUserData(db, dt);
						}
					}		
				}
			}
			
		}
		Iterator j2=null;
		j2 = JsonResult.iterator();
		while(j2.hasNext()){
			org.json.simple.JSONObject innerObj_Header = (org.json.simple.JSONObject) j2.next();
			org.json.simple.JSONArray JsonArray_Detail=(JSONArray) innerObj_Header.get("ListOfclsTnotificationHeader_mobile");
			if(JsonArray_Detail!=null){
				tNotificationBL _tNotificationBL=new tNotificationBL();
				List<tNotificationData> listDatatNotificationData=new ArrayList<tNotificationData>();
				Iterator jDetail=JsonArray_Detail.iterator();
				int index=_tNotificationBL.getContactsCount()+1;
				while(jDetail.hasNext()){
					org.json.simple.JSONObject innerObj_Detail = (org.json.simple.JSONObject) jDetail.next();
					tNotificationData dttNotificationData=new tNotificationData();
					dttNotificationData.set_bitActive(String.valueOf(innerObj_Detail.get("IntActive")));
					dttNotificationData.set_dtPublishEnd(String.valueOf(innerObj_Detail.get("DtEnd")));
					dttNotificationData.set_guiID(String.valueOf(innerObj_Detail.get("TxtIdHeaderNotif")));
					dttNotificationData.set_intIndex(String.valueOf(index));
					dttNotificationData.set_intPriority(String.valueOf(innerObj_Detail.get("IntPriority")));
					dttNotificationData.set_intSubmit("1");
					dttNotificationData.set_intSync("1");
					dttNotificationData.set_tPublishStart(String.valueOf(innerObj_Detail.get("DtStart")));
					dttNotificationData.set_txtBranchCode("");
					dttNotificationData.set_txtDescription(String.valueOf(innerObj_Detail.get("TxtDesc")));
					dttNotificationData.set_txtImage("");
					dttNotificationData.set_txtLink(String.valueOf(innerObj_Detail.get("TxtLink")));
					dttNotificationData.set_txtOutlet("");
					dttNotificationData.set_txtOutletName("");
					dttNotificationData.set_txtStatus("2");
					dttNotificationData.set_txtTitle(String.valueOf(innerObj_Detail.get("TxtTitle")));
					listDatatNotificationData.add(dttNotificationData);
					index+=1;
				}
				_tNotificationBL.saveData(listDatatNotificationData);
			}
		}
		
		if(dtJson.get_ListOftTransactionDetailData()!=null){
			for (tTransactionDetailData dt : dtJson.get_ListOftTransactionDetailData()) {
				tTransactionDetailDA _tTransactionDetailDA=new tTransactionDetailDA(db);
				dt.set_intPush("1");
				_tTransactionDetailDA.SaveDatatTransactionDetailData(db, dt);
			}
		}
		db.close();
	}
	public org.json.simple.JSONArray callPushDataReturnJson(String versionName,String strJson,HashMap<String, String> ListOfDataFile) throws Exception{
		SQLiteDatabase _db=getDb();
		Boolean flag=true;
		String ErrorMess="";
		String txtMethod="PushDataKBN";
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
		dataAPI = _mconfigDA.getData(_db,enumConfigData.BackGroundServiceOnline.getidConfigData());
		String TimeOut = dataAPI.get_txtValue();
		String strLinkAPI= dtlinkAPI.QueryString(strVal2);
		String JsonData=_help.PushDataWithFile(strLinkAPI, strJson, Integer.valueOf(TimeOut), ListOfDataFile);
		//String JsonData= _help.ResultJsonData(_help.getHTML(strLinkAPI));
		org.json.simple.JSONArray JsonArray= _help.ResultJsonArray(JsonData);
		APIData dtAPIDATA=new APIData();
		Iterator i = JsonArray.iterator();
		mCounterNumberDA _mCounterNumberDA=new mCounterNumberDA(_db);
		while (i.hasNext()) {
			org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
			int boolValid= Integer.valueOf(String.valueOf( innerObj.get(dtAPIDATA.boolValid)));
			if(boolValid == Integer.valueOf(new clsHardCode().intSuccess)){
				mCounterNumberData _data =new mCounterNumberData();
				_data.set_intId(enumCounterData.dtPushKBN.getidCounterData());
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
		return JsonArray;
	}

	public org.json.simple.JSONArray GetDatamversionAppPostData(String versionName) throws Exception{
		SQLiteDatabase _db=getDb();
		String txtMethod="GetDatamversionAppPostData";
		linkAPI dtlinkAPI=new linkAPI();
		clsHelper _help =new clsHelper();
		dtlinkAPI=new linkAPI();
		dtlinkAPI.set_txtMethod(txtMethod);
		tUserLoginDA _tUserLoginDA=new tUserLoginDA(_db);
		dtlinkAPI.set_txtParam("|||");
		dtlinkAPI.set_txtToken(new clsHardCode().txtTokenAPI);
		dtlinkAPI.set_txtVesion(versionName);
		String strVal2="";
		mconfigDA _mconfigDA =new mconfigDA(_db);
		mconfigData dataAPI = _mconfigDA.getData(db,enumConfigData.ApiKalbe.getidConfigData());
		strVal2 = dataAPI.get_txtValue();
		if (dataAPI.get_txtValue() == "") {
			strVal2 = dataAPI.get_txtDefaultValue();
		}

		String TimeOut = new clsMainBL().getBackGroundServiceOnline();
		String strLinkAPI= dtlinkAPI.QueryString(strVal2);
		JSONObject resJson = new JSONObject();
		resJson.put("intVersionApp", "");
		resJson.put("txtTypeApp", new clsMainBL().getTypeMobile());
		resJson.put("txtVersion", "");
		resJson.put("status", "1");
		String JsonData=_help.pushtData(strLinkAPI, String.valueOf(resJson), Integer.valueOf(TimeOut));
		//String JsonData= _help.ResultJsonData(_help.getHTML(strLinkAPI));
		org.json.simple.JSONArray JsonArray= _help.ResultJsonArray(JsonData);
		APIData dtAPIDATA=new APIData();
		Iterator i = JsonArray.iterator();
		_db.close();
		return JsonArray;
	}

	public void callPushData(String versionName,String strJson,HashMap<String, String> ListOfDataFile) throws Exception{
		SQLiteDatabase _db=getDb();
		Boolean flag=true;
		String ErrorMess="";
		String txtMethod="PushDataKBN";
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
		dataAPI = _mconfigDA.getData(_db,enumConfigData.BackGroundServiceOnline.getidConfigData());
		String TimeOut = dataAPI.get_txtValue();
		String strLinkAPI= dtlinkAPI.QueryString(strVal2);
		String JsonData=_help.PushDataWithFile(strLinkAPI, strJson, Integer.valueOf(TimeOut), ListOfDataFile);
		//String JsonData= _help.ResultJsonData(_help.getHTML(strLinkAPI));
		org.json.simple.JSONArray JsonArray= _help.ResultJsonArray(JsonData);
		APIData dtAPIDATA=new APIData();
		Iterator i = JsonArray.iterator();
		mCounterNumberDA _mCounterNumberDA=new mCounterNumberDA(_db);
		while (i.hasNext()) {
			org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
			int boolValid= Integer.valueOf(String.valueOf( innerObj.get(dtAPIDATA.boolValid)));
			if(boolValid == Integer.valueOf(new clsHardCode().intSuccess)){
				mCounterNumberData _data =new mCounterNumberData();
				_data.set_intId(enumCounterData.dtPushKBN.getidCounterData());
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
	public clsPushData pushData(){
		clsPushData dtclsPushData = new clsPushData();
		dataJson dtPush=new dataJson();
		SQLiteDatabase db=getDb();
		tUserLoginDA _tUserLoginDA=new tUserLoginDA(db);
		HashMap<String, String> FileUpload=null;
		if(_tUserLoginDA.getContactsCount(db)> 0){
			tUserLoginData _tUserLoginData=_tUserLoginDA.getData(db, 1);
			dtPush.set_txtUserId(_tUserLoginData.get_txtUserId());
			dtPush.set_txtSessionLoginId(_tUserLoginData.get_txtDataId());
			try {
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    		Calendar cal = Calendar.getInstance();
				mCounterNumberDA _mCounterNumberDA=new mCounterNumberDA(db);
				mCounterNumberData _data =new mCounterNumberData();
				_data.set_intId(enumCounterData.MonitorSchedule.getidCounterData());
				_data.set_txtDeskripsi("value menunjukan waktu terakhir menjalankan services");
				_data.set_txtName("Monitor Service");
				_data.set_txtValue(dateFormat.format(cal.getTime()));
				_mCounterNumberDA.SaveDataMConfig(db, _data);
				
				//new clsInit().PushData(db,versionName);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			tAbsenUserDA _tAbsenUserDA =new tAbsenUserDA (db);
			tActivityDA _tActivityDA =new tActivityDA (db);
			tGRNDetail_mobileDA _tGRNDetail_mobileDA =new tGRNDetail_mobileDA (db);
			tGRNHeader_mobileDA _tGRNHeader_mobileDA =new tGRNHeader_mobileDA (db);
			tPenguaranHeader_MobileDA _tPenguaranHeader_MobileDA =new tPenguaranHeader_MobileDA (db);
			tPenguaranDetail_MobileDA _tPenguaranDetail_MobileDA =new tPenguaranDetail_MobileDA (db);
			tPenguaranStatus_MobileDA _tPenguaranStatus_MobileDA =new tPenguaranStatus_MobileDA (db);
			tPOHeader_mobileDA _tPOHeader_mobileDA =new tPOHeader_mobileDA (db);
			tPODetail_mobileDA _tPODetail_mobileDA =new tPODetail_mobileDA (db);
			tPOStatus_mobileDA _tPOStatus_mobileDA =new tPOStatus_mobileDA (db);
			tSalesOrderDetail_MobileDA _tSalesOrderDetail_MobileDA =new tSalesOrderDetail_MobileDA (db);
			tSalesOrderHeader_MobileDA _tSalesOrderHeader_MobileDA =new tSalesOrderHeader_MobileDA(db);
			tTransactionDetailDA _tTransactionDetailDA =new tTransactionDetailDA(db);
			mItemSalesPack_StockDA _mItemSalesPack_StockDA =new mItemSalesPack_StockDA(db);
			mItemSalesPack_StockLogDA _mItemSalesPack_StockLogDA =new mItemSalesPack_StockLogDA(db);
			tStockOpnameDetail_mobileDA _tStockOpnameDetail_mobileDA =new tStockOpnameDetail_mobileDA (db);
			tStockOpnameHeader_mobileDA _tStockOpnameHeader_mobileDA =new tStockOpnameHeader_mobileDA (db);
			tLeaveMobileDA _tLeaveMobileDA =new tLeaveMobileDA(db);
			List<tLeaveMobileData> ListOftLeaveData=_tLeaveMobileDA.getAllDataPushData(db);
			List<tStockOpnameDetail_mobileData> ListOftStockOpnameDetail_mobileData=_tStockOpnameDetail_mobileDA.getAllDataToPushData(db);
			List<tStockOpnameHeader_mobileData> ListOftStockOpnameHeader_mobileData=_tStockOpnameHeader_mobileDA.getAllDataToPushData(db);
			List<tAbsenUserData> ListOftAbsenUserData=_tAbsenUserDA.getAllDataToPushData(db);
			List<tActivityData> ListOftActivityData=_tActivityDA.getAllDataToPushData(db);
			List<tGRNHeader_mobileData> ListOftGRNHeader_mobileData=_tGRNHeader_mobileDA.getAllDataToPushData(db);
			List<tGRNDetail_mobileData> ListOftGRNDetail_mobileData=_tGRNDetail_mobileDA.getAllDataToPushData(db);
			List<tPOHeader_mobileData> ListOftPOHeader_mobileData=_tPOHeader_mobileDA.getAllDataToPushData(db);
			List<tPODetail_mobileData> ListOftPODetail_mobileData=_tPODetail_mobileDA.getAllDataToPushData(db);
			List<tPOStatus_mobileData> ListOftPOStatus_mobileData=_tPOStatus_mobileDA.getAllDataToPushData(db);
			List<tPenguaranDetail_MobileData> ListOftPenguaranDetail_MobileData=_tPenguaranDetail_MobileDA.getAllDataToPushData(db);
			List<tPenguaranHeader_MobileData> ListOftPenguaranHeader_MobileData = _tPenguaranHeader_MobileDA.getAllDataToPushData(db);
			List<tPenguaranStatus_MobileData> ListOftPenguaranStatus_MobileData = _tPenguaranStatus_MobileDA.getAllDataToPushData(db);
			List<tSalesOrderDetail_MobileData> ListOftSalesOrderDetail_MobileData = _tSalesOrderDetail_MobileDA.getAllDataToPushData(db);
			List<tSalesOrderHeader_MobileData> ListOftSalesOrderHeader_MobileData = _tSalesOrderHeader_MobileDA.getAllDataToPushData(db);
			List<tTransactionDetailData> ListOftTransactionDetailData = _tTransactionDetailDA.getAllDataToPushData(db);
			List<mItemSalesPack_StockData> ListOfmItemSalesPack_StockData = _mItemSalesPack_StockDA.getAllDataToPushData(db);
			List<mItemSalesPack_StockLogData> ListOfmItemSalesPack_StockLogData = _mItemSalesPack_StockLogDA.getAllDataToPushData(db);
			
			
			FileUpload=new HashMap<String, String>();
//			if(ListOftAbsenUserData!= null){
//				dtPush.setListOftAbsenUserData(ListOftAbsenUserData);
//				for (tAbsenUserData dttAbsenUserData : ListOftAbsenUserData) {
//					if(dttAbsenUserData.get_txtImg1().equals("null")==false){
//						FileUpload.put("FUAbsen"+dttAbsenUserData.get_intId()+"-1", String.valueOf(dttAbsenUserData.get_txtImg1()));
//					}
//					if(dttAbsenUserData.get_txtImg2().equals("null")==false){
//						FileUpload.put("FUAbsen"+dttAbsenUserData.get_intId()+"-2", String.valueOf(dttAbsenUserData.get_txtImg2()));
//					}
//				}
//			}
			if(ListOftActivityData!=null){
				dtPush.setListOftActivityData(ListOftActivityData);
				for (tActivityData dttActivityData : ListOftActivityData) {
					FileUpload.put("idUploadActivity", "FUAbsen"+dttActivityData.get_intId());
					if(dttActivityData.get_txtImg1()!=null){
						FileUpload.put("FUActivity"+dttActivityData.get_intId()+"-1", "file://"+dttActivityData.get_txtImg1());
					}
					if(dttActivityData.get_txtImg2()!=null){
						FileUpload.put("FUActivity"+dttActivityData.get_intId()+"-2", "file://"+dttActivityData.get_txtImg2());	
					}
				}
			}
			if(ListOftLeaveData!=null){
				dtPush.setListOftLeaveMobileData(ListOftLeaveData);
			}
			if(ListOftPenguaranStatus_MobileData!=null){
				dtPush.set_ListOftPenguaranStatus_MobileData(ListOftPenguaranStatus_MobileData);
			}
			if(ListOftPenguaranHeader_MobileData!=null){
				dtPush.set_ListOftPenguaranHeader_MobileData(ListOftPenguaranHeader_MobileData);
			}
			if(ListOftPenguaranDetail_MobileData!=null){
				dtPush.set_ListOftPenguaranDetail_MobileData(ListOftPenguaranDetail_MobileData);
			}
			if(ListOftStockOpnameHeader_mobileData!=null){
				dtPush.set_ListOftStockOpnameHeader_mobileData(ListOftStockOpnameHeader_mobileData);
			}
			if(ListOftStockOpnameDetail_mobileData!=null){
				dtPush.set_ListOftStockOpnameDetail_mobileData(ListOftStockOpnameDetail_mobileData);
			}
			if(ListOftGRNHeader_mobileData!=null){
				dtPush.set_ListOftGRNHeader_mobileData(ListOftGRNHeader_mobileData);
			}
			if(ListOftGRNDetail_mobileData!=null){
				dtPush.set_ListOftGRNDetail_mobileData(ListOftGRNDetail_mobileData);
			}
			if(ListOftSalesOrderDetail_MobileData!=null){
				dtPush.set_ListOftSalesOrderDetail_MobileData(ListOftSalesOrderDetail_MobileData);
			}
			if(ListOftSalesOrderHeader_MobileData!=null){
				dtPush.set_ListOftSalesOrderHeader_MobileData(ListOftSalesOrderHeader_MobileData);
			}
			if(ListOftPOHeader_mobileData!=null){
				dtPush.set_ListOftPOHeader_mobileData(ListOftPOHeader_mobileData);
			}
			if(ListOftPODetail_mobileData!=null){
				dtPush.set_ListOftPODetail_mobileData(ListOftPODetail_mobileData);
			}
			if(ListOftPOStatus_mobileData!=null){
				dtPush.set_ListOftPOStatus_mobileData(ListOftPOStatus_mobileData);
			}
			if(ListOftTransactionDetailData!=null){
				dtPush.set_ListOftTransactionDetailData(ListOftTransactionDetailData);
			}
			if(ListOfmItemSalesPack_StockData!=null){
				dtPush.set_ListOfmItemSalesPack_StockData(ListOfmItemSalesPack_StockData);
			}
			if(ListOfmItemSalesPack_StockLogData!=null){
				dtPush.set_ListOfmItemSalesPack_StockLogData(ListOfmItemSalesPack_StockLogData);
			}
		}
		else{
			dtPush=null;
		}
		db.close();
		dtclsPushData.setDtdataJson(dtPush);
		dtclsPushData.setFileUpload(FileUpload);
		return dtclsPushData;
	}
}
