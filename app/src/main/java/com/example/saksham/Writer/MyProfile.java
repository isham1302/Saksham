package com.example.saksham.Writer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.saksham.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class MyProfile extends AppCompatActivity {

    EditText fname;
    EditText lname;
    EditText username;
    EditText gender;
    EditText dob;
    EditText phone;
    EditText email;
    EditText lang;
    EditText school;
    EditText ug;
    EditText pg;
    EditText work;
    EditText pass;
    Button btn_save;
    ImageView profile;

    FirebaseAuth firebaseAuth;
    DatabaseReference writerDatabase;

    Uri resultUri;
    String userId, firstname, LastName, Username, Gender,Dob ,Phone_no, email_id, language,school_clg, underg, postg, current_status, password, profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        fname = findViewById(R.id.firstname);
        lname = findViewById(R.id.lastname);
        username = findViewById(R.id.username);
        gender = findViewById(R.id.gender);
        dob = findViewById(R.id.dob);
        phone = findViewById(R.id.mobile);
        email = findViewById(R.id.email_id);
        lang = findViewById(R.id.lang);
        school = findViewById(R.id.school);
        ug = findViewById(R.id.ug);
        pg = findViewById(R.id.pg);
        work = findViewById(R.id.work);
        pass = findViewById(R.id.secured_password);
        profile = findViewById(R.id.profilePic);
        btn_save = findViewById(R.id.save);

        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        writerDatabase = FirebaseDatabase.getInstance().getReference().child("Saksham").child("Writer").child(userId);
        getUserInfo();

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInformation();
            }
        });
    }

    private void getUserInfo() {
        writerDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                    if (map.get("fname") != null) {
                        firstname = map.get("fname").toString();
                        fname.setText(firstname);
                    }
                        if (map.get("lname") != null) {
                            LastName = map.get("lname").toString();
                            lname.setText(LastName);
                        }
                            if (map.get("username") != null) {
                                Username = map.get("username").toString();
                                username.setText(Username);
                            }
                                if (map.get("gender") != null) {
                                    Gender = map.get("gender").toString();
                                    gender.setText(Gender);
                                }
                                    if (map.get("dob") != null) {
                                        Dob = map.get("dob").toString();
                                        dob.setText(Dob);
                                    }
                                        if (map.get("phone") != null) {
                                            Phone_no = map.get("phone").toString();
                                            phone.setText(Phone_no);
                                        }
                    if (map.get("email") != null) {
                        email_id = map.get("email").toString();
                        email.setText(email_id);
                    }
                    if (map.get("lang") != null) {
                        language = map.get("lang").toString();
                        lang.setText(language);
                    }
                    if (map.get("language") != null) {
                        language = map.get("language").toString();
                        lang.setText(language);
                    }
                    if (map.get("school") != null) {
                        school_clg = map.get("school").toString();
                        school.setText(school_clg);
                    }
                    if (map.get("ug") != null) {
                        underg = map.get("ug").toString();
                        ug.setText(underg);
                    }
                    if (map.get("pg") != null) {
                        postg = map.get("pg").toString();
                        pg.setText(postg);
                    }
                    if (map.get("work") != null) {
                        current_status = map.get("work").toString();
                        work.setText(current_status);
                    }
                    if (map.get("pass") != null) {
                        password = map.get("pass").toString();
                        pass.setText(password);
                    }
                    if (map.get("profileImageUrl") != null) {
                        profilePic = map.get("profileImageUrl").toString();
                        Glide.with(getApplication()).load(profilePic).into(profile);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void saveUserInformation () {
                        firstname = fname.getText().toString().trim();
                        LastName = lname.getText().toString().trim();
                        Username = username.getText().toString().trim();
                        Gender = gender.getText().toString().trim();
                        Dob= dob.getText().toString().trim();
                        Phone_no = phone.getText().toString().trim();
                        email_id = email.getText().toString().trim();
                        language = lang.getText().toString().trim();
                        school_clg = school.getText().toString().trim();
                        underg = ug.getText().toString().trim();
                        postg = pg.getText().toString().trim();
                        current_status = work.getText().toString().trim();
                        password = pass.getText().toString().trim();

                        Map userInfo = new HashMap();
                        userInfo.put("fname", firstname);
                        userInfo.put("lname", LastName);
                        userInfo.put("username", Username);
                        userInfo.put("gender", Gender);
                        userInfo.put("dob", Dob);
                        userInfo.put("phone", Phone_no);
                        userInfo.put("email", email_id);
                        userInfo.put("language", language);
                        userInfo.put("school", school_clg);
                        userInfo.put("ug", underg);
                        userInfo.put("pg", postg);
                        userInfo.put("work", current_status);
                        userInfo.put("pass", password);
                        writerDatabase.updateChildren(userInfo);

                        if (resultUri != null){
                            StorageReference filepath= FirebaseStorage.getInstance().getReference().child("ProfileImages").child(userId);
                            Bitmap bitmap= null;
                            try {
                                bitmap= MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG,20, byteArrayOutputStream);
                            byte[] data= byteArrayOutputStream.toByteArray();
                            UploadTask uploadTask= filepath.putBytes(data);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    finish();
                                }
                            });
                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            Map userInfo = new HashMap();
                                            userInfo.put("profileImageUrl",task.getResult().toString());
                                            writerDatabase.updateChildren(userInfo);
                                            finish();
                                            return;
                                        }
                                    });
                                }
                            });
                        }else {
                            finish();
                        }
                    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            final Uri imageUri = data.getData();
            resultUri= imageUri;
            profile.setImageURI(resultUri);
        }
    }
}

