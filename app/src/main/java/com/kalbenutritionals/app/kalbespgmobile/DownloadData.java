package com.kalbenutritionals.app.kalbespgmobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bl.clsHelperBL;
import bl.mEmployeeAreaBL;
import bl.mEmployeeBranchBL;
import bl.mEmployeeSalesProductBL;
import bl.mItemSalesPack_stockBL;
import bl.mPriceInOutletBL;
import bl.mProductBarcodeBL;
import bl.mProductBrandHeaderBL;
import bl.mReason_mobileBL;
import bl.mStatusDocumentBL;
import bl.mTypeLeaveBL;
import bl.mTypePenguaranMobileBL;
import bl.mstockawal_mobileBL;
import bl.tAbsenUserBL;
import bl.tGRNHeader_mobileBL;
import bl.tLeaveMobileBL;
import bl.tPOHeader_mobileBL;
import bl.tPenguaranHeader_MobileBL;
import bl.tSalesOrderHeader_MobileBL;
import bl.tStockOpnameHeader_mobileBL;
import library.salesforce.common.APIData;
import library.salesforce.common.dataJson;
import library.salesforce.common.mEmployeeAreaData;
import library.salesforce.common.mEmployeeBranchData;
import library.salesforce.common.mEmployeeSalesProductData;
import library.salesforce.common.mItemSalesPack_StockData;
import library.salesforce.common.mPriceInOutletData;
import library.salesforce.common.mProductBarcodeData;
import library.salesforce.common.mProductBrandHeaderData;
import library.salesforce.common.mReasonData;
import library.salesforce.common.mStatusDocumentData;
import library.salesforce.common.mStockAwalData;
import library.salesforce.common.mTypeLeaveMobileData;
import library.salesforce.common.mTypePenguaranMobileData;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tGRNHeader_mobileData;
import library.salesforce.common.tLeaveMobileData;
import library.salesforce.common.tPOHeader_mobileData;
import library.salesforce.common.tPenguaranHeader_MobileData;
import library.salesforce.common.tSalesOrderHeader_MobileData;
import library.salesforce.common.tStockOpnameHeader_mobileData;
import library.salesforce.dal.clsHardCode;

public class DownloadData extends clsMainActivity {
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

    int intProcesscancel = 0;
    private PackageInfo pInfo = null;
    private List<String> arrData;
    private String MenuID;
    @Override
    public void onBackPressed() {
        finish();
        Intent nextScreen = new Intent(getApplicationContext(), Home.class);
        nextScreen.putExtra(clsParameterPutExtra.MenuID, MenuID);
        startActivity(nextScreen);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterpage);
        setHeaderFull();
        Intent i = getIntent();
        MenuID=getMenuID(i);
        LinearLayout lnContent = (LinearLayout) findViewById(R.id.llContent);
        getLayoutInflater().inflate(R.layout.activity_download_data, lnContent);
        btnAllDownload = (Button) findViewById(R.id.btnAllDownload);
        btnBranch = (Button) findViewById(R.id.btnBranch);
        spnBranch = (Spinner) findViewById(R.id.spnBranch);
        btnOutlet = (Button) findViewById(R.id.btnOutlet);
        spnOutlet = (Spinner) findViewById(R.id.spnOutlet);
        btnProduct = (Button) findViewById(R.id.btnProduct);
        spnProduct = (Spinner) findViewById(R.id.spnProduct);
        btnLeave = (Button) findViewById(R.id.btnLeave);
        spnLeave = (Spinner) findViewById(R.id.spnLeave);
        spnBrand = (Spinner) findViewById(R.id.spnBrand);
        btnBrand = (Button) findViewById(R.id.btnDlBrand);

        pInfo=getPinfo();
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
    }
    private void loadData(){
        pInfo=getPinfo();
        List<mEmployeeBranchData> listDataBranch=new mEmployeeBranchBL().GetAllData();
        List<mEmployeeAreaData> listDataArea=new mEmployeeAreaBL().GetAllData();
        List<mEmployeeSalesProductData> listDataProduct=new mEmployeeSalesProductBL().GetAllData();
        List<mItemSalesPack_StockData> listDataStock=new mItemSalesPack_stockBL().GetAllData();
        List<mTypeLeaveMobileData> listDataLeave=new mTypeLeaveBL().GetAllData();
        List<mTypePenguaranMobileData> listDatamTypePenguaranMobileData=new mTypePenguaranMobileBL().getData("");
        List<mStatusDocumentData> listmStatusDocumentData=new mStatusDocumentBL().getData("");
        List<mReasonData> listmReasonData=new mReason_mobileBL().getData("");
        List<mStockAwalData> listmStockAwalData=new mstockawal_mobileBL().getData("");
        List<mPriceInOutletData> listmPriceInOutletData=new mPriceInOutletBL().GetAllData();
        List<tPOHeader_mobileData> listtPOHeader_mobileBL=new tPOHeader_mobileBL().getData("");
        List<tGRNHeader_mobileData> listtGRNHeader_mobileBL=new tGRNHeader_mobileBL().getData("");
        List<tPenguaranHeader_MobileData> listtPenguaranHeader_MobileData=new tPenguaranHeader_MobileBL().getData("");
        List<tStockOpnameHeader_mobileData> listtStockOpnameHeader_mobileData=new tStockOpnameHeader_mobileBL().getData("");
        List<tSalesOrderHeader_MobileData> listtSalesOrderHeader_MobileData=new tSalesOrderHeader_MobileBL().getData("");
        List<tLeaveMobileData> listtLeaveMobileData=new tLeaveMobileBL().getData("");
        List<tAbsenUserData> listtAbsenUserData=new tAbsenUserBL().GetData("");
        List<mProductBrandHeaderData> listmProductBrandData = new mProductBrandHeaderBL().getData("");

        arrData=new ArrayList<String>();
        if(listDataBranch.size()>0){
            for(mEmployeeBranchData dt :listDataBranch ){
                arrData.add(dt.get_txtBranchCode()+" - "+ dt.get_txtBranchName());
            }
            spnBranch.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
        }
        arrData=new ArrayList<String>();
        if(listDataLeave!=null){
            for(mTypeLeaveMobileData dt :listDataLeave ){
                arrData.add(dt.get_intTipeLeave()+" - "+ dt.get_txtTipeLeaveName());
            }
            spnLeave.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
        }
        arrData=new ArrayList<String>();
        if(listDataArea.size()>0){
            for(mEmployeeAreaData dt :listDataArea ){
                arrData.add(dt.get_txtOutletCode()+" - "+dt.get_txtOutletName());
            }
            spnOutlet.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
        }
        arrData=new ArrayList<String>();
        if(listDataProduct.size()>0){
            for(mEmployeeSalesProductData dt :listDataProduct ){
                arrData.add(dt.get_txtProductBrandDetailGramName());
            }
            spnProduct.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
        }
        arrData=new ArrayList<String>();
        if(listmProductBrandData.size()>0){
            for(mProductBrandHeaderData dt :listmProductBrandData ){
                arrData.add(dt.get_txtProductBrandName());
            }
            spnBrand.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
        }
    }
    private List<String> SaveDatamItemSalesPack_StockData(JSONArray JData){
        List<String> _array=new ArrayList<String>();
        APIData dtAPIDATA = new APIData();
        _array= new ArrayList<String>();
        Iterator i = JData.iterator();
        Boolean flag = true;
        String ErrorMess = "";
        List<mItemSalesPack_StockData> _Listdata = new ArrayList<mItemSalesPack_StockData>();
        while (i.hasNext()) {
            org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
            int boolValid = Integer.valueOf(String.valueOf(innerObj.get(dtAPIDATA.boolValid)));
            if (boolValid == Integer.valueOf(new clsHardCode().intSuccess)) {
                mItemSalesPack_StockData _data = new mItemSalesPack_StockData();
                _data.set_intProductCode((String) innerObj.get("_intProductCode"));
                _data.set_txtDataId((String) innerObj.get("_txtDataId"));
                _data.set_txtPeriode((String) innerObj.get("_txtPeriode"));
                _data.set_intSaldoAwal((String) innerObj.get("_intSaldoAwal"));
                _data.set_intWeek((String) innerObj.get("_intWeek"));
                _data.set_intQtyIn((String) innerObj.get("_intQtyIn"));
                _data.set_intQtyOut((String) innerObj.get("_intQtyOut"));
                _data.set_intQtyAdj((String) innerObj.get("_intQtyAdj"));
                _data.set_txtOutletCode((String) innerObj.get("_txtOutletCode"));
                _data.set_txtOutletName((String) innerObj.get("_txtOutletName"));
                _data.set_txtBranchCode((String) innerObj.get("_txtBranchCode"));
                _data.set_txtNoTransaction((String) innerObj.get("_txtNoTransaction"));
                _array.add(_data.get_txtOutletCode() +" - "+_data.get_txtOutletName()+" : "+_data.get_intQtyAvailable());
                _Listdata.add(_data);
            } else {
                flag = false;
                ErrorMess = (String) innerObj.get(dtAPIDATA.strMessage);
                break;
            }
        }
        new mItemSalesPack_stockBL().saveData(_Listdata);
        return _array;
    }
    private class AsyncCallStock extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            JSONArray Json = null;
            try {
                Json = new mItemSalesPack_stockBL().DownloadmProductBarcode2(pInfo.versionName);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Json;
        }
        private ProgressDialog Dialog = new ProgressDialog(DownloadData.this);
        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }

        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata.size() > 0) {
                arrData=SaveDatamItemSalesPack_StockData(roledata);
                //spnStock.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                loadData();
                Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessSuccessDownload,
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessDataNotFound,
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                }

            }
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage(new clsHardCode().txtMessGetMItemSalesPackStock);
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
        private ProgressDialog Dialog = new ProgressDialog(DownloadData.this);
        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }

        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata.size() > 0) {
                loadData();
                Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessSuccessDownload,
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessDataNotFound,
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                }

            }
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
        private ProgressDialog Dialog = new ProgressDialog(DownloadData.this);
        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }

        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata.size() > 0) {
                loadData();
                Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessSuccessDownload,
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessDataNotFound,
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                }

            }
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
            Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }
        private ProgressDialog Dialog = new ProgressDialog(DownloadData.this);
        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata.size() > 0) {
                arrData=SaveDatamEmployeeAreaData(roledata);
                loadData();
                //spnOutlet.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessSuccessDownload,
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessDataNotFound,
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                }

            }
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

    private class AsyncCallBranch extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            JSONArray Json = null;
            try {
                new tSalesOrderHeader_MobileBL().GetDataNotSalesOrderHeader_Mobile(pInfo.versionName);
                new tStockOpnameHeader_mobileBL().GetDataNotStockOpnameHeader_mobile(pInfo.versionName);
                new tGRNHeader_mobileBL().GetDataNotGRNHeader_mobile(pInfo.versionName);
                new mItemSalesPack_stockBL().GenerateDatepartNow(pInfo.versionName);
                new mItemSalesPack_stockBL().GeneratePeriodeNow(pInfo.versionName);
                new tPOHeader_mobileBL().GetDataNoPurchaseOrder(pInfo.versionName);
                new tPenguaranHeader_MobileBL().GetDatamTypePenguaranMobile(pInfo.versionName);
                new clsHelperBL().DownloadData(pInfo.versionName);
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
            Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }
        private ProgressDialog Dialog = new ProgressDialog(DownloadData.this);
        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata.size() > 0) {
                arrData=SaveDatamEmployeeBranchData(roledata);
                //spnBranch.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                loadData();
                Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessSuccessDownload,
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessDataNotFound,
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                }

            }
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
    private List<String> SaveDatamStockAwal(JSONArray JData){
        List<String> _array=new ArrayList<String>();
        APIData dtAPIDATA = new APIData();
        _array= new ArrayList<String>();
        Iterator i = JData.iterator();
        Boolean flag = true;
        String ErrorMess = "";
        List<mStockAwalData> _Listdata = new ArrayList<mStockAwalData>();
        while (i.hasNext()) {
            org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
            int boolValid = Integer.valueOf(String.valueOf(innerObj.get(dtAPIDATA.boolValid)));
            if (boolValid == Integer.valueOf(new clsHardCode().intSuccess)) {
                mStockAwalData _data = new mStockAwalData();
                _data.set_bitActive((String) innerObj.get("BitActive"));
                _data.set_intdata((String) innerObj.get("TxtdataId"));
                _data.set_intQty((String) innerObj.get("IntQty"));
                _data.set_txtBranchCode((String) innerObj.get("TxtBranchCode"));
                _data.set_txtNoDoc((String) innerObj.get("TxtNoDoc"));
                _data.set_txtOutletCode((String) innerObj.get("TxtOutletCode"));
                _data.set_txtOutletName((String) innerObj.get("TxtOutletName"));
                _data.set_txtProductCode((String) innerObj.get("TxtProductCode"));
                _data.set_txtProductName((String) innerObj.get("TxtProductName"));
                _data.set_txtStatus((String) innerObj.get("TxtStatus"));
                _array.add(_data.get_txtOutletCode()+" - "+_data.get_txtOutletName() +" : "+ _data.get_intQty());
                _Listdata.add(_data);
            } else {
                flag = false;
                ErrorMess = (String) innerObj.get(dtAPIDATA.strMessage);
                break;
            }
        }
        new mstockawal_mobileBL().saveData(_Listdata);
        return _array;
    }
    private class AsyncCallStockAwal extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            JSONArray Json = null;
            try {
                Json = new mstockawal_mobileBL().DownloadData(pInfo.versionName,getDataLoginActive().get_TxtEmpId());

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Json;
        }
        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }
        private ProgressDialog Dialog = new ProgressDialog(DownloadData.this);
        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata.size() > 0) {
                arrData=SaveDatamStockAwal(roledata);
                //spnStockAwal.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                loadData();
                Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessSuccessDownload,
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessDataNotFound,
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                }
            }
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage(new clsHardCode().txtMessGetStockAwal);
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

    private class AsyncCallDownloadAll extends AsyncTask<JSONArray, Void, List<dataJson>> {
        @Override
        protected List<dataJson> doInBackground(JSONArray... params) {
            JSONArray Json = null;
            List<dataJson> listDataJson=new ArrayList<dataJson>();
            dataJson dtdataJson=new dataJson();
            try {
                new tSalesOrderHeader_MobileBL().GetDataNotSalesOrderHeader_Mobile(pInfo.versionName);
                new tStockOpnameHeader_mobileBL().GetDataNotStockOpnameHeader_mobile(pInfo.versionName);
                new tGRNHeader_mobileBL().GetDataNotGRNHeader_mobile(pInfo.versionName);
                new mItemSalesPack_stockBL().GenerateDatepartNow(pInfo.versionName);
                new mItemSalesPack_stockBL().GeneratePeriodeNow(pInfo.versionName);
                new tPOHeader_mobileBL().GetDataNoPurchaseOrder(pInfo.versionName);
                new clsHelperBL().DownloadData(pInfo.versionName);
                new mPriceInOutletBL().DownloadmPriceInOutlet(pInfo.versionName);
                new tPenguaranHeader_MobileBL().GetDatamTypePenguaranMobile(pInfo.versionName);
                Json = new mEmployeeBranchBL().DownloadEmployeeBranch2(pInfo.versionName);
                SaveDatamEmployeeBranchData(Json);
                Json = new mItemSalesPack_stockBL().DownloadmProductBarcode2(pInfo.versionName);
                SaveDatamItemSalesPack_StockData(Json);
                Json = new mProductBarcodeBL().DownloadmProductBarcode2(pInfo.versionName);
                SaveDatamProductBarcodeData(Json);
                Json = new mEmployeeAreaBL().DownloadEmployeeArea2(pInfo.versionName);
                SaveDatamEmployeeAreaData(Json);
                Json =new mTypeLeaveBL().DownloadTypeLeave2(pInfo.versionName);
                SaveDatamTypeLeaveMobileData(Json);
                Json = new mStatusDocumentBL().DownloadData(pInfo.versionName);
                SaveDatamStatusDocumentData(Json);
                Json = new mTypePenguaranMobileBL().DownloadData(pInfo.versionName);
                SaveDatamTypePenguaranMobileData(Json);
                Json= new mReason_mobileBL().DownloadData(pInfo.versionName);
                SaveDatamReasonData(Json);
                Json= new mstockawal_mobileBL().DownloadData(pInfo.versionName, getDataLoginActive().get_TxtEmpId());
                SaveDatamStockAwal(Json);
                dtdataJson.setIntResult("1");
            } catch (Exception e) {
                dtdataJson.setIntResult("0");
                dtdataJson.setTxtMessage(e.getMessage().toString());
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            listDataJson.add(dtdataJson);
            return listDataJson;
        }
        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }
        private ProgressDialog Dialog = new ProgressDialog(DownloadData.this);
        @Override
        protected void onPostExecute(List<dataJson> listdataJson) {
            String txtMess=new clsHardCode().txtMessSuccessDownload;
            if(listdataJson.get(0).getIntResult().equals("0")){
                txtMess=listdataJson.get(0).getTxtMessage();
            }else{
                loadData();
            }
            Toast toast = Toast.makeText(DownloadData.this, txtMess,
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
            Dialog.dismiss();
            finish();
            Intent nextScreen = new Intent(getApplicationContext(), Home.class);
            nextScreen.putExtra(clsParameterPutExtra.MenuID, MenuID);
            startActivity(nextScreen);
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
    private List<String> SaveDatamStatusDocumentData(JSONArray JData){
        List<String> _array=new ArrayList<String>();
        APIData dtAPIDATA = new APIData();
        _array= new ArrayList<String>();
        Iterator i = JData.iterator();
        Boolean flag = true;
        String ErrorMess = "";
        List<mStatusDocumentData> _Listdata = new ArrayList<mStatusDocumentData>();
        while (i.hasNext()) {
            org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
            int boolValid = Integer.valueOf(String.valueOf(innerObj.get(dtAPIDATA.boolValid)));
            if (boolValid == Integer.valueOf(new clsHardCode().intSuccess)) {
                mStatusDocumentData _data = new mStatusDocumentData();

                _data.set_bitActive("1");
                _data.set_intStatus((String.valueOf(innerObj.get("_intStatus"))) );
                _data.set_txtStatus((String) innerObj.get("_txtStatus"));
                _array.add(_data.get_txtStatus());
                _Listdata.add(_data);
            } else {
                flag = false;
                ErrorMess = (String) innerObj.get(dtAPIDATA.strMessage);
                break;
            }
        }
        new mStatusDocumentBL().saveData(_Listdata);
        return _array;
    }
    private class AsyncCallStatusDocument extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            JSONArray Json = null;
            try {
                Json = new mStatusDocumentBL().DownloadData(pInfo.versionName);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Json;
        }
        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }
        private ProgressDialog Dialog = new ProgressDialog(DownloadData.this);
        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata.size() > 0) {
                arrData=SaveDatamStatusDocumentData(roledata);
                //spnTypeStatus.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                loadData();
                Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessSuccessDownload,
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessDataNotFound,
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                }

            }
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage(new clsHardCode().txtMessDownloadStatusDocument);
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
    private List<String> SaveDatamReasonData(JSONArray JData){
        List<String> _array=new ArrayList<String>();
        APIData dtAPIDATA = new APIData();
        _array= new ArrayList<String>();
        Iterator i = JData.iterator();
        Boolean flag = true;
        String ErrorMess = "";
        List<mReasonData> _Listdata = new ArrayList<mReasonData>();
        while (i.hasNext()) {
            org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
            int boolValid = Integer.valueOf(String.valueOf(innerObj.get(dtAPIDATA.boolValid)));
            if (boolValid == Integer.valueOf(new clsHardCode().intSuccess)) {
                mReasonData _data = new mReasonData();
                _data.set_bitActive("1");
                _data.set_intData((String.valueOf(innerObj.get("IntData"))) );
                _data.set_txtReason((String) innerObj.get("TxtReason"));
                _data.set_txtType((String) innerObj.get("TxtType"));
                _array.add(_data.get_txtReason()+"-"+ _data.get_txtType());
                _Listdata.add(_data);
            } else {
                flag = false;
                ErrorMess = (String) innerObj.get(dtAPIDATA.strMessage);
                break;
            }
        }
        new mReason_mobileBL().saveData(_Listdata);
        return _array;
    }
    private class AsyncCallmReason extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            JSONArray Json = null;
            try {
                Json = new mReason_mobileBL().DownloadData(pInfo.versionName);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Json;
        }
        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }
        private ProgressDialog Dialog = new ProgressDialog(DownloadData.this);
        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata.size() > 0) {
                arrData=SaveDatamReasonData(roledata);
                //spnReason.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                loadData();
                Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessSuccessDownload,
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessDataNotFound,
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                }

            }
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage(new clsHardCode().txtMessGetReason);
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
        private ProgressDialog Dialog = new ProgressDialog(DownloadData.this);
        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }

        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata.size() > 0) {
                arrData=SaveDatamTypeLeaveMobileData(roledata);
                //spnLeave.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                loadData();
                Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessSuccessDownload,
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessDataNotFound,
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                }

            }
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
    private List<String> SaveDatamPriceProduct(JSONArray JData){
        List<String> _array=new ArrayList<String>();
        APIData dtAPIDATA = new APIData();
        _array= new ArrayList<String>();
        Iterator i = JData.iterator();
        Boolean flag = true;
        String ErrorMess = "";
        List<mPriceInOutletData> _Listdata = new ArrayList<mPriceInOutletData>();
        while (i.hasNext()) {
            org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
            int boolValid = Integer.valueOf(String.valueOf(innerObj.get(dtAPIDATA.boolValid)));
            if (boolValid == Integer.valueOf(new clsHardCode().intSuccess)) {
                mPriceInOutletData _data = new mPriceInOutletData();

                _data.set_decPriceHJD((String) innerObj.get("_decPriceHJD"));
                _data.set_intIdItemPrice((String) innerObj.get("_intIdItemPrice"));
                _data.set_intProductCode(String.valueOf(innerObj.get("_intProductCode")));
                _data.set_txtProductName((String) innerObj.get("_txtProductName"));
                _data.set_txtBranchCode((String) innerObj.get("_txtBranchCode"));
                _data.set_txtOutletCode((String) innerObj.get("_txtOutletCode"));
                _data.set_txtOutletName((String) innerObj.get("_txtOutletName"));
                _array.add(_data.get_txtProductName() +" - Rp."+_data.get_decPriceHJD());
                _Listdata.add(_data);
            } else {
                flag = false;
                ErrorMess = (String) innerObj.get(dtAPIDATA.strMessage);
                break;
            }
        }
        new mPriceInOutletBL().saveData(_Listdata);
        return _array;
    }
    private class AsyncCallPriceProduct extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            JSONArray Json = null;
            try {
                Json = new mPriceInOutletBL().DownloadmPriceInOutlet2(pInfo.versionName);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Json;
        }
        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }
        private ProgressDialog Dialog = new ProgressDialog(DownloadData.this);
        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata.size() > 0) {
                arrData=SaveDatamPriceProduct(roledata);
                //spnTypeDocument.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                loadData();
                Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessSuccessDownload,
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessDataNotFound,
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                }

            }
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage(new clsHardCode().txtMessDownloadPriceProduct);
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
    private List<String> SaveDatamTypePenguaranMobileData(JSONArray JData){
        List<String> _array=new ArrayList<String>();
        APIData dtAPIDATA = new APIData();
        _array= new ArrayList<String>();
        Iterator i = JData.iterator();
        Boolean flag = true;
        String ErrorMess = "";
        List<mTypePenguaranMobileData> _Listdata = new ArrayList<mTypePenguaranMobileData>();
        while (i.hasNext()) {
            org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
            int boolValid = Integer.valueOf(String.valueOf(innerObj.get(dtAPIDATA.boolValid)));
            if (boolValid == Integer.valueOf(new clsHardCode().intSuccess)) {
                mTypePenguaranMobileData _data = new mTypePenguaranMobileData();

                _data.set_bitActive("1");
                _data.set_intTypePenguaranMobile((String.valueOf(innerObj.get("_intTypePenguaranMobile"))) );
                _data.set_txtNamaPenguaran((String) innerObj.get("_txtNamaPenguaran"));
                _array.add(_data.get_txtNamaPenguaran());
                _Listdata.add(_data);
            } else {
                flag = false;
                ErrorMess = (String) innerObj.get(dtAPIDATA.strMessage);
                break;
            }
        }
        new mTypePenguaranMobileBL().saveData(_Listdata);
        return _array;
    }
    private class AsyncCallTypePenguaran extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            JSONArray Json = null;
            try {
                Json = new mTypePenguaranMobileBL().DownloadData(pInfo.versionName);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Json;
        }
        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }
        private ProgressDialog Dialog = new ProgressDialog(DownloadData.this);
        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata.size() > 0) {
                arrData=SaveDatamTypePenguaranMobileData(roledata);
                //spnTypeDocument.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                loadData();
                Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessSuccessDownload,
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    Toast toast = Toast.makeText(DownloadData.this, new clsHardCode().txtMessDataNotFound,
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                }

            }
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage(new clsHardCode().txtMessDownloadTypePengeluaran);
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
            LayoutInflater inflater=getLayoutInflater();
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
}
