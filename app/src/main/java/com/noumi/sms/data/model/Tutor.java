package com.noumi.sms.data.model;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class Tutor {
    private String mTutorId;
    private String mTutorName;
    private String mTutorEmail;
    private String mTutorCity;
    private String mTutorGender;
    private List<String> mTutorSubjects;
    private String mTutorDegreeName;
    private String mTutorDegreeSubject;
    private GeoPoint mTutorLocation;
    private long mTutorRating;
    private String mTutorAboutMe;
    private long mTutorFee;
    private String mTokenId;

    public String getTokenId() {
        return mTokenId;
    }

    public void setTokenId(String tokenId) {
        mTokenId = tokenId;
    }

    public Tutor() {
    }

    public Tutor(String tutorName, String tutorEmail, String tutorCity, String tutorGender) {
        mTutorId = "123";
        mTutorName = tutorName;
        mTutorEmail = tutorEmail;
        mTutorCity = tutorCity;
        mTutorGender = tutorGender;
        mTutorSubjects = new ArrayList<>();
        mTutorDegreeName = "";
        mTutorDegreeSubject = "";
        mTutorLocation = new GeoPoint(0,0);
        mTutorRating = 0;
        mTutorAboutMe = "";
        mTutorFee = 0;
        mTokenId = "";
    }

    public Tutor(String tutorId, String tutorName, String tutorEmail, String tutorCity, String tutorGender, List<String> tutorSubjects, String tutorDegreeName, String tutorDegreeSubject, GeoPoint tutorLocation, long tutorRating, String tutorAboutMe, long tutorFee) {
        mTutorId = tutorId;
        mTutorName = tutorName;
        mTutorEmail = tutorEmail;
        mTutorCity = tutorCity;
        mTutorGender = tutorGender;
        mTutorSubjects = tutorSubjects;
        mTutorDegreeName = tutorDegreeName;
        mTutorDegreeSubject = tutorDegreeSubject;
        mTutorLocation = tutorLocation;
        mTutorRating = tutorRating;
        mTutorAboutMe = tutorAboutMe;
        mTutorFee = tutorFee;
    }

    public String getTutorId() {
        return mTutorId;
    }

    public void setTutorId(String tutorId) {
        mTutorId = tutorId;
    }

    public String getTutorName() {
        return mTutorName;
    }

    public void setTutorName(String tutorName) {
        mTutorName = tutorName;
    }

    public String getTutorEmail() {
        return mTutorEmail;
    }

    public void setTutorEmail(String tutorEmail) {
        mTutorEmail = tutorEmail;
    }

    public String getTutorCity() {
        return mTutorCity;
    }

    public void setTutorCity(String tutorCity) {
        mTutorCity = tutorCity;
    }

    public String getTutorGender() {
        return mTutorGender;
    }

    public void setTutorGender(String tutorGender) {
        mTutorGender = tutorGender;
    }

    public List<String> getTutorSubjects() {
        return mTutorSubjects;
    }

    public void setTutorSubjects(List<String> tutorSubjects) {
        mTutorSubjects = tutorSubjects;
    }

    public String getTutorDegreeName() {
        return mTutorDegreeName;
    }

    public void setTutorDegreeName(String tutorDegreeName) {
        mTutorDegreeName = tutorDegreeName;
    }

    public String getTutorDegreeSubject() {
        return mTutorDegreeSubject;
    }

    public void setTutorDegreeSubject(String tutorDegreeSubject) {
        mTutorDegreeSubject = tutorDegreeSubject;
    }

    public GeoPoint getTutorLocation() {
        return mTutorLocation;
    }

    public void setTutorLocation(GeoPoint tutorLocation) {
        mTutorLocation = tutorLocation;
    }

    public long getTutorRating() {
        return mTutorRating;
    }

    public void setTutorRating(long tutorRating) {
        mTutorRating = tutorRating;
    }

    public String getTutorAboutMe() {
        return mTutorAboutMe;
    }

    public void setTutorAboutMe(String tutorAboutMe) {
        mTutorAboutMe = tutorAboutMe;
    }

    public long getTutorFee() {
        return mTutorFee;
    }

    public void setTutorFee(long tutorFee) {
        mTutorFee = tutorFee;
    }
}
