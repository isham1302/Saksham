package com.example.saksham.Student;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.saksham.R;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class RegisterStud extends AppCompatActivity {
    Toolbar toolbar;
    TextView dob_txt;
    ImageButton dob_btn;
    ImageView profilePicture;
    Button btn_stud;
    EditText eFirstName, eLastName, eUserName, ePhoneNo, edit_email, edit_school, edit_course, ePassword, eCPassword;
    RadioButton male_btn, female_btn;
    String gender = "";
    ProgressBar progressBar;
    Uri resultUri;

    Calendar c;
    DatePickerDialog pickerDialog;
    AwesomeValidation awesomeValidation;

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_stud);
        toolbar = findViewById(R.id.titleToolbar);
        setSupportActionBar(toolbar);

        dob_btn = findViewById(R.id.calender_btn);
        dob_txt = findViewById(R.id.dob);
        btn_stud = findViewById(R.id.register_stud);
        eFirstName = findViewById(R.id.fname);
        eLastName = findViewById(R.id.lname);
        eUserName = findViewById(R.id.username);
        ePhoneNo = findViewById(R.id.phoneNo);
        male_btn = findViewById(R.id.male_btn);
        female_btn = findViewById(R.id.female_btn);
        edit_email = findViewById(R.id.email);
        edit_school = findViewById(R.id.school_college);
        edit_course = findViewById(R.id.course);
        ePassword = findViewById(R.id.password);
        eCPassword = findViewById(R.id.cpassword);
        profilePicture= findViewById(R.id.profile_pic);
        progressBar= findViewById(R.id.spin_kit);

        CubeGrid cubeGrid= new CubeGrid();
        progressBar.setIndeterminateDrawable(cubeGrid);
        progressBar.setVisibility(View.INVISIBLE);

        auth= FirebaseAuth.getInstance();
        listener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                if( user != null){
                    Intent regIntent= new Intent(RegisterStud.this, HomeStud.class);
                    startActivity(regIntent);
                    finish();
                }
            }
        };

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.fname,
                RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.lname, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.username, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.phoneNo, "^[5-9]{1}[0-9]{9}$", R.string.invalid_number);
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.dob, RegexTemplate.NOT_EMPTY, R.string.invalid_date);
        awesomeValidation.addValidation(this, R.id.school_college, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.course, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.password, ".{6,}", R.string.invalid_Password);
        awesomeValidation.addValidation(this, R.id.cpassword, R.id.password, R.string.invalid_cpass);

        dob_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                pickerDialog = new DatePickerDialog(RegisterStud.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        dob_txt.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
                    }
                }, day, month, year);
                pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pickerDialog.show();
            }
        });
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });
        btn_stud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {

                    progressBar.setVisibility(View.VISIBLE);
                    final String fname = eFirstName.getText().toString().trim();
                    final String lname = eLastName.getText().toString().trim();
                    final String username = eUserName.getText().toString().trim();
                    final String dob = dob_txt.getText().toString().trim();
                    final String phone = ePhoneNo.getText().toString().trim();
                    final String email = edit_email.getText().toString().trim();
                    final String scho_clg = edit_school.getText().toString().trim();
                    final String course = edit_course.getText().toString().trim();
                    final String password = ePassword.getText().toString().trim();
                    if (male_btn.isChecked()) {
                        gender = "Male";
                    } else if (female_btn.isChecked()) {
                        gender = "Female";
                    } else {
                        Toast.makeText(RegisterStud.this, "Please choose the gender", Toast.LENGTH_SHORT).show();
                    }auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterStud.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(RegisterStud.this, "Sorry, something went wrong!!", Toast.LENGTH_SHORT).show();
                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                String StudId= auth.getCurrentUser().getUid();

                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Saksham").child("Student").child(StudId);
                                Member_Stud memberStud = new Member_Stud(fname, lname, username, gender, dob, phone, email, scho_clg,course,password);
                                reference.setValue(memberStud)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(RegisterStud.this, "Registration Successful!!", Toast.LENGTH_SHORT).show();
                                                    Intent regIntent = new Intent(RegisterStud.this, HomeStud.class);
                                                    startActivity(regIntent);
                                                } else {
                                                    Toast.makeText(RegisterStud.this, "Sorry, Something went wrong!!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterStud.this, "Sorry, Something went wrong!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== 1){
            if (resultCode== Activity.RESULT_OK){
                Uri imageUri= data.getData();
                profilePicture.setImageURI(imageUri);
            }
        }
    }
}
