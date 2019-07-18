package com.noumi.sms.ui.login;

//this class connects LoginActivity to database and passes feedbacks from database to LoginActivity to update views and
//feedback to user

import com.noumi.sms.data.DatabaseHandler;
import com.noumi.sms.data.DatabaseInterface;
import com.noumi.sms.ui.base.BasePresenter;

public class LoginPresenter extends BasePresenter implements LoginPresenterInterface {
    private String TAG = "com.noumi.sms.custom.log";
    private DatabaseInterface mDatabaseHandler;
    private LoginViewInterface mLoginViewInterface;

    public LoginPresenter(LoginViewInterface view) {
        mDatabaseHandler = new DatabaseHandler();
        mLoginViewInterface = view;
    }
    //check login on start of LoginActivity
    @Override
    public void checkLogin() {
        mDatabaseHandler.checkLogin(this);
    }
    //login user into system
    @Override
    public void loginUser(String email, String password) {
        mDatabaseHandler.LoginUser(email, password, this);
    }
    //method to get feedback from database handler and pass to LoginActivity
    @Override
    public void onQueryResult(String result) {
        mLoginViewInterface.onResult(result);
    }
    //method to get feedback from database handler on successful login and pass to LoginActivity
    @Override
    public void onLoginSuccess() {
        mLoginViewInterface.onLoginSuccess();
    }
}