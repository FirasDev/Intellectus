package com.example.intellectus.tn.esprit.intellectus.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.intellectus.R;
import com.example.intellectus.tn.esprit.intellectus.Adapters.ManagersListAdapter;
import com.example.intellectus.tn.esprit.intellectus.Adapters.TeachersListAdapter;
import com.example.intellectus.tn.esprit.intellectus.Entity.Manager;
import com.example.intellectus.tn.esprit.intellectus.Entity.Teacher;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.INodeJS;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerList extends Fragment {


    private List<Manager> managerList;

    private RecyclerView mRecyclerView;
    private ManagersListAdapter usersAdapter;
    String myValue;


    INodeJS myAPI;

    public ManagerList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_manager_list, container, false);



        /// Init myAPI
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        mRecyclerView = rootView.findViewById(R.id.recycler_manager_list);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));


        Call<List<Manager>> call = myAPI.getAllManagers();

//        if(myValue == null || myValue.equals("All")){
//            call = myAPI.get();
//        }else{
//            call = myAPI.getQuestionsByCategory(myValue);
//        }

        call.enqueue(new Callback<List<Manager>>() {
            @Override
            public void onResponse(Call<List<Manager>> call, Response<List<Manager>> response) {
                managerList = response.body();
                usersAdapter = new ManagersListAdapter(getActivity(), managerList);
                mRecyclerView.setAdapter(usersAdapter);
                //usersAdapter.notifyDataSetChanged();
                Log.i("SIZE ---> ",response.body().size()+"");

            }
            @Override
            public void onFailure(Call<List<Manager>> call, Throwable t) {
                Log.d("failture",t.getMessage());
            }
        });

        return rootView;
    }

}
