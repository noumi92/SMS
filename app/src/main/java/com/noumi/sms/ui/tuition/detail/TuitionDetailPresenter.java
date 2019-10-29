package com.noumi.sms.ui.tuition.detail;

import com.noumi.sms.data.database.DatabaseHandler;
import com.noumi.sms.data.database.DatabaseInterface;
import com.noumi.sms.data.model.Rating;
import com.noumi.sms.data.model.Tuition;
import com.noumi.sms.data.model.Tutor;

public class TuitionDetailPresenter implements TuitionDetailPresenterInterface {
    private DatabaseInterface mDatabaseHandler;
    private TuitionDetailViewInterface mTuitionDetailView;

    public TuitionDetailPresenter(TuitionDetailViewInterface tuitionDetailView) {
        mDatabaseHandler = new DatabaseHandler();
        mTuitionDetailView = tuitionDetailView;
    }

    @Override
    public void onTuitionLoad(Tuition tuition) {
        mTuitionDetailView.onTuitionLoad(tuition);
    }

    @Override
    public void onTutorLoad(Tutor tutor) {
        mTuitionDetailView.onTutorLoad(tutor);
    }

    @Override
    public void onQueryResult(String result) {
        mTuitionDetailView.onResult(result);
    }

    @Override
    public void logoutUser() {
        mDatabaseHandler.logoutUser();
    }

    @Override
    public void loadTuition(String tuitionId) {
        mDatabaseHandler.loadTuition(tuitionId, this);
    }

    @Override
    public void loadTutor(String tutorId) {
        mDatabaseHandler.loadTutor(tutorId, this);
    }

    @Override
    public void loadRating(String ratingId) {
        mDatabaseHandler.loadRating(ratingId, this);
    }

    @Override
    public void updateTuitionRating(Rating rating) {
        mDatabaseHandler.updateTuitionRating(rating, this);
    }

    @Override
    public void updateTuition(Tuition tuition) {
        mDatabaseHandler.updateTuition(tuition, this);
    }

    @Override
    public void deleteTuition(String tuitionId) {
        mDatabaseHandler.deleteTuition(tuitionId, this);
    }

    @Override
    public void onRatingLoad(Rating rating) {
        mTuitionDetailView.onRatingLoad(rating);
    }

    @Override
    public void onRatingUpdate() {
        mTuitionDetailView.onRatingUpdate();
    }

    @Override
    public void onTuitionUpdate() {
        mTuitionDetailView.onTuitionUpdate();
    }

    @Override
    public void onTuitionDelete() {
        mTuitionDetailView.onTuitionDelete();
    }
}
