package com.example.saksham.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.saksham.R;
import com.example.saksham.Writer.MatchObject;
import com.example.saksham.Writer.MatchesAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListMatchesStud extends AppCompatActivity {

    Toolbar toolbar;
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter mMatchesAdapter;
    private  RecyclerView.LayoutManager mMatchesLayoutManager;

    private String CurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_matches_stud);
        toolbar = findViewById(R.id.MatchToolbar);
        setSupportActionBar(toolbar);

        CurrentUser= FirebaseAuth.getInstance().getCurrentUser().getUid();

        recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

        mMatchesLayoutManager= new LinearLayoutManager(ListMatchesStud.this);
        recyclerView.setLayoutManager(mMatchesLayoutManager);
        mMatchesAdapter= new MatchAdapterStud(getDataSetMatches(),ListMatchesStud.this);
        recyclerView.setAdapter(mMatchesAdapter);

        getUserMastchId();
    }

    private void getUserMastchId() {
        DatabaseReference matchDB= FirebaseDatabase.getInstance().getReference().child("Saksham").child("Student").child(CurrentUser).child("Connection").child("Matches");
        matchDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot match : snapshot.getChildren()){
                        FetchMatchInformation(match.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void FetchMatchInformation(String key) {
        DatabaseReference userDB= FirebaseDatabase.getInstance().getReference().child("Saksham").child("Writer").child(key);
        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String userId= snapshot.getKey();
                    String firstName= "";
                    String profileImageUrl="";
                    if (snapshot.child("fname").getValue() != null){
                        firstName= snapshot.child("fname").getValue().toString();
                    }
                    if (snapshot.child("profileImageUrl").getValue() != null){
                        profileImageUrl= snapshot.child("profileImageUrl").getValue().toString();
                    }
                    MatchObjectStud obj= new MatchObjectStud(userId,firstName,profileImageUrl);
                    resultMatches.add(obj);
                    mMatchesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private ArrayList<MatchObjectStud> resultMatches= new ArrayList<MatchObjectStud>();
    private List<MatchObjectStud> getDataSetMatches() {
        return resultMatches;
    }

}