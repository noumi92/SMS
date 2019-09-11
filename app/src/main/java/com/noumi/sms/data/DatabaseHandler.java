package com.noumi.sms.data;
//it is a database handler class. it is used to perform all database functions. On result it passes result to corresponding presenter class
//for onward messaging to view class. No view directly accesses this class view accesses presenter and presenter calls methods of databasehandler

import android.support.annotation.NonNull;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.noumi.sms.data.model.LoggedInUser;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.data.model.Tuition;
import com.noumi.sms.data.model.Tutor;
import com.noumi.sms.ui.forgotpassword.ForgotPasswordPresenterInterface;
import com.noumi.sms.ui.login.LoginPresenterInterface;
import com.noumi.sms.ui.signup.SignupPresenterInterface;
import com.noumi.sms.ui.students.list.StudentListPresenterInterface;
import com.noumi.sms.ui.students.profile.StudentProfilePresenterInterface;
import com.noumi.sms.ui.tutors.detail.TutorDetailPresenterInterface;
import com.noumi.sms.ui.tutors.list.TutorListPresenterInterface;
import com.noumi.sms.ui.tutors.profile.TutorProfilePresenterInterface;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler implements DatabaseInterface {
    //fields
    private String TAG = "com.noumi.sms.custom.log";
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;
    private FirebaseUser mUser;
    private List<Student> mStudents;
    private List<Tutor> mTutors;
    private Boolean mUserTypeSetFlag;
    //constructor which initializes firebase authentication and firestore database instances
    public DatabaseHandler() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseFirestore.getInstance();
        mUser = null;
        mStudents = new ArrayList<>();
        mTutors = new ArrayList<>();
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
                        insertStudent(student, signupPresenter);
                        authResult.getUser().sendEmailVerification()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            signupPresenter.onQueryResult("Email Verification sent");
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
                        insertTutor(tutor, signupPresenter);
                        authResult.getUser().sendEmailVerification()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            signupPresenter.onQueryResult("Email Verification sent");
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
            mUser = mAuth.getCurrentUser();
            if(mUser.isEmailVerified()) {
                LoggedInUser.getLoggedInUser().setUserId(mUser.getUid());
                LoggedInUser.getLoggedInUser().setUserEmail(mUser.getEmail());
                String userId = LoggedInUser.getLoggedInUser().getUserId();
                mUserTypeSetFlag = false;
                if(!mUserTypeSetFlag) {
                    mDatabase.collection("students").document(userId).get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            LoggedInUser.getLoggedInUser().setUserType("student");
                                            loginPresenter.onLoginSuccess(LoggedInUser.getLoggedInUser().getUserType());
                                            loginPresenter.onQueryResult("Welcome Back...." + LoggedInUser.getLoggedInUser().getUserEmail());
                                            mUserTypeSetFlag = true;
                                        }
                                    } else {
                                        Log.d(TAG, task.getException().getMessage());
                                    }
                                }
                            });
                }
                if(!mUserTypeSetFlag) {
                    mDatabase.collection("tutors").document(userId).get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            LoggedInUser.getLoggedInUser().setUserType("tutor");
                                            loginPresenter.onLoginSuccess(LoggedInUser.getLoggedInUser().getUserType());
                                            loginPresenter.onQueryResult("Welcome Back...." + LoggedInUser.getLoggedInUser().getUserEmail());
                                            mUserTypeSetFlag = true;
                                        }
                                    } else {
                                        Log.d(TAG, task.getException().getMessage());
                                    }
                                }
                            });
                }
            }
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
                                                            loginPresenter.onLoginSuccess("student");
                                                            loginPresenter.onQueryResult("Login as.." + mUser.getEmail());
                                                            Log.d(TAG, "Login as.." + mUser.getEmail());
                                                        } else {
                                                            LoggedInUser.getLoggedInUser().setUserEmail("");
                                                            LoggedInUser.getLoggedInUser().setUserId("");
                                                            mAuth.signOut();
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
                                                            loginPresenter.onLoginSuccess("tutor");
                                                            loginPresenter.onQueryResult("Login as.." + mUser.getEmail());
                                                            Log.d(TAG, "Login as.." + mUser.getEmail());
                                                        } else {
                                                            LoggedInUser.getLoggedInUser().setUserEmail("");
                                                            LoggedInUser.getLoggedInUser().setUserId("");
                                                            mAuth.signOut();
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
                                    Log.d(TAG, "Email not verified");
                                }
                            } else {
                                loginPresenter.onQueryResult("User is empty..");
                                Log.d(TAG, "User is empty" + mUser.getEmail());
                            }
                        } else {
                            loginPresenter.onQueryResult("Error signing in: " + task.getException().getMessage());
                            Log.d(TAG, "Error signing in: " + task.getException().getMessage());
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
                                studentProfilePresenterInterface.onDataLoad(student);
                            }
                            studentProfilePresenterInterface.onQueryResult("load student successful");
                        }else{
                            studentProfilePresenterInterface.onQueryResult(task.getException().getMessage());
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
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                tutorDetailPresenterInterface.onQueryResult("Record already exist");
                            }else{
                                mDatabase.collection("tuitions").document(tuition.getTuitionId()).set(tuition)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    tutorDetailPresenterInterface.onQueryResult("tuition inserted....");
                                                }else{
                                                    tutorDetailPresenterInterface.onQueryResult("tuition insertion failed..." + task.getException().getMessage());
                                                }
                                            }
                                        });
                            }
                        }else{
                            tutorDetailPresenterInterface.onQueryResult("tuition insertion failed" + task.getException().getMessage());
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
}
