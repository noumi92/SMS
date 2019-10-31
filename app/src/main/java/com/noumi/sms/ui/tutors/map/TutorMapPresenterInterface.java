package com.noumi.sms.ui.tutors.map;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.data.model.Tutor;

import java.util.List;
public interface TutorMapPresenterInterface {
    void getStudentById(String userId);
    void onQueryResult(String message);
    void onStudentLoad(Student student);
    void getTutorsByCity(String studentCity);
    void onTutorsLoad(List<Tutor> tutors);
    void getLatLongByCity(String cityName, Context context);
    void onLatLongByCityLoad(LatLng latLng);
}
