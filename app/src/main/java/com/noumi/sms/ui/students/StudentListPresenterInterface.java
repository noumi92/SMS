package com.noumi.sms.ui.students;

//this class defines contract for StudentListPresenter and these methods are implemented in StudentListPresenter

import com.noumi.sms.data.model.Student;
import java.util.List;

public interface StudentListPresenterInterface {
    void loadStudents();
    void onDataLoadComplete(List<Student> students);
    void logoutUser();
    void getStudentsByGender(String gender);
    void getStudentsByCity(String city);
    void getstudentsByCityAndGender(String city, String gender);
}
