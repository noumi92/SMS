package com.noumi.sms.ui.rating;

//this class connects StudentListActivity to database and passes feedbacks from database to StudentListActivity to update views and
//feedback to user

import com.noumi.sms.data.database.DatabaseHandler;
import com.noumi.sms.data.database.DatabaseInterface;
import com.noumi.sms.data.model.Rating;
import com.noumi.sms.data.model.Student;

import java.util.List;

public class RatingListPresenter implements RatingListPresenterInterface {
    private DatabaseInterface mDatabaseHandler;
    private RatingListViewInterface mListViewInterface;

    public RatingListPresenter(RatingListViewInterface listViewInterface) {
        mDatabaseHandler = new DatabaseHandler();
        mListViewInterface = listViewInterface;
    }
    //fetch all students from database
    @Override
    public void loadRatingsByTutorId(String tutorId) {
        mDatabaseHandler.getRatingsByTutorId(tutorId, this);
    }
    //logout user
    @Override
    public void logoutUser() {
        mDatabaseHandler.logoutUser();
    }
    //this method is called when all fetching is complete
    @Override
    public void onDataLoadComplete(List<Rating> ratings, List<Student> students) {
        mListViewInterface.onLoadComplete(ratings, students);
    }
    //method to get feedback from database handler and pass to StudentListActivity
    @Override
    public void onQueryResult(String result) {
        mListViewInterface.onResult(result);
    }
}
