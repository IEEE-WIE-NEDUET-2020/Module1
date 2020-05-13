package com.example.timetableremainder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseEnteringActivity extends AppCompatActivity {
    private LinearLayout lnrDynamicEditTextHolder;
    DatabaseReference databaseReference ;
    Button gotonext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_entering);
        gotonext=findViewById(R.id.go);


        lnrDynamicEditTextHolder = (LinearLayout) findViewById(R.id.EditTextHolder);
        final Intent mIntent = getIntent();
        final int num_of_subj = mIntent.getIntExtra("num_of_subj", 0);
        final String problem=mIntent.getStringExtra("sem");






//        databaseReference.child(code);
       final List<EditText> allText= new ArrayList<EditText>();



        for (int i=0; i <num_of_subj; i++){
            EditText editText = new EditText(CourseEnteringActivity.this);
            allText.add(editText);
            editText.setId(i);
            editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            allText.add(editText);
            lnrDynamicEditTextHolder.addView(editText);
        }



        gotonext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                final String userAccount=account.getEmail();
                databaseReference = FirebaseDatabase.getInstance().getReference();
                String code =databaseReference.push().getKey();
                databaseReference=FirebaseDatabase.getInstance().getReference(code);
                CRemail cr=new CRemail(userAccount);
                cr.setCR_email(userAccount);
                databaseReference.setValue(cr);
                databaseReference=FirebaseDatabase.getInstance().getReference(code).child("Subjects");

                 int i=0;

                while(i<num_of_subj *2){

                    Courses masla = new Courses(allText.get(i).getText().toString());
                    masla.setCourse(allText.get(i).getText().toString());
                    String id = databaseReference.push().getKey();
                    databaseReference.child(id).setValue(masla);

                    i=i+2;






                }

                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                databaseReference=FirebaseDatabase.getInstance().getReference("Users");
                DatabaseReference ref=databaseReference.child(user.getUid());
                HashMap<String,String> myMap=new HashMap<>();
                myMap.put("LoginStatus","true");
                myMap.put("email",userAccount);
                myMap.put("code",code);
                ref.setValue(myMap);

                SharedPreferences result =getSharedPreferences("SaveData", Context.MODE_PRIVATE);
                String value=result.getString("value","Data not Found");

                databaseReference=FirebaseDatabase.getInstance().getReference().child("ClassCode").child(user.getUid());


                String unique=databaseReference.push().getKey();


                HashMap<String,String> codemap= new HashMap<>();
                codemap.put(value,code);



                databaseReference.child(unique).setValue(codemap);


                Intent codeintent = new Intent(CourseEnteringActivity.this, CodeSharingActivity.class);
                codeintent.putExtra("Code", code);




                codeintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(codeintent);
                finish();










            }
        });







    }
}
