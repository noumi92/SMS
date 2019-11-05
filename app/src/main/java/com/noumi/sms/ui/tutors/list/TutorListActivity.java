package com.noumi.sms.ui.tutors.list;

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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.noumi.sms.R;
import com.noumi.sms.data.model.Tutor;
import com.noumi.sms.ui.login.LoginActivity;
import com.noumi.sms.utils.NavigationUtils;

import java.util.ArrayList;
import java.util.List;

public class TutorListActivity extends AppCompatActivity implements TutorListViewInterface{
    private String TAG = "com.noumi.sms.custom.log";
    private TutorListPresenterInterface mTutorListPresenter;
    private RecyclerView mTutorRecyclerView;
    private TutorAdapter mTutorAdapter;
    private RadioGroup mGenderRadioGroup;
    private RadioButton mMaleRadioButton;
    private RadioButton mFemaleRadioButton;
    private Spinner mCitySpinner;
    private Spinner mDegreeNameSpinner;
    private Spinner mSubjectSpinner;
    private EditText mFeeView;
    private Button mApplyFilters;
    private Button mClearFilters;
    private DrawerLayout mDrawerLayout;
    private ListDivider mListDivider;
    private NavigationView mNavigationView;
    private LinearLayout mProgressbar;
    private boolean mFilterStateFlag;
    private List<Tutor> mTutors;
    private List<Tutor> mFilteredTutors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_list);
        //get references of views
        mTutorRecyclerView = (RecyclerView) findViewById(R.id.tutor_recycler_view);
        mGenderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
        mMaleRadioButton = (RadioButton) findViewById(R.id.male_filter);
        mFemaleRadioButton = (RadioButton) findViewById(R.id.female_filter);
        mCitySpinner = (Spinner) findViewById(R.id.city_spinner);
        mDegreeNameSpinner = (Spinner) findViewById(R.id.degree_name_spinner);
        mSubjectSpinner = (Spinner) findViewById(R.id.subject_spinner) ;
        mFeeView = (EditText) findViewById(R.id.fee_input);
        mApplyFilters = (Button) findViewById(R.id.button_apply_filters);
        mClearFilters = (Button) findViewById(R.id.button_clear_filters);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_menu_view);
        mProgressbar = (LinearLayout) findViewById(R.id.progressbar);
        mFilteredTutors = new ArrayList<>();
        mFilterStateFlag = false;
        //setup adapter here
        mTutorRecyclerView.setHasFixedSize(true);
        mTutorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Drawable divider = ContextCompat.getDrawable(this, R.drawable.list_item_seperator);
        mListDivider = new ListDivider(divider);
        mTutorRecyclerView.addItemDecoration(mListDivider);
        //display progressbar on startup while activity initializes contents
        mProgressbar.setVisibility(View.VISIBLE);
        //setting up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar!=null){
            toolbar.setTitle("Browse Tutors");
            setSupportActionBar(toolbar);
        }
        NavigationUtils.startStudentNaigation(this, mNavigationView);
        //apply filter button when any filter selected app calls appropriate method to fetch required data from database
        mApplyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close filter navigation menu
                mDrawerLayout.closeDrawers();
            }
        });
        //clear all filters and fetch all students from database
        mClearFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TutorListActivity.this, TutorListActivity.class));
            }
        });
    }
    //initial code to run onn startup
    @Override
    protected void onStart() {
        if(mTutorListPresenter == null){
            mTutorListPresenter = new TutorListPresenter(TutorListActivity.this);
        }
        mTutorListPresenter.loadTutors();
        super.onStart();
    }
    //this method is called when database handler completes data fetchinn
    @Override
    public void onLoadComplete(List<Tutor> tutors) {
        if (mTutorAdapter == null) {
            mTutors = tutors;
            mTutorAdapter = new TutorAdapter(this,tutors);
            mTutorRecyclerView.setAdapter(mTutorAdapter);
        } else {
            mTutorAdapter.notifyDataSetChanged();
        }
        startFilterListeners();
        mProgressbar.setVisibility(View.GONE);
    }
    //display feedback to user actions
    @Override
    public void onResult(String result) {
        mProgressbar.setVisibility(View.GONE);
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
            mTutorListPresenter.logoutUser();
            startActivity(new Intent(TutorListActivity.this, LoginActivity.class));
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
    private void startFilterListeners() {
        mGenderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String gender = "";
                if(checkedId == mFemaleRadioButton.getId()){
                    gender = "Female";
                }else if(checkedId == mMaleRadioButton.getId()){
                    gender = "Male";
                }
                if(!mFilterStateFlag){
                    mFilteredTutors = mTutorListPresenter.filterTutorsByGender(mTutors, gender);
                }else{
                    mFilteredTutors = mTutorListPresenter.filterTutorsByGender(mFilteredTutors, gender);
                }
                mFilterStateFlag = true;
                mMaleRadioButton.setEnabled(false);
                mFemaleRadioButton.setEnabled(false);
                mClearFilters.setEnabled(true);
                mTutorAdapter.setTutors(mFilteredTutors);
                mTutorAdapter.notifyDataSetChanged();
            }
        });
        mCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String city = mCitySpinner.getSelectedItem().toString();
                if(!mFilterStateFlag ){
                    mFilteredTutors = mTutorListPresenter.filterTutorsByCity(mTutors, city);
                }else{
                    mFilteredTutors = mTutorListPresenter.filterTutorsByCity(mFilteredTutors, city);
                }
                mFilterStateFlag = true;
                mCitySpinner.setEnabled(false);
                mClearFilters.setEnabled(true);
                mTutorAdapter.setTutors(mFilteredTutors);
                mTutorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mDegreeNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String degreeName = mDegreeNameSpinner.getSelectedItem().toString();
                if(!mFilterStateFlag ){
                    mFilteredTutors = mTutorListPresenter.filterTutorsByDegree(mTutors, degreeName);
                }else{
                    mFilteredTutors = mTutorListPresenter.filterTutorsByDegree(mFilteredTutors, degreeName);
                }
                mFilterStateFlag = true;
                mDegreeNameSpinner.setEnabled(false);
                mClearFilters.setEnabled(true);
                mTutorAdapter.setTutors(mFilteredTutors);
                mTutorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSubjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String subject = mSubjectSpinner.getSelectedItem().toString();
                if(!mFilterStateFlag ){
                    mFilteredTutors = mTutorListPresenter.filterTutorsBySubject(mTutors, subject);
                }else{
                    mFilteredTutors = mTutorListPresenter.filterTutorsBySubject(mFilteredTutors, subject);
                }
                mSubjectSpinner.setEnabled(false);
                mFilterStateFlag = true;
                mClearFilters.setEnabled(true);
                mTutorAdapter.setTutors(mFilteredTutors);
                mTutorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mFeeView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.equals(mFeeView.getText(),"") && count>2) {
                    int fee = Integer.valueOf(mFeeView.getText().toString());
                    if(!mFilterStateFlag){
                        mFilteredTutors = mTutorListPresenter.filterTutorsByFee(mTutors, fee);
                    }else{
                        mFilteredTutors = mTutorListPresenter.filterTutorsByFee(mFilteredTutors, fee);
                    }
                    mFeeView.setEnabled(false);
                    mFilterStateFlag = true;
                    mClearFilters.setEnabled(true);
                    mTutorAdapter.setTutors(mFilteredTutors);
                    mTutorAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}