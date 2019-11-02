package com.noumi.sms.ui.tutors.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;
import com.noumi.sms.R;
import com.noumi.sms.data.model.LoggedInUser;

public class TutorLocationActivity extends AppCompatActivity implements TutorLocationViewInterface {

    private static final int FINE_LOCATION_REQUEST = 1;
    private GoogleMap mLocationPickerMap;
    private SupportMapFragment mLocationPickerFragment;
    private MarkerOptions mTutorMarker;
    private Button mUpdateTutorLocation;
    private TutorLocationPresenterInterface mTutorLocationPresenter;
    private boolean mLocationPermissionGranted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_map);
        //get references and initialize variables
        mLocationPickerFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.location_picker_map);
        mUpdateTutorLocation = (Button) findViewById(R.id.update_tutor_location);

        mTutorLocationPresenter = new TutorLocationPresenter(this);
        getLocationIntent();
        getLocationPermissions();
        if(mLocationPickerFragment != null){
            mLocationPickerFragment.getMapAsync(this);
        }
        mUpdateTutorLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = LoggedInUser.getLoggedInUser().getUserId();
                GeoPoint geoPoint = new GeoPoint(mTutorMarker.getPosition().latitude, mTutorMarker.getPosition().longitude);
                mTutorLocationPresenter.updateTutorLocationById(id, geoPoint);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mLocationPickerMap = googleMap;
        updateUI();
        mLocationPickerMap.addMarker(mTutorMarker);
        mLocationPickerMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mTutorMarker.getPosition(),15));
        mLocationPickerMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {}
            @Override
            public void onMarkerDrag(Marker marker) {}
            @Override
            public void onMarkerDragEnd(Marker marker) {
                mTutorMarker.position(marker.getPosition());
                mUpdateTutorLocation.setEnabled(true);
            }
        });
        mLocationPickerMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
            @Override
            public void onMyLocationClick(@NonNull Location location) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mTutorMarker.position(latLng);
                mLocationPickerMap.clear();
                mLocationPickerMap.addMarker(mTutorMarker);
                mLocationPickerMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                mUpdateTutorLocation.setEnabled(true);
            }
        });
    }

    private void getLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_REQUEST);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionGranted = false;
        if (requestCode == FINE_LOCATION_REQUEST){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Enable GPS");
                dialog.setMessage("Enable GPS manually and restart App to get current location");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.create().show();
                updateUI();
            }else if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                Toast.makeText(this, "Location permission is required to get current location", Toast.LENGTH_SHORT).show();
                getLocationPermissions();
            }
        }else{
            getLocationPermissions();
        }
    }

    private void updateUI() {
        try{
            if(mLocationPermissionGranted){
                mLocationPickerMap.setMyLocationEnabled(true);
                mLocationPickerMap.getUiSettings().setMyLocationButtonEnabled(true);
            }else{
                getLocationPermissions();
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mTutorLocationPresenter == null){
            mTutorLocationPresenter = new TutorLocationPresenter(this);
        }
    }

    @Override
    public void onTutorLocationUpdate() {
        Intent intent = new Intent(TutorLocationActivity.this, TutorProfileActivity.class);
        intent.putExtra("tutorId", LoggedInUser.getLoggedInUser().getUserId());
        startActivity(intent);
    }

    @Override
    public void onResult(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void getLocationIntent(){
        if(getIntent().hasExtra("tutorMarker")){
            double[] latlang = getIntent().getDoubleArrayExtra("tutorMarker");
            LatLng tutorLatLng = new LatLng(latlang[0],latlang[1]);
            mTutorMarker = new MarkerOptions().position(tutorLatLng).draggable(true);
        }
    }
}
