package com.example.saksham.Writer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class RegistrationWriter extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Toolbar toolbar;
    TextView dob_txt, lang_txt, school_txt, ug_txt, pg_txt, work_txt;
    ImageButton dob_btn, profilePic;
    Button btn_writer;
    Spinner spinner, workSpinner, underSpinner, postSpinner, schoolSpinner;
    EditText eFirstName, eLastName, eUserName, ePhoneNo, edit_email, ePassword, eCPassword;
    CheckBox eng, hindi, guj;
    RadioButton male, female;
    String gender = "";
    String Language = "";
    ProgressBar progressBar;

    Calendar c;
    DatePickerDialog pickerDialog;
    AwesomeValidation awesomeValidation;
    String item1, item2, item3, item4, item5;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_writer);
        toolbar = findViewById(R.id.titleToolbar);
        setSupportActionBar(toolbar);

        dob_btn = findViewById(R.id.calender_btn);
        dob_txt = findViewById(R.id.dob);
        school_txt = findViewById(R.id.spin_school);
        ug_txt = findViewById(R.id.spin_ug);
        pg_txt = findViewById(R.id.spin_pg);
        work_txt = findViewById(R.id.spin_work);
        btn_writer = findViewById(R.id.register_writer);
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        schoolSpinner = findViewById(R.id.spinner_school);
        schoolSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        underSpinner = findViewById(R.id.spinner_undergraduation);
        underSpinner.setOnItemSelectedListener(this);
        postSpinner = findViewById(R.id.spinner_postgraduation);
        postSpinner.setOnItemSelectedListener(this);
        workSpinner = findViewById(R.id.spinner_work);
        workSpinner.setOnItemSelectedListener(this);
        eFirstName = findViewById(R.id.fname);
        eLastName = findViewById(R.id.lname);
        eUserName = findViewById(R.id.username);
        ePhoneNo = findViewById(R.id.phoneNo);
        edit_email = findViewById(R.id.email);
        ePassword = findViewById(R.id.password);
        eCPassword = findViewById(R.id.cpassword);
        profilePic = findViewById(R.id.profileImage);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        lang_txt = findViewById(R.id.language);
        eng = findViewById(R.id.lang_eng);
        hindi = findViewById(R.id.lang_hind);
        guj = findViewById(R.id.lang_guj);
        progressBar= findViewById(R.id.spin_kit);

        CubeGrid cubeGrid= new CubeGrid();
        progressBar.setIndeterminateDrawable(cubeGrid);
        progressBar.setVisibility(View.INVISIBLE);

        mAuth= FirebaseAuth.getInstance();
        listener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                if( user != null){
                    Intent regIntent= new Intent(RegistrationWriter.this, Home.class);
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
        awesomeValidation.addValidation(this, R.id.language, RegexTemplate.NOT_EMPTY, R.string.invalid_lang);
        awesomeValidation.addValidation(this, R.id.spin_school, RegexTemplate.NOT_EMPTY, R.string.invalid_lang);
        awesomeValidation.addValidation(this, R.id.spin_ug, RegexTemplate.NOT_EMPTY, R.string.invalid_lang);
        awesomeValidation.addValidation(this, R.id.spin_pg, RegexTemplate.NOT_EMPTY, R.string.invalid_lang);
        awesomeValidation.addValidation(this, R.id.spin_work, RegexTemplate.NOT_EMPTY, R.string.invalid_lang);
        awesomeValidation.addValidation(this, R.id.password, ".{6,}", R.string.invalid_Password);
        awesomeValidation.addValidation(this, R.id.cpassword, R.id.password, R.string.invalid_cpass);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.language, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapterS = ArrayAdapter.createFromResource(this, R.array.school, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schoolSpinner.setAdapter(adapterS);

        ArrayAdapter<CharSequence> adapterU = ArrayAdapter.createFromResource(this, R.array.undergradutation, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        underSpinner.setAdapter(adapterU);

        ArrayAdapter<CharSequence> adapterP = ArrayAdapter.createFromResource(this, R.array.postgradutaion, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        postSpinner.setAdapter(adapterP);

        ArrayAdapter<CharSequence> adapterW = ArrayAdapter.createFromResource(this, R.array.workProfile, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workSpinner.setAdapter(adapterW);

        dob_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                pickerDialog = new DatePickerDialog(RegistrationWriter.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        dob_txt.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
                    }
                }, day, month, year);
                pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pickerDialog.show();
            }
        });


        btn_writer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {

                    progressBar.setVisibility(View.VISIBLE);
                    final String fname = eFirstName.getText().toString().trim();
                    final String lname = eLastName.getText().toString().trim();
                    final String username = eUserName.getText().toString().trim();
                    final String dob = dob_txt.getText().toString().trim();
                    final String lang = lang_txt.getText().toString().trim();
                    final String school = school_txt.getText().toString().trim();
                    final String ug = ug_txt.getText().toString().trim();
                    final String pg = pg_txt.getText().toString().trim();
                    final String work = work_txt.getText().toString().trim();
                    final String phone = ePhoneNo.getText().toString().trim();
                    final String email = edit_email.getText().toString().trim();
                    final String pass = ePassword.getText().toString().trim();

                    if (male.isChecked()) {
                        gender = "Male";

                    } else if (female.isChecked()) {
                        gender = "Female";
                    } else {
                        Toast.makeText(RegistrationWriter.this, "Please Select The Gender", Toast.LENGTH_SHORT).show();
                    }
                    if (eng.isChecked()) {
                        Language = "English";
                    } else if (hindi.isChecked()) {
                        Language = "Hindi";
                    } else if (guj.isChecked()) {
                        Language = "Gujarati";
                    } else {
                        Toast.makeText(RegistrationWriter.this, "Please Select The Language", Toast.LENGTH_SHORT).show();
                    }
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(RegistrationWriter.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(RegistrationWriter.this, "Sorry, something went wrong!!", Toast.LENGTH_SHORT).show();
                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                String WriterId= mAuth.getCurrentUser().getUid();
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Saksham").child("Writer").child(WriterId);
                                Member member = new Member(fname, lname, username, gender, dob, phone, school ,ug,pg,work,email,pass,Language, lang);
                                reference.setValue(member)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(RegistrationWriter.this, "Registration Successful!!", Toast.LENGTH_SHORT).show();
                                                    Intent regIntent = new Intent(RegistrationWriter.this, Home.class);
                                                    startActivity(regIntent);
                                                } else {
                                                    Toast.makeText(RegistrationWriter.this, "Sorry, Something went wrong!!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                            }
                        }
                    });

                }
            }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item1 = spinner.getSelectedItem().toString();
        lang_txt.setText(item1);
        item2 = schoolSpinner.getSelectedItem().toString();
        school_txt.setText(item2);
        item3 = underSpinner.getSelectedItem().toString();
        ug_txt.setText(item3);
        item4 = postSpinner.getSelectedItem().toString();
        pg_txt.setText(item4);
        item5 = workSpinner.getSelectedItem().toString();
        work_txt.setText(item5);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Please select the above options..", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(listener);
    }
}