package com.noumi.sms.ui.students;

//this class displays students in StudentListActivity

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.noumi.sms.R;
import com.noumi.sms.data.model.Student;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentHolder> {
    //field
    private List<Student> mStudents;
    //constructor
    public StudentAdapter(List<Student> students) {
        mStudents = students;
    }
    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_student_list, viewGroup, false);
        return new StudentHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull StudentHolder studentHolder, int i) {
        Student student = mStudents.get(i);
        studentHolder.mNameTextView.setText(student.getStudentName());
        studentHolder.mCityTextView.setText(student.getStudentCity());
        if(student.getStudentGender().equals("Male")){
            studentHolder.mProfileImageView.setImageResource(R.drawable.profile_male);
        }else if(student.getStudentGender().equals("Female")){
            studentHolder.mProfileImageView.setImageResource(R.drawable.profile_female);
        }
    }
    @Override
    public int getItemCount() {
        return mStudents.size();
    }
    //inner class which holds a single item of student data list
    public class StudentHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;
        private TextView mCityTextView;
        private ImageView mProfileImageView;
        public StudentHolder(@NonNull View itemView) {
            super(itemView);
            mProfileImageView = (ImageView) itemView.findViewById(R.id.student_thumb_view);
            mNameTextView = (TextView) itemView.findViewById(R.id.student_name_view);
            mCityTextView = (TextView) itemView.findViewById(R.id.student_city_view);
        }
    }
}
