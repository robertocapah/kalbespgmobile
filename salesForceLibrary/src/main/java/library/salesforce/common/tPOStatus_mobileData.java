package library.salesforce.common;

public class tPOStatus_mobileData {
	private String _txtDataId;
	private String _txtNoPO;
	private String _intStatus;
	private String _txtStatus;
	private String _intSubmit;
	private String _intSync;
	private String _intBitActive;
	public String get_intBitActive() {
		return _intBitActive;
	}
	public void set_intBitActive(String _intBitActive) {
		this._intBitActive = _intBitActive;
	}
	private String _txtNoDoc;

	public String get_txtNoDoc() {
		return _txtNoDoc;
	}
	public void set_txtNoDoc(String _txtNoDoc) {
		this._txtNoDoc = _txtNoDoc;
	}
	public String Property_txtDataId="txtDataId";
	public String Property_txtNoPO="txtNoPO";
	public String Property_txtNoDoc="txtNoDoc";
	public String Property_intStatus="intStatus";
	public String Property_txtStatus="txtStatus";
	public String Property_intSubmit="intSubmit";
	public String Property_intSync="intSync";
	public String Property_bitActive="bitActive";
	public String Property_All=Property_bitActive+","+Property_intStatus+","+Property_intSubmit+","+Property_intSync+","+Property_txtDataId
			+","+Property_txtNoDoc+","+Property_txtNoPO+","+Property_txtStatus;
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
	public String get_intStatus() {
		return _intStatus;
	}
	public void set_intStatus(String _intStatus) {
		this._intStatus = _intStatus;
	}
	public String get_txtStatus() {
		return _txtStatus;
	}
	public void set_txtStatus(String _txtStatus) {
		this._txtStatus = _txtStatus;
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
