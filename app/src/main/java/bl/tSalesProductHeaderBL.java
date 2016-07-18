package bl;

import android.database.sqlite.SQLiteDatabase;

import library.salesforce.common.tSalesProductHeaderData;
import library.salesforce.dal.tSalesProductHeaderDA;

/**
 * Created by ASUS ZE on 14/07/2016.
 */
public class tSalesProductHeaderBL extends clsMainBL {
    SQLiteDatabase db;

    public void SaveData(tSalesProductHeaderData dt){
        SQLiteDatabase _db =getDb();
        tSalesProductHeaderDA _tSalesProductDA = new tSalesProductHeaderDA(_db);
        _tSalesProductDA.SaveDatatSalesProductHeaderData(_db, dt);
    }
}
