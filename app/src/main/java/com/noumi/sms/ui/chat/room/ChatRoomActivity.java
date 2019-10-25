package com.noumi.sms.ui.chat.room;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.noumi.sms.R;
import com.noumi.sms.data.model.LoggedInUser;
import com.noumi.sms.data.model.Message;
import com.noumi.sms.ui.login.LoginActivity;
import com.noumi.sms.utils.NavigationUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity implements ChatRoomViewInterface{

    private String TAG = "com.noumi.sms.custom.log";
    private String mChatId;
    private  ChatRoomPresenterInterface mChatRoomPresenter;
    private RecyclerView mMessagesRecyclerView;
    private ChatRoomAdapter mChatRoomAdapter;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private EditText mMessageEditText;
    private Button mSendMessage;
    private LinearLayout mProgressbar;
    private String mChatTitle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        //get references of views
        mMessagesRecyclerView = (RecyclerView) findViewById(R.id.messages_recycler_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_menu_view);
        mMessageEditText = (EditText) findViewById(R.id.message_edit_text);
        mSendMessage = (Button) findViewById(R.id.send_message_button);
        mProgressbar = (LinearLayout) findViewById(R.id.progressbar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //setup adapter here
        mMessagesRecyclerView.setHasFixedSize(true);
        mMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //setting up toolbar
        if(mToolbar != null){
            mToolbar.setTitle("Chat Room");
            setSupportActionBar(mToolbar);
        }
        NavigationUtils.startStudentNaigation(this, mNavigationView);

        getChatRoomIntent();

        mSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageBody = mMessageEditText.getText().toString();
                if(!messageBody.isEmpty()){
                    Date messageTime = Calendar.getInstance().getTime();
                    Message message = new Message(messageBody, LoggedInUser.getLoggedInUser().getUserId(), messageTime);
                    Log.d(TAG, "message added to: " + mChatId);
                    mChatRoomPresenter.sendMessage(mChatId, message);

                }
            }
        });
    }
    //initial code to run onn startup
    @Override
    protected void onStart() {
        mProgressbar.setVisibility(View.VISIBLE);
        if(mChatRoomPresenter == null){
            mChatRoomPresenter = new ChatRoomPresenter(this);
        }
        super.onStart();
    }

    //this method is called when database handler completes data fetchinn
    @Override
    public void onLoadComplete(List<Message> messages) {
        Log.d(TAG, "data result count:" + messages.size());
        if (mChatRoomAdapter == null) {
            mChatRoomAdapter = new ChatRoomAdapter(this, messages, mChatTitle);
            mMessagesRecyclerView.setAdapter(mChatRoomAdapter);
        } else {
            mChatRoomAdapter.notifyDataSetChanged();
        }
        mProgressbar.setVisibility(View.GONE);
    }
    //display feedback to user actions
    @Override
    public void onResult(String result) {
        mMessageEditText.setText("");
        mMessageEditText.clearFocus();
        mChatRoomPresenter.loadMessages(mChatId);
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
            mChatRoomPresenter.logoutUser();
            startActivity(new Intent(ChatRoomActivity.this, LoginActivity.class));
        }
        return true;
    }

    private void getChatRoomIntent(){
        if(getIntent().hasExtra("chatId")){
            mChatId = getIntent().getStringExtra("chatId");
            mChatTitle = getIntent().getStringExtra("senderName");
            mToolbar.setTitle(mChatTitle + "'s Chats");
            if(mChatRoomPresenter == null){
                mChatRoomPresenter = new ChatRoomPresenter(this);
            }
            mChatRoomPresenter.loadMessages(mChatId);
        }
    }
}
