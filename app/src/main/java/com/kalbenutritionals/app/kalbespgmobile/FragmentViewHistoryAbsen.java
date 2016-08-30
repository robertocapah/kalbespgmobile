package com.kalbenutritionals.app.kalbespgmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bl.tAbsenUserBL;
import edu.swu.pulltorefreshswipemenulistview.library.PullToRefreshSwipeMenuListView;
import edu.swu.pulltorefreshswipemenulistview.library.pulltorefresh.interfaces.IXListViewListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.bean.SwipeMenu;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.OnMenuItemClickListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.SwipeMenuCreator;
import edu.swu.pulltorefreshswipemenulistview.library.util.RefreshTime;
import library.salesforce.common.AppAdapter;
import library.salesforce.common.clsSwipeList;
import library.salesforce.common.tAbsenUserData;

public class FragmentViewHistoryAbsen extends Fragment implements IXListViewListener {

    private static List<clsSwipeList> swipeList = new ArrayList<clsSwipeList>();
    private AppAdapter mAdapter;
    private PullToRefreshSwipeMenuListView mListView;
    private Handler mHandler;
    private static Map<String, HashMap> mapMenu;

    static List<tAbsenUserData> dt;

    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_customerbase_view, container, false);

        clsSwipeList swplist;
        dt = new tAbsenUserBL().getAllDataActive();

        swipeList.clear();

        if (dt!=null){
            for (int i = 0; i < dt.size(); i++) {
                swplist = new clsSwipeList();
                swplist.set_txtTitle("Outlet : " + dt.get(i).get_txtOutletName());
                swplist.set_txtDescription("Check in : " + dt.get(i).get_dtDateCheckIn() + "\n" + "Check out : " + dt.get(i).get_dtDateCheckOut());
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

    private void viewList(Context ctx, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.getContext());
        final View promptView = layoutInflater.inflate(R.layout.popup_view_history_absen, null);

        final TextView tvUsername = (TextView) promptView.findViewById(R.id.tvUsername);
        final TextView tvBranch = (TextView) promptView.findViewById(R.id.tvBranch);
        final TextView tvOutlet = (TextView) promptView.findViewById(R.id.tvOutlet);
        final TextView tvCheckin = (TextView) promptView.findViewById(R.id.tvCheckin);
        final TextView tvCheckout = (TextView) promptView.findViewById(R.id.tvCheckout);

        final ImageView imgAbsen1 = (ImageView) promptView.findViewById(R.id.imgAbsen1);
        final ImageView imgAbsen2 = (ImageView) promptView.findViewById(R.id.imgAbsen2);

        tvUsername.setText(dt.get(position).get_txtUserId());
        tvBranch.setText(dt.get(position).get_txtBranchName());
        tvOutlet.setText(dt.get(position).get_txtOutletName());
        tvCheckin.setText(dt.get(position).get_dtDateCheckIn());
        tvCheckout.setText(dt.get(position).get_dtDateCheckOut());

        byte[] imgFile = dt.get(position).get_txtImg1();
        if (imgFile != null) {
            Bitmap myBitmap = BitmapFactory.decodeByteArray(imgFile, 0, imgFile.length);
            imgAbsen1.setImageBitmap(myBitmap);
        } else {
            imgAbsen1.setVisibility(View.INVISIBLE);
        }
        byte[] imgFile2 = dt.get(position).get_txtImg2();
        if (imgFile2 != null && imgFile != null) {
            Bitmap myBitmap = BitmapFactory.decodeByteArray(imgFile2, 0, imgFile2.length);
            imgAbsen2.setImageBitmap(myBitmap);
        }
        else if(imgFile2 != null && imgFile == null){
            Bitmap myBitmap = BitmapFactory.decodeByteArray(imgFile2, 0, imgFile2.length);
            imgAbsen1.setVisibility(View.VISIBLE);
            imgAbsen1.setImageBitmap(myBitmap);
        }
        else{
            imgAbsen2.setVisibility(View.INVISIBLE);
        }

        GoogleMap mMap = ((MapFragment) (getActivity()).getFragmentManager().findFragmentById(R.id.map)).getMap();

        if (mMap == null) {
            mMap = ((MapFragment) (getActivity()).getFragmentManager().findFragmentById(R.id.map)).getMap();
        }
        double latitude = 0;
        double longitude = 0;

        try{
            latitude = Double.parseDouble(dt.get(position).get_txtLatitude());
            longitude = Double.parseDouble(dt.get(position).get_txtLongitude());
        } catch (NumberFormatException e){
            e.printStackTrace();
        }



        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Location");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMap.clear();
        mMap.addMarker(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(19).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder
                .setCancelable(false)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MapFragment f = (MapFragment) (getActivity()).getFragmentManager().findFragmentById(R.id.map);
                        if (f != null){
                            (getActivity()).getFragmentManager().beginTransaction().remove(f).commit();
                        }
                        dialog.cancel();
                    }
                });
        final AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }
}
