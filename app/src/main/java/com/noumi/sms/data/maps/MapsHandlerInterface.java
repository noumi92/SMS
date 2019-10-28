package com.noumi.sms.data.maps;

import android.content.Context;

import com.noumi.sms.ui.tutors.map.TutorMapPresenter;

public interface MapsHandlerInterface {
    void getLatLongByCity(String cityName, Context context, TutorMapPresenter tutorMapPresenter);
}
