package service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONException;
import org.json.simple.JSONArray;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bl.clsHelperBL;
import bl.clsMainBL;
import library.salesforce.common.clsPushData;
import library.salesforce.common.dataJson;
import library.salesforce.common.mCounterNumberData;
import library.salesforce.common.mItemSalesPack_StockData;
import library.salesforce.common.mItemSalesPack_StockLogData;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tActivityData;
import library.salesforce.common.tGRNDetail_mobileData;
import library.salesforce.common.tGRNHeader_mobileData;
import library.salesforce.common.tPODetail_mobileData;
import library.salesforce.common.tPOHeader_mobileData;
import library.salesforce.common.tPOStatus_mobileData;
import library.salesforce.common.tSalesOrderDetail_MobileData;
import library.salesforce.common.tSalesOrderHeader_MobileData;
import library.salesforce.common.tTransactionDetailData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.enumCounterData;
import library.salesforce.dal.mCounterNumberDA;
import library.salesforce.dal.mItemSalesPack_StockDA;
import library.salesforce.dal.mItemSalesPack_StockLogDA;
import library.salesforce.dal.tAbsenUserDA;
import library.salesforce.dal.tActivityDA;
import library.salesforce.dal.tGRNDetail_mobileDA;
import library.salesforce.dal.tGRNHeader_mobileDA;
import library.salesforce.dal.tPODetail_mobileDA;
import library.salesforce.dal.tPOHeader_mobileDA;
import library.salesforce.dal.tPOStatus_mobileDA;
import library.salesforce.dal.tPenguaranDetail_MobileDA;
import library.salesforce.dal.tPenguaranHeader_MobileDA;
import library.salesforce.dal.tPenguaranStatus_MobileDA;
import library.salesforce.dal.tSalesOrderDetail_MobileDA;
import library.salesforce.dal.tSalesOrderHeader_MobileDA;
import library.salesforce.dal.tTransactionDetailDA;
import library.salesforce.dal.tUserLoginDA;

public class MyServiceNative extends Service{
	public MyServiceNative() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	
	@Override
    public void onCreate() {
        //Toast.makeText(this, "Welcome Kalbe SPG Mobile", Toast.LENGTH_LONG).show();
       
    }
	
	//private static long UPDATE_INTERVAL = 1*36*1000;  //default
	private static long UPDATE_INTERVAL = 1*360*1000;;  //default
	//private static long UPDATE_INTERVAL_DELAY = 180000;  //default
	private static long UPDATE_INTERVAL_TESTING = 6000;  //default
    private static Timer timer = new Timer(); 
	private void _startService()
    {      
		long intInverval=0;
		if(new clsMainBL().getLIVE().equals("1")){
			intInverval=UPDATE_INTERVAL;
		}else{
			intInverval=UPDATE_INTERVAL_TESTING;
		}
			
		if(timer != null)
	    {
			timer.cancel();
	    }
		timer = new Timer();
        timer.scheduleAtFixedRate(    
                new TimerTask() {
                    public void run() {
                    	try {
							doServiceWork();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
                }, 30000,intInverval);
        //Log.i(getClass().getSimpleName(), "FileScannerService Timer started....");
    }
	
    private void doServiceWork() throws JSONException
    {
    	clsPushData dtJson= new clsHelperBL().pushData();
    	if(dtJson==null){
    		_shutdownService();
    	}else{
    		String versionName="";
    		try {
    			versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
    		} catch (NameNotFoundException e2) {
    			// TODO Auto-generated catch block
    			e2.printStackTrace();
    		}
    		try {
    			JSONArray JsonArrayResult=new clsHelperBL().callPushDataReturnJson(versionName,dtJson.getDtdataJson().txtJSON().toString(),dtJson.getFileUpload());
				new clsHelperBL().saveDataPush(dtJson.getDtdataJson(),JsonArrayResult);
				Intent serviceIntent = new Intent(this,MyNotificationService.class);
				serviceIntent.putExtra("From", "PUSHDATA");
				startService(serviceIntent);
		    
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	SQLiteDatabase db;
    	String versionName="";
    	try {
			versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		clsHardCode clsdthc = new clsHardCode();
		db = SQLiteDatabase.openOrCreateDatabase(clsdthc.txtDatabaseName,null);
		tUserLoginDA _tUserLoginDA=new tUserLoginDA(db);
		if(_tUserLoginDA.getContactsCount(db)> 0){
			tUserLoginData _tUserLoginData=_tUserLoginDA.getData(db, 1);

			try {
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    		Calendar cal = Calendar.getInstance();
				mCounterNumberDA _mCounterNumberDA=new mCounterNumberDA(db);
				mCounterNumberData _data =new mCounterNumberData();
				_data.set_intId(enumCounterData.MonitorSchedule.getidCounterData());
				_data.set_txtDeskripsi("value menunjukan waktu terakhir menjalankan services");
				_data.set_txtName("Monitor Service");
				_data.set_txtValue(dateFormat.format(cal.getTime()));
				_mCounterNumberDA.SaveDataMConfig(db, _data);

				//new clsInit().PushData(db,versionName);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			tAbsenUserDA _tAbsenUserDA =new tAbsenUserDA (db);
			tActivityDA _tActivityDA =new tActivityDA (db);
			tGRNDetail_mobileDA _tGRNDetail_mobileDA =new tGRNDetail_mobileDA (db);
			tGRNHeader_mobileDA _tGRNHeader_mobileDA =new tGRNHeader_mobileDA (db);
			tPenguaranHeader_MobileDA _tPenguaranHeader_MobileDA =new tPenguaranHeader_MobileDA (db);
			tPenguaranDetail_MobileDA _tPenguaranDetail_MobileDA =new tPenguaranDetail_MobileDA (db);
			tPenguaranStatus_MobileDA _tPenguaranStatus_MobileDA =new tPenguaranStatus_MobileDA (db);
			tPOHeader_mobileDA _tPOHeader_mobileDA =new tPOHeader_mobileDA (db);
			tPODetail_mobileDA _tPODetail_mobileDA =new tPODetail_mobileDA (db);
			tPOStatus_mobileDA _tPOStatus_mobileDA =new tPOStatus_mobileDA (db);
			tSalesOrderDetail_MobileDA _tSalesOrderDetail_MobileDA =new tSalesOrderDetail_MobileDA (db);
			tSalesOrderHeader_MobileDA _tSalesOrderHeader_MobileDA =new tSalesOrderHeader_MobileDA(db);
			tTransactionDetailDA _tTransactionDetailDA =new tTransactionDetailDA(db);
			mItemSalesPack_StockDA _mItemSalesPack_StockDA =new mItemSalesPack_StockDA(db);
			mItemSalesPack_StockLogDA _mItemSalesPack_StockLogDA =new mItemSalesPack_StockLogDA(db);

			List<tAbsenUserData> ListOftAbsenUserData=_tAbsenUserDA.getAllDataToPushData(db);
			List<tActivityData> ListOftActivityData=_tActivityDA.getAllDataToPushData(db);
			List<tGRNHeader_mobileData> ListOftGRNHeader_mobileData=_tGRNHeader_mobileDA.getAllDataToPushData(db);
			List<tGRNDetail_mobileData> ListOftGRNDetail_mobileData=_tGRNDetail_mobileDA.getAllDataToPushData(db);
			List<tPOHeader_mobileData> ListOftPOHeader_mobileData=_tPOHeader_mobileDA.getAllDataToPushData(db);
			List<tPODetail_mobileData> ListOftPODetail_mobileData=_tPODetail_mobileDA.getAllDataToPushData(db);
			List<tPOStatus_mobileData> ListOftPOStatus_mobileData=_tPOStatus_mobileDA.getAllDataToPushData(db);
			//List<tPenguaranDetail_MobileData> ListOftPenguaranDetail_MobileData=_tPenguaranDetail_MobileDA.getAllDataToPushData(db);
			//List<tPenguaranHeader_MobileData> ListOftPenguaranHeader_MobileData = _tPenguaranHeader_MobileDA.getAllDataToPushData(db);
			//List<tPenguaranStatus_MobileData> ListOftPenguaranStatus_MobileData = _tPenguaranStatus_MobileDA.getAllDataToPushData(db);
			List<tSalesOrderDetail_MobileData> ListOftSalesOrderDetail_MobileData = _tSalesOrderDetail_MobileDA.getAllDataToPushData(db);
			List<tSalesOrderHeader_MobileData> ListOftSalesOrderHeader_MobileData = _tSalesOrderHeader_MobileDA.getAllDataToPushData(db);
			List<tTransactionDetailData> ListOftTransactionDetailData = _tTransactionDetailDA.getAllDataToPushData(db);
			List<mItemSalesPack_StockData> ListOfmItemSalesPack_StockData = _mItemSalesPack_StockDA.getAllDataToPushData(db);
			List<mItemSalesPack_StockLogData> ListOfmItemSalesPack_StockLogData = _mItemSalesPack_StockLogDA.getAllDataToPushData(db);
			dataJson dtPush=new dataJson();
			HashMap<String, String> FileUpload=null;
			FileUpload=new HashMap<String, String>();
			if(ListOftAbsenUserData!= null){
				dtPush.setListOftAbsenUserData(ListOftAbsenUserData);
				for (tAbsenUserData dttAbsenUserData : ListOftAbsenUserData) {
					if(dttAbsenUserData.get_txtImg1()!=null){
						FileUpload.put("FUAbsen1"+dttAbsenUserData.get_intId(), dttAbsenUserData.get_txtImg1().toString());
					}
					if(dttAbsenUserData.get_txtImg2()!=null){
						FileUpload.put("FUAbsen2"+dttAbsenUserData.get_intId(), dttAbsenUserData.get_txtImg2().toString());
					}
				}
			}
			if(ListOftActivityData!=null){
				dtPush.setListOftActivityData(ListOftActivityData);
				for (tActivityData dttActivityData : ListOftActivityData) {
					if(dttActivityData.get_txtImg1()!=null){
						FileUpload.put("FUActivity1"+dttActivityData.get_intId(), dttActivityData.get_txtImg1().toString());
					}
					if(dttActivityData.get_txtImg2()!=null){
						FileUpload.put("FUActivity2"+dttActivityData.get_intId(), dttActivityData.get_txtImg2().toString());
					}
				}
			}
			if(ListOftGRNHeader_mobileData!=null){
				dtPush.set_ListOftGRNHeader_mobileData(ListOftGRNHeader_mobileData);
			}
			if(ListOftGRNDetail_mobileData!=null){
				dtPush.set_ListOftGRNDetail_mobileData(ListOftGRNDetail_mobileData);
			}
			if(ListOftSalesOrderDetail_MobileData!=null){
				dtPush.set_ListOftSalesOrderDetail_MobileData(ListOftSalesOrderDetail_MobileData);
			}
			if(ListOftSalesOrderHeader_MobileData!=null){
				dtPush.set_ListOftSalesOrderHeader_MobileData(ListOftSalesOrderHeader_MobileData);
			}
			if(ListOftPOHeader_mobileData!=null){
				dtPush.set_ListOftPOHeader_mobileData(ListOftPOHeader_mobileData);
			}
			if(ListOftPODetail_mobileData!=null){
				dtPush.set_ListOftPODetail_mobileData(ListOftPODetail_mobileData);
			}
			if(ListOftPOStatus_mobileData!=null){
				dtPush.set_ListOftPOStatus_mobileData(ListOftPOStatus_mobileData);
			}
			if(ListOftTransactionDetailData!=null){
				dtPush.set_ListOftTransactionDetailData(ListOftTransactionDetailData);
			}
			if(ListOfmItemSalesPack_StockData!=null){
				dtPush.set_ListOfmItemSalesPack_StockData(ListOfmItemSalesPack_StockData);
			}
			if(ListOfmItemSalesPack_StockLogData!=null){
				dtPush.set_ListOfmItemSalesPack_StockLogData(ListOfmItemSalesPack_StockLogData);
			}
		}
		else{
			_shutdownService();
		}

		db.close();
    }

    private void _shutdownService()
    {
        if (timer != null) timer.cancel();
        Log.i(getClass().getSimpleName(), "Timer stopped...");
    }
 
    @Override
    public void onStart(Intent intent, int startId) {
    	// For time consuming an long tasks you can launch a new thread here...
        //Toast.makeText(this, "Welcome Kalbe SPG Mobile", Toast.LENGTH_LONG).show();
    	_startService();
   
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
    	//Toast.makeText(this, " onStartCommand", Toast.LENGTH_LONG).show();
    	_startService();
        return START_STICKY;
    }
 
    @Override
    public void onDestroy() {
        //Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        _shutdownService();
    }
}
