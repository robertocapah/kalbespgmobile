package library.salesforce.common;

public class tCustomerBaseDetailData {
	public synchronized String get_txtDataId() {
		return _txtDataId;
	}
	public synchronized void set_txtDataId(String _txtDataId) {
		this._txtDataId = _txtDataId;
	}
	public synchronized String get_intCustomerId() {
		return _intCustomerId;
	}
	public synchronized void set_intCustomerId(String _intCustomerId) {
		this._intCustomerId = _intCustomerId;
	}
	public synchronized String get_txtProductBrandCode() {
		return _txtProductBrandCode;
	}
	public synchronized void set_txtProductBrandCode(String _txtProductBrandCode) {
		this._txtProductBrandCode = _txtProductBrandCode;
	}
	public synchronized String get_txtProductBrandName() {
		return _txtProductBrandName;
	}
	public synchronized void set_txtProductBrandName(String _txtProductBrandName) {
		this._txtProductBrandName = _txtProductBrandName;
	}
	public synchronized String get_intQty() {
		return _intQty;
	}
	public synchronized void set_intQty(String _intQty) {
		this._intQty = _intQty;
	}
	private String _txtDataId;
	private String _intCustomerId;
	private String _txtProductBrandCode;
	private String _txtProductBrandName;
	private String _intQty;
	
	public String property_txtDataId="txtDataId";
	public String Property_intCustomerId="intCustomerId";
	public String Property_txtProductBrandCode="txtProductBrandCode";
	public String Property_txtProductBrandName="txtProductBrandName";
	public String Property_intQty="intQty";
	public String PropertyAll=property_txtDataId+","+Property_intCustomerId+","+Property_intQty+","+
	Property_txtProductBrandCode+","+Property_txtProductBrandName;
	public String PropertyListOftCustomerBaseDetailData="ListOftCustomerBaseDetailData";
}
