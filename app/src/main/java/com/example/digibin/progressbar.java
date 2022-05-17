package com.example.digibin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class progressbar extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressbar);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
//        loadingPB = findViewById(R.id.idProgressBar);

        DocumentReference documentReference = db.collection("users").document(mAuth.getCurrentUser().getEmail());
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String userType = value.getString("UserType");
                if(userType.matches("Admin")){
//                    loadingPB.setVisibility(View.GONE);
                    startActivity(new Intent(progressbar.this, home.class));
                }else{
//                    loadingPB.setVisibility(View.GONE);
                    startActivity(new Intent(progressbar.this, all_status_user.class));
                }
            }
        });
    }
}