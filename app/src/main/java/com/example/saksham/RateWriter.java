package com.example.saksham;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RateWriter extends AppCompatActivity {
    RatingBar ratingstar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_writer);
        ratingstar= findViewById(R.id.rate);
        ratingstar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
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