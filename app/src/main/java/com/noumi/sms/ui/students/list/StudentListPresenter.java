package com.noumi.sms.ui.students.list;

//this class connects StudentListActivity to database and passes feedbacks from database to StudentListActivity to update views and
//feedback to user

import com.noumi.sms.data.database.DatabaseHandler;
import com.noumi.sms.data.database.DatabaseInterface;
import com.noumi.sms.data.model.Student;

import java.util.List;

public class StudentListPresenter implements StudentListPresenterInterface {
    private DatabaseInterface mDatabaseHandler;
    private StudentListViewInterface mListViewInterface;

    StudentListPresenter(StudentListViewInterface listViewInterface) {
        mDatabaseHandler = new DatabaseHandler();
        mListViewInterface = listViewInterface;
    }
    //fetch all students from database
    @Override
    public void loadStudents() {
        mDatabaseHandler.getStudents(this);
    }
    //logout user
    @Override
    public void logoutUser() {
        mDatabaseHandler.logoutUser();
    }
    //this method is called when all fetching is complete
    @Override
    public void onDataLoadComplete(List<Student> students) {
        mListViewInterface.onLoadComplete(students);
    }
    //method to get feedback from database handler and pass to StudentListActivity
    @Override
    public void onQueryResult(String result) {
        mListViewInterface.onResult(result);
    }
    //get student based on selected filters
    @Override
    public void getFilteredStudents(String city, String gender, int filter) {
        switch (filter){
            case 1:
                mDatabaseHandler.getStudentsByCity(city, this);
                break;
            case 2:
                mDatabaseHandler.getStudentsByGender(gender, this);
                break;
            case 3:
                mDatabaseHandler.getStudentsByCityAndGender(city, gender, this);
                break;
            default:
                mDatabaseHandler.getStudents(this);
        }
    }
}
