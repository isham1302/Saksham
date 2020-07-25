package com.example.saksham;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeStud extends AppCompatActivity {
    EditText eFirstName, eLastName, eUserName, ePhoneNo, edit_email, edit_school, edit_course, ePassword, eCPassword;
    TextView dob_txt;
    RadioButton male_btn, female_btn;
    GridView gridView;
    ImageButton profilePic;
    private NotificationManagerCompat notificationManager;

    String gender = "";

    DatabaseReference reference;
    FirebaseAuth auth;

    String[] topic= {"Request Writer","My Profile","Notification","Rate Writer","Contact Us","Logout"};
    int[] topicImage={R.drawable.writer, R.drawable.profile, R.drawable.notification, R.drawable.rate, R.drawable.contact, R.drawable.logout};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_stud);
        gridView= findViewById(R.id.grid_view);
        notificationManager= NotificationManagerCompat.from(this);

        auth= FirebaseAuth.getInstance();

        dob_txt = findViewById(R.id.dob);
        eFirstName = findViewById(R.id.fname);
        eLastName = findViewById(R.id.lname);
        eUserName = findViewById(R.id.username);
        ePhoneNo = findViewById(R.id.phoneNo);
        male_btn = findViewById(R.id.male_btn);
        female_btn = findViewById(R.id.female_btn);
        edit_email = findViewById(R.id.email);
        edit_school = findViewById(R.id.school_college);
        edit_course = findViewById(R.id.course);
        ePassword = findViewById(R.id.password);
        eCPassword = findViewById(R.id.cpassword);
        profilePic= findViewById(R.id.profile_pic);

        MainAdapter adapter= new MainAdapter(HomeStud.this,topic,topicImage);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"You Clicked"+topic[+i],Toast.LENGTH_SHORT).show();
                if (topic[i].equals("Request Writer")){
                    Intent requestWriter= new Intent(HomeStud.this,RequestWriter.class);
                    startActivity(requestWriter);
                }
                if (topic[i].equals("My Profile")){
                    reference= FirebaseDatabase.getInstance().getReference().child("Registration student").child("1");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                           final String stud_firstName= snapshot.child("firstName").getValue().toString();
                            final String stud_lastName= snapshot.child("lastName").getValue().toString();
                            final String stud_username= snapshot.child("username").getValue().toString();
                            final String stud_phoneNo= snapshot.child("phoneNo").getValue().toString();
                            final String stud_email= snapshot.child("email").getValue().toString();
                            final String stud_dob= snapshot.child("dob").getValue().toString();
                            final String stud_gender= snapshot.child("gender").getValue().toString();
                            final String stud_schoolCollegeName= snapshot.child("school_College_Name").getValue().toString();
                            final String stud_course= snapshot.child("course").getValue().toString();
                            final  String stud_pic= snapshot.child("profilePic").getValue().toString();
                            Intent profileIntent= new Intent(HomeStud.this,ProfileStud.class);
                           profileIntent.putExtra("firstName",stud_firstName);
                            profileIntent.putExtra("lastName",stud_lastName);
                            profileIntent.putExtra("username",stud_username);
                            profileIntent.putExtra("phoneNo",stud_phoneNo);
                            profileIntent.putExtra("email",stud_email);
                            profileIntent.putExtra("dob",stud_dob);
                            profileIntent.putExtra("gender",stud_gender);
                            profileIntent.putExtra("school_College_Name",stud_schoolCollegeName);
                            profileIntent.putExtra("course",stud_course);
                            profileIntent.putExtra("profilePic",stud_pic);

                            startActivity(profileIntent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    }
                if (topic[i].equals("Notification")){
                    PopupNotification();
                }
                if (topic[i].equals("Rate Writer")){
                    Intent rateWriter= new Intent(HomeStud.this,RateWriter.class);
                    startActivity(rateWriter);
                }
                if (topic[i].equals("Contact Us")){
                    Intent contact= new Intent(HomeStud.this,ContactUs.class);
                    startActivity(contact);
                }
                if (topic[i].equals("Logout")){
                    Toast.makeText(HomeStud.this, "Logout done successfully..", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HomeStud.this,LoginStud.class));
                    auth.signOut();
                    finish();
                }

            }
        });
    }
    private void PopupNotification() {
        Intent activityIntent= new Intent(HomeStud.this, ListNotification.class);
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
}
