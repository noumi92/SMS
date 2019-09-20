package com.noumi.sms.ui.chat.room;

import com.noumi.sms.data.model.Message;

import java.util.List;

public interface ChatRoomPresenterInterface {
    void loadMessages(String threadId);
    void onMessagesLoadComplete(List<Message> messages);
    void logoutUser();
    void onQueryResult(String result);
    void sendMessage(String chatId, Message message);
}
