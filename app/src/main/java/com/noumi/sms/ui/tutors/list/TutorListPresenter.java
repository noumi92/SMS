package com.noumi.sms.ui.tutors.list;

//this class connects StudentListActivity to database and passes feedbacks from database to StudentListActivity to update views and
//feedback to user

import com.noumi.sms.data.database.DatabaseHandler;
import com.noumi.sms.data.database.DatabaseInterface;
import com.noumi.sms.data.model.Tutor;

import java.util.List;

public class TutorListPresenter implements TutorListPresenterInterface {
    private DatabaseInterface mDatabaseHandler;
    private TutorListViewInterface mListViewInterface;

    TutorListPresenter(TutorListViewInterface listViewInterface) {
        mDatabaseHandler = new DatabaseHandler();
        mListViewInterface = listViewInterface;
    }
    //fetch all students from database
    @Override
    public void loadTutors() {
        mDatabaseHandler.getTutors(this);
    }
    //logout user
    @Override
    public void logoutUser() {
        mDatabaseHandler.logoutUser();
    }
    //this method is called when all fetching is complete
    @Override
    public void onDataLoadComplete(List<Tutor> tutors) {
        mListViewInterface.onLoadComplete(tutors);
    }
    //method to get feedback from database handler and pass to StudentListActivity
    @Override
    public void onQueryResult(String result) {
        mListViewInterface.onResult(result);
    }
    //get student based on selected filters
    @Override
    public void getFilteredTutors(String city, String gender, int filter) {
        switch (filter){
            case 1:
                mDatabaseHandler.getTutorsByCity(city, this);
                break;
            case 2:
                mDatabaseHandler.getTutorsByGender(gender, this);
                break;
            case 3:
                mDatabaseHandler.getTutorsByCityAndGender(city, gender, this);
                break;
            default:
                mDatabaseHandler.getTutors(this);
        }
    }
}
