package com.noumi.sms.data.model;

public class Tuition {
    private String mTuitionId;
    private String mTutorId;
    private String mStudentId;
    private boolean mActive;
    private boolean mAccepted;

    public Tuition() {
    }

    public Tuition(String tuitionId, String tutorId, String studentId, boolean active, boolean accepted) {
        mTuitionId = tuitionId;
        mTutorId = tutorId;
        mStudentId = studentId;
        mActive = active;
        mAccepted = accepted;
    }

    public String getTuitionId() {
        return mTuitionId;
    }

    public void setTuitionId(String tuitionId) {
        mTuitionId = tuitionId;
    }

    public String getTutorId() {
        return mTutorId;
    }

    public void setTutorId(String tutorId) {
        mTutorId = tutorId;
    }

    public String getStudentId() {
        return mStudentId;
    }

    public void setStudentId(String studentId) {
        mStudentId = studentId;
    }

    public boolean isActive() {
        return mActive;
    }

    public void setActive(boolean active) {
        mActive = active;
    }

    public boolean isAccepted() {
        return mAccepted;
    }

    public void setAccepted(boolean accepted) {
        mAccepted = accepted;
    }
}
