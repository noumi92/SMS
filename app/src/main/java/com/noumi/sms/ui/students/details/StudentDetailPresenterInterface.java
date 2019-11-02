package com.noumi.sms.ui.students.details;

import com.noumi.sms.data.model.Student;
import com.noumi.sms.data.model.Tuition;

public interface StudentDetailPresenterInterface {
    void onDataLoad(Student student);
    void onQueryResult(String result);
    void loadStudent(String studentId);
    void logoutUser();
	void addTuition(Tuition tuition);
    void onOfferTuitionSuccess(String tuitionId);
}
