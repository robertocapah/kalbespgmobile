package com.kalbenutritionals.app.kalbespgmobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

import bl.clsHelperBL;
import library.salesforce.common.clsPushData;
import library.salesforce.common.dataJson;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tActivityData;
import library.salesforce.common.tCustomerBasedMobileHeaderData;
import library.salesforce.common.tLeaveMobileData;
import library.salesforce.common.tSalesProductHeaderData;
import library.salesforce.dal.clsHardCode;

public class FragmentPushData extends Fragment {


    private TableLayout tlSOHeader;
    private TableLayout tlSODetail;
    private TableLayout tlActivity;
    private TableLayout tlCustomerBase;
    private TableLayout tlAbsen;
    private TableLayout tlLeave;
    private Button btnPush;

    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.activity_push_data, container, false);

        tlSOHeader = (TableLayout) v.findViewById(R.id.tlSOHeader);
        tlActivity = (TableLayout) v.findViewById(R.id.tlActivity);
        tlCustomerBase = (TableLayout) v.findViewById(R.id.tl_cb);
        tlAbsen = (TableLayout) v.findViewById(R.id.tl_absen);
        tlLeave = (TableLayout) v.findViewById(R.id.tl_leave);
        btnPush = (Button) v.findViewById(R.id.btnPush);

        btnPush.setTextColor(Color.parseColor("#FFFFFF"));

        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncCallRole task=new AsyncCallRole();
                task.execute();
            }
        });

        loadData();

        return  v;
    }

    private void loadData() {
        clsPushData dtclsPushData=new clsHelperBL().pushData();

        if(dtclsPushData!=null){
            dataJson dtJson =dtclsPushData.getDtdataJson();

            if(dtJson.getListOftSalesProductHeaderData()!=null){
                initSOHeader(getContext(),dtJson.getListOftSalesProductHeaderData());
            } else {
                initSOHeader(getContext(),null);
            }

            if(dtJson.getListOftActivityData()!=null){
                initActivity(getContext(),dtJson.getListOftActivityData());
            } else {
                initActivity(getContext(),null);
            }

            if(dtJson.get_ListOftCustomerBasedMobileHeaderData()!=null){
                initCustomerBase(getContext(),dtJson.get_ListOftCustomerBasedMobileHeaderData());
            } else {
                initCustomerBase(getContext(),null);
            }

            if(dtJson.getListOftAbsenUserData()!=null){
                inittAbsen(getContext(),dtJson.getListOftAbsenUserData());
            } else {
                inittAbsen(getContext(),null);
            }

            if(dtJson.getListOftLeaveMobileData()!=null){
                inittLeave(getContext(),dtJson.getListOftLeaveMobileData());
            } else {
                inittLeave(getContext(),null);
            }

        }
    }

    private void inittLeave(Context context, List<tLeaveMobileData> listOftLeaveMobileData) {
        tlLeave = (TableLayout) v.findViewById(R.id.tl_leave);

        tlLeave.removeAllViews();

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        params.setMargins(1, 1, 1, 1);

        TableRow tr = new TableRow(getContext());

        TableLayout tl = new TableLayout(getContext());

        String[] colTextHeader = {"No.", "Reason", "Type", "Date"};

        for (String text : colTextHeader) {
            TextView tv = new TextView(getContext());
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

            tv.setTextSize(14);
            tv.setPadding(10, 10, 10, 10);
            tv.setText(text);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundColor(Color.parseColor("#4CAF50"));

            tv.setTextColor(Color.WHITE);
            tr.addView(tv,params);
        }
        tl.addView(tr);

        if(listOftLeaveMobileData!=null){
            int index = 1;
            for(tLeaveMobileData dat : listOftLeaveMobileData){
                tr = new TableRow(getContext());
                TableLayout.LayoutParams tableRowParams=
                        new TableLayout.LayoutParams
                                (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

                int leftMargin=0;
                int topMargin=0;
                int rightMargin=0;
                int bottomMargin=0;
                tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

                tr.setLayoutParams(tableRowParams);

                TextView tv_index = new TextView(getContext());
                tv_index.setTextSize(12);
                tv_index.setPadding(10, 10, 10, 10);
                tv_index.setBackgroundColor(Color.parseColor("#f0f0f0"));
                tv_index.setTextColor(Color.BLACK);
                tv_index.setText(String.valueOf(index + "."));
                tr.addView(tv_index,params);

                TextView outlet_code = new TextView(getContext());
                outlet_code.setTextSize(12);
                outlet_code.setPadding(10, 10, 10, 10);
                outlet_code.setBackgroundColor(Color.parseColor("#f0f0f0"));
                outlet_code.setTextColor(Color.BLACK);
                outlet_code.setGravity(Gravity.CENTER);
                outlet_code.setText(dat.get_txtAlasan());
                tr.addView(outlet_code,params);

                TextView outlet_name = new TextView(getContext());
                outlet_name.setTextSize(12);
                outlet_name.setPadding(10, 10, 10, 10);
                outlet_name.setBackgroundColor(Color.parseColor("#f0f0f0"));
                outlet_name.setTextColor(Color.BLACK);
                outlet_name.setGravity(Gravity.CENTER);
                outlet_name.setText(dat.get_txtTypeAlasanName());
                tr.addView(outlet_name,params);

                TextView date = new TextView(getContext());
                date.setTextSize(12);
                date.setPadding(10, 10, 10, 10);
                date.setBackgroundColor(Color.parseColor("#f0f0f0"));
                date.setTextColor(Color.BLACK);
                date.setGravity(Gravity.CENTER);
                date.setText(new clsMainActivity().giveFormatDate2(dat.get_dtLeave()));
                tr.addView(date,params);

                tl.addView(tr, tableRowParams);
                index ++;
            }
        }

        tlLeave.addView(tl);
    }

    private void initCustomerBase(Context context, List<tCustomerBasedMobileHeaderData> listOftCustomerBasedMobileHeaderData) {

        tlCustomerBase.removeAllViews();

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        params.setMargins(1, 1, 1, 1);

        TableRow tr = new TableRow(getContext());

        TableLayout tl = new TableLayout(getContext());

        String[] colTextHeader = {"No.", "Code", "Date", "Branch Code"};

        for (String text : colTextHeader) {
            TextView tv = new TextView(getContext());
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

            tv.setTextSize(14);
            tv.setPadding(10, 10, 10, 10);
            tv.setText(text);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundColor(Color.parseColor("#4CAF50"));

            tv.setTextColor(Color.WHITE);
            tr.addView(tv,params);
        }
        tl.addView(tr);

        if(listOftCustomerBasedMobileHeaderData!=null){
            int index = 1;
            for(tCustomerBasedMobileHeaderData dat : listOftCustomerBasedMobileHeaderData){
                tr = new TableRow(getContext());
                TableLayout.LayoutParams tableRowParams=
                        new TableLayout.LayoutParams
                                (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

                int leftMargin=0;
                int topMargin=0;
                int rightMargin=0;
                int bottomMargin=0;
                tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

                tr.setLayoutParams(tableRowParams);

                TextView tv_index = new TextView(getContext());
                tv_index.setTextSize(12);
                tv_index.setPadding(10, 10, 10, 10);
                tv_index.setBackgroundColor(Color.parseColor("#f0f0f0"));
                tv_index.setTextColor(Color.BLACK);
                tv_index.setText(String.valueOf(index + "."));
                tr.addView(tv_index,params);

                TextView outlet_code = new TextView(getContext());
                outlet_code.setTextSize(12);
                outlet_code.setPadding(10, 10, 10, 10);
                outlet_code.setBackgroundColor(Color.parseColor("#f0f0f0"));
                outlet_code.setTextColor(Color.BLACK);
                outlet_code.setGravity(Gravity.CENTER);
                outlet_code.setText(dat.get_txtSubmissionId());
                tr.addView(outlet_code,params);

                TextView outlet_name = new TextView(getContext());
                outlet_name.setTextSize(12);
                outlet_name.setPadding(10, 10, 10, 10);
                outlet_name.setBackgroundColor(Color.parseColor("#f0f0f0"));
                outlet_name.setTextColor(Color.BLACK);
                outlet_name.setGravity(Gravity.CENTER);
                outlet_name.setText(new clsMainActivity().giveFormatDate(dat.get_dtDate()));
                tr.addView(outlet_name,params);

                TextView date = new TextView(getContext());
                date.setTextSize(12);
                date.setPadding(10, 10, 10, 10);
                date.setBackgroundColor(Color.parseColor("#f0f0f0"));
                date.setTextColor(Color.BLACK);
                date.setGravity(Gravity.CENTER);
                date.setText(dat.get_txtBranchCode());
                tr.addView(date,params);

                tl.addView(tr, tableRowParams);
                index ++;
            }
        }

        tlCustomerBase.addView(tl);
    }

    private void initActivity(Context context, List<tActivityData> listOftActivityData) {

        tlActivity.removeAllViews();

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        params.setMargins(1, 1, 1, 1);

        TableRow tr = new TableRow(getContext());

        TableLayout tl = new TableLayout(getContext());

        String[] colTextHeader = {"No.", "Desc.", "Date", "Outlet Code"};

        for (String text : colTextHeader) {
            TextView tv = new TextView(getContext());
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

            tv.setTextSize(14);
            tv.setPadding(10, 10, 10, 10);
            tv.setText(text);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundColor(Color.parseColor("#4CAF50"));

            tv.setTextColor(Color.WHITE);
            tr.addView(tv,params);
        }
        tl.addView(tr);

        if(listOftActivityData!=null){
            int index = 1;
            for(tActivityData dat : listOftActivityData){
                tr = new TableRow(getContext());
                TableLayout.LayoutParams tableRowParams=
                        new TableLayout.LayoutParams
                                (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

                int leftMargin=0;
                int topMargin=0;
                int rightMargin=0;
                int bottomMargin=0;
                tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

                tr.setLayoutParams(tableRowParams);

                TextView tv_index = new TextView(getContext());
                tv_index.setTextSize(12);
                tv_index.setPadding(10, 10, 10, 10);
                tv_index.setBackgroundColor(Color.parseColor("#f0f0f0"));
                tv_index.setTextColor(Color.BLACK);
                tv_index.setText(String.valueOf(index + "."));
                tr.addView(tv_index,params);

                TextView outlet_code = new TextView(getContext());
                outlet_code.setTextSize(12);
                outlet_code.setPadding(10, 10, 10, 10);
                outlet_code.setBackgroundColor(Color.parseColor("#f0f0f0"));
                outlet_code.setTextColor(Color.BLACK);
                outlet_code.setGravity(Gravity.CENTER);
                outlet_code.setText(dat.get_txtDesc());
                tr.addView(outlet_code,params);

                TextView outlet_name = new TextView(getContext());
                outlet_name.setTextSize(12);
                outlet_name.setPadding(10, 10, 10, 10);
                outlet_name.setBackgroundColor(Color.parseColor("#f0f0f0"));
                outlet_name.setTextColor(Color.BLACK);
                outlet_name.setGravity(Gravity.CENTER);
                outlet_name.setText(new clsMainActivity().giveFormatDate(dat.get_dtActivity()));
                tr.addView(outlet_name,params);

                TextView date = new TextView(getContext());
                date.setTextSize(12);
                date.setPadding(10, 10, 10, 10);
                date.setBackgroundColor(Color.parseColor("#f0f0f0"));
                date.setTextColor(Color.BLACK);
                date.setGravity(Gravity.CENTER);
                date.setText(dat.get_txtOutletCode());
                tr.addView(date,params);

                tl.addView(tr, tableRowParams);
                index ++;
            }
        }

        tlActivity.addView(tl);
    }

    private void initSOHeader(Context context, List<tSalesProductHeaderData> listOftSalesProductHeaderData) {
        tlSOHeader = (TableLayout) v.findViewById(R.id.tlSOHeader);
        tlSOHeader.removeAllViews();

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        params.setMargins(1, 1, 1, 1);

        TableRow tr = new TableRow(getContext());

        TableLayout tl = new TableLayout(getContext());

        String[] colTextHeader = {"No.", "NO SO", "Date", "Outlet Code"};

        for (String text : colTextHeader) {
            TextView tv = new TextView(getContext());
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

            tv.setTextSize(14);
            tv.setPadding(10, 10, 10, 10);
            tv.setText(text);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundColor(Color.parseColor("#4CAF50"));

            tv.setTextColor(Color.WHITE);
            tr.addView(tv,params);
        }
        tl.addView(tr);

        if(listOftSalesProductHeaderData!=null){
            int index = 1;
            for(tSalesProductHeaderData dat : listOftSalesProductHeaderData){
                tr = new TableRow(getContext());
                TableLayout.LayoutParams tableRowParams=
                        new TableLayout.LayoutParams
                                (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

                int leftMargin=0;
                int topMargin=0;
                int rightMargin=0;
                int bottomMargin=0;
                tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

                tr.setLayoutParams(tableRowParams);

                TextView tv_index = new TextView(getContext());
                tv_index.setTextSize(12);
                tv_index.setPadding(10, 10, 10, 10);
                tv_index.setBackgroundColor(Color.parseColor("#f0f0f0"));
                tv_index.setTextColor(Color.BLACK);
                tv_index.setText(String.valueOf(index + "."));
                tr.addView(tv_index,params);

                TextView outlet_code = new TextView(getContext());
                outlet_code.setTextSize(12);
                outlet_code.setPadding(10, 10, 10, 10);
                outlet_code.setBackgroundColor(Color.parseColor("#f0f0f0"));
                outlet_code.setTextColor(Color.BLACK);
                outlet_code.setGravity(Gravity.CENTER);
                outlet_code.setText(dat.get_intId());
                tr.addView(outlet_code,params);

                TextView outlet_name = new TextView(getContext());
                outlet_name.setTextSize(12);
                outlet_name.setPadding(10, 10, 10, 10);
                outlet_name.setBackgroundColor(Color.parseColor("#f0f0f0"));
                outlet_name.setTextColor(Color.BLACK);
                outlet_name.setGravity(Gravity.CENTER);
                outlet_name.setText(dat.get_dtDate());
                tr.addView(outlet_name,params);

                TextView date = new TextView(getContext());
                date.setTextSize(12);
                date.setPadding(10, 10, 10, 10);
                date.setBackgroundColor(Color.parseColor("#f0f0f0"));
                date.setTextColor(Color.BLACK);
                date.setGravity(Gravity.CENTER);
                date.setText(new clsMainActivity().giveFormatDate(dat.get_dtDate()));
                tr.addView(date,params);

                tl.addView(tr, tableRowParams);
                index ++;
            }
        }

        tlSOHeader.addView(tl);

    }

    private class AsyncCallRole extends AsyncTask<List<dataJson>, Void, List<dataJson>> {
        @Override
        protected List<dataJson> doInBackground(List<dataJson>... params) {
//            android.os.Debug.waitForDebugger();
            List<dataJson> roledata=new ArrayList<dataJson>();
            clsPushData dtJson= new clsHelperBL().pushData();
            dataJson dtdataJson=new dataJson();
            if(dtJson!=null){
                String versionName="";
                try {
                    versionName = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionName;
//                    versionName = new clsMainActivity().getApplicationContext().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
                } catch (NameNotFoundException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
                try {
                    JSONArray Jresult= new clsHelperBL().callPushDataReturnJson(versionName,dtJson.getDtdataJson().txtJSON().toString(),dtJson.getFileUpload());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                dtdataJson.setIntResult("1");

            }
            else
            {
                dtdataJson.setIntResult("0");
                dtdataJson.setTxtMessage("No Data");
            }
            roledata.add(dtdataJson);
            return roledata ;
        }

        private ProgressDialog Dialog = new ProgressDialog(getContext());
        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(getContext(),
                    new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }
        @Override
        protected void onPostExecute(List<dataJson> roledata) {
            if(roledata.get(0).getIntResult().equals("1")){
                new clsMainActivity().showToast(getContext(), "Success Push Data");
            }else{
                new clsMainActivity().showToast(getContext(), roledata.get(0).getTxtMessage());
            }
            Dialog.dismiss();
        }
        int intProcesscancel=0;
        @Override
        protected void onPreExecute() {
            //Make ProgressBar invisible
            //pg.setVisibility(View.VISIBLE);
            Dialog.setMessage("Syncronize Data!!");
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


    public void inittAbsen(Context _ctx,List<tAbsenUserData> ListData){
        tlAbsen = (TableLayout) v.findViewById(R.id.tl_absen);
        tlAbsen.removeAllViews();

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        params.setMargins(1, 1, 1, 1);

        TableRow tr = new TableRow(getContext());

        TableLayout tl = new TableLayout(getContext());

        String[] colTextHeader = {"No.", "Outlet Code", "Outlet Name", "Date"};

        for (String text : colTextHeader) {
            TextView tv = new TextView(getContext());
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

            tv.setTextSize(14);
            tv.setPadding(10, 10, 10, 10);
            tv.setText(text);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundColor(Color.parseColor("#4CAF50"));

            tv.setTextColor(Color.WHITE);
            tr.addView(tv,params);
        }
        tl.addView(tr);

        if(ListData!=null){
            int index = 1;
            for(tAbsenUserData dat : ListData){
                tr = new TableRow(getContext());
                TableLayout.LayoutParams tableRowParams=
                        new TableLayout.LayoutParams
                                (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

                int leftMargin=0;
                int topMargin=0;
                int rightMargin=0;
                int bottomMargin=0;
                tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

                tr.setLayoutParams(tableRowParams);

                TextView tv_index = new TextView(getContext());
                tv_index.setTextSize(12);
                tv_index.setPadding(10, 10, 10, 10);
                tv_index.setBackgroundColor(Color.parseColor("#f0f0f0"));
                tv_index.setTextColor(Color.BLACK);
                tv_index.setText(String.valueOf(index + "."));
                tr.addView(tv_index,params);

                TextView outlet_code = new TextView(getContext());
                outlet_code.setTextSize(12);
                outlet_code.setPadding(10, 10, 10, 10);
                outlet_code.setBackgroundColor(Color.parseColor("#f0f0f0"));
                outlet_code.setTextColor(Color.BLACK);
                outlet_code.setGravity(Gravity.CENTER);
                outlet_code.setText(dat.get_txtOutletCode());
                tr.addView(outlet_code,params);

                TextView outlet_name = new TextView(getContext());
                outlet_name.setTextSize(12);
                outlet_name.setPadding(10, 10, 10, 10);
                outlet_name.setBackgroundColor(Color.parseColor("#f0f0f0"));
                outlet_name.setTextColor(Color.BLACK);
                outlet_name.setGravity(Gravity.CENTER);
                outlet_name.setText(dat.get_txtOutletName());
                tr.addView(outlet_name,params);

                TextView date = new TextView(getContext());
                date.setTextSize(12);
                date.setPadding(10, 10, 10, 10);
                date.setBackgroundColor(Color.parseColor("#f0f0f0"));
                date.setTextColor(Color.BLACK);
                date.setGravity(Gravity.CENTER);
                date.setText(new clsMainActivity().giveFormatDate2(dat.get_dtDateCheckIn()));
                tr.addView(date,params);

                tl.addView(tr, tableRowParams);
                index ++;
            }
        }

        tlAbsen.addView(tl);
    }
}
