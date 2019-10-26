package com.noumi.sms.ui.students.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import com.noumi.sms.R;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.ui.login.LoginActivity;
import com.noumi.sms.utils.NavigationUtils;

import static android.view.View.GONE;

public class StudentProfileActivity extends AppCompatActivity implements StudentProfileViewInterface {
    private String TAG = "com.noumi.sms.custom.log";
    private TextView mStudentNameView;
    private TextView mStudentEmailView;
    private TextView mStudentCityView;
    private TextView mStudentGenderView;
    private Student mStudent;
    private StudentProfilePresenterInterface mStudentDetailPresenter;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Button mUpdateProfileButton;
    private LinearLayout mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        mStudentNameView = (TextView) findViewById(R.id.student_name_text);
        mStudentEmailView = (TextView) findViewById(R.id.email_text);
        mStudentCityView = (TextView) findViewById(R.id.city_text);
        mStudentGenderView = (TextView) findViewById(R.id.gender_text);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_menu_view);
        mUpdateProfileButton = (Button) findViewById(R.id.update_student_button);
        mProgressBar = (LinearLayout) findViewById(R.id.progressbar);

        //display progressbar on startup while activity initializes contents
        mProgressBar.setVisibility(View.VISIBLE);

        //setting up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar!=null){
            toolbar.setTitle("My Profile");
            setSupportActionBar(toolbar);
        }

        NavigationUtils.startStudentNaigation(this,mNavigationView);
        getStudentIntent();

        mStudentNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder nameDialog = new AlertDialog.Builder(StudentProfileActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_text, null);
                final EditText editText = view.findViewById(R.id.dialog_text_view);
                editText.setText(mStudent.getStudentName());
                nameDialog.setView(view);
                nameDialog.setTitle("Edit Name");
                nameDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editText.getText().toString();
                        mStudent.setStudentName(name);
                        mStudentNameView.setText(mStudent.getStudentName());
                        mUpdateProfileButton.setEnabled(true);
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
        mStudentCityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder nameDialog = new AlertDialog.Builder(StudentProfileActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_city, null);
                final Spinner spinner = view.findViewById(R.id.dialog_city_spinner);
                nameDialog.setView(view);
                nameDialog.setTitle("Edit City");
                nameDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String city = spinner.getSelectedItem().toString();
                        mStudent.setStudentCity(city);
                        mStudentCityView.setText(mStudent.getStudentCity());
                        mUpdateProfileButton.setEnabled(true);
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
        mStudentGenderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder nameDialog = new AlertDialog.Builder(StudentProfileActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_gender, null);
                final Spinner spinner = view.findViewById(R.id.dialog_gender_spinner);
                nameDialog.setView(view);
                nameDialog.setTitle("Edit Gender");
                nameDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String gender = spinner.getSelectedItem().toString();
                        mStudent.setStudentGender(gender);
                        mStudentGenderView.setText(mStudent.getStudentGender());
                        mUpdateProfileButton.setEnabled(true);
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
        mUpdateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                mStudentDetailPresenter.updateStudent(mStudent);
            }
        });

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
        mStudent = student;
        mStudentNameView.setText(mStudent.getStudentName());
        mStudentEmailView.setText(mStudent.getStudentEmail());
        mStudentCityView.setText(mStudent.getStudentCity());
        mStudentGenderView.setText(mStudent.getStudentGender());
        mProgressBar.setVisibility(GONE);
    }

    @Override
    public void onUpdateStudentSuccess() {
        mUpdateProfileButton.setEnabled(false);
        mProgressBar.setVisibility(GONE);
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
        mProgressBar.setVisibility(GONE);
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
