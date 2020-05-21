package com.example.testmath.DB;

import com.example.testmath.Models.Question;
import com.example.testmath.Models.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DBService {
   /* String server_name = "https://tests999.000webhostapp.com";
    HttpURLConnection connect;
    Cursor cursor;*/

    public DBService() {
    }

    public List<Subject> getAllSubjects() throws JSONException {
        List<Subject> subjectList = new ArrayList<>();
        DBConnect asyncClass = new DBConnect();
        JSONArray ja = null;
        try {
            ja = asyncClass.execute("getAllSubjects").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jo;
        int i=0;
        while (ja != null && i < ja.length()) {
            jo = ja.getJSONObject(i);
            Subject subject = new Subject();
            subject.setId(Integer.parseInt(jo.getString("_id")));
            subject.setName(jo.getString("name"));
            subjectList.add(subject);
            i++;
        }
        return subjectList;
    }

    public ArrayList<Question> getAllQuestions() throws JSONException {
        ArrayList<Question> questionList = new ArrayList<>();
        DBConnect asyncClass = new DBConnect();
        JSONArray ja = null;
        try {
            ja = asyncClass.execute("getAllQuestions").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jo;
        int i=0;
        while (ja != null && i < ja.length()) {
            jo = ja.getJSONObject(i);
            Question question = new Question();
            question.setId(Integer.parseInt(jo.getString("_id")));
            question.setQuestion(jo.getString("question"));
            question.setPosition1(jo.getString("position1"));
            question.setPosition2(jo.getString("position2"));
            question.setPosition3(jo.getString("position3"));
            question.setAnswerNr(Integer.parseInt(jo.getString("answer_nr")));
            question.setSubjectID(Integer.parseInt(jo.getString("subject_id")));
            questionList.add(question);
            i++;
        }
        return questionList;
    }

    public ArrayList<Question> getQuestions(int subjectID) throws JSONException {
        ArrayList<Question> questionList = getAllQuestions();
        ArrayList<Question> _questionList = new ArrayList<>();
        for (Question question: questionList
             ) {
            if(question.getSubjectID() == subjectID){
                _questionList.add(question);
            }
        }
        return _questionList;
    }

}
