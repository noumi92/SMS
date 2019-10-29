package com.noumi.sms.ui.signup;

//this class defines contract for SignupActivity and these methods are implemented in SignupActivity

import com.google.android.gms.maps.model.LatLng;
public interface SignupViewInterface {
    void onResult(String message);
    void onLatLongByCityLoad(LatLng latLng);
    void onSignupSuccess();
}
