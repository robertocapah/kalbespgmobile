package com.kalbenutritionals.app.kalbespgmobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bl.mEmployeeAreaBL;
import bl.mEmployeeBranchBL;
import bl.mEmployeeSalesProductBL;
import bl.mPriceInOutletBL;
import bl.mProductBarcodeBL;
import bl.mProductBrandHeaderBL;
import bl.mTypeLeaveBL;
import library.salesforce.common.APIData;
import library.salesforce.common.dataJson;
import library.salesforce.common.mEmployeeAreaData;
import library.salesforce.common.mEmployeeBranchData;
import library.salesforce.common.mEmployeeSalesProductData;
import library.salesforce.common.mProductBarcodeData;
import library.salesforce.common.mProductBrandHeaderData;
import library.salesforce.common.mTypeLeaveMobileData;
import library.salesforce.dal.clsHardCode;

/**
 * Created by ASUS ZE on 27/07/2016.
 */
public class FragmentDownloadData extends Fragment {
    View v;

    private Button btnBranch;
    private Spinner spnBranch;
    private Button btnOutlet;
    private Spinner spnOutlet;
    private Button btnProduct;
    private Spinner spnProduct;
    private Button btnAllDownload;
    private Spinner spnLeave;
    private Button btnLeave;
    private Spinner spnBrand;
    private Button btnBrand;

    private PackageInfo pInfo = null;
    private List<String> arrData;
    private String[] strip = new String[]{"-"};
    private String MenuID;
    int intProcesscancel = 0;

    clsMainActivity _clsMainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_download_data,container,false);
        btnAllDownload = (Button) v.findViewById(R.id.btnAllDownload);
        btnBranch = (Button) v.findViewById(R.id.btnBranch);
        spnBranch = (Spinner) v.findViewById(R.id.spnType);
        btnOutlet = (Button) v.findViewById(R.id.btnOutlet);
        spnOutlet = (Spinner) v.findViewById(R.id.spnOutlet);
        btnProduct = (Button) v.findViewById(R.id.btnProduct);
        spnProduct = (Spinner) v.findViewById(R.id.spnProduct);
        btnLeave = (Button) v.findViewById(R.id.btnLeave);
        spnLeave = (Spinner) v.findViewById(R.id.spnLeave);
        spnBrand = (Spinner) v.findViewById(R.id.spnBrand);
        btnBrand = (Button) v.findViewById(R.id.btnDlBrand);

        loadData();
        btnAllDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                intProcesscancel = 0;
                AsyncCallDownloadAll task = new AsyncCallDownloadAll();
                task.execute();
            }
        });
        btnLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intProcesscancel = 0;
                AsyncCallLeave task = new AsyncCallLeave();
                task.execute();
            }
        });
        btnBranch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                intProcesscancel = 0;
                AsyncCallBranch task = new AsyncCallBranch();
                task.execute();
            }
        });
        btnOutlet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                intProcesscancel = 0;
                AsyncCallOutlet task = new AsyncCallOutlet();
                task.execute();
            }
        });
        btnProduct.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                intProcesscancel = 0;
                AsyncCallProduct task = new AsyncCallProduct();
                task.execute();
            }
        });
        btnBrand.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                intProcesscancel = 0;
                AsyncCallProductBrand task = new AsyncCallProductBrand();
                task.execute();
            }
        });


        return v;
    }
    private void loadData(){
        _clsMainActivity = new clsMainActivity();
        try {
            pInfo=getContext().getPackageManager().getPackageInfo(getActivity().getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        List<mEmployeeBranchData> listDataBranch=new mEmployeeBranchBL().GetAllData();
        List<mEmployeeAreaData> listDataArea=new mEmployeeAreaBL().GetAllData();
        List<mEmployeeSalesProductData> listDataProduct=new mEmployeeSalesProductBL().GetAllData();
        List<mTypeLeaveMobileData> listDataLeave=new mTypeLeaveBL().GetAllData();
        List<mProductBrandHeaderData> listmProductBrandData = new mProductBrandHeaderBL().getData("");

        arrData=new ArrayList<String>();
        if(listDataBranch.size()>0){
            for(mEmployeeBranchData dt :listDataBranch ){
                arrData.add(dt.get_txtBranchCode()+" - "+ dt.get_txtBranchName());
            }
            spnBranch.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));
            spnBranch.setEnabled(true);
        } else if (listDataBranch.size()==0){
            ArrayAdapter<String> adapterspn = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, strip);
            spnBranch.setAdapter(adapterspn);
            spnBranch.setEnabled(false);
        }
        arrData=new ArrayList<String>();
        if(listDataLeave.size()>0){
            for(mTypeLeaveMobileData dt :listDataLeave ){
                arrData.add(dt.get_intTipeLeave()+" - "+ dt.get_txtTipeLeaveName());
            }
            spnLeave.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));
            spnLeave.setEnabled(true);
        }else if (listDataLeave.size()==0){
            ArrayAdapter<String> adapterspn = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, strip);
            spnLeave.setAdapter(adapterspn);
            spnLeave.setEnabled(false);
        }
        arrData=new ArrayList<String>();
        if(listDataArea.size()>0){
            for(mEmployeeAreaData dt :listDataArea ){
                arrData.add(dt.get_txtOutletCode()+" - "+dt.get_txtOutletName());
            }
            spnOutlet.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));
            spnOutlet.setEnabled(true);
        } else if (listDataArea.size()==0){
            ArrayAdapter<String> adapterspn = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, strip);
            spnOutlet.setAdapter(adapterspn);
            spnOutlet.setEnabled(false);
        }
        arrData=new ArrayList<String>();
        if(listDataProduct.size()>0){
            for(mEmployeeSalesProductData dt :listDataProduct ){
                arrData.add(dt.get_txtProductBrandDetailGramName());
            }
            spnProduct.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));
            spnProduct.setEnabled(true);
        } else if (listDataProduct.size()==0){
            ArrayAdapter<String> adapterspn = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, strip);
            spnProduct.setAdapter(adapterspn);
            spnProduct.setEnabled(false);
        }
        arrData=new ArrayList<String>();
        if(listmProductBrandData.size()>0){
            for(mProductBrandHeaderData dt :listmProductBrandData ){
                arrData.add(dt.get_txtProductBrandName());
            }
            spnBrand.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));
            spnBrand.setEnabled(true);
        } else if (listmProductBrandData.size()==0){
            ArrayAdapter<String> adapterspn = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, strip);
            spnBrand.setAdapter(adapterspn);
            spnBrand.setEnabled(false);
        }
    }
    public class MyAdapter extends ArrayAdapter<String>
    {
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
        public MyAdapter(Context context, int textViewResourceId, List<String> objects)
        {
            super(context, textViewResourceId, objects);
            setCtx(context);
            setArrayDataAdapyter(objects);
            // TODO Auto-generated constructor stub
        }
        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent)
        {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater=getActivity().getLayoutInflater();
            View row=inflater.inflate(R.layout.custom_spinner, parent, false);
            if(getArrayDataAdapyter().size()>0){
                TextView label=(TextView)row.findViewById(R.id.tvTitle);
                //label.setText(arrData.get(position));
                label.setText(getArrayDataAdapyter().get(position));
                label.setTextColor(new Color().parseColor("#000000"));
                TextView sub=(TextView)row.findViewById(R.id.tvDesc);
                sub.setVisibility(View.INVISIBLE);
                sub.setVisibility(View.GONE);
                row.setBackgroundColor(new Color().parseColor("#FFFFFF"));
            }
            //sub.setText(mydata2[position]);
            return row;
        }

    }
    private class AsyncCallDownloadAll extends AsyncTask<JSONArray, Void, List<dataJson>> {
        @Override
        protected List<dataJson> doInBackground(JSONArray... params) {
            //android.os.Debug.waitForDebugger();
            JSONArray Json = null;
            List<dataJson> listDataJson=new ArrayList<dataJson>();
            dataJson dtdataJson=new dataJson();
            JSONParser parser = new JSONParser();
            try {
                new mPriceInOutletBL().DownloadmPriceInOutlet(pInfo.versionName);
                Json = new mEmployeeBranchBL().DownloadEmployeeBranch2(pInfo.versionName);
                SaveDatamEmployeeBranchData(Json);
                Json =new mTypeLeaveBL().DownloadTypeLeave2(pInfo.versionName);
                SaveDatamTypeLeaveMobileData(Json);
                Json = new mEmployeeSalesProductBL().DownloadEmployeeSalesProduct(pInfo.versionName);
                SaveDatamProductBarcodeData(Json);
                Json = new mProductBrandHeaderBL().DownloadBrandHeader(pInfo.versionName);
                SaveDatamProductBarcodeData(Json);
                Json = new mEmployeeAreaBL().DownloadEmployeeArea2(pInfo.versionName);
                SaveDatamEmployeeAreaData(Json);
                dtdataJson.setIntResult("1");
            } catch (Exception e) {
                dtdataJson.setIntResult("0");
                //dtdataJson.setTxtMessage(e.getMessage().toString());
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            listDataJson.add(dtdataJson);
            return listDataJson;
        }
        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(getContext(), new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }
        private ProgressDialog Dialog = new ProgressDialog(getContext());
        @Override
        protected void onPostExecute(List<dataJson> listdataJson) {
            String txtMess=new clsHardCode().txtMessSuccessDownload;
            if(listdataJson.get(0).getIntResult().equals("0")){
                //txtMess=listdataJson.get(0).getTxtMessage();
                new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessUnableToConnect, false);
                Dialog.dismiss();

            }
            else{
                loadData();
                new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessSuccessDownload, true);
                Dialog.dismiss();
//                getActivity().finish();
                checkingDataTable();
//                Intent nextScreen = new Intent(getContext(), MainMenu.class);
//                nextScreen.putExtra(clsParameterPutExtra.MenuID, MenuID);
//                startActivity(nextScreen);
            }
//            Toast toast = Toast.makeText(getContext(), txtMess, Toast.LENGTH_LONG);
//            toast.setGravity(Gravity.TOP, 25, 400);
//            toast.show();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage(new clsHardCode().txtMessGetAllData);
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
    private List<String> SaveDatamEmployeeBranchData(JSONArray JData){
        List<String> _array=new ArrayList<String>();
        APIData dtAPIDATA = new APIData();
        _array= new ArrayList<String>();
        Iterator i = JData.iterator();
        Boolean flag = true;
        String ErrorMess = "";
        List<mEmployeeBranchData> _Listdata = new ArrayList<mEmployeeBranchData>();
        while (i.hasNext()) {
            org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
            int boolValid = Integer.valueOf(String.valueOf(innerObj.get(dtAPIDATA.boolValid)));
            if (boolValid == Integer.valueOf(new clsHardCode().intSuccess)) {
                mEmployeeBranchData _data = new mEmployeeBranchData();

                _data.set_EmpId((String) innerObj.get("EmpId"));
                _data.set_txtBranchCode((String) innerObj.get("IntBranchId"));
                _data.set_intID((String) innerObj.get("IntID"));
                _data.set_txtBranchCode((String) innerObj.get("TxtBranchCode"));
                _data.set_txtBranchName((String) innerObj.get("TxtBranchName"));
                _data.set_txtNIK((String) innerObj.get("TxtNIK"));
                _data.set_txtName((String) innerObj.get("TxtName"));
                _array.add(_data.get_txtBranchCode()+" - "+_data.get_txtBranchName());
                _Listdata.add(_data);
            } else {
                flag = false;
                ErrorMess = (String) innerObj.get(dtAPIDATA.strMessage);
                break;
            }
        }
        new mEmployeeBranchBL().saveData(_Listdata);
        return _array;
    }
    private List<String> SaveDatamProductBarcodeData(JSONArray JData){
        List<String> _array=new ArrayList<String>();
        APIData dtAPIDATA = new APIData();
        _array= new ArrayList<String>();
        Iterator i = JData.iterator();
        Boolean flag = true;
        String ErrorMess = "";
        List<mProductBarcodeData> _Listdata = new ArrayList<mProductBarcodeData>();
        while (i.hasNext()) {
            org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
            int boolValid = Integer.valueOf(String.valueOf(innerObj.get(dtAPIDATA.boolValid)));
            if (boolValid == Integer.valueOf(new clsHardCode().intSuccess)) {
                mProductBarcodeData _data = new mProductBarcodeData();

                _data.set_intProductCode(String.valueOf(innerObj.get("_intProductCode")));
                _data.set_txtProductCode((String) innerObj.get("_txtProductCode"));
                _data.set_txtBarcode((String) innerObj.get("_txtBarcode"));
                _data.set_txtProductName((String) innerObj.get("_txtProductName"));
                _data.set_intSubmit("1");
                _data.set_intSync("0");
                _array.add(_data.get_txtProductCode() +" - "+_data.get_txtProductName());
                _Listdata.add(_data);
            } else {
                flag = false;
                ErrorMess = (String) innerObj.get(dtAPIDATA.strMessage);
                break;
            }
        }
        new mProductBarcodeBL().saveData(_Listdata);
        return _array;
    }
    private class AsyncCallProduct extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            JSONArray Json = null;
            try {
//                Json = new mProductBarcodeBL().DownloadmProductBarcode2(pInfo.versionName);
                Json = new mEmployeeSalesProductBL().DownloadEmployeeSalesProduct(pInfo.versionName);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Json;
        }
        private ProgressDialog Dialog = new ProgressDialog(getContext());
        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(getContext(), new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }

        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata!=null&&roledata.size() > 0) {
                loadData();
                Toast toast = Toast.makeText(getContext(), new clsHardCode().txtMessSuccessDownload,
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    Toast toast = Toast.makeText(getContext(), new clsHardCode().txtMessDataNotFound,
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                }

            }
            checkingDataTable();
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage(new clsHardCode().txtMessGetProduct);
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
    private class AsyncCallProductBrand extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            JSONArray Json = null;
            try {
                Json = new mProductBrandHeaderBL().DownloadBrandHeader(pInfo.versionName);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Json;
        }
        private ProgressDialog Dialog = new ProgressDialog(getContext());
        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(getContext(), new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }

        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata!=null&&roledata.size() > 0) {
                loadData();
                Toast toast = Toast.makeText(getContext(), new clsHardCode().txtMessSuccessDownload,
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    Toast toast = Toast.makeText(getContext(), new clsHardCode().txtMessDataNotFound,
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                }

            }
            checkingDataTable();
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage(new clsHardCode().txtMessGetProduct);
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
    private List<String> SaveDatamEmployeeAreaData(JSONArray JData){
        List<String> _array=new ArrayList<String>();
        APIData dtAPIDATA = new APIData();
        _array= new ArrayList<String>();
        Iterator i = JData.iterator();
        Boolean flag = true;
        String ErrorMess = "";
        List<mEmployeeAreaData> _Listdata = new ArrayList<mEmployeeAreaData>();
        while (i.hasNext()) {
            org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
            int boolValid = Integer.valueOf(String.valueOf(innerObj.get(dtAPIDATA.boolValid)));
            if (boolValid == Integer.valueOf(new clsHardCode().intSuccess)) {
                String latitude = (String) innerObj.get("txtLatitude");
                String longitude = (String) innerObj.get("txtLongitude");
                if((latitude.equals("")||latitude==null
                        ||longitude.equals("")||longitude==null)){

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast toast = Toast.makeText(getContext(), "Location Outlet Can't be Found...",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 25, 400);
                            toast.show();
                        }
                    });


                }
                mEmployeeAreaData _data = new mEmployeeAreaData();
                _data.set_intBranchId((String) innerObj.get("IntBranchId"));
                _data.set_intChannelId((String) innerObj.get("IntChannelId"));
                _data.set_intEmployeeId((String) innerObj.get("IntEmployeeId"));
                _data.set_intID((String) innerObj.get("IntID"));
                _data.set_intOutletId((String) innerObj.get("IntOutletId"));
                _data.set_intChannelId((String) innerObj.get("IntChannelId"));
                _data.set_intRayonId((String) innerObj.get("IntRayonId"));
                _data.set_intRegionId((String) innerObj.get("IntRegionId"));
                _data.set_txtBranchCode((String) innerObj.get("TxtBranchCode"));
                _data.set_txtBranchName((String) innerObj.get("TxtBranchName"));
                _data.set_txtNIK((String) innerObj.get("TxtNIK"));
                _data.set_txtName((String) innerObj.get("TxtName"));
                _data.set_txtOutletCode((String) innerObj.get("TxtOutletCode"));
                _data.set_txtOutletName((String) innerObj.get("TxtOutletName"));
                _data.set_txtRayonCode((String) innerObj.get("TxtRayonCode"));
                _data.set_txtRayonName((String) innerObj.get("TxtRayonName"));
                _data.set_txtRegionName((String) innerObj.get("TxtRegionName"));
//                _data.set_txtLatitude((String) innerObj.get("txtLatitude"));
//                _data.set_txtLongitude((String) innerObj.get("txtLongitude"));

                //hardcode cui..
                _data.set_txtLatitude("-6.150721");
                _data.set_txtLongitude("106.887543");

                _array.add(_data.get_txtOutletCode() +" - "+ _data.get_txtOutletName());
                _Listdata.add(_data);
            } else {
                flag = false;
                ErrorMess = (String) innerObj.get(dtAPIDATA.strMessage);
                break;
            }
        }
        new mEmployeeAreaBL().saveData(_Listdata);
        return _array;
    }
    private class AsyncCallOutlet extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            //android.os.Debug.waitForDebugger();
            JSONArray Json = null;
            try {
                Json = new mEmployeeAreaBL().DownloadEmployeeArea2(pInfo.versionName);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Json;
        }
        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(getContext(), new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }
        private ProgressDialog Dialog = new ProgressDialog(getContext());
        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata!=null&&roledata.size() > 0) {
                arrData=SaveDatamEmployeeAreaData(roledata);
                loadData();
                //spnOutlet.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                Toast toast = Toast.makeText(getContext(), new clsHardCode().txtMessSuccessDownload,
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    Toast toast = Toast.makeText(getContext(), new clsHardCode().txtMessDataNotFound,
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                }

            }
            checkingDataTable();
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage(new clsHardCode().txtMessGetOutlet);
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
    private class AsyncCallBranch extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            //android.os.Debug.waitForDebugger();
            JSONArray Json = null;
            try {
                Json = new mEmployeeBranchBL().DownloadEmployeeBranch2(pInfo.versionName);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Json;
        }
        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(getContext(), new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }
        private ProgressDialog Dialog = new ProgressDialog(getContext());
        @Override
        protected void onPostExecute(JSONArray roledata) {

            if (roledata!=null && roledata.size() > 0) {
                arrData=SaveDatamEmployeeBranchData(roledata);
                //spnBranch.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                loadData();
                Toast toast = Toast.makeText(getContext(), new clsHardCode().txtMessSuccessDownload,
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    Toast toast = Toast.makeText(getContext(), new clsHardCode().txtMessDataNotFound,
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                }

            }
            checkingDataTable();
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage(new clsHardCode().txtMessGetBranch);
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

    private void checkingDataTable() {
        List<mEmployeeBranchData> mEmployeeBranchDataList = new ArrayList<>();
        List<mEmployeeSalesProductData> employeeSalesProductDataList = new ArrayList<>();
        List<mEmployeeAreaData> mEmployeeAreaDataList = new ArrayList<>();
        List<mProductBrandHeaderData> mProductBrandHeaderDataList = new ArrayList<>();
        List<mTypeLeaveMobileData> mTypeLeaveMobileDataList = new ArrayList<>();

        mEmployeeBranchBL _mEmployeeBranchBL = new mEmployeeBranchBL();
        mEmployeeSalesProductBL _mEmployeeSalesProductBL = new mEmployeeSalesProductBL();
        mEmployeeAreaBL _mEmployeeAreaBL = new mEmployeeAreaBL();
        mProductBrandHeaderBL _mProductBrandHeaderBL = new mProductBrandHeaderBL();
        mTypeLeaveBL _mTypeLeaveBL = new mTypeLeaveBL();


        employeeSalesProductDataList = _mEmployeeSalesProductBL.GetAllData();
        mEmployeeBranchDataList = _mEmployeeBranchBL.GetAllData();
        mEmployeeAreaDataList = _mEmployeeAreaBL.GetAllData();
        mProductBrandHeaderDataList = _mProductBrandHeaderBL.GetAllData();
        mTypeLeaveMobileDataList = _mTypeLeaveBL.GetAllData();

        List<mEmployeeAreaData> data = _mEmployeeAreaBL.GetAllData();

        int validate = 0;

        if (mEmployeeBranchDataList.size()>0
                && employeeSalesProductDataList.size()>0
                && mEmployeeAreaDataList.size()>0
                && mProductBrandHeaderDataList.size()>0
                && mTypeLeaveMobileDataList.size()>0){

            goToMainMenu();
            //validate = 1;

//            for(mEmployeeAreaData dt : data){
//                if(dt.get_txtLatitude()==""||dt.get_txtLatitude()==null&&dt.get_txtLongitude()==""||dt.get_txtLongitude()==null){
//                    validate = 0;
//                }
//            }
        }

//        if(validate==1){
//            goToMainMenu();
//        }

        return;
    }

    private void goToMainMenu() {
        Intent myIntent = new Intent(getContext(), MainMenu.class);
        getActivity().finish();
        startActivity(myIntent);
        return;
    }

    private List<String> SaveDatamTypeLeaveMobileData(JSONArray JData){
        List<String> _array=new ArrayList<String>();
        APIData dtAPIDATA = new APIData();
        _array= new ArrayList<String>();
        Iterator i = JData.iterator();
        Boolean flag = true;
        String ErrorMess = "";
        List<mTypeLeaveMobileData> _Listdata = new ArrayList<mTypeLeaveMobileData>();
        while (i.hasNext()) {
            org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
            int boolValid = Integer.valueOf(String.valueOf(innerObj.get(dtAPIDATA.boolValid)));
            if (boolValid == Integer.valueOf(new clsHardCode().intSuccess)) {
                mTypeLeaveMobileData _data = new mTypeLeaveMobileData();
                _data.set_intTipeLeave(String.valueOf(innerObj.get("IntTipeLeave")));
                _data.set_txtTipeLeaveName(String.valueOf(innerObj.get("TxtTipeLeaveName")));
                _array.add(_data.get_intTipeLeave() +" - "+_data.get_txtTipeLeaveName());
                _Listdata.add(_data);
            } else {
                flag = false;
                ErrorMess = (String) innerObj.get(dtAPIDATA.strMessage);
                break;
            }
        }
        new mTypeLeaveBL().saveData(_Listdata);
        return _array;
    }
    private class AsyncCallLeave extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            JSONArray Json = null;
            try {
                Json = new mTypeLeaveBL().DownloadTypeLeave2(pInfo.versionName);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Json;
        }
        private ProgressDialog Dialog = new ProgressDialog(getContext());
        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(getContext(), new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }

        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata!=null&&roledata.size() > 0) {
                arrData=SaveDatamTypeLeaveMobileData(roledata);
                //spnLeave.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                loadData();
                Toast toast = Toast.makeText(getContext(), new clsHardCode().txtMessSuccessDownload,
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    Toast toast = Toast.makeText(getContext(), new clsHardCode().txtMessDataNotFound,
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                }

            }
            checkingDataTable();
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage(new clsHardCode().txtMessGetUserRole);
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
}
