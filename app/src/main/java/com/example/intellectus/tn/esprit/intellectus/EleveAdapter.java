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
import com.example.intellectus.tn.esprit.intellectus.Entity.User;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.INodeJS;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EleveAdapter extends RecyclerView.Adapter<EleveAdapter.ViewHolder> {

    private List<User> elevesList;
    private Context mContext;
    INodeJS myAPI;

    public EleveAdapter(Context mContext, List<User> elevesList) {
        this.mContext = mContext ;
        this.elevesList = elevesList;
        /// Init myAPI
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
    }


    public void notifyChange(List<User> elevesList){
        this.elevesList = elevesList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EleveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mItemView = LayoutInflater.from(mContext).inflate(R.layout.row_user, parent, false);

        return new EleveAdapter.ViewHolder(mItemView);
    }


    @Override
    public void onBindViewHolder(@NonNull EleveAdapter.ViewHolder holder, int position) {
        final User singleItem = elevesList.get(position);

        holder.mail.setText(singleItem.getEmail());
        holder.date.setText(String.valueOf(singleItem.getDataNaissance()));
        holder.btnDeleteQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClass(singleItem.getId());
            }
        });

    }


    @Override
    public int getItemCount() {

        return elevesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        public final TextView mail,date;
        public final Button btnDeleteQ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.mail = itemView.findViewById(R.id.mail);
            this.date = itemView.findViewById(R.id.date);
            this.btnDeleteQ = itemView.findViewById(R.id.btnDeleteQ);

        }

    }

    private void deleteClass(int id){
        Call<Void> call = myAPI.deleteEleve(id);
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

        Call<List<User>> call = myAPI.getListEleve();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
               elevesList = response.body();
                Log.i("SIZE ---> ",response.body().size()+"");
                EleveAdapter.this.notifyChange(elevesList);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

}

