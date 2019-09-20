package com.noumi.sms.ui.tuition.list;

//this class connects StudentListActivity to database and passes feedbacks from database to StudentListActivity to update views and
//feedback to user

import com.noumi.sms.data.database.DatabaseHandler;
import com.noumi.sms.data.database.DatabaseInterface;
import com.noumi.sms.data.model.Tuition;

import java.util.List;

public class TuitionListPresenter implements TuitionListPresenterInterface {
    private DatabaseInterface mDatabaseHandler;
    private TuitionListViewInterface mTuitionListViewInterface;

    TuitionListPresenter(TuitionListViewInterface listViewInterface) {
        mDatabaseHandler = new DatabaseHandler();
        mTuitionListViewInterface = listViewInterface;
    }
    //fetch all students from database
    @Override
    public void loadTuitions(String userId, String userType) {
        if(userType.equals("student")){
            mDatabaseHandler.getTuitionsByStudentId(userId, this);
        }else if(userType.equals("tutor")){
            mDatabaseHandler.getTuitionsByTutorId(userId, this);
        }
    }
    //logout user
    @Override
    public void logoutUser() {
        mDatabaseHandler.logoutUser();
    }
    //this method is called when all fetching is complete
    @Override
    public void onDataLoadComplete(List<Tuition> tuitions) {
        mTuitionListViewInterface.onLoadComplete(tuitions);
    }
    //method to get feedback from database handler and pass to StudentListActivity
    @Override
    public void onQueryResult(String result) {
        mTuitionListViewInterface.onResult(result);
    }
}
