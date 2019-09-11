package com.noumi.sms.ui.tutors.list;

//this class defines contract for TutorListPresenter and these methods are implemented in TutorListPresenter

import com.noumi.sms.data.model.Tutor;

import java.util.List;

public interface TutorListPresenterInterface {
    void loadTutors();
    void onDataLoadComplete(List<Tutor> tutors);
    void logoutUser();
    void onQueryResult(String result);
    void getFilteredTutors(String city, String gender, int filter);
}
