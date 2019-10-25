package com.noumi.sms.ui.chat.room;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.noumi.sms.R;
import com.noumi.sms.data.model.LoggedInUser;
import com.noumi.sms.data.model.Message;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ChatRoomHolder>{
    //field
    private String TAG = "com.noumi.sms.custom.log";
    private List<Message> mMessages;
    private Context mContext;
    private String mChatTitle;
    //constructor
    public ChatRoomAdapter(Context context, List<Message> messages, String chatId) {
        mMessages = messages;
        mContext = context;
        mChatTitle = chatId;
    }
    @NonNull
    @Override
    public ChatRoomHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_message_list, viewGroup, false);
        return new ChatRoomHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ChatRoomHolder chatRoomHolder, int i) {
        Message message = mMessages.get(i);
        Log.d(TAG, "message sender id@" + message.getSenderId() + " user id@" + LoggedInUser.getLoggedInUser().getUserId());
        String name;
        if(TextUtils.equals(message.getSenderId(), LoggedInUser.getLoggedInUser().getUserId())){
            name = LoggedInUser.getLoggedInUser().getUserName();
        }else{
            name = mChatTitle;
        }
        Log.d(TAG, "name: " + name);
        chatRoomHolder.bind(message, name);
    }
    @Override
    public int getItemCount() {
        return mMessages.size();
    }
    //inner class which holds a single item of student data list
    public class ChatRoomHolder extends RecyclerView.ViewHolder {

        private TextView mMessageSenderNameView;
        private TextView mMessageBodyView;
        private TextView mMessageTimeView;
        private Message mMessage;
        public ChatRoomHolder(@NonNull View itemView) {
            super(itemView);
            mMessageSenderNameView = (TextView) itemView.findViewById(R.id.sender_name_view);
            mMessageBodyView = (TextView) itemView.findViewById(R.id.message_body_view);
            mMessageTimeView = (TextView) itemView.findViewById(R.id.message_time_view);
        }

        public void bind(Message message, String name) {
            mMessage = message;
            mMessageSenderNameView.setText(name);
            mMessageBodyView.setText(mMessage.getMessageBody());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
            String formattedDate = simpleDateFormat.format(message.getMessageTime());
            mMessageTimeView.setText(formattedDate);
        }
    }
}



