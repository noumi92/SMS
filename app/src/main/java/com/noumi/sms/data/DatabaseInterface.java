package com.noumi.sms.data;
//this interface defines the contract for DatabaseHandler class
import com.noumi.sms.data.model.Student;
import com.noumi.sms.ui.forgotpassword.ForgotPasswordPresenter;
import com.noumi.sms.ui.login.LoginPresenter;
import com.noumi.sms.ui.signup.SignupPresenter;
import com.noumi.sms.ui.students.StudentListPresenter;

public interface DatabaseInterface {
    void signupStudent(Student student, String password, SignupPresenter signupPresenter);
    void LoginUser(String email, String password, LoginPresenter loginPresenter);
    void checkLogin(LoginPresenter loginPresenter);
    void getStudents(StudentListPresenter listPresenter);
    void logoutUser(StudentListPresenter listPresenter);
    void resetPassword(String email, ForgotPasswordPresenter forgotPasswordPresenter);
    void getStudentsByGender(String gender, StudentListPresenter studentListPresenter);
    void getStudentsByCity(String city, StudentListPresenter studentListPresenter);
    void getStudentsByCityAndGender(String city, String gender, StudentListPresenter studentListPresenter);
}
