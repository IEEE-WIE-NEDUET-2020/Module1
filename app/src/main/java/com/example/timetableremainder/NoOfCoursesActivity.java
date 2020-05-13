package com.example.timetableremainder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NoOfCoursesActivity extends AppCompatActivity {
    int num_of_course = 0;
    EditText semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_of_courses);
        ImageView imgPlus = findViewById(R.id.imageView);
        ImageView imgMinus = findViewById(R.id.imageView2);
        Button Next = findViewById(R.id.button);
        semester=findViewById(R.id.sem);






        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(NoOfCoursesActivity.this, CourseEnteringActivity.class);
                SharedPreferences pref=getSharedPreferences("SaveData", Context.MODE_PRIVATE);

                SharedPreferences.Editor  editor=pref.edit();

                editor.putString("value",semester.getText().toString());
                editor.apply();


                myIntent.putExtra("num_of_subj", num_of_course);












                if (num_of_course == 0) {
                    Toast.makeText(getApplicationContext(), "enter number of subjects", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(myIntent);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    finish();
                }


            }
        });

        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num_of_course++;
                setText();

            }
        });


        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num_of_course > 0) {
                    num_of_course--;
                    setText();
                }

            }
        });


    }


    private void setText() {
        TextView txtNumbers = findViewById(R.id.textView);
        txtNumbers.setText(num_of_course + "");

    }
}
