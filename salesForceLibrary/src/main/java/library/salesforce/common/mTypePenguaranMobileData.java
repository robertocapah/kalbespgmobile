package library.salesforce.common;

public class mTypePenguaranMobileData {
	public mTypePenguaranMobileData(){
		
	}
	private String  _intTypePenguaranMobile;
	private String  _txtNamaPenguaran;
	private String  _bitActive;
	
	public String  Property_intTypePenguaranMobile="intTypePenguaranMobile";
	public String  Property_txtNamaPenguaran="txtNamaPenguaran";
	public String  Property_bitActive="bitActive";
	public String  PropertyAll=Property_bitActive+","+Property_intTypePenguaranMobile+","+Property_txtNamaPenguaran;
	public synchronized String get_intTypePenguaranMobile() {
		return _intTypePenguaranMobile;
	}
	public synchronized void set_intTypePenguaranMobile(String _intTypePenguaranMobile) {
		this._intTypePenguaranMobile = _intTypePenguaranMobile;
	}
	public synchronized String get_txtNamaPenguaran() {
		return _txtNamaPenguaran;
	}
	public synchronized void set_txtNamaPenguaran(String _txtNamaPenguaran) {
		this._txtNamaPenguaran = _txtNamaPenguaran;
	}
	public synchronized String get_bitActive() {
		return _bitActive;
	}
	public synchronized void set_bitActive(String _bitActive) {
		this._bitActive = _bitActive;
	}
	
}
