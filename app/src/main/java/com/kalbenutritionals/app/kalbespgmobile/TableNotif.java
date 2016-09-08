package com.kalbenutritionals.app.kalbespgmobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bl.tNotificationBL;
import come.example.viewbadger.ShortcutBadger;
import library.salesforce.common.tNotificationData;

//import com.kalbe.viewbadger.ShortcutBadger;
//import com.kalbe.badger.BadgeView;

@SuppressWarnings("deprecation")
@SuppressLint("NewApi") public class TableNotif extends Fragment {
	
    private TextView txtTitle;
    private TextView txtDesc;
    private ImageView txtImg;

	View v;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		//		setContentView(R.layout.activity_detail_notification);
		//setTitleForm("Notification Detail");
		//ScrollView scrollable_contents = (ScrollView) findViewById(R.id.scrollableContents);
		//getLayoutInflater().inflate(R.layout.activity_detail_notification, scrollable_contents);
		txtTitle = (TextView) v.findViewById(R.id.tv_detail_title);
		txtDesc = (TextView) v.findViewById(R.id.tv_detail_desc);
		txtImg = (ImageView) v.findViewById(R.id.imageViewDetailN);

		Intent i= getActivity().getIntent();
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
//				finish();
				Intent nextScreen = new Intent(getActivity(), LNotifi.class);
				startActivity(nextScreen);
			}

		}
		int totalStatus = new tNotificationBL().getContactsCountStatus();
		ShortcutBadger.applyCount(getActivity(), totalStatus);

		return v;

	}

//	private String MenuID;
//	  @Override
//		public void onBackPressed() {
//			finish();
//			Intent nextScreen = new Intent(getApplicationContext(), LNotifi.class);
//			startActivity(nextScreen);
//		}

}
