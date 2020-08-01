package com.example.saksham;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.saksham.Student.HomeStud;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class RequestWriter extends AppCompatActivity {
    Toolbar toolbar;
    EditText firstname,lastname,examName,medPaper,add,school_clg_name,course_name;
    Button Btn_request;
    ImageButton exam;
    TextView date_Exam;

    Calendar c;
    DatePickerDialog pickerDialog;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_writer);
        toolbar = findViewById(R.id.titleToolbar);
        setSupportActionBar(toolbar);

        firstname= findViewById(R.id.fname);
        lastname= findViewById(R.id.lname);
        examName= findViewById(R.id.exam_name);
        medPaper= findViewById(R.id.medium_paper);
        add= findViewById(R.id.exam_add);
        school_clg_name= findViewById(R.id.name_sch_clg);
        course_name= findViewById(R.id.name_course);
        date_Exam= findViewById(R.id.date_exam);
        exam= findViewById(R.id.calender_exam);
        Btn_request= findViewById(R.id.request_writer);

        exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                pickerDialog = new DatePickerDialog(RequestWriter.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        date_Exam.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
                    }
                }, day, month, year);
                pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pickerDialog.show();
            }
        });

        Btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth= FirebaseAuth.getInstance();

                String fname = firstname.getText().toString().trim();
                String lname = lastname.getText().toString().trim();
                String exam = examName.getText().toString().trim();
                String paper = medPaper.getText().toString().trim();
                String address = add.getText().toString().trim();
                String scho_clg = school_clg_name.getText().toString().trim();
                String course = course_name.getText().toString().trim();

                String StudId= auth.getCurrentUser().getUid();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Saksham").child("RequestWriter").child(StudId);
                RequestWriterHelper helper= new RequestWriterHelper(fname,lname,exam,paper,address,scho_clg,course);
                reference.setValue(helper)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(RequestWriter.this, "Request send", Toast.LENGTH_SHORT).show();
                                    Intent regIntent= new Intent(RequestWriter.this, HomeStud.class);
                                    startActivity(regIntent);
                                }
                                else {
                                    Toast.makeText(RequestWriter.this, "Sorry, Something went wrong!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}