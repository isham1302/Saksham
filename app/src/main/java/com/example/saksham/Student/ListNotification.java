package com.example.saksham.Student;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.saksham.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class ListNotification extends AppCompatActivity {
    private CardsNotification cards_data[];
    private ArrayListNotification adapter;
    private int i;

    SwipeFlingAdapterView flingContainer;
    ListView listView;
    List<CardsNotification> rowItem;
    Toolbar toolbar;

    FirebaseAuth firebaseAuth;
    String currentId;
    DatabaseReference userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notification);

        toolbar = findViewById(R.id.notificationToolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        userDB = FirebaseDatabase.getInstance().getReference().child("Saksham");
        currentId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        getWriterData();

        rowItem = new ArrayList<CardsNotification>();

        adapter = new ArrayListNotification (this, R.layout.row, rowItem);

        flingContainer = findViewById(R.id.frame);
        flingContainer.setAdapter(adapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                rowItem.remove(0);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                CardsNotification obj = (CardsNotification) dataObject;
                String userId = obj.getUserId();
                userDB.child("Writer").child(userId).child("Connection").child("Reject").child(currentId).setValue(true);
                Toast.makeText(ListNotification.this, "Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(ListNotification.this, "Right", Toast.LENGTH_SHORT).show();
                CardsNotification obj = (CardsNotification) dataObject;
                String userId = obj.getUserId();
                isConnected(userId);
                userDB.child("Writer").child(userId).child("Connection").child("Accept").child(currentId).setValue(true);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(ListNotification.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void isConnected(String userId) {
        DatabaseReference currentUserId= userDB.child("Student").child(currentId).child("Connection").child("Accept").child(userId);
        currentUserId.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Toast.makeText(ListNotification.this, "Accepted achieved!!", Toast.LENGTH_SHORT).show();
                    String key= FirebaseDatabase.getInstance().getReference().child("Chat").push().getKey();

                    userDB.child("Writer").child(snapshot.getKey()).child("Connection").child("Matches").child(currentId).child("ChatId").setValue(key);
                    userDB.child(currentId).child("Connection").child("Matches").child(snapshot.getKey()).child(currentId).child("ChatId").setValue(key);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getWriterData() {
        DatabaseReference studentReference = FirebaseDatabase.getInstance().getReference().child("Saksham").child("Writer");
        studentReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists() && !snapshot.child("Connection").child("Reject").hasChild(currentId) && !snapshot.child("Connection").child("Accept").hasChild(currentId)) {
                    CardsNotification item = new CardsNotification(snapshot.getKey(),snapshot.child("fname").getValue().toString(),snapshot.child("lname").getValue().toString(),snapshot.child("work").getValue().toString(),snapshot.child("profileImageUrl").getValue().toString());
                    rowItem.add(item);
                    adapter.notifyDataSetChanged();
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