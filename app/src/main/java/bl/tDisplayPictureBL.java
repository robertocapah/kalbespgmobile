package bl;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import library.salesforce.common.tDisplayPictureData;
//import library.salesforce.common.tTestingSaveBlobData;
import library.salesforce.dal.tDisplayPictureDA;
//import library.salesforce.dal.tTestingSaveBlobDA;

/**
 * Created by ASUS ZE on 23/08/2016.
 */
public class tDisplayPictureBL extends clsMainBL{

    public void saveData(List<tDisplayPictureData> Listdata){
        SQLiteDatabase db=getDb();
        tDisplayPictureDA _tDisplayPictureDA = new tDisplayPictureDA(db);
        for(tDisplayPictureData data:Listdata){
            _tDisplayPictureDA.SaveData(db, data);
        }
    }

//    public void saveDatatTestingSaveBlob(List<tTestingSaveBlobData> Listdata){
//        SQLiteDatabase db=getDb();
//        tTestingSaveBlobDA _tTestingSaveBlobDA = new tTestingSaveBlobDA(db);
//        for(tTestingSaveBlobData data:Listdata){
//            _tTestingSaveBlobDA.SaveData(db, data);
//        }
//    }

    public tDisplayPictureData getData(){
        SQLiteDatabase db=getDb();
        tDisplayPictureDA _tDisplayPictureDA = new tDisplayPictureDA(db);
        tDisplayPictureData data=_tDisplayPictureDA.getData(db);
        return data;
    }

//    public tTestingSaveBlobData getDataTestingSaveBlob(){
//        SQLiteDatabase db=getDb();
//        tTestingSaveBlobDA _tTestingSaveBlobDA = new tTestingSaveBlobDA(db);
//        tTestingSaveBlobData data=_tTestingSaveBlobDA.getData(db);
//        return data;
//    }
}
