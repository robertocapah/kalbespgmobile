package library.salesforce.common;

import java.io.Serializable;

public class tStockOpnameDetail_mobileData implements Serializable{
	private String _txtDataId;
	private String _txtNoAdj;
	private String _intProductCode;
	private String _txtProductName;
	private String _intQtyStock;
	private String _intQtyAdj;
	private String _intQty;
	private String _txtBatchNo;
	private String _dtED;
	private String _intSubmit;
	private String _intSync;
	
	public String Property_txtDataId="txtDataId";
	public String Property_txtNoAdj="txtNoAdj";
	public String Property_intProductCode="intProductCode";
	public String Property_txtProductName="txtProductName";
	public String Property_intQtyStock="intQtyStock";
	public String Property_intQtyAdj="intQtyAdj";
	public String Property_intQty="intQty";
	public String Property_txtBatchNo="txtBatchNo";
	public String Property_dtED="dtED";
	public String Property_intSubmit="intSubmit";
	public String Property_intSync="intSync";
	public String PropertyAll=Property_dtED+","+Property_intProductCode+","+Property_intQty+","+Property_intQtyAdj+","+
	Property_intQtyStock+","+Property_intSubmit+","+Property_intSync+","+Property_txtBatchNo+","+Property_txtDataId+","+
			Property_txtNoAdj+","+Property_txtProductName;
	
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
	public String get_txtNoAdj() {
		return _txtNoAdj;
	}
	public void set_txtNoAdj(String _txtNoAdj) {
		this._txtNoAdj = _txtNoAdj;
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
	public String get_intQtyStock() {
		return _intQtyStock;
	}
	public void set_intQtyStock(String _intQtyStock) {
		this._intQtyStock = _intQtyStock;
	}
	public String get_intQtyAdj() {
		return _intQtyAdj;
	}
	public void set_intQtyAdj(String _intQtyAdj) {
		this._intQtyAdj = _intQtyAdj;
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
}
