package com.example.saksham.Writer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saksham.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    EditText txt_fpass;
    Button btn_otp;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btn_otp= findViewById(R.id.otp);
        txt_fpass= findViewById(R.id.phoneNo);

        mAuth= FirebaseAuth.getInstance();

        btn_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = txt_fpass.getText().toString().trim();
                if(mobile.isEmpty() || mobile.length() < 10){
                    Toast.makeText(ForgotPassword.this, "Please enter the valid number", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(ForgotPassword.this, PasswordChange.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
            }
        });
    }


}