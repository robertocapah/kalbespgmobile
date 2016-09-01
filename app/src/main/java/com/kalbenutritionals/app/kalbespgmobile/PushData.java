package com.kalbenutritionals.app.kalbespgmobile;

import android.app.ActionBar.LayoutParams;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

import bl.clsHelperBL;
import bl.mProductBarcodeBL;
import library.salesforce.common.clsPushData;
import library.salesforce.common.dataJson;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tLeaveMobileData;
import library.salesforce.dal.clsHardCode;

public class PushData extends clsMainActivity {
	
	
	private TableLayout tlSO;
	private TableLayout tlActivity;
	private TableLayout tlCustomerBase;
	private TableLayout tlAbesen;
	private TableLayout tlLeave;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_masterpage);
//		setTitleForm("Absen");
		ScrollView scrollable_contents = (ScrollView) findViewById(R.id.scrollableContents);
		getLayoutInflater().inflate(R.layout.activity_push_data, scrollable_contents);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//		 tlGRN=(TableLayout)findViewById(R.id.tlGRN);
//		 tlPO=(TableLayout)findViewById(R.id.tlPO);
//		 tlSales=(TableLayout)findViewById(R.id.tlSales);
//		 tlPengeluaran=(TableLayout)findViewById(R.id.tlPengeluaran);
//		 tlStockOpname=(TableLayout)findViewById(R.id.tlStockOpname);
//		 tlStockAwal=(TableLayout)findViewById(R.id.tlStockAwal);
//		 tlSalesPackStock=(TableLayout)findViewById(R.id.tlSalesPackStock);
//		 tlSalesPackStockLog=(TableLayout)findViewById(R.id.tlSalesPackStockLog);
//		 tlTransactionDetail=(TableLayout)findViewById(R.id.tlTransactionDetail);
		 tlAbesen=(TableLayout)findViewById(R.id.tlAbesen);
//		 tlLeave=(TableLayout)findViewById(R.id.tlLeave);
		 Button btnPush=(Button) findViewById(R.id.btnPush);
		 btnPush.setOnClickListener(new View.OnClickListener() {
				//int intProcesscancel=0;
					@Override
					public void onClick(View v) {
						AsyncCallRole task=new AsyncCallRole();
						task.execute();
					}
		 });
	}
	private class AsyncCallRole extends AsyncTask<List<dataJson>, Void, List<dataJson>> {
		@Override
	    protected List<dataJson> doInBackground(List<dataJson>... params) {
	    	List<dataJson> roledata=new ArrayList<dataJson>();
	    	clsPushData dtJson= new clsHelperBL().pushData();
	    	dataJson dtdataJson=new dataJson();
			if(dtJson!=null){
				String versionName="";
	    		try {
	    			versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
	    		} catch (NameNotFoundException e2) {
	    			// TODO Auto-generated catch block
	    			e2.printStackTrace();
	    		}
	    		try {
	    			JSONArray Jresult= new clsHelperBL().callPushDataReturnJson(versionName,dtJson.getDtdataJson().txtJSON().toString(),dtJson.getFileUpload());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		dtdataJson.setIntResult("1");
	    		
			}
			else
			{
				dtdataJson.setIntResult("0");
				dtdataJson.setTxtMessage("No Data");
			}
			roledata.add(dtdataJson);
	        return roledata ;
	    }
	    
	    private ProgressDialog Dialog = new ProgressDialog(PushData.this);
	    @Override
	    protected void onCancelled() {
	    	Dialog.dismiss();
	    	   Toast toast = Toast.makeText(PushData.this, 
	    			   new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
	    	   toast.setGravity(Gravity.TOP, 25, 400);
	    	   toast.show();
	    }
	    @Override
	    protected void onPostExecute(List<dataJson> roledata) {
	        if(roledata.get(0).getIntResult().equals("1")){
	        	showToast(PushData.this, "Success Push Data");
	        }else{
	        	showToast(PushData.this, roledata.get(0).getTxtMessage());
	        }
	        Dialog.dismiss();
	    }
	    int intProcesscancel=0;
	    @Override
	    protected void onPreExecute() {
	                    //Make ProgressBar invisible
	        //pg.setVisibility(View.VISIBLE);
	    	Dialog.setMessage("Syncronize Data!!");
	    	Dialog.setCancelable(false);
	    	Dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
	    	    @Override
	    	    public void onClick(DialogInterface dialog, int which) {
	    	    	intProcesscancel=1;
	    	    }
	    	});
	    	Dialog.show();
	    }

	    @Override
	    protected void onProgressUpdate(Void... values) {
	        Dialog.dismiss();
	    }

	}

	public void inittLeave(Context _ctx,List<tLeaveMobileData> ListData){
//		tlLeave = (TableLayout) findViewById(R.id.tlLeave);
		TableRow.LayoutParams paramTextView = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);

		// Tittle
		TableRow row=new TableRow (_ctx);
		row.setBackgroundColor(Color.parseColor("#607D8B"));
		TextView txtVIndex=new TextView(_ctx);
		TextView txtVAlasan=new TextView(_ctx);
		TextView txtVDate=new TextView(_ctx);
		TextView txtVType=new TextView(_ctx);

		txtVIndex.setLayoutParams(paramTextView);
		txtVAlasan.setLayoutParams(paramTextView);
		txtVDate.setLayoutParams(paramTextView);
		txtVType.setLayoutParams(paramTextView);
		

		txtVIndex.setGravity(Gravity.CENTER);
		txtVAlasan.setGravity(Gravity.CENTER);
		txtVDate.setGravity(Gravity.CENTER);
		txtVType.setGravity(Gravity.CENTER);

		txtVIndex.setText("No.");        	
		txtVAlasan.setText("Alasan");
		txtVType.setText("Type");
		txtVDate.setText("Date");

		row.addView(txtVIndex);
		row.addView(txtVDate);
		row.addView(txtVType);
		row.addView(txtVAlasan);
		
		
		tlLeave.addView(row,0);
		mProductBarcodeBL _mProductBarcodeBL=new mProductBarcodeBL();
		if (ListData!=null){
			int index=0;
			for (tLeaveMobileData dt : ListData) {
				String txtAlasan= String.valueOf(dt.get_txtAlasan());
				String txtTypeAlasan=String.valueOf(dt.get_txtTypeAlasan());
				String txtDate= giveFormatDate(dt.get_dtLeave());
				
				row=new TableRow (_ctx);
				
				if(index%2==1){
	    			row.setBackgroundColor(Color.parseColor("#FFF8E0"));
	    		}else{
	    			row.setBackgroundColor(Color.parseColor("#ECEFF1"));
	    		}
	    		
				txtVIndex =new TextView(_ctx);
				txtVAlasan=new TextView(_ctx);
				txtVDate=new TextView(_ctx);
				txtVType=new TextView(_ctx);
				
	    		txtVIndex.setLayoutParams(paramTextView);
	    		txtVAlasan.setLayoutParams(paramTextView);
	    		txtVDate.setLayoutParams(paramTextView);
	    		txtVType.setLayoutParams(paramTextView);
	    	
	    		txtVIndex.setGravity(Gravity.CENTER);
	    		//txtVCodeProduct.setGravity(Gravity.CENTER);
	    		//txtVNamaProduct.setGravity(Gravity.CENTER);
	    		//txtVBatch.setGravity(Gravity.CENTER);
	    		//txtVED.setGravity(Gravity.CENTER);
	    		
	    		txtVIndex.setText(String.valueOf(index+1));        	
	    		txtVAlasan.setText(txtAlasan);
	    		txtVDate.setText(txtDate);
	    		txtVType.setText(txtTypeAlasan);
	        
				
				row.addView(txtVIndex);
				row.addView(txtVType);
				row.addView(txtVAlasan);
	        	row.addView(txtVDate);
	        	
	        	tlLeave.addView(row,index+1);
	        	index+=1;
			}
		}
	}
	public void inittAbsen(Context _ctx,List<tAbsenUserData> ListData){
		tlAbesen = (TableLayout) findViewById(R.id.tlAbesen);
		TableRow.LayoutParams paramTextView = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);

		// Tittle
		TableRow row=new TableRow (_ctx);
		row.setBackgroundColor(Color.parseColor("#607D8B"));
		TextView txtVIndex=new TextView(_ctx);
		TextView txtVOutletCode=new TextView(_ctx);
		TextView txtVDate=new TextView(_ctx);
		TextView txtVOutletName=new TextView(_ctx);

		txtVIndex.setLayoutParams(paramTextView);
		txtVOutletCode.setLayoutParams(paramTextView);
		txtVDate.setLayoutParams(paramTextView);
		txtVOutletName.setLayoutParams(paramTextView);
		

		txtVIndex.setGravity(Gravity.CENTER);
		txtVOutletCode.setGravity(Gravity.CENTER);
		txtVDate.setGravity(Gravity.CENTER);
		txtVOutletName.setGravity(Gravity.CENTER);

		txtVIndex.setText("No.");        	
		txtVOutletCode.setText("Outlet Code");
		txtVOutletName.setText("Outlet Name");
		txtVDate.setText("Date");

		row.addView(txtVIndex);
		row.addView(txtVOutletCode);
		row.addView(txtVOutletName);
		row.addView(txtVDate);
		
		tlAbesen.addView(row,0);
		mProductBarcodeBL _mProductBarcodeBL=new mProductBarcodeBL();
		if (ListData!=null){
			int index=0;
			for (tAbsenUserData dt : ListData) {
				String txtOutletName= String.valueOf(dt.get_txtOutletName());
				String txtOutletCode=String.valueOf(dt.get_txtOutletCode());
				String txtDate= giveFormatDate(dt.get_dtDateCheckIn());
				
				row=new TableRow (_ctx);
				
				if(index%2==1){
	    			row.setBackgroundColor(Color.parseColor("#FFF8E0"));
	    		}else{
	    			row.setBackgroundColor(Color.parseColor("#ECEFF1"));
	    		}
	    		
				txtVIndex =new TextView(_ctx);
				txtVOutletCode=new TextView(_ctx);
				txtVDate=new TextView(_ctx);
				txtVOutletName=new TextView(_ctx);
				
	    		txtVIndex.setLayoutParams(paramTextView);
	    		txtVOutletCode.setLayoutParams(paramTextView);
	    		txtVDate.setLayoutParams(paramTextView);
	    		txtVOutletName.setLayoutParams(paramTextView);
	    	
	    		txtVIndex.setGravity(Gravity.CENTER);
	    		//txtVCodeProduct.setGravity(Gravity.CENTER);
	    		//txtVNamaProduct.setGravity(Gravity.CENTER);
	    		//txtVBatch.setGravity(Gravity.CENTER);
	    		//txtVED.setGravity(Gravity.CENTER);
	    		
	    		txtVIndex.setText(String.valueOf(index+1));        	
	    		txtVOutletCode.setText(txtOutletCode);
	    		txtVDate.setText(txtDate);
	    		txtVOutletName.setText(txtOutletName);
	        
				
				row.addView(txtVIndex);
				row.addView(txtVOutletCode);
				row.addView(txtVOutletName);
	        	row.addView(txtVDate);
	        	
	        	tlAbesen.addView(row,index+1);
	        	index+=1;
			}
		}
	}
}
