package com.noumi.sms.ui.chat.list;

//this class displays students in StudentListActivity

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.noumi.sms.R;
import com.noumi.sms.data.model.Chat;
import com.noumi.sms.data.model.Student;
import com.noumi.sms.data.model.Tutor;
import com.noumi.sms.ui.chat.room.ChatRoomActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {
    //field
    private String TAG = "com.noumi.sms.custom.log";
    private List<Chat> mChats;
    private Context mContext;
    private List<Tutor> mTutors;
    private List<Student> mStudents;
    //constructor
    public ChatAdapter(Context context, List<Chat> chats, List<Tutor> tutors) {
        mChats = chats;
        mTutors = tutors;
        mStudents = null;
        mContext = context;
    }
    //constructor
    public ChatAdapter(List<Chat> chats, Context context, List<Student> students) {
        mChats = chats;
        mContext = context;
        mStudents = students;
        mTutors = null;
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_chat_list, viewGroup, false);
        return new ChatHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ChatHolder chatHolder, int i) {
        Chat chat = mChats.get(i);
        String name = null;
        //get name
        if(mStudents == null){
            for(Tutor tutor: mTutors){
                if(TextUtils.equals(tutor.getTutorId(),chat.getTutorId())){
                    name = tutor.getTutorName();
                }
            }
        }else if(mTutors == null){
            for(Student student: mStudents){
                if(TextUtils.equals(student.getStudentId(),chat.getStudentId())){
                    name = student.getStudentName();
                }
            }
        }

        chatHolder.bind(chat, name);
    }
    @Override
    public int getItemCount() {
        return mChats.size();
    }

    //inner class which holds a single item of student data list
    public class ChatHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private String TAG = "com.noumi.sms.custom.log";
        private TextView mChatTimeView;
        private TextView mNameView;
        private Chat mChat;
        private String mName;
        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mChatTimeView = (TextView) itemView.findViewById(R.id.chat_time_view);
            mNameView = (TextView) itemView.findViewById(R.id.tutor_name_view);
        }

        @Override
        public void onClick(View view) {
            Intent chatIntent = new Intent(mContext, ChatRoomActivity.class);
            chatIntent.putExtra("chatId", mChat.getChatId());
            chatIntent.putExtra("senderName", mName);
            mContext.startActivity(chatIntent);
        }

        public void bind(Chat chat, String name) {
            mChat = chat;
            mName = name;
            mNameView.setText(name);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
            String formattedDate = simpleDateFormat.format(chat.getChatTime());
            mChatTimeView.setText(formattedDate);
        }
    }
}