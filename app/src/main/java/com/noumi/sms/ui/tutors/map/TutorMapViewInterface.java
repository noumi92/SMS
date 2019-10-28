package com.noumi.sms.ui.tutors.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.data.model.Tutor;

import java.util.List;

public interface TutorMapViewInterface extends OnMapReadyCallback {
    @Override
    void onMapReady(GoogleMap googleMap);
    void onResult(String message);
    void onStudentLoad(Student student);
    void onTutorsLoad(List<Tutor> tutors);
    void onLatLongByCityLoad(LatLng latLng);
}
