package com.noumi.sms.ui.tutors.map;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.noumi.sms.data.database.DatabaseHandler;
import com.noumi.sms.data.database.DatabaseInterface;
import com.noumi.sms.data.maps.MapsHandler;
import com.noumi.sms.data.maps.MapsHandlerInterface;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.data.model.Tutor;

import java.util.List;

public class TutorMapPresenter implements TutorMapPresenterInterface {
    private DatabaseInterface mDatabaseHandler;
    private MapsHandlerInterface mMapsHandler;
    private TutorMapViewInterface mTutorMapViewInterface;

    public TutorMapPresenter(TutorMapViewInterface tutorMapViewInterface) {
        mDatabaseHandler = new DatabaseHandler();
        mMapsHandler = new MapsHandler();
        mTutorMapViewInterface = tutorMapViewInterface;
    }

    @Override
    public void getStudentById(String userId) {
        mDatabaseHandler.loadStudent(userId, this);
    }

    @Override
    public void onQueryResult(String message) {
        mTutorMapViewInterface.onResult(message);
    }

    @Override
    public void onStudentLoad(Student student) {
        mTutorMapViewInterface.onStudentLoad(student);
    }

    @Override
    public void getTutorsByCity(String studentCity) {
        mDatabaseHandler.getTutorsByCity(studentCity, this);
    }

    @Override
    public void onTutorsLoad(List<Tutor> tutors) {
        mTutorMapViewInterface.onTutorsLoad(tutors);
    }

    @Override
    public void getLatLongByCity(String cityName, Context context) {
        mMapsHandler.getLatLongByCity(cityName, context, this);
    }

    @Override
    public void onLatLongByCityLoad(LatLng latLng) {
        mTutorMapViewInterface.onLatLongByCityLoad(latLng);
    }
}
