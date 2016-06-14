package com.kalbenutritionals.app.kalbespgmobile;

//import  com.aanjunian.app.

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;

import bl.clsMainBL;
import library.salesforce.common.clsHelper;
import library.salesforce.common.clsStatusMenuStart;
import library.salesforce.dal.enumStatusMenuStart;

public class ProgressBarActivity extends AppCompatActivity {
    private  int progressStatus = 0;
    private Handler handler = new Handler();
    long delay = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);

        //ActionBar actionBar = getActionBar();
        //actionBar.hide();

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final TextView tv2 = (TextView) findViewById(R.id.textView2);
        progressBar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        Timer runProgress = new Timer();
        TimerTask viewTask = new TimerTask() {

            public void run() {
                Intent myIntent = new Intent(ProgressBarActivity.this, Login.class);
                 clsHelper _clsHelper = new clsHelper();
                _clsHelper.createFolderApp();
                try {
                    clsStatusMenuStart _clsStatusMenuStart= new clsMainBL().checkUserActive();
                    if(_clsStatusMenuStart.get_intStatus()== enumStatusMenuStart.FormLogin){
                        myIntent = new Intent(ProgressBarActivity.this, Login.class);
                    }else if(_clsStatusMenuStart.get_intStatus()== enumStatusMenuStart.PushDataSPGMobile){
                        myIntent = new Intent(ProgressBarActivity.this, Login.class);
                    } else if(_clsStatusMenuStart.get_intStatus()== enumStatusMenuStart.UserActiveLogin){
                        myIntent = new Intent(ProgressBarActivity.this, Login.class);
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
