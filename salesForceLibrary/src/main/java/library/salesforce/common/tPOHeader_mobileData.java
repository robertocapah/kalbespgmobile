package library.salesforce.common;

public class tPOHeader_mobileData {
	private String _txtNoPO;
	private String _txtNOMO;
	private String _dtDate;
	private String _txtOutletCode;
	private String _txtOutletName;
	private String _txtBranchCode;
	private String _txtDesc;
	private String _intStockAwal;
	
	public String get_txtDesc() {
		return _txtDesc;
	}
	public void set_txtDesc(String _txtDesc) {
		this._txtDesc = _txtDesc;
	}
	private String _txtDeviceId;
	private String _txtUserId;
	private String _intSubmit;
	private String _intTyePO;
	public String get_intTyePO() {
		return _intTyePO;
	}
	public void set_intTyePO(String _intTyePO) {
		this._intTyePO = _intTyePO;
	}
	private String _intSync;
	
	public String Property_txtNoPO="txtNoPO";
	public String Property_txtNoMO="txtNoMO";
	public String Property_dtDate="dtDate";
	public String Property_txtOutletCode="txtOutletCode";
	public String Property_txtOutletName="txtOutletName";
	public String Property_txtBranchCode="txtBranchCode";
	public String Property_txtDeviceId="txtDeviceId";
	public String Property_txtUserId="txtUserId";
	public String Property_intSubmit="intSubmit";
	public String Property_intStockAwal="intStockAwal";
	public String Property_typePO="intTypePO";
	public String Property_intSync="intSync";
	public String Property_txtDesc="txtDesc";
	public String Property_All=Property_dtDate+","+Property_intSubmit+ "," + Property_intStockAwal + ","+Property_intSync+","+Property_txtBranchCode+
			","+Property_txtDesc+","+Property_txtDeviceId+","+Property_txtNoPO+","+Property_txtNoMO+","+Property_txtOutletCode+","+Property_txtOutletName+
			","+Property_txtUserId+","+Property_typePO;
	
	public String get_txtNoPO() {
		return _txtNoPO;
	}
	public void set_txtNoPO(String _txtNoPO) {
		this._txtNoPO = _txtNoPO;
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
	public String get_intStockAwal() {
		return _intStockAwal;
	}
	public void set_intStockAwal(String _intStockAwal) {
		this._intStockAwal = _intStockAwal;
	}
	public String get_txtNOMO() {
		return _txtNOMO;
	}
	public void set_txtNOMO(String _txtNOMO) {
		this._txtNOMO = _txtNOMO;
	}
}
