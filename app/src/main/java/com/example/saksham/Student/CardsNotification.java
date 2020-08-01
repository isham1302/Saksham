package com.example.saksham.Student;

public class CardsNotification {
    private String userId, fname,lname,workStatus,profileImageUrl;

    public CardsNotification(String userId, String fname, String lname, String workStatus, String profileImageUrl) {
        this.userId = userId;
        this.fname = fname;
        this.lname = lname;
        this.workStatus = workStatus;
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

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
