package com.noumi.sms.ui.tuition.list;

//this class displays students in StudentListActivity

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.noumi.sms.R;
import com.noumi.sms.data.model.Tuition;
import com.noumi.sms.ui.tuition.detail.TuitionDetailActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TuitionAdapter extends RecyclerView.Adapter<TuitionAdapter.TuitionHolder>{
    //field
    private List<Tuition> mTuitions;
    private Context mContext;
    //constructor
    public TuitionAdapter(Context context, List<Tuition> tuitions) {
        mTuitions = tuitions;
        mContext = context;
    }
    @NonNull
    @Override
    public TuitionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_tuition_list, viewGroup, false);
        return new TuitionHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TuitionHolder tuitionHolder, int i) {
        Tuition tuition = mTuitions.get(i);
        tuitionHolder.bind(tuition);
        tuitionHolder.mTuitionThumbView.setImageResource(R.drawable.thumb_tuition);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        String formattedDate = simpleDateFormat.format(tuition.getRequestedDate());
        tuitionHolder.mTuitionDateView.setText(formattedDate);
        if(tuition.isActive()){
            tuitionHolder.mTuitionActiveView.setText("Active");
        }else{
            tuitionHolder.mTuitionActiveView.setText("Inactive");
        }
        if(tuition.isAccepted()){
            tuitionHolder.mTuitionAcceptedView.setText("Accepted");
        }else{
            tuitionHolder.mTuitionAcceptedView.setText("Not Accepted");
        }
    }
    @Override
    public int getItemCount() {
        return mTuitions.size();
    }


    //inner class which holds a single item of student data list
    public class TuitionHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private String TAG = "com.noumi.sms.custom.log";
        private TextView mTuitionDateView;
        private TextView mTuitionActiveView;
        private TextView mTuitionAcceptedView;
        private ImageView mTuitionThumbView;
        private Tuition mTuition;
        public TuitionHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTuitionThumbView = (ImageView) itemView.findViewById(R.id.tuition_thumb_view);
            mTuitionDateView = (TextView) itemView.findViewById(R.id.tuition_date_view);
            mTuitionActiveView = (TextView) itemView.findViewById(R.id.tuition_active_view);
            mTuitionAcceptedView = (TextView) itemView.findViewById(R.id.tuition_acceptance_view);
        }

        @Override
        public void onClick(View view) {
            Intent tuitionIntent = new Intent(mContext, TuitionDetailActivity.class);
            tuitionIntent.putExtra("tuitionId", mTuition.getTuitionId());
            Log.d(TAG, "putExtra: " + mTuition.getTuitionId());
            mContext.startActivity(tuitionIntent);
        }

        public void bind(Tuition tuition) {
            mTuition = tuition;
        }
    }
}
