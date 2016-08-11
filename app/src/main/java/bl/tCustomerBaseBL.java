package bl;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public List<tCustomerBaseData> getAllCustomerBase(){
        SQLiteDatabase _db=getDb();

        tCustomerBaseDA _tCustomerBaseDA = new tCustomerBaseDA(_db);
        List<tCustomerBaseData> dt = _tCustomerBaseDA.getAllData(_db);

        return dt;
    }

    public List<tCustomerBaseData> getAllDataByIntSyc(String val){
        SQLiteDatabase _db =getDb();
        tCustomerBaseDA _tCustomerBaseDA = new tCustomerBaseDA(_db);
        List<tCustomerBaseData> dt = _tCustomerBaseDA.getAllDataByIntSyc(_db,val);
        if(dt == null){
            dt = new ArrayList<>(0);
        }
        return dt ;
    }
}
