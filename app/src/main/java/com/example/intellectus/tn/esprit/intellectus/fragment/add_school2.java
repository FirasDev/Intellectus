package com.example.intellectus.tn.esprit.intellectus.fragment;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;

import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.intellectus.R;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.INodeJS;
import com.example.intellectus.tn.esprit.intellectus.Retrofit.RetrofitClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


/**
 * A simple {@link Fragment} subclass.
 */
public class add_school2 extends Fragment {

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_RESULT = 200;
    EditText phone1,phone2,fax,fb,school_principal;
    Button sendbtn,logobtn;
    ImageView imageview;
    private String mediaPath;
    Bitmap mBitmap;

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public add_school2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_school2, container, false);

        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        phone1 = rootView.findViewById(R.id.txtphone1);
        phone2 = rootView.findViewById(R.id.txtphone2);
        fax = rootView.findViewById(R.id.txtfax);
        fb = rootView.findViewById(R.id.txtFacebook);
        school_principal = rootView.findViewById(R.id.txtDirecteur);
        logobtn = rootView.findViewById(R.id.button_upload);
        sendbtn  = rootView.findViewById(R.id.suiv2);
        imageview = rootView.findViewById(R.id.imageview);

        logobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                askPermissions();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,0);


            }
        });

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String regexStr = "^[0-9]*$";

                if(phone1.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Numéro de telephone 1 erroné", Toast.LENGTH_LONG).show();
                }else if(phone2.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Numéro de telephone 2 erroné", Toast.LENGTH_LONG).show();
                }else if(phone1.getText().toString().length()< 8){
                    Toast.makeText(getContext(), "Numéro de telephone 1 doit comporté 8 chiffres", Toast.LENGTH_LONG).show();
                }else if(phone2.getText().toString().length()< 8){
                    Toast.makeText(getContext(), "Numéro de telephone 2 doit comporté 8 chiffres", Toast.LENGTH_LONG).show();
                }else if(fax.getText().toString().length()< 8){
                    Toast.makeText(getContext(), "Numéro de fax doit comporté 8 chiffres", Toast.LENGTH_LONG).show();
                }else if(fax.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Numéro de fax erroné", Toast.LENGTH_LONG).show();
                }else if(fb.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Facebook adresse erroné", Toast.LENGTH_LONG).show();
                }else if(school_principal.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Vous devez spécifier le directeur", Toast.LENGTH_LONG).show();
                }
//                else if(imageUrl.isEmpty()){
//                    Toast.makeText(getContext(), "Vous devez chosir un logo", Toast.LENGTH_LONG).show();
//                }
                else if(!phone1.getText().toString().trim().matches(regexStr)){
                    Toast.makeText(getContext(), "Numéro de telephone 1 doit être un numero", Toast.LENGTH_LONG).show();
                }else if(!phone2.getText().toString().trim().matches(regexStr)){
                    Toast.makeText(getContext(), "Numéro de telephone 2 doit être un numero", Toast.LENGTH_LONG).show();
                }else if(!fax.getText().toString().trim().matches(regexStr)){
                    Toast.makeText(getContext(), "Numéro de fax doit être un numero", Toast.LENGTH_LONG).show();
                }
                else{
                //    InsertSchool(AddSchool.schoolName,AddSchool.schoolEmail,AddSchool.schoolAddress,AddSchool.schoolState,AddSchool.schoolCity,phone1.getText().toString(),phone2.getText().toString(),AddSchool.schoolPostal_code,fax.getText().toString(),mediaPath,fb.getText().toString(),school_principal.getText().toString());

                    UplodaImage(mediaPath);
                    //multipartImageUpload();
                }
            }
        });

        return rootView;
    }

    private void InsertSchool(String name,String email,String address,String state,String city,String phone1,String phone2,String postal_code,String fax,String logo, String fb,String school_principal) {
        Log.d("name:",name);
        Log.d("email:",email);
        Log.d("address:",address);
        Log.d("state:",state);
        Log.d("city:",city);
        Log.d("postal_code:",postal_code);
        Log.d("phone1:",phone1);
        Log.d("phone2:",phone2);
        Log.d("fax:",fax);
        Log.d("fb:",fb);
        Log.d("school_principal:",school_principal);
        Log.d("logo:",logo);

        //compositeDisposable.add(myAPI.addSchool(name,email,address,postal_code,state,city,phone1,phone2,fax,logo,fb,school_principal)

        compositeDisposable.add(myAPI.addSchool(name,email,address,postal_code,state,city,phone1,phone2,fax,logo,fb,school_principal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String s) throws Exception {
                                   if (s.contains("Insertion Successfull"))
                                   {
                                       Toast.makeText(getActivity(), "école ajouté avec succès", Toast.LENGTH_SHORT).show();
                                       AddManager addManager = new AddManager();
                                       FragmentManager fragmentManager = getFragmentManager();
                                       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                       fragmentTransaction.replace(R.id.frame, addManager);
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
        RequestBody requestBody;RequestBody logoname;

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

        Log.d("body",body.toString());
        Log.d("logoname",logoname.toString());

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
                                   InsertSchool(AddSchool.schoolName,AddSchool.schoolEmail,AddSchool.schoolAddress,AddSchool.schoolState,AddSchool.schoolCity,phone1.getText().toString(),phone2.getText().toString(),AddSchool.schoolPostal_code,fax.getText().toString(),s,fb.getText().toString(),school_principal.getText().toString());

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
            imageview.setImageBitmap(mBitmap);

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
