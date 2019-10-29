package com.noumi.sms.ui.tutors.profile;

import com.google.firebase.firestore.GeoPoint;
public interface TutorLocationPresenterInterface {
    void updateTutorLocationById(String id, GeoPoint geoPoint);
    void onTutorLocationUpdate();
    void OnQueryResult(String tutor_lcation_updated);
}
