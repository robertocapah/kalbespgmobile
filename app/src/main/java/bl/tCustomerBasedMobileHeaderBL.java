package bl;

import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import library.salesforce.common.mCounterNumberData;
import library.salesforce.common.tCustomerBaseData;
import library.salesforce.common.tCustomerBasedMobileDetailData;
import library.salesforce.common.tCustomerBasedMobileDetailProductData;
import library.salesforce.common.tCustomerBasedMobileHeaderData;
import library.salesforce.dal.mCounterNumberDA;
import library.salesforce.dal.tCustomerBaseDA;
import library.salesforce.dal.tCustomerBasedMobileHeaderDA;

public class tCustomerBasedMobileHeaderBL extends clsMainBL{
	SQLiteDatabase db;

	public void saveData(tCustomerBasedMobileHeaderData dt){
		SQLiteDatabase _db=getDb();
		tCustomerBasedMobileHeaderDA _tCustomerBasedMobileHeaderDA = new tCustomerBasedMobileHeaderDA(_db);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();

//		mCounterNumberDA _mCountNumberDA = new mCounterNumberDA(db);
//		mCounterNumberData _ListOfmCounterNumberData = _mCountNumberDA.getData(db, 2);
		String dtDate = dateFormat.format(cal.getTime());
		String[] split = dtDate.split("-");
		String yy = split[0];
		String mm = split[1];
		String dd = split[2];

		String txtSubmissionCode = "LPUJKT";

		String txtSubmissionId = txtSubmissionCode + "." + dd + mm + yy.substring(2) + ".007";

		dt.set_txtSubmissionId(txtSubmissionId);
		dt.set_txtSubmissionCode(txtSubmissionCode);

		_tCustomerBasedMobileHeaderDA.SaveDatatCustomerBasedMobileHeaderData(_db, dt);
	}

	public tCustomerBasedMobileHeaderData getDataByBitActive() {
		SQLiteDatabase _db=getDb();
		tCustomerBasedMobileHeaderData dt = new tCustomerBasedMobileHeaderDA(_db).getDataByBitActive(_db);
		return dt;
	}

	public Boolean submit() {
		SQLiteDatabase _db=getDb();

		Boolean status = false;
		tCustomerBasedMobileHeaderData dtHeader = getDataByBitActive();
		if(dtHeader != null){
			List<tCustomerBasedMobileDetailData> dtDetail = new tCustomerBasedMobileDetailBL().getAllDataByHeaderId(dtHeader.get_intTrCustomerId());
			if(dtDetail != null){
				for(tCustomerBasedMobileDetailData dt: dtDetail){
					List<tCustomerBasedMobileDetailProductData> dtProduct = new tCustomerBasedMobileDetailProductBL().getDataByCustomerDetailId(dt.get_intTrCustomerIdDetail());
					if(dtProduct == null || dtProduct.size() == 0){
						status = false;
						break;
					}
					else{
						status = true;
					}
				}
			}
		}

		if(status){
			dtHeader.set_bitActive("0");
			dtHeader.set_intSubmit("1");
			new tCustomerBasedMobileHeaderDA(_db).SaveDatatCustomerBasedMobileHeaderData(_db, dtHeader);

			List<tCustomerBasedMobileDetailData> dtDetail = new tCustomerBasedMobileDetailBL().getAllDataByHeaderId(dtHeader.get_intTrCustomerId());

			for(tCustomerBasedMobileDetailData dt: dtDetail){
				dt.set_bitActive("0");
				new tCustomerBasedMobileDetailBL().saveData(dt);

				List<tCustomerBasedMobileDetailProductData> dtProduct = new tCustomerBasedMobileDetailProductBL().getDataByCustomerDetailId(dt.get_intTrCustomerIdDetail());

				for(tCustomerBasedMobileDetailProductData dtP : dtProduct){
					dtP.set_bitActive("0");
					new tCustomerBasedMobileDetailProductBL().saveData(dtP);
				}
			}
		}

        return status;
	}

	public List<tCustomerBasedMobileHeaderData> getAllData() {
		SQLiteDatabase _db=getDb();
		List<tCustomerBasedMobileHeaderData> dt = new tCustomerBasedMobileHeaderDA(_db).getAllData(_db);
		return dt;
	}
}
