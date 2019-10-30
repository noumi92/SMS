package com.noumi.sms.ui.rating;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.noumi.sms.R;
import com.noumi.sms.data.model.LoggedInUser;
import com.noumi.sms.data.model.Rating;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.ui.login.LoginActivity;
import com.noumi.sms.utils.NavigationUtils;

import java.util.List;

public class RatingListActivity extends AppCompatActivity implements RatingListViewInterface {
    private String TAG = "com.noumi.sms.custom.log";
    private RatingListPresenterInterface mRatingListPresenter;
    private RecyclerView mRatingsRecyclerView;
    private RatingAdapter mRatingAdapter;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private List<Rating> mRatings;
    private List<Student> mStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_list);

        //get references of views
        mRatingsRecyclerView = (RecyclerView) findViewById(R.id.rating_recycler_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_menu_view);
        //setup adapter here
        mRatingsRecyclerView.setHasFixedSize(true);
        mRatingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //setting up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar!=null) {
            toolbar.setTitle("My Ratings");
            setSupportActionBar(toolbar);
        }
        mNavigationView.getMenu().clear();
        mNavigationView.inflateMenu(R.menu.menu_tutor_navigation);
        NavigationUtils.startTutorNaigation(this, mNavigationView);
    }
    //initial code to run onn startup
    @Override
    protected void onStart() {
        if(mRatingListPresenter == null) {
            mRatingListPresenter = new RatingListPresenter(RatingListActivity.this);
        }
        super.onStart();
        mRatingListPresenter.loadRatingsByTutorId(LoggedInUser.getLoggedInUser().getUserId());
    }
    @Override
    public void onLoadComplete(List<Rating> ratings, List<Student> students) {
        Log.d(TAG, "data result count:" + students.size());
        if (mRatingAdapter == null) {
            mRatingAdapter = new RatingAdapter(ratings, students, RatingListActivity.this);
            mRatingsRecyclerView.setAdapter(mRatingAdapter);
        } else {
            mRatingAdapter.notifyDataSetChanged();
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
            mRatingListPresenter.logoutUser();
            startActivity(new Intent(RatingListActivity.this, LoginActivity.class));
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
}