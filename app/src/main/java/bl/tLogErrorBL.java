package bl;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import library.salesforce.common.tLogErrorData;
import library.salesforce.dal.tLogErrorDA;

/**
 * Created by Robert on 06/04/2017.
 */

public class tLogErrorBL extends clsMainBL{
    SQLiteDatabase db;
    public void saveData(tLogErrorData dt){
       SQLiteDatabase _db = getDb();
        tLogErrorDA _tLogErrorDA = new tLogErrorDA(_db);
        _tLogErrorDA.SaveLogData(_db, dt);
    }

    public List<tLogErrorData> getAllData(){
        SQLiteDatabase _db = getDb();
        List<tLogErrorData> dt = new tLogErrorDA(_db).getAllData(_db);
        return dt;
    }

    public void deleteData(tLogErrorData dt){
        SQLiteDatabase _db=getDb();
        new tLogErrorDA(_db).DeleteAllData(_db);
    }
}
