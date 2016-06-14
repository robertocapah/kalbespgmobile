package library.salesforce.common;

import java.util.List;

public class tCustomerBaseInfoData {
	
	public synchronized List<tCustomerBaseData> get_ListdttCustomerBaseData() {
		return _ListdttCustomerBaseData;
	}
	public synchronized void set_ListdttCustomerBaseData(
			List<tCustomerBaseData> _ListdttCustomerBaseData) {
		this._ListdttCustomerBaseData = _ListdttCustomerBaseData;
	}
	public synchronized List<tCustomerBaseDetailData> get_ListdttCustomerBaseDetailData() {
		return _ListdttCustomerBaseDetailData;
	}
	public synchronized void set_ListdttCustomerBaseDetailData(
			List<tCustomerBaseDetailData> _ListdttCustomerBaseDetailData) {
		this._ListdttCustomerBaseDetailData = _ListdttCustomerBaseDetailData;
	}
	private List<tCustomerBaseData> _ListdttCustomerBaseData;
	private List<tCustomerBaseDetailData> _ListdttCustomerBaseDetailData;
	public String Property_tCustomerBaseData ="tCustomerBaseData";
	public String Property_tCustomerBaseDetailData ="tCustomerBaseDetailData";
	public String PropertyListOftCustomerBaseInfoData ="ListOftCustomerBaseInfoData";
}
