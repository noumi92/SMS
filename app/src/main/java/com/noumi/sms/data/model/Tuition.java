package com.noumi.sms.data.model;

import java.util.Date;

public class Tuition {
    private String mTuitionId;
    private String mTutorId;
    private String mStudentId;
    private Date mRequestedDate;
    private Date mAcceptedDate;
    private boolean mActive;
    private boolean mAccepted;

    public Tuition() {
    }

    public Tuition(String tuitionId, String tutorId, String studentId, Date requestedDate, boolean active, boolean accepted) {
        mTuitionId = tuitionId;
        mTutorId = tutorId;
        mStudentId = studentId;
        mRequestedDate = requestedDate;
        mAcceptedDate = null;
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

    public Date getRequestedDate() {
        return mRequestedDate;
    }

    public void setRequestedDate(Date requestedDate) {
        mRequestedDate = requestedDate;
    }

    public Date getAcceptedDate() {
        return mAcceptedDate;
    }

    public void setAcceptedDate(Date acceptedDate) {
        mAcceptedDate = acceptedDate;
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
