package com.noumi.sms.ui.tuition.list;

import com.noumi.sms.data.model.Tuition;

import java.util.List;

public interface TuitionAdapterInterface {
    void onLoadComplete(List<Tuition> tuitions);
}
