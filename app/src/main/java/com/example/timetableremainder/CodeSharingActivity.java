package com.example.timetableremainder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class CodeSharingActivity extends AppCompatActivity {
    TextView codeshow;

    String codeKey;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_sharing);
        Intent mIntent = getIntent();
        final String codeKey=mIntent.getStringExtra("Code");
        codeshow=findViewById(R.id.code);
        codeshow.setText(codeKey);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);




    }



    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        menu.findItem(R.id.share).setVisible(true);
        menu.findItem(R.id.sign_out).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:

                Intent mIntent = getIntent();
                final String codeKey = mIntent.getStringExtra("Code");


                Intent intentshare = new Intent(Intent.ACTION_SEND);
                intentshare.setType("text/plane");


                intentshare.putExtra(intentshare.EXTRA_TEXT, codeKey);
                startActivity(intentshare.createChooser(intentshare, " Code Share"));
                return true;


            case R.id.sign_out:
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//

                databaseReference = FirebaseDatabase.getInstance().getReference().getRoot().child("Users");
                DatabaseReference nodeuser = databaseReference.child(user.getUid());
                nodeuser.setValue(null);
                signOut();

                Intent n=new Intent(CodeSharingActivity.this,SignINActivity.class);
                n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP   |  Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(n);
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



