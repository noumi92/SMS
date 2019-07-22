package com.noumi.sms.ui.students;

//this class defines contract for StudentListPresenter and these methods are implemented in StudentListPresenter

import com.noumi.sms.data.model.Student;

import java.util.List;

public interface StudentListPresenterInterface {
    void loadStudents();
    void onDataLoadComplete(List<Student> students);
    void logoutUser();
    void getFilteredStudents(String city, String gender, int filter);
    void onQueryResult(String result);
}
