package com.kalbenutritionals.app.kalbespgmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bl.tCustomerBasedMobileDetailBL;
import bl.tCustomerBasedMobileDetailProductBL;
import bl.tCustomerBasedMobileHeaderBL;
import edu.swu.pulltorefreshswipemenulistview.library.PullToRefreshSwipeMenuListView;
import edu.swu.pulltorefreshswipemenulistview.library.pulltorefresh.interfaces.IXListViewListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.bean.SwipeMenu;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.OnMenuItemClickListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.SwipeMenuCreator;
import edu.swu.pulltorefreshswipemenulistview.library.util.RefreshTime;
import library.salesforce.common.AppAdapter;
import library.salesforce.common.clsSwipeList;
import library.salesforce.common.tCustomerBasedMobileDetailData;
import library.salesforce.common.tCustomerBasedMobileDetailProductData;
import library.salesforce.common.tCustomerBasedMobileHeaderData;

public class FragmentViewCustomerBaseSPG extends Fragment implements IXListViewListener {

    private static List<clsSwipeList> swipeList = new ArrayList<clsSwipeList>();
    private AppAdapter mAdapter;
    private PullToRefreshSwipeMenuListView mListView;
    private Handler mHandler;
    private static Map<String, HashMap> mapMenu;

    static List<tCustomerBasedMobileHeaderData> dt;
//    static FragmentViewCustomerBase instance;

    View v;

//    public static FragmentViewCustomerBaseSPG getInstance() {
//        if(instance == null){
//            instance = new FragmentViewCustomerBaseSPG();
//            instance = new FragmentViewCustomerBaseSPG();
//        }
//        return instance;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_customerbase_view,container,false);

        //instance = this;

        clsSwipeList swplist;
        dt = new tCustomerBasedMobileHeaderBL().getAllData();

        swipeList.clear();

        for(int i = 0; i < dt.size(); i++){
            swplist = new clsSwipeList();
            swplist.set_txtTitle("Code : " + dt.get(i).get_txtSubmissionId());
            swplist.set_txtDescription("Nama : " + dt.get(i).get_txtNamaDepan());
            swipeList.add(swplist);
        }

        clsMainActivity clsMain = new clsMainActivity();

        mListView = (PullToRefreshSwipeMenuListView) v.findViewById(R.id.listView);
        mAdapter = clsMain.setList(getActivity().getApplicationContext(), swipeList);
        mListView.setAdapter(mAdapter);
        mListView.setPullRefreshEnable(false);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
        mHandler = new Handler();

        HashMap<String, String> mapView = new HashMap<String, String>();

        mapView.put("name", "View");
        mapView.put("bgColor", "#3498db");

        mapMenu = new HashMap<String, HashMap>();
        mapMenu.put("0", mapView);

        SwipeMenuCreator creator = clsMain.setCreator(getActivity().getApplicationContext(), mapMenu);
        mListView.setMenuCreator(creator);
        mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                clsSwipeList item = swipeList.get(position);
                switch (index) {
                    case 0:
                        viewList(getActivity().getApplicationContext(), position);
                }
            }
        });

        return v;
    }

    private void onLoad() {
        mListView.setRefreshTime(RefreshTime.getRefreshTime(getActivity().getApplicationContext()));
        mListView.stopRefresh();

        mListView.stopLoadMore();

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoad();
            }
        }, 1);
    }

    private void viewList(Context ctx, int position) {

        LayoutInflater layoutInflater = LayoutInflater.from(this.getContext());
        final View promptView = layoutInflater.inflate(R.layout.activity_preview_customerbase, null);

        final TextView _tvSubmissionId = (TextView) promptView.findViewById(R.id.tvSubmissionId);
        final TextView _tvNama = (TextView) promptView.findViewById(R.id.tvNama);
        final TextView _tvTelp = (TextView) promptView.findViewById(R.id.tvTelp);
        final TextView _tvTelpKantor = (TextView) promptView.findViewById(R.id.tvTelpKantor);
        final TextView _tvEmail = (TextView) promptView.findViewById(R.id.tvEmail);
        final TextView _tvPinBbm = (TextView) promptView.findViewById(R.id.tvPinBbm);
        final TextView _tvAlamat = (TextView) promptView.findViewById(R.id.tvAlamat);
        final TextView _tvPicPelanggan = (TextView) promptView.findViewById(R.id.tvPicPelanggan);
//        final ListView _lvProduk = (ListView) promptView.findViewById(R.id.lvProduks);

        _tvSubmissionId.setText(dt.get(position).get_txtSubmissionId());
        _tvNama.setText(dt.get(position).get_txtNamaDepan());
        _tvTelp.setText(dt.get(position).get_txtTelp());
        _tvTelpKantor.setText(dt.get(position).get_txtTelpKantor());
        _tvEmail.setText(dt.get(position).get_txtEmail());
        _tvPinBbm.setText(dt.get(position).get_txtPINBBM());
        _tvAlamat.setText(dt.get(position).get_txtALamat());
        _tvPicPelanggan.setText(Integer.parseInt(dt.get(position).get_intPIC()) == 1 ? "Yes" : "No");

        TableLayout tl = (TableLayout) promptView.findViewById(R.id.tlPerson);
        tl.removeAllViews();

        TableRow row = new TableRow(getContext());
        row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        String[] colTextHeader = {"Nama", "Produk"};

        for (String text : colTextHeader) {
            TextView tv = new TextView(getContext());
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

            tv.setTextSize(14);
            tv.setPadding(10, 10, 10, 10);
            tv.setText(text);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundColor(Color.parseColor("#4CAF50"));

            tv.setTextColor(Color.WHITE);
            row.addView(tv);
        }
        tl.addView(row);

        final List<tCustomerBasedMobileDetailData> dtListDetail = new tCustomerBasedMobileDetailBL().getAllDataByHeaderId(dt.get(position).get_intTrCustomerId());

        for (int count = 0; count < dtListDetail.size(); count++) {

            List<tCustomerBasedMobileDetailProductData> dtListProduct = new tCustomerBasedMobileDetailProductBL().getDataByCustomerDetailId(dtListDetail.get(count).get_intTrCustomerIdDetail());

            if (dtListProduct != null) {
                row = new TableRow(getContext());
                row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                String product = "";
                for (tCustomerBasedMobileDetailProductData dataproduct : dtListProduct) {
                    product = product + "- " + dataproduct.get_txtProductBrandName() + " (" + dataproduct.get_txtProductBrandQty() + ")\n";
                }
                String[] colText = {dtListDetail.get(count).get_txtNamaDepan(), product};

                int j = 1;

                for (String text : colText) {
                    TextView tv = new TextView(getContext());

                    if (j % 2 == 0) {
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1));
                    } else {
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));
                    }
                    tv.setTextSize(14);
                    tv.setPadding(10, 10, 10, 10);
                    tv.setText(text);
                    tv.setGravity(Gravity.LEFT);

                    if (count % 2 == 0) {
                        tv.setBackgroundColor(Color.parseColor("#F0F0F0"));
                    } else {
                        tv.setBackgroundColor(Color.GRAY);
                    }

                    tv.setTextColor(Color.BLACK);

                    row.addView(tv);
                    j++;
                }
                tl.addView(row);
            }
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder
                .setCancelable(false)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }

}