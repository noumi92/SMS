package com.noumi.sms.ui.tutors.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.noumi.sms.R;
import com.noumi.sms.data.model.LoggedInUser;
import com.noumi.sms.data.model.Tutor;
import com.noumi.sms.ui.login.LoginActivity;
import com.noumi.sms.utils.NavigationUtils;

public class TutorProfileActivity extends AppCompatActivity implements TutorProfileViewInterface {
    private static final String TUTOR_ID_KEY = "tutorId";
    private String TAG = "com.noumi.sms.custom.log";
    private TutorProfilePresenterInterface mTutorProfilePresenter;
    private TextView mTutorNameView;
    private TextView mTutorEmailView;
    private TextView mTutorCityView;
    private TextView mTutorGenderView;
    private TextView mTutorQualificationView;
    private TextView mTutorSubjectsView;
    private TextView mTutorRatingView;
    private TextView mTutorLocationView;
    private TextView mTutorFeeView;
    private TextView mTutorAboutMeView;
    private NavigationView mNavigationView;
    private LinearLayout mProgressbar;
    private Tutor mTutor;
    private Button mUpdateProfile;
    private Button mDeleteAccount;
    private GoogleMap mTutorLocationThumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);
        mTutorNameView = (TextView) findViewById(R.id.tutor_name_text);
        mTutorEmailView = (TextView) findViewById(R.id.email_text);
        mTutorCityView = (TextView) findViewById(R.id.city_text);
        mTutorGenderView = (TextView) findViewById(R.id.gender_text);
        mTutorSubjectsView = (TextView) findViewById(R.id.subjects_text);
        mTutorLocationView = (TextView) findViewById(R.id.location_text);
        mTutorRatingView = (TextView) findViewById(R.id.rating_text);
        mTutorFeeView = (TextView) findViewById(R.id.fee_text);
        mTutorAboutMeView = (TextView) findViewById(R.id.about_me_text);
        mTutorQualificationView = (TextView) findViewById(R.id.qualification_text);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_menu_view);
        mProgressbar = (LinearLayout) findViewById(R.id.progressbar);
        mUpdateProfile = (Button) findViewById(R.id.update_tutor_profile);
        mDeleteAccount = (Button) findViewById(R.id.delete_account);
        //display progressbar on startup while activity initializes contents
        mProgressbar.setVisibility(View.VISIBLE);
        mUpdateProfile.setEnabled(false);
        //setting up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar!=null){
            toolbar.setTitle("My Profile");
            setSupportActionBar(toolbar);
        }
        getTutorIntent();
        NavigationUtils.startTutorNaigation(this, mNavigationView);
        mTutorNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder nameDialog = new AlertDialog.Builder(TutorProfileActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_text, null);
                final EditText editText = view.findViewById(R.id.dialog_text_view);
                editText.setText(mTutor.getTutorName());
                nameDialog.setView(view);
                nameDialog.setTitle("Edit Name");
                nameDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editText.getText().toString();
                        mTutor.setTutorName(name);
                        mTutorNameView.setText(mTutor.getTutorName());
                        mUpdateProfile.setEnabled(true);
                    }
                });
                nameDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                nameDialog.create().show();
            }
        });
        mTutorCityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder cityDialog = new AlertDialog.Builder(TutorProfileActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_city, null);
                final Spinner spinner = view.findViewById(R.id.dialog_city_spinner);
                cityDialog.setView(view);
                cityDialog.setTitle("Edit City");
                cityDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String city = spinner.getSelectedItem().toString();
                        mTutor.setTutorCity(city);
                        mTutorCityView.setText(mTutor.getTutorCity());
                        mUpdateProfile.setEnabled(true);
                    }
                });
                cityDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                cityDialog.create().show();
            }
        });
        mTutorGenderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder genderDialog = new AlertDialog.Builder(TutorProfileActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_gender, null);
                final Spinner spinner = view.findViewById(R.id.dialog_gender_spinner);
                genderDialog.setView(view);
                genderDialog.setTitle("Edit Gender");
                genderDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String gender = spinner.getSelectedItem().toString();
                        mTutor.setTutorGender(gender);
                        mTutorGenderView.setText(mTutor.getTutorGender());
                        mUpdateProfile.setEnabled(true);
                    }
                });
                genderDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                genderDialog.create().show();
            }
        });
        mTutorQualificationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder qualificationDialog = new AlertDialog.Builder(TutorProfileActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_qualificcation, null);
                final Spinner degreeNameSpinner = view.findViewById(R.id.dialog_degree_name_spinner);
                final Spinner degreeSubjectSpinner = view.findViewById(R.id.dialog_degree_subjects_spinner);
                qualificationDialog.setView(view);
                qualificationDialog.setTitle("Edit Qualification");
                qualificationDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String degreeName = degreeNameSpinner.getSelectedItem().toString();
                        String degreeSubject = degreeSubjectSpinner.getSelectedItem().toString();
                        mTutor.setTutorDegreeName(degreeName);
                        mTutor.setTutorDegreeSubject(degreeSubject);
                        String qualification = mTutor.getTutorDegreeName() + " " + mTutor.getTutorDegreeSubject();
                        mTutorQualificationView.setText(qualification);
                        mUpdateProfile.setEnabled(true);
                    }
                });
                qualificationDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                qualificationDialog.create().show();
            }
        });
        mTutorSubjectsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder subjectsDialog = new AlertDialog.Builder(TutorProfileActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_subjects, null);
                subjectsDialog.setView(view);
                subjectsDialog.setTitle("Edit Subjects");
                subjectsDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                subjectsDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                subjectsDialog.create().show();
            }
        });
        mTutorFeeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder feeDialog = new AlertDialog.Builder(TutorProfileActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_text, null);
                final EditText editText = view.findViewById(R.id.dialog_text_view);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setText(String.valueOf(mTutor.getTutorFee()));
                feeDialog.setView(view);
                feeDialog.setTitle("Edit Fee");
                feeDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int fee = Integer.valueOf(editText.getText().toString());
                        mTutor.setTutorFee(fee);
                        mTutorFeeView.setText(String.valueOf(mTutor.getTutorFee()));
                        mUpdateProfile.setEnabled(true);
                    }
                });
                feeDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                feeDialog.create().show();
            }
        });
        mTutorAboutMeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder aboutMeDialog = new AlertDialog.Builder(TutorProfileActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_text, null);
                final EditText editText = view.findViewById(R.id.dialog_text_view);
                editText.setLines(10);
                editText.setText(mTutor.getTutorAboutMe());
                aboutMeDialog.setView(view);
                aboutMeDialog.setTitle("Edit About Me");
                aboutMeDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String aboutMe = editText.getText().toString();
                        mTutor.setTutorAboutMe(aboutMe);
                        mTutorAboutMeView.setText(mTutor.getTutorAboutMe());
                        mUpdateProfile.setEnabled(true);
                    }
                });
                aboutMeDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                aboutMeDialog.create().show();
            }
        });

        mUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressbar.setVisibility(View.VISIBLE);
                mTutorProfilePresenter.updateTutor(mTutor);
            }
        });

        mDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder deleteAccountDialog = new AlertDialog.Builder(TutorProfileActivity.this);
                deleteAccountDialog
                        .setTitle("Delete Account")
                        .setMessage("Are you sure you want to delete account permanently from system")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTutorProfilePresenter.deleteTutorById(LoggedInUser.getLoggedInUser().getUserId());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create().show();
            }
        });
    }

    @Override
    protected void onStart() {
        if(mTutorProfilePresenter == null){
            mTutorProfilePresenter = new TutorProfilePresenter(TutorProfileActivity.this);
        }
        super.onStart();
    }

    @Override
    public void onLoadComplete(Tutor tutor) {
        //load map
        ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.location_thumb)).getMapAsync(this);
        mTutor = tutor;
        //display values
        mTutorNameView.setText(mTutor.getTutorName());
        mTutorEmailView.setText(mTutor.getTutorEmail());
        mTutorCityView.setText(mTutor.getTutorCity());
        mTutorGenderView.setText(mTutor.getTutorGender());
        StringBuilder subjectsSB = new StringBuilder();
        for(String subject : mTutor.getTutorSubjects()){
            subjectsSB.append(subject);
            subjectsSB.append("\n");
        }
        mTutorSubjectsView.setText(subjectsSB.toString());
        String qualification = mTutor.getTutorDegreeName() + " " + mTutor.getTutorDegreeSubject();
        mTutorQualificationView.setText(qualification);
        mTutorRatingView.setText(String.valueOf(mTutor.getTutorRating()));
        mTutorFeeView.setText(String.valueOf(mTutor.getTutorFee()));
        mTutorAboutMeView.setText(mTutor.getTutorAboutMe());
        mTutorLocationView.setText(mTutor.getTutorLocation().toString());
    }

    @Override
    public void onTutorUpdateSuccess() {
        mProgressbar.setVisibility(View.GONE);
    }

    //create option menu to
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }
    //provides functionality when user clicks on option menu item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout_item){
            mTutorProfilePresenter.logoutUser();
            startActivity(new Intent(TutorProfileActivity.this, LoginActivity.class));
        }
        return true;
    }
    @Override
    public void onResult(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    //exit app on back pressed
    //code updated in version 1.0.1 now app exit properly
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeScreenIntent = new Intent(Intent.ACTION_MAIN);
        homeScreenIntent.addCategory(Intent.CATEGORY_HOME);
        homeScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeScreenIntent);
        this.finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //hide progress bar
        mProgressbar.setVisibility(View.GONE);
        mTutorLocationThumb = googleMap;
        mTutorLocationThumb.setMinZoomPreference(10.0f);
        mTutorLocationThumb.setMaxZoomPreference(18.0f);
        //display tutor location on mapview
        LatLng latLng = new LatLng(mTutor.getTutorLocation().getLatitude(), mTutor.getTutorLocation().getLongitude());
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(mTutor.getTutorName());
        mTutorLocationThumb.addMarker(markerOptions);
        mTutorLocationThumb.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,8.0f));
        mTutorLocationThumb.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(TutorProfileActivity.this, TutorLocationActivity.class);
                double[] lat = new double[2];
                lat[0] = marker.getPosition().latitude;
                lat[1] = marker.getPosition().longitude;
                intent.putExtra("tutorMarker", lat);
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public void onDeleteAccount() {
        startActivity(new Intent(TutorProfileActivity.this, LoginActivity.class));
    }

    private void getTutorIntent(){
        if(getIntent().hasExtra("tutorId")){
            String tutorId = getIntent().getStringExtra("tutorId");
            if(mTutorProfilePresenter == null){
                mTutorProfilePresenter = new TutorProfilePresenter(this);
            }
            mTutorProfilePresenter.loadTutor(tutorId);
        }
    }
}
