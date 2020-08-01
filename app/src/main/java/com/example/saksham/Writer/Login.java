

package com.example.saksham.Writer;

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
        import android.widget.ProgressBar;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;

        import com.basgeekball.awesomevalidation.AwesomeValidation;
        import com.basgeekball.awesomevalidation.ValidationStyle;
        import com.example.saksham.ImageSliderAdapter;
        import com.example.saksham.ImageSliderModal;
        import com.example.saksham.R;
        import com.github.ybq.android.spinkit.style.CubeGrid;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.smarteist.autoimageslider.SliderView;

        import java.util.ArrayList;
        import java.util.List;

public class Login extends AppCompatActivity {

    Button btn_login;
    TextView txt_reg,txt_fpass;
    EditText ePassword,edit_email;
    SliderView sliderView;
    List<ImageSliderModal> imageSliderModalList;
    AwesomeValidation awesomeValidation;
    ProgressBar progressBar;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (!isConnected(Login.this))buildDialog(Login.this).show();

        btn_login= findViewById(R.id.login);
        txt_reg= findViewById(R.id.register);
        edit_email= findViewById(R.id.emailId);
        ePassword= findViewById(R.id.password);
        txt_fpass= findViewById(R.id.fpassword);
        imageSliderModalList= new ArrayList<>();
        sliderView= findViewById(R.id.imageSlider);
        progressBar= findViewById(R.id.spin_kit);

        CubeGrid cubeGrid= new CubeGrid();
        progressBar.setIndeterminateDrawable(cubeGrid);
        progressBar.setVisibility(View.INVISIBLE);


        awesomeValidation= new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.emailId, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.password,".{6,}", R.string.invalid_Password);

        imageSliderModalList.add(new ImageSliderModal(R.drawable.photo));
        imageSliderModalList.add(new ImageSliderModal(R.drawable.map));
        imageSliderModalList.add(new ImageSliderModal(R.drawable.stud));
        imageSliderModalList.add(new ImageSliderModal(R.drawable.happy));
        imageSliderModalList.add(new ImageSliderModal(R.drawable.study));

        sliderView.setSliderAdapter(new ImageSliderAdapter(this,imageSliderModalList));

        mAuth= FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(Login.this, Home.class);
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

                    progressBar.setVisibility(View.VISIBLE);
                    String email = edit_email.getText().toString();
                    String pass = ePassword.getText().toString();
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(Login.this, "sign in error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Login.this, "Error occurred during login please check once again!!", Toast.LENGTH_SHORT).show();
                }

            }

        });
        txt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent= new Intent(Login.this, RegistrationWriter.class);
                startActivity(regIntent);
            }
        });
        txt_fpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
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

    public AlertDialog.Builder buildDialog(Context c){
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