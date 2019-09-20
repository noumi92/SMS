package com.noumi.sms.ui.chat.list;

import com.noumi.sms.data.model.Chat;
import com.noumi.sms.data.model.Tutor;

import java.util.List;

interface ChatListViewInterface {
    void onDataLoadComplete(List<Chat> chats, List<Tutor> tutors);
    void onResult(String result);
}