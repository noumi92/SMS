package com.noumi.sms.data.maps;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.noumi.sms.ui.tutors.map.TutorMapPresenter;

import java.util.List;
public class MapsHandler implements MapsHandlerInterface {
    private Geocoder mGeocoder;

    @Override
    public void getLatLongByCity(String cityName, Context context, TutorMapPresenter tutorMapPresenter) {
        mGeocoder = new Geocoder(context);
        List<Address> addressList = null;
        try {
            addressList = mGeocoder.getFromLocationName(cityName,1);
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            tutorMapPresenter.onLatLongByCityLoad(latLng);
        }catch (Exception e){
            tutorMapPresenter.onQueryResult("Location fetch failed: " + e.getMessage());
        }

    }
}
