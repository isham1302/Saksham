package com.example.saksham.Writer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.saksham.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListMatches extends AppCompatActivity {

    Toolbar toolbar;
    private RecyclerView recyclerView;
   private  RecyclerView.Adapter mMatchesAdapter;
   private  RecyclerView.LayoutManager mMatchesLayoutManager;

    private String CurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_matches);

        toolbar = findViewById(R.id.MatchToolbar);
        setSupportActionBar(toolbar);

        CurrentUser= FirebaseAuth.getInstance().getCurrentUser().getUid();

        recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

        mMatchesLayoutManager= new LinearLayoutManager(ListMatches.this);
        recyclerView.setLayoutManager(mMatchesLayoutManager);
        mMatchesAdapter= new MatchesAdapter(getDataSetMatches(),ListMatches.this);
        recyclerView.setAdapter(mMatchesAdapter);

        getUserMastchId();
    }

    private void getUserMastchId() {
        DatabaseReference matchDB= FirebaseDatabase.getInstance().getReference().child("Saksham").child("Writer").child(CurrentUser).child("Connection").child("Matches");
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
        DatabaseReference userDB= FirebaseDatabase.getInstance().getReference().child("Saksham").child("Student").child(key);
        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String userId= snapshot.getKey();
                    String firstName= "";
                    String profileImageUrl="";
                    if (snapshot.child("firstName").getValue() != null){
                        firstName= snapshot.child("firstName").getValue().toString();
                    }
                    if (snapshot.child("profileImageUrl").getValue() != null){
                        profileImageUrl= snapshot.child("profileImageUrl").getValue().toString();
                    }
                    MatchObject obj= new MatchObject(userId,firstName,profileImageUrl);
                    resultMatches.add(obj);
                    mMatchesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private ArrayList<MatchObject> resultMatches= new ArrayList<MatchObject>();
    private List<MatchObject> getDataSetMatches() {
        return resultMatches;
    }
}