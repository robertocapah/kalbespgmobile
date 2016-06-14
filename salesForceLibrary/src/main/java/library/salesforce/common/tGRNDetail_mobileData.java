package library.salesforce.common;

import java.io.Serializable;

public class tGRNDetail_mobileData implements Serializable{
	private String _txtDataId;
	private String _txtNoGRN;
	private String _intProductCode;
	private String _txtProductName;
	private String _txtBatchNo;
	private String _dtED;
	private String _intQty;
	private String _intSubmit;
	private String _intStockAwal;
	private String _intSync;
	private String _intReason;
	
	public String get_intReason() {
		return _intReason;
	}
	public void set_intReason(String _intReason) {
		this._intReason = _intReason;
	}
	public String Property_txtDataId="txtDataId";
	public String Property_txtNoGRN="txtNoGRN";
	public String Property_intProductCode="intProductCode";
	public String Property_txtProductName="txtProductName";
	public String Property_txtBatchNo="txtBatchNo";
	public String Property_dtED="dtED";
	public String Property_intQty="intQty";
	public String Property_intSubmit="intSubmit";
	public String Property_intStockAwal="intStockAwal";
	public String Property_intSync="intSync";
	public String Property_intReason="intReason";
	public String Property_ListOftGRNDetail_mobileData="ListOftGRNDetail_mobileData";
	public String PropertyAll=Property_dtED+","+Property_intProductCode+","+Property_intQty+","+Property_intReason+","+Property_intSubmit+","+ Property_intStockAwal + "," + Property_intSync+","+
	Property_txtBatchNo+","+Property_txtDataId+","+Property_txtNoGRN+","+Property_txtProductName;
	public synchronized String get_txtDataId() {
		return _txtDataId;
	}
	public synchronized void set_txtDataId(String _txtDataId) {
		this._txtDataId = _txtDataId;
	}
	public synchronized String get_txtNoGRN() {
		return _txtNoGRN;
	}
	public synchronized void set_txtNoGRN(String _txtNoGRN) {
		this._txtNoGRN = _txtNoGRN;
	}
	public synchronized String get_intProductCode() {
		return _intProductCode;
	}
	public synchronized void set_intProductCode(String _intProductCode) {
		this._intProductCode = _intProductCode;
	}
	public synchronized String get_txtProductName() {
		return _txtProductName;
	}
	public synchronized void set_txtProductName(String _txtProductName) {
		this._txtProductName = _txtProductName;
	}
	public synchronized String get_txtBatchNo() {
		return _txtBatchNo;
	}
	public synchronized void set_txtBatchNo(String _txtBatchNo) {
		this._txtBatchNo = _txtBatchNo;
	}
	public synchronized String get_dtED() {
		return _dtED;
	}
	public synchronized void set_dtED(String _dtED) {
		this._dtED = _dtED;
	}
	public synchronized String get_intQty() {
		return _intQty;
	}
	public synchronized void set_intQty(String _intQty) {
		this._intQty = _intQty;
	}
	public synchronized String get_intSubmit() {
		return _intSubmit;
	}
	public synchronized void set_intSubmit(String _intSubmit) {
		this._intSubmit = _intSubmit;
	}
	public synchronized String get_intSync() {
		return _intSync;
	}
	public synchronized void set_intSync(String _intSync) {
		this._intSync = _intSync;
	}
	public String get_intStockAwal() {
		return _intStockAwal;
	}
	public void set_intStockAwal(String _intStockAwal) {
		this._intStockAwal = _intStockAwal;
	}
}
