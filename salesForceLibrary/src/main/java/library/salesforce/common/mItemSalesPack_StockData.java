package library.salesforce.common;

public class mItemSalesPack_StockData{
	private String _txtDataId;
	private String _txtPeriode;
	private String _intWeek;
	private String _intProductCode;
	private String _intSaldoAwal;
	private String _intQtyIn;
	private String _intQtyOut;
	private String _intQtyAdj;
	private String _intQtyReserved;
	
	private String _intQtyAvailable;
	private String _txtOutletCode;
	private String _txtOutletName;
	private String _txtBranchCode;
	private String _txtNoTransaction;
	private String _intSubmit;
	private String _intSync;	
	
	public String Property_txtDataId="txtDataId";
	public String Property_txtPeriode="txtPeriode";
	public String Property_intWeek="intWeek";
	public String Property_intProductCode="intProductCode";
	public String Property_intSaldoAwal="intSaldoAwal";
	public String Property_intQtyIn="intQtyIn";
	public String Property_intQtyReserved="intQtyReserved";
	public String Property_intQtyOut="intQtyOut";
	public String Property_intQtyAdj="intQtyAdj";
	public String Property_intQtyAvailable="intQtyAvailable";
	public String Property_txtOutletCode="txtOutletCode";
	public String Property_txtOutletName="txtOutletName";
	public String Property_txtBranchCode="txtBranchCode";
	public String Property_txtNoTransaction="txtNoTransaction";
	public String Property_intSubmit="intSubmit";
	public String Property_intSync="intSync";
	public String Property_ListOfmItemSalesPack_StockData="ListOfmItemSalesPack_StockData";
	public String PropertyAll=Property_intProductCode+","+Property_intQtyAdj+","+Property_intQtyAvailable+","+Property_intQtyIn+","+Property_intQtyOut+","+Property_intQtyReserved+
			","+Property_intSaldoAwal+","+Property_intSubmit+","+Property_intSync+","+Property_intWeek+","+Property_txtBranchCode+","+Property_txtDataId+","+Property_txtNoTransaction+
			","+Property_txtOutletCode+","+Property_txtOutletName+","+Property_txtPeriode;
	
	public String get_intQtyReserved() {
		return _intQtyReserved;
	}
	public void set_intQtyReserved(String _intQtyReserved) {
		this._intQtyReserved = _intQtyReserved;
	}
	public String get_txtDataId() {
		return _txtDataId;
	}
	public void set_txtDataId(String _txtDataId) {
		this._txtDataId = _txtDataId;
	}
	public String get_txtPeriode() {
		return _txtPeriode;
	}
	public void set_txtPeriode(String _txtPeriode) {
		this._txtPeriode = _txtPeriode;
	}
	public String get_intWeek() {
		return _intWeek;
	}
	public void set_intWeek(String _intWeek) {
		this._intWeek = _intWeek;
	}
	public String get_intProductCode() {
		return _intProductCode;
	}
	public void set_intProductCode(String _intProductCode) {
		this._intProductCode = _intProductCode;
	}
	public String get_intSaldoAwal() {
		return _intSaldoAwal;
	}
	public void set_intSaldoAwal(String _intSaldoAwal) {
		this._intSaldoAwal = _intSaldoAwal;
	}
	public String get_intQtyIn() {
		return _intQtyIn;
	}
	public void set_intQtyIn(String _intQtyIn) {
		this._intQtyIn = _intQtyIn;
	}
	public String get_intQtyOut() {
		return _intQtyOut;
	}
	public void set_intQtyOut(String _intQtyOut) {
		this._intQtyOut = _intQtyOut;
	}
	public String get_intQtyAdj() {
		return _intQtyAdj;
	}
	public void set_intQtyAdj(String _intQtyAdj) {
		this._intQtyAdj = _intQtyAdj;
	}
	public String get_intQtyAvailable() {
		int intOut=0;
		int intIn=0;
		int intAdj=0;
		
		if(get_intQtyOut().equals("")==true){
			intOut=0;
		}else{
			intOut=Integer.valueOf(get_intQtyOut());
		}
		
		if(get_intQtyIn().equals("")==true){
			intIn=0;
		}else{
			intIn=Integer.valueOf(get_intQtyIn());	
		}
		
		if(get_intQtyAdj().equals("")==true){
			intAdj=0;
		}else{
			intAdj=Integer.valueOf(get_intQtyAdj());
		}
			
		return String.valueOf(intIn- intOut+intAdj);
	}

	public String get_txtOutletCode() {
		return _txtOutletCode;
	}
	public void set_txtOutletCode(String _txtOutletCode) {
		this._txtOutletCode = _txtOutletCode;
	}
	public String get_txtOutletName() {
		return _txtOutletName;
	}
	public void set_txtOutletName(String _txtOutletName) {
		this._txtOutletName = _txtOutletName;
	}
	public String get_txtBranchCode() {
		return _txtBranchCode;
	}
	public void set_txtBranchCode(String _txtBranchCode) {
		this._txtBranchCode = _txtBranchCode;
	}
	public String get_txtNoTransaction() {
		return _txtNoTransaction;
	}
	public void set_txtNoTransaction(String _txtNoTransaction) {
		this._txtNoTransaction = _txtNoTransaction;
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
