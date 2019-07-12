package com.noumi.sms.ui.forgotpassword;
//this class connects ForgotPasswordActivity to database and passes feedbacks from database to ForgotPasswordActivity to update views and
//feedback to user
import com.noumi.sms.data.DatabaseHandler;
import com.noumi.sms.ui.base.BasePresenter;

public class ForgotPasswordPresenter extends BasePresenter implements ForgotPasswordPresenterInterface {
    private DatabaseHandler mDatabaseHandler;
    private ForgotPasswordViewInterface mForgotPasswordViewInterface;

    public ForgotPasswordPresenter(ForgotPasswordViewInterface forgotPasswordViewInterface) {
        mDatabaseHandler = new DatabaseHandler();
        mForgotPasswordViewInterface = forgotPasswordViewInterface;
    }

    @Override
    public void onQueryResult(String result) {
        mForgotPasswordViewInterface.onResult(result);
    }

    @Override
    public void resetPassword(String email) {
        mDatabaseHandler.resetPassword(email, this);
    }
}

