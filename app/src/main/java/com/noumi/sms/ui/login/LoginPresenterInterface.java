package com.noumi.sms.ui.login;

//this class defines contract for LoginPresenter and these methods are implemented in LoginPresenter

public interface LoginPresenterInterface {
    void loginUser(String email, String password, String userType);
    void checkLogin();
    void onLoginSuccess(String userType);
    void onQueryResult(String result);
}
