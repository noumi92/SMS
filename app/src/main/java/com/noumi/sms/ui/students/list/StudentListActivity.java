package com.noumi.sms.ui.students.list;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.noumi.sms.R;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.ui.login.LoginActivity;
import com.noumi.sms.utils.NavigationUtils;

import java.util.List;

public class StudentListActivity extends AppCompatActivity implements StudentListViewInterface{
    private static final String FILTER_STATE_FLAG_KEY = "filter_selection";
    private static final String SELECTED_CITY_KEY = "selected_city";
    private static final String SELECTED_GENDER_KEY = "selected_gender";
    private String TAG = "com.noumi.sms.custom.log";
    private StudentListPresenterInterface mStudentListPresenter;
    private RecyclerView mStudentsRecyclerView;
    private StudentAdapter mStudentAdapter;
    private RadioGroup mGenderRadioGroup;
    private Spinner mCitySpinner;
    private Button mApplyFilters;
    private Button mClearFilters;
    private DrawerLayout mDrawerLayout;
    private int mFilterStateFlag;
    private String mSelectedCity;
    private String mSelectedGender;
    private ListDivider mListDivider;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        //get references of views
        mStudentsRecyclerView = (RecyclerView) findViewById(R.id.student_recycler_view);
        mGenderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
        mCitySpinner = (Spinner) findViewById(R.id.city_spinner);
        mApplyFilters = (Button) findViewById(R.id.button_apply_filters);
        mClearFilters = (Button) findViewById(R.id.button_clear_filters);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_menu_view);
        //setup adapter here
        mStudentsRecyclerView.setHasFixedSize(true);
        mStudentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Drawable divider = ContextCompat.getDrawable(this, R.drawable.list_item_seperator);
        mListDivider = new ListDivider(divider);
        mStudentsRecyclerView.addItemDecoration(mListDivider);
        // 0  means no filter applied
        mFilterStateFlag = 0;
        //setting up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar!=null){
            toolbar.setTitle(R.string.app_name);
            setSupportActionBar(toolbar);
        }
        NavigationUtils.startTutorNaigation(this, mNavigationView);
        //apply filter button when any filter selected app calls appropriate method to fetch required data from database
        mApplyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get selection for gender
                int genderRadioButtonId = mGenderRadioGroup.getCheckedRadioButtonId();
                //get selection for city
                mSelectedCity = mCitySpinner.getSelectedItem().toString();
                //get selection of filtera and set filter type flags
                //FILTER_FLAGS-> 1=city, 2=gender, 3=city&gender, 0=no filter applied
                if(genderRadioButtonId != -1 && !mSelectedCity.isEmpty()){
                    //this block runs when both gender and city filters are selected
                    mFilterStateFlag = 3;
                    RadioButton button = (RadioButton) findViewById(genderRadioButtonId);
                    mSelectedGender = button.getText().toString();
                }else if(!mSelectedCity.isEmpty()){
                    //this block runs when only city filter is selected
                    mFilterStateFlag = 1;
                }else if(genderRadioButtonId != -1){
                    //this block runs when onle gender filter is selected for data
                    mFilterStateFlag = 2;
                    RadioButton button = (RadioButton) findViewById(genderRadioButtonId);
                    mSelectedGender = button.getText().toString();
                }
                mStudentListPresenter.getFilteredStudents(mSelectedCity, mSelectedGender, mFilterStateFlag);
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
        if(mStudentListPresenter == null){
            mStudentListPresenter = new StudentListPresenter(StudentListActivity.this);
        }
        //initial loading of all students
        if(mFilterStateFlag == 0){
            mStudentListPresenter.loadStudents();
        }
        super.onStart();
    }
    //this method is called when database handler completes data fetchinn
    @Override
    public void onLoadComplete(List<Student> students) {
        Log.d(TAG, "data result count:" + students.size());
        if (mStudentAdapter == null) {
            mStudentAdapter = new StudentAdapter(students, this);
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