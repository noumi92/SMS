package com.noumi.sms.ui.login;

//this class defines contract for LoginPresenter and these methods are implemented in LoginPresenter

public interface LoginPresenterInterface {
    void loginUser(String email, String password);
    void checkLogin();
    void onLoginSuccess();
    void onQueryResult(String result);
}
