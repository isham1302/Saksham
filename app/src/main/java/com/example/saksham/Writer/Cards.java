package com.example.saksham.Writer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;
public class Cards {
        private String userId, fname, lname, examName, MediumOfPaper, address, College_School_Name, Course;

        public Cards(String userId, String fname, String lname, String examName, String mediumOfPaper, String address, String college_School_Name, String course) {
                this.userId = userId;
                this.fname = fname;
                this.lname = lname;
                this.examName = examName;
                MediumOfPaper = mediumOfPaper;
                this.address = address;
                College_School_Name = college_School_Name;
                Course = course;
        }

        public String getUserId() {
                return userId;
        }

        public void setUserId(String userId) {
                this.userId = userId;
        }

        public String getFname() {
                return fname;
        }

        public void setFname(String fname) {
                this.fname = fname;
        }

        public String getLname() {
                return lname;
        }

        public void setLname(String lname) {
                this.lname = lname;
        }

        public String getExamName() {
                return examName;
        }

        public void setExamName(String examName) {
                this.examName = examName;
        }

        public String getMediumOfPaper() {
                return MediumOfPaper;
        }

        public void setMediumOfPaper(String mediumOfPaper) {
                MediumOfPaper = mediumOfPaper;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public String getCollege_School_Name() {
                return College_School_Name;
        }

        public void setCollege_School_Name(String college_School_Name) {
                College_School_Name = college_School_Name;
        }

        public String getCourse() {
                return Course;
        }

        public void setCourse(String course) {
                Course = course;
        }



}
