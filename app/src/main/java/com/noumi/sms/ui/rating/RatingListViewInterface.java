package com.noumi.sms.ui.rating;

//this class defines contract for StudentListActivity and these methods are implemented in StudentListActivity

import com.noumi.sms.data.model.Rating;
import com.noumi.sms.data.model.Student;

import java.util.List;

public interface RatingListViewInterface {
    void onLoadComplete(List<Rating> ratings, List<Student> students);
    void onResult(String result);
}
