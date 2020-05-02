package com.example.intellectus.tn.esprit.intellectus.Entity;

import java.util.Date;

public class User {

    private int id;
    private String name;
    private String last_name;
    private String email;
    private String role;
    private String is_parent;
    private String image_url;
    private Date dataNaissance;


    public User(int id, String name, String last_name, String email, String role, String is_parent, String image_url, Date dataNaissance) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.role = role;
        this.is_parent = is_parent;
        this.image_url = image_url;
        this.dataNaissance = dataNaissance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIs_parent() {
        return is_parent;
    }

    public void setIs_parent(String is_parent) {
        this.is_parent = is_parent;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Date getDataNaissance() {
        return dataNaissance;
    }

    public void setDataNaissance(Date dataNaissance) {
        this.dataNaissance = dataNaissance;
    }
}

