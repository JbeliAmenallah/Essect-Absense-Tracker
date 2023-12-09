package com.example.essect3;


public class Prof {
    private String nom;

    private String email;
    private String password;


    // Required default constructor for Firebase
    public Prof() {
    }

    public Prof(String nom,String email, String password) {
        this.email = email;
        this.password = password;
      this.nom=nom;
    }

    // Getter and setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}