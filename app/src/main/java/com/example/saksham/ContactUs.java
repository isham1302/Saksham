package com.example.saksham;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ContactUs extends AppCompatActivity {
    ImageButton btn_call, btn_mail;

    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        btn_call = findViewById(R.id.call);
        btn_mail = findViewById(R.id.mail);

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }

        });

        btn_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipient="isham1302@gmail.com";
                String subject="Register enquiry";
                String message="Hi!! How may I help you.";
                Toast.makeText(getApplicationContext(), "Mail Us", Toast.LENGTH_SHORT).show();
                sendMail(recipient,subject,message);
            }
        });
    }

    private void makePhoneCall() {
        if (ContextCompat.checkSelfPermission(ContactUs.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ContactUs.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:9723555107"));
            startActivity(callIntent);

        }
    }
    private void sendMail(String recipient, String subject, String message) {
        Intent mailIntent= new Intent(Intent.ACTION_SEND);
        mailIntent.setData(Uri.parse("mailto:"));
        mailIntent.setType("text/plain");
        mailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{recipient});
        mailIntent.putExtra(Intent.EXTRA_SUBJECT,subject);
        mailIntent.putExtra(Intent.EXTRA_TEXT,message);
        try {
            startActivity(mailIntent);
        }catch (Exception e){
            Toast.makeText(this, "Something went wrong..", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission Denied..", Toast.LENGTH_SHORT).show();
            }
        }
    }
}