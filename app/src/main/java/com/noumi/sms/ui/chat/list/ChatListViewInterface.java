package com.noumi.sms.ui.chat.list;

import com.noumi.sms.data.model.Chat;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.data.model.Tutor;

import java.util.List;

interface ChatListViewInterface {
    void onChatsLoadByStudentId(List<Chat> chats, List<Tutor> tutors);
    void onChatsLoadByTutorId(List<Chat> chats, List<Student> students);
    void onResult(String result);
}