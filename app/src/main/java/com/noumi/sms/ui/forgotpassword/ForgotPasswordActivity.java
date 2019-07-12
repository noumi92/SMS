package com.noumi.sms.ui.forgotpassword;
//ForgotPasswordActivity class provides the funnctionality to reset password
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.noumi.sms.R;

public class ForgotPasswordActivity extends AppCompatActivity implements ForgotPasswordViewInterface{
    //fields
    private EditText mEmailTextView;
    private Button mPasswordResetButton;
    private ForgotPasswordPresenter mForgotPasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        //get view references here
        mEmailTextView = (EditText) findViewById(R.id.email_view);
        mPasswordResetButton = (Button) findViewById(R.id.reset_password_button);
        //send reset email when user click on password reset button
        mPasswordResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailTextView.getText().toString().trim();
                if(validateEmail(email)){
                    mForgotPasswordPresenter.resetPassword(email);
                }
            }
        });
    }
    //method to provide feedback to user on password reset action by user
    @Override
    public void onResult(String result) {
        Toast.makeText(ForgotPasswordActivity.this, result, Toast.LENGTH_LONG).show();
    }
    //method to perform initial initializations on start of activity
    @Override
    protected void onStart() {
        super.onStart();
        if(mForgotPasswordPresenter == null){
            mForgotPasswordPresenter = new ForgotPasswordPresenter(ForgotPasswordActivity.this);
        }
    }
    //private utility method to validate email input field
    private boolean validateEmail(String email) {
        if(email.isEmpty()){
            mEmailTextView.setError("Please enter email address");
            mEmailTextView.requestFocus();
            return false;
        }
        if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")){
            mEmailTextView.setError("invalid email address");
            mEmailTextView.requestFocus();
            return false;
        }
        return true;
    }
}
