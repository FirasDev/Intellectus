package com.example.intellectus.tn.esprit.intellectus.Entity;

import com.google.gson.annotations.SerializedName;

public class Classe {

    @SerializedName("class_Id")
    private int class_Id;

    @SerializedName("class_name")
    private String class_name;

    @SerializedName("class_capacite")
    private int class_capacite;


    public Classe(int class_Id, String class_name, int class_capacite){
        this.class_Id = class_Id;
        this.class_name = class_name;
        this.class_capacite = class_capacite;
    }

    public int getClass_Id() {
        return class_Id;
    }

    public void setClass_Id(int class_Id) {
        this.class_Id = class_Id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public int getClass_capacite() {
        return class_capacite;
    }

    public void setClass_capacite(int class_capacite) {
        this.class_capacite = class_capacite;
    }
}

