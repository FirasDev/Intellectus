package com.example.intellectus.tn.esprit.intellectus.fragment;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intellectus.MainActivity;
import com.example.intellectus.R;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.INodeJS;
import com.example.intellectus.tn.esprit.intellectus.Utils.AppUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfile extends Fragment {

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_RESULT = 200;
    private String mediaPath;
    Bitmap mBitmap;

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    //User
    public String DATA_Name = MainActivity.mainUser.getName();
    public int DATA_UID = MainActivity.mainUser.getId();
    public String DATA_LASTNAME = MainActivity.mainUser.getLast_name();
    public String DATA_ROLE = MainActivity.mainUser.getRole();
    public String DATA_EMAIL = MainActivity.mainUser.getEmail();
    String pattern = "HH:mm - EEEE, dd MMMM yyyy";
    SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat(pattern, new Locale("fr", "FR"));
    String date = AppUtils.CAPITALIZETXT(simpleDateFormat.format(MainActivity.mainUser.getDataNaissance()));
    public String DATA_DATE = date;
    public String DATA_PARENT = MainActivity.mainUser.getIs_parent();
    public String DATA_UIMG = MainActivity.mainUser.getImage_url();

    ImageView imgUser;

    String role;

    ImageView editbtn;

    Button confirm;


    public UserProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_user_profile, container, false);

        imgUser = rootView.findViewById(R.id.userAvatar);
        confirm = rootView.findViewById(R.id.confirm);

        confirm.setVisibility(View.GONE);

        if (DATA_UIMG.length() > 3){
            Picasso.with(getContext()).load(AppUtils.SERVER_URL+"uploads/"+DATA_UIMG).into(imgUser);
        }else{
            Picasso.with(getContext()).load(AppUtils.SERVER_URL+"uploads/default.jpg").into(imgUser);
        }

        String fullName = DATA_Name+ " "+ DATA_LASTNAME;
        TextView title = rootView.findViewById(R.id.formtitle);
        title.setText(fullName);

        TextView txtaddress = (TextView) rootView.findViewById(R.id.address);
        txtaddress.setText(DATA_Name);

        TextView txtstate = (TextView) rootView.findViewById(R.id.state);
        txtstate.setText(DATA_LASTNAME);

        TextView txtcity = (TextView) rootView.findViewById(R.id.city);
        txtcity.setText(DATA_EMAIL);

        TextView txtpostal = (TextView) rootView.findViewById(R.id.postal_code);
        txtpostal.setText(DATA_DATE.substring(7));

        TextView txtphone1 = (TextView) rootView.findViewById(R.id.phone_1);

        editbtn = rootView.findViewById(R.id.editbtn);
        if (DATA_ROLE.equals("Teacher"))
        {
            role = "Professeur";
        }
        else if (DATA_ROLE.equals("Teacher"))
        {
            role = "Eleve";
        }
        else {
            role = DATA_ROLE;
        }
        txtphone1.setText(role);

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //askPermissions();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,0);
                confirm.setVisibility(View.VISIBLE);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UplodaImage(mediaPath);
            }
        });
        return rootView;
    }

    private void EditingUser(String logo) {

        compositeDisposable.add(myAPI.editUser(DATA_UID,logo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String s) throws Exception {
                                   if (s.contains("Update Successful"))
                                   {
                                       Toast.makeText(getActivity(), "Votre photo a été changer", Toast.LENGTH_SHORT).show();
                                       UserProfile myProfile = new UserProfile();
                                       FragmentManager fragmentManager = getFragmentManager();
                                       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                       fragmentTransaction.replace(R.id.frame, myProfile);
                                       fragmentTransaction.addToBackStack(null).commit();

                                   }
                                   else
                                       Toast.makeText(getActivity(), "Erreur", Toast.LENGTH_SHORT).show();
                               }
                           }
                ));
    }








    //////////////////////////PERMISSION///////////////////////////////////
    private void askPermissions() {
        permissions.add(CAMERA);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    //////////////////////////PERMISSION///////////////////////////////////


    ////////////////////IMAGE UPLOAD//////////////////////////


    private void UplodaImage(String img){
        File file ;
        MultipartBody.Part body;
        RequestBody requestBody;
        RequestBody logoname;

//        try{
//            file = new File(img);
//            requestBody  = RequestBody.create(MediaType.parse("image/*"),file);
//            body = MultipartBody.Part.createFormData("file",file.getName(),requestBody);
//
//        }catch (Exception e){
//            Log.i("Error","File");
//            body = null;
//        }

        file = new File(img);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
        logoname = RequestBody.create(MediaType.parse("text/plain"), "upload");

        compositeDisposable.add(myAPI.postImage(body,logoname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String s) throws Exception {
                                   if (TextUtils.isEmpty(s))
                                   {
                                       Log.d("Error","Error");
                                   }
                                   else
                                       Log.d("Success","Success");
                                   EditingUser(s);

                               }
                           }
                ));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            if (data == null){
                Toast.makeText(this.getContext(), "Unable To load Data", Toast.LENGTH_LONG).show();
            }

            Uri imageUri = data.getData();
            mediaPath = getRealPathFromUri(imageUri);
            mBitmap = BitmapFactory.decodeFile(mediaPath);
            imgUser.setImageBitmap(mBitmap);

        }
        else if (resultCode != getActivity().RESULT_CANCELED) {
            Toast.makeText(this.getContext(), "Sorry, there was an error!", Toast.LENGTH_LONG).show();
        }
    }

    private String getRealPathFromUri(Uri uri){
        String[] projection =  {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getActivity().getApplicationContext(),uri ,projection,null,null,null);
        Cursor cursor = loader.loadInBackground();
        int column_idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_idx);
        cursor.close();
        return result;
    }







    //////////////////IMAGE UPLOAD/////////////////////////

}
