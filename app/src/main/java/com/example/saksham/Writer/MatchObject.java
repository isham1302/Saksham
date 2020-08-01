package com.example.saksham.Writer;

public class MatchObject {
    String userId,firstName,profileImageUrl;

    public MatchObject(String userId, String firstName, String profileImageUrl) {
        this.userId = userId;
        this.firstName = firstName;
        this.profileImageUrl = profileImageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
