package bl;

import android.database.sqlite.SQLiteDatabase;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import library.salesforce.common.APIData;
import library.salesforce.common.clsHelper;
import library.salesforce.common.linkAPI;
import library.salesforce.common.mProductBrandHeaderData;
import library.salesforce.common.mconfigData;
import library.salesforce.common.tSalesProductHeaderData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.enumConfigData;
import library.salesforce.dal.mProductBrandHeaderDA;
import library.salesforce.dal.mconfigDA;
import library.salesforce.dal.tSalesProductHeaderDA;
import library.salesforce.dal.tUserLoginDA;

/**
 * Created by ASUS ZE on 14/07/2016.
 */
public class tSalesProductHeaderBL extends clsMainBL {
    SQLiteDatabase db=getDb();

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

    public List<tSalesProductHeaderData> getAllDataByIntSyc(String val){
        SQLiteDatabase _db =getDb();
        tSalesProductHeaderDA _tSalesProductHeaderDA = new tSalesProductHeaderDA(_db);
        List<tSalesProductHeaderData> dt = _tSalesProductHeaderDA.getAllDataByIntSyc(_db,val);
        if(dt == null){
            dt = new ArrayList<>(0);
        }
        return dt ;
    }

    public JSONArray DownloadReso(String versionName) throws Exception{
        SQLiteDatabase _db=getDb();
        tUserLoginDA _tUserLoginDA=new tUserLoginDA(_db);
        mconfigDA _mconfigDA =new mconfigDA(_db);

        String strVal2="";
        mconfigData dataAPI = _mconfigDA.getData(db,enumConfigData.ApiKalbe.getidConfigData());
        strVal2 = dataAPI.get_txtValue();
        if (dataAPI.get_txtValue() == "") {
            strVal2 = dataAPI.get_txtDefaultValue();
        }
        //ambil version dari webservices
        tUserLoginData _dataUserLogin = _tUserLoginDA.getData(db, 1);
        clsHelper _help =new clsHelper();
        linkAPI dtlinkAPI=new linkAPI();
        String txtMethod="GetDataTransactionReso";
        JSONObject resJson = new JSONObject();
        dtlinkAPI.set_txtMethod(txtMethod);
        dtlinkAPI.set_txtParam("|" + _dataUserLogin.get_TxtEmpId() + "|");
        dtlinkAPI.set_txtToken(new clsHardCode().txtTokenAPI);
        dtlinkAPI.set_txtVesion(versionName);

        String strLinkAPI= dtlinkAPI.QueryString(strVal2);
        String JsonData= _help.pushtData(strLinkAPI, dtlinkAPI.get_txtParam(), Integer.valueOf(getBackGroundServiceOnline()));

        //String strLinkAPI= dtlinkAPI.QueryString(strVal2);
        //String JsonData= _help.ResultJsonData(_help.getHTML(strLinkAPI));

        org.json.simple.JSONArray JsonArray= _help.ResultJsonArray(JsonData);
        _db.close();
        return JsonArray;
    }
}
