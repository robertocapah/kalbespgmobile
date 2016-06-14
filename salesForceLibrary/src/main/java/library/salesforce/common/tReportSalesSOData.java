package library.salesforce.common;

import android.R.integer;

public class tReportSalesSOData {
	public synchronized tSalesProductHeaderData get_tSalesProductHeaderData() {
		return _tSalesProductHeaderData;
	}
	public synchronized void set_tSalesProductHeaderData(
			tSalesProductHeaderData _tSalesProductHeaderData) {
		this._tSalesProductHeaderData = _tSalesProductHeaderData;
	}
	public synchronized tSalesProductDetailData get_tSalesProductDetailData() {
		return _tSalesProductDetailData;
	}
	public synchronized void set_tSalesProductDetailData(
			tSalesProductDetailData _tSalesProductDetailData) {
		this._tSalesProductDetailData = _tSalesProductDetailData;
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
	
	private tSalesProductHeaderData _tSalesProductHeaderData;
	private tSalesProductDetailData _tSalesProductDetailData;
	private integer _TotalQty;
	private integer _TotalAmount;
	
	public String Property_tSalesProductHeaderData = "tSalesProductHeaderData";
	public String Property_tSalesProductDetailData ="tSalesProductDetailData";
	public String Property_TotalQty ="TotalQty";
	public String Property_TotalAmount ="TotalAmount";
	public String Property_ListOftReportSalesSO ="ListOftReportSalesSO";
}
