package com.noumi.sms.ui.tutors.list;

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
import com.noumi.sms.data.model.Tutor;
import com.noumi.sms.ui.tutors.detail.TutorDetailActivity;

import java.util.List;

public class TutorAdapter extends RecyclerView.Adapter<TutorAdapter.TutorHolder> {
    //field
    private List<Tutor> mTutors;
    private Context mContext;
    //constructor
    public TutorAdapter(Context context, List<Tutor> tutors) {
        mTutors = tutors;
        mContext = context;
    }
    @NonNull
    @Override
    public TutorHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_tutor_list, viewGroup, false);
        return new TutorHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TutorHolder tutorHolder, int i) {
        Tutor tutor = mTutors.get(i);
        tutorHolder.bind(tutor);
        tutorHolder.mNameTextView.setText(tutor.getTutorName());
        tutorHolder.mCityTextView.setText(tutor.getTutorCity());
        if(tutor.getTutorGender().equals("Male")){
            tutorHolder.mProfileImageView.setImageResource(R.drawable.profile_male);
        }else if(tutor.getTutorGender().equals("Female")){
            tutorHolder.mProfileImageView.setImageResource(R.drawable.profile_female);
        }
    }
    @Override
    public int getItemCount() {
        return mTutors.size();
    }

    public void setTutors(List<Tutor> tutors) {
        mTutors = tutors;
    }

    //inner class which holds a single item of student data list
    public class TutorHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private String TAG = "com.noumi.sms.custom.log";
        private TextView mNameTextView;
        private TextView mCityTextView;
        private ImageView mProfileImageView;
        private Tutor mTutor;
        public TutorHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mProfileImageView = (ImageView) itemView.findViewById(R.id.tutor_thumb_view);
            mNameTextView = (TextView) itemView.findViewById(R.id.tutor_name_view);
            mCityTextView = (TextView) itemView.findViewById(R.id.tutor_city_view);
        }

        @Override
        public void onClick(View view) {
            Intent tutorIntent = new Intent(mContext, TutorDetailActivity.class);
            tutorIntent.putExtra("tutorId", mTutor.getTutorId());
            Log.d(TAG, "putExtra: " + mTutor.getTutorId());
            mContext.startActivity(tutorIntent);
        }

        public void bind(Tutor tutor) {
            mTutor = tutor;
        }
    }
}
