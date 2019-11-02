package com.noumi.sms.ui.students.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.noumi.sms.R;
import com.noumi.sms.data.model.LoggedInUser;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.data.model.Tuition;
import com.noumi.sms.ui.login.LoginActivity;
import com.noumi.sms.ui.tuition.list.TuitionsListActivity;
import com.noumi.sms.utils.NavigationUtils;

import java.util.Calendar;
import java.util.Date;

import static android.view.View.GONE;

public class StudentDetailActivity extends AppCompatActivity implements StudentDetailViewInterface {
    private String TAG = "com.noumi.sms.custom.log";
    private TextView mStudentNameView;
    private TextView mStudentEmailView;
    private TextView mStudentCityView;
    private TextView mStudentGenderView;
    private Student mStudent;
    private StudentDetailPresenterInterface mStudentDetailPresenter;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Button mOfferTuition;
    private LinearLayout mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        mStudentNameView = (TextView) findViewById(R.id.student_name_text);
        mStudentEmailView = (TextView) findViewById(R.id.email_text);
        mStudentCityView = (TextView) findViewById(R.id.city_text);
        mStudentGenderView = (TextView) findViewById(R.id.gender_text);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_menu_view);
        mOfferTuition = (Button) findViewById(R.id.offer_tuition);
        mProgressBar = (LinearLayout) findViewById(R.id.progressbar);

        //display progressbar on startup while activity initializes contents
        mProgressBar.setVisibility(View.VISIBLE);

        //setting up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar!=null){
            toolbar.setTitle("Browse Students");
            setSupportActionBar(toolbar);
        }

        NavigationUtils.startTutorNaigation(this,mNavigationView);
        getStudentIntent();


        mOfferTuition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                String studentId = mStudent.getStudentId();
                String tutorId = LoggedInUser.getLoggedInUser().getUserId();
                StringBuilder sb = new StringBuilder();
                sb.append(tutorId);
                sb.append("#");
                sb.append(studentId);
                String tuitionId = sb.toString();
                Date requestedDate = Calendar.getInstance().getTime();
                Tuition tuition = new Tuition(tuitionId, tutorId, studentId, studentId, requestedDate, false, false);
                mStudentDetailPresenter.addTuition(tuition);
            }
        });


    }

    @Override
    protected void onStart() {
        if(mStudentDetailPresenter == null){
            mStudentDetailPresenter = new StudentDetailPresenter(StudentDetailActivity.this);
        }
        super.onStart();
    }

    @Override
    public void onLoadComplete(Student student) {
        mProgressBar.setVisibility(GONE);
        mStudent = student;
        mStudentNameView.setText(mStudent.getStudentName());
        mStudentEmailView.setText(mStudent.getStudentEmail());
        mStudentCityView.setText(mStudent.getStudentCity());
        mStudentGenderView.setText(mStudent.getStudentGender());
    }

    @Override
    public void onOfferTuitionSuccess(String tuitionId) {
        Intent intent = new Intent(StudentDetailActivity.this, TuitionsListActivity.class);
        startActivity(intent);
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
            startActivity(new Intent(StudentDetailActivity.this, LoginActivity.class));
        }
        return true;
    }
    @Override
    public void onResult(String message) {
        mProgressBar.setVisibility(GONE);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    private void getStudentIntent(){
        if(getIntent().hasExtra("studentId")){
            String studentId = getIntent().getStringExtra("studentId");
            if(mStudentDetailPresenter == null){
                mStudentDetailPresenter = new StudentDetailPresenter(this);
            }
            mStudentDetailPresenter.loadStudent(studentId);
        }
    }
}
