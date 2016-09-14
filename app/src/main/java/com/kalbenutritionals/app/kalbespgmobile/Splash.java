package com.kalbenutritionals.app.kalbespgmobile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.InputStream;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_progress_bar);
//        int c_green = 0xFFCCCCCC;

        GifView gifView = (GifView) findViewById(R.id.imageView) ;
        gifView.loadGIFResource(this, R.drawable.tes);

//        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        progressBar.getProgressDrawable().setColorFilter(c_green, PorterDuff.Mode.SRC_IN);

        Intent myIntent = new Intent(Splash.this, Splash.class);
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(Splash.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasReadContactsPermission = ContextCompat.checkSelfPermission(Splash.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (Build.VERSION.SDK_INT >= 23&&hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            checkPermission();

        } else if (Build.VERSION.SDK_INT >= 23&&hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED){
            doProcess();

        } else {
            doProcess();
        }
    }

    private class MYGIFView extends View {

        Movie movie,movie1;
        InputStream is=null,is1=null;
        long moviestart;
        public MYGIFView(Context context) {
            super(context);

            is=context.getResources().openRawResource(+ R.drawable.loading);
            movie=Movie.decodeStream(is);

        }

        @Override
        protected void onDraw(Canvas canvas) {

            canvas.drawColor(Color.WHITE);
            super.onDraw(canvas);
            long now=android.os.SystemClock.uptimeMillis();
//            System.out.println("now="+now);
            if (moviestart == 0) { // first time
                moviestart = now;

            }
//            System.out.println("\tmoviestart="+moviestart);
            int relTime = (int)((now - moviestart) % movie.duration()) ;
//            System.out.println("time="+relTime+"\treltime="+movie.duration());
            movie.setTime(relTime);
            movie.draw(canvas,this.getWidth(),this.getHeight());
            this.invalidate();
        }
    }

    private void checkPermission() {

        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(Splash.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasReadContactsPermission = ContextCompat.checkSelfPermission(Splash.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (!ActivityCompat.shouldShowRequestPermissionRationale(Splash.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showMessageOKCancel("You need to allow access. . .",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Splash.this,
                                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA},
                                    REQUEST_CODE_ASK_PERMISSIONS);
                            doProcess();
                        }
                    });
            return;

        }
        ActivityCompat.requestPermissions(Splash.this,
                new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA},
                REQUEST_CODE_ASK_PERMISSIONS);
        return;

    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Splash.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
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
}

