
package com.example.intellectus.tn.esprit.intellectus.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.intellectus.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerButtons extends Fragment {

    Button GestionClass,GestionAffectation,Eleves,Ens,Admins,logout;

    public ManagerButtons() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manager_buttons, container, false);
        GestionClass = rootView.findViewById(R.id.button);
        GestionAffectation = rootView.findViewById(R.id.button2);
        Eleves = rootView.findViewById(R.id.button3);
        Ens = rootView.findViewById(R.id.button4);
        Admins = rootView.findViewById(R.id.button5);
        logout = rootView.findViewById(R.id.button6);
        GestionClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new GestionClass());

            }
        });
        GestionAffectation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new GestionAffectation());

            }
        });
        Eleves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new GestionEleve());

            }
        });
        Ens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new GestionTeacher());

            }
        });
        Admins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new GestionAdmin());

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new Home());

            }
        });
        return rootView;
    }

    public void showFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }


}
