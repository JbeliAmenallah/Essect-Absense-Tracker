package com.example.essect3;

import com.google.firebase.auth.FirebaseAuth;


public class FirebaseAuthManager {
    // Singleton instance
    private static FirebaseAuthManager instance;
    private FirebaseAuth firebaseAuth;

    private FirebaseAuthManager() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public static synchronized FirebaseAuthManager getInstance() {
        if (instance == null) {
            instance = new FirebaseAuthManager();
        }
        return instance;
    }

    public void signOut() {
        firebaseAuth.signOut();
    }
}
