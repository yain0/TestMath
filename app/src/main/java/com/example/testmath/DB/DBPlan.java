package com.example.testmath.DB;

import android.provider.BaseColumns;

public final class DBPlan {

    private DBPlan() {
    }

    public static class SubjectsTable implements BaseColumns {
        public static final String TABLE_NAME = "test_subjects";
        public static final String COLUMN_NAME = "name";
    }

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "test_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_POSITION1 = "position1";
        public static final String COLUMN_POSITION2 = "position2";
        public static final String COLUMN_POSITION3 = "position3";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
        public static final String COLUMN_SUBJECT_ID = "subject_id";
    }

}
