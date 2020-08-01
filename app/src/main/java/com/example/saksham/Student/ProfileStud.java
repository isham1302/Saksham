package com.example.saksham.Student;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.saksham.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProfileStud extends AppCompatActivity {
    EditText fname;
    EditText lname;
    EditText username;
    EditText gender;
    EditText dob;
    EditText phone;
    EditText email;
    EditText course;
    EditText sch_clg_name;
    EditText pass;
    Button btn_save;
    ImageView profile;

    FirebaseAuth firebaseAuth;
    DatabaseReference studentDatabase;

    Uri resultUri;
    String userId, firstname, LastName, Username, Gender,Dob ,Phone_no, email_id, course_obtained,clg_school_name ,password, profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_stud);
        fname = findViewById(R.id.stud_fname);
        lname = findViewById(R.id.stud_lname);
        username = findViewById(R.id.stud_username);
        gender = findViewById(R.id.stud_gender);
        dob = findViewById(R.id.stud_dob);
        phone = findViewById(R.id.stud_num);
        email = findViewById(R.id.stud_email);
        course= findViewById(R.id.stud_course);
        sch_clg_name= findViewById(R.id.stud_schoolClg);
        pass = findViewById(R.id.stud_password);
        profile = findViewById(R.id.profilePic);
        btn_save = findViewById(R.id.save);


        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        studentDatabase = FirebaseDatabase.getInstance().getReference().child("Saksham").child("Student").child(userId);
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
        studentDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                    if (map.get("firstName") != null) {
                        firstname = map.get("firstName").toString();
                        fname.setText(firstname);
                    }
                    if (map.get("lastName") != null) {
                        LastName = map.get("lastName").toString();
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
                    if (map.get("phoneNo") != null) {
                        Phone_no = map.get("phoneNo").toString();
                        phone.setText(Phone_no);
                    }
                    if (map.get("email") != null) {
                        email_id = map.get("email").toString();
                        email.setText(email_id);
                    }
                    if (map.get("course") != null) {
                        course_obtained = map.get("course").toString();
                        course.setText(course_obtained);
                    }
                    if (map.get("school_College_Name") != null) {
                        clg_school_name = map.get("school_College_Name").toString();
                        sch_clg_name.setText(clg_school_name);
                    }
                    if (map.get("password") != null) {
                        password = map.get("password").toString();
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
        Phone_no = phone.getText().toString().trim();
        email_id = email.getText().toString().trim();
        course_obtained = course.getText().toString().trim();
        clg_school_name = sch_clg_name.getText().toString().trim();
        password = pass.getText().toString().trim();

        Map userInfo = new HashMap();
        userInfo.put("fname", firstname);
        userInfo.put("lname", LastName);
        userInfo.put("username", Username);
        userInfo.put("gender", Gender);
        userInfo.put("phone", Phone_no);
        userInfo.put("email", email_id);
        userInfo.put("course", course_obtained);
        userInfo.put("school_clg_name", clg_school_name);
        userInfo.put("password", password);
        studentDatabase.updateChildren(userInfo);

        if (resultUri != null){
            StorageReference filepath= FirebaseStorage.getInstance().getReference().child("ProfileImages").child(userId);
            Bitmap bitmap= null;
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,20, byteArrayOutputStream);
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
                                    Task<Uri> downloadUrl =  taskSnapshot.getStorage().getDownloadUrl();
                                    Map userInfo = new HashMap();
                                    userInfo.put("profileImageUrl", downloadUrl);
                                    studentDatabase.updateChildren(userInfo);
                    finish();
                    return;
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