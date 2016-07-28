package com.kalbenutritionals.app.kalbespgmobile;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
import bl.tAbsenUserBL;
import bl.tDeviceInfoUserBL;
import bl.tNotificationBL;
import bl.tUserLoginBL;
import come.example.viewbadger.ShortcutBadger;
import library.salesforce.common.clsPushData;
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

    private TextView tvUsername, tvEmail;
    private ImageView ivProfile;

    PackageInfo pInfo = null;

    int selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedId = 0;

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        tUserLoginData dt=new tUserLoginBL().getUserActive();

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View vwHeader = navigationView.getHeaderView(0);

        ivProfile = (ImageView) vwHeader.findViewById(R.id.profile_image);
        tvUsername = (TextView) vwHeader.findViewById(R.id.username);
        tvEmail = (TextView) vwHeader.findViewById(R.id.email);
//        dttAbsenUserData = _tAbsenUserBL.getDataCheckInActive();
//        byte[] photo = dttAbsenUserData.get_txtImg1();
//        Bitmap bmpPht = BitmapFactory.decodeByteArray(photo, 0, photo.length);
//        ivProfile.setImageBitmap(bmpPht);
        tvUsername.setText("Welcome, " + dt.get_txtName());
        tvEmail.setText(dt.get_TxtEmail());

        HomeFragment homeFragment = new HomeFragment();
        android.support.v4.app.FragmentTransaction fragmentTransactionhome = getSupportFragmentManager().beginTransaction();
        fragmentTransactionhome.replace(R.id.frame,homeFragment);
        fragmentTransactionhome.commit();

        Menu header = navigationView.getMenu();

        tAbsenUserData dtAbsen = new tAbsenUserBL().getDataCheckInActive();

        if(dtAbsen == null){
            header.setGroupVisible(R.id.groupListMenu, true);
            header.setGroupVisible(R.id.groupListMenu1, false);
        }
        else{
            header.setGroupVisible(R.id.groupListMenu, false);
            header.setGroupVisible(R.id.groupListMenu1, true);
            header.add(R.id.groupListMenu1, 1234, 1, "Absen").setIcon(R.drawable.ic_notifications).setCheckable(true);
        }

//        header.add(Menu.NONE, 0, 0, "Reso");
//        header.add(Menu.NONE, 1, 1, "Activity");
//        header.add(Menu.NONE, 2, 2, "Customer Base");
//
//        MenuItem menuItemReso = header.findItem(0);
//        MenuItem menuItemActivity = header.findItem(1);
//        final MenuItem menuCustomerBase = header.findItem(2);
//
//        menuItemReso.setIcon(R.drawable.ic_send_black);
//        menuItemReso.setChecked(false);
//        menuItemActivity.setIcon(R.drawable.ic_home_black);
//        menuItemActivity.setChecked(false);
//        menuCustomerBase.setIcon(R.drawable.ic_star_black);
//        menuCustomerBase.setChecked(false);


//Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()){

                    case  R.id.download :
                        //Toast.makeText(getApplicationContext(), "tes download", Toast.LENGTH_LONG).show();
                        toolbar.setTitle("Download Data");
                        DownloadDataFragment downloadDataFragment = new DownloadDataFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransactiondownloadData = getSupportFragmentManager().beginTransaction();
                        fragmentTransactiondownloadData.replace(R.id.frame,downloadDataFragment);
                        fragmentTransactiondownloadData.commit();

                        return true;
                    case R.id.menu_home:
                        toolbar.setTitle("Home");
                        HomeFragment homeFragment1 = new HomeFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransactionhome = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionhome.replace(R.id.frame,homeFragment1);
                        fragmentTransactionhome.commit();
                        selectedId=1;

                        return true;

                    case R.id.absen:
                        toolbar.setTitle("Absen");
                        AbsenFragment absenFragment = new AbsenFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransactionAbsen = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionAbsen.replace(R.id.frame,absenFragment);
                        fragmentTransactionAbsen.commit();
                        selectedId=1;

                        return true;

                    case R.id.view_reso:
                        toolbar.setTitle("View "+menuItem.getTitle().toString());
                        ViewResoFragment viewresofragment = new ViewResoFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransactionviewreso = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionviewreso.replace(R.id.frame,viewresofragment);
                        fragmentTransactionviewreso.commit();
                        selectedId=1;

                        return true;

                    case R.id.view_activity:
                        toolbar.setTitle("View "+menuItem.getTitle().toString());
                        ViewActivityFragment fragmentViewActivity = new ViewActivityFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransactionViewActivity = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionViewActivity.replace(R.id.frame,fragmentViewActivity);
                        fragmentTransactionViewActivity.commit();
                        selectedId=2;

                        return true;

                    case R.id.view_customerbase:
                        toolbar.setTitle("View "+menuItem.getTitle().toString());
                        ViewCustomerBaseFragment fragment3 = new ViewCustomerBaseFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction3.replace(R.id.frame,fragment3);
                        fragmentTransaction3.commit();
                        selectedId=3;

                        return true;
                    case R.id.checkout:
                        Toast.makeText(getApplicationContext(),"Check Out",Toast.LENGTH_SHORT).show();
//                        String myClass ;
//                        final String MenuID ;
//                        Class<?> clazz = null;
//
//                        myClass= strLink;
//                        MenuID = strDesc;
//                        String branchCode = BranchId;
//                        String outletCode = OutletId;
//                        try {
//                            if (myClass.equals("com.kalbe.salesforce.Checkout") && MenuID.toString().contains("mnCheckoutKBN")){
                                LayoutInflater layoutInflater = LayoutInflater.from(MainMenu.this);
                                final View promptView = layoutInflater.inflate(R.layout.confirm_data, null);

                                final TextView _tvConfirm=(TextView) promptView.findViewById(R.id.tvTitle);
                                final TextView _tvDesc=(TextView) promptView.findViewById(R.id.tvDesc);
                                _tvDesc.setVisibility(View.INVISIBLE);
                                _tvConfirm.setText("Check Out Data ?");

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainMenu.this);
                                alertDialogBuilder.setView(promptView);
                                alertDialogBuilder
                                        .setCancelable(false)
                                        .setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
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
                                                            Intent nextScreen = new Intent(getApplicationContext(), Home.class);
//                                                            nextScreen.putExtra(clsParameterPutExtra.MenuID, MenuID);
//                                                            nextScreen.putExtra(clsParameterPutExtra.BranchCode, BranchCode);
//                                                            nextScreen.putExtra(clsParameterPutExtra.OutletCode, OutletCode);
                                                            startActivity(nextScreen);
                                                        }
                                                    }
                                                })
                                        .setNegativeButton("Cancel",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog,	int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                final AlertDialog alertD = alertDialogBuilder.create();
                                alertD.show();
//                            }else{
//                                clazz = Class.forName(myClass);
//                                Intent myIntent = new Intent(getApplicationContext(), clazz);
//                                myIntent.putExtra(clsParameterPutExtra.MenuID, MenuID);
//                                myIntent.putExtra(clsParameterPutExtra.BranchCode, branchCode);
//                                myIntent.putExtra(clsParameterPutExtra.OutletCode, outletCode);
//                                finish();
//                                startActivity(myIntent);
//                            }
//                        } catch (ClassNotFoundException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == 1) {
            //MenuItem menuItem = null;
            //toolbar.setTitle(menuItem.getTitle().toString() + " Reso");
            toolbar.setTitle("Add Reso");
            Reso resofragment = new Reso();
            android.support.v4.app.FragmentTransaction fragmentTransactionreso = getSupportFragmentManager().beginTransaction();
            fragmentTransactionreso.replace(R.id.frame,resofragment);
            fragmentTransactionreso.commit();
            selectedId=4;

            return true;
        }
        if(id == 2){
            toolbar.setTitle("Add Activity");
            AddActivityFragment addFragment = new AddActivityFragment();
            android.support.v4.app.FragmentTransaction fragmentTransactionActivity = getSupportFragmentManager().beginTransaction();
            fragmentTransactionActivity.replace(R.id.frame,addFragment);
            fragmentTransactionActivity.commit();
            selectedId=5;

            return true;
        }
        if(id == 3){
            toolbar.setTitle("Add Customer Base");
            CustomerBaseFragment fragment2 = new CustomerBaseFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction2.replace(R.id.frame,fragment2);
            fragmentTransaction2.commit();
            selectedId=6;

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override

    public boolean onPrepareOptionsMenu(Menu menu) {


        menu.clear();

        if(selectedId == 1) {

            menu.add(0, 1, 0, "Add Reso");

        } else if(selectedId == 2) {

            menu.add(0, 2, 0, "Add Activity");

        } else if(selectedId == 3){

            menu.add(0, 3, 0, "Add Customer Base");

        } else if (selectedId==4){

            menu.add(1, 4, 0, "View Reso");
            menu.setGroupEnabled(1,false);

        } else if (selectedId==5){

            menu.add(2, 5, 0, "View Activity");
            menu.setGroupEnabled(2,false);

        } else if (selectedId==6){

            menu.add(3, 6, 0, "View Customer Base");
            menu.setGroupEnabled(3,false);

        }
        else if(selectedId == 0){

            menu.add(0,4,0, "default");
        }

        //Toast.makeText(this, String.valueOf(selectedId), Toast.LENGTH_LONG).show();
        return super.onPrepareOptionsMenu(menu);

    }

}
