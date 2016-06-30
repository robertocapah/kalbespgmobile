package com.kalbenutritionals.app.kalbespgmobile;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bl.mEmployeeSalesProductBL;
import library.salesforce.common.ModelListview;
import library.salesforce.common.mEmployeeSalesProductData;

/**
 * Created by Admin on 04-06-2015.
 */
public class CustomerBaseFragment extends Fragment implements View.OnClickListener {
    private ArrayList<ModelListview> modelItems;
    private ArrayList<ModelListview> mDisplayedValues;
    ListView listView;
    MyAdapter dataAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customerbase_add,container,false);
        final EditText searchProduct = (EditText) v.findViewById(R.id.searchProduct);
        Button btnPreview = (Button) v.findViewById(R.id.btnPreview);
        btnPreview.setOnClickListener(this);

        List<mEmployeeSalesProductData> data = new mEmployeeSalesProductBL().GetAllData();
        modelItems = new ArrayList<ModelListview>();

        if(data.size() > 0){
            for(int i = 0; i < data.size(); i++){
                ModelListview dt = new ModelListview(data.get(i).get_txtProductBrandDetailGramName(), 0, false);
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
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnPreview:
                int a = listView.getCount();
                int checked = 0;

                for(int i = 0; i < a; i++){
                    if(modelItems.get(i).isSelected()){
                        checked++;
                    }
                }

                Toast.makeText(getActivity().getApplicationContext(), String.valueOf(checked), Toast.LENGTH_SHORT).show();
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


//    private class MyCustomAdapter extends ArrayAdapter<ModelListview>
//    {
//
//        private ArrayList<ModelListview> stateList;
//
//        public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<ModelListview> stateList)
//        {
//            super(getActivity().getApplicationContext(), textViewResourceId, stateList);
//            this.stateList = new ArrayList<ModelListview>();
//            this.stateList.addAll(stateList);
//        }
//
//        private class ViewHolder
//        {
//            TextView code;
//            CheckBox name;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent)
//        {
//
//            ViewHolder holder = null;
//
//            Log.v("ConvertView", String.valueOf(position));
//
//            if (convertView == null)
//            {
//
//                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                convertView = vi.inflate(R.layout.custom_listitem, null);
//
//                holder = new ViewHolder();
//                holder.code = (TextView) convertView.findViewById(R.id.textView1);
//                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
//
//                convertView.setTag(holder);
//
//                holder.name.setOnClickListener( new View.OnClickListener()
//                {
//                    public void onClick(View v)
//                    {
//                        CheckBox cb = (CheckBox) v;
//                        ModelListview _state = (ModelListview) cb.getTag();
//
//                        Toast.makeText(getActivity().getApplicationContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(),
//                                Toast.LENGTH_LONG).show();
//
//                        _state.setSelected(cb.isChecked());
//                    }
//                });
//
//            }
//            else
//            {
//                holder = (ViewHolder) convertView.getTag();
//            }
//
//            ModelListview state = stateList.get(position);
//
//            holder.name.setText(state.getName());
//            holder.name.setChecked(state.isSelected());
//
//            holder.name.setTag(state);
//
//            return convertView;
//        }
//
//        @Override
//        public Filter getFilter() {
//            Filter filter = new Filter() {
//
//                @SuppressWarnings("unchecked")
//                @Override
//                protected void publishResults(CharSequence constraint,Filter.FilterResults results) {
//
//                    mDisplayedValues = (ArrayList<ModelListview>) results.values; // has the filtered values
//                    dataAdapter.notifyDataSetChanged();  // notifies the data with new filtered values
//                }
//
//                @Override
//                protected Filter.FilterResults performFiltering(CharSequence constraint) {
//                    Filter.FilterResults results = new Filter.FilterResults();        // Holds the results of a filtering operation in values
//                    ArrayList<ModelListview> FilteredArrList = new ArrayList<ModelListview>();
//
//                    if (modelItems == null) {
//                        modelItems = new ArrayList<ModelListview>(mDisplayedValues); // saves the original data in mOriginalValues
//                    }
//
//                    /********
//                     *
//                     *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
//                     *  else does the Filtering and returns FilteredArrList(Filtered)
//                     *
//                     ********/
//                    if (constraint == null || constraint.length() == 0) {
//
//                        // set the Original result to return
//                        results.count = modelItems.size();
//                        results.values = modelItems;
//                    } else {
//                        constraint = constraint.toString().toLowerCase();
//                        for (int i = 0; i < modelItems.size(); i++) {
//                            String data = modelItems.get(i).getName();
//                            if (data.toLowerCase().startsWith(constraint.toString())) {
//                                FilteredArrList.add(new ModelListview(modelItems.get(i).getName(),modelItems.get(i).getValue(), modelItems.get(i).isSelected()));
//                            }
//                        }
//                        // set the Filtered result to return
//                        results.count = FilteredArrList.size();
//                        results.values = FilteredArrList;
//                    }
//                    return results;
//                }
//            };
//            return filter;
//        }
//
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

                        Toast.makeText(getActivity().getApplicationContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();

                        _state.setSelected(cb.isChecked());
                        mOriginalValues.get(position).setSelected(cb.isChecked());
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
                                FilteredArrList.add(new ModelListview(mOriginalValues.get(i).getName(),mOriginalValues.get(i).getValue(), mOriginalValues.get(i).isSelected()));
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
