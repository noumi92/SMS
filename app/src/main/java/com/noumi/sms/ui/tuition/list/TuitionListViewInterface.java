package com.noumi.sms.ui.tuition.list;

import com.noumi.sms.data.model.Tuition;

import java.util.List;

interface TuitionListViewInterface {
    void onLoadComplete(List<Tuition> tuitions);
    void onResult(String result);
}