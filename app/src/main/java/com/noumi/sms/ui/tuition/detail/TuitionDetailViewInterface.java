package com.noumi.sms.ui.tuition.detail;

import com.noumi.sms.data.model.Rating;
import com.noumi.sms.data.model.Tuition;
import com.noumi.sms.data.model.Tutor;

public interface TuitionDetailViewInterface {
    void onTuitionLoad(Tuition tuition);
    void onTutorLoad(Tutor tutor);
    void onResult(String message);
    void onRatingLoad(Rating rating);
}
