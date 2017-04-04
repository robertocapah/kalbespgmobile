package com.kalbenutritionals.app.kalbespgmobile;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by Robert on 03/04/2017.
 */

public class FragmentSettings extends Fragment {

    View v;

    String[] menuList = {"Ubah Password"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_settings, null);
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.activity_listview_settings, menuList);

        ListView listView = (ListView) v.findViewById(R.id.menu_list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    ubahPassword();
                }
            }
        });
        /*listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    ubahPassword();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


        return v;
    }
    int intSet1 = 1;
    int intSet2 = 1;
    int intSet3 = 1;
    private void ubahPassword(){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View promptView = layoutInflater.inflate(R.layout.popup_setting_ubah_password, null);

        final EditText passLama = (EditText) promptView.findViewById(R.id.etPassLama);
        final EditText passBaru1 = (EditText) promptView.findViewById(R.id.etPassbaru1);
        final EditText passBaru2 = (EditText) promptView.findViewById(R.id.etPassbaru2);

        final TextInputLayout tiPassLama = (TextInputLayout) promptView.findViewById(R.id.input_pass_lama);
        final  TextInputLayout tiPassBaru1 = (TextInputLayout) promptView.findViewById(R.id.input_pass_baru1);
        final TextInputLayout tiPassBaru2 = (TextInputLayout) promptView.findViewById(R.id.input_pass_baru2);

        passLama.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(passLama) {
            public boolean onDrawableClick() {
                if (intSet1 == 1) {
                    passLama.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    intSet1 = 0;
                } else {
                    passLama.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    intSet1 = 1;
                }

                return true;
            }
        });
        passBaru1.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(passBaru1) {
            public boolean onDrawableClick() {
                if (intSet2 == 1) {
                    passBaru1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    intSet2 = 0;
                } else {
                    passBaru1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    intSet2 = 1;
                }

                return true;
            }
        });
        passBaru2.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(passBaru2) {
            public boolean onDrawableClick() {
                if (intSet3 == 1) {
                    passBaru2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    intSet3 = 0;
                } else {
                    passBaru1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    intSet3 = 1;
                }

                return true;
            }
        });



        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder.setView(promptView);
        alertBuilder.setCancelable(false)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
    final AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new clsMainActivity().removeErrorMessage(tiPassLama);
                new clsMainActivity().removeErrorMessage(tiPassBaru1);
                new clsMainActivity().removeErrorMessage(tiPassBaru2);

                boolean validate = true ;
                if (passLama.getText().toString().equals("")){
                    validate = false;
                    new clsMainActivity().setErrorMessage(getContext(), tiPassLama, passLama, "Password lama wajib di isi");
                }
                if (passBaru1.getText().toString().equals("")){
                    validate = false;
                    new clsMainActivity().setErrorMessage(getContext(), tiPassBaru1, passBaru1, "Password baru wajib di isi");
                }
                if (passBaru2.getText().toString().equals("")){
                    new clsMainActivity().setErrorMessage(getContext(), tiPassBaru2, passBaru2, "Ulangi password baru wajib di isi");
                    validate = false;
                }else if (!passBaru1.getText().toString().equals(passBaru2.getText().toString()) && !passBaru2.getText().toString().equals("")){
                    new clsMainActivity().setErrorMessage(getContext(), tiPassBaru2, passBaru2, "Password baru tidak sama");
                    validate = false;
                }

                if(validate){
                    alertDialog.dismiss();
                    new clsMainActivity().showCustomToast(getContext(), "Password telah di ubah", true);
                }
            }
        });
    }

}
