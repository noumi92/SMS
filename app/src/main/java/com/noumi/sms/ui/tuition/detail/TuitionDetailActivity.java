package com.noumi.sms.ui.tuition.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.noumi.sms.R;
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
    private Tuition mTuition;
    private Tutor mTutor;
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
        //setting up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar!=null){
            toolbar.setTitle("Tuition Details");
            setSupportActionBar(toolbar);
        }
        getTuitionIntent();
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
    }

    @Override
    public void onTutorLoad(Tutor tutor) {
        mTutor = tutor;
        updateUI();
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
    private void updateUI() {
        mTutorNameTextView.setText(mTutor.getTutorName());
        if(mTuition.isAccepted()){
            mTuitionAcceptanceTextView.setText("Accepted");
        }else{
            mTuitionAcceptanceTextView.setText("Pending");
        }
        if(mTuition.isActive()){
            mTuitionAcceptanceTextView.setText("Active");
        }else{
            mTuitionAcceptanceTextView.setText("InActive");
        }
        mTutorRatingTextView.setText(Long.toString(mTuition.getRating()));
        mCommentsView.setText(mTuition.getComments());
    }
}
