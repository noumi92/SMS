package com.noumi.sms.ui.signup;

//this class provides the functionality for signing up user

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.noumi.sms.R;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.data.model.Tutor;
import com.noumi.sms.ui.login.LoginActivity;

public class SignupActivity extends AppCompatActivity implements SignupViewInterface{
    //fields
    private String TAG = "com.noumi.sms.custom.log"; //tag for debugging
    private SignupPresenterInterface mSignupPresenter;
    private EditText mUsernameView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    private Spinner mGenderSpinner;
    private Spinner mCitySpinner;
    private Button mSignupButton;
    private TextView mLoginView;
    private Student mStudent;
    private Tutor mTutor;
    private RadioGroup mUserTypeRadioGroup;
    private RadioButton mStudentRadioButton;
    private RadioButton mTutorRadioButton;
    private LinearLayout mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.setTitle("Signup Account");
        //get references of views
        mUsernameView = (EditText) findViewById(R.id.name_view);
        mEmailView = (EditText) findViewById(R.id.email_view);
        mPasswordView = (EditText) findViewById(R.id.password_view);
        mConfirmPasswordView = (EditText) findViewById(R.id.password_confirm_view);
        mGenderSpinner = (Spinner) findViewById(R.id.gender_spinner);
        mCitySpinner = (Spinner) findViewById(R.id.city_spinner);
        mSignupButton = (Button) findViewById(R.id.signup_button);
        mLoginView = (TextView) findViewById(R.id.login_view);
        mUserTypeRadioGroup = (RadioGroup) findViewById(R.id.user_type_radio);
        mStudentRadioButton = (RadioButton) findViewById(R.id.user_type_student);
        mTutorRadioButton = (RadioButton) findViewById(R.id.user_type_tutor);
        mProgressBar = (LinearLayout) findViewById(R.id.progressbar);
        //click event response to signup user
        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsernameView.getText().toString();
                String email = mEmailView.getText().toString().trim();
                String password = mPasswordView.getText().toString();
                String confirmPassword = mConfirmPasswordView.getText().toString();
                String gender = mGenderSpinner.getSelectedItem().toString();
                String city = mCitySpinner.getSelectedItem().toString();
                String userType = getUserType();
                if (validateInput(username, email, password, confirmPassword, gender, city, userType)){
                    if(userType.equals("student")) {
                        mStudent = new Student("123", email, username, city, gender);
                        mProgressBar.setVisibility(View.VISIBLE);
                        mSignupPresenter.signupStudent(mStudent, password);
                    }else if(userType.equals("tutor")){
                        mProgressBar.setVisibility(View.VISIBLE);
                        mTutor = new Tutor(username, email, city, gender);
                        mSignupPresenter.signupTutor(mTutor, password);
                    }
                }
            }
        });
        //link to open LoginActivity
        mLoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    //method to display result to user signup action
    @Override
    public void onResult(String message) {
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(SignupActivity.this, message, Toast.LENGTH_LONG).show();
    }
    //method to perform initial initializations on start of activity
    @Override
    protected void onStart() {
        super.onStart();
        if (mSignupPresenter == null){
            mSignupPresenter = new SignupPresenter(SignupActivity.this);
        }
    }
    //utility method to validate input fields
    private boolean validateInput(String username, String email, String password, String confirmPassword, String gender, String city, String userType) {
        if (username.isEmpty()){
            mUsernameView.setError("Please enter username.");
            mUsernameView.requestFocus();
            return false;
        }
        if (email.isEmpty()){
            mEmailView.setError("Please enter email.");
            mEmailView.requestFocus();
            return false;
        }
        if(password.isEmpty()){
            mPasswordView.setError("Please enter password..");
            mPasswordView.requestFocus();
            return false;
        }
        if(password.length() < 8){
            mPasswordView.setError("Password must be of at least 8 characters..");
            mPasswordView.requestFocus();
            return false;
        }
        if(!TextUtils.equals(password, confirmPassword)){
            mConfirmPasswordView.setError("Password does not match...");
            mConfirmPasswordView.requestFocus();
            return false;
        }
        if(userType == null){
            Toast.makeText(SignupActivity.this, "Please Set a User Type:", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private String getUserType() {
        int type = mUserTypeRadioGroup.getCheckedRadioButtonId();
        if (type == mStudentRadioButton.getId()){
            return "student";
        }else if(type == mTutorRadioButton.getId()){
            return "tutor";
        }else{
            return null;
        }
    }
}
