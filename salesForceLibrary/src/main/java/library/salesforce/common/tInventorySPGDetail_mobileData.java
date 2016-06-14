package library.salesforce.common;
public class tInventorySPGDetail_mobileData {
	public synchronized String get_txtNoInv() {
		return _txtNoInv;
	}
	public synchronized void set_txtNoInv(String _txtNoInv) {
		this._txtNoInv = _txtNoInv;
	}
	public synchronized String get_intQtyDisplay() {
		return _intQtyDisplay;
	}
	public synchronized void set_intQtyDisplay(String _intQtyDisplay) {
		this._intQtyDisplay = _intQtyDisplay;
	}
	public synchronized String get_intQtyGudang() {
		return _intQtyGudang;
	}
	public synchronized void set_intQtyGudang(String _intQtyGudang) {
		this._intQtyGudang = _intQtyGudang;
	}
	public synchronized String get_intActive() {
		return _intActive;
	}
	public synchronized void set_intActive(String _intActive) {
		this._intActive = _intActive;
	}

	public synchronized String get_intTotal() {
		return _intTotal;
	}
	public synchronized void set_intTotal(String _intTotal) {
		this._intTotal = _intTotal;
	}
	public synchronized Integer get_intId() {
		return _intId;
	}
	public synchronized void set_intId(Integer _intId) {
		this._intId = _intId;
	}
	public synchronized String get_txtNIK() {
		return _txtNIK;
	}
	public synchronized void set_txtNIK(String _txtNIK) {
		this._txtNIK = _txtNIK;
	}
	public synchronized String get_txtKeterangan() {
		return _txtKeterangan;
	}
	public synchronized void set_txtKeterangan(String _txtKeterangan) {
		this._txtKeterangan = _txtKeterangan;
	}
	public synchronized String get_dtDate() {
		return _dtDate;
	}
	public synchronized void set_dtDate(String _dtDate) {
		this._dtDate = _dtDate;
	}
	public synchronized String get_txtCodeProduct() {
		return _txtCodeProduct;
	}
	public synchronized void set_txtCodeProduct(String _txtCodeProduct) {
		this._txtCodeProduct = _txtCodeProduct;
	}
	public synchronized String get_txtNameProduct() {
		return _txtNameProduct;
	}
	public synchronized void set_txtNameProduct(String _txtNameProduct) {
		this._txtNameProduct = _txtNameProduct;
	}

	private Integer _intId;
	private String _txtNIK;
	private String _txtKeterangan;
	private String _dtDate;
	private String _txtCodeProduct;
	private String _txtNameProduct;
	private String _intQtyDisplay;
	private String _intQtyGudang;
	private String _txtNoInv;
	private String _intActive;
	private String _intTotal;
	
	public String Property_intId="intId";
	public String Property_intTotal="intTotal";
	public String Property_txtNIK="txtNIK";
	public String Property_txtKeterangan="txtKeterangan";
	public String Property_dtDate="dtDate";
	public String Property_txtCodeProduct="txtCodeProduct";
	public String Property_txtNameProduct="txtNameProduct";
	public String Property_intQtyDisplay="intQtyDisplay";
	public String Property_intQtyGudang="intQtyGudang";
	public String Property_txtNoInv="txtNoInv";
	public String Property_intActive="intActive";
	public String Property_ListOftInventorySPGDetail_mobileData="ListOftInventorySPGDetail_mobileData";
	
	public String Property_All=Property_dtDate +","+
			Property_intId +","+
			Property_intQtyDisplay +","+
			Property_intQtyGudang +","+
			Property_txtCodeProduct +","+
			Property_txtKeterangan +","+
			Property_txtNameProduct +","+
			Property_intTotal +","+
			Property_txtNIK+","+
			Property_txtNoInv+","+
			Property_intActive;
	
	public tInventorySPGDetail_mobileData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
