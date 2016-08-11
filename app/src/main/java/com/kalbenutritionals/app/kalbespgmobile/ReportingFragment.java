package com.kalbenutritionals.app.kalbespgmobile;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bl.tCustomerBaseBL;
import bl.tCustomerBaseDetailBL;
import bl.tSalesProductDetailBL;
import bl.tSalesProductHeaderBL;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import library.salesforce.common.ReportTable;
import library.salesforce.common.tCustomerBaseData;
import library.salesforce.common.tCustomerBaseDetailData;
import library.salesforce.common.tSalesProductDetailData;
import library.salesforce.common.tSalesProductHeaderData;

public class ReportingFragment extends Fragment {

    private SortableReportTableView ReportTableView;
    private Spinner spnTypeReport;

    String spinnerSelected = null;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_report, container, false);

        ReportTableView = (SortableReportTableView) v.findViewById(R.id.tableView);
        spnTypeReport = (Spinner) v.findViewById(R.id.spnType);

        List<String> arrData=new ArrayList<>();

        arrData.add(0, "Reso");
        arrData.add(1, "Customer Base");

        spnTypeReport.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData));

        spnTypeReport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerSelected = spnTypeReport.getSelectedItem().toString();
                generateReport(spinnerSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;
    }

    private void generateReport(String spinnerSelected) {
        String[] header;
        SimpleTableHeaderAdapter simpleTableHeaderAdapter;
        List<ReportTable> reportList;
        int i;

        switch (spinnerSelected){
            case "Reso":
                header = new String[6];
                header[1] = "SO";
                header[2] = "Total Product";
                header[3] = "Total Item";
                header[4] = "Total Price";
                header[5] = "Status";

                ReportTableView.setColumnCount(header.length);

                simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(getContext(), header);
                simpleTableHeaderAdapter.setTextColor(ContextCompat.getColor(getContext(), R.color.table_header_text));
                simpleTableHeaderAdapter.setTextSize(14);
                simpleTableHeaderAdapter.setPaddingBottom(20);
                simpleTableHeaderAdapter.setPaddingTop(20);

                ReportTableView.setColumnComparator(1, ReportComparators.getNoSoComparator());
                ReportTableView.setColumnComparator(2, ReportComparators.getTotalProductComparator());
                ReportTableView.setColumnComparator(3, ReportComparators.getTotalItemComparator());
                ReportTableView.setColumnComparator(4, ReportComparators.getTotalPriceComparator());
                ReportTableView.setColumnComparator(5, ReportComparators.getStatusComparator());

                ReportTableView.setColumnWeight(1, 2);
                ReportTableView.setColumnWeight(2, 1);
                ReportTableView.setColumnWeight(3, 1);
                ReportTableView.setColumnWeight(4, 1);
                ReportTableView.setColumnWeight(5, 1);

                ReportTableView.setHeaderAdapter(simpleTableHeaderAdapter);

                List<tSalesProductHeaderData> dt_so = new tSalesProductHeaderBL().getAllSalesProductHeader();
                reportList = new ArrayList<>();

                if(dt_so != null){
                    for(i = 0; i < dt_so.size(); i++){
                        ReportTable rt = new ReportTable();

                        rt.set_report_type("Reso");
                        rt.set_no_so(dt_so.get(i).get_intId());
                        rt.set_total_product(dt_so.get(i).get_intSumItem());
                        rt.set_total_price("Rp " + new clsMainActivity().convertNumberDec(Double.valueOf(dt_so.get(i).get_intSumAmount())));
                        rt.set_status(dt_so.get(i).get_intSubmit());

                        List<tSalesProductDetailData> dt_detail = new tSalesProductDetailBL().GetDataByNoSO(dt_so.get(i).get_intId());

                        int total_item = 0;

                        for(i = 0; i < dt_detail.size(); i++){
                            total_item = total_item + Integer.parseInt(dt_detail.get(i).get_intQty());
                        }

                        rt.set_total_item(String.valueOf(total_item));
                        rt.set_total_product(String.valueOf(dt_detail.size()));

                        reportList.add(rt);
                    }
                }

                ReportTableView.setDataAdapter(new ReportTableDataAdapter(getContext(), reportList));
                break;

            case "Customer Base":
                header = new String[5];
                header[1] = "Name";
                header[2] = "Sex";
                header[3] = "Number";
                header[4] = "Total Product";

                ReportTableView.setColumnCount(header.length);

                simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(getContext(), header);
                simpleTableHeaderAdapter.setTextColor(ContextCompat.getColor(getContext(), R.color.table_header_text));
                simpleTableHeaderAdapter.setTextSize(14);
                simpleTableHeaderAdapter.setPaddingBottom(20);
                simpleTableHeaderAdapter.setPaddingTop(20);

                ReportTableView.setColumnComparator(1, ReportComparators.getCustomerNameComparator());
                ReportTableView.setColumnComparator(2, ReportComparators.getCustomerSexComparator());
                ReportTableView.setColumnComparator(3, ReportComparators.getCustomerNumberComparator());
                ReportTableView.setColumnComparator(4, ReportComparators.getTotalProductComparator());

                ReportTableView.setColumnWeight(1, 2);
                ReportTableView.setColumnWeight(2, 1);
                ReportTableView.setColumnWeight(3, 1);
                ReportTableView.setColumnWeight(4, 1);

                ReportTableView.setHeaderAdapter(simpleTableHeaderAdapter);

                List<tCustomerBaseData> dt = new tCustomerBaseBL().getAllCustomerBase();
                reportList = new ArrayList<>();

                for(i = 0; i < dt.size(); i++){
                    ReportTable rt = new ReportTable();

                    rt.set_report_type("Customer Base");
                    rt.set_customer_sex(dt.get(i).get_txtSex());
                    rt.set_customer_name(dt.get(i).get_txtNama());
                    rt.set_customer_number(dt.get(i).get_txtTelp());

                    List<tCustomerBaseDetailData> dt_detail = new tCustomerBaseDetailBL().getData("'" + dt.get(i).get_intCustomerId() + "'");
                    rt.set_total_product(String.valueOf(dt_detail.size()));

                    reportList.add(rt);
                }

                ReportTableView.setDataAdapter(new ReportTableDataAdapter(getContext(), reportList));

                break;

            default:
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public class MyAdapter extends ArrayAdapter<String>
    {
        private List<String> arrayDataAdapyter;
        private Context Ctx;

        public List<String> getArrayDataAdapyter() {
            return arrayDataAdapyter;
        }
        public void setArrayDataAdapyter(List<String> arrayDataAdapyter) {
            this.arrayDataAdapyter = arrayDataAdapyter;
        }
        public Context getCtx() {
            return Ctx;
        }
        public void setCtx(Context ctx) {
            Ctx = ctx;
        }
        public MyAdapter(Context context, int textViewResourceId, List<String> objects)
        {
            super(context, textViewResourceId, objects);
            setCtx(context);
            setArrayDataAdapyter(objects);
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
            LayoutInflater inflater=getActivity().getLayoutInflater();
            View row=inflater.inflate(R.layout.custom_spinner, parent, false);
            if(getArrayDataAdapyter().size()>0){
                TextView label=(TextView)row.findViewById(R.id.tvTitle);
                //label.setText(arrData.get(position));
                label.setText(getArrayDataAdapyter().get(position));
                label.setTextColor(new Color().parseColor("#000000"));
                TextView sub=(TextView)row.findViewById(R.id.tvDesc);
                sub.setVisibility(View.INVISIBLE);
                sub.setVisibility(View.GONE);
                row.setBackgroundColor(new Color().parseColor("#FFFFFF"));
            }
            //sub.setText(mydata2[position]);
            return row;
        }

    }

}







