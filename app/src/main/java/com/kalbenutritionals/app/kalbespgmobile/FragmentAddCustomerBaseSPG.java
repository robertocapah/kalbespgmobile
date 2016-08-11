package com.kalbenutritionals.app.kalbespgmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bl.clsMainBL;
import bl.mEmployeeSalesProductBL;
import bl.tAbsenUserBL;
import bl.tCustomerBaseBL;
import bl.tCustomerBasedMobileDetailBL;
import bl.tCustomerBasedMobileDetailProductBL;
import bl.tCustomerBasedMobileHeaderBL;
import library.salesforce.common.ModelListview;
import library.salesforce.common.mEmployeeSalesProductData;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tCustomerBaseData;
import library.salesforce.common.tCustomerBaseDetailData;
import library.salesforce.common.tCustomerBasedMobileDetailData;
import library.salesforce.common.tCustomerBasedMobileDetailProductData;
import library.salesforce.common.tCustomerBasedMobileHeaderData;
import library.salesforce.dal.tCustomerBaseDetailDA;

public class FragmentAddCustomerBaseSPG extends Fragment implements View.OnClickListener {
    private ArrayList<ModelListview> modelItems;
    private List<tCustomerBasedMobileDetailData> dtDetail;
    tCustomerBasedMobileHeaderData dtHeader;
    ListView listView;
    MyAdapter dataAdapter;
    EditText etEmail, etNama, etTelpon, etAlamat, etTelponKantor, etPinBBM;
    CheckBox cbPIC;
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dtDetail = new ArrayList<>();
        v = inflater.inflate(R.layout.fragment_customerbase_add, container, false);
        etAlamat = (EditText) v.findViewById(R.id.etAlamat);
        etNama = (EditText) v.findViewById(R.id.etNama);
        etTelpon = (EditText) v.findViewById(R.id.etTelpon);
        etEmail = (EditText) v.findViewById(R.id.etEmail);
        etTelponKantor = (EditText) v.findViewById(R.id.etTelponKantor);
        etPinBBM = (EditText) v.findViewById(R.id.etPinBBM);
        cbPIC = (CheckBox) v.findViewById(R.id.cbPIC);

        dtHeader = new tCustomerBasedMobileHeaderBL().getDataByBitActive();

        if (dtHeader.get_intTrCustomerId() != null) {
            etAlamat.setText(dtHeader.get_txtALamat());
            etNama.setText(dtHeader.get_txtNamaDepan());
            etTelpon.setText(dtHeader.get_txtTelp());
            etTelponKantor.setText(dtHeader.get_txtTelpKantor());
            etEmail.setText(dtHeader.get_txtEmail());
            etPinBBM.setText(dtHeader.get_txtPINBBM());
            cbPIC.setChecked(dtHeader.get_intPIC().equals("1") ? true : false);
        }

        Button btnAdd = (Button) v.findViewById(R.id.btnAdd);
        Button btnAddPerson = (Button) v.findViewById(R.id.btnAddPerson);
        Button btnSave = (Button) v.findViewById(R.id.btnSave);
        btnAdd.setOnClickListener(this);
        btnAddPerson.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddPerson:
                popUpAddPerson(new tCustomerBasedMobileDetailData());
                break;

            case R.id.btnAdd:
                LinearLayout lnTop = (LinearLayout) v.findViewById(R.id.linearLayoutTop);
                LinearLayout lnBottom = (LinearLayout) v.findViewById(R.id.linearLayoutBottom);
                TextView tvNama = (TextView) v.findViewById(R.id.tvNamaPreview);
                TextView tvTelp = (TextView) v.findViewById(R.id.tvTelpPreview);
                TextView tvAlamat = (TextView) v.findViewById(R.id.tvAlamatPreview);
                TextView tvEmail = (TextView) v.findViewById(R.id.tvEmailPreview);

                lnTop.setVisibility(View.GONE);
                lnBottom.setVisibility(View.VISIBLE);

                tvNama.setText("Nama : " + etNama.getText().toString());
                tvTelp.setText("Telp : " + etTelpon.getText().toString());
                tvAlamat.setText("Alamat : " + etAlamat.getText().toString());
                tvEmail.setText("Email : " + etEmail.getText().toString());

                saveCustomerBaseHeader();

                break;

            case R.id.btnSave:
                saveCustomerBase();
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void saveCustomerBase() {
        new tCustomerBasedMobileHeaderBL().submit();
        getActivity().finish();
        Intent intent = new Intent(getContext(),MainMenu.class);
        startActivity(intent);
        return;
    }

    private void popUpAddPerson(final tCustomerBasedMobileDetailData dataDetail) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View promptView = layoutInflater.inflate(R.layout.popup_add_customerbase, null);

        final EditText nama = (EditText) promptView.findViewById(R.id.etNama);
        final EditText searchProduct = (EditText) promptView.findViewById(R.id.searchProduct);
        listView = (ListView) promptView.findViewById(R.id.listView2);

        final List<mEmployeeSalesProductData> data = new mEmployeeSalesProductBL().GetAllData();
        modelItems = new ArrayList<>();

        if(dataDetail.get_intTrCustomerIdDetail() != null){
            nama.setText(dataDetail.get_txtNamaDepan());
        }

        if (data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                ModelListview dt = new ModelListview();
                dt.set_id(data.get(i).get_txtBrandDetailGramCode());
                dt.set_name(data.get(i).get_txtProductBrandDetailGramName());
                dt.set_value(0);
                dt.set_selected(false);
                modelItems.add(dt);
            }
        }

        dataAdapter = new MyAdapter(getActivity().getApplicationContext(), modelItems);

        listView.setAdapter(dataAdapter);
        listView.setTextFilterEnabled(true);

//        setListViewHeightBasedOnItems(listView);

        searchProduct.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dataAdapter.getFilter().filter(s.toString());
            }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Calendar cal = Calendar.getInstance();

                        tCustomerBasedMobileDetailData data = dataDetail;

                        if(data.get_intTrCustomerIdDetail() == null){
                            data = new tCustomerBasedMobileDetailData();
                            data.set_intTrCustomerIdDetail(new clsMainActivity().GenerateGuid());
                            data.set_intPIC(dataDetail.get_intPIC());
                        }

                        data.set_intTrCustomerId(dtHeader.get_intTrCustomerId());
                        data.set_txtNamaDepan(nama.getText().toString());
                        data.set_intNo(String.valueOf(dtDetail.size() + 1));
                        data.set_bitActive("1");
                        data.set_dtInserted(dateFormat.format(cal.getTime()));

                        dtDetail.add(data);

                        new tCustomerBasedMobileDetailBL().saveData(data);

                        new tCustomerBasedMobileDetailProductBL().deleteData(data.get_intTrCustomerIdDetail());

                        for (int i = 0; i < modelItems.size(); i++) {
                            if (modelItems.get(i).is_selected()) {
                                tCustomerBasedMobileDetailProductData dtProduct = new tCustomerBasedMobileDetailProductData();
                                dtProduct.set_intTrCustomerIdDetailProduct(new clsMainActivity().GenerateGuid());
                                dtProduct.set_intTrCustomerIdDetail(data.get_intTrCustomerIdDetail());
                                dtProduct.set_txtProductBrandCode(modelItems.get(i).get_id());
                                dtProduct.set_txtProductBrandName(modelItems.get(i).get_name());
                                dtProduct.set_bitActive("1");
                                dtProduct.set_txtProductBrandQty(String.valueOf(modelItems.get(i).get_value()));
                                dtProduct.set_dtInserted(dateFormat.format(cal.getTime()));

                                new tCustomerBasedMobileDetailProductBL().saveData(dtProduct);
                            }
                        }
                        setTablePerson(data.get_intTrCustomerIdDetail());
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        final AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }

    private void setTablePerson(String idCustomerDetail) {
        TableLayout tl = (TableLayout) v.findViewById(R.id.tlPerson);
        tl.removeAllViews();

        TableRow row = new TableRow(getContext());
        row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        String[] colTextHeader = {"Nama", "Produk", ""};

        for (String text : colTextHeader) {
            TextView tv = new TextView(getContext());
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

            tv.setTextSize(14);
            tv.setPadding(10, 10, 10, 10);
            tv.setText(text);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundColor(Color.parseColor("#4CAF50"));

            tv.setTextColor(Color.WHITE);
            row.addView(tv);
        }
        tl.addView(row);

        final List<tCustomerBasedMobileDetailData> dtListDetail = new tCustomerBasedMobileDetailBL().getAllDataByHeaderId(dtHeader.get_intTrCustomerId());

        for (int count = 0; count < dtListDetail.size(); count++) {

            List<tCustomerBasedMobileDetailProductData> dtListProduct = new tCustomerBasedMobileDetailProductBL().getDataByCustomerDetailId(dtListDetail.get(count).get_intTrCustomerIdDetail());

            if (dtListProduct != null) {
                row = new TableRow(getContext());
                row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                String product = "";
                for (tCustomerBasedMobileDetailProductData dataproduct : dtListProduct) {
                    product = product + "- " + dataproduct.get_txtProductBrandName() + " (" + dataproduct.get_txtProductBrandQty() + ")\n";
                }
                String[] colText = {dtListDetail.get(count).get_txtNamaDepan(), product, "Edit"};

                int j = 1;

                for (String text : colText) {
                    TextView tv = new TextView(getContext());

                    if (j % 2 == 0) {
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1));
                    } else {
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));
                    }
                    tv.setTextSize(14);
                    tv.setPadding(10, 10, 10, 10);
                    tv.setText(text);
                    tv.setGravity(Gravity.LEFT);

                    if (count % 2 == 0) {
                        tv.setBackgroundColor(Color.DKGRAY);
                    } else {
                        tv.setBackgroundColor(Color.GRAY);
                    }

                    tv.setTextColor(Color.BLACK);

                    if(j == 3){
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        final int finalCount = count;
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popUpAddPerson(dtListDetail.get(finalCount));
                            }
                        });
                    }

                    row.addView(tv);
                    j++;
                }
                tl.addView(row);
            }
        }
    }

    private void saveCustomerBaseHeader() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        if (dtHeader.get_intTrCustomerId() == null) {
            dtHeader = new tCustomerBasedMobileHeaderData();
            dtHeader.set_intTrCustomerId(new clsMainActivity().GenerateGuid());
        }

        dtHeader.set_txtBranchCode(new tAbsenUserBL().getDataCheckInActive().get_txtBranchCode());
        dtHeader.set_txtSumberData(new tAbsenUserBL().getDataCheckInActive().get_txtOutletCode());
        dtHeader.set_txtNamaSumberData(new tAbsenUserBL().getDataCheckInActive().get_txtOutletName());
        dtHeader.set_txtNamaDepan(etNama.getText().toString());
        dtHeader.set_txtTelp(etTelpon.getText().toString());
        dtHeader.set_txtTelpKantor(etTelponKantor.getText().toString());
        dtHeader.set_txtEmail(etEmail.getText().toString());
        dtHeader.set_txtPINBBM(etPinBBM.getText().toString());
        dtHeader.set_txtALamat(etAlamat.getText().toString());
        dtHeader.set_txtUserId(new tAbsenUserBL().getDataCheckInActive().get_txtUserId());

        if (cbPIC.isChecked()) {
            tCustomerBasedMobileDetailData data = new tCustomerBasedMobileDetailBL().getAllDataByHeaderIdandintPIC(dtHeader.get_intTrCustomerId());
            dtHeader.set_intPIC("1");

            if(data.get_intTrCustomerId() == null){
                data = new tCustomerBasedMobileDetailData();
                data.set_intTrCustomerIdDetail(new clsMainActivity().GenerateGuid());
            }

            data.set_intTrCustomerId(dtHeader.get_intTrCustomerId());
            data.set_txtNamaDepan(dtHeader.get_txtNamaDepan());
            data.set_intNo(String.valueOf(dtDetail.size() + 1));
            data.set_intPIC("1");
            data.set_bitActive("1");
            data.set_dtInserted(dateFormat.format(cal.getTime()));

            dtDetail.add(data);

            new tCustomerBasedMobileDetailBL().saveData(data);

            setTablePerson(data.get_intTrCustomerIdDetail());
        } else {
            dtHeader.set_intPIC("0");
            tCustomerBasedMobileDetailData data = new tCustomerBasedMobileDetailBL().getAllDataByHeaderIdandintPIC(dtHeader.get_intTrCustomerId());

            if(data != null){
                new tCustomerBasedMobileDetailBL().deleteData(data);
            }
        }

        dtHeader.set_txtDeviceId(new tAbsenUserBL().getDataCheckInActive().get_txtDeviceId());
        dtHeader.set_bitActive("1");
        dtHeader.set_dtDate(dateFormat.format(cal.getTime()));
        dtHeader.set_intSubmit("0");
        dtHeader.set_intSync("0");
        new tCustomerBasedMobileHeaderBL().saveData(dtHeader);
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

        private class ViewHolder {
            TextView code;
            CheckBox name;
            EditText values;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {

                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = vi.inflate(R.layout.custom_listitem, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.textView1);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                holder.values = (EditText) convertView.findViewById(R.id.editText4);

                convertView.setTag(holder);

                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        ModelListview _state = (ModelListview) cb.getTag();
                        _state.set_selected(cb.isChecked());
                        for (int i = 0; i < mOriginalValues.size(); i++) {
                            if (cb.getText().equals(mOriginalValues.get(i).get_name())) {
                                mOriginalValues.get(i).set_selected(cb.isChecked());
                                break;
                            }
                        }
                    }
                });

                final ViewHolder finalHolder = holder;
                holder.values.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        EditText et = finalHolder.values;
                        for (int i = 0; i < mOriginalValues.size(); i++) {
                            if (finalHolder.name.getText().equals(mOriginalValues.get(i).get_name())) {
                                if (s.length() == 0) {
                                    mOriginalValues.get(i).set_value(0);
                                    break;

                                } else {
                                    mOriginalValues.get(i).set_value(Integer.parseInt(et.getText().toString()));
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {


                    }
                });

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ModelListview state = mDisplayedValues.get(position);

            holder.name.setText(state.get_name());
            holder.name.setChecked(state.isSelected());
            holder.values.setText(String.valueOf(state.get_value()));

            holder.name.setTag(state);

            return convertView;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    mDisplayedValues = (ArrayList<ModelListview>) results.values; // has the filtered values
                    notifyDataSetChanged();  // notifies the data with new filtered values
//                    setListViewHeightBasedOnItems(listView);
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
                                FilteredArrList.add(dt);
                            }
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

    public void viewCBFragment() {
        Intent intent = new Intent(getContext(), MainMenu.class);
        intent.putExtra("key_view", "View Customer Base");
        getActivity().finish();
        startActivity(intent);
        return;
    }
}
