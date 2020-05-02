package com.example.intellectus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intellectus.tn.esprit.intellectus.Entity.User;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.INodeJS;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.RetrofitClient;
import com.example.intellectus.tn.esprit.intellectus.Utils.SessionManager;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    INodeJS myApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    SessionManager session;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    Button login_btn;
    EditText username, password;
    String musername, mpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());
        //init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myApi = retrofit.create(INodeJS.class);

        //View
        login_btn = findViewById(R.id.btnLogin);
        username = findViewById(R.id.edEmail);
        password = findViewById(R.id.edPassword);


        if (session.isLoggedIn()) {
            Call<User> call = myApi.getUserByEmail(session.getEmail());
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("failed session", "failed session");
                }
            });
        } else {
            Log.i("User", "NO");
        }


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //myusername = username.getText().toString();

                musername = username.getText().toString();
                mpassword = password.getText().toString();
                loginUser(musername, mpassword);
                Log.e("here 1", username.getText().toString());
                Log.e("here 2", password.getText().toString());
            }
        });
    }


    private void loginUser(final String email, String password) {
        Call<User> call = myApi.loginUser(email, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Log.i("Code :", String.valueOf(response.code()));
                }
                MainActivity.mainUser = response.body();
                if (MainActivity.mainUser == null) {
                    Toast.makeText(LoginActivity.this, "Mot de passe inconnue !", Toast.LENGTH_SHORT).show();

                } else {
                    //if (cbRememberMe.isChecked()){
                    session.cleanEditor();
                    session.createLoginSession(MainActivity.mainUser);
                    //sessionBasic.writeLoginStatus(true);
                    //}

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("Connexion", "Fail");
                Log.i("throwable :", String.valueOf(t.getMessage()));
                Toast.makeText(LoginActivity.this, "Connection failed !", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
