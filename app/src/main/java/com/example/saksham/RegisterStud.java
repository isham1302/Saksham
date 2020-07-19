package com.example.saksham;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.Calendar;

public class RegisterStud extends AppCompatActivity {
    Toolbar toolbar;
    TextView dob_txt;
    ImageButton dob_btn;
    Button btn_stud;
    EditText eFirstName,eLastName,eUserName,ePhoneNo,edit_email,ePassword,eCPassword;

    Calendar c;
    DatePickerDialog pickerDialog;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_stud);
        toolbar= findViewById(R.id.titleToolbar);
        setSupportActionBar(toolbar);
        dob_btn= findViewById(R.id.calender_btn);
        dob_txt= findViewById(R.id.dob);
        btn_stud= findViewById(R.id.register_stud);
        eFirstName= findViewById(R.id.fname);
        eLastName= findViewById(R.id.lname);
        eUserName= findViewById(R.id.username);
        ePhoneNo= findViewById(R.id.phoneNo);
        edit_email= findViewById(R.id.email);
        ePassword= findViewById(R.id.password);
        eCPassword= findViewById(R.id.cpassword);

        awesomeValidation= new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.fname,
                RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.lname,RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.username,RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.phoneNo,"^[5-9]{1}[0-9]{9}$", R.string.invalid_number);
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.dob,RegexTemplate.NOT_EMPTY, R.string.invalid_date);
        awesomeValidation.addValidation(this, R.id.password,".{6,}", R.string.invalid_Password);
        awesomeValidation.addValidation(this, R.id.cpassword, R.id.password, R.string.invalid_cpass);

        dob_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c= Calendar.getInstance();
                int day= c.get(Calendar.DAY_OF_MONTH);
                int month= c.get(Calendar.MONTH);
                int year= c.get(Calendar.YEAR);
                pickerDialog= new DatePickerDialog(RegisterStud.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        dob_txt.setText(mDay+"/"+(mMonth+1)+"/"+mYear);
                    }
                },day,month,year);
                pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pickerDialog.show();
            }
        });
        btn_stud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()){
                    Toast.makeText(RegisterStud.this, "Registration done successfully..", Toast.LENGTH_SHORT).show();
                    Intent regIntent= new Intent(RegisterStud.this, Login.class);
                    startActivity(regIntent);

                }else {
                    Toast.makeText(RegisterStud.this, "Error occurred during registration please check once again!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}