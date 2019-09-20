package com.noumi.sms.ui.chat.list;

//this class displays students in StudentListActivity

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.noumi.sms.R;
import com.noumi.sms.data.model.Chat;
import com.noumi.sms.data.model.Tutor;
import com.noumi.sms.ui.chat.room.ChatRoomActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder>{
    //field
    private List<Chat> mChats;
    private Context mContext;
    private List<Tutor> mTutors;
    //constructor
    public ChatAdapter(Context context, List<Chat> chats, List<Tutor> tutors) {
        mChats = chats;
        mTutors = tutors;
        mContext = context;
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
        Tutor tutor = mTutors.get(i);
        chatHolder.bind(chat, tutor);
    }
    @Override
    public int getItemCount() {
        return mChats.size();
    }

    //inner class which holds a single item of student data list
    public class ChatHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private String TAG = "com.noumi.sms.custom.log";
        private TextView mChatTimeView;
        private TextView mTutorNameView;
        private Chat mChat;
        private Tutor mTutor;
        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mChatTimeView = (TextView) itemView.findViewById(R.id.chat_time_view);
            mTutorNameView = (TextView) itemView.findViewById(R.id.tutor_name_view);
        }

        @Override
        public void onClick(View view) {
            Intent chatIntent = new Intent(mContext, ChatRoomActivity.class);
            chatIntent.putExtra("chatId", mChat.getChatId());Log.d(TAG, "putExtra: " + mChat.getChatId());
            Log.d(TAG, "chatid in chatlist" + mChat.getChatId());
            mContext.startActivity(chatIntent);
        }

        public void bind(Chat chat, Tutor tutor) {
            mChat = chat;
            mTutor = tutor;
            mTutorNameView.setText(tutor.getTutorName());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
            String formattedDate = simpleDateFormat.format(chat.getChatTime());
            mChatTimeView.setText(formattedDate);
        }
    }
}