package com.joaojunio.contact.util;

import com.joaojunio.contact.model.RecordHistory;

import java.util.Date;

public class UpdateDatetimeAccessUser {

    public static void updateDatetimeAccessUser(RecordHistory recordHistory) {
        recordHistory.setDatetimeAccess(new Date());
    }
}
