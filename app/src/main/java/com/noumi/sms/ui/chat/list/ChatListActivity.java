package com.noumi.sms.ui.chat.list;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.noumi.sms.R;
import com.noumi.sms.data.model.Chat;
import com.noumi.sms.data.model.LoggedInUser;
import com.noumi.sms.data.model.Tutor;
import com.noumi.sms.ui.login.LoginActivity;
import com.noumi.sms.utils.NavigationUtils;

import java.util.List;

public class ChatListActivity extends AppCompatActivity implements ChatListViewInterface{
    private String TAG = "com.noumi.sms.custom.log";
    private ChatListPresenterInterface mChatsListPresenter;
    private RecyclerView mChatsRecyclerView;
    private ChatAdapter mChatAdapter;
    private DrawerLayout mDrawerLayout;
    private ListDivider mListDivider;
    private NavigationView mNavigationView;
    private String mUserType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        //get references of views
        mChatsRecyclerView = (RecyclerView) findViewById(R.id.chat_recycler_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_menu_view);
        mUserType = LoggedInUser.getLoggedInUser().getUserType();
        Log.d(TAG, "musertype: " + mUserType);
        //setup adapter here
        mChatsRecyclerView.setHasFixedSize(true);
        mChatsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Drawable divider = ContextCompat.getDrawable(this, R.drawable.list_item_seperator);
        mListDivider = new ListDivider(divider);
        mChatsRecyclerView.addItemDecoration(mListDivider);

        //setting up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar!=null){
            toolbar.setTitle(this.getTitle());
            setSupportActionBar(toolbar);
        }
        if(mUserType.equals("student")){
            mNavigationView.getMenu().clear();
            mNavigationView.inflateMenu(R.menu.menu_student_navigation);
            NavigationUtils.startStudentNaigation(this, mNavigationView);
        }else if(mUserType.equals("tutor")){
            mNavigationView.getMenu().clear();
            mNavigationView.inflateMenu(R.menu.menu_tutor_navigation);
            NavigationUtils.startTutorNaigation(this, mNavigationView);
        }
    }
    //initial code to run onn startup
    @Override
    protected void onStart() {
        if(mChatsListPresenter == null){
            mChatsListPresenter = new ChatListPresenter(this);
            String userId = LoggedInUser.getLoggedInUser().getUserId();
            mUserType = LoggedInUser.getLoggedInUser().getUserType();
            mChatsListPresenter.loadChats(userId, mUserType);
        }
        super.onStart();
    }

    //this method is called when database handler completes data fetchinn
    @Override
    public void onDataLoadComplete(List<Chat> chats, List<Tutor> tutors) {
        if (mChatAdapter == null) {
            mChatAdapter = new ChatAdapter(this, chats, tutors);
            mChatsRecyclerView.setAdapter(mChatAdapter);
        } else {
            mChatAdapter.notifyDataSetChanged();
        }
    }

    //display feedback to user actions
    @Override
    public void onResult(String result) {
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
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
            mChatsListPresenter.logoutUser();
            startActivity(new Intent(ChatListActivity.this, LoginActivity.class));
        }
        return true;
    }
    //exit app on back pressed
    //code updated in version 1.0.1 now app exit properly
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeScreenIntent = new Intent(Intent.ACTION_MAIN);
        homeScreenIntent.addCategory(Intent.CATEGORY_HOME);
        homeScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeScreenIntent);
        this.finish();
    }
    //inner class to provide horizontal seperator after every lis item
    class ListDivider extends RecyclerView.ItemDecoration{
        private Drawable mDivider;

        public ListDivider(Drawable divider) {
            mDivider = divider;
        }

        @Override
        public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
            int left = 16;
            int right = parent.getWidth()- 16;
            for(int i = 0; i < parent.getChildCount(); i++){
                View child = parent.getChildAt(i);
                int top = child.getBottom();
                int bottom = top + mDivider.getIntrinsicHeight();
                //Log.d(TAG, "Layout Position:" + left + "," +top + "," + right + "," + bottom);
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}