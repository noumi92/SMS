package com.noumi.sms.ui.chat.room;

import com.noumi.sms.data.model.Message;

import java.util.List;

public interface ChatRoomViewInterface {
    void onLoadComplete(List<Message> messages);
    void onResult(String result);
}
