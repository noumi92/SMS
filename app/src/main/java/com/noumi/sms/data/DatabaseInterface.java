package com.noumi.sms.data;
//this interface defines the contract for DatabaseHandler class
import com.noumi.sms.data.model.Student;
import com.noumi.sms.ui.forgotpassword.ForgotPasswordPresenterInterface;
import com.noumi.sms.ui.login.LoginPresenterInterface;
import com.noumi.sms.ui.signup.SignupPresenterInterface;
import com.noumi.sms.ui.students.StudentListPresenterInterface;

public interface DatabaseInterface {
    void signupStudent(Student student, String password, SignupPresenterInterface signupPresenter);
    void LoginUser(String email, String password, LoginPresenterInterface loginPresenter);
    void checkLogin(LoginPresenterInterface loginPresenter);
    void getStudents(StudentListPresenterInterface listPresenter);
    void logoutUser(StudentListPresenterInterface listPresenter);
    void resetPassword(String email, ForgotPasswordPresenterInterface forgotPasswordPresenter);
    void getStudentsByGender(String gender, StudentListPresenterInterface studentListPresenter);
    void getStudentsByCity(String city, StudentListPresenterInterface studentListPresenter);
    void getStudentsByCityAndGender(String city, String gender, StudentListPresenterInterface studentListPresenter);
}
