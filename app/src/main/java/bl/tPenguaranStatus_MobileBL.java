package bl;

import android.database.sqlite.SQLiteDatabase;
import library.salesforce.common.tPenguaranStatus_MobileData;
import library.salesforce.dal.tPenguaranStatus_MobileDA;

public class tPenguaranStatus_MobileBL extends clsMainBL {
	
	public void  SaveData(tPenguaranStatus_MobileData dt){
		SQLiteDatabase db=getDb();
		tPenguaranStatus_MobileDA _tPenguaranStatus_MobileDA=new tPenguaranStatus_MobileDA(db);
		_tPenguaranStatus_MobileDA.SaveDatatSalesProductHeaderData(db, dt);
	}
	
}
