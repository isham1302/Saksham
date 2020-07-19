package com.example.saksham;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class HomeStud extends AppCompatActivity {
    GridView gridView;
    private NotificationManagerCompat notificationManager;
    public static final String SHARED_PREF_NAME="mypref";
    SharedPreferences sharedPreferences;
    String[] topic= {"Request Writer","My Profile","Notification","Rate Writer","Contact Us","Logout"};
    int[] topicImage={R.drawable.writer, R.drawable.profile, R.drawable.notification, R.drawable.rate, R.drawable.contact, R.drawable.logout};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_stud);
        gridView= findViewById(R.id.grid_view);
        sharedPreferences= getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        notificationManager= NotificationManagerCompat.from(this);

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
                    Intent profileIntent= new Intent(HomeStud.this,ProfileStud.class);
                    startActivity(profileIntent);
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
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    finish();
                    Toast.makeText(HomeStud.this, "Logout done successfully..", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HomeStud.this,Login.class));
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