package com.kalbenutritionals.app.kalbespgmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bl.tAbsenUserBL;
import bl.tActivityBL;
import edu.swu.pulltorefreshswipemenulistview.library.PullToRefreshSwipeMenuListView;
import edu.swu.pulltorefreshswipemenulistview.library.pulltorefresh.interfaces.IXListViewListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.bean.SwipeMenu;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.OnMenuItemClickListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.SwipeMenuCreator;
import edu.swu.pulltorefreshswipemenulistview.library.util.RefreshTime;
import library.salesforce.common.AppAdapter;
import library.salesforce.common.clsSwipeList;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tActivityData;

public class FragmentViewActvitySPG extends Fragment implements IXListViewListener {

    private static List<clsSwipeList> swipeList = new ArrayList<clsSwipeList>();
    private AppAdapter mAdapter;
    private PullToRefreshSwipeMenuListView mListView;
    private Handler mHandler;
    private static Map<String, HashMap> mapMenu;

    static List<tActivityData> dt;

    private static Bitmap mybitmap1;
    private static Bitmap mybitmap2;

    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_customerbase_view,container,false);

        clsSwipeList swplist;

        tAbsenUserData dtAbsen = new tAbsenUserBL().getDataCheckInActive();

        dt = new tActivityBL().getAllDataByOutletCode(dtAbsen.get_txtOutletCode());

        swipeList.clear();


        for(int i = 0; i < dt.size(); i++){
            String status = dt.get(i).get_intSubmit().equals("1") && dt.get(i).get_intIdSyn().equals("1") ? "Sync" : "Submit";
            swplist = new clsSwipeList();
            swplist.set_txtTitle("Type : " + dt.get(i).get_intFlag());
            swplist.set_txtDescription("Description : " + dt.get(i).get_txtDesc() + "\n" + status);
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

    public void onRefresh() {
    }

    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoad();
            }
        }, 1);
    }

    private void viewList(Context ctx, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.getContext());
        final View promptView = layoutInflater.inflate(R.layout.fragment_activity_add, null);

        final EditText etDesc = (EditText) promptView.findViewById(R.id.etNama);
        final ImageButton img1 = (ImageButton) promptView.findViewById(R.id.imageButton);
        final ImageButton img2 = (ImageButton) promptView.findViewById(R.id.imageButton2);
        final Button btnSave = (Button) promptView.findViewById(R.id.btnSave);
        final RadioButton rbKalbe = (RadioButton) promptView.findViewById(R.id.rbKalbe);
        final RadioButton rbCompetitor = (RadioButton) promptView.findViewById(R.id.rbCompetitor);

        if(dt.get(position).get_intFlag().equals("KALBE")) {
            rbKalbe.setChecked(true);
            rbCompetitor.setChecked(false);
        }
        else if(dt.get(position).get_intFlag().equals("Competitor")) {
            rbKalbe.setChecked(false);
            rbCompetitor.setChecked(true);
        }
        else{
            rbKalbe.setChecked(false);
            rbCompetitor.setChecked(false);
        }

        rbKalbe.setTextColor(Color.BLACK);
        rbKalbe.setEnabled(false);
        rbCompetitor.setEnabled(false);

        img1.setEnabled(true);
        img2.setEnabled(true);

        btnSave.setVisibility(View.GONE);
        etDesc.setText(dt.get(position).get_txtDesc());
        etDesc.setTextColor(Color.BLACK);
        etDesc.setEnabled(false);

        final byte[] imgFile = dt.get(position).get_txtImg1();
        if(imgFile!=null){
            mybitmap1 = BitmapFactory.decodeByteArray(imgFile, 0 , imgFile.length);
            Bitmap bitmap = Bitmap.createScaledBitmap(mybitmap1, 150, 150, true);
            img1.setImageBitmap(bitmap);
        }
        else{
            img1.setVisibility(View.GONE);
        }
        final byte[] imgFile2 = dt.get(position).get_txtImg2();
        if(imgFile2!=null){
            mybitmap2 = BitmapFactory.decodeByteArray(imgFile2, 0 , imgFile2.length);
            Bitmap bitmap = Bitmap.createScaledBitmap(mybitmap2, 150, 150, true);
            img2.setImageBitmap(bitmap);
        }
        else{
            img2.setVisibility(View.GONE);
        }

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());
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

        img1.setClickable(true);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new clsMainActivity().zoomImage(mybitmap1, getActivity());
            }
        });

        img2.setClickable(true);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new clsMainActivity().zoomImage(mybitmap2, getActivity());
            }
        });
    }
}
