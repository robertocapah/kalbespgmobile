package library.salesforce.common;

public class mReasonData {
	private String _intData;
	private String _txtType;
	private String _txtReason;
	private String _bitActive;
	
	public String Property_intdata="intdata";
	public String Property_txtType="txtType";
	public String Property_txtReason="txtReason";
	public String Property_bitActive="bitActive";
	public String Property_ListOfmReasonData="ListOfmReasonData";
	public String Property_ALL=Property_intdata+","+Property_txtType+","+Property_txtReason+","+Property_bitActive;
	
	public synchronized String get_intData() {
		return _intData;
	}
	public synchronized void set_intData(String _intData) {
		this._intData = _intData;
	}
	public synchronized String get_txtType() {
		return _txtType;
	}
	public synchronized void set_txtType(String _txtType) {
		this._txtType = _txtType;
	}
	public synchronized String get_txtReason() {
		return _txtReason;
	}
	public synchronized void set_txtReason(String _txtReason) {
		this._txtReason = _txtReason;
	}
	public synchronized String get_bitActive() {
		return _bitActive;
	}
	public synchronized void set_bitActive(String _bitActive) {
		this._bitActive = _bitActive;
	}
}
