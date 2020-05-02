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
public class Devoir extends Fragment {

    Button btnAjouter;
    EditText title,content,lien;
    INodeJS myApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public Devoir() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_devoir, container, false);
        btnAjouter = rootView.findViewById(R.id.ajouter);
        title = rootView.findViewById(R.id.titre);
        content = rootView.findViewById(R.id.contenu);
        lien = rootView.findViewById(R.id.link);
        Retrofit retrofit = RetrofitClient.getInstance();
        myApi = retrofit.create(INodeJS.class);
        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(title.getText().toString().equals("") || content.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"Veuillez remplir le(s) champ(s) vide(s)",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Devoir déposé avec success",Toast.LENGTH_SHORT).show();
                    InsertClass(MainActivity.mainUser.getEmail(),title.getText().toString(),content.getText().toString(),lien.getText().toString());
                    showFragment(new Devoir());
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

    private void InsertClass(String user,String title,String content,String link) {
        compositeDisposable.add(myApi.addHomework(user,title,content,link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {


                    }



                }));
    }

}
