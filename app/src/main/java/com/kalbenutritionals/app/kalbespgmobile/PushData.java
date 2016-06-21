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
import bl.tPOStatus_mobileBL;
import library.salesforce.common.clsPushData;
import library.salesforce.common.dataJson;
import library.salesforce.common.mItemSalesPack_StockData;
import library.salesforce.common.mItemSalesPack_StockLogData;
import library.salesforce.common.mProductBarcodeData;
import library.salesforce.common.mStockAwalData;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tGRNHeader_mobileData;
import library.salesforce.common.tLeaveMobileData;
import library.salesforce.common.tPOHeader_mobileData;
import library.salesforce.common.tPOStatus_mobileData;
import library.salesforce.common.tPenguaranHeader_MobileData;
import library.salesforce.common.tSalesOrderHeader_MobileData;
import library.salesforce.common.tStockOpnameHeader_mobileData;
import library.salesforce.common.tTransactionDetailData;
import library.salesforce.dal.clsHardCode;

public class PushData extends clsMainActivity {
	
	
	private TableLayout tlGRN;
	private TableLayout tlPO;
	private TableLayout tlSales;
	private TableLayout tlPengeluaran;
	private TableLayout tlStockOpname;
	private TableLayout tlStockAwal;
	private TableLayout tlSalesPackStock;
	private TableLayout tlSalesPackStockLog;
	private TableLayout tlTransactionDetail;
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
		 tlGRN=(TableLayout)findViewById(R.id.tlGRN);
		 tlPO=(TableLayout)findViewById(R.id.tlPO);
		 tlSales=(TableLayout)findViewById(R.id.tlSales);
		 tlPengeluaran=(TableLayout)findViewById(R.id.tlPengeluaran);
		 tlStockOpname=(TableLayout)findViewById(R.id.tlStockOpname);
		 tlStockAwal=(TableLayout)findViewById(R.id.tlStockAwal);
		 tlSalesPackStock=(TableLayout)findViewById(R.id.tlSalesPackStock);
		 tlSalesPackStockLog=(TableLayout)findViewById(R.id.tlSalesPackStockLog);
		 tlTransactionDetail=(TableLayout)findViewById(R.id.tlTransactionDetail);
		 tlAbesen=(TableLayout)findViewById(R.id.tlAbesen);
		 tlLeave=(TableLayout)findViewById(R.id.tlLeave);
		 Button btnPush=(Button) findViewById(R.id.btnPush);
		 btnPush.setOnClickListener(new View.OnClickListener() {
				//int intProcesscancel=0;
					@Override
					public void onClick(View v) {
						AsyncCallRole task=new AsyncCallRole();
						task.execute();
					}
		 });
		 loadData();
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
					new clsHelperBL().saveDataPush(dtJson.getDtdataJson(),Jresult);
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
	private void loadData(){
		 clsPushData dtclsPushData=new clsHelperBL().pushData();
		 if(dtclsPushData!=null){
			 dataJson dtJson =dtclsPushData.getDtdataJson();
			 if(dtJson.get_ListOftGRNHeader_mobileData()!=null)
				 initGRN(this,dtJson.get_ListOftGRNHeader_mobileData());
			 if(dtJson.get_ListOftPOHeader_mobileData()!=null)
				 initPO(this,dtJson.get_ListOftPOHeader_mobileData());
			 if(dtJson.get_ListOftSalesOrderHeader_MobileData()!=null)
				 initSales(this,dtJson.get_ListOftSalesOrderHeader_MobileData());
			 if(dtJson.get_ListOftPenguaranHeader_MobileData()!=null)
				 initPenguaran(this,dtJson.get_ListOftPenguaranHeader_MobileData());
			 if(dtJson.get_ListOftStockOpnameHeader_mobileData()!=null)
				 initStockOpname(this,dtJson.get_ListOftStockOpnameHeader_mobileData());
			 if(dtJson.get_ListOftTransactionDetailData()!=null)
				 initTransactionDetail(this,dtJson.get_ListOftTransactionDetailData());
			 if(dtJson.get_ListOfmItemSalesPack_StockData()!=null)
				 initMitemSalesPackStock(this,dtJson.get_ListOfmItemSalesPack_StockData());
			 if(dtJson.get_ListOfmItemSalesPack_StockLogData()!=null)
				 initMitemSalesPackStockLog(this,dtJson.get_ListOfmItemSalesPack_StockLogData());
			 if(dtJson.get_ListOfmStockAwalData()!=null)
				 initMStockAwal(this,dtJson.get_ListOfmStockAwalData());
			 if(dtJson.getListOftAbsenUserData()!=null)
				 inittAbsen(this,dtJson.getListOftAbsenUserData());
			 if(dtJson.getListOftLeaveMobileData()!=null)
				 inittLeave(this,dtJson.getListOftLeaveMobileData());
		 }
	}
	public void inittLeave(Context _ctx,List<tLeaveMobileData> ListData){
		tlLeave = (TableLayout) findViewById(R.id.tlLeave);
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
	public void initMStockAwal(Context _ctx,List<mStockAwalData> ListData){
		tlStockAwal = (TableLayout) findViewById(R.id.tlStockAwal);
		TableRow.LayoutParams paramTextView = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);

		// Tittle
		TableRow row=new TableRow (_ctx);
		row.setBackgroundColor(Color.parseColor("#607D8B"));
		TextView txtVIndex=new TextView(_ctx);
		TextView txtVNoTransaction=new TextView(_ctx);
		TextView txtVDate=new TextView(_ctx);
		TextView txtVOutlet=new TextView(_ctx);

		txtVIndex.setLayoutParams(paramTextView);
		txtVNoTransaction.setLayoutParams(paramTextView);
		txtVDate.setLayoutParams(paramTextView);
		txtVOutlet.setLayoutParams(paramTextView);
		

		txtVIndex.setGravity(Gravity.CENTER);
		txtVNoTransaction.setGravity(Gravity.CENTER);
		txtVDate.setGravity(Gravity.CENTER);
		txtVOutlet.setGravity(Gravity.CENTER);

		txtVIndex.setText("No.");        	
		txtVNoTransaction.setText("Product Name");
		txtVDate.setText("Qty");
		txtVOutlet.setText("Outlet Code");

		row.addView(txtVIndex);
		row.addView(txtVNoTransaction);
		row.addView(txtVDate);
		row.addView(txtVOutlet);
		tlStockAwal.addView(row,0);
		mProductBarcodeBL _mProductBarcodeBL=new mProductBarcodeBL();
		if (ListData!=null){
			int index=0;
			for (mStockAwalData dt : ListData) {
				mProductBarcodeData dtmProductBarcodeData=_mProductBarcodeBL.getData(dt.get_txtProductCode());
				String txtProductName= String.valueOf(dtmProductBarcodeData.get_txtProductName());
				String txtOutletCode=String.valueOf(dt.get_txtOutletCode());
				String txtQty= giveFormatDate(dt.get_intQty());
				String txtOutletName= String.valueOf(dt.get_txtOutletName());
				
				row=new TableRow (_ctx);
				
				if(index%2==1){
	    			row.setBackgroundColor(Color.parseColor("#FFF8E0"));
	    		}else{
	    			row.setBackgroundColor(Color.parseColor("#ECEFF1"));
	    		}
	    		
				txtVIndex =new TextView(_ctx);
				txtVNoTransaction=new TextView(_ctx);
				txtVDate=new TextView(_ctx);
				txtVOutlet=new TextView(_ctx);
				
	    		txtVIndex.setLayoutParams(paramTextView);
	    		txtVNoTransaction.setLayoutParams(paramTextView);
	    		txtVDate.setLayoutParams(paramTextView);
	    		txtVOutlet.setLayoutParams(paramTextView);
	    	
	    		txtVIndex.setGravity(Gravity.CENTER);
	    		//txtVCodeProduct.setGravity(Gravity.CENTER);
	    		//txtVNamaProduct.setGravity(Gravity.CENTER);
	    		//txtVBatch.setGravity(Gravity.CENTER);
	    		//txtVED.setGravity(Gravity.CENTER);
	    		
	    		txtVIndex.setText(String.valueOf(index+1));        	
	    		txtVNoTransaction.setText(txtProductName);
	    		txtVDate.setText(txtQty);
	    		txtVOutlet.setText(txtOutletCode+" - "+txtOutletName);
	        
				
				row.addView(txtVIndex);
				row.addView(txtVNoTransaction);
				row.addView(txtVDate);
	        	row.addView(txtVOutlet);
	        	
	        	tlStockAwal.addView(row,index+1);
	        	index+=1;
			}
		}
	}
	public void initMitemSalesPackStockLog(Context _ctx,List<mItemSalesPack_StockLogData> ListData){
		tlSalesPackStockLog = (TableLayout) findViewById(R.id.tlSalesPackStockLog);
		TableRow.LayoutParams paramTextView = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);

		// Tittle
		TableRow row=new TableRow (_ctx);
		row.setBackgroundColor(Color.parseColor("#607D8B"));
		TextView txtVIndex=new TextView(_ctx);
		TextView txtVNoTransaction=new TextView(_ctx);
		TextView txtVDate=new TextView(_ctx);
		TextView txtVOutlet=new TextView(_ctx);

		txtVIndex.setLayoutParams(paramTextView);
		txtVNoTransaction.setLayoutParams(paramTextView);
		txtVDate.setLayoutParams(paramTextView);
		txtVOutlet.setLayoutParams(paramTextView);
		

		txtVIndex.setGravity(Gravity.CENTER);
		txtVNoTransaction.setGravity(Gravity.CENTER);
		txtVDate.setGravity(Gravity.CENTER);
		txtVOutlet.setGravity(Gravity.CENTER);

		txtVIndex.setText("No.");        	
		txtVNoTransaction.setText("Product Name");
		txtVDate.setText("Qty");
		txtVOutlet.setText("Outlet Code");

		row.addView(txtVIndex);
		row.addView(txtVNoTransaction);
		row.addView(txtVDate);
		row.addView(txtVOutlet);
		tlSalesPackStockLog.addView(row,0);
		mProductBarcodeBL _mProductBarcodeBL=new mProductBarcodeBL();
		if (ListData!=null){
			int index=0;
			for (mItemSalesPack_StockLogData dt : ListData) {
				mProductBarcodeData dtmProductBarcodeData=_mProductBarcodeBL.getData(dt.get_intProductCode());
				String txtProductName= String.valueOf(dtmProductBarcodeData.get_txtProductName());
				String txtOutletCode=String.valueOf(dt.get_txtOutletCode());
				String txtQty= giveFormatDate(dt.get_intQtyAvailable());
				String txtOutletName= String.valueOf(dt.get_txtOutletName());
				
				row=new TableRow (_ctx);
				
				if(index%2==1){
	    			row.setBackgroundColor(Color.parseColor("#FFF8E0"));
	    		}else{
	    			row.setBackgroundColor(Color.parseColor("#ECEFF1"));
	    		}
	    		
				txtVIndex =new TextView(_ctx);
				txtVNoTransaction=new TextView(_ctx);
				txtVDate=new TextView(_ctx);
				txtVOutlet=new TextView(_ctx);
				
	    		txtVIndex.setLayoutParams(paramTextView);
	    		txtVNoTransaction.setLayoutParams(paramTextView);
	    		txtVDate.setLayoutParams(paramTextView);
	    		txtVOutlet.setLayoutParams(paramTextView);
	    	
	    		txtVIndex.setGravity(Gravity.CENTER);
	    		//txtVCodeProduct.setGravity(Gravity.CENTER);
	    		//txtVNamaProduct.setGravity(Gravity.CENTER);
	    		//txtVBatch.setGravity(Gravity.CENTER);
	    		//txtVED.setGravity(Gravity.CENTER);
	    		
	    		txtVIndex.setText(String.valueOf(index+1));        	
	    		txtVNoTransaction.setText(txtProductName);
	    		txtVDate.setText(txtQty);
	    		txtVOutlet.setText(txtOutletCode+" - "+txtOutletName);
	        
				
				row.addView(txtVIndex);
				row.addView(txtVNoTransaction);
				row.addView(txtVDate);
	        	row.addView(txtVOutlet);
	        	
	        	tlSalesPackStockLog.addView(row,index+1);
	        	index+=1;
			}
		}
	}
	public void initMitemSalesPackStock(Context _ctx,List<mItemSalesPack_StockData> ListData){
		tlSalesPackStock = (TableLayout) findViewById(R.id.tlSalesPackStock);
		TableRow.LayoutParams paramTextView = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);

		// Tittle
		TableRow row=new TableRow (_ctx);
		row.setBackgroundColor(Color.parseColor("#607D8B"));
		TextView txtVIndex=new TextView(_ctx);
		TextView txtVNoTransaction=new TextView(_ctx);
		TextView txtVDate=new TextView(_ctx);
		TextView txtVOutlet=new TextView(_ctx);

		txtVIndex.setLayoutParams(paramTextView);
		txtVNoTransaction.setLayoutParams(paramTextView);
		txtVDate.setLayoutParams(paramTextView);
		txtVOutlet.setLayoutParams(paramTextView);
		

		txtVIndex.setGravity(Gravity.CENTER);
		txtVNoTransaction.setGravity(Gravity.CENTER);
		txtVDate.setGravity(Gravity.CENTER);
		txtVOutlet.setGravity(Gravity.CENTER);

		txtVIndex.setText("No.");        	
		txtVNoTransaction.setText("Product Name");
		txtVDate.setText("Qty");
		txtVOutlet.setText("Outlet Code");

		row.addView(txtVIndex);
		row.addView(txtVNoTransaction);
		row.addView(txtVDate);
		row.addView(txtVOutlet);
		tlSalesPackStock.addView(row,0);
		mProductBarcodeBL _mProductBarcodeBL=new mProductBarcodeBL();
		if (ListData!=null){
			int index=0;
			for (mItemSalesPack_StockData dt : ListData) {
				mProductBarcodeData dtmProductBarcodeData=_mProductBarcodeBL.getData(dt.get_intProductCode());
				String txtProductName= String.valueOf(dtmProductBarcodeData.get_txtProductName());
				String txtOutletCode=String.valueOf(dt.get_txtOutletCode());
				String txtQty= giveFormatDate(dt.get_intQtyAvailable());
				String txtOutletName= String.valueOf(dt.get_txtOutletName());
				
				row=new TableRow (_ctx);
				
				if(index%2==1){
	    			row.setBackgroundColor(Color.parseColor("#FFF8E0"));
	    		}else{
	    			row.setBackgroundColor(Color.parseColor("#ECEFF1"));
	    		}
	    		
				txtVIndex =new TextView(_ctx);
				txtVNoTransaction=new TextView(_ctx);
				txtVDate=new TextView(_ctx);
				txtVOutlet=new TextView(_ctx);
				
	    		txtVIndex.setLayoutParams(paramTextView);
	    		txtVNoTransaction.setLayoutParams(paramTextView);
	    		txtVDate.setLayoutParams(paramTextView);
	    		txtVOutlet.setLayoutParams(paramTextView);
	    	
	    		txtVIndex.setGravity(Gravity.CENTER);
	    		//txtVCodeProduct.setGravity(Gravity.CENTER);
	    		//txtVNamaProduct.setGravity(Gravity.CENTER);
	    		//txtVBatch.setGravity(Gravity.CENTER);
	    		//txtVED.setGravity(Gravity.CENTER);
	    		
	    		txtVIndex.setText(String.valueOf(index+1));        	
	    		txtVNoTransaction.setText(txtProductName);
	    		txtVDate.setText(txtQty);
	    		txtVOutlet.setText(txtOutletCode+" - "+txtOutletName);
	        
				
				row.addView(txtVIndex);
				row.addView(txtVNoTransaction);
				row.addView(txtVDate);
	        	row.addView(txtVOutlet);
	        	
	        	tlSalesPackStock.addView(row,index+1);
	        	index+=1;
			}
		}
	}
	public void initTransactionDetail(Context _ctx,List<tTransactionDetailData> ListData){
		tlTransactionDetail = (TableLayout) findViewById(R.id.tlTransactionDetail);
		TableRow.LayoutParams paramTextView = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);

		// Tittle
		TableRow row=new TableRow (_ctx);
		row.setBackgroundColor(Color.parseColor("#607D8B"));
		TextView txtVIndex=new TextView(_ctx);
		TextView txtVNoTransaction=new TextView(_ctx);
		TextView txtVDate=new TextView(_ctx);
		TextView txtVOutlet=new TextView(_ctx);

		txtVIndex.setLayoutParams(paramTextView);
		txtVNoTransaction.setLayoutParams(paramTextView);
		txtVDate.setLayoutParams(paramTextView);
		txtVOutlet.setLayoutParams(paramTextView);
		

		txtVIndex.setGravity(Gravity.CENTER);
		txtVNoTransaction.setGravity(Gravity.CENTER);
		txtVDate.setGravity(Gravity.CENTER);
		txtVOutlet.setGravity(Gravity.CENTER);

		txtVIndex.setText("No.");        	
		txtVNoTransaction.setText("Product Name");
		txtVDate.setText("Qty");
		txtVOutlet.setText("Outlet Code");

		row.addView(txtVIndex);
		row.addView(txtVNoTransaction);
		row.addView(txtVDate);
		row.addView(txtVOutlet);
		tlTransactionDetail.addView(row,0);
		mProductBarcodeBL _mProductBarcodeBL=new mProductBarcodeBL();
		if (ListData!=null){
			int index=0;
			for (tTransactionDetailData dt : ListData) {
				mProductBarcodeData dtmProductBarcodeData=_mProductBarcodeBL.getData(dt.get_intProductCode());
				String txtNOTransactionDetail= String.valueOf(dtmProductBarcodeData.get_txtProductName());
				String txtOutletCode=String.valueOf(dt.get_txtOutletCode());
				String txtDate= giveFormatDate(dt.get_intQty());
				String txtOutletName= String.valueOf(dt.get_txtOutletName());
				
				row=new TableRow (_ctx);
				
				if(index%2==1){
	    			row.setBackgroundColor(Color.parseColor("#FFF8E0"));
	    		}else{
	    			row.setBackgroundColor(Color.parseColor("#ECEFF1"));
	    		}
	    		
				txtVIndex =new TextView(_ctx);
				txtVNoTransaction=new TextView(_ctx);
				txtVDate=new TextView(_ctx);
				txtVOutlet=new TextView(_ctx);
				
	    		txtVIndex.setLayoutParams(paramTextView);
	    		txtVNoTransaction.setLayoutParams(paramTextView);
	    		txtVDate.setLayoutParams(paramTextView);
	    		txtVOutlet.setLayoutParams(paramTextView);
	    	
	    		txtVIndex.setGravity(Gravity.CENTER);
	    		//txtVCodeProduct.setGravity(Gravity.CENTER);
	    		//txtVNamaProduct.setGravity(Gravity.CENTER);
	    		//txtVBatch.setGravity(Gravity.CENTER);
	    		//txtVED.setGravity(Gravity.CENTER);
	    		
	    		txtVIndex.setText(String.valueOf(index+1));        	
	    		txtVNoTransaction.setText(txtNOTransactionDetail);
	    		txtVDate.setText(txtDate);
	    		txtVOutlet.setText(txtOutletCode+" - "+txtOutletName);
	        
				
				row.addView(txtVIndex);
				row.addView(txtVNoTransaction);
				row.addView(txtVDate);
	        	row.addView(txtVOutlet);
	        	
	        	tlTransactionDetail.addView(row,index+1);
	        	index+=1;
			}
		}
	}
	public void initStockOpname(Context _ctx,List<tStockOpnameHeader_mobileData> ListData){
		tlStockOpname = (TableLayout) findViewById(R.id.tlStockOpname);
		TableRow.LayoutParams paramTextView = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);

		// Tittle
		TableRow row=new TableRow (_ctx);
		row.setBackgroundColor(Color.parseColor("#607D8B"));
		TextView txtVIndex=new TextView(_ctx);
		TextView txtVNoPenguaran=new TextView(_ctx);
		TextView txtVDate=new TextView(_ctx);
		TextView txtVOutlet=new TextView(_ctx);

		txtVIndex.setLayoutParams(paramTextView);
		txtVNoPenguaran.setLayoutParams(paramTextView);
		txtVDate.setLayoutParams(paramTextView);
		txtVOutlet.setLayoutParams(paramTextView);
		

		txtVIndex.setGravity(Gravity.CENTER);
		txtVNoPenguaran.setGravity(Gravity.CENTER);
		txtVDate.setGravity(Gravity.CENTER);
		txtVOutlet.setGravity(Gravity.CENTER);

		txtVIndex.setText("No.");        	
		txtVNoPenguaran.setText("No Stockopname");
		txtVDate.setText("Date");
		txtVOutlet.setText("Outlet Code");

		row.addView(txtVIndex);
		row.addView(txtVNoPenguaran);
		row.addView(txtVDate);
		row.addView(txtVOutlet);
		tlStockOpname.addView(row,0);
		
		if (ListData!=null){
			int index=0;
			for (tStockOpnameHeader_mobileData dt : ListData) {
				String txtNOAdj= String.valueOf(dt.get_txtNoAdj());
				String txtOutletCode=String.valueOf(dt.get_txtOutletCode());
				String txtDate= giveFormatDate(dt.get_dtDate());
				String txtOutletName= String.valueOf(dt.get_txtOutletName());
				
				row=new TableRow (_ctx);
				
				if(index%2==1){
	    			row.setBackgroundColor(Color.parseColor("#FFF8E0"));
	    		}else{
	    			row.setBackgroundColor(Color.parseColor("#ECEFF1"));
	    		}
	    		
				txtVIndex =new TextView(_ctx);
				txtVNoPenguaran=new TextView(_ctx);
				txtVDate=new TextView(_ctx);
				txtVOutlet=new TextView(_ctx);
				
	    		txtVIndex.setLayoutParams(paramTextView);
	    		txtVNoPenguaran.setLayoutParams(paramTextView);
	    		txtVDate.setLayoutParams(paramTextView);
	    		txtVOutlet.setLayoutParams(paramTextView);
	    	
	    		txtVIndex.setGravity(Gravity.CENTER);
	    		//txtVCodeProduct.setGravity(Gravity.CENTER);
	    		//txtVNamaProduct.setGravity(Gravity.CENTER);
	    		//txtVBatch.setGravity(Gravity.CENTER);
	    		//txtVED.setGravity(Gravity.CENTER);
	    		
	    		txtVIndex.setText(String.valueOf(index+1));        	
	    		txtVNoPenguaran.setText(txtNOAdj);
	    		txtVDate.setText(txtDate);
	    		txtVOutlet.setText(txtOutletCode+" - "+txtOutletName);
	        
				
				row.addView(txtVIndex);
				row.addView(txtVNoPenguaran);
				row.addView(txtVDate);
	        	row.addView(txtVOutlet);
	        	
	        	tlStockOpname.addView(row,index+1);
	        	index+=1;
			}
		}
	}
	public void initPenguaran(Context _ctx,List<tPenguaranHeader_MobileData> ListData){
		tlPengeluaran = (TableLayout) findViewById(R.id.tlPengeluaran);
		TableRow.LayoutParams paramTextView = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);

		// Tittle
		TableRow row=new TableRow (_ctx);
		row.setBackgroundColor(Color.parseColor("#607D8B"));
		TextView txtVIndex=new TextView(_ctx);
		TextView txtVNoPenguaran=new TextView(_ctx);
		TextView txtVDate=new TextView(_ctx);
		TextView txtVOutlet=new TextView(_ctx);

		txtVIndex.setLayoutParams(paramTextView);
		txtVNoPenguaran.setLayoutParams(paramTextView);
		txtVDate.setLayoutParams(paramTextView);
		txtVOutlet.setLayoutParams(paramTextView);
		

		txtVIndex.setGravity(Gravity.CENTER);
		txtVNoPenguaran.setGravity(Gravity.CENTER);
		txtVDate.setGravity(Gravity.CENTER);
		txtVOutlet.setGravity(Gravity.CENTER);

		txtVIndex.setText("No.");        	
		txtVNoPenguaran.setText("No Pengeluaran");
		txtVDate.setText("Date");
		txtVOutlet.setText("Outlet Code");

		row.addView(txtVIndex);
		row.addView(txtVNoPenguaran);
		row.addView(txtVDate);
		row.addView(txtVOutlet);
		tlPengeluaran.addView(row,0);
		
		if (ListData!=null){
			int index=0;
			for (tPenguaranHeader_MobileData dt : ListData) {
				String txtNOSalesOrder= String.valueOf(dt.get_txtNoPenguaran());
				String txtOutletCode=String.valueOf(dt.get_txtOutletCode());
				String txtDate= giveFormatDate(dt.get_dtDate());
				String txtOutletName= String.valueOf(dt.get_txtOutletName());
				
				row=new TableRow (_ctx);
				
				if(index%2==1){
	    			row.setBackgroundColor(Color.parseColor("#FFF8E0"));
	    		}else{
	    			row.setBackgroundColor(Color.parseColor("#ECEFF1"));
	    		}
	    		
				txtVIndex =new TextView(_ctx);
				txtVNoPenguaran=new TextView(_ctx);
				txtVDate=new TextView(_ctx);
				txtVOutlet=new TextView(_ctx);
				
	    		txtVIndex.setLayoutParams(paramTextView);
	    		txtVNoPenguaran.setLayoutParams(paramTextView);
	    		txtVDate.setLayoutParams(paramTextView);
	    		txtVOutlet.setLayoutParams(paramTextView);
	    	
	    		txtVIndex.setGravity(Gravity.CENTER);
	    		//txtVCodeProduct.setGravity(Gravity.CENTER);
	    		//txtVNamaProduct.setGravity(Gravity.CENTER);
	    		//txtVBatch.setGravity(Gravity.CENTER);
	    		//txtVED.setGravity(Gravity.CENTER);
	    		
	    		txtVIndex.setText(String.valueOf(index+1));        	
	    		txtVNoPenguaran.setText(txtNOSalesOrder);
	    		txtVDate.setText(txtDate);
	    		txtVOutlet.setText(txtOutletCode+" - "+txtOutletName);
	        
				
				row.addView(txtVIndex);
				row.addView(txtVNoPenguaran);
				row.addView(txtVDate);
	        	row.addView(txtVOutlet);
	        	
	        	tlPengeluaran.addView(row,index+1);
	        	index+=1;
			}
		}
	}
	public void initSales(Context _ctx,List<tSalesOrderHeader_MobileData> ListData){
		tlSales = (TableLayout) findViewById(R.id.tlSales);
		TableRow.LayoutParams paramTextView = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);

		// Tittle
		TableRow row=new TableRow (_ctx);
		row.setBackgroundColor(Color.parseColor("#607D8B"));
		TextView txtVIndex=new TextView(_ctx);
		TextView txtVNoSales=new TextView(_ctx);
		TextView txtVDate=new TextView(_ctx);
		TextView txtVOutlet=new TextView(_ctx);

		txtVIndex.setLayoutParams(paramTextView);
		txtVNoSales.setLayoutParams(paramTextView);
		txtVDate.setLayoutParams(paramTextView);
		txtVOutlet.setLayoutParams(paramTextView);
		

		txtVIndex.setGravity(Gravity.CENTER);
		txtVNoSales.setGravity(Gravity.CENTER);
		txtVDate.setGravity(Gravity.CENTER);
		txtVOutlet.setGravity(Gravity.CENTER);

		txtVIndex.setText("No.");        	
		txtVNoSales.setText("No Sales");
		txtVDate.setText("Date");
		txtVOutlet.setText("Outlet Code");

		row.addView(txtVIndex);
		row.addView(txtVNoSales);
		row.addView(txtVDate);
		row.addView(txtVOutlet);
		tlSales.addView(row,0);
		
		if (ListData!=null){
			int index=0;
			for (tSalesOrderHeader_MobileData dt : ListData) {
				String txtNOSalesOrder= String.valueOf(dt.get_txtNoSalesOrder());
				String txtOutletCode=String.valueOf(dt.get_txtOutletCode());
				String txtDate= giveFormatDate(dt.get_dtDate());
				String txtOutletName= String.valueOf(dt.get_txtOutletName());
				
				row=new TableRow (_ctx);
				
				if(index%2==1){
	    			row.setBackgroundColor(Color.parseColor("#FFF8E0"));
	    		}else{
	    			row.setBackgroundColor(Color.parseColor("#ECEFF1"));
	    		}
	    		
				txtVIndex =new TextView(_ctx);
				txtVNoSales=new TextView(_ctx);
				txtVDate=new TextView(_ctx);
				txtVOutlet=new TextView(_ctx);
				
	    		txtVIndex.setLayoutParams(paramTextView);
	    		txtVNoSales.setLayoutParams(paramTextView);
	    		txtVDate.setLayoutParams(paramTextView);
	    		txtVOutlet.setLayoutParams(paramTextView);
	    	
	    		txtVIndex.setGravity(Gravity.CENTER);
	    		//txtVCodeProduct.setGravity(Gravity.CENTER);
	    		//txtVNamaProduct.setGravity(Gravity.CENTER);
	    		//txtVBatch.setGravity(Gravity.CENTER);
	    		//txtVED.setGravity(Gravity.CENTER);
	    		
	    		txtVIndex.setText(String.valueOf(index+1));        	
	    		txtVNoSales.setText(txtNOSalesOrder);
	    		txtVDate.setText(txtDate);
	    		txtVOutlet.setText(txtOutletCode+" - "+txtOutletName);
	        
				
				row.addView(txtVIndex);
				row.addView(txtVNoSales);
				row.addView(txtVDate);
	        	row.addView(txtVOutlet);
	        	
	        	tlSales.addView(row,index+1);
	        	index+=1;
			}
		}
	}
	public void initPO(Context _ctx,List<tPOHeader_mobileData> ListData){
		tlPO = (TableLayout) findViewById(R.id.tlPO);
		TableRow.LayoutParams paramTextView = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);

		// Tittle
		TableRow row=new TableRow (_ctx);
		row.setBackgroundColor(Color.parseColor("#607D8B"));
		TextView txtVIndex=new TextView(_ctx);
		TextView txtVNoPO=new TextView(_ctx);
		TextView txtVStatus=new TextView(_ctx);
		TextView txtVOutlet=new TextView(_ctx);

		txtVIndex.setLayoutParams(paramTextView);
		txtVNoPO.setLayoutParams(paramTextView);
		txtVStatus.setLayoutParams(paramTextView);
		txtVOutlet.setLayoutParams(paramTextView);
		

		txtVIndex.setGravity(Gravity.CENTER);
		txtVNoPO.setGravity(Gravity.CENTER);
		txtVStatus.setGravity(Gravity.CENTER);
		txtVOutlet.setGravity(Gravity.CENTER);

		txtVIndex.setText("No.");        	
		txtVNoPO.setText("No GRN");
		txtVStatus.setText("Status");
		txtVOutlet.setText("Outlet Code");

		row.addView(txtVIndex);
		row.addView(txtVNoPO);
		row.addView(txtVStatus);
		row.addView(txtVOutlet);
		tlPO.addView(row,0);
		
		if (ListData!=null){
			int index=0;
			for (tPOHeader_mobileData dt : ListData) {
				String txtNOPO= String.valueOf(dt.get_txtNoPO());
				List<tPOStatus_mobileData> ListDataStatus= new tPOStatus_mobileBL().getStatusActiveByHeaderId(txtNOPO);
				String txtOutletCode=String.valueOf(dt.get_txtOutletCode());
				String txtStatus= "";
				if(ListDataStatus.size()>0)
					txtStatus= String.valueOf(ListDataStatus.get(0).get_txtStatus());
				String txtOutletName= String.valueOf(dt.get_txtOutletName());
				row=new TableRow (_ctx);
				
				if(index%2==1){
	    			row.setBackgroundColor(Color.parseColor("#FFF8E0"));
	    		}else{
	    			row.setBackgroundColor(Color.parseColor("#ECEFF1"));
	    		}
	    		
				txtVIndex =new TextView(_ctx);
				txtVNoPO=new TextView(_ctx);
				txtVStatus=new TextView(_ctx);
				txtVOutlet=new TextView(_ctx);
				
	    		txtVIndex.setLayoutParams(paramTextView);
	    		txtVNoPO.setLayoutParams(paramTextView);
	    		txtVStatus.setLayoutParams(paramTextView);
	    		txtVOutlet.setLayoutParams(paramTextView);
	    	
	    		txtVIndex.setGravity(Gravity.CENTER);
	    		//txtVCodeProduct.setGravity(Gravity.CENTER);
	    		//txtVNamaProduct.setGravity(Gravity.CENTER);
	    		//txtVBatch.setGravity(Gravity.CENTER);
	    		//txtVED.setGravity(Gravity.CENTER);
	    		
	    		txtVIndex.setText(String.valueOf(index+1));        	
	    		txtVNoPO.setText(txtNOPO);
	    		txtVStatus.setText(txtStatus);
	    		txtVOutlet.setText(txtOutletCode+" - "+txtOutletName);
	        
				
				row.addView(txtVIndex);
				row.addView(txtVNoPO);
				row.addView(txtVStatus);
	        	row.addView(txtVOutlet);
	        	
	        	tlPO.addView(row,index+1);
	        	index+=1;
			}
		}
	}
	public void initGRN(Context _ctx,List<tGRNHeader_mobileData> ListData){
		tlGRN = (TableLayout) findViewById(R.id.tlGRN);
		TableRow.LayoutParams paramTextView = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);

		// Tittle
		TableRow row=new TableRow (_ctx);
		row.setBackgroundColor(Color.parseColor("#607D8B"));
		TextView txtVIndex=new TextView(_ctx);
		TextView txtVNoGRN=new TextView(_ctx);
		TextView txtVNoMO=new TextView(_ctx);
		TextView txtVOutlet=new TextView(_ctx);

		txtVIndex.setLayoutParams(paramTextView);
		txtVNoGRN.setLayoutParams(paramTextView);
		txtVNoMO.setLayoutParams(paramTextView);
		txtVOutlet.setLayoutParams(paramTextView);
		

		txtVIndex.setGravity(Gravity.CENTER);
		txtVNoGRN.setGravity(Gravity.CENTER);
		txtVNoMO.setGravity(Gravity.CENTER);
		txtVOutlet.setGravity(Gravity.CENTER);

		txtVIndex.setText("No.");        	
		txtVNoGRN.setText("No GRN");
		txtVNoMO.setText("MO");
		txtVOutlet.setText("Outlet Code");

		row.addView(txtVIndex);
		row.addView(txtVNoGRN);
		row.addView(txtVNoMO);
		row.addView(txtVOutlet);
		tlGRN.addView(row,0);
		
		if (ListData!=null){
			int index=0;
			for (tGRNHeader_mobileData dt : ListData) {
				String txtNOGRN= String.valueOf(dt.get_txtNoGRN());
				String txtNOMO= String.valueOf(dt.get_txtNoMO());
				String txtOutletCode= String.valueOf(dt.get_txtOutletCode());
				String txtOutletName= String.valueOf(dt.get_txtOutletName());
				row=new TableRow (_ctx);
				
				if(index%2==1){
	    			row.setBackgroundColor(Color.parseColor("#FFF8E0"));
	    		}else{
	    			row.setBackgroundColor(Color.parseColor("#ECEFF1"));
	    		}
	    		
				txtVIndex =new TextView(_ctx);
				txtVNoGRN=new TextView(_ctx);
				txtVNoMO=new TextView(_ctx);
				txtVOutlet=new TextView(_ctx);
				
	    		txtVIndex.setLayoutParams(paramTextView);
	    		txtVNoGRN.setLayoutParams(paramTextView);
	    		txtVNoMO.setLayoutParams(paramTextView);
	    		txtVOutlet.setLayoutParams(paramTextView);
	    	
	    		txtVIndex.setGravity(Gravity.CENTER);
	    		//txtVCodeProduct.setGravity(Gravity.CENTER);
	    		//txtVNamaProduct.setGravity(Gravity.CENTER);
	    		//txtVBatch.setGravity(Gravity.CENTER);
	    		//txtVED.setGravity(Gravity.CENTER);
	    		
	    		txtVIndex.setText(String.valueOf(index+1));        	
	    		txtVNoGRN.setText(txtNOGRN);
	    		txtVNoMO.setText(txtNOMO);
	    		txtVOutlet.setText(txtOutletCode+" - "+txtOutletName);
	        
				
				row.addView(txtVIndex);
	        	row.addView(txtVNoGRN);
	        	row.addView(txtVNoMO);
	        	row.addView(txtVOutlet);
	        	
	        	tlGRN.addView(row,index+1);
	        	index+=1;
			}
		}
	}
}
