package com.example.intellectus.tn.esprit.intellectus.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.intellectus.ChatBoxActivity;
import com.example.intellectus.MainActivity;
import com.example.intellectus.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OwnerButtons extends Fragment {


    ImageButton chat,schoolsList,managersList,profile;

    public OwnerButtons() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_owner_buttons, container, false);

        managersList = rootView.findViewById(R.id.button5);
        schoolsList = rootView.findViewById(R.id.button4);
        chat = rootView.findViewById(R.id.button6);
        profile  = rootView.findViewById(R.id.button7);

        managersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.logo.setVisibility(View.GONE);
                showFragment(new ManagerList());

            }
        });

        schoolsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.logo.setVisibility(View.GONE);
                showFragment(new SchoolList());

            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChatBoxActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.logo.setVisibility(View.GONE);
                showFragment(new UserProfile());

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
