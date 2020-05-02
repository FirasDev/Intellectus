package com.example.intellectus.tn.esprit.intellectus.fragment;


import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.intellectus.R;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.INodeJS;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.RetrofitClient;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddManager extends Fragment {

    EditText name,lastname;
    TextView school;
    Button finishbtn;
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public AddManager() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_manager, container, false);

        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        school = rootView.findViewById(R.id.textView);
        school.setText(AddSchool.schoolName);
        name = rootView.findViewById(R.id.txtName);
        lastname = rootView.findViewById(R.id.txtLastname);
        finishbtn = rootView.findViewById(R.id.btnfinish);

        finishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Nom erroné", Toast.LENGTH_LONG).show();
                }else if(lastname.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Prénom erroné", Toast.LENGTH_LONG).show();
                }else{
                    Call<Void> call = myAPI.registerManager(name.getText().toString()+"."+lastname.getText().toString(),name.getText().toString()+"."+lastname.getText().toString(),name.getText().toString(),lastname.getText().toString(),"Manager","No","No");
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.i("Register DONE","DONE");
                            Toast.makeText(getContext(), "Admin enregistré avec succés !", Toast.LENGTH_LONG).show();
                            SchoolList sl = new SchoolList();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame, sl);
                            fragmentTransaction.addToBackStack(null).commit();
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.i("Register DONE","FAIL");
                        }
                    });
                }
            }
        });
        return rootView;
    }

}
