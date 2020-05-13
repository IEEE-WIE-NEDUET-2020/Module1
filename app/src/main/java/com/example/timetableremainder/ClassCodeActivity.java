package com.example.timetableremainder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ClassCodeActivity extends AppCompatActivity {

    Button createcode;
    Button enter;
    EditText writecode;
    FirebaseAuth mAuth;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_code);


        createcode = findViewById(R.id.btncreate);
        writecode = findViewById(R.id.codetext);
        enter = findViewById(R.id.enter);



        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        final String userAccount = account.getEmail();
      final   FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("CRS");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String key = dataSnapshot1.getKey();
                    String value = dataSnapshot1.getValue().toString();
                    if (value.equals(userAccount)) {
                        createcode.setVisibility(View.VISIBLE);


                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        createcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                            Intent courseselectorIntent = new Intent(ClassCodeActivity.this, NoOfCoursesActivity.class);
                            courseselectorIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(courseselectorIntent);
                            finish();
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String enteredcode = writecode.getText().toString();
                if(writecode.getText().toString().trim().length()>0){

                   if(createcode.getVisibility()==View.VISIBLE){
                        databaseReference = FirebaseDatabase.getInstance().getReference(enteredcode);
                        databaseReference.child("cr_email").addValueEventListener(new ValueEventListener() {
                      @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String value = String.valueOf(dataSnapshot.getValue());
                                if (value.equals(userAccount)) {
                                    databaseReference=FirebaseDatabase.getInstance().getReference("Users");
                                    DatabaseReference ref=databaseReference.child(user.getUid());
                                    HashMap<String,String> myMap=new HashMap<>();
                                    myMap.put("LoginStatus","true");
                                    myMap.put("email",userAccount);
                                    myMap.put("code",enteredcode);
                                    ref.setValue(myMap);



                                    start(enteredcode);
                            }


                                else{
                                    Toast.makeText(ClassCodeActivity.this, "You are not a CR of this class", Toast.LENGTH_LONG).show();
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });





                    }



                    else{
//                        for simple user


                       databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");
                       DatabaseReference ref=databaseReference.child(user.getUid());
                       HashMap<String,String> myMap=new HashMap<>();
                       myMap.put("LoginStatus","true");
                       myMap.put("email",userAccount);
                       myMap.put("code",enteredcode);
                       ref.setValue(myMap);

                       start(enteredcode);


////                        start();
////                       final String[] awaen=new String[1];
//
//                       databaseReference=FirebaseDatabase.getInstance().getReference().child("Users")
//                       ;
//                       databaseReference.orderByChild("email").equalTo(userAccount).addValueEventListener(new ValueEventListener() {
//                           @Override
//                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                               for(DataSnapshot datas:dataSnapshot.getChildren()) {
//                                   final String keys = datas.getKey();
//                                   awaen[0]=keys;
//
//
//
//                               }
//
//
//                               databaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child(awaen[0]);
//                               HashMap<String,String> myMap=new HashMap<>();
//                               myMap.put("LoginStatus","true");
//                               myMap.put("email",userAccount);
//
//                               databaseReference.setValue(myMap);
//                               start();
//
//
//
//                           }
//
//                           @Override
//                           public void onCancelled(@NonNull DatabaseError databaseError) {
//
//
//                           }
//                       });


                    }

                        }





                else{
                 Toast.makeText(ClassCodeActivity.this,"Enter code",Toast.LENGTH_LONG).show();
                    }













            }
        });



















    }















public void start(String Code){
    Intent myIntent = new Intent(ClassCodeActivity.this, ClassActivity.class);
    myIntent.putExtra("code",Code);
    myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(myIntent);
    finish();
}








}



















