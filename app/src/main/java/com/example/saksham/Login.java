package com.example.saksham;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    Button btn_login;
    TextView txt_reg;
    EditText eUserName,ePassword,edit_email;
    SliderView sliderView;
    List<ImageSliderModal> imageSliderModalList;
    AwesomeValidation awesomeValidation;
    SharedPreferences sharedPreferences;
    public static final String SHARED_PREF_NAME="mypref";
    public static final String KEY_USERNAME="username";
    public static final String KEY_PASSWORD="password";

    //FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login= findViewById(R.id.login);
        txt_reg= findViewById(R.id.register);
        eUserName= findViewById(R.id.username);
        edit_email= findViewById(R.id.email);
        ePassword= findViewById(R.id.password);
        imageSliderModalList= new ArrayList<>();
        sliderView= findViewById(R.id.imageSlider);
        sharedPreferences= getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        //fAuth= FirebaseAuth.getInstance();

        String name= sharedPreferences.getString(KEY_USERNAME,null);
        if (name != null){
            Intent loginIntent= new Intent(Login.this, Home.class);
            startActivity(loginIntent);
            Toast.makeText(Login.this, "Login done successfully..", Toast.LENGTH_SHORT).show();
        }

        awesomeValidation= new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.username, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.password,".{6,}", R.string.invalid_Password);

        imageSliderModalList.add(new ImageSliderModal(R.drawable.photo));
        imageSliderModalList.add(new ImageSliderModal(R.drawable.map));
        imageSliderModalList.add(new ImageSliderModal(R.drawable.stud));
        imageSliderModalList.add(new ImageSliderModal(R.drawable.happy));
        imageSliderModalList.add(new ImageSliderModal(R.drawable.study));

        sliderView.setSliderAdapter(new ImageSliderAdapter(this,imageSliderModalList));


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_USERNAME, eUserName.getText().toString());
                    editor.putString(KEY_PASSWORD, ePassword.getText().toString());
                    editor.apply();
                    String name = eUserName.getText().toString();
                    String pass = ePassword.getText().toString();
                    if (name.equals("writer") && pass.equals("writer")){
                        Intent loginIntent= new Intent(Login.this, Home.class);
                        startActivity(loginIntent);
                        Toast.makeText(Login.this, "Login done successfully..", Toast.LENGTH_SHORT).show();
                    }else if (name.equals("student") && pass.equals("student")){
                        Intent login= new Intent(Login.this, HomeStud.class);
                        startActivity(login);
                        Toast.makeText(Login.this, "Login done successfully..", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Login.this, "Error occurred during login please check once again!!", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(Login.this, "Error occurred during login please check once again!!", Toast.LENGTH_SHORT).show();
                }
                    /*fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent loginIntent= new Intent(Login.this,Home.class);
                                startActivity(loginIntent);
                                Toast.makeText(Login.this, "Login done successfully..", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(Login.this, "Error occurred during login"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });*/

                }

        });
        txt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent= new Intent(Login.this,RegistrationBtn.class);
                startActivity(regIntent);
            }
        });
    }
}