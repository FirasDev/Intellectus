package com.example.intellectus.tn.esprit.intellectus.fragment;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.intellectus.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddSchool extends Fragment {

    EditText name,email,address,state,city,postal_code;
    Button sendbtn;

    public static String schoolName = "";
    public static String schoolEmail = "";
    public static String schoolAddress = "x";
    public static String schoolState = "";
    public static String schoolCity = "";
    public static String schoolPostal_code = "";

    OnCallbackReceived mCallback;

    public interface OnCallbackReceived {
        public void passDataStepTwo(String name,String email,String address,String gov,String city,String codePostal);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnCallbackReceived) activity;
        } catch (ClassCastException e) {

        }
    }

    public AddSchool() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_school, container, false);

        name = rootView.findViewById(R.id.txtName);
        email = rootView.findViewById(R.id.txtEmail);
        address = rootView.findViewById(R.id.txtaddress);
        state = rootView.findViewById(R.id.txtGov);
        city = rootView.findViewById(R.id.txtCity);
        postal_code = rootView.findViewById(R.id.txtpostal_code);
        sendbtn  = rootView.findViewById(R.id.suiv);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String regexStr = "^[0-9]*$";

                if(name.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Nom de l'école erroné", Toast.LENGTH_LONG).show();
                }else if(email.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Email de l'école erroné", Toast.LENGTH_LONG).show();
                }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                    Toast.makeText(getContext(), "Erreur de format de email", Toast.LENGTH_LONG).show();
                }else if(address.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Adresse de l'école erroné", Toast.LENGTH_LONG).show();
                }else if(state.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Ville de l'école erroné", Toast.LENGTH_LONG).show();
                }else if(city.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Cité de l'école erroné", Toast.LENGTH_LONG).show();
                }else if(postal_code.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Code postal erroné", Toast.LENGTH_LONG).show();
                }else if(!postal_code.getText().toString().trim().matches(regexStr)){
                    Toast.makeText(getContext(), "Code postal doit être un numero", Toast.LENGTH_LONG).show();
                }else{
                    add_school2 addSchool2 = new add_school2();
                    schoolName = name.getText().toString();
                    schoolEmail = email.getText().toString();
                    schoolAddress = address.getText().toString();
                    schoolState = state.getText().toString();
                    schoolCity = city.getText().toString();
                    schoolPostal_code = postal_code.getText().toString();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame, addSchool2);
                    fragmentTransaction.addToBackStack(null).commit();
                }
            }
        });

        return rootView;
    }

}
