package library.salesforce.common;

public class tPenguaranStatus_MobileData {
	private String _txtDataId;
	private String _txtNoPenguaran;
	private String _intStatus;
	private String _txtStatus;
	private String _bitActive;
	private String _intSubmit;
	private String _intSync;
	
	public String Property_txtDataId="txtDataId";
	public String Property_txtNoPenguaran="txtNoPenguaran";
	public String Property_intStatus="intStatus";
	public String Property_txtStatus="txtStatus";
	public String Property_bitActive="bitActive";
	public String Property_intSubmit="intSubmit";
	public String Property_intSync="intSync";
	
	public tPenguaranStatus_MobileData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String Property_All= Property_txtDataId +","+
								Property_txtNoPenguaran +","+
								Property_intStatus +","+
								Property_txtStatus +","+
								Property_bitActive +","+
								Property_intSubmit +","+
								Property_intSync;
	
	public synchronized String get_txtDataId() {
		return _txtDataId;
	}

	public synchronized void set_txtDataId(String _txtDataId) {
		this._txtDataId = _txtDataId;
	}

	public synchronized String get_txtNoPenguaran() {
		return _txtNoPenguaran;
	}

	public synchronized void set_txtNoPenguaran(String _txtNoPenguaran) {
		this._txtNoPenguaran = _txtNoPenguaran;
	}

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

	public synchronized String get_intSubmit() {
		return _intSubmit;
	}

	public synchronized void set_intSubmit(String _intSubmit) {
		this._intSubmit = _intSubmit;
	}

	public synchronized String get_intSync() {
		return _intSync;
	}

	public synchronized void set_intSync(String _intSync) {
		this._intSync = _intSync;
	}

	
}