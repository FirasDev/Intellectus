package com.example.intellectus.tn.esprit.intellectus.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.intellectus.MainActivity;
import com.example.intellectus.R;
import com.example.intellectus.tn.esprit.intellectus.Adapters.UsersListAdapter;
import com.example.intellectus.tn.esprit.intellectus.Entity.Student;
import com.example.intellectus.tn.esprit.intellectus.Entity.User;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.INodeJS;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersList extends Fragment {

    private List<Student> usersList;


    private RecyclerView mRecyclerView;
    private UsersListAdapter usersAdapter;
    String myValue;

    FloatingActionButton ajouter;


    INodeJS myAPI;

    public UsersList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_users_list, container, false);

        ajouter = rootView.findViewById(R.id.ajouter);
//        try{
//            myValue = getArguments().getString("category");
//            Log.i("myValue ------------->",myValue);
//        }catch (Exception e){
//            Log.i("Erreur",e.getMessage());
//        }

        /// Init myAPI
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        mRecyclerView = rootView.findViewById(R.id.recycler_user_list);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));


        Call<List<Student>> call = myAPI.getAllStudents();

//        if(myValue == null || myValue.equals("All")){
//            call = myAPI.get();
//        }else{
//            call = myAPI.getQuestionsByCategory(myValue);
//        }


        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.logo.setVisibility(View.GONE);

            }
        });

        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                usersList = response.body();
                usersAdapter = new UsersListAdapter(getActivity(), usersList);
                mRecyclerView.setAdapter(usersAdapter);
                //usersAdapter.notifyDataSetChanged();
                Log.i("SIZE ---> ",response.body().size()+"");

            }
            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Log.d("failture",t.getMessage());
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
