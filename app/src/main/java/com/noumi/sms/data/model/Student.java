package com.noumi.sms.data.model;

//a model cllass which is used to store a single student. when students data is fetched from firebase, it is casted to this model class
public class Student {
    //fields
    private String mStudentId;
    private String mStudentEmail;
    private String mStudentName;
    private String mStudentCity;
    private String mStudentGender;
    //constructor required for database operations
    public Student() {
    }
    //constructor to manually create student
    public Student(String studentId, String studentEmail, String studentName, String studentCity, String studentGender) {
        mStudentId = studentId;
        mStudentEmail = studentEmail;
        mStudentName = studentName;
        mStudentCity = studentCity;
        mStudentGender = studentGender;
    }
    //getters and setters for fields
    public String getStudentId() {
        return mStudentId;
    }

    public void setStudentId(String studentId) {
        this.mStudentId = studentId;
    }

    public String getStudentEmail() {
        return mStudentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.mStudentEmail = studentEmail;
    }

    public String getStudentName() {
        return mStudentName;
    }

    public void setStudentName(String studentName) {
        this.mStudentName = studentName;
    }

    public String getStudentCity() {
        return mStudentCity;
    }

    public void setStudentCity(String studentCity) {
        this.mStudentCity = studentCity;
    }

    public String getStudentGender() {
        return mStudentGender;
    }

    public void setStudentGender(String studentGender) {
        this.mStudentGender = studentGender;
    }
    //overrides toString method to print student data
    @Override
    public String toString() {
        return "Email: " + mStudentEmail + " Name: " + mStudentName + " Genser: " + mStudentGender;
    }
}
