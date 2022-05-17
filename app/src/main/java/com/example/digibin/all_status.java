package com.example.digibin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Date;

public class all_status extends AppCompatActivity {
    TextView temp, hmd, gas, bin,date,last_update_time;
    DatabaseReference mydb;
    ImageView dustbin;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_status);
        temp = (TextView) findViewById(R.id.textView16);
        hmd = (TextView) findViewById(R.id.textView19);
        gas = (TextView) findViewById(R.id.textView21);
        bin = (TextView) findViewById(R.id.textView4);
        last_update_time = (TextView) findViewById(R.id.lastUpdate);
        dustbin =(ImageView) findViewById(R.id.imageView);

        mydb = FirebaseDatabase.getInstance().getReference().child("Sensor/SIR01");

        try {

            mydb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String tempdata = dataSnapshot.child("temp").getValue().toString();
                    String humdata = dataSnapshot.child("humid").getValue().toString();
                    String gasdata = dataSnapshot.child("gas").getValue().toString();
                    String bindata = dataSnapshot.child("empty").getValue().toString();
                    //String datedata = dataSnapshot.child("Date").getValue().toString();
                    temp.setText(tempdata + "°C");
                    hmd.setText(humdata +"%");
                    gas.setText(gasdata + "ppm");
                    bin.setText(bindata + "%");
                    //date.setText(datedata);

                    Resources res = getResources();
                    int i=Integer.parseInt(bindata);

                    if (i >= 90) {
                        setLastUpdateTime("DH-01");
                        dustbin.setImageDrawable(res.getDrawable(R.drawable.full));

                    } else if (90 > i && i >= 65) {
                        setLastUpdateTime("DH-01");
                        dustbin.setImageDrawable(res.getDrawable(R.drawable.onth));

                    } else if (65 > i && i >= 40) {
                        setLastUpdateTime("DH-01");
                        dustbin.setImageDrawable(res.getDrawable(R.drawable.half));

                    } else if (40 > i && i >= 15) {
                        setLastUpdateTime("DH-01");
                        dustbin.setImageDrawable(res.getDrawable(R.drawable.str));

                    } else {
                        dustbin.setImageDrawable(res.getDrawable(R.drawable.emp));

                    }
                    getLastUpdateTime("DH-01");
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value

                }
            });

        } catch (Exception e) {


        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(all_status.this, home.class);
        startActivity(i);
    }

    public void  getLastUpdateTime(String binId){

        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("bins").document(binId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                String update_time = value.getString("LastUpdateTime");
                last_update_time.setText(update_time);

            }
        });

    }

    public void  setLastUpdateTime(String binId){
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("bins").document(binId);
        documentReference.update("LastUpdateTime","Last Update: " + currentDateTimeString);
    }
}