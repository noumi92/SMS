package com.noumi.sms.ui.tuition.detail;

import com.noumi.sms.data.model.Rating;
import com.noumi.sms.data.model.Tuition;
import com.noumi.sms.data.model.Tutor;

public interface TuitionDetailPresenterInterface {
    void onTuitionLoad(Tuition tuition);
    void onTutorLoad(Tutor tutor);
    void onQueryResult(String result);
    void logoutUser();
    void loadTuition(String tuitionId);
    void loadTutor(String tutorId);
    void loadRating(String ratingId);
    void onRatingLoad(Rating rating);
    void updateTuitionRating(Rating rating);
    void deleteTuition(String tuitionId);
    void updateTuition(Tuition tuition);
    void onRatingUpdate();
    void onTuitionUpdate();
    void onTuitionDelete();
}

