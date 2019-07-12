package com.noumi.sms.ui.students;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.noumi.sms.R;
import com.noumi.sms.data.DatabaseHandler;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity implements StudentListViewInterface{
    private StudentListPresenter mStudentListPresenter;
    private RecyclerView mStudentsRecyclerView;
    private StudentAdapter mStudentAdapter;
    private String TAG = "com.noumi.sms.custom.log";
    private RadioGroup mGenderRadioGroup;
    private RadioButton mGenderRadioButton;
    private Spinner mCitySpinner;
    private Button mApplyFilters;
    private Button mClearFilters;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        mStudentsRecyclerView = (RecyclerView) findViewById(R.id.student_recycler_view);
        mStudentsRecyclerView.setHasFixedSize(true);
        mStudentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Drawable divider = ContextCompat.getDrawable(this, R.drawable.list_item_seperator);
        mStudentsRecyclerView.addItemDecoration(new ListDivider(divider));
        mGenderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
        mCitySpinner = (Spinner) findViewById(R.id.student_city_spinner);
        mApplyFilters = (Button) findViewById(R.id.button_apply_filters);
        mClearFilters = (Button) findViewById(R.id.button_clear_filters);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        //setting up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar!=null){
            toolbar.setTitle(R.string.app_name);
            setSupportActionBar(toolbar);
        }
        //apply filter button when any filter selected app calls appropriate method to fetch required data from database
        mApplyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get selection for gender
                int genderSelectedid = mGenderRadioGroup.getCheckedRadioButtonId();
                //get selection for city
                String city = mCitySpinner.getSelectedItem().toString();
                if(genderSelectedid != -1 && !city.isEmpty()){
                    //this block runs when both gender and city filters are selected
                    mGenderRadioButton = (RadioButton) findViewById(genderSelectedid);
                    mStudentListPresenter.getstudentsByCityAndGender(city, mGenderRadioButton.getText().toString());
                }else if(!city.isEmpty()){
                    //this block runs when only city filter is selected
                    mStudentListPresenter.getStudentsByCity(city);
                }else if(genderSelectedid != -1){
                    //this block runs when onle gender filter is selected for data
                    mGenderRadioButton = (RadioButton) findViewById(genderSelectedid);
                    mStudentListPresenter.getStudentsByGender(mGenderRadioButton.getText().toString());
                }
                //close filter navigation menu
                mDrawerLayout.closeDrawers();
            }
        });
        //clear all filters and fetch all students from database
        mClearFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGenderRadioGroup.clearCheck();
                mCitySpinner.setSelected(false);
                mStudentListPresenter.loadStudents();
                mDrawerLayout.closeDrawers();
            }
        });
    }
    //initial code to run onn startup
    @Override
    protected void onStart() {
        super.onStart();
        if(mStudentListPresenter == null){
            mStudentListPresenter = new StudentListPresenter(StudentListActivity.this);
        }
        mStudentListPresenter.loadStudents();
    }
    //this method is called when database handler completes data fetchinn
    @Override
    public void onLoadComplete(List<Student> students) {
        Log.d(TAG, "data result count:" + students.size());
        if (mStudentAdapter == null) {
            mStudentAdapter = new StudentAdapter(students);
            mStudentsRecyclerView.setAdapter(mStudentAdapter);
        } else {
            mStudentAdapter.notifyDataSetChanged();
        }
    }
    //display feedback to user actions
    @Override
    public void onResult(String result) {
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
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
            mStudentListPresenter.logoutUser();
            startActivity(new Intent(StudentListActivity.this, LoginActivity.class));
        }
        return true;
    }
    //exit app on back pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
    //inner class to provide horizontal seperator after every lis item
    class ListDivider extends RecyclerView.ItemDecoration{
        private Drawable mDivider;

        public ListDivider(Drawable divider) {
            mDivider = divider;
        }

        @Override
        public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
            int left = 16;
            int right = parent.getWidth()- 16;
            for(int i = 0; i < parent.getChildCount(); i++){
                View child = parent.getChildAt(i);
                int top = child.getBottom();
                int bottom = top + mDivider.getIntrinsicHeight();
                //Log.d(TAG, "Layout Position:" + left + "," +top + "," + right + "," + bottom);
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}