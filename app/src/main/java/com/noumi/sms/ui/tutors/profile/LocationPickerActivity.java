package com.noumi.sms.ui.tutors.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.noumi.sms.R;

public class LocationPickerActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mLocationPickerMap;
    private SupportMapFragment mLocationPickerFragment;
    private MarkerOptions mTutorMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_map);
        mLocationPickerFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.location_picker_map);
        getLocationIntent();
        if(mLocationPickerFragment != null){
            mLocationPickerFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mLocationPickerMap = googleMap;
        mLocationPickerMap.addMarker(mTutorMarker);
        mLocationPickerMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mTutorMarker.getPosition(),15));
    }

    private void getLocationIntent(){
        if(getIntent().hasExtra("tutorMarker")){
            double[] latlang = getIntent().getDoubleArrayExtra("tutorMarker");
            LatLng tutorLatLng = new LatLng(latlang[0],latlang[1]);
            mTutorMarker = new MarkerOptions().position(tutorLatLng);
        }
    }
}
