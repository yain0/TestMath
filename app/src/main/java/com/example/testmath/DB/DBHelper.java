package com.example.testmath.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.testmath.Models.Question;
import com.example.testmath.Models.Subject;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TestDB.db";
    private static final int DATABASE_VERSION = 6;

    private static DBHelper instance;

    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_SUBJECTS_TABLE = "CREATE TABLE " +
                DBPlan.SubjectsTable.TABLE_NAME + "( " +
                DBPlan.SubjectsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBPlan.SubjectsTable.COLUMN_NAME + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                DBPlan.QuestionsTable.TABLE_NAME + " ( " +
                DBPlan.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBPlan.QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                DBPlan.QuestionsTable.COLUMN_POSITION1 + " TEXT, " +
                DBPlan.QuestionsTable.COLUMN_POSITION2 + " TEXT, " +
                DBPlan.QuestionsTable.COLUMN_POSITION3 + " TEXT, " +
                DBPlan.QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                DBPlan.QuestionsTable.COLUMN_SUBJECT_ID + " INTEGER, " +

                "FOREIGN KEY(" + DBPlan.QuestionsTable.COLUMN_SUBJECT_ID + ") REFERENCES " +
                DBPlan.SubjectsTable.TABLE_NAME + "(" + DBPlan.SubjectsTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        db.execSQL(SQL_CREATE_SUBJECTS_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillSubjectsTable();
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBPlan.SubjectsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBPlan.QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillSubjectsTable() {
        Subject c1 = new Subject("Алгебра)");
        insertSubject(c1);
        Subject c2 = new Subject("Геометрия");
        insertSubject(c2);
    }

    public void addSubject(Subject subject){
        db = getWritableDatabase();
        insertSubject(subject);
    }

    public void addSubjects(List<Subject> subjects){
        db = getWritableDatabase();
        for(Subject subject : subjects){
            insertSubject(subject);
        }
    }

    public void insertSubject(Subject subject) {
        ContentValues cv = new ContentValues();
        cv.put(DBPlan.SubjectsTable.COLUMN_NAME, subject.getName());
        db.insert(DBPlan.SubjectsTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("2+2?",
                "6", "5", "4", 3,
                1);
        insertQuestion(q1);
        Question q2 = new Question("5+6?",
                "10", "11", "12", 2, 1);
        insertQuestion(q2);
        Question q3 = new Question("3/4?",
                "0,25", "0,85", "0,75", 3, 1);
        insertQuestion(q3);
        Question q4 = new Question("Какая дробь правильная?",
                "1/4", "5/2", "5/6", 1, 1);
        insertQuestion(q4);
        Question q5 = new Question("10*10",
                "20", "100", "1000", 2, 1);
        insertQuestion(q5);
        Question q6 = new Question("Сколько градусов составляет сумма углов треугольника?",
                "360", "180", "270", 2, 2);
        insertQuestion(q6);
        Question q7 = new Question("Какие векторы называются коллинеарными?",
                "Направленные в одну сторону", "Лежащие в одной плоскости", "Лежащие на параллельных прямых", 3, 2);
        insertQuestion(q7);
        Question q8 = new Question("Прямоугольник - это частный случай...",
                "Квадрата", "Ромба", "Параллелограмма", 3, 2);
        insertQuestion(q8);
        Question q9 = new Question("Верно ли утверждение, что параллелограмм является невыпуклым четырехугольником?",
                "Верно", "Неверно", "Иногда верно", 2,
                2);
        insertQuestion(q9);
        Question q10 = new Question("Вектор - это ...",
                "Отрезок", "Луч", "Прямая", 1, 2);
        insertQuestion(q10);
    }

    public void addQuestion(Question question) {
        db = getWritableDatabase();
        insertQuestion(question);
    }

    public void addQuestions(List<Question> questions) {
        db = getWritableDatabase();

        for (Question question : questions) {
            insertQuestion(question);
        }
    }

    public void insertQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(DBPlan.QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(DBPlan.QuestionsTable.COLUMN_POSITION1, question.getPosition1());
        cv.put(DBPlan.QuestionsTable.COLUMN_POSITION2, question.getPosition2());
        cv.put(DBPlan.QuestionsTable.COLUMN_POSITION3, question.getPosition3());
        cv.put(DBPlan.QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(DBPlan.QuestionsTable.COLUMN_SUBJECT_ID, question.getSubjectID());
        db.insert(DBPlan.QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjectList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DBPlan.SubjectsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Subject subject = new Subject();
                subject.setId(c.getInt(c.getColumnIndex(DBPlan.SubjectsTable._ID)));
                subject.setName(c.getString(c.getColumnIndex(DBPlan.SubjectsTable.COLUMN_NAME)));
                subjectList.add(subject);
            } while (c.moveToNext());
        }

        c.close();
        return subjectList;
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DBPlan.QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(DBPlan.QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(DBPlan.QuestionsTable.COLUMN_QUESTION)));
                question.setPosition1(c.getString(c.getColumnIndex(DBPlan.QuestionsTable.COLUMN_POSITION1)));
                question.setPosition2(c.getString(c.getColumnIndex(DBPlan.QuestionsTable.COLUMN_POSITION2)));
                question.setPosition3(c.getString(c.getColumnIndex(DBPlan.QuestionsTable.COLUMN_POSITION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(DBPlan.QuestionsTable.COLUMN_ANSWER_NR)));
                question.setSubjectID(c.getInt(c.getColumnIndex(DBPlan.QuestionsTable.COLUMN_SUBJECT_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(int subjectID) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = DBPlan.QuestionsTable.COLUMN_SUBJECT_ID + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(subjectID)};

        Cursor c = db.query(
                DBPlan.QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(DBPlan.QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(DBPlan.QuestionsTable.COLUMN_QUESTION)));
                question.setPosition1(c.getString(c.getColumnIndex(DBPlan.QuestionsTable.COLUMN_POSITION1)));
                question.setPosition2(c.getString(c.getColumnIndex(DBPlan.QuestionsTable.COLUMN_POSITION2)));
                question.setPosition3(c.getString(c.getColumnIndex(DBPlan.QuestionsTable.COLUMN_POSITION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(DBPlan.QuestionsTable.COLUMN_ANSWER_NR)));
                question.setSubjectID(c.getInt(c.getColumnIndex(DBPlan.QuestionsTable.COLUMN_SUBJECT_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
