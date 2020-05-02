package com.example.intellectus.tn.esprit.intellectus.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intellectus.MainActivity;
import com.example.intellectus.R;
import com.example.intellectus.tn.esprit.intellectus.Utils.AppUtils;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class SchoolDetails extends Fragment {

    //User
    public static String DATA_NAME = "undefined";
    public static int DATA_UID = 0;
    public static String DATA_ADR = "undefined";
    public static String DATA_VILLE = "undefined";
    public static String DATA_CITE = "undefined";
    public static String DATA_CODE = "undefined";
    public static String DATA_PHONE1 = "undefined";
    public static String DATA_PHONE2 = "undefined";
    public static String DATA_EMAIL = "undefined";
    public static String DATA_FAX = "undefined";
    public static String DATA_FB = "undefined";
    public static String DATA_DIRECTEUR = "undefined";
    public static String DATA_UIMG = "undefined";

    ImageView imgUser;

    Button btnComment,btnReponse;
    public SchoolDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_school_details, container, false);


        imgUser = rootView.findViewById(R.id.imgUser);

        if (DATA_UIMG.length() > 3){
            Picasso.with(getContext()).load(AppUtils.SERVER_URL+"uploads/"+DATA_UIMG).into(imgUser);
        }else{
            Picasso.with(getContext()).load(AppUtils.SERVER_URL+"uploads/default.jpg").into(imgUser);
        }

        String fullName = DATA_NAME;
        TextView title = rootView.findViewById(R.id.formtitle);
        title.setText(fullName);

        TextView txtaddress = (TextView) rootView.findViewById(R.id.address);
        txtaddress.setText(DATA_ADR);

        TextView txtstate = (TextView) rootView.findViewById(R.id.state);
        txtstate.setText(DATA_VILLE);

        TextView txtcity = (TextView) rootView.findViewById(R.id.city);
        txtcity.setText(DATA_CITE);

        TextView txtpostal = (TextView) rootView.findViewById(R.id.postal_code);
        txtpostal.setText(DATA_CODE);

        TextView txtphone1 = (TextView) rootView.findViewById(R.id.phone_1);
        txtphone1.setText(DATA_PHONE1);

        TextView txtphone2 = (TextView) rootView.findViewById(R.id.phone_2);
        txtphone2.setText(DATA_PHONE2);


        TextView txtemail = (TextView) rootView.findViewById(R.id.email);
        txtemail.setText(DATA_EMAIL);

        TextView txtfax = (TextView) rootView.findViewById(R.id.fax);
        txtfax.setText(DATA_FAX);

        ImageView txtfacebook = rootView.findViewById(R.id.facebook);
        ImageView gps = rootView.findViewById(R.id.gps);

        txtfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(DATA_FB));
                startActivity(i);
            }
        });

        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gps = Uri.parse("geo:0,0?q= "+ DATA_ADR);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gps);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        TextView txtowner = (TextView) rootView.findViewById(R.id.owner);
        txtowner.setText(DATA_DIRECTEUR);

        return rootView;
    }

}
