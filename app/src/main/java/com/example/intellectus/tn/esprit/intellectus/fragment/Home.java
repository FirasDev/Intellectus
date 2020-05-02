package com.example.intellectus.tn.esprit.intellectus.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.intellectus.LoginActivity;
import com.example.intellectus.MainActivity;
import com.example.intellectus.R;
import com.example.intellectus.tn.esprit.intellectus.MyAdmin;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    Button btnlogout;
    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        btnlogout = rootView.findViewById(R.id.button7);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MainActivity.mainUser = null;
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);



            }
        });

        return rootView;
    }

}
