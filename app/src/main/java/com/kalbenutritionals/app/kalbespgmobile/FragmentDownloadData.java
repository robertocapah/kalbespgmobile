package com.kalbenutritionals.app.kalbespgmobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.util.ByteArrayBuffer;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bl.clsMainBL;
import bl.mEmployeeAreaBL;
import bl.mEmployeeBranchBL;
import bl.mEmployeeSalesProductBL;
import bl.mPriceInOutletBL;
import bl.mProductBarcodeBL;
import bl.mProductBrandHeaderBL;
import bl.mProductCompetitorBL;
import bl.mProductPICBL;
import bl.mProductSPGBL;
import bl.mTypeLeaveBL;
import bl.mTypeSubmissionMobileBL;
import bl.tAbsenUserBL;
import bl.tActivityBL;
import bl.tCustomerBasedMobileDetailBL;
import bl.tCustomerBasedMobileDetailProductBL;
import bl.tCustomerBasedMobileHeaderBL;
import bl.tLeaveMobileBL;
import bl.tSalesProductHeaderBL;
import bl.tUserLoginBL;
import library.salesforce.common.APIData;
import library.salesforce.common.clsHelper;
import library.salesforce.common.dataJson;
import library.salesforce.common.mEmployeeAreaData;
import library.salesforce.common.mEmployeeBranchData;
import library.salesforce.common.mEmployeeSalesProductData;
import library.salesforce.common.mProductBarcodeData;
import library.salesforce.common.mProductBrandHeaderData;
import library.salesforce.common.mProductCompetitorData;
import library.salesforce.common.mProductPICData;
import library.salesforce.common.mProductSPGData;
import library.salesforce.common.mTypeLeaveMobileData;
import library.salesforce.common.mTypeSubmissionMobile;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tActivityData;
import library.salesforce.common.tCustomerBasedMobileDetailData;
import library.salesforce.common.tCustomerBasedMobileDetailProductData;
import library.salesforce.common.tCustomerBasedMobileHeaderData;
import library.salesforce.common.tLeaveMobileData;
import library.salesforce.common.tSalesProductDetailData;
import library.salesforce.common.tSalesProductHeaderData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.tSalesProductDetailDA;

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
    private Spinner spnReso;
    private Button btnReso;
    private Spinner spnActivity;
    private Button btnActivity;
    private Spinner spnCustomerBase;
    private Button btnCustomerBase;
    private Spinner spnAbsen;
    private Button btnAbsen;
    private Spinner spnDataLeave, spnProductComp, spnTypeSubmission, spnProdSPGCusBased, spnProdPICCusBased;
    private Button btnDataLeave, btnProductComp, btnTypeSubmission, btnProdSPGCusBased, btnProdPICCusBased;

    private PackageInfo pInfo = null;
    private List<String> arrData;
    private String[] strip = new String[]{"-"};
    int intProcesscancel = 0;

    clsMainActivity _clsMainActivity;
    tUserLoginData loginData;

    private String strMessage = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_download_data, container, false);
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
        spnReso = (Spinner) v.findViewById(R.id.spnReso);
        btnReso = (Button) v.findViewById(R.id.btnDlReso);
        spnActivity = (Spinner) v.findViewById(R.id.spnActivity);
        btnActivity = (Button) v.findViewById(R.id.btnDlActivity);
        spnCustomerBase = (Spinner) v.findViewById(R.id.spnCustomerBase);
        btnCustomerBase = (Button) v.findViewById(R.id.btnDlCustomerBase);
        spnAbsen = (Spinner) v.findViewById(R.id.spnAbsen);
        btnAbsen = (Button) v.findViewById(R.id.btnDlAbsen);
        spnDataLeave = (Spinner) v.findViewById(R.id.spnDataLeave);
        btnDataLeave = (Button) v.findViewById(R.id.btnDlDataLeave);
        spnProductComp = (Spinner) v.findViewById(R.id.spnProdComp);
        btnProductComp = (Button) v.findViewById(R.id.btnProdComp);
        spnTypeSubmission = (Spinner) v.findViewById(R.id.spnTypeSubm);
        btnTypeSubmission = (Button) v.findViewById(R.id.btnSumbisson);
        spnProdSPGCusBased = (Spinner) v.findViewById(R.id.spnProdSPGCusBase);
        btnProdSPGCusBased = (Button) v.findViewById(R.id.btnProdSPGCusBase);
        spnProdPICCusBased = (Spinner) v.findViewById(R.id.spnProdPICCusBase);
        btnProdPICCusBased = (Button) v.findViewById(R.id.btnProdPICCusBase);

        loginData = new tUserLoginData();
        loginData = new tUserLoginBL().getUserActive();

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
        btnReso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                intProcesscancel = 0;
                AsyncCallReso task = new AsyncCallReso();
                task.execute();
            }
        });
        btnActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                intProcesscancel = 0;
                AsyncCallActivity task = new AsyncCallActivity();
                task.execute();
            }
        });
        btnCustomerBase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                intProcesscancel = 0;
                AsyncCallCustomerBase task = new AsyncCallCustomerBase();
                task.execute();
            }
        });
        btnAbsen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                intProcesscancel = 0;
                AsyncCallAbsen task = new AsyncCallAbsen();
                task.execute();
            }
        });
        btnDataLeave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                intProcesscancel = 0;
                AsyncCallDataLeave task = new AsyncCallDataLeave();
                task.execute();
            }
        });
        btnProductComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intProcesscancel = 0;
                AsyncCallDataProdComp task = new AsyncCallDataProdComp();
                task.execute();
            }
        });

        btnTypeSubmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncCallTypeSubmission task = new AsyncCallTypeSubmission();
                task.execute();
            }
        });
        btnProdSPGCusBased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncCallDataProdSPGCusBased task = new AsyncCallDataProdSPGCusBased();
                task.execute();
            }
        });
        btnProdPICCusBased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncCallDataProdPICCusBased task = new AsyncCallDataProdPICCusBased();
                task.execute();
            }
        });


        return v;
    }

    private void loadData() {
        _clsMainActivity = new clsMainActivity();
        try {
            pInfo = getContext().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        List<mEmployeeBranchData> listDataBranch = new mEmployeeBranchBL().GetAllData();
        List<mEmployeeAreaData> listDataArea = new mEmployeeAreaBL().GetAllData();
        List<mEmployeeSalesProductData> listDataProduct = new mEmployeeSalesProductBL().GetAllData();
        List<mTypeLeaveMobileData> listDataLeave = new mTypeLeaveBL().GetAllData();
        List<mProductBrandHeaderData> listmProductBrandData = new mProductBrandHeaderBL().getData("");
        List<tSalesProductHeaderData> listtSalesProductHeaderData = new tSalesProductHeaderBL().getAllSalesProductHeader();
        List<tCustomerBasedMobileHeaderData> listtCustomerBasedHeaderData = new tCustomerBasedMobileHeaderBL().getAllData();
        List<tActivityData> listtActivityData = new tActivityBL().getAllData();
        List<tAbsenUserData> listtAbsenUserData = new tAbsenUserBL().getAllDataActive();
        List<tLeaveMobileData> listtLeaveData = new tLeaveMobileBL().getData("");
        List<mProductCompetitorData> productCompetitorDataList = new mProductCompetitorBL().GetAllData();
        List<mTypeSubmissionMobile> typeSubmissionDataList = new mTypeSubmissionMobileBL().GetAllData();
        List<mProductSPGData> mProductSPGDataList = new mProductSPGBL().GetAllData();
        List<mProductPICData> mProductPICDataList = new mProductPICBL().GetAllData();


        arrData = new ArrayList<String>();
        if (typeSubmissionDataList.size() > 0) {
            for (mTypeSubmissionMobile dt : typeSubmissionDataList) {
                arrData.add(dt.get_txtMasterID() + "-" + dt.get_txtNamaMasterData());
            }
            spnTypeSubmission.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));
            spnTypeSubmission.setEnabled(true);
        } else if (typeSubmissionDataList.size() == 0) {
            ArrayAdapter<String> adapterspn = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, strip);
            spnTypeSubmission.setAdapter(adapterspn);
            spnTypeSubmission.setEnabled(false);
        }
        arrData = new ArrayList<String>();
        if (mProductSPGDataList.size() > 0) {
            for (mProductSPGData dt : mProductSPGDataList) {
                arrData.add(dt.get_txtMasterId() + " - " + dt.get_txtProductBrandDetailGramName());
            }
            spnProdSPGCusBased.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));
            spnProdSPGCusBased.setEnabled(true);
        } else if (mProductSPGDataList.size() == 0) {
            ArrayAdapter<String> adapterspn = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, strip);
            spnProdSPGCusBased.setAdapter(adapterspn);
            spnProdSPGCusBased.setEnabled(false);
        }
        arrData = new ArrayList<String>();
        if (mProductPICDataList.size() > 0) {
            for (mProductPICData dt : mProductPICDataList) {
                arrData.add(dt.get_txtMasterId() + " - " + dt.get_txtProductBrandDetailGramName());
            }
            spnProdPICCusBased.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));
            spnProdPICCusBased.setEnabled(true);
        } else if (mProductPICDataList.size() == 0) {
            ArrayAdapter<String> adapterspn = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, strip);
            spnProdPICCusBased.setAdapter(adapterspn);
            spnProdPICCusBased.setEnabled(false);
        }
        arrData = new ArrayList<String>();
        if (productCompetitorDataList.size() > 0) {
            for (mProductCompetitorData dt : productCompetitorDataList) {
                arrData.add(dt.get_txtProductDetailCode() + "(" + dt.get_txtProdukKompetitorID() + ")");
            }
            spnProductComp.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));
            spnProductComp.setEnabled(true);
        } else if (productCompetitorDataList.size() == 0) {
            ArrayAdapter<String> adapterspn = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, strip);
            spnProductComp.setAdapter(adapterspn);
            spnProductComp.setEnabled(false);
        }
        arrData = new ArrayList<String>();
        if (listDataBranch.size() > 0) {
            for (mEmployeeBranchData dt : listDataBranch) {
                arrData.add(dt.get_txtBranchCode() + " - " + dt.get_txtBranchName());
            }
            spnBranch.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));
            spnBranch.setEnabled(true);
        } else if (listDataBranch.size() == 0) {
            ArrayAdapter<String> adapterspn = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, strip);
            spnBranch.setAdapter(adapterspn);
            spnBranch.setEnabled(false);
        }
        arrData = new ArrayList<String>();
        if (listDataLeave.size() > 0) {
            for (mTypeLeaveMobileData dt : listDataLeave) {
                arrData.add(dt.get_intTipeLeave() + " - " + dt.get_txtTipeLeaveName());
            }
            spnLeave.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));
            spnLeave.setEnabled(true);
        } else if (listDataLeave.size() == 0) {
            ArrayAdapter<String> adapterspn = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, strip);
            spnLeave.setAdapter(adapterspn);
            spnLeave.setEnabled(false);
        }
        arrData = new ArrayList<String>();
        if (listDataArea.size() > 0) {
            for (mEmployeeAreaData dt : listDataArea) {
                arrData.add(dt.get_txtOutletCode() + " - " + dt.get_txtOutletName());
            }
            spnOutlet.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));
            spnOutlet.setEnabled(true);
        } else if (listDataArea.size() == 0) {
            ArrayAdapter<String> adapterspn = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, strip);
            spnOutlet.setAdapter(adapterspn);
            spnOutlet.setEnabled(false);
        }
        arrData = new ArrayList<String>();
        if (listDataProduct.size() > 0) {
            for (mEmployeeSalesProductData dt : listDataProduct) {
                arrData.add(dt.get_txtProductBrandDetailGramName());
            }
            spnProduct.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));
            spnProduct.setEnabled(true);
        } else if (listDataProduct.size() == 0) {
            ArrayAdapter<String> adapterspn = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, strip);
            spnProduct.setAdapter(adapterspn);
            spnProduct.setEnabled(false);
        }
        arrData = new ArrayList<String>();
        if (listmProductBrandData.size() > 0) {
            for (mProductBrandHeaderData dt : listmProductBrandData) {
                arrData.add(dt.get_txtProductBrandName());
            }
            spnBrand.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));
            spnBrand.setEnabled(true);
        } else if (listmProductBrandData.size() == 0) {
            ArrayAdapter<String> adapterspn = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, strip);
            spnBrand.setAdapter(adapterspn);
            spnBrand.setEnabled(false);
        }
        arrData = new ArrayList<String>();
        if (listtSalesProductHeaderData != null) {
            for (tSalesProductHeaderData dt : listtSalesProductHeaderData) {
                arrData.add(dt.get_txtNoSo());
            }
            spnReso.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));
            spnReso.setEnabled(true);
        } else if (listtSalesProductHeaderData == null) {
            ArrayAdapter<String> adapterspn = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, strip);
            spnReso.setAdapter(adapterspn);
            spnReso.setEnabled(false);
        }
        arrData = new ArrayList<String>();
        if (listtCustomerBasedHeaderData != null && listtCustomerBasedHeaderData.size() != 0) {
            for (tCustomerBasedMobileHeaderData dt : listtCustomerBasedHeaderData) {
                arrData.add(dt.get_txtSubmissionId());
            }
            spnCustomerBase.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));
            spnCustomerBase.setEnabled(true);
        } else {
            ArrayAdapter<String> adapterspn = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, strip);
            spnCustomerBase.setAdapter(adapterspn);
            spnCustomerBase.setEnabled(false);
        }
        arrData = new ArrayList<String>();
        if (listtActivityData != null && listtActivityData.size() != 0) {
            for (tActivityData dt : listtActivityData) {
                arrData.add(dt.get_intFlag() + "-" + dt.get_txtDesc());
            }
            spnActivity.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));
            spnActivity.setEnabled(true);
        } else {
            ArrayAdapter<String> adapterspn = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, strip);
            spnActivity.setAdapter(adapterspn);
            spnActivity.setEnabled(false);
        }
        arrData = new ArrayList<String>();
        if (listtAbsenUserData != null) {
            for (tAbsenUserData dt : listtAbsenUserData) {
                arrData.add(dt.get_txtBranchName() + " - " + dt.get_txtOutletName());
            }
            spnAbsen.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));
            spnAbsen.setEnabled(true);
        } else if (listtAbsenUserData == null) {
            ArrayAdapter<String> adapterspn = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, strip);
            spnAbsen.setAdapter(adapterspn);
            spnAbsen.setEnabled(false);
        }

        arrData = new ArrayList<String>();
        if (listtAbsenUserData != null) {
            for (tLeaveMobileData dt : listtLeaveData) {
                arrData.add(dt.get_txtTypeAlasanName());
            }
            spnDataLeave.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));
            spnDataLeave.setEnabled(true);
        } else if (listtAbsenUserData == null) {
            ArrayAdapter<String> adapterspn = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, strip);
            spnDataLeave.setAdapter(adapterspn);
            spnDataLeave.setEnabled(false);
        }
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
            if (getArrayDataAdapyter().size() > 0) {
                TextView label = (TextView) row.findViewById(R.id.tvTitle);
                //label.setText(arrData.get(position));
                label.setText(getArrayDataAdapyter().get(position));
                label.setTextColor(new Color().parseColor("#000000"));
                TextView sub = (TextView) row.findViewById(R.id.tvDesc);
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
            List<dataJson> listDataJson = new ArrayList<dataJson>();
            dataJson dtdataJson = new dataJson();
            JSONParser parser = new JSONParser();
            try {
                new mPriceInOutletBL().DownloadmPriceInOutlet(pInfo.versionName);
                Json = new mEmployeeBranchBL().DownloadEmployeeBranch2(pInfo.versionName);
                SaveDatamEmployeeBranchData(Json);
                Json = new mTypeLeaveBL().DownloadTypeLeave2(pInfo.versionName);
                SaveDatamTypeLeaveMobileData(Json);
                Json = new mEmployeeSalesProductBL().DownloadEmployeeSalesProduct(pInfo.versionName);
                SaveDatamProductBarcodeData(Json);
                Json = new mProductBrandHeaderBL().DownloadBrandHeader(pInfo.versionName);
                SaveDatamProductBarcodeData(Json);
                Json = new mProductCompetitorBL().DownloadProdctCompetitor(pInfo.versionName, loginData.get_txtUserId(), loginData.get_TxtEmpId());
                SaveDatammProductCompetitorData(Json);
                Json = new mProductSPGBL().DownloadProductSPG(pInfo.versionName, loginData.get_txtUserId(), loginData.get_TxtEmpId());
                SaveDatammProductSPGData(Json);
                Json = new mProductPICBL().DownloadProductPIC(pInfo.versionName, loginData.get_txtUserId(), loginData.get_TxtEmpId());
                SaveDatammProductPICData(Json);
                Json = new mEmployeeAreaBL().DownloadEmployeeArea2(pInfo.versionName);
                SaveDatamEmployeeAreaData(Json);
                Json = new tSalesProductHeaderBL().DownloadReso(pInfo.versionName);

                Iterator i = Json.iterator();
                org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
                int boolValid = Integer.valueOf(String.valueOf(innerObj.get("_pboolValid")));
                if(boolValid == 1) SaveDatatSalesProductData(Json);

                Json = new tActivityBL().DownloadActivity(pInfo.versionName);
                SaveDatatActivityData(Json);
                Json = new tCustomerBasedMobileHeaderBL().DownloadCustomerBase(pInfo.versionName);
                SaveDatatCustomerBasedData(Json);
                Json = new tAbsenUserBL().DownloadAbsen(pInfo.versionName);
                SaveDatatAbsenUserData(Json);
                Json = new tLeaveMobileBL().DownloadDataLeave(pInfo.versionName);
                SaveDatatLeaveData(Json);
                Json = new mTypeSubmissionMobileBL().DownloadTypeSubmission(pInfo.versionName, loginData.get_txtUserId(), loginData.get_TxtEmpId());
                SaveDatamTypeSubmissionMobile(Json);

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
            new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessCancelRequest, false);
        }

        private ProgressDialog Dialog = new ProgressDialog(getContext());

        @Override
        protected void onPostExecute(List<dataJson> listdataJson) {
            String txtMess = new clsHardCode().txtMessSuccessDownload;
            if (listdataJson.get(0).getIntResult().equals("0")) {
                //txtMess=listdataJson.get(0).getTxtMessage();
                new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessUnableToConnect, false);
                Dialog.dismiss();

            } else {
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

    private List<String> SaveDatamEmployeeBranchData(JSONArray JData) {
        List<String> _array = new ArrayList<String>();
        APIData dtAPIDATA = new APIData();
        _array = new ArrayList<String>();
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
                _array.add(_data.get_txtBranchCode() + " - " + _data.get_txtBranchName());
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

    private List<String> SaveDatatSalesProductData(JSONArray JData) {
        List<String> _array;
        APIData dtAPIDATA = new APIData();
        _array = new ArrayList<>();
        Iterator i = JData.iterator();
        Boolean flag = true;
        String ErrorMess = "";
        List<tSalesProductHeaderData> ListdataHeader = new ArrayList<>();
        while (i.hasNext()) {
            org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();

            try {
                JSONArray JsonArray_header = new clsHelper().ResultJsonArray(String.valueOf(innerObj.get("ListDatatSalesProductHeader_mobile")));
                if (JsonArray_header != null) {
                    Iterator j = JsonArray_header.iterator();

                    while (j.hasNext()) {
                        tSalesProductHeaderData _data = new tSalesProductHeaderData();
                        org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) j.next();
                        _data.set_txtNoSo(String.valueOf(innerObj_detail.get("TxtNoSO")));
                        _data.set_txtBranchCode(String.valueOf(innerObj_detail.get("TxtBranchCode")));
                        _data.set_intSubmit("1");
                        _data.set_intSync("1");
                        _data.set_intSumAmount(String.valueOf(innerObj_detail.get("IntSumAmount")));
                        _data.set_UserId(String.valueOf(innerObj_detail.get("txtUserId")));
                        _data.set_intId(String.valueOf(innerObj_detail.get("TxtDataId")));
                        _data.set_txtKeterangan(String.valueOf(innerObj_detail.get("TxtKeterangan")));
                        _data.set_intSumItem(String.valueOf(innerObj_detail.get("IntSumItem")));
                        _data.set_txtBranchName(String.valueOf(innerObj_detail.get("TxtBranchName")));
                        _data.set_txtNIK(String.valueOf(innerObj_detail.get("TxtNik")));
                        _data.set_OutletCode(String.valueOf(innerObj_detail.get("TxtOutletCode")));
                        _data.set_OutletName(String.valueOf(innerObj_detail.get("TxtOutletName")));
                        _data.set_dtDate(String.valueOf(innerObj_detail.get("DtDate")));
                        new tSalesProductHeaderBL().SaveData(_data);
                    }

                    JSONArray JsonArray_detail = new clsHelper().ResultJsonArray(String.valueOf(innerObj.get("ListDatatSalesProductDetail_mobile")));
                    Iterator k = JsonArray_detail.iterator();

                    clsMainBL _clsMainBL = new clsMainBL();
                    SQLiteDatabase _db = _clsMainBL.getDb();

                    while (k.hasNext()) {
                        tSalesProductDetailData _data = new tSalesProductDetailData();
                        org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) k.next();
                        _data.set_txtNoSo(String.valueOf(innerObj_detail.get("TxtNoSO")));
                        _data.set_txtNameProduct(String.valueOf(innerObj_detail.get("TxtNameProduct")));
                        _data.set_txtNoSo(String.valueOf(innerObj_detail.get("TxtNoSO")));
                        _data.set_txtCodeProduct(String.valueOf(innerObj_detail.get("TxtCodeProduct")));
                        _data.set_intTotal(String.valueOf(innerObj_detail.get("IntTotal")));
                        _data.set_intPrice(String.valueOf(innerObj_detail.get("IntPrice")));
                        _data.set_intQty(String.valueOf(innerObj_detail.get("IntQty")));
                        _data.set_intId(String.valueOf(innerObj_detail.get("TxtDataId")));
                        new tSalesProductDetailDA(_db).SaveDatatSalesProductDetailData(_db, _data);
                    }
                } else {
                    new clsMainActivity().showCustomToast(getContext(), "Data not found", false);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return _array;
    }

    private List<String> SaveDatatActivityData(JSONArray JData) {
        List<String> _array;
        APIData dtAPIDATA = new APIData();
        _array = new ArrayList<>();
        Iterator i = JData.iterator();
        Boolean flag = true;
        String ErrorMess = "";
        List<tActivityData> ListdataActivity = new ArrayList<>();
        while (i.hasNext()) {
            org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();

            tActivityData _data = new tActivityData();
            _data.set_dtActivity(String.valueOf(innerObj.get("DtActivity")));
            _data.set_intActive(String.valueOf(innerObj.get("IntActive")));
            _data.set_intSubmit("1");
            _data.set_intIdSyn("1");
            _data.set_intId(String.valueOf(innerObj.get("IntIdData")));
            _data.set_txtBranch(String.valueOf(innerObj.get("TxtCabId")));
            _data.set_txtDesc(String.valueOf(innerObj.get("TxtDesc")));
            _data.set_txtDeviceId(String.valueOf(innerObj.get("TxtDeviceId")));
            _data.set_txtOutletCode(String.valueOf(innerObj.get("TxtOutletCode")));
            _data.set_txtOutletName(String.valueOf(innerObj.get("TxtOutletName")));
            _data.set_txtUserId(String.valueOf(innerObj.get("TxtUserId")));
            _data.set_intFlag(String.valueOf(innerObj.get("TxtType")));
            _data.set_intActive(String.valueOf(innerObj.get("IntActive")));

            String url1 = String.valueOf(innerObj.get("TxtLinkImg1"));
            String url2 = String.valueOf(innerObj.get("TxtLinkImg2"));

            byte[] logoImage1 = getLogoImage(url1);
            byte[] logoImage2 = getLogoImage(url2);

            if (logoImage1 != null) {
                _data.set_txtImg1(logoImage1);
            }

            if (logoImage2 != null) {
                _data.set_txtImg2(logoImage2);
            }

            ListdataActivity.add(_data);
        }

        if(ListdataActivity.size() > 0){
            new tActivityBL().saveData(ListdataActivity);
        }

        return _array;
    }

    private List<String> SaveDatatAbsenUserData(JSONArray JData) {
        List<String> _array;
        APIData dtAPIDATA = new APIData();
        _array = new ArrayList<>();
        Iterator i = JData.iterator();
        Boolean flag = true;
        String ErrorMess = "";
        List<tAbsenUserData> ListdataAbsen = new ArrayList<>();
        while (i.hasNext()) {
            org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();

            int boolValid = Integer.valueOf(String.valueOf(innerObj.get(dtAPIDATA.boolValid)));
            if (boolValid == Integer.valueOf(new clsHardCode().intSuccess)) {
                tAbsenUserData _data = new tAbsenUserData();
                _data.set_dtDateCheckIn(String.valueOf(innerObj.get("DtCheckIn")));
                _data.set_dtDateCheckOut(String.valueOf(innerObj.get("DtCheckOut")));
                _data.set_intSubmit("1");
                _data.set_intSync("1");
                _data.set_txtAccuracy(String.valueOf(innerObj.get("TxtAccuracy")));
                _data.set_txtBranchCode(String.valueOf(innerObj.get("TxtBranchCode")));
                _data.set_txtBranchName(String.valueOf(innerObj.get("TxtBranchName")));
                _data.set_txtDeviceId(String.valueOf(innerObj.get("TxtDeviceId")));
                _data.set_txtOutletCode(String.valueOf(innerObj.get("TxtOutletCode")));
                _data.set_txtOutletName(String.valueOf(innerObj.get("TxtOutletName")));
                _data.set_txtUserId(String.valueOf(innerObj.get("TxtUserId")));
                _data.set_intId(String.valueOf(innerObj.get("TxtDataId")));
                _data.set_txtLatitude(String.valueOf(innerObj.get("TxtLatitude")));
                _data.set_txtLongitude(String.valueOf(innerObj.get("TxtLongitude")));
                _data.set_txtUserId(String.valueOf(innerObj.get("TxtUserId")));

                String url1 = String.valueOf(innerObj.get("TxtLinkImg1"));
                String url2 = String.valueOf(innerObj.get("TxtLinkImg2"));

                byte[] logoImage1 = getLogoImage(url1);
                byte[] logoImage2 = getLogoImage(url2);

                if (logoImage1 != null) {
                    _data.set_txtImg1(logoImage1);
                }

                if (logoImage2 != null) {
                    _data.set_txtImg2(logoImage2);
                }

                ListdataAbsen.add(_data);

            } else {
                flag = false;
                ErrorMess = (String) innerObj.get(dtAPIDATA.strMessage);
                break;
            }
        }
        new tAbsenUserBL().saveData(ListdataAbsen);
        return _array;
    }

    private List<String> SaveDatatLeaveData(JSONArray JData) {
        List<String> _array;
        APIData dtAPIDATA = new APIData();
        _array = new ArrayList<>();
        Iterator i = JData.iterator();
        Boolean flag = true;
        String ErrorMess = "";
        List<tLeaveMobileData> ListdataLeave = new ArrayList<>();
        while (i.hasNext()) {
            org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();

            int boolValid = Integer.valueOf(String.valueOf(innerObj.get(dtAPIDATA.boolValid)));
            if (boolValid == Integer.valueOf(new clsHardCode().intSuccess)) {
                tLeaveMobileData _data = new tLeaveMobileData();
                _data.set_dtLeave(String.valueOf(innerObj.get("DtLeave")));
                _data.set_intLeaveId(String.valueOf(innerObj.get("IntLeaveId")));
                _data.set_intSubmit("1");
                _data.set_intLeaveIdSync("1");
                _data.set_txtAlasan(String.valueOf(innerObj.get("TxtAlasan")));
                _data.set_txtDeviceId(String.valueOf(innerObj.get("TxtDeviceId")));
                _data.set_txtTypeAlasan(String.valueOf(innerObj.get("TxtTypeAlasan")));
                _data.set_txtDeviceId(String.valueOf(innerObj.get("TxtDeviceId")));
                _data.set_txtUserId(String.valueOf(innerObj.get("TxtUserId")));
                _data.set_txtTypeAlasanName(new mTypeLeaveBL().GetDataByintTypeLeave(_data.get_txtTypeAlasan()).get_txtTipeLeaveName());
                ListdataLeave.add(_data);
            } else {
                flag = false;
                ErrorMess = (String) innerObj.get(dtAPIDATA.strMessage);
                break;
            }
        }
        new tLeaveMobileBL().saveData(ListdataLeave);
        return _array;
    }

    private byte[] getLogoImage(String url) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL imageUrl = new URL(url);
            URLConnection ucon = imageUrl.openConnection();
            String contentType = ucon.getHeaderField("Content-Type");
            boolean image = contentType.startsWith("image/");

            if (image) {
                InputStream is = ucon.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);

                ByteArrayBuffer baf = new ByteArrayBuffer(500);
                int current = 0;
                while ((current = bis.read()) != -1) {
                    baf.append((byte) current);
                }

                return baf.toByteArray();
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d("ImageManager", "Error: " + e.toString());
        }
        return null;
    }

    private List<String> SaveDatatCustomerBasedData(JSONArray JData) {
        List<String> _array;
        APIData dtAPIDATA = new APIData();
        _array = new ArrayList<>();
        Iterator i = JData.iterator();
        Boolean flag = true;
        String ErrorMess = "";
        List<tCustomerBasedMobileHeaderData> ListdataHeader = new ArrayList<>();
        while (i.hasNext()) {
            org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();

            try {
                JSONArray JsonArray_header = new clsHelper().ResultJsonArray(String.valueOf(innerObj.get("ListDatatCustomerBasedHeader_mobile")));
                if (JsonArray_header != null) {
                    Iterator j = JsonArray_header.iterator();

                    while (j.hasNext()) {
                        tCustomerBasedMobileHeaderData _data = new tCustomerBasedMobileHeaderData();
                        org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) j.next();
                        _data.set_bitActive(String.valueOf(innerObj_detail.get("_bitActive")));
                        _data.set_dtDate(String.valueOf(innerObj_detail.get("_dtDate")));
                        _data.set_intSubmit("1");
                        _data.set_intSync("1");
                        _data.set_intPIC(String.valueOf(innerObj_detail.get("_intPIC")));
                        _data.set_txtALamat(String.valueOf(innerObj_detail.get("_txtALamat")));
                        _data.set_txtBranchCode(String.valueOf(innerObj_detail.get("_txtBranchCode")));
                        _data.set_txtDeviceId(String.valueOf(innerObj_detail.get("_txtDeviceId")));
                        _data.set_txtEmail(String.valueOf(innerObj_detail.get("_txtEmail")));
                        _data.set_txtGender(String.valueOf(innerObj_detail.get("_txtGender")));
                        _data.set_txtNamaDepan(String.valueOf(innerObj_detail.get("_txtNamaDepan")));
                        _data.set_txtNamaSumberData(String.valueOf(innerObj_detail.get("_txtNamaSumberData")));
                        _data.set_txtPINBBM(String.valueOf(innerObj_detail.get("_txtPINBBM")));
                        _data.set_txtSubmissionCode(String.valueOf(innerObj_detail.get("_txtSubmissionCode")));
                        _data.set_txtSubmissionId(String.valueOf(innerObj_detail.get("_txtSubmissionId")));
                        _data.set_txtSumberData(String.valueOf(innerObj_detail.get("_txtSumberData")));
                        _data.set_txtTelp(String.valueOf(innerObj_detail.get("_txtTelp")));
                        _data.set_txtTelpKantor(String.valueOf(innerObj_detail.get("_txtTelpKantor")));
                        _data.set_intTrCustomerId(String.valueOf(innerObj_detail.get("_txtTrCustomerId")));
                        _data.set_txtUserId(String.valueOf(innerObj_detail.get("_txtUserId")));
                        _data.set_txtTelp2(String.valueOf(innerObj_detail.get("_txtTelp2")));
                        new tCustomerBasedMobileHeaderBL().saveData(_data);
                    }

                    JSONArray JsonArray_detail = new clsHelper().ResultJsonArray(String.valueOf(innerObj.get("ListDatatCustomerBasedDetail_mobile")));
                    Iterator k = JsonArray_detail.iterator();

                    while (k.hasNext()) {
                        tCustomerBasedMobileDetailData _data = new tCustomerBasedMobileDetailData();
                        org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) k.next();
                        _data.set_bitActive(String.valueOf(innerObj_detail.get("_bitActive")));
                        _data.set_dtInserted(String.valueOf(innerObj_detail.get("_dtInserted")));
                        _data.set_dtUpdated(String.valueOf(innerObj_detail.get("_dtUpdated")));
                        _data.set_intNo(String.valueOf(innerObj_detail.get("_intNo")));
                        _data.set_intPIC(String.valueOf(innerObj_detail.get("_intPIC")));
                        _data.set_txtGender(String.valueOf(innerObj_detail.get("_txtGender")));
                        _data.set_txtInsertedBy(String.valueOf(innerObj_detail.get("_txtInsertedBy")));
                        _data.set_txtNamaDepan(String.valueOf(innerObj_detail.get("_txtNamaDepan")));
                        _data.set_intTrCustomerId(String.valueOf(innerObj_detail.get("_txtTrCustomerId")));
                        _data.set_intTrCustomerIdDetail(String.valueOf(innerObj_detail.get("_txtTrCustomerIdDetail")));
                        _data.set_txtUpdatedBy(String.valueOf(innerObj_detail.get("_txtUpdatedBy")));
                        _data.set_txtUsiaKehamilan(String.valueOf(innerObj_detail.get("_intUsiaKehamilan")));
                        _data.set_txtTglLahir(String.valueOf(innerObj_detail.get("_txtDateOfBirth")));
                        new tCustomerBasedMobileDetailBL().saveData(_data);
                    }

                    JSONArray JsonArray_detailProduct = new clsHelper().ResultJsonArray(String.valueOf(innerObj.get("ListDatatCustomerBasedDetailProduct_mobile")));
                    Iterator l = JsonArray_detailProduct.iterator();

                    while (l.hasNext()) {
                        tCustomerBasedMobileDetailProductData _data = new tCustomerBasedMobileDetailProductData();
                        org.json.simple.JSONObject innerObj_detail = (org.json.simple.JSONObject) l.next();
                        _data.set_bitActive(String.valueOf(innerObj_detail.get("_bitActive")));
                        _data.set_dtInserted(String.valueOf(innerObj_detail.get("_dtInserted")));
                        _data.set_dtUpdated(String.valueOf(innerObj_detail.get("_dtUpdated")));
                        _data.set_txtProductBrandCode(String.valueOf(innerObj_detail.get("_txtProductBrandCode")));
                        _data.set_txtProductBrandName(String.valueOf(innerObj_detail.get("_txtProductBrandName")));
                        _data.set_intTrCustomerIdDetailProduct(String.valueOf(innerObj_detail.get("_txtTrCustomerIdDetailProduct")));
                        _data.set_txtInsertedBy(String.valueOf(innerObj_detail.get("_txtInsertedBy")));
                        _data.set_intTrCustomerIdDetail(String.valueOf(innerObj_detail.get("_txtTrCustomerIdDetail")));
                        _data.set_txtUpdatedBy(String.valueOf(innerObj_detail.get("_txtUpdatedBy")));
                        _data.set_txtProductBrandQty(String.valueOf(innerObj_detail.get("_intProductBrandQty")));
                        _data.set_txtProductCompetitorCode(String.valueOf(innerObj_detail.get("_txtProductCodeCompetitor")));
                        _data.set_txtProductCompetitorName(String.valueOf(innerObj_detail.get("_txtProductNameCompetitor")));
                        new tCustomerBasedMobileDetailProductBL().saveData(_data);
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

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
                _array.add(_data.get_txtBranchCode() + " - " + _data.get_txtBranchName());
//                _Listdata.add(_data);
            } else {
                flag = false;
                ErrorMess = (String) innerObj.get(dtAPIDATA.strMessage);
                break;
            }
        }
//        new mEmployeeBranchBL().saveData(_Listdata);
        return _array;
    }

    private List<String> SaveDatamProductBarcodeData(JSONArray JData) {
        List<String> _array = new ArrayList<String>();
        APIData dtAPIDATA = new APIData();
        _array = new ArrayList<String>();
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
                _array.add(_data.get_txtProductCode() + " - " + _data.get_txtProductName());
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
            new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessCancelRequest, false);
        }

        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata != null && roledata.size() > 0) {
                loadData();
                new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessSuccessDownload, true);
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessDataNotFound, false);
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
            new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessCancelRequest, false);
        }

        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata != null && roledata.size() > 0) {
                loadData();
                new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessSuccessDownload, true);
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessDataNotFound, false);
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

    private class AsyncCallReso extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            JSONArray Json = null;
            try {
                Json = new tSalesProductHeaderBL().DownloadReso(pInfo.versionName);
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
            new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessCancelRequest, false);
        }

        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata != null && roledata.size() > 0) {
                arrData = SaveDatatSalesProductData(roledata);
                //spnBranch.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                loadData();
                new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessSuccessDownload, true);
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessDataNotFound, false);
                }

            }
            checkingDataTable();
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage("Getting Reso");
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

    private class AsyncCallActivity extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
//            android.os.Debug.waitForDebugger();
            JSONArray Json = null;
            try {
                Json = new tActivityBL().DownloadActivity(pInfo.versionName);
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
            new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessCancelRequest, false);
        }

        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata != null && roledata.size() > 0) {
                arrData = SaveDatatActivityData(roledata);
                //spnBranch.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                loadData();
                new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessSuccessDownload, true);
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessDataNotFound, false);
                }

            }
            checkingDataTable();
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage("Getting Activity");
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

    private class AsyncCallCustomerBase extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
//            android.os.Debug.waitForDebugger();
            JSONArray Json = null;
            try {
                Json = new tCustomerBasedMobileHeaderBL().DownloadCustomerBase(pInfo.versionName);
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
            new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessCancelRequest, false);
        }

        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata != null) {
                arrData = SaveDatatCustomerBasedData(roledata);
                //spnBranch.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                loadData();
                new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessSuccessDownload, true);
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessDataNotFound, false);
                }
            }
            checkingDataTable();
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage("Getting Customer Base");
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

    private class AsyncCallAbsen extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
//            android.os.Debug.waitForDebugger();
            JSONArray Json = null;
            try {
                Json = new tAbsenUserBL().DownloadAbsen(pInfo.versionName);
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
            new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessCancelRequest, false);
        }

        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata != null && roledata.size() > 0) {
                arrData = SaveDatatAbsenUserData(roledata);
                //spnBranch.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                loadData();
                new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessSuccessDownload, true);
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessDataNotFound, false);
                }

            }
            checkingDataTable();
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage("Getting Absen");
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

    private class AsyncCallDataLeave extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
//            android.os.Debug.waitForDebugger();
            JSONArray Json = null;
            try {
                Json = new tLeaveMobileBL().DownloadDataLeave(pInfo.versionName);
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
            new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessCancelRequest, false);
        }

        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata != null && roledata.size() > 0) {
                arrData = SaveDatatLeaveData(roledata);
                loadData();
                new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessSuccessDownload, true);
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessDataNotFound, false);
                }

            }
            checkingDataTable();
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage("Getting Absen");
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

    private List<String> SaveDatamEmployeeAreaData(JSONArray JData) {
        List<String> _array = new ArrayList<String>();
        APIData dtAPIDATA = new APIData();
        _array = new ArrayList<String>();
        Iterator i = JData.iterator();
        Boolean flag = true;
        String ErrorMess = "";
        List<mEmployeeAreaData> _Listdata = new ArrayList<mEmployeeAreaData>();
        while (i.hasNext()) {
            org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
            int boolValid = Integer.valueOf(String.valueOf(innerObj.get(dtAPIDATA.boolValid)));
            if (boolValid == Integer.valueOf(new clsHardCode().intSuccess)) {
//                String latitude = (String) innerObj.get("txtLatitude");
//                String longitude = (String) innerObj.get("txtLongitude");
//                if ((latitude.equals("") || latitude == null
//                        || longitude.equals("") || longitude == null)) {
//
//                    getActivity().runOnUiThread(new Runnable() {
//                        public void run() {
//                            Toast toast = Toast.makeText(getContext(), "Location Outlet Can't be Found...",
//                                    Toast.LENGTH_SHORT);
//                            toast.setGravity(Gravity.TOP, 25, 400);
//                            toast.show();
//                        }
//                    });
//
//
//                }
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
                _data.set_txtLatitude((String) innerObj.get("txtLatitude"));
                _data.set_txtLongitude((String) innerObj.get("txtLongitude"));

                //hardcode cui..
//                _data.set_txtLatitude("-6.150721");
//                _data.set_txtLongitude("106.887543");

                _array.add(_data.get_txtOutletCode() + " - " + _data.get_txtOutletName());
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
            new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessCancelRequest, false);
        }

        private ProgressDialog Dialog = new ProgressDialog(getContext());

        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata != null && roledata.size() > 0) {
                arrData = SaveDatamEmployeeAreaData(roledata);
                loadData();
                //spnOutlet.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessSuccessDownload, true);
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessDataNotFound, false);
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
            new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessCancelRequest, false);
        }

        private ProgressDialog Dialog = new ProgressDialog(getContext());

        @Override
        protected void onPostExecute(JSONArray roledata) {

            if (roledata != null && roledata.size() > 0) {
                arrData = SaveDatamEmployeeBranchData(roledata);
                //spnBranch.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                loadData();
                new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessSuccessDownload, true);
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessDataNotFound, false);
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
        List<mProductCompetitorData> mProductCompetitorDataList = new ArrayList<>();
        List<mProductSPGData> mProductSPGDataList = new ArrayList<>();
        List<mProductPICData> mProductPICDataList = new ArrayList<>();
        List<mTypeSubmissionMobile> mTypeSubmissionMobileList = new ArrayList<>();

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
        mProductCompetitorDataList = new mProductCompetitorBL().GetAllData();
        mProductSPGDataList = new mProductSPGBL().GetAllData();
        mProductPICDataList = new mProductPICBL().GetAllData();
        mTypeSubmissionMobileList = new mTypeSubmissionMobileBL().GetAllData();

        List<mEmployeeAreaData> data = _mEmployeeAreaBL.GetAllData();

        int validate = 0;

        if (mEmployeeBranchDataList.size() > 0
                && employeeSalesProductDataList.size() > 0
                && mEmployeeAreaDataList.size() > 0
                && mProductBrandHeaderDataList.size() > 0
                && mTypeLeaveMobileDataList.size() > 0
                && mProductCompetitorDataList.size() > 0
                && mProductSPGDataList.size() > 0
                && mProductPICDataList.size() > 0
                && mTypeSubmissionMobileList.size() >0) {

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

    private List<String> SaveDatamTypeLeaveMobileData(JSONArray JData) {
        List<String> _array = new ArrayList<String>();
        APIData dtAPIDATA = new APIData();
        _array = new ArrayList<String>();
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
                _array.add(_data.get_intTipeLeave() + " - " + _data.get_txtTipeLeaveName());
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

    private List<String> SaveDatammProductCompetitorData(JSONArray jsonArray) {
        List<String> _array = new ArrayList<String>();
        APIData dtAPIDATA = new APIData();
        _array = new ArrayList<String>();
        Iterator i = jsonArray.iterator();
        Boolean flag = true;
        String ErrorMess = "";
        List<mProductCompetitorData> _Listdata = new ArrayList<mProductCompetitorData>();
        while (i.hasNext()) {
            org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
            int boolValid = Integer.valueOf(String.valueOf(innerObj.get(dtAPIDATA.boolValid)));
            if (boolValid == Integer.valueOf(new clsHardCode().intSuccess)) {
                strMessage = (String) innerObj.get(dtAPIDATA.strMessage);
                mProductCompetitorData _data = new mProductCompetitorData();
                _data.set_txtID(new clsHelper().GenerateGuid());
                _data.set_txtCRMCode(String.valueOf(innerObj.get("TxtBranchCRMCode")));
                _data.set_GroupProduct(String.valueOf(innerObj.get("TxtGroupProduct")));
                _data.set_txtLobName(String.valueOf(innerObj.get("TxtLobName")));
                _data.set_txtNIK(String.valueOf(innerObj.get("TxtNIK")));
                _data.set_txtName(String.valueOf(innerObj.get("TxtName")));
                _data.set_txtProductDetailCode(String.valueOf(innerObj.get("TxtProductDetailCode")));
                _data.set_txtProdukKompetitorID(String.valueOf(innerObj.get("TxtProdukKompetitorID")));
                _data.set_txtProdukid(String.valueOf(innerObj.get("TxtProdukid")));
                _array.add(_data.get_txtProdukKompetitorID());
                _Listdata.add(_data);
            } else {
                flag = false;
                strMessage = (String) innerObj.get(dtAPIDATA.strMessage);
                break;
            }
        }
        new mProductCompetitorBL().deleteAllData();
        new mProductCompetitorBL().saveData(_Listdata);
        return _array;
    }

    private List<String> SaveDatammProductSPGData(JSONArray jsonArray) {
        List<String> _array = new ArrayList<String>();
        APIData dtAPIDATA = new APIData();
        _array = new ArrayList<String>();
        Iterator i = jsonArray.iterator();
        Boolean flag = true;
        String ErrorMess = "";
        List<mProductSPGData> _Listdata = new ArrayList<mProductSPGData>();
        new mProductSPGBL().deleteAllData();
        int intsum = new mProductSPGBL().getContactCount();
        while (i.hasNext()) {
            org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
            int boolValid = Integer.valueOf(String.valueOf(innerObj.get(dtAPIDATA.boolValid)));
            if (boolValid == Integer.valueOf(new clsHardCode().intSuccess)) {
                strMessage = (String) innerObj.get(dtAPIDATA.strMessage);
                intsum+=1;
                mProductSPGData _data = new mProductSPGData();
                _data.set_intId(String.valueOf(intsum));
                _data.set_decBobot((String) innerObj.get("DecBobot"));
                _data.set_decHJD((String) innerObj.get("DecHJD"));
                _data.set_txtBrandDetailGramCode((String) innerObj.get("TxtBrandDetailGramCode"));
                _data.set_txtNIK((String) innerObj.get("TxtNIK"));
                _data.set_txtName((String) innerObj.get("TxtName"));
                _data.set_txtProductBrandDetailGramName((String) innerObj.get("TxtProductBrandDetailGramName"));
                _data.set_txtProductDetailCode((String) innerObj.get("TxtProductDetailCode"));
                _data.set_txtProductDetailName((String) innerObj.get("TxtProductDetailName"));
                _data.set_txtLobName((String) innerObj.get("TxtLobName"));
                _data.set_txtMasterId((String) innerObj.get("TxtMasterId"));
                _data.set_txtNamaMasterData((String) innerObj.get("TxtNamaMasterData"));
                _data.set_txtKeterangan((String) innerObj.get("TxtKeterangan"));
                _Listdata.add(_data);
            } else {
                flag = false;
                strMessage = (String) innerObj.get(dtAPIDATA.strMessage);
                break;
            }
        }
        new mProductSPGBL().saveData(_Listdata);
        return _array;
    }

    private List<String> SaveDatammProductPICData(JSONArray jsonArray) {
        List<String> _array = new ArrayList<String>();
        APIData dtAPIDATA = new APIData();
        _array = new ArrayList<String>();
        Iterator i = jsonArray.iterator();
        Boolean flag = true;
        String ErrorMess = "";
        List<mProductPICData> _Listdata = new ArrayList<mProductPICData>();
        new mProductPICBL().deleteAllData();
        int intsum = new mProductPICBL().getContactCount();
        while (i.hasNext()) {
            org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
            int boolValid = Integer.valueOf(String.valueOf(innerObj.get(dtAPIDATA.boolValid)));
            if (boolValid == Integer.valueOf(new clsHardCode().intSuccess)) {
                strMessage = (String) innerObj.get(dtAPIDATA.strMessage);
                intsum+=1;
                mProductPICData _data = new mProductPICData();
                _data.set_intId(String.valueOf(intsum));
                _data.set_decBobot((String) innerObj.get("DecBobot"));
                _data.set_decHJD((String) innerObj.get("DecHJD"));
                _data.set_txtBrandDetailGramCode((String) innerObj.get("TxtBrandDetailGramCode"));
                _data.set_txtNIK((String) innerObj.get("TxtNIK"));
                _data.set_txtName((String) innerObj.get("TxtName"));
                _data.set_txtProductBrandDetailGramName((String) innerObj.get("TxtProductBrandDetailGramName"));
                _data.set_txtProductDetailCode((String) innerObj.get("TxtProductDetailCode"));
                _data.set_txtProductDetailName((String) innerObj.get("TxtProductDetailName"));
                _data.set_txtLobName((String) innerObj.get("TxtLobName"));
                _data.set_txtMasterId((String) innerObj.get("TxtMasterId"));
                _data.set_txtNamaMasterData((String) innerObj.get("TxtNamaMasterData"));
                _data.set_txtKeterangan((String) innerObj.get("TxtKeterangan"));
                _Listdata.add(_data);
            } else {
                flag = false;
                strMessage = (String) innerObj.get(dtAPIDATA.strMessage);
                break;
            }
        }
        new mProductPICBL().saveData(_Listdata);
        return _array;
    }

    private List<String> SaveDatamTypeSubmissionMobile(JSONArray jsonArray) {
        List<String> _array = new ArrayList<String>();
        APIData dtAPIDATA = new APIData();
        _array = new ArrayList<String>();
        Iterator i = jsonArray.iterator();
        Boolean flag = true;
        String ErrorMess = "";
        List<mTypeSubmissionMobile> _Listdata = new ArrayList<mTypeSubmissionMobile>();
        while (i.hasNext()) {
            org.json.simple.JSONObject innerObj = (org.json.simple.JSONObject) i.next();
            int boolValid = Integer.valueOf(String.valueOf(innerObj.get(dtAPIDATA.boolValid)));
            if (boolValid == Integer.valueOf(new clsHardCode().intSuccess)) {
                strMessage = (String) innerObj.get(dtAPIDATA.strMessage);
                mTypeSubmissionMobile _data = new mTypeSubmissionMobile();
                _data.set_txtMasterID(String.valueOf(innerObj.get("TxtMasterID")));
                _data.set_txtGrupMasterID(String.valueOf(innerObj.get("TxtGrupMasterID")));
                _data.set_txtKeterangan(String.valueOf(innerObj.get("TxtKeterangan")));
                _data.set_txtNamaMasterData(String.valueOf(innerObj.get("TxtNamaMasterData")));
                _data.set_intLastActiveSelection("0");
                _array.add(_data.get_txtMasterID());
                _Listdata.add(_data);
            } else {
                flag = false;
                strMessage = (String) innerObj.get(dtAPIDATA.strMessage);
                break;
            }
        }
        new mTypeSubmissionMobileBL().saveData(_Listdata);
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
            new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessCancelRequest, false);
        }

        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata != null && roledata.size() > 0) {
                arrData = SaveDatamTypeLeaveMobileData(roledata);
                //spnLeave.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                loadData();
                new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessSuccessDownload, true);
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessDataNotFound, false);
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

    private class AsyncCallDataProdComp extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            JSONArray Json = null;
            try {
                Json = new mProductCompetitorBL().DownloadProdctCompetitor(pInfo.versionName, loginData.get_txtUserId(), loginData.get_TxtEmpId());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Json;
        }
        private ProgressDialog Dialog = new ProgressDialog(getContext());

        @Override
        protected void onCancelled(JSONArray jsonArray) {
            Dialog.dismiss();
            new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessCancelRequest, false);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if (jsonArray != null && jsonArray.size() > 0) {
                arrData = SaveDatammProductCompetitorData(jsonArray);
                //spnLeave.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                loadData();
                new clsMainActivity().showCustomToast(getContext(), strMessage, true);
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessDataNotFound, false);
                }

            }
            checkingDataTable();
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage("Getting Product Competitor");
            Dialog.setCancelable(false);
            Dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    intProcesscancel = 1;
                }
            });
            Dialog.show();
        }
    }

    private class AsyncCallDataProdSPGCusBased extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            JSONArray Json = null;
            try {
                Json = new mProductSPGBL().DownloadProductSPG(pInfo.versionName, loginData.get_txtUserId(), loginData.get_TxtEmpId());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Json;
        }
        private ProgressDialog Dialog = new ProgressDialog(getContext());

        @Override
        protected void onCancelled(JSONArray jsonArray) {
            Dialog.dismiss();
            new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessCancelRequest, false);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if (jsonArray != null && jsonArray.size() > 0) {
                arrData = SaveDatammProductSPGData(jsonArray);
                //spnLeave.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                loadData();
                new clsMainActivity().showCustomToast(getContext(), strMessage, true);
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessDataNotFound, false);
                }

            }
            checkingDataTable();
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage("Getting Product SPG");
            Dialog.setCancelable(false);
            Dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    intProcesscancel = 1;
                }
            });
            Dialog.show();
        }
    }

    private class AsyncCallDataProdPICCusBased extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            JSONArray Json = null;
            try {
                Json = new mProductPICBL().DownloadProductPIC(pInfo.versionName, loginData.get_txtUserId(), loginData.get_TxtEmpId());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Json;
        }
        private ProgressDialog Dialog = new ProgressDialog(getContext());

        @Override
        protected void onCancelled(JSONArray jsonArray) {
            Dialog.dismiss();
            new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessCancelRequest, false);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if (jsonArray != null && jsonArray.size() > 0) {
                arrData = SaveDatammProductPICData(jsonArray);
                //spnLeave.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                loadData();
                new clsMainActivity().showCustomToast(getContext(), strMessage, true);
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessDataNotFound, false);
                }

            }
            checkingDataTable();
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage("Getting Product PIC");
            Dialog.setCancelable(false);
            Dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    intProcesscancel = 1;
                }
            });
            Dialog.show();
        }
    }

    private class AsyncCallTypeSubmission extends AsyncTask<JSONArray, Void, JSONArray>{
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            JSONArray Json = null;
            try {
                Json = new mTypeSubmissionMobileBL().DownloadTypeSubmission(pInfo.versionName, loginData.get_txtUserId(), loginData.get_TxtEmpId());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Json;
        }
        private ProgressDialog Dialog = new ProgressDialog(getContext());

        @Override
        protected void onCancelled(JSONArray jsonArray) {
            Dialog.dismiss();
            new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessCancelRequest, false);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if (jsonArray != null && jsonArray.size() > 0) {
                arrData = SaveDatamTypeSubmissionMobile(jsonArray);
                //spnLeave.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
                loadData();
                new clsMainActivity().showCustomToast(getContext(), strMessage, true);
            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    new clsMainActivity().showCustomToast(getContext(), new clsHardCode().txtMessDataNotFound, false);
                }

            }
            checkingDataTable();
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // Make ProgressBar invisible
            // pg.setVisibility(View.VISIBLE);
            Dialog.setMessage("Getting Type Submission");
            Dialog.setCancelable(false);
            Dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    intProcesscancel = 1;
                }
            });
            Dialog.show();
        }
    }
}
