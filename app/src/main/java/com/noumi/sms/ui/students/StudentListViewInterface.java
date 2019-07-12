package com.noumi.sms.ui.students;

//this class defines contract for StudentListActivity and these methods are implemented in StudentListActivity

import com.noumi.sms.data.model.Student;
import java.util.List;

public interface StudentListViewInterface {
    void onLoadComplete(List<Student> students);
    void onResult(String result);
}
