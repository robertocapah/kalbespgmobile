package com.kalbenutritionals.app.kalbespgmobile;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bl.clsMainBL;
import bl.mEmployeeSalesProductBL;
import bl.tAbsenUserBL;
import bl.tCustomerBaseBL;
import library.salesforce.common.ModelListview;
import library.salesforce.common.mEmployeeSalesProductData;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tCustomerBaseData;
import library.salesforce.common.tCustomerBaseDetailData;
import library.salesforce.dal.tCustomerBaseDetailDA;

/**
 * Created by Admin on 04-06-2015.
 */
public class CustomerBaseFragment extends Fragment implements View.OnClickListener {
    private ArrayList<ModelListview> modelItems;
//    private ArrayList<ModelListview> mDisplayedValues;
    ListView listView;
    MyAdapter dataAdapter;
    EditText etAlamat, etNama, etTelpon;
    RadioGroup rdSex;
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_customerbase_add,container,false);
        final EditText searchProduct = (EditText) v.findViewById(R.id.searchProduct);
        final TextView tvListproduct = (TextView) v.findViewById(R.id.textView10);
        final ScrollView scroll = (ScrollView) v.findViewById(R.id.scroll);
        etAlamat = (EditText) v.findViewById(R.id.etAlamat) ;
        etNama = (EditText) v.findViewById(R.id.etNama) ;
        etTelpon = (EditText) v.findViewById(R.id.etTelpon);
        rdSex = (RadioGroup) v.findViewById(R.id.radioSex);

        searchProduct.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    scroll.scrollTo(0, tvListproduct.getTop());
                }
            }
        });

        Button btnPreview = (Button) v.findViewById(R.id.btnPreview);
        btnPreview.setOnClickListener(this);

        List<mEmployeeSalesProductData> data = new mEmployeeSalesProductBL().GetAllData();
        modelItems = new ArrayList<ModelListview>();

        if(data.size() > 0){
            for(int i = 0; i < data.size(); i++){
                ModelListview dt = new ModelListview(data.get(i).get_txtBrandDetailGramCode(), data.get(i).get_txtProductBrandDetailGramName(), 0, false);
                modelItems.add(dt);
            }
        }

        dataAdapter = new MyAdapter(getActivity().getApplicationContext(), modelItems);
        listView = (ListView) v.findViewById(R.id.listView2);
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
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnPreview:
                int a = listView.getCount();
                int checked = 0;

                int selectedId = rdSex.getCheckedRadioButtonId();
                RadioButton radioSexButton = (RadioButton) v.findViewById(selectedId);

                tCustomerBaseData dt = new tCustomerBaseData();

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();

                clsMainActivity _clsMainActivity = new clsMainActivity();
                tAbsenUserData absenUserData = new tAbsenUserBL().getDataCheckInActive();

                dt.set_intCustomerId(_clsMainActivity.GenerateGuid());
                dt.set_intCustomerIdSync("1");
                dt.set_bitActive("1");
                dt.set_dtDate(dateFormat.format(cal.getTime()));
                dt.set_intSubmit("1");
                dt.set_txtAlamat(etAlamat.getText().toString());
                dt.set_txtBranchId(absenUserData.get_txtBranchCode());
                dt.set_txtNama(etNama.getText().toString());
                dt.set_txtOutletId(absenUserData.get_txtOutletCode());
                dt.set_txtUserId(absenUserData.get_txtUserId());
                dt.set_txtDeviceId(absenUserData.get_txtDeviceId());
                dt.set_txtSex(radioSexButton.getText().toString());
                dt.set_txtTelp(etTelpon.getText().toString());

                new tCustomerBaseBL().saveData(dt);

                clsMainBL _clsMainBL = new clsMainBL();

                SQLiteDatabase _db=_clsMainBL.getDb();

                for(int i = 0; i < a; i++){
                    if(modelItems.get(i).isSelected()){
                        tCustomerBaseDetailData dtDetail = new tCustomerBaseDetailData();
                        dtDetail.set_txtDataId(_clsMainActivity.GenerateGuid());
                        dtDetail.set_intCustomerId(dt.get_intCustomerId());
                        dtDetail.set_intQty("1");
                        dtDetail.set_txtProductBrandCode(modelItems.get(i).getId());
                        dtDetail.set_txtProductBrandName(modelItems.get(i).getName());

                        new tCustomerBaseDetailDA(_db).SaveDatatCustomerBaseDetailData(_db, dtDetail);
                    }
                }

                Toast.makeText(getActivity().getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                break;
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
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {

            ViewHolder holder = null;

            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null)
            {

                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = vi.inflate(R.layout.custom_listitem, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.textView1);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);

                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        CheckBox cb = (CheckBox) v;
                        ModelListview _state = (ModelListview) cb.getTag();
                        _state.setSelected(cb.isChecked());
                        for(int i = 0; i < mOriginalValues.size(); i++){
                            if(cb.getText().equals(mOriginalValues.get(i).getName())){
                                mOriginalValues.get(i).setSelected(cb.isChecked());
                                break;
                            }
                        }
                    }
                });

            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }

            ModelListview state = mDisplayedValues.get(position);

            holder.name.setText(state.getName());
            holder.name.setChecked(state.isSelected());

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
                    setListViewHeightBasedOnItems(listView);
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
                            String data = mOriginalValues.get(i).getName();
                            if (data.toLowerCase().startsWith(constraint.toString())) {
                                FilteredArrList.add(new ModelListview(mOriginalValues.get(i).getId(), mOriginalValues.get(i).getName(),mOriginalValues.get(i).getValue(), mOriginalValues.get(i).isSelected()));
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
}
