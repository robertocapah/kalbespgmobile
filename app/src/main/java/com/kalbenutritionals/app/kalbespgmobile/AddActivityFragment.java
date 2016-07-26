package com.kalbenutritionals.app.kalbespgmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bl.clsMainBL;
import bl.mEmployeeSalesProductBL;
import bl.tAbsenUserBL;
import bl.tActivityBL;
import bl.tCustomerBaseBL;
import library.salesforce.common.ModelListview;
import library.salesforce.common.mEmployeeSalesProductData;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tActivityData;
import library.salesforce.common.tCustomerBaseData;
import library.salesforce.common.tCustomerBaseDetailData;
import library.salesforce.dal.clsHardCode;
import library.salesforce.dal.tCustomerBaseDetailDA;

/**
 * Created by Admin on 04-06-2015.
 */
public class AddActivityFragment extends Fragment implements View.OnClickListener {
    View v;
    ImageButton imgActivity1, imgActivity2;
    EditText etDescription;
    RadioGroup rdFlag;

    private static final int CAMERA_CAPTURE_IMAGE1_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_IMAGE2_REQUEST_CODE = 130;
    private static final String IMAGE_DIRECTORY_NAME = "Image Activity";

    private Uri uriImage;
    private int countActivity;

    private tActivityData dtActivityData;

    private static Bitmap photo1, photo2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_activity_add,container,false);
        imgActivity1 = (ImageButton) v.findViewById(R.id.imageButton);
        imgActivity1.setOnClickListener(this);

        imgActivity2 = (ImageButton) v.findViewById(R.id.imageButton2);
        imgActivity2.setOnClickListener(this);

        Button btnSave = (Button) v.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        etDescription = (EditText) v.findViewById(R.id.etNama);
        rdFlag = (RadioGroup) v.findViewById(R.id.radioFlag);

        countActivity = new tActivityBL().getCountActivity();
        dtActivityData = new tActivityBL().getDataByBitActive();


        if(dtActivityData.get_intId() != null){
            File imgFile = new  File(dtActivityData.get_txtImg1());
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgActivity1.setImageBitmap(myBitmap);
            }

            File imgFile2 = new  File(dtActivityData.get_txtImg2());
            if(imgFile2.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile2.getAbsolutePath());
                imgActivity2.setImageBitmap(myBitmap);
            }
        }

        if(photo1 != null){
            imgActivity1.setImageBitmap(photo1);
        }

        if(photo2 != null){
            imgActivity2.setImageBitmap(photo2);
        }

        return v;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.imageButton:
                Intent intentCamera1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCamera1, CAMERA_CAPTURE_IMAGE1_REQUEST_CODE);

                break;
            case R.id.imageButton2:
                Intent intentCamera2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCamera2, CAMERA_CAPTURE_IMAGE2_REQUEST_CODE);

                break;
            case R.id.btnSave:
                int selectedId = rdFlag.getCheckedRadioButtonId();
                RadioButton radioFlag = (RadioButton) v.findViewById(selectedId);

                dtActivityData = new tActivityBL().getDataByBitActive();

                if(dtActivityData.get_txtImg1() == null && dtActivityData.get_txtImg2() == null){
                    Toast.makeText(this.getContext(), "Failed to save: Please take at least 1 photo", Toast.LENGTH_LONG).show();
                }
                else{
                    dtActivityData.set_intActive("0");
                    dtActivityData.set_txtDesc(String.valueOf(etDescription.getText()));
                    dtActivityData.set_intFlag(String.valueOf(radioFlag.getText()));
                    dtActivityData.set_intId("'" + dtActivityData.get_intId() + "'");
                    dtActivityData.set_intSubmit("1");

                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Calendar cal = Calendar.getInstance();

                    dtActivityData.set_dtActivity(dateFormat.format(cal.getTime()));

                    List<tActivityData> dtList = new ArrayList<>();
                    dtList.add(dtActivityData);

                    new tActivityBL().saveData(dtList);

                    Toast.makeText(getActivity().getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_IMAGE1_REQUEST_CODE) {
            if (resultCode == -1 && (data.getExtras().get("data") != null || data.getData() != null))
                if (data.getExtras().get("data") != null) {
                    photo1 = (Bitmap) data.getExtras().get("data");
                    imgActivity1.setImageBitmap(photo1);

                    tActivityData dt = null;

                    if(dtActivityData.get_intId() == null){

                        dt = new tActivityData();
                        dt.set_intId("'" + String.valueOf(new clsMainActivity().GenerateGuid()) + "'");
                        dt.set_intActive("1");
                        dt.set_intIdSyn("0");
                        dt.set_intSubmit("0");
                        dt.set_txtDesc("");

                        tAbsenUserData dtAbsen = new tAbsenUserBL().getDataCheckInActive();
                        dt.set_txtOutletCode(dtAbsen.get_txtOutletCode());
                        dt.set_txtOutletName(dtAbsen.get_txtOutletName());
                        dt.set_txtUserId(dtAbsen.get_txtUserId());
                        dt.set_intFlag("0");
                        dt.set_txtBranch(dtAbsen.get_txtBranchCode());
                        dt.set_txtDeviceId(dtAbsen.get_txtDeviceId());

                        List<tActivityData> dtListActivityData = new ArrayList<>();
                        dtListActivityData.add(dt);
                        new tActivityBL().saveData(dtListActivityData);

                        dtActivityData = dt;
                    }

                    previewCapturedImage1(photo1);
                } else {
                    try {
                        photo1 = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), data.getData());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            else if (resultCode == 0) {
                Toast.makeText(getActivity().getApplicationContext(), "User canceled to capture image", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == CAMERA_CAPTURE_IMAGE2_REQUEST_CODE) {
            if (resultCode == -1 && (data.getExtras().get("data") != null || data.getData() != null))
                if (data.getExtras().get("data") != null) {
                    photo2 = (Bitmap) data.getExtras().get("data");
                    imgActivity2.setImageBitmap(photo2);

                    tActivityData dt = null;

                    if(dtActivityData.get_intId() == null){
                        dt = new tActivityData();
                        dt.set_intId("'" + String.valueOf(new clsMainActivity().GenerateGuid()) + "'");
                        dt.set_intActive("1");
                        dt.set_intIdSyn("0");
                        dt.set_intSubmit("0");
                        dt.set_txtDesc("");

                        tAbsenUserData dtAbsen = new tAbsenUserBL().getDataCheckInActive();
                        dt.set_txtOutletCode(dtAbsen.get_txtOutletCode());
                        dt.set_txtOutletName(dtAbsen.get_txtOutletName());
                        dt.set_txtUserId(dtAbsen.get_txtUserId());
                        dt.set_intFlag("0");
                        dt.set_txtBranch(dtAbsen.get_txtBranchCode());
                        dt.set_txtDeviceId(dtAbsen.get_txtDeviceId());

                        List<tActivityData> dtListActivityData = new ArrayList<>();
                        dtListActivityData.add(dt);
                        new tActivityBL().saveData(dtListActivityData);

                        dtActivityData = dt;
                    }

                    previewCapturedImage2(photo2);
                } else {
                    try {
                        photo1 = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), data.getData());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            else if (resultCode == 0) {
                Toast.makeText(getActivity().getApplicationContext(), "User canceled to capture image", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getActivity().getApplicationContext(), "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
        }
    }

    private void previewCapturedImage1(Bitmap photo) {
        try {
            uriImage = getOutputMediaFileUri();
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(uriImage.getPath().toString());
                photo.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Bitmap bitmap1 = Bitmap.createScaledBitmap(photo, 150, 150, false);
            imgActivity1.setImageBitmap(bitmap1);
            String uriForSave = String.valueOf(uriImage.getPath().toString());

            dtActivityData = new tActivityBL().getDataByBitActive();

            if(dtActivityData != null) {
                dtActivityData.set_txtImg1(uriForSave);
                dtActivityData.set_intId("'" + dtActivityData.get_intId() + "'");

                List<tActivityData> dtListActivity = new ArrayList<>();
                dtListActivity.add(dtActivityData);
                new tActivityBL().saveData(dtListActivity);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void previewCapturedImage2(Bitmap photo) {
        try {
            uriImage = getOutputMediaFileUri();
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(uriImage.getPath().toString());
                photo.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Bitmap bitmap2 = Bitmap.createScaledBitmap(photo, 150, 150, false);
            imgActivity2.setImageBitmap(bitmap2);
            String uriForSave = String.valueOf(uriImage.getPath().toString());

            dtActivityData = new tActivityBL().getDataByBitActive();

            if(dtActivityData != null) {
                dtActivityData.set_txtImg2(uriForSave);
                dtActivityData.set_intId("'" + dtActivityData.get_intId() + "'");

                List<tActivityData> dtListActivity = new ArrayList<>();
                dtListActivity.add(dtActivityData);
                new tActivityBL().saveData(dtListActivity);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    private File getOutputMediaFile() {
        // External sdcard location

        File mediaStorageDir = new File(new clsHardCode().txtFolderActivity + String.valueOf(countActivity) + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG" + "_" + timeStamp + ".jpg");
        return mediaFile;
    }
}
