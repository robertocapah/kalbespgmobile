package com.kalbenutritionals.app.kalbespgmobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import bl.tNotificationBL;
import come.example.viewbadger.ShortcutBadger;
import library.salesforce.common.tNotificationData;

//import com.kalbe.viewbadger.ShortcutBadger;
//import com.kalbe.badger.BadgeView;

@SuppressWarnings("deprecation")
@SuppressLint("NewApi") public class TableNotif extends clsMainActivity {
	
    private TextView txtTitle;
    private TextView txtDesc;
    private ImageView txtImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_masterpage);
		//setTitleForm("Notification Detail");
		ScrollView scrollable_contents = (ScrollView) findViewById(R.id.scrollableContents);
		getLayoutInflater().inflate(R.layout.activity_detail_notification, scrollable_contents);	
		txtTitle = (TextView) findViewById(R.id.textViewDetailN1);
		txtDesc = (TextView) findViewById(R.id.textViewDetailN2);
		txtImg = (ImageView) findViewById(R.id.imageViewDetailN);
		
		Intent i= getIntent();
	    if(i.hasExtra("id")){
	    	String messageId = i.getStringExtra("id");
	        List<tNotificationData> tNotifId = new tNotificationBL().getData(messageId);
	        if(tNotifId!=null){
	        	tNotificationData dataStatus = new tNotificationData();
		        String titleEx = 	String.valueOf(tNotifId.get(0).get_txtTitle()) +"\n";					
		     	String descEx = String.valueOf(tNotifId.get(0).get_txtDescription())+"\n";
		     	String img = String.valueOf(tNotifId.get(0).get_txtImage());
		     	txtTitle.setText(titleEx);
		     	txtDesc.setText(descEx);

		     	if (img !=""){
		     		txtImg.setVisibility(View.GONE);
		     		txtImg.getLayoutParams().height = 0;
		     	} else {
		     		txtImg.setImageURI(Uri.parse(img));
		     	}
		     	
		     	tNotifId.add(dataStatus);
				new tNotificationBL().SaveDataUpdate(tNotifId);
	        }
	        else{
	        	finish();
				Intent nextScreen = new Intent(getApplicationContext(), LNotifi.class);
				startActivity(nextScreen);
	        }
	        	
	    }
	    int totalStatus = new tNotificationBL().getContactsCountStatus();
     	ShortcutBadger.applyCount(TableNotif.this, totalStatus);
	}
	

	private String MenuID;
	  @Override
		public void onBackPressed() {
			finish();
			Intent nextScreen = new Intent(getApplicationContext(), LNotifi.class);
			startActivity(nextScreen);
		}

}
