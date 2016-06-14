package bl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import library.salesforce.common.APIData;
import library.salesforce.common.clsHelper;
import library.salesforce.common.clsStatusMenuStart;
import library.salesforce.common.dataJson;
import library.salesforce.common.linkAPI;
import library.salesforce.common.mItemSalesPack_StockData;
import library.salesforce.common.mItemSalesPack_StockLogData;
import library.salesforce.common.mMenuData;
import library.salesforce.common.mStockAwalData;
import library.salesforce.common.mconfigData;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tActivityData;
import library.salesforce.common.tCustomerBaseData;
import library.salesforce.common.tCustomerBaseDetailData;
import library.salesforce.common.tGRNDetail_mobileData;
import library.salesforce.common.tGRNHeader_mobileData;
import library.salesforce.common.tLeaveMobileData;
import library.salesforce.common.tPODetail_mobileData;
import library.salesforce.common.tPOHeader_mobileData;
import library.salesforce.common.tPOStatus_mobileData;
import library.salesforce.common.tPenguaranDetail_MobileData;
import library.salesforce.common.tPenguaranHeader_MobileData;
import library.salesforce.common.tPenguaranStatus_MobileData;
import library.salesforce.common.tSalesOrderDetail_MobileData;
import library.salesforce.common.tSalesOrderHeader_MobileData;
import library.salesforce.common.tSalesProductDetailData;
import library.salesforce.common.tSalesProductHeaderData;
import library.salesforce.common.tStockOpnameDetail_mobileData;
import library.salesforce.common.tStockOpnameHeader_mobileData;
import library.salesforce.common.tTransactionDetailData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.enumConfigData;
import library.salesforce.dal.enumStatusMenuStart;
import library.salesforce.dal.mItemSalesPack_StockDA;
import library.salesforce.dal.mItemSalesPack_StockLogDA;
import library.salesforce.dal.mMenuDA;
import library.salesforce.dal.mStockAwalDA;
import library.salesforce.dal.mconfigDA;
import library.salesforce.dal.tAbsenUserDA;
import library.salesforce.dal.tActivityDA;
import library.salesforce.dal.tCustomerBaseDA;
import library.salesforce.dal.tCustomerBaseDetailDA;
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
import library.salesforce.dal.tSalesProductDetailDA;
import library.salesforce.dal.tSalesProductHeaderDA;
import library.salesforce.dal.tStockOpnameDetail_mobileDA;
import library.salesforce.dal.tStockOpnameHeader_mobileDA;
import library.salesforce.dal.tTransactionDetailDA;
import library.salesforce.dal.tUserLoginDA;

public class clsMainBL {
	SQLiteDatabase db;
	public clsMainBL() {
		super();
		this.db = getDb();
	}
	
	public SQLiteDatabase getDb() {
		clsHardCode _clsHardCode;
		clsHelper _clsHelper=new clsHelper();
		_clsHardCode =new clsHardCode();
		_clsHelper.createFolderApp();
		String rootDB = _clsHardCode.txtDatabaseName;
		db=SQLiteDatabase.openOrCreateDatabase(rootDB, null);
		return db;
	}
	public String getLinkAPI(){
		this.db = getDb();
		String txtLinkAPI=new mconfigDA(db).getLinkAPIData(db);
		this.db.close();
		return txtLinkAPI;
	}
	public String getTypeMobile(){
		this.db = getDb();
		String txtLinkAPI=new mconfigDA(db).getTypeMobile(db);
		this.db.close();
		return txtLinkAPI;
	}
	public String getLIVE(){
		this.db = getDb();
		String txtLinkAPI=new mconfigDA(db).getLIVE(db);
		this.db.close();
		return txtLinkAPI;
	}
	public String getBackGroundServiceOnline(){
		this.db = getDb();
		String valueBackGroundServiceOnline=new mconfigDA(db).getBackGroundServiceOnlineData(db);
		this.db.close();
		return valueBackGroundServiceOnline;
	}
	public tUserLoginData getUserActive() {
		this.db = getDb();
		tUserLoginDA _tUserLoginDA=new tUserLoginDA(db);
		List<tUserLoginData> listData=_tUserLoginDA.getUserLoginNow(db);
		db.close();
		return listData.get(0);
	}
	public String GenerateGuid(){
		 UUID uuid = UUID.randomUUID();
		 String randomUUIDString = uuid.toString();
		 return randomUUIDString;
	 }
	public String GenerateGuid(Context context){
		DeviceUuidFactory _DeviceUuidFactory=new DeviceUuidFactory(context);
		return _DeviceUuidFactory.getDeviceUuid().toString();
	 }
	public clsStatusMenuStart checkUserActive() throws ParseException{
		this.db = getDb();
		tSalesProductHeaderDA _tSalesProductHeaderDA=new tSalesProductHeaderDA(db);
		tUserLoginDA _tUserLoginDA=new tUserLoginDA(db);
    	tActivityDA _tActivityDA=new tActivityDA(db);
    	tAbsenUserDA _tAbsenUserDA=new tAbsenUserDA(db);
    	tCustomerBaseDA _tCustomerBaseDA=new tCustomerBaseDA(db);
    	tLeaveMobileDA _tLeaveMobileDA=new tLeaveMobileDA(db);
    	mMenuDA _mMenuDA=new mMenuDA(db);
    	clsStatusMenuStart _clsStatusMenuStart =new clsStatusMenuStart();
    	if(_tUserLoginDA.CheckLoginNow(db)){
    		List<tUserLoginData> listData=_tUserLoginDA.getUserLoginNow(db);
    		_clsStatusMenuStart.set_intStatus(enumStatusMenuStart.UserActiveLogin);
    	}else{
    		Boolean dvalid=false;
    		List<tSalesProductHeaderData> listDataPush= _tSalesProductHeaderDA.getAllDataToPushData(db);
    		List<tActivityData> listtActivityDataPush= _tActivityDA.getAllDataToPushData(db);
    		List<tAbsenUserData> listtAbsenUserDataPush= _tAbsenUserDA.getAllDataToPushData(db);
    		List<tCustomerBaseData> listtCustomerBaseDetailDataPush= _tCustomerBaseDA.getPushData(db);
    		List<tLeaveMobileData> listTLeave= _tLeaveMobileDA.getAllDataPushData(db);
    		if(listDataPush != null && dvalid==false){
    			dvalid=true;
    		}
    		if(listtActivityDataPush != null && dvalid==false){
    			dvalid=true;
    		}
    		if(listtAbsenUserDataPush != null && dvalid==false){
    			dvalid=true;
    		}
    		if(listtCustomerBaseDetailDataPush != null && dvalid==false){
    			dvalid=true;
    		}
    		if(listTLeave != null && dvalid==false){
    			dvalid=true;
    		}
    		if(listDataPush != null && dvalid==false){
    			dvalid=true;
    		}
    		if(dvalid){
    			mMenuData listMenuData= _mMenuDA.getDataByName(db, "mnUploadDataMobile");
    			_clsStatusMenuStart.set_intStatus(enumStatusMenuStart.PushDataSPGMobile);
    			_clsStatusMenuStart.set_mMenuData(listMenuData);
    		}else{
        		new clsHelper().DeleteAllDB(db);
        		_clsStatusMenuStart.set_intStatus(enumStatusMenuStart.FormLogin);
    		}
    	}
    	this.db.close();
    	return _clsStatusMenuStart;
	}
	public dataJson GetAllPushData(String VersionName) throws Exception{
		SQLiteDatabase db=getDb();
		dataJson dtJson=new dataJson();
		tPOHeader_mobileDA _tPOHeader_mobileDA=new tPOHeader_mobileDA(db);
		tPODetail_mobileDA _tPODetail_mobileDA=new tPODetail_mobileDA(db);
		tPOStatus_mobileDA _tPOStatus_mobileDA=new tPOStatus_mobileDA(db);
		tGRNHeader_mobileDA _tGRNHeader_mobileDA=new tGRNHeader_mobileDA(db);
		tGRNDetail_mobileDA _tGRNDetail_mobileDA=new tGRNDetail_mobileDA(db);
		tSalesOrderHeader_MobileDA _tSalesOrderHeader_mobileDA=new tSalesOrderHeader_MobileDA(db);
		tSalesOrderDetail_MobileDA _tSalesOrderDetail_MobileDA=new tSalesOrderDetail_MobileDA(db);
		tPenguaranHeader_MobileDA _tPenguaranHeader_MobileDA=new tPenguaranHeader_MobileDA(db);
		tPenguaranDetail_MobileDA _tPenguaranDetail_MobileDA=new tPenguaranDetail_MobileDA(db);
		tPenguaranStatus_MobileDA _tPenguaranStatus_MobileDA=new tPenguaranStatus_MobileDA(db);
		tStockOpnameHeader_mobileDA _tStockOpnameHeader_MobileDA=new tStockOpnameHeader_mobileDA(db);
		tStockOpnameDetail_mobileDA _tStockOpnameDetail_mobileDA=new tStockOpnameDetail_mobileDA(db);
		tTransactionDetailDA _tTransactionDetailDA=new tTransactionDetailDA(db);
		mItemSalesPack_StockDA _mItemSalesPack_StockDA=new mItemSalesPack_StockDA(db);
		mStockAwalDA _mStockAwalDA=new mStockAwalDA(db);
		mItemSalesPack_StockLogDA _mItemSalesPack_StockLogDA=new mItemSalesPack_StockLogDA(db);
		tAbsenUserDA _tAbsenUserDA=new tAbsenUserDA(db);
		tLeaveMobileDA _tLeaveMobileDA=new tLeaveMobileDA(db);
		
		List<tPOHeader_mobileData> listOftPOHeader_mobileData=_tPOHeader_mobileDA.getAllDataToPushData(db);
		dtJson.set_ListOftPOHeader_mobileData(listOftPOHeader_mobileData);
		List<tPODetail_mobileData> listOftPODetail_mobileData=_tPODetail_mobileDA.getAllDataToPushData(db);
		dtJson.set_ListOftPODetail_mobileData(listOftPODetail_mobileData);
		List<tPOStatus_mobileData> listOftPOStatus_mobileData=_tPOStatus_mobileDA.getAllDataToPushData(db);
		dtJson.set_ListOftPOStatus_mobileData(listOftPOStatus_mobileData);
		
		List<tGRNHeader_mobileData> listOftGRNHeader_mobileData=_tGRNHeader_mobileDA.getAllDataToPushData(db);
		dtJson.set_ListOftGRNHeader_mobileData(listOftGRNHeader_mobileData);
		List<tGRNDetail_mobileData> listOftGRNDetail_mobileData=_tGRNDetail_mobileDA.getAllDataToPushData(db);
		dtJson.set_ListOftGRNDetail_mobileData(listOftGRNDetail_mobileData);
		
		List<tSalesOrderHeader_MobileData> listOftSalesOrderHeader_mobileData=_tSalesOrderHeader_mobileDA.getAllDataToPushData(db);
		dtJson.set_ListOftSalesOrderHeader_MobileData(listOftSalesOrderHeader_mobileData);
		List<tSalesOrderDetail_MobileData> listOftSalesOrderDetail_mobileData=_tSalesOrderDetail_MobileDA.getAllDataToPushData(db);
		dtJson.set_ListOftSalesOrderDetail_MobileData(listOftSalesOrderDetail_mobileData);
		
		List<tPenguaranHeader_MobileData> listOftPenguaranHeader_MobileData=_tPenguaranHeader_MobileDA.getAllDataToPushData(db);
		dtJson.set_ListOftPenguaranHeader_MobileData(listOftPenguaranHeader_MobileData);
		List<tPenguaranDetail_MobileData> listOftPenguaranDetail_MobileData=_tPenguaranDetail_MobileDA.getAllDataToPushData(db);
		dtJson.set_ListOftPenguaranDetail_MobileData(listOftPenguaranDetail_MobileData);
		List<tPenguaranStatus_MobileData> listOftPenguaranStatus_MobileData=_tPenguaranStatus_MobileDA.getAllDataToPushData(db);
		dtJson.set_ListOftPenguaranStatus_MobileData(listOftPenguaranStatus_MobileData);
		
		List<tStockOpnameHeader_mobileData> listOftStockOpnameHeader_MobileData=_tStockOpnameHeader_MobileDA.getAllDataToPushData(db);
		dtJson.set_ListOftStockOpnameHeader_mobileData(listOftStockOpnameHeader_MobileData);
		List<tStockOpnameDetail_mobileData> listOftStockOpnameDetail_MobileData=_tStockOpnameDetail_mobileDA.getAllDataToPushData(db);
		dtJson.set_ListOftStockOpnameHeader_mobileData(listOftStockOpnameHeader_MobileData);
		
		List<tTransactionDetailData> listOftTransactionDetailData=_tTransactionDetailDA.getAllDataToPushData(db);
		dtJson.set_ListOftTransactionDetailData(listOftTransactionDetailData);
		List<mItemSalesPack_StockData> listOfmItemSalesPack_StockData=_mItemSalesPack_StockDA.getAllDataToPushData(db);
		dtJson.set_ListOftTransactionDetailData(listOftTransactionDetailData);
		List<mItemSalesPack_StockLogData> listOfmItemSalesPack_StockLogData=_mItemSalesPack_StockLogDA.getAllDataToPushData(db);
		dtJson.set_ListOftTransactionDetailData(listOftTransactionDetailData);
		List<tAbsenUserData> listOftAbsenUserData=_tAbsenUserDA.getAllDataToPushData(db);
		dtJson.set_ListOftTransactionDetailData(listOftTransactionDetailData);
		List<tLeaveMobileData> listOftLeaveMobileData=_tLeaveMobileDA.getAllDataPushData(db);
		dtJson.set_ListOftTransactionDetailData(listOftTransactionDetailData);
		List<mStockAwalData> listOfmStockAwalData=_mStockAwalDA.getAllDataPushData(db);
		dtJson.set_ListOfmStockAwalData(listOfmStockAwalData);
		db.close();
		return dtJson;
	}
	public void PushData(String VersionName) throws Exception{
		SQLiteDatabase _db=getDb();
		mconfigDA _mconfigDA =new mconfigDA(_db);
		String _StrLINKAPI="";
		mconfigData dataAPI = _mconfigDA.getData(_db,enumConfigData.ApiKalbe.getidConfigData());
		_StrLINKAPI = dataAPI.get_txtValue();
		if (dataAPI.get_txtValue() == "") {
			_StrLINKAPI = dataAPI.get_txtDefaultValue();
		}
		clsHelper _help =new clsHelper();
		String root = new clsHardCode().txtPathUserData;
		File myDir = new File(root);
		myDir.mkdirs();
		linkAPI dtlinkAPI=new linkAPI();
		String txtMethod="PushDataTabsenUser";
		dtlinkAPI.set_txtMethod(txtMethod);
		dtlinkAPI.set_txtParam("");
		dtlinkAPI.set_txtToken(new clsHardCode().txtTokenAPI);
		dtlinkAPI.set_txtVesion(VersionName);
		String strLinkAPI= dtlinkAPI.QueryString(_StrLINKAPI);
		
		dataAPI = _mconfigDA.getData(_db,enumConfigData.BackGroundServiceOnline.getidConfigData());
		String TimeOut = dataAPI.get_txtValue();
		if (dataAPI.get_txtValue() == "") {
			TimeOut = dataAPI.get_txtDefaultValue();
		}
		
		tAbsenUserDA _tAbsenUserDA=new tAbsenUserDA(_db);
		List<tAbsenUserData> listDataAbsen= _tAbsenUserDA.getAllDataToPushData(_db);
		if(listDataAbsen != null){
			for (tAbsenUserData dataAbsen : listDataAbsen) {
				List<tAbsenUserData> tmplistDataAbsen=new ArrayList<tAbsenUserData>();
				tmplistDataAbsen.add(dataAbsen);
				dataJson Json= new dataJson();
				Json.setIntResult("1");
				Json.setListOftAbsenUserData(tmplistDataAbsen);
				//String Html= new clsHelper().pushtData(strLinkAPI,Json.txtJSON().toString(),Integer.valueOf(TimeOut));
				String Html= new clsHelper().PushDataWithFile(strLinkAPI,Json.txtJSON().toString(),Integer.valueOf(TimeOut),dataAbsen.get_txtImg1(),dataAbsen.get_txtImg2());
				org.json.simple.JSONArray JsonArray= _help.ResultJsonArray(Html);
				Iterator i = JsonArray.iterator();
				while (i.hasNext()) {
					APIData dtAPIDATA=new APIData();
					org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
					int boolValid= Integer.valueOf(String.valueOf( innerObj.get(dtAPIDATA.boolValid)));
					if(boolValid == Integer.valueOf(new clsHardCode().intSuccess)){
						dataAbsen.set_intSync("1");
						dataAbsen.set_txtAbsen(String.valueOf(innerObj.get(dtAPIDATA.strArgument)));
						_tAbsenUserDA.SaveDatatAbsenUserData(_db, dataAbsen);
					}
				}
			}
		}
		
		dtlinkAPI=new linkAPI();
		txtMethod="PushDataTHeaderSales";
		dtlinkAPI.set_txtMethod(txtMethod);
		dtlinkAPI.set_txtParam("");
		dtlinkAPI.set_txtToken(new clsHardCode().txtTokenAPI);
		dtlinkAPI.set_txtVesion(VersionName);
		strLinkAPI= dtlinkAPI.QueryString(_StrLINKAPI);
		
		tSalesProductHeaderDA _tSalesProductHeaderDA=new tSalesProductHeaderDA(_db);
		List<tSalesProductHeaderData> ListDataTsalesHeader= _tSalesProductHeaderDA.getAllDataToPushData(_db);
		if(ListDataTsalesHeader!=null){
			for (tSalesProductHeaderData dataheader : ListDataTsalesHeader) {
				dataJson Json= new dataJson();
				List<tSalesProductHeaderData> tmplistDatatSalesProductHeaderData=new ArrayList<tSalesProductHeaderData>();
				tmplistDatatSalesProductHeaderData.add(dataheader);
				tAbsenUserData _tmpdataabsen= _tAbsenUserDA.getData(_db, Integer.valueOf(dataheader.get_intIdAbsenUser()));
				List<tAbsenUserData> tmpListDataUserAbsen=new ArrayList<tAbsenUserData>();
				tmpListDataUserAbsen.add(_tmpdataabsen);
				tSalesProductDetailDA _tSalesProductDetailDA=new tSalesProductDetailDA(_db);
				List<tSalesProductDetailData> tmpListSalesProductDetail= _tSalesProductDetailDA.getSalesProductDetailByHeaderId(_db, dataheader.get_intId());
				if(tmpListSalesProductDetail!=null){
					Json.setListOftSalesProductDetailData(tmpListSalesProductDetail);
				}
				Json.setListOftAbsenUserData(tmpListDataUserAbsen);
				Json.setListOftSalesProductHeaderData(tmplistDatatSalesProductHeaderData);
				String Html= new clsHelper().pushtData(strLinkAPI,Json.txtJSON().toString(),Integer.valueOf(TimeOut));
				org.json.simple.JSONArray JsonArray= _help.ResultJsonArray(Html);
				Iterator i = JsonArray.iterator();
				while (i.hasNext()) {
					APIData dtAPIDATA=new APIData();
					org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
					int boolValid= Integer.valueOf(String.valueOf( innerObj.get(dtAPIDATA.boolValid)));
					if(boolValid == Integer.valueOf(new clsHardCode().intSuccess)){
						dataheader.set_intSync("1");
						_tSalesProductHeaderDA.SaveDatatSalesProductHeaderData(_db, dataheader);
					}
				}
			}
		}
		
		dtlinkAPI=new linkAPI();
		txtMethod="SaveDataTActivityMobile";
		dtlinkAPI.set_txtMethod(txtMethod);
		dtlinkAPI.set_txtParam("");
		dtlinkAPI.set_txtToken(new clsHardCode().txtTokenAPI);
		dtlinkAPI.set_txtVesion(VersionName);
		strLinkAPI= dtlinkAPI.QueryString(_StrLINKAPI);
		
		tActivityDA _tActivityDA=new tActivityDA(_db);
		List<tActivityData> ListDatatActivityData= _tActivityDA.getAllDataToPushData(_db);
		if(ListDatatActivityData!=null){
			for (tActivityData dataActivity : ListDatatActivityData) {
				List<tActivityData> tmpListDatatActivityData=new ArrayList<tActivityData>();
				tmpListDatatActivityData.add(dataActivity);
				dataJson Json= new dataJson();
				Json.setIntResult("1");
				Json.setListOftActivityData(tmpListDatatActivityData);
				//
				String Html= new clsHelper().PushDataWithFile(strLinkAPI,Json.txtJSON().toString(),Integer.valueOf(TimeOut),dataActivity.get_txtImg1(),dataActivity.get_txtImg2());
				org.json.simple.JSONArray JsonArray= _help.ResultJsonArray(Html);
				Iterator i = JsonArray.iterator();
				while (i.hasNext()) {
					APIData dtAPIDATA=new APIData();
					org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
					int boolValid= Integer.valueOf(String.valueOf( innerObj.get(dtAPIDATA.boolValid)));
					if(boolValid == Integer.valueOf(new clsHardCode().intSuccess)){
						dataActivity.set_intIdSyn(String.valueOf(innerObj.get(dtAPIDATA.strArgument)));
						_tActivityDA.SaveDatatActivityData(_db, dataActivity);
					}
				}
			}
		}
		
		dtlinkAPI=new linkAPI();
		txtMethod="APISaveDatatCustomerBase";
		dtlinkAPI.set_txtMethod(txtMethod);
		dtlinkAPI.set_txtParam("");
		dtlinkAPI.set_txtToken(new clsHardCode().txtTokenAPI);
		dtlinkAPI.set_txtVesion(VersionName);
		strLinkAPI= dtlinkAPI.QueryString(_StrLINKAPI);
		
		tCustomerBaseDA _tCustomerBaseDA=new tCustomerBaseDA(_db);
		tCustomerBaseDetailDA _tCustomerBaseDetailDA=new tCustomerBaseDetailDA(_db);
		
		List<tCustomerBaseData> tmpListOftCustomerBaseData=new ArrayList<tCustomerBaseData>();
		List<tCustomerBaseDetailData> tmpListOftCustomerBaseDetailData=new ArrayList<tCustomerBaseDetailData>();
		List<tCustomerBaseData> ListDatatCustomerBase= _tCustomerBaseDA.getPushData(_db);
		if(ListDatatCustomerBase!=null){
			for (tCustomerBaseData dataActivity : ListDatatCustomerBase) {
				tmpListOftCustomerBaseData.add(dataActivity);
				tmpListOftCustomerBaseDetailData=_tCustomerBaseDetailDA.getDataByCustomerId(_db, dataActivity.get_intCustomerId());
				
				dataJson Json= new dataJson();
				Json.setIntResult("1");
				Json.setListOftCustomerBase(tmpListOftCustomerBaseData);
				Json.setListOftCustomerBaseDetailData(tmpListOftCustomerBaseDetailData);
				//
				String Html= new clsHelper().pushtData(strLinkAPI,Json.txtJSON().toString(),Integer.valueOf(TimeOut));
				org.json.simple.JSONArray JsonArray= _help.ResultJsonArray(Html);
				Iterator i = JsonArray.iterator();
				while (i.hasNext()) {
					APIData dtAPIDATA=new APIData();
					org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
					int boolValid= Integer.valueOf(String.valueOf( innerObj.get(dtAPIDATA.boolValid)));
					if(boolValid == Integer.valueOf(new clsHardCode().intSuccess)){
						dataActivity.set_intCustomerIdSync(String.valueOf(innerObj.get(dtAPIDATA.strArgument)));
						_tCustomerBaseDA.SaveDatatCustomerBaseData(_db, dataActivity);
					}
				}
			}
		}
		
		dtlinkAPI=new linkAPI();
		txtMethod="SaveDatatLeaveMobile";
		dtlinkAPI.set_txtMethod(txtMethod);
		dtlinkAPI.set_txtParam("");
		dtlinkAPI.set_txtToken(new clsHardCode().txtTokenAPI);
		dtlinkAPI.set_txtVesion(VersionName);
		strLinkAPI= dtlinkAPI.QueryString(_StrLINKAPI);
		
		tLeaveMobileDA _tLeaveMobileDA=new tLeaveMobileDA(_db);
		List<tLeaveMobileData> listoftLeaveMobileData= _tLeaveMobileDA.getAllDataPushData(_db);
		if(listoftLeaveMobileData!=null){
			for (tLeaveMobileData dttLeaveMobileData : listoftLeaveMobileData) {
				//_tLeaveMobileDA.deleteContact(_db, dttLeaveMobileData.get_intLeaveIdSync());
				List<tLeaveMobileData> tmpListDatatLeaveMobileData=new ArrayList<tLeaveMobileData>();
				tmpListDatatLeaveMobileData.add(dttLeaveMobileData);
				dataJson Json= new dataJson();
				Json.setIntResult("1");
				Json.setListOftLeaveMobileData(tmpListDatatLeaveMobileData);
				String Html= new clsHelper().pushtData(strLinkAPI,Json.txtJSON().toString(),Integer.valueOf(TimeOut));
				org.json.simple.JSONArray JsonArray= _help.ResultJsonArray(Html);
				Iterator i = JsonArray.iterator();
				while (i.hasNext()) {
					APIData dtAPIDATA=new APIData();
					org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
					int boolValid= Integer.valueOf(String.valueOf( innerObj.get(dtAPIDATA.boolValid)));
					if(boolValid == Integer.valueOf(new clsHardCode().intSuccess)){
						dttLeaveMobileData.set_intLeaveIdSync(String.valueOf(innerObj.get(dtAPIDATA.strArgument)));
						_tLeaveMobileDA.SaveDataMConfig(_db, dttLeaveMobileData);
					}
				}
			}
		}
		_db.close();
	}
}
