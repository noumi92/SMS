package com.noumi.sms.ui.signup;

//this class connects SignupActivity to database and passes feedbacks from database to SignupActivity to update views and
//feedback to user

import com.noumi.sms.data.DatabaseHandler;
import com.noumi.sms.data.DatabaseInterface;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.ui.base.BasePresenter;

public class SignupPresenter extends BasePresenter implements SignupPresenterInterface {
    //fields
    private String TAG = "com.noumi.sms.custom.log"; // tag for debugging
    private DatabaseInterface mDBHandler;
    private SignupViewInterface mSignupViewInterface;

    public SignupPresenter(SignupViewInterface view) {
        mDBHandler = new DatabaseHandler();
        mSignupViewInterface = view;
    }
    //method to signup student
    @Override
    public void signupStudent(Student student, String password) {
        mDBHandler.signupStudent(student, password, this);
    }
    //method to get feedback from database handler and pass to SignupActivity
    @Override
    public void onQueryResult(String result){
        mSignupViewInterface.onResult(result);
    }
}
