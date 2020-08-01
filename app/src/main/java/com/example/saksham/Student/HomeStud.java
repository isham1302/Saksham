package com.example.saksham.Student;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.saksham.MainAdapter;
import com.example.saksham.R;
import com.example.saksham.RequestWriter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class HomeStud extends AppCompatActivity {
    EditText eFirstName, eLastName, eUserName, ePhoneNo, edit_email, edit_school, edit_course, ePassword, eCPassword;
    TextView dob_txt;
    RadioButton male_btn, female_btn;
    GridView gridView;
    ImageButton profilePic;
    private NotificationManagerCompat notificationManager;

    String gender = "";

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
                    Intent requestWriter= new Intent(HomeStud.this, RequestWriter.class);
                    startActivity(requestWriter);
                }
                if (topic[i].equals("My Profile")){
                    Toast.makeText(HomeStud.this, "My Profile", Toast.LENGTH_SHORT).show();
                    Intent profileIntent = new Intent(HomeStud.this, ProfileStud.class);
                    startActivity(profileIntent);
                    }
                if (topic[i].equals("Notification")){
                    PopupNotification();
                }
                if (topic[i].equals("Rate Writer")){
                    Intent rateWriter= new Intent(HomeStud.this, RateWriter.class);
                    startActivity(rateWriter);
                }
                if (topic[i].equals("Contact Us")){
                    Intent contact= new Intent(HomeStud.this, ContactUs.class);
                    startActivity(contact);
                }
                if (topic[i].equals("Logout")){
                    Toast.makeText(HomeStud.this, "Logout done successfully..", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HomeStud.this, LoginStud.class));
                    auth.signOut();
                    finish();
                }

            }
        });
    }
    private void PopupNotification() {
        Intent activityIntent= new Intent(HomeStud.this, ListNotification.class);
        PendingIntent contentIntent= PendingIntent.getActivity(this,0,activityIntent,0);
        Intent broadcastIntent= new Intent(this, NotificationReceiver.class);
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
