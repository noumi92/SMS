package com.noumi.sms.ui.tutors.detail;

import com.noumi.sms.data.DatabaseHandler;
import com.noumi.sms.data.DatabaseInterface;
import com.noumi.sms.data.model.Tuition;
import com.noumi.sms.data.model.Tutor;

public class TutorDetailPresenter implements TutorDetailPresenterInterface {
    private DatabaseInterface mDatabaseHandler;
    private TutorDetailViewInterface mTutorDetailViewInterface;
    public TutorDetailPresenter(TutorDetailViewInterface tutorDetailViewInterface) {
        mTutorDetailViewInterface = tutorDetailViewInterface;
        mDatabaseHandler = new DatabaseHandler();
    }

    @Override
    public void loadTutor(String tutorId) {
        mDatabaseHandler.loadTutor(tutorId, this);
    }

    @Override
    public void addTuition(Tuition tuition) {
        mDatabaseHandler.addTuition(tuition, this);
    }

    @Override
    public void onDataLoad(Tutor tutor) {
        mTutorDetailViewInterface.onLoadComplete(tutor);
    }

    @Override
    public void onQueryResult(String result) {
        mTutorDetailViewInterface.onResult(result);
    }

    @Override
    public void logoutUser() {
        mDatabaseHandler.logoutUser();
    }
}
