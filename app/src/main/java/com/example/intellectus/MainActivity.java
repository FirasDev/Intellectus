package com.example.intellectus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.intellectus.tn.esprit.intellectus.Entity.User;
import com.example.intellectus.tn.esprit.intellectus.Utils.SessionManager;
import com.example.intellectus.tn.esprit.intellectus.fragment.AdminButtons;
import com.example.intellectus.tn.esprit.intellectus.fragment.ManagerButtons;
import com.example.intellectus.tn.esprit.intellectus.fragment.OwnerButtons;
import com.example.intellectus.tn.esprit.intellectus.fragment.StudentButtons;
import com.example.intellectus.tn.esprit.intellectus.fragment.TeacherButtons;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class MainActivity extends AppCompatActivity {

    public static User mainUser;
    ImageView logout;
    SessionManager session;
    public static ImageView logo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = findViewById(R.id.imageView2);

        if (mainUser.getRole().equals("Admin")){
            AdminButtons ab = new AdminButtons();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, ab).commit();
        } else if (mainUser.getRole().equals("Student")){
            StudentButtons ab = new StudentButtons();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, ab).commit();
        }else if (mainUser.getRole().equals("Manager")){
            ManagerButtons mb = new ManagerButtons();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mb).commit();
        }else if (mainUser.getRole().equals("Teacher")){
            TeacherButtons mb = new TeacherButtons();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mb).commit();
        }else if (mainUser.getRole().equals("Owner")){
            OwnerButtons mb = new OwnerButtons();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mb).commit();
        }

        logout = (ImageView) findViewById(R.id.imglogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*SharedPreferences myPrefs = getSharedPreferences("MY_PREF",
                        MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(getApplicationContext(),getString(R.string.main_activity_logout), Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);*/

                new MaterialAlertDialogBuilder(MainActivity.this)
                        .setTitle("Déconnexion...")
                        .setMessage("Voulez-vous vraiment déconnecter Intellectus ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // User pressed YES button. Write Logic Here
                                MainActivity.mainUser = null;
                                Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent1);
                                finish();
                            }
                        })
                        .setNegativeButton("Non", null)
                        .show();

            }
        });

    }
}
