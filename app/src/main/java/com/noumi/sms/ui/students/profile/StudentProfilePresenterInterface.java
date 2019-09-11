package com.noumi.sms.ui.students.profile;

import com.noumi.sms.data.model.Student;

public interface StudentProfilePresenterInterface {
    void onDataLoad(Student student);
    void onQueryResult(String result);
    void loadStudent(String studentId);
    void logoutUser();
}
