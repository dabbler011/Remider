package com.example.akshat.remider.data;

import android.provider.BaseColumns;

/**
 * Created by akshat on 16/6/17.
 */

public class dbContract {
    public static final class dbEntry implements BaseColumns{
        public static final String TABLE_NAME = "data";
        public static final String COLUMN_NAME = "column_name";
        public static final String COLUMN_STATUS = "column_status";
    }
}
