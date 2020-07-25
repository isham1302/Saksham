package com.example.saksham;

public class Member_Stud {
     String FirstName,LastName,Username,Gender,dob,phoneNo,Email,School_College_Name,Course,Password,Type;

    public Member_Stud(String fname, String lname, String username, String gender, String dob, String phone, String email, String scho_clg, String course, String userType) {
    }

    public Member_Stud(String firstName, String lastName, String username, String gender, String dob, String phoneNo, String email, String school_College_Name, String course, String password,String Type) {
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
        this.Type= Type;
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

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
