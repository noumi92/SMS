package com.noumi.sms.ui.students.list;

//this class defines contract for TutorListPresenter and these methods are implemented in TutorListPresenter

import com.noumi.sms.data.model.Student;

import java.util.List;

public interface StudentListPresenterInterface {
    void loadStudents();
    void onDataLoadComplete(List<Student> students);
    void logoutUser();
    void getFilteredStudents(String city, String gender, int filter);
    void onQueryResult(String result);
}
