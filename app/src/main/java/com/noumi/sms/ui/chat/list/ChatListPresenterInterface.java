package com.noumi.sms.ui.chat.list;

//this class defines contract for TuitionListPresenter and these methods are implemented in TuitionListPresenter

import com.noumi.sms.data.model.Chat;
import com.noumi.sms.data.model.Tutor;

import java.util.List;

public interface ChatListPresenterInterface {
    void loadChats(String userId, String userType);
    void onDataLoadComplete(List<Chat> chats, List<Tutor> tutors);
    void logoutUser();
    void onQueryResult(String result);
}
