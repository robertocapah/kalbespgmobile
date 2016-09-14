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

import addons.tableview.ReportComparators;
import addons.tableview.ReportTableDataAdapter;
import addons.tableview.SortableReportTableView;
import bl.tCustomerBasedMobileDetailBL;
import bl.tCustomerBasedMobileHeaderBL;
import bl.tSalesProductDetailBL;
import bl.tSalesProductHeaderBL;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import library.salesforce.common.ReportTable;
import library.salesforce.common.tCustomerBasedMobileDetailData;
import library.salesforce.common.tCustomerBasedMobileHeaderData;
import library.salesforce.common.tSalesProductDetailData;
import library.salesforce.common.tSalesProductHeaderData;

public class FragmentReporting extends Fragment {

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
                header[2] = "Tot. Product";
                header[3] = "Tot. Item";
                header[4] = "Tot. Price";
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
                ReportTableView.setColumnWeight(4, 2);
                ReportTableView.setColumnWeight(5, 1);

                ReportTableView.setHeaderAdapter(simpleTableHeaderAdapter);

                List<tSalesProductHeaderData> dt_so = new tSalesProductHeaderBL().getAllSalesProductHeader();
                reportList = new ArrayList<>();

                if(dt_so != null){
                    for(tSalesProductHeaderData datas : dt_so ){
                        ReportTable rt = new ReportTable();

                        rt.set_report_type("Reso");
                        rt.set_no_so(datas.get_intId());
                        rt.set_total_product(datas.get_intSumItem());
                        rt.set_total_price(new clsMainActivity().convertNumberDec(Double.valueOf(datas.get_intSumAmount())));
                        if (datas.get_intSubmit().equals("1")&&datas.get_intSync().equals("0")){
                            rt.set_status("Submit");
                        } else if (datas.get_intSubmit().equals("1")&&datas.get_intSync().equals("1")){
                            rt.set_status("Sync");
                        }

                        List<tSalesProductDetailData> dt_detail = new tSalesProductDetailBL().GetDataByNoSO(datas.get_intId());

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
                header = new String[7];
                header[1] = "Sub. Id";
                header[2] = "Name";
                header[3] = "No Telp";
                header[4] = "PIC";
                header[5] = "Tot. Member";
                header[6] = "Tot. Product";

                ReportTableView.setColumnCount(header.length);

                simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(getContext(), header);
                simpleTableHeaderAdapter.setTextColor(ContextCompat.getColor(getContext(), R.color.table_header_text));
                simpleTableHeaderAdapter.setTextSize(14);
                simpleTableHeaderAdapter.setPaddingBottom(20);
                simpleTableHeaderAdapter.setPaddingTop(20);

                ReportTableView.setColumnComparator(1, ReportComparators.getNoCbComparator());
                ReportTableView.setColumnComparator(2, ReportComparators.getCustomerNameComparator());
                ReportTableView.setColumnComparator(3, ReportComparators.getNoTelpComparator());
                ReportTableView.setColumnComparator(4, ReportComparators.getPICComparator());
                ReportTableView.setColumnComparator(5, ReportComparators.getTotalMemberComparator());
                ReportTableView.setColumnComparator(6, ReportComparators.getTotalProductComparator());

                ReportTableView.setColumnWeight(1, 2);
                ReportTableView.setColumnWeight(2, 1);
                ReportTableView.setColumnWeight(3, 1);
                ReportTableView.setColumnWeight(4, 1);
                ReportTableView.setColumnWeight(5, 1);
                ReportTableView.setColumnWeight(6, 1);

                ReportTableView.setHeaderAdapter(simpleTableHeaderAdapter);

                List<tCustomerBasedMobileHeaderData> data_cb = new tCustomerBasedMobileHeaderBL().getAllData();

                reportList = new ArrayList<>();

                if (data_cb!=null){

                    for(tCustomerBasedMobileHeaderData datas : data_cb){

                        ReportTable rt = new ReportTable();

                        rt.set_report_type("Customer Base");
                        rt.set_no_cb(datas.get_txtSubmissionId());
                        rt.set_customer_name(datas.get_txtNamaDepan());
                        rt.set_no_tlp(datas.get_txtTelp());
                        if(datas.get_intPIC().equals("1")){
                            rt.set_pic("Yes");
                        } else {
                            rt.set_pic("No");
                        }

                        final List<tCustomerBasedMobileDetailData> dtListDetail = new tCustomerBasedMobileDetailBL().getAllDataByHeaderId(datas.get_intTrCustomerId());
                        rt.set_total_member(String.valueOf(dtListDetail.size()));
                        rt.set_total_product("-");

                        reportList.add(rt);
                    }
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







