package com.example.essect3;


public class Etudiant {
    private String nom;

    private String email;
    private String password;
    private String group;


    // Required default constructor for Firebase
    public Etudiant() {
    }

    public Etudiant(String nom,String email, String password, String group) {
        this.email = email;
        this.password = password;
        this.nom=nom;
        this.group=group;
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


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
    public String toCSVString() {
        // Assuming you have properties like email, nom, and group
        return getEmail() + "," + getNom() + "," + getGroup() + "\n";
    }

}
