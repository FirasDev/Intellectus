package com.example.intellectus.tn.esprit.intellectus.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.intellectus.ChatBoxActivity;
import com.example.intellectus.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentButtons extends Fragment {

    Button chat,Devoir,map,logout,emploi;
    public StudentButtons() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_student_buttons, container, false);
        Devoir = rootView.findViewById(R.id.btn1);
        emploi = rootView.findViewById(R.id.btn2);
        map = rootView.findViewById(R.id.btn3);
        logout = rootView.findViewById(R.id.btn4);
        chat = rootView.findViewById(R.id.button6);

        Devoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new Devoir());

            }
        });

        emploi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new Emploi());

            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new Map());

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new Home());

            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChatBoxActivity.class);
                startActivity(intent);
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
