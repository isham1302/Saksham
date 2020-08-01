package com.example.saksham.Student;

public class Member_Stud {
     String FirstName,LastName,Username,Gender,dob,phoneNo,Email,School_College_Name,Course,Password;


    public Member_Stud(String firstName, String lastName, String username, String gender, String dob, String phoneNo, String email, String school_College_Name, String course, String password) {
        FirstName = firstName;
        LastName = lastName;
        Username = username;
        Gender = gender;
        this.dob = dob;
        this.phoneNo = phoneNo;
        Email = email;
        School_College_Name = school_College_Name;
        Course = course;
        Password = password;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSchool_College_Name() {
        return School_College_Name;
    }

    public void setSchool_College_Name(String school_College_Name) {
        School_College_Name = school_College_Name;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
