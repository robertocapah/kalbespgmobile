package bl;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import library.salesforce.common.tCustomerBaseData;
import library.salesforce.common.tCustomerBaseDetailData;
import library.salesforce.dal.tCustomerBaseDA;
import library.salesforce.dal.tCustomerBaseDetailDA;

/**
 * Created by ASUS on 30/06/2016.
 */
public class tCustomerBaseDetailBL extends clsMainBL{

    SQLiteDatabase db;

    public void saveData(tCustomerBaseData dt){
        SQLiteDatabase _db=getDb();
        tCustomerBaseDA _tCustomerBaseDA = new tCustomerBaseDA(_db);
        _tCustomerBaseDA.SaveDatatCustomerBaseData(_db, dt);
    }

    public List<tCustomerBaseDetailData> getData(String id){
        SQLiteDatabase _db=getDb();
        tCustomerBaseDetailDA _tCustomerBaseDetailDA = new tCustomerBaseDetailDA(_db);
        return _tCustomerBaseDetailDA.getDataByCustomerId(_db, id);
    }
}
