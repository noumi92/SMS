package com.noumi.sms.ui.tutors.list;

//this class defines contract for TuitionListPresenter and these methods are implemented in TuitionListPresenter

import com.noumi.sms.data.model.Tutor;

import java.util.List;

public interface TutorListPresenterInterface {
    void loadTutors();
    void onDataLoadComplete(List<Tutor> tutors);
    void logoutUser();
    void onQueryResult(String result);
    List<Tutor> filterTutorsByCity(List<Tutor> tutor, String city);
    List<Tutor> filterTutorsByGender(List<Tutor> tutors, String gender);
    List<Tutor> filterTutorsByFee(List<Tutor> tutors, int fee);
    List<Tutor> filterTutorsBySubject(List<Tutor> tutors, String subject);
    List<Tutor> filterTutorsByDegree(List<Tutor> tutors, String degreeName);
}
