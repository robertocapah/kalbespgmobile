package bl;

import android.database.sqlite.SQLiteDatabase;

import com.kalbenutritionals.app.kalbespgmobile.clsMainActivity;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import library.salesforce.common.mCounterNumberData;
import library.salesforce.common.tGRNDetail_mobileData;
import library.salesforce.common.tGRNHeader_mobileData;
import library.salesforce.common.tPenguaranDetail_MobileData;
import library.salesforce.common.tPenguaranHeader_MobileData;
import library.salesforce.common.tSalesOrderDetail_MobileData;
import library.salesforce.common.tSalesOrderHeader_MobileData;
import library.salesforce.common.tStockOpnameDetail_mobileData;
import library.salesforce.common.tStockOpnameHeader_mobileData;
import library.salesforce.common.tTransactionDetailData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.enumCounterData;
import library.salesforce.dal.mCounterNumberDA;
import library.salesforce.dal.tGRNDetail_mobileDA;
import library.salesforce.dal.tGRNHeader_mobileDA;
import library.salesforce.dal.tPenguaranDetail_MobileDA;
import library.salesforce.dal.tPenguaranHeader_MobileDA;
import library.salesforce.dal.tSalesOrderDetail_MobileDA;
import library.salesforce.dal.tSalesOrderHeader_MobileDA;
import library.salesforce.dal.tStockOpnameDetail_mobileDA;
import library.salesforce.dal.tStockOpnameHeader_mobileDA;
import library.salesforce.dal.tTransactionDetailDA;

public class tTransactionDetailBL extends clsMainBL {
	public List<tTransactionDetailData> getData(String id){
		SQLiteDatabase db=getDb();
		List<tTransactionDetailData> listData=new ArrayList<tTransactionDetailData>();
		tTransactionDetailDA _tTransactionDetailDA=new tTransactionDetailDA(db);
		if(id.equals("")){
			listData=_tTransactionDetailDA.getAllData(db);
			if(listData!=null)
				listData=new ArrayList<tTransactionDetailData>();
		}else{
			tTransactionDetailData dt=new tTransactionDetailData();
			dt=_tTransactionDetailDA.getData(db, id);
			if(dt!=null)
				dt=new tTransactionDetailData();
			listData.add(dt);
		}
		return listData;
	}
	public List<tTransactionDetailData> getAllDataBatchFromIntProductCodeBatchNo(String intProductCode,String txtBatchNo){
		SQLiteDatabase db=getDb();
		List<tTransactionDetailData> listData=new ArrayList<tTransactionDetailData>();
		tTransactionDetailDA _tTransactionDetailDA=new tTransactionDetailDA(db);
		listData=_tTransactionDetailDA.getAllDataBatchFromIntProductCodeBatchNo(db, intProductCode, txtBatchNo);
		return listData;
	}
	public List<tTransactionDetailData> getAllDataFromIntProductCodeBatchNo(String intProductCode,String txtBatchNo){
		SQLiteDatabase db=getDb();
		List<tTransactionDetailData> listData=new ArrayList<tTransactionDetailData>();
		tTransactionDetailDA _tTransactionDetailDA=new tTransactionDetailDA(db);
		listData=_tTransactionDetailDA.getAllDataFromIntProductCodeBatchNo(db, intProductCode, txtBatchNo);
		return listData;
	}
	public tTransactionDetailData getCalculateQtyByProductId(String txtBatch,String dtED,String intProductCode){
		SQLiteDatabase db=getDb();
		tTransactionDetailData listData=new tTransactionDetailData();
		tTransactionDetailDA _tTransactionDetailDA=new tTransactionDetailDA(db);
		listData=_tTransactionDetailDA.getCalculateQtyByProductId(db, txtBatch, dtED, intProductCode);
		return listData;
	}
	public void saveData(tTransactionDetailData dt){
		SQLiteDatabase db=getDb();
		tTransactionDetailDA _tTransactionDetailDA=new tTransactionDetailDA(db);
		_tTransactionDetailDA.SaveDatatTransactionDetailData(db, dt);
	}
	public void saveData(List<tTransactionDetailData> dt){
		SQLiteDatabase db=getDb();
		tTransactionDetailDA _tTransactionDetailDA=new tTransactionDetailDA(db);
		for (tTransactionDetailData tTransactionDetailData : dt) {
			_tTransactionDetailDA.SaveDatatTransactionDetailData(db, tTransactionDetailData);	
		}
	}
	public org.json.simple.JSONArray getCalculateQtyForReportED(String txtOutletCode,String txtProductName) throws JSONException{
		SQLiteDatabase db=getDb();
		tTransactionDetailDA _tTransactionDetailDA=new tTransactionDetailDA(db);
		
		org.json.simple.JSONArray JsonArray=new org.json.simple.JSONArray();
		JsonArray=_tTransactionDetailDA.getCalculateQtyForReportExpiredDate(db,txtOutletCode,txtProductName);
		
		return JsonArray;
	}
	public org.json.simple.JSONArray getCalculateQtyForReportSO(String txtOutletCode,String txtProductName) throws JSONException{
		SQLiteDatabase db=getDb();
		tTransactionDetailDA _tTransactionDetailDA=new tTransactionDetailDA(db);
		
		org.json.simple.JSONArray JsonArray=new org.json.simple.JSONArray();
		JsonArray=_tTransactionDetailDA.getCalculateQtyForReportPenjualanSO(db,txtOutletCode,txtProductName);
		
		return JsonArray;
	}
	public org.json.simple.JSONArray getCalculateQtyForReportMutasi(String txtOutletCode,String txtProductName) throws JSONException{
		SQLiteDatabase db=getDb();
		tTransactionDetailDA _tTransactionDetailDA=new tTransactionDetailDA(db);
		
		org.json.simple.JSONArray JsonArray=new org.json.simple.JSONArray();
		JsonArray=_tTransactionDetailDA.getCalculateQtyForReportMutasi(db,txtOutletCode,txtProductName);
		
		return JsonArray;
	}
	public org.json.simple.JSONArray getCalculateQtyForReportAvailableVsStock(String txtOutletCode,String txtProductName) throws JSONException{
		SQLiteDatabase db=getDb();
		tTransactionDetailDA _tTransactionDetailDA=new tTransactionDetailDA(db);
		
		org.json.simple.JSONArray JsonArray=new org.json.simple.JSONArray();
		JsonArray=_tTransactionDetailDA.getCalculateQtyForReportAvailableVsStock(db,txtOutletCode,txtProductName);
		
		return JsonArray;
	}
	public org.json.simple.JSONArray getCalculateQtyForReport(String txtOutletCode,String txtProductName) throws JSONException{
		SQLiteDatabase db=getDb();
		tTransactionDetailDA _tTransactionDetailDA=new tTransactionDetailDA(db);
		
		org.json.simple.JSONArray JsonArray=new org.json.simple.JSONArray();
		JsonArray=_tTransactionDetailDA.getCalculateQtyForReport(db,txtOutletCode,txtProductName);
		
		return JsonArray;
	}
	public void CalculateProductFromPenguaran(String txtNo){
		SQLiteDatabase db=getDb();
		tPenguaranHeader_MobileDA _tPenguaranHeader_MobileDA=new tPenguaranHeader_MobileDA(db);
		tPenguaranDetail_MobileDA _tPenguaranDetail_MobileDA=new tPenguaranDetail_MobileDA(db);
		tTransactionDetailDA _tTransactionDetailDA=new tTransactionDetailDA(db);
		tPenguaranHeader_MobileData dtHeader=_tPenguaranHeader_MobileDA.getData(db, txtNo);
		List<tPenguaranDetail_MobileData> dtDetail=_tPenguaranDetail_MobileDA.getCalculateProductGroupByintProductCodetxtBatchNodtED(db, txtNo);
		mCounterNumberDA _mCounterNumberDA=new mCounterNumberDA(db);
		mCounterNumberData dtmCounterNumberDataDatePartNow=_mCounterNumberDA.getData(db, enumCounterData.DatePartNow.getidCounterData());
		clsMainActivity _clsMainActivity=new clsMainActivity();
		for(tPenguaranDetail_MobileData dt :  dtDetail){
			tTransactionDetailData dtcurr=new tTransactionDetailData();
			//dtcurr.set_dtDate(dtHeader.get_dtDate());
			dtcurr.set_dtDate(_clsMainActivity.FormatDateComplete());
			dtcurr.set_dtED(dt.get_dtED());
			dtcurr.set_intProductCode(dt.get_intProductCode());
			dtcurr.set_intPush("0");
			dtcurr.set_intSubmit("1");
			dtcurr.set_intQty(dt.get_intQty());
			dtcurr.set_intWeek(dtmCounterNumberDataDatePartNow.get_txtValue());
			dtcurr.set_txtBatchNo(dt.get_txtBatchNo());
			dtcurr.set_txtNoDoc(dtHeader.get_txtNoPenguaran());
			dtcurr.set_txtOutletCode(dtHeader.get_txtOutletCode());
			dtcurr.set_txtBranchCode(dtHeader.get_txtBranchCode());
			dtcurr.set_txtOutletName(dtHeader.get_txtOutletName());
			dtcurr.set_txtTransId(GenerateGuid());
			dtcurr.set_txtType(new clsHardCode().txtStatus_TypeDataPengeluaran);
			_tTransactionDetailDA.SaveDatatTransactionDetailData(db, dtcurr);
		}
	}
	public void CalculateProductFromSO(String txtNo){
		SQLiteDatabase db=getDb();
		tSalesOrderHeader_MobileDA _tSalesOrderHeader_MobileDA=new tSalesOrderHeader_MobileDA(db);
		tSalesOrderDetail_MobileDA _tSalesOrderDetail_MobileDA=new tSalesOrderDetail_MobileDA(db);
		tTransactionDetailDA _tTransactionDetailDA=new tTransactionDetailDA(db);
		tSalesOrderHeader_MobileData dtHeader=_tSalesOrderHeader_MobileDA.getData(db, txtNo);
		List<tSalesOrderDetail_MobileData> dtDetail=_tSalesOrderDetail_MobileDA.getCalculateProductGroupByintProductCodetxtBatchNodtED(db, txtNo);
		mCounterNumberDA _mCounterNumberDA=new mCounterNumberDA(db);
		mCounterNumberData dtmCounterNumberDataDatePartNow=_mCounterNumberDA.getData(db, enumCounterData.DatePartNow.getidCounterData());
		clsMainActivity _clsMainActivity=new clsMainActivity();
		for(tSalesOrderDetail_MobileData dt :  dtDetail){
			tTransactionDetailData dtcurr=new tTransactionDetailData();
			//dtcurr.set_dtDate(dtHeader.get_dtDate());
			dtcurr.set_dtDate(_clsMainActivity.FormatDateComplete());
			dtcurr.set_dtED(dt.get_dtED());
			dtcurr.set_intProductCode(dt.get_intProductCode());
			dtcurr.set_intPush("0");
			dtcurr.set_intSubmit("1");
			dtcurr.set_intQty(dt.get_intQty());
			dtcurr.set_intWeek(dtmCounterNumberDataDatePartNow.get_txtValue());
			dtcurr.set_txtBatchNo(dt.get_txtBatchNo());
			dtcurr.set_txtNoDoc(dtHeader.get_txtNoSalesOrder());
			dtcurr.set_txtOutletCode(dtHeader.get_txtOutletCode());
			dtcurr.set_txtBranchCode(dtHeader.get_txtBranchCode());
			dtcurr.set_txtOutletName(dtHeader.get_txtOutletName());
			dtcurr.set_txtTransId(GenerateGuid());
			dtcurr.set_txtType(new clsHardCode().txtStatus_TypeDataRESO);
			_tTransactionDetailDA.SaveDatatTransactionDetailData(db, dtcurr);
		}
	}
	public void CalculateProductFromOpname(String txtNo){
		SQLiteDatabase db=getDb();
		tStockOpnameDetail_mobileDA _tStockOpnameDetail_mobileDA=new tStockOpnameDetail_mobileDA(db);
		tStockOpnameHeader_mobileDA _tStockOpnameHeader_mobileDA=new tStockOpnameHeader_mobileDA(db);
		tTransactionDetailDA _tTransactionDetailDA=new tTransactionDetailDA(db);
		tStockOpnameHeader_mobileData dtHeader=_tStockOpnameHeader_mobileDA.getData(db, txtNo);
		//List<tStockOpnameDetail_mobileData> dtDetail=_tStockOpnameDetail_mobileDA.getCalculateProduct(db, txtNo);
		List<tStockOpnameDetail_mobileData> dtDetail=_tStockOpnameDetail_mobileDA.getAllDataByHeaderId(db, txtNo);
		mCounterNumberDA _mCounterNumberDA=new mCounterNumberDA(db);
		mCounterNumberData dtmCounterNumberDataDatePartNow=_mCounterNumberDA.getData(db, enumCounterData.DatePartNow.getidCounterData());
		clsMainActivity _clsMainActivity=new clsMainActivity();
		for(tStockOpnameDetail_mobileData dt :  dtDetail){
			tTransactionDetailData dtcurr=new tTransactionDetailData();
			dtcurr.set_dtDate(_clsMainActivity.FormatDateComplete());
			//dtcurr.set_dtDate(dtHeader.get_dtDate());
			dtcurr.set_dtED(dt.get_dtED());
			dtcurr.set_intProductCode(dt.get_intProductCode());
			dtcurr.set_intPush("0");
			dtcurr.set_intSubmit("1");
			dtcurr.set_intQty(dt.get_intQtyAdj());
			dtcurr.set_intWeek(dtmCounterNumberDataDatePartNow.get_txtValue());
			dtcurr.set_txtBatchNo(dt.get_txtBatchNo());
			dtcurr.set_txtNoDoc(dtHeader.get_txtNoAdj());
			dtcurr.set_txtOutletCode(dtHeader.get_txtOutletCode());
			dtcurr.set_txtBranchCode(dtHeader.get_txtBranchCode());
			dtcurr.set_txtOutletName(dtHeader.get_txtOutletName());
			dtcurr.set_txtTransId(GenerateGuid());
			dtcurr.set_txtType(new clsHardCode().txtStatus_TypeDataStockOpname);
			_tTransactionDetailDA.SaveDatatTransactionDetailData(db, dtcurr);
		}
	}
	public void CalculateProductFromGRN(String txtNo){
		SQLiteDatabase db=getDb();
		tGRNHeader_mobileDA _tGRNHeader_mobileDA=new tGRNHeader_mobileDA(db);
		tGRNDetail_mobileDA _tGRNDetail_mobileDA=new tGRNDetail_mobileDA(db);
		tTransactionDetailDA _tTransactionDetailDA=new tTransactionDetailDA(db);
		tGRNHeader_mobileData dtHeader=_tGRNHeader_mobileDA.getData(db, txtNo);
		List<tGRNDetail_mobileData> dtDetail=_tGRNDetail_mobileDA.getCalculateProductGroupByintProductCodetxtBatchNodtED(db, txtNo);
		mCounterNumberDA _mCounterNumberDA=new mCounterNumberDA(db);
		mCounterNumberData dtmCounterNumberDataDatePartNow=_mCounterNumberDA.getData(db, enumCounterData.DatePartNow.getidCounterData());
		clsMainActivity _clsMainActivity=new clsMainActivity();
		for(tGRNDetail_mobileData dt :  dtDetail){
			tTransactionDetailData dtcurr=new tTransactionDetailData();
			//dtcurr.set_dtDate(dtHeader.get_dtDate());
			dtcurr.set_dtDate(_clsMainActivity.FormatDateComplete());
			dtcurr.set_dtED(dt.get_dtED());
			dtcurr.set_intProductCode(dt.get_intProductCode());
			dtcurr.set_intPush("0");
			dtcurr.set_intSubmit("1");
			dtcurr.set_intQty(dt.get_intQty());
			dtcurr.set_intWeek(dtmCounterNumberDataDatePartNow.get_txtValue());
			dtcurr.set_txtBatchNo(dt.get_txtBatchNo());
			dtcurr.set_txtNoDoc(dtHeader.get_txtNoGRN());
			dtcurr.set_txtNoMO(dtHeader.get_txtNoMO());
			dtcurr.set_txtOutletCode(dtHeader.get_txtOutletCode());
			dtcurr.set_txtBranchCode(dtHeader.get_txtBranchCode());
			dtcurr.set_txtOutletName(dtHeader.get_txtOutletName());
			dtcurr.set_txtTransId(GenerateGuid());
			dtcurr.set_txtType(new clsHardCode().txtStatus_TypeDataGRN);
			_tTransactionDetailDA.SaveDatatTransactionDetailData(db, dtcurr);
		}
	}
}
