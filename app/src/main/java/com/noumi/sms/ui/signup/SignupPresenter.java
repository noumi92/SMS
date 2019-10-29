package com.noumi.sms.ui.signup;

//this class connects SignupActivity to database and passes feedbacks from database to SignupActivity to update views and
//feedback to user

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.noumi.sms.data.database.DatabaseHandler;
import com.noumi.sms.data.database.DatabaseInterface;
import com.noumi.sms.data.maps.MapsHandler;
import com.noumi.sms.data.maps.MapsHandlerInterface;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.data.model.Tutor;

public class SignupPresenter implements SignupPresenterInterface {
    //fields
    private String TAG = "com.noumi.sms.custom.log"; // tag for debugging
    private DatabaseInterface mDBHandler;
    private SignupViewInterface mSignupViewInterface;
    private MapsHandlerInterface mMapsHandler;

    public SignupPresenter(SignupViewInterface view) {
        mDBHandler = new DatabaseHandler();
        mMapsHandler = new MapsHandler();
        mSignupViewInterface = view;
    }
    //method to signup student
    @Override
    public void signupStudent(Student student, String password) {
        mDBHandler.signupStudent(student, password, this);
    }
    //method to signup tutor
    @Override
    public void signupTutor(Tutor tutor, String password) {
        mDBHandler.signupTutor(tutor, password, this);
    }
    //method to get feedback from database handler and pass to SignupActivity
    @Override
    public void onQueryResult(String result){
        mSignupViewInterface.onResult(result);
    }

    @Override
    public void getLatLongByCity(String cityName, Context context) {
        mMapsHandler.getLatLongByCity(cityName, context,this);
    }

    @Override
    public void onLatLongByCityLoad(LatLng latLng) {
        mSignupViewInterface.onLatLongByCityLoad(latLng);
    }

    @Override
    public void onSignupSuccess() {
        mSignupViewInterface.onSignupSuccess();
    }
}
