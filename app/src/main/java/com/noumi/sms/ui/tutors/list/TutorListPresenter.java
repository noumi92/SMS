package com.noumi.sms.ui.tutors.list;

//this class connects StudentListActivity to database and passes feedbacks from database to StudentListActivity to update views and
//feedback to user

import android.text.TextUtils;

import com.noumi.sms.data.database.DatabaseHandler;
import com.noumi.sms.data.database.DatabaseInterface;
import com.noumi.sms.data.model.Tutor;

import java.util.ArrayList;
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

    @Override
    public List<Tutor> filterTutorsByCity(List<Tutor> tutors, String city) {
        List<Tutor> filteredTutors = new ArrayList<>();
        for (Tutor tutor : tutors){
            if(TextUtils.equals(tutor.getTutorCity(), city)){
                filteredTutors.add(tutor);
            }
        }
        return filteredTutors;
    }

    @Override
    public List<Tutor> filterTutorsByGender(List<Tutor> tutors, String gender) {
        List<Tutor> filteredTutors = new ArrayList<>();
        for (Tutor tutor : tutors){
            if(TextUtils.equals(tutor.getTutorGender(), gender)){
                filteredTutors.add(tutor);
            }
        }
        return filteredTutors;
    }

    @Override
    public List<Tutor> filterTutorsByFee(List<Tutor> tutors, int fee) {
        List<Tutor> filteredTutors = new ArrayList<>();
        for (Tutor tutor : tutors){
            if(tutor.getTutorFee() <= fee){
                filteredTutors.add(tutor);
            }
        }
        return filteredTutors;
    }

    @Override
    public List<Tutor> filterTutorsBySubject(List<Tutor> tutors, String subject) {
        List<Tutor> filteredTutors = new ArrayList<>();
        for (Tutor tutor : tutors){
            for(String tutorSubject: tutor.getTutorSubjects()) {
                if (TextUtils.equals(tutor.getTutorGender(), subject)) {
                    filteredTutors.add(tutor);
                }
            }
        }
        return filteredTutors;
    }

    @Override
    public List<Tutor> filterTutorsByDegree(List<Tutor> tutors, String degreeName) {
        List<Tutor> filteredTutors = new ArrayList<>();
        for (Tutor tutor : tutors){
            if(TextUtils.equals(tutor.getTutorDegreeName(), degreeName)){
                filteredTutors.add(tutor);
            }
        }
        return filteredTutors;
    }
}