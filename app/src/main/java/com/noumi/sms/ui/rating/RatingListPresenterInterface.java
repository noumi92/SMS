package com.noumi.sms.ui.rating;

//this class defines contract for TuitionListPresenter and these methods are implemented in TuitionListPresenter

import com.noumi.sms.data.model.Rating;
import com.noumi.sms.data.model.Student;

import java.util.List;

public interface RatingListPresenterInterface {
    void loadRatingsByTutorId(String tutorId);
    void onDataLoadComplete(List<Rating> ratings, List<Student> students);
    void logoutUser();
    void onQueryResult(String result);
}
