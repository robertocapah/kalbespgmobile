package library.salesforce.common;

public class mStockAwalData {
	private String _intdata;
	private String _txtProductCode;
	private String _txtProductName;
	private String _txtOutletCode;
	private String _txtOutletName;
	private String _txtBranchCode;
	private String _intQty;
	private String _txtStatus;
	private String _txtNoDoc;
	private String _intSubmit;
	private String _intSync;
	
	private String _bitActive;
	
	public String Property_intdata="intdata";
	public String Property_txtProductCode="txtProductCode";
	public String Property_txtProductName="txtProductName";
	public String Property_txtOutletCode="txtOutletCode";
	public String Property_txtOutletName="txtOutletName";
	public String Property_txtBranchCode="txtBranchCode";
	public String Property_intQty="intQty";
	public String Property_txtStatus="txtStatus";
	public String Property_txtNoDoc="txtNoDoc";
	public String Property_bitActive="bitActive";
	public String Property_intSubmit="intSubmit";
	public String Property_intSync="intSync";
	public String Property_ListOfmStockAwalData="ListOfmStockAwalData";
	public String Property_ALL=Property_intdata+","+Property_txtProductCode+","+Property_txtProductName+","+
							  Property_txtOutletCode+","+Property_txtOutletName+","+Property_txtBranchCode+","+
							  Property_intQty+","+Property_txtStatus+","+Property_txtNoDoc+","+Property_bitActive+
							  ","+Property_intSubmit+","+Property_intSync;
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
	public synchronized String get_intdata() {
		return _intdata;
	}
	public synchronized void set_intdata(String _intdata) {
		this._intdata = _intdata;
	}
	public synchronized String get_txtProductCode() {
		return _txtProductCode;
	}
	public synchronized void set_txtProductCode(String _txtProductCode) {
		this._txtProductCode = _txtProductCode;
	}
	public synchronized String get_txtProductName() {
		return _txtProductName;
	}
	public synchronized void set_txtProductName(String _txtProductName) {
		this._txtProductName = _txtProductName;
	}
	public synchronized String get_txtOutletCode() {
		return _txtOutletCode;
	}
	public synchronized void set_txtOutletCode(String _txtOutletCode) {
		this._txtOutletCode = _txtOutletCode;
	}
	public synchronized String get_txtOutletName() {
		return _txtOutletName;
	}
	public synchronized void set_txtOutletName(String _txtOutletName) {
		this._txtOutletName = _txtOutletName;
	}
	public synchronized String get_txtBranchCode() {
		return _txtBranchCode;
	}
	public synchronized void set_txtBranchCode(String _txtBranchCode) {
		this._txtBranchCode = _txtBranchCode;
	}
	public synchronized String get_intQty() {
		return _intQty;
	}
	public synchronized void set_intQty(String _intQty) {
		this._intQty = _intQty;
	}
	public synchronized String get_txtStatus() {
		return _txtStatus;
	}
	public synchronized void set_txtStatus(String _txtStatus) {
		this._txtStatus = _txtStatus;
	}
	public synchronized String get_txtNoDoc() {
		return _txtNoDoc;
	}
	public synchronized void set_txtNoDoc(String _txtNoDoc) {
		this._txtNoDoc = _txtNoDoc;
	}
	public synchronized String get_bitActive() {
		return _bitActive;
	}
	public synchronized void set_bitActive(String _bitActive) {
		this._bitActive = _bitActive;
	}
}
