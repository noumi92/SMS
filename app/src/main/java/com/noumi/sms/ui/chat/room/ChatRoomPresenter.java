package com.noumi.sms.ui.chat.room;

import com.noumi.sms.data.database.DatabaseHandler;
import com.noumi.sms.data.database.DatabaseInterface;
import com.noumi.sms.data.model.Message;
import java.util.List;

public class ChatRoomPresenter implements ChatRoomPresenterInterface {
    private DatabaseInterface mDatabaseHandler;
    private ChatRoomViewInterface mChatRoomViewInterface;

    public ChatRoomPresenter(ChatRoomViewInterface chatRoomViewInterface) {
        mDatabaseHandler = new DatabaseHandler();
        mChatRoomViewInterface = chatRoomViewInterface;
    }

    @Override
    public void loadMessages(String threadId) {
        mDatabaseHandler.getMessages(threadId, this);
    }

    @Override
    public void onMessagesLoadComplete(List<Message> messages) {
        mChatRoomViewInterface.onLoadComplete(messages);
    }

    @Override
    public void logoutUser() {
        mDatabaseHandler.logoutUser();
    }

    @Override
    public void onQueryResult(String result) {
        mChatRoomViewInterface.onResult(result);
    }

    @Override
    public void sendMessage(String chatId, Message message) {
        mDatabaseHandler.sendMessage(chatId, message, this);
    }
}
