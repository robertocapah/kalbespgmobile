package com.kalbenutritionals.app.kalbespgmobile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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
import bl.clsMainBL;
import bl.mCounterNumberBL;
import bl.mMenuBL;
import bl.mUserRoleBL;
import bl.tDeviceInfoUserBL;
import bl.tLogErrorBL;
import bl.tUserLoginBL;
import library.salesforce.common.mMenuData;
import library.salesforce.common.mUserRoleData;
import library.salesforce.common.tLogErrorData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.enumConfigData;
import library.salesforce.dal.mconfigDA;

import static junit.framework.Assert.assertEquals;

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
    private String userName="";
    private TextView tvForgotPassword, tvPushError;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Exit");
        builder.setMessage("Do you want to exit?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private clsHardCode clsHardcode = new clsHardCode();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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
                    if (!txtEmail1.equals("")) {
                        AsyncCallRole task = new AsyncCallRole();
                        task.execute();
                    } else {
                        txtLoginEmail.requestFocus();
                        showCustomToast(Login.this, "Please input username", false);
                    }
                    return true;
                }
                return false;
            }
        });


        AsyncCallAppVesion task1 = new AsyncCallAppVesion();
        task1.execute();
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

        txtLoginPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction()==KeyEvent.ACTION_DOWN){
                    if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER ||
                            keyCode == KeyEvent.KEYCODE_ENTER){
                        btnLogin.performClick();
                        return true;
                    }
                }

                return false;
            }
        });

        txtLoginPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btnLogin.performClick();
                    return true;
                }
                return false;
            }
        });

        String linkAPI = new clsMainBL().getLinkAPI();
        txtVersionLogin = (TextView) findViewById(R.id.txtVersionLogin);
        txtVersionLogin.setText(pInfo.versionName + "\n" + linkAPI);
        txtVersionLogin.setGravity(Gravity.CENTER_HORIZONTAL);
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
        tvForgotPassword = (TextView) findViewById(R.id.tv_forgot_password);
        tvForgotPassword.setPaintFlags(tvForgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAccount();
            }
        });

        List<tLogErrorData> datass = new tLogErrorBL().getAllData();

        tvPushError = (TextView) findViewById(R.id.tv_push_error);
        if(datass.size()>0 ){
            tvPushError.setVisibility(View.VISIBLE);
        }else{
            tvPushError.setVisibility(View.GONE);
        }
        tvPushError = (TextView) findViewById(R.id.tv_push_error);
        tvPushError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pushError = new Intent(Login.this, ActivityPushError.class);
                pushError.putExtra("status", 0);
                startActivity(pushError);
//                pushError();
            }
        });

        btnLogin=(Button)findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                intProcesscancel=0;
                if(txtLoginEmail.getText().length()==0){
                    showCustomToast(Login.this, "Please input username", false);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);

                builder.setTitle("Exit");
                builder.setMessage("Do you want to exit?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        Button btnPing = (Button) findViewById(R.id.buttonPing);
        btnPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUrl = new mconfigDA(new clsMainBL().getDb()).getData(new clsMainBL().getDb(), enumConfigData.ApiKalbe.getidConfigData()).get_txtValue();

                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    URL url = new URL(strUrl);
                    HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.connect();

                    assertEquals(HttpURLConnection.HTTP_OK, urlConn.getResponseCode());
                    showCustomToast(Login.this, "Connected", true);

                } catch (IOException e) {
                    showCustomToast(Login.this, "Not connected", false);
                }
            }
        });
        Button btnResetPass = (Button) findViewById(R.id.button_reset);
        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAccount();
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


        AsyncCallAppVesion task = new AsyncCallAppVesion();
        task.execute();
    }

    private android.support.v7.app.AlertDialog alertDialog;
    private void resetAccount(){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View promptView = inflater.inflate(R.layout.fragment_reset_password, null);
        final EditText etEmail = (EditText) promptView.findViewById(R.id.et_email_reset);
        final TextInputLayout tiEmail = (TextInputLayout) promptView.findViewById(R.id.input_layout_email_reset);
        etEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        Button btnReset = (Button) promptView.findViewById(R.id.button_reset);
        new clsMainActivity().removeErrorMessage(tiEmail);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int a = 2/0;
//                System.out.println(a);
                if(etEmail.getText().toString().equals("")){
                    new clsMainActivity().setErrorMessage(getApplicationContext(), tiEmail, etEmail, "Email cannot empty");
                }else if(!etEmail.getText().toString().equals("")){
                    userName = etEmail.getText().toString();
                    AsyncCallReset task = new AsyncCallReset();
                    task.execute();
                }
            }
        });
        android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        alertBuilder.setView(promptView);
        alertDialog = alertBuilder.create();
        alertBuilder.setCancelable(false);
        alertDialog.show();
    }
    private void pushError(){


       /* ActivityPushError activityPushError = new ActivityPushError();
        FragmentTransaction fragmentTransactionPushError = getFragmentManager().beginTransaction();

        FrameLayout frame = new FrameLayout(this);
        frame.setId(1);
        setContentView(frame,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fragmentTransactionPushError.add(frame.getId(), activityPushError).commit();
        */
       /* LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View promptView = inflater.inflate(R.layout.fragment_push_error, null);
        final Button btnPushError = (Button) promptView.findViewById(R.id.btn_push_err);
        btnPushError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

    }

    int intProcesscancel = 0;
    private class AsyncCallLogin extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
//            android.os.Debug.waitForDebugger();
            JSONArray Json=null;
            String nameRole = selectedRole;
            String nameOutlet = selectedOutlet;
            try {
                Json= new tUserLoginBL().LoginNew(String.valueOf(txtEmail1), String.valueOf(txtPassword1), HMRole.get(nameRole), null, null, HMBranchCode.get(0), pInfo.versionName);
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
            showCustomToast(Login.this, new clsHardCode().txtMessCancelRequest, false);
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
                        _tUserLoginData.set_txtSubmissionID((String) innerObj.get("TxtSubmissonId"));
                        _tUserLoginData.set_txtCheckLocation((String) innerObj.get("IntRadius"));

                        String TxtSubmissonId = (String) innerObj.get("TxtSubmissonId");
                        if(TxtSubmissonId.equals("")||TxtSubmissonId==null){
                            showCustomToast(Login.this, new clsHardCode().txtMessDataSubmissionIdNotFound, false);
                            Dialog.dismiss();
                            return;
                        }

                        new tDeviceInfoUserBL().SaveInfoDevice(_tUserLoginData.get_TxtEmpId(), _tUserLoginData.get_txtDeviceId());
                        new tUserLoginBL().saveData(_tUserLoginData);

//                        String nameOutlet = spnOutlet.getSelectedItem().toString();
//                        new mEmployeeAreaBL().DeleteEmployeeNotInId(HMOutletCode.get(nameOutlet));

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
//                        startService(new Intent(Login.this, MyServiceNative.class));
                        finish();
                        Intent myIntent = new Intent(Login.this, MainMenu.class);
                        myIntent.putExtra("keyMainMenu", "main_menu");
                        startActivity(myIntent);
                    }else{
                        showCustomToast(Login.this, PstrMessage, false);
                        txtLoginPassword.requestFocus();
                    }
                }

            }else{
                if(intProcesscancel==1){
                    onCancelled();
                }else{
                    showCustomToast(Login.this, new clsHardCode().txtMessDataNotFound, false);
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
//            android.os.Debug.waitForDebugger();
            List<mUserRoleData> roledata = new ArrayList<mUserRoleData>();
            try {
                //EditText txt = (EditText) findViewById(R.id.txtLoginEmail);
                roledata = new mUserRoleBL().getRoleAndOutlet(txtEmail1, pInfo.versionName, getApplicationContext());

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
            showCustomToast(Login.this, new clsHardCode().txtMessCancelRequest, false);
        }

        @Override
        protected void onPostExecute(List<mUserRoleData> roledata) {
            if (roledata.size() > 0) {
                arrrole = new ArrayList<String>();
                for (mUserRoleData dt : roledata) {
                    arrrole.add(dt.get_txtRoleName());
                    HMRole.put(dt.get_txtRoleName(), dt.get_intRoleId());
                }
                spnRole.setAdapter(new MyAdapter(Login.this, R.layout.custom_spinner, arrrole));
                spnRole.setEnabled(true);

//                List<mEmployeeAreaData> dataOutlet = new mEmployeeAreaBL().GetAllData();
//
//                arroutlet = new ArrayList<String>();
//                for (mEmployeeAreaData dtOutlet : dataOutlet) {
//                    arroutlet.add(dtOutlet.get_txtOutletName());
//                    HMOutletCode.put(dtOutlet.get_txtOutletName(), dtOutlet.get_txtOutletCode());
//                    HMOutletName.put(dtOutlet.get_txtOutletName(), dtOutlet.get_txtOutletName());
//                    HMBranchCode.put(dtOutlet.get_txtOutletName(), dtOutlet.get_txtBranchCode());
//                }
//
//                spnOutlet.setAdapter(new MyAdapter2(getApplicationContext(), R.layout.custom_spinner, arroutlet));
//                spnOutlet.setEnabled(true);
            } else if (roledata.size()==0){
                txtLoginEmail.requestFocus();
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    showCustomToast(Login.this, clsHardcode.txtMessNetworkOffline, false);

                    spnRole.setAdapter(null);
                    spnOutlet.setAdapter(null);
                    txtLoginEmail.requestFocus();
                }
                txtLoginEmail.requestFocus();
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
            showCustomToast(Login.this, new clsHardCode().txtMessCancelRequest, false);
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
                            //TAND.2016.003
                            //innerObj.get("TxtVersion").toString())
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
                showCustomToast(Login.this, clsHardcode.txtMessNetworkOffline, false);
            }
            Dialog.dismiss();
        }

        int intProcesscancel = 0;

        @Override
        protected void onPreExecute() {
            //Make ProgressBar invisible
            //pg.setVisibility(View.VISIBLE);
            Dialog.setMessage("Checking Your SPG Mobile Version");
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
                output = new FileOutputStream(txtPath + "kalbespgmobile.apk");

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
            }
            finally {
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
                showToast(context, "File downloaded");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String txtPath = new clsHardCode().txtPathUserData + "kalbespgmobile.apk";
                intent.setDataAndType(Uri.fromFile(new File(txtPath)), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (Build.VERSION.SDK_INT >= 24) {
                    intent.setDataAndType(FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", new File(txtPath)), "application/vnd.android.package-archive");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }else{
                    intent.setDataAndType(Uri.fromFile(new File(txtPath)), "application/vnd.android.package-archive");
                }
                //intent.setDataAndType(Uri.fromFile(new File(txtPath)), "application/vnd.android.package-archive");

                startActivity(intent);
            }
        }
    }

    private class AsyncCallReset extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            JSONArray Json=null;
            try {
                Json= new tUserLoginBL().resetPassword(String.valueOf(userName), pInfo.versionName);
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
            showCustomToast(Login.this, new clsHardCode().txtMessCancelRequest, false);
        }
        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata!=null&&roledata.size() > 0){
                Iterator i = roledata.iterator();
                while (i.hasNext()) {
                    JSONObject innerObj = (JSONObject) i.next();
                    Long IntResult=(Long) innerObj.get("_pboolValid");
                    String PstrMessage=(String) innerObj.get("_pstrMessage");

                    if(IntResult == 1){
                        showCustomToast(Login.this, PstrMessage, true);
                        alertDialog.dismiss();
                    }else{
                        showCustomToast(Login.this, "Failed"+PstrMessage, false);
                    }
                }
            }else{
                if(intProcesscancel==1){
                    onCancelled();
                }else{
                    showCustomToast(Login.this, new clsHardCode().txtMessUnableToConnect, false);
                }
            }
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            //Make ProgressBar invisible
            //pg.setVisibility(View.VISIBLE);
            Dialog.setMessage(new clsHardCode().txtMessReset);
            Dialog.setCancelable(false);
            Dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    intProcesscancel=1;
                }
            });
            Dialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Dialog.dismiss();
        }

    }
}
