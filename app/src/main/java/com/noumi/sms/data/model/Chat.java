package com.noumi.sms.data.model;

import java.util.Date;

public class Chat {
    private String mChatId;
    private Date mChatTime;
    private String mTutorId;
    private String mStudentId;

    public Chat() {
    }

    public Chat(String chatId, Date chatTime, String tutorId, String studentId) {
        mChatId = chatId;
        mChatTime = chatTime;
        mTutorId = tutorId;
        mStudentId = studentId;
    }

    public String getChatId() {
        return mChatId;
    }

    public void setChatId(String chatId) {
        mChatId = chatId;
    }

    public Date getChatTime() {
        return mChatTime;
    }

    public void setChatTime(Date chatTime) {
        mChatTime = chatTime;
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
}