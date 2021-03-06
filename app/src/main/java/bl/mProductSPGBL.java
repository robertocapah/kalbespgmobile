package bl;

import android.database.sqlite.SQLiteDatabase;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.List;

import library.salesforce.common.APIData;
import library.salesforce.common.clsHelper;
import library.salesforce.common.linkAPI;
import library.salesforce.common.mProductSPGData;
import library.salesforce.common.tDeviceInfoUserData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.mProductSPGDA;
import library.salesforce.dal.mconfigDA;
import library.salesforce.dal.tDeviceInfoUserDA;

/**
 * Created by ASUS ZE on 24/03/2017.
 */

public class mProductSPGBL extends clsMainBL{
    public void deleteAllData() {
        SQLiteDatabase db = getDb();
        mProductSPGDA _mProductSPGDA=new mProductSPGDA(db);
        _mProductSPGDA.DeleteAllDataMConfig(db);
    }
    public void saveData(List<mProductSPGData> Listdata) {
        SQLiteDatabase db = getDb();
        mProductSPGDA _mProductSPGDA=new mProductSPGDA(db);
        for (mProductSPGData data : Listdata) {
            _mProductSPGDA.SaveDataMConfig(db, data);
        }
    }

    public List<mProductSPGData> GetAllData(){
        SQLiteDatabase db=getDb();
        mProductSPGDA _mProductSPGDA=new mProductSPGDA(db);
        List<mProductSPGData>ListData=_mProductSPGDA.getAllData(db);
        db.close();
        return ListData;
    }
    public List<mProductSPGData> GetDataByMasterId(String masterId){
        SQLiteDatabase db=getDb();
        mProductSPGDA _mProductSPGDA=new mProductSPGDA(db);
        List<mProductSPGData>ListData=_mProductSPGDA.getDataByMasterId(db,masterId);
        db.close();
        return ListData;
    }

    public int getContactCount(){
        int count = 0;
        mProductSPGDA _mProductSPGDA=new mProductSPGDA(db);
        count = _mProductSPGDA.getContactsCount(db);
        return count;
    }

    public JSONArray DownloadProductSPG(String versionApp, String txtUserId, String txtEmpId) throws ParseException {
        SQLiteDatabase db=getDb();
        JSONArray res=new JSONArray();
        mconfigDA _mconfigDA=new mconfigDA(db);
        tDeviceInfoUserData dt= new tDeviceInfoUserDA(db).getData(db, 1);
        String txtDomain= _mconfigDA.getDomainKalbeData(db);
        //String txtParam= txtDomain+"|"+txtUserName+"|"+txtPass+"||"+dt.get_txtVersion()+"|"+dt.get_txtDevice()+"|"+dt.get_txtModel()+"|"+intRoleId;
        JSONObject resJson = new JSONObject();
        resJson.put("TxtNIK", txtEmpId);
        linkAPI dtlinkAPI=new linkAPI();
        dtlinkAPI.set_txtMethod("GetDatavw_SalesInsentive_EmployeeSalesProductSPG");
        dtlinkAPI.set_txtParam("");
        dtlinkAPI.set_txtToken(new clsHardCode().txtTokenAPI);
        dtlinkAPI.set_txtVesion(versionApp);
        String strLinkAPI= dtlinkAPI.QueryString(getLinkAPI());
        APIData dtAPIDATA=new APIData();
        clsHelper _clsHelper=new clsHelper();
        String JsonData= _clsHelper.pushtData(strLinkAPI, String.valueOf(resJson), Integer.valueOf(getBackGroundServiceOnline()));
        res= _clsHelper.ResultJsonArray(JsonData);
        //String txtParam=
        return res;
    }
}
