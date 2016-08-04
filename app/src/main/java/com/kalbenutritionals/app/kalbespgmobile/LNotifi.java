package com.kalbenutritionals.app.kalbespgmobile;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bl.tNotificationBL;
import edu.swu.pulltorefreshswipemenulistview.library.PullToRefreshSwipeMenuListView;
import edu.swu.pulltorefreshswipemenulistview.library.pulltorefresh.interfaces.IXListViewListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.SwipeMenuCreator;
import edu.swu.pulltorefreshswipemenulistview.library.util.RefreshTime;
import library.salesforce.common.AppAdapter;
import library.salesforce.common.clsRowItem;
import library.salesforce.common.clsSwipeList;
import library.salesforce.common.tNotificationData;

public class LNotifi extends clsMainActivity implements IXListViewListener{
	private String MenuID, BranchCode, OutletCode, intStockAwal;
	private PackageInfo pInfo = null;
	private List<String> arrData;
	ArrayAdapter<String> dataAdapter ;
	ListView listNotip;
	
	private static List<String> arrguiID, arrintIndex, tPublishStart, arrdtPublishEnd, arrbitActive, arrtxtTitle, arrtxtDescription, arrtxtImage, arrtxtLink, arrtxtOutlet, arrtxtOutletName, arrtxtBranchCode, arrtxtStatus, arrdtUpdate, arrintSubmit, arrintSync;
	private static List<clsSwipeList> swipeList = new ArrayList<clsSwipeList>();
	List<clsRowItem> rowItems;
	private static AppAdapter mAdapter;
	private static AppAdapterNotif mmAdapter;
	private static PullToRefreshSwipeMenuListView mListView;
	private static Handler mHandler;
	private static Map<String, HashMap> mapMenu;
	private ArrayList<tNotificationData> ListtNotificationData;
	private tNotificationBL _tNotificationBL = new tNotificationBL();
	private ImageView imgBdge;
	public static final Integer images1 = R.drawable.ic_bbm_notif;
	public static final Integer images2 = R.drawable.ic_bbm_transp;

	  @Override
		public void onBackPressed() {
			finish();
			Intent nextScreen = new Intent(getApplicationContext(), MainMenu.class);
			startActivity(nextScreen);
		}
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e2) {
			e2.printStackTrace();
		}
		Intent i = getIntent();
		MenuID=getMenuID(i);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lnotifi);
		//setTitleForm("List Notification");
		//RelativeLayout scrollable_contents = (RelativeLayout) findViewById(R.id.content);
		//getLayoutInflater().inflate(R.layout.activity_lnotifi, scrollable_contents);
		listNotip = (ListView) findViewById(R.id.listViewNotifikasi);
		mListView = (PullToRefreshSwipeMenuListView) findViewById(R.id.listView);
		imgBdge = (ImageView) findViewById(R.id.iv_icon);
		List<tNotificationData> listNotifikasi = new tNotificationBL().GetAllData();
		arrData=new ArrayList<String>();
		if(listNotifikasi!=null){
			swipeList.clear();
			rowItems = new ArrayList<clsRowItem>();
			for(tNotificationData dt :listNotifikasi ){
				String status = String.valueOf(dt.get_txtStatus());
				if(status.equals("0")){
					clsRowItem item = new clsRowItem();
					item.set_imageId(String.valueOf(images2));
					item.set_title(dt.get_txtTitle()+"\n"+dt.get_txtDescription());
					item.set_txtId(dt.get_guiID());
					item.set_desc(dt.get_dtPublishEnd());
					rowItems.add(item);
				} else if (status.equals("1")){
					clsRowItem item = new clsRowItem();
					item.set_imageId(String.valueOf(images1));
					item.set_title(dt.get_txtTitle()+"\n"+dt.get_txtDescription());
					item.set_txtId(dt.get_guiID());
					item.set_desc(dt.get_dtPublishEnd());
					rowItems.add(item);
				}
				
			}
	        mmAdapter = setListA(getApplicationContext(), rowItems);
	        mListView.setAdapter(mmAdapter);
	        mListView.setPullRefreshEnable(false);
	        mListView.setPullLoadEnable(true);
	        mListView.setXListViewListener(this);
	        mHandler = new Handler();
	        HashMap<String, String> mapView = new HashMap<String, String>();
	        
	        mapView.put("name", "View");
	        mapView.put("bgColor", "#3498db");
	        
	        mapMenu = new HashMap<String, HashMap>();
	        mapMenu.put("0", mapView);
	        
	        SwipeMenuCreator creator = setCreator(getApplicationContext(), mapMenu);
	        mListView.setMenuCreator(creator);
	        
//	        mListView.setOnMenuItemClickListener(mmenuSwipeListener(this, "LNotifi", mapMenu, rowItems));
		}
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
        mListView.setRefreshTime(RefreshTime.getRefreshTime(getApplicationContext()));
        mListView.stopRefresh();

        mListView.stopLoadMore();
	}
}
