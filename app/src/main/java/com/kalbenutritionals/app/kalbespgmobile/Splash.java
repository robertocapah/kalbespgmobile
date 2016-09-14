package com.kalbenutritionals.app.kalbespgmobile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;

import addons.gifview.GifView;
import bl.clsMainBL;
import library.salesforce.common.clsHelper;
import library.salesforce.common.clsStatusMenuStart;
import library.salesforce.dal.enumStatusMenuStart;
import service.MyServiceNative;

public class Splash extends AppCompatActivity {
    long delay = 5000;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private static int result = 0;

    private TextView version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_progress_bar);
        version = (TextView) findViewById(R.id.tv_version);

        try {
            version.setText(getPackageManager().getPackageInfo(getPackageName(), 0).versionName + " \u00a9 KN-IT");
//            version.setTypeface(Typeface.SERIF);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

//        int c_green = 0xFFCCCCCC;

        GifView gifView = (GifView) findViewById(R.id.imageView) ;
        gifView.loadGIFResource(this, R.drawable.loading);

//        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        progressBar.getProgressDrawable().setColorFilter(c_green, PorterDuff.Mode.SRC_IN);

        Intent myIntent = new Intent(Splash.this, Splash.class);
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(Splash.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasReadContactsPermission = ContextCompat.checkSelfPermission(Splash.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (Build.VERSION.SDK_INT >= 23&&hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            boolean a = checkPermission();

        } else if (Build.VERSION.SDK_INT >= 23&&hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED){
            doProcess();

        } else {
            doProcess();
        }
    }

    private boolean checkPermission() {

        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(Splash.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasReadContactsPermission = ContextCompat.checkSelfPermission(Splash.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        AlertDialog.Builder builder = new AlertDialog.Builder(Splash.this);
        builder.setMessage("You need to allow access. . .");
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(Splash.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    ActivityCompat.requestPermissions(Splash.this,
                            new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA},
                            REQUEST_CODE_ASK_PERMISSIONS);
                    dialog.dismiss();

                }
                ActivityCompat.requestPermissions(Splash.this,
                        new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

        if (result==1){
            return true;
        } else {
            return false;
        }
    }

    private void doProcess() {

        Timer runProgress = new Timer();
        TimerTask viewTask = new TimerTask() {

            public void run() {
                Intent myIntent = new Intent(Splash.this, Login.class);
                clsHelper _clsHelper = new clsHelper();
                _clsHelper.createFolderApp();
                try {
                    clsStatusMenuStart _clsStatusMenuStart = new clsMainBL().checkUserActive();
                    if (_clsStatusMenuStart.get_intStatus() == enumStatusMenuStart.FormLogin) {
                        myIntent = new Intent(Splash.this, Login.class);
                    } else if (_clsStatusMenuStart.get_intStatus() == enumStatusMenuStart.PushDataSPGMobile) {
//                        myIntent = new Intent(ProgressBarActivity.this, PushData.class);
                    } else if (_clsStatusMenuStart.get_intStatus() == enumStatusMenuStart.UserActiveLogin) {
//                        myIntent = new Intent(ProgressBarActivity.this, Home.class);
                        myIntent = new Intent(Splash.this, MainMenu.class);
                        //myIntent.putExtra("key_view", "main_menu");
                        startService(new Intent(getApplicationContext(), MyServiceNative.class));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                finish();
                startActivity(myIntent);
            }
        };
        runProgress.schedule(viewTask, delay);
    }

    @Override
    protected void onResume() {
        super.onResume();

        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(Splash.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (Build.VERSION.SDK_INT >= 23&&hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            boolean a = checkPermission();

        } else if (Build.VERSION.SDK_INT >= 23&&hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED){
            doProcess();

        } else {
            doProcess();
        }


    }
}