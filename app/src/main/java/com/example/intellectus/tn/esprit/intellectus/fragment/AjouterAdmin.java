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

import com.example.intellectus.MainActivity;
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
public class AjouterAdmin extends Fragment {

    Button btnAjouter;
    EditText prenom,nom,email,date,pass;
    INodeJS myApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public AjouterAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ajouter_admin, container, false);
        btnAjouter = rootView.findViewById(R.id.ajouterA);
        prenom = rootView.findViewById(R.id.prenomA);
        nom = rootView.findViewById(R.id.nomA);
        email = rootView.findViewById(R.id.mailA);
        pass = rootView.findViewById(R.id.passwordA);
        Retrofit retrofit = RetrofitClient.getInstance();
        myApi = retrofit.create(INodeJS.class);
        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(prenom.getText().toString().equals("") || nom.getText().toString().equals("") || email.getText().toString().equals("") || date.getText().toString().equals("") || pass.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"Veuillez remplir le(s) champ(s) vide(s)",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Devoir déposé avec success",Toast.LENGTH_SHORT).show();
                    showFragment(new GestionAdmin());
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

    private void InsertClass(String prenom,String nom,String email,String role,String isParent, String image, String password ) {
        compositeDisposable.add(myApi.addAdmin(prenom,nom,email,role,isParent,image,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {


                    }



                }));
    }
}
