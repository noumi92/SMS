package com.noumi.sms.ui.tutors.map;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.noumi.sms.R;
import com.noumi.sms.data.model.LoggedInUser;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.data.model.Tutor;
import java.util.ArrayList;
import java.util.List;

public class TutorMapActivity extends FragmentActivity implements TutorMapViewInterface {

    private GoogleMap mMap;
    private List<Tutor> mTutorList;
    private SupportMapFragment mMapFragment;
    private TutorMapPresenterInterface mTutorMapPresenter;
    private Student mStudent;
    private Spinner mCitySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_map);

        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mTutorMapPresenter = new TutorMapPresenter(this);
        mCitySpinner = (Spinner) findViewById(R.id.city_spinner);
        mTutorMapPresenter.checkLocationPermission(this);
        if (mMapFragment != null) {
            mMapFragment.getMapAsync(this);
        }
        mCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTutorMapPresenter.getLatLongByCity(mCitySpinner.getSelectedItem().toString(), TutorMapActivity.this);
                mTutorMapPresenter.getTutorsByCity(mCitySpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(10.0f);
        mMap.setMaxZoomPreference(18.0f);
        mTutorMapPresenter.getStudentById(LoggedInUser.getLoggedInUser().getUserId());
    }

    @Override
    public void onResult(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStudentLoad(Student student) {
        mStudent = student;
        mTutorMapPresenter.getLatLongByCity(mStudent.getStudentCity(), TutorMapActivity.this);
        mTutorMapPresenter.getTutorsByCity(mStudent.getStudentCity());
    }

    @Override
    public void onTutorsLoad(List<Tutor> tutors) {
        mTutorList = new ArrayList<>();
        mTutorList = tutors;
        List<Marker> markers = new ArrayList<>();
        for(Tutor tutor : mTutorList){
            LatLng latLng = new LatLng(tutor.getTutorLocation().getLatitude(), tutor.getTutorLocation().getLongitude());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title(tutor.getTutorName())
                    .snippet(tutor.getTutorSubjects().toString());
            Marker marker = mMap.addMarker(markerOptions);
            markers.add(marker);
        }
        if(markers.size() != 0) {
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(markers.get(0).getPosition()));
        }else{
            Toast.makeText(this, "No Tutors Found in " + mCitySpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLatLongByCityLoad(LatLng latLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}