package com.example.saksham.Student;

public class RateUsModal {
    String FullName,Review,Star;

    public RateUsModal(String fullName, String review, String star) {
        FullName = fullName;
        Review = review;
        Star = star;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }

    public String getStar() {
        return Star;
    }

    public void setStar(String star) {
        Star = star;
    }
}
