package com.example.intellectus.tn.esprit.intellectus.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.intellectus.R;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.INodeJS;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class AffectationEmploiClasse extends Fragment {

    Button btnAjouter;
    EditText nom,lien;
    INodeJS myApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public AffectationEmploiClasse() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_affectation_emploi_classe, container, false);
        btnAjouter = rootView.findViewById(R.id.ajouter);
        Retrofit retrofit = RetrofitClient.getInstance();
        myApi = retrofit.create(INodeJS.class);
        nom = rootView.findViewById(R.id.nomclasse);
        lien = rootView.findViewById(R.id.lien);
        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nom.getText().toString().equals("") || lien.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"Veuillez remplir le(s) champ(s) vide(s)",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Emploi ajoutée avec success",Toast.LENGTH_SHORT).show();
                    InsertClass(nom.getText().toString(),lien.getText().toString());
                    showFragment(new GestionAffectation());
                }



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

    private void InsertClass(String name,String capacite) {
        compositeDisposable.add(myApi.addEmploi(name,capacite)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {


                    }



                }));
    }

}
