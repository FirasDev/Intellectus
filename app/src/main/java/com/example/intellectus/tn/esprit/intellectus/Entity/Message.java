package com.example.intellectus.tn.esprit.intellectus.Entity;

public class Message {

    private String nickname;
    private String message ;
    public String avatar;

    public Message(){

    }
//    public Message(String nickname, String message) {
//        this.nickname = nickname;
//        this.message = message;
//    }

    public Message(String nickname, String message, String avatar) {
        this.nickname = nickname;
        this.message = message;
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
