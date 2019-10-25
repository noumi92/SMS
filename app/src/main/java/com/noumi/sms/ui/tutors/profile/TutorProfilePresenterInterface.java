package com.noumi.sms.ui.tutors.profile;

import com.noumi.sms.data.model.Tutor;

public interface TutorProfilePresenterInterface {
    void onDataLoad(Tutor tutor);
    void onQueryResult(String result);
    void logoutUser();
    void loadTutor(String tutorId);
    void updateTutor(Tutor tutor);
    void onUpdateTutorSuccess();
}
