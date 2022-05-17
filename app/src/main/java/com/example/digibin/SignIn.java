package com.example.digibin;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignIn extends AppCompatActivity {

    Button s_submit,go_login_btn;
    TextInputLayout s_email, s_password, user_location, user_name, user_phonenumber;
    FirebaseAuth fAuth;


    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    private static int RC_SIGN_IN =100;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        go_login_btn = findViewById(R.id.login_page_btn);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);

        s_email = findViewById(R.id.email);
        s_password = findViewById(R.id.password);
        s_submit = findViewById(R.id.signin);
        user_name = findViewById(R.id.name);
        user_phonenumber = findViewById(R.id.phone_number);
        user_location = findViewById(R.id.location);
        fAuth = FirebaseAuth.getInstance();

        s_submit.setOnClickListener(view -> {
            createUser();
        });

        go_login_btn.setOnClickListener(view ->{
            Intent intent = new Intent(SignIn.this, login.class);
            startActivity(intent);
        });
    };



    private void createUser(){
            String user_email = s_email.getEditText().getText().toString().trim();
            String pass = s_password.getEditText().getText().toString().trim();
            String name = user_name.getEditText().getText().toString().trim();
            String phonenumber = user_phonenumber.getEditText().getText().toString().trim();
            String location = user_location.getEditText().getText().toString().trim();

            if(name.isEmpty())
            {
                user_name.setError("Name Field Can't be Empty");
                user_name.requestFocus();
                return;
            }

            if(user_email.isEmpty())
            {
                s_email.setError("Email Field Can't be Empty");
                s_email.requestFocus();
                return;
            }

            if(phonenumber.isEmpty())
            {
                user_phonenumber.setError("Phone Number Field Can't be Empty");
                user_phonenumber.requestFocus();
                return;
            }

            if(location.isEmpty())
            {
                user_location.setError("Location Field Can't be Empty");
                user_location.requestFocus();
                return;
            }

            if(pass.isEmpty())
            {
                s_password.setError("Password Field Can't be Empty");
                s_password.requestFocus();
                return;
            }
            if(pass.length()<6)
            {
                s_password.setError("Length of s_password is more than 6");
                s_password.requestFocus();
                return;
            }

            fAuth.createUserWithEmailAndPassword(user_email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        try {
                            Toast.makeText(SignIn.this,"Registration Has been Completed",Toast.LENGTH_SHORT).show();
                            addDataToFirestore(name, phonenumber, location, user_email);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    fAuth.getInstance().signOut();
                                }
                            }, 3000);
                            startActivity(new Intent(SignIn.this,NotApprovedPage.class));
                        }catch (Exception e){
                            Toast.makeText(SignIn.this,e.toString(),Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(SignIn.this,"SignedIn Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        };

    private void addDataToFirestore(String userName, String phone_number, String location, String email) {

        db = FirebaseFirestore.getInstance();
        CollectionReference dbUsers = db.collection("users");
        Map<String,Object> user_info = new HashMap<>();

        user_info.put("Email",email);
        user_info.put("Name",userName);
        user_info.put("PhoneNumber",phone_number);
        user_info.put("Location",location);
        user_info.put("Status","Approved");
        user_info.put("UserType","user");

//        user_info.put("admin_",false);
        dbUsers.document(email).set(user_info);

    }

};
