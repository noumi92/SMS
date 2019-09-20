package com.noumi.sms.ui.chat.list;

//this class connects StudentListActivity to database and passes feedbacks from database to StudentListActivity to update views and
//feedback to user

import com.noumi.sms.data.database.DatabaseHandler;
import com.noumi.sms.data.database.DatabaseInterface;
import com.noumi.sms.data.model.Chat;
import com.noumi.sms.data.model.Tutor;

import java.util.List;

public class ChatListPresenter implements ChatListPresenterInterface {
    private DatabaseInterface mDatabaseHandler;
    private ChatListViewInterface mChatListViewInterface;

    ChatListPresenter(ChatListViewInterface listViewInterface) {
        mDatabaseHandler = new DatabaseHandler();
        mChatListViewInterface = listViewInterface;
    }
    //fetch all students from database
    @Override
    public void loadChats(String userId, String userType) {
        if(userType.equals("student")){
            mDatabaseHandler.getChatsByStudentId(userId, this);
        }else if(userType.equals("tutor")){
            mDatabaseHandler.getChatsByTutorId(userId, this);
        }
    }
    //logout user
    @Override
    public void logoutUser() {
        mDatabaseHandler.logoutUser();
    }
    //this method is called when all fetching is complete


    @Override
    public void onDataLoadComplete(List<Chat> chats, List<Tutor> tutors) {
        mChatListViewInterface.onDataLoadComplete(chats, tutors);
    }

    //method to get feedback from database handler and pass to StudentListActivity
    @Override
    public void onQueryResult(String result) {
        mChatListViewInterface.onResult(result);
    }
}
