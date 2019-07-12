package com.noumi.sms.ui.login;

//this class defines contract for LoginActivity and these methods are implemented in LoginActivity

public interface LoginViewInterface {
    void onResult(String message);
    void onLoginSuccess();
}
