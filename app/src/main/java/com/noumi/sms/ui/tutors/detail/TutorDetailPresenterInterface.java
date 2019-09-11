package com.noumi.sms.ui.tutors.detail;

import com.noumi.sms.data.model.Tuition;
import com.noumi.sms.data.model.Tutor;

public interface TutorDetailPresenterInterface {
    void onDataLoad(Tutor tutor);
    void onQueryResult(String result);
    void logoutUser();
    void loadTutor(String tutorId);
    void addTuition(Tuition tuition);
}
