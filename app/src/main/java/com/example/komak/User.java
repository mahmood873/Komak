package com.example.komak;

public class User {

    public String uid;
    public String firstname;
    public String lastname;
    public String email;
    public String password;

    public User(){

    }
    public User(String uid, String firstname, String lastname, String email, String password){
        this.uid=uid;
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;
        this.password=password;
    }
}
