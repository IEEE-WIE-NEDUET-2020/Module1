package com.example.timetableremainder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.logging.LogRecord;


public class MainActivity extends AppCompatActivity {


public static String masla;
public static String ekormasla;
FirebaseAuth mAuth;
Button hiba;

 DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        hiba=findViewById(R.id.button3);



        final int SPLASH_TIME_OUT = 3000;
        hiba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(MainActivity.this,user.getUid(),Toast.LENGTH_LONG).show();

            }
        });






      if (FirebaseAuth.getInstance().getCurrentUser()==null){
          new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {
                  Intent a= new Intent(MainActivity.this,SignINActivity.class);
                  startActivity(a);
                  finish();



              }
          },SPLASH_TIME_OUT);
      }
      else if (FirebaseAuth.getInstance().getCurrentUser()!=null){
          GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
          FirebaseUser currentuser=FirebaseAuth.getInstance().getCurrentUser();

          String name=account.getDisplayName();
          databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");
             DatabaseReference refchild= databaseReference.child(currentuser.getUid());

          refchild.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  if(dataSnapshot.hasChild("LoginStatus")){
                      final String entercode=dataSnapshot.child("code").getValue().toString();
                      new Handler().postDelayed(new Runnable() {
                          @Override
                          public void run() {
                         Intent b= new Intent(MainActivity.this,ClassActivity.class);
                         b.putExtra("code",entercode);
                         startActivity(b);
                         finish();
                          }
                      },SPLASH_TIME_OUT);
                  }else{
                      new Handler().postDelayed(new Runnable() {
                          @Override
                          public void run() {
                              Intent c= new Intent(MainActivity.this,ClassCodeActivity.class);
                              startActivity(c);
                              finish();

                          }
                      },SPLASH_TIME_OUT);
                  }
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });

      }














}









}