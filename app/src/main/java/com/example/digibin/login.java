package com.example.digibin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class login extends AppCompatActivity {
    Button login_to_home;
    Button signin_page;

    private TextInputLayout user_email;
    private TextInputLayout user_password;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user_email=findViewById(R.id.email);
        user_password=findViewById(R.id.password);
        login_to_home = findViewById(R.id.login);
        signin_page = findViewById(R.id.sigin_page);

        mAuth = FirebaseAuth.getInstance();
        login_to_home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String email= user_email.getEditText().getText().toString().trim();
                String password= user_password.getEditText().getText().toString().trim();
                if(email.isEmpty())
                {
                    user_email.setError("Email Field Can't be Empty");
                    user_email.requestFocus();
                    return;
                }

                if(password.isEmpty())
                {
                    user_password.setError("Password Field Can't be Empty");
                    user_password.requestFocus();
                    return;
                }
                if(password.length()<6)
                {
                    user_password.setError("Length of s_password is more than 6");
                    user_password.requestFocus();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        approve_status(email);
                    }
                    else
                    {
                        Toast.makeText(login.this,"Please Check Your login Credentials", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });
        signin_page.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, SignIn.class);
                startActivity(intent);
            }
        });
    }

    public void  approve_status(String email){

        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("users").document(email);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                String status = value.getString("Status");
                String userType = value.getString("UserType");
                if(status.matches("Approved") && userType.matches("Admin") ){
//                    Toast.makeText(login.this,"You Have Successfully Logged In", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(login.this, home.class));
                }else if(status.matches("Approved")){
                    startActivity(new Intent(login.this, all_status_user.class));
                }else{
                    startActivity(new Intent(login.this, NotApprovedPage.class));
                    mAuth.getInstance().signOut();
                }
            }
        });

    }

}