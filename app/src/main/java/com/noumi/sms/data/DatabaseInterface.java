package com.noumi.sms.data;
//this interface defines the contract for DatabaseHandler class

import com.noumi.sms.data.model.Student;
import com.noumi.sms.data.model.Tuition;
import com.noumi.sms.data.model.Tutor;
import com.noumi.sms.ui.forgotpassword.ForgotPasswordPresenterInterface;
import com.noumi.sms.ui.login.LoginPresenterInterface;
import com.noumi.sms.ui.signup.SignupPresenterInterface;
import com.noumi.sms.ui.students.profile.StudentProfilePresenterInterface;
import com.noumi.sms.ui.students.list.StudentListPresenterInterface;
import com.noumi.sms.ui.tutors.detail.TutorDetailPresenterInterface;
import com.noumi.sms.ui.tutors.profile.TutorProfilePresenterInterface;
import com.noumi.sms.ui.tutors.list.TutorListPresenterInterface;

public interface DatabaseInterface {
    void signupStudent(Student student, String password, SignupPresenterInterface signupPresenter);
    void signupTutor(Tutor tutor, String password, SignupPresenterInterface signupPresenter);
    void LoginUser(String email, String password, String usertype, LoginPresenterInterface loginPresenter);
    void checkLogin(LoginPresenterInterface loginPresenter);
    void getStudents(StudentListPresenterInterface listPresenter);
    void getTutors(TutorListPresenterInterface tutorListPresenter);
    void logoutUser();
    void resetPassword(String email, ForgotPasswordPresenterInterface forgotPasswordPresenter);
    void getStudentsByGender(String gender, StudentListPresenterInterface studentListPresenter);
    void getStudentsByCity(String city, StudentListPresenterInterface studentListPresenter);
    void getStudentsByCityAndGender(String city, String gender, StudentListPresenterInterface studentListPresenter);
    void getTutorsByGender(String gender, TutorListPresenterInterface tutorListPresenter);
    void getTutorsByCity(String city, TutorListPresenterInterface tutorListPresenter);
    void getTutorsByCityAndGender(String city, String gender, TutorListPresenterInterface tutorListPresenter);
    void loadStudent(String studentId, StudentProfilePresenterInterface studentProfilePresenterInterface);
    void loadTutor(String tutorId, TutorProfilePresenterInterface tutorProfilePresenterInterface);
    void loadTutor(String tutorId, TutorDetailPresenterInterface tutorDetailPresenterInterface);
    void addTuition(Tuition tuition, TutorDetailPresenterInterface tutorDetailPresenterInterface);
}
