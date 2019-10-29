package com.noumi.sms.ui.tutors.profile;

import com.google.firebase.firestore.GeoPoint;
import com.noumi.sms.data.database.DatabaseHandler;
import com.noumi.sms.data.database.DatabaseInterface;
public class TutorLocationPresenter implements TutorLocationPresenterInterface {
    private DatabaseInterface mDatabaseHandler;
    private TutorLocationViewInterface mTutorLocationViewInterface;

    public TutorLocationPresenter(TutorLocationViewInterface tutorLocationViewInterface) {
        mDatabaseHandler = new DatabaseHandler();
        mTutorLocationViewInterface = tutorLocationViewInterface;
    }

    @Override
    public void updateTutorLocationById(String id, GeoPoint geoPoint) {
        mDatabaseHandler.updateTutorLocationById(id, geoPoint, this);
    }

    @Override
    public void onTutorLocationUpdate() {
        mTutorLocationViewInterface.onTutorLocationUpdate();
    }

    @Override
    public void OnQueryResult(String message) {
        mTutorLocationViewInterface.onResult(message);
    }
}
