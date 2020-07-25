package com.example.saksham;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;
public class Adapter {
        private String userId, fname, lname, examname, medium_paper, dateOfExam, Venu, College_School_Name, Course, profileImageUrl;

        public Adapter(String userId, String fname, String lname, String examname, String medium_paper, String dateOfExam, String venu, String college_School_Name, String course, String profileImageUrl) {
                this.userId = userId;
                this.fname = fname;
                this.lname = lname;
                this.examname = examname;
                this.medium_paper = medium_paper;
                this.dateOfExam = dateOfExam;
                Venu = venu;
                College_School_Name = college_School_Name;
                Course = course;
                this.profileImageUrl = profileImageUrl;
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

        public String getExamname() {
                return examname;
        }

        public void setExamname(String examname) {
                this.examname = examname;
        }

        public String getMedium_paper() {
                return medium_paper;
        }

        public void setMedium_paper(String medium_paper) {
                this.medium_paper = medium_paper;
        }

        public String getDateOfExam() {
                return dateOfExam;
        }

        public void setDateOfExam(String dateOfExam) {
                this.dateOfExam = dateOfExam;
        }

        public String getVenu() {
                return Venu;
        }

        public void setVenu(String venu) {
                Venu = venu;
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

        public String getProfileImageUrl() {
                return profileImageUrl;
        }

        public void setProfileImageUrl(String profileImageUrl) {
                this.profileImageUrl = profileImageUrl;
        }
}