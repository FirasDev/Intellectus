package com.example.intellectus.tn.esprit.intellectus.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private static Retrofit instance;
    String emulatedIp = "http://10.0.2.2:3036/";
    String pcIp = "http://192.168.1.12:3036/";
    String serverIp = "http://41.226.11.252:11836";

    public static String myIp = "http://intellectus.eu-4.evennode.com/";
    //public static String myIp = "http://10.0.2.2:3036/";


    public static Retrofit getInstance(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (instance == null)
            instance = new Retrofit.Builder()
                    .baseUrl(myIp) // in emulator localhost will change to baseUrl
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return instance;
    }
}
