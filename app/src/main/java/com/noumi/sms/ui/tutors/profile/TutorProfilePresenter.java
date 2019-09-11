package com.noumi.sms.ui.tutors.profile;

import com.noumi.sms.data.DatabaseHandler;
import com.noumi.sms.data.DatabaseInterface;
import com.noumi.sms.data.model.Tutor;

public class TutorProfilePresenter implements TutorProfilePresenterInterface {
    private DatabaseInterface mDatabaseHandler;
    private TutorProfileViewInterface mTutorProfileViewInterface;
    public TutorProfilePresenter(TutorProfileViewInterface tutorProfileViewInterface) {
        mTutorProfileViewInterface = tutorProfileViewInterface;
        mDatabaseHandler = new DatabaseHandler();
    }

    @Override
    public void loadTutor(String tutorId) {
        mDatabaseHandler.loadTutor(tutorId, this);
    }

    @Override
    public void onDataLoad(Tutor tutor) {
        mTutorProfileViewInterface.onLoadComplete(tutor);
    }

    @Override
    public void onQueryResult(String result) {
        mTutorProfileViewInterface.onResult(result);
    }

    @Override
    public void logoutUser() {
        mDatabaseHandler.logoutUser();
    }
}
