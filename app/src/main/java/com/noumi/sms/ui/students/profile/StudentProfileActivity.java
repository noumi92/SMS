package com.noumi.sms.ui.students.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.noumi.sms.R;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.ui.login.LoginActivity;
import com.noumi.sms.utils.NavigationUtils;

public class StudentProfileActivity extends AppCompatActivity implements StudentProfileViewInterface {
    private String TAG = "com.noumi.sms.custom.log";
    private static final String STUDENT_NAME_KEY = "student_name";
    private static final String STUDENT_EMAIL_KEY = "student_email";
    private static final String STUDENT_CITY_KEY = "student_city";
    private static final String STUDENT_GENDER_KEY = "student_gender";
    private TextView mStudentNameView;
    private EditText mStudentEmailView;
    private EditText mStudentCityView;
    private EditText mStudentGenderView;
    private String mStudentName;
    private String mStudentEmail;
    private String mStudentCity;
    private String mStudentGender;
    private StudentProfilePresenterInterface mStudentDetailPresenter;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        if(savedInstanceState != null){
            super.onRestoreInstanceState(savedInstanceState);
            mStudentName = savedInstanceState.getString(STUDENT_NAME_KEY);
            mStudentEmail = savedInstanceState.getString(STUDENT_EMAIL_KEY);
            mStudentCity = savedInstanceState.getString(STUDENT_CITY_KEY);
            mStudentGender = savedInstanceState.getString(STUDENT_GENDER_KEY);
        }

        mStudentNameView = (TextView) findViewById(R.id.student_name_text);
        mStudentEmailView = (EditText) findViewById(R.id.email_text);
        mStudentCityView = (EditText) findViewById(R.id.city_text);
        mStudentGenderView = (EditText) findViewById(R.id.gender_text);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_menu_view);

        //setting up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar!=null){
            toolbar.setTitle(R.string.app_name);
            setSupportActionBar(toolbar);
        }

        NavigationUtils.startStudentNaigation(this,mNavigationView);
        getStudentIntent();

    }

    @Override
    protected void onStart() {
        if(mStudentDetailPresenter == null){
            mStudentDetailPresenter = new StudentProfilePresenter(StudentProfileActivity.this);
        }
        super.onStart();
    }

    @Override
    public void onLoadComplete(Student student) {
        mStudentName = student.getStudentName();
        mStudentEmail = student.getStudentEmail();
        mStudentCity = student.getStudentCity();
        mStudentGender = student.getStudentGender();
        mStudentNameView.setText(mStudentName);
        mStudentEmailView.setText(mStudentEmail);
        mStudentCityView.setText(mStudentCity);
        mStudentGenderView.setText(mStudentGender);
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
            mStudentDetailPresenter.logoutUser();
            startActivity(new Intent(StudentProfileActivity.this, LoginActivity.class));
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
    private void getStudentIntent(){
        if(getIntent().hasExtra("studentId")){
            String studentId = getIntent().getStringExtra("studentId");
            if(mStudentDetailPresenter == null){
                mStudentDetailPresenter = new StudentProfilePresenter(this);
            }
            mStudentDetailPresenter.loadStudent(studentId);
        }
    }
}
