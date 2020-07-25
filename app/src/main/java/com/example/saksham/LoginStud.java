package com.example.saksham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class LoginStud extends AppCompatActivity {
    Button btn_login;
    TextView txt_reg,txt_fpass;
    EditText ePassword,edit_email;
    SliderView sliderView;
    List<ImageSliderModal> imageSliderModalList;
    AwesomeValidation awesomeValidation;

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_stud);

        if (!isConnected(LoginStud.this))builDialog(LoginStud.this).show();

        btn_login= findViewById(R.id.login);
        txt_reg= findViewById(R.id.register);
        edit_email= findViewById(R.id.email_add);
        ePassword= findViewById(R.id.password);
        txt_fpass= findViewById(R.id.fpassword);
        imageSliderModalList= new ArrayList<>();
        sliderView= findViewById(R.id.imageSlider);


        awesomeValidation= new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.email_add, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.password,".{6,}", R.string.invalid_Password);

        imageSliderModalList.add(new ImageSliderModal(R.drawable.photo));
        imageSliderModalList.add(new ImageSliderModal(R.drawable.map));
        imageSliderModalList.add(new ImageSliderModal(R.drawable.stud));
        imageSliderModalList.add(new ImageSliderModal(R.drawable.happy));
        imageSliderModalList.add(new ImageSliderModal(R.drawable.study));

        sliderView.setSliderAdapter(new ImageSliderAdapter(this,imageSliderModalList));

        auth= FirebaseAuth.getInstance();
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(LoginStud.this, HomeStud.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {

                    String email = edit_email.getText().toString();
                    String pass = ePassword.getText().toString();
                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(LoginStud.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginStud.this, "sign in error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(LoginStud.this, "Error occurred during login please check once again!!", Toast.LENGTH_SHORT).show();
                }

            }

        });
        txt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent= new Intent(LoginStud.this,RegisterStud.class);
                startActivity(regIntent);
            }
        });
        txt_fpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginStud.this,ForgotPasswordStud.class);
                startActivity(intent);
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
    public boolean isConnected(Context context){
        ConnectivityManager cm= (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            android.net.NetworkInfo mobile= cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            android.net.NetworkInfo wifi= cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mobile != null && mobile.isConnectedOrConnecting() || wifi != null && wifi.isConnectedOrConnecting()){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public AlertDialog.Builder builDialog(Context c){
        AlertDialog.Builder builder= new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please turn on Mobile Data or Wifi Connection.");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        return builder;
        }
    }
