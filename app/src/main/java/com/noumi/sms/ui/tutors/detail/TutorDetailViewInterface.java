package com.noumi.sms.ui.tutors.detail;

import com.noumi.sms.data.model.Tutor;

public interface TutorDetailViewInterface {
    void onLoadComplete(Tutor tutor);
    void onResult(String message);
}
