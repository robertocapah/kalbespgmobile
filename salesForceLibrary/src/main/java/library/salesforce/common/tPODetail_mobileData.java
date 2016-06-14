package library.salesforce.common;

public class tPODetail_mobileData {
	private String _intSubmit;
	private String _intSync;
	private String _txtDataId;
	private String _txtNoPO;
	private String _txtNoDoc;
	public String get_txtNoDoc() {
		return _txtNoDoc;
	}
	public void set_txtNoDoc(String _txtNoDoc) {
		this._txtNoDoc = _txtNoDoc;
	}
	private String _intProductCode;
	private String _bitActive;
	private String _intQty;
	private String _intQtyGRN;
	private String _intQtySisa;
	public String get_intQtyGRN() {
		return _intQtyGRN;
	}
	public void set_intQtyGRN(String _intQtyGRN) {
		this._intQtyGRN = _intQtyGRN;
	}
	public String get_intQtySisa() {
		return _intQtySisa;
	}
	public void set_intQtySisa(String _intQtySisa) {
		this._intQtySisa = _intQtySisa;
	}
	private String _txtBatchNo;
	private String _dtED;
	private String _intStockAwal;
	
	public String get_intQty() {
		return _intQty;
	}
	public void set_intQty(String _intQty) {
		this._intQty = _intQty;
	}
	private String _txtProductName;
	
	public String Property_intSubmit="intSubmit";
	public String Property_intSync="intSync";
	public String Property_txtDataId="txtDataId";
	public String Property_txtNoPO="txtNoPO";
	public String Property_intProductCode="intProductCode";
	public String Property_txtProductName="txtProductName";
	public String Property_intQty="intQty";
	public String Property_bitActive="bitActive";
	public String Property_intQtyGRN="intQtyGRN";
	public String Property_intQtySisa="intQtySisa";
	public String Property_intStockAwal="intStockAwal";
	public String Property_txtNoDoc="txtNoDoc";
	public String Property_All= Property_intProductCode+","+Property_intQty+","+Property_intQtyGRN+","+Property_intQtySisa+","+Property_intStockAwal+","+Property_intSubmit+","+Property_intSync+","+Property_txtDataId+","+Property_txtNoDoc+","+Property_txtNoPO+","+Property_txtProductName+","+Property_bitActive;
	
	public String get_bitActive() {
		return _bitActive;
	}
	public void set_bitActive(String _bitActive) {
		this._bitActive = _bitActive;
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
	public String get_txtDataId() {
		return _txtDataId;
	}
	public void set_txtDataId(String _txtDataId) {
		this._txtDataId = _txtDataId;
	}
	public String get_txtNoPO() {
		return _txtNoPO;
	}
	public void set_txtNoPO(String _txtNoPO) {
		this._txtNoPO = _txtNoPO;
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
	public String get_txtBatchNo() {
		return _txtBatchNo;
	}
	public void set_txtBatchNo(String _txtBatchNo) {
		this._txtBatchNo = _txtBatchNo;
	}
	public String get_intStockAwal() {
		return _intStockAwal;
	}
	public void set_intStockAwal(String _intStockAwal) {
		this._intStockAwal = _intStockAwal;
	}
	public String get_dtED() {
		return _dtED;
	}
	public void set_dtED(String _dtED) {
		this._dtED = _dtED;
	}
	
}
