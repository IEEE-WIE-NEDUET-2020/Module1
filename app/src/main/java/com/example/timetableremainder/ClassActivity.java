package com.example.timetableremainder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClassActivity extends AppCompatActivity {

DatabaseReference databaseReference;
    FirebaseAuth mAuth;



    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);



        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }


    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
       final String id=account.getEmail();

        databaseReference=FirebaseDatabase.getInstance().getReference().child("CRS");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String value=dataSnapshot1.getValue().toString();
                    if(value.equals(id)){
                        menu.findItem(R.id.share).setVisible(true);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return super.onPrepareOptionsMenu(menu);
    }






        @Override
        public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override

        public boolean onOptionsItemSelected (@NonNull MenuItem item){


        switch (item.getItemId()) {
            case R.id.profile:
                Intent intent = new Intent(ClassActivity.this, ProfileActivity.class);
                ClassActivity.this.startActivity(intent);

            case R.id.share:


                Intent code_receiving=getIntent();
                String receive_code=code_receiving.getStringExtra("code");

                Intent intentshare = new Intent(Intent.ACTION_SEND);
                intentshare.setType("text/plane");


                intentshare.putExtra(intentshare.EXTRA_TEXT, receive_code);
                startActivity(intentshare.createChooser(intentshare, " Code Share"));










                return true;
            case R.id.sign_out:
                GoogleSignInAccount address = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                FirebaseUser  user=FirebaseAuth.getInstance().getCurrentUser();

                databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("Users");
                DatabaseReference nodeuser = databaseReference.child(user.getUid());
                nodeuser.setValue(null);
                signOut();

                Intent d=new Intent(ClassActivity.this,SignINActivity.class);
                d.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(d);
                finish();


                return true;


            default:
                return super.onOptionsItemSelected(item);


        }
















    }
    private void signOut() {
        // Firebase sign out
        FirebaseAuth.getInstance().signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }






    public void updateUI(FirebaseUser fUser) {
//        btnSignOut.setVisibility(View.VISIBLE);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();

        if (account != null) {
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personEmail = account.getEmail();
            String uid = FirebaseAuth.getInstance().getUid();
        }
    }

































}
