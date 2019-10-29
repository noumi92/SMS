package com.noumi.sms.ui.students.profile;

import com.noumi.sms.data.model.Student;

public interface StudentProfileViewInterface {
    void onLoadComplete(Student student);
    void onResult(String message);
    void onUpdateStudentSuccess();
    void onDeleteAccount();
}