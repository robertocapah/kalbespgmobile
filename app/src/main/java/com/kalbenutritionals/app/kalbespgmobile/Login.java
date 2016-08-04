package com.kalbenutritionals.app.kalbespgmobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import bl.clsHelperBL;
import bl.mCounterNumberBL;
import bl.mEmployeeAreaBL;
import bl.mMenuBL;
import bl.mUserRoleBL;
import bl.tDeviceInfoUserBL;
import bl.tUserLoginBL;
import library.salesforce.common.mEmployeeAreaData;
import library.salesforce.common.mMenuData;
import library.salesforce.common.mUserRoleData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import service.MyServiceNative;

public class Login extends clsMainActivity {
    private String role = "Role";
    private String[] roles = new String[1];
    ProgressDialog progress;
    long Delay = 3000;
    private EditText txtLoginEmail;
    private EditText txtLoginPassword;
    private Button btnLogin, btnPing;
    private TextView txtVersionLogin;
    private PackageInfo pInfo = null;
    private List<String> arrrole, arroutlet;
    private HashMap<String, String> HMRole = new HashMap<String, String>();
    private HashMap<String, String> HMOutletCode = new HashMap<String, String>();
    private HashMap<String, String> HMOutletName = new HashMap<String, String>();
    private HashMap<String, String> HMBranchCode = new HashMap<String, String>();
    private Spinner spnRole, spnOutlet;
    private int intSet = 1;
    private String selectedRole;
    private String selectedOutlet;
    private String txtEmail;
    private String txtEmail1;
    private String txtPassword1;
    private String txtPassword;
    private String[] arrdefaultBranch = new String[]{"-"};
    private String[] arrdefaultOutlet = new String[]{"-"};
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    @Override
    public void onBackPressed() {
        finish();
    }

    private clsHardCode clsHardcode = new clsHardCode();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        setTitleForm("Login");
//        RelativeLayout contents = (RelativeLayout) findViewById(R.id.rlContent);
//        getLayoutInflater().inflate(R.layout.activity_login, contents);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        setContentView(R.layout.activity_login);

        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

//        Timer RunSplash = new Timer();
//
//        // Note: declare ProgressDialog progress as a field in your class.
//
//        progress = ProgressDialog.show(this, "",
//                "Checking Version!!!", true);
//
//        TimerTask ShowSplash = new TimerTask() {
//            @Override
//            public void run() {
//                //Intent myIntent = new Intent(Login.this, Login.class);
//                // do the thing that takes a long time
//                progress.dismiss();
//                //finish();
//                //startActivity(myIntent);
//            }
//        };
//
//        RunSplash.schedule(ShowSplash, Delay);
        new tDeviceInfoUserBL().SaveInfoDevice("","");
        ImageView imgBanner = (ImageView) findViewById(R.id.ivBannerLogin) ;
        imgBanner.setAdjustViewBounds(true);
        imgBanner.setScaleType(ImageView.ScaleType.CENTER_CROP);
        txtLoginEmail = (EditText) findViewById(R.id.txtLoginEmail);
        txtLoginPassword = (EditText) findViewById(R.id.editTextPass);
        txtLoginEmail.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    intProcesscancel = 0;
                    txtEmail1 = txtLoginEmail.getText().toString();
                    txtPassword1 = txtLoginPassword.getText().toString();
                    AsyncCallRole task = new AsyncCallRole();
                    task.execute();
                    return true;
                }
                return false;
            }
        });


        //AsyncCallAppVesion task1 = new AsyncCallAppVesion();
        //task1.execute();
        txtLoginPassword.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(txtLoginPassword) {
            public boolean onDrawableClick() {
                if (intSet == 1) {
                    txtLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    intSet = 0;
                } else {
                    txtLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    intSet = 1;
                }

                return true;
            }
       });
        txtVersionLogin = (TextView) findViewById(R.id.txtVersionLogin);
        txtVersionLogin.setText(pInfo.versionName);
        spnRole=(Spinner)findViewById(R.id.spnType);

        spnRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedRole = spnRole.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        spnOutlet=(Spinner)findViewById(R.id.spnOutlet);

        spnOutlet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedOutlet = spnOutlet.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        btnLogin=(Button)findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                intProcesscancel=0;
                if(txtLoginEmail.getText().length()==0){
                    Toast.makeText(getApplicationContext(), "Please input username", Toast.LENGTH_SHORT);

                } else {
                    if(spnRole.getCount()==0){
                        txtEmail1 = txtLoginEmail.getText().toString();
                        AsyncCallRole task = new AsyncCallRole();
                        task.execute();
                    }else{
                        txtEmail1 = txtLoginEmail.getText().toString();
                        txtPassword1 = txtLoginPassword.getText().toString();
                        AsyncCallLogin task = new AsyncCallLogin();
                        task.execute();
                    }
                }
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        Button btnExit = (Button) findViewById(R.id.buttonExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button btnPing = (Button) findViewById(R.id.buttonPing);
        btnPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IsReachable(getApplicationContext());
            }
        });
        ArrayAdapter<String> adapterspnBranch = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrdefaultBranch);
        spnRole.setAdapter(adapterspnBranch);
        spnRole.setEnabled(false);
        ArrayAdapter<String> adapterspnOutlet = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrdefaultOutlet);
        spnOutlet.setAdapter(adapterspnOutlet);
        spnOutlet.setEnabled(false);
    }

    int intProcesscancel = 0;
    private class AsyncCallLogin extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            JSONArray Json=null;
            String nameRole = selectedRole;
            String nameOutlet = selectedOutlet;
            try {
                Json= new tUserLoginBL().LoginNew(String.valueOf(txtEmail1), String.valueOf(txtPassword1), HMRole.get(nameRole), HMOutletCode.get(nameOutlet), HMOutletName.get(nameOutlet), HMBranchCode.get(nameOutlet), pInfo.versionName);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Json ;
        }

        private ProgressDialog Dialog = new ProgressDialog(Login.this);
        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(Login.this,
                    new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }
        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata.size() > 0){
                Iterator i = roledata.iterator();
                while (i.hasNext()) {
                    JSONObject innerObj = (JSONObject) i.next();
                    Long IntResult=(Long) innerObj.get("_pboolValid");
                    String PstrMessage=(String) innerObj.get("_pstrMessage");

                    if(IntResult == 1){
                        tUserLoginData _tUserLoginData=new tUserLoginData();
                        new mCounterNumberBL().saveDateTimeServer((String) innerObj.get("DatetimeNow"));
                        _tUserLoginData.set_intId(1);
                        _tUserLoginData.set_txtCab((String) innerObj.get("TxtCab"));
                        _tUserLoginData.set_txtDataId((String) innerObj.get("TxtDataId"));
                        _tUserLoginData.set_txtDeviceId((String) innerObj.get("TxtDeviceId"));
                        _tUserLoginData.set_TxtEmail((String) innerObj.get("TxtEmail"));
                        _tUserLoginData.set_TxtEmpId((String) innerObj.get("TxtEmpId"));
                        _tUserLoginData.set_txtName((String) innerObj.get("TxtName"));
                        _tUserLoginData.set_txtPassword((String) innerObj.get("TxtPassword"));
                        _tUserLoginData.set_txtPathImage((String) innerObj.get("TxtPathImage"));
                        _tUserLoginData.set_txtRoleId((String) innerObj.get("TxtRoleId"));
                        _tUserLoginData.set_txtRoleName((String) innerObj.get("TxtRoleName"));
                        _tUserLoginData.set_txtUserId((String) innerObj.get("TxtUserId"));
                        _tUserLoginData.set_txtUserName((String) innerObj.get("TxtUserName"));
                        _tUserLoginData.set_dtLastLogin((String) innerObj.get("DtLastLogin"));
                        _tUserLoginData.set_txtOutletCode((String) innerObj.get("TxtOutletCode"));
                        _tUserLoginData.set_txtOutletName((String) innerObj.get("TxtOutletName"));
                        _tUserLoginData.set_txtBranchCode((String) innerObj.get("TxtBranchCode"));

                        new tDeviceInfoUserBL().SaveInfoDevice(_tUserLoginData.get_TxtEmpId(), _tUserLoginData.get_txtDeviceId());
                        new tUserLoginBL().saveData(_tUserLoginData);

                        String nameOutlet = spnOutlet.getSelectedItem().toString();
                        new mEmployeeAreaBL().DeleteEmployeeNotInId(HMOutletCode.get(nameOutlet));


                        JSONArray JsonArrayDetail=(JSONArray) innerObj.get("ListOfMWebMenuAPI");
                        Iterator iDetail = JsonArrayDetail.iterator();
                        List<mMenuData> listData=new ArrayList<mMenuData>();
                        while (iDetail.hasNext()) {
                            JSONObject innerObjDetail = (JSONObject) iDetail.next();
                            mMenuData data=new mMenuData();
                            data.set_IntMenuID(String.valueOf((Long) innerObjDetail.get("IntMenuID")));
                            data.set_IntOrder((Long) innerObjDetail.get("IntOrder"));
                            data.set_IntParentID((Long) innerObjDetail.get("IntParentID"));
                            data.set_TxtDescription((String) innerObjDetail.get("TxtDescription"));
                            data.set_TxtLink((String) innerObjDetail.get("TxtLink"));
                            data.set_TxtMenuName((String) innerObjDetail.get("TxtMenuName"));
                            listData.add(data);
                        }
                        new mMenuBL().SaveData(listData);
                        startService(new Intent(getApplicationContext(), MyServiceNative.class));
                        finish();
                        Intent myIntent = new Intent(getApplicationContext(), MainMenu.class);
                        myIntent.putExtra("keyMainMenu", "main_menu");
                        startActivity(myIntent);
                    }else{
                        Toast toast = Toast.makeText(Login.this,
                                PstrMessage, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 25, 400);
                        toast.show();
                        txtLoginPassword.requestFocus();
                    }
                }

            }else{
                if(intProcesscancel==1){
                    onCancelled();
                }else{
                    Toast toast = Toast.makeText(Login.this,
                            new clsHardCode().txtMessDataNotFound, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                    txtLoginEmail.requestFocus();
                }

            }
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            //Make ProgressBar invisible
            //pg.setVisibility(View.VISIBLE);
            Dialog.setMessage(new clsHardCode().txtMessLogin);
            Dialog.setCancelable(false);
            Dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    intProcesscancel=1;
                    txtLoginEmail.requestFocus();
                }
            });
            Dialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Dialog.dismiss();
        }

    }

    ProgressDialog mProgressDialog;

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Login Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app URL is correct.
//                Uri.parse("android-app://spgmobile/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client, viewAction);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Login Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app URL is correct.
//                Uri.parse("android-app://spgmobile/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(client, viewAction);
//        client.disconnect();
//    }

    private class AsyncCallRole extends AsyncTask<List<mUserRoleData>, Void, List<mUserRoleData>> {
        @Override
        protected List<mUserRoleData> doInBackground(List<mUserRoleData>... params) {
            List<mUserRoleData> roledata = new ArrayList<mUserRoleData>();
            try {
                //EditText txt = (EditText) findViewById(R.id.txtLoginEmail);
                roledata = new mUserRoleBL().getRoleAndOutlet(txtEmail1, pInfo.versionName);

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return roledata;
        }

        private ProgressDialog Dialog = new ProgressDialog(Login.this);

        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(Login.this,
                    new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }

        @Override
        protected void onPostExecute(List<mUserRoleData> roledata) {
            if (roledata.size() > 0) {
                arrrole = new ArrayList<String>();
                for (mUserRoleData dt : roledata) {
                    arrrole.add(dt.get_txtRoleName());
                    HMRole.put(dt.get_txtRoleName(), dt.get_intRoleId());
                }
                spnRole.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrrole));
                spnRole.setEnabled(true);

                List<mEmployeeAreaData> dataOutlet = new mEmployeeAreaBL().GetAllData();

                arroutlet = new ArrayList<String>();
                for (mEmployeeAreaData dtOutlet : dataOutlet) {
                    arroutlet.add(dtOutlet.get_txtOutletName());
                    HMOutletCode.put(dtOutlet.get_txtOutletName(), dtOutlet.get_txtOutletCode());
                    HMOutletName.put(dtOutlet.get_txtOutletName(), dtOutlet.get_txtOutletName());
                    HMBranchCode.put(dtOutlet.get_txtOutletName(), dtOutlet.get_txtBranchCode());
                }

                spnOutlet.setAdapter(new MyAdapter2(getApplicationContext(), R.layout.custom_spinner, arroutlet));
                spnOutlet.setEnabled(true);
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    Toast toast = Toast.makeText(Login.this,
                            clsHardcode.txtMessNetworkOffline, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();

                    spnRole.setAdapter(null);
                    spnOutlet.setAdapter(null);
                    txtLoginEmail.requestFocus();
                }

            }
            Dialog.dismiss();
        }

        int intProcesscancel = 0;

        @Override
        protected void onPreExecute() {
            //Make ProgressBar invisible
            //pg.setVisibility(View.VISIBLE);
            Dialog.setMessage(new clsHardCode().txtMessGetUserRole);
            Dialog.setCancelable(false);
            Dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    intProcesscancel = 1;
                    txtLoginEmail.requestFocus();
                }
            });
            Dialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Dialog.dismiss();
        }

    }

    public class MyAdapter extends ArrayAdapter<String> {
        public MyAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
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
            TextView label = (TextView) row.findViewById(R.id.tvTitle);
            label.setText(arrrole.get(position));
            TextView sub = (TextView) row.findViewById(R.id.tvDesc);
            sub.setVisibility(View.INVISIBLE);
            sub.setVisibility(View.GONE);
            //sub.setText(mydata2[position]);
            //label.setTextColor(new Color().parseColor("#FFFFF"));
            row.setBackgroundColor(new Color().TRANSPARENT);
            return row;
        }

    }

    public class MyAdapter2 extends ArrayAdapter<String> {
        public MyAdapter2(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
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
            TextView label = (TextView) row.findViewById(R.id.tvTitle);
            label.setText(arroutlet.get(position));
            TextView sub = (TextView) row.findViewById(R.id.tvDesc);
            sub.setVisibility(View.INVISIBLE);
            sub.setVisibility(View.GONE);
            //sub.setText(mydata2[position]);
            //label.setTextColor(new Color().parseColor("#FFFFF"));
            row.setBackgroundColor(new Color().TRANSPARENT);
            return row;
        }

    }

    private class AsyncCallAppVesion extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            JSONArray JsonData = null;
            try {
                JsonData = new clsHelperBL().GetDatamversionAppPostData(pInfo.versionName);

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return JsonData;
        }

        private ProgressDialog Dialog = new ProgressDialog(Login.this);

        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(Login.this,
                    new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }

        @Override
        protected void onPostExecute(JSONArray JsonArry) {
            if (JsonArry != null) {
                arrrole = new ArrayList<String>();
                clsHelperBL _clsHelper = new clsHelperBL();
                // declare the dialog as a member field of your activity
                Iterator i = JsonArry.iterator();
                Boolean resUpdate = false;
                String txtLink = "";
                while (i.hasNext()) {
                    JSONObject innerObj = (JSONObject) i.next();
                    int boolValid = Integer.valueOf(String.valueOf(innerObj.get("_pboolValid")));
                    if (boolValid == Integer.valueOf(new clsHardCode().intSuccess)) {
                        if (pInfo.versionName.equals(innerObj.get("TxtVersion").toString())) {
                            resUpdate = false;
                        } else {
                            resUpdate = true;
                            txtLink = String.valueOf(innerObj.get("TxtLinkApp"));
                        }
                    }
                }
                if (resUpdate) {
                    // instantiate it within the onCreate method
                    mProgressDialog = new ProgressDialog(Login.this);
                    mProgressDialog.setMessage("Please Wait For Downloading File....");
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    mProgressDialog.setCancelable(false);

                    // execute this when the downloader must be fired
                    final DownloadTask downloadTask = new DownloadTask(Login.this);
                    downloadTask.execute(txtLink);

                    mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            downloadTask.cancel(true);
                        }
                    });
                }
            } else {
                showToast(Login.this, clsHardcode.txtMessNetworkOffline);
            }
            Dialog.dismiss();
        }

        int intProcesscancel = 0;

        @Override
        protected void onPreExecute() {
            //Make ProgressBar invisible
            //pg.setVisibility(View.VISIBLE);
            Dialog.setMessage("Checking Your Version SalesForce!!");
            Dialog.setCancelable(false);
            Dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    intProcesscancel = 1;
                    txtLoginEmail.requestFocus();
                }
            });
            Dialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Dialog.dismiss();
        }

    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {
        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                String txtPath = new clsHardCode().txtPathUserData;
                File mediaStorageDir = new File(txtPath);
                // Create the storage directory if it does not exist
                if (!mediaStorageDir.exists()) {
                    if (!mediaStorageDir.mkdirs()) {
                        return null;
                    }
                }
                output = new FileOutputStream(txtPath + "salesforce.apk");

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        int intProcesscancel = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                showToast(context, "Download error: " + result);
            else {
                showToast(context, "File downloaded" + result);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String txtPath = new clsHardCode().txtPathUserData + "salesforce.apk";
                intent.setDataAndType(Uri.fromFile(new File(txtPath)), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }
}
