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
import com.example.intellectus.tn.esprit.intellectus.fragment.SchoolDetails;
import com.example.intellectus.tn.esprit.intellectus.Entity.School;
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

public class SchoolsListAdapter  extends RecyclerView.Adapter<SchoolsListAdapter.ViewHolder>  {

    private List<School> schools;
    private Context mContext;
    INodeJS myAPI;


    public SchoolsListAdapter(Context mContext, List<School> schools) {
        this.mContext = mContext ;
        this.schools = schools;
        /// Init myAPI
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
    }

    public void notifyChange(List<School> schools){
        this.schools = schools;
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SchoolsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mItemView = LayoutInflater.from(mContext).inflate(R.layout.user_row, parent, false);

        return new SchoolsListAdapter.ViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolsListAdapter.ViewHolder holder, final int position) {
        final School singleItem = schools.get(position);


        //String pattern = "EEEEE dd MMMMM yyyy HH:mm:ss.SSSZ";
        String pattern = "HH:mm - EEEE, dd MMMM yyyy";
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(pattern, new Locale("fr", "FR"));

        String fullname = singleItem.getSchoolName();
        holder.txtUsername.setText(fullname);
        holder.txtDate.setText(singleItem.getState());
        holder.txtQuestion.setText(singleItem.getCity());

        Picasso.with(mContext).load(AppUtils.SERVER_URL+"uploads/"+singleItem.getLogo()).into(holder.imgUser);

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


                String pattern = "HH:mm - EEEE, dd MMMM yyyy";
                SimpleDateFormat simpleDateFormat =
                        new SimpleDateFormat(pattern, new Locale("fr", "FR"));
                SchoolDetails.DATA_NAME = singleItem.getSchoolName();
                SchoolDetails.DATA_UID = singleItem.getId();
                SchoolDetails.DATA_ADR = singleItem.getAddress();
                SchoolDetails.DATA_VILLE = singleItem.getState();
                SchoolDetails.DATA_EMAIL = singleItem.getEmail();
                SchoolDetails.DATA_CITE = singleItem.getCity();
                SchoolDetails.DATA_UIMG = singleItem.getLogo();

                SchoolDetails.DATA_CODE = singleItem.getPostalCode();
                SchoolDetails.DATA_PHONE1 = singleItem.getPhone1();
                SchoolDetails.DATA_PHONE2 = singleItem.getPhone2();
                SchoolDetails.DATA_FAX = singleItem.getFax();
                SchoolDetails.DATA_FB = singleItem.getFbUrl();
                SchoolDetails.DATA_DIRECTEUR = singleItem.getPrincipal();

                SchoolDetails questionDetail = new SchoolDetails();
                showFragmentStudent(questionDetail);
                //Intent intent = new Intent(mContext, DisplayQuestion.class);
                //mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        int a ;

        if(schools != null && !schools.isEmpty()) {

            a = schools.size();
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
        Call<School> call = myAPI.getSchoolById(id);
        call.enqueue(new Callback<School>() {
            @Override
            public void onResponse(Call<School> call, Response<School> response) {
                Log.i("User ->",response.body().getSchoolName());
                //HomePage.USER = response.body();
                SchoolDetails fProfileOne = new SchoolDetails();
                showFragmentProfile(fProfileOne);
            }

            @Override
            public void onFailure(Call<School> call, Throwable t) {
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
        schools.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, schools.size());
        Toast.makeText(mContext, "Eleve supprimé avec succés", Toast.LENGTH_SHORT).show();
    }

    public void DeleteFromDB(String id){
        Call<Void> call = myAPI.deleteSchool(id);
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

