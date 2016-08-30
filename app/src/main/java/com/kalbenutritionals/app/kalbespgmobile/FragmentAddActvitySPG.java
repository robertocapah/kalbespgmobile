package com.kalbenutritionals.app.kalbespgmobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bl.tAbsenUserBL;
import bl.tActivityBL;
import library.salesforce.common.tAbsenUserData;
import library.salesforce.common.tActivityData;

/**
 * Created by Admin on 04-06-2015.
 */
public class FragmentAddActvitySPG extends Fragment implements View.OnClickListener {
    View v;
    ImageButton imgActivity1, imgActivity2;
    EditText etDescription;
    RadioGroup rdFlag;

    private static final int CAMERA_CAPTURE_IMAGE1_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_IMAGE2_ACTIVITY_REQUEST_CODE = 130;
    private static final String IMAGE_DIRECTORY_NAME = "Image Activity";

    private Uri uriImage;
    private int countActivity;

    private tActivityData dtActivityData;
    private tActivityBL _tActivityBL;

    private static Bitmap photo1, photo2;
    private static ByteArrayOutputStream output = new ByteArrayOutputStream();
    private static byte[] pht1;
    private static byte[] pht2;

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

        _tActivityBL = new tActivityBL();

        pht1=null;
        pht2=null;


        if(dtActivityData.get_intId() != null){
            byte[] imgFile = dtActivityData.get_txtImg1();
            if(imgFile!=null){
                Bitmap myBitmap = BitmapFactory.decodeByteArray(imgFile, 0 , imgFile.length);
                imgActivity1.setImageBitmap(myBitmap);
            }

            byte[] imgFile2 = dtActivityData.get_txtImg2();
            if(imgFile2!=null){
                Bitmap myBitmap = BitmapFactory.decodeByteArray(imgFile2, 0 , imgFile2.length);;
                imgActivity2.setImageBitmap(myBitmap);
            }
        }

        if(photo1 != null){
            imgActivity1.setImageBitmap(photo1);
            photo1.compress(Bitmap.CompressFormat.PNG, 100, output);
            pht1 = output.toByteArray();
        }

        if(photo2 != null){
            imgActivity2.setImageBitmap(photo2);
            photo2.compress(Bitmap.CompressFormat.PNG, 100, output);
            pht2 = output.toByteArray();
        }

        return v;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.imageButton:
//                Intent intentCamera1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intentCamera1, CAMERA_CAPTURE_IMAGE1_REQUEST_CODE);
//                tActivityData dt = null;
//
//                if(dtActivityData.get_intId() == null){
//
//                    dt = new tActivityData();
//                    dt.set_intId("'" + String.valueOf(new clsMainActivity().GenerateGuid()) + "'");
//                    dt.set_intActive("1");
//                    dt.set_intIdSyn("0");
//                    dt.set_intSubmit("0");
//                    dt.set_txtDesc("");
//
//                    tAbsenUserData dtAbsen = new tAbsenUserBL().getDataCheckInActive();
//                    dt.set_txtOutletCode(dtAbsen.get_txtOutletCode());
//                    dt.set_txtOutletName(dtAbsen.get_txtOutletName());
//                    dt.set_txtUserId(dtAbsen.get_txtUserId());
//                    dt.set_intFlag("0");
//                    dt.set_txtBranch(dtAbsen.get_txtBranchCode());
//                    dt.set_txtDeviceId(dtAbsen.get_txtDeviceId());
//
//                    List<tActivityData> dtListActivityData = new ArrayList<>();
//                    dtListActivityData.add(dt);
//                    new tActivityBL().saveData(dtListActivityData);
//
//                    dtActivityData = dt;
//                }
                captureImage1();

                break;
            case R.id.imageButton2:
//                Intent intentCamera2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intentCamera2, CAMERA_CAPTURE_IMAGE2_REQUEST_CODE);
                captureImage2();

                break;
            case R.id.btnSave:

                if(etDescription.getText().toString().equals("")&&etDescription.getText().toString().length()==0){
                    Toast.makeText(getActivity(), "Please give Description...", Toast.LENGTH_LONG).show();
                } else if(pht1 == null && pht2 == null) {
                    Toast.makeText(getContext(), "Failed to save: Please take at least 1 photo", Toast.LENGTH_LONG).show();
                } else {

                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("Save Activity...?");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            int selectedId = rdFlag.getCheckedRadioButtonId();
                            RadioButton radioFlag = (RadioButton) v.findViewById(selectedId);
                            tAbsenUserData dtAbsen = new tAbsenUserBL().getDataCheckInActive();

                            dtActivityData.set_intActive("0");
                            dtActivityData.set_intIdSyn("0");
                            dtActivityData.set_txtDesc(String.valueOf(etDescription.getText()));
                            dtActivityData.set_intFlag(String.valueOf(radioFlag.getText()));
                            dtActivityData.set_intId(String.valueOf(new clsMainActivity().GenerateGuid()));
                            dtActivityData.set_intSubmit("1");
                            dtActivityData.set_txtOutletCode(dtAbsen.get_txtOutletCode());
                            dtActivityData.set_txtOutletName(dtAbsen.get_txtOutletName());
                            dtActivityData.set_txtDeviceId(dtAbsen.get_txtDeviceId());
                            dtActivityData.set_txtBranch(dtAbsen.get_txtBranchCode());
                            dtActivityData.set_txtUserId(dtAbsen.get_txtUserId());
                            dtActivityData.set_txtImg1(pht1);
                            dtActivityData.set_txtImg2(pht2);

                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            Calendar cal = Calendar.getInstance();

                            dtActivityData.set_dtActivity(dateFormat.format(cal.getTime()));

                            List<tActivityData> dtList = new ArrayList<>();
                            dtList.add(dtActivityData);

                            new tActivityBL().saveData(dtList);

                            Toast.makeText(getActivity().getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                            viewActivityFragment();
                            photo1 = null;
                            photo2 = null;
                        }
                    });
                    alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });

                    alertDialog.show();
                }

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_IMAGE1_ACTIVITY_REQUEST_CODE) {
            if (resultCode == -1 && (data.getExtras().get("data") != null || data.getData() != null))
                if (data.getExtras().get("data") != null) {
                    photo1 = (Bitmap) data.getExtras().get("data");
//                    imgActivity1.setImageBitmap(photo1);

//                    tActivityData dt = null;
//
//                    if(dtActivityData.get_intId() == null){
//
//                        dt = new tActivityData();
//                        dt.set_intId("'" + String.valueOf(new clsMainActivity().GenerateGuid()) + "'");
//                        dt.set_intActive("1");
//                        dt.set_intIdSyn("0");
//                        dt.set_intSubmit("0");
//                        dt.set_txtDesc("");
//
//                        tAbsenUserData dtAbsen = new tAbsenUserBL().getDataCheckInActive();
//                        dt.set_txtOutletCode(dtAbsen.get_txtOutletCode());
//                        dt.set_txtOutletName(dtAbsen.get_txtOutletName());
//                        dt.set_txtUserId(dtAbsen.get_txtUserId());
//                        dt.set_intFlag("0");
//                        dt.set_txtBranch(dtAbsen.get_txtBranchCode());
//                        dt.set_txtDeviceId(dtAbsen.get_txtDeviceId());
//
//                        List<tActivityData> dtListActivityData = new ArrayList<>();
//                        dtListActivityData.add(dt);
//                        new tActivityBL().saveData(dtListActivityData);
//
//                        dtActivityData = dt;
//                    }

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
        } else if (requestCode == CAMERA_CAPTURE_IMAGE2_ACTIVITY_REQUEST_CODE) {
            if (resultCode == -1 && (data.getExtras().get("data") != null || data.getData() != null))
                if (data.getExtras().get("data") != null) {
                    photo2 = (Bitmap) data.getExtras().get("data");
//                    imgActivity2.setImageBitmap(photo2);
//
//                    tActivityData dt = null;
//
//                    if(dtActivityData.get_intId() == null){
//                        dt = new tActivityData();
//                        dt.set_intId("'" + String.valueOf(new clsMainActivity().GenerateGuid()) + "'");
//                        dt.set_intActive("1");
//                        dt.set_intIdSyn("0");
//                        dt.set_intSubmit("0");
//                        dt.set_txtDesc("");
//
//                        tAbsenUserData dtAbsen = new tAbsenUserBL().getDataCheckInActive();
//                        dt.set_txtOutletCode(dtAbsen.get_txtOutletCode());
//                        dt.set_txtOutletName(dtAbsen.get_txtOutletName());
//                        dt.set_txtUserId(dtAbsen.get_txtUserId());
//                        dt.set_intFlag("0");
//                        dt.set_txtBranch(dtAbsen.get_txtBranchCode());
//                        dt.set_txtDeviceId(dtAbsen.get_txtDeviceId());
//
//                        List<tActivityData> dtListActivityData = new ArrayList<>();
//                        dtListActivityData.add(dt);
//                        new tActivityBL().saveData(dtListActivityData);
//
//                        dtActivityData = dt;
//                    }

                    previewCapturedImage2(photo2);
                } else {
                    try {
                        photo2 = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), data.getData());
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
            //Toast.makeText(getActivity().getApplicationContext(), "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
        }
    }

    private void previewCapturedImage1(Bitmap photo) {
        try {
            //uriImage = getOutputMediaFileUri();
            //ByteArrayOutputStream out = null;
            try {
                output = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, output); // bmp is your Bitmap instance
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (output != null) {
                        output.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Bitmap bitmap1 = Bitmap.createScaledBitmap(photo, 150, 150, false);
            pht1 = output.toByteArray();
            imgActivity1.setImageBitmap(bitmap1);
            //String uriForSave = String.valueOf(uriImage.getPath().toString());

            dtActivityData = new tActivityBL().getDataByBitActive();

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();

//            if(dtActivityData != null) {
//                dtActivityData.set_txtImg1(pht);
//                dtActivityData.set_intId("'" + dtActivityData.get_intId() + "'");
//
//                List<tActivityData> dtListActivity = new ArrayList<>();
//                dtListActivity.add(dtActivityData);
//                new tActivityBL().saveData(dtListActivity);
//            }
            if (dtActivityData != null) {
                dtActivityData.set_txtImg1(pht1);
                dtActivityData.set_txtImg2(pht2);
            } else {
                dtActivityData.set_txtImg1(pht1);
                dtActivityData.set_txtImg2(pht2);
            }
//            dtActivityData.set_dtActivity(dateFormat.format(cal.getTime()));
//            dtActivityData.set_intIdSyn("0");
//            dttAbsenUserData.set_txtAbsen("0");//
                List<tActivityData> dtListActivity = new ArrayList<>();
                dtListActivity.add(dtActivityData);
                //new tActivityBL().saveData(dtListActivity);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void previewCapturedImage2(Bitmap photo) {
        try {
            //uriImage = getOutputMediaFileUri();
            //ByteArrayOutputStream out = null;
            try {
                output = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, output); // bmp is your Bitmap instance
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (output != null) {
                        output.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Bitmap bitmap2 = Bitmap.createScaledBitmap(photo, 150, 150, false);
            pht2 = output.toByteArray();
            imgActivity2.setImageBitmap(bitmap2);
            String uriForSave = String.valueOf(uriImage.getPath().toString());

            dtActivityData = new tActivityBL().getDataByBitActive();

//            if(dtActivityData != null) {
//                dtActivityData.set_txtImg2(pht);
//                dtActivityData.set_intId("'" + dtActivityData.get_intId() + "'");
//
//                List<tActivityData> dtListActivity = new ArrayList<>();
//                dtListActivity.add(dtActivityData);
//                new tActivityBL().saveData(dtListActivity);
//            }
            if (dtActivityData != null) {
                dtActivityData.set_txtImg1(pht1);
                dtActivityData.set_txtImg2(pht2);
            } else {
                dtActivityData.set_txtImg1(pht1);
                dtActivityData.set_txtImg2(pht2);
            }
            List<tActivityData> dtListActivity = new ArrayList<>();
            dtListActivity.add(dtActivityData);
            //new tActivityBL().saveData(dtListActivity);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

//    private Uri getOutputMediaFileUri() {
//        return Uri.fromFile(getOutputMediaFile());
//    }

//    private File getOutputMediaFile() {
//        // External sdcard location
//
//        File mediaStorageDir = new File(new clsHardCode().txtFolderActivity + String.valueOf(countActivity) + File.separator);
//        // Create the storage directory if it does not exist
//        if (!mediaStorageDir.exists()) {
//            if (!mediaStorageDir.mkdirs()) {
//                Log.d(IMAGE_DIRECTORY_NAME, "Failed create " + IMAGE_DIRECTORY_NAME + " directory");
//                return null;
//            }
//        }
//        // Create a media file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
//        File mediaFile;
//        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG" + "_" + timeStamp + ".jpg");
//        return mediaFile;
//    }

    public void viewActivityFragment(){
        Intent intent = new Intent(getContext(),MainMenu.class);
        intent.putExtra("key_view", "View Actvity");
        getActivity().finish();
        startActivity(intent);
        return;
    }
    protected void captureImage1() {
        Intent intentCamera1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentCamera1, CAMERA_CAPTURE_IMAGE1_ACTIVITY_REQUEST_CODE);
    }

    protected void captureImage2() {
        Intent intentCamera2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentCamera2, CAMERA_CAPTURE_IMAGE2_ACTIVITY_REQUEST_CODE);
    }
}
