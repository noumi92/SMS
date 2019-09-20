package com.noumi.sms.ui.chat.room;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.noumi.sms.R;
import com.noumi.sms.data.model.Message;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ChatRoomHolder>{
    //field
    private List<Message> mMessages;
    private Context mContext;
    //constructor
    public ChatRoomAdapter(Context context, List<Message> messages) {
        mMessages = messages;
        mContext = context;
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
        chatRoomHolder.bind(message);
    }
    @Override
    public int getItemCount() {
        return mMessages.size();
    }


    //inner class which holds a single item of student data list
    public class ChatRoomHolder extends RecyclerView.ViewHolder {
        private String TAG = "com.noumi.sms.custom.log";
        private TextView mMessageSenderNameView;
        private TextView mMessageBodyView;
        private TextView mMessageTimeView;
        private RelativeLayout mMessageContainer;
        private Message mMessage;
        private Point mWindoeSize;
        public ChatRoomHolder(@NonNull View itemView) {
            super(itemView);
            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            mWindoeSize = new Point();
            display.getSize(mWindoeSize);
            mMessageSenderNameView = (TextView) itemView.findViewById(R.id.sender_name_view);
            mMessageBodyView = (TextView) itemView.findViewById(R.id.message_body_view);
            mMessageTimeView = (TextView) itemView.findViewById(R.id.message_time_view);
            mMessageContainer = (RelativeLayout) itemView.findViewById(R.id.message_container);
        }

        public void bind(Message message) {
            mMessage = message;

            //ViewGroup.LayoutParams prams = mMessageContainer.getLayoutParams();
            //prams.width = mWindoeSize.x - 100;
            //mMessageContainer.setLayoutParams(prams);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mWindoeSize.x - 100, RelativeLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.addRule(RelativeLayout.RIGHT_OF);//in my case
            mMessageContainer.setLayoutParams(layoutParams);


            mMessageSenderNameView.setText(mMessage.getSenderId());
            mMessageBodyView.setText(mMessage.getMessageBody());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
            String formattedDate = simpleDateFormat.format(message.getMessageTime());
            mMessageTimeView.setText(formattedDate);
        }
    }
}



