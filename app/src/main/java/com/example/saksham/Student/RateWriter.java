package com.example.saksham.Student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.saksham.R;
import com.example.saksham.RequestWriter;
import com.example.saksham.RequestWriterHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RateWriter extends AppCompatActivity {
    RatingBar ratingstar;
    Button btn_rate;
    EditText mName,mReview;
    TextView rate;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_writer);
        ratingstar = findViewById(R.id.rate);
        btn_rate = findViewById(R.id.submit_rate);
        mName = findViewById(R.id.examWriter_name);
        rate= findViewById(R.id.rate_us);
        mReview= findViewById(R.id.review);
        btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth= FirebaseAuth.getInstance();
                String s= String.valueOf(ratingstar.getRating());
                rate.setText(s);
                String fullname = mName.getText().toString().trim();
                String star = rate.getText().toString().trim();
                String review = mReview.getText().toString().trim();
                String Id= auth.getCurrentUser().getUid();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Saksham").child("Writer").child("Rating").child(Id);
                RateUsModal modal= new RateUsModal(fullname,review,star);
                reference.setValue(modal).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RateWriter.this, "Your review is been submitted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(RateWriter.this, "Sorry, Something went wrong!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

}