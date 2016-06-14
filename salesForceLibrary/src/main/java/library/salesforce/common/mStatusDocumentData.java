package library.salesforce.common;

public class mStatusDocumentData {
	private String _intStatus;
	private String _txtStatus;
	private String _bitActive;
	public String Property_intStatus="intStatus";
	public String Property_txtStatus="txtStatus";
	public String Property_bitActive="bitActive";
	public String PropertyALL=Property_bitActive+","+Property_intStatus+","+Property_txtStatus;
	public synchronized String get_intStatus() {
		return _intStatus;
	}
	public synchronized void set_intStatus(String _intStatus) {
		this._intStatus = _intStatus;
	}
	public synchronized String get_txtStatus() {
		return _txtStatus;
	}
	public synchronized void set_txtStatus(String _txtStatus) {
		this._txtStatus = _txtStatus;
	}
	public synchronized String get_bitActive() {
		return _bitActive;
	}
	public synchronized void set_bitActive(String _bitActive) {
		this._bitActive = _bitActive;
	}
	
}
