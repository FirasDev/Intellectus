package com.example.intellectus.tn.esprit.intellectus.Retrofit;

import com.example.intellectus.tn.esprit.intellectus.Entity.Classe;
import com.example.intellectus.tn.esprit.intellectus.Entity.Manager;
import com.example.intellectus.tn.esprit.intellectus.Entity.School;
import com.example.intellectus.tn.esprit.intellectus.Entity.Student;
import com.example.intellectus.tn.esprit.intellectus.Entity.Teacher;
import com.example.intellectus.tn.esprit.intellectus.Entity.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface INodeJS {
    @POST("insert")
    @FormUrlEncoded
    Call<Void> registerUser(@Field("email") String email,
                            @Field("password") String password,
                            @Field("name") String name,
                            @Field("prenom")String lastname,
                            @Field("role")String role,
                            @Field("dateNaissance") String dateNaissance,
                            @Field("isParent")String isParent,
                            @Field("image")String image
    );




    @GET("students")
    Call<List<Student>> getAllStudents();

    @GET("teachers")
    Call<List<Teacher>> getAllTeachers();

    @GET("Managers")
    Call<List<Manager>> getAllManagers();

    @GET("schools")
    Call<List<School>> getAllSchools();

    @GET("getUserInfo/{id}")
    Call<User> getUserById(@Path("id") int id);

    @GET("getSchoolInfo/{id}")
    Call<School> getSchoolById(@Path("id") int id);

    @POST("/insert/manager")
    @FormUrlEncoded
    Call<Void> registerManager(@Field("email") String email,
                               @Field("password") String password,
                               @Field("name") String name,
                               @Field("prenom")String lastname,
                               @Field("role")String role,
                               @Field("isParent")String isParent,
                               @Field("image")String image
    );
    @POST("login")
    @FormUrlEncoded
    Call<User> loginUser(@Field("email") String email,
                         @Field("password") String password
    );

    @POST("getUserByEmail")
    @FormUrlEncoded
    Call<User> getUserByEmail(@Field("email")String email);

    @DELETE("school/delete/{id}")
    Call<Void> deleteSchool(@Path("id") String id);

    @DELETE("user/delete/{id}")
    Call<Void> deleteUser(@Path("id") String id);

    @POST("schools/add")
    @FormUrlEncoded
    Observable<String> addSchool(@Field("name") String name,
                                 @Field("email") String email,
                                 @Field("address") String address,
                                 @Field("postal_code") String postal_code,
                                 @Field("state") String state,
                                 @Field("city") String city,
                                 @Field("phone1") String phone1,
                                 @Field("phone2") String phone2,
                                 @Field("fax") String fax,
                                 @Field("logo") String logo,
                                 @Field("fb") String fb,
                                 @Field("school_principal") String school_principal
    );

    @PUT("/update/user")
    @FormUrlEncoded
    Observable<String> editUser(@Field("id")int id,
                        @Field("image")String image);



    @Multipart
    @POST("/upload")
    Observable<String> postImage(@Part MultipartBody.Part image, @Part("upload") RequestBody name);


    @POST("/affect/")
    @FormUrlEncoded
    Call<Void> affectation(@Field("id") String id,
                           @Field("classid") String classid
    );
    @POST("getIdByEmail")
    @FormUrlEncoded
    Call<String> getUserId(@Field("email")String email);
    @POST("getIdByClass")
    @FormUrlEncoded
    Call<String> getClassId(@Field("classe")String classe);


    @POST("class/add")
    @FormUrlEncoded
    Observable<String> addClass(@Field("name") String name,
                                @Field("capacite") String capacite
    );

    @POST("emploi/add")
    @FormUrlEncoded
    Observable<String> addEmploi(@Field("name") String name,
                                 @Field("link") String link
    );



    @POST("eleveclass/add")
    @FormUrlEncoded
    Observable<String> addEleveClass(@Field("usermail") String usermail,
                                     @Field("classname") String classname
    );

    @POST("teacherclass/add")
    @FormUrlEncoded
    Observable<String> addTeacherClass(@Field("usermail") String usermail,
                                       @Field("classname") String classname
    );

    @POST("homework/add")
    @FormUrlEncoded
    Observable<String> addHomework(@Field("usermail") String usermail,
                                   @Field("title") String title,
                                   @Field("content") String content,
                                   @Field("link") String link
    );

    @GET("classes")
    Call<List<Classe>> getListClass();

    @DELETE("class/delete/{id}")
    Call<Void> deleteClass(@Path("id") int id);

    @GET("eleves")
    Call<List<User>> getListEleve();

    @DELETE("eleve/delete/{id}")
    Call<Void> deleteEleve(@Path("id") int id);

    @GET("profs")
    Call<List<User>> getListprof();

    @DELETE("prof/delete/{id}")
    Call<Void> deleteProf(@Path("id") int id);

    @GET("admins")
    Call<List<User>> getListadmin();

    @DELETE("admin/delete/{id}")
    Call<Void> deleteadmin(@Path("id") int id);

    @POST("insert/admin")
    @FormUrlEncoded
    Observable<String> addAdmin(@Field("name") String prenom,
                                   @Field("last_name") String nom,
                                   @Field("email") String email,
                                   @Field("role") String role,
                                @Field("isParent") String isParent,
                                @Field("image") String image,
                                @Field("password") String password

    );

}
