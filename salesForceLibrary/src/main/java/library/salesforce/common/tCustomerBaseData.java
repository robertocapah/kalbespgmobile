package library.salesforce.common;

public class tCustomerBaseData {
	public synchronized String get_txtProductBrandCode() {
		return _txtProductBrandCode;
	}
	public synchronized void set_txtProductBrandCode(String _txtProductBrandCode) {
		this._txtProductBrandCode = _txtProductBrandCode;
	}
	public synchronized String get_txtSex() {
		return _txtSex;
	}
	public synchronized void set_txtSex(String _txtSex) {
		this._txtSex = _txtSex;
	}
	public synchronized String get_txtUserId() {
		return _txtUserId;
	}
	public synchronized void set_txtUserId(String _txtUserId) {
		this._txtUserId = _txtUserId;
	}
	public synchronized String get_txtDeviceId() {
		return _txtDeviceId;
	}
	public synchronized void set_txtDeviceId(String _txtDeviceId) {
		this._txtDeviceId = _txtDeviceId;
	}
	public synchronized String get_intCustomerId() {
		return _intCustomerId;
	}
	public synchronized void set_intCustomerId(String _intCustomerId) {
		this._intCustomerId = _intCustomerId;
	}
	public synchronized String get_txtOutletId() {
		return _txtOutletId;
	}
	public synchronized void set_txtOutletId(String _txtOutletId) {
		this._txtOutletId = _txtOutletId;
	}
	public synchronized String get_txtBranchId() {
		return _txtBranchId;
	}
	public synchronized void set_txtBranchId(String _txtBranchId) {
		this._txtBranchId = _txtBranchId;
	}
	public synchronized String get_txtNama() {
		return _txtNama;
	}
	public synchronized void set_txtNama(String _txtNama) {
		this._txtNama = _txtNama;
	}
	public synchronized String get_txtTelp() {
		return _txtTelp;
	}
	public synchronized void set_txtTelp(String _txtTelp) {
		this._txtTelp = _txtTelp;
	}
	public synchronized String get_txtAlamat() {
		return _txtAlamat;
	}
	public synchronized void set_txtAlamat(String _txtAlamat) {
		this._txtAlamat = _txtAlamat;
	}
	public synchronized String get_dtDate() {
		return _dtDate;
	}
	public synchronized void set_dtDate(String _dtDate) {
		this._dtDate = _dtDate;
	}
	public synchronized String get_bitActive() {
		return _bitActive;
	}
	public synchronized void set_bitActive(String _bitActive) {
		this._bitActive = _bitActive;
	}
	public synchronized String get_intCustomerIdSync() {
		return _intCustomerIdSync;
	}
	public synchronized void set_intCustomerIdSync(String _intCustomerIdSync) {
		this._intCustomerIdSync = _intCustomerIdSync;
	}
	public synchronized String get_intSubmit() {
		return _intSubmit;
	}
	public synchronized void set_intSubmit(String _intSubmit) {
		this._intSubmit = _intSubmit;
	}
	private String _intCustomerId;
	private String _txtOutletId;
	private String _txtBranchId;
	private String _txtNama;
	private String _txtTelp;
	private String _txtAlamat;
	private String _dtDate;
	private String _bitActive;
	private String _intCustomerIdSync;
	private String _intSubmit;
	private String _txtProductBrandCode;
	private String _txtUserId;
	private String _txtDeviceId;
	private String _txtSex;
	public tCustomerBaseData() {
		// TODO Auto-generated constructor stub
	}
	
	public String Property_intCustomerId="intCustomerId";
	public String Property_txtOutletId="txtOutletId";
	public String Property_txtBranchId="txtBranchId";
	public String Property_txtNama="txtNama";
	public String Property_txtTelp="txtTelp";
	public String Property_txtAlamat="txtAlamat";
	public String Property_dtDate="dtDate";
	public String Property_bitActive="bitActive";
	public String Property_intCustomerIdSync="intCustomerIdSync";
	public String Property_intSubmit="intSubmit";
	public String Property_txtUserId="txtUserId";
	public String Property_txtSex="txtSex";
	public String Property_txtDeviceId="txtDeviceId";
	public String Property_txtProductBrandCode="txtProductBrandCode";
	
	public String Property_ListOftCustomerBaseData="ListOftCustomerBaseData";
	public String Property_ALL=Property_bitActive+","+Property_dtDate+","+Property_intCustomerId+","+Property_intCustomerIdSync+","+
			Property_intSubmit+","+Property_txtAlamat+","+Property_txtBranchId+","+Property_txtNama+","+Property_txtOutletId+","+
			Property_txtTelp+","+Property_txtUserId+","+Property_txtDeviceId+","+Property_txtSex+","+Property_txtProductBrandCode;
}
