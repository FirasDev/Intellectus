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
import com.example.intellectus.tn.esprit.intellectus.fragment.AffectationEmploiClasse;


/**
 * A simple {@link Fragment} subclass.
 */
public class GestionAffectation extends Fragment {

    Button btnemploiclass,btneleveclass,btnensclass;

    public GestionAffectation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gestion_affectation, container, false);
        btnemploiclass = rootView.findViewById(R.id.emploiclass);
        btneleveclass = rootView.findViewById(R.id.eleveclass);
        btnensclass = rootView.findViewById(R.id.enseignantclass);
        btnemploiclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new AffectationEmploiClasse());

            }
        });
        btneleveclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new AffectationEleveClasse());

            }
        });
        btnensclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new AffectationEnsClasse());

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
