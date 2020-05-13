package com.example.timetableremainder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView Email;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private static final String USERS= "timetable-remainder-f8f2e";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Intent intent = getIntent();
       // String email = user.getEmail;

        Email = findViewById(R.id.Email);

        database= FirebaseDatabase.getInstance();
        userRef = database.getReference(USERS);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String email = user.getEmail();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.child("email").getValue().equals(email)){
                        Email.setText(ds.child("email").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
