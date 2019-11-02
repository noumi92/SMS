package com.noumi.sms.data.model;

//a singelton class to store logged in user data.

public class LoggedInUser {
    private static LoggedInUser sLoggedInUser;
    private String userId;
    private String userName;
    private String userEmail;
    private String userType;

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    private LoggedInUser() {
        userName = "FindMytutor";
    }
    public static LoggedInUser getLoggedInUser(){
        if(sLoggedInUser == null){
            sLoggedInUser = new LoggedInUser();
        }
        return sLoggedInUser;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void clearUser(){
        userId = null;
        userName = null;
        userEmail = null;
        userType = null;
    }
}