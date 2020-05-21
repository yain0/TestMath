package com.example.testmath.DB;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DBConnect extends AsyncTask<String, Void, JSONArray> {

    public String server_name = "https://tests999.000webhostapp.com";
    public HttpURLConnection connect;


    private JSONArray getAllSubjects(){
        String answer, lnk;
        try {
            lnk = server_name + "/test_categories.php?action=select";
            connect = (HttpURLConnection) new URL(lnk)
                    .openConnection();
            connect.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String bfr_st = null;
            while ((bfr_st = br.readLine()) != null) {
                sb.append(bfr_st);
            }

            answer = sb.toString();
            br.close();
            JSONArray ja = new JSONArray();
            while (answer.indexOf("{") != -1) {
                int l = answer.indexOf("{"), r = answer.indexOf("}");
                String str = answer.substring(l, r + 1);
                JSONObject jObject = new JSONObject(str);
                ja.put(jObject);
                answer = answer.substring(r + 1);

            }
            return ja;
        }catch (Exception e){
            return null;
        }
    }

    private JSONArray getAllQuestions(){
        String answer, lnk;
        try {
            lnk = server_name + "/test_questions.php?action=select";
            connect = (HttpURLConnection) new URL(lnk)
                    .openConnection();
            connect.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String bfr_st = null;
            while ((bfr_st = br.readLine()) != null) {
                sb.append(bfr_st);
            }

            answer = sb.toString();
            br.close();
            JSONArray ja = new JSONArray();
            while (answer.indexOf("{") != -1) {
                int l = answer.indexOf("{"), r = answer.indexOf("}");
                String str = answer.substring(l, r + 1);
                JSONObject jObject = new JSONObject(str);
                ja.put(jObject);
                answer = answer.substring(r + 1);

            }
            return ja;
        }catch (Exception e){
            return null;
        }
    }

    private JSONArray getQuestions(String subjectID, String difficulty){
        String answer, lnk;
        try {
            lnk = server_name + "/test_questions.php?action=select&subject_id=" + subjectID + "&difficulty" + difficulty;
            connect = (HttpURLConnection) new URL(lnk)
                    .openConnection();
            connect.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String bfr_st = null;
            while ((bfr_st = br.readLine()) != null) {
                sb.append(bfr_st);
            }

            answer = sb.toString();
            br.close();
            JSONArray ja = new JSONArray();
            while (answer.indexOf("{") != -1) {
                int l = answer.indexOf("{"), r = answer.indexOf("}");
                String str = answer.substring(l, r + 1);
                JSONObject jObject = new JSONObject(str);
                if(jObject.getInt("subject_id") == Integer.parseInt(subjectID) && jObject.getString("difficulty") == difficulty)
                    ja.put(jObject);
                answer = answer.substring(r + 1);

            }
            return ja;
        }catch (Exception e){
            return null;
        }
    }


    @Override
    protected JSONArray doInBackground(String... strings) {
        if (strings[0].equals("getAllQuestions"))
            return getAllQuestions();
        if (strings[0].equals("getAllSubjects"))
            return getAllSubjects();
        if (strings[0].equals("getQuestions"))
            return getQuestions(strings[1], strings[2]);
        return null;
    }

}