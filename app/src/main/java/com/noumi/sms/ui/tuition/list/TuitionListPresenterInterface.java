package com.noumi.sms.ui.tuition.list;

//this class defines contract for TuitionListPresenter and these methods are implemented in TuitionListPresenter

import com.noumi.sms.data.model.Tuition;

import java.util.List;

public interface TuitionListPresenterInterface {
    void loadTuitions(String studentId, String userType);
    void onDataLoadComplete(List<Tuition> tuitions);
    void logoutUser();
    void onQueryResult(String result);
}
