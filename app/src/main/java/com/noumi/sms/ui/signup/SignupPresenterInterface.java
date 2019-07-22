package com.noumi.sms.ui.signup;

//this class defines contract for SignupPresenter and these methods are implemented in SignupPresenter

import com.noumi.sms.data.model.Student;

public interface SignupPresenterInterface {
    void signupStudent(Student student, String password);
    void onQueryResult(String result);
}
