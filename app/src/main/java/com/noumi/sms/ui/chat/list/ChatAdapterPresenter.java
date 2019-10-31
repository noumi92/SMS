package com.noumi.sms.ui.chat.list;

import com.noumi.sms.data.database.DatabaseHandler;
import com.noumi.sms.data.database.DatabaseInterface;
public class ChatAdapterPresenter implements ChatAdapterInterface {
    private DatabaseInterface mDatabaseHandler;

    public ChatAdapterPresenter() {
        mDatabaseHandler = new DatabaseHandler();
    }

    @Override
    public void deleteChatById(String chatId) {
        mDatabaseHandler.deleteChatById(chatId, this);
    }


}
