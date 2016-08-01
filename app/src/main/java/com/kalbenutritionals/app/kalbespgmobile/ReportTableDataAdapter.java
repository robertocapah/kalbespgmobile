package com.kalbenutritionals.app.kalbespgmobile;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings.RenderPriority;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import library.salesforce.common.ReportTable;

import de.codecrafters.tableview.TableColumnModel;
import de.codecrafters.tableview.TableDataAdapter;


public class ReportTableDataAdapter extends TableDataAdapter<ReportTable> {

    private static final int TEXT_SIZE = 14;
    private static final NumberFormat PRICE_FORMATTER = NumberFormat.getNumberInstance();


    public ReportTableDataAdapter(final Context context, final List<ReportTable> data) {
        super(context, data);
    }

    @Override
    public View getCellView(final int rowIndex, final int columnIndex, final ViewGroup parentView) {
        final ReportTable data = getRowData(rowIndex);
        View renderedView = null;

        if(data.get_report_type() == "Reso"){
            switch (columnIndex) {
                case 1:
                    renderedView = renderString(data.get_no_so(), "left");
                    break;
                case 2:
                    renderedView = renderString(data.get_total_product(), "left");
                    break;
                case 3:
                    renderedView = renderString(data.get_total_item(), "left");
                    break;
                case 4:
                    renderedView = renderString(data.get_total_price(), "right");
                    break;
                case 5:
                    renderedView = renderString(data.get_status(), "left");
                    break;
                default:
                    break;
            }
        }

        if(data.get_report_type() == "Customer Base"){
	        switch (columnIndex) {
	            case 1:
	                renderedView = renderString(data.get_customer_name(), "left");
	                break;
	            case 2:
	                renderedView = renderString(data.get_customer_sex(), "left");
	                break;
	            case 3:
	            	renderedView = renderString(data.get_customer_number(), "left");
	            	break;
                case 4:
                    renderedView = renderString(data.get_total_product(), "left");
                    break;
                default:
                    break;
	        }
        }

        return renderedView;
    }

    private View renderString(final String value, final String align) {
        final TextView textView = new TextView(getContext());
        textView.setText(value);
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(TEXT_SIZE);
        
        if(align.equals("right")){
        	textView.setGravity(Gravity.RIGHT);
        }
        
        return textView;
    }

}
