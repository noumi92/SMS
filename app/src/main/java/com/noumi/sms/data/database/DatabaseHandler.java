package com.noumi.sms.data.database;
//it is a database handler class. it is used to perform all database functions. On result it passes result to corresponding presenter class
//for onward messaging to view class. No view directly accesses this class view accesses presenter and presenter calls methods of databasehandler

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.noumi.sms.data.model.Chat;
import com.noumi.sms.data.model.LoggedInUser;
import com.noumi.sms.data.model.Message;
import com.noumi.sms.data.model.Rating;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.data.model.Tuition;
import com.noumi.sms.data.model.Tutor;
import com.noumi.sms.ui.chat.list.ChatAdapterInterface;
import com.noumi.sms.ui.chat.list.ChatListPresenter;
import com.noumi.sms.ui.chat.room.ChatRoomPresenter;
import com.noumi.sms.ui.chat.room.ChatRoomPresenterInterface;
import com.noumi.sms.ui.forgotpassword.ForgotPasswordPresenterInterface;
import com.noumi.sms.ui.login.LoginPresenterInterface;
import com.noumi.sms.ui.rating.RatingListPresenterInterface;
import com.noumi.sms.ui.signup.SignupPresenterInterface;
import com.noumi.sms.ui.students.details.StudentDetailPresenterInterface;
import com.noumi.sms.ui.students.list.StudentListPresenterInterface;
import com.noumi.sms.ui.students.profile.StudentProfilePresenterInterface;
import com.noumi.sms.ui.tuition.detail.TuitionDetailPresenter;
import com.noumi.sms.ui.tuition.detail.TuitionDetailPresenterInterface;
import com.noumi.sms.ui.tuition.list.TuitionListPresenterInterface;
import com.noumi.sms.ui.tutors.detail.TutorDetailPresenterInterface;
import com.noumi.sms.ui.tutors.list.TutorListPresenterInterface;
import com.noumi.sms.ui.tutors.map.TutorMapPresenterInterface;
import com.noumi.sms.ui.tutors.profile.TutorLocationPresenterInterface;
import com.noumi.sms.ui.tutors.profile.TutorProfilePresenterInterface;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler implements DatabaseInterface {
    //fields
    private String TAG = "com.noumi.sms.custom.log";
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;
    private FirebaseUser mUser;
    private Tutor mTutor;
    private Tuition mTuition;
    private Chat mChat;
    private List<Student> mStudents;
    private List<Tutor> mTutors;
    private List<Tuition> mTuitions;
    private List<Chat> mChats;
    private List<Message> mMessages;
    private Boolean mUserTypeSetFlag;
    private List<Rating> mRatings;

    //constructor which initializes firebase authentication and firestore database instances
    public DatabaseHandler() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseFirestore.getInstance();
        mUser = null;
        mStudents = new ArrayList<>();
        mTutors = new ArrayList<>();
        mTuitions = new ArrayList<>();
        mChats = new ArrayList<>();
        mMessages = new ArrayList<>();
        mRatings = new ArrayList<>();
    }
    //public methods which are defined in databaseinterface and implemented in handler class
    //method to register new student
    @Override
    public void signupStudent(final Student student, String password, final SignupPresenterInterface signupPresenter){
        mAuth.createUserWithEmailAndPassword(student.getStudentEmail(), password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        signupPresenter.onQueryResult("User created successfully...");
                        student.setStudentId(authResult.getUser().getUid());
                        FirebaseInstanceId.getInstance().getInstanceId()
                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                        if(task.isSuccessful()){
                                            student.setTokenId(task.getResult().getToken());
                                            insertStudent(student, signupPresenter);
                                        }
                                    }
                                });
                        authResult.getUser().sendEmailVerification()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            mAuth.signOut();
                                            signupPresenter.onQueryResult("Email Verification sent");
                                            signupPresenter.onSignupSuccess();
                                        }
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        signupPresenter.onQueryResult(e.getMessage());
                    }
                });
    }
    //method to register new student
    @Override
    public void signupTutor(final Tutor tutor, String password, final SignupPresenterInterface signupPresenter){
        mAuth.createUserWithEmailAndPassword(tutor.getTutorEmail(), password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        signupPresenter.onQueryResult("User created successfully...");
                        tutor.setTutorId(authResult.getUser().getUid());
                        FirebaseInstanceId.getInstance().getInstanceId()
                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                        if(task.isSuccessful()){
                                            tutor.setTokenId(task.getResult().getToken());
                                            insertTutor(tutor, signupPresenter);
                                        }
                                    }
                                });
                        authResult.getUser().sendEmailVerification()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            mAuth.signOut();
                                            signupPresenter.onQueryResult("Email Verification sent");
                                            signupPresenter.onSignupSuccess();
                                        }
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        signupPresenter.onQueryResult(e.getMessage());
                    }
                });
    }
    //on application startup this method checks if user is previously logged in or not.
    @Override
    public void checkLogin(final LoginPresenterInterface loginPresenter) {
        if(mAuth.getCurrentUser() != null){
            Log.d(TAG, "current user not null");
            mUser = mAuth.getCurrentUser();
            if(mUser.isEmailVerified()) {
                Log.d(TAG, "email verified");
                LoggedInUser.getLoggedInUser().setUserId(mUser.getUid());
                LoggedInUser.getLoggedInUser().setUserEmail(mUser.getEmail());
                String userId = LoggedInUser.getLoggedInUser().getUserId();
                mUserTypeSetFlag = false;
                if(!mUserTypeSetFlag) {
                    Log.d(TAG, "checkin in student database");
                    mDatabase.collection("students").document(userId).get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Log.d(TAG, "user found in student database");
                                            LoggedInUser.getLoggedInUser().setUserType("student");
                                            loginPresenter.onLoginSuccess(LoggedInUser.getLoggedInUser().getUserType());
                                            loginPresenter.onQueryResult("Welcome Back...." + LoggedInUser.getLoggedInUser().getUserEmail());
                                            mUserTypeSetFlag = true;
                                        }
                                    } else {
                                        Log.d(TAG, task.getException().getMessage());
                                        loginPresenter.onLoginFailure();
                                    }
                                }
                            });
                }
                if(!mUserTypeSetFlag) {
                    Log.d(TAG, "checkin in tutor database");
                    mDatabase.collection("tutors").document(userId).get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Log.d(TAG, "user found in tutor database");
                                            LoggedInUser.getLoggedInUser().setUserType("tutor");
                                            loginPresenter.onLoginSuccess(LoggedInUser.getLoggedInUser().getUserType());
                                            loginPresenter.onQueryResult("Welcome Back...." + LoggedInUser.getLoggedInUser().getUserEmail());
                                            mUserTypeSetFlag = true;
                                        }
                                    } else {
                                        Log.d(TAG, task.getException().getMessage());
                                        loginPresenter.onLoginFailure();
                                    }
                                }
                            });
                }
            }
            else{
                Log.d(TAG, "email not verified");
                loginPresenter.onLoginFailure();
            }
        }else{
            Log.d(TAG, "current user is null");
            loginPresenter.onLoginFailure();
        }
    }
    //method to perform firebase authentication to login user
    @Override
    public void LoginUser(String email, String password, final String userType, final LoginPresenterInterface loginPresenter) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mUser = mAuth.getCurrentUser();
                            Log.d(TAG, "Credentials verefied for " + mUser.getEmail());
                            if (mUser != null) {
                                Log.d(TAG, "User is not empty" + mUser.getEmail());
                                if (mUser.isEmailVerified()) {
                                    Log.d(TAG, "Email verified for " + mUser.getEmail());
                                    if (userType.equals("student")) {
                                        mDatabase.collection("students").document(mUser.getUid()).get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        if (documentSnapshot.exists()) {
                                                            Log.d(TAG, "In student section Usertype verefied as...." + userType + " for " + mUser.getEmail());
                                                            LoggedInUser.getLoggedInUser().setUserEmail(mUser.getEmail());
                                                            LoggedInUser.getLoggedInUser().setUserId(mUser.getUid());
                                                            LoggedInUser.getLoggedInUser().setUserType("student");
                                                            updateStudentTokenId(mUser.getUid());
                                                            loginPresenter.onLoginSuccess("student");
                                                            loginPresenter.onQueryResult("Login as.." + mUser.getEmail());
                                                            Log.d(TAG, "Login as.." + mUser.getEmail());
                                                        } else {
                                                            LoggedInUser.getLoggedInUser().setUserEmail("");
                                                            LoggedInUser.getLoggedInUser().setUserId("");
                                                            mAuth.signOut();
                                                            loginPresenter.onLoginFailure();
                                                            Log.d(TAG, "user type validation failed ");
                                                        }
                                                    }
                                                });
                                    } else if (userType.equals("tutor")) {
                                        mDatabase.collection("tutors").document(mUser.getUid()).get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        if (documentSnapshot.exists()) {
                                                            Log.d(TAG, "In student section Usertype verefied as...." + userType + " for " + mUser.getEmail());
                                                            LoggedInUser.getLoggedInUser().setUserEmail(mUser.getEmail());
                                                            LoggedInUser.getLoggedInUser().setUserId(mUser.getUid());
                                                            LoggedInUser.getLoggedInUser().setUserType("tutor");
                                                            updateTutorTokenId(mUser.getUid());
                                                            loginPresenter.onLoginSuccess("tutor");
                                                            loginPresenter.onQueryResult("Login as.." + mUser.getEmail());
                                                            Log.d(TAG, "Login as.." + mUser.getEmail());
                                                        } else {
                                                            LoggedInUser.getLoggedInUser().setUserEmail("");
                                                            LoggedInUser.getLoggedInUser().setUserId("");
                                                            mAuth.signOut();
                                                            loginPresenter.onLoginFailure();
                                                            Log.d(TAG, "user type validation failed ");
                                                        }
                                                    }
                                                });
                                    }
                                } else {
                                    LoggedInUser.getLoggedInUser().setUserEmail("");
                                    LoggedInUser.getLoggedInUser().setUserId("");
                                    mAuth.signOut();
                                    loginPresenter.onQueryResult("Email not verified...");
                                    loginPresenter.onLoginFailure();
                                    loginPresenter.onLoginFailure();
                                    Log.d(TAG, "Email not verified");
                                }
                            } else {
                                loginPresenter.onQueryResult("User is empty..");
                                loginPresenter.onLoginFailure();
                                Log.d(TAG, "User is empty" + mUser.getEmail());
                            }
                        } else {
                            loginPresenter.onQueryResult("Error signing in: " + task.getException().getMessage());
                            loginPresenter.onLoginFailure();
                            Log.d(TAG, "Error signing in: " + task.getException().getMessage());
                        }
                    }
                });
    }

    private void updateStudentTokenId(final String uid) {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if(task.isSuccessful()){
                            String tokenId = task.getResult().getToken();
                            mDatabase.collection("students").document(uid).update("tokenId", tokenId);
                        }
                    }
                });
    }
    private void updateTutorTokenId(final String uid) {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if(task.isSuccessful()){
                            String tokenId = task.getResult().getToken();
                            mDatabase.collection("tutors").document(uid).update("tokenId", tokenId);
                        }
                    }
                });
    }

    //method to fetch all students from firestore database
    @Override
    public void getStudents(final StudentListPresenterInterface listPresenter) {
        mStudents.clear();
        mDatabase.collection("students").get()
           .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
               @Override
               public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()){
                            Log.d(TAG, "database query getstudents: " + document.toObject(Student.class).toString());
                            mStudents.add(document.toObject(Student.class));
                        }
                        listPresenter.onDataLoadComplete(mStudents);
                    }
               }
           });
    }
    //method to fetch all students from firestore database
    @Override
    public void getTutors(final TutorListPresenterInterface listPresenter) {
        mTutors.clear();
        mDatabase.collection("tutors").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, "database query gettutors: " + document.toObject(Tutor.class).toString());
                                mTutors.add(document.toObject(Tutor.class));
                            }
                            listPresenter.onDataLoadComplete(mTutors);
                        }
                    }
                });
    }
    //method to fetch students by gender
    @Override
    public void getStudentsByGender(String gender, final StudentListPresenterInterface studentListPresenter) {
        mStudents.clear();
        mDatabase.collection("students").whereEqualTo("studentGender", gender).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, "database query getstudents by gender: " + document.toObject(Student.class).toString());
                                mStudents.add(document.toObject(Student.class));
                            }
                            studentListPresenter.onDataLoadComplete(mStudents);
                        }
                    }
                });
    }
    //method to get students from firestore filtered by gender
    @Override
    public void getStudentsByCity(String city, final StudentListPresenterInterface studentListPresenter) {
        mStudents.clear();
        mDatabase.collection("students").whereEqualTo("studentCity", city).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, "database query getstudents by city: " + document.toObject(Student.class).toString());
                                mStudents.add(document.toObject(Student.class));
                            }
                            studentListPresenter.onDataLoadComplete(mStudents);
                        }
                    }
                });
    }
    //method to fetch students by city and gender. this method performs a composite query by logically AND on two fields
    @Override
    public void getStudentsByCityAndGender(String city, String gender, final StudentListPresenterInterface studentListPresenter) {
        mStudents.clear();
        mDatabase.collection("students").whereEqualTo("studentGender", gender).whereEqualTo("studentCity", city).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, "database query getstudents by city and gender: " + document.toObject(Student.class).toString());
                                mStudents.add(document.toObject(Student.class));
                            }
                            studentListPresenter.onDataLoadComplete(mStudents);
                        }
                    }
                });
    }
    //method to logout user
    @Override
    public void logoutUser() {
        String userType = LoggedInUser.getLoggedInUser().getUserType();
        if(TextUtils.equals(userType, "student")){
            mDatabase.collection("students").document(LoggedInUser.getLoggedInUser().getUserId()).update("tokenId", "");
        }else if(TextUtils.equals(userType, "tutor")){
            mDatabase.collection("tutors").document(LoggedInUser.getLoggedInUser().getUserId()).update("tokenId", "");
        }
        mAuth.signOut();
    }
    //method to reset email for password reset
    @Override
    public void resetPassword(String email, final ForgotPasswordPresenterInterface forgotPasswordPresenter) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "reset email sent");
                            forgotPasswordPresenter.onQueryResult("Password Reset Email Sent");
                        }else{
                            Log.d(TAG, "Error sending password reset email: " + task.getException().getMessage());
                            forgotPasswordPresenter.onQueryResult("Error sending password reset email: " + task.getException().getMessage());
                        }
                    }
                });
    }
    @Override
    public void loadStudent(String studentId, final StudentProfilePresenterInterface studentProfilePresenterInterface) {
        mDatabase.collection("students").document(studentId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                Student student = document.toObject(Student.class);
                                LoggedInUser.getLoggedInUser().setUserName(student.getStudentName());
                                studentProfilePresenterInterface.onDataLoad(student);
                            }
                            studentProfilePresenterInterface.onQueryResult("load student successful");
                        }else{
                            studentProfilePresenterInterface.onQueryResult(task.getException().getMessage());
                        }
                    }
                });
    }
    public void loadStudent(String studentId, final StudentDetailPresenterInterface studentDetailPresenter) {
        mDatabase.collection("students").document(studentId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                Student student = document.toObject(Student.class);
                                LoggedInUser.getLoggedInUser().setUserName(student.getStudentName());
                                studentDetailPresenter.onDataLoad(student);
                            }
                            studentDetailPresenter.onQueryResult("load student successful");
                        }else{
                            studentDetailPresenter.onQueryResult(task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void loadStudent(String studentId, final TutorMapPresenterInterface tutorMapPresenter) {
        mDatabase.collection("students").document(studentId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                Student student = document.toObject(Student.class);
                                LoggedInUser.getLoggedInUser().setUserName(student.getStudentName());
                                tutorMapPresenter.onStudentLoad(student);
                            }
                            tutorMapPresenter.onQueryResult("load student successful");
                        }else{
                            tutorMapPresenter.onQueryResult(task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void loadTutor(String tutorId, final TutorProfilePresenterInterface tutorProfilePresenterInterface) {
        mDatabase.collection("tutors").document(tutorId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            final DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                final Tutor tutor = new Tutor();
                                tutor.setTutorId(document.getString("tutorId"));
                                tutor.setTutorName(document.getString("tutorName"));
                                tutor.setTutorEmail(document.getString("tutorEmail"));
                                tutor.setTutorCity(document.getString("tutorCity"));
                                tutor.setTutorGender(document.getString("tutorGender"));
                                tutor.setTutorRating(document.getLong("tutorRating"));
                                tutor.setTutorFee(document.getLong("tutorFee"));
                                tutor.setTutorAboutMe(document.getString("tutorAboutMe"));
                                tutor.setTutorLocation(document.getGeoPoint("tutorLocation"));
                                List<String> subjects = (List<String>) document.get("tutorSubjects");
                                tutor.setTutorSubjects(subjects);
                                tutor.setTutorDegreeName(document.getString("tutorDegreeName"));
                                tutor.setTutorDegreeSubject(document.getString("tutorDegreeSubject"));
                                LoggedInUser.getLoggedInUser().setUserName(tutor.getTutorName());
                                tutorProfilePresenterInterface.onDataLoad(tutor);
                                tutorProfilePresenterInterface.onQueryResult("load tutor successful");
                            }
                        }else{
                            Log.d(TAG, "tutor load failed: " + task.getException().getMessage());
                            tutorProfilePresenterInterface.onQueryResult(task.getException().getMessage());
                        }
                    }
                });
    }
    @Override
    public void loadTutor(String tutorId, final TutorDetailPresenterInterface tutorDetailPresenterInterface) {
        mDatabase.collection("tutors").document(tutorId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            final DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                final Tutor tutor = new Tutor();
                                tutor.setTutorId(document.getString("tutorId"));
                                tutor.setTutorName(document.getString("tutorName"));
                                tutor.setTutorEmail(document.getString("tutorEmail"));
                                tutor.setTutorCity(document.getString("tutorCity"));
                                tutor.setTutorGender(document.getString("tutorGender"));
                                tutor.setTutorRating(document.getLong("tutorRating"));
                                tutor.setTutorFee(document.getLong("tutorFee"));
                                tutor.setTutorAboutMe(document.getString("tutorAboutMe"));
                                tutor.setTutorLocation(document.getGeoPoint("tutorLocation"));
                                List<String> subjects = (List<String>) document.get("tutorSubjects");
                                tutor.setTutorSubjects(subjects);
                                tutor.setTutorDegreeName(document.getString("tutorDegreeName"));
                                tutor.setTutorDegreeSubject(document.getString("tutorDegreeSubject"));
                                tutorDetailPresenterInterface.onDataLoad(tutor);
                                tutorDetailPresenterInterface.onQueryResult("load tutor successful");
                            }
                        }else{
                            Log.d(TAG, "tutor load failed: " + task.getException().getMessage());
                            tutorDetailPresenterInterface.onQueryResult(task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void addTuition(final Tuition tuition, final TutorDetailPresenterInterface tutorDetailPresenterInterface) {
        mDatabase.collection("tuitions").document(tuition.getTuitionId()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> checExixtingTask) {
                        if(checExixtingTask.isSuccessful()){
                            DocumentSnapshot document = checExixtingTask.getResult();
                            if(document.exists()){
                                tutorDetailPresenterInterface.onQueryResult("Record already exist");
                                tutorDetailPresenterInterface.onApplyTuitionSuccess(tuition.getTuitionId());
                            }else{
                                mDatabase.collection("tuitions").document(tuition.getTuitionId()).set(tuition)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> addTuitiontask) {
                                                if(addTuitiontask.isSuccessful()){
                                                    Rating rating = new Rating(tuition.getTuitionId(), tuition.getTutorId(), tuition.getStudentId());
                                                    mDatabase.collection("ratings").document(tuition.getTuitionId()).set(rating)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> addRatingtask) {
                                                                    if(addRatingtask.isSuccessful()){
                                                                        tutorDetailPresenterInterface.onApplyTuitionSuccess(tuition.getTuitionId());
                                                                    }else{
                                                                        tutorDetailPresenterInterface.onQueryResult("rating insertion failed..." + addRatingtask.getException().getMessage());
                                                                    }
                                                                }
                                                            });
                                                }else{
                                                    tutorDetailPresenterInterface.onQueryResult("tuition insertion failed..." + addTuitiontask.getException().getMessage());
                                                }
                                            }
                                        });
                            }
                        }else{
                            tutorDetailPresenterInterface.onQueryResult("tuition insertion failed" + checExixtingTask.getException().getMessage());
                        }
                    }
                });

    }
    @Override
    public void addTuition(final Tuition tuition, final StudentDetailPresenterInterface studentDetailPresenter) {
        mDatabase.collection("tuitions").document(tuition.getTuitionId()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> checExixtingTask) {
                        if(checExixtingTask.isSuccessful()){
                            DocumentSnapshot document = checExixtingTask.getResult();
                            if(document.exists()){
                                studentDetailPresenter.onQueryResult("Record already exist");
                                studentDetailPresenter.onOfferTuitionSuccess(tuition.getTuitionId());
                            }else{
                                mDatabase.collection("tuitions").document(tuition.getTuitionId()).set(tuition)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> addTuitiontask) {
                                                if(addTuitiontask.isSuccessful()){
                                                    Rating rating = new Rating(tuition.getTuitionId(), tuition.getTutorId(), tuition.getStudentId());
                                                    mDatabase.collection("ratings").document(tuition.getTuitionId()).set(rating)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> addRatingtask) {
                                                                    if(addRatingtask.isSuccessful()){
                                                                        studentDetailPresenter.onOfferTuitionSuccess(tuition.getTuitionId());
                                                                    }else{
                                                                        studentDetailPresenter.onQueryResult("rating insertion failed..." + addRatingtask.getException().getMessage());
                                                                    }
                                                                }
                                                            });
                                                }else{
                                                    studentDetailPresenter.onQueryResult("tuition insertion failed..." + addTuitiontask.getException().getMessage());
                                                }
                                            }
                                        });
                            }
                        }else{
                            studentDetailPresenter.onQueryResult("tuition insertion failed" + checExixtingTask.getException().getMessage());
                        }
                    }
                });

    }
    @Override
    public void addChat(final Chat chat, final TutorDetailPresenterInterface tutorDetailPresenterInterface) {
        mDatabase.collection("chats").document(chat.getChatId()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                tutorDetailPresenterInterface.onQueryResult("Record already exist");
                                tutorDetailPresenterInterface.onAddChatSuccess(chat.getChatId());
                            }else{
                                mDatabase.collection("chats").document(chat.getChatId()).set(chat)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    tutorDetailPresenterInterface.onQueryResult("chat room created....");
                                                    tutorDetailPresenterInterface.onAddChatSuccess(chat.getChatId());
                                                }else{
                                                    tutorDetailPresenterInterface.onQueryResult("chat insertion failed..." + task.getException().getMessage());
                                                }
                                            }
                                        });
                            }
                        }else{
                            tutorDetailPresenterInterface.onQueryResult("chat creation failed" + task.getException().getMessage());
                        }
                    }
                });

    }

    //method to fetch tutors by gender
    @Override
    public void getTutorsByGender(String gender, final TutorListPresenterInterface tutorListPresenter) {
        mTutors.clear();
        mDatabase.collection("tutors").whereEqualTo("tutorGender", gender).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, "database query gettutorss by gender: " + document.toObject(Tutor.class).toString());
                                mTutors.add(document.toObject(Tutor.class));
                            }
                            tutorListPresenter.onDataLoadComplete(mTutors);
                        }
                    }
                });
    }
    //method to get tutors from firestore filtered by gender
    @Override
    public void getTutorsByCity(String city, final TutorListPresenterInterface tutorListPresenter) {
        mTutors.clear();
        mDatabase.collection("tutors").whereEqualTo("tutorCity", city).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, "database query gettutors by city: " + document.toObject(Tutor.class).toString());
                                mTutors.add(document.toObject(Tutor.class));
                            }
                            tutorListPresenter.onDataLoadComplete(mTutors);
                        }
                    }
                });
    }

    @Override
    public void getTutorsByCity(String city, final TutorMapPresenterInterface tutorMapPresenter) {
        mTutors.clear();
        mDatabase.collection("tutors").whereEqualTo("tutorCity", city).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, "database query gettutors by city: " + document.toObject(Tutor.class).toString());
                                mTutors.add(document.toObject(Tutor.class));
                            }
                            tutorMapPresenter.onTutorsLoad(mTutors);
                        }
                    }
                });
    }

    //method to fetch tutors by city and gender. this method performs a composite query by logically AND on two fields
    @Override
    public void getTutorsByCityAndGender(String city, String gender, final TutorListPresenterInterface tutorListPresenter) {
        mTutors.clear();
        mDatabase.collection("tutors").whereEqualTo("tutorGender", gender).whereEqualTo("tutorCity", city).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, "database query gettutors by city and gender: " + document.toObject(Tutor.class).toString());
                                mTutors.add(document.toObject(Tutor.class));
                            }
                            tutorListPresenter.onDataLoadComplete(mTutors);
                        }
                    }
                });
    }
    //getTuitions by student ud
    @Override
    public void getTuitionsByStudentId(String studentId, final TuitionListPresenterInterface tuitionListPresenter) {
        mDatabase.collection("tuitions").whereEqualTo("studentId", studentId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if (!task.getResult().isEmpty()) {
                                for(QueryDocumentSnapshot document: task.getResult()){
                                    mTuitions.add(document.toObject(Tuition.class));
                                }
                                tuitionListPresenter.onDataLoadComplete(mTuitions);
                            }else{
                                tuitionListPresenter.onQueryResult("No Tuitions found");
                            }
                        }else{
                            tuitionListPresenter.onQueryResult(task.getException().getMessage());
                        }
                    }
                });
    }
    @Override
    public void getTuitionsByTutorId(String tutorId, final TuitionListPresenterInterface tuitionListPresenter) {
        mDatabase.collection("tuitions").whereEqualTo("tutorId", tutorId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if (!task.getResult().isEmpty()) {
                                for(QueryDocumentSnapshot document: task.getResult()){
                                    mTuitions.add(document.toObject(Tuition.class));
                                }
                                tuitionListPresenter.onDataLoadComplete(mTuitions);
                            }else{
                                tuitionListPresenter.onQueryResult("No tuitions found");
                            }
                        }else {
                            tuitionListPresenter.onQueryResult(task.getException().getMessage());
                        }
                    }
                });
    }
    //get tutor by id
    @Override
    public void getTutorById(String tutorId, final TuitionListPresenterInterface tuitionListPresenter) {
        mDatabase.collection("tutors").document(tutorId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            mTutor = task.getResult().toObject(Tutor.class);
                        }

                    }
                });
    }

    @Override
    public void loadTuition(String tuitionId, final TuitionDetailPresenter tuitionDetailPresenter) {
        mDatabase.collection("tuitions").document(tuitionId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            mTuition = task.getResult().toObject(Tuition.class);
                            tuitionDetailPresenter.onTuitionLoad(mTuition);
                            tuitionDetailPresenter.onQueryResult("Tuition Loaded Successfully");
                        }else{
                            tuitionDetailPresenter.onQueryResult(task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void loadTutor(String tutorId, final TuitionDetailPresenter tuitionDetailPresenter) {
        mDatabase.collection("tutors").document(tutorId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            mTutor = task.getResult().toObject(Tutor.class);
                            tuitionDetailPresenter.onTutorLoad(mTutor);
                            tuitionDetailPresenter.onQueryResult("Tutor Loaded Successfully");
                        }else{
                            tuitionDetailPresenter.onQueryResult(task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void getChatsByStudentId(String studentId, final ChatListPresenter chatListPresenter) {
        mChats.clear();
        mTutors.clear();
        Log.d(TAG, "getChatsByStudentId started....");
        mDatabase.collection("chats").whereEqualTo("studentId",studentId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if (!task.getResult().isEmpty()) {
                                for(QueryDocumentSnapshot document: task.getResult()) {
                                    final Chat chat = document.toObject(Chat.class);
                                    Log.d(TAG, "chat added@" + chat.getChatId());
                                    mChats.add(chat);
                                    Log.d(TAG, "getting tutor data for: " + chat.getTutorId());
                                    mDatabase.collection("tutors").document(chat.getTutorId()).get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    Tutor tutor = documentSnapshot.toObject(Tutor.class);
                                                    mTutors.add(tutor);
                                                    Log.d(TAG, "tutor added@" + tutor.getTutorId());
                                                    chatListPresenter.onChatsLoadByStudentId(mChats, mTutors);
                                                }
                                            });
                                    }
                            }else{
                                chatListPresenter.onQueryResult("No chats found");
                            }
                        }else{
                            chatListPresenter.onQueryResult(task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void getChatsByTutorId(String tutorId, final ChatListPresenter chatListPresenter) {
        mChats.clear();
        mStudents.clear();
        mDatabase.collection("chats").whereEqualTo("tutorId",tutorId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "chats fetch task success");
                            if (!task.getResult().isEmpty()) {
                                for(QueryDocumentSnapshot document: task.getResult()){
                                    Chat chat = document.toObject(Chat.class);
                                    Log.d(TAG,"chat added at" + chat.getStudentId());
                                    mChats.add(chat);
                                    mDatabase.collection("students").document(chat.getStudentId()).get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    Log.d(TAG, "students fetch task success");
                                                    mStudents.add(task.getResult().toObject(Student.class));
                                                    chatListPresenter.onChatsLoadByTutorId(mChats, mStudents);
                                                }
                                            });
                                }
                            }else{
                                chatListPresenter.onQueryResult("No chats found");
                            }
                        }else{
                            chatListPresenter.onQueryResult(task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void getMessages(String chatId, final ChatRoomPresenter chatRoomPresenter) {
        mMessages.clear();
        mDatabase.collection("chats").document(chatId).collection("messages").orderBy("messageTime").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, "database query gettutors: " + document.toObject(Message.class).toString());
                                Message message = document.toObject(Message.class);
                                mMessages.add(message);
                            }
                            chatRoomPresenter.onMessagesLoadComplete(mMessages);
                        }
                    }
                });
    }

    @Override
    public void sendMessage(String chatId, Message message, final ChatRoomPresenterInterface chatRoomPresenter) {
        mDatabase.collection("chats").document(chatId).collection("messages").document().set(message)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            chatRoomPresenter.onQueryResult("Message sent");
                        }else{
                            chatRoomPresenter.onQueryResult(task.getException().getMessage());
                        }
                    }
                });
    }

    //private utility methods
    //utility method to insert student into database after user is created
    private void insertStudent(Student student, final SignupPresenterInterface signupPresenter) {
        String userId = student.getStudentId();
        Log.d(TAG, "userId in insertStudent() " + userId);
        mDatabase.collection("students").document(userId).set(student)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        signupPresenter.onQueryResult("Student added in database");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        signupPresenter.onQueryResult("Error: " + e.getMessage());
                        Log.d(TAG, "Error inserting student in database: " + e.getMessage());
                    }
                });
    }
    //utility method to insert tutor into database after user is created
    private void insertTutor(Tutor tutor, final SignupPresenterInterface signupPresenter) {
        String userId = tutor.getTutorId();
        Log.d(TAG, "userId in insertTutor() " + userId);
        mDatabase.collection("tutors").document(userId).set(tutor)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        signupPresenter.onQueryResult("Tutor added in database");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        signupPresenter.onQueryResult("Error: " + e.getMessage());
                        Log.d(TAG, "Error inserting tutor in database: " + e.getMessage());
                    }
                });
    }

    @Override
    public void loadRating(String ratingId, final TuitionDetailPresenterInterface tuitionDetailPresenter) {
        mDatabase.collection("ratings").document(ratingId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            Rating rating = task.getResult().toObject(Rating.class);
                            tuitionDetailPresenter.onRatingLoad(rating);
                        }else{
                            tuitionDetailPresenter.onQueryResult("Rating load failed" + task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void updateStudent(final StudentProfilePresenterInterface studentProfilePresenter, Student student) {
        mDatabase.collection("students").document(student.getStudentId()).set(student)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            studentProfilePresenter.onUpdateStudentSuccess();
                        } else{
                            studentProfilePresenter.onQueryResult("Update Error: " + task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void updateTutor(Tutor tutor, final TutorProfilePresenterInterface tutorProfilePresenter) {
        mDatabase.collection("tutors").document(tutor.getTutorId()).set(tutor)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            tutorProfilePresenter.onUpdateTutorSuccess();
                        } else{
                            tutorProfilePresenter.onQueryResult("Update Error: " + task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void updateTutorLocationById(String id, GeoPoint geoPoint, final TutorLocationPresenterInterface tutorLocationPresenter) {
        mDatabase.collection("tutors").document(id).update("tutorLocation", geoPoint)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            tutorLocationPresenter.onTutorLocationUpdate();
                            tutorLocationPresenter.OnQueryResult("Tutor lcation updated");
                        }else{
                            tutorLocationPresenter.OnQueryResult("Tutor Update Failed: " + task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void deleteTutorById(final String tutorId, final TutorProfilePresenterInterface tutorProfilePresenter) {
        Log.d(TAG, "Account deletion started");
        Log.d(TAG, "deleting chat records");
        mDatabase.collection("chats").whereEqualTo("tutorId", tutorId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(final QueryDocumentSnapshot chatdocument : task.getResult()){
                            mDatabase.collection("chats").document(chatdocument.getId()).collection("messages").get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            Log.d(TAG, "deleting messages records");
                                            for(QueryDocumentSnapshot messageDocument : task.getResult()){
                                                mDatabase.collection("chats").document(chatdocument.getId())
                                                        .collection("messages").document(messageDocument.getId()).delete();
                                            }
                                        }
                                    });
                            Log.d(TAG, "deleting chat");
                            mDatabase.collection("chats").document(chatdocument.getId()).delete();
                        }
                        Log.d(TAG, "deleting tuition records");
                        mDatabase.collection("tuitions").whereEqualTo("tutorId", tutorId).get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        for(QueryDocumentSnapshot document : task.getResult()){
                                            mDatabase.collection("tuitions").document(document.getId()).delete();
                                        }
                                        Log.d(TAG, "deleting ratings records");
                                        mDatabase.collection("ratings").whereEqualTo("tutorId", tutorId).get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        for(QueryDocumentSnapshot document : task.getResult()){
                                                            mDatabase.collection("ratings").document(document.getId()).delete();
                                                        }
                                                        Log.d(TAG, "deleting tutor profile");
                                                        mDatabase.collection("tutors").document(tutorId).delete();
                                                        Log.d(TAG, "deleting user authentication record");
                                                        mAuth.getCurrentUser().delete()
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        Log.d(TAG, "Account deleted from system");
                                                                        tutorProfilePresenter.onQueryResult("Account Deleted from Database");
                                                                        tutorProfilePresenter.onDeleteAccount();
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                });

    }

    @Override
    public void deleteStudentById(final String studentId, final StudentProfilePresenterInterface studentProfilePresenter) {
        Log.d(TAG, "Account deletion started");
        Log.d(TAG, "deleting chat records");
        mDatabase.collection("chats").whereEqualTo("studentId", studentId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(final QueryDocumentSnapshot chatdocument : task.getResult()){
                            mDatabase.collection("chats").document(chatdocument.getId()).collection("messages").get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            Log.d(TAG, "deleting messages records");
                                            for(QueryDocumentSnapshot messageDocument : task.getResult()){
                                                mDatabase.collection("chats").document(chatdocument.getId())
                                                        .collection("messages").document(messageDocument.getId()).delete();
                                            }
                                        }
                                    });
                            Log.d(TAG, "deleting chat");
                            mDatabase.collection("chats").document(chatdocument.getId()).delete();
                        }
                        Log.d(TAG, "deleting tuition records");
                        mDatabase.collection("tuitions").whereEqualTo("studentId", studentId).get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        for(QueryDocumentSnapshot document : task.getResult()){
                                            mDatabase.collection("tuitions").document(document.getId()).delete();
                                            mDatabase.collection("ratings").document(document.getId()).delete();
                                        }
                                            Log.d(TAG, "deleting tutor profile");
                                            mDatabase.collection("students").document(studentId).delete();
                                            Log.d(TAG, "deleting user authentication record");
                                            mAuth.getCurrentUser().delete()
                                                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                       Log.d(TAG, "Account deleted from system");
                                                       studentProfilePresenter.onQueryResult("Account Deleted from Database");
                                                        studentProfilePresenter.onDeleteAccount();
                                                            }
                                                                });
                                                    }
                                                });
                                    }
                                });
    }

    @Override
    public void updateTuitionRating(Rating rating, final TuitionDetailPresenterInterface tuitionDetailPresenter) {
        mDatabase.collection("ratings").document(rating.getRatingId()).set(rating)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            tuitionDetailPresenter.onQueryResult("Ratings updated");
                            tuitionDetailPresenter.onRatingUpdate();
                        }else {
                            tuitionDetailPresenter.onQueryResult("Rating update failed" + task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void updateTuition(Tuition tuition, final TuitionDetailPresenterInterface tuitionDetailPresenter) {
        mDatabase.collection("tuitions").document(tuition.getTuitionId()).set(tuition)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            tuitionDetailPresenter.onQueryResult("Tuition updated");
                            tuitionDetailPresenter.onTuitionUpdate();
                        }else {
                            tuitionDetailPresenter.onQueryResult("Tuition update failed" + task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void deleteTuition(String tuitionId, final TuitionDetailPresenterInterface tuitionDetailPresenter) {
        mDatabase.collection("tuitions").document(tuitionId).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            tuitionDetailPresenter.onQueryResult("Tuition Deleted");
                            tuitionDetailPresenter.onTuitionDelete();
                        }else {
                            tuitionDetailPresenter.onQueryResult("Tuition delete failed" + task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void getRatingsByTutorId(String tutorId, final RatingListPresenterInterface ratingListPresenter) {
        mStudents.clear();
        mRatings.clear();
        mDatabase.collection("ratings").whereEqualTo("tutorId", tutorId).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for(QueryDocumentSnapshot document: queryDocumentSnapshots){
                                Log.d(TAG, "getting rating data");
                                final Rating rating = document.toObject(Rating.class);
                                Log.d(TAG, "rating data" + rating.toString());
                                mRatings.add(rating);
                                mDatabase.collection("students").document(rating.getStudentId()).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.isSuccessful()){
                                                    Student student = task.getResult().toObject(Student.class);
                                                    mStudents.add(student);
                                                    ratingListPresenter.onDataLoadComplete(mRatings, mStudents);
                                                }
                                            }
                                        });
                            }
                        }else{
                            ratingListPresenter.onQueryResult("No Rating found");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    ratingListPresenter.onQueryResult(e.getMessage());
                }
        });
    }

    @Override
    public void deleteChatById(String chatId, ChatAdapterInterface chatAdapterInterface) {
        mDatabase.collection("chats").document(chatId).delete();
    }
}
