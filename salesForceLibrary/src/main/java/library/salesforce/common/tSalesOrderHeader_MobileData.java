package library.salesforce.common;

import java.io.Serializable;

public class tSalesOrderHeader_MobileData implements Serializable{
	private String _txtNoSalesOrder;
	private String _dtDate;
	private String _txtOutletCode;
	private String _txtOutletName;
	private String _txtBranchCode;
	private String _txtDeviceId;
	private String _txtUserId;
	private String _intSubmit;
	private String _intSync;
	private String _bitPO;
	private String _intGenerate;
	
	public String get_intGenerate() {
		return _intGenerate;
	}
	public void set_intGenerate(String _intGenerate) {
		this._intGenerate = _intGenerate;
	}
	
	public String get_bitPO() {
		return _bitPO;
	}
	public void set_bitPO(String _bitPO) {
		this._bitPO = _bitPO;
	}

	public String Property_txtNoSalesOrder="txtNoSalesOrder";
	public String Property_dtDate="dtDate";
	public String Property_bitPO="bitPO";
	public String Property_intGenerate="intGenerate";
	public String Property_txtOutletCode="txtOutletCode";
	public String Property_txtOutletName="txtOutletName";
	public String Property_txtBranchCode="txtBranchCode";
	public String Property_txtDeviceId="txtDeviceId";
	public String Property_txtUserId="txtUserId";
	public String Property_intSubmit="intSubmit";
	public String Property_intSync="intSync";
	public String PropertyAll=Property_dtDate+","+Property_intSubmit+","+Property_intSync+","+Property_txtBranchCode+","+Property_txtDeviceId+","+Property_bitPO+","+Property_intGenerate+","+Property_txtNoSalesOrder+","+
			Property_txtOutletCode+","+Property_txtOutletName+","+Property_txtUserId;
	
	public String get_txtNoSalesOrder() {
		return _txtNoSalesOrder;
	}
	public void set_txtNoSalesOrder(String _txtNoSalesOrder) {
		this._txtNoSalesOrder = _txtNoSalesOrder;
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
}
