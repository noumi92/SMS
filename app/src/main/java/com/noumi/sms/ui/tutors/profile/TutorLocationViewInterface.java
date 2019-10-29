package com.noumi.sms.ui.tutors.profile;

import com.google.android.gms.maps.OnMapReadyCallback;
public interface TutorLocationViewInterface extends OnMapReadyCallback {
    void onTutorLocationUpdate();
    void onResult(String message);
}
