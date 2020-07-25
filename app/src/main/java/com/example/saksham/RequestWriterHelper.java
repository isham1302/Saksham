package com.example.saksham;

public class RequestWriterHelper {
    String FirstName,LastName,examName,medPaper,address,school_clg_name,course_name;

    public RequestWriterHelper() {
    }

    public RequestWriterHelper(String firstName, String lastName, String examName, String medPaper, String address, String school_clg_name, String course_name) {
        FirstName = firstName;
        LastName = lastName;
        this.examName = examName;
        this.medPaper = medPaper;
        this.address = address;
        this.school_clg_name = school_clg_name;
        this.course_name = course_name;
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

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getMedPaper() {
        return medPaper;
    }

    public void setMedPaper(String medPaper) {
        this.medPaper = medPaper;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSchool_clg_name() {
        return school_clg_name;
    }

    public void setSchool_clg_name(String school_clg_name) {
        this.school_clg_name = school_clg_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
}
