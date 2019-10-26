package com.noumi.sms.ui.tuition.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.noumi.sms.R;
import com.noumi.sms.data.model.LoggedInUser;
import com.noumi.sms.data.model.Rating;
import com.noumi.sms.data.model.Tuition;
import com.noumi.sms.data.model.Tutor;
import com.noumi.sms.ui.login.LoginActivity;

public class TuitionDetailActivity extends AppCompatActivity implements TuitionDetailViewInterface{
    TuitionDetailPresenterInterface mTuitionDetailPresenter;
    private ImageView mTuitionImageView;
    private TextView mTutorNameTextView;
    private TextView mTuitionAcceptanceTextView;
    private TextView mTuitionActiveTextView;
    private TextView mTutorRatingTextView;
    private EditText mCommentsView;
    private Button mUpdateRatingButton;
    private Button mLeaveTuitionButton;
    private Button mAcceptTuitionButton;
    private Button mDeleteTuitionButton;
    private Tuition mTuition;
    private Tutor mTutor;
    private Rating mRating;
    private LinearLayout mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition_detail);

        //initialize and get references
        mTuitionDetailPresenter = new TuitionDetailPresenter(this);
        mTuitionImageView = (ImageView) findViewById(R.id.tuition_thumb_view);
        mTutorNameTextView = (TextView) findViewById(R.id.tutor_name_view);
        mTuitionAcceptanceTextView = (TextView) findViewById(R.id.tuition_acceptance_view);
        mTuitionActiveTextView = (TextView) findViewById(R.id.tuition_active_view);
        mTutorRatingTextView = (TextView) findViewById(R.id.tuition_rating_view);
        mCommentsView = (EditText) findViewById(R.id.tuition_comments_view);
        mUpdateRatingButton = (Button) findViewById(R.id.update_tuition_rating_button);
        mLeaveTuitionButton = (Button) findViewById(R.id.leave_tuition_button);
        mAcceptTuitionButton = (Button) findViewById(R.id.accept_tuition_button);
        mDeleteTuitionButton = (Button) findViewById(R.id.delete_tuition_button);
        mProgressBar = (LinearLayout) findViewById(R.id.progressbar);
        //display progressbar on startup while activity initializes contents
        mProgressBar.setVisibility(View.VISIBLE);
        //setting up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar!=null){
            toolbar.setTitle("Tuition Details");
            setSupportActionBar(toolbar);
        }
        getTuitionIntent();
        String studentType = LoggedInUser.getLoggedInUser().getUserType();
        if(TextUtils.equals(studentType, "student")){
            mAcceptTuitionButton.setVisibility(View.GONE);
            mDeleteTuitionButton.setVisibility(View.GONE);
            mTutorRatingTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder ratingDialog = new AlertDialog.Builder(TuitionDetailActivity.this);
                    View view = getLayoutInflater().inflate(R.layout.dialog_rating, null);
                    final Spinner spinner = view.findViewById(R.id.dialog_rating_spinner);
                    ratingDialog.setView(view);
                    ratingDialog.setTitle("Edit Rating");
                    ratingDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mRating.setRating(Integer.valueOf(spinner.getSelectedItem().toString()));
                            mTutorRatingTextView.setText(String.valueOf(mRating.getRating()));
                            mUpdateRatingButton.setEnabled(true);
                        }
                    });
                    ratingDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    ratingDialog.create().show();
                }
            });
            mCommentsView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(!TextUtils.equals(mCommentsView.getText().toString(), mRating.getComments())){
                        mRating.setComments(mCommentsView.getText().toString());
                        mUpdateRatingButton.setEnabled(true);
                    }
                }
            });
        }else if(TextUtils.equals(studentType, "tutor")){
            mUpdateRatingButton.setVisibility(View.GONE);
            mLeaveTuitionButton.setVisibility(View.GONE);
            mCommentsView.setFocusable(false);
        }
        mUpdateRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onStart() {
        if(mTuitionDetailPresenter == null){
            mTuitionDetailPresenter = new TuitionDetailPresenter(this);
        }
        super.onStart();
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
            mTuitionDetailPresenter.logoutUser();
            startActivity(new Intent(this, LoginActivity.class));
        }
        return true;
    }

    @Override
    public void onTuitionLoad(Tuition tuition) {
        mTuition = tuition;
        mTuitionDetailPresenter.loadTutor(tuition.getTutorId());
        mTuitionDetailPresenter.loadRating(tuition.getTuitionId());
    }

    @Override
    public void onTutorLoad(Tutor tutor) {
        mTutor = tutor;
        updateTutor();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRatingLoad(Rating rating) {
        mRating = rating;
        updateRating();
    }

    @Override
    public void onResult(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    private void getTuitionIntent(){
        if(getIntent().hasExtra("tuitionId")){
            String tuitionId = getIntent().getStringExtra("tuitionId");
            if(mTuitionDetailPresenter == null){
                mTuitionDetailPresenter = new TuitionDetailPresenter(this);
            }
            mTuitionDetailPresenter.loadTuition(tuitionId);
        }
    }
    private void updateTutor() {
        mTutorNameTextView.setText(mTutor.getTutorName());
        if(mTuition.isAccepted()){
            mTuitionAcceptanceTextView.setText("Accepted");
        }else{
            mTuitionAcceptanceTextView.setText("Pending");
            mAcceptTuitionButton.setEnabled(true);
        }
        if(mTuition.isActive()){
            mTuitionActiveTextView.setText("Active");
        }else{
            mTuitionActiveTextView.setText("InActive");
        }
        //student has requseted tuition and tutor not yet taken any action
        if(!mTuition.isActive() && !mTuition.isAccepted()){
            mUpdateRatingButton.setVisibility(View.GONE);
            mLeaveTuitionButton.setVisibility(View.GONE);
            mDeleteTuitionButton.setVisibility(View.VISIBLE);
            mCommentsView.setFocusable(false);
            mTutorRatingTextView.setClickable(false);
        }
        //tutor accepted the tuition but student left tuition
        if(mTuition.isAccepted() && !mTuition.isActive()){
            mUpdateRatingButton.setVisibility(View.GONE);
            mLeaveTuitionButton.setVisibility(View.GONE);
            mCommentsView.setFocusable(false);
            mTutorRatingTextView.setClickable(false);
        }
        //tutor acceptence pending and student left
        if(!mTuition.isAccepted() && !mTuition.isActive()){
            mUpdateRatingButton.setVisibility(View.GONE);
            mCommentsView.setFocusable(false);
            mTutorRatingTextView.setClickable(false);
        }
    }
    private void updateRating(){
        mTutorRatingTextView.setText(Integer.toString(mRating.getRating()));
        mCommentsView.setText(mRating.getComments());
    }
}
