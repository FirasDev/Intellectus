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
import android.widget.Toast;

import com.example.intellectus.MainActivity;
import com.example.intellectus.R;
import com.example.intellectus.tn.esprit.intellectus.Adapters.SchoolsListAdapter;
import com.example.intellectus.tn.esprit.intellectus.Adapters.UsersListAdapter;
import com.example.intellectus.tn.esprit.intellectus.Entity.School;
import com.example.intellectus.tn.esprit.intellectus.Entity.Student;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.INodeJS;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class SchoolList extends Fragment {


    private List<School> schoolsList;

    FloatingActionButton add;


    private RecyclerView mRecyclerView;
    private SchoolsListAdapter schoolsAdapter;
    String myValue;


    INodeJS myAPI;
    public SchoolList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_school_list, container, false);

        add = rootView.findViewById(R.id.addSchool);
        /// Init myAPI
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        mRecyclerView = rootView.findViewById(R.id.recycler_school_list);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        if (!MainActivity.mainUser.getRole().contains("Owner")){
            add.setVisibility(View.GONE);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.logo.setVisibility(View.GONE);
                showFragment(new AddSchool());
            }
        });

        Call<List<School>> call = myAPI.getAllSchools();

        call.enqueue(new Callback<List<School>>() {
            @Override
            public void onResponse(Call<List<School>> call, Response<List<School>> response) {
                schoolsList = response.body();
                schoolsAdapter = new SchoolsListAdapter(getActivity(), schoolsList);
                mRecyclerView.setAdapter(schoolsAdapter);
                //usersAdapter.notifyDataSetChanged();
                Log.i("SIZE ---> ",response.body().size()+"");

            }
            @Override
            public void onFailure(Call<List<School>> call, Throwable t) {
                Log.d("failture",t.getMessage());
            }
        });

        return rootView;
    }

    private void DeleteSchool(String id){
        Call<Void> call = myAPI.deleteSchool(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getActivity(), "Ecole effacé avec succés", Toast.LENGTH_SHORT).show();
                schoolsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Cette école n'existe plus", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

}
