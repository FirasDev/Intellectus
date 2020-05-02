package com.example.intellectus.tn.esprit.intellectus.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intellectus.R;
import com.example.intellectus.tn.esprit.intellectus.Entity.Student;
import com.example.intellectus.tn.esprit.intellectus.Entity.Teacher;
import com.example.intellectus.tn.esprit.intellectus.Entity.User;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.INodeJS;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.RetrofitClient;
import com.example.intellectus.tn.esprit.intellectus.Utils.AppUtils;
import com.example.intellectus.tn.esprit.intellectus.fragment.UserDetails;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TeachersListAdapter  extends RecyclerView.Adapter<TeachersListAdapter.ViewHolder>  {

    private List<Teacher> users;
    private Context mContext;
    INodeJS myAPI;


    public TeachersListAdapter(Context mContext, List<Teacher> users) {
        this.mContext = mContext ;
        this.users = users;
        /// Init myAPI
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
    }

    public void notifyChange(List<Teacher> users){
        this.users = users;
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TeachersListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mItemView = LayoutInflater.from(mContext).inflate(R.layout.user_row, parent, false);

        return new TeachersListAdapter.ViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeachersListAdapter.ViewHolder holder, final int position) {
        final Teacher singleItem = users.get(position);


        //String pattern = "EEEEE dd MMMMM yyyy HH:mm:ss.SSSZ";
        String pattern = "HH:mm - EEEE, dd MMMM yyyy";
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(pattern, new Locale("fr", "FR"));

        String date = AppUtils.CAPITALIZETXT(simpleDateFormat.format(singleItem.getDataNaissance()));
        String fullname = singleItem.getName()+" "+singleItem.getLast_name();
        holder.txtUsername.setText(fullname);
        holder.txtDate.setText(singleItem.getEmail());
        holder.txtQuestion.setText(singleItem.getSchoolname());

        if (singleItem.getImage_url().length() > 3){
            Picasso.with(mContext).load(AppUtils.SERVER_URL+"uploads/"+singleItem.getImage_url()).into(holder.imgUser);
        }else{
            Picasso.with(mContext).load(AppUtils.SERVER_URL+"uploads/default.jpg").into(holder.imgUser);
        }

        holder.imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfo(singleItem.getId());
            }
        });

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("position",String.valueOf(position));
                removeAt(position);
                DeleteFromDB(String.valueOf(singleItem.getId()));
                Log.d("deleted user :","|"+String.valueOf(singleItem.getId())+"|");
            }
        });


        holder.moreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // USER


                String pattern = "HH:mm - EEEE, dd MMMM yyyy";
                SimpleDateFormat simpleDateFormat =
                        new SimpleDateFormat(pattern, new Locale("fr", "FR"));
                UserDetails.DATA_Name = singleItem.getName();
                UserDetails.DATA_UID = singleItem.getId();
                UserDetails.DATA_LASTNAME = singleItem.getLast_name();
                UserDetails.DATA_ROLE = singleItem.getRole();
                UserDetails.DATA_EMAIL = singleItem.getEmail();
                UserDetails.DATA_DATE = AppUtils.CAPITALIZETXT(simpleDateFormat.format(singleItem.getDataNaissance()));
                UserDetails.DATA_PARENT = singleItem.getIs_parent();
                UserDetails.DATA_UIMG = singleItem.getImage_url();
                UserDetails questionDetail = new UserDetails();
                showFragmentStudent(questionDetail);
                //Intent intent = new Intent(mContext, DisplayQuestion.class);
                //mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        int a ;

        if(users != null && !users.isEmpty()) {

            a = users.size();
        }
        else {

            a = 0;

        }

        return a;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public final TextView txtUsername,txtDate,txtQuestion;
        public final ImageView imgUser;
        public final LinearLayout moreDetails;
        public final FloatingActionButton del;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtUsername = itemView.findViewById(R.id.txtUsername);
            this.txtDate = itemView.findViewById(R.id.txtDate);
            this.txtQuestion = itemView.findViewById(R.id.txtQuestion);
            this.imgUser = itemView.findViewById(R.id.imgUser);
            this.moreDetails = itemView.findViewById(R.id.moreDetails);
            this.del = itemView.findViewById(R.id.del);
        }

    }

    private void userInfo(int id){
        Call<User> call = myAPI.getUserById(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("User ->",response.body().getRole());
                //HomePage.USER = response.body();
                UserDetails fProfileOne = new UserDetails();
                showFragmentProfile(fProfileOne);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("User ->","ERROR");
            }
        });
    }

    private void showFragmentStudent(Fragment fragment){
        FragmentManager fragmentManager = ((AppCompatActivity)mContext).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    private void showFragmentProfile(Fragment fragment){
        FragmentManager fragmentManager = ((AppCompatActivity)mContext).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    public void removeAt(int position) {
        users.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, users.size());
        Toast.makeText(mContext, "Eleve supprimé avec succés", Toast.LENGTH_SHORT).show();
    }

    public void DeleteFromDB(String id){
        Call<Void> call = myAPI.deleteUser(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("done","and done");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(mContext, "Cette école n'existe plus", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
