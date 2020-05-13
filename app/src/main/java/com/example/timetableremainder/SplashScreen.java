package com.example.timetableremainder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {
    DatabaseReference databaseReference;

    public static String mushkil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);







        setContentView(R.layout.activity_splash_screen);






        final int SPLASH_TIME_OUT = 3000;
        final String name="Hiba";




        readData(new FirebaseCallback() {
            @Override
            public void onCallback(String[] tension) {

                String mushkil = tension[0];

            }

        });

           Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intenta = new Intent(SplashScreen.this, ClassActivity.class);

                    startActivity(intenta);
                    finish();


                }
            },SPLASH_TIME_OUT );











    }

    private void readData(final FirebaseCallback firebaseCallback) {
        final String[] answer=new String[1];


        final String[] parent = new String[1];
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        String userAccount = account.getEmail();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.orderByChild("email").equalTo(userAccount).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    final String keys = datas.getKey();
                    parent[0] = keys;


                }
//                        firebaseCallback.onCallback(parent);
                databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(parent[0]);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String value = String.valueOf(dataSnapshot.child("LoginStatus").getValue());
                        answer[0]=value;
                        firebaseCallback.onCallback(answer);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });











    }




    private interface FirebaseCallback {


        void onCallback(String[] tension);


    }












}
