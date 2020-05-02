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
import com.example.intellectus.tn.esprit.intellectus.Adapters.TeachersListAdapter;
import com.example.intellectus.tn.esprit.intellectus.Adapters.UsersListAdapter;
import com.example.intellectus.tn.esprit.intellectus.Entity.Student;
import com.example.intellectus.tn.esprit.intellectus.Entity.Teacher;
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
public class TeacherList extends Fragment {

    private List<Teacher> teacherList;


    private RecyclerView mRecyclerView;
    private TeachersListAdapter usersAdapter;
    String myValue;
    FloatingActionButton add;

    INodeJS myAPI;

    public TeacherList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_teacher_list, container, false);

        add = rootView.findViewById(R.id.addTeach);

        /// Init myAPI
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        mRecyclerView = rootView.findViewById(R.id.recycler_teacher_list);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        Call<List<Teacher>> call = myAPI.getAllTeachers();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.logo.setVisibility(View.GONE);
                showFragment(new AddTeacher());
            }
        });

        call.enqueue(new Callback<List<Teacher>>() {
            @Override
            public void onResponse(Call<List<Teacher>> call, Response<List<Teacher>> response) {
                teacherList = response.body();
                usersAdapter = new TeachersListAdapter(getActivity(), teacherList);
                mRecyclerView.setAdapter(usersAdapter);
                //usersAdapter.notifyDataSetChanged();
                Log.i("SIZE ---> ",response.body().size()+"");

            }
            @Override
            public void onFailure(Call<List<Teacher>> call, Throwable t) {
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
