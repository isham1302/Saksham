package com.example.saksham.Writer.Chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saksham.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chat extends AppCompatActivity {

    Toolbar toolbar;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mChatAdapter;
    private RecyclerView.LayoutManager mChatLayoutManager;

    EditText mSendEditText;
    Button mSendBtn;

    private String CurrentUser,matchId, chatId;

    DatabaseReference mDatabaseUser, mDatabaseChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toolbar = findViewById(R.id.ChatToolbar);
        setSupportActionBar(toolbar);

        CurrentUser= FirebaseAuth.getInstance().getCurrentUser().getUid();
        matchId=getIntent().getExtras().getString("matchId");
        mDatabaseUser= FirebaseDatabase.getInstance().getReference().child("Saksham").child("Writer").child(CurrentUser).child("Connection").child("Matches").child(matchId).child("ChatId");
        mDatabaseChat= FirebaseDatabase.getInstance().getReference().child("Saksham").child("Chat");

        getChatId();

        mSendEditText= findViewById(R.id.msg);
        mSendBtn= findViewById(R.id.send);

        recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        mChatLayoutManager= new LinearLayoutManager(Chat.this);
        recyclerView.setLayoutManager(mChatLayoutManager);
        mChatAdapter= new ChatAdapter(getDataSetChat(),Chat.this);
        recyclerView.setAdapter(mChatAdapter);

        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String sendMessageText= mSendEditText.getText().toString().trim();
        if (!sendMessageText.isEmpty()){
            DatabaseReference newMessageDb= mDatabaseChat.push();
            Map newMessage= new HashMap();
            newMessage.put("createdByUser",CurrentUser);
            newMessage.put("Text",sendMessageText);
            newMessageDb.setValue(newMessage);
        }
        mSendEditText.setText(null);
    }
    private void getChatId(){
        mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 if (snapshot.exists()){
                     chatId= snapshot.getValue().toString();
                     mDatabaseChat= mDatabaseChat.child(chatId);
                     getChatMessages();
                 }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getChatMessages() {
        mDatabaseChat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()){
                    String  message= null;
                    String createdByUser= null;
                    if (snapshot.child("Text").getValue() != null){
                        message= snapshot.child("Text").getValue().toString().trim();
                    }
                    if (snapshot.child("createdByUser").getValue() != null){
                        createdByUser= snapshot.child("createdByUser").getValue().toString().trim();
                    }
                    if (message !=null && createdByUser != null){
                        Boolean currentUserBoolean= false;
                        if (createdByUser.equals(CurrentUser)){
                            currentUserBoolean= true;
                        }
                        ChatObject newMessage= new ChatObject(message,currentUserBoolean);
                        resultChat.add(newMessage);
                        mChatAdapter.notifyDataSetChanged();
                    }
                }else{
                    Toast.makeText(Chat.this, "Sorry something went wrong", Toast.LENGTH_SHORT).show();
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

    private ArrayList<ChatObject> resultChat= new ArrayList<ChatObject>();
    private List<ChatObject> getDataSetChat() {
        return resultChat;
    }
}