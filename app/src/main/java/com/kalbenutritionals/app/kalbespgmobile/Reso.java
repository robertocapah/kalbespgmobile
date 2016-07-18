package com.kalbenutritionals.app.kalbespgmobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bl.clsMainBL;
import bl.mCounterNumberBL;
import bl.mEmployeeSalesProductBL;
import bl.tAbsenUserBL;
import bl.tSalesProductHeaderBL;
import library.salesforce.common.ModelListview;
import library.salesforce.common.mEmployeeSalesProductData;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tSalesProductDetailData;
import library.salesforce.common.tSalesProductHeaderData;
import library.salesforce.dal.enumCounterData;
import library.salesforce.dal.tSalesProductDetailDA;

public class Reso extends Fragment implements View.OnClickListener {
    TextView tv_date;
    TextView tv_noso;
    Button btn_preview;
    private EditText edketerangan;
    private String prvKet;
    private List<String> arrdata;
    private ArrayList<ModelListview> modelItems;
    private ArrayList<ModelListview> mDisplayedValues;
    MyAdapter dataAdapter;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_reso,container,false);
        tv_noso = (TextView) v.findViewById(R.id.txtNoreso);
        tv_date = (TextView) v.findViewById(R.id.txtviewDate);
        edketerangan = (EditText) v.findViewById(R.id.etKeterangan);
        final EditText searchProduct = (EditText) v.findViewById(R.id.searchProduct);
        prvKet = "ddada";
                //edketerangan.getText().toString();
        final String nosoNew = new mCounterNumberBL().getData(enumCounterData.NoDataSO);
        tv_noso.setText(nosoNew);
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(new Date());
        tv_date.setText(timeStamp);
        btn_preview = (Button) v.findViewById(R.id.btnPreviewReso);
        btn_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = listView.getCount();
                int checked = 0;

                //int selectedId = rdSex.getCheckedRadioButtonId();
                //RadioButton radioSexButton = (RadioButton) v.findViewById(selectedId);

                tSalesProductHeaderData dt = new tSalesProductHeaderData();

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();

                clsMainActivity _clsMainActivity = new clsMainActivity();
                mEmployeeSalesProductData _mEmployeeSalesProductData = new mEmployeeSalesProductData();
                tAbsenUserData absenUserData = new tAbsenUserBL().getDataCheckInActive();

                dt.set_intId(tv_noso.getText().toString());
                dt.set_dtDate(dateFormat.format(cal.getTime()));
                dt.set_OutletCode(absenUserData.get_txtOutletCode());
                dt.set_OutletName(absenUserData.get_txtOutletName());
                dt.set_txtKeterangan(edketerangan.getText().toString());
                dt.set_intSumItem("1");
                dt.set_intSumAmount("100000");
                dt.set_UserId(absenUserData.get_txtUserId());
                dt.set_intSubmit("1");
                dt.set_intSync("0");
                dt.set_txtBranchName(absenUserData.get_txtBranchName());
                dt.set_intIdAbsenUser(absenUserData.get_intId());

                new tSalesProductHeaderBL().SaveData(dt);

                clsMainBL _clsMainBL = new clsMainBL();

                SQLiteDatabase _db=_clsMainBL.getDb();

                for(int i = 0; i < a; i++){
                    if(modelItems.get(i).get_value()>=0){
                        //tCustomerBaseDetailData dtDetail = new tCustomerBaseDetailData();
                        tSalesProductDetailData dtDetail = new tSalesProductDetailData();
                        dtDetail.set_intId(_clsMainActivity.GenerateGuid());
                        dtDetail.set_dtDate(dateFormat.format(cal.getTime()));
                        dtDetail.set_intPrice("100");
                        dtDetail.set_intQty(String.valueOf(modelItems.get(i).get_value()));
                        dtDetail.set_txtCodeProduct(_mEmployeeSalesProductData.get_txtBrandDetailGramCode());
                        dtDetail.set_txtKeterangan(edketerangan.getText().toString());
                        dtDetail.set_txtNameProduct(String.valueOf(modelItems.get(i).get_name()));
                        dtDetail.set_intTotal("100");
                        dtDetail.set_txtNoSo(tv_noso.getText().toString());
                        dtDetail.set_intActive("1");
                        dtDetail.set_txtNIK(_mEmployeeSalesProductData.get_txtNIK());

                        //new tCustomerBaseDetailDA(_db).SaveDatatCustomerBaseDetailData(_db, dtDetail);
                        new tSalesProductDetailDA(_db).SaveDatatSalesProductDetailData(_db, dtDetail);
                    }
                }

                Toast.makeText(getActivity().getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                funcPreviewSO(getContext());
                //Toast.makeText(getContext(), prvKet, Toast.LENGTH_SHORT).show();
            }
        });
        List<mEmployeeSalesProductData> employeeSalesProductDataList = new mEmployeeSalesProductBL().GetAllData();
        modelItems = new ArrayList<ModelListview>();

        if (employeeSalesProductDataList.size() > 0) {
            for (int i = 0; i < employeeSalesProductDataList.size(); i++) {
                ModelListview dt = new ModelListview();
                dt.set_id(employeeSalesProductDataList.get(i).get_txtBrandDetailGramCode());
                dt.set_name(employeeSalesProductDataList.get(i).get_txtProductBrandDetailGramName());
                dt.set_value(0);
                dt.set_selected(false);
                modelItems.add(dt);
            }
        }

        dataAdapter = new MyAdapter(getActivity().getApplicationContext(), modelItems);
        listView = (ListView) v.findViewById(R.id.lvProduk);
        listView.setAdapter(dataAdapter);
        listView.setTextFilterEnabled(true);

        setListViewHeightBasedOnItems(listView);
        searchProduct.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                List<mEmployeeSalesProductData> data = new mEmployeeSalesProductBL().GetDataByProductName(searchProduct.getText().toString());
                dataAdapter.getFilter().filter(s.toString());

            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {

    }
    private void showMyDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_preview_so);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

//        TextView txtnoreso = (TextView) dialog.findViewById(R.id.txtnoreso);
//        TextView txtketerangan = (TextView) dialog.findViewById(R.id.txtketerangan);

//        ListView listView = (ListView) dialog.findViewById(R.id.listView);
//        Button btnBtmLeft = (Button) dialog.findViewById(R.id.btnBtmLeft);
//        Button btnBtmRight = (Button) dialog.findViewById(R.id.btnBtmRight);

//        btnBtmLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        btnBtmRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // do whatever you want here
//            }
//        });

        /**
         * if you want the dialog to be specific size, do the following
         * this will cover 85% of the screen (85% width and 85% height)
         */
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dialogWidth = (int)(displayMetrics.widthPixels * 0.85);
        int dialogHeight = (int)(displayMetrics.heightPixels * 0.85);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
    private void funcPreviewSO(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View promptView = layoutInflater.inflate(R.layout.activity_preview_so, null);

        final TextView _tvNoSO = (TextView) promptView.findViewById(R.id.tvnoSOtbl);
        final  TextView _tvKet = (TextView) promptView.findViewById(R.id.tvkettbl);
        _tvNoSO.setText(new mCounterNumberBL().getData(enumCounterData.NoDataSO));
        _tvKet.setText(edketerangan.getText().toString());

//        List<mEmployeeSalesProductData> employeeSalesProductDataList = new mEmployeeSalesProductBL().GetAllData();
//        arrdata = new ArrayList<String>();
//        if (employeeSalesProductDataList.size() > 0) {
//            for (mEmployeeSalesProductData dt : employeeSalesProductDataList) {
//                arrdata.add(dt.get_txtBrandDetailGramCode()+ "\n" + dt.get_txtProductBrandDetailGramName());
//            }
//            ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), R.layout.activity_listview, arrdata);
//            ListView listView = (ListView) promptView.findViewById(R.id.lvProduks);
//            listView.setAdapter(adapter);
//        }

//        final TextView _tvDesc = (TextView) promptView.findViewById(R.id.tvDesc);
//        _tvDesc.setVisibility(View.INVISIBLE);
//        _tvConfirm.setText("");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                .setNegativeButton("Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        final AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }
    public class MyAdapter extends BaseAdapter implements Filterable {

        private ArrayList<ModelListview> mOriginalValues; // Original Values
        private ArrayList<ModelListview> mDisplayedValues;    // Values to be displayed

        public MyAdapter(Context context, ArrayList<ModelListview> mProductArrayList) {
            this.mOriginalValues = mProductArrayList;
            this.mDisplayedValues = mProductArrayList;
        }

        @Override
        public int getCount() {
            return mDisplayedValues.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder
        {
            EditText code;
            TextView name;
            //CheckBox name;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {

            ViewHolder holder = null;

            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null)
            {

                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = vi.inflate(R.layout.activity_listview, null);

                holder = new ViewHolder();
                    holder.name = (TextView) convertView.findViewById(R.id.textViewproduk);
                holder.code = (EditText) convertView.findViewById(R.id.editText4);
                //holder.code.setTextColor(Color.BLACK);


                convertView.setTag(holder);

                final ViewHolder finalHolder = holder;
                holder.code.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        EditText et = finalHolder.code;
                        for(int i = 0; i < mOriginalValues.size(); i++) {
                            if(finalHolder.name.getText().equals(mOriginalValues.get(i).get_name())){
                                if(s.length()==0){
                                    mOriginalValues.get(i).set_value(0);
                                    break;

                                } else {
                                    mOriginalValues.get(i).set_value(Integer.parseInt(et.getText().toString()));
                                    //et.setText("12");
                                    break;

                                }
                            }
                           // if (s.length() != 0)
//                            if(et.getText().length() !=0) {
//                                et.getText().toString();
//                            }
                        }

//                        Toast.makeText(getActivity().getApplicationContext(), "Clicked on Checkbox: " + et.getText() + " is " + et.isFocusable(),
//                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {


                    }
                });

            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }

            ModelListview state = mDisplayedValues.get(position);

            holder.name.setText(state.get_name());
            holder.code.setText(String.valueOf(state.get_value()));

            holder.name.setTag(state);

            return convertView;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint,FilterResults results) {

                    mDisplayedValues = (ArrayList<ModelListview>) results.values; // has the filtered values
                    notifyDataSetChanged();  // notifies the data with new filtered values
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                    ArrayList<ModelListview> FilteredArrList = new ArrayList<ModelListview>();

                    if (mOriginalValues == null) {
                        mOriginalValues = new ArrayList<ModelListview>(mDisplayedValues); // saves the original data in mOriginalValues
                    }

                    /********
                     *
                     *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                     *  else does the Filtering and returns FilteredArrList(Filtered)
                     *
                     ********/
                    if (constraint == null || constraint.length() == 0) {

                        // set the Original result to return
                        results.count = mOriginalValues.size();
                        results.values = mOriginalValues;
                    } else {
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < mOriginalValues.size(); i++) {
                            String data = mOriginalValues.get(i).get_name();
                            if (data.toLowerCase().startsWith(constraint.toString())) {
                                ModelListview dt = new ModelListview();
                                dt.set_id(mOriginalValues.get(i).get_id());
                                dt.set_name(mOriginalValues.get(i).get_name());
                                dt.set_value(mOriginalValues.get(i).get_value());
                                dt.set_selected(mOriginalValues.get(i).isSelected());
                                FilteredArrList.add(dt);}
                        }
                        // set the Filtered result to return
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }
            };
            return filter;
        }
    }
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

}

