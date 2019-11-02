package com.noumi.sms.ui.students.details;

import com.noumi.sms.data.database.DatabaseHandler;
import com.noumi.sms.data.database.DatabaseInterface;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.data.model.Tuition;

public class StudentDetailPresenter implements StudentDetailPresenterInterface {
    private DatabaseInterface mDatabaseHandler;
    private StudentDetailViewInterface mStudentDetailViewInterface;

    public StudentDetailPresenter(StudentDetailViewInterface studentDetailViewInterface) {
        mDatabaseHandler = new DatabaseHandler();
        mStudentDetailViewInterface = studentDetailViewInterface;
    }

    @Override
    public void onDataLoad(Student student) {
        mStudentDetailViewInterface.onLoadComplete(student);
    }

    @Override
    public void onQueryResult(String result) {
        mStudentDetailViewInterface.onResult(result);
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
    public void addTuition(Tuition tuition) {
        mDatabaseHandler.addTuition(tuition, this);
    }

    @Override
    public void onOfferTuitionSuccess(String tuitionId) {

    }
}
