package com.noumi.sms.ui.forgotpassword;

//this class defines contract for ForgotPasswordPresenter and these methods are implemented in ForgotPAsswordPasswordPresenter

public interface ForgotPasswordPresenterInterface {
    void resetPassword(String email);
    void onQueryResult(String result);
}
