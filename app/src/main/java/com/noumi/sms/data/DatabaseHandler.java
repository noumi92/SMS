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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.noumi.sms.data.model.LoggedInUser;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.ui.forgotpassword.ForgotPasswordPresenter;
import com.noumi.sms.ui.login.LoginPresenter;
import com.noumi.sms.ui.signup.SignupPresenter;
import com.noumi.sms.ui.students.StudentListPresenter;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler implements DatabaseInterface {
    //fields
    private String TAG = "com.noumi.sms.custom.log";
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;
    private FirebaseUser mUser;
    private List<Student> mStudents;
    //constructor which initializes firebase authentication and firestore database instances
    public DatabaseHandler() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseFirestore.getInstance();
        mUser = null;
        mStudents = new ArrayList<>();
    }
    //public methods which are defined in databaseinterface and implemented in handler class
    //method to register new student
    @Override
    public void signupStudent(final Student student, String password, final SignupPresenter signupPresenter){
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
    //on application startup this method checks if user is previously logged in or not.
    @Override
    public void checkLogin(LoginPresenter loginPresenter) {
        if(mAuth.getCurrentUser() != null){
            mUser = mAuth.getCurrentUser();
            if(mUser.isEmailVerified()) {
                LoggedInUser.getLoggedInUser().setUserId(mUser.getUid());
                LoggedInUser.getLoggedInUser().setUserEmail(mUser.getEmail());
                loginPresenter.onLoginSuccess();
                loginPresenter.onQueryResult("Welcome Back...." + LoggedInUser.getLoggedInUser().getUserEmail());
            }
        }
    }
    //method to perform firebase authentication to login user
    @Override
    public void LoginUser(String email, String password, final LoginPresenter loginPresenter) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mUser = mAuth.getCurrentUser();
                            if (mUser != null){
                                if(mUser.isEmailVerified()) {
                                    LoggedInUser.getLoggedInUser().setUserEmail(mUser.getEmail());
                                    LoggedInUser.getLoggedInUser().setUserId(mUser.getUid());
                                    loginPresenter.onLoginSuccess();
                                    loginPresenter.onQueryResult("Login as.." + mUser.getEmail());
                                    Log.d(TAG, "Login as.." + mUser.getEmail());
                                }else{
                                    LoggedInUser.getLoggedInUser().setUserEmail("");
                                    LoggedInUser.getLoggedInUser().setUserId("");
                                    mAuth.signOut();
                                    loginPresenter.onQueryResult("Email not verified...");
                                    Log.d(TAG, "Email not verified");
                                }
                            }
                        }else{
                            loginPresenter.onQueryResult("Error signing in: " + task.getException().getMessage());
                            Log.d(TAG, "Error signing in: " + task.getException().getMessage());
                        }
                    }
                });
    }
    //method to fetch all students from firestore database
    @Override
    public void getStudents(final StudentListPresenter listPresenter) {
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
    //method to fetch students by gender
    @Override
    public void getStudentsByGender(String gender, final StudentListPresenter studentListPresenter) {
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
    public void getStudentsByCity(String city, final StudentListPresenter studentListPresenter) {
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
    public void getStudentsByCityAndGender(String city, String gender, final StudentListPresenter studentListPresenter) {
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
    public void logoutUser(StudentListPresenter listPresenter) {
        mAuth.signOut();
        listPresenter.onQueryResult("Logout Successful...");
    }
    //method to reset email for password reset
    @Override
    public void resetPassword(String email, final ForgotPasswordPresenter forgotPasswordPresenter) {
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

    //private utility methods
    //utility method to insert student into database after user is created
    private void insertStudent(Student student, final SignupPresenter signupPresenter) {
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

}
