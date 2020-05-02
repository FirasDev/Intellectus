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
import com.example.intellectus.tn.esprit.intellectus.MyClasse;


/**
 * A simple {@link Fragment} subclass.
 */
public class GestionClass extends Fragment {

    Button btnAjout;

    public GestionClass() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gestion_class, container, false);
        showFragment2(new MyClasse());
        btnAjout = rootView.findViewById(R.id.ajout);
        btnAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new AjouterClass());

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

    public void showFragment2(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_class_container, fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }





}

