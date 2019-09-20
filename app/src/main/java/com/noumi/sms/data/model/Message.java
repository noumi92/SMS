package com.noumi.sms.data.model;

import java.util.Date;

public class Message {
    private String mMessageBody;
    private String mSenderId;
    private Date mMessageTime;

    public Message() {
    }

    public Message(String messageBody, String senderId, Date messageTime) {
        mMessageBody = messageBody;
        mSenderId = senderId;
        mMessageTime = messageTime;
    }

    public String getMessageBody() {
        return mMessageBody;
    }

    public void setMessageBody(String messageBody) {
        mMessageBody = messageBody;
    }

    public String getSenderId() {
        return mSenderId;
    }

    public void setSenderId(String senderId) {
        mSenderId = senderId;
    }

    public Date getMessageTime() {
        return mMessageTime;
    }

    public void setMessageTime(Date messageTime) {
        mMessageTime = messageTime;
    }
}
