package com.example.intellectus.tn.esprit.intellectus.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.intellectus.R;
import com.example.intellectus.tn.esprit.intellectus.Utils.AppUtils;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetails extends Fragment {

    //User
    public static String DATA_Name = "undefined";
    public static int DATA_UID = 0;
    public static String DATA_LASTNAME = "undefined";
    public static String DATA_ROLE = "undefined";
    public static String DATA_EMAIL = "undefined";
    public static String DATA_DATE = "undefined";
    public static String DATA_PARENT = "undefined";
    public static String DATA_UIMG = "undefined";

    ImageView imgUser;

    String role;

    Button btnComment,btnReponse;


    public UserDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_user_details, container, false);

        imgUser = rootView.findViewById(R.id.imgUser);

        if (DATA_UIMG.length() > 3){
            Picasso.with(getContext()).load(AppUtils.SERVER_URL+"uploads/"+DATA_UIMG).into(imgUser);
        }else{
            Picasso.with(getContext()).load(AppUtils.SERVER_URL+"uploads/default.jpg").into(imgUser);
        }

        String fullName = DATA_Name+ " "+ DATA_LASTNAME;
        TextView title = rootView.findViewById(R.id.formtitle);
        title.setText(fullName);

        TextView txtaddress = (TextView) rootView.findViewById(R.id.address);
        txtaddress.setText(DATA_Name);

        TextView txtstate = (TextView) rootView.findViewById(R.id.state);
        txtstate.setText(DATA_LASTNAME);

        TextView txtcity = (TextView) rootView.findViewById(R.id.city);
        txtcity.setText(DATA_EMAIL);

        TextView txtpostal = (TextView) rootView.findViewById(R.id.postal_code);
        txtpostal.setText(DATA_DATE.substring(7));

        TextView txtphone1 = (TextView) rootView.findViewById(R.id.phone_1);
        if (DATA_ROLE.equals("Teacher"))
            role = "Professeur";
        else role = "Eleve";
        txtphone1.setText(role);



//        TextView txtphone2 = (TextView) rootView.findViewById(R.id.phone_2);
//        if (objStudData.getString("isParent").equals("Yes"))
//            txtphone2.setText(objStudData.getString("Parent"));
//        else txtphone2.setText(objStudData.getString("Cet utilisateur n'est pas un parent"));

        return rootView;
    }

}
