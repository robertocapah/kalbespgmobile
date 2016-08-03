package com.kalbenutritionals.app.kalbespgmobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
import library.salesforce.common.clsHelper;
import library.salesforce.common.mEmployeeSalesProductData;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tSalesProductDetailData;
import library.salesforce.common.tSalesProductHeaderData;
import library.salesforce.dal.enumCounterData;
import library.salesforce.dal.tSalesProductDetailDA;

public class Reso extends Fragment implements View.OnClickListener {
    TextView tv_date;
    TextView tv_noso;

    //Button btn_preview;
    private EditText edketerangan, searchProduct;
    private String prvKet, noso;
    private List<String> arrdata;
    private ArrayList<ModelListview> modelItems;
    private ArrayList<ModelListview> arrprview;
    private ArrayList<ModelListview> arrdataPriv;
    private ArrayList<ModelListview> mDisplayedValues;
    MyAdapter dataAdapter;
    PreviewAdapter dtAdapter;
    ListView listView, listView2, listView3;
    int selectedId;
    //private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //onBackPressed();

        selectedId = 0;
        View v = inflater.inflate(R.layout.activity_reso,container,false);
        tv_noso = (TextView) v.findViewById(R.id.txtNoreso);
        tv_date = (TextView) v.findViewById(R.id.txtviewDate);
        edketerangan = (EditText) v.findViewById(R.id.etKeterangan);
        searchProduct = (EditText) v.findViewById(R.id.searchProduct);
        List<tSalesProductHeaderData> dtta = new tSalesProductHeaderBL().getAllSalesProductHeader();
        if(dtta==null) {
            noso = new mCounterNumberBL().getData(enumCounterData.NoDataSO);

        } else {
            List<tSalesProductHeaderData> dttas = new tSalesProductHeaderBL().getLastData();
            clsHelper _clsHelper=new clsHelper();
            String oldVersion = dttas.get(0).get_intId();
            noso = _clsHelper.generateNewId(oldVersion, "-" , "5");
        }

        tv_noso.setText(noso);

        String timeStamp = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(new Date());
        tv_date.setText(timeStamp);
        Button btn_preview = (Button) v.findViewById(R.id.btnPreviewReso);
        btn_preview.setOnClickListener(this);

        List<mEmployeeSalesProductData> employeeSalesProductDataList = new mEmployeeSalesProductBL().GetAllData();
        modelItems = new ArrayList<ModelListview>();

        if (employeeSalesProductDataList.size() > 0) {
            for (int i = 0; i < employeeSalesProductDataList.size(); i++) {
                ModelListview dt = new ModelListview();
                dt.set_id(employeeSalesProductDataList.get(i).get_txtBrandDetailGramCode());
                dt.set_name(employeeSalesProductDataList.get(i).get_txtProductBrandDetailGramName());
                dt.set_price((employeeSalesProductDataList.get(i).get_decHJD()));
                dt.set_NIK(employeeSalesProductDataList.get(i).get_txtNIK());
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
            dataAdapter.getFilter().filter(s.toString());
            }
        });
        return v;
    }

    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }


    public void viewResoFragment(){
        selectedId=1;
        Toolbar toolbar;
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("View Reso");
        ViewResoFragment viewresofragment = new ViewResoFragment();
        android.support.v4.app.FragmentTransaction fragmentTransactionviewreso = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransactionviewreso.replace(R.id.frame,viewresofragment);
        fragmentTransactionviewreso.commit();
        selectedId = 1;
        return;
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId()) {
            case R.id.btnPreviewReso:
                int x = listView.getCount();
                arrdataPriv = new ArrayList<ModelListview>();
                for (int i = 0; i < x; i++) {
                    if (modelItems.get(i).get_value() > 0) {
                        ModelListview data = new ModelListview();
                        data.set_id(modelItems.get(i).get_id());
                        data.set_price(modelItems.get(i).get_price());
                        data.set_name(modelItems.get(i).get_name());
                        data.set_value(modelItems.get(i).get_value());
                        arrdataPriv.add(data);
                    }
                }
                if (edketerangan.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Keterangan Belum Diisi", Toast.LENGTH_SHORT).show();
                } else
                if (arrdataPriv.size()==0){
                    Toast.makeText(getContext(), "Silahkan Input Quantity Product", Toast.LENGTH_SHORT).show();
                } else if (arrdataPriv.size()>0){
                    int a = listView.getCount();
                    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                    final View promptView = layoutInflater.inflate(R.layout.activity_preview_so, null);

                    final TextView _tvNoSO = (TextView) promptView.findViewById(R.id.tvnoSOtbl);
                    final TextView _tvKet = (TextView) promptView.findViewById(R.id.tvkettbl);
                    _tvNoSO.setText(": " + new mCounterNumberBL().getData(enumCounterData.NoDataSO));
                    _tvKet.setText(": " + edketerangan.getText().toString());
                    arrdataPriv = new ArrayList<ModelListview>();
                    for (int i = 0; i < a; i++) {
                        if (modelItems.get(i).get_value() > 0) {
                            ModelListview data = new ModelListview();
                            data.set_id(modelItems.get(i).get_id());
                            data.set_price(modelItems.get(i).get_price());
                            data.set_name(modelItems.get(i).get_name());
                            data.set_value(modelItems.get(i).get_value());
                            arrdataPriv.add(data);
                        }
                    }

                    TableLayout tl = new TableLayout(getContext());
                    double qtySum = 0;
                    double qtyNum;
                    for (ModelListview dt : arrdataPriv) {
                        TableRow tr = new TableRow(getContext());
                        TableLayout.LayoutParams tableRowParams =
                                new TableLayout.LayoutParams
                                        (TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);

                        int leftMargin = 10;
                        int topMargin = 2;
                        int rightMargin = 10;
                        int bottomMargin = 2;

                        tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

                        tr.setLayoutParams(tableRowParams);

                        TextView product = new TextView(getContext());
                        product.setTextSize(12);
                        product.setWidth(300);
                        product.setText(dt.get_name());
                        tr.addView(product);

                        TextView qty = new TextView(getContext());
                        qty.setTextSize(12);
                        qty.setPadding(15, 0, 0, 0);
                        qty.setText(String.valueOf(dt.get_value()));
                        tr.addView(qty);

                        TextView price = new TextView(getContext());
                        price.setTextColor(Color.GREEN);
                        price.setTextSize(12);
                        price.setPadding(15, 0, 0, 0);
                        price.setText(dt.get_price());
                        tr.addView(price);

                        TextView amount = new TextView(getContext());
                        amount.setTextColor(Color.RED);
                        amount.setTextSize(12);
                        amount.setPadding(15, 0, 0, 0);
                        double prc = Double.valueOf(dt.get_price());
                        double itm = Double.valueOf(dt.get_value());
                        qtyNum = prc * itm;
                        qtySum += qtyNum;
                        amount.setText(String.valueOf(qtyNum));
                        tr.addView(amount);

                        tl.addView(tr, tableRowParams);
                    }

                    TableLayout tlb = (TableLayout) promptView.findViewById(R.id.tlProduct);
                    tlb.addView(tl);

                    final TextView tv_item = (TextView) promptView.findViewById(R.id.tvItemtbl);
                    tv_item.setTypeface(null, Typeface.BOLD);
                    int sumItem = arrdataPriv.size();
                    tv_item.setText(": " + String.valueOf(sumItem));

                    final TextView tv_amount = (TextView) promptView.findViewById(R.id.tvSumAmount);
                    tv_amount.setTypeface(null, Typeface.BOLD);
                    tv_amount.setText(": " + String.valueOf(qtySum));

                    final TextView tv_status = (TextView) promptView.findViewById(R.id.tvStatus);
                    tv_status.setTypeface(null, Typeface.BOLD);
                    tv_status.setText(": Open");

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                    alertDialogBuilder.setView(promptView);
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("Save",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            saveReso();
//                                            Intent myIntent = new Intent(getActivity(), MainMenu.class);
//                                            myIntent.putExtra("keyReso", "add_reso");
//                                            startActivity(myIntent);
                                            //dialog.dismiss();
                                            viewResoFragment();
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
                    break;
                }
        }

    }

    private void saveReso() {
        int a = listView.getCount();
        String nik = null;
        List<String> item = new ArrayList<>();
        arrdataPriv = new ArrayList<ModelListview>();
        double qntySum=0;
        double qntyNum;
        for (int i = 0; i < a; i++) {
            if (modelItems.get(i).get_value() > 0) {
                            double prc = Double.valueOf(modelItems.get(i).get_price());
                            double itm = Double.valueOf(modelItems.get(i).get_value());
                            ModelListview data = new ModelListview();
                            data.set_id(modelItems.get(i).get_id());
                            data.set_name(modelItems.get(i).get_name());
                            data.set_value(modelItems.get(i).get_value());
                            nik = data.set_NIK(String.valueOf(modelItems.get(i).get_NIK()));
                            qntyNum = prc*itm;
                            qntySum += qntyNum;
                            arrdataPriv.add(data);
                        }
                    }

                tSalesProductHeaderData dt = new tSalesProductHeaderData();

                java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();

                clsMainActivity _clsMainActivity = new clsMainActivity();
                mEmployeeSalesProductData _mEmployeeSalesProductData = new mEmployeeSalesProductData();
                tAbsenUserData absenUserData = new tAbsenUserBL().getDataCheckInActive();

                dt.set_intId(tv_noso.getText().toString());
                dt.set_dtDate(dateFormat.format(cal.getTime()));
                dt.set_OutletCode(absenUserData.get_txtOutletCode());
                dt.set_OutletName(absenUserData.get_txtOutletName());
                dt.set_txtKeterangan(edketerangan.getText().toString());
                dt.set_intSumItem(String.valueOf(arrdataPriv.size()));
                dt.set_intSumAmount(String.valueOf(qntySum));
                dt.set_UserId(absenUserData.get_txtUserId());
                dt.set_intSubmit("1");
                dt.set_intSync("0");
                dt.set_txtBranchCode(absenUserData.get_txtBranchCode());
                dt.set_txtBranchName(absenUserData.get_txtBranchName());
                dt.set_intIdAbsenUser(absenUserData.get_intId());
                dt.set_txtNIK(nik);

                new tSalesProductHeaderBL().SaveData(dt);

                clsMainBL _clsMainBL = new clsMainBL();

                SQLiteDatabase _db=_clsMainBL.getDb();
        for (int i = 0; i < a; i++) {
            if (modelItems.get(i).get_value() > 0) {
                double prc = Double.valueOf(modelItems.get(i).get_price());
                double itm = Double.valueOf(modelItems.get(i).get_value());
                tSalesProductDetailData dtDetail = new tSalesProductDetailData();
                dtDetail.set_intId(_clsMainActivity.GenerateGuid());
                dtDetail.set_dtDate(dateFormat.format(cal.getTime()));
                dtDetail.set_intPrice(modelItems.get(i).get_price());
                dtDetail.set_intQty(String.valueOf(modelItems.get(i).get_value()));
                dtDetail.set_txtCodeProduct(modelItems.get(i).get_id());
                dtDetail.set_txtKeterangan(edketerangan.getText().toString());
                dtDetail.set_txtNameProduct(String.valueOf(modelItems.get(i).get_name()));
                dtDetail.set_intTotal(String.valueOf(prc * itm));
                dtDetail.set_txtNoSo(tv_noso.getText().toString());
                dtDetail.set_intActive("1");
                dtDetail.set_txtNIK(modelItems.get(i).get_NIK());
                new tSalesProductDetailDA(_db).SaveDatatSalesProductDetailData(_db, dtDetail);
            }

        }

        Toast.makeText(getActivity().getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
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
//    private void funcPreviewSO(Context context) {
//        int a = listView.getCount();
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        final View promptView = layoutInflater.inflate(R.layout.activity_preview_so, null);
//
//        final TextView _tvNoSO = (TextView) promptView.findViewById(R.id.tvnoSOtbl);
//        final  TextView _tvKet = (TextView) promptView.findViewById(R.id.tvkettbl);
//        final ListView _lvProduct = (ListView) promptView.findViewById(R.id.lvProduks);
//        _tvNoSO.setText(new mCounterNumberBL().getData(enumCounterData.NoDataSO));
//        _tvKet.setText(edketerangan.getText().toString());
//
////        List<mEmployeeSalesProductData> employeeSalesProductDataList = new mEmployeeSalesProductBL().GetAllData();
////        arrdata = new ArrayList<String>();
////        if (employeeSalesProductDataList.size() > 0) {
////            for (mEmployeeSalesProductData dt : employeeSalesProductDataList) {
////                arrdata.add(dt.get_txtBrandDetailGramCode()+ "\n" + dt.get_txtProductBrandDetailGramName());
////            }
////            ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), R.layout.activity_listview, arrdata);
////            ListView listView = (ListView) promptView.findViewById(R.id.lvProduks);
////            listView.setAdapter(adapter);
////        }
//
////        final TextView _tvDesc = (TextView) promptView.findViewById(R.id.tvDesc);
////        _tvDesc.setVisibility(View.INVISIBLE);
////        _tvConfirm.setText("");
////        List<mEmployeeBranchData> listDataBranch = new mEmployeeBranchBL().GetAllData();
//        List<tSalesProductDetailData> _tSalesProductDetailData = new tSalesProductDetailBL().GetDataByNoSO(new mCounterNumberBL().getData(enumCounterData.NoDataSO));
//        List<String> item = new ArrayList<>();
//        arrprview = new ArrayList<ModelListview>();
//
////        for(int i = 0; i < a; i++){
//            if(_tSalesProductDetailData.size()>0){
//                for(tSalesProductDetailData dt : _tSalesProductDetailData){
//                    //item.add(modelItems.get(i).get_name()+ modelItems.get(i).get_value());
//                    ModelListview dtt = new ModelListview();
//                    //dt.set_name(modelItems.get(i).get_name());
//                    //dt.set_value(modelItems.get(i).get_value());
//                    dtt.set_name(dt.get_txtNameProduct());
//                    dtt.set_value(Integer.parseInt(dt.get_intQty()));
//                    arrprview.add(dtt);
//                }
//
//            }
////        }
////        _lvProduct.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, item));
////        setListViewHeightBasedOnItems(_lvProduct);
//        dataAdapter = new MyAdapter(getActivity().getApplicationContext(), arrprview);
//        listView = (ListView) promptView.findViewById(R.id.lvProduks);
//        listView.setAdapter(dataAdapter);
//
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//        alertDialogBuilder.setView(promptView);
//        alertDialogBuilder
//                .setCancelable(false)
//                .setPositiveButton("Save",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                ViewResoFragment viewresofragment = new ViewResoFragment();
//                                android.support.v4.app.FragmentTransaction fragmentTransactionviewreso = getActivity().getSupportFragmentManager().beginTransaction();
//                                fragmentTransactionviewreso.replace(R.id.frame,viewresofragment);
//                                fragmentTransactionviewreso.commit();
//                            }
//                        })
//                .setNegativeButton("Close",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//        final AlertDialog alertD = alertDialogBuilder.create();
//        alertD.show();
//    }
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
                            if(finalHolder.name.getText().equals(mOriginalValues.get(i).get_id() + "\n" + mOriginalValues.get(i).get_name())){
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
//            if (state.get_id()==null){
//                holder.name.setText(state.get_name());
//                holder.code.setText(String.valueOf(state.get_value()));
//                holder.code.setFocusable(false);
//            } else {
                holder.name.setText(state.get_id()+"\n"+state.get_name());
                holder.code.setText(String.valueOf(state.get_value()));
//            }

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

    private class PreviewAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();

        if(selectedId == 1) {

            menu.add(0, 1, 0, "Add Reso");

        }
        super.onPrepareOptionsMenu(menu);
    }

}

