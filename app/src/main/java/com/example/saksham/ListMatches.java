package com.example.saksham;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ListMatches extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter mMatchesAdapter;
    RecyclerView.LayoutManager mMatchesLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_matches);
        recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        mMatchesLayoutManager= new LinearLayoutManager(ListMatches.this);
        recyclerView.setLayoutManager(mMatchesLayoutManager);
        mMatchesAdapter= new MatchesAdapter(getDatasetMatch(),ListMatches.this);
        recyclerView.setAdapter(mMatchesAdapter);

        MatchObject object= new MatchObject("asd");
        resultMatches.add(object);
        mMatchesAdapter.notifyDataSetChanged();
    }

    private ArrayList<MatchObject> resultMatches= new ArrayList<MatchObject>();
    private List<MatchObject> getDatasetMatch() {
        return resultMatches;
    }
}