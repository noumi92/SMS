package com.noumi.sms.data.database;
//this interface defines the contract for DatabaseHandler class

import com.noumi.sms.data.model.Chat;
import com.noumi.sms.data.model.Message;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.data.model.Tuition;
import com.noumi.sms.data.model.Tutor;
import com.noumi.sms.ui.chat.list.ChatListPresenter;
import com.noumi.sms.ui.chat.room.ChatRoomPresenter;
import com.noumi.sms.ui.chat.room.ChatRoomPresenterInterface;
import com.noumi.sms.ui.forgotpassword.ForgotPasswordPresenterInterface;
import com.noumi.sms.ui.login.LoginPresenterInterface;
import com.noumi.sms.ui.signup.SignupPresenterInterface;
import com.noumi.sms.ui.students.list.StudentListPresenterInterface;
import com.noumi.sms.ui.students.profile.StudentProfilePresenterInterface;
import com.noumi.sms.ui.tuition.detail.TuitionDetailPresenter;
import com.noumi.sms.ui.tuition.detail.TuitionDetailPresenterInterface;
import com.noumi.sms.ui.tuition.list.TuitionListPresenterInterface;
import com.noumi.sms.ui.tutors.detail.TutorDetailPresenterInterface;
import com.noumi.sms.ui.tutors.list.TutorListPresenterInterface;
import com.noumi.sms.ui.tutors.map.TutorMapPresenterInterface;
import com.noumi.sms.ui.tutors.profile.TutorProfilePresenterInterface;

public interface DatabaseInterface {
    //TODO 1: segregate methods activity wise
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
    void getTutorsByCity(String city, TutorMapPresenterInterface tutorMapPresenter);
    void getTutorsByCityAndGender(String city, String gender, TutorListPresenterInterface tutorListPresenter);
    void loadStudent(String studentId, StudentProfilePresenterInterface studentProfilePresenterInterface);
    void loadStudent(String studentId, TutorMapPresenterInterface tutorMapPresenter);
    void loadTutor(String tutorId, TutorProfilePresenterInterface tutorProfilePresenterInterface);
    void loadTutor(String tutorId, TutorDetailPresenterInterface tutorDetailPresenterInterface);
    void addTuition(Tuition tuition, TutorDetailPresenterInterface tutorDetailPresenterInterface);
    void getTuitionsByStudentId(String studentId, TuitionListPresenterInterface tuitionListPresenter);
    void getTutorById(String tutorId, TuitionListPresenterInterface tuitionListPresenter);
    void loadTuition(String tuitionId, TuitionDetailPresenter tuitionDetailPresenter);
    void loadTutor(String tutorId, TuitionDetailPresenter tuitionDetailPresenter);
    void getChatsByStudentId(String studentId, ChatListPresenter chatListPresenter);
    void getChatsByTutorId(String tutorId, ChatListPresenter chatListPresenter);
    void addChat(Chat chat, TutorDetailPresenterInterface tutorDetailPresenter);
    void getMessages(String threadId, ChatRoomPresenter chatRoomPresenter);
    void sendMessage(String chatId, Message message, ChatRoomPresenterInterface chatRoomPresenter);
    void getTuitionsByTutorId(String userId, TuitionListPresenterInterface tuitionListPresenter);
    void loadRating(String ratingId, TuitionDetailPresenterInterface tuitionDetailPresenter);
    void updateStudent(StudentProfilePresenterInterface studentProfilePresenter, Student student);
    void updateTutor(Tutor tutor, TutorProfilePresenterInterface tutorProfilePresenter);
}
