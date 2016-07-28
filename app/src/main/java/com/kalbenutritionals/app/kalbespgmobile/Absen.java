package com.kalbenutritionals.app.kalbespgmobile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import bl.mEmployeeAreaBL;
import bl.mEmployeeBranchBL;
import bl.mMenuBL;
import bl.tAbsenUserBL;
import bl.tDeviceInfoUserBL;
import bl.tUserLoginBL;
import library.salesforce.common.mEmployeeAreaData;
import library.salesforce.common.mEmployeeBranchData;
import library.salesforce.common.mMenuData;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tDeviceInfoUserData;
import library.salesforce.common.tUserLoginData;

public class Absen extends clsMainActivity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
	private GoogleMap mMap;
	private Location mLastLocation;
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
	private static final String TAG = Absen.class.getSimpleName();
	// Google client to interact with Google API
	private GoogleApiClient mGoogleApiClient;
	// boolean flag to toggle periodic location updates
	private boolean mRequestingLocationUpdates = false;
	private LocationRequest mLocationRequest;
	// Location updates intervals in sec
	private static int UPDATE_INTERVAL = 3000; // 10 sec
	private static int FATEST_INTERVAL = 5000; // 5 sec
	private static int DISPLACEMENT = 10; // 10 meters
	private Spinner spnOutlet;
	private Spinner spnBranch;
	private List<String> arrData;
	private Uri uriImage;
	private String uriImageSaveDB1;
	private String uriImageSaveDB2;
	private String Long;
	private String Lat;
	private String Acc;
	private static final int CAMERA_CAPTURE_IMAGE1_REQUEST_CODE = 100;
	private static final int CAMERA_CAPTURE_IMAGE2_REQUEST_CODE = 130;
	private ImageView imgPrevNoImg1;
	private ImageView imgPrevNoImg2;
	private static final String IMAGE_DIRECTORY_NAME = "Image Sales";
	private HashMap<String, String> HMbranch = new HashMap<String, String>();
	private HashMap<String, String> HMoutlet = new HashMap<String, String>();
	final String finalFile = null;
	private TextView lblLong;
	private TextView lblLang;
	private TextView lblAcc;
	private TextView txtHDId;
	private ArrayAdapter<String> dataAdapterBranch;
	private ArrayAdapter<String> dataAdapterOutlet;
	private tAbsenUserBL _tAbsenUserBL;
	Options options;
	private tAbsenUserData dttAbsenUserData;
	private Button btnRefreshMaps;
	private Button btnCheckIn;
	private String MenuID;
	private String[] arrdefaultBranch = new String[]{"Branch"};
	private String[] arrdefaultOutlet = new String[]{"Outlet"};


	private String nameBranch;
	private String nameOutlet;
	private String branchCode;
	private String outletCode;
	private String myClass;
	private Class<?> clazz = null;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	public void onBackPressed() {
		finish();
		Intent nextScreen = new Intent(getApplicationContext(), Home.class);
		nextScreen.putExtra(clsParameterPutExtra.MenuID, MenuID);
		startActivity(nextScreen);
	}

	public static Bitmap resizeBitMapImage1(String filePath, int targetWidth, int targetHeight) {
		Bitmap bitMapImage = null;
		try {
			Options options = new Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(filePath, options);
			double sampleSize = 0;
			Boolean scaleByHeight = Math.abs(options.outHeight - targetHeight) >= Math.abs(options.outWidth
					- targetWidth);
			if (options.outHeight * options.outWidth * 2 >= 1638) {
				sampleSize = scaleByHeight ? options.outHeight / targetHeight : options.outWidth / targetWidth;
				sampleSize = (int) Math.pow(2d, Math.floor(Math.log(sampleSize) / Math.log(2d)));
			}
			options.inJustDecodeBounds = false;
			options.inTempStorage = new byte[128];
			while (true) {
				try {
					options.inSampleSize = (int) sampleSize;
					bitMapImage = BitmapFactory.decodeFile(filePath, options);
					break;
				} catch (Exception ex) {
					try {
						sampleSize = sampleSize * 2;
					} catch (Exception ex1) {

					}
				}
			}
		} catch (Exception ex) {

		}
		return bitMapImage;
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Absen Page", // TODO: Define a title for the content shown.
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

	public class MyAdapter extends ArrayAdapter<String> {
		private List<String> arrayDataAdapyter;
		private Context Ctx;

		public List<String> getArrayDataAdapyter() {
			return arrayDataAdapyter;
		}

		public void setArrayDataAdapyter(List<String> arrayDataAdapyter) {
			this.arrayDataAdapyter = arrayDataAdapyter;
		}

		public Context getCtx() {
			return Ctx;
		}

		public void setCtx(Context ctx) {
			Ctx = ctx;
		}

		public MyAdapter(Context context, int textViewResourceId, List<String> objects) {
			super(context, textViewResourceId, objects);
			setCtx(context);
			setArrayDataAdapyter(objects);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		public View getCustomView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.custom_spinner, parent, false);
			if (arrData.size() > 0) {
				TextView label = (TextView) row.findViewById(R.id.tvTitle);
				//label.setText(arrData.get(position));
				label.setText(getArrayDataAdapyter().get(position));
				TextView sub = (TextView) row.findViewById(R.id.tvDesc);
				sub.setVisibility(View.GONE);
				sub.setVisibility(View.GONE);
				label.setTextColor(new Color().parseColor("#000000"));
				row.setBackgroundColor(new Color().parseColor("#FFFFFF"));
			}
			//sub.setText(mydata2[position]);
			return row;
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_masterpage);
//		setTitleForm("Absen");
		LinearLayout scrollable_contents = (LinearLayout) findViewById(R.id.llContent);
		getLayoutInflater().inflate(R.layout.activity_absen, scrollable_contents);
		txtHDId = (TextView) findViewById(R.id.txtHDId);
//		btnRefreshMap = (Button) findViewById(R.id.btnRefreshMap);
		btnRefreshMaps = (Button) findViewById(R.id.btnRefreshMaps);
		btnCheckIn = (Button) findViewById(R.id.buttonCheckIn);
		spnOutlet = (Spinner) findViewById(R.id.spnOutlet);
		spnBranch = (Spinner) findViewById(R.id.spnBranch);
		imgPrevNoImg1 = (ImageView) findViewById(R.id.imageViewCamera1);
		imgPrevNoImg2 = (ImageView) findViewById(R.id.imageViewCamera2);
		lblLong = (TextView) findViewById(R.id.tvLong);
		lblLang = (TextView) findViewById(R.id.tvLat);
		lblAcc = (TextView) findViewById(R.id.tvAcc);
		options = new Options();
		options.inSampleSize = 2;
		_tAbsenUserBL = new tAbsenUserBL();
		dttAbsenUserData = _tAbsenUserBL.getDataCheckInActive();
		lblLong.setText("");
		lblLang.setText("");
		lblAcc.setText("");
		Intent i = getIntent();
		MenuID = "mnAbsenKBN";

		final mMenuData dtmenuData = new mMenuBL().getMenuDataByMenuName(MenuID);
		//TableLayout TableLayout1 = (TableLayout)findViewById(R.id.TableLayout1);
//		btnRefreshMap.setOnClickListener(new OnClickListener() {
//			//int intProcesscancel=0;
//			@Override
//			public void onClick(View v) {
//				displayLocation();
//				showToast(getApplicationContext(), "Location Founded");
//			}
//		});
		btnRefreshMaps.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				displayLocation();
				showToast(getApplicationContext(), "Location Updated");
			}
		});

		List<mEmployeeBranchData> listDataBranch = new mEmployeeBranchBL().GetAllData();
		List<mEmployeeAreaData> listDataArea = new mEmployeeAreaBL().GetAllData();
		if (checkPlayServices()) {
			// Building the GoogleApi client
			buildGoogleApiClient();
			//createLocationRequest();
		}
		// First we need to check availability of play services
		imgPrevNoImg1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String nameBranch = spnBranch.getSelectedItem().toString();
				String nameOutlet = spnOutlet.getSelectedItem().toString();
				String branchCode = HMbranch.get(nameBranch);
				String outletCode = HMoutlet.get(nameOutlet);
				tUserLoginData dataUserActive = new tAbsenUserBL().getUserActive();
				String idUserActive = String.valueOf(dataUserActive.get_txtUserId());
				List<tDeviceInfoUserData> dataDeviceInfoUser = new tDeviceInfoUserBL().getData(1);
				String deviceInfo = String.valueOf(dataDeviceInfoUser.get(0).get_txtDeviceId());
				List<tAbsenUserData> absenUserDatas = new ArrayList<tAbsenUserData>();
				if (dttAbsenUserData == null) {
					dttAbsenUserData = new tAbsenUserData();
				}
				dttAbsenUserData.set_intId(txtHDId.getText().toString());
				dttAbsenUserData.set_intSubmit("0");
				dttAbsenUserData.set_intSync("0");
				dttAbsenUserData.set_txtAbsen("0");//
				dttAbsenUserData.set_txtAccuracy(lblAcc.getText().toString());
				dttAbsenUserData.set_txtBranchCode(branchCode);
				dttAbsenUserData.set_txtBranchName(nameBranch);
				dttAbsenUserData.set_txtLatitude(lblLang.getText().toString());
				dttAbsenUserData.set_txtLongitude(lblLong.getText().toString());
				dttAbsenUserData.set_txtOutletCode(outletCode);
				dttAbsenUserData.set_txtOutletName(nameOutlet);
				dttAbsenUserData.set_txtDeviceId(deviceInfo);
				dttAbsenUserData.set_txtUserId(idUserActive); //
				//dttAbsenUserData.set_txtImg1(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_maps_local_see));
				absenUserDatas.add(dttAbsenUserData);
				new tAbsenUserBL().saveData(absenUserDatas);
				captureImage1();
			}
		});
		imgPrevNoImg2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String nameBranch = spnBranch.getSelectedItem().toString();
				String nameOutlet = spnOutlet.getSelectedItem().toString();
				String branchCode = HMbranch.get(nameBranch);
				String outletCode = HMoutlet.get(nameOutlet);
				if (dttAbsenUserData == null) {
					dttAbsenUserData = new tAbsenUserData();
				}
				tUserLoginData dataUserActive = new tAbsenUserBL().getUserActive();
				String idUserActive = String.valueOf(dataUserActive.get_txtUserId());
				List<tDeviceInfoUserData> dataDeviceInfoUser = new tDeviceInfoUserBL().getData(1);
				String deviceInfo = String.valueOf(dataDeviceInfoUser.get(0).get_txtDeviceId());
				List<tAbsenUserData> absenUserDatas = new ArrayList<tAbsenUserData>();
				dttAbsenUserData.set_intId(txtHDId.getText().toString());
				dttAbsenUserData.set_intSubmit("0");
				dttAbsenUserData.set_intSync("0");
				dttAbsenUserData.set_txtAbsen("0");//
				dttAbsenUserData.set_txtAccuracy(lblAcc.getText().toString());
				dttAbsenUserData.set_txtBranchCode(branchCode);
				dttAbsenUserData.set_txtBranchName(nameBranch);
				dttAbsenUserData.set_txtLatitude(lblLang.getText().toString());
				dttAbsenUserData.set_txtLongitude(lblLong.getText().toString());
				dttAbsenUserData.set_txtOutletCode(outletCode);
				dttAbsenUserData.set_txtOutletName(nameOutlet);
				dttAbsenUserData.set_txtDeviceId(deviceInfo);
				dttAbsenUserData.set_txtUserId(idUserActive); //
				//dttAbsenUserData.set_txtImg2(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_maps_local_see));
				absenUserDatas.add(dttAbsenUserData);
				new tAbsenUserBL().saveData(absenUserDatas);
				captureImage2();
			}
		});
		arrData = new ArrayList<String>();
		if (listDataBranch.size() > 0) {
			for (mEmployeeBranchData dt : listDataBranch) {
				arrData.add(dt.get_txtBranchName());
				HMbranch.put(dt.get_txtBranchName(), dt.get_txtBranchCode());
			}
			dataAdapterBranch = new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData);
			spnBranch.setAdapter(dataAdapterBranch);
		}
		arrData = new ArrayList<String>();
		if (listDataArea.size() > 0) {
			for (mEmployeeAreaData dt : listDataArea) {
				arrData.add(dt.get_txtOutletName());
				HMoutlet.put(dt.get_txtOutletName(), dt.get_txtOutletCode());
			}
			dataAdapterOutlet = new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData);
			spnOutlet.setAdapter(dataAdapterOutlet);
		}

		if (dttAbsenUserData != null) {
			if (dttAbsenUserData.get_intSubmit().equals("1")) {
				spnBranch.setEnabled(false);
				spnOutlet.setEnabled(false);
				imgPrevNoImg1.setClickable(false);
				imgPrevNoImg2.setClickable(false);
			}


			txtHDId.setText(dttAbsenUserData.get_intId());
			int intPosition = getSpinnerPositionByValue(HMbranch, dttAbsenUserData.get_txtBranchCode(), spnBranch);
			spnBranch.setSelection(intPosition);
			intPosition = getSpinnerPositionByValue(HMoutlet, dttAbsenUserData.get_txtOutletCode(), spnOutlet);
			spnOutlet.setSelection(intPosition);
			lblAcc.setText(dttAbsenUserData.get_txtAccuracy());
			lblLang.setText(dttAbsenUserData.get_txtLatitude());
			lblLong.setText(dttAbsenUserData.get_txtLongitude());

			double latitude = Double.valueOf(lblLang.getText().toString());
			double longitude = Double.valueOf(lblLong.getText().toString());
			MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Updating Location!");
			marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
			// adding marker
			try {
				// Loading map
				initilizeMap();
				// Changing map type
				mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				//mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

			} catch (Exception e) {
				e.printStackTrace();
			}
			mMap.clear();
			mMap.addMarker(marker);
			if (dttAbsenUserData.get_intSubmit().equals("1")) {
				//showToast(getApplicationContext(), "Location Founded");
//				ShowMenu(dtmenuData);
			}
		} else {
			int IdAbsen = _tAbsenUserBL.getContactsCount() + 1;
			txtHDId.setText(String.valueOf(IdAbsen));
			displayLocation();
		}

		// Checking camera availability
		if (!isDeviceSupportCamera()) {
			Toast.makeText(getApplicationContext(), "Sorry! Your device doesn't support camera", Toast.LENGTH_LONG).show();
			// will close the app if the device does't have camera
			finish();
		}

		dttAbsenUserData = _tAbsenUserBL.getDataCheckInActive();
		if (dttAbsenUserData != null) {
			if (dttAbsenUserData.get_intSubmit().equals("1")) {
				// Kalau ada langsung ke Main Menu
				String nameBranch = spnBranch.getSelectedItem().toString();
				String nameOutlet = spnOutlet.getSelectedItem().toString();

				String branchCode = HMbranch.get(nameBranch);
				String outletCode = HMoutlet.get(nameOutlet);

				String myClass = "com.kalbe.salesforce.Checkin";
				String MenuID = "mnCheckinKBN";
				Class<?> clazz = null;

//				clazz = Class.forName(myClass);
				Intent myIntent = new Intent(getApplicationContext(), Home.class);
				myIntent.putExtra(clsParameterPutExtra.MenuID, MenuID);
				myIntent.putExtra(clsParameterPutExtra.BranchCode, branchCode);
				myIntent.putExtra(clsParameterPutExtra.OutletCode, outletCode);
				finish();
				startActivity(myIntent);
			} else {
				// Kalau ga ada harus check in dulu
//				ShowMenu(dtmenuData);
			}
		} else {
			// Kalau ga ada harus check in dulu
//			ShowMenu(dtmenuData);
		}
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
		btnCheckIn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myClass="com.kalbenutritionals.app.kalbespgmobile.Home"; ;
				MenuID="mnCheckinKBN";
				clazz = null;

				myClass= "com.kalbenutritionals.app.kalbespgmobile.Home";
				MenuID = "mnCheckinKBN";
				nameBranch = spnBranch.getSelectedItem().toString();
				nameOutlet = spnOutlet.getSelectedItem().toString();
				branchCode = HMbranch.get(nameBranch);
				outletCode = HMoutlet.get(nameOutlet);
				LayoutInflater layoutInflater = LayoutInflater.from(Absen.this);
				final View promptView = layoutInflater.inflate(R.layout.confirm_data, null);

				final TextView _tvConfirm=(TextView) promptView.findViewById(R.id.tvTitle);
				final TextView _tvDesc=(TextView) promptView.findViewById(R.id.tvDesc);
				_tvDesc.setVisibility(View.INVISIBLE);
				_tvConfirm.setText("Check In Data ?");

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Absen.this);
				alertDialogBuilder.setView(promptView);
				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,	int id) {
										Boolean pRes= true;
										if(dttAbsenUserData == null){
											pRes=false;
										}else{
											if((dttAbsenUserData.get_txtImg1().equals("")|| dttAbsenUserData.get_txtImg1().equals("null"))
													&& (dttAbsenUserData.get_txtImg2().equals("")|| dttAbsenUserData.get_txtImg2().equals("null"))){
												pRes=false;
											}
										}
										if(pRes){
											nameBranch = spnBranch.getSelectedItem().toString();
											nameOutlet = spnOutlet.getSelectedItem().toString();
											branchCode = HMbranch.get(nameBranch);
											outletCode = HMoutlet.get(nameOutlet);
											if(dttAbsenUserData == null){
												dttAbsenUserData=new tAbsenUserData();
											}
											tAbsenUserData datatAbsenUserData = dttAbsenUserData;
											tUserLoginData dataUserActive = new tUserLoginBL().getUserActive();
											String idUserActive = String.valueOf(dataUserActive.get_txtUserId());
											List<tDeviceInfoUserData> dataDeviceInfoUser = new tDeviceInfoUserBL().getData(1);
											String deviceInfo = String.valueOf(dataDeviceInfoUser.get(0).get_txtDeviceId());
											List<tAbsenUserData> absenUserDatas = new ArrayList<tAbsenUserData>();
											DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
											Calendar cal = Calendar.getInstance();
											datatAbsenUserData.set_dtDateCheckIn(dateFormat.format(cal.getTime()));
											datatAbsenUserData.set_intId(txtHDId.getText().toString());
											datatAbsenUserData.set_intSubmit("1");
											datatAbsenUserData.set_intSync("0");
											datatAbsenUserData.set_txtAbsen("0");//
											datatAbsenUserData.set_txtBranchCode(branchCode);
											datatAbsenUserData.set_txtBranchName(nameBranch);
											datatAbsenUserData.set_txtAccuracy(lblAcc.getText().toString());
											datatAbsenUserData.set_txtLatitude(lblLang.getText().toString());
											datatAbsenUserData.set_txtLongitude(lblLong.getText().toString());
											datatAbsenUserData.set_txtOutletCode(outletCode);
											datatAbsenUserData.set_txtOutletName(nameOutlet);
											datatAbsenUserData.set_txtDeviceId(deviceInfo);
											datatAbsenUserData.set_txtUserId(idUserActive); //
											absenUserDatas.add(datatAbsenUserData);
											new tAbsenUserBL().saveData(absenUserDatas);
											showToast(getApplicationContext(), "Save Data Check-in");
											spnBranch.setEnabled(false);
											spnOutlet.setEnabled(false);
											imgPrevNoImg1.setClickable(false);
											imgPrevNoImg2.setClickable(false);
											btnRefreshMaps.setClickable(false);
											btnRefreshMaps.setVisibility(View.GONE);

											try {
												clazz = Class.forName(myClass);
												Intent myIntent = new Intent(getApplicationContext(), MainMenu.class);
												myIntent.putExtra(clsParameterPutExtra.MenuID, MenuID);
												myIntent.putExtra(clsParameterPutExtra.BranchCode, branchCode);
												myIntent.putExtra(clsParameterPutExtra.OutletCode, outletCode);
												finish();
												startActivity(myIntent);
											} catch (ClassNotFoundException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}else{
											showToast(getApplicationContext(), "Please Photo at least 1 photo..");
										}
									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,	int id) {
										dialog.cancel();
									}
								});
				final AlertDialog alertD = alertDialogBuilder.create();
				alertD.show();
			}
//					else{
//						clazz = Class.forName(myClass);
//						Intent myIntent = new Intent(getApplicationContext(), clazz);
//						myIntent.putExtra(clsParameterPutExtra.MenuID, MenuID);
//						myIntent.putExtra(clsParameterPutExtra.BranchCode, branchCode);
//						myIntent.putExtra(clsParameterPutExtra.OutletCode, outletCode);
//						finish();
//						startActivity(myIntent);
//					}

		});
	}

//	private void ShowMenu(final mMenuData dtmenuData) {
//		List<mMenuData> listMenu = new mMenuBL().getDatabyParentId(Integer.valueOf(dtmenuData.get_IntMenuID()));
//		int countMenu = listMenu.size();
//		int jumLinearLayout = 0;
//		int positionRow = 0;
//		final LinearLayout lm = (LinearLayout) findViewById(R.id.linearMain);
//		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
//		lm.setLayoutParams(params);
//		lm.setVisibility(View.VISIBLE);
//		LinearLayout ll = new LinearLayout(this);
//		Button btn = new Button(this, null, R.style.HomeButton);
//		LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
//		paramsButton.weight = 1.0f;
//		paramsButton.gravity = Gravity.CENTER_VERTICAL;
//
//		mMenuData datMenu = new mMenuData();
//		int imgResource;
//		for (int j = 0; j < countMenu; j++) {
//			if (jumLinearLayout == 0) {
//				ll = new LinearLayout(this);
//				ll.setOrientation(LinearLayout.HORIZONTAL);
//				LinearLayout.LayoutParams paramsDynamic = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//				paramsDynamic.setMargins(0, 0, 0, 40);
//				ll.setLayoutParams(paramsDynamic);
//				ll.setPadding(0, 0, 0, 40);
//				jumLinearLayout += 1;
//
//				if (positionRow == 0) {
//					for (int k = 0; k < 2; k++) {
//						if (positionRow < listMenu.size()) {
//							datMenu = listMenu.get(positionRow);
//							final String strLink = datMenu.get_TxtLink();
//							final String strDesc = datMenu.get_TxtDescription();
//
//							// Create Button
//							btn = new Button(this, null, R.style.HomeButton);
//							btn.setId(positionRow + 1);
//							//imgResource= R.drawable.shape_oval_orange;
//							//imgResource = getStringResourceByName("b_"+strDesc.toLowerCase(),this.getPackageName());
//							imgResource = getStringResourceByName(strDesc.toLowerCase(), this.getPackageName());
//
//							btn.setCompoundDrawablesWithIntrinsicBounds(0, imgResource, 0, 0);
//							btn.setText(datMenu.get_TxtMenuName());
//							btn.setLayoutParams(paramsButton);
//
//							btn.setGravity(Gravity.CENTER_HORIZONTAL);
//							btn.setTypeface(btn.getTypeface(), Typeface.NORMAL);
//							//btn.setTextColor(Color.parseColor("#00000"));
//
//
//							final int index = positionRow;
//							if (positionRow > 1) {
//								j += 1;
//							}
//							positionRow += 1;
//							btn.setOnClickListener(new OnClickListener() {
//								public void onClick(View v) {
//									myClass = "";
//									;
//									MenuID = "";
//									clazz = null;
//
//									myClass = strLink;
//									MenuID = strDesc;
//									nameBranch = spnBranch.getSelectedItem().toString();
//									nameOutlet = spnOutlet.getSelectedItem().toString();
//									branchCode = HMbranch.get(nameBranch);
//									outletCode = HMoutlet.get(nameOutlet);
//									try {
//										if (myClass.equals("com.kalbe.salesforce.Checkin") && MenuID.toString().contains("mnCheckinKBN")) {
//											LayoutInflater layoutInflater = LayoutInflater.from(Absen.this);
//											final View promptView = layoutInflater.inflate(R.layout.confirm_data, null);
//
//											final TextView _tvConfirm = (TextView) promptView.findViewById(R.id.tvTitle);
//											final TextView _tvDesc = (TextView) promptView.findViewById(R.id.tvDesc);
//											_tvDesc.setVisibility(View.INVISIBLE);
//											_tvConfirm.setText("Check In Data ?");
//
//											AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Absen.this);
//											alertDialogBuilder.setView(promptView);
//											alertDialogBuilder
//													.setCancelable(false)
//													.setPositiveButton("OK",
//															new DialogInterface.OnClickListener() {
//																public void onClick(DialogInterface dialog, int id) {
//																	Boolean pRes = true;
//																	String pWarning = "";
//																	if (dttAbsenUserData == null) {
//																		pRes = false;
//																		pWarning = "Please Photo at least 1 photo..";
//																	} else {
//																		if ((dttAbsenUserData.get_txtImg1().equals("") || dttAbsenUserData.get_txtImg1().equals("null"))
//																				&& (dttAbsenUserData.get_txtImg2().equals("") || dttAbsenUserData.get_txtImg2().equals("null"))) {
//																			pRes = false;
//																			pWarning = "Please Photo at least 1 photo..";
//																		}
//																		if (lblLong.getText().equals("") || lblAcc.getText().equals("") || lblLang.getText().equals("")) {
//																			pRes = false;
//																			pWarning = "please turn on GPS Mobile...";
//																		}
//																	}
//																	if (pRes) {
//																		nameBranch = spnBranch.getSelectedItem().toString();
//																		nameOutlet = spnOutlet.getSelectedItem().toString();
//																		branchCode = HMbranch.get(nameBranch);
//																		outletCode = HMoutlet.get(nameOutlet);
//																		if (dttAbsenUserData == null) {
//																			dttAbsenUserData = new tAbsenUserData();
//																		}
//																		tAbsenUserData datatAbsenUserData = dttAbsenUserData;
//																		tUserLoginData dataUserActive = new tUserLoginBL().getUserActive();
//																		String idUserActive = String.valueOf(dataUserActive.get_txtUserId());
//																		List<tDeviceInfoUserData> dataDeviceInfoUser = new tDeviceInfoUserBL().getData(1);
//																		String deviceInfo = String.valueOf(dataDeviceInfoUser.get(0).get_txtDeviceId());
//																		List<tAbsenUserData> absenUserDatas = new ArrayList<tAbsenUserData>();
//																		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//																		Calendar cal = Calendar.getInstance();
//																		datatAbsenUserData.set_dtDateCheckIn(dateFormat.format(cal.getTime()));
//																		datatAbsenUserData.set_intId(txtHDId.getText().toString());
//																		datatAbsenUserData.set_intSubmit("1");
//																		datatAbsenUserData.set_intSync("0");
//																		datatAbsenUserData.set_txtAbsen("0");//
//																		datatAbsenUserData.set_txtBranchCode(branchCode);
//																		datatAbsenUserData.set_txtBranchName(nameBranch);
//																		datatAbsenUserData.set_txtAccuracy(lblAcc.getText().toString());
//																		datatAbsenUserData.set_txtLatitude(lblLang.getText().toString());
//																		datatAbsenUserData.set_txtLongitude(lblLong.getText().toString());
//																		datatAbsenUserData.set_txtOutletCode(outletCode);
//																		datatAbsenUserData.set_txtOutletName(nameOutlet);
//																		datatAbsenUserData.set_txtDeviceId(deviceInfo);
//																		datatAbsenUserData.set_txtUserId(idUserActive); //
//																		absenUserDatas.add(datatAbsenUserData);
//																		new tAbsenUserBL().saveData(absenUserDatas);
//																		showToast(getApplicationContext(), "Save Data Check-in");
//																		spnBranch.setEnabled(false);
//																		spnOutlet.setEnabled(false);
//																		imgPrevNoImg1.setClickable(false);
//																		imgPrevNoImg2.setClickable(false);
//																		btnRefreshMap.setClickable(false);
//																		btnRefreshMap.setVisibility(View.GONE);
//
//																		try {
//																			clazz = Class.forName(myClass);
//																			Intent myIntent = new Intent(getApplicationContext(), clazz);
//																			myIntent.putExtra(clsParameterPutExtra.MenuID, MenuID);
//																			myIntent.putExtra(clsParameterPutExtra.BranchCode, branchCode);
//																			myIntent.putExtra(clsParameterPutExtra.OutletCode, outletCode);
//																			finish();
//																			startActivity(myIntent);
//																		} catch (ClassNotFoundException e) {
//																			// TODO Auto-generated catch block
//																			e.printStackTrace();
//																		}
//																	} else {
//																		showToast(getApplicationContext(), pWarning);
//																	}
//																}
//															})
//													.setNegativeButton("Cancel",
//															new DialogInterface.OnClickListener() {
//																public void onClick(DialogInterface dialog, int id) {
//																	dialog.cancel();
//																}
//															});
//											final AlertDialog alertD = alertDialogBuilder.create();
//											alertD.show();
//										} else {
//											clazz = Class.forName(myClass);
//											Intent myIntent = new Intent(getApplicationContext(), clazz);
//											myIntent.putExtra(clsParameterPutExtra.MenuID, MenuID);
//											myIntent.putExtra(clsParameterPutExtra.BranchCode, branchCode);
//											myIntent.putExtra(clsParameterPutExtra.OutletCode, outletCode);
//											finish();
//											startActivity(myIntent);
//										}
//									} catch (ClassNotFoundException e) {
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//									}
//
//									//Toast.makeText(getApplicationContext(), "Clicked Button Index :" + index, Toast.LENGTH_LONG).show();
//								}
//							});
//
//							//Add button to LinearLayout
//							ll.addView(btn);
//						}
//					}
//					//Add button to LinearLayout defined in XML
//					lm.addView(ll);
//				}
//			} else if (positionRow % 2d == 0) {
//				ll = new LinearLayout(this);
//				ll.setOrientation(LinearLayout.HORIZONTAL);
//				LinearLayout.LayoutParams paramsDynamic = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//				paramsDynamic.setMargins(0, 0, 0, 40);
//				ll.setLayoutParams(paramsDynamic);
//				ll.setPadding(0, 0, 0, 40);
//				jumLinearLayout += 1;
//
//				if (positionRow > 0) {
//					for (int k = 0; k < 2; k++) {
//						if (positionRow < listMenu.size()) {
//							datMenu = listMenu.get(positionRow);
//							final String strLink = datMenu.get_TxtLink();
//							final String strDesc = datMenu.get_TxtDescription();
//
//							// Create Button
//							btn = new Button(this, null, R.style.HomeButton);
//							btn.setId(positionRow + 1);
//							//imgResource= R.drawable.shape_oval_orange;
//							//imgResource = Integer.valueOf(getStringResourceByName("b_"+strDesc.toLowerCase(),this.getPackageName()));
//							imgResource = getStringResourceByName(strDesc.toLowerCase(), this.getPackageName());
//
//							btn.setCompoundDrawablesWithIntrinsicBounds(0, imgResource, 0, 0);
//							btn.setText(datMenu.get_TxtMenuName());
//							btn.setLayoutParams(paramsButton);
//							btn.setGravity(Gravity.CENTER_HORIZONTAL);
//							btn.setTypeface(btn.getTypeface(), Typeface.NORMAL);
//							//btn.setTextColor(Color.parseColor("#00000"));
//
//							j += 1;
//							positionRow += 1;
//
//							final int index = positionRow;
//							btn.setOnClickListener(new OnClickListener() {
//								public void onClick(View v) {
//									myClass = "";
//									MenuID = "";
//									clazz = null;
//									nameBranch = spnBranch.getSelectedItem().toString();
//									nameOutlet = spnOutlet.getSelectedItem().toString();
//									branchCode = HMbranch.get(nameBranch);
//									outletCode = HMoutlet.get(nameOutlet);
//									myClass = strLink;
//									MenuID = strDesc;
//									try {
//										if (myClass.equals("com.kalbe.salesforce.Checkin") && MenuID.toString().contains("mnCheckinKBN")) {
//											LayoutInflater layoutInflater = LayoutInflater.from(Absen.this);
//											final View promptView = layoutInflater.inflate(R.layout.confirm_data, null);
//
//											final TextView _tvConfirm = (TextView) promptView.findViewById(R.id.tvTitle);
//											final TextView _tvDesc = (TextView) promptView.findViewById(R.id.tvDesc);
//											_tvDesc.setVisibility(View.INVISIBLE);
//											_tvConfirm.setText("Check In Data ?");
//
//											AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Absen.this);
//											alertDialogBuilder.setView(promptView);
//											alertDialogBuilder
//													.setCancelable(false)
//													.setPositiveButton("OK",
//															new DialogInterface.OnClickListener() {
//																public void onClick(DialogInterface dialog, int id) {
//																	Boolean pRes = true;
//																	if (dttAbsenUserData == null) {
//																		pRes = false;
//																	} else {
//																		if ((dttAbsenUserData.get_txtImg1().equals("") || dttAbsenUserData.get_txtImg1().equals("null"))
//																				&& (dttAbsenUserData.get_txtImg2().equals("") || dttAbsenUserData.get_txtImg2().equals("null"))) {
//																			pRes = false;
//																		}
//																	}
//																	if (pRes) {
//																		nameBranch = spnBranch.getSelectedItem().toString();
//																		nameOutlet = spnOutlet.getSelectedItem().toString();
//																		branchCode = HMbranch.get(nameBranch);
//																		outletCode = HMoutlet.get(nameOutlet);
//																		if (dttAbsenUserData == null) {
//																			dttAbsenUserData = new tAbsenUserData();
//																		}
//																		tAbsenUserData datatAbsenUserData = dttAbsenUserData;
//																		tUserLoginData dataUserActive = new tUserLoginBL().getUserActive();
//																		String idUserActive = String.valueOf(dataUserActive.get_txtUserId());
//																		List<tDeviceInfoUserData> dataDeviceInfoUser = new tDeviceInfoUserBL().getData(1);
//																		String deviceInfo = String.valueOf(dataDeviceInfoUser.get(0).get_txtDeviceId());
//																		List<tAbsenUserData> absenUserDatas = new ArrayList<tAbsenUserData>();
//																		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//																		Calendar cal = Calendar.getInstance();
//																		datatAbsenUserData.set_dtDateCheckIn(dateFormat.format(cal.getTime()));
//																		datatAbsenUserData.set_intId(txtHDId.getText().toString());
//																		datatAbsenUserData.set_intSubmit("1");
//																		datatAbsenUserData.set_intSync("0");
//																		datatAbsenUserData.set_txtAbsen("0");//
//																		datatAbsenUserData.set_txtBranchCode(branchCode);
//																		datatAbsenUserData.set_txtBranchName(nameBranch);
//																		datatAbsenUserData.set_txtAccuracy(lblAcc.getText().toString());
//																		datatAbsenUserData.set_txtLatitude(lblLang.getText().toString());
//																		datatAbsenUserData.set_txtLongitude(lblLong.getText().toString());
//																		datatAbsenUserData.set_txtOutletCode(outletCode);
//																		datatAbsenUserData.set_txtOutletName(nameOutlet);
//																		datatAbsenUserData.set_txtDeviceId(deviceInfo);
//																		datatAbsenUserData.set_txtUserId(idUserActive); //
//																		absenUserDatas.add(datatAbsenUserData);
//																		new tAbsenUserBL().saveData(absenUserDatas);
//																		showToast(getApplicationContext(), "Save Data Check-in");
//																		spnBranch.setEnabled(false);
//																		spnOutlet.setEnabled(false);
//																		imgPrevNoImg1.setClickable(false);
//																		imgPrevNoImg2.setClickable(false);
//																		btnRefreshMap.setClickable(false);
//																		btnRefreshMap.setVisibility(View.GONE);
//
//																		try {
//																			clazz = Class.forName(myClass);
//																			Intent myIntent = new Intent(getApplicationContext(), clazz);
//																			myIntent.putExtra(clsParameterPutExtra.MenuID, MenuID);
//																			myIntent.putExtra(clsParameterPutExtra.BranchCode, branchCode);
//																			myIntent.putExtra(clsParameterPutExtra.OutletCode, outletCode);
//																			finish();
//																			startActivity(myIntent);
//																		} catch (ClassNotFoundException e) {
//																			// TODO Auto-generated catch block
//																			e.printStackTrace();
//																		}
//																	} else {
//																		showToast(getApplicationContext(), "Please Photo at least 1 photo..");
//																	}
//																}
//															})
//													.setNegativeButton("Cancel",
//															new DialogInterface.OnClickListener() {
//																public void onClick(DialogInterface dialog, int id) {
//																	dialog.cancel();
//																}
//															});
//											final AlertDialog alertD = alertDialogBuilder.create();
//											alertD.show();
//										} else {
//											clazz = Class.forName(myClass);
//											Intent myIntent = new Intent(getApplicationContext(), clazz);
//											myIntent.putExtra(clsParameterPutExtra.MenuID, MenuID);
//											myIntent.putExtra(clsParameterPutExtra.BranchCode, branchCode);
//											myIntent.putExtra(clsParameterPutExtra.OutletCode, outletCode);
//											finish();
//											startActivity(myIntent);
//										}
//									} catch (ClassNotFoundException e) {
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//									}
//
//									//Toast.makeText(getApplicationContext(), "Clicked Button Index :" + index, Toast.LENGTH_LONG).show();
//								}
//							});
//
//							//Add button to LinearLayout
//							ll.addView(btn);
//						} else if (positionRow == listMenu.size()) {
//							// Create Button
//							btn = new Button(this, null, R.style.HomeButton);
//							btn.setId(positionRow + 1);
//							imgResource = R.drawable.shape_oval_orange;
//							btn.setCompoundDrawablesWithIntrinsicBounds(0, imgResource, 0, 0);
//							btn.setText(datMenu.get_TxtMenuName());
//							btn.setLayoutParams(paramsButton);
//							btn.setGravity(Gravity.CENTER_HORIZONTAL);
//							btn.setTypeface(btn.getTypeface(), Typeface.NORMAL);
//							btn.setVisibility(View.INVISIBLE);
//
//							ll.addView(btn);
//						}
//					}
//					//Add button to LinearLayout defined in XML
//					lm.addView(ll);
//				}
//			}
//
//			/*
//			final TableRow row=new TableRow (getApplicationContext());
//			if(index%2==1){
//				row.setBackgroundColor(0xFF00FF00);
//			}
//			TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//			row.setLayoutParams(lp);
//			final TextView txtVIndex=new TextView(getApplicationContext());
//			txtVIndex.setText(String.valueOf(index+1));
//			txtVIndex.setWidth(30);
//			TextView txtVNamaProduct=new TextView(getApplicationContext());
//			txtVNamaProduct.setId(index);
//			txtVNamaProduct.setText(dt.get_TxtMenuName());
//			txtVNamaProduct.setWidth(450);
//			row.setOnClickListener(new View.OnClickListener() {
//			    @Override
//			    public void onClick(View v) {
//		        	String myClass = dt.get_TxtLink();
//		        	String MenuID = dt.get_TxtDescription();
//		        	Class<?> clazz = null;
//					try {
//						clazz = Class.forName(myClass);
//						String nameBranch = spnBranch.getSelectedItem().toString();
//						String nameOutlet = spnOutlet.getSelectedItem().toString();
//						String branchCode = HMbranch.get(nameBranch);
//						String outletCode = HMoutlet.get(nameOutlet);
//						Intent myIntent = new Intent(getApplicationContext(), clazz);
//						myIntent.putExtra(clsParameterPutExtra.MenuID, MenuID);
//						myIntent.putExtra(clsParameterPutExtra.BranchCode, branchCode);
//						myIntent.putExtra(clsParameterPutExtra.OutletCode, outletCode);
//						finish();
//			        	startActivity(myIntent);
//					} catch (ClassNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//			    }
//			});
//			row.addView(txtVIndex);
//			row.addView(txtVNamaProduct);
//			TableLayout1.addView(row,index);
//			index+=1;
//			*/
//		}
//		//TableLayout1.setVisibility(View.VISIBLE);
//	}

	private void createLocationRequest() {
		// TODO Auto-generated method stub
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(UPDATE_INTERVAL);
		mLocationRequest.setFastestInterval(FATEST_INTERVAL);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setSmallestDisplacement(DISPLACEMENT);

	}

	@SuppressWarnings("deprecation")
	private void displayLocation() {
		// TODO Auto-generated method stub
		if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

		if (mLastLocation != null) {
			double latitude = mLastLocation.getLatitude();
			double longitude = mLastLocation.getLongitude();
			double accurate = mLastLocation.getAccuracy();
			lblLong.setText(longitude + "");
			lblLang.setText(latitude + "");
			lblAcc.setText(accurate + "");

			Long = String.valueOf(longitude);
			Lat = String.valueOf(latitude);
			Acc = String.valueOf(accurate);

			try {
				// Loading map
				initilizeMap();

				// Changing map type
				mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				mMap.getUiSettings().setZoomControlsEnabled(true);
				mMap.getUiSettings().setMyLocationButtonEnabled(true);
				mMap.getUiSettings().setMapToolbarEnabled(true);
				//mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

			} catch (Exception e) {
				e.printStackTrace();
			}

			MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Updating Location!");
			marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			//marker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_kalbe_spgmobile));
			// adding marker
			mMap.clear();
			//Toast.makeText(getApplicationContext(),"Location Updated", Toast.LENGTH_LONG).show();
			mMap.addMarker(marker);
			//OnLocationChanged(location);
			CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(16).build();
			mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
			//togglePeriodicLocationUpdates();
		}

	}

	private void buildGoogleApiClient() {
		// TODO Auto-generated method stub
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();

	}

	@SuppressWarnings("deprecation")
	private boolean checkPlayServices() {
		// TODO Auto-generated method stub
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				showToast(getApplicationContext(), "This device is not supported GPS.");
				finish();
			}
			return false;
		}
		return true;
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private void initilizeMap() {
		// TODO Auto-generated method stub
		if (mMap == null) {
//			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();


			// check if map is created successfully or not
			if (mMap == null) {
				showToast(getApplicationContext(), "Sorry! unable to create maps");
			}
		}

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		if (mGoogleApiClient != null) {
			mGoogleApiClient.connect();
		}
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Absen Page", // TODO: Define a title for the content shown.
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initilizeMap();
		checkPlayServices();
	}

	protected void captureImage1() {
		Intent intentCamera1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		//uriImage = getOutputMediaFileUri();
		startActivityForResult(intentCamera1, CAMERA_CAPTURE_IMAGE1_REQUEST_CODE);
	}

	protected void captureImage2() {
		Intent intentCamera2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		//uriImage = getOutputMediaFileUri();
		startActivityForResult(intentCamera2, CAMERA_CAPTURE_IMAGE2_REQUEST_CODE);
	}

//	private Uri getOutputMediaFileUri() {
//		return Uri.fromFile(getOutputMediaFile());
//	}

//	private File getOutputMediaFile() {
//		// External sdcard location
//		File mediaStorageDir = new File(
//				new clsHardCode().txtFolderAbsen + txtHDId.getText().toString() + File.separator);
//		// Create the storage directory if it does not exist
//		if (!mediaStorageDir.exists()) {
//			if (!mediaStorageDir.mkdirs()) {
//				Log.d(IMAGE_DIRECTORY_NAME, "Failed create "
//						+ IMAGE_DIRECTORY_NAME + " directory");
//				return null;
//			}
//		}
//		// Create a media file name
//		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
//				Locale.getDefault()).format(new Date());
//		File mediaFile;
//		mediaFile = new File(mediaStorageDir.getPath() + File.separator
//				+ "IMG" + "_" + timeStamp + ".jpg");
//		return mediaFile;
//	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if the result is capturing Image
		//super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_CAPTURE_IMAGE1_REQUEST_CODE) {
			if (resultCode == RESULT_OK && (data.getExtras().get("data") != null || data.getData() != null)) {
				Bitmap photo = null;
				if (data.getExtras().get("data") != null) {
					photo = (Bitmap) data.getExtras().get("data");
				}
				else {
					try {
						photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				previewCapturedImage1(photo);
			} else if (resultCode == RESULT_CANCELED) {
				showToast(getApplicationContext(), "User cancelled image capture");
			} else {
				showToast(getApplicationContext(), "Sorry! Failed to capture image");
			}
		} else if (requestCode == CAMERA_CAPTURE_IMAGE2_REQUEST_CODE) {
			if (resultCode == RESULT_OK && (data.getExtras().get("data") != null || data.getData() != null)) {
				Bitmap photo = null;
				if (data.getExtras().get("data") != null) {
					photo = (Bitmap) data.getExtras().get("data");
				} else {
					try {
						photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				previewCapturedImage2(photo);
			} else if (resultCode == RESULT_CANCELED) {
				showToast(getApplicationContext(), "User cancelled image capture");
			} else {
				showToast(getApplicationContext(), "Sorry! Failed to capture image");
			}
		}

	}


	private String getRealPathFromURI(Uri uri) {
		//TODO Auto-generated method stub
		Cursor cursor = getContentResolver().query(uri, null, null, null, null);
		cursor.moveToFirst();
		int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
		return cursor.getString(idx);
	}
	private void previewCapturedImage1(Bitmap photo) {
		try {
			dttAbsenUserData = _tAbsenUserBL.getDataCheckInActive();
			imgPrevNoImg1.setVisibility(View.VISIBLE);
			ByteArrayOutputStream out = null;
			try {
				out = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ByteArrayOutputStream blob = new ByteArrayOutputStream();
			photo.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, blob);
			Bitmap bitmap1 = Bitmap.createScaledBitmap(photo, 150, 150, false);
			bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, blob);
			byte[] pht = out.toByteArray();
			imgPrevNoImg1.setImageBitmap(bitmap1);
			if (dttAbsenUserData != null) {
				dttAbsenUserData.set_txtImg1(pht);
			} else {
				dttAbsenUserData.set_txtImg1(pht);
				dttAbsenUserData.set_txtImg2(null);
				dttAbsenUserData.set_intId(txtHDId.getText().toString());
			}
			dttAbsenUserData.set_intSubmit("0");
			dttAbsenUserData.set_intSync("0");
			dttAbsenUserData.set_txtAbsen("0");//
			List<tAbsenUserData> Listdata = new ArrayList<tAbsenUserData>();
			Listdata.add(dttAbsenUserData);
			_tAbsenUserBL.saveData(Listdata);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	private void previewCapturedImage2(Bitmap photo) {
		try {
			dttAbsenUserData = _tAbsenUserBL.getDataCheckInActive();
			imgPrevNoImg2.setVisibility(View.VISIBLE);
			ByteArrayOutputStream out = null;
			try {
				out = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ByteArrayOutputStream blob = new ByteArrayOutputStream();
			photo.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, blob);
			Bitmap bitmap1 = Bitmap.createScaledBitmap(photo, 150, 150, false);
			bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, blob);
			byte[] pht = out.toByteArray();
			imgPrevNoImg2.setImageBitmap(bitmap1);
			if (dttAbsenUserData != null) {
				dttAbsenUserData.set_txtImg2(pht);
			} else {
				dttAbsenUserData.set_txtImg1(null);
				dttAbsenUserData.set_txtImg2(pht);
				dttAbsenUserData.set_intId(txtHDId.getText().toString());
			}
			dttAbsenUserData.set_intSubmit("0");
			dttAbsenUserData.set_intSync("0");
			dttAbsenUserData.set_txtAbsen("0");//
			List<tAbsenUserData> Listdata = new ArrayList<tAbsenUserData>();
			Listdata.add(dttAbsenUserData);
			_tAbsenUserBL.saveData(Listdata);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}




	@Override
	public void onConnectionFailed(@NonNull ConnectionResult result) {
		// TODO Auto-generated method stub
		Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
				+ result.getErrorCode());

	}

	@Override
	public void onConnected(@Nullable Bundle arg0) {
		// TODO Auto-generated method stub
		// Once connected with google api, get the location
		displayLocation();
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		mGoogleApiClient.connect();

	}

	private boolean isDeviceSupportCamera() {
		if (getApplicationContext().getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false;
		}
	}

	/**
	 * Method to toggle periodic location updates
	 */
	private void togglePeriodicLocationUpdates() {
		if (!mRequestingLocationUpdates) {
			// Changing the button text
			//btnStartLocationUpdates
			//.setText(getString(R.string.btn_stop_location_updates));

			mRequestingLocationUpdates = true;

			// Starting the location updates
			startLocationUpdates();
			//displayLocation();

			Log.d(TAG, "Periodic location updates started!");

		}
	}

	/**
	 * Starting the location updates
	 */
	protected void startLocationUpdates() {
		if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);

	}

	/**
	 * Stopping location updates
	 */
	protected void stopLocationUpdates() {
		LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		mLastLocation = location;
		displayLocation();

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}
}







