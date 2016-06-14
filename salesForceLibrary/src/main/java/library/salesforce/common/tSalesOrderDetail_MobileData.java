package library.salesforce.common;

import java.io.Serializable;

public class tSalesOrderDetail_MobileData implements Serializable{
	private String _txtDataId;
	private String _txtNoSalesOrder;
	private String _intProductCode;
	private String _txtProductName;
	private String _intQty;
	private String _txtBatchNo;
	private String _dtED;
	private String _intItemPriceID;
	private String _decPrice;
	private String _intSubmit;
	private String _intSync;
	private String _txtNoPO;
	
	public String get_txtNoPO() {
		return _txtNoPO;
	}
	public void set_txtNoPO(String _txtNoPO) {
		this._txtNoPO = _txtNoPO;
	}
	public String Property_txtDataId="txtDataId";
	public String Property_txtNoSalesOrder="txtNoSalesOrder";
	public String Property_intProductCode="intProductCode";
	public String Property_txtProductName="txtProductName";
	public String Property_intQty="intQty";
	public String Property_txtBatchNo="txtBatchNo";
	public String Property_dtED="dtED";
	public String Property_intItemPriceID="intItemPriceID";
	public String Property_decPrice="decPrice";
	public String Property_intSubmit="intSubmit";
	public String Property_intSync="intSync";
	public String Property_txtNoPO="txtNoPO";
	public String PropertyAll=Property_dtED+","+Property_intProductCode+","+Property_intQty+","+Property_intSubmit+","+Property_intSync+
			","+Property_txtBatchNo+","+Property_txtDataId+","+Property_txtNoPO+","+Property_txtNoSalesOrder+","+Property_txtProductName+","+Property_intItemPriceID+","+Property_decPrice;
	
	
	public String get_txtDataId() {
		return _txtDataId;
	}
	public void set_txtDataId(String _txtDataId) {
		this._txtDataId = _txtDataId;
	}
	public String get_txtNoSalesOrder() {
		return _txtNoSalesOrder;
	}
	public void set_txtNoSalesOrder(String _txtNoSalesOrder) {
		this._txtNoSalesOrder = _txtNoSalesOrder;
	}
	public String get_intProductCode() {
		return _intProductCode;
	}
	public void set_intProductCode(String _intProductCode) {
		this._intProductCode = _intProductCode;
	}
	public String get_txtProductName() {
		return _txtProductName;
	}
	public void set_txtProductName(String _txtProductName) {
		this._txtProductName = _txtProductName;
	}
	public String get_intQty() {
		return _intQty;
	}
	public void set_intQty(String _intQty) {
		this._intQty = _intQty;
	}
	public String get_txtBatchNo() {
		return _txtBatchNo;
	}
	public void set_txtBatchNo(String _txtBatchNo) {
		this._txtBatchNo = _txtBatchNo;
	}
	public String get_dtED() {
		return _dtED;
	}
	public void set_dtED(String _dtED) {
		this._dtED = _dtED;
	}
	public String get_intItemPriceID() {
		return _intItemPriceID;
	}
	public void set_intItemPriceID(String _intItemPriceID) {
		this._intItemPriceID = _intItemPriceID;
	}
	public String get_decPrice() {
		return _decPrice;
	}
	public void set_decPrice(String _decPrice) {
		this._decPrice = _decPrice;
	}
	public String get_intSubmit() {
		return _intSubmit;
	}
	public void set_intSubmit(String _intSubmit) {
		this._intSubmit = _intSubmit;
	}
	public String get_intSync() {
		return _intSync;
	}
	public void set_intSync(String _intSync) {
		this._intSync = _intSync;
	}
	
}
