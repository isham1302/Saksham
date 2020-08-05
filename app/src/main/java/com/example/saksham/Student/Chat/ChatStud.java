package com.example.saksham.Student.Chat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ChatStud extends AppCompatActivity {

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
        mDatabaseUser= FirebaseDatabase.getInstance().getReference().child("Saksham").child("Student").child(CurrentUser).child("Connection").child("Matches").child(matchId).child("chatId");
        mDatabaseChat= FirebaseDatabase.getInstance().getReference().child("Saksham").child("Chat");

        getChatId();

        mSendEditText= findViewById(R.id.msg);
        mSendBtn= findViewById(R.id.send);

        recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        mChatLayoutManager= new LinearLayoutManager(ChatStud.this);
        recyclerView.setLayoutManager(mChatLayoutManager);
        mChatAdapter= new ChaStudtAdapter(getDatasetChat(), ChatStud.this);
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
            DatabaseReference newMessageDB= mDatabaseChat.push();
            Map newMessage= new HashMap();
            newMessage.put("createdByUser",CurrentUser);
            newMessage.put("Text",sendMessageText);
            newMessageDB.setValue(newMessage);
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
                        ChatStudObject newMessage= new ChatStudObject(message,currentUserBoolean);
                        resultChat.add(newMessage);
                        mChatAdapter.notifyDataSetChanged();
                    }
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

    private ArrayList<ChatStudObject> resultChat= new ArrayList<ChatStudObject>();
    private List<ChatStudObject> getDatasetChat() {
        return resultChat;
    }
}