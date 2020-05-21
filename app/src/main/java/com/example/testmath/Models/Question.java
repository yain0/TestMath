package com.example.testmath.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {

    private int id;
    private String question;
    private String position1;
    private String position2;
    private String position3;
    private int answerNr;
    private int subjectID;

    public Question() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPosition1() {
        return position1;
    }

    public void setPosition1(String position1) {
        this.position1 = position1;
    }

    public String getPosition2() {
        return position2;
    }

    public void setPosition2(String position2) {
        this.position2 = position2;
    }

    public String getPosition3() {
        return position3;
    }

    public void setPosition3(String position3) {
        this.position3 = position3;
    }

    public int getAnswerNr() {
        return answerNr;
    }

    public void setAnswerNr(int answerNr) {
        this.answerNr = answerNr;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public Question(String question, String position1, String position2, String position3,
                    int answerNr, int subjectID) {
        this.question = question;
        this.position1 = position1;
        this.position2 = position2;
        this.position3 = position3;
        this.answerNr = answerNr;
        this.subjectID = subjectID;
    }

    protected Question(Parcel in) {
        id = in.readInt();
        question = in.readString();
        position1 = in.readString();
        position2 = in.readString();
        position3 = in.readString();
        answerNr = in.readInt();
        subjectID = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(question);
        dest.writeString(position1);
        dest.writeString(position2);
        dest.writeString(position3);
        dest.writeInt(answerNr);
        dest.writeInt(subjectID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };





}