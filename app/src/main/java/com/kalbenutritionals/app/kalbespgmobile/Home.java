package com.kalbenutritionals.app.kalbespgmobile;

import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import bl.clsHelperBL;
import bl.mMenuBL;
import bl.tAbsenUserBL;
import bl.tNotificationBL;
import bl.tUserLoginBL;
import come.example.viewbadger.ShortcutBadger;
import library.salesforce.common.clsPushData;
import library.salesforce.common.mMenuData;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tNotificationData;
import library.salesforce.dal.clsHardCode;
import service.MyServiceNative;

public class Home extends clsMainActivity {
	ListView lvMenu;
	List<String> arrMenu;
	HashMap<String, String> HMMenu = new HashMap<String, String>();
	HashMap<String, String> HMMenuID = new HashMap<String, String>();
	PackageInfo pInfo = null;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	//private Button btnLogout;
	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_masterpage);
		//setTitleForm("HOME");
		//setTitleGreeting(getDataLoginActive().get_txtName());
		LinearLayout contents = (LinearLayout) findViewById(R.id.llContent);
		getLayoutInflater().inflate(R.layout.activity_home, contents);
		pInfo = getPinfo();
		setHeaderFull();
		//ImageButton _btnHomeMaster=(ImageButton) this.findViewById(R.id.btnHomeMaster);
		//_btnHomeMaster.setVisibility(View.INVISIBLE);

		//lvMenu=(ListView)findViewById(R.id.lvMenu);
		/*
		btnLogout=(Button)findViewById(R.id.btnLogout);
		btnLogout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				//PushData
				AsyncCallLogOut task=new AsyncCallLogOut();
				task.execute();
				//new clsHelperBL().DeleteAllDB();
				
			}
		});
		*/
		
		

		/*
		arrMenu= new ArrayList<String>();
		for (mMenuData data : listMenu) {
			arrMenu.add(data.get_TxtMenuName());
			HMMenu.put( data.get_TxtMenuName(),data.get_TxtLink());
			HMMenuID.put( data.get_TxtMenuName(),data.get_TxtDescription());
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Home.this,
                android.R.layout.simple_spinner_item, arrMenu);
		 dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 lvMenu.setAdapter(dataAdapter);
		 lvMenu.setOnItemClickListener(new OnItemClickListener() {
		        @Override
		        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		            long arg3) {
		        		String lvMenuName = arrMenu.get(arg2);
			        	String myClass = HMMenu.get(lvMenuName);
			        	String MenuID = HMMenuID.get(lvMenuName);
			        	
			        	Class<?> clazz = null;
						try {
							clazz = Class.forName(myClass);
							Intent myIntent = new Intent(getApplicationContext(), clazz);
							myIntent.putExtra("MenuID", MenuID);
							finish();
				        	startActivity(myIntent);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
		            }
		 });
		 */
		List<mMenuData> listMenu = new mMenuBL().getDatabyParentId(0);
		//test dynamic
		int countMenu = listMenu.size();
		int jumLinearLayout = 0;
		int positionRow = 0;


		mMenuData datMenu = new mMenuData();

		final LinearLayout lm = (LinearLayout) findViewById(R.id.linearMain);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER_VERTICAL;
		params.setMargins(20, 100, 20, 100);
		lm.setLayoutParams(params);

		LinearLayout ll;
		Button btn;
		LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		paramsButton.weight = 1.0f;
		paramsButton.setMargins(10, 10, 10, 10);
		paramsButton.gravity = Gravity.CENTER_HORIZONTAL;


		int imgResource;

		for (int j = 0; j < countMenu; j++) {
			if (jumLinearLayout == 0) {
				ll = new LinearLayout(this);
				ll.setOrientation(LinearLayout.VERTICAL);
				LinearLayout.LayoutParams paramsDynamic = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				paramsDynamic.gravity = Gravity.CENTER_VERTICAL;
				paramsDynamic.setMargins(50, 20, 50, 20);
				ll.setLayoutParams(paramsDynamic);
				//ll.setPadding(0, 0, 0, 100);
				jumLinearLayout += 1;

				if (positionRow == 0) {
					for (int k = 0; k < 8; k++) {
						if (positionRow < listMenu.size()) {
							datMenu = listMenu.get(positionRow);
							final String strLink = datMenu.get_TxtLink();
							final String strDesc = datMenu.get_TxtDescription();

							// Create Button
							btn = new Button(this, null, R.style.HomeButton);
							btn.setId(positionRow + 1);
							//imgResource= R.drawable.shape_oval_orange;
							//imgResource = getStringResourceByName("b_"+strDesc.toLowerCase(),this.getPackageName());
							imgResource = getStringResourceByName(strDesc.toLowerCase(), this.getPackageName());

							btn.setBackgroundResource(R.drawable.btn_on_click);
							btn.setText(datMenu.get_TxtMenuName());
							btn.setLayoutParams(paramsButton);

							btn.setGravity(Gravity.CENTER_HORIZONTAL);
							btn.setTypeface(btn.getTypeface(), Typeface.NORMAL);
							//btn.setTextColor(Color.parseColor("#00000"));


							final int index = positionRow;
							if (positionRow > 1) {
								j += 1;
							}
							positionRow += 1;
							btn.setOnClickListener(new OnClickListener() {
								public void onClick(View v) {
									String myClass;
									String MenuID;
									Class<?> clazz = null;

									myClass = strLink;
									MenuID = strDesc;

									try {
										if (myClass.equals("#") && MenuID.toString().contains("Logout")) {
											funcLogOut();
										} else if (MenuID.toString().contains("mnDownloadData")) {
											Intent myIntent = new Intent(getApplicationContext(), DownloadData.class);
											myIntent.putExtra("MenuID", MenuID);
											finish();
											startActivity(myIntent);
										}
										else if (MenuID.toString().contains("mnAbsenKBN")) {
											Intent myIntent = new Intent(getApplicationContext(), Absen.class);
											myIntent.putExtra("MenuID", MenuID);
											finish();
											startActivity(myIntent);
										} else if (MenuID.toString().contains("mnNotifikasiKBN")) {
											Intent myIntent = new Intent(getApplicationContext(), LNotifi.class);
											myIntent.putExtra("MenuID", MenuID);
											finish();
											startActivity(myIntent);
										} else if (MenuID.toString().contains("mnNotifikasiKBN")) {
											Intent myIntent = new Intent(getApplicationContext(), Reso.class);
											myIntent.putExtra("MenuID", MenuID);
											finish();
											startActivity(myIntent);
										}
										else{
											clazz = Class.forName(myClass);
											Intent myIntent = new Intent(getApplicationContext(), clazz);
											myIntent.putExtra("MenuID", MenuID);
											finish();
											startActivity(myIntent);
										}
									} catch (ClassNotFoundException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									//Toast.makeText(getApplicationContext(), "Clicked Button Index :" + index, Toast.LENGTH_LONG).show();
								}
							});

							//Add button to LinearLayout
							ll.addView(btn);
						}
					}
					//Add button to LinearLayout defined in XML
					lm.addView(ll);
				}
			}
		}
		int totalStatus = new tNotificationBL().getContactsCountStatus();
		ShortcutBadger.removeCount(Home.this);
		ShortcutBadger.applyCount(Home.this, totalStatus);
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	private clsHardCode clsHardcode = new clsHardCode();

	private void funcLogOut() {
		LayoutInflater layoutInflater = LayoutInflater.from(Home.this);
		final View promptView = layoutInflater.inflate(R.layout.confirm_data, null);

		final TextView _tvConfirm = (TextView) promptView.findViewById(R.id.tvTitle);
		final TextView _tvDesc = (TextView) promptView.findViewById(R.id.tvDesc);
		_tvDesc.setVisibility(View.INVISIBLE);
		_tvConfirm.setText("Log Out Application ?");

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Home.this);
		alertDialogBuilder.setView(promptView);
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								//PushData
								stopService(new Intent(getApplicationContext(), MyServiceNative.class));
								AsyncCallLogOut task = new AsyncCallLogOut();
								task.execute();
								//new clsHelperBL().DeleteAllDB();
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		final AlertDialog alertD = alertDialogBuilder.create();
		alertD.show();
	}

	int intProcesscancel = 0;

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Home Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://com.kalbenutritionals.app.kalbespgmobile/http/host/path")
		);
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Home Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://com.kalbenutritionals.app.kalbespgmobile/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();
	}

	private class AsyncCallLogOut extends AsyncTask<JSONArray, Void, JSONArray> {
		@Override
		protected JSONArray doInBackground(JSONArray... params) {
			JSONArray Json = null;

			try {
				List<tAbsenUserData> listAbsenData = new ArrayList<tAbsenUserData>();
				tAbsenUserData dtTabsenData = new tAbsenUserBL().getDataCheckInActive();
				if (dtTabsenData != null) {
					dtTabsenData.set_dtDateCheckOut(FormatDateComplete().toString());
					dtTabsenData.set_intSubmit("1");
					dtTabsenData.set_intSync("0");
					listAbsenData.add(dtTabsenData);
					new tAbsenUserBL().saveData(listAbsenData);
				}
				clsPushData dtJson = new clsHelperBL().pushData();
				if (dtJson != null) {
					String versionName = "";
					try {
						versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
					} catch (NameNotFoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {
						JSONArray Jresult = null;
						if (dtJson.getDtdataJson().getListOftAbsenUserData() != null) {
							List<tAbsenUserData> listAbsen = dtJson.getDtdataJson().getListOftAbsenUserData();
							if (listAbsen.get(0).get_txtAbsen().equals("0")) {
								Jresult = new clsHelperBL().callPushDataReturnJson(versionName, dtJson.getDtdataJson().txtJSON().toString(), dtJson.getFileUpload());
							} else {
								Jresult = new clsHelperBL().callPushDataReturnJson(versionName, dtJson.getDtdataJson().txtJSON().toString(), null);
							}
							new clsHelperBL().saveDataPush(dtJson.getDtdataJson(), Jresult);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				Json = new tUserLoginBL().Logout(pInfo.versionName);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			return Json;
		}

		private ProgressDialog Dialog = new ProgressDialog(Home.this);

		@Override
		protected void onCancelled() {
			Dialog.dismiss();
			Toast toast = Toast.makeText(Home.this,
					clsHardcode.txtMessCancelRequest, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.TOP, 25, 400);
			toast.show();
		}

		@Override
		protected void onPostExecute(JSONArray roledata) {
			if (roledata != null) {
				Iterator i = roledata.iterator();
				while (i.hasNext()) {
					JSONObject innerObj = (JSONObject) i.next();
					Long IntResult = (Long) innerObj.get("_pboolValid");
					String PstrMessage = (String) innerObj.get("_pstrMessage");

					if (IntResult == 1) {
						tNotificationBL _tNotificationBL = new tNotificationBL();
						List<tNotificationData> ListData = _tNotificationBL.getAllDataWillAlert("1");
						if (ListData != null) {
							for (tNotificationData dttNotificationData : ListData) {
								NotificationManager tnotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
								tnotificationManager.cancelAll();
								ShortcutBadger.applyCount(Home.this, 0);
								System.gc();
							}
						}
						new clsHelperBL().DeleteAllDB();
						finish();
						Intent nextScreen = new Intent(getApplicationContext(), ProgressBarActivity.class);
						startActivity(nextScreen);
					} else {
						Toast toast = Toast.makeText(getApplicationContext(),
								PstrMessage, Toast.LENGTH_LONG);
						toast.setGravity(Gravity.TOP, 25, 400);
						toast.show();
					}
				}

			} else {
				if (intProcesscancel == 1) {
					onCancelled();
				} else {
					Toast toast = Toast.makeText(getApplicationContext(),
							clsHardcode.txtMessNetworkOffline, Toast.LENGTH_LONG);
					toast.setGravity(Gravity.TOP, 25, 400);
					toast.show();
				}

			}
			Dialog.dismiss();
		}

		@Override
		protected void onPreExecute() {
			//Make ProgressBar invisible
			//pg.setVisibility(View.VISIBLE);
			Dialog.setMessage(new clsHardCode().txtMessLogOut);
			Dialog.setCancelable(false);
			Dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					intProcesscancel = 1;
				}
			});
			Dialog.show();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Dialog.dismiss();
		}

	}

	
	
	/*
	public void onClickFeature (View v)
	{
		String myClass ;
  	    String MenuID ;
  	    Class<?> clazz = null;
	    int id = v.getId ();
	    
	    switch (id) {
	      case R.id.home_btn_feature1 : 
	    	  myClass= "com.kalbe.salesforce.DownloadData";
	    	  MenuID= "mnDownloadData";
	    	 
				try {
					clazz = Class.forName(myClass);
					Intent myIntent = new Intent(getApplicationContext(), clazz);
					myIntent.putExtra("MenuID", MenuID);
					finish();
		        	startActivity(myIntent);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           break;
	      case R.id.home_btn_feature2 :
	    	  
	    	  myClass= "com.kalbe.salesforce.Pengeluaran";
	    	  MenuID="mnPengeluaranKBN";
	    	  
				try {
					clazz = Class.forName(myClass);
					Intent myIntent = new Intent(getApplicationContext(), clazz);
					myIntent.putExtra("MenuID", MenuID);
					finish();
		        	startActivity(myIntent);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           break;
	      default: 
	    	   break;
	    }
	}
	*/


}
