package com.noumi.sms.ui.tutors.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.noumi.sms.R;
import com.noumi.sms.data.model.Chat;
import com.noumi.sms.data.model.LoggedInUser;
import com.noumi.sms.data.model.Tuition;
import com.noumi.sms.data.model.Tutor;
import com.noumi.sms.ui.chat.list.ChatListActivity;
import com.noumi.sms.ui.chat.room.ChatRoomActivity;
import com.noumi.sms.ui.login.LoginActivity;
import com.noumi.sms.ui.tuition.detail.TuitionDetailActivity;
import com.noumi.sms.ui.tuition.list.TuitionsListActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TutorDetailActivity extends AppCompatActivity implements TutorDetailViewInterface{
    private static final String TUTOR_ID_KEY = "tutorId";
    private String TAG = "com.noumi.sms.custom.log";
    private TutorDetailPresenterInterface mTutorDetailPresenter;
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
    private Tutor mTutor;
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
    private Button mApplyTuition;
    private Button mCreateChat;
    private LinearLayout mProgressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_detail);
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
        mApplyTuition = (Button) findViewById(R.id.apply_tuition);
        mCreateChat = (Button) findViewById(R.id.apply_chat);
        mProgressbar = (LinearLayout) findViewById(R.id.progressbar);
        //setting up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar!=null){
            toolbar.setTitle("Tutor Details");
            setSupportActionBar(toolbar);
        }
        getTutorIntent();
        mApplyTuition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tutorId = mTutor.getTutorId();
                String studentId = LoggedInUser.getLoggedInUser().getUserId();
                StringBuilder sb = new StringBuilder();
                sb.append(tutorId);
                sb.append("#");
                sb.append(studentId);
                String tuitionId = sb.toString();
                Date requestedDate = Calendar.getInstance().getTime();
                Tuition tuition = new Tuition(tuitionId, tutorId, studentId, requestedDate, false, false);
                mProgressbar.setVisibility(View.VISIBLE);
                mTutorDetailPresenter.addTuition(tuition);
            }
        });
        mCreateChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tutorId = mTutor.getTutorId();
                String studentId = LoggedInUser.getLoggedInUser().getUserId();
                StringBuilder sb = new StringBuilder();
                sb.append(tutorId);
                sb.append("#");
                sb.append(studentId);
                String chatId = sb.toString();
                Date chatTime = Calendar.getInstance().getTime();
                Chat chat = new Chat(chatId, chatTime, tutorId, studentId);
                mProgressbar.setVisibility(View.VISIBLE);
                mTutorDetailPresenter.addChat(chat);
            }
        });
    }

    @Override
    protected void onStart() {
        mProgressbar.setVisibility(View.VISIBLE);
        if(mTutorDetailPresenter == null){
            mTutorDetailPresenter = new TutorDetailPresenter(TutorDetailActivity.this);
        }
        super.onStart();
    }

    @Override
    public void onLoadComplete(Tutor tutor) {
        //get values
        mTutor = tutor;
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
        mProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void onApplyTuitionSuccess(String tuitionId) {
        Intent intent = new Intent(TutorDetailActivity.this, TuitionsListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onAddChatSuccess(String chatId) {
        Intent intent = new Intent(TutorDetailActivity.this, ChatListActivity.class);
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
            mTutorDetailPresenter.logoutUser();
            startActivity(new Intent(TutorDetailActivity.this, LoginActivity.class));
        }
        return true;
    }
    @Override
    public void onResult(String message) {
        mProgressbar.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    private void getTutorIntent(){
        if(getIntent().hasExtra("tutorId")){
            String tutorId = getIntent().getStringExtra("tutorId");
            if(mTutorDetailPresenter == null){
                mTutorDetailPresenter = new TutorDetailPresenter(this);
            }
            mTutorDetailPresenter.loadTutor(tutorId);
        }
    }
}
