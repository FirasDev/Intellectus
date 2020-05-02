package com.example.intellectus.tn.esprit.intellectus.Entity;

import com.google.gson.annotations.SerializedName;

public class School {

    @SerializedName("school_id")
    private int id;
    @SerializedName("school_name")
    private String schoolName;
    @SerializedName("school_address")
    private String address;
    @SerializedName("school_state")
    private String state;
    @SerializedName("school_city")
    private String city;
    @SerializedName("school_postal_code")
    private String postalCode;
    @SerializedName("school_phone1")
    private String phone1;
    @SerializedName("school_phone2")
    private String phone2;
    @SerializedName("school_email")
    private String Email;
    @SerializedName("school_fax")
    private String fax;
    @SerializedName("school_logo")
    private String logo;
    @SerializedName("school_facebook")
    private String fbUrl;
    @SerializedName("school_principal")
    private String principal;

    public School() {
    }

    public School(int id, String schoolName, String address, String state, String city, String postalCode, String phone1, String phone2, String email, String fax, String logo, String fbUrl, String principal) {
        this.id = id;
        this.schoolName = schoolName;
        this.address = address;
        this.state = state;
        this.city = city;
        this.postalCode = postalCode;
        this.phone1 = phone1;
        this.phone2 = phone2;
        Email = email;
        this.fax = fax;
        this.logo = logo;
        this.fbUrl = fbUrl;
        this.principal = principal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFbUrl() {
        return fbUrl;
    }

    public void setFbUrl(String fbUrl) {
        this.fbUrl = fbUrl;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }
}
