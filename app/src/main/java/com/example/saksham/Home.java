package com.example.saksham;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;


public class Home extends AppCompatActivity {
    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 101;
    private static final int REQUEST_CALL=1;
    private NotificationManagerCompat notificationManager;
    Toolbar toolbar;

    private Adapter adapter[];
    private arrayAdapter arrayAdapter;
    private int i;

    FirebaseAuth firebaseAuth;
    DatabaseReference userdb;

    SwipeFlingAdapterView flingContainer;

    ListView listView;
    List<Adapter> row_items;
     String currentUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar= findViewById(R.id.homeToolbar);
        setSupportActionBar(toolbar);

        notificationManager= NotificationManagerCompat.from(this);
        firebaseAuth= FirebaseAuth.getInstance();
        currentUid= firebaseAuth.getCurrentUser().getUid();
        userdb= FirebaseDatabase.getInstance().getReference().child("Saksham");
        checkUser();

        row_items = new ArrayList<Adapter>();

        arrayAdapter = new arrayAdapter(this, R.layout.item, row_items );
        flingContainer = findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                row_items.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Adapter obj= (Adapter)dataObject;
               final String id= obj.getUserId();
                userdb.child(userStudent).child(id).child("Connection").child("Reject").child(currentUid).setValue(true);
                Toast.makeText(Home.this, "Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Adapter obj= (Adapter)dataObject;
                String id= obj.getUserId();
                userdb.child(userStudent).child(id).child("Connection").child("Accept").child(currentUid).setValue(true);
                isConnectionMatch(id);
                Toast.makeText(Home.this, "Right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(Home.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void isConnectionMatch(String id) {
        DatabaseReference currentUserId= userdb.child(userStudent).child(currentUid).child("Connections").child("Accept").child(id);
        currentUserId.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Toast.makeText(Home.this, "Accepted...", Toast.LENGTH_SHORT).show();
                    userdb.child("Student").child(snapshot.getKey()).child("Connection").child("Matches").child(currentUid).setValue(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                Intent profileIntent = new Intent(Home.this, MyProfile.class);
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
                Toast.makeText(Home.this, "Logout done successfully..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,Login.class));
                firebaseAuth.signOut();
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
        Intent activityIntent= new Intent(Home.this,ListMatches.class);

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

    private String userWriter;
    private String userStudent;
    public void checkUser(){
        final FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference writerDB= FirebaseDatabase.getInstance().getReference().child("Saksham").child("Writer");
        writerDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getKey().equals(user.getUid())){
                    userWriter="Writer";
                    userStudent="Student";
                    getMatch();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference studDB= FirebaseDatabase.getInstance().getReference().child("Saksham").child("Student");
        studDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getKey().equals(user.getUid())){
                    userWriter="Writer";
                    userStudent="Student";
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getMatch() {
        DatabaseReference matchDB= FirebaseDatabase.getInstance().getReference().child("Saksham").child(userStudent);
        matchDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists() && snapshot.child("Connection").child("Reject").hasChild(currentUid) && snapshot.child("Connection").child("Accept").hasChild(currentUid)){
                   Adapter item= new Adapter(snapshot.getKey(),snapshot.child("fname").getValue().toString(),snapshot.child("lname").getValue().toString(),snapshot.child("examname").getValue().toString(),snapshot.child("medium_paper").getValue().toString(),snapshot.child("dateOfExam").getValue().toString(),snapshot.child("venu").getValue().toString(),snapshot.child("college_School_Name").getValue().toString(),snapshot.child("course").getValue().toString(),snapshot.child("profileImageUrl").getValue().toString());
                    row_items.add(item);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}