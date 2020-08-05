package com.example.saksham.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.saksham.R;
import com.example.saksham.Writer.ForgotPassword;
import com.example.saksham.Writer.PasswordChange;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class ForgotPasswordStud extends AppCompatActivity {
    EditText txt_fpass;
    Button btn_otp;
     FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_stud);
        txt_fpass= findViewById(R.id.phoneNo);
        btn_otp= findViewById(R.id.otp);

        mAuth= FirebaseAuth.getInstance();

        btn_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = txt_fpass.getText().toString().trim();
                if(mobile.isEmpty() || mobile.length() < 10){
                    Toast.makeText(ForgotPasswordStud.this, "Please enter the valid number", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(ForgotPasswordStud.this, OTP.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
                }
        });
    }

   }