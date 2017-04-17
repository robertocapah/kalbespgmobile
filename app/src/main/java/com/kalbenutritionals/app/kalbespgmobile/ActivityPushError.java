package com.kalbenutritionals.app.kalbespgmobile;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import bl.clsHelperBL;
import library.salesforce.common.APIData;
import library.salesforce.common.clsPushData;
import library.salesforce.common.dataJson;
import library.salesforce.common.tErrorLogData;
import library.salesforce.dal.clsHardCode;

/**
 * Created by Robert on 13/04/2017.
 */

public class ActivityPushError extends clsMainActivity {
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_push_error);

        Button btnPush = (Button) findViewById(R.id.btn_push_err);
        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncCallRole asyncCallRole = new AsyncCallRole();
                asyncCallRole.execute();
            }
        });

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class AsyncCallRole extends AsyncTask<List<tErrorLogData>, Void, List<tErrorLogData>> {

        @Override
        protected List<tErrorLogData> doInBackground(List<tErrorLogData>... params) {
            List<dataJson> roledata = new ArrayList<dataJson>();
            clsPushData dtJson = new clsHelperBL().pushData();
            dataJson dtdataJson = new dataJson();
//            if(dtJson!=null){
            String versionName = "";
            try {
                versionName = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
//                    versionName = new clsMainActivity().getApplicationContext().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
            try {
                HashMap<String, String> FileUpload = new HashMap<String, String>();
                clsHardCode _path = new clsHardCode();
                FileUpload.put("FULog-1", _path.txtPathApp + "log_17-04-2017.txt");

                JSONArray Jresult = new clsHelperBL().callPushErrorReturnJson(versionName, null, FileUpload);
//                    JSONArray Jresult = null;
                new clsHelperBL().saveDataPush(dtJson.getDtdataJson(), Jresult);

                APIData dtAPIDATA = new APIData();
                Iterator i = Jresult.iterator();
                boolean validPush = false;
                while (i.hasNext()) {
                    org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
                    int boolValid = Integer.valueOf(String.valueOf(innerObj.get(dtAPIDATA.boolValid)));
                    if (boolValid == Integer.valueOf(new clsHardCode().intSuccess)) {
                        validPush = true;
                    } else {
                        validPush = false;
                        break;
                    }
                }

                dtdataJson.setIntResult("1");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                dtdataJson.setIntResult("0");
                dtdataJson.setTxtMessage("Please Check Your Connection !!!");
            }
//            }
//            else
//            {
//                dtdataJson.setIntResult("0");
//                dtdataJson.setTxtMessage("No Data");
//            }
            roledata.add(dtdataJson);
            return null;
//            return null;
        }

        private ProgressDialog Dialog = new ProgressDialog(ActivityPushError.this);

        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            showCustomToast(ActivityPushError.this, new clsHardCode().txtMessCancelRequest, false);
        }

    }
}

