package bl;

import android.database.sqlite.SQLiteDatabase;

import library.salesforce.common.tCustomerBaseData;
import library.salesforce.dal.tCustomerBaseDA;

/**
 * Created by ASUS on 30/06/2016.
 */
public class tCustomerBaseBL extends clsMainBL{

    SQLiteDatabase db;

    public void saveData(tCustomerBaseData dt){
        SQLiteDatabase _db=getDb();
        tCustomerBaseDA _tCustomerBaseDA = new tCustomerBaseDA(_db);
        _tCustomerBaseDA.SaveDatatCustomerBaseData(_db, dt);
    }
}
