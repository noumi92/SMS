package com.noumi.sms.ui.tutors.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.noumi.sms.R;
import com.noumi.sms.data.model.Tutor;
import com.noumi.sms.ui.login.LoginActivity;
import com.noumi.sms.utils.NavigationUtils;

import java.util.List;

public class TutorProfileActivity extends AppCompatActivity implements TutorProfileViewInterface {
    private static final String TUTOR_ID_KEY = "tutorId";
    private String TAG = "com.noumi.sms.custom.log";
    private TutorProfilePresenterInterface mTutorDetailPresenter;
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
    private String mTutorName;
    private String mTutorEmail;
    private String mTutorCity;
    private String mTutorGender;
    private String mTutorDegreeName;
    private String mTutorDegreeSubject;
    private List<String> mTutorSubjects;
    private long mTutorRating;
    private String mTutorLocation;
    private long mTutorFee;
    private String mTutorAboutMe;
    private NavigationView mNavigationView;

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
        //setting up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar!=null){
            toolbar.setTitle(R.string.app_name);
            setSupportActionBar(toolbar);
        }
        getTutorIntent();
        NavigationUtils.startTutorNaigation(this, mNavigationView);
    }

    @Override
    protected void onStart() {
        if(mTutorDetailPresenter == null){
            mTutorDetailPresenter = new TutorProfilePresenter(TutorProfileActivity.this);
        }
        super.onStart();
    }

    @Override
    public void onLoadComplete(Tutor tutor) {
        //get values
        mTutorName = tutor.getTutorName();
        mTutorEmail = tutor.getTutorEmail();
        mTutorCity = tutor.getTutorCity();
        mTutorGender = tutor.getTutorGender();
        mTutorSubjects = tutor.getTutorSubjects();
        mTutorRating = tutor.getTutorRating();
        mTutorDegreeName = tutor.getTutorDegreeName();
        mTutorDegreeSubject = tutor.getTutorDegreeSubject();
        mTutorFee = tutor.getTutorFee();
        mTutorAboutMe = tutor.getTutorAboutMe();
        mTutorLocation = tutor.getTutorLocation().toString();
        //display values
        mTutorNameView.setText(mTutorName);
        mTutorEmailView.setText(mTutorEmail);
        mTutorCityView.setText(mTutorCity);
        mTutorGenderView.setText(mTutorGender);
        StringBuilder subjectsSB = new StringBuilder();
        for(String subject : mTutorSubjects){
            subjectsSB.append(subject);
            subjectsSB.append("\n");
        }
        mTutorSubjectsView.setText(subjectsSB.toString());
        String qualification = mTutorDegreeName + " " + mTutorDegreeSubject;
        mTutorQualificationView.setText(qualification);
        mTutorRatingView.setText(Long.toString(mTutorRating));
        mTutorFeeView.setText(Long.toString(mTutorFee));
        mTutorAboutMeView.setText(mTutorAboutMe);
        mTutorLocationView.setText(mTutorLocation);
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
            mTutorDetailPresenter.logoutUser();
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
    private void getTutorIntent(){
        if(getIntent().hasExtra("tutorId")){
            String tutorId = getIntent().getStringExtra("tutorId");
            if(mTutorDetailPresenter == null){
                mTutorDetailPresenter = new TutorProfilePresenter(this);
            }
            mTutorDetailPresenter.loadTutor(tutorId);
        }
    }
}
