package com.noumi.sms.ui.students.profile;

import com.noumi.sms.data.database.DatabaseHandler;
import com.noumi.sms.data.database.DatabaseInterface;
import com.noumi.sms.data.model.Student;

public class StudentProfilePresenter implements StudentProfilePresenterInterface {
    private DatabaseInterface mDatabaseHandler;
    private StudentProfileViewInterface mStudentProfileViewInterface;

    public StudentProfilePresenter(StudentProfileViewInterface studentProfileViewInterface) {
        mDatabaseHandler = new DatabaseHandler();
        mStudentProfileViewInterface = studentProfileViewInterface;
    }

    @Override
    public void onDataLoad(Student student) {
        mStudentProfileViewInterface.onLoadComplete(student);
    }

    @Override
    public void onQueryResult(String result) {
        mStudentProfileViewInterface.onResult(result);
    }

    @Override
    public void loadStudent(String studentId) {
        mDatabaseHandler.loadStudent(studentId, this);
    }

    @Override
    public void logoutUser() {
        mDatabaseHandler.logoutUser();
    }

    @Override
    public void updateStudent(Student student) {
        mDatabaseHandler.updateStudent(this, student);
    }

    @Override
    public void onUpdateStudentSuccess() {
        mStudentProfileViewInterface.onUpdateStudentSuccess();
    }

    @Override
    public void deleteStudentById(String userId) {
        mDatabaseHandler.deleteStudentById(userId, this);
    }

    @Override
    public void onDeleteAccount() {
        mStudentProfileViewInterface.onDeleteAccount();
    }
}
