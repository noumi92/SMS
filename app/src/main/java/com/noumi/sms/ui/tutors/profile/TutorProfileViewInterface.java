package com.noumi.sms.ui.tutors.profile;

import com.noumi.sms.data.model.Tutor;

public interface TutorProfileViewInterface {
    void onLoadComplete(Tutor tutor);
    void onResult(String message);
    void onTutorUpdateSuccess();
}
