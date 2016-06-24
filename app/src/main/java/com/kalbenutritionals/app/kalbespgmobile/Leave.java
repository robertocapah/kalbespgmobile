package com.kalbenutritionals.app.kalbespgmobile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import bl.mTypeLeaveBL;
import bl.tAbsenUserBL;
import bl.tDeviceInfoUserBL;
import bl.tLeaveMobileBL;
import library.salesforce.common.mTypeLeaveMobileData;
import library.salesforce.common.tDeviceInfoUserData;
import library.salesforce.common.tLeaveMobileData;
import library.salesforce.common.tUserLoginData;

public class Leave extends clsMainActivity {
	private TextView date;
	private Spinner spLeave;
	private List<String> arrData;
	private Button btnSaveleave;
	EditText edReason;
	private String typeAlasan;
	private String typeAlasanName;
	ArrayAdapter<String> dataAdapter ;
	private HashMap<String,String> HMLeave1 = new HashMap<String, String>();
	private HashMap<String,String> HMLeave2 = new HashMap<String, String>();
    @Override
	public void onBackPressed() {
		finish();
		Intent nextScreen = new Intent(getApplicationContext(), Home.class);
		startActivity(nextScreen);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leave);
		//setTitleForm("Leave");
		//ScrollView scrollable_contents = (ScrollView) findViewById(R.id.scrollableContents);
		//getLayoutInflater().inflate(R.layout.activity_leave, scrollable_contents);
		
		spLeave = (Spinner) findViewById(R.id.spnTypeLeave);
		btnSaveleave = (Button) findViewById(R.id.buttonSaveLeave);
		edReason  = (EditText) findViewById(R.id.editTextReason);
		date = (TextView) findViewById(R.id.textViewDateTime);
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(new Date());
		date.setText("Date " + timeStamp);
		List<mTypeLeaveMobileData> listDataLeave = new mTypeLeaveBL().GetAllData();
		arrData=new ArrayList<String>();
		if(listDataLeave!=null){
			for(mTypeLeaveMobileData dt :listDataLeave ){
				arrData.add(dt.get_txtTipeLeaveName());
				String idname = String.valueOf(dt.get_txtTipeLeaveName());
				HMLeave1.put(dt.get_txtTipeLeaveName(),dt.get_intTipeLeave());
				HMLeave2.put(dt.get_txtTipeLeaveName(),idname);
			}	
			//dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
             //      android.R.layout.simple_spinner_item, arrData);
			
    		//dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    		spLeave.setAdapter(new MyAdapter(getApplicationContext(), R.layout.custom_spinner, arrData));
    		//spLeave.setAdapter(dataAdapter);
		}
		
		
		btnSaveleave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(edReason.getText().equals("")){
					showToast(getApplicationContext(), "please fill reason!!!");
				}else{
					String idname = "1";
							//spLeave.getSelectedItem().toString();
					//String nameid = spLeave.getSelectedItem().toString();
					String idleave = HMLeave1.get(idname);
					String nameleave = HMLeave2.get(idname);
					//String nameLeave = HMLeave1.get(String.valueOf(value));
					tLeaveMobileData dataTypeLeaveMobileData = new tLeaveMobileData();
					List<tLeaveMobileData> leaveUserdatas = new ArrayList<tLeaveMobileData>();
					List<tDeviceInfoUserData> dataDeviceInfoUser = new tDeviceInfoUserBL().getData(1);
					String deviceInfo = String.valueOf(dataDeviceInfoUser.get(0).get_txtDeviceId());
					tUserLoginData dataUserActive = new tAbsenUserBL().getUserActive();
					String idUserActive = String.valueOf(dataUserActive.get_txtUserId());
					int index = new tLeaveMobileBL().getContactsCount()+1;
					dataTypeLeaveMobileData.set_intLeaveId(String.valueOf(index));
					dataTypeLeaveMobileData.set_intLeaveIdSync("0");
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	        		Calendar cal = Calendar.getInstance();
	            	dataTypeLeaveMobileData.set_dtLeave(dateFormat.format(cal.getTime()));
					dataTypeLeaveMobileData.set_intSubmit("1");
					dataTypeLeaveMobileData.set_txtAlasan(String.valueOf(edReason.getText()));
					dataTypeLeaveMobileData.set_txtDeviceId(deviceInfo);
					dataTypeLeaveMobileData.set_txtTypeAlasan(idleave);
					dataTypeLeaveMobileData.set_txtTypeAlasanName(nameleave);
					dataTypeLeaveMobileData.set_txtUserId(idUserActive);
					leaveUserdatas.add(dataTypeLeaveMobileData);
					new tLeaveMobileBL().saveData(leaveUserdatas);
					finish();
					Intent nextScreen = new Intent(getApplicationContext(), Home.class);
					startActivity(nextScreen);
				}

			}
		});
		tLeaveMobileBL _tLeaveMobileBL=new tLeaveMobileBL();
		List<tLeaveMobileData> listData=_tLeaveMobileBL.getData("");
		if(listData.size()>0){
			int intPosition=getSpinnerPositionByValue(HMLeave1, listData.get(0).get_txtTypeAlasan(), spLeave);
			spLeave.setSelection(intPosition);
			edReason.setText(listData.get(0).get_txtAlasan());
			edReason.setEnabled(false);
			spLeave.setEnabled(false);
			btnSaveleave.setVisibility(View.INVISIBLE);
		}
	}
	public class MyAdapter extends ArrayAdapter<String>
    {
            public MyAdapter(Context context, int textViewResourceId, List<String> objects) 
            {
                  super(context, textViewResourceId, objects);
                  // TODO Auto-generated constructor stub
            }
            
            
            @Override
            public View getDropDownView(int position, View convertView,ViewGroup parent)
            {
            return getCustomView(position, convertView, parent);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) 
            {
            return getCustomView(position, convertView, parent);
            }

	        public View getCustomView(int position, View convertView, ViewGroup parent) 
	        {
	            LayoutInflater inflater=getLayoutInflater();
	            View row=inflater.inflate(R.layout.custom_spinner, parent, false);
	            TextView label=(TextView)row.findViewById(R.id.tvTitle);
	            label.setText(arrData.get(position));
	            TextView sub=(TextView)row.findViewById(R.id.tvDesc);
	            sub.setVisibility(View.INVISIBLE);
	            sub.setVisibility(View.GONE);
	            //sub.setText(mydata2[position]);
	            //label.setTextColor(new Color().parseColor("#FFFFF"));
	            row.setBackgroundColor(new Color().parseColor("#6FBC20"));
	            return row;
	            }
	       
    	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
