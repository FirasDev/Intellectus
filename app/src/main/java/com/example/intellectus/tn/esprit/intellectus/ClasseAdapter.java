package com.example.intellectus.tn.esprit.intellectus;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class ClasseAdapter extends RecyclerView.Adapter<ClasseAdapter.ViewHolder> {

    private List<Classe> classesList;
    private Context mContext;
    INodeJS myAPI;

    public ClasseAdapter(Context mContext, List<Classe> classesList) {
        this.mContext = mContext ;
        this.classesList = classesList;
        /// Init myAPI
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
    }


    public void notifyChange(List<Classe> classesList){
        this.classesList = classesList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClasseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mItemView = LayoutInflater.from(mContext).inflate(R.layout.row_classe, parent, false);

        return new ClasseAdapter.ViewHolder(mItemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ClasseAdapter.ViewHolder holder, int position) {
        final Classe singleItem = classesList.get(position);

        holder.txtClasse.setText(singleItem.getClass_name());
        holder.txtClasseNbr.setText(String.valueOf(singleItem.getClass_capacite()));
        holder.btnDeleteQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClass(singleItem.getClass_Id());
            }
        });

    }


    @Override
    public int getItemCount() {

        return classesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        public final TextView txtClasseNbr,txtClasse;
        public final Button btnDeleteQ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.txtClasseNbr = itemView.findViewById(R.id.txtClasseNbr);
            this.txtClasse = itemView.findViewById(R.id.txtClasse);
            this.btnDeleteQ = itemView.findViewById(R.id.btnDeleteQ);

        }

    }

    private void deleteClass(int id){
        Call<Void> call = myAPI.deleteClass(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                refreshList();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void refreshList(){

        Call<List<Classe>> call = myAPI.getListClass();

        call.enqueue(new Callback<List<Classe>>() {
            @Override
            public void onResponse(Call<List<Classe>> call, Response<List<Classe>> response) {
                classesList = response.body();
                Log.i("SIZE ---> ",response.body().size()+"");
                ClasseAdapter.this.notifyChange(classesList);
            }

            @Override
            public void onFailure(Call<List<Classe>> call, Throwable t) {

            }
        });
    }

}

