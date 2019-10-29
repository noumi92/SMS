package com.noumi.sms.ui.rating;

//this class displays students in StudentListActivity

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.noumi.sms.R;
import com.noumi.sms.data.model.Rating;
import com.noumi.sms.data.model.Student;

import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.RatingHolder> {
    //field
    private List<Rating> mRatings;
    private List<Student> mStudents;
    private Context mContext;
    //constructor
    public RatingAdapter(List<Rating> ratings, List<Student> students, Context context) {
        mRatings = ratings;
        mStudents = students;
        mContext = context;
    }
    @NonNull
    @Override
    public RatingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_rating_list, viewGroup, false);
        return new RatingHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RatingHolder ratingHolder, int i) {
        Rating rating = mRatings.get(i);
        String studentName = null;
        for(Student student: mStudents){
            if(TextUtils.equals(rating.getStudentId(),student.getStudentId())){
                studentName = student.getStudentName();
            }
        }
        ratingHolder.bind(rating, studentName);
    }
    @Override
    public int getItemCount() {
        return mRatings.size();
    }
    //inner class which holds a single item of student data list
    public class RatingHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView mStudentNameView;
        private TextView mRatingView;
        private TextView mCommentsView;
        private Rating mRating;
        public RatingHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            mStudentNameView = (TextView) itemView.findViewById(R.id.student_name_view);
            mRatingView = (TextView) itemView.findViewById(R.id.rating_view);
            mCommentsView = (TextView) itemView.findViewById(R.id.comments_view);
        }

        @Override
        public boolean onLongClick(View v) {
            AlertDialog.Builder deleteAccountDialog = new AlertDialog.Builder(mContext);
            deleteAccountDialog
                    .setTitle("Delete Account")
                    .setMessage("Are you sure you want to delete account permanently from system")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //mRatingAdapterPresenter.deleteRating(mRating.getRatingId());
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .create().show();
            return true;
        }

        private void bind(Rating rating, String studentName){
            mRating = rating;
            mStudentNameView.setText(studentName);
            mRatingView.setText(String.valueOf(mRating.getRating()));
            mCommentsView.setText(mRating.getComments());
        }
    }
}
