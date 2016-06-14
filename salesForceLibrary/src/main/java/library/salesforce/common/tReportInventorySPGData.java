package library.salesforce.common;

import android.R.integer;

public class tReportInventorySPGData {
	public synchronized tInventorySPGHeader_mobileData get_tInventorySPGHeader_mobileData() {
		return _tInventorySPGHeader_mobileData;
	}
	public synchronized void set_tInventorySPGHeader_mobileData(
			tInventorySPGHeader_mobileData _tInventorySPGHeader_mobileData) {
		this._tInventorySPGHeader_mobileData = _tInventorySPGHeader_mobileData;
	}
	public synchronized tInventorySPGDetail_mobileData get_tInventorySPGDetail_mobileData() {
		return _tInventorySPGDetail_mobileData;
	}
	public synchronized void set_tInventorySPGDetail_mobileData(
			tInventorySPGDetail_mobileData _tInventorySPGDetail_mobileData) {
		this._tInventorySPGDetail_mobileData = _tInventorySPGDetail_mobileData;
	}
	public synchronized integer get_TotalQty() {
		return _TotalQty;
	}
	public synchronized void set_TotalQty(integer _TotalQty) {
		this._TotalQty = _TotalQty;
	}
	public synchronized integer get_TotalAmount() {
		return _TotalAmount;
	}
	public synchronized void set_TotalAmount(integer _TotalAmount) {
		this._TotalAmount = _TotalAmount;
	}
	
	private tInventorySPGHeader_mobileData _tInventorySPGHeader_mobileData;
	private tInventorySPGDetail_mobileData _tInventorySPGDetail_mobileData;
	private integer _TotalQty;
	private integer _TotalAmount;
	
	public String Property_tInventorySPGHeader_mobileData = "tInventorySPGHeader_mobileData";
	public String Property_tInventorySPGDetail_mobileData ="tInventorySPGDetail_mobileData";
	public String Property_TotalQty ="TotalQty";
	public String Property_TotalAmount ="TotalAmount";
	public String Property_ListOftReportInventorySPGData ="tReportInventorySPGData";
}
