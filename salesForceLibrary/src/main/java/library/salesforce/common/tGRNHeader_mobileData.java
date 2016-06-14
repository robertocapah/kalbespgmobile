package library.salesforce.common;

import java.io.Serializable;

public class tGRNHeader_mobileData implements Serializable{
	private String _txtNoGRN;
	private String _dtDate;
	private String _txtOutletCode;
	private String _txtOutletName;
	private String _txtBranchCode;
	private String _txtNoPO;
	private String _txtSource;
	private String _intStockAwal;
	
	public String get_txtNoPO() {
		return _txtNoPO;
	}
	public void set_txtNoPO(String _txtNoPO) {
		this._txtNoPO = _txtNoPO;
	}
	public String get_txtSource() {
		return _txtSource;
	}
	public void set_txtSource(String _txtSource) {
		this._txtSource = _txtSource;
	}
	private String _txtNoMO;
	private String _txtDeviceId;
	private String _txtUserId;
	private String _intSubmit;
	private String _intSync;
	
	public String Property_txtNoGRN="txtNoGRN";
	public String Property_dtDate="dtDate";
	public String Property_txtOutletCode="txtOutletCode";
	public String Property_txtOutletName="txtOutletName";
	public String Property_txtBranchCode="txtBranchCode";
	public String Property_txtNoMO="txtNoMO";
	public String Property_txtNoPO="txtNoPO";
	public String Property_txtSource="txtSource";
	public String Property_txtDeviceId="txtDeviceId";
	public String Property_txtUserId="txtUserId";
	public String Property_intSubmit="intSubmit";
	public String Property_intSync="intSync";
	public String Property_intStockAwal="intStockAwal";
	public String PropertyAll=Property_dtDate+","+Property_intSubmit+ "," + Property_intStockAwal + ","+Property_intSync+","+Property_txtBranchCode+","+
	Property_txtDeviceId+","+Property_txtNoGRN+","+Property_txtNoMO+","+Property_txtNoPO+","+Property_txtOutletCode+","+Property_txtOutletName+","+
	Property_txtSource+","+Property_txtUserId;
	public String get_txtNoGRN() {
		return _txtNoGRN;
	}
	public void set_txtNoGRN(String _txtNoGRN) {
		this._txtNoGRN = _txtNoGRN;
	}
	public String get_dtDate() {
		return _dtDate;
	}
	public void set_dtDate(String _dtDate) {
		this._dtDate = _dtDate;
	}
	public String get_txtOutletCode() {
		return _txtOutletCode;
	}
	public void set_txtOutletCode(String _txtOutletCode) {
		this._txtOutletCode = _txtOutletCode;
	}
	public String get_txtOutletName() {
		return _txtOutletName;
	}
	public void set_txtOutletName(String _txtOutletName) {
		this._txtOutletName = _txtOutletName;
	}
	public String get_txtBranchCode() {
		return _txtBranchCode;
	}
	public void set_txtBranchCode(String _txtBranchCode) {
		this._txtBranchCode = _txtBranchCode;
	}
	public String get_txtNoMO() {
		return _txtNoMO;
	}
	public void set_txtNoMO(String _txtNoMO) {
		this._txtNoMO = _txtNoMO;
	}
	public String get_txtDeviceId() {
		return _txtDeviceId;
	}
	public void set_txtDeviceId(String _txtDeviceId) {
		this._txtDeviceId = _txtDeviceId;
	}
	public String get_txtUserId() {
		return _txtUserId;
	}
	public void set_txtUserId(String _txtUserId) {
		this._txtUserId = _txtUserId;
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
	public String get_intStockAwal() {
		return _intStockAwal;
	}
	public void set_intStockAwal(String _intStockAwal) {
		this._intStockAwal = _intStockAwal;
	}
}
