package com.example.saksham.Student;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.saksham.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RateWriter extends AppCompatActivity {
    RatingBar ratingstar;
    Button btn_rate;
    EditText mName;
    String currentUser;
    String WriterId;
    DatabaseReference find;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_writer);
        ratingstar= findViewById(R.id.rate);
        btn_rate= findViewById(R.id.submit_rate);
        mName= findViewById(R.id.examWriter_name);

         currentUser= FirebaseAuth.getInstance().getCurrentUser().getUid();
         reference= FirebaseDatabase.getInstance().getReference().child("Saksham").child("Student").child(currentUser);
         WriterId= FirebaseDatabase.getInstance().getReference().child("Saksham").child("Writer").getKey();
         find= FirebaseDatabase.getInstance().getReference().child(WriterId).child("Fname");

        btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name= mName.getText().toString().trim();
                if (name.equals(find))
                {
                    getRating();
                }else {
                    Toast.makeText(RateWriter.this, "No such user exists", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getRating() {
        ratingstar.setVisibility(View.VISIBLE);
        ratingstar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                reference.child("Rating").setValue(v);
                String path= String.valueOf(reference);
                DatabaseReference mWriterRatingDB= FirebaseDatabase.getInstance().getReference().child(WriterId).child("Rating");
                mWriterRatingDB.child(path).setValue(v);
                int rating= (int)v;
                switch (rating){
                    case 1:
                        Toast.makeText(RateWriter.this, "Sorry to hear that!", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(RateWriter.this, "We always accept suggestions!", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(RateWriter.this, "Good Enough!", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(RateWriter.this, "Great! Thank you", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(RateWriter.this, "Excellent! Thank you", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }
}