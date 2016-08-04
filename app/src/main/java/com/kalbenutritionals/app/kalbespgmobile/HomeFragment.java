package com.kalbenutritionals.app.kalbespgmobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import bl.mEmployeeSalesProductBL;
import bl.mProductBrandHeaderBL;
import bl.tAbsenUserBL;
import bl.tActivityBL;
import bl.tCustomerBaseBL;
import bl.tSalesProductHeaderBL;
import bl.tUserLoginBL;
import library.salesforce.common.mEmployeeSalesProductData;
import library.salesforce.common.mProductBrandHeaderData;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tActivityData;
import library.salesforce.common.tCustomerBaseData;
import library.salesforce.common.tSalesProductHeaderData;
import library.salesforce.common.tUserLoginData;

public class HomeFragment extends Fragment {

    View v;
    private Toolbar toolbar;
    TextView username, branch, outlet, statusAbsen, totalBrand, totalProduct, totalReso, totalActivity, totalCustomerBase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_home,container,false);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) v.findViewById(R.id.coordinatorLayout);
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Welcome to AndroidHive", Snackbar.LENGTH_LONG);

        snackbar.show();


        username = (TextView) v.findViewById(R.id.username);
        branch = (TextView) v.findViewById(R.id.branch);
        outlet = (TextView) v.findViewById(R.id.outlet);
        statusAbsen = (TextView) v.findViewById(R.id.statusAbsen);
        totalBrand = (TextView) v.findViewById(R.id.totalBrand);
        totalProduct = (TextView) v.findViewById(R.id.totalProduct);
        totalReso = (TextView) v.findViewById(R.id.totalReso);
        totalActivity = (TextView) v.findViewById(R.id.totalActivity);
        totalCustomerBase = (TextView) v.findViewById(R.id.totalCustomerBase);

        tUserLoginData dt=new tUserLoginBL().getUserActive();
        tAbsenUserData dtAbsen = new tAbsenUserBL().getDataCheckInActive();
        List<mEmployeeSalesProductData> dtBrand = new mProductBrandHeaderBL().GetAllData();
        List<mEmployeeSalesProductData> dtProduct = new mEmployeeSalesProductBL().GetAllData();
        List<tSalesProductHeaderData> dtReso = new tSalesProductHeaderBL().getAllSalesProductHeader();
        List<tActivityData> dtActivity = new tActivityBL().getAllData();
        List<tCustomerBaseData> dtCustomerBase = new tCustomerBaseBL().getAllCustomerBase();

        username.setText(dt.get_txtName());
        branch.setText(dt.get_txtBranchCode());
        outlet.setText(dt.get_txtOutletName());
        statusAbsen.setText(dtAbsen != null ? "Active" : "Inactive");
        totalBrand.setText(String.valueOf(dtBrand.size()));
        totalProduct.setText(String.valueOf(dtProduct.size()));

        if(dtReso != null) totalReso.setText(String.valueOf(dtReso.size()));
        if(dtActivity != null) totalActivity.setText(String.valueOf(dtActivity.size()));
        if(dtCustomerBase != null) totalCustomerBase.setText(String.valueOf(dtCustomerBase.size()));

        return v;
    }

}
