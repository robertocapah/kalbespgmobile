package com.kalbenutritionals.app.kalbespgmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bl.mMenuBL;
import bl.tAbsenUserBL;
import bl.tUserLoginBL;
import edu.swu.pulltorefreshswipemenulistview.library.PullToRefreshSwipeMenuListView;
import edu.swu.pulltorefreshswipemenulistview.library.pulltorefresh.interfaces.IXListViewListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.bean.SwipeMenu;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.OnMenuItemClickListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.SwipeMenuCreator;
import edu.swu.pulltorefreshswipemenulistview.library.util.RefreshTime;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;
import library.salesforce.common.AppAdapter;
import library.salesforce.common.clsSwipeList;
import library.salesforce.common.mMenuData;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tUserLoginData;

public class FragmentViewHistoryAbsen extends Fragment implements IXListViewListener {

    private static List<clsSwipeList> swipeList = new ArrayList<clsSwipeList>();
    private AppAdapter mAdapter;
    private PullToRefreshSwipeMenuListView mListView;
    private Handler mHandler;
    private static Map<String, HashMap> mapMenu;

    static List<tAbsenUserData> dt;

    private static Bitmap mybitmap1;
    private static Bitmap mybitmap2;

    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_custom_fab, container, false);

        FabSpeedDial fabSpeedDial = (FabSpeedDial) v.findViewById(R.id.customfab);

        tAbsenUserData dtAbsens = new tAbsenUserBL().getDataCheckInActive();

        List<mMenuData> menu;

        if (dtAbsens == null) {
            fabSpeedDial.setVisibility(View.GONE);
        } else {
            fabSpeedDial.setVisibility(View.VISIBLE);
        }

        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                NavigationView nv = (NavigationView) getActivity().findViewById(R.id.navigation_view);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                switch(menuItem.getItemId()){

                    case R.id.action_add_reso:
                        toolbar.setTitle("Add Reso SPG");
                        nv.setCheckedItem(0);
                        FragmentAddResoSPG fragmentAddResoSPG = new FragmentAddResoSPG();
                        fragmentTransaction.replace(R.id.frame, fragmentAddResoSPG);
                        fragmentTransaction.commit();
                        return true;

                    case R.id.action_add_activity:
                        toolbar.setTitle("Add Actvity SPG");
                        nv.setCheckedItem(1);
                        FragmentAddActvitySPG fragmentAddActvitySPG = new FragmentAddActvitySPG();
                        fragmentTransaction.replace(R.id.frame, fragmentAddActvitySPG);
                        fragmentTransaction.commit();
                        return true;

                    case R.id.action_add_customerbase:
                        toolbar.setTitle("Add Customer Base SPG");
                        nv.setCheckedItem(2);
                        FragmentAddCustomerBaseSPG fragmentAddCustomerBaseSPG = new FragmentAddCustomerBaseSPG();
                        Bundle args = new Bundle();
                        args.putString("idTrCustomer", "null");
                        args.putString("param", "null");
                        fragmentTransaction.replace(R.id.frame, fragmentAddCustomerBaseSPG);
                        fragmentTransaction.commit();
                        return true;

                    default:
                        return false;
                }
            }
        });


        clsSwipeList swplist;
        dt = new tAbsenUserBL().getAllDataActiveOrderByDate();

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
        mListView.setEmptyView( v.findViewById(R.id.LayoutEmpty));
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

        SliderLayout mDemoSlider = (SliderLayout) promptView.findViewById(R.id.slider);

        tUserLoginData dtLogin = new tUserLoginBL().getUserLoginByUserId(dt.get(position).get_txtUserId());

        tvUsername.setText(dtLogin.get_txtUserName());
        tvBranch.setText(dt.get(position).get_txtBranchName());
        tvOutlet.setText(dt.get(position).get_txtOutletName());
        tvCheckin.setText(dt.get(position).get_dtDateCheckIn());
        tvCheckout.setText(dt.get(position).get_dtDateCheckOut());

        File folder = new File(Environment.getExternalStorageDirectory().toString() + "/Android/data/SPG.MOBILE/tempdata");
        folder.mkdir();

        final byte[] imgFile = dt.get(position).get_txtImg1();
        if (imgFile != null) {
            mybitmap1 = BitmapFactory.decodeByteArray(imgFile, 0, imgFile.length);
            Bitmap bitmap = Bitmap.createScaledBitmap(mybitmap1, 150, 150, true);
            imgAbsen1.setImageBitmap(bitmap);

            File file = null;
            try {
                file = File.createTempFile("image-", ".jpg", new File(Environment.getExternalStorageDirectory().toString() + "/Android/data/SPG.MOBILE/tempdata"));
                FileOutputStream out = new FileOutputStream(file);
                out.write(imgFile);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView
                    .description("History Absen")
                    .image(file)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            new clsMainActivity().zoomImage(mybitmap1, getActivity());
                        }
                    });

            mDemoSlider.addSlider(textSliderView);

        } else {
            imgAbsen1.setVisibility(View.GONE);
        }
        final byte[] imgFile2 = dt.get(position).get_txtImg2();
        if (imgFile2 != null) {
            mybitmap2 = BitmapFactory.decodeByteArray(imgFile2, 0, imgFile2.length);
            Bitmap bitmap = Bitmap.createScaledBitmap(mybitmap2, 150, 150, true);
            imgAbsen2.setImageBitmap(bitmap);

            File file = null;
            try {
                file = File.createTempFile("image-", ".jpg", new File(Environment.getExternalStorageDirectory().toString() + "/Android/data/SPG.MOBILE/tempdata"));
                FileOutputStream out = new FileOutputStream(file);
                out.write(imgFile2);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView
                    .description("History Absen")
                    .image(file)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            new clsMainActivity().zoomImage(mybitmap2, getActivity());
                        }
                    });

            mDemoSlider.addSlider(textSliderView);
        } else {
            imgAbsen2.setVisibility(View.GONE);
        }

        if (imgFile == null || imgFile2 == null) {
            mDemoSlider.stopAutoCycle();
            mDemoSlider.setPagerTransformer(false, new BaseTransformer() {
                @Override
                protected void onTransform(View view, float v) {
                }
            });
        } else {
            mDemoSlider.stopAutoCycle();
            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            mDemoSlider.setDuration(4000);
        }

//        byte[] imgFile = dt.get(position).get_txtImg1();
//        if (imgFile != null) {
//            mybitmap1 = BitmapFactory.decodeByteArray(imgFile, 0, imgFile.length);
//            Bitmap bitmap = Bitmap.createScaledBitmap(mybitmap1, 150, 150, true);
//            imgAbsen1.setImageBitmap(bitmap);
//        } else {
//            imgAbsen1.setVisibility(View.INVISIBLE);
//        }
//        byte[] imgFile2 = dt.get(position).get_txtImg2();
//        if (imgFile2 != null && imgFile != null) {
//            mybitmap2 = BitmapFactory.decodeByteArray(imgFile2, 0, imgFile2.length);
//            Bitmap bitmap = Bitmap.createScaledBitmap(mybitmap2, 150, 150, true);
//            imgAbsen2.setImageBitmap(bitmap);
//        }
//        else if(imgFile2 != null && imgFile == null){
//            mybitmap1 = BitmapFactory.decodeByteArray(imgFile2, 0, imgFile2.length);
//            Bitmap bitmap = Bitmap.createScaledBitmap(mybitmap1, 150, 150, true);
//            imgAbsen1.setVisibility(View.VISIBLE);
//            imgAbsen1.setImageBitmap(bitmap);
//            imgAbsen2.setVisibility(View.INVISIBLE);
//        }
//        else{
//            imgAbsen2.setVisibility(View.INVISIBLE);
//        }
//
//        imgAbsen1.setClickable(true);
//        imgAbsen1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                new clsMainActivity().showCustomToast(getContext(), "tes", true);
//                new clsMainActivity().zoomImage(mybitmap1, getActivity());
//            }
//        });
//
//        imgAbsen2.setClickable(true);
//        imgAbsen2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new clsMainActivity().zoomImage(mybitmap2, getActivity());
//            }
//        });

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
