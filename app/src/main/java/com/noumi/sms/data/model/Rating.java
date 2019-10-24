package com.noumi.sms.data.model;

public class Rating {
    private String mComments;
    private int mRating;
    private String mRatingId;
    private String mStudentId;
    private String mTutorId;

    public Rating() {
    }

    public Rating(String ratingId, String studentId, String tutorId) {
        mRatingId = ratingId;
        mStudentId = studentId;
        mTutorId = tutorId;
    }

    public String getComments() {
        return mComments;
    }

    public void setComments(String comments) {
        mComments = comments;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

    public String getRatingId() {
        return mRatingId;
    }

    public void setRatingId(String ratingId) {
        mRatingId = ratingId;
    }

    public String getStudentId() {
        return mStudentId;
    }

    public void setStudentId(String studentId) {
        mStudentId = studentId;
    }

    public String getTutorId() {
        return mTutorId;
    }

    public void setTutorId(String tutorId) {
        mTutorId = tutorId;
    }
}
