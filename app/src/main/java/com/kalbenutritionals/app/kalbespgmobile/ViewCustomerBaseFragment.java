package com.kalbenutritionals.app.kalbespgmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bl.tCustomerBaseBL;
import bl.tCustomerBaseDetailBL;
import edu.swu.pulltorefreshswipemenulistview.library.PullToRefreshSwipeMenuListView;
import edu.swu.pulltorefreshswipemenulistview.library.pulltorefresh.interfaces.IXListViewListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.bean.SwipeMenu;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.OnMenuItemClickListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.SwipeMenuCreator;
import edu.swu.pulltorefreshswipemenulistview.library.util.RefreshTime;
import library.salesforce.common.AppAdapter;
import library.salesforce.common.clsSwipeList;
import library.salesforce.common.tCustomerBaseData;
import library.salesforce.common.tCustomerBaseDetailData;

public class ViewCustomerBaseFragment extends Fragment implements IXListViewListener {

    private static List<clsSwipeList> swipeList = new ArrayList<clsSwipeList>();
    private AppAdapter mAdapter;
    private PullToRefreshSwipeMenuListView mListView;
    private Handler mHandler;
    private static Map<String, HashMap> mapMenu;

    static List<tCustomerBaseData> dt;
    static ViewCustomerBaseFragment instance;

    View v;

    public static ViewCustomerBaseFragment getInstance() {
        if(instance == null){
            instance = new ViewCustomerBaseFragment ();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_customerbase_view,container,false);

        instance = this;

        clsSwipeList swplist;
        dt = new tCustomerBaseBL().getAllCustomerBase();

        swipeList.clear();

        for(int i = 0; i < dt.size(); i++){
            swplist = new clsSwipeList();
            swplist.set_txtTitle("Nama : " + dt.get(i).get_txtNama());
            swplist.set_txtDescription("No telp : " + dt.get(i).get_txtTelp());
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

        final TextView _tvSex = (TextView) promptView.findViewById(R.id.tvTypeSex);
        final TextView _tvNama = (TextView) promptView.findViewById(R.id.tvNama);
        final TextView _tvTelp = (TextView) promptView.findViewById(R.id.tvNoTelp);
        final TextView _tvAlamat = (TextView) promptView.findViewById(R.id.tvAlamat);
        final TextView _tvStatus = (TextView) promptView.findViewById(R.id.tvStatus);
        final ListView _lvProduk = (ListView) promptView.findViewById(R.id.lvProduks);

        _tvSex.setText(dt.get(position).get_txtSex());
        _tvNama.setText(dt.get(position).get_txtNama());
        _tvTelp.setText(dt.get(position).get_txtTelp());
        _tvAlamat.setText(dt.get(position).get_txtAlamat());
        _tvStatus.setText("Open");

        List<tCustomerBaseDetailData> dtDetail = new tCustomerBaseDetailBL().getData("'" + dt.get(position).get_intCustomerId() + "'");
        List<String> item = new ArrayList<>();

        for(int i = 0; i < dtDetail.size(); i++){
            item.add(dtDetail.get(i).get_txtProductBrandName());
        }

        _lvProduk.setAdapter(new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, item));

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
