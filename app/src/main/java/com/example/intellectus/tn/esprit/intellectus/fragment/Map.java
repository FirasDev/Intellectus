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
public class Map extends Fragment {

    Button btnAjouter;
    public Map() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        btnAjouter = rootView.findViewById(R.id.map);


        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Search for restaurants nearby
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=Lycee "+MainActivity.mainUser.getEmail().substring(MainActivity.mainUser.getEmail().indexOf("@") + 1 ,MainActivity.mainUser.getEmail().indexOf(".")));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);





            }
        });
        return rootView;
    }

}
