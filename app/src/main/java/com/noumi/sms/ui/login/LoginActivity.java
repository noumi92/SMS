package com.noumi.sms.ui.login;

//this class provides the functionality to login a user into the system

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.noumi.sms.R;
import com.noumi.sms.ui.forgotpassword.ForgotPasswordActivity;
import com.noumi.sms.ui.signup.SignupActivity;
import com.noumi.sms.ui.students.StudentListActivity;

public class LoginActivity extends AppCompatActivity implements LoginViewInterface{
    //fields
    private String TAG = "com.noumi.sms.custom.log";
    private LoginPresenter mLoginPresenter;
    private EditText mEmailView;
    private EditText mPasswordView;
    private Button mLoginButton;
    private TextView mRegisterView;
    private TextView mForgotPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //get references of views
        mEmailView = (EditText) findViewById(R.id.email_view);
        mPasswordView = (EditText) findViewById(R.id.password_view);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mRegisterView = (TextView) findViewById(R.id.register_view);
        mForgotPasswordView = (TextView) findViewById(R.id.forgot_password_view);
        mLoginPresenter = new LoginPresenter(LoginActivity.this);
        //login functionality: when user clicks on login this listener validate input fields and login user into the system
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailView.getText().toString().trim();
                String password = mPasswordView.getText().toString();
                if(validateInput(email, password)) {
                    mLoginPresenter.loginUser(email, password);
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
    public void onLoginSuccess() {
        Intent intent = new Intent(LoginActivity.this, StudentListActivity.class);
        startActivity(intent);
    }
    //utility method to validate input fields
    private boolean validateInput(String email, String password) {
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
        return true;
    }
}
