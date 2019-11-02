package com.noumi.sms.ui.students.details;

import com.noumi.sms.data.model.Student;

public interface StudentDetailViewInterface {
    void onLoadComplete(Student student);
    void onResult(String message);
    void onOfferTuitionSuccess(String tuitionId);
}