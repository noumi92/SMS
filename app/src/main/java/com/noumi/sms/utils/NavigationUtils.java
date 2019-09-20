package com.noumi.sms.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import com.noumi.sms.R;
import com.noumi.sms.data.model.LoggedInUser;
import com.noumi.sms.ui.chat.list.ChatListActivity;
import com.noumi.sms.ui.students.profile.StudentProfileActivity;
import com.noumi.sms.ui.tuition.list.TuitionsListActivity;
import com.noumi.sms.ui.tutors.list.TutorListActivity;
import com.noumi.sms.ui.tutors.profile.TutorProfileActivity;

public class NavigationUtils {
    public static void startStudentNaigation(final Context context, NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_student_profile:{
                        Intent studentIntent = new Intent(context, StudentProfileActivity.class);
                        studentIntent.putExtra("studentId", LoggedInUser.getLoggedInUser().getUserId());
                        context.startActivity(studentIntent);
                        return true;
                    }
                    case R.id.nav_browse_tutors:{
                        Intent tutorsIntent = new Intent(context, TutorListActivity.class);
                        context.startActivity(tutorsIntent);
                        return true;
                    }
                    case R.id.nav_browse_tuitions:{
                        Intent tuitionsIntent = new Intent(context, TuitionsListActivity.class);
                        context.startActivity(tuitionsIntent);
                        return true;
                    }
                    case R.id.nav_browse_chats:{
                        Intent tuitionsIntent = new Intent(context, ChatListActivity.class);
                        context.startActivity(tuitionsIntent);
                        return true;
                    }
                }
                return true;
            }
        });
    }
    public static void startTutorNaigation(final Context context, NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_tutor_profile:{
                        Intent tutorIntent = new Intent(context, TutorProfileActivity.class);
                        tutorIntent.putExtra("tutorId", LoggedInUser.getLoggedInUser().getUserId());
                        context.startActivity(tutorIntent);
                        return true;
                    }
                    case R.id.nav_browse_tuitions:{
                        Intent tuitionsIntent = new Intent(context, TuitionsListActivity.class);
                        context.startActivity(tuitionsIntent);
                        return true;
                    }
                    case R.id.nav_browse_chats:{
                        Intent tuitionsIntent = new Intent(context, ChatListActivity.class);
                        context.startActivity(tuitionsIntent);
                        return true;
                    }
                    case R.id.nav_tutor_ratings:{
                        return true;
                    }
                }
                return true;
            }
        });
    }
}
