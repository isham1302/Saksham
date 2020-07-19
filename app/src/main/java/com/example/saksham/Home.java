package com.example.saksham;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 101;
    private static final int REQUEST_CALL=1;
    private NotificationManagerCompat notificationManager;
    Toolbar toolbar;
    Button apply;
    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    SharedPreferences sharedPreferences;
    public static final String SHARED_PREF_NAME="mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar= findViewById(R.id.homeToolbar);
        setSupportActionBar(toolbar);

        apply= findViewById(R.id.btn_apply);
        sharedPreferences= getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        notificationManager= NotificationManagerCompat.from(this);

        models= new ArrayList<>();
        models.add(new Model(R.drawable.woman,"Name:Pooja Sharma","Medium of Paper:English"+"\n"+"Exam:Maths"));
        models.add(new Model(R.drawable.woman,"Name:Pooja Sharma","Medium of Paper:English"+"\n"+"Exam:Social Science"));
       models.add(new Model(R.drawable.woman,"Name:Pooja Sharma","Medium of Paper:English"+"\n"+"Exam:Science"));
        models.add(new Model(R.drawable.woman,"Name:Pooja Sharma","Medium of Paper:English"+"\n"+"Exam:Social Science"));
        models.add(new Model(R.drawable.woman,"Name:Pooja Sharma","Medium of Paper:English"+"\n"+"Exam:Social Science"));

        adapter= new Adapter(models, this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(110,15,130,0);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position =viewPager.getCurrentItem();
                Toast.makeText(Home.this,position, Toast.LENGTH_SHORT).show();
                viewPager.setBackground(Drawable.createFromPath("#000"));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "My Profile", Toast.LENGTH_SHORT).show();
                Intent profileIntent= new Intent(Home.this, MyProfile.class);
                startActivity(profileIntent);
                return true;
            case R.id.item2:
                Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show();
                PopupNotification();
                return true;
            case R.id.item3:
                Toast.makeText(this, "Contact Us", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item4:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.clear();
                editor.commit();
                finish();
                Toast.makeText(Home.this, "Logout done successfully..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,Login.class));
                finish();
                return true;
            case R.id.subitem1:
                Toast.makeText(getApplicationContext(), "Make a call", Toast.LENGTH_SHORT).show();
                makePhoneCall();
                    return true;
            case R.id.subitem2:
                Toast.makeText(getApplicationContext(), "Text Us", Toast.LENGTH_SHORT).show();
                textSMS();
                return true;
            case R.id.subitem3:
                String recipient="isham1302@gmail.com";
                String subject="Register enquiry";
                String message="Hi!! How may I help you.";
                Toast.makeText(getApplicationContext(), "Mail Us", Toast.LENGTH_SHORT).show();
                sendMail(recipient,subject,message);
                return true;
            case R.id.subitem4:
                Toast.makeText(getApplicationContext(), "WhatsApp Us", Toast.LENGTH_SHORT).show();
                boolean installed= appInstalledOrNot("com.whatsapp");
                if (installed){
                    Intent whatsIntent= new Intent(Intent.ACTION_VIEW);
                    whatsIntent.setData(Uri.parse("http://api.whatsapp.com/send?phone"+"+9723555107"+"&text="+"Hi!! How may I help you"));
                    startActivity(whatsIntent);
                }else {
                    Toast.makeText(this, "Something went wrong..", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void PopupNotification() {
        Intent activityIntent= new Intent(Home.this,ListNotification.class);
        PendingIntent contentIntent= PendingIntent.getActivity(this,0,activityIntent,0);
        Intent broadcastIntent= new Intent(this,NotificationReceiver.class);
        broadcastIntent.putExtra("ToastMessage","This is message");
        PendingIntent actionIntent= PendingIntent.getBroadcast(this,0,broadcastIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        android.app.Notification notification= new NotificationCompat.Builder(this, Notification.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("This the new request")
                .setContentText("Please check the details of examination informed by student")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.ic_launcher,"Toast", actionIntent)
                .build();
        notificationManager.notify(1,notification);
    }

    private void textSMS() {
        if (ContextCompat.checkSelfPermission(Home.this,Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }else {
            SmsManager smsManager= SmsManager.getDefault();
            smsManager.sendTextMessage("9723555107",null,"Hi!! How may I help You",null,null);
        }
    }

    private void makePhoneCall() {
        if (ContextCompat.checkSelfPermission(Home.this,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:9723555107"));
            startActivity(callIntent);
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
        if (requestCode == SEND_SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                textSMS();
            } else {
                Toast.makeText(this, "Permission Denied..", Toast.LENGTH_SHORT).show();
            }
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
    private boolean appInstalledOrNot(String url) {
        PackageManager packageManager= getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed=true;
        }catch (PackageManager.NameNotFoundException e){
            app_installed=false;
        }
        return app_installed;
    }
}