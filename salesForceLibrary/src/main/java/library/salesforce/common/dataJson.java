package library.salesforce.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class dataJson {

	public synchronized List<tEmployeeBRWithLOBData> getListOftEmployeeBRWithLOBData() {
		return ListOftEmployeeBRWithLOBData;
	}
	public synchronized void setListOftEmployeeBRWithLOBData(
			List<tEmployeeBRWithLOBData> listOftEmployeeBRWithLOBData) {
		ListOftEmployeeBRWithLOBData = listOftEmployeeBRWithLOBData;
	}
	
	public synchronized List<tUserLOBData> getListOftUserLOBData() {
		return ListOftUserLOBData;
	}
	public synchronized void setListOftUserLOBData(
			List<tUserLOBData> listOftUserLOBData) {
		ListOftUserLOBData = listOftUserLOBData;
	}
	
	public synchronized List<tReportInventorySPGData> getListOftReportInventorySPGData() {
		return ListOftReportInventorySPGData;
	}
	public synchronized void setListOftReportInventorySPGData(
			List<tReportInventorySPGData> listOftReportInventorySPGData) {
		ListOftReportInventorySPGData = listOftReportInventorySPGData;
	}
	public synchronized List<tInventorySPGDetail_mobileData> getListOftInventorySPGDetail_mobileData() {
		return ListOftInventorySPGDetail_mobileData;
	}
	public synchronized void setListOftInventorySPGDetail_mobileData(
			List<tInventorySPGDetail_mobileData> listOftInventorySPGDetail_mobileData) {
		ListOftInventorySPGDetail_mobileData = listOftInventorySPGDetail_mobileData;
	}
	public synchronized List<tInventorySPGHeader_mobileData> getListOftInventorySPGHeader_mobileData() {
		return ListOftInventorySPGHeader_mobileData;
	}
	public synchronized void setListOftInventorySPGHeader_mobileData(
			List<tInventorySPGHeader_mobileData> listOftInventorySPGHeader_mobileData) {
		ListOftInventorySPGHeader_mobileData = listOftInventorySPGHeader_mobileData;
	}
	public synchronized List<tCustomerBaseInfoData> getListOftCustomerBaseInfoData() {
		return ListOftCustomerBaseInfoData;
	}
	public synchronized void setListOftCustomerBaseInfoData(
			List<tCustomerBaseInfoData> listOftCustomerBaseInfoData) {
		ListOftCustomerBaseInfoData = listOftCustomerBaseInfoData;
	}
	public synchronized List<tCustomerBaseDetailData> getListOftCustomerBaseDetailData() {
		return ListOftCustomerBaseDetailData;
	}
	public synchronized void setListOftCustomerBaseDetailData(
			List<tCustomerBaseDetailData> listOftCustomerBaseDetailData) {
		ListOftCustomerBaseDetailData = listOftCustomerBaseDetailData;
	}
	public synchronized List<tLeaveMobileData> getListOftLeaveMobileData() {
		return ListOftLeaveMobileData;
	}
	public synchronized void setListOftLeaveMobileData(
			List<tLeaveMobileData> listOftLeaveMobileData) {
		ListOftLeaveMobileData = listOftLeaveMobileData;
	}
	public synchronized List<mTypeLeaveMobileData> getListOfmTypeLeaveMobileData() {
		return ListOfmTypeLeaveMobileData;
	}
	public synchronized void setListOfmTypeLeaveMobileData(
			List<mTypeLeaveMobileData> listOfmTypeLeaveMobileData) {
		ListOfmTypeLeaveMobileData = listOfmTypeLeaveMobileData;
	}
	public synchronized List<tCustomerBaseData> getListOftCustomerBase() {
		return ListOftCustomerBase;
	}
	public synchronized void setListOftCustomerBase(
			List<tCustomerBaseData> listOftCustomerBase) {
		ListOftCustomerBase = listOftCustomerBase;
	}
	public synchronized List<mProductBrandHeaderData> getListOfmProductBrandHeaderData() {
		return ListOfmProductBrandHeaderData;
	}
	public synchronized void setListOfmProductBrandHeaderData(
			List<mProductBrandHeaderData> listOfmProductBrandHeaderData) {
		ListOfmProductBrandHeaderData = listOfmProductBrandHeaderData;
	}
	public synchronized List<tActivityData> getListOftActivityData() {
		return ListOftActivityData;
	}
	public synchronized void setListOftActivityData(
			List<tActivityData> listOftActivityData) {
		ListOftActivityData = listOftActivityData;
	}
	public synchronized List<tReportSalesSOData> getListOftReportSalesSOData() {
		return ListOftReportSalesSOData;
	}
	public synchronized void setListOftReportSalesSOData(
			List<tReportSalesSOData> listOftReportSalesSOData) {
		ListOftReportSalesSOData = listOftReportSalesSOData;
	}
	public synchronized List<mGeolocationOutletSPGData> getListOfmGeolocationOutletSPGData() {
		return ListOfmGeolocationOutletSPGData;
	}
	public synchronized void setListOfmGeolocationOutletSPGData(
			List<mGeolocationOutletSPGData> listOfmGeolocationOutletSPGData) {
		ListOfmGeolocationOutletSPGData = listOfmGeolocationOutletSPGData;
	}
	public synchronized List<tAbsenUserData> getListOftAbsenUserData() {
		return ListOftAbsenUserData;
	}
	public synchronized void setListOftAbsenUserData(
			List<tAbsenUserData> listOftAbsenUserData) {
		ListOftAbsenUserData = listOftAbsenUserData;
	}
	public synchronized List<mEmployeeAreaData> getListOfmEmployeeAreaData() {
		return ListOfmEmployeeAreaData;
	}
	public synchronized void setListOfmEmployeeAreaData(
			List<mEmployeeAreaData> listOfmEmployeeAreaData) {
		ListOfmEmployeeAreaData = listOfmEmployeeAreaData;
	}
	public synchronized List<mEmployeeBranchData> getListOfmEmployeeBranchData() {
		return ListOfmEmployeeBranchData;
	}
	public synchronized void setListOfmEmployeeBranchData(
			List<mEmployeeBranchData> listOfmEmployeeBranchData) {
		ListOfmEmployeeBranchData = listOfmEmployeeBranchData;
	}
	public synchronized List<mEmployeeSalesProductData> getListOfmEmployeeSalesProductData() {
		return ListOfmEmployeeSalesProductData;
	}
	public synchronized void setListOfmEmployeeSalesProductData(
			List<mEmployeeSalesProductData> listOfmEmployeeSalesProductData) {
		ListOfmEmployeeSalesProductData = listOfmEmployeeSalesProductData;
	}
	public synchronized List<mNotificationData> getListOfmNotificationData() {
		return ListOfmNotificationData;
	}
	public synchronized void setListOfmNotificationData(
			List<mNotificationData> listOfmNotificationData) {
		ListOfmNotificationData = listOfmNotificationData;
	}
	public synchronized List<tErrorLogData> getListOftErrorLogData() {
		return ListOftErrorLogData;
	}
	public synchronized void setListOftErrorLogData(
			List<tErrorLogData> listOftErrorLogData) {
		ListOftErrorLogData = listOftErrorLogData;
	}
	public synchronized List<tSalesProductDetailData> getListOftSalesProductDetailData() {
		return ListOftSalesProductDetailData;
	}
	public synchronized void setListOftSalesProductDetailData(
			List<tSalesProductDetailData> listOftSalesProductDetailData) {
		ListOftSalesProductDetailData = listOftSalesProductDetailData;
	}
	public synchronized List<tSalesProductHeaderData> getListOftSalesProductHeaderData() {
		return ListOftSalesProductHeaderData;
	}
	public synchronized void setListOftSalesProductHeaderData(
			List<tSalesProductHeaderData> listOftSalesProductHeaderData) {
		ListOftSalesProductHeaderData = listOftSalesProductHeaderData;
	}
	public synchronized List<tDeviceInfoUserData> getListDatatDeviceInfoUser() {
		return ListDatatDeviceInfoUser;
	}
	public synchronized void setListDatatDeviceInfoUser(
			List<tDeviceInfoUserData> listDatatDeviceInfoUser) {
		ListDatatDeviceInfoUser = listDatatDeviceInfoUser;
	}
	public synchronized List<mMenuData> getListOfmMenuData() {
		return ListOfmMenuData;
	}
	public synchronized void setListOfmMenuData(List<mMenuData> listOfmMenuData) {
		ListOfmMenuData = listOfmMenuData;
	}
	public synchronized String getTxtValue() {
		return txtValue;
	}
	public synchronized void setTxtValue(String txtValue) {
		this.txtValue = txtValue;
	}
	public String getTxtMethod() {
		return txtMethod;
	}
	public void setTxtMethod(String txtMethod) {
		this.txtMethod = txtMethod;
	}
	
	public List<tUserLoginData> getListDatatUserLogin() {
		return ListDatatUserLogin;
	}
	public void setListDatatUserLogin(List<tUserLoginData> listDatatUserLogin) {
		ListDatatUserLogin = listDatatUserLogin;
	}
	public JSONObject txtJSON() throws JSONException {
		JSONObject resJson = new JSONObject();
		Collection<JSONObject> itemsListJquey = new ArrayList<JSONObject>();
		try
		{
			if(this.getListDatamConfig() != null){
				mconfigData dtConfig=new mconfigData();
				for (mconfigData data : this.getListDatamConfig()) {
					JSONObject item1 = new JSONObject();
					item1.put(dtConfig.Property_intEditAdmin, String.valueOf(data.get_intEditAdmin()));
					item1.put(dtConfig.Property_intId, String.valueOf(data.get_intId()));
					item1.put(dtConfig.Property_txtName, String.valueOf(data.get_txtName()));
					item1.put(dtConfig.Property_txtValue, String.valueOf(data.get_txtValue()));
					itemsListJquey.add(item1);
				}
				resJson.put(dtConfig.Property_ListOfMconfig, new JSONArray(itemsListJquey));
			}
			
			if(this.get_ListOfmProductBarcodeData()!=null){
				mProductBarcodeData dt=new mProductBarcodeData();
				for (mProductBarcodeData data : this.get_ListOfmProductBarcodeData()) {
					JSONObject item1 = new JSONObject();
					item1.put(data.Property_intProductCode, String.valueOf(data.get_intProductCode()));
					item1.put(data.Property_txtBarcode, String.valueOf(data.get_txtBarcode()));
					item1.put(data.Property_txtProductCode, String.valueOf(data.get_txtProductCode()));
					item1.put(data.Property_txtProductName, String.valueOf(data.get_txtProductName()));
					itemsListJquey.add(item1);
				}
				resJson.put(dt.Property_ListOfmProductBarcodeData, new JSONArray(itemsListJquey));
			}
			
			if(this.getListDatatUserLogin()!=null){
				tUserLoginData dttUserLoginData=new tUserLoginData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (tUserLoginData data : this.getListDatatUserLogin()) {
					JSONObject item1 = new JSONObject();
					item1.put(dttUserLoginData.Property_intId, String.valueOf(data.get_intId()));
					item1.put(dttUserLoginData.Property_txtPassword, String.valueOf(data.get_txtPassword()));
					item1.put(dttUserLoginData.Property_txtPathImage, String.valueOf(data.get_txtPathImage()));
					item1.put(dttUserLoginData.Property_txtRoleId, String.valueOf(data.get_txtRoleId()));
					item1.put(dttUserLoginData.Property_txtRoleName, String.valueOf(data.get_txtRoleName()));
					item1.put(dttUserLoginData.Property_txtUserId, String.valueOf(data.get_txtUserId()));
					item1.put(dttUserLoginData.Property_txtDataId, String.valueOf(data.get_txtDataId()));
					item1.put(dttUserLoginData.Property_DtCheckIn, String.valueOf(data.get_dtCheckIn()));
					item1.put(dttUserLoginData.Property_DtCheckOut, String.valueOf(data.get_dtCheckOut()));
					item1.put(dttUserLoginData.Property_DtLastLogin, String.valueOf(data.get_dtLastLogin()));
					item1.put(dttUserLoginData.Property_DtLogOut, String.valueOf(data.get_dtLogOut()));
					item1.put(dttUserLoginData.Property_TxtCab, String.valueOf(data.get_txtCab()));
					item1.put(dttUserLoginData.Property_TxtDeviceId, String.valueOf(data.get_txtDeviceId()));
					item1.put(dttUserLoginData.Property_TxtEmail, String.valueOf(data.get_TxtEmail()));
					item1.put(dttUserLoginData.Property_TxtEmpId, String.valueOf(data.get_TxtEmpId()));
					item1.put(dttUserLoginData.Property_txtName, String.valueOf(data.get_txtName()));
					item1.put(dttUserLoginData.Property_txtPassword, String.valueOf(data.get_txtPassword()));
					itemsListJquey.add(item1);
				}
				resJson.put(dttUserLoginData.Property_ListOftUserLoginData, new JSONArray(itemsListJquey));
			}
			
			if(this.getListDatatDeviceInfoUser()!=null){
				tDeviceInfoUserData dttDeviceInfoUserData=new tDeviceInfoUserData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (tDeviceInfoUserData data : this.getListDatatDeviceInfoUser()) {
					JSONObject item1 = new JSONObject();
					item1.put(dttDeviceInfoUserData.Property_intId, String.valueOf(data.get_intId()));
					item1.put(dttDeviceInfoUserData.Property_txtDeviceId, String.valueOf(data.get_txtDeviceId()));
					item1.put(dttDeviceInfoUserData.Property_txtDevice, String.valueOf(data.get_txtDevice()));
					item1.put(dttDeviceInfoUserData.Property_txtModel, String.valueOf(data.get_txtModel()));
					item1.put(dttDeviceInfoUserData.Property_txtUserId , String.valueOf(data.get_txtUserId()));
					item1.put(dttDeviceInfoUserData.Property_txtVersion , String.valueOf(data.get_txtVersion()));
					itemsListJquey.add(item1);
				}
				resJson.put(dttDeviceInfoUserData.Property_ListOftDeviceInfoUserData, new JSONArray(itemsListJquey));
			}
			
			if(this.getListOfmMenuData()!=null){
				mMenuData dtmMenuData=new mMenuData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (mMenuData data : this.getListOfmMenuData()) {
					JSONObject item1 = new JSONObject();
					item1.put(dtmMenuData.Property_intId, Long.valueOf(data.get_intId()));
					item1.put(dtmMenuData.Property_IntOrder, Long.valueOf(data.get_IntOrder()));
					item1.put(dtmMenuData.Property_IntParentID, Long.valueOf(data.get_IntParentID()));
					item1.put(dtmMenuData.Property_TxtDescription , String.valueOf(data.get_TxtDescription()));
					item1.put(dtmMenuData.Property_TxtLink , String.valueOf(data.get_TxtLink()));
					item1.put(dtmMenuData.Property_TxtMenuName , String.valueOf(data.get_TxtMenuName()));
					itemsListJquey.add(item1);
				}
				resJson.put(dtmMenuData.Property_ListOfMMenuData, new JSONArray(itemsListJquey));
			}
			
			
			if(this.getListOftSalesProductDetailData()!=null){
				tSalesProductDetailData dttSalesProductDetailData=new tSalesProductDetailData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (tSalesProductDetailData data : this.getListOftSalesProductDetailData()) {
					JSONObject item1 = new JSONObject();
					item1.put(dttSalesProductDetailData.Property_intId, String.valueOf(data.get_intId()));
					item1.put(dttSalesProductDetailData.Property_dtDate, String.valueOf(data.get_dtDate()));
					item1.put(dttSalesProductDetailData.Property_intPrice, String.valueOf(data.get_intPrice()));
					item1.put(dttSalesProductDetailData.Property_intQty , String.valueOf(data.get_intQty()));
					item1.put(dttSalesProductDetailData.Property_txtCodeProduct , String.valueOf(data.get_txtCodeProduct()));
					item1.put(dttSalesProductDetailData.Property_txtKeterangan , String.valueOf(data.get_txtKeterangan()));
					item1.put(dttSalesProductDetailData.Property_txtNameProduct , String.valueOf(data.get_txtNameProduct()));
					item1.put(dttSalesProductDetailData.Property_txtNIK , String.valueOf(data.get_txtNIK()));
					item1.put(dttSalesProductDetailData.Property_intTotal , String.valueOf(data.get_intTotal()));
					item1.put(dttSalesProductDetailData.Property_txtNoSo , String.valueOf(data.get_txtNoSo()));
					itemsListJquey.add(item1);
				}
				resJson.put(dttSalesProductDetailData.Property_ListOftSalesProductDetailData, new JSONArray(itemsListJquey));
			}
			
			
			if(this.getListOftSalesProductHeaderData()!=null){
				tSalesProductHeaderData dttSalesProductHeaderData=new tSalesProductHeaderData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (tSalesProductHeaderData data : this.getListOftSalesProductHeaderData()) {
					JSONObject item1 = new JSONObject();
					item1.put(dttSalesProductHeaderData.Property_intId, String.valueOf(data.get_intId()));
					item1.put(dttSalesProductHeaderData.Property_OutletCode, String.valueOf(data.get_OutletCode()));
					item1.put(dttSalesProductHeaderData.Property_OutletName, String.valueOf(data.get_OutletName()));
					item1.put(dttSalesProductHeaderData.Property_txtDate , String.valueOf(data.get_dtDate()));
					item1.put(dttSalesProductHeaderData.Property_intIdAbsenUser , String.valueOf(data.get_intIdAbsenUser()));
					item1.put(dttSalesProductHeaderData.Property_intSubmit , String.valueOf(data.get_intSubmit()));
					item1.put(dttSalesProductHeaderData.Property_intSumAmount , String.valueOf(data.get_intSumAmount()));
					item1.put(dttSalesProductHeaderData.Property_intSumItem , String.valueOf(data.get_intSumItem()));
					item1.put(dttSalesProductHeaderData.Property_intSync , String.valueOf(data.get_intSync()));
					item1.put(dttSalesProductHeaderData.Property_txtBranchCode , String.valueOf(data.get_txtBranchCode()));
					item1.put(dttSalesProductHeaderData.Property_txtBranchName , String.valueOf(data.get_txtBranchName()));
					item1.put(dttSalesProductHeaderData.Property_UserId , String.valueOf(data.get_UserId()));
					item1.put(dttSalesProductHeaderData.Property_txtKeterangan , String.valueOf(data.get_txtKeterangan()));
					item1.put(dttSalesProductHeaderData.Property_txtNIK , String.valueOf(data.get_txtNIK()));
					itemsListJquey.add(item1);
				}
				resJson.put(dttSalesProductHeaderData.Property_ListOftSalesProductHeaderData, new JSONArray(itemsListJquey));
			}
			
			
			if(this.getListOftInventorySPGDetail_mobileData()!=null){
				tInventorySPGDetail_mobileData dttInventorySPGDetail_mobileData=new tInventorySPGDetail_mobileData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (tInventorySPGDetail_mobileData data : this.getListOftInventorySPGDetail_mobileData()) {
					JSONObject item1 = new JSONObject();
					item1.put(dttInventorySPGDetail_mobileData.Property_intId, String.valueOf(data.get_intId()));
					item1.put(dttInventorySPGDetail_mobileData.Property_dtDate, String.valueOf(data.get_dtDate()));
					item1.put(dttInventorySPGDetail_mobileData.Property_intQtyDisplay , String.valueOf(data.get_intQtyDisplay()));
					item1.put(dttInventorySPGDetail_mobileData.Property_intQtyGudang , String.valueOf(data.get_intQtyGudang()));
					item1.put(dttInventorySPGDetail_mobileData.Property_txtCodeProduct , String.valueOf(data.get_txtCodeProduct()));
					item1.put(dttInventorySPGDetail_mobileData.Property_txtKeterangan , String.valueOf(data.get_txtKeterangan()));
					item1.put(dttInventorySPGDetail_mobileData.Property_txtNameProduct , String.valueOf(data.get_txtNameProduct()));
					item1.put(dttInventorySPGDetail_mobileData.Property_txtNIK , String.valueOf(data.get_txtNIK()));
					item1.put(dttInventorySPGDetail_mobileData.Property_intTotal , String.valueOf(data.get_intTotal()));
					item1.put(dttInventorySPGDetail_mobileData.Property_txtNoInv , String.valueOf(data.get_txtNoInv()));
					itemsListJquey.add(item1);
				}
				resJson.put(dttInventorySPGDetail_mobileData.Property_ListOftInventorySPGDetail_mobileData, new JSONArray(itemsListJquey));
			}
			
			
			if(this.getListOftInventorySPGHeader_mobileData()!=null){
				tInventorySPGHeader_mobileData dttInventorySPGHeader_mobileData=new tInventorySPGHeader_mobileData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (tInventorySPGHeader_mobileData data : this.getListOftInventorySPGHeader_mobileData()) {
					JSONObject item1 = new JSONObject();
					item1.put(dttInventorySPGHeader_mobileData.Property_intId, String.valueOf(data.get_intId()));
					item1.put(dttInventorySPGHeader_mobileData.Property_OutletCode, String.valueOf(data.get_OutletCode()));
					item1.put(dttInventorySPGHeader_mobileData.Property_OutletName, String.valueOf(data.get_OutletName()));
					item1.put(dttInventorySPGHeader_mobileData.Property_txtDate , String.valueOf(data.get_dtDate()));
					item1.put(dttInventorySPGHeader_mobileData.Property_intIdAbsenUser , String.valueOf(data.get_intIdAbsenUser()));
					item1.put(dttInventorySPGHeader_mobileData.Property_intSubmit , String.valueOf(data.get_intSubmit()));
					item1.put(dttInventorySPGHeader_mobileData.Property_intSumAmount , String.valueOf(data.get_intSumAmount()));
					item1.put(dttInventorySPGHeader_mobileData.Property_intSumItem , String.valueOf(data.get_intSumItem()));
					item1.put(dttInventorySPGHeader_mobileData.Property_intSync , String.valueOf(data.get_intSync()));
					item1.put(dttInventorySPGHeader_mobileData.Property_txtBranchCode , String.valueOf(data.get_txtBranchCode()));
					item1.put(dttInventorySPGHeader_mobileData.Property_txtBranchName , String.valueOf(data.get_txtBranchName()));
					item1.put(dttInventorySPGHeader_mobileData.Property_UserId , String.valueOf(data.get_UserId()));
					item1.put(dttInventorySPGHeader_mobileData.Property_txtKeterangan , String.valueOf(data.get_txtKeterangan()));
					item1.put(dttInventorySPGHeader_mobileData.Property_txtNIK , String.valueOf(data.get_txtNIK()));
					itemsListJquey.add(item1);
				}
				resJson.put(dttInventorySPGHeader_mobileData.Property_tInventorySPGHeader_mobileData, new JSONArray(itemsListJquey));
			}
			
			
			if(this.getListOfmEmployeeAreaData()!=null){
				mEmployeeAreaData dtmEmployeeAreaData=new mEmployeeAreaData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (mEmployeeAreaData data : this.getListOfmEmployeeAreaData()) {
					JSONObject item1 = new JSONObject();
					item1.put(dtmEmployeeAreaData.Property_intBranchId, String.valueOf(data.get_intBranchId()));
					item1.put(dtmEmployeeAreaData.Property_intChannelId, String.valueOf(data.get_intChannelId()));
					item1.put(dtmEmployeeAreaData.Property_intEmployeeId, String.valueOf(data.get_intEmployeeId()));
					item1.put(dtmEmployeeAreaData.Property_intID , String.valueOf(data.get_intID()));
					item1.put(dtmEmployeeAreaData.Property_intOutletId , String.valueOf(data.get_intOutletId()));
					item1.put(dtmEmployeeAreaData.Property_intRayonId , String.valueOf(data.get_intRayonId()));
					item1.put(dtmEmployeeAreaData.Property_intRegionId , String.valueOf(data.get_intRegionId()));
					item1.put(dtmEmployeeAreaData.Property_txtBranchCode , String.valueOf(data.get_txtBranchCode()));
					item1.put(dtmEmployeeAreaData.Property_txtBranchName , String.valueOf(data.get_txtBranchName()));
					item1.put(dtmEmployeeAreaData.Property_txtName , String.valueOf(data.get_txtName()));
					item1.put(dtmEmployeeAreaData.Property_txtNIK , String.valueOf(data.get_txtNIK()));
					item1.put(dtmEmployeeAreaData.Property_txtOutletCode , String.valueOf(data.get_txtOutletCode()));
					item1.put(dtmEmployeeAreaData.Property_txtOutletName , String.valueOf(data.get_txtOutletName()));
					item1.put(dtmEmployeeAreaData.Property_txtRayonName , String.valueOf(data.get_txtRayonCode()));
					item1.put(dtmEmployeeAreaData.Property_txtRayonName , String.valueOf(data.get_txtRayonName()));
					item1.put(dtmEmployeeAreaData.Property_txtRegionName , String.valueOf(data.get_txtRegionName()));
					itemsListJquey.add(item1);
				}
				resJson.put(dtmEmployeeAreaData.Property_ListOfmEmployeeAreaData, new JSONArray(itemsListJquey));
			}
			
			
			if(this.getListOfmEmployeeBranchData()!=null){
				mEmployeeBranchData dtmEmployeeBranchData=new mEmployeeBranchData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (mEmployeeBranchData data : this.getListOfmEmployeeBranchData()) {
					JSONObject item1 = new JSONObject();
					item1.put(dtmEmployeeBranchData.Property_EmpId, String.valueOf(data.get_EmpId()));
					item1.put(dtmEmployeeBranchData.Property_intID, String.valueOf(data.get_intID()));
					item1.put(dtmEmployeeBranchData.Property_txtBranchCode, String.valueOf(data.get_txtBranchCode()));
					item1.put(dtmEmployeeBranchData.Property_txtBranchName , String.valueOf(data.get_txtBranchName()));
					item1.put(dtmEmployeeBranchData.Property_txtName , String.valueOf(data.get_txtName()));
					item1.put(dtmEmployeeBranchData.Property_txtNIK , String.valueOf(data.get_txtNIK()));
					itemsListJquey.add(item1);
				}
				resJson.put(dtmEmployeeBranchData.Property_ListOfEmployeeBranchData, new JSONArray(itemsListJquey));
			}
			
			if(this.get_ListOfmStockAwalData()!=null){
				itemsListJquey = new ArrayList<JSONObject>();
				for (mStockAwalData data : this.get_ListOfmStockAwalData()) {
					JSONObject item1 = new JSONObject();
					item1.put(data.Property_bitActive, String.valueOf(data.get_bitActive()));
					item1.put(data.Property_intdata, String.valueOf(data.get_intdata()));
					item1.put(data.Property_intQty, String.valueOf(data.get_intQty()));
					item1.put(data.Property_txtBranchCode , String.valueOf(data.get_txtBranchCode()));
					item1.put(data.Property_txtNoDoc , String.valueOf(data.get_txtNoDoc()));
					item1.put(data.Property_txtOutletCode , String.valueOf(data.get_txtOutletCode()));
					item1.put(data.Property_txtOutletName , String.valueOf(data.get_txtOutletName()));
					item1.put(data.Property_txtProductCode , String.valueOf(data.get_txtProductCode()));
					item1.put(data.Property_txtProductName , String.valueOf(data.get_txtProductName()));
					item1.put(data.Property_txtStatus , String.valueOf(data.get_txtStatus()));
					itemsListJquey.add(item1);
				}
				resJson.put("ListOfmStockAwalData", new JSONArray(itemsListJquey));
			}
			if(this.getListOfmEmployeeSalesProductData()!=null){
				mEmployeeSalesProductData dtmEmployeeSalesProductData=new mEmployeeSalesProductData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (mEmployeeSalesProductData data : this.getListOfmEmployeeSalesProductData()) {
					JSONObject item1 = new JSONObject();
					item1.put(dtmEmployeeSalesProductData.Property_decBobot, String.valueOf(data.get_decBobot()));
					item1.put(dtmEmployeeSalesProductData.Property_decHJD, String.valueOf(data.get_decHJD()));
					item1.put(dtmEmployeeSalesProductData.Property_intId, String.valueOf(data.get_intId()));
					item1.put(dtmEmployeeSalesProductData.Property_txtBrandDetailGramCode , String.valueOf(data.get_txtBrandDetailGramCode()));
					item1.put(dtmEmployeeSalesProductData.Property_txtName , String.valueOf(data.get_txtName()));
					item1.put(dtmEmployeeSalesProductData.Property_txtNIK , String.valueOf(data.get_txtNIK()));
					item1.put(dtmEmployeeSalesProductData.Property_txtProductBrandDetailGramName , String.valueOf(data.get_txtProductBrandDetailGramName()));
					itemsListJquey.add(item1);
				}
				resJson.put(dtmEmployeeSalesProductData.Property_ListOfmEmployeeSalesProductData, new JSONArray(itemsListJquey));
			}
			
			
			if(this.getListOfmNotificationData()!=null){
				mNotificationData dtmNotificationData=new mNotificationData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (mNotificationData data : this.getListOfmNotificationData()) {
					JSONObject item1 = new JSONObject();
					item1.put(dtmNotificationData.Property_dtPublishEnd, String.valueOf(data.get_dtPublishEnd()));
					item1.put(dtmNotificationData.Property_dtPublishStart, String.valueOf(data.get_dtPublishStart()));
					item1.put(dtmNotificationData.Property_dtStatus, String.valueOf(data.get_dtStatus()));
					item1.put(dtmNotificationData.Property_intSync , String.valueOf(data.get_intSync()));
					item1.put(dtmNotificationData.Property_txtDataId , String.valueOf(data.get_txtDataId()));
					item1.put(dtmNotificationData.Property_txtDescription , String.valueOf(data.get_txtDescription()));
					item1.put(dtmNotificationData.Property_txtImage , String.valueOf(data.get_txtImage()));
					item1.put(dtmNotificationData.Property_txtNotifId , String.valueOf(data.get_txtNotifId()));
					item1.put(dtmNotificationData.Property_txtStatus , String.valueOf(data.get_txtStatus()));
					item1.put(dtmNotificationData.Property_txtTitle , String.valueOf(data.get_txtTitle()));
					item1.put(dtmNotificationData.Property_txtUserID , String.valueOf(data.get_txtUserID()));
					item1.put(dtmNotificationData.Property_txtLinkImage , String.valueOf(data.get_txtLinkImage()));
					item1.put(dtmNotificationData.Property_intSuccessDLFile , String.valueOf(data.get_intSuccessDLFile()));
					itemsListJquey.add(item1);
				}
				resJson.put(dtmNotificationData.Property_ListOfmNotificationData, new JSONArray(itemsListJquey));
			}
			
			if(this.getListOftAbsenUserData()!=null){
				tAbsenUserData dttAbsenUserData=new tAbsenUserData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (tAbsenUserData data : this.getListOftAbsenUserData()) {
					JSONObject item1 = new JSONObject();
					item1.put(dttAbsenUserData.Property_dtDateCheckIn, String.valueOf(data.get_dtDateCheckIn()));
					item1.put(dttAbsenUserData.Property_dtDateCheckOut, String.valueOf(data.get_dtDateCheckOut()));
					item1.put(dttAbsenUserData.Property_intId, String.valueOf(data.get_intId()));
					item1.put(dttAbsenUserData.Property_intSubmit, String.valueOf(data.get_intSubmit()));
					item1.put(dttAbsenUserData.Property_intSync , String.valueOf(data.get_intSync()));
					item1.put(dttAbsenUserData.Property_txtAbsen , String.valueOf(data.get_txtAbsen()));
					item1.put(dttAbsenUserData.Property_txtAccuracy , String.valueOf(data.get_txtAccuracy()));
					item1.put(dttAbsenUserData.Property_txtBranchCode , String.valueOf(data.get_txtBranchCode()));
					item1.put(dttAbsenUserData.Property_txtBranchName , String.valueOf(data.get_txtBranchName()));
					item1.put(dttAbsenUserData.Property_txtLatitude , String.valueOf(data.get_txtLatitude()));
					item1.put(dttAbsenUserData.Property_txtLongitude , String.valueOf(data.get_txtLongitude()));
					item1.put(dttAbsenUserData.Property_txtOutletCode , String.valueOf(data.get_txtOutletCode()));
					item1.put(dttAbsenUserData.Property_txtOutletName , String.valueOf(data.get_txtOutletName()));
					item1.put(dttAbsenUserData.Property_txtUserId , String.valueOf(data.get_txtUserId()));
					item1.put(dttAbsenUserData.Property_txtDeviceId , String.valueOf(data.get_txtDeviceId()));
					item1.put(dttAbsenUserData.Property_txtImg1 , String.valueOf(data.get_txtImg1()));
					item1.put(dttAbsenUserData.Property_txtImg2 , String.valueOf(data.get_txtImg2()));
					itemsListJquey.add(item1);
				}
				resJson.put(dttAbsenUserData.Property_ListOftAbsenUser, new JSONArray(itemsListJquey));
			}
			
			
			if(this.getListOfmGeolocationOutletSPGData()!=null){
				mGeolocationOutletSPGData dtmGeolocationOutletSPGData=new mGeolocationOutletSPGData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (mGeolocationOutletSPGData data : this.getListOfmGeolocationOutletSPGData()) {
					JSONObject item1 = new JSONObject();
					item1.put(dtmGeolocationOutletSPGData.Property_IntId, String.valueOf(data.get_intId()));
					item1.put(dtmGeolocationOutletSPGData.Property_txtAcc, String.valueOf(data.get_txtAcc()));
					item1.put(dtmGeolocationOutletSPGData.Property_txtBranchCode, String.valueOf(data.get_txtBranchCode()));
					item1.put(dtmGeolocationOutletSPGData.Property_txtLatitude , String.valueOf(data.get_txtLatitude()));
					item1.put(dtmGeolocationOutletSPGData.Property_txtLongitude , String.valueOf(data.get_txtLongitude()));
					item1.put(dtmGeolocationOutletSPGData.Property_txtOutletCode , String.valueOf(data.get_txtOutletCode()));
					itemsListJquey.add(item1);
				}
				resJson.put(dtmGeolocationOutletSPGData.Property_ListOfmGeolocationOutletSPG, new JSONArray(itemsListJquey));
			}
			
			
			if(this.getListOftReportSalesSOData()!=null){
				tReportSalesSOData dttReportSalesSOData=new tReportSalesSOData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (tReportSalesSOData data : this.getListOftReportSalesSOData()) {
					JSONObject item1 = new JSONObject();
					item1.put(dttReportSalesSOData.Property_tSalesProductDetailData, GenerateJsontSalesProductDetailData(data.get_tSalesProductDetailData()));
					item1.put(dttReportSalesSOData.Property_tSalesProductHeaderData, GenerateJsontSalesProductHeaderData(data.get_tSalesProductHeaderData()));
					item1.put(dttReportSalesSOData.Property_TotalAmount, String.valueOf(data.get_TotalAmount()));
					item1.put(dttReportSalesSOData.Property_TotalQty , String.valueOf(data.get_TotalQty()));
					itemsListJquey.add(item1);
				}
				resJson.put(dttReportSalesSOData.Property_ListOftReportSalesSO, new JSONArray(itemsListJquey));
			}
			
			
			if(this.getListOftReportSalesSOData()!=null){
				tReportInventorySPGData dttReportInventorySPGData=new tReportInventorySPGData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (tReportInventorySPGData data : this.getListOftReportInventorySPGData()) {
					JSONObject item1 = new JSONObject();
					item1.put(dttReportInventorySPGData.Property_tInventorySPGDetail_mobileData, GenerateJsontInventorySPGDetail_mobile(data.get_tInventorySPGDetail_mobileData()));
					item1.put(dttReportInventorySPGData.Property_tInventorySPGHeader_mobileData, GenerateJsontInventorySPGHeader_mobile(data.get_tInventorySPGHeader_mobileData()));
					item1.put(dttReportInventorySPGData.Property_TotalAmount, String.valueOf(data.get_TotalAmount()));
					item1.put(dttReportInventorySPGData.Property_TotalQty , String.valueOf(data.get_TotalQty()));
					itemsListJquey.add(item1);
				}
				resJson.put(dttReportInventorySPGData.Property_ListOftReportInventorySPGData, new JSONArray(itemsListJquey));
			}
			
			
			if(this.getListOftActivityData()!=null){
				tActivityData dttActivityData=new tActivityData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (tActivityData data : this.getListOftActivityData()) {
					JSONObject item1 = new JSONObject();
					item1.put(dttActivityData.Property_dtActivity, String.valueOf(data.get_dtActivity()));
					item1.put(dttActivityData.Property_intActive, String.valueOf(data.get_intActive()));
					item1.put(dttActivityData.Property_intId, String.valueOf(data.get_intId()));
					item1.put(dttActivityData.Property_intIdSyn , String.valueOf(data.get_intIdSyn()));
					item1.put(dttActivityData.Property_intFlag , String.valueOf(data.get_intFlag()));
					item1.put(dttActivityData.Property_intSubmit , String.valueOf(data.get_intSubmit()));
					item1.put(dttActivityData.Property_txtDesc , String.valueOf(data.get_txtDesc()));
					item1.put(dttActivityData.Property_txtDeviceId , String.valueOf(data.get_txtDeviceId()));
					item1.put(dttActivityData.Property_txtImg1 , String.valueOf(data.get_txtImg1()));
					item1.put(dttActivityData.Property_txtImg2 , String.valueOf(data.get_txtImg2()));
					item1.put(dttActivityData.Property_txtOutletCode , String.valueOf(data.get_txtOutletCode()));
					item1.put(dttActivityData.Property_txtOutletName , String.valueOf(data.get_txtOutletName()));
					item1.put(dttActivityData.Property_txtUserId , String.valueOf(data.get_txtUserId()));
					item1.put(dttActivityData.Property_txtBranch , String.valueOf(data.get_txtBranch()));
					itemsListJquey.add(item1);
				}
				resJson.put(dttActivityData.Property_ListOfTActivity, new JSONArray(itemsListJquey));
			}
			
			
			if(this.getListOftCustomerBase()!=null){
				tCustomerBaseData dttCustomerBaseData=new tCustomerBaseData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (tCustomerBaseData data : this.getListOftCustomerBase()) {
					JSONObject item1 = new JSONObject();
					item1.put(dttCustomerBaseData.Property_bitActive ,String.valueOf(data.get_bitActive()));
					item1.put(dttCustomerBaseData.Property_dtDate , String.valueOf(data.get_dtDate()));
					item1.put(dttCustomerBaseData.Property_intCustomerId , String.valueOf(data.get_intCustomerId()));
					item1.put(dttCustomerBaseData.Property_intCustomerIdSync , String.valueOf(data.get_intCustomerIdSync()));
					item1.put(dttCustomerBaseData.Property_intSubmit , String.valueOf(data.get_intSubmit()));
					item1.put(dttCustomerBaseData.Property_txtAlamat , String.valueOf(data.get_txtAlamat()));
					item1.put(dttCustomerBaseData.Property_txtBranchId , String.valueOf(data.get_txtBranchId()));
					item1.put(dttCustomerBaseData.Property_txtNama , String.valueOf(data.get_txtNama()));
					item1.put(dttCustomerBaseData.Property_txtOutletId , String.valueOf(data.get_txtOutletId()));
					item1.put(dttCustomerBaseData.Property_txtTelp , String.valueOf(data.get_txtTelp()));
					item1.put(dttCustomerBaseData.Property_txtUserId , String.valueOf(data.get_txtUserId()));
					item1.put(dttCustomerBaseData.Property_txtDeviceId , String.valueOf(data.get_txtDeviceId()));
					item1.put(dttCustomerBaseData.Property_txtSex , String.valueOf(data.get_txtSex()));
					item1.put(dttCustomerBaseData.Property_txtProductBrandCode , String.valueOf(data.get_txtProductBrandCode()));
					itemsListJquey.add(item1);
				}
				resJson.put(dttCustomerBaseData.Property_ListOftCustomerBaseData, new JSONArray(itemsListJquey));
			}
			
			
			if(this.getListOftCustomerBaseDetailData()!=null){
				tCustomerBaseDetailData dttCustomerBaseDetailData=new tCustomerBaseDetailData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (tCustomerBaseDetailData data : this.getListOftCustomerBaseDetailData()) {
					JSONObject item1 = new JSONObject();
					item1.put(dttCustomerBaseDetailData.Property_intCustomerId ,String.valueOf(data.get_intCustomerId()));
					item1.put(dttCustomerBaseDetailData.Property_intQty , String.valueOf(data.get_intQty()));
					item1.put(dttCustomerBaseDetailData.Property_txtProductBrandCode , String.valueOf(data.get_txtProductBrandCode()));
					item1.put(dttCustomerBaseDetailData.Property_txtProductBrandName , String.valueOf(data.get_txtProductBrandName()));
					item1.put(dttCustomerBaseDetailData.property_txtDataId , String.valueOf(data.get_txtDataId()));
					itemsListJquey.add(item1);
				}
				resJson.put(dttCustomerBaseDetailData.PropertyListOftCustomerBaseDetailData, new JSONArray(itemsListJquey));
			}
			
			
			if(this.getListOfmProductBrandHeaderData()!=null){
				mProductBrandHeaderData dtmProductBrandHeaderData=new mProductBrandHeaderData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (mProductBrandHeaderData data : this.getListOfmProductBrandHeaderData()) {
					JSONObject item1 = new JSONObject();
					item1.put(dtmProductBrandHeaderData.Property_intmProductUmbrandId ,String.valueOf(data.get_intmProductUmbrandId()));
					item1.put(dtmProductBrandHeaderData.Property_txtAliasName , String.valueOf(data.get_txtAliasName()));
					item1.put(dtmProductBrandHeaderData.Property_txtProductBrandCode , String.valueOf(data.get_txtProductBrandCode()));
					item1.put(dtmProductBrandHeaderData.Property_txtProductBrandName , String.valueOf(data.get_txtProductBrandName()));
					itemsListJquey.add(item1);
				}
				resJson.put(dtmProductBrandHeaderData.Property_ListOfmProductBrandHeader, new JSONArray(itemsListJquey));
			}
			
			
			if(this.getListOfmTypeLeaveMobileData()!=null){
				mTypeLeaveMobileData dtmTypeLeaveMobileData=new mTypeLeaveMobileData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (mTypeLeaveMobileData data : this.getListOfmTypeLeaveMobileData()) {
					JSONObject item1 = new JSONObject();
					item1.put(dtmTypeLeaveMobileData.Property_intTipeLeave ,String.valueOf(data.get_intTipeLeave()));
					item1.put(dtmTypeLeaveMobileData.Property_txtTipeLeaveName , String.valueOf(data.get_txtTipeLeaveName()));
					itemsListJquey.add(item1);
				}
				resJson.put(dtmTypeLeaveMobileData.PropertyListOfmTypeLeaveMobileData, new JSONArray(itemsListJquey));
			}
			
			
			if(this.getListOftLeaveMobileData()!=null){
				List<mTypeLeaveMobileData> _ListOfDatamTypeLeaveMobileData=getListOfmTypeLeaveMobileData();
				tLeaveMobileData dttLeaveMobileData=new tLeaveMobileData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (tLeaveMobileData data : this.getListOftLeaveMobileData()) {
					JSONObject item1 = new JSONObject();
					item1.put(dttLeaveMobileData.Property_dtLeave ,String.valueOf(data.get_dtLeave()));
					item1.put(dttLeaveMobileData.Property_intLeaveId , String.valueOf(data.get_intLeaveId()));
					item1.put(dttLeaveMobileData.Property_intLeaveIdSync , String.valueOf(data.get_intLeaveIdSync()));
					item1.put(dttLeaveMobileData.Property_intSubmit , String.valueOf(data.get_intSubmit()));
					item1.put(dttLeaveMobileData.Property_txtAlasan , String.valueOf(data.get_txtAlasan()));
					item1.put(dttLeaveMobileData.Property_txtDeviceId , String.valueOf(data.get_txtDeviceId()));
					item1.put(dttLeaveMobileData.Property_txtTypeAlasan , String.valueOf(data.get_txtTypeAlasan()));
					if(_ListOfDatamTypeLeaveMobileData!=null){
						for(mTypeLeaveMobileData d : _ListOfDatamTypeLeaveMobileData){
							if(Integer.valueOf(d.get_intTipeLeave())== Integer.valueOf(data.get_txtTypeAlasan())){
								item1.put(dttLeaveMobileData.Property_txtTypeAlasanName , String.valueOf(d.get_txtTipeLeaveName()));
								break;
							}
						}	
					}
					item1.put(dttLeaveMobileData.Property_txtUserId , String.valueOf(data.get_txtUserId()));
					itemsListJquey.add(item1);
				}
				resJson.put(dttLeaveMobileData.PropertyListOftLeaveMobileData, new JSONArray(itemsListJquey));
			}
			
			
						
			if(this.getListOftEmployeeBRWithLOBData()!=null){
				tEmployeeBRWithLOBData dttEmployeeBRWithLOBData=new tEmployeeBRWithLOBData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (tEmployeeBRWithLOBData data : this.getListOftEmployeeBRWithLOBData()) {
					JSONObject item1 = new JSONObject();
					item1.put(dttEmployeeBRWithLOBData.Property_IntEmployeeDetailId ,String.valueOf(data.get_IntEmployeeDetailId()));
					item1.put(dttEmployeeBRWithLOBData.Property_IntEmployeeId , String.valueOf(data.get_IntEmployeeId()));
					item1.put(dttEmployeeBRWithLOBData.Property_IntlobId , String.valueOf(data.get_IntlobId()));
					item1.put(dttEmployeeBRWithLOBData.Property_TxtLobName , String.valueOf(data.get_TxtLobName()));
					itemsListJquey.add(item1);
				}
				resJson.put(dttEmployeeBRWithLOBData.Property_ListOftEmployeeBRWithLOBData, new JSONArray(itemsListJquey));
			}
			
			if(this.getListOftUserLOBData()!=null){
				tUserLOBData dttUserLOBData=new tUserLOBData();
				itemsListJquey = new ArrayList<JSONObject>();
				for (tUserLOBData data : this.getListOftUserLOBData()) {
					JSONObject item1 = new JSONObject();
					item1.put(dttUserLOBData.Property_intLOB ,String.valueOf(data.get_intLOB()));
					item1.put(dttUserLOBData.Property_txtLOB , String.valueOf(data.get_txtLOB()));
					item1.put(dttUserLOBData.Property_txtNik , String.valueOf(data.get_txtNik()));
					itemsListJquey.add(item1);
				}
				resJson.put(dttUserLOBData.Property_ListOftUserLOBData, new JSONArray(itemsListJquey));
			}
			if(this.get_ListOfmItemSalesPack_StockLogData()!=null){
				itemsListJquey = new ArrayList<JSONObject>();
				for (mItemSalesPack_StockLogData data : this.get_ListOfmItemSalesPack_StockLogData()) {
					JSONObject item1 = new JSONObject();
					item1.put(data.Property_intProductCode ,String.valueOf(data.get_intProductCode()));
					item1.put(data.Property_intQtyAdj ,String.valueOf(data.get_intQtyAdj()));
					item1.put(data.Property_intQtyAvailable ,String.valueOf(data.get_intQtyAvailable()));
					item1.put(data.Property_intQtyIn ,String.valueOf(data.get_intQtyIn()));
					item1.put(data.Property_intQtyOut ,String.valueOf(data.get_intQtyOut()));
					item1.put(data.Property_intSaldoAwal ,String.valueOf(data.get_intSaldoAwal()));
					item1.put(data.Property_intWeek ,String.valueOf(data.get_intWeek()));
					item1.put(data.Property_txtBranchCode ,String.valueOf(data.get_txtBranchCode()));
					item1.put(data.Property_txtDataId ,String.valueOf(data.get_txtDataId()));
					item1.put(data.Property_txtPeriode ,String.valueOf(data.get_txtPeriode()));
					item1.put(data.Property_txtNoTransaction ,String.valueOf(data.get_txtNoTransaction()));
					item1.put(data.Property_txtOutletCode ,String.valueOf(data.get_txtOutletCode()));
					item1.put(data.Property_txtOutletName ,String.valueOf(data.get_txtOutletName()));
					itemsListJquey.add(item1);
				}
				resJson.put("ListOfmItemSalesPack_StockLogData", new JSONArray(itemsListJquey));
			}
			if(this.get_ListOfmItemSalesPack_StockData()!=null){
				itemsListJquey = new ArrayList<JSONObject>();
				for (mItemSalesPack_StockData data : this.get_ListOfmItemSalesPack_StockData()) {
					JSONObject item1 = new JSONObject();
					item1.put(data.Property_intProductCode ,String.valueOf(data.get_intProductCode()));
					item1.put(data.Property_intQtyAdj ,String.valueOf(data.get_intQtyAdj()));
					item1.put(data.Property_intQtyAvailable ,String.valueOf(data.get_intQtyAvailable()));
					item1.put(data.Property_intQtyIn ,String.valueOf(data.get_intQtyIn()));
					item1.put(data.Property_intQtyOut ,String.valueOf(data.get_intQtyOut()));
					item1.put(data.Property_intSaldoAwal ,String.valueOf(data.get_intSaldoAwal()));
					item1.put(data.Property_intWeek ,String.valueOf(data.get_intWeek()));
					item1.put(data.Property_txtBranchCode ,String.valueOf(data.get_txtBranchCode()));
					item1.put(data.Property_txtDataId ,String.valueOf(data.get_txtDataId()));
					item1.put(data.Property_txtPeriode ,String.valueOf(data.get_txtPeriode()));
					item1.put(data.Property_txtNoTransaction ,String.valueOf(data.get_txtNoTransaction()));
					item1.put(data.Property_txtOutletCode ,String.valueOf(data.get_txtOutletCode()));
					item1.put(data.Property_txtOutletName ,String.valueOf(data.get_txtOutletName()));
					itemsListJquey.add(item1);
				}
				resJson.put("ListOfmItemSalesPack_StockData", new JSONArray(itemsListJquey));
			}
			if(this.get_ListOftGRNDetail_mobileData()!=null){
				itemsListJquey = new ArrayList<JSONObject>();
				for (tGRNDetail_mobileData data : this.get_ListOftGRNDetail_mobileData()) {
					JSONObject item1 = new JSONObject();
					item1.put(data.Property_intProductCode ,String.valueOf(data.get_intProductCode()));
					item1.put(data.Property_dtED ,String.valueOf(data.get_dtED()));
					item1.put(data.Property_intQty ,String.valueOf(data.get_intQty()));
					item1.put(data.Property_txtBatchNo ,String.valueOf(data.get_txtBatchNo()));
					item1.put(data.Property_txtDataId ,String.valueOf(data.get_txtDataId()));
					item1.put(data.Property_txtNoGRN ,String.valueOf(data.get_txtNoGRN()));
					item1.put(data.Property_intReason ,String.valueOf(data.get_intReason()));
					item1.put(data.Property_txtProductName ,String.valueOf(data.get_txtProductName()));
					itemsListJquey.add(item1);
				}
				resJson.put("ListOftGRNDetail_mobileData", new JSONArray(itemsListJquey));
			}
			if(this.get_ListOftGRNHeader_mobileData()!=null){
				itemsListJquey = new ArrayList<JSONObject>();
				for (tGRNHeader_mobileData data : this.get_ListOftGRNHeader_mobileData()) {
					JSONObject item1 = new JSONObject();
					item1.put(data.Property_dtDate ,String.valueOf(data.get_dtDate()));
					item1.put(data.Property_txtBranchCode ,String.valueOf(data.get_txtBranchCode()));
					item1.put(data.Property_txtDeviceId ,String.valueOf(data.get_txtDeviceId()));
					item1.put(data.Property_txtNoGRN ,String.valueOf(data.get_txtNoGRN()));
					item1.put(data.Property_txtNoMO ,String.valueOf(data.get_txtNoMO()));
					item1.put(data.Property_txtNoPO ,String.valueOf(data.get_txtNoPO()));
					item1.put(data.Property_txtOutletCode ,String.valueOf(data.get_txtOutletCode()));
					item1.put(data.Property_txtOutletName ,String.valueOf(data.get_txtOutletName()));
					item1.put(data.Property_txtSource ,String.valueOf(data.get_txtSource()));
					item1.put(data.Property_txtUserId ,String.valueOf(data.get_txtUserId()));
					itemsListJquey.add(item1);
				}
				resJson.put("ListOftGRNHeader_mobileData", new JSONArray(itemsListJquey));
			}
			if(this.get_ListOftPenguaranDetail_MobileData()!=null){
				itemsListJquey = new ArrayList<JSONObject>();
				for (tPenguaranDetail_MobileData data : this.get_ListOftPenguaranDetail_MobileData()) {
					JSONObject item1 = new JSONObject();
					item1.put(data.Property_dtED ,String.valueOf(data.get_dtED()));
					item1.put(data.Property_intProductCode ,String.valueOf(data.get_intProductCode()));
					item1.put(data.Property_intQty ,String.valueOf(data.get_intQty()));
					item1.put(data.Property_txtBatchNo ,String.valueOf(data.get_txtBatchNo()));
					item1.put(data.Property_txtDataId ,String.valueOf(data.get_txtDataId()));
					item1.put(data.Property_txtDesc ,String.valueOf(data.get_txtDesc()));
					item1.put(data.Property_intReason ,String.valueOf(data.get_intReason()));
					item1.put(data.Property_txtNoPenguaran ,String.valueOf(data.get_txtNoPenguaran()));
					item1.put(data.Property_txtProductName ,String.valueOf(data.get_txtProductName()));
					itemsListJquey.add(item1);
				}
				resJson.put("ListOftPenguaranDetail_MobileData", new JSONArray(itemsListJquey));
			}
			if(this.get_ListOftPenguaranHeader_MobileData()!=null){
				itemsListJquey = new ArrayList<JSONObject>();
				for (tPenguaranHeader_MobileData data : this.get_ListOftPenguaranHeader_MobileData()) {
					JSONObject item1 = new JSONObject();
					item1.put(data.Property_dtDate ,String.valueOf(data.get_dtDate()));
					item1.put(data.Property_intTypePenguaran ,String.valueOf(data.get_intTypePenguaran()));
					item1.put(data.Property_txtBranchCode ,String.valueOf(data.get_txtBranchCode()));
					item1.put(data.Property_txtDesc ,String.valueOf(data.get_txtDesc()));
					item1.put(data.Property_txtDeviceId ,String.valueOf(data.get_txtDeviceId()));
					item1.put(data.Property_bitPO ,String.valueOf(data.get_bitPO()));
					item1.put(data.Property_txtNoPenguaran ,String.valueOf(data.get_txtNoPenguaran()));
					item1.put(data.Property_txtOutletCode ,String.valueOf(data.get_txtOutletCode()));
					item1.put(data.Property_txtOutletName ,String.valueOf(data.get_txtOutletName()));
					item1.put(data.Property_txtOutletCodeDestination ,String.valueOf(data.get_txtOutletCodeDestination()));
					item1.put(data.Property_txtOutletNameDestination ,String.valueOf(data.get_txtOutletNameDestination()));
					item1.put(data.Property_txtUserId ,String.valueOf(data.get_txtUserId()));
					itemsListJquey.add(item1);
				}
				resJson.put("ListOftPenguaranHeader_MobileData", new JSONArray(itemsListJquey));
			}
			if(this.get_ListOftPenguaranStatus_MobileData()!=null){
				itemsListJquey = new ArrayList<JSONObject>();
				for (tPenguaranStatus_MobileData data : this.get_ListOftPenguaranStatus_MobileData()) {
					JSONObject item1 = new JSONObject();
					item1.put(data.Property_txtNoPenguaran ,String.valueOf(data.get_txtNoPenguaran()));
					item1.put(data.Property_txtDataId ,String.valueOf(data.get_txtDataId()));
					item1.put(data.Property_txtStatus ,String.valueOf(data.get_txtStatus()));
					item1.put(data.Property_intStatus ,String.valueOf(data.get_intStatus()));
					item1.put(data.Property_bitActive ,String.valueOf(data.get_bitActive()));
					itemsListJquey.add(item1);
				}
				resJson.put("ListOftPenguaranStatus_MobileData", new JSONArray(itemsListJquey));
			}
			
			if(this.get_ListOftPODetail_mobileData()!=null){
				itemsListJquey = new ArrayList<JSONObject>();
				for (tPODetail_mobileData data : this.get_ListOftPODetail_mobileData()) {
					JSONObject item1 = new JSONObject();
					item1.put(data.Property_intProductCode ,String.valueOf(data.get_intProductCode()));
					item1.put(data.Property_txtDataId ,String.valueOf(data.get_txtDataId()));
					item1.put(data.Property_intQty ,String.valueOf(data.get_intQty()));
					item1.put(data.Property_txtNoPO ,String.valueOf(data.get_txtNoPO()));
					item1.put(data.Property_txtProductName ,String.valueOf(data.get_txtProductName()));
					item1.put(data.Property_bitActive ,String.valueOf(data.get_bitActive()));
					item1.put(data.Property_intQtyGRN ,String.valueOf(data.get_intQtyGRN()));
					item1.put(data.Property_intQtySisa ,String.valueOf(data.get_intQtySisa()));
					item1.put(data.Property_intStockAwal ,String.valueOf(data.get_intStockAwal()));
					item1.put(data.Property_txtNoDoc ,String.valueOf(data.get_txtNoDoc()));
					itemsListJquey.add(item1);
				}
				resJson.put("ListOftPODetail_mobileData", new JSONArray(itemsListJquey));
			}
			if(this.get_ListOftPOHeader_mobileData()!=null){
				itemsListJquey = new ArrayList<JSONObject>();
				for (tPOHeader_mobileData data : this.get_ListOftPOHeader_mobileData()) {
					JSONObject item1 = new JSONObject();
					item1.put(data.Property_dtDate ,String.valueOf(data.get_dtDate()));
					item1.put(data.Property_txtBranchCode ,String.valueOf(data.get_txtBranchCode()));
					item1.put(data.Property_txtDesc ,String.valueOf(data.get_txtDesc()));
					item1.put(data.Property_txtDeviceId ,String.valueOf(data.get_txtDeviceId()));
					item1.put(data.Property_txtNoPO ,String.valueOf(data.get_txtNoPO()));
					item1.put(data.Property_txtOutletCode ,String.valueOf(data.get_txtOutletCode()));
					item1.put(data.Property_txtOutletName ,String.valueOf(data.get_txtOutletName()));
					item1.put(data.Property_txtUserId ,String.valueOf(data.get_txtUserId()));
					item1.put(data.Property_intStockAwal ,String.valueOf(data.get_intStockAwal()));
					itemsListJquey.add(item1);
				}
				resJson.put("ListOftPOHeader_mobileData", new JSONArray(itemsListJquey));
			}
			if(this.get_ListOftPOStatus_mobileData()!=null){
				itemsListJquey = new ArrayList<JSONObject>();
				for (tPOStatus_mobileData data : this.get_ListOftPOStatus_mobileData()) {
					JSONObject item1 = new JSONObject();
					item1.put(data.Property_txtDataId ,String.valueOf(data.get_txtDataId()));
					item1.put(data.Property_intStatus ,String.valueOf(data.get_intStatus()));
					item1.put(data.Property_txtStatus ,String.valueOf(data.get_txtStatus()));
					item1.put(data.Property_txtNoPO ,String.valueOf(data.get_txtNoPO()));
					item1.put(data.Property_txtNoDoc ,String.valueOf(data.get_txtNoDoc()));
					item1.put(data.Property_bitActive ,String.valueOf(data.get_intBitActive()));
					itemsListJquey.add(item1);
				}
				resJson.put("ListOftPOStatus_mobileData", new JSONArray(itemsListJquey));
			}
			if(this.get_ListOftSalesOrderDetail_MobileData()!=null){
				itemsListJquey = new ArrayList<JSONObject>();
				for (tSalesOrderDetail_MobileData data : this.get_ListOftSalesOrderDetail_MobileData()) {
					JSONObject item1 = new JSONObject();
					item1.put(data.Property_dtED ,String.valueOf(data.get_dtED()));
					item1.put(data.Property_intProductCode ,String.valueOf(data.get_intProductCode()));
					item1.put(data.Property_intQty ,String.valueOf(data.get_intQty()));
					item1.put(data.Property_txtBatchNo ,String.valueOf(data.get_txtBatchNo()));
					item1.put(data.Property_txtDataId ,String.valueOf(data.get_txtDataId()));
					item1.put(data.Property_txtNoSalesOrder ,String.valueOf(data.get_txtNoSalesOrder()));
					item1.put(data.Property_txtProductName ,String.valueOf(data.get_txtProductName()));
					item1.put(data.Property_intItemPriceID ,String.valueOf(data.get_intItemPriceID()));
					item1.put(data.Property_decPrice ,String.valueOf(data.get_decPrice()));
					itemsListJquey.add(item1);
				}
				resJson.put("ListOftSalesOrderDetail_MobileData", new JSONArray(itemsListJquey));
			}
			if(this.get_ListOftSalesOrderHeader_MobileData()!=null){
				itemsListJquey = new ArrayList<JSONObject>();
				for (tSalesOrderHeader_MobileData data : this.get_ListOftSalesOrderHeader_MobileData()) {
					JSONObject item1 = new JSONObject();
					item1.put(data.Property_dtDate ,String.valueOf(data.get_dtDate()));
					item1.put(data.Property_txtBranchCode ,String.valueOf(data.get_txtBranchCode()));
					item1.put(data.Property_txtDeviceId ,String.valueOf(data.get_txtDeviceId()));
					item1.put(data.Property_txtNoSalesOrder ,String.valueOf(data.get_txtNoSalesOrder()));
					item1.put(data.Property_txtOutletCode ,String.valueOf(data.get_txtOutletCode()));
					item1.put(data.Property_txtOutletName ,String.valueOf(data.get_txtOutletName()));
					item1.put(data.Property_txtUserId ,String.valueOf(data.get_txtUserId()));
					itemsListJquey.add(item1);
				}
				resJson.put("ListOftSalesOrderHeader_MobileData", new JSONArray(itemsListJquey));
			}
			if(this.get_ListOftStockOpnameDetail_mobileData()!=null){
				itemsListJquey = new ArrayList<JSONObject>();
				for (tStockOpnameDetail_mobileData data : this.get_ListOftStockOpnameDetail_mobileData()) {
					JSONObject item1 = new JSONObject();
					item1.put(data.Property_dtED ,String.valueOf(data.get_dtED()));
					item1.put(data.Property_intProductCode ,String.valueOf(data.get_intProductCode()));
					item1.put(data.Property_intQty ,String.valueOf(data.get_intQty()));
					item1.put(data.Property_intQtyAdj ,String.valueOf(data.get_intQtyAdj()));
					item1.put(data.Property_intQtyStock ,String.valueOf(data.get_intQtyStock()));
					item1.put(data.Property_txtBatchNo ,String.valueOf(data.get_txtBatchNo()));
					item1.put(data.Property_txtDataId ,String.valueOf(data.get_txtDataId()));
					item1.put(data.Property_txtNoAdj ,String.valueOf(data.get_txtNoAdj()));
					item1.put(data.Property_txtProductName ,String.valueOf(data.get_txtProductName()));
					itemsListJquey.add(item1);
				}
				resJson.put("ListOftStockOpnameDetail_mobileData", new JSONArray(itemsListJquey));
			}
			if(this.get_ListOftStockOpnameHeader_mobileData()!=null){
				itemsListJquey = new ArrayList<JSONObject>();
				for (tStockOpnameHeader_mobileData data : this.get_ListOftStockOpnameHeader_mobileData()) {
					JSONObject item1 = new JSONObject();
					item1.put(data.Property_dtDate ,String.valueOf(data.get_dtDate()));
					item1.put(data.Property_txtBranchCode ,String.valueOf(data.get_txtBranchCode()));
					item1.put(data.Property_txtDeviceId ,String.valueOf(data.get_txtDeviceId()));
					item1.put(data.Property_txtNoAdj ,String.valueOf(data.get_txtNoAdj()));
					item1.put(data.Property_txtOutletCode ,String.valueOf(data.get_txtOutletCode()));
					item1.put(data.Property_txtOutletName ,String.valueOf(data.get_txtOutletName()));
					item1.put(data.Property_txtUserId ,String.valueOf(data.get_txtUserId()));
					itemsListJquey.add(item1);
				}
				resJson.put("ListOftStockOpnameHeader_mobileData", new JSONArray(itemsListJquey));
			}
			if(this.get_ListOftTransactionDetailData()!=null){
				itemsListJquey = new ArrayList<JSONObject>();
				for (tTransactionDetailData data : this.get_ListOftTransactionDetailData()) {
					JSONObject item1 = new JSONObject();
					item1.put(data.Property_dtDate ,String.valueOf(data.get_dtDate()));
					item1.put(data.Property_dtED ,String.valueOf(data.get_dtED()));
					item1.put(data.Property_intProductCode ,String.valueOf(data.get_intProductCode()));
					item1.put(data.Property_intWeek ,String.valueOf(data.get_intWeek()));
					item1.put(data.Property_txtBatchNo ,String.valueOf(data.get_txtBatchNo()));
					item1.put(data.Property_txtBranchCode ,String.valueOf(data.get_txtBranchCode()));
					item1.put(data.Property_txtNoDoc ,String.valueOf(data.get_txtNoDoc()));
					item1.put(data.Property_intQty ,String.valueOf(data.get_intQty()));
					item1.put(data.Property_txtNoMO ,String.valueOf(data.get_txtNoMO()));
					item1.put(data.Property_txtOutletCode ,String.valueOf(data.get_txtOutletCode()));
					item1.put(data.Property_txtOutletName ,String.valueOf(data.get_txtOutletName()));
					item1.put(data.Property_txtTransId ,String.valueOf(data.get_txtTransId()));
					item1.put(data.Property_txtType ,String.valueOf(data.get_txtType()));
					itemsListJquey.add(item1);
				}
				resJson.put("ListOftTransactionDetailData", new JSONArray(itemsListJquey));
			}
		}
		catch(Exception ex)
		{
		
		}
		resJson.put(Property_intResult, getIntResult());
		resJson.put(Property_txtDescription, getTxtDescription());
		resJson.put(Property_txtMessage, getTxtMessage());
		resJson.put(Property_txtMethod, getTxtMethod());
		resJson.put(Property_txtValue, getTxtValue());
		resJson.put(Property_txtSessionLoginId, get_txtSessionLoginId());
		resJson.put(Property_txtUserId, get_txtUserId());
		return resJson;
	}
	public JSONObject GenerateJsontSalesProductDetailData(tSalesProductDetailData data) throws JSONException{
		JSONObject item1 = new JSONObject();
		item1.put(data.Property_dtDate, String.valueOf(data.get_dtDate()));
		item1.put(data.Property_intActive, String.valueOf(data.get_intActive()));
		item1.put(data.Property_intId, String.valueOf(data.get_intId()));
		item1.put(data.Property_intPrice, String.valueOf(data.get_intPrice()));
		item1.put(data.Property_intQty, String.valueOf(data.get_intQty()));
		item1.put(data.Property_intTotal, String.valueOf(data.get_intTotal()));
		item1.put(data.Property_txtCodeProduct, String.valueOf(data.get_txtCodeProduct()));
		item1.put(data.Property_txtKeterangan, String.valueOf(data.get_txtKeterangan()));
		item1.put(data.Property_txtNameProduct, String.valueOf(data.get_txtNameProduct()));
		item1.put(data.Property_txtNIK, String.valueOf(data.get_txtNIK()));
		item1.put(data.Property_txtNoSo, String.valueOf(data.get_txtNoSo()));
		return item1;
	}
	public JSONObject GenerateJsontSalesProductHeaderData(tSalesProductHeaderData data) throws JSONException{
		JSONObject item1 = new JSONObject();
		item1.put(data.Property_intId, String.valueOf(data.get_intId()));
		item1.put(data.Property_txtKeterangan, String.valueOf(data.get_txtKeterangan()));
		item1.put(data.Property_txtNIK, String.valueOf(data.get_txtNIK()));
		item1.put(data.Property_txtBranchCode, String.valueOf(data.get_txtBranchCode()));
		item1.put(data.Property_txtBranchName, String.valueOf(data.get_txtBranchName()));
		item1.put(data.Property_txtDate, String.valueOf(data.get_dtDate()));
		item1.put(data.Property_intIdAbsenUser, String.valueOf(data.get_intIdAbsenUser()));
		item1.put(data.Property_intSumAmount, String.valueOf(data.get_intSumAmount()));
		item1.put(data.Property_intSumItem, String.valueOf(data.get_intSumItem()));
		item1.put(data.Property_OutletCode, String.valueOf(data.get_OutletCode()));
		item1.put(data.Property_OutletName, String.valueOf(data.get_OutletName()));
		item1.put(data.Property_UserId, String.valueOf(data.get_UserId()));
		return item1;
	}
	
	public JSONObject GenerateJsontInventorySPGDetail_mobile(tInventorySPGDetail_mobileData data) throws JSONException{
		JSONObject item1 = new JSONObject();
		item1.put(data.Property_dtDate, String.valueOf(data.get_dtDate()));
		item1.put(data.Property_intActive, String.valueOf(data.get_intActive()));
		item1.put(data.Property_intId, String.valueOf(data.get_intId()));
		item1.put(data.Property_intQtyDisplay, String.valueOf(data.get_intQtyDisplay()));
		item1.put(data.Property_intQtyGudang, String.valueOf(data.get_intQtyGudang()));
		item1.put(data.Property_intTotal, String.valueOf(data.get_intTotal()));
		item1.put(data.Property_txtCodeProduct, String.valueOf(data.get_txtCodeProduct()));
		item1.put(data.Property_txtKeterangan, String.valueOf(data.get_txtKeterangan()));
		item1.put(data.Property_txtNameProduct, String.valueOf(data.get_txtNameProduct()));
		item1.put(data.Property_txtNIK, String.valueOf(data.get_txtNIK()));
		item1.put(data.Property_txtNoInv, String.valueOf(data.get_txtNoInv()));
		return item1;
	}
	public JSONObject GenerateJsontInventorySPGHeader_mobile(tInventorySPGHeader_mobileData data) throws JSONException{
		JSONObject item1 = new JSONObject();
		item1.put(data.Property_intId, String.valueOf(data.get_intId()));
		item1.put(data.Property_txtKeterangan, String.valueOf(data.get_txtKeterangan()));
		item1.put(data.Property_txtNIK, String.valueOf(data.get_txtNIK()));
		item1.put(data.Property_txtBranchCode, String.valueOf(data.get_txtBranchCode()));
		item1.put(data.Property_txtBranchName, String.valueOf(data.get_txtBranchName()));
		item1.put(data.Property_txtDate, String.valueOf(data.get_dtDate()));
		item1.put(data.Property_intIdAbsenUser, String.valueOf(data.get_intIdAbsenUser()));
		item1.put(data.Property_intSumAmount, String.valueOf(data.get_intSumAmount()));
		item1.put(data.Property_intSumItem, String.valueOf(data.get_intSumItem()));
		item1.put(data.Property_OutletCode, String.valueOf(data.get_OutletCode()));
		item1.put(data.Property_OutletName, String.valueOf(data.get_OutletName()));
		item1.put(data.Property_UserId, String.valueOf(data.get_UserId()));
		return item1;
	}
	
	
	
	public JSONObject GenerateJsontCustomerBaseDetailData(tCustomerBaseDetailData data) throws JSONException{
		JSONObject item1 = new JSONObject();
		item1.put(data.Property_intCustomerId, String.valueOf(data.get_intCustomerId()));
		item1.put(data.Property_intQty, String.valueOf(data.get_intQty()));
		item1.put(data.Property_txtProductBrandCode, String.valueOf(data.get_txtProductBrandCode()));
		item1.put(data.Property_txtProductBrandName, String.valueOf(data.get_txtProductBrandName()));
		return item1;
	}
	
	public JSONObject GenerateJsontCustomerBaseData(tCustomerBaseData data) throws JSONException{
		JSONObject item1 = new JSONObject();
		item1.put(data.Property_bitActive, String.valueOf(data.get_bitActive()));
		item1.put(data.Property_dtDate, String.valueOf(data.get_dtDate()));
		item1.put(data.Property_intCustomerId, String.valueOf(data.get_intCustomerId()));
		item1.put(data.Property_intCustomerIdSync, String.valueOf(data.get_intCustomerIdSync()));
		item1.put(data.Property_intSubmit, String.valueOf(data.get_intSubmit()));
		item1.put(data.Property_txtAlamat, String.valueOf(data.get_txtAlamat()));
		item1.put(data.Property_txtBranchId, String.valueOf(data.get_txtBranchId()));
		item1.put(data.Property_txtDeviceId, String.valueOf(data.get_txtDeviceId()));
		item1.put(data.Property_txtNama, String.valueOf(data.get_txtNama()));
		item1.put(data.Property_txtOutletId, String.valueOf(data.get_txtOutletId()));
		item1.put(data.Property_txtProductBrandCode, String.valueOf(data.get_txtProductBrandCode()));
		item1.put(data.Property_txtSex, String.valueOf(data.get_txtSex()));
		item1.put(data.Property_txtTelp, String.valueOf(data.get_txtTelp()));
		item1.put(data.Property_txtUserId, String.valueOf(data.get_txtUserId()));
		return item1;
	}
	
	public String getIntResult() {
		return intResult;
	}
	public void setIntResult(String intResult) {
		this.intResult = intResult;
	}
	public String getTxtMessage() {
		return txtMessage;
	}
	public void setTxtMessage(String txtMessage) {
		this.txtMessage = txtMessage;
	}
	public String getTxtDescription() {
		return txtDescription;
	}
	public void setTxtDescription(String txtDescription) {
		this.txtDescription = txtDescription;
	}
	public List<mconfigData> getListDatamConfig() {
		return ListDatamConfig;
	}
	public void setListDatamConfig(List<mconfigData> listDatamConfig) {
		ListDatamConfig = listDatamConfig;
	}
	public dataJson() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private String intResult;
	private String txtMessage;
	private String txtMethod;
	private String txtValue;
	private String txtDescription;
	private List<mconfigData> ListDatamConfig;
	private List<tUserLoginData> ListDatatUserLogin;
	private List<tDeviceInfoUserData> ListDatatDeviceInfoUser;
	private List<mMenuData> ListOfmMenuData;
	private List<tSalesProductDetailData> ListOftSalesProductDetailData;
	private List<tSalesProductHeaderData> ListOftSalesProductHeaderData;
	private List<mEmployeeAreaData> ListOfmEmployeeAreaData;
	private List<mEmployeeBranchData> ListOfmEmployeeBranchData;
	private List<mEmployeeSalesProductData> ListOfmEmployeeSalesProductData;
	private List<mNotificationData> ListOfmNotificationData;
	private List<tErrorLogData> ListOftErrorLogData;
	private List<tAbsenUserData> ListOftAbsenUserData;
	private List<mGeolocationOutletSPGData> ListOfmGeolocationOutletSPGData;
	private List<tReportSalesSOData> ListOftReportSalesSOData;
	private List<tReportInventorySPGData> ListOftReportInventorySPGData;
	private List<tActivityData> ListOftActivityData;
	private List<tCustomerBaseData> ListOftCustomerBase;
	private List<tCustomerBaseDetailData> ListOftCustomerBaseDetailData;
	private List<mProductBrandHeaderData> ListOfmProductBrandHeaderData;
	private List<tLeaveMobileData> ListOftLeaveMobileData;
	private List<mTypeLeaveMobileData> ListOfmTypeLeaveMobileData;
	private List<tCustomerBaseInfoData> ListOftCustomerBaseInfoData;
	private List<tInventorySPGDetail_mobileData> ListOftInventorySPGDetail_mobileData;
	private List<tInventorySPGHeader_mobileData> ListOftInventorySPGHeader_mobileData;
	private List<tEmployeeBRWithLOBData> ListOftEmployeeBRWithLOBData;
	
	private List<tPenguaranDetail_MobileData> _ListOftPenguaranDetail_MobileData;
	private List<tPenguaranHeader_MobileData> _ListOftPenguaranHeader_MobileData;
	private List<tPenguaranStatus_MobileData> _ListOftPenguaranStatus_MobileData;
	private List<tPOHeader_mobileData> _ListOftPOHeader_mobileData;
	private List<tPODetail_mobileData> _ListOftPODetail_mobileData;
	private List<tPOStatus_mobileData> _ListOftPOStatus_mobileData;
	private List<tTransactionDetailData> _ListOftTransactionDetailData;
	
	private List<tSalesOrderDetail_MobileData> _ListOftSalesOrderDetail_MobileData;
	private List<tSalesOrderHeader_MobileData> _ListOftSalesOrderHeader_MobileData;
	private List<tGRNHeader_mobileData> _ListOftGRNHeader_mobileData;
	private List<tGRNDetail_mobileData> _ListOftGRNDetail_mobileData;
	private List<mProductBarcodeData> _ListOfmProductBarcodeData;
	private List<mItemSalesPack_StockData> _ListOfmItemSalesPack_StockData;
	private List<mItemSalesPack_StockLogData> _ListOfmItemSalesPack_StockLogData;
	private List<mStockAwalData> _ListOfmStockAwalData;
	public List<mStockAwalData> get_ListOfmStockAwalData() {
		return _ListOfmStockAwalData;
	}
	public void set_ListOfmStockAwalData(List<mStockAwalData> _ListOfmStockAwalData) {
		this._ListOfmStockAwalData = _ListOfmStockAwalData;
	}
	public List<tPenguaranDetail_MobileData> get_ListOftPenguaranDetail_MobileData() {
		return _ListOftPenguaranDetail_MobileData;
	}
	public void set_ListOftPenguaranDetail_MobileData(
			List<tPenguaranDetail_MobileData> _ListOftPenguaranDetail_MobileData) {
		this._ListOftPenguaranDetail_MobileData = _ListOftPenguaranDetail_MobileData;
	}
	public List<tPenguaranHeader_MobileData> get_ListOftPenguaranHeader_MobileData() {
		return _ListOftPenguaranHeader_MobileData;
	}
	public void set_ListOftPenguaranHeader_MobileData(
			List<tPenguaranHeader_MobileData> _ListOftPenguaranHeader_MobileData) {
		this._ListOftPenguaranHeader_MobileData = _ListOftPenguaranHeader_MobileData;
	}
	public List<tPenguaranStatus_MobileData> get_ListOftPenguaranStatus_MobileData() {
		return _ListOftPenguaranStatus_MobileData;
	}
	public void set_ListOftPenguaranStatus_MobileData(
			List<tPenguaranStatus_MobileData> _ListOftPenguaranStatus_MobileData) {
		this._ListOftPenguaranStatus_MobileData = _ListOftPenguaranStatus_MobileData;
	}
	public List<tPOHeader_mobileData> get_ListOftPOHeader_mobileData() {
		return _ListOftPOHeader_mobileData;
	}
	public void set_ListOftPOHeader_mobileData(List<tPOHeader_mobileData> _ListOftPOHeader_mobileData) {
		this._ListOftPOHeader_mobileData = _ListOftPOHeader_mobileData;
	}
	public List<tPODetail_mobileData> get_ListOftPODetail_mobileData() {
		return _ListOftPODetail_mobileData;
	}
	public void set_ListOftPODetail_mobileData(List<tPODetail_mobileData> _ListOftPODetail_mobileData) {
		this._ListOftPODetail_mobileData = _ListOftPODetail_mobileData;
	}
	public List<tPOStatus_mobileData> get_ListOftPOStatus_mobileData() {
		return _ListOftPOStatus_mobileData;
	}
	public void set_ListOftPOStatus_mobileData(List<tPOStatus_mobileData> _ListOftPOStatus_mobileData) {
		this._ListOftPOStatus_mobileData = _ListOftPOStatus_mobileData;
	}
	public List<tTransactionDetailData> get_ListOftTransactionDetailData() {
		return _ListOftTransactionDetailData;
	}
	public void set_ListOftTransactionDetailData(List<tTransactionDetailData> _ListOftTransactionDetailData) {
		this._ListOftTransactionDetailData = _ListOftTransactionDetailData;
	}

	private List<tStockOpnameDetail_mobileData> _ListOftStockOpnameDetail_mobileData;
	private List<tStockOpnameHeader_mobileData> _ListOftStockOpnameHeader_mobileData;
	
	public List<tSalesOrderDetail_MobileData> get_ListOftSalesOrderDetail_MobileData() {
		return _ListOftSalesOrderDetail_MobileData;
	}
	public void set_ListOftSalesOrderDetail_MobileData(
			List<tSalesOrderDetail_MobileData> _ListOftSalesOrderDetail_MobileData) {
		this._ListOftSalesOrderDetail_MobileData = _ListOftSalesOrderDetail_MobileData;
	}
	public List<tSalesOrderHeader_MobileData> get_ListOftSalesOrderHeader_MobileData() {
		return _ListOftSalesOrderHeader_MobileData;
	}
	public void set_ListOftSalesOrderHeader_MobileData(
			List<tSalesOrderHeader_MobileData> _ListOftSalesOrderHeader_MobileData) {
		this._ListOftSalesOrderHeader_MobileData = _ListOftSalesOrderHeader_MobileData;
	}
	public List<tGRNHeader_mobileData> get_ListOftGRNHeader_mobileData() {
		return _ListOftGRNHeader_mobileData;
	}
	public void set_ListOftGRNHeader_mobileData(List<tGRNHeader_mobileData> _ListOftGRNHeader_mobileData) {
		this._ListOftGRNHeader_mobileData = _ListOftGRNHeader_mobileData;
	}
	public List<tGRNDetail_mobileData> get_ListOftGRNDetail_mobileData() {
		return _ListOftGRNDetail_mobileData;
	}
	public void set_ListOftGRNDetail_mobileData(List<tGRNDetail_mobileData> _ListOftGRNDetail_mobileData) {
		this._ListOftGRNDetail_mobileData = _ListOftGRNDetail_mobileData;
	}
	public List<mProductBarcodeData> get_ListOfmProductBarcodeData() {
		return _ListOfmProductBarcodeData;
	}
	public void set_ListOfmProductBarcodeData(List<mProductBarcodeData> _ListOfmProductBarcodeData) {
		this._ListOfmProductBarcodeData = _ListOfmProductBarcodeData;
	}
	public List<mItemSalesPack_StockData> get_ListOfmItemSalesPack_StockData() {
		return _ListOfmItemSalesPack_StockData;
	}
	public void set_ListOfmItemSalesPack_StockData(List<mItemSalesPack_StockData> _ListOfmItemSalesPack_StockData) {
		this._ListOfmItemSalesPack_StockData = _ListOfmItemSalesPack_StockData;
	}
	public List<tStockOpnameDetail_mobileData> get_ListOftStockOpnameDetail_mobileData() {
		return _ListOftStockOpnameDetail_mobileData;
	}
	public void set_ListOftStockOpnameDetail_mobileData(
			List<tStockOpnameDetail_mobileData> _ListOftStockOpnameDetail_mobileData) {
		this._ListOftStockOpnameDetail_mobileData = _ListOftStockOpnameDetail_mobileData;
	}
	public List<tStockOpnameHeader_mobileData> get_ListOftStockOpnameHeader_mobileData() {
		return _ListOftStockOpnameHeader_mobileData;
	}
	public void set_ListOftStockOpnameHeader_mobileData(
			List<tStockOpnameHeader_mobileData> _ListOftStockOpnameHeader_mobileData) {
		this._ListOftStockOpnameHeader_mobileData = _ListOftStockOpnameHeader_mobileData;
	}

	private List<tUserLOBData> ListOftUserLOBData;
	private String _txtSessionLoginId;
	public String get_txtSessionLoginId() {
		return _txtSessionLoginId;
	}
	public void set_txtSessionLoginId(String _txtSessionLoginId) {
		this._txtSessionLoginId = _txtSessionLoginId;
	}

	private String _txtUserId;
	public String get_txtUserId() {
		return _txtUserId;
	}
	public void set_txtUserId(String _txtUserId) {
		this._txtUserId = _txtUserId;
	}

	public List<mItemSalesPack_StockLogData> get_ListOfmItemSalesPack_StockLogData() {
		return _ListOfmItemSalesPack_StockLogData;
	}
	public void set_ListOfmItemSalesPack_StockLogData(List<mItemSalesPack_StockLogData> _ListOfmItemSalesPack_StockLogData) {
		this._ListOfmItemSalesPack_StockLogData = _ListOfmItemSalesPack_StockLogData;
	}

	private String Property_txtUserId="txtUserId";
	private String Property_txtSessionLoginId="txtSessionLoginId";
	public String Property_intResult="intResult";
	public String Property_txtMessage="txtMessage";
	public String Property_txtDescription="txtDescription";
	public String Property_txtMethod="txtMethod";
	public String Property_txtValue="txtValue";
}
