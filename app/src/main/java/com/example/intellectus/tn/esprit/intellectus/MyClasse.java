package com.example.intellectus.tn.esprit.intellectus;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.intellectus.R;
import com.example.intellectus.tn.esprit.intellectus.Entity.Classe;
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
public class MyClasse extends Fragment {


    INodeJS myAPI;
    private List<Classe> classeList;

    private RecyclerView mRecyclerView;
    private ClasseAdapter classeAdapter;
    public MyClasse() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_classe, container, false);

        /// Init myAPI
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        Log.i("Fragement Load","Enter");
        mRecyclerView = rootView.findViewById(R.id.recycler_list_classe);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        Call<List<Classe>> call = myAPI.getListClass();

        call.enqueue(new Callback<List<Classe>>() {
            @Override
            public void onResponse(Call<List<Classe>> call, Response<List<Classe>> response) {
                classeList = response.body();
                classeAdapter = new ClasseAdapter(getActivity(),classeList);
                mRecyclerView.setAdapter(classeAdapter);
                Log.i("SIZE ---> ",response.body().size()+"");
            }

            @Override
            public void onFailure(Call<List<Classe>> call, Throwable t) {

            }
        });





        return rootView;
    }

}

