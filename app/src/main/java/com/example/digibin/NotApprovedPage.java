package com.example.digibin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotApprovedPage extends AppCompatActivity {

    Button NA_lgoin_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_approved_page);

        NA_lgoin_btn = findViewById(R.id.NA_login_page);

        NA_lgoin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotApprovedPage.this, login.class);
                startActivity(intent);
            }
        });
    }
}