package com.noumi.sms.ui.tutors.profile;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.noumi.sms.data.model.Tutor;

public interface TutorProfileViewInterface extends OnMapReadyCallback {
    void onLoadComplete(Tutor tutor);
    void onResult(String message);
    void onTutorUpdateSuccess();
    void onDeleteAccount();
}
