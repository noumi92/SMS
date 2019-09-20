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
        //setup adapter here
        mMessagesRecyclerView.setHasFixedSize(true);
        mMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //setting up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar!=null){
            toolbar.setTitle(this.getTitle());
            setSupportActionBar(toolbar);
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
                    mChatRoomPresenter.sendMessage(mChatId, message);

                }
            }
        });
    }
    //initial code to run onn startup
    @Override
    protected void onStart() {
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
            mChatRoomAdapter = new ChatRoomAdapter(this, messages);
            mMessagesRecyclerView.setAdapter(mChatRoomAdapter);
        } else {
            mChatRoomAdapter.notifyDataSetChanged();
        }
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
            if(mChatRoomPresenter == null){
                mChatRoomPresenter = new ChatRoomPresenter(this);
            }
            mChatRoomPresenter.loadMessages(mChatId);
        }
    }
}
