package com.example.saksham;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Registration extends AppCompatActivity {

    private static final String TAG = "Registration Writer";
    Toolbar toolbar;
    TextView dob_txt;
    ImageButton dob_btn;
    Button btn_writer;
    Spinner spinner, workSpinner, underSpinner, postSpinner, schoolSpinner;
    EditText eFirstName, eLastName, eUserName, ePhoneNo, edit_email, ePassword, eCPassword;

    Calendar c;
    DatePickerDialog pickerDialog;
    AwesomeValidation awesomeValidation;

    public static final String KEY_FNAME = "first name";
    public static final String KEY_LNAME = "last name";
    public static final String KEY_USERNAME = "user name";
    public static final String KEY_PHONE_NO = "mobile number";
    public static final String KEY_EMAIL = "email id";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_DOB = "date of birth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        toolbar = findViewById(R.id.titleToolbar);
        setSupportActionBar(toolbar);


        dob_btn = findViewById(R.id.calender_btn);
        dob_txt = findViewById(R.id.dob);
        btn_writer = findViewById(R.id.register_writer);
        spinner = findViewById(R.id.spinner);
        schoolSpinner = findViewById(R.id.spinner_school);
        underSpinner = findViewById(R.id.spinner_undergraduation);
        postSpinner = findViewById(R.id.spinner_postgraduation);
        workSpinner = findViewById(R.id.spinner_work);
        eFirstName = findViewById(R.id.fname);
        eLastName = findViewById(R.id.lname);
        eUserName = findViewById(R.id.username);
        ePhoneNo = findViewById(R.id.phoneNo);
        edit_email = findViewById(R.id.email);
        ePassword = findViewById(R.id.password);
        eCPassword = findViewById(R.id.cpassword);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.fname,
                RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.lname, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.username, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.phoneNo, "^[5-9]{1}[0-9]{9}$", R.string.invalid_number);
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.dob, RegexTemplate.NOT_EMPTY, R.string.invalid_date);
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
                pickerDialog = new DatePickerDialog(Registration.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
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
                    String fname = eFirstName.getText().toString().trim();
                    String lname = eLastName.getText().toString().trim();
                    String username = eUserName.getText().toString().trim();
                    String dob = dob_txt.getText().toString().trim();
                    String phone = ePhoneNo.getText().toString().trim();
                    String email = edit_email.getText().toString().trim();
                    String pass = ePassword.getText().toString().trim();
                    Map<String, Object> data = new HashMap<>();
                    data.put(KEY_FNAME, fname);
                    data.put(KEY_LNAME, lname);
                    data.put(KEY_USERNAME, username);
                    data.put(KEY_DOB, dob);
                    data.put(KEY_PHONE_NO, phone);
                    data.put(KEY_EMAIL, email);
                    data.put(KEY_PASSWORD, pass);


                    Toast.makeText(Registration.this, "Registration done successfully..", Toast.LENGTH_SHORT).show();
                    Intent regIntent = new Intent(Registration.this, Login.class);
                    startActivity(regIntent);
                }else {
                    Toast.makeText(Registration.this, "Error occurred during registration please check once again!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}