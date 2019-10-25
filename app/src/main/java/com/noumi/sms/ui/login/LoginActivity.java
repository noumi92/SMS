package com.noumi.sms.ui.login;

//this class provides the functionality to login a user into the system

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.noumi.sms.R;
import com.noumi.sms.data.model.LoggedInUser;
import com.noumi.sms.ui.forgotpassword.ForgotPasswordActivity;
import com.noumi.sms.ui.signup.SignupActivity;
import com.noumi.sms.ui.students.profile.StudentProfileActivity;
import com.noumi.sms.ui.tutors.list.TutorListActivity;
import com.noumi.sms.ui.tutors.profile.TutorProfileActivity;

public class LoginActivity extends AppCompatActivity implements LoginViewInterface{
    //fields
    private String TAG = "com.noumi.sms.custom.log";
    private LoginPresenterInterface mLoginPresenter;
    private EditText mEmailView;
    private EditText mPasswordView;
    private Button mLoginButton;
    private TextView mRegisterView;
    private TextView mForgotPasswordView;
    private RadioGroup mUserTypeRadioGroup;
    private RadioButton mStudentRadioButton;
    private RadioButton mTutorRadioButton;
    private LinearLayout mProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //get references of views
        mEmailView = (EditText) findViewById(R.id.email_view);
        mPasswordView = (EditText) findViewById(R.id.password_view);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mRegisterView = (TextView) findViewById(R.id.register_view);
        mUserTypeRadioGroup = (RadioGroup) findViewById(R.id.user_type_radio);
        mTutorRadioButton = (RadioButton) findViewById(R.id.user_type_tutor);
        mStudentRadioButton = (RadioButton) findViewById(R.id.user_type_student);
        mForgotPasswordView = (TextView) findViewById(R.id.forgot_password_view);
        mLoginPresenter = new LoginPresenter(LoginActivity.this);
        mProgressbar = (LinearLayout) findViewById(R.id.progressbar);
        final TextView progressbarText = mProgressbar.findViewById(R.id.progressbar_text);
        progressbarText.setText(getString(R.string.wecome_text));
        //login functionality: when user clicks on login this listener validate input fields and login user into the system
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailView.getText().toString().trim();
                String password = mPasswordView.getText().toString();
                String userType = getUserType();
                Log.d(TAG, "Usertype = " + userType);
                if(validateInput(email, password, userType)) {
                    progressbarText.setText("Logging In");
                    mProgressbar.setVisibility(View.VISIBLE);
                    mLoginPresenter.loginUser(email, password, userType);
                }
            }
        });
        //link to open signupactivity for registering new user
        mRegisterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        //link to open ForgotPasswordActivity to reset password
        mForgotPasswordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
    }
    //method to perform initial initializations on start of activity
    @Override
    protected void onStart() {
        super.onStart();
        mProgressbar.setVisibility(View.VISIBLE);
        if (mLoginPresenter == null){
            mLoginPresenter = new LoginPresenter(LoginActivity.this);
        }
        mLoginPresenter.checkLogin();
    }
    //display feedback to user actions
    @Override
    public void onResult(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }
    //call on successful login
    @Override
    public void onLoginSuccess(String userType) {
        if(userType.equals("student")) {
            Intent studentIntent = new Intent(LoginActivity.this, StudentProfileActivity.class);
            studentIntent.putExtra("studentId", LoggedInUser.getLoggedInUser().getUserId());
            startActivity(studentIntent);
        }else if(userType.equals("tutor")){
            Intent tutorIntent = new Intent(LoginActivity.this, TutorProfileActivity.class);
            tutorIntent.putExtra("tutorId", LoggedInUser.getLoggedInUser().getUserId());
            startActivity(tutorIntent);
        }
    }

    @Override
    public void onLoginFailure() {
        mProgressbar.setVisibility(View.GONE);
    }

    //utility method to validate input fields
    private boolean validateInput(String email, String password, String userType) {
        if(email.isEmpty()){
            mEmailView.setError("Please enter email address");
            mEmailView.requestFocus();
            return false;
        }
        if(password.isEmpty()){
            mPasswordView.setError("Please Enter Password");
            mPasswordView.requestFocus();
            return false;
        }
        if(userType == null){
            Toast.makeText(LoginActivity.this, "Please Set a User Type:", Toast.LENGTH_LONG).show();
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
