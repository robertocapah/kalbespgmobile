package com.kalbenutritionals.app.kalbespgmobile;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;

/**
 * Created by ASUS ZE on 01/09/2016.
 */
public class FragmentPushData extends Fragment{

    View v;

    private TableLayout tlSO;
    private TableLayout tlActivity;
    private TableLayout tlCustomerBase;
    private TableLayout tlAbsen;
    private TableLayout tlLeave;
    private Button btnPush;

    private PackageInfo pInfo = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_push_data, container, false);

        tlSO = (TableLayout) v.findViewById(R.id.tlSO);
        tlActivity = (TableLayout) v.findViewById(R.id.tlActivity);
        tlCustomerBase = (TableLayout) v.findViewById(R.id.tl_cb);
        tlAbsen = (TableLayout) v.findViewById(R.id.tl_absen);
        tlLeave = (TableLayout) v.findViewById(R.id.tl_leave);
        btnPush = (Button) v.findViewById(R.id.btnPush);

        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AsyncCallRole task=new AsyncCallRole();
//                task.execute();
            }
        });

        clsMainActivity clsMainActivity = new clsMainActivity();

        return v;
    }

//    private class AsyncCallRole extends AsyncTask<List<dataJson>, Void, List<dataJson>> {
//        @Override
//        protected List<dataJson> doInBackground(List<dataJson>... params) {
//            List<dataJson> roledata=new ArrayList<dataJson>();
//            clsPushData dtJson= new clsHelperBL().pushData();
//            dataJson dtdataJson=new dataJson();
//            if(dtJson!=null){
//                String versionName="";
//                try {
//                    versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
//                } catch (PackageManager.NameNotFoundException e2) {
//                    // TODO Auto-generated catch block
//                    e2.printStackTrace();
//                }
//                try {
//                    JSONArray Jresult= new clsHelperBL().callPushDataReturnJson(versionName,dtJson.getDtdataJson().txtJSON().toString(),dtJson.getFileUpload());
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                dtdataJson.setIntResult("1");
//
//            }
//            else
//            {
//                dtdataJson.setIntResult("0");
//                dtdataJson.setTxtMessage("No Data");
//            }
//            roledata.add(dtdataJson);
//            return roledata ;
//        }
//
//        private ProgressDialog Dialog = new ProgressDialog(getContext());
//        @Override
//        protected void onCancelled() {
//            Dialog.dismiss();
//            Toast toast = Toast.makeText(getContext(),
//                    new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
//            toast.setGravity(Gravity.TOP, 25, 400);
//            toast.show();
//        }
//        @Override
//        protected void onPostExecute(List<dataJson> roledata) {
//            if(roledata.get(0).getIntResult().equals("1")){
//                clsMainActivity.showToastStatic(getContext(), "Success Push Data");
//            }else{
//                clsMainActivity.showToastStatic(getContext(), roledata.get(0).getTxtMessage());
//            }
//            Dialog.dismiss();
//        }
//        int intProcesscancel=0;
//        @Override
//        protected void onPreExecute() {
//            //Make ProgressBar invisible
//            //pg.setVisibility(View.VISIBLE);
//            Dialog.setMessage("Syncronize Data!!");
//            Dialog.setCancelable(false);
//            Dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    intProcesscancel=1;
//                }
//            });
//            Dialog.show();
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            Dialog.dismiss();
//        }
//
//    }
}
