package bl;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

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
//    public List<tCustomerBaseData> getAllCustomerBase(){
//        SQLiteDatabase _db=getDb();
//
//        tCustomerBaseDA _tCustomerBaseDA = new tCustomerBaseDA(_db);
//        List<tCustomerBaseData> dt = _tCustomerBaseDA.getAllData(_db);
//
//        return dt;
//    }
    public List<tSalesProductHeaderData> getAllSalesProductHeader(){
        SQLiteDatabase _db =getDb();
        tSalesProductHeaderDA _tSalesProductHeaderDA = new tSalesProductHeaderDA(_db);
        List<tSalesProductHeaderData> dt = _tSalesProductHeaderDA.getAllData(_db);
        return dt ;
    }
    public List<tSalesProductHeaderData> getLastData(){
        SQLiteDatabase _db =getDb();
        tSalesProductHeaderDA _tSalesProductHeaderDA = new tSalesProductHeaderDA(_db);
        List<tSalesProductHeaderData> dt = _tSalesProductHeaderDA.getLastData(_db);
        return dt ;
    }
}
