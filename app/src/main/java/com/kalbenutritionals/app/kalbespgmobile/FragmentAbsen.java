package com.kalbenutritionals.app.kalbespgmobile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

public class FragmentAbsen extends Fragment implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private Location mLastLocation;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private static final String TAG = AbsenFragment.class.getSimpleName();
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
    private Button btnRefreshMaps, btnPopupMap;
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

    View v;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
            LayoutInflater inflater = getActivity().getLayoutInflater();
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
            return row;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_absen, container, false);

        txtHDId = (TextView) v.findViewById(R.id.txtHDId);
        btnRefreshMaps = (Button) v.findViewById(R.id.btnRefreshMaps);
        btnCheckIn = (Button) v.findViewById(R.id.buttonCheckIn);
        btnPopupMap = (Button) v.findViewById(R.id.viewMap);
        spnOutlet = (Spinner) v.findViewById(R.id.spnOutlet);
        spnBranch = (Spinner) v.findViewById(R.id.spnType);
        imgPrevNoImg1 = (ImageView) v.findViewById(R.id.imageViewCamera1);
        imgPrevNoImg2 = (ImageView) v.findViewById(R.id.imageViewCamera2);
        lblLong = (TextView) v.findViewById(R.id.tvLong);
        lblLang = (TextView) v.findViewById(R.id.tvLat);
        lblAcc = (TextView) v.findViewById(R.id.tvAcc);
        options = new Options();
        options.inSampleSize = 2;
        _tAbsenUserBL = new tAbsenUserBL();
        dttAbsenUserData = _tAbsenUserBL.getDataCheckInActive();
        lblLong.setText("");
        lblLang.setText("");
        lblAcc.setText("");
        MenuID = "mnAbsenKBN";

        final mMenuData dtmenuData = new mMenuBL().getMenuDataByMenuName(MenuID);
        btnRefreshMaps.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLocation();
                Toast.makeText(getContext(), "Location Updated", Toast.LENGTH_SHORT).show();
            }
        });

        btnPopupMap.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                final View promptView = layoutInflater.inflate(R.layout.popup_map_absen, null);

                GoogleMap mMap = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    mMap = ((MapFragment) (getActivity()).getFragmentManager().findFragmentById(R.id.map)).getMap();

                    if (mMap == null) {
                        mMap = ((MapFragment) (getActivity()).getFragmentManager().findFragmentById(R.id.map)).getMap();
                    }

                    double latitude = Double.parseDouble(String.valueOf(lblLang.getText()));
                    double longitude = Double.parseDouble(String.valueOf(lblLong.getText()));
                    double accurate = Double.parseDouble(String.valueOf(lblAcc.getText()));

                    MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Location");
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    mMap.clear();
                    mMap.addMarker(marker);
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(19).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                    alertDialogBuilder.setView(promptView);
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,	int id) {
                                            MapFragment f = null;
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                                f = (MapFragment) (getActivity()).getFragmentManager().findFragmentById(R.id.map);
                                            }
                                            if (f != null){
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                                    (getActivity()).getFragmentManager().beginTransaction().remove(f).commit();
                                                }
                                            }

                                            dialog.dismiss();
                                        }
                                    });
                    final AlertDialog alertD = alertDialogBuilder.create();
                    alertD.setTitle("Your Location");
                    alertD.show();
                }

            }
        });

        List<mEmployeeBranchData> listDataBranch = new mEmployeeBranchBL().GetAllData();
        List<mEmployeeAreaData> listDataArea = new mEmployeeAreaBL().GetAllData();
        if (checkPlayServices()) {
            buildGoogleApiClient();
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
                dttAbsenUserData.set_txtUserId(idUserActive);
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
                dttAbsenUserData.set_txtUserId(idUserActive);
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
            dataAdapterBranch = new MyAdapter(getContext(), R.layout.custom_spinner, arrData);
            spnBranch.setAdapter(dataAdapterBranch);
        }
        arrData = new ArrayList<String>();
        if (listDataArea.size() > 0) {
            for (mEmployeeAreaData dt : listDataArea) {
                arrData.add(dt.get_txtOutletName());
                HMoutlet.put(dt.get_txtOutletName(), dt.get_txtOutletCode());
            }
            dataAdapterOutlet = new MyAdapter(getContext(), R.layout.custom_spinner, arrData);
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
            int intPosition = new clsMainActivity().getSpinnerPositionByValue(HMbranch, dttAbsenUserData.get_txtBranchCode(), spnBranch);
            spnBranch.setSelection(intPosition);
            intPosition = new clsMainActivity().getSpinnerPositionByValue(HMoutlet, dttAbsenUserData.get_txtOutletCode(), spnOutlet);
            spnOutlet.setSelection(intPosition);
            lblAcc.setText(dttAbsenUserData.get_txtAccuracy());
            lblLang.setText(dttAbsenUserData.get_txtLatitude());
            lblLong.setText(dttAbsenUserData.get_txtLongitude());

            double latitude = Double.valueOf(lblLang.getText().toString());
            double longitude = Double.valueOf(lblLong.getText().toString());
            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Updating Location!");
            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            try {
                initilizeMap();
                // Changing map type
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            } catch (Exception e) {
                e.printStackTrace();
            }
            mMap.clear();
            mMap.addMarker(marker);
            if (dttAbsenUserData.get_intSubmit().equals("1")) {

            }
        } else {
            int IdAbsen = _tAbsenUserBL.getContactsCount() + 1;
            txtHDId.setText(String.valueOf(IdAbsen));
            displayLocation();
        }

        // Checking camera availability
        if (!isDeviceSupportCamera()) {
        }

        dttAbsenUserData = _tAbsenUserBL.getDataCheckInActive();
        if (dttAbsenUserData != null) {
            if (dttAbsenUserData.get_intSubmit().equals("1")) {
            } else {
                // Kalau ga ada harus check in dulu
            }
        } else {
            // Kalau ga ada harus check in dulu
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(getContext()).addApi(AppIndex.API).build();
        btnCheckIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myClass="com.kalbenutritionals.app.kalbespgmobile.MainMenu"; ;
                MenuID="mnCheckinKBN";
                clazz = null;

                myClass= "com.kalbenutritionals.app.kalbespgmobile.MainMenu";
                MenuID = "mnCheckinKBN";
                nameOutlet = spnOutlet.getSelectedItem().toString();
                outletCode = HMoutlet.get(nameOutlet);
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                final View promptView = layoutInflater.inflate(R.layout.confirm_data, null);

                final TextView _tvConfirm=(TextView) promptView.findViewById(R.id.tvTitle);
                final TextView _tvDesc=(TextView) promptView.findViewById(R.id.tvDesc);
                _tvDesc.setVisibility(View.INVISIBLE);
                _tvConfirm.setText("Check In Data ?");

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
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
                                                    && (dttAbsenUserData.get_txtImg2().equals("")|| dttAbsenUserData.get_txtImg2().equals("null"))
                                                    && (spnBranch.getSelectedItem().toString().equals("")||spnBranch.getSelectedItem().toString().equals("null"))
                                                    && (HMbranch.get(nameBranch).equals("")|| HMbranch.get(nameBranch).equals("null"))){

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
                                            datatAbsenUserData.set_txtBranchCode(HMbranch.get(nameBranch));
                                            datatAbsenUserData.set_txtBranchName(spnBranch.getSelectedItem().toString());
                                            datatAbsenUserData.set_txtAccuracy(lblAcc.getText().toString());
                                            datatAbsenUserData.set_txtLatitude(lblLang.getText().toString());
                                            datatAbsenUserData.set_txtLongitude(lblLong.getText().toString());
                                            datatAbsenUserData.set_txtOutletCode(outletCode);
                                            datatAbsenUserData.set_txtOutletName(nameOutlet);
                                            datatAbsenUserData.set_txtDeviceId(deviceInfo);
                                            datatAbsenUserData.set_txtUserId(idUserActive); //
                                            absenUserDatas.add(datatAbsenUserData);
                                            new tAbsenUserBL().saveData(absenUserDatas);
                                            spnBranch.setEnabled(false);
                                            spnOutlet.setEnabled(false);
                                            imgPrevNoImg1.setClickable(false);
                                            imgPrevNoImg2.setClickable(false);
                                            btnRefreshMaps.setClickable(false);
                                            btnRefreshMaps.setVisibility(View.GONE);


                                            Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                                            try {
                                                clazz = Class.forName(myClass);
                                                Intent myIntent = new Intent(getContext(), MainMenu.class);
                                                myIntent.putExtra(clsParameterPutExtra.MenuID, MenuID);
                                                myIntent.putExtra(clsParameterPutExtra.BranchCode, branchCode);
                                                myIntent.putExtra(clsParameterPutExtra.OutletCode, outletCode);
                                                startActivity(myIntent);
                                            } catch (ClassNotFoundException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                            }
                                        }
                                        else {
                                            Toast.makeText(getContext(), "Please Photo at least 1 photo..", Toast.LENGTH_SHORT).show();
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
        return v;
    }

    @SuppressWarnings("deprecation")
    private void displayLocation() {
        // TODO Auto-generated method stub
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        }

    }

    private void buildGoogleApiClient() {
        // TODO Auto-generated method stub
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

    }

    @SuppressWarnings("deprecation")
    private boolean checkPlayServices() {
        // TODO Auto-generated method stub
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
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

            // check if map is created successfully or not
            if (mMap == null) {

            }
        }

    }

    @Override
    public void onStart() {
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
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        initilizeMap();
        checkPlayServices();
    }

    protected void captureImage1() {
        Intent intentCamera1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentCamera1, CAMERA_CAPTURE_IMAGE1_REQUEST_CODE);
    }

    protected void captureImage2() {
        Intent intentCamera2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentCamera2, CAMERA_CAPTURE_IMAGE2_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE1_REQUEST_CODE) {
            if (resultCode == -1 && (data.getExtras().get("data") != null || data.getData() != null)) {
                Bitmap photo = null;
                if (data.getExtras().get("data") != null) {
                    photo = (Bitmap) data.getExtras().get("data");
                }
                else {
                    try {
                        photo = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), data.getData());
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                previewCapturedImage1(photo);
            } else if (resultCode == 0) {
            } else {
            }
        } else if (requestCode == CAMERA_CAPTURE_IMAGE2_REQUEST_CODE) {
            if (resultCode == -1 && (data.getExtras().get("data") != null || data.getData() != null)) {
                Bitmap photo = null;
                if (data.getExtras().get("data") != null) {
                    photo = (Bitmap) data.getExtras().get("data");
                } else {
                    try {
                        photo = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), data.getData());
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                previewCapturedImage2(photo);
            } else if (resultCode == 0) {
            } else {
            }
        }

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
        if (getContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
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






