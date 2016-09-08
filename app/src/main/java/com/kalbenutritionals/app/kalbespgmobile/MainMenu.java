package com.kalbenutritionals.app.kalbespgmobile;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bl.clsHelperBL;
import bl.mMenuBL;
import bl.tAbsenUserBL;
import bl.tActivityBL;
import bl.tDeviceInfoUserBL;
import bl.tDisplayPictureBL;
import bl.tNotificationBL;
import bl.tUserLoginBL;
import come.example.viewbadger.ShortcutBadger;
import de.hdodenhof.circleimageview.CircleImageView;
import library.salesforce.common.clsPushData;
import library.salesforce.common.mMenuData;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tActivityData;
import library.salesforce.common.tDeviceInfoUserData;
import library.salesforce.common.tDisplayPictureData;
import library.salesforce.common.tNotificationData;
import library.salesforce.common.tUserLoginData;
import library.salesforce.dal.clsHardCode;
import service.MyServiceNative;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private tAbsenUserBL _tAbsenUserBL;
    private tAbsenUserData dttAbsenUserData;
    private tAbsenUserData dtAbsens;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 200;

    private TextView tvUsername, tvEmail;
    private CircleImageView ivProfile;
    private tDisplayPictureData tDisplayPictureData;
    private tActivityData _tActivityData;

    private Uri mCropImageUri;
    private CropImageView mCropImageView;

    private boolean requirePermissions = false;

    PackageInfo pInfo = null;

    int selectedId;
    int saskak;
    private static int menuId = 0;
    Boolean isSubMenu = false;

    String[] listMenu;
    String[] linkMenu;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
            pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        FragmentHome homeFragment = new FragmentHome();
        FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
        fragmentTransactionHome.replace(R.id.frame, homeFragment);
        fragmentTransactionHome.commit();
        selectedId = 99;

        //set menu masih manual untuk create db nya
//        new mMenuBL().setMenuManual();

        tUserLoginData dt = new tUserLoginBL().getUserActive();

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View vwHeader = navigationView.getHeaderView(0);

        ivProfile = (CircleImageView) vwHeader.findViewById(R.id.profile_image);
        tvUsername = (TextView) vwHeader.findViewById(R.id.username);
        tvEmail = (TextView) vwHeader.findViewById(R.id.email);
        tvUsername.setText("Welcome, " + dt.get_txtName());
        tvEmail.setText(dt.get_TxtEmail());

        mCropImageView = new CropImageView(this);

        _tActivityData = new tActivityBL().getDataByBitActive();
        tDisplayPictureData = new tDisplayPictureBL().getData();

        if (tDisplayPictureData.get_image() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(tDisplayPictureData.get_image(), 0, tDisplayPictureData.get_image().length);
            ivProfile.setImageBitmap(bitmap);
        } else {
            ivProfile.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                    R.drawable.profile));
        }

        ivProfile.setOnClickListener(this);

        dtAbsens = new tAbsenUserBL().getDataCheckInActive();
        Menu header = navigationView.getMenu();

        Intent intent = getIntent();
        String i_view = intent.getStringExtra("key_view");

                if(i_view!=null){
                    if (i_view.equals("Notifcation")){
                        Class<?> fragmentClass = null;
                        try {
                            fragmentClass = Class.forName("com.kalbenutritionals.app.kalbespgmobile.Fragment" + i_view);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        toolbar.setTitle("Information");
                        Fragment fragment = null;
                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.commit();
                        selectedId = 99;
                    }
                }

        int statusAbsen = 0;
        int menuActive = 0;

        if (dtAbsens == null) {
            statusAbsen = 1;
            menuActive = R.id.groupListMenu;
            header.removeItem(R.id.checkout);
        } else {
            header.removeItem(R.id.logout);
            mMenuData data = new mMenuBL().getMenuDataByMenuName("mnAbsenSPG");
            menuId = Integer.parseInt(data.get_IntMenuID());
            statusAbsen = menuId;
            menuActive = R.id.groupListMenu1;

            List<mMenuData> menu = new mMenuBL().getDatabyParentId(statusAbsen);
            listMenu = new String[menu.size()];

            for (int i = 0; i < menu.size(); i++) {
                listMenu[i] = menu.get(i).get_TxtMenuName();
            }

            if (i_view != null){
                    try {
                        Class<?> fragmentClass = Class.forName("com.kalbenutritionals.app.kalbespgmobile.Fragment" + i_view.replaceAll("\\s+", "") + "SPG");
                        try {
                            for (int i = 0; i < listMenu.length; i++) {
                                if (("View " + listMenu[i]).equals(i_view + " SPG")) {
                                    selectedId = i;
                                    break;
                                }
                            }
                            toolbar.setTitle(i_view);
                            Fragment fragment = (Fragment) fragmentClass.newInstance();
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frame, fragment);
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

        }

        List<mMenuData> menu;

        if (dtAbsens == null) {
            menu = new mMenuBL().getDatabyParentId(0);
        } else {
            menu = new mMenuBL().getDatabyParentId(211);
        }

        linkMenu = new String[menu.size()];
        listMenu = new String[menu.size()];

        if (menu != null) {
            for (int i = 0; i < menu.size(); i++) {

                int resId = getResources().getIdentifier(String.valueOf(menu.get(i).get_TxtDescription().toLowerCase()), "drawable", MainMenu.this.getPackageName());
                Drawable icon = MainMenu.this.getResources().getDrawable(resId);

                header.add(menuActive, i, 1, menu.get(i).get_TxtMenuName()).setIcon(icon).setCheckable(true);

                linkMenu[i] = menu.get(i).get_TxtLink();
                listMenu[i] = menu.get(i).get_TxtMenuName();
            }
        }

        TextView view = (TextView) navigationView.getMenu().findItem(R.id.home).getActionView();
        view.setText("99");

//        TextView view = (TextView) navigationView.getMenu().findItem(R.id.information).getActionView();
//        List<tNotificationData> ListData=new tNotificationBL().getAllDataWillAlert("2");
//        view.setText("1");

        SubMenu subMenuVersion = header.addSubMenu(R.id.groupVersion, 0, 3, "Version");
        try {
            subMenuVersion.add(getPackageManager().getPackageInfo(getPackageName(), 0).versionName + " \u00a9 KN-IT").setIcon(R.drawable.ic_android).setEnabled(false);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                Fragment fragment = null;

                switch (menuItem.getItemId()) {
                    case R.id.logout:
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

                        FragmentReporting reportingFragment = new FragmentReporting();
                        FragmentTransaction fragmentTransactionReport = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionReport.replace(R.id.frame, reportingFragment);
                        fragmentTransactionReport.commit();
                        selectedId = 100;

                        return true;

                    case R.id.home:
                        toolbar.setTitle("Home");

                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

                        FragmentHome homeFragment = new FragmentHome();
                        FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionHome.replace(R.id.frame, homeFragment);
                        fragmentTransactionHome.commit();
                        selectedId = 99;

                        return true;

                    case R.id.historyAbsen:
                        toolbar.setTitle("History Absen");

                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

                        FragmentViewHistoryAbsen fragmentViewHistoryAbsen = new FragmentViewHistoryAbsen();
                        FragmentTransaction fragmentTransactionHistoryAbsen = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionHistoryAbsen.replace(R.id.frame, fragmentViewHistoryAbsen);
                        fragmentTransactionHistoryAbsen.commit();
                        selectedId = 99;

                        return true;

                    case R.id.information:
                        toolbar.setTitle("Information");

                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

                        FragmentNotifcation fragmentNotifcation = new FragmentNotifcation();
                        FragmentTransaction fragmentTransactionNotifcation = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionNotifcation.replace(R.id.frame, fragmentNotifcation);
                        fragmentTransactionNotifcation.commit();
                        selectedId = 99;

                        return  true;

                    case R.id.checkout:
                        LayoutInflater _layoutInflater = LayoutInflater.from(MainMenu.this);
                        final View _promptView = _layoutInflater.inflate(R.layout.confirm_data, null);

                        final TextView tvConfirm = (TextView) _promptView.findViewById(R.id.tvTitle);
                        final TextView tvDesc = (TextView) _promptView.findViewById(R.id.tvDesc);
                        tvDesc.setVisibility(View.INVISIBLE);
                        tvConfirm.setText("Check Out Data ?");

                        AlertDialog.Builder _alertDialogBuilder = new AlertDialog.Builder(MainMenu.this);
                        _alertDialogBuilder.setView(_promptView);
                        _alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        _tAbsenUserBL = new tAbsenUserBL();
                                        dttAbsenUserData = new tAbsenUserData();

                                        dttAbsenUserData = _tAbsenUserBL.getDataCheckInActive();
                                        tAbsenUserData datatAbsenUserData = dttAbsenUserData;
                                        tUserLoginData dataUserActive = new tUserLoginBL().getUserActive();
                                        String idUserActive = String.valueOf(dataUserActive.get_txtUserId());

                                        List<tDeviceInfoUserData> dataDeviceInfoUser = new tDeviceInfoUserBL().getData(1);
                                        String deviceInfo = String.valueOf(dataDeviceInfoUser.get(0).get_txtDeviceId());
                                        List<tAbsenUserData> absenUserDatas = new ArrayList<tAbsenUserData>();
                                        clsMainActivity _clsMainActivity = new clsMainActivity();

                                        if (dttAbsenUserData != null) {
                                            datatAbsenUserData.set_intId(dttAbsenUserData.get_intId());
                                            datatAbsenUserData.set_dtDateCheckOut(_clsMainActivity.FormatDateDB().toString());
                                            datatAbsenUserData.set_intSubmit("1");
                                            datatAbsenUserData.set_intSync("0");
                                            datatAbsenUserData.set_txtAbsen("0");
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
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        final AlertDialog _alertD = _alertDialogBuilder.create();
                        _alertD.show();

                        return true;
                    default:
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
                        try {
                            Class<?> fragmentClass = Class.forName(linkMenu[menuItem.getItemId()]);
                            try {
                                if (dtAbsens != null) {
                                    toolbar.setTitle("View " + menuItem.getTitle().toString());
                                } else {
                                    toolbar.setTitle(menuItem.getTitle().toString());
                                }

//                                menuId = Integer.parseInt(new mMenuBL().getMenuDataByMenuName2(menuItem.getTitle().toString()).get_IntMenuID());

                                fragment = (Fragment) fragmentClass.newInstance();
                                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.frame, fragment);
                                fragmentTransaction.addToBackStack(fragment.getClass().getName());
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
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

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


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
            Fragment fragment = null;

            fragmentClass = Class.forName("com.kalbenutritionals.app.kalbespgmobile.Fragment" + String.valueOf(item.getTitle()).replaceAll("\\s+", ""));
            fragment = (Fragment) fragmentClass.newInstance();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commit();

            selectedId=id;

            if (!isSubMenu) isSubMenu = true;
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
        String[] data = listMenu;
        if (listMenu.length <= selectedId) {
            if (toolbar.getTitle().equals("Reporting")&&!isSubMenu||isSubMenu && dtAbsens != null&&listMenu.length>0&&toolbar.getTitle()=="Reporting"){
                for(String s : listMenu){
                    if(s.contains("Reso SPG")) {
                        int a = listMenu.length;
                        for (int i=0; i<a; i++ ){
                            if (i==1){
                                menu.removeItem(i);
                            } else {
                                menu.add(0, i, 0, "Add " + listMenu[i]);
                            }
                        }
                    } else {
                        menu.add(4, 0, 0, "-");
                        menu.setGroupEnabled(4, false);
                        break;
                    }
                    break;
                }
            } else {
                menu.add(4, 0, 0, "-");
                menu.setGroupEnabled(4, false);
            }
        } else if (!isSubMenu && dtAbsens != null) {
            menu.add(0, selectedId, 0, "Add " + listMenu[selectedId]);
        } else if (isSubMenu && dtAbsens != null) {
            menu.add(1, selectedId, 0, "View " + listMenu[selectedId]);
        }
        else {
            menu.add(4, 0, 0, "-");
            menu.setGroupEnabled(4, false);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_image:
                pickImage();
                break;
        }
    }

    protected void pickImage() {

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

//        if (CropImage.isExplicitCameraPermissionRequired(this)) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(new String[]{Manifest.permission.CAMERA}, CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
//            }
//        } else {
//            CropImage.startPickImageActivity(this);
//        }
//        drawerLayout.closeDrawers();

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
//            Uri imageUri = CropImage.getPickImageResultUri(this, data);
//
//            // For API >= 23 we need to check specifically that we have permissions to read external storage,
//            // but we don't know if we need to for the URI so the simplest is to try open the stream and see if we get error.
//            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
//
//                // request permissions and handle the result in onRequestPermissionsResult()
//                requirePermissions = true;
//
//                mCropImageUri = imageUri;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
//                }
//            } else {
//
//                setImageUri(imageUri);
//            }
//        }
//    }

    /**
     * Set the image to show for cropping.
     */
    public void setImageUri(Uri imageUri) {
        mCropImageView.setImageUriAsync(imageUri);
        //        CropImage.activity(imageUri)
        //                .start(getContext(), this);
    }

        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        Bitmap photo = null;
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                final boolean isCamera;
                if (data == null) {

                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = true;
                    } else {
                        photo = (Bitmap) data.getExtras().get("data");
                        isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }

                if (isCamera) {
                    try {
                        photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                previewPickImage(photo);
            }
        } else {
                for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
        }
    }

    private void previewPickImage(Bitmap photo) {
        try {
            ByteArrayOutputStream out = null;
            try {
                out = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ByteArrayOutputStream blob = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, blob);
            Bitmap bitmap = Bitmap.createScaledBitmap(photo, 150, 150, false);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, blob);
            byte[] pht = out.toByteArray();
            ivProfile.setImageBitmap(photo);

            tDisplayPictureData.set_intID("1");
            tDisplayPictureData.set_image(pht);
            tDisplayPictureData.set_intPush("1");

            List<tDisplayPictureData> dtList = new ArrayList<>();
            dtList.add(tDisplayPictureData);

            new tDisplayPictureBL().saveData(dtList);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MainMenu Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.kalbenutritionals.app.kalbespgmobile/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MainMenu Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.kalbenutritionals.app.kalbespgmobile/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

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
