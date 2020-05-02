package com.example.intellectus.tn.esprit.intellectus.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.intellectus.MainActivity;
import com.example.intellectus.R;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.INodeJS;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.RetrofitClient;

import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class Emploi extends Fragment {

    Button btnAjouter;
    public Emploi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_emploi, container, false);
        btnAjouter = rootView.findViewById(R.id.emploi);


        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://i.pinimg.com/originals/3c/52/3a/3c523a141ad4e1f4c5220847801d46bf.jpg"));
                getActivity().startActivity(i);


            }
        });
        return rootView;
    }

}
