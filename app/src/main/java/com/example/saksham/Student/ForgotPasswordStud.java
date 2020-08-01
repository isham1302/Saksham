package com.example.saksham.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.chaos.view.PinView;
import com.example.saksham.R;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class ForgotPasswordStud extends AppCompatActivity {
    EditText txt_fpass;
    Button btn_otp;
    PinView pinView;
    ProgressBar progressBar;

    String codeSent;

    AwesomeValidation awesomeValidation;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_stud);
        txt_fpass= findViewById(R.id.phoneNo);
        btn_otp= findViewById(R.id.otp);
        pinView= findViewById(R.id.pin_view);
        progressBar= findViewById(R.id.spin_kit);

        CubeGrid cubeGrid= new CubeGrid();
        progressBar.setIndeterminateDrawable(cubeGrid);
        progressBar.setVisibility(View.INVISIBLE);

        mAuth= FirebaseAuth.getInstance();


        awesomeValidation= new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.otp, "^[5-9]{1}[0-9]{9}$", R.string.invalid_number);
        btn_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()){
                    progressBar.setVisibility(View.VISIBLE);
                    Query checkUser= FirebaseDatabase.getInstance().getReference().child("Saksham").child("Student").orderByChild("phone").equalTo(String.valueOf(txt_fpass));
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                getVerificationCode();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(ForgotPasswordStud.this, "No such User exists", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void codeVerification() {
        String code= pinView.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(ForgotPasswordStud.this, "Successfully Verified ", Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(ForgotPasswordStud.this, HomeStud.class);
                            startActivity(intent);
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(ForgotPasswordStud.this, "Sorry!! Something went wrong", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(ForgotPasswordStud.this, "You entered wrong OTP", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void getVerificationCode() {
        final String mobileNo= txt_fpass.getText().toString().trim();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobileNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                ForgotPasswordStud.this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            String code= credential.getSmsCode();
            if (code != null){
                pinView.setText(code);
                codeVerification();
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                Toast.makeText(ForgotPasswordStud.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            } else if (e instanceof FirebaseTooManyRequestsException) {

            }


        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            codeSent= verificationId;
        }
    };


}