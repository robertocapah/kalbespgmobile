package library.salesforce.common;

public class tUserLOBData {
	public synchronized String get_txtNik() {
		return _txtNik;
	}
	public synchronized void set_txtNik(String _txtNik) {
		this._txtNik = _txtNik;
	}
	public synchronized String get_intLOB() {
		return _intLOB;
	}
	public synchronized void set_intLOB(String _intLOB) {
		this._intLOB = _intLOB;
	}
	public synchronized String get_txtLOB() {
		return _txtLOB;
	}
	public synchronized void set_txtLOB(String _txtLOB) {
		this._txtLOB = _txtLOB;
	}
	private String _txtNik;
	private String _intLOB;
	private String _txtLOB;
	
	public String Property_txtNik="txtNik";
	public String Property_intLOB="intLOB";
	public String Property_txtLOB="txtLOB";
	public String Property_All=Property_intLOB+","+Property_txtLOB+","+Property_txtNik;
	public String Property_ListOftUserLOBData="ListOftUserLOBData";
}
