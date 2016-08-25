package com.kalbenutritionals.app.kalbespgmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import bl.tSalesProductDetailBL;
import bl.tSalesProductHeaderBL;
import edu.swu.pulltorefreshswipemenulistview.library.PullToRefreshSwipeMenuListView;
import edu.swu.pulltorefreshswipemenulistview.library.pulltorefresh.interfaces.IXListViewListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.bean.SwipeMenu;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.OnMenuItemClickListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.SwipeMenuCreator;
import edu.swu.pulltorefreshswipemenulistview.library.util.RefreshTime;
import library.salesforce.common.AppAdapter;
import library.salesforce.common.clsSwipeList;
import library.salesforce.common.tSalesProductDetailData;
import library.salesforce.common.tSalesProductHeaderData;

/**
 * Created by ASUS ZE on 26/07/2016.
 */
public class FragmentViewResoSPG extends Fragment implements IXListViewListener {
    private static List<clsSwipeList> swipeList = new ArrayList<clsSwipeList>();
    private AppAdapter mAdapter;
    private PullToRefreshSwipeMenuListView mListView;
    private Handler mHandler;
    private static Map<String, HashMap> mapMenu;
    static List<tSalesProductHeaderData> dt;
    static List<tSalesProductDetailData> data;

    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_customerbase_view,container,false);

        clsSwipeList swplist;
        dt = new tSalesProductHeaderBL().getAllSalesProductHeader();

        swipeList.clear();

        if(dt!=null) {
            for (int i = 0; i < dt.size(); i++) {
                swplist = new clsSwipeList();
                swplist.set_txtTitle(dt.get(i).get_intId());
                if (dt.get(i).get_intSubmit().equals("1")&&dt.get(i).get_intSync().equals("0")){
                    swplist.set_txtDescription("Submit");
                } else if (dt.get(i).get_intSubmit().equals("1")&&dt.get(i).get_intSync().equals("1")){
                    swplist.set_txtDescription("Sync");
                }

                swipeList.add(swplist);
            }
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

    private void onLoad() {
        mListView.setRefreshTime(RefreshTime.getRefreshTime(getActivity().getApplicationContext()));
        mListView.stopRefresh();

        mListView.stopLoadMore();

    }

    private void viewList(Context ctx, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View promptView = layoutInflater.inflate(R.layout.activity_preview_so, null);

        final TextView _tvNoSO = (TextView) promptView.findViewById(R.id.tvnoSOtbl);
        final TextView _tvKet = (TextView) promptView.findViewById(R.id.tvkettbl);
        _tvNoSO.setText(": " + dt.get(position).get_intId());
        _tvKet.setText(": " + dt.get(position).get_txtKeterangan());
        final TextView tv_item = (TextView) promptView.findViewById(R.id.tvItemtbl);
        tv_item.setTypeface(null, Typeface.BOLD);
        tv_item.setText(": " + String.valueOf(dt.get(position).get_intSumItem()));
        final  TextView tv_amount = (TextView) promptView.findViewById(R.id.tvSumAmount) ;
        tv_amount.setTypeface(null, Typeface.BOLD);
        tv_amount.setText(": " + new clsMainActivity().convertNumberDec(Double.valueOf(dt.get(position).get_intSumAmount())));
        final  TextView tv_status = (TextView) promptView.findViewById(R.id.tvStatus);
        tv_status.setTypeface(null, Typeface.BOLD);

        if (dt.get(position).get_intSubmit().equals("1")&&dt.get(position).get_intSync().equals("0")){
            tv_status.setText(": Submit");
        } else if (dt.get(position).get_intSubmit().equals("1")&&dt.get(position).get_intSync().equals("1")){
            tv_status.setText(": Sync");
        }


        data = new tSalesProductDetailBL().GetDataByNoSO(dt.get(position).get_intId());
        TableLayout tl = new TableLayout(getContext());
        double qtySum=0;
        double qtyNum;
        for(tSalesProductDetailData dat : data){
            TableRow tr = new TableRow(getContext());
            TableLayout.LayoutParams tableRowParams=
                    new TableLayout.LayoutParams
                            (TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin=10;
            int topMargin=2;
            int rightMargin=10;
            int bottomMargin=2;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

            tr.setLayoutParams(tableRowParams);

            TextView product = new TextView(getContext());
            product.setTextSize(12);
            product.setWidth(300);
            product.setText(dat.get_txtCodeProduct() + " " + dat.get_txtNameProduct());
            tr.addView(product);

            TextView qty = new TextView(getContext());
            qty.setTextSize(12);
            qty.setPadding(15, 0, 0, 0);
            qty.setText(String.valueOf(dat.get_intQty()));
            tr.addView(qty);

            TextView price = new TextView(getContext());
            price.setTextColor(Color.GREEN);
            price.setTextSize(12);
            price.setPadding(15, 0, 0, 0);
            price.setText(new clsMainActivity().convertNumberDec(Double.valueOf(dat.get_intPrice())));
            tr.addView(price);

            TextView amount = new TextView(getContext());
            amount.setTextColor(Color.RED);
            amount.setTextSize(12);
            amount.setPadding(15, 0, 0, 0);
            double prc = Double.valueOf(dat.get_intPrice());
            double itm = Double.valueOf(dat.get_intQty());
            qtyNum = prc*itm;
            qtySum += qtyNum;
            amount.setText(new clsMainActivity().convertNumberDec(qtyNum));
            tr.addView(amount);

            tl.addView(tr, tableRowParams);
        }
        TableLayout tlb = (TableLayout) promptView.findViewById(R.id.tlProduct) ;
        tlb.addView(tl);

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
