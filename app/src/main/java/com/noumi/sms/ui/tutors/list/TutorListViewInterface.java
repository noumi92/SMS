package com.noumi.sms.ui.tutors.list;

//this class defines contract for StudentListActivity and these methods are implemented in StudentListActivity

import com.noumi.sms.data.model.Tutor;

import java.util.List;

public interface TutorListViewInterface {
    void onLoadComplete(List<Tutor> tutors);
    void onResult(String result);
}
