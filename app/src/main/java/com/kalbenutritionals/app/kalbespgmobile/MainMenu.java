package com.kalbenutritionals.app.kalbespgmobile;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bl.clsHelperBL;
import bl.mMenuBL;
import bl.tAbsenUserBL;
import bl.tDeviceInfoUserBL;
import bl.tNotificationBL;
import bl.tUserLoginBL;
import come.example.viewbadger.ShortcutBadger;
import library.salesforce.common.clsPushData;
import library.salesforce.common.mMenuData;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tDeviceInfoUserData;
import library.salesforce.common.tNotificationData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import service.MyServiceNative;

public class MainMenu extends AppCompatActivity {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private tAbsenUserBL _tAbsenUserBL;
    private tAbsenUserData dttAbsenUserData;
    private tAbsenUserData dtAbsens;

    private TextView tvUsername, tvEmail;
    private ImageView ivProfile;

    PackageInfo pInfo = null;

    int selectedId;
    private static int menuId = 0;
    Boolean isSubMenu = false;

    String[] listMenu;
    String[] linkMenu;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Exit");
        builder.setMessage("Do you want to exit?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedId = 0;

        try {
            pInfo=getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        HomeFragment homeFragment = new HomeFragment();
        android.support.v4.app.FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
        fragmentTransactionHome.replace(R.id.frame,homeFragment);
        fragmentTransactionHome.commit();
        selectedId=99;

        //set menu masih manual untuk create db nya
//        new mMenuBL().setMenuManual();

        tUserLoginData dt=new tUserLoginBL().getUserActive();

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View vwHeader = navigationView.getHeaderView(0);

        ivProfile = (ImageView) vwHeader.findViewById(R.id.profile_image);
        tvUsername = (TextView) vwHeader.findViewById(R.id.username);
        tvEmail = (TextView) vwHeader.findViewById(R.id.email);
        tvUsername.setText("Welcome, " + dt.get_txtName());
        tvEmail.setText(dt.get_TxtEmail());

        dtAbsens = new tAbsenUserBL().getDataCheckInActive();
        Menu header = navigationView.getMenu();

        Intent intent = getIntent();
        String i_view = intent.getStringExtra("key_view");

        int statusAbsen = 0;
        int menuActive = 0;

        if(dtAbsens == null){
            statusAbsen = 1;
            menuActive = R.id.groupListMenu;
            header.removeItem(R.id.checkout);
        }
        else{
            //
            mMenuData data = new mMenuBL().getMenuDataByMenuName("mnAbsenSPG");
            menuId = Integer.parseInt(data.get_IntMenuID());
            statusAbsen = menuId;
            menuActive = R.id.groupListMenu1;

            List<mMenuData> menu = new mMenuBL().getDatabyParentId(statusAbsen);
            listMenu = new String[menu.size()];

            for(int i = 0; i < menu.size(); i++){
                listMenu[i] = menu.get(i).get_TxtMenuName();
            }

            if(i_view!=null)
                try{
                    Class<?> fragmentClass = Class.forName("com.kalbenutritionals.app.kalbespgmobile.Fragment" + i_view.replaceAll("\\s+","") );
                    try {
                        for(int i = 0; i < listMenu.length; i++){
                            if(("View " + listMenu[i]).equals(i_view + " SPG")){
                                 selectedId = i;
                                break;
                            }
                        }
                        toolbar.setTitle(i_view);
                        Fragment fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame,fragment);
                        fragmentTransaction.commit();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        List<mMenuData> menu;

        if(dtAbsens == null){
            menu = new mMenuBL().getDatabyParentId(0);
        }
        else{
            menu = new mMenuBL().getDatabyParentId(211);
        }

        linkMenu = new String[menu.size()];
        listMenu = new String[menu.size()];

        if(menu != null) {
            for (int i = 0; i < menu.size(); i++) {

                int resId = getResources().getIdentifier(String.valueOf(menu.get(i).get_TxtDescription().toLowerCase()), "drawable", MainMenu.this.getPackageName());
                Drawable icon = MainMenu.this.getResources().getDrawable(resId);

                header.add(menuActive, i, 1, menu.get(i).get_TxtMenuName()).setIcon(icon).setCheckable(true);

                linkMenu[i] = menu.get(i).get_TxtLink();
                listMenu[i] = menu.get(i).get_TxtMenuName();
            }
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                android.support.v4.app.Fragment fragment = null;

                switch (menuItem.getItemId()){
                    case R.id.logout :
                        //funcLogOut();
                        LayoutInflater layoutInflater = LayoutInflater.from(MainMenu.this);
                        final View promptView = layoutInflater.inflate(R.layout.confirm_data, null);

                        final TextView _tvConfirm = (TextView) promptView.findViewById(R.id.tvTitle);
                        final TextView _tvDesc = (TextView) promptView.findViewById(R.id.tvDesc);
                        _tvDesc.setVisibility(View.INVISIBLE);
                        _tvConfirm.setText("Log Out Application ?");

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainMenu.this);
                        alertDialogBuilder.setView(promptView);
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //PushData
                                        stopService(new Intent(getApplicationContext(), MyServiceNative.class));
                                        AsyncCallLogOut task = new AsyncCallLogOut();
                                        task.execute();
                                        //new clsHelperBL().DeleteAllDB();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        final AlertDialog alertD = alertDialogBuilder.create();
                        alertD.show();
                        return true;

                    case R.id.reporting:
                        toolbar.setTitle("Reporting");

                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

                        ReportingFragment reportingFragment = new ReportingFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransactionReport = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionReport.replace(R.id.frame,reportingFragment);
                        fragmentTransactionReport.commit();
                        selectedId=0;

                        return true;

                    case R.id.home:
                        toolbar.setTitle("Home");

                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

                        HomeFragment homeFragment = new HomeFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionHome.replace(R.id.frame,homeFragment);
                        fragmentTransactionHome.commit();
                        selectedId=99;

                        return true;

                    case R.id.checkout:
                        Toast.makeText(getApplicationContext(),"Check Out",Toast.LENGTH_SHORT).show();
                        LayoutInflater _layoutInflater = LayoutInflater.from(MainMenu.this);
                        final View _promptView = _layoutInflater.inflate(R.layout.confirm_data, null);

                        final TextView tvConfirm=(TextView) _promptView.findViewById(R.id.tvTitle);
                        final TextView tvDesc=(TextView) _promptView.findViewById(R.id.tvDesc);
                        tvDesc.setVisibility(View.INVISIBLE);
                        tvConfirm.setText("Check Out Data ?");

                        AlertDialog.Builder _alertDialogBuilder = new AlertDialog.Builder(MainMenu.this);
                        _alertDialogBuilder.setView(_promptView);
                        _alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,	int id) {
                                        _tAbsenUserBL=new tAbsenUserBL();
                                        dttAbsenUserData = new tAbsenUserData();

                                        dttAbsenUserData=_tAbsenUserBL.getDataCheckInActive();
                                        tAbsenUserData datatAbsenUserData = dttAbsenUserData;
                                        tUserLoginData dataUserActive = new tUserLoginBL().getUserActive();
                                        String idUserActive = String.valueOf(dataUserActive.get_txtUserId());

                                        List<tDeviceInfoUserData> dataDeviceInfoUser = new tDeviceInfoUserBL().getData(1);
                                        String deviceInfo = String.valueOf(dataDeviceInfoUser.get(0).get_txtDeviceId());
                                        List<tAbsenUserData> absenUserDatas = new ArrayList<tAbsenUserData>();
                                        clsMainActivity _clsMainActivity = new clsMainActivity();

                                        if (dttAbsenUserData!=null){
                                            datatAbsenUserData.set_intId(dttAbsenUserData.get_intId());
                                            datatAbsenUserData.set_dtDateCheckOut(_clsMainActivity.FormatDateDB().toString());
                                            datatAbsenUserData.set_intSubmit("1");
                                            datatAbsenUserData.set_intSync("0");
                                            datatAbsenUserData.set_txtAbsen("0");//
                                            absenUserDatas.add(datatAbsenUserData);
                                            new tAbsenUserBL().saveData(absenUserDatas);

                                            finish();
                                            Intent nextScreen = new Intent(getApplicationContext(), MainMenu.class);
                                            nextScreen.putExtra("keyMainMenu", "main_menu");
                                            finish();
                                            startActivity(nextScreen);
                                        }
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,	int id) {
                                        dialog.cancel();
                                    }
                                });
                        final AlertDialog _alertD = _alertDialogBuilder.create();
                        _alertD.show();

                        return true;
                    default:
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
                        try{
                            Class<?> fragmentClass = Class.forName(linkMenu[menuItem.getItemId()]);
                            try {
                                if(dtAbsens != null){
                                    toolbar.setTitle("View "+menuItem.getTitle().toString());
                                }
                                else{
                                    toolbar.setTitle(menuItem.getTitle().toString());
                                }

//                                menuId = Integer.parseInt(new mMenuBL().getMenuDataByMenuName2(menuItem.getTitle().toString()).get_IntMenuID());

                                fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.frame,fragment);
                                fragmentTransaction.commit();
                                selectedId = menuItem.getItemId();
                                isSubMenu = false;

                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
//        MenuItem menuItem = null;
//        if(menuItem.getItemId()==R.id.view_reso){
//            menu.add(0, 1, Menu.CATEGORY_ALTERNATIVE, "Add Reso");
//        }
//        else {
//            menu.add(0, 2, Menu.NONE, "Setting");
//        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        int idd = item.getGroupId();

        toolbar.setTitle(item.getTitle());

        Class<?> fragmentClass = null;
        try {
            android.support.v4.app.Fragment fragment = null;

            fragmentClass = Class.forName("com.kalbenutritionals.app.kalbespgmobile.Fragment" + String.valueOf(item.getTitle()).replaceAll("\\s+",""));
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();

            if(!isSubMenu) isSubMenu = true;
            else isSubMenu = false;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if(listMenu.length <= selectedId){
            menu.add(4, 0, 0, "Default");
            menu.setGroupEnabled(4,false);
        }
        else if(!isSubMenu){
            menu.add(0, selectedId, 0, "Add " + listMenu[selectedId]);
        }
        else if(isSubMenu){
            menu.add(1, selectedId, 0, "View " + listMenu[selectedId]);
        }

//
//        if(selectedId == 1) {
//
//            menu.add(0, 1, 0, "Add Reso");
//
//        } else if(selectedId == 2) {
//
//            menu.add(0, 2, 0, "Add Activity");
//
//        } else if(selectedId == 3){
//
//            menu.add(0, 3, 0, "Add Customer Base");
//
//        } else if (selectedId==4){
//
//            menu.add(1, 4, 0, "View Reso");
//            menu.setGroupEnabled(1,false);
//
//        } else if (selectedId==5){
//
//            menu.add(2, 5, 0, "View Activity");
//            menu.setGroupEnabled(2,false);
//
//        } else if (selectedId==6){
//
//            menu.add(3, 6, 0, "View Customer Base");
//            menu.setGroupEnabled(3,false);
//
//        }
//        else if(selectedId == 0){
//
//            menu.add(4, 0, 0, "De");
//            menu.setGroupEnabled(1,false);
//        }

        //Toast.makeText(this, String.valueOf(selectedId), Toast.LENGTH_LONG).show();
        return super.onPrepareOptionsMenu(menu);

    }


    int intProcesscancel = 0;
    private clsHardCode clsHardcode = new clsHardCode();
    private class AsyncCallLogOut extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
            JSONArray Json = null;

            try {
                List<tAbsenUserData> listAbsenData = new ArrayList<tAbsenUserData>();
                tAbsenUserData dtTabsenData = new tAbsenUserBL().getDataCheckInActive();
                if (dtTabsenData != null) {
                    dtTabsenData.set_dtDateCheckOut(new clsMainActivity().FormatDateComplete().toString());
                    dtTabsenData.set_intSubmit("1");
                    dtTabsenData.set_intSync("0");
                    listAbsenData.add(dtTabsenData);
                    new tAbsenUserBL().saveData(listAbsenData);
                }
                clsPushData dtJson = new clsHelperBL().pushData();
                if (dtJson != null) {
                    String versionName = "";
                    try {
                        versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                    } catch (PackageManager.NameNotFoundException e2) {
                        // TODO Auto-generated catch block
                        e2.printStackTrace();
                    }
                    try {
                        JSONArray Jresult = null;
                        if (dtJson.getDtdataJson().getListOftAbsenUserData() != null) {
                            List<tAbsenUserData> listAbsen = dtJson.getDtdataJson().getListOftAbsenUserData();
                            if (listAbsen.get(0).get_txtAbsen().equals("0")) {
                                Jresult = new clsHelperBL().callPushDataReturnJson(versionName, dtJson.getDtdataJson().txtJSON().toString(), dtJson.getFileUpload());
                            } else {
                                Jresult = new clsHelperBL().callPushDataReturnJson(versionName, dtJson.getDtdataJson().txtJSON().toString(), null);
                            }
//                            new clsHelperBL().saveDataPush(dtJson.getDtdataJson(), Jresult);
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

                try {
                    pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }

                Json = new tUserLoginBL().Logout(pInfo.versionName);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
            return Json;
        }

        private ProgressDialog Dialog = new ProgressDialog(MainMenu.this);

        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(MainMenu.this, "cancel", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }

        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata != null) {
                Iterator i = roledata.iterator();
                while (i.hasNext()) {
                    JSONObject innerObj = (JSONObject) i.next();
                    Long IntResult = (Long) innerObj.get("_pboolValid");
                    String PstrMessage = (String) innerObj.get("_pstrMessage");

                    if (IntResult == 1) {
                        tNotificationBL _tNotificationBL = new tNotificationBL();
                        List<tNotificationData> ListData = _tNotificationBL.getAllDataWillAlert("1");
                        if (ListData != null) {
                            for (tNotificationData dttNotificationData : ListData) {
                                NotificationManager tnotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                tnotificationManager.cancelAll();
                                ShortcutBadger.applyCount(MainMenu.this, 0);
                                System.gc();
                            }
                        }
                        new clsHelperBL().DeleteAllDB();
                        finish();
                        Intent nextScreen = new Intent(MainMenu.this, ProgressBarActivity.class);
                        startActivity(nextScreen);
                    } else {
                        Toast toast = Toast.makeText(MainMenu.this,
                                PstrMessage, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 25, 400);
                        toast.show();
                    }
                }

            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    Toast toast = Toast.makeText(MainMenu.this, "Offline", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                }

            }
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            //Make ProgressBar invisible
            //pg.setVisibility(View.VISIBLE);
            Dialog.setMessage(new clsHardCode().txtMessLogOut);
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
