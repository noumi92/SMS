package com.noumi.sms.ui.tutors.detail;

import com.noumi.sms.data.database.DatabaseHandler;
import com.noumi.sms.data.database.DatabaseInterface;
import com.noumi.sms.data.model.Chat;
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
    public void addChat(Chat chat) {
        mDatabaseHandler.addChat(chat, this);
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

    @Override
    public void onApplyTuitionSuccess(String tuitionId) {
        mTutorDetailViewInterface.onApplyTuitionSuccess(tuitionId);
    }

    @Override
    public void onAddChatSuccess(String chatId) {
        mTutorDetailViewInterface.onAddChatSuccess(chatId);
    }
}
