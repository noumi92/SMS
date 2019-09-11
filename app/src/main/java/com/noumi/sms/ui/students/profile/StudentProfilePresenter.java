package com.noumi.sms.ui.students.profile;

import com.noumi.sms.data.DatabaseHandler;
import com.noumi.sms.data.DatabaseInterface;
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
}
