package bl;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import library.salesforce.common.APIData;
import library.salesforce.common.clsHelper;
import library.salesforce.common.linkAPI;
import library.salesforce.common.mconfigData;
import library.salesforce.common.tActivityData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.enumConfigData;
import library.salesforce.dal.mconfigDA;
import library.salesforce.dal.tActivityDA;
import library.salesforce.dal.tUserLoginDA;

public class tActivityBL extends clsMainBL{
	public void DownloadActivity(String versionName) throws Exception{
		//ambil linkapi Database sqllite
		SQLiteDatabase _db=getDb();
		tUserLoginDA _tUserLoginDA=new tUserLoginDA(_db);
		mconfigDA _mconfigDA =new mconfigDA(_db);
		
		String strVal2="";
		mconfigData dataAPI = _mconfigDA.getData(_db,enumConfigData.ApiKalbe.getidConfigData());
		strVal2 = dataAPI.get_txtValue();
		if (dataAPI.get_txtValue() == "") {
			strVal2 = dataAPI.get_txtDefaultValue();
		}
		//ambil version dari webservices
		tUserLoginData _dataUserLogin = _tUserLoginDA.getData(_db, 1);
		clsHelper _help =new clsHelper();
		linkAPI dtlinkAPI=new linkAPI();
		String txtMethod="GetDataTActivityMobile";
		dtlinkAPI.set_txtMethod(txtMethod);
		dtlinkAPI.set_txtParam("|||||1|"+_dataUserLogin.get_TxtEmpId());
		dtlinkAPI.set_txtToken(new clsHardCode().txtTokenAPI);
		dtlinkAPI.set_txtVesion(versionName);
		String strLinkAPI= dtlinkAPI.QueryString(strVal2);
		String JsonData= _help.ResultJsonData(_help.getHTML(strLinkAPI));
		org.json.simple.JSONArray JsonArray= _help.ResultJsonArray(JsonData);
		APIData dtAPIDATA=new APIData();
		//String aa=new clsHelper().linkAPI(db);
		Iterator i = JsonArray.iterator();
		Boolean flag=true;
		String ErrorMess="";
		tActivityDA _tActivityDA=new tActivityDA(_db);
		int iddata=_tActivityDA.getContactsCount(_db)+1;
		while (i.hasNext()) {
			org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
			int boolValid= Integer.valueOf(String.valueOf( innerObj.get(dtAPIDATA.boolValid)));
			if(boolValid == Integer.valueOf(new clsHardCode().intSuccess)){
				tActivityData _data =new tActivityData();
				_tActivityDA.deleteContact(_db, String.valueOf(innerObj.get("IntIdData")));
				_data.set_dtActivity((String) innerObj.get("DtActivity"));
				_data.set_intActive((String) innerObj.get("IntActive"));
				_data.set_intFlag((String) innerObj.get("TxtType"));
				_data.set_intActive("1");
				_data.set_txtOutletCode((String) innerObj.get("TxtOutletCode"));
				_data.set_intId(String.valueOf(iddata));
				_data.set_intIdSyn((String) innerObj.get("IntIdData"));
				_data.set_intSubmit("1");
				_data.set_txtBranch((String) innerObj.get("TxtCabId"));
				_data.set_txtDesc((String) innerObj.get("TxtDesc"));
				String txtLink="";
				if(innerObj.get("TxtImg1")=="" || innerObj.get("TxtImg1")==null){
					//_data.set_txtImg1(new clsHardCode().txtPhotoNotAvailabe);
				}else{
					txtLink=(String) innerObj.get("TxtLinkImg1");
					Bitmap _Bitmap= _help.downloadFile(txtLink);
					if(_Bitmap!=null){
						String root = new clsHardCode().txtFolderActivity;
						File myDir = new File(root+_data.get_intIdSyn()+(String) File.separator);
						myDir.mkdirs();
						String fileName = (String)innerObj.get("TxtImg1");
						File file = new File (myDir, fileName);
						if (file.exists ()) file.delete (); 
						try {
						       FileOutputStream out = new FileOutputStream(file);
						       _Bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
						       out.flush();
						       out.close();
						       //_data.set_txtImg1(myDir+File.separator+fileName);
						} catch (Exception e) {
						       e.printStackTrace();
						}
					}
				}
				if(innerObj.get("TxtImg2")=="" || innerObj.get("TxtImg2")==null){
					//_data.set_txtImg2(new clsHardCode().txtPhotoNotAvailabe);
				}else{
					txtLink=(String) innerObj.get("TxtLinkImg2");
					Bitmap _Bitmap= _help.downloadFile(txtLink);
					if(_Bitmap!=null){
						String root = new clsHardCode().txtFolderActivity;
						File myDir = new File(root+_data.get_intIdSyn()+(String) File.separator);
						myDir.mkdirs();
						String fileName = (String)innerObj.get("TxtImg2");
						File file = new File (myDir, fileName);
						if (file.exists ()) file.delete (); 
						try {
						       FileOutputStream out = new FileOutputStream(file);
						       _Bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
						       out.flush();
						       out.close();
						      // _data.set_txtImg2(myDir+File.separator+fileName);
						} catch (Exception e) {
						       e.printStackTrace();
						}
					}
				}
				_data.set_txtOutletName((String) innerObj.get("TxtOutletName"));
				_data.set_txtDeviceId((String) innerObj.get("TxtDeviceId"));
				_data.set_txtUserId((String) innerObj.get("TxtUserId"));
				iddata+=1;
				_tActivityDA.SaveDatatActivityData(_db, _data);
				//_mEmployeeBranchDA.SaveDataMConfig(db, _data);
			}else{
				flag=false;
				ErrorMess=(String) innerObj.get(dtAPIDATA.strMessage);
				break;
			}
		}
	}

	public int getCountActivity(){
		SQLiteDatabase _db=getDb();

		tActivityDA _tActivityDA = new tActivityDA(_db);
		List<tActivityData> dt = _tActivityDA.getAllData(_db);

		return dt.size();
	}

	public void saveData(List<tActivityData> Listdata){
		SQLiteDatabase db=getDb();
		tActivityDA _tActivityDA = new tActivityDA(db);
		for(tActivityData data:Listdata){
			_tActivityDA.SaveDatatActivityData(db, data);
		}
	}

	public List<tActivityData> getDataNew(){
		SQLiteDatabase db=getDb();
		tActivityDA _tActivityDA=new tActivityDA(db);
		List<tActivityData> listData=_tActivityDA.getAllDataNew(db);
		return listData;
	}

	public tActivityData getDataByBitActive(){
		SQLiteDatabase db=getDb();
		tActivityDA _tActivityDA=new tActivityDA(db);
		tActivityData listData=_tActivityDA.getAllDataByBitActive(db);
		return listData;
	}

	public List<tActivityData> getAllData(){
		SQLiteDatabase db=getDb();
		tActivityDA _tActivityDA=new tActivityDA(db);
		List<tActivityData> listData=_tActivityDA.getAllData(db);
		return listData;
	}

	public List<tActivityData> getAllDataByIntSyc(String val){
		SQLiteDatabase _db =getDb();
		tActivityDA _tActivityDA = new tActivityDA(_db);
		List<tActivityData> dt = _tActivityDA.getAllDataByIntSyc(_db,val);
		if(dt == null){
			dt = new ArrayList<>(0);
		}
		return dt ;
	}

}
