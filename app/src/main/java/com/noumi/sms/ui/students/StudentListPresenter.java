package com.noumi.sms.ui.students;

//this class connects StudentListActivity to database and passes feedbacks from database to StudentListActivity to update views and
//feedback to user

import com.noumi.sms.data.DatabaseHandler;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.ui.base.BasePresenter;
import java.util.List;

public class StudentListPresenter extends BasePresenter implements StudentListPresenterInterface {
    private DatabaseHandler mDatabaseHandler;
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
        mDatabaseHandler.logoutUser(this);
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
    //fetch students filtered by gender
    @Override
    public void getStudentsByGender(String gender) {
        mDatabaseHandler.getStudentsByGender(gender, this);
    }
    //fetch students filtered by city
    @Override
    public void getStudentsByCity(String city) {
        mDatabaseHandler.getStudentsByCity(city, this);
    }
    //fetch students filtered by gender and city both
    @Override
    public void getstudentsByCityAndGender(String city, String gender) {
        mDatabaseHandler.getStudentsByCityAndGender(city, gender, this);
    }
}
