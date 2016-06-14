package library.salesforce.common;

import java.util.List;

import edu.swu.pulltorefreshswipemenulistview.library.R;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppAdapter extends BaseAdapter {

    private Context context;
    //private PackageManager packageManager;
    private List<String> mAppList;

    /*
    public AppAdapter(Context context, PackageManager packageManager, List<String> mAppList) {
        this.context = context;
        this.packageManager = packageManager;
        this.mAppList = mAppList;
    }
    */
    
    public AppAdapter(Context context, List<String> mAppList) {
        this.context = context;
        //this.packageManager = packageManager;
        this.mAppList = mAppList;
    }

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public String getItem(int position) {
        return mAppList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_list_app, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        String item = getItem(position);
        //holder.iv_icon.setImageDrawable(item.loadIcon(packageManager));
        holder.tv_name.setText(item);
        return convertView;
    }

    class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;

        public ViewHolder(View view) {
            iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            view.setTag(this);
        }
    }
}
